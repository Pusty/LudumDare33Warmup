package me.engine.render;

import java.util.HashMap;

public class AnimationHandler {
	public int size;
	public Animation[] animations;
	public int index;
	public int currentanimation;

	public AnimationHandler(int s) {
		size = s;
		animations = new Animation[size];
		index = 0;
		currentanimation = 0;
	}

	public void addAnimation(String en, String an, int size, int start,
			boolean loop) {
		animations[index] = new Animation(en, an, size, start, loop);
		index++;
	}

	public String getCur(int side) {
		Animation anim = animations[currentanimation];
		return anim.play(side);
	}

	public Animation getCurA() {
		Animation anim = animations[currentanimation];
		if (anim.isDone()) {
			anim = animations[0];
			animations[0].start(-1,-1);
			currentanimation = 0;
		}
		return anim;
	}

	public void playAnimation(int ind, int frame,int speed) {
		animations[ind].start(frame,speed);
		currentanimation = ind;
	}

	public void playAnimation(String name, int frame,int speed) {
		int index=0;
		for (int i = 0; i < size; i++)
			if (animations[i] != null
					&& animations[i].name.equalsIgnoreCase(name))
			{index=i;break;}
		animations[index].start(frame,speed);
		currentanimation = index;
	}

	
	public Animation getAnimation(int ind) {
		return animations[ind];
	}

	public Animation getAnimation(String name) {
		for (int i = 0; i < size; i++)
			if (animations[i] != null
					&& animations[i].name.equalsIgnoreCase(name))
				return animations[i];
		return null;
	}

	static HashMap<String, AnimationHandler> handlers = new HashMap<String, AnimationHandler>();

	public static AnimationHandler getHandler(String name) {
		if (handlers.containsKey(name.toLowerCase())) {
			return handlers.get(name);
		}
		return null;
	}

	public static void addHandler(String name, int size) {
		handlers.put(name, new AnimationHandler(size));
	}

	public AnimationHandler copy() {
	AnimationHandler n = new AnimationHandler(size);
	Animation[] anim = new Animation[animations.length];
	for(int i=0;i<anim.length;i++)
		anim[i]=animations[i].clone();
	n.animations=anim;
	n.currentanimation=currentanimation;
	n.index=index;
	n.size=size;
	return n;
	}
            


}
