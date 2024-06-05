package net.java.games.input;

import net.java.games.util.plugins.*;
import java.security.*;
import java.io.*;
import java.util.*;

public final class OSXEnvironmentPlugin extends ControllerEnvironment implements Plugin
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
                OSXEnvironmentPlugin.supported = false;
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
    
    private static final boolean isMacOSXEqualsOrBetterThan(final int major_required, final int minor_required) {
        final String os_version = System.getProperty("os.version");
        final StringTokenizer version_tokenizer = new StringTokenizer(os_version, ".");
        int major;
        int minor;
        try {
            final String major_str = version_tokenizer.nextToken();
            final String minor_str = version_tokenizer.nextToken();
            major = Integer.parseInt(major_str);
            minor = Integer.parseInt(minor_str);
        }
        catch (Exception e) {
            ControllerEnvironment.log("Exception occurred while trying to determine OS version: " + e);
            return false;
        }
        return major > major_required || (major == major_required && minor >= minor_required);
    }
    
    public OSXEnvironmentPlugin() {
        if (this.isSupported()) {
            this.controllers = enumerateControllers();
        }
        else {
            this.controllers = new Controller[0];
        }
    }
    
    @Override
    public final Controller[] getControllers() {
        return this.controllers;
    }
    
    @Override
    public boolean isSupported() {
        return OSXEnvironmentPlugin.supported;
    }
    
    private static final void addElements(final OSXHIDQueue queue, final List<OSXHIDElement> elements, final List<OSXComponent> components, final boolean map_mouse_buttons) throws IOException {
        for (final OSXHIDElement element : elements) {
            Component.Identifier id = element.getIdentifier();
            if (id == null) {
                continue;
            }
            if (map_mouse_buttons) {
                if (id == Component.Identifier.Button._0) {
                    id = Component.Identifier.Button.LEFT;
                }
                else if (id == Component.Identifier.Button._1) {
                    id = Component.Identifier.Button.RIGHT;
                }
                else if (id == Component.Identifier.Button._2) {
                    id = Component.Identifier.Button.MIDDLE;
                }
            }
            final OSXComponent component = new OSXComponent(id, element);
            components.add(component);
            queue.addElement(element, component);
        }
    }
    
    private static final Keyboard createKeyboardFromDevice(final OSXHIDDevice device, final List<OSXHIDElement> elements) throws IOException {
        final List<OSXComponent> components = new ArrayList<OSXComponent>();
        final OSXHIDQueue queue = device.createQueue(32);
        try {
            addElements(queue, elements, components, false);
        }
        catch (IOException e) {
            queue.release();
            throw e;
        }
        final Component[] components_array = new Component[components.size()];
        components.toArray(components_array);
        return new OSXKeyboard(device, queue, components_array, new Controller[0], new Rumbler[0]);
    }
    
    private static final Mouse createMouseFromDevice(final OSXHIDDevice device, final List<OSXHIDElement> elements) throws IOException {
        final List<OSXComponent> components = new ArrayList<OSXComponent>();
        final OSXHIDQueue queue = device.createQueue(32);
        try {
            addElements(queue, elements, components, true);
        }
        catch (IOException e) {
            queue.release();
            throw e;
        }
        final Component[] components_array = new Component[components.size()];
        components.toArray(components_array);
        final Mouse mouse = new OSXMouse(device, queue, components_array, new Controller[0], new Rumbler[0]);
        if (mouse.getPrimaryButton() != null && mouse.getX() != null && mouse.getY() != null) {
            return mouse;
        }
        queue.release();
        return null;
    }
    
    private static final AbstractController createControllerFromDevice(final OSXHIDDevice device, final List<OSXHIDElement> elements, final Controller.Type type) throws IOException {
        final List<OSXComponent> components = new ArrayList<OSXComponent>();
        final OSXHIDQueue queue = device.createQueue(32);
        try {
            addElements(queue, elements, components, false);
        }
        catch (IOException e) {
            queue.release();
            throw e;
        }
        final Component[] components_array = new Component[components.size()];
        components.toArray(components_array);
        return new OSXAbstractController(device, queue, components_array, new Controller[0], new Rumbler[0], type);
    }
    
    private static final void createControllersFromDevice(final OSXHIDDevice device, final List<Controller> controllers) throws IOException {
        final UsagePair usage_pair = device.getUsagePair();
        if (usage_pair == null) {
            return;
        }
        final List<OSXHIDElement> elements = device.getElements();
        if (usage_pair.getUsagePage() == UsagePage.GENERIC_DESKTOP && (usage_pair.getUsage() == GenericDesktopUsage.MOUSE || usage_pair.getUsage() == GenericDesktopUsage.POINTER)) {
            final Controller mouse = createMouseFromDevice(device, elements);
            if (mouse != null) {
                controllers.add(mouse);
            }
        }
        else if (usage_pair.getUsagePage() == UsagePage.GENERIC_DESKTOP && (usage_pair.getUsage() == GenericDesktopUsage.KEYBOARD || usage_pair.getUsage() == GenericDesktopUsage.KEYPAD)) {
            controllers.add(createKeyboardFromDevice(device, elements));
        }
        else if (usage_pair.getUsagePage() == UsagePage.GENERIC_DESKTOP && usage_pair.getUsage() == GenericDesktopUsage.JOYSTICK) {
            controllers.add(createControllerFromDevice(device, elements, Controller.Type.STICK));
        }
        else if (usage_pair.getUsagePage() == UsagePage.GENERIC_DESKTOP && usage_pair.getUsage() == GenericDesktopUsage.MULTI_AXIS_CONTROLLER) {
            controllers.add(createControllerFromDevice(device, elements, Controller.Type.STICK));
        }
        else if (usage_pair.getUsagePage() == UsagePage.GENERIC_DESKTOP && usage_pair.getUsage() == GenericDesktopUsage.GAME_PAD) {
            controllers.add(createControllerFromDevice(device, elements, Controller.Type.GAMEPAD));
        }
    }
    
    private static final Controller[] enumerateControllers() {
        final List<Controller> controllers = new ArrayList<Controller>();
        try {
            final OSXHIDDeviceIterator it = new OSXHIDDeviceIterator();
            while (true) {
                try {
                    while (true) {
                        final OSXHIDDevice device = it.next();
                        if (device == null) {
                            break;
                        }
                        boolean device_used = false;
                        try {
                            final int old_size = controllers.size();
                            createControllersFromDevice(device, controllers);
                            device_used = (old_size != controllers.size());
                        }
                        catch (IOException e3) {
                            ControllerEnvironment.log("Failed to create controllers from device: " + device.getProductName());
                        }
                        if (device_used) {
                            continue;
                        }
                        device.release();
                    }
                }
                catch (IOException e) {
                    ControllerEnvironment.log("Failed to enumerate device: " + e.getMessage());
                    continue;
                }
                finally {
                    it.close();
                }
                break;
            }
        }
        catch (IOException e2) {
            ControllerEnvironment.log("Failed to enumerate devices: " + e2.getMessage());
            return new Controller[0];
        }
        final Controller[] controllers_array = new Controller[controllers.size()];
        controllers.toArray(controllers_array);
        return controllers_array;
    }
    
    static {
        OSXEnvironmentPlugin.supported = false;
        final String osName = getPrivilegedProperty("os.name", "").trim();
        if (osName.equals("Mac OS X")) {
            OSXEnvironmentPlugin.supported = true;
            loadLibrary("jinput-osx");
        }
    }
}
