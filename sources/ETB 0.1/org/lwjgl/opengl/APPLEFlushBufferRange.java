package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;







public final class APPLEFlushBufferRange
{
  public static final int GL_BUFFER_SERIALIZED_MODIFY_APPLE = 35346;
  public static final int GL_BUFFER_FLUSHING_UNMAP_APPLE = 35347;
  
  private APPLEFlushBufferRange() {}
  
  public static void glBufferParameteriAPPLE(int target, int pname, int param)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glBufferParameteriAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglBufferParameteriAPPLE(target, pname, param, function_pointer);
  }
  
  static native void nglBufferParameteriAPPLE(int paramInt1, int paramInt2, int paramInt3, long paramLong);
  
  public static void glFlushMappedBufferRangeAPPLE(int target, long offset, long size) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glFlushMappedBufferRangeAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglFlushMappedBufferRangeAPPLE(target, offset, size, function_pointer);
  }
  
  static native void nglFlushMappedBufferRangeAPPLE(int paramInt, long paramLong1, long paramLong2, long paramLong3);
}
