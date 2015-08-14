package me.engine.skill;

import me.engine.entity.EntityLiving;
import me.engine.entity.Particle;
import me.engine.entity.Projectile;
import me.engine.entity.ProjectileFlame;
import me.engine.entity.Status;
import me.engine.location.Location;
import me.engine.location.Velocity;
import me.engine.main.MainClass;

public class SkillSmash extends Skill {
	public SkillSmash(){
		super();
	}

	@Override
	public int getCooldown() {
		return 240;
	}
	float range=1.5f;
	@Override
	public void tick(EntityLiving living, MainClass m) {
		if(living.hasEffect(skillname)==235){
			living.addStatus("UNMOVE", 80, false);
			living.playAnimation("death",-1,80);
			
			for(int i=0;i<12;i++){
				float mx=(float) Math.cos(Math.toRadians(i*30))*range;
				float mz=(float) Math.sin(Math.toRadians(i*30))*range;
//				x=x+living.getX();
//				z=z+living.getZ();#
				m.getWorld().addParticle(new Particle(living.getX(),living.getZ()-0.5f-0.15f,80,new Velocity(mx/20/80,mz/20/80),0));
			}
		}
		if(living.hasEffect(skillname)==235-80){
			living.playAnimation("walk",-1,-1);
			hit(living, m.getWorld().getPlayer());
				for (int a = 0; a < m.getWorld().getEntityArray().length; a++) {
					if (m.getWorld().getEntityArray()[a] == null)
						continue;
					if (m.getWorld().getEntityArray()[a] instanceof EntityLiving) {
						EntityLiving el = (EntityLiving) m
								.getWorld().getEntityArray()[a];
						hit(living, el);
					}
				}
			
			m.getSoundPlayer().playSound("exp"+0, true);
		}
	}
	
	private void hit(EntityLiving at,EntityLiving goal){
		if(at.equals(goal))return;
		if(goal.getName().equalsIgnoreCase(at.getName()))return;
		
		if(Location.getDistance(at.getLocation(), goal.getLocation()) < range){
			goal.damage(1, true);
			goal.addStatus("UNMOVE", 40, true);
		}

	}

	@Override
	public void reset(EntityLiving living,MainClass m) {
		this.skilllocs=new Location[1];
		this.skilllocs[0]=living.getLocation().clone();
		this.sender=living;
	}

	@Override
	public void end(EntityLiving living,MainClass m) {
	}

	@Override
	public String getName() {
		return "Smash";
	}

}
