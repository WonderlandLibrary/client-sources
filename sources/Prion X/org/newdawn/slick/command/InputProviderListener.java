package org.newdawn.slick.command;

public abstract interface InputProviderListener
{
  public abstract void controlPressed(Command paramCommand);
  
  public abstract void controlReleased(Command paramCommand);
}
