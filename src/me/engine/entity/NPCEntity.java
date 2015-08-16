package me.engine.entity;

import java.util.Random;

import me.engine.location.Location;
import me.engine.main.MainClass;



public class NPCEntity extends EntityLiving {
	private String playerName;
	public NPCEntity(MainClass m,int x, int y, String name) {
		super(m ,x, y,1f,1f);
		playerName = "player3";
//		this.side=3;
//		this.setMainItem(new Item(30));
//		this.setUtilItem(new Item(31));
	}
	public String getPlayerName(){
		return playerName;
	}
	public String getShownName(){
		return playerName;
	}
	
	public String getDialogText(Random r){
		if(Location.getDistance(this.getLocation(), new Location(0,0)) < 5f)
			return "I am getting near the spawn!";
		int random = r.nextInt(3);
		if(random==0)
			return "I like cheese!";
		if(random==1)
			return "I would love to see a magic cheese at some point";
		if(random==2)
			return "Have you seen the cheese dungeon yet?";
		return null;
	}
	
	public Entity createByString(MainClass m,String s,String split){
		Entity e = null;
		System.out.println("Initing "+getName());
		try{
		int x = Integer.parseInt(s.split(split)[0]);
		int z = Integer.parseInt(s.split(split)[1]);
		String name = s.split(split)[2];
		e=new NPCEntity(m,x,z,name);
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
		s=s+playerName;
		s=s+"\n";
		return s;
	}
	
	public float getSpeed(){return 0.1f;}
	protected NPCEntity(){/*USED FOR DUMMY*/}
	public  String getName(){return "NPCEntity";}
	
	public void addRender(){
		if(getAnimation().currentanimation != 0 || moving)
			getAnimation().getCurA().addTick();
	}
	
	public String getTextureName(){
		return this.playerName;
	}
	public void render(MainClass m){
		super.render(m);
}


}
