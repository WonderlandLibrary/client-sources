package org.lwjgl.util;

public abstract interface WritableRectangle
  extends WritablePoint, WritableDimension
{
  public abstract void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void setBounds(ReadablePoint paramReadablePoint, ReadableDimension paramReadableDimension);
  
  public abstract void setBounds(ReadableRectangle paramReadableRectangle);
}
