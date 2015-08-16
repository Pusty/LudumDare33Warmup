package me.engine.block;


public class BlockHandler {
	HandledBlock[] blockList;
	public BlockHandler(){
		blockList=new HandledBlock[100];
	}
	
	public void addBlock(String n,HandledBlock b,int id){blockList[id]=b;}
	public HandledBlock getById(int id){return blockList[id];}
	
}
