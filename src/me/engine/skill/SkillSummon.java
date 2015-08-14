package me.engine.skill;

import me.engine.entity.EntityLiving;
import me.engine.entity.EntitySummon;
import me.engine.entity.Projectile;
import me.engine.entity.ProjectileFlame;
import me.engine.entity.Status;
import me.engine.location.Location;
import me.engine.location.Velocity;
import me.engine.main.MainClass;

public class SkillSummon extends Skill {
	public SkillSummon(){
		super();
	}

	@Override
	public int getCooldown() {
		return 1000;
	}
	
	

	@Override
	public void tick(EntityLiving living, MainClass m) {
		if(living.hasEffect(skillname)==1000-10){
			living.addStatus("UNMOVE", 360, false);
			living.playAnimation("attack",-1,360);
		}
		if(living.hasEffect(skillname)==1000-360){
			Velocity v= new Velocity(0,0);
			if(living.getSide()==0)v=new Velocity(0,-0.5f/20);
			if(living.getSide()==1)v=new Velocity(0,0.5f/20);
			if(living.getSide()==2)v=new Velocity(0.5f/20,0);
			if(living.getSide()==3)v=new Velocity(-0.5f/20,0);
			skillproj[0]=m.getWorld().addEntity(new EntitySummon(m,living.getX()+v.x/2f,living.getZ()+v.z/2f,this));
			m.getSoundPlayer().playSound("exp"+0, true);
		}
	}

	@Override
	public void reset(EntityLiving living,MainClass m) {
		if(skillproj!=null)
			m.getWorld().getEntityArray()[skillproj[0]]=null;
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
		return "Fireball";
	}
}
