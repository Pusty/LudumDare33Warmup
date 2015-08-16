package me.engine.render;

import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
public class TextureLoader {

	public static int loadTexture(String path) throws IOException{
		int textId = glGenTextures();	
		BufferedImage image = ImageIO.read(new File(path));
		int[] pixels = new int[image.getHeight()*image.getWidth()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth()*image.getHeight()*4);
		
	
		for(int y = image.getHeight()-1;y>-1;y--){
			for(int x=0;x<image.getWidth();x++){
				int pixel = pixels[y*image.getHeight()+x];
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
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,image.getHeight(),image.getHeight(),0,GL_RGBA,GL_UNSIGNED_BYTE,buffer);
		glBindTexture(GL_TEXTURE_2D,0);
		return textId;
	}
}
