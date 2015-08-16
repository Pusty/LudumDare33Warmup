package me.engine.multiplayer;


public abstract class  Side{
  public abstract String getSide();
  public abstract void sendPacket(Packet p);
  
}
