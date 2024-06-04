package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;


















public final class AMDVertexShaderTessellator
{
  public static final int GL_SAMPLER_BUFFER_AMD = 36865;
  public static final int GL_INT_SAMPLER_BUFFER_AMD = 36866;
  public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_AMD = 36867;
  public static final int GL_DISCRETE_AMD = 36870;
  public static final int GL_CONTINUOUS_AMD = 36871;
  public static final int GL_TESSELLATION_MODE_AMD = 36868;
  public static final int GL_TESSELLATION_FACTOR_AMD = 36869;
  
  private AMDVertexShaderTessellator() {}
  
  public static void glTessellationFactorAMD(float factor)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glTessellationFactorAMD;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglTessellationFactorAMD(factor, function_pointer);
  }
  
  static native void nglTessellationFactorAMD(float paramFloat, long paramLong);
  
  public static void glTessellationModeAMD(int mode) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glTessellationModeAMD;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglTessellationModeAMD(mode, function_pointer);
  }
  
  static native void nglTessellationModeAMD(int paramInt, long paramLong);
}
