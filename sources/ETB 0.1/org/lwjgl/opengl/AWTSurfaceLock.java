package org.lwjgl.opengl;

import java.awt.Canvas;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;










































final class AWTSurfaceLock
{
  private static final int WAIT_DELAY_MILLIS = 100;
  private final ByteBuffer lock_buffer;
  private boolean firstLockSucceeded;
  
  AWTSurfaceLock()
  {
    lock_buffer = createHandle();
  }
  
  private static native ByteBuffer createHandle();
  
  public ByteBuffer lockAndGetHandle(Canvas component) throws LWJGLException {
    while (!privilegedLockAndInitHandle(component)) {
      LWJGLUtil.log("Could not get drawing surface info, retrying...");
      try {
        Thread.sleep(100L);
      } catch (InterruptedException e) {
        LWJGLUtil.log("Interrupted while retrying: " + e);
      }
    }
    
    return lock_buffer;
  }
  




  private boolean privilegedLockAndInitHandle(final Canvas component)
    throws LWJGLException
  {
    if (firstLockSucceeded) {
      return lockAndInitHandle(lock_buffer, component);
    }
    try {
      firstLockSucceeded = ((Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction() {
        public Boolean run() throws LWJGLException {
          return Boolean.valueOf(AWTSurfaceLock.lockAndInitHandle(lock_buffer, component));
        }
      })).booleanValue();
      return firstLockSucceeded;
    } catch (PrivilegedActionException e) {
      throw ((LWJGLException)e.getException());
    }
  }
  
  private static native boolean lockAndInitHandle(ByteBuffer paramByteBuffer, Canvas paramCanvas) throws LWJGLException;
  
  void unlock() throws LWJGLException {
    nUnlock(lock_buffer);
  }
  
  private static native void nUnlock(ByteBuffer paramByteBuffer)
    throws LWJGLException;
}
