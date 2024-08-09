/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.RawDevice;
import net.java.games.input.RawDeviceInfo;
import net.java.games.input.RawInputEventQueue;
import net.java.games.input.SetupAPIDevice;
import net.java.games.util.plugins.Plugin;

public final class RawInputEnvironmentPlugin
extends ControllerEnvironment
implements Plugin {
    private static boolean supported = false;
    private final Controller[] controllers;

    static void loadLibrary(String string) {
        AccessController.doPrivileged(new PrivilegedAction(string){
            private final String val$lib_name;
            {
                this.val$lib_name = string;
            }

            public final Object run() {
                try {
                    String string = System.getProperty("net.java.games.input.librarypath");
                    if (string != null) {
                        System.load(string + File.separator + System.mapLibraryName(this.val$lib_name));
                    } else {
                        System.loadLibrary(this.val$lib_name);
                    }
                } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                    unsatisfiedLinkError.printStackTrace();
                    RawInputEnvironmentPlugin.access$002(false);
                }
                return null;
            }
        });
    }

    static String getPrivilegedProperty(String string) {
        return (String)AccessController.doPrivileged(new PrivilegedAction(string){
            private final String val$property;
            {
                this.val$property = string;
            }

            public Object run() {
                return System.getProperty(this.val$property);
            }
        });
    }

    static String getPrivilegedProperty(String string, String string2) {
        return (String)AccessController.doPrivileged(new PrivilegedAction(string, string2){
            private final String val$property;
            private final String val$default_value;
            {
                this.val$property = string;
                this.val$default_value = string2;
            }

            public Object run() {
                return System.getProperty(this.val$property, this.val$default_value);
            }
        });
    }

    public RawInputEnvironmentPlugin() {
        Controller[] controllerArray = new Controller[]{};
        if (this.isSupported()) {
            try {
                RawInputEventQueue rawInputEventQueue = new RawInputEventQueue();
                controllerArray = this.enumControllers(rawInputEventQueue);
            } catch (IOException iOException) {
                RawInputEnvironmentPlugin.logln("Failed to enumerate devices: " + iOException.getMessage());
            }
        }
        this.controllers = controllerArray;
    }

    public final Controller[] getControllers() {
        return this.controllers;
    }

    private static final SetupAPIDevice lookupSetupAPIDevice(String string, List list) {
        string = string.replaceAll("#", "\\\\").toUpperCase();
        for (int i = 0; i < list.size(); ++i) {
            SetupAPIDevice setupAPIDevice = (SetupAPIDevice)list.get(i);
            if (string.indexOf(setupAPIDevice.getInstanceId().toUpperCase()) == -1) continue;
            return setupAPIDevice;
        }
        return null;
    }

    private static final void createControllersFromDevices(RawInputEventQueue rawInputEventQueue, List list, List list2, List list3) throws IOException {
        ArrayList<RawDevice> arrayList = new ArrayList<RawDevice>();
        for (int i = 0; i < list2.size(); ++i) {
            RawDeviceInfo rawDeviceInfo;
            Controller controller;
            RawDevice rawDevice = (RawDevice)list2.get(i);
            SetupAPIDevice setupAPIDevice = RawInputEnvironmentPlugin.lookupSetupAPIDevice(rawDevice.getName(), list3);
            if (setupAPIDevice == null || (controller = (rawDeviceInfo = rawDevice.getInfo()).createControllerFromDevice(rawDevice, setupAPIDevice)) == null) continue;
            list.add(controller);
            arrayList.add(rawDevice);
        }
        rawInputEventQueue.start(arrayList);
    }

    private static final native void enumerateDevices(RawInputEventQueue var0, List var1) throws IOException;

    private final Controller[] enumControllers(RawInputEventQueue rawInputEventQueue) throws IOException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        RawInputEnvironmentPlugin.enumerateDevices(rawInputEventQueue, arrayList2);
        List list = RawInputEnvironmentPlugin.enumSetupAPIDevices();
        RawInputEnvironmentPlugin.createControllersFromDevices(rawInputEventQueue, arrayList, arrayList2, list);
        Controller[] controllerArray = new Controller[arrayList.size()];
        arrayList.toArray(controllerArray);
        return controllerArray;
    }

    public boolean isSupported() {
        return supported;
    }

    private static final List enumSetupAPIDevices() throws IOException {
        ArrayList arrayList = new ArrayList();
        RawInputEnvironmentPlugin.nEnumSetupAPIDevices(RawInputEnvironmentPlugin.getKeyboardClassGUID(), arrayList);
        RawInputEnvironmentPlugin.nEnumSetupAPIDevices(RawInputEnvironmentPlugin.getMouseClassGUID(), arrayList);
        return arrayList;
    }

    private static final native void nEnumSetupAPIDevices(byte[] var0, List var1) throws IOException;

    private static final native byte[] getKeyboardClassGUID();

    private static final native byte[] getMouseClassGUID();

    static boolean access$002(boolean bl) {
        supported = bl;
        return supported;
    }

    static {
        String string = RawInputEnvironmentPlugin.getPrivilegedProperty("os.name", "").trim();
        if (string.startsWith("Windows")) {
            supported = true;
            if ("x86".equals(RawInputEnvironmentPlugin.getPrivilegedProperty("os.arch"))) {
                RawInputEnvironmentPlugin.loadLibrary("jinput-raw");
            } else {
                RawInputEnvironmentPlugin.loadLibrary("jinput-raw_64");
            }
        }
    }
}

