package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.opengles.ContextAttribs;
import org.lwjgl.opengles.ContextCapabilities;
import org.lwjgl.opengles.EGL;
import org.lwjgl.opengles.EGLContext;
import org.lwjgl.opengles.EGLDisplay;
import org.lwjgl.opengles.EGLSurface;
import org.lwjgl.opengles.GLContext;
import org.lwjgl.opengles.GLES20;
import org.lwjgl.opengles.PowerManagementEventException;








































final class ContextGLES
  implements Context
{
  private static final ThreadLocal<ContextGLES> current_context_local = new ThreadLocal();
  
  private final DrawableGLES drawable;
  
  private final EGLContext eglContext;
  
  private final ContextAttribs contextAttribs;
  
  private boolean destroyed;
  
  private boolean destroy_requested;
  
  private Thread thread;
  

  static
  {
    Sys.initialize();
  }
  
  public EGLContext getEGLContext() {
    return eglContext;
  }
  
  ContextAttribs getContextAttribs() {
    return contextAttribs;
  }
  
  static ContextGLES getCurrentContext() {
    return (ContextGLES)current_context_local.get();
  }
  
  ContextGLES(DrawableGLES drawable, ContextAttribs attribs, ContextGLES shared_context) throws LWJGLException
  {
    if (drawable == null) {
      throw new IllegalArgumentException();
    }
    ContextGLES context_lock = shared_context != null ? shared_context : this;
    

    synchronized (context_lock) {
      if ((shared_context != null) && (destroyed)) {
        throw new IllegalArgumentException("Shared context is destroyed");
      }
      this.drawable = drawable;
      contextAttribs = attribs;
      eglContext = drawable.getEGLDisplay().createContext(drawable.getEGLConfig(), shared_context == null ? null : eglContext, attribs == null ? new ContextAttribs(2).getAttribList() : attribs.getAttribList());
    }
  }
  

  public void releaseCurrent()
    throws LWJGLException, PowerManagementEventException
  {
    EGL.eglReleaseCurrent(drawable.getEGLDisplay());
    GLContext.useContext(null);
    current_context_local.set(null);
    
    synchronized (this) {
      thread = null;
      checkDestroy();
    }
  }
  
  public static void swapBuffers() throws LWJGLException, PowerManagementEventException
  {
    ContextGLES current_context = getCurrentContext();
    if (current_context != null)
      drawable.getEGLSurface().swapBuffers();
  }
  
  private boolean canAccess() {
    return (thread == null) || (Thread.currentThread() == thread);
  }
  
  private void checkAccess() {
    if (!canAccess()) {
      throw new IllegalStateException("From thread " + Thread.currentThread() + ": " + thread + " already has the context current");
    }
  }
  
  public synchronized void makeCurrent() throws LWJGLException, PowerManagementEventException {
    checkAccess();
    if (destroyed)
      throw new IllegalStateException("Context is destroyed");
    thread = Thread.currentThread();
    current_context_local.set(this);
    eglContext.makeCurrent(drawable.getEGLSurface());
    GLContext.useContext(this);
  }
  
  public synchronized boolean isCurrent() throws LWJGLException
  {
    if (destroyed)
      throw new IllegalStateException("Context is destroyed");
    return EGL.eglIsCurrentContext(eglContext);
  }
  
  private void checkDestroy() {
    if ((!destroyed) && (destroy_requested)) {
      try {
        eglContext.destroy();
        destroyed = true;
        thread = null;
      } catch (LWJGLException e) {
        LWJGLUtil.log("Exception occurred while destroying context: " + e);
      }
    }
  }
  






  public static void setSwapInterval(int value)
  {
    ContextGLES current_context = getCurrentContext();
    if (current_context != null) {
      try {
        drawable.getEGLDisplay().setSwapInterval(value);
      } catch (LWJGLException e) {
        LWJGLUtil.log("Failed to set swap interval. Reason: " + e.getMessage());
      }
    }
  }
  



  public synchronized void forceDestroy()
    throws LWJGLException
  {
    checkAccess();
    destroy();
  }
  


  public synchronized void destroy()
    throws LWJGLException
  {
    if (destroyed)
      return;
    destroy_requested = true;
    boolean was_current = isCurrent();
    int error = 0;
    if (was_current) {
      if ((GLContext.getCapabilities() != null) && (getCapabilitiesOpenGLES20)) {
        error = GLES20.glGetError();
      }
      try {
        releaseCurrent();
      }
      catch (PowerManagementEventException e) {}
    }
    
    checkDestroy();
    if ((was_current) && (error != 0)) {
      throw new OpenGLException(error);
    }
  }
  
  public void releaseDrawable()
    throws LWJGLException
  {}
}
