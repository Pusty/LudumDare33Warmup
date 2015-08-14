package me.engine.render;


public class Animation {
	public String name;
	public String file;
	public int size;
	public int startFrame;
	public boolean loop;
	public boolean played;
	public int ticks;
	public int speed;
	Animation(String en, String an, int size, int start, boolean loop) {
		this.name = an;
		this.file = en;
		this.size = size;
		this.startFrame = start;
		this.loop = loop;
		ticks = (size*startFrame/4);
		played = false;
		speed=size;
	}

	public void addTick() {
		ticks = ticks + 1;
		if (ticks >= speed) {
			ticks = 0;
			played = true;
		}
	}

	public boolean isDone() {
		return (!loop && played);
	}

	public void start(int frame,int sp) {
		if(frame==-1)
			frame=this.startFrame;
		if(sp==-1)
			speed=this.size;
		else
			speed=sp;
		ticks = (speed*frame/4);
		played = false;
	}

	public String play(int side) {
		float usedrender = ticks;
		int index = (int) Math.floor((usedrender / speed * 4));
		return file + "_" + (side * 4 + index);
	}
	
	public Animation clone(){
		Animation n = new Animation(file, name, size, startFrame, loop);
		n.file=file;
		n.loop=loop;
		n.name=name;
		n.played=played;
		n.size=size;
		n.speed=speed;
		n.startFrame=startFrame;
		n.ticks=ticks;
		return n;
	}

}