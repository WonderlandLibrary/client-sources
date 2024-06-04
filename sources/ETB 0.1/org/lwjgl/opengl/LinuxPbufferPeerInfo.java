package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;



































final class LinuxPbufferPeerInfo
  extends LinuxPeerInfo
{
  LinuxPbufferPeerInfo(int width, int height, PixelFormat pixel_format)
    throws LWJGLException
  {
    LinuxDisplay.lockAWT();
    try {
      GLContext.loadOpenGLLibrary();
      try {
        LinuxDisplay.incDisplay();
        try {
          nInitHandle(LinuxDisplay.getDisplay(), LinuxDisplay.getDefaultScreen(), getHandle(), width, height, pixel_format);
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
  
  private static native void nInitHandle(long paramLong, int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3, PixelFormat paramPixelFormat) throws LWJGLException;
  
  public void destroy() { LinuxDisplay.lockAWT();
    nDestroy(getHandle());
    LinuxDisplay.decDisplay();
    GLContext.unloadOpenGLLibrary();
    LinuxDisplay.unlockAWT();
  }
  
  private static native void nDestroy(ByteBuffer paramByteBuffer);
  
  protected void doLockAndInitHandle()
    throws LWJGLException
  {}
  
  protected void doUnlock()
    throws LWJGLException
  {}
}
