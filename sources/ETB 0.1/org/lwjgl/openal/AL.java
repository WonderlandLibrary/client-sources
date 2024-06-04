package org.lwjgl.openal;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;































































public final class AL
{
  static ALCdevice device;
  static ALCcontext context;
  private static boolean created;
  
  private AL() {}
  
  private static native void nCreate(String paramString)
    throws LWJGLException;
  
  private static native void nCreateDefault()
    throws LWJGLException;
  
  private static native void nDestroy();
  
  public static boolean isCreated()
  {
    return created;
  }
  









  public static void create(String deviceArguments, int contextFrequency, int contextRefresh, boolean contextSynchronized)
    throws LWJGLException
  {
    create(deviceArguments, contextFrequency, contextRefresh, contextSynchronized, true);
  }
  




  public static void create(String deviceArguments, int contextFrequency, int contextRefresh, boolean contextSynchronized, boolean openDevice)
    throws LWJGLException
  {
    if (created)
      throw new IllegalStateException("Only one OpenAL context may be instantiated at any one time.");
    String libname;
    String[] library_names;
    switch (LWJGLUtil.getPlatform()) {
    case 3:  String[] library_names;
      if (Sys.is64Bit()) {
        String libname = "OpenAL64";
        library_names = new String[] { "OpenAL64.dll" };
      } else {
        libname = "OpenAL32";
        library_names = new String[] { "OpenAL32.dll" };
      }
      break;
    case 1: 
      libname = "openal";
      library_names = new String[] { "libopenal64.so", "libopenal.so", "libopenal.so.0" };
      break;
    case 2: 
      libname = "openal";
      library_names = new String[] { "openal.dylib" };
      break;
    default: 
      throw new LWJGLException("Unknown platform: " + LWJGLUtil.getPlatform());
    }
    String[] oalPaths = LWJGLUtil.getLibraryPaths(libname, library_names, AL.class.getClassLoader());
    LWJGLUtil.log("Found " + oalPaths.length + " OpenAL paths");
    for (String oalPath : oalPaths) {
      try {
        nCreate(oalPath);
        created = true;
        init(deviceArguments, contextFrequency, contextRefresh, contextSynchronized, openDevice);
      }
      catch (LWJGLException e) {
        LWJGLUtil.log("Failed to load " + oalPath + ": " + e.getMessage());
      }
    }
    if ((!created) && (LWJGLUtil.getPlatform() == 2))
    {
      nCreateDefault();
      created = true;
      init(deviceArguments, contextFrequency, contextRefresh, contextSynchronized, openDevice);
    }
    if (!created)
      throw new LWJGLException("Could not locate OpenAL library.");
  }
  
  private static void init(String deviceArguments, int contextFrequency, int contextRefresh, boolean contextSynchronized, boolean openDevice) throws LWJGLException {
    try {
      AL10.initNativeStubs();
      ALC10.initNativeStubs();
      
      if (openDevice) {
        device = ALC10.alcOpenDevice(deviceArguments);
        if (device == null) {
          throw new LWJGLException("Could not open ALC device");
        }
        
        if (contextFrequency == -1) {
          context = ALC10.alcCreateContext(device, null);
        } else {
          context = ALC10.alcCreateContext(device, ALCcontext.createAttributeList(contextFrequency, contextRefresh, contextSynchronized ? 1 : 0));
        }
        

        ALC10.alcMakeContextCurrent(context);
      }
    } catch (LWJGLException e) {
      destroy();
      throw e;
    }
    
    ALC11.initialize();
    







    if (ALC10.alcIsExtensionPresent(device, "ALC_EXT_EFX")) {
      EFX10.initNativeStubs();
    }
  }
  





  public static void create()
    throws LWJGLException
  {
    create(null, 44100, 60, false);
  }
  


  public static void destroy()
  {
    if (context != null) {
      ALC10.alcMakeContextCurrent(null);
      ALC10.alcDestroyContext(context);
      context = null;
    }
    if (device != null) {
      boolean result = ALC10.alcCloseDevice(device);
      device = null;
    }
    resetNativeStubs(AL10.class);
    resetNativeStubs(AL11.class);
    resetNativeStubs(ALC10.class);
    resetNativeStubs(ALC11.class);
    resetNativeStubs(EFX10.class);
    
    if (created)
      nDestroy();
    created = false;
  }
  

  private static native void resetNativeStubs(Class paramClass);
  

  public static ALCcontext getContext()
  {
    return context;
  }
  


  public static ALCdevice getDevice()
  {
    return device;
  }
  
  static {}
}
