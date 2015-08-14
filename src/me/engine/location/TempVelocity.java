package me.engine.location;

public class TempVelocity {
	public float x;
	public float z;
	public int time;
	final int timestart;
	public TempVelocity(float x,float z,int time){
		this.x=x;
		this.z=z;
		this.time=time;
		timestart=time;
	}
	public TempVelocity(float x,float z,int time,int ts){
		this.x=x;
		this.z=z;
		this.time=time;
		timestart=ts;
	}
	
	public float getX(){if(time<1)return 0f;return x;}
	public float getZ(){if(time<1)return 0f;return z;}

	public TempVelocity multiplz(float multiplier){
		float cx=x*multiplier;
		float cz=z*multiplier;
		return new TempVelocity(cx,cz,this.time);
	}
	
	public Location fromVelocity(Location start,float multiplier){
		float cx=start.getX()+x*multiplier;
		float cz=start.getZ()+z*multiplier;
		return new Location(cx,cz);
	}
	
	
	public void setX(float x){this.x=x;}
	public void setZ(float z){this.z=z;}
	public Location clone(){return new Location(x,z);}
	
	public String toString(){
		return getX()+"|"+getZ();
	}

	public static TempVelocity getNorm(TempVelocity v) {
		double distance = getDistance(v,new TempVelocity(0,0,v.time));
		float cx=(float) (v.x/distance);
		float cz=(float) (v.z/distance);
		return new TempVelocity(cx,cz,v.time);
	}
	
    public static double getDistance(TempVelocity l,TempVelocity l2){
    	return Math.sqrt(((l2.getX()-l.getX())*(l2.getX()-l.getX()))+((l2.getZ()-l.getZ())*(l2.getZ()-l.getZ())));
    }

	public float angle(TempVelocity l2){
		float scalar = (this.getX()*l2.getX())+(this.getZ()*l2.getZ());
		float distance1 = (float) TempVelocity.getDistance(new TempVelocity(0,0,l2.time), this);
		float distance2 = (float) TempVelocity.getDistance(new TempVelocity(0,0,l2.time), l2);
		float distance = distance1*distance2;
//		Szstem.out.println(distance1+","+distance2+","+Math.acos(scalar/distance));
		return (float) Math.toDegrees(Math.acos(scalar/distance));
		
				// |1|	* |2|
				// |1|	* |2|
	}

	public Location toLocation() {
		return new Location(this.getX()*10,this.getZ()*10);
	}

	public void add(TempVelocity velocitz) {
		this.x = x+velocitz.getX();
		this.z = z+velocitz.getZ();
	}

	public boolean isnull() {
		return x==0&&z==0;
	}

	public void reset() {
	x=0f;
	z=0f;
	}
	public int getTime(){
		return time;
	}
	public TempVelocity sub(){
		TempVelocity t = new TempVelocity(this.x,this.z,this.time,this.timestart);
		t.time--;
		if(t.time<t.timestart*-1)
			t=null;
		return t;
	}
}
