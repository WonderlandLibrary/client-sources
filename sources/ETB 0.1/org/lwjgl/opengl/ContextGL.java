package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.PointerBuffer;
import org.lwjgl.Sys;












































final class ContextGL
  implements Context
{
  private Thread thread;
  private boolean destroy_requested;
  private boolean destroyed;
  private final boolean forwardCompatible;
  private final ContextAttribs contextAttribs;
  private final PeerInfo peer_info;
  private final ByteBuffer handle;
  private static final ThreadLocal<ContextGL> current_context_local = new ThreadLocal();
  
















  static { Sys.initialize(); }
  private static final ContextImplementation implementation = createImplementation();
  
  private static ContextImplementation createImplementation()
  {
    switch () {
    case 1: 
      return new LinuxContextImplementation();
    case 3: 
      return new WindowsContextImplementation();
    case 2: 
      return new MacOSXContextImplementation();
    }
    throw new IllegalStateException("Unsupported platform");
  }
  
  PeerInfo getPeerInfo()
  {
    return peer_info;
  }
  
  ContextAttribs getContextAttribs() {
    return contextAttribs;
  }
  
  static ContextGL getCurrentContext() {
    return (ContextGL)current_context_local.get();
  }
  
  ContextGL(PeerInfo peer_info, ContextAttribs attribs, ContextGL shared_context) throws LWJGLException
  {
    ContextGL context_lock = shared_context != null ? shared_context : this;
    

    synchronized (context_lock) {
      if ((shared_context != null) && (destroyed))
        throw new IllegalArgumentException("Shared context is destroyed");
      GLContext.loadOpenGLLibrary();
      try {
        this.peer_info = peer_info;
        contextAttribs = attribs;
        
        IntBuffer attribList;
        if (attribs != null) {
          IntBuffer attribList = attribs.getAttribList();
          forwardCompatible = attribs.isForwardCompatible();
        } else {
          attribList = null;
          forwardCompatible = false;
        }
        
        handle = implementation.create(peer_info, attribList, shared_context != null ? handle : null);
      } catch (LWJGLException e) {
        GLContext.unloadOpenGLLibrary();
        throw e;
      }
    }
  }
  
  public void releaseCurrent() throws LWJGLException
  {
    ContextGL current_context = getCurrentContext();
    if (current_context != null) {
      implementation.releaseCurrentContext();
      GLContext.useContext(null);
      current_context_local.set(null);
      synchronized (current_context) {
        thread = null;
        current_context.checkDestroy();
      }
    }
  }
  




  public synchronized void releaseDrawable()
    throws LWJGLException
  {
    if (destroyed)
      throw new IllegalStateException("Context is destroyed");
    implementation.releaseDrawable(getHandle());
  }
  
  public synchronized void update()
  {
    if (destroyed)
      throw new IllegalStateException("Context is destroyed");
    implementation.update(getHandle());
  }
  
  public static void swapBuffers() throws LWJGLException
  {
    implementation.swapBuffers();
  }
  
  private boolean canAccess() {
    return (thread == null) || (Thread.currentThread() == thread);
  }
  
  private void checkAccess() {
    if (!canAccess()) {
      throw new IllegalStateException("From thread " + Thread.currentThread() + ": " + thread + " already has the context current");
    }
  }
  
  public synchronized void makeCurrent() throws LWJGLException {
    checkAccess();
    if (destroyed)
      throw new IllegalStateException("Context is destroyed");
    thread = Thread.currentThread();
    current_context_local.set(this);
    implementation.makeCurrent(peer_info, handle);
    GLContext.useContext(this, forwardCompatible);
  }
  
  ByteBuffer getHandle() {
    return handle;
  }
  
  public synchronized boolean isCurrent() throws LWJGLException
  {
    if (destroyed)
      throw new IllegalStateException("Context is destroyed");
    return implementation.isCurrent(handle);
  }
  
  private void checkDestroy() {
    if ((!destroyed) && (destroy_requested)) {
      try {
        releaseDrawable();
        implementation.destroy(peer_info, handle);
        CallbackUtil.unregisterCallbacks(this);
        destroyed = true;
        thread = null;
        GLContext.unloadOpenGLLibrary();
      } catch (LWJGLException e) {
        LWJGLUtil.log("Exception occurred while destroying context: " + e);
      }
    }
  }
  






  public static void setSwapInterval(int value)
  {
    implementation.setSwapInterval(value);
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
    if (was_current)
    {
      try {
        error = GL11.glGetError();
      }
      catch (Exception e) {}
      
      releaseCurrent();
    }
    checkDestroy();
    if ((was_current) && (error != 0))
      throw new OpenGLException(error);
  }
  
  public synchronized void setCLSharingProperties(PointerBuffer properties) throws LWJGLException {
    ByteBuffer peer_handle = peer_info.lockAndGetHandle();
    try {
      switch (LWJGLUtil.getPlatform()) {
      case 3: 
        WindowsContextImplementation implWindows = (WindowsContextImplementation)implementation;
        properties.put(8200L).put(implWindows.getHGLRC(handle));
        properties.put(8203L).put(implWindows.getHDC(peer_handle));
        break;
      case 1: 
        LinuxContextImplementation implLinux = (LinuxContextImplementation)implementation;
        properties.put(8200L).put(implLinux.getGLXContext(handle));
        properties.put(8202L).put(implLinux.getDisplay(peer_handle));
        break;
      case 2: 
        if (LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 6))
        {
          MacOSXContextImplementation implMacOSX = (MacOSXContextImplementation)implementation;
          long CGLShareGroup = implMacOSX.getCGLShareGroup(handle);
          properties.put(268435456L).put(CGLShareGroup); }
        break;
      }
      
      throw new UnsupportedOperationException("CL/GL context sharing is not supported on this platform.");
    }
    finally {
      peer_info.unlock();
    }
  }
}
