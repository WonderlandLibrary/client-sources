package net.java.games.input;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import net.java.games.util.plugins.Plugin;











































public final class DirectInputEnvironmentPlugin
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
          DirectInputEnvironmentPlugin.access$002(false);
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
      if ("x86".equals(getPrivilegedProperty("os.arch"))) {
        loadLibrary("jinput-dx8");
      } else {
        loadLibrary("jinput-dx8_64");
      }
    }
  }
  
  private final String val$default_value;
  private final List active_devices = new ArrayList();
  private final DummyWindow window;
  
  public DirectInputEnvironmentPlugin()
  {
    DummyWindow window = null;
    Controller[] controllers = new Controller[0];
    if (isSupported()) {
      try {
        window = new DummyWindow();
        try {
          controllers = enumControllers(window);
        } catch (IOException e) {
          window.destroy();
          throw e;
        }
      } catch (IOException e) {
        logln("Failed to enumerate devices: " + e.getMessage());
      }
      this.window = window;
      this.controllers = controllers;
      AccessController.doPrivileged(new PrivilegedAction()
      {
        public final Object run() {
          Runtime.getRuntime().addShutdownHook(new DirectInputEnvironmentPlugin.ShutdownHook(DirectInputEnvironmentPlugin.this, null));
          return null;
        }
      });
    }
    else
    {
      this.window = null;
      this.controllers = controllers;
    }
  }
  
  public final Controller[] getControllers() {
    return controllers;
  }
  
  private final Component[] createComponents(IDirectInputDevice device, boolean map_mouse_buttons) {
    List device_objects = device.getObjects();
    List controller_components = new ArrayList();
    for (int i = 0; i < device_objects.size(); i++) {
      DIDeviceObject device_object = (DIDeviceObject)device_objects.get(i);
      Component.Identifier identifier = device_object.getIdentifier();
      if (identifier != null)
      {
        if ((map_mouse_buttons) && ((identifier instanceof Component.Identifier.Button))) {
          identifier = DIIdentifierMap.mapMouseButtonIdentifier((Component.Identifier.Button)identifier);
        }
        DIComponent component = new DIComponent(identifier, device_object);
        controller_components.add(component);
        device.registerComponent(device_object, component);
      } }
    Component[] components = new Component[controller_components.size()];
    controller_components.toArray(components);
    return components;
  }
  
  private final Mouse createMouseFromDevice(IDirectInputDevice device) {
    Component[] components = createComponents(device, true);
    Mouse mouse = new DIMouse(device, components, new Controller[0], device.getRumblers());
    if ((mouse.getX() != null) && (mouse.getY() != null) && (mouse.getPrimaryButton() != null)) {
      return mouse;
    }
    return null;
  }
  
  private final AbstractController createControllerFromDevice(IDirectInputDevice device, Controller.Type type) {
    Component[] components = createComponents(device, false);
    AbstractController controller = new DIAbstractController(device, components, new Controller[0], device.getRumblers(), type);
    return controller;
  }
  
  private final Keyboard createKeyboardFromDevice(IDirectInputDevice device) {
    Component[] components = createComponents(device, false);
    return new DIKeyboard(device, components, new Controller[0], device.getRumblers());
  }
  
  private final Controller createControllerFromDevice(IDirectInputDevice device) {
    switch (device.getType()) {
    case 18: 
      return createMouseFromDevice(device);
    case 19: 
      return createKeyboardFromDevice(device);
    case 21: 
      return createControllerFromDevice(device, Controller.Type.GAMEPAD);
    case 22: 
      return createControllerFromDevice(device, Controller.Type.WHEEL);
    

    case 20: 
    case 23: 
    case 24: 
      return createControllerFromDevice(device, Controller.Type.STICK);
    }
    return createControllerFromDevice(device, Controller.Type.UNKNOWN);
  }
  
  private final Controller[] enumControllers(DummyWindow window) throws IOException
  {
    List controllers = new ArrayList();
    IDirectInput dinput = new IDirectInput(window);
    try {
      List devices = dinput.getDevices();
      for (int i = 0; i < devices.size(); i++) {
        IDirectInputDevice device = (IDirectInputDevice)devices.get(i);
        Controller controller = createControllerFromDevice(device);
        if (controller != null) {
          controllers.add(controller);
          active_devices.add(device);
        } else {
          device.release();
        }
      }
    } finally { dinput.release();
    }
    Controller[] controllers_array = new Controller[controllers.size()];
    controllers.toArray(controllers_array);
    return controllers_array;
  }
  
  private final class ShutdownHook extends Thread { ShutdownHook(DirectInputEnvironmentPlugin.1 x1) { this(); }
    
    public final void run() {
      for (int i = 0; i < active_devices.size(); i++) {
        IDirectInputDevice device = (IDirectInputDevice)active_devices.get(i);
        device.release();
      }
    }
    
    private ShutdownHook() {}
  }
  
  public boolean isSupported()
  {
    return supported;
  }
}
