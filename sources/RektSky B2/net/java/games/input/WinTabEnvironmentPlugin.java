package net.java.games.input;

import net.java.games.util.plugins.*;
import java.io.*;
import java.security.*;
import java.util.*;

public class WinTabEnvironmentPlugin extends ControllerEnvironment implements Plugin
{
    private static boolean supported;
    private final Controller[] controllers;
    private final List<WinTabDevice> active_devices;
    private final WinTabContext winTabContext;
    
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
                WinTabEnvironmentPlugin.supported = false;
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
    
    public WinTabEnvironmentPlugin() {
        this.active_devices = new ArrayList<WinTabDevice>();
        if (this.isSupported()) {
            WinTabContext winTabContext = null;
            Controller[] controllers = new Controller[0];
            try {
                final DummyWindow window = new DummyWindow();
                winTabContext = new WinTabContext(window);
                try {
                    winTabContext.open();
                    controllers = winTabContext.getControllers();
                }
                catch (Exception e) {
                    window.destroy();
                    throw e;
                }
            }
            catch (Exception e) {
                ControllerEnvironment.log("Failed to enumerate devices: " + e.getMessage());
                e.printStackTrace();
            }
            this.controllers = controllers;
            this.winTabContext = winTabContext;
            AccessController.doPrivileged(() -> {
                Runtime.getRuntime().addShutdownHook(new ShutdownHook());
                return null;
            });
        }
        else {
            this.winTabContext = null;
            this.controllers = new Controller[0];
        }
    }
    
    @Override
    public boolean isSupported() {
        return WinTabEnvironmentPlugin.supported;
    }
    
    @Override
    public Controller[] getControllers() {
        return this.controllers;
    }
    
    static {
        WinTabEnvironmentPlugin.supported = false;
        final String osName = getPrivilegedProperty("os.name", "").trim();
        if (osName.startsWith("Windows")) {
            WinTabEnvironmentPlugin.supported = true;
            loadLibrary("jinput-wintab");
        }
    }
    
    private final class ShutdownHook extends Thread
    {
        @Override
        public final void run() {
            for (int i = 0; i < WinTabEnvironmentPlugin.this.active_devices.size(); ++i) {}
            WinTabEnvironmentPlugin.this.winTabContext.close();
        }
    }
}
