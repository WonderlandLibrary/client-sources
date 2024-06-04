package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;











public final class AMDInterleavedElements
{
  public static final int GL_VERTEX_ELEMENT_SWIZZLE_AMD = 37284;
  public static final int GL_VERTEX_ID_SWIZZLE_AMD = 37285;
  
  private AMDInterleavedElements() {}
  
  public static void glVertexAttribParameteriAMD(int index, int pname, int param)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glVertexAttribParameteriAMD;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglVertexAttribParameteriAMD(index, pname, param, function_pointer);
  }
  
  static native void nglVertexAttribParameteriAMD(int paramInt1, int paramInt2, int paramInt3, long paramLong);
}
