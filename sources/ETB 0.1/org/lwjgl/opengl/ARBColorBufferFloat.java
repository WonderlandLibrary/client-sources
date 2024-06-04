package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;
































public final class ARBColorBufferFloat
{
  public static final int GL_RGBA_FLOAT_MODE_ARB = 34848;
  public static final int GL_CLAMP_VERTEX_COLOR_ARB = 35098;
  public static final int GL_CLAMP_FRAGMENT_COLOR_ARB = 35099;
  public static final int GL_CLAMP_READ_COLOR_ARB = 35100;
  public static final int GL_FIXED_ONLY_ARB = 35101;
  public static final int WGL_TYPE_RGBA_FLOAT_ARB = 8608;
  public static final int GLX_RGBA_FLOAT_TYPE = 8377;
  public static final int GLX_RGBA_FLOAT_BIT = 4;
  
  private ARBColorBufferFloat() {}
  
  public static void glClampColorARB(int target, int clamp)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glClampColorARB;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglClampColorARB(target, clamp, function_pointer);
  }
  
  static native void nglClampColorARB(int paramInt1, int paramInt2, long paramLong);
}
