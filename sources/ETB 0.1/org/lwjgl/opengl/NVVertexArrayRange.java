package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;

public final class NVVertexArrayRange
{
  public static final int GL_VERTEX_ARRAY_RANGE_NV = 34077;
  public static final int GL_VERTEX_ARRAY_RANGE_LENGTH_NV = 34078;
  public static final int GL_VERTEX_ARRAY_RANGE_VALID_NV = 34079;
  public static final int GL_MAX_VERTEX_ARRAY_RANGE_ELEMENT_NV = 34080;
  public static final int GL_VERTEX_ARRAY_RANGE_POINTER_NV = 34081;
  
  private NVVertexArrayRange() {}
  
  public static void glVertexArrayRangeNV(ByteBuffer pPointer)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glVertexArrayRangeNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(pPointer);
    nglVertexArrayRangeNV(pPointer.remaining(), MemoryUtil.getAddress(pPointer), function_pointer);
  }
  
  public static void glVertexArrayRangeNV(java.nio.DoubleBuffer pPointer) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glVertexArrayRangeNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(pPointer);
    nglVertexArrayRangeNV(pPointer.remaining() << 3, MemoryUtil.getAddress(pPointer), function_pointer);
  }
  
  public static void glVertexArrayRangeNV(java.nio.FloatBuffer pPointer) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glVertexArrayRangeNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(pPointer);
    nglVertexArrayRangeNV(pPointer.remaining() << 2, MemoryUtil.getAddress(pPointer), function_pointer);
  }
  
  public static void glVertexArrayRangeNV(java.nio.IntBuffer pPointer) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glVertexArrayRangeNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(pPointer);
    nglVertexArrayRangeNV(pPointer.remaining() << 2, MemoryUtil.getAddress(pPointer), function_pointer);
  }
  
  public static void glVertexArrayRangeNV(java.nio.ShortBuffer pPointer) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glVertexArrayRangeNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(pPointer);
    nglVertexArrayRangeNV(pPointer.remaining() << 1, MemoryUtil.getAddress(pPointer), function_pointer);
  }
  
  static native void nglVertexArrayRangeNV(int paramInt, long paramLong1, long paramLong2);
  
  public static void glFlushVertexArrayRangeNV() { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glFlushVertexArrayRangeNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglFlushVertexArrayRangeNV(function_pointer);
  }
  
  static native void nglFlushVertexArrayRangeNV(long paramLong);
  
  public static ByteBuffer glAllocateMemoryNV(int size, float readFrequency, float writeFrequency, float priority) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glAllocateMemoryNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    ByteBuffer __result = nglAllocateMemoryNV(size, readFrequency, writeFrequency, priority, size, function_pointer);
    return (org.lwjgl.LWJGLUtil.CHECKS) && (__result == null) ? null : __result.order(java.nio.ByteOrder.nativeOrder());
  }
  
  static native ByteBuffer nglAllocateMemoryNV(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, long paramLong1, long paramLong2);
  
  public static void glFreeMemoryNV(ByteBuffer pointer) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glFreeMemoryNV;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(pointer);
    nglFreeMemoryNV(MemoryUtil.getAddress(pointer), function_pointer);
  }
  
  static native void nglFreeMemoryNV(long paramLong1, long paramLong2);
}
