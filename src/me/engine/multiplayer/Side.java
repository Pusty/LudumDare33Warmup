package me.engine.multiplayer;

import me.engine.main.MainClass;

public abstract class  Side{
  public abstract String getSide();
  public abstract void sendPacket(Packet p);
  
}
