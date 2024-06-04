package org.lwjgl.opengl;

import java.nio.ByteBuffer;



































abstract class LinuxPeerInfo
  extends PeerInfo
{
  LinuxPeerInfo()
  {
    super(createHandle());
  }
  
  private static native ByteBuffer createHandle();
  
  public final long getDisplay() { return nGetDisplay(getHandle()); }
  
  private static native long nGetDisplay(ByteBuffer paramByteBuffer);
  
  public final long getDrawable() {
    return nGetDrawable(getHandle());
  }
  
  private static native long nGetDrawable(ByteBuffer paramByteBuffer);
}
