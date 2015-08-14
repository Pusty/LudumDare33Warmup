package me.engine.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import me.engine.location.Location;
import me.engine.location.Velocity;
import me.engine.skill.Skill;
import me.engine.world.Chunk;
import me.engine.entity.Entity;
import me.engine.main.Controls;
import me.engine.main.MainClass;

public class EntityLiving extends Entity {
	Velocity line1;
	Velocity line2;
	Location p1;
	boolean hasseenplayer;
	public boolean seesplayer;
	protected int emoticon;
	Location loc2;
	Velocity velocity;
	int coints;
	int health;
	MainClass main;
	Skill[] skills;
	public EntityLiving(MainClass m,float x, float y, float sx, float sy) {
		super(x, y, sx, sy);
		main=m;
		hasseenplayer = false;
		seesplayer = false;
		line1 = new Velocity(1, 1);
		line2 = new Velocity(1, -1);
		p1 = new Location(0, 0);
		loc2 = new Location(0, 0);
		emoticon = 0;
		velocity = new Velocity(0, 0);
		coints = 0;
		health = getStartHealth();
		skills=new Skill[2];
	}
	public void setSkill(int id,Skill s){skills[id]=s;}
	public Skill getSkill(int id){return skills[id];}
	
	protected EntityLiving(){/*USED FOR DUMMY*/}
	
	public Entity createByString(MainClass m,String s,String split){
		Entity e = null;
		System.out.println("Initing "+getName());
		try{
		int x = Integer.parseInt(s.split(split)[0]);
		int z = Integer.parseInt(s.split(split)[1]);
		float sx = Float.parseFloat(s.split(split)[2]);
		float sz = Float.parseFloat(s.split(split)[3]);
		e=new EntityLiving(m,x,z,sx,sz);
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
		s=s+split;
		s=s+this.getSizeX();
		s=s+split;
		s=s+this.getSizeZ();
		s=s+"\n";
		return s;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int h) {
		health = h;
	}

	public boolean isDead() {	
		return health < 1;
	}

	public int getStartHealth() {
		return 10;
	}

	public int getCoints() {
		return coints;
	}

	public void setCoints(int c) {
		coints = c;
	}


	public Velocity getV() {
		return velocity;
	}

	public Location getLocation2() {
		return loc2;
	}

	public int getEmoticon() {
		return emoticon;
	}

	public void setEmoticon(int e) {
		emoticon = e;
	}

	public Velocity getLine1() {
		return line1;
	}

	public Velocity getLine2() {
		return line2;
	}

	protected float seedistance = 10f;

	public Location point1() {
		return p1;
	}
/*
	public boolean inSight(MainClass mainclass, Location loc) {
		loc = loc.clone();
		loc.setX(loc.getX() + 0.5f);
		loc.setY(loc.getY() + 0.5f);
		float x = getLocation().getX() + 0.5f;
		float y = getLocation().getY() + 0.5f;
		if (Location.getDistance(new Location(x, y), loc) > seedistance) {
			seesplayer = false;
			if(mainclass.getWorld().getDanger()>=25 && this instanceof EntityVillager){
				((EntityVillager)this).type=5;
			}
			return false;
		}
		if (y + line2.getY() * ((loc.getX() - x) * line2.getX()) > loc.getY()) {
			seesplayer = false;
			if(mainclass.getWorld().getDanger()>=25 && this instanceof EntityVillager){
				((EntityVillager)this).type=5;
			}
			return false;
		}
		if (y + line1.getY() * ((loc.getX() - x) * line1.getX()) < loc.getY()) {
			seesplayer = false;
			if(mainclass.getWorld().getDanger()>=25 && this instanceof EntityVillager){
				((EntityVillager)this).type=5;
			}
			return false;
		}
		velocity = new Velocity(loc.x - x, loc.y - y);
		float oldxc = (float) Math.sqrt(velocity.getX() * velocity.getX());
		velocity = Velocity.getNorm(velocity);
		for(Chunk c:mainclass.getWorld().getChunkArray()){
			if(Location.getDistance(new Location(c.getChunkX(),c.getChunkY()),
					new Location(getX()/10,getY()/10))<2f)
			for(Block b:c.getBlockArray()){
			if (b != null
					&& b.getID() != 0
					&& Location
							.getDistance(new Location(x, y), b.getLocation()) <= seedistance) {
				for (float i = 0; i < seedistance; i++) {
					float bx = (float) (x + velocity.x * (i));
					float by = (float) (y + velocity.y * (i));
					loc2 = new Location(bx, by);
					if (Location.getDistance(new Location(x, y),
							b.getLocation()) > 2f)
						if (mainclass
								.getWorld()
								.getBlockHandler()
								.getBlock(b.getID() + "")
								.collision(b.getLocation(), bx, by, bx + 1f,
										by + 1f)) {
							if (Location.getDistance(new Location(bx, by),
									b.getLocation()) > 2f)
								continue;
							if (velocity.x > 0
									&& Location.getDistance(new Location(x, y),
											new Location(loc.x, loc.y)) < Location
											.getDistance(new Location(x, y),
													new Location(bx, by)))
								continue;
							if (velocity.x < 0
									&& Location.getDistance(new Location(x, y),
											new Location(loc.x, loc.y)) < Location
											.getDistance(new Location(x, y),
													new Location(bx, by)))
								continue;
							seesplayer = false;
							if(mainclass.getWorld().getDanger()>=25 && this instanceof EntityVillager){
								((EntityVillager)this).type=5;
							}
							return false;
						}
				}
			}
		}
		}
		hasseenplayer = true;
		seesplayer = true;
		mainclass.getWorld().setHiden(false);
		seetick++;
		if(mainclass.getWorld().getDanger()>=25 && this.getMainItem()!=null && seetick>=20)
		{
			seetick=0;
			mainclass.getWorld().getScriptLoader().putVar("entityx", getX());
			mainclass.getWorld().getScriptLoader().putVar("entityy", getY());
			mainclass.getWorld().getScriptLoader().putVar("entityaimx", this.getV().getX());
			mainclass.getWorld().getScriptLoader().putVar("entityaimy", this.getV().getY());
			mainclass.getWorld().getScriptLoader().startScript("item_"+this.getMainItem().getID()+"_use_villager");
			if(this instanceof EntityVillager){
				((EntityVillager)this).type=-1;
			}
		}
		return true;
	}*/
	int seetick=0;

	public boolean hasSeenPlayer() {
		return hasseenplayer;
	}

	// public Velocity getV(){
	// return v;
	// }
	public void move(MainClass mainclass) {
	}

	public Velocity getMoveDirection(){
		return new Velocity(0,0);
	}
	
	protected int side=0;
	public boolean moving=false;
	public float getSpeed(){return 0.025f;}

	
	
	public void setSightVelocity(Velocity v){
		velocity=v;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int b) {
		side = b;
	}
	
	   int allreadystepped;
	   int goalID=-1;
	   protected int lastID=-1;
	 
	   
	   int itemID = 0;
	   public int getItemID(){return itemID;}
	   public void setItemID(int i){itemID=i;}
	

	   Location nextlocation=null;

	   Location nextloc;
	   Random random = new Random();
		int times = 0;

		public Location getNextLocation() {
			if(nextlocation==null)this.recalcNextLocation(true);
			return nextlocation;
		}

	public void render(MainClass m) {
		super.render(m);
	}

	public String getRenderID(){
		if(getAnimation() != null)
			return getAnimation().getCur(side);
		return "null";
	}
	
	HashMap<Integer,Integer> status = new HashMap<Integer,Integer>();

	public void addStatus(String effect,int time,boolean stack) {
		try{
		int effectindex=Status.valueOf(Status.class, effect).ordinal();
	int currently=0;
		if(status.containsKey(effectindex)){
			currently=status.get(effectindex);
		}
		if(stack)
		status.put(effectindex, currently+time);
		else if(!stack && currently == 0)
			status.put(effectindex, currently+time);
		}catch(Exception e){
			System.out.println("Effect does not exist: "+effect);
		}
	}
	
	public void removeEffect(String effect) {
		try{
		int effectindex=Status.valueOf(Status.class, effect).ordinal();
		if(status.containsKey(effectindex)){
			status.remove(effectindex);
		}
		}catch(Exception e){
			System.out.println("Effect does not exist: "+effect);
		}
	}
	
	public int hasEffect(String effect){
		int effectindex=Status.valueOf(Status.class, effect).ordinal();
		int currently=0;
		if(status.containsKey(effectindex)){
		currently=status.get(effectindex);
		}else return -1;
			return currently;
			
	}
	
	public void tickStatus(){
		Object[] array = status.keySet().toArray().clone();
		if(array.length>0)
	for(int i=0;i<array.length;i++){
			if(!status.containsKey(array[i]))continue;
			int value = status.get(array[i])-1;
			int effect = (Integer) array[i];
			status.put(effect,value);
			if(value<0)status.remove(effect);
		}
	}
	//if i is positive then b == overheal if i is negative then b == canKill
	public int damage(int i,boolean b) {
		int start = getHealth();
		if(getHealth()<=0)return 0;
		if(i>0 && this.hasEffect("DMG")>=0)return start;
		if(i>0 && b){
			if(getHealth()-i>0)setHealth(this.getHealth()-i);
			else setHealth(0);
		}else if(i>0 && !b){
			if(getHealth()-i>0)setHealth(this.getHealth()-i);
			else setHealth(1);
		}else if(i<0 && b)setHealth(this.getHealth()-i);
		else if(i<0 && !b){
			if(getHealth()-i>this.getStartHealth())setHealth(getStartHealth());
			else setHealth(getHealth()-i);
		}
		if(start>getHealth())this.addStatus("DMG",Status.DAMAGE_COOLDOWN,false);
		
		return getHealth();
	}


	public boolean left=false;
	public boolean right=false;
	public boolean up=false;
	public boolean down=false;
	public boolean left(MainClass m) {
		left=true;
		return true;
	}
	
	public boolean right(MainClass m) {
		right=true;
		return true;
	}
	

	public boolean forward(MainClass m) {
		up=true;
		return true;
	}
	
	public boolean backward(MainClass m) {
		down=true;
		return true;
	}
	
	public void tick(MainClass m){
	//TICK Entity SKILLS AND SO ON	
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
	public boolean Moveleft(MainClass m) {
		this.getVelocity().add(new Velocity(-1,0).multiplz(getSpeed()));
		line1=new Velocity(-1,0).multiplz(getSpeed());
		line2=new Velocity(-1,0).multiplz(getSpeed());
		side=3;
		return true;
	}
	
	public boolean Moveright(MainClass m) {
		this.getVelocity().add(new Velocity(1,0).multiplz(getSpeed()));
		line1=new Velocity(1,0).multiplz(getSpeed());
		line2=new Velocity(1,0).multiplz(getSpeed());
		side=2;
		return true;
	}
	

	public boolean Moveforward(MainClass m) {
		this.getVelocity().add(new Velocity(0,1).multiplz(getSpeed()));
		line1=new Velocity(0,1).multiplz(getSpeed());
		line2=new Velocity(0,1).multiplz(getSpeed());
		side=1;
		return true;
	}
	
	public boolean Movebackward(MainClass m) {
		this.getVelocity().add(new Velocity(0,-1).multiplz(getSpeed()));
		line1=new Velocity(0,-1).multiplz(getSpeed());
		line2=new Velocity(0,-1).multiplz(getSpeed());
		side=0;
		return true;
	}
	int timer=0;
	public void recalcNextLocation(boolean b) {
		times++;
		timer++;
		if(timer<=40 && !b)return;
		timer=0;
		if (nextlocation == null || times > 100){
			nextlocation = main.getWorld().getPlayer().getLocation()
					.add(new Location(random.nextFloat(), random.nextFloat()))
					.clone();
			times=0;
		}
		if (Location.getDistance(this.getLocation(), nextlocation) < 0.5f) {
			nextlocation = main.getWorld().getPlayer().getLocation()
					.add(new Location(random.nextFloat(), random.nextFloat()))
					.clone();

		}
	}

	
	
}
