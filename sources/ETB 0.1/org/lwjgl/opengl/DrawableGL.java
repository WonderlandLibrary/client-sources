package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.PointerBuffer;






































abstract class DrawableGL
  implements DrawableLWJGL
{
  protected PixelFormat pixel_format;
  protected PeerInfo peer_info;
  protected ContextGL context;
  
  protected DrawableGL() {}
  
  public void setPixelFormat(PixelFormatLWJGL pf)
    throws LWJGLException
  {
    throw new UnsupportedOperationException();
  }
  
  public void setPixelFormat(PixelFormatLWJGL pf, ContextAttribs attribs) throws LWJGLException {
    pixel_format = ((PixelFormat)pf);
    peer_info = Display.getImplementation().createPeerInfo(pixel_format, attribs);
  }
  
  public PixelFormatLWJGL getPixelFormat() {
    return pixel_format;
  }
  
  public ContextGL getContext() {
    synchronized (GlobalLock.lock) {
      return context;
    }
  }
  
  public ContextGL createSharedContext() throws LWJGLException {
    synchronized (GlobalLock.lock) {
      checkDestroyed();
      return new ContextGL(peer_info, context.getContextAttribs(), context);
    }
  }
  

  public void checkGLError() {}
  
  public void setSwapInterval(int swap_interval)
  {
    ContextGL.setSwapInterval(swap_interval);
  }
  
  public void swapBuffers()
    throws LWJGLException
  {}
  
  public void initContext(float r, float g, float b)
  {
    GL11.glClearColor(r, g, b, 0.0F);
    
    GL11.glClear(16384);
  }
  
  public boolean isCurrent() throws LWJGLException {
    synchronized (GlobalLock.lock) {
      checkDestroyed();
      return context.isCurrent();
    }
  }
  
  public void makeCurrent() throws LWJGLException {
    synchronized (GlobalLock.lock) {
      checkDestroyed();
      context.makeCurrent();
    }
  }
  
  public void releaseContext() throws LWJGLException {
    synchronized (GlobalLock.lock) {
      checkDestroyed();
      if (context.isCurrent())
        context.releaseCurrent();
    }
  }
  
  public void destroy() {
    synchronized (GlobalLock.lock) {
      if (context == null) {
        return;
      }
      try {
        releaseContext();
        
        context.forceDestroy();
        context = null;
        
        if (peer_info != null) {
          peer_info.destroy();
          peer_info = null;
        }
      } catch (LWJGLException e) {
        LWJGLUtil.log("Exception occurred while destroying Drawable: " + e);
      }
    }
  }
  
  public void setCLSharingProperties(PointerBuffer properties) throws LWJGLException {
    synchronized (GlobalLock.lock) {
      checkDestroyed();
      context.setCLSharingProperties(properties);
    }
  }
  
  protected final void checkDestroyed() {
    if (context == null) {
      throw new IllegalStateException("The Drawable has no context available.");
    }
  }
}
