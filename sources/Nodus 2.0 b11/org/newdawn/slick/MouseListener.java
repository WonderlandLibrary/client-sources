package org.newdawn.slick;

public abstract interface MouseListener
  extends ControlledInputReciever
{
  public abstract void mouseWheelMoved(int paramInt);
  
  public abstract void mouseClicked(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void mousePressed(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void mouseReleased(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void mouseMoved(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void mouseDragged(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.MouseListener
 * JD-Core Version:    0.7.0.1
 */