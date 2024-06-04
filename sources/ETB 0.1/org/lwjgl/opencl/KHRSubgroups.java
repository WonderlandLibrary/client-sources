package org.lwjgl.opencl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;

public final class KHRSubgroups
{
  private KHRSubgroups() {}
  
  public static int clGetKernelSubGroupInfoKHR(CLKernel kernel, CLDevice device, int param_name, ByteBuffer input_value, ByteBuffer param_value, org.lwjgl.PointerBuffer param_value_size_ret)
  {
    long function_pointer = CLCapabilities.clGetKernelSubGroupInfoKHR;
    BufferChecks.checkFunctionAddress(function_pointer);
    if (input_value != null)
      BufferChecks.checkDirect(input_value);
    if (param_value != null)
      BufferChecks.checkDirect(param_value);
    if (param_value_size_ret != null)
      BufferChecks.checkBuffer(param_value_size_ret, 1);
    int __result = nclGetKernelSubGroupInfoKHR(kernel.getPointer(), device == null ? 0L : device.getPointer(), param_name, input_value == null ? 0 : input_value.remaining(), MemoryUtil.getAddressSafe(input_value), param_value == null ? 0 : param_value.remaining(), MemoryUtil.getAddressSafe(param_value), MemoryUtil.getAddressSafe(param_value_size_ret), function_pointer);
    return __result;
  }
  
  static native int nclGetKernelSubGroupInfoKHR(long paramLong1, long paramLong2, int paramInt, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, long paramLong8);
}
