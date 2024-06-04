package org.lwjgl.util;

public abstract interface ReadableDimension
{
  public abstract int getWidth();
  
  public abstract int getHeight();
  
  public abstract void getSize(WritableDimension paramWritableDimension);
}
