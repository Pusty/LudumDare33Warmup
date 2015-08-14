package me.engine.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import java.awt.Window.Type;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import me.engine.location.Location;
import me.engine.location.TempVelocity;
import me.engine.location.Velocity;
import me.engine.main.MainClass;
import me.engine.render.AnimationHandler;

public class Entity{
	Location location;
	float sizex;
	float sizez;
	Velocity velocity;
	TempVelocity tempvelocity;
	int index;
	AnimationHandler privathandler;
	public Entity(float x,float z,float sx,float sz){
		index=(new Random()).nextInt(1000000);
		location=new Location(x,z);
		this.sizex=sx;
		this.sizez=sz;
		this.velocity=new Velocity(0,0);
		tempvelocity=null;
		
		privathandler=AnimationHandler.getHandler(getTextureName(-1)).copy();
//		System.out.println(privathandler);
	}
	public AnimationHandler getAnimation(){
		return privathandler;
	}
	protected Entity(){/*USED FOR DUMMY*/}
	
	public String getName(){return this.getClass().getSimpleName();}
	public static Entity createByName(Class<? extends Entity> type,MainClass m,String string,String split){
		try {
			Entity obj = type.cast(type.newInstance());
			return obj.createByString(m,string, split);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Entity createByName(String en, MainClass m, String string,
			String split) {
			Class<? extends Entity> type = Entity.class;
			if(en.equals(EntityLiving.class.getSimpleName()))
				type = EntityLiving.class;
			if(en.equals(Player.class.getSimpleName()))
				type = Player.class;
			if(en.equals(EntityPortal.class.getSimpleName()))
				type = EntityPortal.class;
			if(en.equals(Entity.class.getSimpleName()))
				type = Entity.class;
	return createByName(type,m,string,split);
	}
	
	public Entity createByString(MainClass m,String s,String split){
		Entity e = null;
		System.out.println("Initing "+getName());
		try{
		int x = Integer.parseInt(s.split(split)[0]);
		int z = Integer.parseInt(s.split(split)[1]);
		float sx = Float.parseFloat(s.split(split)[2]);
		float sz = Float.parseFloat(s.split(split)[3]);
		e=new Entity(x,z,sx,sz);
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(s);
		}
		return e;
	}
	public String createToString(MainClass m,String split){
		String s="";
		s=s+(int)this.getX();
		s=s+split;
		s=s+(int)this.getZ();
		s=s+split;
		s=s+this.getSizeX();
		s=s+split;
		s=s+this.getSizeZ();
		s=s+"\n";
		return s;
	}
	public int getIndex(){return index;}
	public Velocity getVelocity(){
		return velocity;
	}
	public TempVelocity getTempVelocity(){
		return tempvelocity;
	}
	public void setTempVelocity(TempVelocity tv){
		tempvelocity=tv;
	}
	
	public float getSizeX(){
		return sizex;
	}
	public float getSizeZ(){
		return sizez;
	}
	public Location getLocation(){
		return location;
	}
	public float getX(){
		return location.getX();
	}
	public float getZ(){
		return location.getZ();
	}

	
	public String getTextureName(int i){
		return "player2";
	}
	


	
	public void addRender(){
		getAnimation().getCurA().addTick();
	}
	
	public void playAnimation(int ind,int f,int s){
		getAnimation().playAnimation(ind,f,s);
	}
	public void playAnimation(String name,int f,int s){
		getAnimation().playAnimation(name,f,s);
	}
	public String getRenderID(){
		if(getAnimation() != null)
			return getAnimation().getCur(0);
		return "null";
	}
	
	
	public void render(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(location.x+0.5f, location.z+0.5f, 0);
		glBindTexture(GL_TEXTURE_2D,
		m.getPictureLoader().getImageAsInteger(getRenderID()));
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f,0.5f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(0.5f,0.5f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(0.5f,-0.5f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f,-0.5f);	
		glEnd(); 
		GL11.glPopMatrix();
	}

	
	public Entity copy(){
		Entity e = new Entity(this.getLocation().getX(),this.getLocation().getZ(),this.getSizeX(),this.getSizeZ());
		return e;
	}

	public void renderShadow(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(this.getX()+0.5f, this.getZ()+0.5f, 0);
		GL11.glTranslatef(0, -0.15f, 0f);
		glBindTexture(GL_TEXTURE_2D,m.getPictureLoader().getImageAsInteger("shadow"));
		glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f,0.5f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(0.5f,0.5f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(0.5f,-0.5f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f,-0.5f);	
		GL11.glEnd();
		GL11.glTranslatef(0f, 0.15f, 0f);
		GL11.glPopMatrix();
	}
	
}
