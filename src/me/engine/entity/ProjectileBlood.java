package me.engine.entity;

import me.engine.location.Velocity;
import me.engine.main.MainClass;

import me.engine.render.Animation;
import me.engine.skill.Skill;


public class ProjectileBlood extends Projectile{
	public ProjectileBlood(MainClass m,float x, float y,Velocity v,Skill skill) {
		super(m,x,y,v,skill);
	}	
	protected ProjectileBlood(){/*USED FOR DUMMY*/}
	
	public Entity createByString(MainClass m,String s,String split){
		Entity e = null;
		System.out.println("Initing "+getName());
		try{
		int x = Integer.parseInt(s.split(split)[0]);
		int z = Integer.parseInt(s.split(split)[1]);
		Velocity v = new Velocity(Float.parseFloat(s.split(split)[2]),Float.parseFloat(s.split(split)[3]));
		e=new ProjectileBlood(m,x,z,v,null);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return e;
	}
	public  String getName(){return "ProjectileBlood";}
	

	public String getTextureName(int i){return "blood";}
	public void tick(MainClass m){
		m.getWorld().addParticle(new Particle(getX(),getZ(),80,new Velocity(0,0),3));
	}
	public void hit(MainClass m){
		m.getWorld().addParticle(new Particle(getX(),getZ(),80,new Velocity(0,0),4));
	}
	public void addRender(){
		Animation a = getAnimation().getCurA();
		a.addTick();
//		if (a.played && a.ticks == 0)a.ticks =  a.speed*2/4;
	}
}
