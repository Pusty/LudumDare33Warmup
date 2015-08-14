package me.engine.multiplayer;

import java.io.DataInputStream;
import java.net.DatagramPacket;

import me.engine.main.MainClass;

public class PacketHandler {
	Side mainclass;
	HandlingPacket[] hpa;
	public PacketHandler(Side mc){
		mainclass=mc;
		hpa=new HandlingPacket[100];
		initPackets();
	}
	public void initPackets(){
		hpa[0]=new PacketPing00(mainclass);

	}
	public void handle(int id, DataInputStream inputStream, DatagramPacket receivePacket ,MainClass m){
		hpa[id].handle(id, inputStream, receivePacket,m);
	}
	public HandlingPacket get(int i) {
		return hpa[i];
	}
	public int getByteRate(int id) {
		return hpa[id].getBitRate();
	}
	

}
