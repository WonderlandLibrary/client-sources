package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;





































abstract class PeerInfo
{
  private final ByteBuffer handle;
  private Thread locking_thread;
  private int lock_count;
  
  protected PeerInfo(ByteBuffer handle)
  {
    this.handle = handle;
  }
  
  private void lockAndInitHandle() throws LWJGLException {
    doLockAndInitHandle();
  }
  
  public final synchronized void unlock() throws LWJGLException {
    if (lock_count <= 0)
      throw new IllegalStateException("PeerInfo not locked!");
    if (Thread.currentThread() != locking_thread)
      throw new IllegalStateException("PeerInfo already locked by " + locking_thread);
    lock_count -= 1;
    if (lock_count == 0) {
      doUnlock();
      locking_thread = null;
      notify();
    }
  }
  
  protected abstract void doLockAndInitHandle() throws LWJGLException;
  
  protected abstract void doUnlock() throws LWJGLException;
  
  public final synchronized ByteBuffer lockAndGetHandle() throws LWJGLException { Thread this_thread = Thread.currentThread();
    while ((locking_thread != null) && (locking_thread != this_thread)) {
      try {
        wait();
      } catch (InterruptedException e) {
        LWJGLUtil.log("Interrupted while waiting for PeerInfo lock: " + e);
      }
    }
    if (lock_count == 0) {
      locking_thread = this_thread;
      doLockAndInitHandle();
    }
    lock_count += 1;
    return getHandle();
  }
  
  protected final ByteBuffer getHandle() {
    return handle;
  }
  
  public void destroy() {}
}
