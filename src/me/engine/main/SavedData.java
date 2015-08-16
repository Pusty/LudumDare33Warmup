package me.engine.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


public class SavedData {
	HashMap<String,Object> data;
	boolean loaded=false;
	public SavedData(){
		data = new HashMap<String,Object>();
	}
	public void putData(String s,Object o){
		data.put(s, o);
	}
	public void removeData(String s){
		data.remove(s);
	}
	public void clearData(){
		data.clear();
	}
	public Object getData(String s){
		if(data.containsKey(s))return data.get(s);
		return null;
	}
	public void createFromNew(){
		//GAMEINFO
		putData("health",10);
		putData("posX",-1f);
		putData("posZ",-1f);
		putData("world",1);
		putData("worldOld",0);
		putData("skill",0);
		putData("inv","0&Fireball%1&Smash%2&Bloodball%3&Potion%3&Potion%3&Potion");
		loaded=true;
	}
	
	public void printOutAll(){
		for(int ind = 0;ind<data.size();ind++){
			String key = (String) data.keySet().toArray()[ind];
			String value = data.get(key).toString();
			System.out.println(key+"/"+value);
		}
	}
	public void saveToFile(String file){
		if(!loaded)return;
		File f = new File(System.getProperty("user.dir") + "\\data\\" + file);
		try {
			if (!f.exists()) {
				f.createNewFile();
			} else {
				f.delete();
				f.createNewFile();
			}
			System.out.println("File created");

			FileOutputStream fw = new FileOutputStream(f);
			for(int ind = 0;ind<data.size();ind++){
				String key = (String) data.keySet().toArray()[ind];
				String value = data.get(key).toString();
				fw.write((key+"/"+value+"\n").getBytes());
			}
				fw.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadFromFile(String file){
		File f = new File(System.getProperty("user.dir") + "\\data\\" + file);
		String line = "";
		try{
		
				if (!f.exists()) {
					System.out.println("File does not exist");
					createFromNew();
				} else {
					FileInputStream fi = new FileInputStream(f);
					InputStreamReader isr = new InputStreamReader(fi);
					BufferedReader br = new BufferedReader(isr);
					line = br.readLine();
			
					while (line != null) {
						if(line.contains("/")){
							try{putData(line.split("/")[0],Integer.parseInt(line.split("/")[1]));}catch(Exception e){
								try{putData(line.split("/")[0],Float.parseFloat(line.split("/")[1]));}catch(Exception e2){
									if(line.split("/")[1].equalsIgnoreCase("true"))putData(line.split("/")[0],true);
									else if(line.split("/")[1].equalsIgnoreCase("false"))putData(line.split("/")[0],false);
								putData(line.split("/")[0],line.split("/")[1]);
										
								}	
							}
							
						}
						line = br.readLine();
					}
					fi.close();
				}
				
			
			} catch (Exception e) {
				System.out.println(line);
				e.printStackTrace();
			}
		loaded=true;
		}
	
}
