package org.lwjgl.opencl;

import java.nio.IntBuffer;
import java.util.List;
import org.lwjgl.LWJGLException;
import org.lwjgl.opencl.api.CLImageFormat;
import org.lwjgl.opencl.api.Filter;
import org.lwjgl.opengl.Drawable;





































public final class CLContext
  extends CLObjectChild<CLPlatform>
{
  private static final CLContextUtil util = (CLContextUtil)CLPlatform.getInfoUtilInstance(CLContext.class, "CL_CONTEXT_UTIL");
  
  private final CLObjectRegistry<CLCommandQueue> clCommandQueues;
  
  private final CLObjectRegistry<CLMem> clMems;
  private final CLObjectRegistry<CLSampler> clSamplers;
  private final CLObjectRegistry<CLProgram> clPrograms;
  private final CLObjectRegistry<CLEvent> clEvents;
  private long contextCallback;
  private long printfCallback;
  
  CLContext(long pointer, CLPlatform platform)
  {
    super(pointer, platform);
    



    if (isValid()) {
      clCommandQueues = new CLObjectRegistry();
      clMems = new CLObjectRegistry();
      clSamplers = new CLObjectRegistry();
      clPrograms = new CLObjectRegistry();
      clEvents = new CLObjectRegistry();
    } else {
      clCommandQueues = null;
      clMems = null;
      clSamplers = null;
      clPrograms = null;
      clEvents = null;
    }
  }
  





  public CLCommandQueue getCLCommandQueue(long id)
  {
    return (CLCommandQueue)clCommandQueues.getObject(id);
  }
  




  public CLMem getCLMem(long id)
  {
    return (CLMem)clMems.getObject(id);
  }
  




  public CLSampler getCLSampler(long id)
  {
    return (CLSampler)clSamplers.getObject(id);
  }
  




  public CLProgram getCLProgram(long id)
  {
    return (CLProgram)clPrograms.getObject(id);
  }
  




  public CLEvent getCLEvent(long id)
  {
    return (CLEvent)clEvents.getObject(id);
  }
  










  public static CLContext create(CLPlatform platform, List<CLDevice> devices, IntBuffer errcode_ret)
    throws LWJGLException
  {
    return create(platform, devices, null, null, errcode_ret);
  }
  










  public static CLContext create(CLPlatform platform, List<CLDevice> devices, CLContextCallback pfn_notify, IntBuffer errcode_ret)
    throws LWJGLException
  {
    return create(platform, devices, pfn_notify, null, errcode_ret);
  }
  










  public static CLContext create(CLPlatform platform, List<CLDevice> devices, CLContextCallback pfn_notify, Drawable share_drawable, IntBuffer errcode_ret)
    throws LWJGLException
  {
    return util.create(platform, devices, pfn_notify, share_drawable, errcode_ret);
  }
  









  public static CLContext createFromType(CLPlatform platform, long device_type, IntBuffer errcode_ret)
    throws LWJGLException
  {
    return util.createFromType(platform, device_type, null, null, errcode_ret);
  }
  










  public static CLContext createFromType(CLPlatform platform, long device_type, CLContextCallback pfn_notify, IntBuffer errcode_ret)
    throws LWJGLException
  {
    return util.createFromType(platform, device_type, pfn_notify, null, errcode_ret);
  }
  










  public static CLContext createFromType(CLPlatform platform, long device_type, CLContextCallback pfn_notify, Drawable share_drawable, IntBuffer errcode_ret)
    throws LWJGLException
  {
    return util.createFromType(platform, device_type, pfn_notify, share_drawable, errcode_ret);
  }
  






  public int getInfoInt(int param_name)
  {
    return util.getInfoInt(this, param_name);
  }
  




  public List<CLDevice> getInfoDevices()
  {
    return util.getInfoDevices(this);
  }
  
  public List<CLImageFormat> getSupportedImageFormats(long flags, int image_type) {
    return getSupportedImageFormats(flags, image_type, null);
  }
  
  public List<CLImageFormat> getSupportedImageFormats(long flags, int image_type, Filter<CLImageFormat> filter) {
    return util.getSupportedImageFormats(this, flags, image_type, filter);
  }
  















  CLObjectRegistry<CLCommandQueue> getCLCommandQueueRegistry() { return clCommandQueues; }
  
  CLObjectRegistry<CLMem> getCLMemRegistry() { return clMems; }
  
  CLObjectRegistry<CLSampler> getCLSamplerRegistry() { return clSamplers; }
  
  CLObjectRegistry<CLProgram> getCLProgramRegistry() { return clPrograms; }
  
  CLObjectRegistry<CLEvent> getCLEventRegistry() { return clEvents; }
  
  private boolean checkCallback(long callback, int result) {
    if ((result == 0) && ((callback == 0L) || (isValid()))) {
      return true;
    }
    if (callback != 0L)
      CallbackUtil.deleteGlobalRef(callback);
    return false;
  }
  





  void setContextCallback(long callback)
  {
    if (checkCallback(callback, 0)) {
      contextCallback = callback;
    }
  }
  




  void setPrintfCallback(long callback, int result)
  {
    if (checkCallback(callback, result)) {
      printfCallback = callback;
    }
  }
  



  void releaseImpl()
  {
    if (release() > 0) {
      return;
    }
    if (contextCallback != 0L)
      CallbackUtil.deleteGlobalRef(contextCallback);
    if (printfCallback != 0L) {
      CallbackUtil.deleteGlobalRef(printfCallback);
    }
  }
  
  static abstract interface CLContextUtil
    extends InfoUtil<CLContext>
  {
    public abstract List<CLDevice> getInfoDevices(CLContext paramCLContext);
    
    public abstract CLContext create(CLPlatform paramCLPlatform, List<CLDevice> paramList, CLContextCallback paramCLContextCallback, Drawable paramDrawable, IntBuffer paramIntBuffer)
      throws LWJGLException;
    
    public abstract CLContext createFromType(CLPlatform paramCLPlatform, long paramLong, CLContextCallback paramCLContextCallback, Drawable paramDrawable, IntBuffer paramIntBuffer)
      throws LWJGLException;
    
    public abstract List<CLImageFormat> getSupportedImageFormats(CLContext paramCLContext, long paramLong, int paramInt, Filter<CLImageFormat> paramFilter);
  }
}
