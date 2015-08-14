package me.engine.multiplayer;

import java.io.DataInputStream;
import java.net.DatagramPacket;

import me.engine.main.MainClass;

public abstract class HandlingPacket {
	Side mainclass;
	public HandlingPacket(Side mc){
		mainclass=mc;
	}
	public Side getMainClass(){return mainclass;}
	public abstract void handle(int id, DataInputStream inputStream, DatagramPacket receivePacket,MainClass m);
	public void handleWrite(Packet p,Object...objs){
		
	}
	public void handleRead(DataInputStream inputStream,Object...objs){
		
	}
	public boolean isClient(){return mainclass instanceof DataClient;}
	public Side side(){return (Side)mainclass;}
	public DataClient client(){return (DataClient)mainclass;}
	public InformationServer server(){return (InformationServer)mainclass;}
	public abstract int getBitRate();
}
