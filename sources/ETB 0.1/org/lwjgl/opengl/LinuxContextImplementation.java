package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.LWJGLException;

final class LinuxContextImplementation
  implements ContextImplementation
{
  LinuxContextImplementation() {}
  
  /* Error */
  public ByteBuffer create(PeerInfo peer_info, IntBuffer attribs, ByteBuffer shared_context_handle)
    throws LWJGLException
  {
    // Byte code:
    //   0: invokestatic 22	org/lwjgl/opengl/LinuxDisplay:lockAWT	()V
    //   3: aload_1
    //   4: invokevirtual 28	org/lwjgl/opengl/PeerInfo:lockAndGetHandle	()Ljava/nio/ByteBuffer;
    //   7: astore 4
    //   9: aload 4
    //   11: aload_2
    //   12: aload_3
    //   13: invokestatic 32	org/lwjgl/opengl/LinuxContextImplementation:nCreate	(Ljava/nio/ByteBuffer;Ljava/nio/IntBuffer;Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
    //   16: astore 5
    //   18: aload_1
    //   19: invokevirtual 35	org/lwjgl/opengl/PeerInfo:unlock	()V
    //   22: invokestatic 38	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   25: aload 5
    //   27: areturn
    //   28: astore 6
    //   30: aload_1
    //   31: invokevirtual 35	org/lwjgl/opengl/PeerInfo:unlock	()V
    //   34: aload 6
    //   36: athrow
    //   37: astore 7
    //   39: invokestatic 38	org/lwjgl/opengl/LinuxDisplay:unlockAWT	()V
    //   42: aload 7
    //   44: athrow
    // Line number table:
    //   Java source line #47	-> byte code offset #0
    //   Java source line #49	-> byte code offset #3
    //   Java source line #51	-> byte code offset #9
    //   Java source line #53	-> byte code offset #18
    //   Java source line #56	-> byte code offset #22
    //   Java source line #53	-> byte code offset #28
    //   Java source line #56	-> byte code offset #37
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	45	0	this	LinuxContextImplementation
    //   0	45	1	peer_info	PeerInfo
    //   0	45	2	attribs	IntBuffer
    //   0	45	3	shared_context_handle	ByteBuffer
    //   7	3	4	peer_handle	ByteBuffer
    //   16	10	5	localByteBuffer1	ByteBuffer
    //   28	7	6	localObject1	Object
    //   37	6	7	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   9	18	28	finally
    //   28	30	28	finally
    //   3	22	37	finally
    //   28	39	37	finally
  }
  
  private static native ByteBuffer nCreate(ByteBuffer paramByteBuffer1, IntBuffer paramIntBuffer, ByteBuffer paramByteBuffer2)
    throws LWJGLException;
  
  native long getGLXContext(ByteBuffer paramByteBuffer);
  
  native long getDisplay(ByteBuffer paramByteBuffer);
  
  public void releaseDrawable(ByteBuffer context_handle)
    throws LWJGLException
  {}
  
  public void swapBuffers()
    throws LWJGLException
  {
    ContextGL current_context = ContextGL.getCurrentContext();
    if (current_context == null)
      throw new IllegalStateException("No context is current");
    synchronized (current_context) {
      PeerInfo current_peer_info = current_context.getPeerInfo();
      LinuxDisplay.lockAWT();
      try {
        ByteBuffer peer_handle = current_peer_info.lockAndGetHandle();
        try {
          nSwapBuffers(peer_handle);
        } finally {
          current_peer_info.unlock();
        }
      } finally {
        LinuxDisplay.unlockAWT();
      }
    }
  }
  
  private static native void nSwapBuffers(ByteBuffer paramByteBuffer) throws LWJGLException;
  
  public void releaseCurrentContext() throws LWJGLException {
    ContextGL current_context = ContextGL.getCurrentContext();
    if (current_context == null)
      throw new IllegalStateException("No context is current");
    synchronized (current_context) {
      PeerInfo current_peer_info = current_context.getPeerInfo();
      LinuxDisplay.lockAWT();
      try {
        ByteBuffer peer_handle = current_peer_info.lockAndGetHandle();
        try {
          nReleaseCurrentContext(peer_handle);
        } finally {
          current_peer_info.unlock();
        }
      } finally {
        LinuxDisplay.unlockAWT();
      }
    }
  }
  
  private static native void nReleaseCurrentContext(ByteBuffer paramByteBuffer) throws LWJGLException;
  
  public void update(ByteBuffer context_handle) {}
  
  public void makeCurrent(PeerInfo peer_info, ByteBuffer handle) throws LWJGLException
  {
    
    try {
      ByteBuffer peer_handle = peer_info.lockAndGetHandle();
      try {
        nMakeCurrent(peer_handle, handle);
      } finally {
        peer_info.unlock();
      }
    } finally {
      LinuxDisplay.unlockAWT();
    }
  }
  
  private static native void nMakeCurrent(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) throws LWJGLException;
  
  public boolean isCurrent(ByteBuffer handle) throws LWJGLException {
    
    try {
      boolean result = nIsCurrent(handle);
      return result;
    } finally {
      LinuxDisplay.unlockAWT();
    }
  }
  
  private static native boolean nIsCurrent(ByteBuffer paramByteBuffer) throws LWJGLException;
  
  public void setSwapInterval(int value) {
    ContextGL current_context = ContextGL.getCurrentContext();
    PeerInfo peer_info = current_context.getPeerInfo();
    
    if (current_context == null)
      throw new IllegalStateException("No context is current");
    synchronized (current_context) {
      LinuxDisplay.lockAWT();
      try {
        ByteBuffer peer_handle = peer_info.lockAndGetHandle();
        try {
          nSetSwapInterval(peer_handle, current_context.getHandle(), value);
        } finally {
          peer_info.unlock();
        }
      }
      catch (LWJGLException e) {
        e.printStackTrace();
      } finally {
        LinuxDisplay.unlockAWT();
      }
    }
  }
  
  private static native void nSetSwapInterval(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2, int paramInt);
  
  public void destroy(PeerInfo peer_info, ByteBuffer handle) throws LWJGLException {
    
    try {
      ByteBuffer peer_handle = peer_info.lockAndGetHandle();
      try {
        nDestroy(peer_handle, handle);
      } finally {
        peer_info.unlock();
      }
    } finally {
      LinuxDisplay.unlockAWT();
    }
  }
  
  private static native void nDestroy(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2)
    throws LWJGLException;
}
