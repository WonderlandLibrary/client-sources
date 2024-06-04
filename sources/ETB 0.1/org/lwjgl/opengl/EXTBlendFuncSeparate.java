package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;



public final class EXTBlendFuncSeparate
{
  public static final int GL_BLEND_DST_RGB_EXT = 32968;
  public static final int GL_BLEND_SRC_RGB_EXT = 32969;
  public static final int GL_BLEND_DST_ALPHA_EXT = 32970;
  public static final int GL_BLEND_SRC_ALPHA_EXT = 32971;
  
  private EXTBlendFuncSeparate() {}
  
  public static void glBlendFuncSeparateEXT(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glBlendFuncSeparateEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglBlendFuncSeparateEXT(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha, function_pointer);
  }
  
  static native void nglBlendFuncSeparateEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
}
