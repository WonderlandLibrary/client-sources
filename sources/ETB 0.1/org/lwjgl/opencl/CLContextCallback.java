package org.lwjgl.opencl;

import java.nio.ByteBuffer;
import org.lwjgl.PointerWrapperAbstract;




































public abstract class CLContextCallback
  extends PointerWrapperAbstract
{
  private final boolean custom;
  
  protected CLContextCallback()
  {
    super(CallbackUtil.getContextCallback());
    custom = false;
  }
  




  protected CLContextCallback(long pointer)
  {
    super(pointer);
    
    if (pointer == 0L) {
      throw new RuntimeException("Invalid callback function pointer specified.");
    }
    custom = true;
  }
  
  final boolean isCustom() {
    return custom;
  }
  
  protected abstract void handleMessage(String paramString, ByteBuffer paramByteBuffer);
}
