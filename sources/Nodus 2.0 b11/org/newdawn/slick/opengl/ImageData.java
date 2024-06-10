package org.newdawn.slick.opengl;

import java.nio.ByteBuffer;

public abstract interface ImageData
{
  public abstract int getDepth();
  
  public abstract int getWidth();
  
  public abstract int getHeight();
  
  public abstract int getTexWidth();
  
  public abstract int getTexHeight();
  
  public abstract ByteBuffer getImageBufferData();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.opengl.ImageData
 * JD-Core Version:    0.7.0.1
 */