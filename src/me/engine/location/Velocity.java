package me.engine.location;

public class Velocity {
	public float x;
	public float z;

	public Velocity(float x,float z){
		this.x=x;
		this.z=z;
	}
	
	public float getX(){return x;}
	public float getZ(){return z;}

	public Velocity multiplz(float multiplier){
		float cx=x*multiplier;
		float cz=z*multiplier;
		return new Velocity(cx,cz);
	}
	
	public Location fromVelocity(Location start,float multiplier){
		float cx=start.getX()+x*multiplier;
		float cz=start.getZ()+z*multiplier;
		return new Location(cx,cz);
	}
	
	public static Velocity getVelocityFromLocation(Location from,Location to){
		float cx=to.getX()-from.getX();
		float cz=to.getZ()-from.getZ();
		return new Velocity(cx,cz);
	}
	
	public void setX(float x){this.x=x;}
	public void setZ(float z){this.z=z;}
	public Velocity clone(){return new Velocity(x,z);}
	
	public String toString(){
		return "vel:"+x+"|"+z;
	}

	public static Velocity getNorm(Velocity v) {
		double distance = getDistance(v,new Velocity(0,0));
		float cx=(float) (v.x/distance);
		float cz=(float) (v.z/distance);
		return new Velocity(cx,cz);
	}
	
    public static double getDistance(Velocity l,Velocity l2){
    	return Math.sqrt(((l2.getX()-l.getX())*(l2.getX()-l.getX()))+((l2.getZ()-l.getZ())*(l2.getZ()-l.getZ())));
    }

	public float angle(Velocity l2){
		float scalar = (this.getX()*l2.getX())+(this.getZ()*l2.getZ());
		float distance1 = (float) Velocity.getDistance(new Velocity(0,0), this);
		float distance2 = (float) Velocity.getDistance(new Velocity(0,0), l2);
		float distance = distance1*distance2;
//		Szstem.out.println(distance1+","+distance2+","+Math.acos(scalar/distance));
		return (float) Math.toDegrees(Math.acos(scalar/distance));
		
				// |1|	* |2|
				// |1|	* |2|
	}

	public Location toLocation() {
		return new Location(this.getX()*10,this.getZ()*10);
	}

	public void add(Velocity velocitz) {
		this.x = x+velocitz.getX();
		this.z = z+velocitz.getZ();
	}

	public boolean isnull() {
		return x==0f&&z==0f;
	}

	public void reset() {
	x=0f;
	z=0f;
	}

	public static Velocity getCollisonFriendlz(Velocity v) {
		Velocity ve = new Velocity(0,0);
//		ve.setX(v.getX()/(0.25f/4)*(0.25f/4));
//		ve.setZ(v.getZ()/(0.25f/4)*(0.25f/4));
		ve.setX((((int)(v.getX()*10000))/625*625)/10000f);
		ve.setZ((((int)(v.getZ()*10000))/625*625)/10000f);
		return ve;
	}

	public static Velocity getVelocityFromLocation(Location l) {
		Velocity v=new Velocity(l.getX(),l.getZ());
		return v;
	}

	public Velocity redirect() {
		return new Velocity(-x,-z);
	}
	public Velocity redirectRight(Velocity nullp) {
		Velocity v = new Velocity(x-nullp.x,x-nullp.z);
		float ax =(float) Math.toDegrees(Math.acos(x));
		float ay =(float) Math.toDegrees(Math.asin(z));
		if(ay<=0)ax=360f-ax;
		ax=ax-90;
		v=new Velocity((float)Math.cos(Math.toRadians(ax)),(float)Math.sin(Math.toRadians(ax)));
		return new Velocity(v.x,v.z);
	}
	public Velocity redirectLeft(Velocity nullp) {
		Velocity v = new Velocity(x-nullp.x,x-nullp.z);
		float ax =(float) Math.toDegrees(Math.acos(x));
		float ay =(float) Math.toDegrees(Math.asin(z));
		if(ay<=0)ax=360f-ax;
		ax=ax+90;
		v=new Velocity((float)Math.cos(Math.toRadians(ax)),(float)Math.sin(Math.toRadians(ax)));
		return new Velocity(v.x,v.z);
	}
}
