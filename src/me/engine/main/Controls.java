package me.engine.main;


import java.util.Random;

import me.engine.entity.Entity;
import me.engine.entity.EntityLiving;
import me.engine.entity.EntityPortal;
import me.engine.entity.NPCEntity;
import me.engine.entity.Player;
import me.engine.location.Location;
import me.game.main.StartClass;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Controls {
	MainClass mainclass;

	public Controls(MainClass m) {
		mainclass = m;
	}
	public static Location[] entityLocations = new Location[25];
	public void startControls() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				while (mainclass.isRunning()) {
					try {
						controls();
						if (Display.isCloseRequested()) {
							close(mainclass);
//							System.exit(0);
						}
					} catch (Exception e) {
						e.printStackTrace();
						mainclass.setRunning(false);
//						System.exit(0);
					}
					try {
						Thread.sleep((int) (1000 / 200 / mainclass
								.getTimeSpeed()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	float mouseSensitivity = 0.15f;
	boolean ispressed = false;
	boolean ispressedPause = false;
	boolean manuelPause = false;
	boolean ispressedChange = false;
	public static boolean ispressedDebug = false;

	boolean mousePressed=false;
	boolean f5pressed=false;
	boolean escPressed=false;

	
	public void controls() {
		
		if (!ispressedDebug && Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			ispressedDebug = true;
			mainclass.toggleDebug();
		} else if (ispressedDebug && !Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			ispressedDebug = false;
		}
		
		if (!f5pressed && Keyboard.isKeyDown(Keyboard.KEY_F5)) {
			f5pressed = true;
			mainclass.getWorld().setDay(!mainclass.getWorld().isDay());
		} else if (f5pressed && !Keyboard.isKeyDown(Keyboard.KEY_F5)) {
			f5pressed = false;
		}

		if (true) {

			int mx = Mouse.getX();
			int mz = Mouse.getY();

			int field = Mouse.getY() / (mainclass.getHeight() / (15));

			field = field - 6;
			if (ispressedPause == false && Keyboard.isKeyDown(Keyboard.KEY_P)) {
				ispressedPause = true;
				mainclass.setTimeRunning(manuelPause);
//				if(manuelPause)
			
				
				manuelPause = !manuelPause;
			}

			if (ispressedPause == true && !Keyboard.isKeyDown(Keyboard.KEY_P)) {
				ispressedPause = false;
				mainclass.setTimeRunning(manuelPause);
				if(manuelPause)
					mainclass.getSoundPlayer().unpauseAll();
				else
					mainclass.getSoundPlayer().pauseAll();

				
			}

			if (cooldown > 0)
				cooldown--;

			
			
	

			float bx = (mx - (mainclass.getWidth() / 2f)) / 29f - 0.5f;
			float bz = (mz - (mainclass.getHeight() / 2f)) / 29f - 0.5f;
	
	

			if(!escPressed && Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) ){
				escPressed=true;
				if(mainclass.getDialog() == null){
				if(mainclass.getGui()==null)StartClass.openInventory(mainclass);
				else StartClass.closeInventory(mainclass);
				}
				
			}else if(escPressed && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){escPressed=false;}
			
			
			if(mainclass.isTimeRunning()){
			mainclass.getWorld().getPlayer()
					.setMouseLocation(new Location(bx, bz));

//			float cmx = mainclass.getWorld().getPlayer().getMouseLocation()
//					.getX();
//			float cmz = mainclass.getWorld().getPlayer().getMouseLocation()
//					.getZ();
			}

			if (!Mouse.isButtonDown(0))
				ispressed = false;

			if (!Keyboard.isKeyDown(Keyboard.KEY_C))
				ispressedChange = false;

			if(Mouse.isButtonDown(1) && mainclass.getDialog() == null && mainclass.getGui() == null){
				Random random = new Random();
				String endtext= null;
				String name=null;
				for(int ei=0;ei<mainclass.getWorld().getEntityArray().length;ei++){
					if(mainclass.getWorld().getEntityArray()[ei]==null)continue;
					Entity e = mainclass.getWorld().getEntityArray()[ei];
					if(e instanceof EntityLiving){
						EntityLiving el = (EntityLiving)e;
						if(el instanceof NPCEntity){
							NPCEntity npc = (NPCEntity)e;
							String text = npc.getDialogText(random);
							if(text==null || Location.getDistance(mainclass.getWorld().getPlayer().getLocation(),npc.getLocation())>2f)continue;
							endtext=text;
							name=npc.getPlayerName();
							break;
						}
					}
				}
				if(endtext == null){
					endtext = mainclass.getWorld().getPlayer().getDialogText(random);
					name = mainclass.getWorld().getPlayer().getTextureName(-1);
				}
				if(endtext != null)
				mainclass.setDialog(name,endtext);
			}
			
			{
				if(mainclass.getGui() != null)
					mainclass.getGui().tick();				
			}
			
			if(Mouse.isButtonDown(0) && !mousePressed){
				if(mainclass.getDialog()!=null &&mainclass.getDialogFrom()!=null){
				mainclass.addDialogCur(1);
				}else if(mainclass.getGui() != null){
					mainclass.getGui().click(mainclass, mx, mz);
				}else if(mainclass.getWorld().getPlayer().getHealth() <= 0){
					restart(mainclass);
				}else
//				mainclass.getSavedData().printOutAll();
				mousePressed=true;
			}else if(!Mouse.isButtonDown(0)){
				mousePressed=false;
			}

			if (mainclass.isTimeRunning() && mainclass.hasMapLoaded()) {

				if (Keyboard.isKeyDown(Keyboard.KEY_1))
					mainclass.setTimeSpeed(1f);
				if (Keyboard.isKeyDown(Keyboard.KEY_2))
					mainclass.setTimeSpeed(0.5f);
				if (Keyboard.isKeyDown(Keyboard.KEY_3))
					mainclass.setTimeSpeed(0.1f);

				if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
					mainclass.getWorld().getPlayer().right(mainclass);
				}else
					mainclass.getWorld().getPlayer().right=false;
				
				
				if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
					mainclass.getWorld().getPlayer().left(mainclass);
				}else
					mainclass.getWorld().getPlayer().left=false;
				
				if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
					mainclass.getWorld().getPlayer().forward(mainclass);
				}else
					mainclass.getWorld().getPlayer().up=false;
				
				if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
					mainclass.getWorld().getPlayer().backward(mainclass);
				}else
					mainclass.getWorld().getPlayer().down=false;
				
				if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
					Location to=new Location(0,0);
					for(Entity e:mainclass.getWorld().getEntityArray()){
						if(e==null || !(e instanceof EntityPortal))continue;
						if(((EntityPortal)e).isFrom() && ((EntityPortal)e).getPortal() == (int)mainclass.getSavedData().getData("worldOld"))to = e.getLocation().clone();
					}
					mainclass.getWorld().getPlayer().getLocation().set(to);
				}
				
		
				if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
					debugAnimation=0;
					mainclass.getSoundPlayer().unpauseAll();
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
					debugAnimation=1;
					mainclass.getSoundPlayer().pauseAll();
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
					debugAnimation=2;
					mainclass.getSoundPlayer().stopSound("bg");
					mainclass.getSoundPlayer().stopSound("bg_long");
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
					debugAnimation=3;
				}
				
				if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
					Player p = mainclass.getWorld().getPlayer();
					if(p.getHealth() <= 0){
						restart(mainclass);
					}else
					p.addStatus("SKILL1",999999,false);

				}
			}
		}


	}
	public static int debugAnimation=0;
	int cooldown=0;
	public boolean valid(Location loc){
		/*boolean done=true;
		//Plot Collison
		for(Plot p:mainclass.getWorld().getPlots()){
			if(p==null || p.getCur()<1)continue;
			if(Location.getDistance(new Location(p.getPlotX(),p.getPlotZ()), new Location(loc.x,loc.z))>p.getMost()*1.2f)continue;
			for(int index=0;index<p.getCur();index++){
				Location b = p.getLocations()[index];
			if(Location.getDistance(b, new Location(loc.x,loc.z))>10f)continue;
						if(Location.getDistance(b, new Location(loc.x+0.5f,loc.z+0.5f))<=0.9f)
							done=false;
					}
		}*/
		return true;
	}
	public static void close(MainClass m) {
		m.getSavedData().saveToFile("player.txt");
		m.setTimeRunning(true);
		m.setRunning(false);
	}

	public static void restart(MainClass m){
		m.getWorld().getPlayer().setHealth(m.getWorld().getPlayer().getStartHealth());
		m.getSavedData().putData("worldOld",(int)m.getSavedData().getData("world"));
		m.getSavedData().putData("world",(int)m.getSavedData().getData("world"));
		((StartClass) m).load((int)m.getSavedData().getData("world"));
		
		Location to=new Location(0,0);
		for(Entity e:m.getWorld().getEntityArray()){
			if(e==null || !(e instanceof EntityPortal))continue;
			if(((EntityPortal)e).isFrom() && ((EntityPortal)e).getPortal() == (int)m.getSavedData().getData("worldOld"))to = e.getLocation().clone();
		}
		m.getWorld().getPlayer().getLocation().set(to);
	}
	

}
