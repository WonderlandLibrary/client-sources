package org.lwjgl.opencl;




































public final class CLKernel
  extends CLObjectChild<CLProgram>
{
  private static final CLKernelUtil util = (CLKernelUtil)CLPlatform.getInfoUtilInstance(CLKernel.class, "CL_KERNEL_UTIL");
  
  CLKernel(long pointer, CLProgram program) {
    super(pointer, program);
    if (isValid()) {
      program.getCLKernelRegistry().registerObject(this);
    }
  }
  











  public CLKernel setArg(int index, byte value)
  {
    util.setArg(this, index, value);
    return this;
  }
  








  public CLKernel setArg(int index, short value)
  {
    util.setArg(this, index, value);
    return this;
  }
  








  public CLKernel setArg(int index, int value)
  {
    util.setArg(this, index, value);
    return this;
  }
  








  public CLKernel setArg(int index, long value)
  {
    util.setArg(this, index, value);
    return this;
  }
  








  public CLKernel setArg(int index, float value)
  {
    util.setArg(this, index, value);
    return this;
  }
  








  public CLKernel setArg(int index, double value)
  {
    util.setArg(this, index, value);
    return this;
  }
  








  public CLKernel setArg(int index, CLObject value)
  {
    util.setArg(this, index, value);
    return this;
  }
  







  public CLKernel setArgSize(int index, long size)
  {
    util.setArgSize(this, index, size);
    return this;
  }
  








  public String getInfoString(int param_name)
  {
    return util.getInfoString(this, param_name);
  }
  






  public int getInfoInt(int param_name)
  {
    return util.getInfoInt(this, param_name);
  }
  








  public long getWorkGroupInfoSize(CLDevice device, int param_name)
  {
    return util.getWorkGroupInfoSize(this, device, param_name);
  }
  






  public long[] getWorkGroupInfoSizeArray(CLDevice device, int param_name)
  {
    return util.getWorkGroupInfoSizeArray(this, device, param_name);
  }
  







  public long getWorkGroupInfoLong(CLDevice device, int param_name)
  {
    return util.getWorkGroupInfoLong(this, device, param_name);
  }
  



























  int release()
  {
    try
    {
      return super.release();
    } finally {
      if (!isValid()) {
        ((CLProgram)getParent()).getCLKernelRegistry().unregisterObject(this);
      }
    }
  }
  
  static abstract interface CLKernelUtil
    extends InfoUtil<CLKernel>
  {
    public abstract void setArg(CLKernel paramCLKernel, int paramInt, byte paramByte);
    
    public abstract void setArg(CLKernel paramCLKernel, int paramInt, short paramShort);
    
    public abstract void setArg(CLKernel paramCLKernel, int paramInt1, int paramInt2);
    
    public abstract void setArg(CLKernel paramCLKernel, int paramInt, long paramLong);
    
    public abstract void setArg(CLKernel paramCLKernel, int paramInt, float paramFloat);
    
    public abstract void setArg(CLKernel paramCLKernel, int paramInt, double paramDouble);
    
    public abstract void setArg(CLKernel paramCLKernel, int paramInt, CLObject paramCLObject);
    
    public abstract void setArgSize(CLKernel paramCLKernel, int paramInt, long paramLong);
    
    public abstract long getWorkGroupInfoSize(CLKernel paramCLKernel, CLDevice paramCLDevice, int paramInt);
    
    public abstract long[] getWorkGroupInfoSizeArray(CLKernel paramCLKernel, CLDevice paramCLDevice, int paramInt);
    
    public abstract long getWorkGroupInfoLong(CLKernel paramCLKernel, CLDevice paramCLDevice, int paramInt);
  }
}
