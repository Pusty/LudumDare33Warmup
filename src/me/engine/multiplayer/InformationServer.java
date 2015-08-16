package me.engine.multiplayer;



import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;






public class InformationServer extends Side implements Runnable{
	DatagramSocket  serverSocket;
	ServerSideClient[] clients;
	PacketHandler packethandler;
	boolean running=true;
	int port;
	public InformationServer(int port){
		this.port = port;
	}
	public void start(){
		try {
	        serverSocket = new DatagramSocket (port);
	        clients = new ServerSideClient[25];
	        packethandler = new PacketHandler(this);
	        new Thread(this).start();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public void stop(){
		running=false;	
	}
	
	public PacketHandler getPacketHandler(){return packethandler;}
	public void tryToGetData(){
        try {
        	 byte[] receiveData = new byte[1024];
        	   DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        	   
               serverSocket.receive(receivePacket);
       
               boolean allreadyingame = false;
               for(ServerSideClient client:clients){
            	   if(client != null && receivePacket.getAddress().equals(client.getAddress()) && receivePacket.getPort() == client.getPort())
            		   allreadyingame = true;
               }
               if(allreadyingame == false)
            	   this.addClient(receivePacket);
               
               DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(receivePacket.getData()));
 
             
               handlePacket(inputStream.readInt(),inputStream,receivePacket);
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ServerSideClient getClient(InetAddress ip,int port){
        for(ServerSideClient client:clients){
     	   if(client != null && ip.equals(client.getAddress()) && port == client.getPort())
     		   return client;
        }
		return null;
	}
	public void removePlayer(int i) {
	    clients[i]=null;
	}
	private void addClient(DatagramPacket packet) {
		for(int i=0;i<clients.length;i++){
			if(clients[i] == null){
				clients[i] = new ServerSideClient(packet.getAddress(),packet.getPort(),i);
				return;
			}
		}
		
	}

	public void handlePacket(int id, DataInputStream inputStream, DatagramPacket receivePacket){
		this.getPacketHandler().handle(id, inputStream, receivePacket,null);
	}
	
	
	



	public void run() {
//		final InformationServer server=this;
        System.out.println("Server started!");
		while(running){
			tryToGetData();
		/*	try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
	}

	@Override
	public String getSide() {
		return "Server";
	}

	
	//To everybody
	@Override
	public void sendPacket(Packet p) {
		for(ServerSideClient c:clients){
			if(c == null)
				continue;
			{
			  DatagramPacket sendPacket =
	                  new DatagramPacket(p.getData(), p.getData().length, c.getAddress(), c.getPort());
	                  try {
						serverSocket.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			
			  DatagramPacket sendPacket =
	                  new DatagramPacket(p.getData(), p.getData().length, c.getAddress(), c.getPort());
	                  try {
						serverSocket.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
		}
	}
	
	//To c
	public void sendPacket(Packet p, InetAddress inet, int port) {
			  DatagramPacket sendPacket =
	                  new DatagramPacket(p.getData(), p.getData().length, inet, port);
	                  try {
						serverSocket.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
	}

	
	/*
	 * 
	        while(true)
	        {
	           Socket connectionSocket = serverSocket.accept();
	           BufferedReader inFromClient =
	              new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	           DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	           clientSentence = inFromClient.readLine();
	           System.out.println("Received: " + clientSentence);
	           capitalizedSentence = clientSentence.toUpperCase() + '\n';
	           outToClient.writeBytes(capitalizedSentence);
	        }
	 */
	
	



}
