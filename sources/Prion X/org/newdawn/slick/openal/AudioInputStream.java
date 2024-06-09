package org.newdawn.slick.openal;

import java.io.IOException;

abstract interface AudioInputStream
{
  public abstract int getChannels();
  
  public abstract int getRate();
  
  public abstract int read()
    throws IOException;
  
  public abstract int read(byte[] paramArrayOfByte)
    throws IOException;
  
  public abstract int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract boolean atEnd();
  
  public abstract void close()
    throws IOException;
}
