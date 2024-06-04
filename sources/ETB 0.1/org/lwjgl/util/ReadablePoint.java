package org.lwjgl.util;

public abstract interface ReadablePoint
{
  public abstract int getX();
  
  public abstract int getY();
  
  public abstract void getLocation(WritablePoint paramWritablePoint);
}
