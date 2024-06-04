package org.lwjgl.opengl;

import java.awt.Canvas;
import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;





































final class LinuxAWTGLCanvasPeerInfo
  extends LinuxPeerInfo
{
  private final Canvas component;
  private final AWTSurfaceLock awt_surface = new AWTSurfaceLock();
  private int screen = -1;
  
  LinuxAWTGLCanvasPeerInfo(Canvas component) {
    this.component = component;
  }
  
  protected void doLockAndInitHandle() throws LWJGLException {
    ByteBuffer surface_handle = awt_surface.lockAndGetHandle(component);
    if (screen == -1) {
      try {
        screen = getScreenFromSurfaceInfo(surface_handle);
      } catch (LWJGLException e) {
        LWJGLUtil.log("Got exception while trying to determine screen: " + e);
        screen = 0;
      }
    }
    nInitHandle(screen, surface_handle, getHandle()); }
  
  private static native int getScreenFromSurfaceInfo(ByteBuffer paramByteBuffer) throws LWJGLException;
  
  private static native void nInitHandle(int paramInt, ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) throws LWJGLException;
  
  protected void doUnlock() throws LWJGLException { awt_surface.unlock(); }
}
