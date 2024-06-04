package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferChecks;

public final class EXTDrawInstanced
{
  private EXTDrawInstanced() {}
  
  public static void glDrawArraysInstancedEXT(int mode, int first, int count, int primcount)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawArraysInstancedEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglDrawArraysInstancedEXT(mode, first, count, primcount, function_pointer);
  }
  
  static native void nglDrawArraysInstancedEXT(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static void glDrawElementsInstancedEXT(int mode, ByteBuffer indices, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawElementsInstancedEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    GLChecks.ensureElementVBOdisabled(caps);
    BufferChecks.checkDirect(indices);
    nglDrawElementsInstancedEXT(mode, indices.remaining(), 5121, org.lwjgl.MemoryUtil.getAddress(indices), primcount, function_pointer);
  }
  
  public static void glDrawElementsInstancedEXT(int mode, IntBuffer indices, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawElementsInstancedEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    GLChecks.ensureElementVBOdisabled(caps);
    BufferChecks.checkDirect(indices);
    nglDrawElementsInstancedEXT(mode, indices.remaining(), 5125, org.lwjgl.MemoryUtil.getAddress(indices), primcount, function_pointer);
  }
  
  public static void glDrawElementsInstancedEXT(int mode, java.nio.ShortBuffer indices, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawElementsInstancedEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    GLChecks.ensureElementVBOdisabled(caps);
    BufferChecks.checkDirect(indices);
    nglDrawElementsInstancedEXT(mode, indices.remaining(), 5123, org.lwjgl.MemoryUtil.getAddress(indices), primcount, function_pointer); }
  
  static native void nglDrawElementsInstancedEXT(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, long paramLong2);
  
  public static void glDrawElementsInstancedEXT(int mode, int indices_count, int type, long indices_buffer_offset, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawElementsInstancedEXT;
    BufferChecks.checkFunctionAddress(function_pointer);
    GLChecks.ensureElementVBOenabled(caps);
    nglDrawElementsInstancedEXTBO(mode, indices_count, type, indices_buffer_offset, primcount, function_pointer);
  }
  
  static native void nglDrawElementsInstancedEXTBO(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, long paramLong2);
}
