package me.engine.entity;

import me.engine.location.TempVelocity;
import me.engine.location.Velocity;
import me.engine.render.AnimationHandler;

public class Particle extends Entity{
	int texture;
	public Particle(float x,float z,int speed,Velocity v,int texture){
		super(x,z,1f,1f);
		this.setTempVelocity(new TempVelocity(v.x,v.z,1000));
		this.getAnimation().animations[0].size=speed;
		this.texture=texture;
		privathandler=AnimationHandler.getHandler(getTextureName(texture)).copy();
	}
	
	public  String getName(){return "Particle";}
	

	public String getTextureName(int in){
		if(in==-1)return "flame";
		return "par"+in;
		
	}
}
