package me.engine.main;

import me.engine.render.Render2D;
import me.engine.skill.Skill;
import me.engine.skill.SkillBloodball;
import me.engine.skill.SkillFireball;
import me.engine.skill.SkillSmash;
import me.engine.skill.SkillSummon;

public class Inventory {
	public static void useItem(MainClass m,int in){
		String skills = (String) m.getSavedData().getData("inv");
		if(skills.length()<1)return;
		String[] inv = skills.split("%");
		if(inv.length<=in)return;
		int ind = Integer.parseInt(inv[in].split("&")[0]);
//		String name = inv[in].split("&")[1];
		
		switch(ind){
		case 0:
			m.getWorld().getPlayer().setSkill(0, skillByIndex(0));
		break;
		case 1:
			m.getWorld().getPlayer().setSkill(0, skillByIndex(1));
		break;
		case 2:
			m.getWorld().getPlayer().setSkill(0, skillByIndex(2));
		break;
		case 3:
			m.getWorld().getPlayer().damage(-2, false);
			removeItem(m,in);
		break;
		default:
			removeItem(m,in);
		}
	}
	
	public static Skill skillByIndex(int in){
		if(in == 0)return new SkillFireball();
		else if(in == 1)return new SkillSmash();
		else if(in == 2)return new SkillBloodball();
		else if(in == 3)return new SkillSummon();
		return null;
	}
	public static int indexBySkill(Skill s){
		if(s instanceof SkillFireball)return 0;
		else if(s instanceof SkillSmash)return 1;
		else if(s instanceof SkillBloodball)return 2;
		else if(s instanceof SkillSummon)return 3;
		return -1;
	}
	
	public static void renderByIndex(MainClass m,int in){
		String texture="item_1";
		if(in == 0)texture="flame_3";
		else if(in == 1)texture="par0_1";
		else if(in == 2)texture="blood_1";
		else if(in == 3)texture="slime_0_1";
		Render2D.renderImage(m, texture);
	}
	
	public static void addItem(MainClass m,int id,String name){
		String string = (String) m.getSavedData().getData("inv");
		string = string + "%"+id+"&"+name;
		m.getSavedData().putData("inv", string);
	}
	public static void removeItem(MainClass m,int index){
		String skills = (String) m.getSavedData().getData("inv");
		String[] inv = skills.split("%");
		String text = "";
		for(int in=0;in<inv.length;in++){
			if(in != index){
				text=text+inv[in]+"%";
			}
		}
		if(text.length()>0)
		text = text.substring(0, text.length()-1);
		m.getSavedData().putData("inv", text);
	}
}
