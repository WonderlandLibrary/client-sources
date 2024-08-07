package net.java.games.input;

import java.io.*;
import java.security.*;
import java.util.*;
import net.java.games.util.plugins.*;

class DefaultControllerEnvironment extends ControllerEnvironment
{
    static String libPath;
    private ArrayList controllers;
    private Collection loadedPlugins;
    static /* synthetic */ Class class$net$java$games$input$ControllerEnvironment;
    
    static void loadLibrary(final String lib_name) {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
            public final Object run() {
                final String lib_path = System.getProperty("net.java.games.input.librarypath");
                if (lib_path != null) {
                    System.load(lib_path + File.separator + System.mapLibraryName(lib_name));
                }
                else {
                    System.loadLibrary(lib_name);
                }
                return null;
            }
        });
    }
    
    static String getPrivilegedProperty(final String property) {
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction() {
            public Object run() {
                return System.getProperty(property);
            }
        });
    }
    
    static String getPrivilegedProperty(final String property, final String default_value) {
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction() {
            public Object run() {
                return System.getProperty(property, default_value);
            }
        });
    }
    
    public DefaultControllerEnvironment() {
        this.loadedPlugins = new ArrayList();
    }
    
    public Controller[] getControllers() {
        if (this.controllers == null) {
            this.controllers = new ArrayList();
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                public Object run() {
                    DefaultControllerEnvironment.this.scanControllers();
                    return null;
                }
            });
            String pluginClasses = getPrivilegedProperty("jinput.plugins", "") + " " + getPrivilegedProperty("net.java.games.input.plugins", "");
            if (!getPrivilegedProperty("jinput.useDefaultPlugin", "true").toLowerCase().trim().equals("false") && !getPrivilegedProperty("net.java.games.input.useDefaultPlugin", "true").toLowerCase().trim().equals("false")) {
                final String osName = getPrivilegedProperty("os.name", "").trim();
                if (osName.equals("Linux")) {
                    pluginClasses += " net.java.games.input.LinuxEnvironmentPlugin";
                }
                else if (osName.equals("Mac OS X")) {
                    pluginClasses += " net.java.games.input.OSXEnvironmentPlugin";
                }
                else if (osName.equals("Windows XP") || osName.equals("Windows Vista")) {
                    pluginClasses += " net.java.games.input.DirectAndRawInputEnvironmentPlugin";
                }
                else if (osName.equals("Windows 98") || osName.equals("Windows 2000")) {
                    pluginClasses += " net.java.games.input.DirectInputEnvironmentPlugin";
                }
                else if (osName.startsWith("Windows")) {
                    System.out.println("WARNING: Found unknown Windows version: " + osName);
                    System.out.println("Attempting to use default windows plug-in.");
                    System.out.flush();
                    pluginClasses += " net.java.games.input.DirectAndRawInputEnvironmentPlugin";
                }
                else {
                    System.out.println("Trying to use default plugin, OS name " + osName + " not recognised");
                }
            }
            final StringTokenizer pluginClassTok = new StringTokenizer(pluginClasses, " \t\n\r\f,;:");
            while (pluginClassTok.hasMoreTokens()) {
                final String className = pluginClassTok.nextToken();
                try {
                    if (this.loadedPlugins.contains(className)) {
                        continue;
                    }
                    System.out.println("Loading: " + className);
                    final Class ceClass = Class.forName(className);
                    final ControllerEnvironment ce = ceClass.newInstance();
                    if (ce.isSupported()) {
                        this.addControllers(ce.getControllers());
                        this.loadedPlugins.add(ce.getClass().getName());
                    }
                    else {
                        ControllerEnvironment.logln(ceClass.getName() + " is not supported");
                    }
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        final Controller[] ret = new Controller[this.controllers.size()];
        final Iterator it = this.controllers.iterator();
        int i = 0;
        while (it.hasNext()) {
            ret[i] = it.next();
            ++i;
        }
        return ret;
    }
    
    private void scanControllers() {
        String pluginPathName = getPrivilegedProperty("jinput.controllerPluginPath");
        if (pluginPathName == null) {
            pluginPathName = "controller";
        }
        this.scanControllersAt(getPrivilegedProperty("java.home") + File.separator + "lib" + File.separator + pluginPathName);
        this.scanControllersAt(getPrivilegedProperty("user.dir") + File.separator + pluginPathName);
    }
    
    private void scanControllersAt(final String path) {
        final File file = new File(path);
        if (!file.exists()) {
            return;
        }
        try {
            final Plugins plugins = new Plugins(file);
            final Class[] envClasses = plugins.getExtends((DefaultControllerEnvironment.class$net$java$games$input$ControllerEnvironment == null) ? (DefaultControllerEnvironment.class$net$java$games$input$ControllerEnvironment = class$("net.java.games.input.ControllerEnvironment")) : DefaultControllerEnvironment.class$net$java$games$input$ControllerEnvironment);
            for (int i = 0; i < envClasses.length; ++i) {
                try {
                    ControllerEnvironment.logln("ControllerEnvironment " + envClasses[i].getName() + " loaded by " + envClasses[i].getClassLoader());
                    final ControllerEnvironment ce = envClasses[i].newInstance();
                    if (ce.isSupported()) {
                        this.addControllers(ce.getControllers());
                        this.loadedPlugins.add(ce.getClass().getName());
                    }
                    else {
                        ControllerEnvironment.logln(envClasses[i].getName() + " is not supported");
                    }
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    private void addControllers(final Controller[] c) {
        for (int i = 0; i < c.length; ++i) {
            this.controllers.add(c[i]);
        }
    }
    
    public boolean isSupported() {
        return true;
    }
    
    static /* synthetic */ Class class$(final String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x) {
            throw new NoClassDefFoundError().initCause(x);
        }
    }
}
