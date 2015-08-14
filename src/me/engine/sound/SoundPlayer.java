package me.engine.sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.WaveData;

import static org.lwjgl.openal.AL10.*;

public class SoundPlayer {
	int NUM_BUFFERS = 0;
	IntBuffer source;
	IntBuffer buffer;
	List<String> urls = new ArrayList<String>();
	List<Float> volume = new ArrayList<Float>();
	List<Boolean> bools = new ArrayList<Boolean>();
	HashMap<String,Integer> names = new HashMap<String,Integer>();

	public SoundPlayer() {
	}

	public void addToBuffer(String name,String url, boolean loop,float v) {
		urls.add(url);
		bools.add(loop);
		volume.add(v);
		names.put(name,urls.size()-1);
	}

	public int fillALBuffer(){
			try {
				NUM_BUFFERS= urls.size();
				source = BufferUtils.createIntBuffer(NUM_BUFFERS);
				buffer=   BufferUtils.createIntBuffer(NUM_BUFFERS);

			    AL10.alGenBuffers(buffer);
			    if(AL10.alGetError() != AL10.AL_NO_ERROR)
			      return AL10.AL_FALSE;

			    for(int i=0;i<NUM_BUFFERS;i++){
				WaveData data = WaveData.create(new BufferedInputStream(
						new FileInputStream(new File(urls.get(i)))));
			    AL10.alBufferData(buffer.get(i), data.format, data.data, data.samplerate);
			    data.dispose();
			    }
			    AL10.alGenSources(source);

			    if(AL10.alGetError() != AL10.AL_NO_ERROR)
				      return AL10.AL_FALSE;
			    
			    for(int i=0;i<NUM_BUFFERS;i++){
			    AL10.alSourcei(source.get(i), AL10.AL_BUFFER,   buffer.get(i) );
			    AL10.alSourcef(source.get(i), AL10.AL_PITCH,    1.0f          );
			    AL10.alSourcef(source.get(i), AL10.AL_GAIN,    volume.get(i));
			    if(!bools.get(i))
			    AL10.alSourcei(source.get(i), AL10.AL_LOOPING,  AL10.AL_FALSE);    
			    else
		        AL10.alSourcei(source.get(i), AL10.AL_LOOPING,  AL10.AL_TRUE);    
			    }
			    // Do another error check and return.
			    if(AL10.alGetError() == AL10.AL_NO_ERROR)
			      return AL10.AL_TRUE;

			    
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		    return AL10.AL_FALSE;
	 }

	public void removeALData() {
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
		AL.destroy();
	}

	public void playSound(String name,boolean i){
		if(source==null)return;
		if(!names.containsKey(name))return;
		int index = names.get(name);
		int play = AL10.alGetSourcei(source.get(index), AL10.AL_SOURCE_STATE);
		if (i || play != AL10.AL_PLAYING) {
			AL10.alSourcePlay(source.get(index));
		}
	}

	public void stopSound(String name){
		if(!names.containsKey(name))return;
		int index = names.get(name);
		int play = AL10.alGetSourcei(source.get(index), AL10.AL_SOURCE_STATE);
		if (play == AL10.AL_PLAYING) {
			AL10.alSourceStop(source.get(index));
		}
	}
	
	public void stopAll(){
		for(String name:names.keySet()){
		if(!names.containsKey(name))return;
		int index = names.get(name);
		int play = AL10.alGetSourcei(source.get(index), AL10.AL_SOURCE_STATE);
		if (play == AL10.AL_PLAYING) {
			AL10.alSourceStop(source.get(index));
		}
		}
	}
	
	public void pauseAll(){
		for(String name:names.keySet()){
		if(!names.containsKey(name))return;
		int index = names.get(name);
		int play = AL10.alGetSourcei(source.get(index), AL10.AL_SOURCE_STATE);
		if (play == AL10.AL_PLAYING) {
			AL10.alSourcePause(source.get(index));
		}
		}
	}
	
	public void unpauseAll(){
		for(String name:names.keySet()){
		if(!names.containsKey(name))return;
		int index = names.get(name);
		int play = AL10.alGetSourcei(source.get(index), AL10.AL_SOURCE_STATE);
		if (play == AL10.AL_PAUSED) {
			AL10.alSourcePlay(source.get(index));
		}
		}
	}

	public void initSoundPlayer(){
		try {
			AL.create();
		} catch (LWJGLException le) {
			le.printStackTrace();
			return;
		}
		AL10.alGetError();
		
		if (fillALBuffer() == AL10.AL_FALSE) {
			System.out.println("Error loading data.");
			return;
		}
	}

}
