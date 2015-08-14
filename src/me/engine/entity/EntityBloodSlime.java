package me.engine.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import java.util.Random;

import me.engine.location.Location;
import me.engine.location.Velocity;
import me.engine.main.Controls;
import me.engine.main.MainClass;
import me.engine.skill.SkillBloodball;
import me.engine.skill.SkillFireball;
import me.engine.skill.SkillSmash;

import org.lwjgl.opengl.GL11;

public class EntityBloodSlime extends EntityMonster {
	public EntityBloodSlime(MainClass m, int x, int y) {
		super(m, x, y, "bloodslime");
		setSkill(0,new SkillBloodball());
	}

	public String getDialogText(Random r) {
		int random = r.nextInt(3);
		if (random == 0)
			return "I am a slime";
		if (random == 1)
			return "SLIIIME";
		if (random == 2)
			return "SLIMY SLIMY";
		return null;
	}
	@Override
	public int getStartHealth(){return 5;}
	public Entity createByString(MainClass m, String s, String split) {
		Entity e = null;
		System.out.println("Initing " + getName());
		try {
			int x = Integer.parseInt(s.split(split)[0]);
			int z = Integer.parseInt(s.split(split)[1]);
			e = new EntityBloodSlime(m, x, z);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}

	public String createToString(MainClass m, String split) {
		String s = "";
		s = s + (int) this.getX();
		s = s + split;
		s = s + (int) this.getZ();
		s = s + "\n";
		return s;
	}


	public void tick(MainClass m){
			addStatus("SKILL1",999999,false);
		super.tick(m);
	}
	protected EntityBloodSlime() {/* USED FOR DUMMY */
	}

	public String getName() {
		return "Bloodslime";
	}

	
	Random random = new Random();


	public void recalcNextLocation(boolean b) {
		times++;
		timer++;
		if(timer<=40 && !b)return;
		timer=0;
		if (nextlocation == null || times > 10){
			nextlocation = getLocation()
					.add(new Location(random.nextFloat()-0.5f, random.nextFloat()-0.5f))
					.clone();
			times=0;
		}
		if (Location.getDistance(this.getLocation(), nextlocation) < 0.5f) {
			nextlocation = getLocation()
					.add(new Location(random.nextFloat()-0.5f, random.nextFloat()-0.5f))
					.clone();

		}
	}
	
	public void addRender(){
		getAnimation().getCurA().addTick();
	}

	public String getTextureName(int i){
		return "bloodslime";
	}
	public void render(MainClass m){
		super.render(m);
	}
	
	
}
