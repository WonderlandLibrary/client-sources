package org.newdawn.slick.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;














public class TextureImpl
  implements Texture
{
  protected static SGL GL = ;
  
  static Texture lastBind;
  private int target;
  private int textureID;
  private int height;
  private int width;
  private int texWidth;
  
  public static Texture getLastBind()
  {
    return lastBind;
  }
  




  private int texHeight;
  



  private float widthRatio;
  


  private float heightRatio;
  


  private boolean alpha;
  


  private String ref;
  


  private String cacheName;
  


  private ReloadData reloadData;
  


  protected TextureImpl() {}
  



  public TextureImpl(String ref, int target, int textureID)
  {
    this.target = target;
    this.ref = ref;
    this.textureID = textureID;
    lastBind = this;
  }
  




  public void setCacheName(String cacheName)
  {
    this.cacheName = cacheName;
  }
  


  public boolean hasAlpha()
  {
    return alpha;
  }
  


  public String getTextureRef()
  {
    return ref;
  }
  




  public void setAlpha(boolean alpha)
  {
    this.alpha = alpha;
  }
  


  public static void bindNone()
  {
    lastBind = null;
    GL.glDisable(3553);
  }
  




  public static void unbind()
  {
    lastBind = null;
  }
  


  public void bind()
  {
    if (lastBind != this) {
      lastBind = this;
      GL.glEnable(3553);
      GL.glBindTexture(target, textureID);
    }
  }
  




  public void setHeight(int height)
  {
    this.height = height;
    setHeight();
  }
  




  public void setWidth(int width)
  {
    this.width = width;
    setWidth();
  }
  


  public int getImageHeight()
  {
    return height;
  }
  


  public int getImageWidth()
  {
    return width;
  }
  


  public float getHeight()
  {
    return heightRatio;
  }
  


  public float getWidth()
  {
    return widthRatio;
  }
  


  public int getTextureHeight()
  {
    return texHeight;
  }
  


  public int getTextureWidth()
  {
    return texWidth;
  }
  




  public void setTextureHeight(int texHeight)
  {
    this.texHeight = texHeight;
    setHeight();
  }
  




  public void setTextureWidth(int texWidth)
  {
    this.texWidth = texWidth;
    setWidth();
  }
  



  private void setHeight()
  {
    if (texHeight != 0) {
      heightRatio = (height / texHeight);
    }
  }
  



  private void setWidth()
  {
    if (texWidth != 0) {
      widthRatio = (width / texWidth);
    }
  }
  


  public void release()
  {
    IntBuffer texBuf = createIntBuffer(1);
    texBuf.put(textureID);
    texBuf.flip();
    
    GL.glDeleteTextures(texBuf);
    
    if (lastBind == this) {
      bindNone();
    }
    
    if (cacheName != null) {
      InternalTextureLoader.get().clear(cacheName);
    } else {
      InternalTextureLoader.get().clear(ref);
    }
  }
  


  public int getTextureID()
  {
    return textureID;
  }
  




  public void setTextureID(int textureID)
  {
    this.textureID = textureID;
  }
  






  protected IntBuffer createIntBuffer(int size)
  {
    ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
    temp.order(ByteOrder.nativeOrder());
    
    return temp.asIntBuffer();
  }
  


  public byte[] getTextureData()
  {
    ByteBuffer buffer = BufferUtils.createByteBuffer((hasAlpha() ? 4 : 3) * texWidth * texHeight);
    bind();
    GL.glGetTexImage(3553, 0, hasAlpha() ? 6408 : 6407, 5121, 
      buffer);
    byte[] data = new byte[buffer.limit()];
    buffer.get(data);
    buffer.clear();
    
    return data;
  }
  


  public void setTextureFilter(int textureFilter)
  {
    bind();
    GL.glTexParameteri(target, 10241, textureFilter);
    GL.glTexParameteri(target, 10240, textureFilter);
  }
  









  public void setTextureData(int srcPixelFormat, int componentCount, int minFilter, int magFilter, ByteBuffer textureBuffer)
  {
    reloadData = new ReloadData(null);
    reloadData.srcPixelFormat = srcPixelFormat;
    reloadData.componentCount = componentCount;
    reloadData.minFilter = minFilter;
    reloadData.magFilter = magFilter;
    reloadData.textureBuffer = textureBuffer;
  }
  


  public void reload()
  {
    if (reloadData != null) {
      textureID = reloadData.reload();
    }
  }
  


  private class ReloadData
  {
    private int srcPixelFormat;
    

    private int componentCount;
    
    private int minFilter;
    
    private int magFilter;
    
    private ByteBuffer textureBuffer;
    

    private ReloadData() {}
    

    public int reload()
    {
      Log.error("Reloading texture: " + ref);
      return InternalTextureLoader.get().reload(TextureImpl.this, srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
    }
  }
}
