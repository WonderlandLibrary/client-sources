/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.util.plugins;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import net.java.games.util.plugins.PluginLoader;

public class Plugins {
    static final boolean DEBUG = true;
    List pluginList = new ArrayList();

    public Plugins(File file) throws IOException {
        this.scanPlugins(file);
    }

    private void scanPlugins(File file) throws IOException {
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            throw new FileNotFoundException("Plugin directory " + file.getName() + " not found.");
        }
        for (int i = 0; i < fileArray.length; ++i) {
            File file2 = fileArray[i];
            if (file2.getName().endsWith(".jar")) {
                this.processJar(file2);
                continue;
            }
            if (!file2.isDirectory()) continue;
            this.scanPlugins(file2);
        }
    }

    private void processJar(File file) {
        try {
            System.out.println("Scanning jar: " + file.getName());
            PluginLoader pluginLoader = new PluginLoader(file);
            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> enumeration = jarFile.entries();
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                System.out.println("Examining file : " + jarEntry.getName());
                if (!jarEntry.getName().endsWith("Plugin.class")) continue;
                System.out.println("Found candidate class: " + jarEntry.getName());
                String string = jarEntry.getName();
                string = string.substring(0, string.length() - 6);
                Class<?> clazz = pluginLoader.loadClass(string = string.replace('/', '.'));
                if (!pluginLoader.attemptPluginDefine(clazz)) continue;
                System.out.println("Adding class to plugins:" + clazz.getName());
                this.pluginList.add(clazz);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Class[] get() {
        Class[] classArray = new Class[this.pluginList.size()];
        return this.pluginList.toArray(classArray);
    }

    public Class[] getImplementsAny(Class[] classArray) {
        ArrayList<Class> arrayList = new ArrayList<Class>(this.pluginList.size());
        HashSet<Class> hashSet = new HashSet<Class>();
        for (int i = 0; i < classArray.length; ++i) {
            hashSet.add(classArray[i]);
        }
        Class[] classArray2 = this.pluginList.iterator();
        while (classArray2.hasNext()) {
            Class clazz = (Class)classArray2.next();
            if (!this.classImplementsAny(clazz, hashSet)) continue;
            arrayList.add(clazz);
        }
        classArray2 = new Class[arrayList.size()];
        return arrayList.toArray(classArray2);
    }

    private boolean classImplementsAny(Class clazz, Set set) {
        int n;
        if (clazz == null) {
            return true;
        }
        Class<?>[] classArray = clazz.getInterfaces();
        for (n = 0; n < classArray.length; ++n) {
            if (!set.contains(classArray[n])) continue;
            return false;
        }
        for (n = 0; n < classArray.length; ++n) {
            if (!this.classImplementsAny(classArray[n], set)) continue;
            return false;
        }
        return this.classImplementsAny(clazz.getSuperclass(), set);
    }

    public Class[] getImplementsAll(Class[] classArray) {
        ArrayList<Class> arrayList = new ArrayList<Class>(this.pluginList.size());
        HashSet<Class> hashSet = new HashSet<Class>();
        for (int i = 0; i < classArray.length; ++i) {
            hashSet.add(classArray[i]);
        }
        Class[] classArray2 = this.pluginList.iterator();
        while (classArray2.hasNext()) {
            Class clazz = (Class)classArray2.next();
            if (!this.classImplementsAll(clazz, hashSet)) continue;
            arrayList.add(clazz);
        }
        classArray2 = new Class[arrayList.size()];
        return arrayList.toArray(classArray2);
    }

    private boolean classImplementsAll(Class clazz, Set set) {
        int n;
        if (clazz == null) {
            return true;
        }
        Class<?>[] classArray = clazz.getInterfaces();
        for (n = 0; n < classArray.length; ++n) {
            if (!set.contains(classArray[n])) continue;
            set.remove(classArray[n]);
            if (set.size() != 0) continue;
            return false;
        }
        for (n = 0; n < classArray.length; ++n) {
            if (!this.classImplementsAll(classArray[n], set)) continue;
            return false;
        }
        return this.classImplementsAll(clazz.getSuperclass(), set);
    }

    public Class[] getExtends(Class clazz) {
        ArrayList<Class> arrayList = new ArrayList<Class>(this.pluginList.size());
        Class[] classArray = this.pluginList.iterator();
        while (classArray.hasNext()) {
            Class clazz2 = (Class)classArray.next();
            if (!this.classExtends(clazz2, clazz)) continue;
            arrayList.add(clazz2);
        }
        classArray = new Class[arrayList.size()];
        return arrayList.toArray(classArray);
    }

    private boolean classExtends(Class clazz, Class clazz2) {
        if (clazz == null) {
            return true;
        }
        if (clazz == clazz2) {
            return false;
        }
        return this.classExtends(clazz.getSuperclass(), clazz2);
    }
}

