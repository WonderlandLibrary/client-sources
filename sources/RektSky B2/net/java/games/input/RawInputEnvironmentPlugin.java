package net.java.games.input;

import net.java.games.util.plugins.*;
import java.security.*;
import java.io.*;
import java.util.*;

public final class RawInputEnvironmentPlugin extends ControllerEnvironment implements Plugin
{
    private static boolean supported;
    private final Controller[] controllers;
    
    static void loadLibrary(final String lib_name) {
        String lib_path;
        AccessController.doPrivileged(() -> {
            try {
                lib_path = System.getProperty("net.java.games.input.librarypath");
                if (lib_path != null) {
                    System.load(lib_path + File.separator + System.mapLibraryName(lib_name));
                }
                else {
                    System.loadLibrary(lib_name);
                }
            }
            catch (UnsatisfiedLinkError e) {
                e.printStackTrace();
                RawInputEnvironmentPlugin.supported = false;
            }
            return null;
        });
    }
    
    static String getPrivilegedProperty(final String property) {
        return AccessController.doPrivileged(() -> System.getProperty(property));
    }
    
    static String getPrivilegedProperty(final String property, final String default_value) {
        return AccessController.doPrivileged(() -> System.getProperty(property, default_value));
    }
    
    public RawInputEnvironmentPlugin() {
        Controller[] controllers = new Controller[0];
        if (this.isSupported()) {
            try {
                final RawInputEventQueue queue = new RawInputEventQueue();
                controllers = this.enumControllers(queue);
            }
            catch (IOException e) {
                ControllerEnvironment.log("Failed to enumerate devices: " + e.getMessage());
            }
        }
        this.controllers = controllers;
    }
    
    @Override
    public final Controller[] getControllers() {
        return this.controllers;
    }
    
    private static final SetupAPIDevice lookupSetupAPIDevice(String device_name, final List<SetupAPIDevice> setupapi_devices) {
        device_name = device_name.replaceAll("#", "\\\\").toUpperCase();
        for (int i = 0; i < setupapi_devices.size(); ++i) {
            final SetupAPIDevice device = setupapi_devices.get(i);
            if (device_name.contains(device.getInstanceId().toUpperCase())) {
                return device;
            }
        }
        return null;
    }
    
    private static final void createControllersFromDevices(final RawInputEventQueue queue, final List<Controller> controllers, final List<RawDevice> devices, final List<SetupAPIDevice> setupapi_devices) throws IOException {
        final List<RawDevice> active_devices = new ArrayList<RawDevice>();
        for (int i = 0; i < devices.size(); ++i) {
            final RawDevice device = devices.get(i);
            final SetupAPIDevice setupapi_device = lookupSetupAPIDevice(device.getName(), setupapi_devices);
            if (setupapi_device != null) {
                final RawDeviceInfo info = device.getInfo();
                final Controller controller = info.createControllerFromDevice(device, setupapi_device);
                if (controller != null) {
                    controllers.add(controller);
                    active_devices.add(device);
                }
            }
        }
        queue.start(active_devices);
    }
    
    private static final native void enumerateDevices(final RawInputEventQueue p0, final List<RawDevice> p1) throws IOException;
    
    private final Controller[] enumControllers(final RawInputEventQueue queue) throws IOException {
        final List<Controller> controllers = new ArrayList<Controller>();
        final List<RawDevice> devices = new ArrayList<RawDevice>();
        enumerateDevices(queue, devices);
        final List<SetupAPIDevice> setupapi_devices = enumSetupAPIDevices();
        createControllersFromDevices(queue, controllers, devices, setupapi_devices);
        final Controller[] controllers_array = new Controller[controllers.size()];
        controllers.toArray(controllers_array);
        return controllers_array;
    }
    
    @Override
    public boolean isSupported() {
        return RawInputEnvironmentPlugin.supported;
    }
    
    private static final List<SetupAPIDevice> enumSetupAPIDevices() throws IOException {
        final List<SetupAPIDevice> devices = new ArrayList<SetupAPIDevice>();
        nEnumSetupAPIDevices(getKeyboardClassGUID(), devices);
        nEnumSetupAPIDevices(getMouseClassGUID(), devices);
        return devices;
    }
    
    private static final native void nEnumSetupAPIDevices(final byte[] p0, final List<SetupAPIDevice> p1) throws IOException;
    
    private static final native byte[] getKeyboardClassGUID();
    
    private static final native byte[] getMouseClassGUID();
    
    static {
        RawInputEnvironmentPlugin.supported = false;
        final String osName = getPrivilegedProperty("os.name", "").trim();
        if (osName.startsWith("Windows")) {
            RawInputEnvironmentPlugin.supported = true;
            if ("x86".equals(getPrivilegedProperty("os.arch"))) {
                loadLibrary("jinput-raw");
            }
            else {
                loadLibrary("jinput-raw_64");
            }
        }
    }
}
