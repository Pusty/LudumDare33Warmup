package me.engine.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TextLoader {
	List<String> text;
	int index=0;
	public TextLoader(){
		text = new ArrayList<String>(); 
	}

	public String getText(){
		return text.get(index);
	}
	public void addIndex(){
		index++;
		if(index>=text.size())index=0;
	}
	Random random = new Random();
	public void loadFromFile(String file){
		File f = new File(System.getProperty("user.dir") + "\\data\\" + file);
		String line = "";
		try{
		
				if (!f.exists()) {
					System.out.println("TextFile does not exist");
					text.add("404 cheese not found!");
				} else {
					FileInputStream fi = new FileInputStream(f);
					InputStreamReader isr = new InputStreamReader(fi);
					BufferedReader br = new BufferedReader(isr);
					line = br.readLine();
			
					while (line != null) {
						text.add(line);
						line = br.readLine();
					}
					fi.close();
				}
				
			
			} catch (Exception e) {
				System.out.println(line);
				e.printStackTrace();
			}

			index = random.nextInt(text.size());
		}
	
}
