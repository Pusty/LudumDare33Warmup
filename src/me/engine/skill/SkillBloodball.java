package me.engine.skill;

import me.engine.entity.EntityLiving;
import me.engine.entity.Particle;
import me.engine.entity.Projectile;
import me.engine.entity.ProjectileBlood;
import me.engine.entity.ProjectileFlame;
import me.engine.entity.Status;
import me.engine.location.Location;
import me.engine.location.Velocity;
import me.engine.main.MainClass;

public class SkillBloodball extends Skill {
	public SkillBloodball(){
		super();
	}

	@Override
	public int getCooldown() {
		return 240;
	}
	
	

	@Override
	public void tick(EntityLiving living, MainClass m) {
		if(living.hasEffect(skillname)==235){
			living.addStatus("UNMOVE", 160, false);
			living.playAnimation("attack",-1,-1);
		}
		if(living.hasEffect(skillname)==155){
			Velocity v= new Velocity(0,0);
			if(living.getSide()==0)v=new Velocity(0,-0.5f/20);
			if(living.getSide()==1)v=new Velocity(0,0.5f/20);
			if(living.getSide()==2)v=new Velocity(0.5f/20,0);
			if(living.getSide()==3)v=new Velocity(-0.5f/20,0);
			skillproj[0]=m.getWorld().addEntity(new ProjectileBlood(m,living.getX()+v.x/2f,living.getZ()+v.z/2f,v,this));
			m.getSoundPlayer().playSound("exp"+0, true);
		}
	}

	@Override
	public void reset(EntityLiving living,MainClass m) {
		this.skilllocs=new Location[1];
		this.skillproj=new int[1];
		this.skilllocs[0]=living.getLocation().clone();
		this.sender=living;
	}

	@Override
	public void end(EntityLiving living,MainClass m) {
	}

	@Override
	public String getName() {
		return "Bloodball";
	}
	@Override
	public boolean projectileHit(MainClass m,Projectile p,EntityLiving hit){
		if(hit.equals(sender))return false;
		if(Location.getDistance(p.getLocation(), hit.getLocation()) < 0.5f){
			int health = hit.getHealth();
			int after = hit.damage(1,true);
			if(health>after)
			m.getWorld().addParticle(new Particle(hit.getX(),hit.getZ(),80,new Velocity(0,0),4));
		
		}
		return false;
	}
}
