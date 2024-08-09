/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import net.java.games.input.AbstractController;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.GenericDesktopUsage;
import net.java.games.input.Keyboard;
import net.java.games.input.Mouse;
import net.java.games.input.OSXAbstractController;
import net.java.games.input.OSXComponent;
import net.java.games.input.OSXHIDDevice;
import net.java.games.input.OSXHIDDeviceIterator;
import net.java.games.input.OSXHIDElement;
import net.java.games.input.OSXHIDQueue;
import net.java.games.input.OSXKeyboard;
import net.java.games.input.OSXMouse;
import net.java.games.input.Rumbler;
import net.java.games.input.UsagePage;
import net.java.games.input.UsagePair;
import net.java.games.util.plugins.Plugin;

public final class OSXEnvironmentPlugin
extends ControllerEnvironment
implements Plugin {
    private static boolean supported = false;
    private final Controller[] controllers = this.isSupported() ? OSXEnvironmentPlugin.enumerateControllers() : new Controller[0];

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
                    OSXEnvironmentPlugin.access$002(false);
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

    private static final boolean isMacOSXEqualsOrBetterThan(int n, int n2) {
        int n3;
        int n4;
        String string = System.getProperty("os.version");
        StringTokenizer stringTokenizer = new StringTokenizer(string, ".");
        try {
            String string2 = stringTokenizer.nextToken();
            String string3 = stringTokenizer.nextToken();
            n4 = Integer.parseInt(string2);
            n3 = Integer.parseInt(string3);
        } catch (Exception exception) {
            OSXEnvironmentPlugin.logln("Exception occurred while trying to determine OS version: " + exception);
            return true;
        }
        return n4 > n || n4 == n && n3 >= n2;
    }

    public final Controller[] getControllers() {
        return this.controllers;
    }

    public boolean isSupported() {
        return supported;
    }

    private static final void addElements(OSXHIDQueue oSXHIDQueue, List list, List list2, boolean bl) throws IOException {
        Iterator iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            OSXHIDElement oSXHIDElement = (OSXHIDElement)iterator2.next();
            Component.Identifier identifier = oSXHIDElement.getIdentifier();
            if (identifier == null) continue;
            if (bl) {
                if (identifier == Component.Identifier.Button._0) {
                    identifier = Component.Identifier.Button.LEFT;
                } else if (identifier == Component.Identifier.Button._1) {
                    identifier = Component.Identifier.Button.RIGHT;
                } else if (identifier == Component.Identifier.Button._2) {
                    identifier = Component.Identifier.Button.MIDDLE;
                }
            }
            OSXComponent oSXComponent = new OSXComponent(identifier, oSXHIDElement);
            list2.add(oSXComponent);
            oSXHIDQueue.addElement(oSXHIDElement, oSXComponent);
        }
    }

    private static final Keyboard createKeyboardFromDevice(OSXHIDDevice oSXHIDDevice, List list) throws IOException {
        ArrayList arrayList = new ArrayList();
        OSXHIDQueue oSXHIDQueue = oSXHIDDevice.createQueue(32);
        try {
            OSXEnvironmentPlugin.addElements(oSXHIDQueue, list, arrayList, false);
        } catch (IOException iOException) {
            oSXHIDQueue.release();
            throw iOException;
        }
        Component[] componentArray = new Component[arrayList.size()];
        arrayList.toArray(componentArray);
        OSXKeyboard oSXKeyboard = new OSXKeyboard(oSXHIDDevice, oSXHIDQueue, componentArray, new Controller[0], new Rumbler[0]);
        return oSXKeyboard;
    }

    private static final Mouse createMouseFromDevice(OSXHIDDevice oSXHIDDevice, List list) throws IOException {
        ArrayList arrayList = new ArrayList();
        OSXHIDQueue oSXHIDQueue = oSXHIDDevice.createQueue(32);
        try {
            OSXEnvironmentPlugin.addElements(oSXHIDQueue, list, arrayList, true);
        } catch (IOException iOException) {
            oSXHIDQueue.release();
            throw iOException;
        }
        Component[] componentArray = new Component[arrayList.size()];
        arrayList.toArray(componentArray);
        OSXMouse oSXMouse = new OSXMouse(oSXHIDDevice, oSXHIDQueue, componentArray, new Controller[0], new Rumbler[0]);
        if (oSXMouse.getPrimaryButton() != null && oSXMouse.getX() != null && oSXMouse.getY() != null) {
            return oSXMouse;
        }
        oSXHIDQueue.release();
        return null;
    }

    private static final AbstractController createControllerFromDevice(OSXHIDDevice oSXHIDDevice, List list, Controller.Type type) throws IOException {
        ArrayList arrayList = new ArrayList();
        OSXHIDQueue oSXHIDQueue = oSXHIDDevice.createQueue(32);
        try {
            OSXEnvironmentPlugin.addElements(oSXHIDQueue, list, arrayList, false);
        } catch (IOException iOException) {
            oSXHIDQueue.release();
            throw iOException;
        }
        Component[] componentArray = new Component[arrayList.size()];
        arrayList.toArray(componentArray);
        OSXAbstractController oSXAbstractController = new OSXAbstractController(oSXHIDDevice, oSXHIDQueue, componentArray, new Controller[0], new Rumbler[0], type);
        return oSXAbstractController;
    }

    private static final void createControllersFromDevice(OSXHIDDevice oSXHIDDevice, List list) throws IOException {
        AbstractController abstractController;
        UsagePair usagePair = oSXHIDDevice.getUsagePair();
        if (usagePair == null) {
            return;
        }
        List list2 = oSXHIDDevice.getElements();
        if (usagePair.getUsagePage() == UsagePage.GENERIC_DESKTOP && (usagePair.getUsage() == GenericDesktopUsage.MOUSE || usagePair.getUsage() == GenericDesktopUsage.POINTER)) {
            Mouse mouse = OSXEnvironmentPlugin.createMouseFromDevice(oSXHIDDevice, list2);
            if (mouse != null) {
                list.add(mouse);
            }
        } else if (usagePair.getUsagePage() == UsagePage.GENERIC_DESKTOP && (usagePair.getUsage() == GenericDesktopUsage.KEYBOARD || usagePair.getUsage() == GenericDesktopUsage.KEYPAD)) {
            Keyboard keyboard = OSXEnvironmentPlugin.createKeyboardFromDevice(oSXHIDDevice, list2);
            if (keyboard != null) {
                list.add(keyboard);
            }
        } else if (usagePair.getUsagePage() == UsagePage.GENERIC_DESKTOP && usagePair.getUsage() == GenericDesktopUsage.JOYSTICK) {
            AbstractController abstractController2 = OSXEnvironmentPlugin.createControllerFromDevice(oSXHIDDevice, list2, Controller.Type.STICK);
            if (abstractController2 != null) {
                list.add(abstractController2);
            }
        } else if (usagePair.getUsagePage() == UsagePage.GENERIC_DESKTOP && usagePair.getUsage() == GenericDesktopUsage.MULTI_AXIS_CONTROLLER) {
            AbstractController abstractController3 = OSXEnvironmentPlugin.createControllerFromDevice(oSXHIDDevice, list2, Controller.Type.STICK);
            if (abstractController3 != null) {
                list.add(abstractController3);
            }
        } else if (usagePair.getUsagePage() == UsagePage.GENERIC_DESKTOP && usagePair.getUsage() == GenericDesktopUsage.GAME_PAD && (abstractController = OSXEnvironmentPlugin.createControllerFromDevice(oSXHIDDevice, list2, Controller.Type.GAMEPAD)) != null) {
            list.add(abstractController);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static final Controller[] enumerateControllers() {
        Controller[] controllerArray;
        ArrayList arrayList;
        block12: {
            arrayList = new ArrayList();
            try {
                controllerArray = new OSXHIDDeviceIterator();
                block9: while (true) {
                    while (true) {
                        try {
                            OSXHIDDevice oSXHIDDevice = controllerArray.next();
                            if (oSXHIDDevice == null) {
                                break block12;
                            }
                            boolean bl = false;
                            try {
                                int n = arrayList.size();
                                OSXEnvironmentPlugin.createControllersFromDevice(oSXHIDDevice, arrayList);
                                bl = n != arrayList.size();
                            } catch (IOException iOException) {
                                OSXEnvironmentPlugin.logln("Failed to create controllers from device: " + oSXHIDDevice.getProductName());
                            }
                            if (bl) continue block9;
                            oSXHIDDevice.release();
                            continue block9;
                        } catch (IOException iOException) {
                            OSXEnvironmentPlugin.logln("Failed to enumerate device: " + iOException.getMessage());
                            continue;
                        }
                        break;
                    }
                }
                finally {
                    controllerArray.close();
                }
            } catch (IOException iOException) {
                OSXEnvironmentPlugin.log("Failed to enumerate devices: " + iOException.getMessage());
                return new Controller[0];
            }
        }
        controllerArray = new Controller[arrayList.size()];
        arrayList.toArray(controllerArray);
        return controllerArray;
    }

    static boolean access$002(boolean bl) {
        supported = bl;
        return supported;
    }

    static {
        String string = OSXEnvironmentPlugin.getPrivilegedProperty("os.name", "").trim();
        if (string.equals("Mac OS X")) {
            supported = true;
            OSXEnvironmentPlugin.loadLibrary("jinput-osx");
        }
    }
}

