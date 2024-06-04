package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferChecks;

public final class ARBDrawInstanced
{
  private ARBDrawInstanced() {}
  
  public static void glDrawArraysInstancedARB(int mode, int first, int count, int primcount)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawArraysInstancedARB;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglDrawArraysInstancedARB(mode, first, count, primcount, function_pointer);
  }
  
  static native void nglDrawArraysInstancedARB(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
  
  public static void glDrawElementsInstancedARB(int mode, ByteBuffer indices, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawElementsInstancedARB;
    BufferChecks.checkFunctionAddress(function_pointer);
    GLChecks.ensureElementVBOdisabled(caps);
    BufferChecks.checkDirect(indices);
    nglDrawElementsInstancedARB(mode, indices.remaining(), 5121, org.lwjgl.MemoryUtil.getAddress(indices), primcount, function_pointer);
  }
  
  public static void glDrawElementsInstancedARB(int mode, IntBuffer indices, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawElementsInstancedARB;
    BufferChecks.checkFunctionAddress(function_pointer);
    GLChecks.ensureElementVBOdisabled(caps);
    BufferChecks.checkDirect(indices);
    nglDrawElementsInstancedARB(mode, indices.remaining(), 5125, org.lwjgl.MemoryUtil.getAddress(indices), primcount, function_pointer);
  }
  
  public static void glDrawElementsInstancedARB(int mode, java.nio.ShortBuffer indices, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawElementsInstancedARB;
    BufferChecks.checkFunctionAddress(function_pointer);
    GLChecks.ensureElementVBOdisabled(caps);
    BufferChecks.checkDirect(indices);
    nglDrawElementsInstancedARB(mode, indices.remaining(), 5123, org.lwjgl.MemoryUtil.getAddress(indices), primcount, function_pointer); }
  
  static native void nglDrawElementsInstancedARB(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, long paramLong2);
  
  public static void glDrawElementsInstancedARB(int mode, int indices_count, int type, long indices_buffer_offset, int primcount) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawElementsInstancedARB;
    BufferChecks.checkFunctionAddress(function_pointer);
    GLChecks.ensureElementVBOenabled(caps);
    nglDrawElementsInstancedARBBO(mode, indices_count, type, indices_buffer_offset, primcount, function_pointer);
  }
  
  static native void nglDrawElementsInstancedARBBO(int paramInt1, int paramInt2, int paramInt3, long paramLong1, int paramInt4, long paramLong2);
}
