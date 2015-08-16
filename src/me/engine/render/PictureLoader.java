package me.engine.render;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import javax.swing.ImageIcon;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;


public class PictureLoader {
	HashMap<String, Image> list = new HashMap<String, Image>();
	HashMap<String, Integer> integerlist = new HashMap<String, Integer>();
    public boolean useOwnSize=false;
    public int ownSizeX=0;
    public int ownSizeY=0;
	
	public PictureLoader() {
		list.clear();
		integerlist.clear();
	}
	
	public void setOwnSize(int x,int y){
		useOwnSize=true;
		ownSizeX=x;
		ownSizeY=y;
	}

	
	
	
	public void ImportFromSheet(String name,SheetLoader loader,int x,int y){
		Image img = loader.getImage(x, y);
		int sizex=(int)(img.getWidth(null));
		int sizey=(int)(img.getHeight(null));
		if(useOwnSize)
		{
	     sizex=ownSizeX;
	     sizey=ownSizeY;
		}
		Image img2 = resizeImage(img,sizex,sizey);
		list.put(name, img2);
	}
	
	public void ImportFromSheet(String name,SheetLoader loader,int x,int y,boolean b){
		Image img = loader.getImage(x, y);

		list.put(name, img);
	}
	
	public void ImportFromSheet(String name,SheetLoader loader,int x,int y,int sizex,int sizey){
		Image img = loader.getImage(x, y);
		Image img2 = resizeImage(img,sizex,sizey);
		list.put(name, img2);
	}
	
	
	
	public void addImage(String name, String pfad) {
		Image img = new ImageIcon(pfad).getImage();
		int sizex=(int)(img.getWidth(null));
		int sizey=(int)(img.getHeight(null));
		if(useOwnSize)
		{
	     sizex=ownSizeX;
	     sizey=ownSizeY;
		}
		Image img2 = resizeImage(img,sizex,sizey);
		list.put(name, img2);
	}
	
	public void addImage(String name, URL pfad) {
		Image img = new ImageIcon(pfad).getImage();
		
		
		//BufferedImage img2 = resizeImage(img,img.getWidth(null),img.getHeight(null));
		int sizex=(int)(img.getWidth(null));
		int sizey=(int)(img.getHeight(null));
		if(useOwnSize)
		{
	     sizex=ownSizeX;
	     sizey=ownSizeY;
		}
		Image img2 = resizeImage(img,img.getWidth(null)*sizex,img.getHeight(null)*sizey);
		
		list.put(name, (Image)img2);
	}

	public void addImage(String name, Image img) {
		list.put(name, img);
	}

	public Image getImage(String name) {
		if (list.containsKey(name))
			return list.get(name);
		else
			return null;
	}
	
	public int getImageAsInteger(String name) {
		if (integerlist.containsKey(name))
			return integerlist.get(name);
		else
			return 0;
	}
	

	public Image removeImage(String name) {
		if (list.containsKey(name))
			return list.remove(name);
		else
			return null;
	}

	public HashMap<String, Image> getList() {
		return list;
	}
	
	public HashMap<String, Integer> getIntegerList() {
		return integerlist;
	}

	public void setIntegerList(HashMap<String, Integer> list) {
		this.integerlist = list;
	}
	public void setList(HashMap<String, Image> list) {
		this.list = list;
	}
	
	
	/*public void reinit(double sizex,double sizey){
		if(sizex < 1 || sizey < 1)
		return;
		for(String n:this.list.keySet())
		{
			Image img =  this.list.get(n);
			Image img2 = resizeImage(img,(int)(img.getWidth(null)*sizex),(int)(img.getHeight(null)*sizey));
			list.put(n,img2);
		}
	}*/
	
    public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, 3);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.SrcOver);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        try{
        image.flush();
        }catch(Exception e){
        			e.printStackTrace();
        }
        return bufferedImage;
    }
    
    public static BufferedImage resizeIcon(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
//        graphics2D.setComposite(AlphaComposite.SrcOver);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        try{
        image.flush();
        }catch(Exception e){
        			e.printStackTrace();
        }
        return bufferedImage;
    }
    
    
 

	public void addRezizedImage(String name, Image img, int sizex, int sizey) {
		Image img2 = resizeImage(img,sizex,sizey);
		list.put(name, img2);
	}

	public void loadTexture(){
		for(int i=0;i<this.getList().size();i++){
			
		

			Image img = this.getList().values().toArray(new Image[this.getList().size()])[i];
			String n = this.getList().keySet().toArray(new String[this.getList().size()])[i];
//				System.out.println(i+","+n);
				img = this.getList().values().toArray(new Image[this.getList().size()])
						[i];
				n = this.getList().keySet().toArray(new String[this.getList().size()])
						[i];
			
			
		//	System.out.println(a+","+i+","+n);
			try {
				this.integerlist.put(n, loadTexture(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void reloadTexture(){
		for(int value:integerlist.values())
		GL11.glDeleteTextures(value);
		integerlist.clear();
		for(int i=0;i<this.getList().size();i++){
			Image img = this.getList().values().toArray(new Image[this.getList().size()])[i];
			String n = this.getList().keySet().toArray(new String[this.getList().size()])[i];
				img = this.getList().values().toArray(new Image[this.getList().size()])
						[i];
				n = this.getList().keySet().toArray(new String[this.getList().size()])
						[i];
			
			try {
				this.integerlist.put(n, loadTexture(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static int generateId(){
		//get next free texture ID
		return glGenTextures();
	}
	public static int loadTexture(Image img) throws IOException{
		int textId = generateId();
		//Open file
         BufferedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null), 3);
         Graphics2D graphics2D = image.createGraphics();
        graphics2D.setComposite(AlphaComposite.SrcOver);
        graphics2D.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
        graphics2D.dispose();
        try{
            img.flush();
            }catch(Exception e){
            			e.printStackTrace();
            }

		//new pixel array,size pixels in image
		int[] pixels = new int[image.getHeight()*image.getWidth()];
		//Loads RGB
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		//RBG buffer
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth()*image.getHeight()*4);
		
	
		for(int y = image.getHeight()-1;y>-1;y--){
			for(int x=0;x<image.getWidth();x++){
				//get cur pixel
				int pixel = pixels[y*image.getWidth()+x];
				buffer.put((byte)((pixel>>16)&0xFF));
				buffer.put((byte)((pixel>>8)&0xFF));
				buffer.put((byte)(pixel&0xFF));
				buffer.put((byte)((pixel>>24)&0xFF));
			}
		}


    
		buffer.flip();
		
		glBindTexture(GL_TEXTURE_2D,textId);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_CLAMP_TO_EDGE);
		//Neares = all pixel || gl nearest = glaettet kanten
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,image.getWidth(),image.getHeight(),0,GL_RGBA,GL_UNSIGNED_BYTE,buffer);
		glBindTexture(GL_TEXTURE_2D,0);
		return textId;
	}
}
