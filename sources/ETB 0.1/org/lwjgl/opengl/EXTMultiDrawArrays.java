package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;

public final class EXTMultiDrawArrays
{
  private EXTMultiDrawArrays() {}
  
  public static void glMultiDrawArraysEXT(int mode, IntBuffer piFirst, IntBuffer piCount)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glMultiDrawArraysEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(piFirst);
    BufferChecks.checkBuffer(piCount, piFirst.remaining());
    nglMultiDrawArraysEXT(mode, MemoryUtil.getAddress(piFirst), MemoryUtil.getAddress(piCount), piFirst.remaining(), function_pointer);
  }
  
  static native void nglMultiDrawArraysEXT(int paramInt1, long paramLong1, long paramLong2, int paramInt2, long paramLong3);
}
