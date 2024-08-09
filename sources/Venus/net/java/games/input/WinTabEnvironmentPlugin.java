/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.DummyWindow;
import net.java.games.input.WinTabContext;
import net.java.games.util.plugins.Plugin;

public class WinTabEnvironmentPlugin
extends ControllerEnvironment
implements Plugin {
    private static boolean supported = false;
    private final Controller[] controllers;
    private final List active_devices = new ArrayList();
    private final WinTabContext winTabContext;

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
                    WinTabEnvironmentPlugin.access$002(false);
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

    public WinTabEnvironmentPlugin() {
        if (this.isSupported()) {
            DummyWindow dummyWindow = null;
            WinTabContext winTabContext = null;
            Controller[] controllerArray = new Controller[]{};
            try {
                dummyWindow = new DummyWindow();
                winTabContext = new WinTabContext(dummyWindow);
                try {
                    winTabContext.open();
                    controllerArray = winTabContext.getControllers();
                } catch (Exception exception) {
                    dummyWindow.destroy();
                    throw exception;
                }
            } catch (Exception exception) {
                WinTabEnvironmentPlugin.logln("Failed to enumerate devices: " + exception.getMessage());
                exception.printStackTrace();
            }
            this.controllers = controllerArray;
            this.winTabContext = winTabContext;
            AccessController.doPrivileged(new PrivilegedAction(this){
                private final WinTabEnvironmentPlugin this$0;
                {
                    this.this$0 = winTabEnvironmentPlugin;
                }

                public final Object run() {
                    Runtime.getRuntime().addShutdownHook(new ShutdownHook(this.this$0, null));
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

    public Controller[] getControllers() {
        return this.controllers;
    }

    static boolean access$002(boolean bl) {
        supported = bl;
        return supported;
    }

    static List access$200(WinTabEnvironmentPlugin winTabEnvironmentPlugin) {
        return winTabEnvironmentPlugin.active_devices;
    }

    static WinTabContext access$300(WinTabEnvironmentPlugin winTabEnvironmentPlugin) {
        return winTabEnvironmentPlugin.winTabContext;
    }

    static {
        String string = WinTabEnvironmentPlugin.getPrivilegedProperty("os.name", "").trim();
        if (string.startsWith("Windows")) {
            supported = true;
            WinTabEnvironmentPlugin.loadLibrary("jinput-wintab");
        }
    }

    private final class ShutdownHook
    extends Thread {
        private final WinTabEnvironmentPlugin this$0;

        private ShutdownHook(WinTabEnvironmentPlugin winTabEnvironmentPlugin) {
            this.this$0 = winTabEnvironmentPlugin;
        }

        public final void run() {
            for (int i = 0; i < WinTabEnvironmentPlugin.access$200(this.this$0).size(); ++i) {
            }
            WinTabEnvironmentPlugin.access$300(this.this$0).close();
        }

        ShutdownHook(WinTabEnvironmentPlugin winTabEnvironmentPlugin, 1 var2_2) {
            this(winTabEnvironmentPlugin);
        }
    }
}

