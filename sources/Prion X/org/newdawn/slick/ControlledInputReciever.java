package org.newdawn.slick;

public abstract interface ControlledInputReciever
{
  public abstract void setInput(Input paramInput);
  
  public abstract boolean isAcceptingInput();
  
  public abstract void inputEnded();
  
  public abstract void inputStarted();
}
