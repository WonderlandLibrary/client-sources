package org.newdawn.slick;

public abstract interface KeyListener
  extends ControlledInputReciever
{
  public abstract void keyPressed(int paramInt, char paramChar);
  
  public abstract void keyReleased(int paramInt, char paramChar);
}
