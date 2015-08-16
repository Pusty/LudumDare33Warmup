package me.engine.entity;



import java.util.Random;

import me.engine.location.Location;

import me.engine.main.MainClass;
import me.engine.skill.SkillSmash;



public class EntitySlime extends EntityMonster {
	public EntitySlime(MainClass m, int x, int y) {
		super(m, x, y, "slime");
		setSkill(0,new SkillSmash());
	}

	public String getDialogText(Random r) {
		int random = r.nextInt(3);
		if (random == 0)
			return "I am a slime";
		if (random == 1)
			return "SLIIIME";
		if (random == 2)
			return "SLIMY SLIMY";
		return null;
	}
	@Override
	public int getStartHealth(){return 3;}
	public Entity createByString(MainClass m, String s, String split) {
		Entity e = null;
		System.out.println("Initing " + getName());
		try {
			int x = Integer.parseInt(s.split(split)[0]);
			int z = Integer.parseInt(s.split(split)[1]);
			e = new EntitySlime(m, x, z);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}

	public String createToString(MainClass m, String split) {
		String s = "";
		s = s + (int) this.getX();
		s = s + split;
		s = s + (int) this.getZ();
		s = s + "\n";
		return s;
	}


	public void tick(MainClass m){
		if(Location.getDistance(this.getLocation(), m.getWorld().getPlayer().getLocation())<1f)
			addStatus("SKILL1",999999,false);
		
		
		super.tick(m);
	}
	protected EntitySlime() {/* USED FOR DUMMY */
	}

	public String getName() {
		return "Slime";
	}

	
	Random random = new Random();


	public void recalcNextLocation(boolean b) {
		times++;
		timer++;
		if(timer<=40 && !b)return;
		timer=0;
		if (nextlocation == null || times > 10){
			nextlocation = main.getWorld().getPlayer().getLocation()
					.add(new Location(random.nextFloat(), random.nextFloat()))
					.clone();
			times=0;
		}
		if (Location.getDistance(this.getLocation(), nextlocation) < 0.5f) {
			nextlocation = main.getWorld().getPlayer().getLocation()
					.add(new Location(random.nextFloat(), random.nextFloat()))
					.clone();

		}
	}
	
	public void addRender(){
		getAnimation().getCurA().addTick();
	}

	public String getTextureName(int i){
		return "slime";
	}
	public void render(MainClass m){
		super.render(m);
	}
	
	
}
