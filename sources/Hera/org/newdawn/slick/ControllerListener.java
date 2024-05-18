package org.newdawn.slick;

public interface ControllerListener extends ControlledInputReciever {
  void controllerLeftPressed(int paramInt);
  
  void controllerLeftReleased(int paramInt);
  
  void controllerRightPressed(int paramInt);
  
  void controllerRightReleased(int paramInt);
  
  void controllerUpPressed(int paramInt);
  
  void controllerUpReleased(int paramInt);
  
  void controllerDownPressed(int paramInt);
  
  void controllerDownReleased(int paramInt);
  
  void controllerButtonPressed(int paramInt1, int paramInt2);
  
  void controllerButtonReleased(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\ControllerListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */