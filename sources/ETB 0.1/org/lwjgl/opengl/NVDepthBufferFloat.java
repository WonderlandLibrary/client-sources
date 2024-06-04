package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;




















public final class NVDepthBufferFloat
{
  public static final int GL_DEPTH_COMPONENT32F_NV = 36267;
  public static final int GL_DEPTH32F_STENCIL8_NV = 36268;
  public static final int GL_FLOAT_32_UNSIGNED_INT_24_8_REV_NV = 36269;
  public static final int GL_DEPTH_BUFFER_FLOAT_MODE_NV = 36271;
  
  private NVDepthBufferFloat() {}
  
  public static void glDepthRangedNV(double n, double f)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDepthRangedNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglDepthRangedNV(n, f, function_pointer);
  }
  
  static native void nglDepthRangedNV(double paramDouble1, double paramDouble2, long paramLong);
  
  public static void glClearDepthdNV(double d) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glClearDepthdNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglClearDepthdNV(d, function_pointer);
  }
  
  static native void nglClearDepthdNV(double paramDouble, long paramLong);
  
  public static void glDepthBoundsdNV(double zmin, double zmax) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDepthBoundsdNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglDepthBoundsdNV(zmin, zmax, function_pointer);
  }
  
  static native void nglDepthBoundsdNV(double paramDouble1, double paramDouble2, long paramLong);
}
