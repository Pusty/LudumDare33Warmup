package me.engine.skill;

import me.engine.entity.EntityLiving;
import me.engine.entity.Projectile;
import me.engine.location.Location;
import me.engine.main.MainClass;

public abstract class Skill {
	protected int[] skillproj;
	protected Location[] skilllocs;
	protected String skillname;
	public EntityLiving sender;
	public Skill(){
	}
	public abstract int getCooldown();
	public abstract String getName();
	public abstract void tick(EntityLiving living, MainClass m);
	public void startSkill(EntityLiving living,MainClass m, String string){
		skillname=string;
		reset(living,m);
	}
	public abstract void reset(EntityLiving living,MainClass m);
	public abstract void end(EntityLiving living,MainClass m);
	public boolean projectileHit(MainClass m,Projectile p,EntityLiving hit){
		return false;
	}
}
