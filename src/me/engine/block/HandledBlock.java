package me.engine.block;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import org.lwjgl.opengl.GL11;

import me.engine.entity.Entity;
import me.engine.main.MainClass;
import me.engine.world.World;
import me.game.main.StartClass;

public class HandledBlock {
	int id;
	boolean event;
	boolean collisiontick;
	String multiblockname;

	public HandledBlock(int id) {
		this.id = id;
		event = false;
		collisiontick = false;
		multiblockname = null;
	}


	public boolean getCollisionTick() {
		return collisiontick;
	}

	public String getMutliBlockName() {
		return multiblockname;
	}

	public boolean getEvent() {
		return event;
	}

	public void setEventBlock(boolean b) {
		event = b;
	}

	public void setMultiBlockName(String m) {
		multiblockname = m;
	}

	int renderID = -1;


	public void render(MainClass mainclass, int x,int z,int id,int meta) {
		if (id == 0)
			return;
		if (renderID == -1) {
				renderID = mainclass.getPictureLoader().getImageAsInteger(
						"block_" + this.id);
		}
		if(StartClass.light){
		int light = mainclass.getWorld().getBlockLight(x, z);
		GL11.glColor3f(0.1f*light+0.2f, 0.1f*light+0.2f, 0.1f*light+0.2f);
		}
		glBindTexture(GL_TEXTURE_2D, renderID);
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(x + 0f, z + 1f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(x + 1f, z + 1f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(x + 1f, z + 0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(x + 0f, z + 0f);
		GL11.glNormal3f(0, 0, 1f);
		glEnd();

	}


	public boolean canBreak(int x,int z,int id,int meta) {
		return false;
	}

	public void onInteract(MainClass m, World w, int x,int z,int id,int meta, Entity e) {

	}


//	public void tick(MainClass m, World w, Location b) {
//		m.getWorld().getScriptLoader().putVar("ablockx", b.getX());
//		m.getWorld().getScriptLoader().putVar("ablocky", b.getY());
//		m.getWorld().getScriptLoader()
//				.startScript("block_" + this.id + "_tick");
//	}

	public void setCollisionTick(boolean b) {
		collisiontick = b;
	}

	public void setRenderID(int i) {
		renderID = i;
	}

}
