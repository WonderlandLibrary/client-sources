package org.lwjgl.opengl;

import java.nio.IntBuffer;






public final class ARBInternalformatQuery
{
  public static final int GL_NUM_SAMPLE_COUNTS = 37760;
  
  private ARBInternalformatQuery() {}
  
  public static void glGetInternalformat(int target, int internalformat, int pname, IntBuffer params)
  {
    GL42.glGetInternalformat(target, internalformat, pname, params);
  }
  
  public static int glGetInternalformat(int target, int internalformat, int pname)
  {
    return GL42.glGetInternalformat(target, internalformat, pname);
  }
}
