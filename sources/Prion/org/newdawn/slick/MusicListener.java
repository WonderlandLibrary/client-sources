package org.newdawn.slick;

public abstract interface MusicListener
{
  public abstract void musicEnded(Music paramMusic);
  
  public abstract void musicSwapped(Music paramMusic1, Music paramMusic2);
}
