package org.newdawn.slick.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;












public class EmptyImageData
  implements ImageData
{
  private int width;
  private int height;
  
  public EmptyImageData(int width, int height)
  {
    this.width = width;
    this.height = height;
  }
  


  public int getDepth()
  {
    return 32;
  }
  


  public int getHeight()
  {
    return height;
  }
  


  public ByteBuffer getImageBufferData()
  {
    return BufferUtils.createByteBuffer(getTexWidth() * getTexHeight() * 4);
  }
  


  public int getTexHeight()
  {
    return InternalTextureLoader.get2Fold(height);
  }
  


  public int getTexWidth()
  {
    return InternalTextureLoader.get2Fold(width);
  }
  


  public int getWidth()
  {
    return width;
  }
}
