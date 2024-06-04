package org.lwjgl.opencl;

import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.MemoryUtil;
import org.lwjgl.Sys;




















































public final class CL
{
  private static boolean created;
  
  private CL() {}
  
  private static native void nCreate(String paramString)
    throws LWJGLException;
  
  private static native void nCreateDefault()
    throws LWJGLException;
  
  private static native void nDestroy();
  
  public static boolean isCreated()
  {
    return created;
  }
  
  public static void create() throws LWJGLException {
    if (created) {
      return;
    }
    
    String libname;
    String[] library_names;
    switch (LWJGLUtil.getPlatform()) {
    case 3: 
      libname = "OpenCL";
      library_names = new String[] { "OpenCL.dll" };
      break;
    case 1: 
      libname = "OpenCL";
      library_names = new String[] { "libOpenCL64.so", "libOpenCL.so" };
      break;
    case 2: 
      libname = "OpenCL";
      library_names = new String[] { "OpenCL.dylib" };
      break;
    default: 
      throw new LWJGLException("Unknown platform: " + LWJGLUtil.getPlatform());
    }
    
    String[] oclPaths = LWJGLUtil.getLibraryPaths(libname, library_names, CL.class.getClassLoader());
    LWJGLUtil.log("Found " + oclPaths.length + " OpenCL paths");
    for (String oclPath : oclPaths) {
      try {
        nCreate(oclPath);
        created = true;
      }
      catch (LWJGLException e) {
        LWJGLUtil.log("Failed to load " + oclPath + ": " + e.getMessage());
      }
    }
    
    if ((!created) && (LWJGLUtil.getPlatform() == 2))
    {
      nCreateDefault();
      created = true;
    }
    
    if (!created) {
      throw new LWJGLException("Could not locate OpenCL library.");
    }
    if (!CLCapabilities.OpenCL10) {
      throw new RuntimeException("OpenCL 1.0 not supported.");
    }
  }
  



  public static void destroy() {}
  



  static long getFunctionAddress(String[] aliases)
  {
    for (String aliase : aliases) {
      long address = getFunctionAddress(aliase);
      if (address != 0L)
        return address;
    }
    return 0L;
  }
  
  static long getFunctionAddress(String name)
  {
    ByteBuffer buffer = MemoryUtil.encodeASCII(name);
    return ngetFunctionAddress(MemoryUtil.getAddress(buffer));
  }
  
  private static native long ngetFunctionAddress(long paramLong);
  
  static native ByteBuffer getHostBuffer(long paramLong, int paramInt);
  
  private static native void resetNativeStubs(Class paramClass);
  
  static {}
}
