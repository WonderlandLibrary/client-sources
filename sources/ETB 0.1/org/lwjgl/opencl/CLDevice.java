package org.lwjgl.opencl;

import org.lwjgl.PointerBuffer;




































public final class CLDevice
  extends CLObjectChild<CLDevice>
{
  private static final InfoUtil<CLDevice> util = CLPlatform.getInfoUtilInstance(CLDevice.class, "CL_DEVICE_UTIL");
  
  private final CLPlatform platform;
  private final CLObjectRegistry<CLDevice> subCLDevices;
  private Object caps;
  
  CLDevice(long pointer, CLPlatform platform)
  {
    this(pointer, null, platform);
  }
  





  CLDevice(long pointer, CLDevice parent)
  {
    this(pointer, parent, parent.getPlatform());
  }
  
  CLDevice(long pointer, CLDevice parent, CLPlatform platform) {
    super(pointer, parent);
    
    if (isValid()) {
      this.platform = platform;
      platform.getCLDeviceRegistry().registerObject(this);
      
      subCLDevices = new CLObjectRegistry();
      if (parent != null)
        subCLDevices.registerObject(this);
    } else {
      this.platform = null;
      subCLDevices = null;
    }
  }
  
  public CLPlatform getPlatform() {
    return platform;
  }
  





  public CLDevice getSubCLDevice(long id)
  {
    return (CLDevice)subCLDevices.getObject(id);
  }
  







  public String getInfoString(int param_name)
  {
    return util.getInfoString(this, param_name);
  }
  






  public int getInfoInt(int param_name)
  {
    return util.getInfoInt(this, param_name);
  }
  






  public boolean getInfoBoolean(int param_name)
  {
    return util.getInfoInt(this, param_name) != 0;
  }
  






  public long getInfoSize(int param_name)
  {
    return util.getInfoSize(this, param_name);
  }
  






  public long[] getInfoSizeArray(int param_name)
  {
    return util.getInfoSizeArray(this, param_name);
  }
  







  public long getInfoLong(int param_name)
  {
    return util.getInfoLong(this, param_name);
  }
  

  void setCapabilities(Object caps)
  {
    this.caps = caps;
  }
  
  Object getCapabilities() {
    return caps;
  }
  
  int retain() {
    if (getParent() == null) {
      return getReferenceCount();
    }
    return super.retain();
  }
  
  int release() {
    if (getParent() == null) {
      return getReferenceCount();
    }
    try {
      return super.release();
    } finally {
      if (!isValid())
        getParentsubCLDevices.unregisterObject(this);
    }
  }
  
  CLObjectRegistry<CLDevice> getSubCLDeviceRegistry() { return subCLDevices; }
  




  void registerSubCLDevices(PointerBuffer devices)
  {
    for (int i = devices.position(); i < devices.limit(); i++) {
      long pointer = devices.get(i);
      if (pointer != 0L) {
        new CLDevice(pointer, this);
      }
    }
  }
}
