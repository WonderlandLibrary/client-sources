package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;




public final class APPLEFence
{
  public static final int GL_DRAW_PIXELS_APPLE = 35338;
  public static final int GL_FENCE_APPLE = 35339;
  
  private APPLEFence() {}
  
  public static void glGenFencesAPPLE(IntBuffer fences)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glGenFencesAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(fences);
    nglGenFencesAPPLE(fences.remaining(), MemoryUtil.getAddress(fences), function_pointer);
  }
  
  static native void nglGenFencesAPPLE(int paramInt, long paramLong1, long paramLong2);
  
  public static int glGenFencesAPPLE() {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glGenFencesAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    IntBuffer fences = APIUtil.getBufferInt(caps);
    nglGenFencesAPPLE(1, MemoryUtil.getAddress(fences), function_pointer);
    return fences.get(0);
  }
  
  public static void glDeleteFencesAPPLE(IntBuffer fences) {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDeleteFencesAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(fences);
    nglDeleteFencesAPPLE(fences.remaining(), MemoryUtil.getAddress(fences), function_pointer);
  }
  
  static native void nglDeleteFencesAPPLE(int paramInt, long paramLong1, long paramLong2);
  
  public static void glDeleteFencesAPPLE(int fence) {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDeleteFencesAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglDeleteFencesAPPLE(1, APIUtil.getInt(caps, fence), function_pointer);
  }
  
  public static void glSetFenceAPPLE(int fence) {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glSetFenceAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglSetFenceAPPLE(fence, function_pointer);
  }
  
  static native void nglSetFenceAPPLE(int paramInt, long paramLong);
  
  public static boolean glIsFenceAPPLE(int fence) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glIsFenceAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    boolean __result = nglIsFenceAPPLE(fence, function_pointer);
    return __result;
  }
  
  static native boolean nglIsFenceAPPLE(int paramInt, long paramLong);
  
  public static boolean glTestFenceAPPLE(int fence) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glTestFenceAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    boolean __result = nglTestFenceAPPLE(fence, function_pointer);
    return __result;
  }
  
  static native boolean nglTestFenceAPPLE(int paramInt, long paramLong);
  
  public static void glFinishFenceAPPLE(int fence) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glFinishFenceAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglFinishFenceAPPLE(fence, function_pointer);
  }
  
  static native void nglFinishFenceAPPLE(int paramInt, long paramLong);
  
  public static boolean glTestObjectAPPLE(int object, int name) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glTestObjectAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    boolean __result = nglTestObjectAPPLE(object, name, function_pointer);
    return __result;
  }
  
  static native boolean nglTestObjectAPPLE(int paramInt1, int paramInt2, long paramLong);
  
  public static void glFinishObjectAPPLE(int object, int name) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glFinishObjectAPPLE;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglFinishObjectAPPLE(object, name, function_pointer);
  }
  
  static native void nglFinishObjectAPPLE(int paramInt1, int paramInt2, long paramLong);
}
