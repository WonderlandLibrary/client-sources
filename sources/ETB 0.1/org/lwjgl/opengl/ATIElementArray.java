package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;

public final class ATIElementArray
{
  public static final int GL_ELEMENT_ARRAY_ATI = 34664;
  public static final int GL_ELEMENT_ARRAY_TYPE_ATI = 34665;
  public static final int GL_ELEMENT_ARRAY_POINTER_ATI = 34666;
  
  private ATIElementArray() {}
  
  public static void glElementPointerATI(ByteBuffer pPointer)
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glElementPointerATI;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(pPointer);
    nglElementPointerATI(5121, MemoryUtil.getAddress(pPointer), function_pointer);
  }
  
  public static void glElementPointerATI(java.nio.IntBuffer pPointer) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glElementPointerATI;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(pPointer);
    nglElementPointerATI(5125, MemoryUtil.getAddress(pPointer), function_pointer);
  }
  
  public static void glElementPointerATI(java.nio.ShortBuffer pPointer) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glElementPointerATI;
    BufferChecks.checkFunctionAddress(function_pointer);
    BufferChecks.checkDirect(pPointer);
    nglElementPointerATI(5123, MemoryUtil.getAddress(pPointer), function_pointer);
  }
  
  static native void nglElementPointerATI(int paramInt, long paramLong1, long paramLong2);
  
  public static void glDrawElementArrayATI(int mode, int count) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawElementArrayATI;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglDrawElementArrayATI(mode, count, function_pointer);
  }
  
  static native void nglDrawElementArrayATI(int paramInt1, int paramInt2, long paramLong);
  
  public static void glDrawRangeElementArrayATI(int mode, int start, int end, int count) { ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glDrawRangeElementArrayATI;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglDrawRangeElementArrayATI(mode, start, end, count, function_pointer);
  }
  
  static native void nglDrawRangeElementArrayATI(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong);
}
