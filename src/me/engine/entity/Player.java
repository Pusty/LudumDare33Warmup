package me.engine.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.engine.entity.EntityLiving;
import me.engine.location.Location;
import me.engine.location.Velocity;
import me.engine.main.Controls;
import me.engine.main.MainClass;
import me.engine.skill.*;

import org.lwjgl.opengl.GL11;

public class Player extends EntityLiving {

	Location mousevelocity;
	public Player(MainClass m,int x, int y) {
		super(m ,x, y,1f,1f);
//		this.setMainItem(new Item(30));
//		this.setUtilItem(new Item(31));
		setSkill(0,new SkillFireball());
	}
	

	public Entity createByString(MainClass m,String s,String split){
		Entity e = null;
		System.out.println("Initing "+getName());
		try{
		int x = Integer.parseInt(s.split(split)[0]);
		int z = Integer.parseInt(s.split(split)[1]);
		e=new Player(m,x,z);
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
	
	
	protected Player(){/*USED FOR DUMMY*/}
	public  String getName(){return "Player";}
	
	public Location getMouseLocation(){
		return mousevelocity;
	}
	
	public void setMouseLocation(Location v){
		mousevelocity=v;
	}
	@Override
	public Velocity getMoveDirection(){
		if(getMouseLocation()!=null)
		return Location.getNorm(getMouseLocation()).toVelocity();
		return new Velocity(0,0);
	}


	Random random = new Random();
	public void tick(MainClass m){
		
	//TICK PLAYER SKILLS AND SO ON	
	if(getHealth()>0){
		if(hasEffect("SKILL1")>9999){
			this.removeEffect("SKILL1");
			Skill skill = this.getSkill(0);
			skill.startSkill(this, m,"SKILL1");
			this.addStatus("SKILL1", skill.getCooldown(), false);
		}else if(hasEffect("SKILL1")>0)
		{
			Skill skill = this.getSkill(0);
			skill.tick(this,m);
		}else if(hasEffect("SKILL1")==0){
			Skill skill = this.getSkill(0);
			skill.end(this,m);
		}
		if(hasEffect("DMG")==Status.DAMAGE_COOLDOWN-1){
			playAnimation("death",3, Status.DAMAGE_COOLDOWN);
			addStatus("UNMOVE",Status.DAMAGE_COOLDOWN/4,false);
		}
	}
		if(Controls.ispressedDebug){
			damage(1000,true);
			Controls.ispressedDebug=false;
		}
		
		if(getHealth()==0 && hasEffect("UNMOVE")==-1){
			addStatus("UNMOVE",this.getAnimation().getAnimation("death").size-10,true);
			playAnimation("death",-1,-1);
			this.setHealth(-1);
		}
		if(getHealth()==-1 && hasEffect("UNMOVE")==-1)
			this.setHealth(-2);
		 if(getHealth()==-2){
			addStatus("UNMOVE",1,true);
			playAnimation("death",3, 10);
		}
		if(hasEffect("UNMOVE") == -1){
		if(right)
			Moveright(m);
		if(left)
			Moveleft(m);
		if(up)
			Moveforward(m);
		if(down)
			Movebackward(m);
		}
		
		tickStatus();
	}


	public String getTextureName(int i){
		return "player2";
	}
	
	
	
	public float getSpeed(){return 0.025f;}

	public void addRender(){
		if(getAnimation().currentanimation != 0 || moving)
			getAnimation().getCurA().addTick();
	}

	public void render(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f, 0.5f, 0f);
		String renderID = getRenderID();
		glBindTexture(GL_TEXTURE_2D,m.getPictureLoader().getImageAsInteger(renderID));
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
		GL11.glPopMatrix();
	}
	public void renderShadow(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f, 0.5f, 0f);
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

	public String getDialogText(Random random) {
	int index = random.nextInt(3);
	if(index==0)return null;
	if(index==1)return "Maybe some cheese is nearby?";
	if(index==2)return "I am going to collect all the cheese in this world!";
		return null;
	}


	
	


}


