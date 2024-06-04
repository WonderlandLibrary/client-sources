package org.lwjgl.opencl;




































public final class CLSampler
  extends CLObjectChild<CLContext>
{
  private static final InfoUtil<CLSampler> util = CLPlatform.getInfoUtilInstance(CLSampler.class, "CL_SAMPLER_UTIL");
  
  CLSampler(long pointer, CLContext context) {
    super(pointer, context);
    if (isValid()) {
      context.getCLSamplerRegistry().registerObject(this);
    }
  }
  







  public int getInfoInt(int param_name)
  {
    return util.getInfoInt(this, param_name);
  }
  







  public long getInfoLong(int param_name)
  {
    return util.getInfoLong(this, param_name);
  }
  
  int release()
  {
    try
    {
      return super.release();
    } finally {
      if (!isValid()) {
        ((CLContext)getParent()).getCLSamplerRegistry().unregisterObject(this);
      }
    }
  }
}
