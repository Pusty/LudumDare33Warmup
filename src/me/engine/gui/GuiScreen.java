package me.engine.gui;

import me.engine.main.MainClass;

public class GuiScreen {
	Part[] parts;

	public GuiScreen(int in) {
		parts = new Part[in];
	}
	public int size(){return parts.length;}
	public void setGuiPart(int in,Part p){
		parts[in]=p;
	}
	public Part getGuiPart(int in){
		return parts[in];
	}
	
	public void tick(){
		for(int in=0;in<size();in++){
			if(parts[in]==null)continue;
				parts[in].tick();
		}
	}
	
	public void click(MainClass m,float mx,float my){
		float x = mx/m.getWidth()*100f;
		float y = my/m.getHeight()*100f;
		for(int in=0;in<size();in++){
			if(parts[in]==null)continue;
			if(y>=parts[in].getP1() && y <= parts[in].getP2()
			&& x>=parts[in].getP3() && x <= parts[in].getP4())
				parts[in].click(m,x,y);
		}
	}
}
