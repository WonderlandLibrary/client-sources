package org.lwjgl.util.glu;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;




















































public class MipMap
  extends Util
{
  public MipMap() {}
  
  public static int gluBuild2DMipmaps(int target, int components, int width, int height, int format, int type, ByteBuffer data)
  {
    if ((width < 1) || (height < 1)) { return 100901;
    }
    int bpp = bytesPerPixel(format, type);
    if (bpp == 0) {
      return 100900;
    }
    int maxSize = glGetIntegerv(3379);
    
    int w = nearestPower(width);
    if (w > maxSize) {
      w = maxSize;
    }
    int h = nearestPower(height);
    if (h > maxSize) {
      h = maxSize;
    }
    
    PixelStoreState pss = new PixelStoreState();
    

    GL11.glPixelStorei(3330, 0);
    GL11.glPixelStorei(3333, 1);
    GL11.glPixelStorei(3331, 0);
    GL11.glPixelStorei(3332, 0);
    

    int retVal = 0;
    boolean done = false;
    ByteBuffer image;
    if ((w != width) || (h != height))
    {
      ByteBuffer image = BufferUtils.createByteBuffer((w + 4) * h * bpp);
      int error = gluScaleImage(format, width, height, type, data, w, h, type, image);
      if (error != 0) {
        retVal = error;
        done = true;
      }
      

      GL11.glPixelStorei(3314, 0);
      GL11.glPixelStorei(3317, 1);
      GL11.glPixelStorei(3315, 0);
      GL11.glPixelStorei(3316, 0);
    } else {
      image = data;
    }
    
    ByteBuffer bufferA = null;
    ByteBuffer bufferB = null;
    
    int level = 0;
    while (!done) {
      if (image != data)
      {
        GL11.glPixelStorei(3314, 0);
        GL11.glPixelStorei(3317, 1);
        GL11.glPixelStorei(3315, 0);
        GL11.glPixelStorei(3316, 0);
      }
      
      GL11.glTexImage2D(target, level, components, w, h, 0, format, type, image);
      
      if ((w == 1) && (h == 1)) {
        break;
      }
      int newW = w < 2 ? 1 : w >> 1;
      int newH = h < 2 ? 1 : h >> 1;
      
      ByteBuffer newImage;
      ByteBuffer newImage;
      if (bufferA == null) {
        newImage = bufferA = BufferUtils.createByteBuffer((newW + 4) * newH * bpp); } else { ByteBuffer newImage;
        if (bufferB == null) {
          newImage = bufferB = BufferUtils.createByteBuffer((newW + 4) * newH * bpp);
        } else
          newImage = bufferB;
      }
      int error = gluScaleImage(format, w, h, type, image, newW, newH, type, newImage);
      if (error != 0) {
        retVal = error;
        done = true;
      }
      
      image = newImage;
      if (bufferB != null) {
        bufferB = bufferA;
      }
      w = newW;
      h = newH;
      level++;
    }
    

    pss.save();
    
    return retVal;
  }
  















  public static int gluScaleImage(int format, int widthIn, int heightIn, int typein, ByteBuffer dataIn, int widthOut, int heightOut, int typeOut, ByteBuffer dataOut)
  {
    int components = compPerPix(format);
    if (components == -1) {
      return 100900;
    }
    






    float[] tempIn = new float[widthIn * heightIn * components];
    float[] tempOut = new float[widthOut * heightOut * components];
    
    int sizein;
    switch (typein) {
    case 5121: 
      sizein = 1;
      break;
    case 5126: 
      sizein = 4;
      break;
    default: 
      return 1280;
    }
    
    int sizeout;
    switch (typeOut) {
    case 5121: 
      sizeout = 1;
      break;
    case 5126: 
      sizeout = 4;
      break;
    default: 
      return 1280;
    }
    
    
    PixelStoreState pss = new PixelStoreState();
    int rowlen;
    int rowlen;
    if (unpackRowLength > 0) {
      rowlen = unpackRowLength;
    } else
      rowlen = widthIn;
    int rowstride;
    int rowstride; if (sizein >= unpackAlignment) {
      rowstride = components * rowlen;
    } else
      rowstride = unpackAlignment / sizein * ceil(components * rowlen * sizein, unpackAlignment);
    int k;
    int i; switch (typein) {
    case 5121: 
      k = 0;
      dataIn.rewind();
      for (i = 0; i < heightIn;) {
        int ubptr = i * rowstride + unpackSkipRows * rowstride + unpackSkipPixels * components;
        for (j = 0; j < widthIn * components; j++) {
          tempIn[(k++)] = (dataIn.get(ubptr++) & 0xFF);
        }
        i++; continue;
        






        k = 0;
        dataIn.rewind();
        for (i = 0; i < heightIn;)
        {
          int fptr = 4 * (i * rowstride + unpackSkipRows * rowstride + unpackSkipPixels * components);
          for (j = 0; j < widthIn * components; j++)
          {
            tempIn[(k++)] = dataIn.getFloat(fptr);
            fptr += 4;
          }
          i++; continue;
          









          return 100900;
        }
      } }
    int j;
    float sx = widthIn / widthOut;
    float sy = heightIn / heightOut;
    
    float[] c = new float[components];
    

    for (int iy = 0; iy < heightOut; iy++) {
      for (int ix = 0; ix < widthOut; ix++) {
        int x0 = (int)(ix * sx);
        int x1 = (int)((ix + 1) * sx);
        int y0 = (int)(iy * sy);
        int y1 = (int)((iy + 1) * sy);
        
        int readPix = 0;
        

        for (int ic = 0; ic < components; ic++) {
          c[ic] = 0.0F;
        }
        

        for (int ix0 = x0; ix0 < x1; ix0++) {
          for (int iy0 = y0; iy0 < y1; iy0++)
          {
            int src = (iy0 * widthIn + ix0) * components;
            
            for (int ic = 0; ic < components; ic++) {
              c[ic] += tempIn[(src + ic)];
            }
            
            readPix++;
          }
        }
        

        int dst = (iy * widthOut + ix) * components;
        
        if (readPix == 0)
        {
          int src = (y0 * widthIn + x0) * components;
          for (int ic = 0; ic < components; ic++) {
            tempOut[(dst++)] = tempIn[(src + ic)];
          }
        }
        else {
          for (k = 0; k < components; k++) {
            tempOut[(dst++)] = (c[k] / readPix);
          }
        }
      }
    }
    


    if (packRowLength > 0) {
      rowlen = packRowLength;
    } else {
      rowlen = widthOut;
    }
    if (sizeout >= packAlignment) {
      rowstride = components * rowlen;
    } else {
      rowstride = packAlignment / sizeout * ceil(components * rowlen * sizeout, packAlignment);
    }
    switch (typeOut) {
    case 5121: 
      k = 0;
      for (i = 0; i < heightOut;) {
        int ubptr = i * rowstride + packSkipRows * rowstride + packSkipPixels * components;
        
        for (j = 0; j < widthOut * components; j++) {
          dataOut.put(ubptr++, (byte)(int)tempOut[(k++)]);
        }
        i++; continue;
        







        k = 0;
        for (i = 0; i < heightOut;) {
          int fptr = 4 * (i * rowstride + unpackSkipRows * rowstride + unpackSkipPixels * components);
          
          for (j = 0; j < widthOut * components; j++) {
            dataOut.putFloat(fptr, tempOut[(k++)]);
            fptr += 4;
          }
          i++; continue;
          








          return 100900;
        }
      } }
    return 0;
  }
}
