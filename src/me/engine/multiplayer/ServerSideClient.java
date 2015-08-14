package me.engine.multiplayer;

import java.net.InetAddress;

public class ServerSideClient {
	int port;
	InetAddress ip;
	int index;
	long lastpinged;
 public ServerSideClient(InetAddress address,int port,int index){
	 this.port = port;
	 this.ip = address;
	 this.index=index;
	 lastpinged=System.currentTimeMillis();
 }

public long getLastPinged(){return lastpinged;}
public void setLastPinged(long l){lastpinged=l;}
public InetAddress getAddress() {
	return ip;
}
public int getIndex(){
	return index;
}
public int getPort() {
	return port;
}
}
