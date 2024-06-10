package org.newdawn.slick;

public abstract interface ControlledInputReciever
{
  public abstract void setInput(Input paramInput);
  
  public abstract boolean isAcceptingInput();
  
  public abstract void inputEnded();
  
  public abstract void inputStarted();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.ControlledInputReciever
 * JD-Core Version:    0.7.0.1
 */