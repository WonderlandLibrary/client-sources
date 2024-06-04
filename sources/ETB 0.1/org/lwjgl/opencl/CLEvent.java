package org.lwjgl.opencl;




































public final class CLEvent
  extends CLObjectChild<CLContext>
{
  private static final CLEventUtil util = (CLEventUtil)CLPlatform.getInfoUtilInstance(CLEvent.class, "CL_EVENT_UTIL");
  private final CLCommandQueue queue;
  
  CLEvent(long pointer, CLContext context)
  {
    this(pointer, context, null);
  }
  
  CLEvent(long pointer, CLCommandQueue queue) {
    this(pointer, (CLContext)queue.getParent(), queue);
  }
  
  CLEvent(long pointer, CLContext context, CLCommandQueue queue) {
    super(pointer, context);
    if (isValid()) {
      this.queue = queue;
      if (queue == null) {
        context.getCLEventRegistry().registerObject(this);
      } else
        queue.getCLEventRegistry().registerObject(this);
    } else {
      this.queue = null;
    }
  }
  




  public CLCommandQueue getCLCommandQueue()
  {
    return queue;
  }
  








  public int getInfoInt(int param_name)
  {
    return util.getInfoInt(this, param_name);
  }
  









  public long getProfilingInfoLong(int param_name)
  {
    return util.getProfilingInfoLong(this, param_name);
  }
  








  CLObjectRegistry<CLEvent> getParentRegistry()
  {
    if (queue == null) {
      return ((CLContext)getParent()).getCLEventRegistry();
    }
    return queue.getCLEventRegistry();
  }
  
  int release() {
    try {
      return super.release();
    } finally {
      if (!isValid()) {
        if (queue == null) {
          ((CLContext)getParent()).getCLEventRegistry().unregisterObject(this);
        } else {
          queue.getCLEventRegistry().unregisterObject(this);
        }
      }
    }
  }
  
  static abstract interface CLEventUtil
    extends InfoUtil<CLEvent>
  {
    public abstract long getProfilingInfoLong(CLEvent paramCLEvent, int paramInt);
  }
}
