/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import net.java.games.input.DefaultControllerEnvironment;

class PluginClassLoader
extends ClassLoader {
    private static String pluginDirectory;
    private static final FileFilter JAR_FILTER;
    static final boolean $assertionsDisabled;
    static Class class$net$java$games$input$PluginClassLoader;

    public PluginClassLoader() {
        super(Thread.currentThread().getContextClassLoader());
    }

    protected Class findClass(String string) throws ClassNotFoundException {
        byte[] byArray = this.loadClassData(string);
        return this.defineClass(string, byArray, 0, byArray.length);
    }

    private byte[] loadClassData(String string) throws ClassNotFoundException {
        if (pluginDirectory == null) {
            pluginDirectory = DefaultControllerEnvironment.libPath + File.separator + "controller";
        }
        try {
            return this.loadClassFromDirectory(string);
        } catch (Exception exception) {
            try {
                return this.loadClassFromJAR(string);
            } catch (IOException iOException) {
                throw new ClassNotFoundException(string, iOException);
            }
        }
    }

    private byte[] loadClassFromDirectory(String string) throws ClassNotFoundException, IOException {
        StringTokenizer stringTokenizer = new StringTokenizer(string, ".");
        StringBuffer stringBuffer = new StringBuffer(pluginDirectory);
        while (stringTokenizer.hasMoreTokens()) {
            stringBuffer.append(File.separator);
            stringBuffer.append(stringTokenizer.nextToken());
        }
        stringBuffer.append(".class");
        File file = new File(stringBuffer.toString());
        if (!file.exists()) {
            throw new ClassNotFoundException(string);
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        if (!$assertionsDisabled && file.length() > Integer.MAX_VALUE) {
            throw new AssertionError();
        }
        int n = (int)file.length();
        byte[] byArray = new byte[n];
        int n2 = fileInputStream.read(byArray);
        if (!$assertionsDisabled && n != n2) {
            throw new AssertionError();
        }
        return byArray;
    }

    private byte[] loadClassFromJAR(String string) throws ClassNotFoundException, IOException {
        File file = new File(pluginDirectory);
        File[] fileArray = file.listFiles(JAR_FILTER);
        if (fileArray == null) {
            throw new ClassNotFoundException("Could not find class " + string);
        }
        for (int i = 0; i < fileArray.length; ++i) {
            JarFile jarFile = new JarFile(fileArray[i]);
            JarEntry jarEntry = jarFile.getJarEntry(string + ".class");
            if (jarEntry == null) continue;
            InputStream inputStream = jarFile.getInputStream(jarEntry);
            if (!$assertionsDisabled && jarEntry.getSize() > Integer.MAX_VALUE) {
                throw new AssertionError();
            }
            int n = (int)jarEntry.getSize();
            if (!$assertionsDisabled && n < 0) {
                throw new AssertionError();
            }
            byte[] byArray = new byte[n];
            int n2 = inputStream.read(byArray);
            if (!$assertionsDisabled && n != n2) {
                throw new AssertionError();
            }
            return byArray;
        }
        throw new FileNotFoundException(string);
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError().initCause(classNotFoundException);
        }
    }

    static {
        $assertionsDisabled = !(class$net$java$games$input$PluginClassLoader == null ? (class$net$java$games$input$PluginClassLoader = PluginClassLoader.class$("net.java.games.input.PluginClassLoader")) : class$net$java$games$input$PluginClassLoader).desiredAssertionStatus();
        JAR_FILTER = new JarFileFilter(null);
    }

    static class 1 {
    }

    private static class JarFileFilter
    implements FileFilter {
        private JarFileFilter() {
        }

        public boolean accept(File file) {
            return file.getName().toUpperCase().endsWith(".JAR");
        }

        JarFileFilter(1 var1_1) {
            this();
        }
    }
}

