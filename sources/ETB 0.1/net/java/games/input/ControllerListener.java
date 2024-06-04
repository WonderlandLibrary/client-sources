package net.java.games.input;

public abstract interface ControllerListener
{
  public abstract void controllerRemoved(ControllerEvent paramControllerEvent);
  
  public abstract void controllerAdded(ControllerEvent paramControllerEvent);
}
