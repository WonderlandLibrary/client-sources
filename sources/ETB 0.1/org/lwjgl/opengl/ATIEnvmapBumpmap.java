package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferChecks;

public final class ATIEnvmapBumpmap
{
  public static final int GL_BUMP_ROT_MATRIX_ATI = 34677;
  public static final int GL_BUMP_ROT_MATRIX_SIZE_ATI = 34678;
  public static final int GL_BUMP_NUM_TEX_UNITS_ATI = 34679;
  public static final int GL_BUMP_TEX_UNITS_ATI = 34680;
  public static final int GL_DUDV_ATI = 34681;
  public static final int GL_DU8DV8_ATI = 34682;
  public static final int GL_BUMP_ENVMAP_ATI = 34683;
  public static final int GL_BUMP_TARGET_ATI = 34684;
  
  private ATIEnvmapBumpmap() {}
  
  public static void glTexBumpParameterATI(int pname, FloatBuffer param)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glTexBumpParameterfvATI;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkBuffer(param, 4);
    nglTexBumpParameterfvATI(pname, org.lwjgl.MemoryUtil.getAddress(param), function_pointer);
  }
  
  static native void nglTexBumpParameterfvATI(int paramInt, long paramLong1, long paramLong2);
  
  public static void glTexBumpParameterATI(int pname, IntBuffer param) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glTexBumpParameterivATI;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkBuffer(param, 4);
    nglTexBumpParameterivATI(pname, org.lwjgl.MemoryUtil.getAddress(param), function_pointer);
  }
  
  static native void nglTexBumpParameterivATI(int paramInt, long paramLong1, long paramLong2);
  
  public static void glGetTexBumpParameterATI(int pname, FloatBuffer param) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glGetTexBumpParameterfvATI;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkBuffer(param, 4);
    nglGetTexBumpParameterfvATI(pname, org.lwjgl.MemoryUtil.getAddress(param), function_pointer);
  }
  
  static native void nglGetTexBumpParameterfvATI(int paramInt, long paramLong1, long paramLong2);
  
  public static void glGetTexBumpParameterATI(int pname, IntBuffer param) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glGetTexBumpParameterivATI;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkBuffer(param, 4);
    nglGetTexBumpParameterivATI(pname, org.lwjgl.MemoryUtil.getAddress(param), function_pointer);
  }
  
  static native void nglGetTexBumpParameterivATI(int paramInt, long paramLong1, long paramLong2);
}
