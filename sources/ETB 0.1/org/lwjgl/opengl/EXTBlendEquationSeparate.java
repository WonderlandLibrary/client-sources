package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;







public final class EXTBlendEquationSeparate
{
  public static final int GL_BLEND_EQUATION_RGB_EXT = 32777;
  public static final int GL_BLEND_EQUATION_ALPHA_EXT = 34877;
  
  private EXTBlendEquationSeparate() {}
  
  public static void glBlendEquationSeparateEXT(int modeRGB, int modeAlpha)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glBlendEquationSeparateEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglBlendEquationSeparateEXT(modeRGB, modeAlpha, function_pointer);
  }
  
  static native void nglBlendEquationSeparateEXT(int paramInt1, int paramInt2, long paramLong);
}
