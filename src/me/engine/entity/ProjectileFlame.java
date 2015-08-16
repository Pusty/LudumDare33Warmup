package me.engine.entity;


import me.engine.location.Velocity;
import me.engine.main.MainClass;

import me.engine.skill.Skill;


public class ProjectileFlame extends Projectile{
	public ProjectileFlame(MainClass m,float x, float y,Velocity v,Skill skill) {
		super(m,x,y,v,skill);
	}	
	protected ProjectileFlame(){/*USED FOR DUMMY*/}
	
	public Entity createByString(MainClass m,String s,String split){
		Entity e = null;
		System.out.println("Initing "+getName());
		try{
		int x = Integer.parseInt(s.split(split)[0]);
		int z = Integer.parseInt(s.split(split)[1]);
		Velocity v = new Velocity(Float.parseFloat(s.split(split)[2]),Float.parseFloat(s.split(split)[3]));
		e=new ProjectileFlame(m,x,z,v,null);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return e;
	}
	public  String getName(){return "ProjectileFlame";}
	

	public String getTextureName(int i){return "flame";}
	public void tick(MainClass m){
		m.getWorld().addParticle(new Particle(getX(),getZ(),80,new Velocity(0,0),2));
	}
	public void hit(MainClass m){
		m.getWorld().addParticle(new Particle(getX(),getZ(),80,new Velocity(0,0),1));
	}
}
