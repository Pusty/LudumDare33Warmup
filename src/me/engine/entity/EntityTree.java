package me.engine.entity;


import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;

import me.engine.main.MainClass;

import org.lwjgl.opengl.GL11;

public class EntityTree extends Entity{
	MainClass main;
	float size=4f;
	public EntityTree(MainClass m,float x, float y) {
		super(x, y, 1f,1f);
		main=m;
	}	

	
	protected EntityTree(){/*USED FOR DUMMY*/}
	
	public Entity createByString(MainClass m,String s,String split){
		Entity e = null;
		System.out.println("Initing "+getName());
		try{
		int x = Integer.parseInt(s.split(split)[0]);
		int z = Integer.parseInt(s.split(split)[1]);
		e=new EntityTree(m,x,z);
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
		s=s+"\n";
		return s;
	}
	

	public void addRender(){
	}
	
	public void playAnimation(int ind,int f,int s){
	}
	public void playAnimation(String name,int f,int s){
	}
	public String getRenderID(){
		return "tree";
	}
	
	public void renderShadow(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(this.getX(), this.getZ(), 0);
		GL11.glTranslatef(0, -0.15f*2, 0f);
		glBindTexture(GL_TEXTURE_2D,m.getPictureLoader().getImageAsInteger("shadow"));
		glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f,size);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(size-0.5f,size);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(size-0.5f,0);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f,0);
		GL11.glEnd();
		GL11.glTranslatef(0f, 0.15f*2, 0f);
		GL11.glPopMatrix();
	}

	public void render(MainClass m) {
		GL11.glPushMatrix();
		GL11.glTranslatef(this.getX(), this.getZ(), 0);
		glBindTexture(GL_TEXTURE_2D,m.getPictureLoader().getImageAsInteger("tree"));
		glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f,size);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(size-0.5f,size);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(size-0.5f,0);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f,0);
		GL11.glEnd();
		

		
	
		GL11.glPopMatrix();
	}
}
