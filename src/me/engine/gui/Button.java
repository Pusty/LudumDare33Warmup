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

public class Button extends Part{
String text;
	public Button(Location loc, float i, float j,float p1,float p2,float p3,float p4,String t) {
		super(loc,i,j,p1,p2,p3,p4);
		text=t;
	}
	int timer=0;
	public void click(MainClass m,float mx,float mz) {
		if(timer>=0){}
		else{
		buttonClick(m,mx,mz);
		timer=20;
		}
	}
	public void tick(){
		if(timer>=0)timer--;
	}
	public void buttonClick(MainClass m,float mx,float mz){
		
	}
	
	public void render(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(loc.x+0.5f, loc.z+0.5f, 0f);
		if(timer<0)
			glBindTexture(GL_TEXTURE_2D,
					m.getPictureLoader().getImageAsInteger("item_0"));
		else
			glBindTexture(GL_TEXTURE_2D,
					m.getPictureLoader().getImageAsInteger("item_8"));
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
		
		GL11.glTranslatef(getSizeX()/2 - text.length()/2f,0.5f,0f);
		Render2D.renderString(m, text);
		GL11.glPopMatrix();
		
	}
}
