package me.engine.multiplayer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet {

	public DataOutputStream outstream;
	public ByteArrayOutputStream byteoutstream;
	public Side packetSide;
	public Packet(Side side,int id,String... s){
		try {
		byteoutstream = new ByteArrayOutputStream(8);
		outstream = new DataOutputStream(new ByteArrayOutputStream(8));
//	if(side instanceof Client){
//		 outstream.writeInt(side.getPacketHandler().getByteRate(id));
//	}
	  outstream.writeInt(id);
	  for(String d:s){
		  if(d != null)
		  outstream.writeUTF(d);
	  }

		
		side = packetSide;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Packet(Side side,int id,String s){
		try {
		byteoutstream = new ByteArrayOutputStream(8);
		outstream = new DataOutputStream(byteoutstream);
//		if(side instanceof Client){
//			 outstream.writeInt(side.getPacketHandler().getByteRate(id));
//		}	
	  outstream.writeInt(id);
	  outstream.writeUTF(s);
		side = packetSide;
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addToPacket(Object o){
		try{
		if(o instanceof Integer)
		outstream.writeInt((int) o);
		else if(o instanceof String)
			outstream.writeUTF((String) o);
		else if(o instanceof byte[])
			outstream.write((byte[]) o);
		else if(o instanceof Byte)
			outstream.writeByte((byte) o);
		else if(o instanceof Boolean)
			outstream.writeBoolean((Boolean) o);
		else if(o instanceof Float)
			outstream.writeFloat((Float) o);
		else if(o instanceof Double)
			outstream.writeDouble((Double) o);
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	
	
	public void send(){
		packetSide.sendPacket(this);
	}
	
	public DataOutputStream getStream(){
		return outstream;
	}
	
	public byte[] getData(){
		return byteoutstream.toByteArray();
	}
}
