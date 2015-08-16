package me.engine.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import org.lwjgl.opengl.GL11;

import me.engine.location.Location;
import me.engine.main.MainClass;
import me.engine.render.Render2D;

public class SkillInventory extends Part{

	public SkillInventory(Location loc) {
		super(loc,-1,-1,20,80,20,80);
	}
	int timer=0;
	int buttonIndex=0;
	int skillIndex=0;
	int offset=0;
	public void click(MainClass m,float mx,float mz) {
		if(timer>=0){}
		else{
		buttonClick(m,mx,mz);
		timer=20;
		}
	}
	
	public int getItemIndex(){
		return skillIndex+offset;
	}
	public void tick(){
		if(timer>=0)timer--;
	}
	public void buttonClick(MainClass m,float mx,float mz){
		int value = (int) ((mz-this.getP3()) / 10);
		buttonIndex=4-value;
		if(buttonIndex >= 0 && buttonIndex < 4)
			skillIndex=buttonIndex;
		
		if(buttonIndex == -1){
			if(offset > 0){
			offset=offset-1;
			skillIndex=skillIndex+1;
			}
		}if(buttonIndex == 4){
			String skills = (String) m.getSavedData().getData("inv");
			String[] inv = skills.split("%");
			if(offset < inv.length-4){
			offset=offset+1;
			skillIndex=skillIndex-1;
			}
		}
	}
	
	public void render(MainClass m){
		String skills = (String) m.getSavedData().getData("inv");
		String[] inv = skills.split("%");
		String[] invS = new String[inv.length];
		for(int in=0;in<inv.length;in++){
			if(inv[in].split("&").length > 1)
			invS[in]=inv[in].split("&")[1];
		}
		
		String[] show = new String[4];
		
		for(int in=0;in<4;in++){
			if(in+offset < invS.length && invS[in+offset] != null){
				show[in]=invS[in+offset];
			}else{show[in]="None";}
		}
		
		
		for(int i=0;i<4;i++){
			renderButton(m,i,i==skillIndex,show[i]);
		}
		renderButton(m,-1,-1==buttonIndex,"///");
		renderButton(m,4,4==buttonIndex,"///");
	}
	public float getSizeY(){
		return 2f;
	}
	public float getSizeX(){
		return 10f;
	}
	public void renderButton(MainClass m,int i,boolean b,String text){
		GL11.glPushMatrix();
		GL11.glTranslatef(loc.x+0.5f, loc.z+0.5f, 0f);
		GL11.glTranslatef(0f, -2f*i, 0);
		if((b && i >= 0 && i < 4) || ((i==-1 || i==4 ) && b && timer > 0))
			glBindTexture(GL_TEXTURE_2D,
					m.getPictureLoader().getImageAsInteger("item_8"));
		else
			glBindTexture(GL_TEXTURE_2D,
					m.getPictureLoader().getImageAsInteger("item_0"));
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(0f,this.getSizeY());
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(getSizeX(),getSizeY());
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(getSizeX(),0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(0f,0f);	
		glEnd(); 

		GL11.glTranslatef(5f- text.length()/2f,0.5f,0f);
		Render2D.renderString(m, text);
		GL11.glPopMatrix();
	}
}
