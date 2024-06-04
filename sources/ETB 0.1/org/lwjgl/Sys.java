package org.lwjgl;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import org.lwjgl.input.Mouse;















































public final class Sys
{
  private static final String JNI_LIBRARY_NAME = "lwjgl";
  private static final String VERSION = "2.9.4";
  private static final String POSTFIX64BIT = "64";
  
  private static void doLoadLibrary(String lib_name)
  {
    AccessController.doPrivileged(new PrivilegedAction() {
      public Object run() {
        String library_path = System.getProperty("org.lwjgl.librarypath");
        if (library_path != null) {
          System.load(library_path + File.separator + LWJGLUtil.mapLibraryName(val$lib_name));
        } else {
          System.loadLibrary(val$lib_name);
        }
        return null;
      }
    });
  }
  
  private static void loadLibrary(String lib_name)
  {
    String osArch = System.getProperty("os.arch");
    boolean try64First = (LWJGLUtil.getPlatform() != 2) && (("amd64".equals(osArch)) || ("x86_64".equals(osArch)));
    
    Error err = null;
    if (try64First) {
      try {
        doLoadLibrary(lib_name + "64");
        return;
      } catch (UnsatisfiedLinkError e) {
        err = e;
      }
    }
    
    try
    {
      doLoadLibrary(lib_name);
    } catch (UnsatisfiedLinkError e) {
      if (try64First) {
        throw err;
      }
      if (implementation.has64Bit()) {
        try {
          doLoadLibrary(lib_name + "64");
          return;
        } catch (UnsatisfiedLinkError e2) {
          LWJGLUtil.log("Failed to load 64 bit library: " + e2.getMessage());
        }
      }
      

      throw e;
    }
  }
  

  private static final SysImplementation implementation = ;
  static { loadLibrary("lwjgl");
    is64Bit = implementation.getPointerSize() == 8;
    
    int native_jni_version = implementation.getJNIVersion();
    int required_version = implementation.getRequiredJNIVersion();
    if (native_jni_version != required_version) {
      throw new LinkageError("Version mismatch: jar version is '" + required_version + "', native library version is '" + native_jni_version + "'");
    }
    implementation.setDebug(LWJGLUtil.DEBUG);
  }
  
  private static SysImplementation createImplementation() {
    switch () {
    case 1: 
      return new LinuxSysImplementation();
    case 3: 
      return new WindowsSysImplementation();
    case 2: 
      return new MacOSXSysImplementation();
    }
    throw new IllegalStateException("Unsupported platform");
  }
  









  public static String getVersion()
  {
    return "2.9.4";
  }
  






  public static boolean is64Bit()
  {
    return is64Bit;
  }
  





  public static long getTimerResolution()
  {
    return implementation.getTimerResolution();
  }
  






  public static long getTime()
  {
    return implementation.getTime() & 0x7FFFFFFFFFFFFFFF;
  }
  








  private static final boolean is64Bit;
  







  public static void alert(String title, String message)
  {
    boolean grabbed = Mouse.isGrabbed();
    if (grabbed) {
      Mouse.setGrabbed(false);
    }
    if (title == null)
      title = "";
    if (message == null)
      message = "";
    implementation.alert(title, message);
    if (grabbed) {
      Mouse.setGrabbed(true);
    }
  }
  













  public static boolean openURL(String url)
  {
    try
    {
      Class<?> serviceManagerClass = Class.forName("javax.jnlp.ServiceManager");
      Method lookupMethod = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction() {
        public Method run() throws Exception {
          return val$serviceManagerClass.getMethod("lookup", new Class[] { String.class });
        }
      });
      Object basicService = lookupMethod.invoke(serviceManagerClass, new Object[] { "javax.jnlp.BasicService" });
      Class<?> basicServiceClass = Class.forName("javax.jnlp.BasicService");
      Method showDocumentMethod = (Method)AccessController.doPrivileged(new PrivilegedExceptionAction() {
        public Method run() throws Exception {
          return val$basicServiceClass.getMethod("showDocument", new Class[] { URL.class });
        }
      });
      try {
        Boolean ret = (Boolean)showDocumentMethod.invoke(basicService, new Object[] { new URL(url) });
        return ret.booleanValue();
      } catch (MalformedURLException e) {
        e.printStackTrace(System.err);
        return false;
      }
      
      return implementation.openURL(url);
    }
    catch (Exception ue) {}
  }
  






  public static String getClipboard()
  {
    return implementation.getClipboard();
  }
  
  private Sys() {}
  
  public static void initialize() {}
}
