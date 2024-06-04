package org.lwjgl.opengl;

import java.awt.Canvas;
import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;






































final class WindowsAWTGLCanvasPeerInfo
  extends WindowsPeerInfo
{
  private final Canvas component;
  private final AWTSurfaceLock awt_surface = new AWTSurfaceLock();
  private final PixelFormat pixel_format;
  private boolean has_pixel_format;
  
  WindowsAWTGLCanvasPeerInfo(Canvas component, PixelFormat pixel_format) {
    this.component = component;
    this.pixel_format = pixel_format;
  }
  
  protected void doLockAndInitHandle() throws LWJGLException {
    nInitHandle(awt_surface.lockAndGetHandle(component), getHandle());
    if ((!has_pixel_format) && (pixel_format != null))
    {
      int format = choosePixelFormat(getHdc(), component.getX(), component.getY(), pixel_format, null, true, true, false, true);
      setPixelFormat(getHdc(), format);
      has_pixel_format = true;
    }
  }
  
  private static native void nInitHandle(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) throws LWJGLException;
  
  protected void doUnlock() throws LWJGLException { awt_surface.unlock(); }
}
