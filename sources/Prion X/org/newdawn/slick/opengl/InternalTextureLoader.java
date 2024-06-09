package org.newdawn.slick.opengl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;









public class InternalTextureLoader
{
  protected static SGL GL = ;
  
  private static final InternalTextureLoader loader = new InternalTextureLoader();
  




  public static InternalTextureLoader get()
  {
    return loader;
  }
  

  private HashMap texturesLinear = new HashMap();
  
  private HashMap texturesNearest = new HashMap();
  
  private int dstPixelFormat = 6408;
  


  private boolean deferred;
  


  private boolean holdTextureData;
  



  private InternalTextureLoader() {}
  


  public void setHoldTextureData(boolean holdTextureData)
  {
    this.holdTextureData = holdTextureData;
  }
  





  public void setDeferredLoading(boolean deferred)
  {
    this.deferred = deferred;
  }
  




  public boolean isDeferredLoading()
  {
    return deferred;
  }
  




  public void clear(String name)
  {
    texturesLinear.remove(name);
    texturesNearest.remove(name);
  }
  


  public void clear()
  {
    texturesLinear.clear();
    texturesNearest.clear();
  }
  


  public void set16BitMode()
  {
    dstPixelFormat = 32859;
  }
  





  public static int createTextureID()
  {
    IntBuffer tmp = createIntBuffer(1);
    GL.glGenTextures(tmp);
    return tmp.get(0);
  }
  







  public Texture getTexture(File source, boolean flipped, int filter)
    throws IOException
  {
    String resourceName = source.getAbsolutePath();
    InputStream in = new FileInputStream(source);
    
    return getTexture(in, resourceName, flipped, filter, null);
  }
  








  public Texture getTexture(File source, boolean flipped, int filter, int[] transparent)
    throws IOException
  {
    String resourceName = source.getAbsolutePath();
    InputStream in = new FileInputStream(source);
    
    return getTexture(in, resourceName, flipped, filter, transparent);
  }
  







  public Texture getTexture(String resourceName, boolean flipped, int filter)
    throws IOException
  {
    InputStream in = ResourceLoader.getResourceAsStream(resourceName);
    
    return getTexture(in, resourceName, flipped, filter, null);
  }
  








  public Texture getTexture(String resourceName, boolean flipped, int filter, int[] transparent)
    throws IOException
  {
    InputStream in = ResourceLoader.getResourceAsStream(resourceName);
    
    return getTexture(in, resourceName, flipped, filter, transparent);
  }
  







  public Texture getTexture(InputStream in, String resourceName, boolean flipped, int filter)
    throws IOException
  {
    return getTexture(in, resourceName, flipped, filter, null);
  }
  









  public TextureImpl getTexture(InputStream in, String resourceName, boolean flipped, int filter, int[] transparent)
    throws IOException
  {
    if (deferred) {
      return new DeferredTexture(in, resourceName, flipped, filter, transparent);
    }
    
    HashMap hash = texturesLinear;
    if (filter == 9728) {
      hash = texturesNearest;
    }
    
    String resName = resourceName;
    if (transparent != null) {
      resName = resName + ":" + transparent[0] + ":" + transparent[1] + ":" + transparent[2];
    }
    resName = resName + ":" + flipped;
    
    if (holdTextureData) {
      TextureImpl tex = (TextureImpl)hash.get(resName);
      if (tex != null) {
        return tex;
      }
    } else {
      SoftReference ref = (SoftReference)hash.get(resName);
      if (ref != null) {
        TextureImpl tex = (TextureImpl)ref.get();
        if (tex != null) {
          return tex;
        }
        hash.remove(resName);
      }
    }
    

    try
    {
      GL.glGetError();
    } catch (NullPointerException e) {
      throw new RuntimeException("Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
    }
    
    TextureImpl tex = getTexture(in, resourceName, 
      3553, 
      filter, 
      filter, flipped, transparent);
    
    tex.setCacheName(resName);
    if (holdTextureData) {
      hash.put(resName, tex);
    } else {
      hash.put(resName, new SoftReference(tex));
    }
    
    return tex;
  }
  



















  private TextureImpl getTexture(InputStream in, String resourceName, int target, int magFilter, int minFilter, boolean flipped, int[] transparent)
    throws IOException
  {
    LoadableImageData imageData = ImageDataFactory.getImageDataFor(resourceName);
    ByteBuffer textureBuffer = imageData.loadImage(new BufferedInputStream(in), flipped, transparent);
    
    int textureID = createTextureID();
    TextureImpl texture = new TextureImpl(resourceName, target, textureID);
    
    GL.glBindTexture(target, textureID);
    







    int width = imageData.getWidth();
    int height = imageData.getHeight();
    boolean hasAlpha = imageData.getDepth() == 32;
    
    texture.setTextureWidth(imageData.getTexWidth());
    texture.setTextureHeight(imageData.getTexHeight());
    
    int texWidth = texture.getTextureWidth();
    int texHeight = texture.getTextureHeight();
    
    IntBuffer temp = BufferUtils.createIntBuffer(16);
    GL.glGetInteger(3379, temp);
    int max = temp.get(0);
    if ((texWidth > max) || (texHeight > max)) {
      throw new IOException("Attempt to allocate a texture to big for the current hardware");
    }
    
    int srcPixelFormat = hasAlpha ? 6408 : 6407;
    int componentCount = hasAlpha ? 4 : 3;
    
    texture.setWidth(width);
    texture.setHeight(height);
    texture.setAlpha(hasAlpha);
    
    if (holdTextureData) {
      texture.setTextureData(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
    }
    
    GL.glTexParameteri(target, 10241, minFilter);
    GL.glTexParameteri(target, 10240, magFilter);
    

    GL.glTexImage2D(target, 
      0, 
      dstPixelFormat, 
      get2Fold(width), 
      get2Fold(height), 
      0, 
      srcPixelFormat, 
      5121, 
      textureBuffer);
    
    return texture;
  }
  






  public Texture createTexture(int width, int height)
    throws IOException
  {
    return createTexture(width, height, 9728);
  }
  






  public Texture createTexture(int width, int height, int filter)
    throws IOException
  {
    ImageData ds = new EmptyImageData(width, height);
    
    return getTexture(ds, filter);
  }
  







  public Texture getTexture(ImageData dataSource, int filter)
    throws IOException
  {
    int target = 3553;
    

    ByteBuffer textureBuffer = dataSource.getImageBufferData();
    

    int textureID = createTextureID();
    TextureImpl texture = new TextureImpl("generated:" + dataSource, target, textureID);
    
    int minFilter = filter;
    int magFilter = filter;
    boolean flipped = false;
    

    GL.glBindTexture(target, textureID);
    







    int width = dataSource.getWidth();
    int height = dataSource.getHeight();
    boolean hasAlpha = dataSource.getDepth() == 32;
    
    texture.setTextureWidth(dataSource.getTexWidth());
    texture.setTextureHeight(dataSource.getTexHeight());
    
    int texWidth = texture.getTextureWidth();
    int texHeight = texture.getTextureHeight();
    
    int srcPixelFormat = hasAlpha ? 6408 : 6407;
    int componentCount = hasAlpha ? 4 : 3;
    
    texture.setWidth(width);
    texture.setHeight(height);
    texture.setAlpha(hasAlpha);
    
    IntBuffer temp = BufferUtils.createIntBuffer(16);
    GL.glGetInteger(3379, temp);
    int max = temp.get(0);
    if ((texWidth > max) || (texHeight > max)) {
      throw new IOException("Attempt to allocate a texture to big for the current hardware");
    }
    
    if (holdTextureData) {
      texture.setTextureData(srcPixelFormat, componentCount, minFilter, magFilter, textureBuffer);
    }
    
    GL.glTexParameteri(target, 10241, minFilter);
    GL.glTexParameteri(target, 10240, magFilter);
    

    GL.glTexImage2D(target, 
      0, 
      dstPixelFormat, 
      get2Fold(width), 
      get2Fold(height), 
      0, 
      srcPixelFormat, 
      5121, 
      textureBuffer);
    
    return texture;
  }
  





  public static int get2Fold(int fold)
  {
    int ret = 2;
    while (ret < fold) {
      ret *= 2;
    }
    return ret;
  }
  






  public static IntBuffer createIntBuffer(int size)
  {
    ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
    temp.order(ByteOrder.nativeOrder());
    
    return temp.asIntBuffer();
  }
  


  public void reload()
  {
    Iterator texs = texturesLinear.values().iterator();
    while (texs.hasNext()) {
      ((TextureImpl)texs.next()).reload();
    }
    texs = texturesNearest.values().iterator();
    while (texs.hasNext()) {
      ((TextureImpl)texs.next()).reload();
    }
  }
  











  public int reload(TextureImpl texture, int srcPixelFormat, int componentCount, int minFilter, int magFilter, ByteBuffer textureBuffer)
  {
    int target = 3553;
    int textureID = createTextureID();
    GL.glBindTexture(target, textureID);
    
    GL.glTexParameteri(target, 10241, minFilter);
    GL.glTexParameteri(target, 10240, magFilter);
    

    GL.glTexImage2D(target, 
      0, 
      dstPixelFormat, 
      texture.getTextureWidth(), 
      texture.getTextureHeight(), 
      0, 
      srcPixelFormat, 
      5121, 
      textureBuffer);
    
    return textureID;
  }
}
