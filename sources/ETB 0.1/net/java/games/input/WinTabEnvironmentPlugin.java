package net.java.games.input;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import net.java.games.util.plugins.Plugin;
























public class WinTabEnvironmentPlugin
  extends ControllerEnvironment
  implements Plugin
{
  private static boolean supported = false;
  


  private final Controller[] controllers;
  


  static void loadLibrary(String lib_name)
  {
    AccessController.doPrivileged(new PrivilegedAction() {
      private final String val$lib_name;
      
      public final Object run() {
        try { String lib_path = System.getProperty("net.java.games.input.librarypath");
          if (lib_path != null) {
            System.load(lib_path + File.separator + System.mapLibraryName(val$lib_name));
          } else
            System.loadLibrary(val$lib_name);
        } catch (UnsatisfiedLinkError e) {
          e.printStackTrace();
          WinTabEnvironmentPlugin.access$002(false);
        }
        return null;
      }
    });
  }
  
  static String getPrivilegedProperty(String property) {
    (String)AccessController.doPrivileged(new PrivilegedAction() { private final String val$property;
      
      public Object run() { return System.getProperty(val$property); }
    });
  }
  

  static String getPrivilegedProperty(String property, final String default_value)
  {
    (String)AccessController.doPrivileged(new PrivilegedAction() { private final String val$property;
      
      public Object run() { return System.getProperty(val$property, default_value); }
    });
  }
  
  static
  {
    String osName = getPrivilegedProperty("os.name", "").trim();
    if (osName.startsWith("Windows")) {
      supported = true;
      loadLibrary("jinput-wintab");
    }
  }
  
  private final String val$default_value;
  private final List active_devices = new ArrayList();
  private final WinTabContext winTabContext;
  
  public WinTabEnvironmentPlugin()
  {
    if (isSupported()) {
      DummyWindow window = null;
      WinTabContext winTabContext = null;
      Controller[] controllers = new Controller[0];
      try {
        window = new DummyWindow();
        winTabContext = new WinTabContext(window);
        try {
          winTabContext.open();
          controllers = winTabContext.getControllers();
        } catch (Exception e) {
          window.destroy();
          throw e;
        }
      } catch (Exception e) {
        logln("Failed to enumerate devices: " + e.getMessage());
        e.printStackTrace();
      }
      this.controllers = controllers;
      this.winTabContext = winTabContext;
      AccessController.doPrivileged(new PrivilegedAction()
      {
        public final Object run() {
          Runtime.getRuntime().addShutdownHook(new WinTabEnvironmentPlugin.ShutdownHook(WinTabEnvironmentPlugin.this, null));
          return null;
        }
      });
    } else {
      this.winTabContext = null;
      this.controllers = new Controller[0];
    }
  }
  
  public boolean isSupported() {
    return supported;
  }
  

  public Controller[] getControllers() { return controllers; }
  
  private final class ShutdownHook extends Thread {
    ShutdownHook(WinTabEnvironmentPlugin.1 x1) { this(); }
    
    public final void run() {
      for (int i = 0; i < active_devices.size(); i++) {}
      


      winTabContext.close();
    }
    
    private ShutdownHook() {}
  }
}
