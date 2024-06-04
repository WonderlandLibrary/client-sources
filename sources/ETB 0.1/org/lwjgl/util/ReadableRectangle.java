package org.lwjgl.util;

public abstract interface ReadableRectangle
  extends ReadableDimension, ReadablePoint
{
  public abstract void getBounds(WritableRectangle paramWritableRectangle);
}
