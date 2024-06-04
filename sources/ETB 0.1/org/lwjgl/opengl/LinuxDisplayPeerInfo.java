package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;




































final class LinuxDisplayPeerInfo
  extends LinuxPeerInfo
{
  final boolean egl;
  
  LinuxDisplayPeerInfo()
    throws LWJGLException
  {
    egl = true;
    org.lwjgl.opengles.GLContext.loadOpenGLLibrary();
  }
  
  LinuxDisplayPeerInfo(PixelFormat pixel_format) throws LWJGLException {
    egl = false;
    LinuxDisplay.lockAWT();
    try {
      GLContext.loadOpenGLLibrary();
      try {
        LinuxDisplay.incDisplay();
        try {
          initDefaultPeerInfo(LinuxDisplay.getDisplay(), LinuxDisplay.getDefaultScreen(), getHandle(), pixel_format);
        }
        catch (LWJGLException e) {
          throw e;
        }
      }
      catch (LWJGLException e) {
        throw e;
      }
    } finally {
      LinuxDisplay.unlockAWT();
    }
  }
  
  private static native void initDefaultPeerInfo(long paramLong, int paramInt, ByteBuffer paramByteBuffer, PixelFormat paramPixelFormat) throws LWJGLException;
  
  protected void doLockAndInitHandle() throws LWJGLException {
    
    try { initDrawable(LinuxDisplay.getWindow(), getHandle());
    } finally {
      LinuxDisplay.unlockAWT();
    }
  }
  
  private static native void initDrawable(long paramLong, ByteBuffer paramByteBuffer);
  
  protected void doUnlock() throws LWJGLException
  {}
  
  public void destroy() {
    super.destroy();
    
    if (egl) {
      org.lwjgl.opengles.GLContext.unloadOpenGLLibrary();
    } else {
      LinuxDisplay.lockAWT();
      LinuxDisplay.decDisplay();
      GLContext.unloadOpenGLLibrary();
      LinuxDisplay.unlockAWT();
    }
  }
}
