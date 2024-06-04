package org.lwjgl.util;

public abstract interface WritablePoint
{
  public abstract void setLocation(int paramInt1, int paramInt2);
  
  public abstract void setLocation(ReadablePoint paramReadablePoint);
  
  public abstract void setX(int paramInt);
  
  public abstract void setY(int paramInt);
}
