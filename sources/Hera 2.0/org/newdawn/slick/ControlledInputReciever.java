package org.newdawn.slick;

public interface ControlledInputReciever {
  void setInput(Input paramInput);
  
  boolean isAcceptingInput();
  
  void inputEnded();
  
  void inputStarted();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\ControlledInputReciever.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */