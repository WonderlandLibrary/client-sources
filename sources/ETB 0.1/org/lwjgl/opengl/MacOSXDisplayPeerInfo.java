package org.lwjgl.opengl;

import java.awt.Canvas;
import org.lwjgl.LWJGLException;



































final class MacOSXDisplayPeerInfo
  extends MacOSXCanvasPeerInfo
{
  private boolean locked;
  
  MacOSXDisplayPeerInfo(PixelFormat pixel_format, ContextAttribs attribs, boolean support_pbuffer)
    throws LWJGLException
  {
    super(pixel_format, attribs, support_pbuffer);
  }
  
  protected void doLockAndInitHandle() throws LWJGLException {
    if (locked)
      throw new RuntimeException("Already locked");
    Canvas canvas = ((MacOSXDisplay)Display.getImplementation()).getCanvas();
    if (canvas != null) {
      initHandle(canvas);
      locked = true;
    }
  }
  
  protected void doUnlock() throws LWJGLException {
    if (locked) {
      super.doUnlock();
      locked = false;
    }
  }
}
