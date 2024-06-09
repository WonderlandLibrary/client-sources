package org.newdawn.slick.opengl;

import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;




















public class DeferredTexture
  extends TextureImpl
  implements DeferredResource
{
  private InputStream in;
  private String resourceName;
  private boolean flipped;
  private int filter;
  private TextureImpl target;
  private int[] trans;
  
  public DeferredTexture(InputStream in, String resourceName, boolean flipped, int filter, int[] trans)
  {
    this.in = in;
    this.resourceName = resourceName;
    this.flipped = flipped;
    this.filter = filter;
    this.trans = trans;
    
    LoadingList.get().add(this);
  }
  

  public void load()
    throws IOException
  {
    boolean before = InternalTextureLoader.get().isDeferredLoading();
    InternalTextureLoader.get().setDeferredLoading(false);
    target = InternalTextureLoader.get().getTexture(in, resourceName, flipped, filter, trans);
    InternalTextureLoader.get().setDeferredLoading(before);
  }
  


  private void checkTarget()
  {
    if (target == null) {
      try {
        load();
        LoadingList.get().remove(this);
        return;
      } catch (IOException e) {
        throw new RuntimeException("Attempt to use deferred texture before loading and resource not found: " + resourceName);
      }
    }
  }
  


  public void bind()
  {
    checkTarget();
    
    target.bind();
  }
  


  public float getHeight()
  {
    checkTarget();
    
    return target.getHeight();
  }
  


  public int getImageHeight()
  {
    checkTarget();
    return target.getImageHeight();
  }
  


  public int getImageWidth()
  {
    checkTarget();
    return target.getImageWidth();
  }
  


  public int getTextureHeight()
  {
    checkTarget();
    return target.getTextureHeight();
  }
  


  public int getTextureID()
  {
    checkTarget();
    return target.getTextureID();
  }
  


  public String getTextureRef()
  {
    checkTarget();
    return target.getTextureRef();
  }
  


  public int getTextureWidth()
  {
    checkTarget();
    return target.getTextureWidth();
  }
  


  public float getWidth()
  {
    checkTarget();
    return target.getWidth();
  }
  


  public void release()
  {
    checkTarget();
    target.release();
  }
  


  public void setAlpha(boolean alpha)
  {
    checkTarget();
    target.setAlpha(alpha);
  }
  


  public void setHeight(int height)
  {
    checkTarget();
    target.setHeight(height);
  }
  


  public void setTextureHeight(int texHeight)
  {
    checkTarget();
    target.setTextureHeight(texHeight);
  }
  


  public void setTextureID(int textureID)
  {
    checkTarget();
    target.setTextureID(textureID);
  }
  


  public void setTextureWidth(int texWidth)
  {
    checkTarget();
    target.setTextureWidth(texWidth);
  }
  


  public void setWidth(int width)
  {
    checkTarget();
    target.setWidth(width);
  }
  


  public byte[] getTextureData()
  {
    checkTarget();
    return target.getTextureData();
  }
  


  public String getDescription()
  {
    return resourceName;
  }
  


  public boolean hasAlpha()
  {
    checkTarget();
    return target.hasAlpha();
  }
  


  public void setTextureFilter(int textureFilter)
  {
    checkTarget();
    target.setTextureFilter(textureFilter);
  }
}
