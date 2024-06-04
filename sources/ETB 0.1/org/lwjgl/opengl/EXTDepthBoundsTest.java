package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;













public final class EXTDepthBoundsTest
{
  public static final int GL_DEPTH_BOUNDS_TEST_EXT = 34960;
  public static final int GL_DEPTH_BOUNDS_EXT = 34961;
  
  private EXTDepthBoundsTest() {}
  
  public static void glDepthBoundsEXT(double zmin, double zmax)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDepthBoundsEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglDepthBoundsEXT(zmin, zmax, function_pointer);
  }
  
  static native void nglDepthBoundsEXT(double paramDouble1, double paramDouble2, long paramLong);
}
