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
import me.engine.skill.Skill;
import me.engine.skill.SkillSmash;

import org.lwjgl.opengl.GL11;

public class EntitySummon extends EntityMonster {
	int enemy;
	EntityLiving enemyE;
	Skill skill;
	public EntitySummon(MainClass m, float x, float y,Skill s) {
		super(m, (int)x, (int)y, "slime");
		this.getLocation().set(new Location(x,y));
		setSkill(0,new SkillSmash());
		enemy=-1;
		enemyE=null;
		skill=s;
	}
	
	public int findEnemy(MainClass m){
		float n=5f;
		int id=-1;
		EntityLiving en=null; 
		for (int a = 0; a < m.getWorld().getEntityArray().length; a++) {
			if (m.getWorld().getEntityArray()[a] == null)
				continue;
			if(m.getWorld().getEntityArray()[a]==this)continue;
			if(m.getWorld().getEntityArray()[a] instanceof EntitySummon)continue;
			if (m.getWorld().getEntityArray()[a] instanceof EntityLiving) {
				EntityLiving el = (EntityLiving) m
						.getWorld().getEntityArray()[a];
				if(el.getHealth()<=0)continue;
				if(Location.getDistance(this.getLocation(), el.getLocation()) < n){
					n=(float) Location.getDistance(this.getLocation(), el.getLocation());
					id=a;
					en=el;
					
				}
			}
		}
		enemy=id;
		enemyE=en;
		return -1;
	}

	public String getDialogText(Random r) {
			return "Random summoned mob";
	}
	@Override
	public int getStartHealth(){return 1;}
	public Entity createByString(MainClass m, String s, String split) {
		Entity e = null;
		System.out.println("Initing " + getName());
		try {
			int x = Integer.parseInt(s.split(split)[0]);
			int z = Integer.parseInt(s.split(split)[1]);
			e = new EntitySummon(m, x, z,null);
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

	int ticks=0;
	public void tick(MainClass m){
		ticks++;
		if(ticks>100)ticks=0;
		if(enemy==-1 && ticks==100)findEnemy(m);
		if(enemy>-1 && !m.getWorld().hasEntity(enemyE))findEnemy(m);
		if(enemy!=-1 &&  ((EntityLiving) m.getWorld().getEntityArray()[enemy]).getHealth() <= 0)findEnemy(m);
		
		if(enemy!=-1)
		if(Location.getDistance(this.getLocation(), m.getWorld().getEntityArray()[enemy].getLocation())<1f)
			addStatus("SKILL1",999999,false);
		
		
		super.tick(m);
	}
	protected EntitySummon() {/* USED FOR DUMMY */
	}

	public String getName() {
		return "Summon";
	}

	
	Random random = new Random();
	int times = 0;


	@Override
	public void recalcNextLocation(boolean b){
	times++;
	if(!b && times < 40)return;

			Location basic = null;
			if(enemy == -1){
				basic = skill.sender.getLocation().clone();
			}else
				basic = enemyE.getLocation().clone();
			
			nextlocation = basic.add(new Location(random.nextFloat(), random.nextFloat()));
			times=0;
		
	}
	
	public void addRender(){
		getAnimation().getCurA().addTick();
	}

	public String getTextureName(int i){
		return "slime";
	}
	public void render(MainClass m){
		super.render(m);
	}
	
	
}
