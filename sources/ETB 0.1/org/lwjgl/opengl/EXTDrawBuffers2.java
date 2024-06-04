package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferChecks;

public final class EXTDrawBuffers2
{
  private EXTDrawBuffers2() {}
  
  public static void glColorMaskIndexedEXT(int buf, boolean r, boolean g, boolean b, boolean a)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glColorMaskIndexedEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglColorMaskIndexedEXT(buf, r, g, b, a, function_pointer);
  }
  
  static native void nglColorMaskIndexedEXT(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, long paramLong);
  
  public static void glGetBooleanIndexedEXT(int value, int index, ByteBuffer data) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glGetBooleanIndexedvEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkBuffer(data, 4);
    nglGetBooleanIndexedvEXT(value, index, org.lwjgl.MemoryUtil.getAddress(data), function_pointer);
  }
  
  static native void nglGetBooleanIndexedvEXT(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static boolean glGetBooleanIndexedEXT(int value, int index) {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glGetBooleanIndexedvEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    ByteBuffer data = APIUtil.getBufferByte(caps, 1);
    nglGetBooleanIndexedvEXT(value, index, org.lwjgl.MemoryUtil.getAddress(data), function_pointer);
    return data.get(0) == 1;
  }
  
  public static void glGetIntegerIndexedEXT(int value, int index, IntBuffer data) {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glGetIntegerIndexedvEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkBuffer(data, 4);
    nglGetIntegerIndexedvEXT(value, index, org.lwjgl.MemoryUtil.getAddress(data), function_pointer);
  }
  
  static native void nglGetIntegerIndexedvEXT(int paramInt1, int paramInt2, long paramLong1, long paramLong2);
  
  public static int glGetIntegerIndexedEXT(int value, int index) {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glGetIntegerIndexedvEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    IntBuffer data = APIUtil.getBufferInt(caps);
    nglGetIntegerIndexedvEXT(value, index, org.lwjgl.MemoryUtil.getAddress(data), function_pointer);
    return data.get(0);
  }
  
  public static void glEnableIndexedEXT(int target, int index) {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glEnableIndexedEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglEnableIndexedEXT(target, index, function_pointer);
  }
  
  static native void nglEnableIndexedEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static void glDisableIndexedEXT(int target, int index) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDisableIndexedEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglDisableIndexedEXT(target, index, function_pointer);
  }
  
  static native void nglDisableIndexedEXT(int paramInt1, int paramInt2, long paramLong);
  
  public static boolean glIsEnabledIndexedEXT(int target, int index) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glIsEnabledIndexedEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    boolean __result = nglIsEnabledIndexedEXT(target, index, function_pointer);
    return __result;
  }
  
  static native boolean nglIsEnabledIndexedEXT(int paramInt1, int paramInt2, long paramLong);
}
