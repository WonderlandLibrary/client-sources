package net.java.games.util.plugins;

import java.io.*;
import java.net.*;

public class PluginLoader extends URLClassLoader
{
    static final boolean DEBUG = false;
    File parentDir;
    boolean localDLLs;
    static /* synthetic */ Class class$net$java$games$util$plugins$Plugin;
    
    public PluginLoader(final File jf) throws MalformedURLException {
        super(new URL[] { jf.toURL() }, Thread.currentThread().getContextClassLoader());
        this.localDLLs = true;
        this.parentDir = jf.getParentFile();
        if (System.getProperty("net.java.games.util.plugins.nolocalnative") != null) {
            this.localDLLs = false;
        }
    }
    
    protected String findLibrary(final String libname) {
        if (this.localDLLs) {
            final String libpath = this.parentDir.getPath() + File.separator + System.mapLibraryName(libname);
            return libpath;
        }
        return super.findLibrary(libname);
    }
    
    public boolean attemptPluginDefine(final Class pc) {
        return !pc.isInterface() && this.classImplementsPlugin(pc);
    }
    
    private boolean classImplementsPlugin(final Class testClass) {
        if (testClass == null) {
            return false;
        }
        final Class[] implementedInterfaces = testClass.getInterfaces();
        for (int i = 0; i < implementedInterfaces.length; ++i) {
            if (implementedInterfaces[i] == ((PluginLoader.class$net$java$games$util$plugins$Plugin == null) ? (PluginLoader.class$net$java$games$util$plugins$Plugin = class$("net.java.games.util.plugins.Plugin")) : PluginLoader.class$net$java$games$util$plugins$Plugin)) {
                return true;
            }
        }
        for (int i = 0; i < implementedInterfaces.length; ++i) {
            if (this.classImplementsPlugin(implementedInterfaces[i])) {
                return true;
            }
        }
        return this.classImplementsPlugin(testClass.getSuperclass());
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
