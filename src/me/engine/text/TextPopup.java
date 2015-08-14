package me.engine.text;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.util.ArrayList;
import java.util.List;


import me.engine.main.MainClass;

import org.lwjgl.opengl.GL11;

public class TextPopup {
	String string;
	float x;
	float y;
	public TextPopup(String s,float x,float y){
	 string=s;
	 this.x=x;
	 this.y=y;
	}

	public void render(MainClass m){render(m,x,y,string);}	
	public static void render(MainClass m,float x,float z,String s){
//		GL11.glLoadIdentity();
//		GL11.glEnable (GL11.GL_BLEND); 
		GL11.glTranslatef(-14f, -10.5f, 0);
//		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
//			GL11.glTranslatef(-(d.getText().toUpperCase().length()/4)*1.5f,0,0);

			for(int xo=0;xo<s.toUpperCase().length();xo++){
				GL11.glTranslatef(dis*xo, 0, 0);
				renderChar(m,x,z,s.toUpperCase().toCharArray()[xo]);
//				renderChar(m,xo,(-i*1.5f)+((options.length/2)*1.5f),d.getText().toUpperCase().toCharArray()[xo],i==selecteddialog);
				GL11.glTranslatef(-xo*dis, 0, 0);

			}
		
//			GL11.glTranslatef(+1, 5.7f, 0);
//			GL11.glTranslatef(0, -((-i*1.5f)+((options.size()/2)*1.5f)), 0);
//			GL11.glTranslatef((d.getText().toUpperCase().length()/4)*1.5f,0,0);
		

		GL11.glTranslatef(14f, 10.5f, 0);
//		GL11.glDisable(GL11.GL_BLEND);
	}
	public static float dis = 0.7f;
	public static void renderChar(MainClass m,float x,float y,char c){
		
//		y=y+5f;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor4f(1f,1f,1f,0.8f);
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(x+0f, y+1f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(x+dis, y+1f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(x+dis, y+0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(x+0f, y+0f);
		GL11.glEnd();
		GL11.glColor4f(1f, 1f, 1f,1f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		glBindTexture(GL_TEXTURE_2D,
				m.getPictureLoader().getImageAsInteger("char_" + c));
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(x+0f, y+1f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(x+dis, y+1f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(x+dis, y+0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(x+0f, y+0f);
		GL11.glEnd();
		

	}


}
