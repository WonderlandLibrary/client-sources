/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.java.games.input.AbstractComponent;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Keyboard;
import net.java.games.input.LinuxAbstractController;
import net.java.games.input.LinuxCombinedController;
import net.java.games.input.LinuxComponent;
import net.java.games.input.LinuxDevice;
import net.java.games.input.LinuxDeviceTask;
import net.java.games.input.LinuxDeviceThread;
import net.java.games.input.LinuxEventComponent;
import net.java.games.input.LinuxEventDevice;
import net.java.games.input.LinuxJoystickAbstractController;
import net.java.games.input.LinuxJoystickAxis;
import net.java.games.input.LinuxJoystickButton;
import net.java.games.input.LinuxJoystickDevice;
import net.java.games.input.LinuxJoystickPOV;
import net.java.games.input.LinuxKeyboard;
import net.java.games.input.LinuxMouse;
import net.java.games.input.LinuxNativeTypesMap;
import net.java.games.input.LinuxPOV;
import net.java.games.input.Mouse;
import net.java.games.input.Rumbler;
import net.java.games.util.plugins.Plugin;

public final class LinuxEnvironmentPlugin
extends ControllerEnvironment
implements Plugin {
    private static final String LIBNAME = "jinput-linux";
    private static final String POSTFIX64BIT = "64";
    private static boolean supported = false;
    private final Controller[] controllers;
    private final List devices = new ArrayList();
    private static final LinuxDeviceThread device_thread = new LinuxDeviceThread();

    static void loadLibrary(String string) {
        AccessController.doPrivileged(new PrivilegedAction(string){
            private final String val$lib_name;
            {
                this.val$lib_name = string;
            }

            public final Object run() {
                String string = System.getProperty("net.java.games.input.librarypath");
                try {
                    if (string != null) {
                        System.load(string + File.separator + System.mapLibraryName(this.val$lib_name));
                    } else {
                        System.loadLibrary(this.val$lib_name);
                    }
                } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                    ControllerEnvironment.logln("Failed to load library: " + unsatisfiedLinkError.getMessage());
                    unsatisfiedLinkError.printStackTrace();
                    LinuxEnvironmentPlugin.access$002(false);
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

    public static final Object execute(LinuxDeviceTask linuxDeviceTask) throws IOException {
        return device_thread.execute(linuxDeviceTask);
    }

    public LinuxEnvironmentPlugin() {
        if (this.isSupported()) {
            this.controllers = this.enumerateControllers();
            LinuxEnvironmentPlugin.logln("Linux plugin claims to have found " + this.controllers.length + " controllers");
            AccessController.doPrivileged(new PrivilegedAction(this){
                private final LinuxEnvironmentPlugin this$0;
                {
                    this.this$0 = linuxEnvironmentPlugin;
                }

                public final Object run() {
                    Runtime.getRuntime().addShutdownHook(new ShutdownHook(this.this$0, null));
                    return null;
                }
            });
        } else {
            this.controllers = new Controller[0];
        }
    }

    public final Controller[] getControllers() {
        return this.controllers;
    }

    private static final Component[] createComponents(List list, LinuxEventDevice linuxEventDevice) {
        Object object;
        LinuxEventComponent linuxEventComponent;
        int n;
        LinuxEventComponent[][] linuxEventComponentArray = new LinuxEventComponent[4][2];
        ArrayList<LinuxComponent> arrayList = new ArrayList<LinuxComponent>();
        for (n = 0; n < list.size(); ++n) {
            linuxEventComponent = (LinuxEventComponent)list.get(n);
            object = linuxEventComponent.getIdentifier();
            if (object == Component.Identifier.Axis.POV) {
                int n2 = linuxEventComponent.getDescriptor().getCode();
                switch (n2) {
                    case 16: {
                        linuxEventComponentArray[0][0] = linuxEventComponent;
                        break;
                    }
                    case 17: {
                        linuxEventComponentArray[0][1] = linuxEventComponent;
                        break;
                    }
                    case 18: {
                        linuxEventComponentArray[5][0] = linuxEventComponent;
                        break;
                    }
                    case 19: {
                        linuxEventComponentArray[5][1] = linuxEventComponent;
                        break;
                    }
                    case 20: {
                        linuxEventComponentArray[5][0] = linuxEventComponent;
                        break;
                    }
                    case 21: {
                        linuxEventComponentArray[5][1] = linuxEventComponent;
                        break;
                    }
                    case 22: {
                        linuxEventComponentArray[5][0] = linuxEventComponent;
                        break;
                    }
                    case 23: {
                        linuxEventComponentArray[5][1] = linuxEventComponent;
                        break;
                    }
                    default: {
                        LinuxEnvironmentPlugin.logln("Unknown POV instance: " + n2);
                        break;
                    }
                }
                continue;
            }
            if (object == null) continue;
            LinuxComponent linuxComponent = new LinuxComponent(linuxEventComponent);
            arrayList.add(linuxComponent);
            linuxEventDevice.registerComponent(linuxEventComponent.getDescriptor(), linuxComponent);
        }
        for (n = 0; n < linuxEventComponentArray.length; ++n) {
            linuxEventComponent = linuxEventComponentArray[n][0];
            object = linuxEventComponentArray[n][1];
            if (linuxEventComponent == null || object == null) continue;
            LinuxPOV linuxPOV = new LinuxPOV(linuxEventComponent, (LinuxEventComponent)object);
            arrayList.add(linuxPOV);
            linuxEventDevice.registerComponent(linuxEventComponent.getDescriptor(), linuxPOV);
            linuxEventDevice.registerComponent(((LinuxEventComponent)object).getDescriptor(), linuxPOV);
        }
        Component[] componentArray = new Component[arrayList.size()];
        arrayList.toArray(componentArray);
        return componentArray;
    }

    private static final Mouse createMouseFromDevice(LinuxEventDevice linuxEventDevice, Component[] componentArray) throws IOException {
        LinuxMouse linuxMouse = new LinuxMouse(linuxEventDevice, componentArray, new Controller[0], linuxEventDevice.getRumblers());
        if (linuxMouse.getX() != null && linuxMouse.getY() != null && linuxMouse.getPrimaryButton() != null) {
            return linuxMouse;
        }
        return null;
    }

    private static final Keyboard createKeyboardFromDevice(LinuxEventDevice linuxEventDevice, Component[] componentArray) throws IOException {
        LinuxKeyboard linuxKeyboard = new LinuxKeyboard(linuxEventDevice, componentArray, new Controller[0], linuxEventDevice.getRumblers());
        return linuxKeyboard;
    }

    private static final Controller createJoystickFromDevice(LinuxEventDevice linuxEventDevice, Component[] componentArray, Controller.Type type) throws IOException {
        LinuxAbstractController linuxAbstractController = new LinuxAbstractController(linuxEventDevice, componentArray, new Controller[0], linuxEventDevice.getRumblers(), type);
        return linuxAbstractController;
    }

    private static final Controller createControllerFromDevice(LinuxEventDevice linuxEventDevice) throws IOException {
        List list = linuxEventDevice.getComponents();
        Component[] componentArray = LinuxEnvironmentPlugin.createComponents(list, linuxEventDevice);
        Controller.Type type = linuxEventDevice.getType();
        if (type == Controller.Type.MOUSE) {
            return LinuxEnvironmentPlugin.createMouseFromDevice(linuxEventDevice, componentArray);
        }
        if (type == Controller.Type.KEYBOARD) {
            return LinuxEnvironmentPlugin.createKeyboardFromDevice(linuxEventDevice, componentArray);
        }
        if (type == Controller.Type.STICK || type == Controller.Type.GAMEPAD) {
            return LinuxEnvironmentPlugin.createJoystickFromDevice(linuxEventDevice, componentArray, type);
        }
        return null;
    }

    private final Controller[] enumerateControllers() {
        ArrayList<LinuxCombinedController> arrayList = new ArrayList<LinuxCombinedController>();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        this.enumerateEventControllers(arrayList2);
        this.enumerateJoystickControllers(arrayList3);
        block0: for (int i = 0; i < arrayList2.size(); ++i) {
            for (int j = 0; j < arrayList3.size(); ++j) {
                Component[] componentArray;
                Component[] componentArray2;
                Controller controller = (Controller)arrayList2.get(i);
                Controller controller2 = (Controller)arrayList3.get(j);
                if (!controller.getName().equals(controller2.getName()) || (componentArray2 = controller.getComponents()).length != (componentArray = controller2.getComponents()).length) continue;
                boolean bl = false;
                for (int k = 0; k < componentArray2.length; ++k) {
                    if (componentArray2[k].getIdentifier() == componentArray[k].getIdentifier()) continue;
                    bl = true;
                }
                if (bl) continue;
                arrayList.add(new LinuxCombinedController((LinuxAbstractController)arrayList2.remove(i), (LinuxJoystickAbstractController)arrayList3.remove(j)));
                --i;
                --j;
                continue block0;
            }
        }
        arrayList.addAll(arrayList2);
        arrayList.addAll(arrayList3);
        Controller[] controllerArray = new Controller[arrayList.size()];
        arrayList.toArray(controllerArray);
        return controllerArray;
    }

    private static final Component.Identifier.Button getButtonIdentifier(int n) {
        switch (n) {
            case 0: {
                return Component.Identifier.Button._0;
            }
            case 1: {
                return Component.Identifier.Button._1;
            }
            case 2: {
                return Component.Identifier.Button._2;
            }
            case 3: {
                return Component.Identifier.Button._3;
            }
            case 4: {
                return Component.Identifier.Button._4;
            }
            case 5: {
                return Component.Identifier.Button._5;
            }
            case 6: {
                return Component.Identifier.Button._6;
            }
            case 7: {
                return Component.Identifier.Button._7;
            }
            case 8: {
                return Component.Identifier.Button._8;
            }
            case 9: {
                return Component.Identifier.Button._9;
            }
            case 10: {
                return Component.Identifier.Button._10;
            }
            case 11: {
                return Component.Identifier.Button._11;
            }
            case 12: {
                return Component.Identifier.Button._12;
            }
            case 13: {
                return Component.Identifier.Button._13;
            }
            case 14: {
                return Component.Identifier.Button._14;
            }
            case 15: {
                return Component.Identifier.Button._15;
            }
            case 16: {
                return Component.Identifier.Button._16;
            }
            case 17: {
                return Component.Identifier.Button._17;
            }
            case 18: {
                return Component.Identifier.Button._18;
            }
            case 19: {
                return Component.Identifier.Button._19;
            }
            case 20: {
                return Component.Identifier.Button._20;
            }
            case 21: {
                return Component.Identifier.Button._21;
            }
            case 22: {
                return Component.Identifier.Button._22;
            }
            case 23: {
                return Component.Identifier.Button._23;
            }
            case 24: {
                return Component.Identifier.Button._24;
            }
            case 25: {
                return Component.Identifier.Button._25;
            }
            case 26: {
                return Component.Identifier.Button._26;
            }
            case 27: {
                return Component.Identifier.Button._27;
            }
            case 28: {
                return Component.Identifier.Button._28;
            }
            case 29: {
                return Component.Identifier.Button._29;
            }
            case 30: {
                return Component.Identifier.Button._30;
            }
            case 31: {
                return Component.Identifier.Button._31;
            }
        }
        return null;
    }

    private static final Controller createJoystickFromJoystickDevice(LinuxJoystickDevice linuxJoystickDevice) {
        AbstractComponent abstractComponent;
        Component.Identifier identifier;
        int n;
        ArrayList<LinuxJoystickButton> arrayList = new ArrayList<LinuxJoystickButton>();
        byte[] byArray = linuxJoystickDevice.getAxisMap();
        char[] cArray = linuxJoystickDevice.getButtonMap();
        LinuxJoystickAxis[] linuxJoystickAxisArray = new LinuxJoystickAxis[6];
        for (n = 0; n < linuxJoystickDevice.getNumButtons(); ++n) {
            identifier = LinuxNativeTypesMap.getButtonID(cArray[n]);
            if (identifier == null) continue;
            abstractComponent = new LinuxJoystickButton(identifier);
            linuxJoystickDevice.registerButton(n, (LinuxJoystickButton)abstractComponent);
            arrayList.add((LinuxJoystickButton)abstractComponent);
        }
        for (n = 0; n < linuxJoystickDevice.getNumAxes(); ++n) {
            identifier = (Component.Identifier.Axis)LinuxNativeTypesMap.getAbsAxisID(byArray[n]);
            abstractComponent = new LinuxJoystickAxis((Component.Identifier.Axis)identifier);
            linuxJoystickDevice.registerAxis(n, (LinuxJoystickAxis)abstractComponent);
            if (byArray[n] == 16) {
                linuxJoystickAxisArray[0] = abstractComponent;
                continue;
            }
            if (byArray[n] == 17) {
                linuxJoystickAxisArray[1] = abstractComponent;
                abstractComponent = new LinuxJoystickPOV(Component.Identifier.Axis.POV, linuxJoystickAxisArray[0], linuxJoystickAxisArray[5]);
                linuxJoystickDevice.registerPOV((LinuxJoystickPOV)abstractComponent);
                arrayList.add((LinuxJoystickButton)abstractComponent);
                continue;
            }
            if (byArray[n] == 18) {
                linuxJoystickAxisArray[2] = abstractComponent;
                continue;
            }
            if (byArray[n] == 19) {
                linuxJoystickAxisArray[3] = abstractComponent;
                abstractComponent = new LinuxJoystickPOV(Component.Identifier.Axis.POV, linuxJoystickAxisArray[5], linuxJoystickAxisArray[5]);
                linuxJoystickDevice.registerPOV((LinuxJoystickPOV)abstractComponent);
                arrayList.add((LinuxJoystickButton)abstractComponent);
                continue;
            }
            if (byArray[n] == 20) {
                linuxJoystickAxisArray[4] = abstractComponent;
                continue;
            }
            if (byArray[n] == 21) {
                linuxJoystickAxisArray[5] = abstractComponent;
                abstractComponent = new LinuxJoystickPOV(Component.Identifier.Axis.POV, linuxJoystickAxisArray[5], linuxJoystickAxisArray[5]);
                linuxJoystickDevice.registerPOV((LinuxJoystickPOV)abstractComponent);
                arrayList.add((LinuxJoystickButton)abstractComponent);
                continue;
            }
            arrayList.add((LinuxJoystickButton)abstractComponent);
        }
        return new LinuxJoystickAbstractController(linuxJoystickDevice, arrayList.toArray(new Component[0]), new Controller[0], new Rumbler[0]);
    }

    private final void enumerateJoystickControllers(List list) {
        File[] fileArray = LinuxEnvironmentPlugin.enumerateJoystickDeviceFiles("/dev/input");
        if ((fileArray == null || fileArray.length == 0) && (fileArray = LinuxEnvironmentPlugin.enumerateJoystickDeviceFiles("/dev")) == null) {
            return;
        }
        for (int i = 0; i < fileArray.length; ++i) {
            File file = fileArray[i];
            try {
                String string = LinuxEnvironmentPlugin.getAbsolutePathPrivileged(file);
                LinuxJoystickDevice linuxJoystickDevice = new LinuxJoystickDevice(string);
                Controller controller = LinuxEnvironmentPlugin.createJoystickFromJoystickDevice(linuxJoystickDevice);
                if (controller != null) {
                    list.add(controller);
                    this.devices.add(linuxJoystickDevice);
                    continue;
                }
                linuxJoystickDevice.close();
                continue;
            } catch (IOException iOException) {
                LinuxEnvironmentPlugin.logln("Failed to open device (" + file + "): " + iOException.getMessage());
            }
        }
    }

    private static final File[] enumerateJoystickDeviceFiles(String string) {
        File file = new File(string);
        return LinuxEnvironmentPlugin.listFilesPrivileged(file, new FilenameFilter(){

            public final boolean accept(File file, String string) {
                return string.startsWith("js");
            }
        });
    }

    private static String getAbsolutePathPrivileged(File file) {
        return (String)AccessController.doPrivileged(new PrivilegedAction(file){
            private final File val$file;
            {
                this.val$file = file;
            }

            public Object run() {
                return this.val$file.getAbsolutePath();
            }
        });
    }

    private static File[] listFilesPrivileged(File file, FilenameFilter filenameFilter) {
        return (File[])AccessController.doPrivileged(new PrivilegedAction(file, filenameFilter){
            private final File val$dir;
            private final FilenameFilter val$filter;
            {
                this.val$dir = file;
                this.val$filter = filenameFilter;
            }

            public Object run() {
                File[] fileArray = this.val$dir.listFiles(this.val$filter);
                Arrays.sort(fileArray, new Comparator(this){
                    private final 7 this$0;
                    {
                        this.this$0 = var1_1;
                    }

                    public int compare(Object object, Object object2) {
                        return ((File)object).getName().compareTo(((File)object2).getName());
                    }
                });
                return fileArray;
            }
        });
    }

    private final void enumerateEventControllers(List list) {
        File file = new File("/dev/input");
        File[] fileArray = LinuxEnvironmentPlugin.listFilesPrivileged(file, new FilenameFilter(this){
            private final LinuxEnvironmentPlugin this$0;
            {
                this.this$0 = linuxEnvironmentPlugin;
            }

            public final boolean accept(File file, String string) {
                return string.startsWith("event");
            }
        });
        if (fileArray == null) {
            return;
        }
        for (int i = 0; i < fileArray.length; ++i) {
            File file2 = fileArray[i];
            try {
                String string = LinuxEnvironmentPlugin.getAbsolutePathPrivileged(file2);
                LinuxEventDevice linuxEventDevice = new LinuxEventDevice(string);
                try {
                    Controller controller = LinuxEnvironmentPlugin.createControllerFromDevice(linuxEventDevice);
                    if (controller != null) {
                        list.add(controller);
                        this.devices.add(linuxEventDevice);
                        continue;
                    }
                    linuxEventDevice.close();
                } catch (IOException iOException) {
                    LinuxEnvironmentPlugin.logln("Failed to create Controller: " + iOException.getMessage());
                    linuxEventDevice.close();
                }
                continue;
            } catch (IOException iOException) {
                LinuxEnvironmentPlugin.logln("Failed to open device (" + file2 + "): " + iOException.getMessage());
            }
        }
    }

    public boolean isSupported() {
        return supported;
    }

    static boolean access$002(boolean bl) {
        supported = bl;
        return supported;
    }

    static List access$200(LinuxEnvironmentPlugin linuxEnvironmentPlugin) {
        return linuxEnvironmentPlugin.devices;
    }

    static {
        String string = LinuxEnvironmentPlugin.getPrivilegedProperty("os.name", "").trim();
        if (string.equals("Linux")) {
            supported = true;
            if ("i386".equals(LinuxEnvironmentPlugin.getPrivilegedProperty("os.arch"))) {
                LinuxEnvironmentPlugin.loadLibrary(LIBNAME);
            } else {
                LinuxEnvironmentPlugin.loadLibrary("jinput-linux64");
            }
        }
    }

    private final class ShutdownHook
    extends Thread {
        private final LinuxEnvironmentPlugin this$0;

        private ShutdownHook(LinuxEnvironmentPlugin linuxEnvironmentPlugin) {
            this.this$0 = linuxEnvironmentPlugin;
        }

        public final void run() {
            for (int i = 0; i < LinuxEnvironmentPlugin.access$200(this.this$0).size(); ++i) {
                try {
                    LinuxDevice linuxDevice = (LinuxDevice)LinuxEnvironmentPlugin.access$200(this.this$0).get(i);
                    linuxDevice.close();
                    continue;
                } catch (IOException iOException) {
                    ControllerEnvironment.logln("Failed to close device: " + iOException.getMessage());
                }
            }
        }

        ShutdownHook(LinuxEnvironmentPlugin linuxEnvironmentPlugin, 1 var2_2) {
            this(linuxEnvironmentPlugin);
        }
    }
}

