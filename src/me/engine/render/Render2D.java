package me.engine.render;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glMaterialf;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import me.engine.entity.EntityLiving;
import me.engine.entity.Particle;
import me.engine.entity.Player;
import me.engine.location.Location;
import me.engine.location.Velocity;
import me.engine.text.TextPopup;
import me.engine.util.Sorter;
import me.engine.world.Chunk;
import me.engine.world.World;
import me.engine.entity.Entity;
import me.engine.main.Controls;
import me.engine.main.GameTickHandler;
import me.engine.main.MainClass;
import me.game.main.StartClass;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Render2D {

	public static int[] chunkList;
	int framerate = 120;
	MainClass mainclass;
	Entity[] entitysbehindplayer;
	public Render2D(MainClass m) {
		mainclass = m;

	}

	public void lookPos(Location loc, boolean t) {
		int m = t ? 1 : -1;
		GL11.glTranslatef(loc.getX() * -1 * m, loc.getZ() * -1 * m, 0);
	}

	float camdis = -15f;

	float xmove=0;

	public void render() {
	 mainclass.getSoundPlayer().playSound("bg_long", false);	
		while (mainclass.isRunning()) {
			if (!mainclass.isTimeRunning()) {
				try {
					Thread.sleep(1000 / 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (chunkList == null) {
				chunkList = new int[mainclass.getWorld().getChunkArray().length];
				for (int i = 0; i < mainclass.getWorld().getChunkArray().length; i++)
					chunkList[i] = -1;
			}

			
//			GL11.glDisable(GL11.GL_DEPTH_TEST);
			
			// START RENDERING
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();

			//SETUP CAM
			GL11.glTranslatef(-0.0f, 0.0f, camdis);
			
			

			Location looking = 
					mainclass.getWorld().getPlayer().getLocation().clone();
			Entity[] entityarray = mainclass.getWorld().getEntityArray().clone();
			lookPos(looking, true);
			
			
			//RERENDER
			if (StartClass.rerender == true) {
				mainclass.getPictureLoader().reloadTexture();
				StartClass.rerender = false;
			}
			
			
			
			if (chunkList[0] == -1) {
				int cidx = -1;
				for (Chunk c : mainclass.getWorld().getChunkArray()) {
					cidx++;
					if (c != null && chunkList.length > cidx)
						chunkList[cidx] = createChunkDisplayList(mainclass, c);
				}
				System.out.println("Init Maprender");
			} 
			
			
	
			//Render Map
				for (int i = 0; i < chunkList.length; i++) {
					// DISTANCE IF HERE!!!
					if (mainclass.getWorld().getChunkArray().length <= i)
						continue;
					if (Location.getDistance(new Location(mainclass.getWorld()
							.getChunkArray()[i].getChunkX(), mainclass
							.getWorld().getChunkArray()[i].getChunkZ()),
							new Location(mainclass.getWorld().getPlayer()
									.getX() / 10, mainclass.getWorld()
									.getPlayer().getZ() / 10)) < 3f) {
						glCallList(chunkList[i]);
					}

				}
			
				//Particle Here
				{
					for(int i = 0; i < mainclass.getWorld().getParticleArray().length;i++){
						Particle p = mainclass.getWorld().getParticleArray()[i];
						if(p == null)continue;
						if(GameTickHandler.inRange(mainclass.getWorld().getPlayer().getLocation(), p.getLocation(), 20)){
							p.render(mainclass);
						}
					}
				}
				{
				//SORT HERE
					Object[] objs = Sorter.sortEntitys(entityarray,
									looking.getZ());
					int[] objInt = (int[]) objs[0];
				for(int i=objInt.length-1;i>=0;i--){
					if(objInt[i] == -5){
						lookPos(looking,false);
						mainclass.getWorld().getPlayer().renderShadow(mainclass);
						lookPos(looking,true);
					}else{
							entityarray[objInt[i]].renderShadow(mainclass);
					}
				}
				for(int i=objInt.length-1;i>=0;i--){
					if(objInt[i] == -5){
						lookPos(looking,false);
						mainclass.getWorld().getPlayer().render(mainclass);
						lookPos(looking,true);
					}else{
							entityarray[objInt[i]].render(mainclass);
					}
				}
			}
			
			 GL11.glLoadIdentity();
			 GL11.glTranslatef(-0.0f, 0.0f, -25f);
			 GL11.glDisable(GL11.GL_LIGHTING);
			 {
			 for(TextPopup tp:mainclass.getTextPopupArray()){
			 if(tp==null)continue;
			 tp.render(mainclass);
			 }
			 }

			
			  double curtime = System.currentTimeMillis(); int oneanimationrot
			  = 1000; if(curtime-lastTime >= oneanimationrot)lastTime=curtime;
			  // animation(1,(int)((curtime-lastTime)/oneanimationrot*16),5f);
			  
			  if(mainclass.getDialogFrom()!=null&&mainclass.getDialog()!=null){
			  int length =
			  displayDialog(mainclass.getDialogFrom(),mainclass.getDialog
			  (),mainclass.getDialogCur()); mainclass.setTimeRunning(false);
			  if(mainclass.getDialogCur()>length-1) { mainclass.setDialog(null,
			  null); mainclass.setTimeRunning(true); }
			  
			  
			  
			  }
			
			Display.update();
			Display.sync(60);

			/*
			 * for(int i=0;i<Controls.entityLocations.length-1;i++){ Location
			 * loc = Controls.entityLocations[i]; Location loc2 =
			 * Controls.entityLocations[i+1]; if(loc==null||loc2==null)continue;
			 * drawLine(loc,loc2); }
			 */

			// GL11.glTranslatef(-0.0f, 0.0f, camdis);
			// looking = mainclass.getWorld().getPlayer().getLocation().clone();
			// lookPos(looking,true);

			// GL11.glPopMatrix();
			// GL11.glLoadIdentity();
			// GL11.glTranslatef(-0.0f, 0.0f, -25f);
			// GL11.glDisable(GL11.GL_LIGHTING);
			// {
			// for(TextPopup tp:mainclass.getTextPopupArray()){
			// if(tp==null)continue;
			// tp.render(mainclass);
			// }
			// }

			// SHADER TEST

			/*
			 * double curtime = System.currentTimeMillis(); int oneanimationrot
			 * = 1000; if(curtime-lastTime >= oneanimationrot)lastTime=curtime;
			 * // animation(1,(int)((curtime-lastTime)/oneanimationrot*16),5f);
			 * 
			 * if(mainclass.getDialogFrom()!=null&&mainclass.getDialog()!=null){
			 * int length =
			 * displayDialog(mainclass.getDialogFrom(),mainclass.getDialog
			 * (),mainclass.getDialogCur()); mainclass.setTimeRunning(false);
			 * if(mainclass.getDialogCur()>length-1) { mainclass.setDialog(null,
			 * null); mainclass.setTimeRunning(true); }
			 * 
			 * 
			 * 
			 * }
			 */

		}
		for (int index = 0; index < Render2D.chunkList.length; index++)
			GL11.glDeleteLists(Render2D.chunkList[index], 1);
		mainclass.getSoundPlayer().removeALData();
		Display.destroy();
		System.exit(0);
	}
	

	public static double lastTime = 0;

	private int displayDialog(String from, String text, int cur) {
		{
			String imageName = "player2_1";
			if (from.equalsIgnoreCase("player3")) {
				imageName = "player3_1";
			}

			GL11.glPushMatrix();
			GL11.glTranslatef(-16f, -10.5f, 0f);
			glBindTexture(GL_TEXTURE_2D, mainclass.getPictureLoader()
					.getImageAsInteger("item_" + 0));
			glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex2f(0, 6);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(32f, 6f);
			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex2f(32f, 0f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(0f, 0f);
			GL11.glEnd();
			GL11.glTranslatef(3, +1f, 0f);
			glBindTexture(GL_TEXTURE_2D, mainclass.getPictureLoader()
					.getImageAsInteger(imageName));
			glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex2f(0, 4);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(4f, 4f);
			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex2f(4f, 0f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(0f, 0f);
			GL11.glEnd();

			GL11.glTranslatef(0f, -0.5f, 0f);
			GL11.glScalef(0.5f, 0.5f, 0f);
			renderString(mainclass, from);
			GL11.glScalef(2f, 2f, 0f);
			GL11.glTranslatef(-0f, 0.5f, 0f);

			List<String> textstring = new ArrayList<String>();
			String[] parts = text.split(" ");
			String curtext = "";
			int curindex = 0;
			for (String p : parts) {
				curindex = curindex + p.length();
				if (curindex < 12) {
					curtext = curtext + p + " ";
				} else {
					curindex = 0;
					textstring.add(curtext);
					curtext = p + " ";
				}
			}
			textstring.add(curtext);

			GL11.glTranslatef(5f, 3.5f, 0f);
			int tind = cur;
			if (textstring.size() >= tind + 1)
				renderString(mainclass, textstring.get(tind + 0));
			GL11.glTranslatef(0f, -1.5f, 0f);
			if (textstring.size() >= tind + 2)
				renderString(mainclass, textstring.get(tind + 1));
			GL11.glTranslatef(0f, -1.5f, 0f);
			if (textstring.size() >= tind + 3)
				renderString(mainclass, textstring.get(tind + 2));
			GL11.glTranslatef(0f, -1.5f, 0f);
			if (textstring.size() >= tind + 4)
				renderString(mainclass, "/ / / / / / / / / / /");
			GL11.glPopMatrix();
			return textstring.size();
		}
	}

	public void animation(int id, int ind, float m) {
		String name = "anim_" + id + "_" + ind;
		int picx = mainclass.getPictureLoader()
				.getImage("anim_" + id + "_" + 0).getWidth(null);
		int picy = mainclass.getPictureLoader()
				.getImage("anim_" + id + "_" + 0).getHeight(null);
		float xratio = (float) picx / (float) picy;
		float yratio = (float) picy / (float) picx;
		xratio = xratio * m;
		yratio = yratio * m;
		if (xratio > yratio)
			yratio = m;
		else
			xratio = m;
		glBindTexture(GL_TEXTURE_2D, mainclass.getPictureLoader()
				.getImageAsInteger(name));
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(0f, yratio);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(xratio, yratio);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(xratio, 0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(0f, 0f);
		GL11.glEnd();
	}

	public static int createChunkDisplayList(MainClass m, Chunk c) {
		int displayList = glGenLists(1);
		return updateChunkDisplayList(m, c, displayList);
	}

	public static int updateChunkDisplayList(MainClass m, Chunk c, int list) {
		glNewList(list, GL_COMPILE);
		{
			int index = -1;
			int blockID = 0;
			Location blockLocation;
			for (int bx = 0; bx < c.getSizeX(); bx++)
				for (int bz = 0; bz < c.getSizeZ(); bz++) {
					index++;
					blockID = c.getBlockID(bx, bz);
					blockLocation = new Location(c.getChunkX() * c.getSizeX()
							+ bx, c.getChunkZ() * c.getSizeZ() + bz);
					m.getBlockHandler()
							.getById(blockID)
							.render(m,
									(int) blockLocation.getX(),
									(int) blockLocation.getZ(),
									blockID,0);
				}
		}
		glEndList();
		return list;
	}

	private void drawLine(Location l, Location l2) {
		GL11.glBindTexture(GL_TEXTURE_2D, 0);
		GL11.glColor3f(1f, 0f, 0f);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f(l.x, l.z);
		GL11.glVertex2f(l2.x, l2.z);
		GL11.glEnd();
		GL11.glColor3f(1f, 1f, 1f);
	}

	private void drawLine(Location l, Location l2, boolean b) {
		GL11.glBindTexture(GL_TEXTURE_2D, 0);
		if (b == false)
			GL11.glColor3f(0f, 0f, 1f);
		else
			GL11.glColor3f(0f, 1f, 0f);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f(l.x, l.z);
		GL11.glVertex2f(l2.x, l2.z);
		GL11.glEnd();
		GL11.glColor3f(1f, 1f, 1f);
	}

	public void renderChunk(MainClass m, Chunk c, int chunkX, int chunkZ, int i) {

	}

	public void renderImage(String img) {
		glBindTexture(GL_TEXTURE_2D, mainclass.getPictureLoader()
				.getImageAsInteger(img));
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(0f, 1f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(1f, 1f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(1f, 0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(0f, 0f);
		GL11.glEnd();
	}

	public static void renderString(MainClass m, String s) {
		for (int xo = 0; xo < s.toUpperCase().length(); xo++) {
			GL11.glTranslatef(xo, 0, 0);
			renderChar(m, 0, 0, s.toUpperCase().toCharArray()[xo]);
			GL11.glTranslatef(-xo, 0, 0);
		}
	}

	public static void renderChar(MainClass m, float x, float y, char c) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,
				m.getPictureLoader().getImageAsInteger("char_" + c));
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(x + 0f, y + 1f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(x + 1f, y + 1f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(x + 1f, y + 0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(x + 0f, y + 0f);
		GL11.glEnd();

	}

	public static ByteBuffer[] getIcons(String path) {
		try {
			Image image = ImageIO.read(new File(path));
			ByteBuffer[] buffer = new ByteBuffer[3];
			buffer[0] = forSizeBuffer(image, 16);
			buffer[1] = forSizeBuffer(image, 32);
			buffer[2] = forSizeBuffer(image, 128);
			return buffer;
		} catch (Exception e) {
			return null;
		}
	}

	public static ByteBuffer forSizeBuffer(Image img, int size) {
		BufferedImage bimg = PictureLoader.resizeIcon(img, size, size);
		byte[] bytebuffer = new byte[bimg.getWidth() * bimg.getHeight() * 4];
		int c = 0;
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++) {
				int rgb = bimg.getRGB(x, y);
				bytebuffer[c + 0] = (byte) (((rgb << 8) >> 24) & 0xFF);
				bytebuffer[c + 1] = (byte) (((rgb << 16) >> 24) & 0xFF);
				bytebuffer[c + 2] = (byte) (((rgb << 24) >> 24) & 0xFF);
				bytebuffer[c + 3] = (byte) ((rgb >> 24) & 0xFF);
				c = c + 4;
			}
		return ByteBuffer.wrap(bytebuffer);
	}

	public void startRender() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				initDisplay(false);
				initGL();
				mainclass.getSoundPlayer().initSoundPlayer();
				mainclass.getPictureLoader().loadTexture();
				render();
			}
		}).start();
	}

	private void initDisplay(boolean fullscreen) {

		DisplayMode chosenMode = null;

		try {
			DisplayMode[] modes = Display.getAvailableDisplayModes();

			for (int i = 0; i < modes.length; i++) {
				if ((modes[i].getWidth() == mainclass.getWidth())
						&& (modes[i].getHeight() == mainclass.getHeight())) {
					chosenMode = modes[i];
					break;
				}
			}
		} catch (LWJGLException e) {
			Sys.alert("Error", "Unable to determine display modes.");
			System.exit(0);
		}

		if (chosenMode == null) {
			Sys.alert("Error", "Unable to find appropriate display mode.");
			System.exit(0);
		}

		try {
			Display.setDisplayMode(chosenMode);
//			 Display.setVSyncEnabled(true);
			Display.setFullscreen(fullscreen); // FULLSCREEn
			Display.setTitle("LudumDare "+StartClass.version);

			Display.setIcon(getIcons(System.getProperty("user.dir")
					+ "\\img\\icon32.png"));

			Display.create();

		} catch (LWJGLException e) {
			Sys.alert("Error", "Unable to create display.");
			System.exit(0);
		}

	}

	private void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GLU.gluPerspective(45.0f, ((float) mainclass.getWidth())
				/ ((float) mainclass.getHeight()), 0.1f, 100.0f);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		GL11.glEnable(GL11.GL_TEXTURE_2D);

	    GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	    GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glClearDepth(1.0f);
		
//		GL11.glDisable(GL11.GL_DEPTH_TEST); //DEPTH TEST
		
	    GL11.glClearStencil(0);
	    GL11.glDisable(GL11.GL_DITHER);

	   
	    GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
	    
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);

		return;
	}

	public static void setLight(int light, Location lightpos, float h,
			float angle) {
		if (!GL11.glIsEnabled(light))
			GL11.glEnable(light);
		GL11.glLight(
				light,
				GL11.GL_POSITION,
				BufferTools.asFlippedFloatBuffer(new float[] { lightpos.x,
						lightpos.z, h, angle }));
	}

	public static void day() {
		setLight(GL11.GL_LIGHT0, new Location(0.5f, 0.5f), 25, 0);
	}

	public static void mid() {
		setLight(GL11.GL_LIGHT0, new Location(0.5f, 0.5f), 25, 0);
	}

	public static void night() {
		setLight(GL11.GL_LIGHT0, new Location(0.5f, 0.5f), 5, 1f);
	}

}
