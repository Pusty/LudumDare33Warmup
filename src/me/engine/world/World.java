package me.engine.world;


import me.engine.entity.Entity;
import me.engine.entity.Particle;
import me.engine.entity.Player;
import me.engine.location.Location;
import me.engine.main.MainClass;

public class World {
	int sizex;
	int sizez;
	int sizee;
	Entity[] entityarray;
	Particle[] particlearray;
	Chunk[] chunkarray;
	Player player;
	MainClass mainclass;
	boolean editor;
	boolean day;
	public World(MainClass m,int sx, int sz, int se) {
		editor=false;
		mainclass=m;
		sizex = sx;
		sizez = sz;
		sizee = se;
		day=true;
		chunkarray = new Chunk[(sx/10) * (sz/10)];
		int c=0;
		for(int cx=0;cx<sx/10;cx++)
			for(int cz=0;cz<sz/10;cz++){
			chunkarray[c]=new Chunk(cx,cz,10,10);c++;}
		entityarray  = new Entity[se];
		particlearray = new Particle[se];
//		entityarray[0] = new NPCEntity(m,0,0,"player3");
	}

	public boolean isDay(){return day;}
	public void setDay(boolean b){day=b;}
	public boolean isEditor(){return editor;}

	public Entity getEntityByIndex(int index){
		for(Entity e:this.entityarray){
			if(e==null)continue;if(e.getIndex()==index)return e;}return null;
	}
	
	
	public int addEntity(Entity e){
		for(int i=0;i<entityarray.length;i++){
			if(entityarray[i]==null){
				entityarray[i] = e;return i;}
		}return -1;
	}
	
	public void removeEntity(Entity e){
		for(int i=0;i<entityarray.length;i++){
			if(entityarray[i]==e){
				entityarray[i] = null;return;}
		}
	}
	
	public Particle[] getParticleArray(){ return particlearray;}
	
	public int addParticle(Particle e){
		for(int i=0;i<particlearray.length;i++){
			if(particlearray[i]==null){
				particlearray[i] = e;return i;}
		}return -1;
	}
	
	public void removeParticle(Particle e){
		for(int i=0;i<particlearray.length;i++){
			if(particlearray[i]==e){
				particlearray[i] = null;return;}
		}
	}
	
	public Entity[] getEntityArray(){
		return entityarray;
	}
	/*
	public void addProjectile(Projectile e){
		for(int i=0;i<projectilearray.length;i++){
			if(projectilearray[i]==null){
				projectilearray[i] = e;return;}
		}
	}
	
	public void removeProjectile(Projectile e){
		for(int i=0;i<projectilearray.length;i++){
			if(projectilearray[i]==e){
				projectilearray[i] = null;return;}
		}
	}
	
	public Projectile[] getProjectileArray(){
		return projectilearray;
	}
	
	*/
	public Player getPlayer(){
		return player;
	}
	
	public void setPlayer(Player p){
		player=p;
	}

	public int getBlockID(int x, int z) {
		int wx = x/10;
		int wz = z/10;
		if(wx*sizez/10+wz>=sizex/10*sizez/10)return 0;
		if(x<0 || z<0)return 0;
		return chunkarray[((wx*(sizez/10))+wz)].getBlockID(x-(10*wx),  z-(10*wz));
	}

	public void setBlockID(int x, int z,int id) {
		int wx = x/10;
		int wz = z/10;
		if(wx*sizez/10+wz>=sizex/10*sizez/10)return;
		if(this.equals(mainclass.getWorld()))
		if(me.engine.render.Render2D.chunkList!=null)
		me.engine.render.Render2D.updateChunkDisplayList(mainclass,chunkarray[((wx*(sizez/10))+wz)],me.engine.render.Render2D.chunkList[((wx*(sizez/10))+wz)]);
		chunkarray[((wx*(sizez/10))+wz)].setBlockID(x-(10*wx),  z-(10*wz),id);
	}
	
	public int getBlockMeta(int x, int z) {
		int wx = x/10;
		int wz = z/10;
		if(wx*sizez/10+wz>=sizex/10*sizez/10)return 0;
		return chunkarray[((wx*(sizez/10))+wz)].getBlockMeta(x-(10*wx),  z-(10*wz));
	}

	public void setBlockMeta(int x, int z,int meta) {
		int wx = x/10;
		int wz = z/10;
		if(wx*sizez/10+wz>=sizex/10*sizez/10)return;
		chunkarray[((wx*(sizez/10))+wz)].setBlockMeta(x-(10*wx),  z-(10*wz),meta);
	}
	
	public int getBlockLight(int x, int z) {
		int wx = x/10;
		int wz = z/10;
		if(wx*sizez/10+wz>=sizex/10*sizez/10)return 0;
		return chunkarray[((wx*(sizez/10))+wz)].getBlockLight(x-(10*wx),  z-(10*wz));
	}

	public void setBlockLight(int x, int z,int meta) {
		int wx = x/10;
		int wz = z/10;
		if(wx*sizez/10+wz>=sizex/10*sizez/10)return;
		if(this.equals(mainclass.getWorld()))
		if(me.engine.render.Render2D.chunkList!=null && ((wx*(sizez/10))+wz) > 0)
		me.engine.render.Render2D.updateChunkDisplayList(mainclass,chunkarray[((wx*(sizez/10))+wz)],me.engine.render.Render2D.chunkList[((wx*(sizez/10))+wz)]);
		chunkarray[((wx*(sizez/10))+wz)].setBlockLight(x-(10*wx),  z-(10*wz),meta);
	}
	public int getSizeX() {
		return sizex;
	}

	public int getSizeZ() {
		return sizez;
	}
	
	public Chunk[] getChunkArray(){
		return chunkarray;
	}
	public int getEntityArraySize() {
	int i=0;
	for(Entity e:getEntityArray())
		if(e!=null)i++;
	return i;
	}
	
	
	public void calcLight(){
		int[] x = new int[sizex*sizez];
		int[] z = new int[sizex*sizez];
		int curindex=0;
		for(int sx=0;sx<getSizeX();sx++)
			for(int sz=0;sz<getSizeZ();sz++){
				if(getBlockMeta(sx,sz)==2){
				x[curindex]=sx;
				z[curindex]=sz;
				curindex++;
				}
			}
		int distance = 5;
		for(int index=0;index<x.length;index++){
			int cx = x[index];
			int cz = z[index];
			if(cx<1f)continue;
			for(int sx=-distance;sx<=distance;sx++)
			for(int sz=-distance;sz<=distance;sz++){
				boolean br=false;
				for(int i=0;i<distance;i++){
					if(Location.getDistance(new Location(cx,cz),new Location(cx+sx*i,cz+sz*i)) > distance)continue;
					if(getBlockLight(cx+sx*i,cz+sz*i)==-1)continue;
					if(getBlockMeta(cx+sx*i,cz+sz*i)==1 || br){setBlockLight(cx+sx*i,cz+sz*i,-1);br=true;}else
					setBlockLight(cx+sx*i,cz+sz*i,(int)(distance- Location.getDistance(new Location(cx,cz),new Location(cx+sx*i,cz+sz*i))));
				}
			}
		}
	}

	public boolean hasEntity(Entity en) {
		for(int i=0;i<entityarray.length;i++){
			if(entityarray[i]==en){
				return true;}
		}
		return false;
	}
	
}
