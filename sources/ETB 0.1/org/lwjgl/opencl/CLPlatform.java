package org.lwjgl.opencl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.api.Filter;






































public final class CLPlatform
  extends CLObject
{
  private static final CLPlatformUtil util = (CLPlatformUtil)getInfoUtilInstance(CLPlatform.class, "CL_PLATFORM_UTIL");
  
  private static final FastLongMap<CLPlatform> clPlatforms = new FastLongMap();
  
  private final CLObjectRegistry<CLDevice> clDevices;
  private Object caps;
  
  CLPlatform(long pointer)
  {
    super(pointer);
    
    if (isValid()) {
      clPlatforms.put(pointer, this);
      clDevices = new CLObjectRegistry();
    } else {
      clDevices = null;
    }
  }
  




  public static CLPlatform getCLPlatform(long id)
  {
    return (CLPlatform)clPlatforms.get(id);
  }
  




  public CLDevice getCLDevice(long id)
  {
    return (CLDevice)clDevices.getObject(id);
  }
  

  static <T extends CLObject> InfoUtil<T> getInfoUtilInstance(Class<T> clazz, String fieldName)
  {
    InfoUtil<T> instance = null;
    try {
      Class<?> infoUtil = Class.forName("org.lwjgl.opencl.InfoUtilFactory");
      instance = (InfoUtil)infoUtil.getDeclaredField(fieldName).get(null);
    }
    catch (Exception e) {}
    
    return instance;
  }
  




  public static List<CLPlatform> getPlatforms()
  {
    return getPlatforms(null);
  }
  






  public static List<CLPlatform> getPlatforms(Filter<CLPlatform> filter)
  {
    return util.getPlatforms(filter);
  }
  






  public String getInfoString(int param_name)
  {
    return util.getInfoString(this, param_name);
  }
  







  public List<CLDevice> getDevices(int device_type)
  {
    return getDevices(device_type, null);
  }
  








  public List<CLDevice> getDevices(int device_type, Filter<CLDevice> filter)
  {
    return util.getDevices(this, device_type, filter);
  }
  










  void setCapabilities(Object caps)
  {
    this.caps = caps;
  }
  
  Object getCapabilities() {
    return caps;
  }
  




  static void registerCLPlatforms(PointerBuffer platforms, IntBuffer num_platforms)
  {
    if (platforms == null) {
      return;
    }
    int pos = platforms.position();
    int count = Math.min(num_platforms.get(0), platforms.remaining());
    for (int i = 0; i < count; i++) {
      long id = platforms.get(pos + i);
      if (!clPlatforms.containsKey(id))
        new CLPlatform(id);
    }
  }
  
  CLObjectRegistry<CLDevice> getCLDeviceRegistry() { return clDevices; }
  




  void registerCLDevices(PointerBuffer devices, IntBuffer num_devices)
  {
    int pos = devices.position();
    int count = Math.min(num_devices.get(num_devices.position()), devices.remaining());
    for (int i = 0; i < count; i++) {
      long id = devices.get(pos + i);
      if (!clDevices.hasObject(id)) {
        new CLDevice(id, this);
      }
    }
  }
  



  void registerCLDevices(ByteBuffer devices, PointerBuffer num_devices)
  {
    int pos = devices.position();
    int count = Math.min((int)num_devices.get(num_devices.position()), devices.remaining()) / PointerBuffer.getPointerSize();
    for (int i = 0; i < count; i++) {
      int offset = pos + i * PointerBuffer.getPointerSize();
      long id = PointerBuffer.is64Bit() ? devices.getLong(offset) : devices.getInt(offset);
      if (!clDevices.hasObject(id)) {
        new CLDevice(id, this);
      }
    }
  }
  
  static abstract interface CLPlatformUtil
    extends InfoUtil<CLPlatform>
  {
    public abstract List<CLPlatform> getPlatforms(Filter<CLPlatform> paramFilter);
    
    public abstract List<CLDevice> getDevices(CLPlatform paramCLPlatform, int paramInt, Filter<CLDevice> paramFilter);
  }
}
