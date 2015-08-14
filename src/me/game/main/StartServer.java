package me.game.main;

import me.engine.multiplayer.InformationServer;

public class StartServer {
	public static void main(String[] args){
		InformationServer s = new InformationServer(25565);
	}
}
