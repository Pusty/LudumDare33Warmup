package me.engine.main;

public class Cons {
	
	public static String getNameFromID(int id){
		String[] text = new String[100];
		text[0]="air";
		text[1]="water";
		text[2]="grass";
		text[3]="flower";
		text[4]="brick";
		text[5]="ground";
		text[6]="broken";
		text[8]="import";
		text[30]="material";
		text[31]="storagematerial";
		text[32]="build";
		text[33]="placed";
		text[34]="block";
		text[35]="breadmaterial";
		text[37]="breadtable";
		text[38]="breadstorage";
		text[39]="breadmaterialstorage";		
		return text[id];
	}
	
	//BLOCKS
	
	public final static int BLOCK_AIR = 0;
	public final static int BLOCK_WATER = 1;
	public final static int BLOCK_GRASS = 2;
	public final static int BLOCK_FLOWER = 3;
	public final static int BLOCK_BRICK = 4;
	public final static int BLOCK_GROUND = 5;
	public final static int BLOCK_BROKEN = 6;
	public final static int BLOCK_IMPORT = 8;
	public final static int BLOCK_MATERIAL = 30;
	public final static int BLOCK_STORAGE_MATERIAL = 31;
	public final static int BLOCK_BUILD = 32;
	public final static int BLOCK_PLACED = 33;
	public final static int BLOCK_BLOCK = 34;
	public final static int BLOCK_BREADMATERIAL = 35;
	public final static int BLOCK_BREADTABLE = 37;
	public final static int BLOCK_STORAGE_BREAD = 38;
	public final static int BLOCK_STORAGE_BREADMATERIAL = 39;
	public final static int BLOCK_FREEZE = 45;
	public final static int BLOCK_EXPORT = 46;
	
	//ITEMS
	
	public final static int ITEM_AIR = 0;	
	public final static int ITEM_MATERIAL = 30;
	public final static int ITEM_BREAD = 38;
	public final static int ITEM_BREADMATERIAL = 40;
}
