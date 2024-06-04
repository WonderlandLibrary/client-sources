package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;





































































































































public final class Pbuffer
  extends DrawableGL
{
  public static final int PBUFFER_SUPPORTED = 1;
  public static final int RENDER_TEXTURE_SUPPORTED = 2;
  public static final int RENDER_TEXTURE_RECTANGLE_SUPPORTED = 4;
  public static final int RENDER_DEPTH_TEXTURE_SUPPORTED = 8;
  public static final int MIPMAP_LEVEL = 8315;
  public static final int CUBE_MAP_FACE = 8316;
  public static final int TEXTURE_CUBE_MAP_POSITIVE_X = 8317;
  public static final int TEXTURE_CUBE_MAP_NEGATIVE_X = 8318;
  public static final int TEXTURE_CUBE_MAP_POSITIVE_Y = 8319;
  public static final int TEXTURE_CUBE_MAP_NEGATIVE_Y = 8320;
  public static final int TEXTURE_CUBE_MAP_POSITIVE_Z = 8321;
  public static final int TEXTURE_CUBE_MAP_NEGATIVE_Z = 8322;
  public static final int FRONT_LEFT_BUFFER = 8323;
  public static final int FRONT_RIGHT_BUFFER = 8324;
  public static final int BACK_LEFT_BUFFER = 8325;
  public static final int BACK_RIGHT_BUFFER = 8326;
  public static final int DEPTH_BUFFER = 8359;
  private final int width;
  private final int height;
  
  public Pbuffer(int width, int height, PixelFormat pixel_format, Drawable shared_drawable)
    throws LWJGLException
  {
    this(width, height, pixel_format, null, shared_drawable);
  }
  


















  public Pbuffer(int width, int height, PixelFormat pixel_format, RenderTexture renderTexture, Drawable shared_drawable)
    throws LWJGLException
  {
    this(width, height, pixel_format, renderTexture, shared_drawable, null);
  }
  



















  public Pbuffer(int width, int height, PixelFormat pixel_format, RenderTexture renderTexture, Drawable shared_drawable, ContextAttribs attribs)
    throws LWJGLException
  {
    if (pixel_format == null)
      throw new NullPointerException("Pixel format must be non-null");
    this.width = width;
    this.height = height;
    peer_info = createPbuffer(width, height, pixel_format, attribs, renderTexture);
    Context shared_context = null;
    if (shared_drawable == null)
      shared_drawable = Display.getDrawable();
    if (shared_drawable != null)
      shared_context = ((DrawableLWJGL)shared_drawable).getContext();
    context = new ContextGL(peer_info, attribs, (ContextGL)shared_context);
  }
  
  private static PeerInfo createPbuffer(int width, int height, PixelFormat pixel_format, ContextAttribs attribs, RenderTexture renderTexture) throws LWJGLException {
    if (renderTexture == null)
    {


      IntBuffer defaultAttribs = BufferUtils.createIntBuffer(1);
      return Display.getImplementation().createPbuffer(width, height, pixel_format, attribs, null, defaultAttribs);
    }
    return Display.getImplementation().createPbuffer(width, height, pixel_format, attribs, pixelFormatCaps, pBufferAttribs);
  }
  








  public synchronized boolean isBufferLost()
  {
    checkDestroyed();
    return Display.getImplementation().isBufferLost(peer_info);
  }
  




  public static int getCapabilities()
  {
    return Display.getImplementation().getPbufferCapabilities();
  }
  















  public synchronized void setAttrib(int attrib, int value)
  {
    checkDestroyed();
    Display.getImplementation().setPbufferAttrib(peer_info, attrib, value);
  }
  






  public synchronized void bindTexImage(int buffer)
  {
    checkDestroyed();
    Display.getImplementation().bindTexImageToPbuffer(peer_info, buffer);
  }
  




  public synchronized void releaseTexImage(int buffer)
  {
    checkDestroyed();
    Display.getImplementation().releaseTexImageFromPbuffer(peer_info, buffer);
  }
  


  public synchronized int getHeight()
  {
    checkDestroyed();
    return height;
  }
  


  public synchronized int getWidth()
  {
    checkDestroyed();
    return width;
  }
  
  static {}
}
