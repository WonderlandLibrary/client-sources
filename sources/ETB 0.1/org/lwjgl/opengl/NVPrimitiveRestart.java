package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;














public final class NVPrimitiveRestart
{
  public static final int GL_PRIMITIVE_RESTART_NV = 34136;
  public static final int GL_PRIMITIVE_RESTART_INDEX_NV = 34137;
  
  private NVPrimitiveRestart() {}
  
  public static void glPrimitiveRestartNV()
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glPrimitiveRestartNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglPrimitiveRestartNV(function_pointer);
  }
  
  static native void nglPrimitiveRestartNV(long paramLong);
  
  public static void glPrimitiveRestartIndexNV(int index) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glPrimitiveRestartIndexNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglPrimitiveRestartIndexNV(index, function_pointer);
  }
  
  static native void nglPrimitiveRestartIndexNV(int paramInt, long paramLong);
}
