package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;

public final class NVPointSprite
{
  public static final int GL_POINT_SPRITE_NV = 34913;
  public static final int GL_COORD_REPLACE_NV = 34914;
  public static final int GL_POINT_SPRITE_R_MODE_NV = 34915;
  
  private NVPointSprite() {}
  
  public static void glPointParameteriNV(int pname, int param)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glPointParameteriNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglPointParameteriNV(pname, param, function_pointer);
  }
  
  static native void nglPointParameteriNV(int paramInt1, int paramInt2, long paramLong);
  
  public static void glPointParameterNV(int pname, IntBuffer params) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glPointParameterivNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkBuffer(params, 4);
    nglPointParameterivNV(pname, MemoryUtil.getAddress(params), function_pointer);
  }
  
  static native void nglPointParameterivNV(int paramInt, long paramLong1, long paramLong2);
}
