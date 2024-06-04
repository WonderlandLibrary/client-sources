package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;



public final class AMDDrawBuffersBlend
{
  private AMDDrawBuffersBlend() {}
  
  public static void glBlendFuncIndexedAMD(int buf, int src, int dst)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glBlendFuncIndexedAMD;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglBlendFuncIndexedAMD(buf, src, dst, function_pointer);
  }
  
  static native void nglBlendFuncIndexedAMD(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static void glBlendFuncSeparateIndexedAMD(int buf, int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glBlendFuncSeparateIndexedAMD;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglBlendFuncSeparateIndexedAMD(buf, srcRGB, dstRGB, srcAlpha, dstAlpha, function_pointer);
  }
  
  static native void nglBlendFuncSeparateIndexedAMD(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong);
  
  public static void glBlendEquationIndexedAMD(int buf, int mode) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glBlendEquationIndexedAMD;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglBlendEquationIndexedAMD(buf, mode, function_pointer);
  }
  
  static native void nglBlendEquationIndexedAMD(int paramInt1, int paramInt2, long paramLong);
  
  public static void glBlendEquationSeparateIndexedAMD(int buf, int modeRGB, int modeAlpha) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glBlendEquationSeparateIndexedAMD;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglBlendEquationSeparateIndexedAMD(buf, modeRGB, modeAlpha, function_pointer);
  }
  
  static native void nglBlendEquationSeparateIndexedAMD(int paramInt1, int paramInt2, int paramInt3, long paramLong);
}
