package org.lwjgl.opengl;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengles.ContextAttribs;
import org.lwjgl.opengles.EGL;
import org.lwjgl.opengles.EGLConfig;
import org.lwjgl.opengles.EGLContext;
import org.lwjgl.opengles.EGLDisplay;
import org.lwjgl.opengles.EGLSurface;
import org.lwjgl.opengles.GLES20;
import org.lwjgl.opengles.PixelFormat;
import org.lwjgl.opengles.PowerManagementEventException;
import org.lwjgl.opengles.Util;




































abstract class DrawableGLES
  implements DrawableLWJGL
{
  protected PixelFormat pixel_format;
  protected EGLDisplay eglDisplay;
  protected EGLConfig eglConfig;
  protected EGLSurface eglSurface;
  protected ContextGLES context;
  protected Drawable shared_drawable;
  
  protected DrawableGLES() {}
  
  public void setPixelFormat(PixelFormatLWJGL pf)
    throws LWJGLException
  {
    synchronized (GlobalLock.lock) {
      pixel_format = ((PixelFormat)pf);
    }
  }
  
  public PixelFormatLWJGL getPixelFormat() {
    synchronized (GlobalLock.lock) {
      return pixel_format;
    }
  }
  
  public void initialize(long window, long display_id, int eglSurfaceType, PixelFormat pf) throws LWJGLException {
    synchronized (GlobalLock.lock) {
      if (this.eglSurface != null) {
        this.eglSurface.destroy();
        this.eglSurface = null;
      }
      
      if (this.eglDisplay != null) {
        this.eglDisplay.terminate();
        this.eglDisplay = null;
      }
      
      EGLDisplay eglDisplay = EGL.eglGetDisplay((int)display_id);
      
      int[] attribs = { 12329, 0, 12352, 4, 12333, 0 };
      




      EGLConfig[] configs = eglDisplay.chooseConfig(pf.getAttribBuffer(eglDisplay, eglSurfaceType, attribs), null, BufferUtils.createIntBuffer(1));
      if (configs.length == 0) {
        throw new LWJGLException("No EGLConfigs found for the specified PixelFormat.");
      }
      EGLConfig eglConfig = pf.getBestMatch(configs);
      EGLSurface eglSurface = eglDisplay.createWindowSurface(eglConfig, window, null);
      pf.setSurfaceAttribs(eglSurface);
      
      this.eglDisplay = eglDisplay;
      this.eglConfig = eglConfig;
      this.eglSurface = eglSurface;
      

      if (context != null)
        context.getEGLContext().setDisplay(eglDisplay);
    }
  }
  
  public void createContext(ContextAttribs attribs, Drawable shared_drawable) throws LWJGLException {
    synchronized (GlobalLock.lock) {
      context = new ContextGLES(this, attribs, shared_drawable != null ? ((DrawableGLES)shared_drawable).getContext() : null);
      this.shared_drawable = shared_drawable;
    }
  }
  
  Drawable getSharedDrawable() {
    synchronized (GlobalLock.lock) {
      return shared_drawable;
    }
  }
  
  public EGLDisplay getEGLDisplay() {
    synchronized (GlobalLock.lock) {
      return eglDisplay;
    }
  }
  
  public EGLConfig getEGLConfig() {
    synchronized (GlobalLock.lock) {
      return eglConfig;
    }
  }
  
  public EGLSurface getEGLSurface() {
    synchronized (GlobalLock.lock) {
      return eglSurface;
    }
  }
  
  public ContextGLES getContext() {
    synchronized (GlobalLock.lock) {
      return context;
    }
  }
  
  public Context createSharedContext() throws LWJGLException {
    synchronized (GlobalLock.lock) {
      checkDestroyed();
      return new ContextGLES(this, context.getContextAttribs(), context);
    }
  }
  

  public void checkGLError() {}
  
  public void setSwapInterval(int swap_interval)
  {
    ContextGLES.setSwapInterval(swap_interval);
  }
  
  public void swapBuffers()
    throws LWJGLException
  {}
  
  public void initContext(float r, float g, float b)
  {
    GLES20.glClearColor(r, g, b, 0.0F);
    
    GLES20.glClear(16384);
  }
  
  public boolean isCurrent() throws LWJGLException {
    synchronized (GlobalLock.lock) {
      checkDestroyed();
      return context.isCurrent();
    }
  }
  
  public void makeCurrent() throws LWJGLException, PowerManagementEventException {
    synchronized (GlobalLock.lock) {
      checkDestroyed();
      context.makeCurrent();
    }
  }
  
  public void releaseContext() throws LWJGLException, PowerManagementEventException {
    synchronized (GlobalLock.lock) {
      checkDestroyed();
      if (context.isCurrent())
        context.releaseCurrent();
    }
  }
  
  public void destroy() {
    synchronized (GlobalLock.lock) {
      try {
        if (context != null) {
          try {
            releaseContext();
          }
          catch (PowerManagementEventException e) {}
          

          context.forceDestroy();
          context = null;
        }
        
        if (eglSurface != null) {
          eglSurface.destroy();
          eglSurface = null;
        }
        
        if (eglDisplay != null) {
          eglDisplay.terminate();
          eglDisplay = null;
        }
        
        pixel_format = null;
        shared_drawable = null;
      } catch (LWJGLException e) {
        LWJGLUtil.log("Exception occurred while destroying Drawable: " + e);
      }
    }
  }
  
  protected void checkDestroyed() {
    if (context == null)
      throw new IllegalStateException("The Drawable has no context available.");
  }
  
  public void setCLSharingProperties(PointerBuffer properties) throws LWJGLException {
    throw new UnsupportedOperationException();
  }
}
