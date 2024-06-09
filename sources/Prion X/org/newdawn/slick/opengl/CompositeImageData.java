package org.newdawn.slick.opengl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.newdawn.slick.util.Log;







public class CompositeImageData
  implements LoadableImageData
{
  private ArrayList sources = new ArrayList();
  
  private LoadableImageData picked;
  

  public CompositeImageData() {}
  

  public void add(LoadableImageData data)
  {
    sources.add(data);
  }
  

  public ByteBuffer loadImage(InputStream fis)
    throws IOException
  {
    return loadImage(fis, false, null);
  }
  

  public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent)
    throws IOException
  {
    return loadImage(fis, flipped, false, transparent);
  }
  

  public ByteBuffer loadImage(InputStream is, boolean flipped, boolean forceAlpha, int[] transparent)
    throws IOException
  {
    CompositeIOException exception = new CompositeIOException();
    ByteBuffer buffer = null;
    
    BufferedInputStream in = new BufferedInputStream(is, is.available());
    in.mark(is.available());
    

    for (int i = 0; i < sources.size(); i++) {
      in.reset();
      try {
        LoadableImageData data = (LoadableImageData)sources.get(i);
        
        buffer = data.loadImage(in, flipped, forceAlpha, transparent);
        picked = data;
      }
      catch (Exception e) {
        Log.warn(sources.get(i).getClass() + " failed to read the data", e);
        exception.addException(e);
      }
    }
    
    if (picked == null) {
      throw exception;
    }
    
    return buffer;
  }
  



  private void checkPicked()
  {
    if (picked == null) {
      throw new RuntimeException("Attempt to make use of uninitialised or invalid composite image data");
    }
  }
  


  public int getDepth()
  {
    checkPicked();
    
    return picked.getDepth();
  }
  


  public int getHeight()
  {
    checkPicked();
    
    return picked.getHeight();
  }
  


  public ByteBuffer getImageBufferData()
  {
    checkPicked();
    
    return picked.getImageBufferData();
  }
  


  public int getTexHeight()
  {
    checkPicked();
    
    return picked.getTexHeight();
  }
  


  public int getTexWidth()
  {
    checkPicked();
    
    return picked.getTexWidth();
  }
  


  public int getWidth()
  {
    checkPicked();
    
    return picked.getWidth();
  }
  


  public void configureEdging(boolean edging)
  {
    for (int i = 0; i < sources.size(); i++) {
      ((LoadableImageData)sources.get(i)).configureEdging(edging);
    }
  }
}
