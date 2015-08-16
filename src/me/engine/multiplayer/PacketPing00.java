package me.engine.multiplayer;

import java.io.DataInputStream;
import java.net.DatagramPacket;

import me.engine.main.MainClass;

public class PacketPing00 extends HandlingPacket {

	public PacketPing00(Side mc) {
		super(mc);
	}


	@Override
	public void handle(int id, DataInputStream inputStream,
			DatagramPacket receivePacket,MainClass m) {
		if(isClient()){
	
//				client().gotmsg=true;
				m.getWorld().getPlayer().addStatus("SHOOT",240,false);
		}else{
			server().sendPacket(new Packet(server(),0,"ping"),receivePacket.getAddress(),receivePacket.getPort());
			server().getClient(receivePacket.getAddress(),receivePacket.getPort()).setLastPinged(System.currentTimeMillis());
		}
		
		System.out.println("Ping");
	}
	
	@Override
	public int getBitRate() {
		return 1024;
	}

}
