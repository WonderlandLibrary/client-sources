package org.newdawn.slick;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.ImageData;






















public class ImageBuffer
  implements ImageData
{
  private int width;
  private int height;
  private int texWidth;
  private int texHeight;
  private byte[] rawData;
  
  public ImageBuffer(int width, int height)
  {
    this.width = width;
    this.height = height;
    
    texWidth = get2Fold(width);
    texHeight = get2Fold(height);
    
    rawData = new byte[texWidth * texHeight * 4];
  }
  




  public byte[] getRGBA()
  {
    return rawData;
  }
  


  public int getDepth()
  {
    return 32;
  }
  


  public int getHeight()
  {
    return height;
  }
  


  public int getTexHeight()
  {
    return texHeight;
  }
  


  public int getTexWidth()
  {
    return texWidth;
  }
  


  public int getWidth()
  {
    return width;
  }
  


  public ByteBuffer getImageBufferData()
  {
    ByteBuffer scratch = BufferUtils.createByteBuffer(rawData.length);
    scratch.put(rawData);
    scratch.flip();
    
    return scratch;
  }
  









  public void setRGBA(int x, int y, int r, int g, int b, int a)
  {
    if ((x < 0) || (x >= width) || (y < 0) || (y >= height)) {
      throw new RuntimeException("Specified location: " + x + "," + y + " outside of image");
    }
    
    int ofs = (x + y * texWidth) * 4;
    
    if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
      rawData[ofs] = ((byte)b);
      rawData[(ofs + 1)] = ((byte)g);
      rawData[(ofs + 2)] = ((byte)r);
      rawData[(ofs + 3)] = ((byte)a);
    } else {
      rawData[ofs] = ((byte)r);
      rawData[(ofs + 1)] = ((byte)g);
      rawData[(ofs + 2)] = ((byte)b);
      rawData[(ofs + 3)] = ((byte)a);
    }
  }
  




  public Image getImage()
  {
    return new Image(this);
  }
  





  public Image getImage(int filter)
  {
    return new Image(this, filter);
  }
  





  private int get2Fold(int fold)
  {
    int ret = 2;
    while (ret < fold) {
      ret *= 2;
    }
    return ret;
  }
}
