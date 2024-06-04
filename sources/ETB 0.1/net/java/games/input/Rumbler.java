package net.java.games.input;

public abstract interface Rumbler
{
  public abstract void rumble(float paramFloat);
  
  public abstract String getAxisName();
  
  public abstract Component.Identifier getAxisIdentifier();
}
