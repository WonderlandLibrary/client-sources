package org.newdawn.slick.geom;

import java.io.Serializable;

public abstract interface Triangulator
  extends Serializable
{
  public abstract int getTriangleCount();
  
  public abstract float[] getTrianglePoint(int paramInt1, int paramInt2);
  
  public abstract void addPolyPoint(float paramFloat1, float paramFloat2);
  
  public abstract void startHole();
  
  public abstract boolean triangulate();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Triangulator
 * JD-Core Version:    0.7.0.1
 */