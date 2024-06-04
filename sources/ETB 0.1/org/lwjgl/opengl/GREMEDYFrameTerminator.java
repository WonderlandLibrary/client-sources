package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;



public final class GREMEDYFrameTerminator
{
  private GREMEDYFrameTerminator() {}
  
  public static void glFrameTerminatorGREMEDY()
  {
    ContextCapabilities caps = GLContext.getCapabilities();
    long function_pointer = glFrameTerminatorGREMEDY;
    BufferChecks.checkFunctionAddress(function_pointer);
    nglFrameTerminatorGREMEDY(function_pointer);
  }
  
  static native void nglFrameTerminatorGREMEDY(long paramLong);
}
