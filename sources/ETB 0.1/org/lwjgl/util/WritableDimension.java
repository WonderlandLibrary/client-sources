package org.lwjgl.util;

public abstract interface WritableDimension
{
  public abstract void setSize(int paramInt1, int paramInt2);
  
  public abstract void setSize(ReadableDimension paramReadableDimension);
  
  public abstract void setHeight(int paramInt);
  
  public abstract void setWidth(int paramInt);
}
