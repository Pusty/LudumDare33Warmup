package me.engine.location;

public class Location {
	public float x;
	public float z;
	public Location(float x,float z){
		this.x=x;
		this.z=z;
	}
	
	public Location sub(Location l){
		float cx=getX()-l.getX();
		float cz=getZ()-l.getZ();
		return new Location(cx,cz);
	}
	
	public static Location getNorm(Location v) {
		double distance = getDistance(v,new Location(0,0));
		float cx=(float) (v.x/distance);
		float cz=(float) (v.z/distance);
		return new Location(cx,cz);
	}
	
	public static Location getCollisonFriendlz(Location v) {
		Location ve = new Location(0,0);
//		ve.setX(v.getX()/(0.25f/4)*(0.25f/4));
//		ve.setz(v.getZ()/(0.25f/4)*(0.25f/4));
		ve.setX((((int)(v.getX()*10000))/625*625)/10000f);
		ve.setZ((((int)(v.getZ()*10000))/625*625)/10000f);
		return ve;
	}
	
	public float angle(Location l2){
		float scalar = (this.getX()*l2.getX())+(this.getZ()*l2.getZ());
		float distance1 = (float) Location.getDistance(new Location(0,0), this);
		float distance2 = (float) Location.getDistance(new Location(0,0), l2);
		float distance = distance1*distance2;
//		Szstem.out.println(distance1+","+distance2+","+Math.acos(scalar/distance));
		return (float) Math.toDegrees(Math.acos(scalar/distance));
		
				// |1|	* |2|
				// |1|	* |2|
	}
	
	public Location subToDirection(Location l){
		float cx=l.getX()-getX();
		float cz=l.getZ()-getZ();
		return new Location((float)Math.sin(cx)/2,(float)Math.sin(cz)/2);
	}
	
	public Location addVelocity(Velocity v){
		float cx=getX()+v.getX();
		float cz=getZ()+v.getZ();
		return new Location(cx,cz);
	}
	public float getX(){return x;}
	public float getZ(){return z;}
	public void setX(float x){this.x=x;}
	public void setZ(float z){this.z=z;}
	public Location clone(){return new Location(x,z);}
    public static double getDistance(Location l,Location l2){
    	return Math.sqrt(((l2.getX()-l.getX())*(l2.getX()-l.getX()))+((l2.getZ()-l.getZ())*(l2.getZ()-l.getZ())));
    }
    

	public Location add(Location a) {
		float cx = x + a.x;
		float cz = z + a.z;
 
		return new Location(cx,cz);
	}
 

 


	
	public boolean sameAs(Location loc){
		if(this.x==loc.x && this.z==loc.z)return true;
		return false;
	}
	
	
	
	public String toString(){
		return x+"|"+z;
	}

	public Location multiply(Location location) {
		float cx = x*location.x;
		float cz = z*location.z;
		return new Location(cx,cz);
	}

	public void set(Location l) {
		this.setX(l.getX());
		this.setZ(l.getZ());
	}

	public Velocity toVelocity() {
		return new Velocity(getX(),getZ());
	}
	
	public Location redirect() {
		return new Location(-x,-z);
	}
	public Location redirectRight(Location nullp) {
		Location v = new Location(x-nullp.x,x-nullp.z);
		float ax =(float) Math.toDegrees(Math.acos(x));
		float ay =(float) Math.toDegrees(Math.asin(z));
		if(ay<=0)ax=360f-ax;
		ax=ax-90;
		v=new Location((float)Math.cos(Math.toRadians(ax)),(float)Math.sin(Math.toRadians(ax)));
		return new Location(v.x,v.z);
	}
	public Location redirectLeft(Location nullp) {
		Location v = new Location(x-nullp.x,x-nullp.z);
		float ax =(float) Math.toDegrees(Math.acos(x));
		float ay =(float) Math.toDegrees(Math.asin(z));
		if(ay<=0)ax=360f-ax;
		ax=ax+90;
		v=new Location((float)Math.cos(Math.toRadians(ax)),(float)Math.sin(Math.toRadians(ax)));
		return new Location(v.x,v.z);
	}

}
