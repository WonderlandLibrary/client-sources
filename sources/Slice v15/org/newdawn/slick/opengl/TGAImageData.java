package org.newdawn.slick.opengl;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.lwjgl.BufferUtils;
























public class TGAImageData
  implements LoadableImageData
{
  private int texWidth;
  private int texHeight;
  private int width;
  private int height;
  private short pixelDepth;
  
  public TGAImageData() {}
  
  private short flipEndian(short signedShort)
  {
    int input = signedShort & 0xFFFF;
    return (short)(input << 8 | (input & 0xFF00) >>> 8);
  }
  


  public int getDepth()
  {
    return pixelDepth;
  }
  


  public int getWidth()
  {
    return width;
  }
  


  public int getHeight()
  {
    return height;
  }
  


  public int getTexWidth()
  {
    return texWidth;
  }
  


  public int getTexHeight()
  {
    return texHeight;
  }
  

  public ByteBuffer loadImage(InputStream fis)
    throws IOException
  {
    return loadImage(fis, true, null);
  }
  

  public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent)
    throws IOException
  {
    return loadImage(fis, flipped, false, transparent);
  }
  

  public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent)
    throws IOException
  {
    if (transparent != null) {
      forceAlpha = true;
    }
    byte red = 0;
    byte green = 0;
    byte blue = 0;
    byte alpha = 0;
    
    BufferedInputStream bis = new BufferedInputStream(fis, 100000);
    DataInputStream dis = new DataInputStream(bis);
    

    short idLength = (short)dis.read();
    short colorMapType = (short)dis.read();
    short imageType = (short)dis.read();
    short cMapStart = flipEndian(dis.readShort());
    short cMapLength = flipEndian(dis.readShort());
    short cMapDepth = (short)dis.read();
    short xOffset = flipEndian(dis.readShort());
    short yOffset = flipEndian(dis.readShort());
    
    if (imageType != 2) {
      throw new IOException("Slick only supports uncompressed RGB(A) TGA images");
    }
    
    width = flipEndian(dis.readShort());
    height = flipEndian(dis.readShort());
    pixelDepth = ((short)dis.read());
    if (pixelDepth == 32) {
      forceAlpha = false;
    }
    
    texWidth = get2Fold(width);
    texHeight = get2Fold(height);
    
    short imageDescriptor = (short)dis.read();
    if ((imageDescriptor & 0x20) == 0) {
      flipped = !flipped;
    }
    

    if (idLength > 0) {
      bis.skip(idLength);
    }
    
    byte[] rawData = null;
    if ((pixelDepth == 32) || (forceAlpha)) {
      pixelDepth = 32;
      rawData = new byte[texWidth * texHeight * 4];
    } else if (pixelDepth == 24) {
      rawData = new byte[texWidth * texHeight * 3];
    } else {
      throw new RuntimeException("Only 24 and 32 bit TGAs are supported");
    }
    
    if (pixelDepth == 24) {
      if (flipped) {
        for (int i = height - 1; i >= 0; i--) {
          for (int j = 0; j < width; j++) {
            blue = dis.readByte();
            green = dis.readByte();
            red = dis.readByte();
            
            int ofs = (j + i * texWidth) * 3;
            rawData[ofs] = red;
            rawData[(ofs + 1)] = green;
            rawData[(ofs + 2)] = blue;
          }
        }
      } else {
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            blue = dis.readByte();
            green = dis.readByte();
            red = dis.readByte();
            
            int ofs = (j + i * texWidth) * 3;
            rawData[ofs] = red;
            rawData[(ofs + 1)] = green;
            rawData[(ofs + 2)] = blue;
          }
        }
      }
    } else if (pixelDepth == 32) {
      if (flipped) {
        for (int i = height - 1; i >= 0; i--) {
          for (int j = 0; j < width; j++) {
            blue = dis.readByte();
            green = dis.readByte();
            red = dis.readByte();
            if (forceAlpha) {
              alpha = -1;
            } else {
              alpha = dis.readByte();
            }
            
            int ofs = (j + i * texWidth) * 4;
            
            rawData[ofs] = red;
            rawData[(ofs + 1)] = green;
            rawData[(ofs + 2)] = blue;
            rawData[(ofs + 3)] = alpha;
            
            if (alpha == 0) {
              rawData[(ofs + 2)] = 0;
              rawData[(ofs + 1)] = 0;
              rawData[ofs] = 0;
            }
          }
        }
      } else {
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            blue = dis.readByte();
            green = dis.readByte();
            red = dis.readByte();
            if (forceAlpha) {
              alpha = -1;
            } else {
              alpha = dis.readByte();
            }
            
            int ofs = (j + i * texWidth) * 4;
            
            if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
              rawData[ofs] = red;
              rawData[(ofs + 1)] = green;
              rawData[(ofs + 2)] = blue;
              rawData[(ofs + 3)] = alpha;
            } else {
              rawData[ofs] = red;
              rawData[(ofs + 1)] = green;
              rawData[(ofs + 2)] = blue;
              rawData[(ofs + 3)] = alpha;
            }
            
            if (alpha == 0) {
              rawData[(ofs + 2)] = 0;
              rawData[(ofs + 1)] = 0;
              rawData[ofs] = 0;
            }
          }
        }
      }
    }
    fis.close();
    
    if (transparent != null) {
      for (int i = 0; i < rawData.length; i += 4) {
        boolean match = true;
        for (int c = 0; c < 3; c++) {
          if (rawData[(i + c)] != transparent[c]) {
            match = false;
          }
        }
        
        if (match) {
          rawData[(i + 3)] = 0;
        }
      }
    }
    

    ByteBuffer scratch = BufferUtils.createByteBuffer(rawData.length);
    scratch.put(rawData);
    
    int perPixel = pixelDepth / 8;
    if (height < texHeight - 1) {
      int topOffset = (texHeight - 1) * (texWidth * perPixel);
      int bottomOffset = (height - 1) * (texWidth * perPixel);
      for (int x = 0; x < texWidth * perPixel; x++) {
        scratch.put(topOffset + x, scratch.get(x));
        scratch.put(bottomOffset + texWidth * perPixel + x, scratch.get(texWidth * perPixel + x));
      }
    }
    if (width < texWidth - 1) {
      for (int y = 0; y < texHeight; y++) {
        for (int i = 0; i < perPixel; i++) {
          scratch.put((y + 1) * (texWidth * perPixel) - perPixel + i, scratch.get(y * (texWidth * perPixel) + i));
          scratch.put(y * (texWidth * perPixel) + width * perPixel + i, scratch.get(y * (texWidth * perPixel) + (width - 1) * perPixel + i));
        }
      }
    }
    
    scratch.flip();
    
    return scratch;
  }
  





  private int get2Fold(int fold)
  {
    int ret = 2;
    while (ret < fold) {
      ret *= 2;
    }
    return ret;
  }
  


  public ByteBuffer getImageBufferData()
  {
    throw new RuntimeException("TGAImageData doesn't store it's image.");
  }
  
  public void configureEdging(boolean edging) {}
}
