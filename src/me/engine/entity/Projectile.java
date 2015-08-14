package me.engine.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import java.util.ArrayList;

import me.engine.location.Location;
import me.engine.location.TempVelocity;
import me.engine.location.Velocity;
import me.engine.main.Controls;
import me.engine.main.MainClass;
import me.engine.render.Animation;
import me.engine.render.Render2D;
import me.engine.skill.Skill;
import me.engine.world.Chunk;
import me.game.main.StartClass;

import org.lwjgl.opengl.GL11;

public class Projectile extends Entity{
	MainClass main;
	Velocity vel;
	Skill skill;
	public Projectile(MainClass m,float x, float y,Velocity v,Skill skill) {
		super(x, y, 1f,1f);
		main=m;
		vel=v;
		this.setTempVelocity(new TempVelocity(v.x,v.z,1000));
		this.skill=skill;
	}	
	public Skill getSkill(){return skill;}
	public void tick(MainClass m){}
	public void hit(MainClass m){}
	
	protected Projectile(){/*USED FOR DUMMY*/}
	public  String getName(){return "Projectile";}
	
	public Entity createByString(MainClass m,String s,String split){
		Entity e = null;
		System.out.println("Initing "+getName());
		try{
		int x = Integer.parseInt(s.split(split)[0]);
		int z = Integer.parseInt(s.split(split)[1]);
		Velocity v = new Velocity(Float.parseFloat(s.split(split)[2]),Float.parseFloat(s.split(split)[3]));
		e=new Projectile(m,x,z,v,null);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return e;
	}
	public String createToString(MainClass m,String split){
		String s="";
		s=s+(int)this.getX();
		s=s+split;
		s=s+(int)this.getZ();
		s=s+split;
		s=s+(int)vel.x;
		s=s+split;
		s=s+(int)vel.z;
		s=s+"\n";
		return s;
	}
	public void addRender(){
		Animation a = getAnimation().getCurA();
		a.addTick();
		if (a.played && a.ticks == 0)a.ticks =  a.speed*2/4;
	}


}
