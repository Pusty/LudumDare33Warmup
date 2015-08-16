package me.engine.main;



import me.engine.entity.Entity;
import me.engine.entity.EntityLiving;
import me.engine.entity.EntityMonster;
import me.engine.entity.EntityPortal;
import me.engine.entity.EntityTree;
import me.engine.entity.NPCEntity;
import me.engine.entity.Projectile;

import me.engine.location.Location;
import me.engine.world.Chunk;
import me.game.main.StartClass;

public class GameTickHandler {
	MainClass mainclass;

	public GameTickHandler(MainClass m) {
		mainclass = m;
	}

	public void startGameTick() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (mainclass.isRunning()) {
					if (mainclass.isTimeRunning() && mainclass.hasMapLoaded()) {
						gameTick();
					}
					try {
						Thread.sleep((int) (1000 / 200 / mainclass
								.getTimeSpeed()));

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (mainclass.isRunning()) {
					if (mainclass.isTimeRunning() && mainclass.hasMapLoaded()) {
						gameTickEntity();
					}
					try {
						// Thread.sleep((int)(1000/200/mainclass.getTimeSpeed()));
						Thread.sleep((int) (1000 / 200 / mainclass
								.getTimeSpeed()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}
int ticks=0;
int tickupdate=40;
	public void gameTickEntity() {
		ticks++;
		if(ticks>tickupdate)ticks=0;
		for(int i = 0; i < mainclass.getWorld().getParticleArray().length;i++){
			if(mainclass.getWorld().getParticleArray()[i] == null)continue;
			mainclass.getWorld().getParticleArray()[i].addRender();
			mainclass.getWorld().getParticleArray()[i].getLocation().set(mainclass.getWorld().getParticleArray()[i].getLocation().add(mainclass.getWorld().getParticleArray()[i].getTempVelocity().toLocation()));
			if(mainclass.getWorld().getParticleArray()[i].getAnimation().getCurA().played)
				mainclass.getWorld().getParticleArray()[i]=null;
		}
		for (int i = 0; i < mainclass.getWorld().getEntityArray().length; i++) {
			if (mainclass.getWorld().getEntityArray()[i] == null)
				continue;
			if (mainclass.getWorld().getEntityArray()[i] instanceof EntityLiving) {
				EntityLiving el = (EntityLiving) mainclass.getWorld()
						.getEntityArray()[i];
				if (el == null)
					continue;
				
				
				el.tick(mainclass);
				movingTickEntity(mainclass, el);
				
				if (el instanceof NPCEntity) {
						((NPCEntity) el).addRender();
				} else if (el instanceof EntityMonster) {
						((EntityMonster) el).addRender();
				}
				
				el.recalcNextLocation(false);
				if(ticks==tickupdate){
					Location next = el.getNextLocation();
					Location mainl = el.getLocation();
					if (next.x > mainl.x && Math.abs(next.x - mainl.x) > 0.1f)
						el.right(mainclass);
					else
						el.right=false;
					if (next.x <= mainl.x && Math.abs(next.x - mainl.x) > 0.1f)
						el.left(mainclass);
					else
						el.left=false;
					if (next.z > mainl.z && Math.abs(next.z - mainl.z) > 0.1f)
						el.forward(mainclass);
					else
						el.up=false;
					if (next.z <= mainl.z && Math.abs(next.z - mainl.z) > 0.1f)
						el.backward(mainclass);
					else
						el.down=false;
				}
			} else if (mainclass.getWorld().getEntityArray()[i] instanceof EntityPortal) {
				EntityPortal ep = (EntityPortal) mainclass.getWorld()
						.getEntityArray()[i];
				ep.addRender();

				if (ep == null || ep.isFrom())
					continue;
				if (Location.getDistance(mainclass.getWorld().getPlayer()
						.getLocation(), ep.getLocation()) < 0.5f) {
					// LOADING NEW MAP
					mainclass.getSavedData().putData("worldOld",(int)mainclass.getSavedData().getData("world"));
					mainclass.getSavedData().putData("world",ep.getPortal());
					((StartClass) mainclass).load((int)mainclass.getSavedData().getData("world"));
					System.out.println("Load new map");
				}
			} else if (mainclass.getWorld().getEntityArray()[i] instanceof Projectile) {
				Projectile pf = (Projectile) mainclass.getWorld()
						.getEntityArray()[i];
				movingTickProjectile(mainclass, pf);
				pf.addRender();
				if (pf.getSkill() != null) {
					if(ticks==40)
					pf.tick(mainclass);
					
				if(!pf.getSkill().projectileHit(mainclass,pf, mainclass.getWorld().getPlayer()))
					for (int a = 0; a < mainclass.getWorld().getEntityArray().length; a++) {
						if (mainclass.getWorld().getEntityArray()[a] == null)
							continue;
						if (mainclass.getWorld().getEntityArray()[a] instanceof EntityLiving) {
							EntityLiving el = (EntityLiving) mainclass
									.getWorld().getEntityArray()[a];
							if (pf.getSkill().projectileHit(mainclass,pf, el))
								break;
						}
					}
				}

			}
		}

	}

	public void gameTick() {
		mainclass.getWorld().getPlayer().tick(mainclass);
		movingTickEntity(mainclass, mainclass.getWorld().getPlayer());
		mainclass.getWorld().getPlayer().addRender();
		if(ticks == tickupdate-1){
			mainclass.getSavedData().putData("posX",mainclass.getWorld().getPlayer().getX());
			mainclass.getSavedData().putData("posZ",mainclass.getWorld().getPlayer().getZ());
		}
//		if (mainclass.getWorld().getPlayer().getHealth() < 1)
//			mainclass.setTimeRunning(false);
	}

	public static boolean collision(Location b, float x, float z) {
		x = x + 0.5f;
		return ((b.getX() + 1f >= x && b.getX() <= x) && (b.getZ() + 1f >= z && b
				.getZ() <= z));
		// return true;
	}

	public static void movingTickEntity(MainClass mainclass, EntityLiving living) {
		boolean done = true;
		boolean done2 = true;
		boolean done3 = true;
		living.moving = false;
		if (!living.getVelocity().isnull() || living.getTempVelocity() != null) {
			{
				living.moving = true;
				float x = living.getLocation().getX()
						+ living.getVelocity().getX();
				float z = living.getLocation().getZ();
				if (living.getTempVelocity() != null)
					x = x + living.getTempVelocity().getX();
				float x2 = living.getLocation().getX();
				float z2 = living.getLocation().getZ()
						+ living.getVelocity().getZ();

				if (living.getTempVelocity() != null)
					z2 = z2 + living.getTempVelocity().getZ();
				for (Chunk c : mainclass.getWorld().getChunkArray()) {
					if (Location
							.getDistance(
									new Location(c.getChunkX(), c.getChunkZ()),
									new Location(living.getX() / 10, living
											.getZ() / 10)) < 2f) {
						int metaID = 0;
						Location blockLocation;
						for (int bx = 0; bx < 10; bx++)
							for (int bz = 0; bz < 10; bz++) {
								metaID = mainclass.getWorld().getBlockMeta(
										c.getChunkX() * 10 + bx,
										c.getChunkZ() * 10 + bz);

								blockLocation = new Location(c.getChunkX() * 10
										+ bx, c.getChunkZ() * 10 + bz);
								if (metaID == 1 &&
								// Location.getDistance(
								// living.getLocation(),
								// blockLocation) < 2f
										inRange(living.getLocation(),
												blockLocation, 3f)) {
									if (collision(blockLocation, x, z)) {
										done = false;
									}
									if (collision(blockLocation, x2, z)) {
										done2 = false;
									}
									if (collision(blockLocation, x, z2)) {
										done3 = false;
									}
								}
							}
					}
				}

				for (int index = 0; index < mainclass.getWorld()
						.getEntityArray().length; index++) {
					Entity e = mainclass.getWorld().getEntityArray()[index];
					if (e == null || e instanceof NPCEntity)
						continue;
					if (!(e instanceof EntityTree))
						continue;
					if (Location.getDistance(
							e.getLocation().add(new Location(0.25f, 0.25f)),
							new Location(x, z)) < 0.5f)
						done = false;
					if (Location.getDistance(
							e.getLocation().add(new Location(0.25f, 0.25f)),
							new Location(x2, z)) < 0.5f)
						done2 = false;
					if (Location.getDistance(
							e.getLocation().add(new Location(0.25f, 0.25f)),
							new Location(x, z2)) < 0.5f)
						done3 = false;
				}

				if (x < 0 || x > mainclass.getWorld().getSizeX() - 1 || z < 0
						|| z > mainclass.getWorld().getSizeZ() - 1)
					done = false;
				if (x2 < 0 || x2 > mainclass.getWorld().getSizeX() - 1
						|| z2 < 0 || z2 > mainclass.getWorld().getSizeZ() - 1)
					done2 = false;
				if (x < 0 || x > mainclass.getWorld().getSizeX() - 1 || z2 < 0
						|| z2 > mainclass.getWorld().getSizeZ() - 1)
					done3 = false;

				if (done3
						&& !(living.getVelocity().getX() == 0f && living
								.getVelocity().getZ() == 0f)) {
					living.getLocation().setX(x);
					living.getLocation().setZ(z2);
				} else if (done) {
					living.getLocation().setX(x);
					living.getLocation().setZ(z);
				} else if (done2) {
					living.getLocation().setX(x2);
					living.getLocation().setZ(z2);
				}
				living.getVelocity().reset();
				if (living.getTempVelocity() != null)
					living.setTempVelocity(living.getTempVelocity().sub());

			}
		}
	}

	public static void movingTickProjectile(MainClass mainclass, Projectile proj) {

		boolean done3 = true;
		if (!proj.getVelocity().isnull() || proj.getTempVelocity() != null) {
			{
				float x = proj.getLocation().getX() + proj.getVelocity().getX();
				if (proj.getTempVelocity() != null)
					x = x + proj.getTempVelocity().getX();
				float z2 = proj.getLocation().getZ()
						+ proj.getVelocity().getZ();

				if (proj.getTempVelocity() != null)
					z2 = z2 + proj.getTempVelocity().getZ();

				for (Chunk c : mainclass.getWorld().getChunkArray()) {
					if (Location.getDistance(
							new Location(c.getChunkX(), c.getChunkZ()),
							new Location(proj.getX() / 10, proj.getZ() / 10)) < 2f) {
						int metaID = 0;
						Location blockLocation;
						for (int bx = 0; bx < 10; bx++)
							for (int bz = 0; bz < 10; bz++) {
								metaID = mainclass.getWorld().getBlockMeta(
										c.getChunkX() * 10 + bx,
										c.getChunkZ() * 10 + bz);
								blockLocation = new Location(c.getChunkX() * 10
										+ bx, c.getChunkZ() * 10 + bz);
								if (metaID == 1
										&& inRange(proj.getLocation(),
												blockLocation, 3f)) {
									if (collision(blockLocation, x, z2)) {
										done3 = false;
									}
								}
							}
					}
				}

				for (int index = 0; index < mainclass.getWorld()
						.getEntityArray().length; index++) {
					Entity e = mainclass.getWorld().getEntityArray()[index];
					if (e == null || e instanceof NPCEntity)
						continue;
					if (e instanceof Projectile)
						continue;
				}

				if (x < 0 || x > mainclass.getWorld().getSizeX() - 1 || z2 < 0
						|| z2 > mainclass.getWorld().getSizeZ() - 1)
					done3 = false;

				if (done3) {
					proj.getLocation().setX(x);
					proj.getLocation().setZ(z2);
				} else {
					mainclass.getWorld().removeEntity(proj);
					proj.hit(mainclass);
				}

				proj.getVelocity().reset();
				if (proj.getTempVelocity() != null)
					proj.setTempVelocity(proj.getTempVelocity().sub());

			}
		}
	}

	public static boolean inRange(Location loc, Location block, float range) {
		return loc.x + range > block.x && loc.z + range > block.z
				&& loc.x - range < block.x && loc.z - range < block.z;
	}
}
