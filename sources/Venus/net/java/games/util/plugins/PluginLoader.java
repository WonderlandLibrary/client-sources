/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.util.plugins;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginLoader
extends URLClassLoader {
    static final boolean DEBUG = false;
    File parentDir;
    boolean localDLLs = true;
    static Class class$net$java$games$util$plugins$Plugin;

    public PluginLoader(File file) throws MalformedURLException {
        super(new URL[]{file.toURL()}, Thread.currentThread().getContextClassLoader());
        this.parentDir = file.getParentFile();
        if (System.getProperty("net.java.games.util.plugins.nolocalnative") != null) {
            this.localDLLs = false;
        }
    }

    protected String findLibrary(String string) {
        if (this.localDLLs) {
            String string2 = this.parentDir.getPath() + File.separator + System.mapLibraryName(string);
            return string2;
        }
        return super.findLibrary(string);
    }

    public boolean attemptPluginDefine(Class clazz) {
        return !clazz.isInterface() && this.classImplementsPlugin(clazz);
    }

    private boolean classImplementsPlugin(Class clazz) {
        int n;
        if (clazz == null) {
            return true;
        }
        Class<?>[] classArray = clazz.getInterfaces();
        for (n = 0; n < classArray.length; ++n) {
            if (classArray[n] != (class$net$java$games$util$plugins$Plugin == null ? PluginLoader.class$("net.java.games.util.plugins.Plugin") : class$net$java$games$util$plugins$Plugin)) continue;
            return false;
        }
        for (n = 0; n < classArray.length; ++n) {
            if (!this.classImplementsPlugin(classArray[n])) continue;
            return false;
        }
        return this.classImplementsPlugin(clazz.getSuperclass());
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }
}

