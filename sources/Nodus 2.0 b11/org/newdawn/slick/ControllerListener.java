package org.newdawn.slick;

public abstract interface ControllerListener
  extends ControlledInputReciever
{
  public abstract void controllerLeftPressed(int paramInt);
  
  public abstract void controllerLeftReleased(int paramInt);
  
  public abstract void controllerRightPressed(int paramInt);
  
  public abstract void controllerRightReleased(int paramInt);
  
  public abstract void controllerUpPressed(int paramInt);
  
  public abstract void controllerUpReleased(int paramInt);
  
  public abstract void controllerDownPressed(int paramInt);
  
  public abstract void controllerDownReleased(int paramInt);
  
  public abstract void controllerButtonPressed(int paramInt1, int paramInt2);
  
  public abstract void controllerButtonReleased(int paramInt1, int paramInt2);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.ControllerListener
 * JD-Core Version:    0.7.0.1
 */