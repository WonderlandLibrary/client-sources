package org.newdawn.slick.opengl;

import java.nio.ByteBuffer;

public interface ImageData {
  int getDepth();
  
  int getWidth();
  
  int getHeight();
  
  int getTexWidth();
  
  int getTexHeight();
  
  ByteBuffer getImageBufferData();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\opengl\ImageData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */