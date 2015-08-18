package me.game.main;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import spy.gui.features.Button;
import spy.gui.features.Form;
import spy.gui.features.SkillInventory;
import me.engine.location.Location;
import me.engine.main.Controls;
import me.engine.main.GameTickHandler;
import me.engine.main.Inventory;
import me.engine.main.MainClass;
import me.engine.multiplayer.DataClient;
import me.engine.block.HandledBlock;
import me.engine.entity.Player;
import me.engine.world.LevelScript;
import me.engine.world.World;
import me.engine.render.AnimationHandler;
import me.engine.render.Render2D;
import me.engine.render.SheetLoader;
import me.game.leveldesign.Level01;

public class StartClass extends MainClass {
	public static boolean light=false;
	public static boolean online=true;
	public void preInit() {
		SheetLoader sheetloader;
		try {

			for (int i = 0; i < 5; i++) {
				try {
					String folder = "player_" + i;
					sheetloader = new SheetLoader(
							System.getProperty("user.dir") + "\\img\\" + folder
									+ "\\player.png", 1, 4 * 4, 16, 16);
					for (int a = 0; a < 4 * 4; a++) {
						getPictureLoader().ImportFromSheet(
								"player" + i + "_" + String.valueOf(a),
								sheetloader, a, 0);
					}
					sheetloader = new SheetLoader(
							System.getProperty("user.dir") + "\\img\\" + folder
									+ "\\player_death.png", 1, 4 * 4, 16, 16);
					for (int a = 0; a < 4 * 4; a++) {
						getPictureLoader().ImportFromSheet(
								"player" + i + "_death_" + String.valueOf(a),
								sheetloader, a, 0);
					}
					sheetloader = new SheetLoader(
							System.getProperty("user.dir") + "\\img\\" + folder
									+ "\\player_attack.png", 1, 4 * 4, 16, 16);
					for (int a = 0; a < 4 * 4; a++) {
						getPictureLoader().ImportFromSheet(
								"player" + i + "_attack_" + String.valueOf(a),
								sheetloader, a, 0);
					}
					sheetloader = new SheetLoader(
							System.getProperty("user.dir") + "\\img\\" + folder
									+ "\\player_melee.png", 1, 4 * 4, 16, 16);
					for (int a = 0; a < 4 * 4; a++) {
						getPictureLoader().ImportFromSheet(
								"player" + i + "_melee_" + String.valueOf(a),
								sheetloader, a, 0);
					}
					System.out.println("Found Player " + i);
				} catch (Exception e) {
				}
			}

			try {
				String folder = "slime";
				sheetloader = new SheetLoader(System.getProperty("user.dir")
						+ "\\img\\" + folder + "\\slime.png", 1, 4 * 4, 16, 16);
				for (int a = 0; a < 4 * 4; a++) {
					getPictureLoader()
							.ImportFromSheet(folder + "_" + String.valueOf(a),
									sheetloader, a, 0);
				}
				sheetloader = new SheetLoader(System.getProperty("user.dir")
						+ "\\img\\" + folder + "\\slime_death.png", 1, 4 * 4,
						16, 16);
				for (int a = 0; a < 4 * 4; a++) {
					getPictureLoader().ImportFromSheet(
							folder + "_death_" + String.valueOf(a),
							sheetloader, a, 0);
				}
				System.out.println("Found Slime");
			} catch (Exception e) {
			}
			
			try {
				String folder = "bloodslime";
				sheetloader = new SheetLoader(System.getProperty("user.dir")
						+ "\\img\\" + folder + "\\slime.png", 1, 4 * 4, 16, 16);
				for (int a = 0; a < 4 * 4; a++) {
					getPictureLoader()
							.ImportFromSheet(folder + "_" + String.valueOf(a),
									sheetloader, a, 0);
				}
				sheetloader = new SheetLoader(System.getProperty("user.dir")
						+ "\\img\\" + folder + "\\slime_death.png", 1, 4 * 4,
						16, 16);
				for (int a = 0; a < 4 * 4; a++) {
					getPictureLoader().ImportFromSheet(
							folder + "_death_" + String.valueOf(a),
							sheetloader, a, 0);
				}
				System.out.println("Found BloodSlime");
			} catch (Exception e) {
			}

			// Image img = ImageIO.read(new
			// File(System.getProperty("user.dir")+"\\data\\"+"map_0"+".png"));
			// sheetloader = new
			// SheetLoader(System.getProperty("user.dir")+"\\data\\"+"map_0"+".png",1,img.getWidth(null)/16,16,16);
			// for(int a=0;a<img.getWidth(null)/16;a++){
			// getPictureLoader().ImportFromSheet("block_"+String.valueOf(a),
			// sheetloader, a,0);
			// }

			sheetloader = new SheetLoader(System.getProperty("user.dir")
					+ "\\img\\items.png", 8, 8, 16, 16);
			for (int a = 0; a < 8 * 8; a++) {
				getPictureLoader().ImportFromSheet("item_" + String.valueOf(a),
						sheetloader, a % 8, a / 8);
			}

			String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
					"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
					"V", "W", "X", "Y", "Z", ":", "!", "?", ".", "[", "]", "0",
					"1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", "+",
					"-", "/", " ", "_" };
			sheetloader = new SheetLoader(System.getProperty("user.dir")
					+ "\\img\\letters.png", 8, 8, 16, 16);
			for (int a = 0; a < letter.length; a++) {
				getPictureLoader().ImportFromSheet("char_" + letter[a],
						sheetloader, a % 8, a / 8);

			}

			getPictureLoader().addImage("shadow",
					System.getProperty("user.dir") + "\\img\\shadow.png");
			getPictureLoader().addImage("tree",
					System.getProperty("user.dir") + "\\img\\tree.png");

			sheetloader = new SheetLoader(System.getProperty("user.dir")
					+ "\\img\\flame.png", 1, 4, 16, 16);
			for (int a = 0; a < 4; a++)
				getPictureLoader().ImportFromSheet("flame_" + a, sheetloader,
						a, 0);
			
			sheetloader = new SheetLoader(System.getProperty("user.dir")
					+ "\\img\\blood.png", 1, 4, 16, 16);
			for (int a = 0; a < 4; a++)
				getPictureLoader().ImportFromSheet("blood_" + a, sheetloader,
						a, 0);
			
			
			sheetloader = new SheetLoader(System.getProperty("user.dir")
					+ "\\img\\portal.png", 1, 4, 16, 16);
			for (int a = 0; a < 4; a++)
				getPictureLoader().ImportFromSheet("portal_" + a, sheetloader,
						a, 0);
			
			for(int n=0;n<10;n++){
				try{
					sheetloader = new SheetLoader(System.getProperty("user.dir")
							+ "\\img\\particle_"+n+".png", 1, 4, 16, 16);
					for (int a = 0; a < 4; a++)
						getPictureLoader().ImportFromSheet("par"+n+"_" + a, sheetloader,
								a, 0);
				}catch(Exception e){};
			}

			
			//Init animations
			AnimationHandler.addHandler("player1", 4);
			AnimationHandler.getHandler("player1").addAnimation("player1", "walk", 100, 1, true);
			AnimationHandler.getHandler("player1").addAnimation("player1_melee", "melee", 160, 0, false);
			AnimationHandler.getHandler("player1").addAnimation("player1_attack", "attack", 160, 0, false);
			AnimationHandler.getHandler("player1").addAnimation("player1_death", "death", 120, 0, false);
			
			AnimationHandler.addHandler("player2", 4);
			AnimationHandler.getHandler("player2").addAnimation("player2", "walk", 100, 1, true);
			AnimationHandler.getHandler("player2").addAnimation("player2_melee", "melee", 160, 0, false);
			AnimationHandler.getHandler("player2").addAnimation("player2_attack", "attack", 160, 0, false);
			AnimationHandler.getHandler("player2").addAnimation("player2_death", "death", 120, 0, false);
			
			AnimationHandler.addHandler("slime", 2);
			AnimationHandler.getHandler("slime").addAnimation("slime", "walk", 120, 1, true);
			AnimationHandler.getHandler("slime").addAnimation("slime_death", "death", 120, 0, false);
			
			AnimationHandler.addHandler("bloodslime", 2);
			AnimationHandler.getHandler("bloodslime").addAnimation("bloodslime", "walk", 120, 1, true);
			AnimationHandler.getHandler("bloodslime").addAnimation("bloodslime_death", "death", 120, 0, false);
			
			AnimationHandler.addHandler("portal", 1);
			AnimationHandler.getHandler("portal").addAnimation("portal", "exist", 120, 1, true);
			
			AnimationHandler.addHandler("flame", 1);
			AnimationHandler.getHandler("flame").addAnimation("flame", "exist", 120, 1, true);
			
			AnimationHandler.addHandler("blood", 1);
			AnimationHandler.getHandler("blood").addAnimation("blood", "exist", 120, 1, true);
			
			
			AnimationHandler.addHandler("par0", 1);
			AnimationHandler.getHandler("par0").addAnimation("par0", "exist", 120, 0, true);
			
			AnimationHandler.addHandler("par1", 1);
			AnimationHandler.getHandler("par1").addAnimation("par1", "exist", 120, 0, true);
			
			AnimationHandler.addHandler("par2", 1);
			AnimationHandler.getHandler("par2").addAnimation("par2", "exist", 120, 0, true);
			
			AnimationHandler.addHandler("par3", 1);
			AnimationHandler.getHandler("par3").addAnimation("par3", "exist", 120, 0, true);

			AnimationHandler.addHandler("par4", 1);
			AnimationHandler.getHandler("par4").addAnimation("par4", "exist", 120, 0, true);

			for (int i = 0; i < 64; i++) {
				HandledBlock b = new HandledBlock(i);
				this.getBlockHandler().addBlock("block_" + i, b, i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Init() {
	}

	@Override
	public void RenderInit() {
		Render2D render2d = new Render2D(this);
		render2d.startRender();

	}

	@Override
	public void ControlInit() {
		Controls controls = new Controls(this);
		controls.startControls();
	}

	@Override
	public void GameTickInit() {
		GameTickHandler g = new GameTickHandler(this);
		this.setGameTickHandler(g);
		this.getGameTickHandler().startGameTick();
	}


	public void postInit(){
		if(online)
		this.setUpConnection(new DataClient(this,"localhost",25565));
	}
	
	public void load(int mapID) {
		this.loadMap();
		getTextLoader().addIndex();
		this.setTimeRunning(false);
		try {
			Image img = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\data\\map_" + mapID + ".png"));
			SheetLoader sheetloader = new SheetLoader(
					System.getProperty("user.dir") + "\\data\\map_" + mapID + ".png",
					1, img.getWidth(null) / 16, 16, 16);
			for (int a = 0; a < (img.getWidth(null) / 16); a++) {
				getPictureLoader().ImportFromSheet(
						"block_" + String.valueOf(a), sheetloader, a, 0);
			}
		} catch (Exception e) {

		}

		Render2D.chunkList = new int[1];
		Render2D.chunkList[0] = 0;

		File f = new File(System.getProperty("user.dir") + "\\data\\map_" + mapID
				+ ".txm");
		File f2 = new File(System.getProperty("user.dir") + "\\data\\smap_" + mapID
				+ ".txm");
		
		LevelScript levelscript = LevelScript.getLevel(mapID);
		String line = "";
		try {
			if (!f.exists() || !f2.exists()) {
				System.out.println("File does not exist");
			} else {
				World world = null;
				Location player = new Location(0, 0);
				{
					FileInputStream fi = new FileInputStream(f);
					InputStreamReader isr = new InputStreamReader(fi);
					BufferedReader br = new BufferedReader(isr);
					line = br.readLine();
//					int index = 0;
					int se = 40;
					List<int[]> blockarray = new ArrayList<int[]>();
					while (line != null) {
						int blocks = line.split(",").length;
						int[] intarray = new int[blocks];
						for (int i = 0; i < blocks; i++)
							intarray[i] = Integer.parseInt(line.split(",")[i]);
						blockarray.add(intarray);
						line = br.readLine();
					}
					world = new World(this, blockarray.get(0).length,
							blockarray.size(), se);
					int sz = 0;
					for (int[] ints : blockarray) {
						int sx = 0;
						for (int in : ints) {
							world.setBlockID(sx, blockarray.size() - sz - 1, in);
							sx++;
						}
						sz++;
					}
				}
				
				{	//Meta File
					FileInputStream fi = new FileInputStream(f2);
					InputStreamReader isr = new InputStreamReader(fi);
					BufferedReader br = new BufferedReader(isr);
					line = br.readLine();
					int z=0;
					while (line != null) {
						int blocks = line.split(",").length;
						int x=0;
						for(int index=0;index<blocks;index++){
							int metaID = Integer.parseInt(line.split(",")[index]);
							world.setBlockMeta(x,world.getSizeZ()-z-1, metaID);
							if(metaID-7 == mapID)
								player = new Location(x,world.getSizeZ()-z-1);
							else if(metaID == 3)
								levelscript.addTree(this,world,metaID,x,world.getSizeZ()-z-1);	
							else if(metaID == 4)
								levelscript.addNPC(this,world,metaID,x,world.getSizeZ()-z-1);
//								world.addEntity(new NPCEntity(this,x,world.getSizeZ()-z,"player3"));
							else if(metaID == 5)
								levelscript.addEnemy(this,world,metaID,x,world.getSizeZ()-z-1);
							else if(metaID == 6)
								levelscript.addSpecial1(this,world,metaID,x,world.getSizeZ()-z-1);
							else if(metaID == 7)
								levelscript.addSpecial2(this,world,metaID,x,world.getSizeZ()-z-1);
							
							if(metaID > 7)
								levelscript.addPortal(this,world,metaID,x,world.getSizeZ()-z-1);
							
								x++;
						}
						line = br.readLine();
						z++;
					}
				}
				if(getSavedData().getData("world") == getSavedData().getData("worldOld") && (float)getSavedData().getData("posX") >= 0)
					player = new Location((float)getSavedData().getData("posX"),(float)getSavedData().getData("posZ"));
				getSavedData().putData("worldOld",(int)getSavedData().getData("world"));
				getSavedData().putData("posX",player.x);
				getSavedData().putData("posZ",player.z);
				world.setPlayer(new Player(this, player.x,  player.z));
				world.getPlayer().setHealth((int)getSavedData().getData("health"));
//				world.calcLight();
				this.setWorld(world);
				reRender(world);
				rerender = true;
			}

		} catch (Exception e) {
			System.out.println(line);
			e.printStackTrace();
		}
		this.setTimeRunning(true);
		this.setLoaded();
		getSavedData().saveToFile("player.txt");
	}

	public static boolean rerender = false;

	private void reRender(World w) {
		Render2D.chunkList = new int[w.getChunkArray().length];
		for (int i = 0; i < w.getChunkArray().length; i++)
			Render2D.chunkList[i] = -1;

	}
	public static void openInventory(MainClass m){
	/*	if(m.getGui() != null)return;
		 GuiScreen gui = new GuiScreen(3);	
		gui.setGuiPart(0, new Button(new Location(6,-10),6,2,0,18,60,100,"Exit"){
			@Override
			public void buttonClick(MainClass m,float mx,float mz){
				Controls.close(m);
			}
		});
		gui.setGuiPart(1, new SkillInventory(new Location(-4.5f,1.75f)));
		
		gui.setGuiPart(2, new Button(new Location(-12,-10),6,2,0,18,0,40,"Use"){
			@Override
			public void buttonClick(MainClass m,float mx,float mz){
				Inventory.useItem(m, ((SkillInventory)(m.getGui().getGuiPart(1))).getItemIndex());
			}
		});
		m.setGui(gui);*/
		if(m.getGui() != null)return;
		Form gui = new Form(new Location(0,0),new Location(0,0),"",3);	
		gui.setGuiPart(0,new Button(new Location(6,-10),new Location(6,2),"Exit",0,18,60,100){
			@Override
			public void buttonClick(MainClass m,float mx,float mz){
				Controls.close(m);
			}
		});
		gui.setGuiPart(1, new SkillInventory(new Location(-4.5f,1.75f)));
		
		gui.setGuiPart(2, new Button(new Location(-12,-10),new Location(6,2),"Use",0,18,0,40){
			@Override
			public void buttonClick(MainClass m,float mx,float mz){
				Inventory.useItem(m, ((SkillInventory)(m.getGui().getGuiPart(1))).getItemIndex());
			}
		});
		m.setGui(gui);
	}
	public static void closeInventory(MainClass m){m.setGui(null);}
	@Override
	public void WorldInit() {
		LevelScript.addLevel(1, new Level01());
	
		getTextLoader().loadFromFile("loading.txt");
		if(!this.getLoadGame())
		getSavedData().loadFromFile("player.txt");
		else
		getSavedData().createFromNew();
		

		load((int)getSavedData().getData("world"));

		this.setDialog("Me",
				"Where am I? What is this ? Why is everything smelling like cake ?");

	}

	@Override
	public void SoundInit() {

	getSoundPlayer().addToBuffer("bg",System.getProperty("user.dir") + "\\util\\track_1_short.wav", false,0.2f);
	getSoundPlayer().addToBuffer("bg_long",System.getProperty("user.dir") + "\\util\\track_1.wav", true,0.2f);
	getSoundPlayer().addToBuffer("exp0",System.getProperty("user.dir") + "\\util\\exp_0.wav", false,1f);
	getSoundPlayer().addToBuffer("exp1",System.getProperty("user.dir") + "\\util\\exp_1.wav", false,1f);
	getSoundPlayer().addToBuffer("hit0",System.getProperty("user.dir") + "\\util\\hit_0.wav", false,1f);
	}



}
