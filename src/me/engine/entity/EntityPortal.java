package me.engine.entity;


import me.engine.main.MainClass;


public class EntityPortal extends Entity{
	MainClass main;
	int portal=0;
	boolean from=false;
	boolean visable=true;
	public EntityPortal(MainClass m,float x, float y,boolean f,int pi,boolean vi) {
		super(x, y, 1f,1f);
		main=m;
		from=f;
		portal=pi;
		visable=vi;
	}
	
	protected EntityPortal(){/*USED FOR DUMMY*/}
	
	public Entity createByString(MainClass m,String s,String split){
		Entity e = null;
		System.out.println("Initing "+getName());
		try{
		int x = Integer.parseInt(s.split(split)[0]);
		int z = Integer.parseInt(s.split(split)[1]);
		boolean f = Boolean.parseBoolean(s.split(split)[2]);
		int pi = Integer.parseInt(s.split(split)[3]);
		boolean vi = Boolean.parseBoolean(s.split(split)[4]);
		e=new EntityPortal(m,x,z,f,pi,vi);
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
		s=s+this.from;
		s=s+split;
		s=s+this.portal;
		s=s+split;
		s=s+this.visable;
		s=s+"\n";
		return s;
	}

	public boolean isFrom(){return from;}
	public int getPortal(){return portal;}
	public boolean isVisible(){return visable;}

	public String getTextureName(int i){
		return "portal";
	}
	
	
	public void render(MainClass m){
		if(from)return;
		super.render(m);
}

	

	public void renderShadow(MainClass m){
		if(from)return;
		super.renderShadow(m);
	}
}
