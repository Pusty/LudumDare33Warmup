package me.engine.world;

import me.engine.entity.EntityPortal;
import me.engine.entity.EntitySlime;
import me.engine.entity.EntityTree;
import me.engine.main.MainClass;

public class LevelScript {
	public static LevelScript[] scripts = new LevelScript[64];
	public static void addLevel(int in,LevelScript ls){scripts[in]=ls;}
	public static LevelScript getLevel(int in){if(in>=scripts.length || in<0 || scripts[in]==null)return new LevelScript(in);return scripts[in];}
	public static LevelScript[] getScripts(){return scripts;}
	int mapID;
	public LevelScript(int map){
		mapID=map;
	}
	public void addTree(MainClass m ,World w, int metaID, int x, int z) {
		w.addEntity(new EntityTree(m,x,z));
	}
	public void addNPC(MainClass m ,World w, int metaID, int x, int z) {

	}
	public void addEnemy(MainClass m ,World w, int metaID, int x, int z) {
		w.addEntity(new EntitySlime(m,x,z));
	}
	public void addSpecial1(MainClass m ,World w, int metaID, int x, int z) {

	}
	public void addSpecial2(MainClass m ,World w, int metaID, int x, int z) {

	}
	public void addPortal(MainClass m ,World w, int metaID, int x, int z) {
		w.addEntity(new EntityPortal(m,x,z,metaID-7==mapID,metaID-7,true));
		
	}
	
	
}
