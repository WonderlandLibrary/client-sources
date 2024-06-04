package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;






public final class NVConditionalRender
{
  public static final int GL_QUERY_WAIT_NV = 36371;
  public static final int GL_QUERY_NO_WAIT_NV = 36372;
  public static final int GL_QUERY_BY_REGION_WAIT_NV = 36373;
  public static final int GL_QUERY_BY_REGION_NO_WAIT_NV = 36374;
  
  private NVConditionalRender() {}
  
  public static void glBeginConditionalRenderNV(int id, int mode)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glBeginConditionalRenderNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglBeginConditionalRenderNV(id, mode, function_pointer);
  }
  
  static native void nglBeginConditionalRenderNV(int paramInt1, int paramInt2, long paramLong);
  
  public static void glEndConditionalRenderNV() { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glEndConditionalRenderNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglEndConditionalRenderNV(function_pointer);
  }
  
  static native void nglEndConditionalRenderNV(long paramLong);
}
