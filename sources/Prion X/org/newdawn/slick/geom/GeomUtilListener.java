package org.newdawn.slick.geom;

public abstract interface GeomUtilListener
{
  public abstract void pointExcluded(float paramFloat1, float paramFloat2);
  
  public abstract void pointIntersected(float paramFloat1, float paramFloat2);
  
  public abstract void pointUsed(float paramFloat1, float paramFloat2);
}
