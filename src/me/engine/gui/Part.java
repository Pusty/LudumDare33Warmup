package me.engine.gui;

import me.engine.location.Location;
import me.engine.main.MainClass;

public class Part {
	Location loc;
	float sizex;
	float sizey;
	float p1;
	float p2;
	float p3;
	float p4;
	public Part(Location loc,float w,float h,float p1,float p2,float p3,float p4) {
		this.loc = loc;
		sizex=w;
		sizey=h;
		this.p1=p1;
		this.p2=p2;
		this.p3=p3;
		this.p4=p4;
	}
	public float getP1(){
		return p1;
	}
	public float getP2(){
		return p2;
	}
	public float getP3(){
		return p3;
	}
	public float getP4(){
		return p4;
	}
	
	public Location getLocation() {
		return loc;
	}
	public void tick(){
		
	}

	public void click(MainClass m,float mx,float mz) {

	}
	
	public void render(MainClass m){
		
	}
	public float getSizeX(){return sizex;}
	public float getSizeY(){return sizey;}
}
