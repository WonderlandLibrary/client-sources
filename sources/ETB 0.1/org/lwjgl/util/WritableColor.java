package org.lwjgl.util;

import java.nio.ByteBuffer;

public abstract interface WritableColor
{
  public abstract void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void set(byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4);
  
  public abstract void set(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void set(byte paramByte1, byte paramByte2, byte paramByte3);
  
  public abstract void setRed(int paramInt);
  
  public abstract void setGreen(int paramInt);
  
  public abstract void setBlue(int paramInt);
  
  public abstract void setAlpha(int paramInt);
  
  public abstract void setRed(byte paramByte);
  
  public abstract void setGreen(byte paramByte);
  
  public abstract void setBlue(byte paramByte);
  
  public abstract void setAlpha(byte paramByte);
  
  public abstract void readRGBA(ByteBuffer paramByteBuffer);
  
  public abstract void readRGB(ByteBuffer paramByteBuffer);
  
  public abstract void readARGB(ByteBuffer paramByteBuffer);
  
  public abstract void readBGRA(ByteBuffer paramByteBuffer);
  
  public abstract void readBGR(ByteBuffer paramByteBuffer);
  
  public abstract void readABGR(ByteBuffer paramByteBuffer);
  
  public abstract void setColor(ReadableColor paramReadableColor);
}
