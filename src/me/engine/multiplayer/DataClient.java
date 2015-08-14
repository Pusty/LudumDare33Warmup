package me.engine.multiplayer;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import me.engine.location.Location;
import me.engine.main.Controls;
import me.engine.main.GameTickHandler;
import me.engine.main.MainClass;
import me.engine.render.Render2D;
import me.engine.render.SheetLoader;


public class DataClient extends Side implements Runnable{
	DatagramSocket clientSocket;
	int port;
    InetAddress serverIp;
    public static int downloadrate=1024;
	PacketHandler packethandler;
	MainClass mainclass;
	public DataClient(MainClass m,String ip,int port){
		  try {
			  clientSocket = new DatagramSocket();
			  serverIp =InetAddress.getByName(ip);
		       packethandler = new PacketHandler(this);
		       mainclass=m;
			  this.port = port;
			  Thread thread =  new Thread(this);
          	thread.start();
		  }
		      catch(Exception e) {
//					e.printStackTrace();
					  System.out.println("Failed to connect to Server (Error)");
		      }
		  
		  
	}

	public PacketHandler getPacketHandler(){return packethandler;}
	public static boolean gotmsg;
	public static long gotTime;
	public int ping=0;
	boolean partsended=false;
	boolean exit=false;
	public void run() {
		final DataClient client = this;
		gotmsg=false;
		gotTime=0L;
        System.out.println("Client started!");
        new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				gotTime=System.currentTimeMillis();
				while(mainclass.isRunning() && !exit){
				if(gotmsg==false && ((System.currentTimeMillis()-gotTime)>5000)){
					exit=true;
				}
				try {  
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
		        System.out.println("Client closed!");
				clientSocket.close();
			}
        }).start();
		sendPacket(new Packet(client,0,"ping"));
		while(mainclass.isRunning() && !exit){
			tryToGetData();
		}
	}

	
	public void tryToGetData(){
        try {
        	 byte[] receiveData = new byte[downloadrate];
        	   DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
               clientSocket.receive(receivePacket);

         
               DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(receivePacket.getData()));
               
//               downloadrate=inputStream.readInt();
               
               handlePacket(inputStream.readInt(),inputStream,receivePacket);
			 
		} catch (IOException e) {
			System.out.println("package Error");
//			e.printStackTrace();
		}
	}
	


	public void handlePacket(int id, DataInputStream inputStream, DatagramPacket receivePacket){
		this.getPacketHandler().handle(id, inputStream, receivePacket,mainclass);
	}
	      

	      
	      
	      
	

	@Override
	public String getSide() {
		return "Client";
	}

	
	//To the Server
	@Override
	public void sendPacket(Packet p) {
		   try {	
		  DatagramPacket sendPacket = new DatagramPacket(p.getData(), p.getData().length, serverIp, port);  
			clientSocket.send(sendPacket);		 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public static void main(String[] args){
//		DataClient c = new DataClient("localhost", 7777);
//	}



}
