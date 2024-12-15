package com.alan.clients.util;

import com.alan.clients.Client;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflectionUtil {
    public static Class<?>[] getClassesInPackage(String packageName) {
        try {
            Set<String> classnames = getClassNamesFromJarFile(Paths.get(ReflectionUtil.path()).toFile());
            List<Class<?>> classes = new ArrayList<>();

            for (String classname : classnames) {
                try {
                    if (classname.startsWith(packageName)) {
                        Class<?> clazz = Class.forName(classname);
                        classes.add(clazz);
                    }
                } catch (NoClassDefFoundError | ClassNotFoundException |
                         UnsupportedClassVersionError noClassDefFoundError) {
                }
            }

            return classes.toArray(new Class[0]);
        } catch (Exception exception) {

            File directory = getPackageDirectory(packageName);

            if (!directory.exists()) {
                throw new IllegalArgumentException("Could not get directory resource for package " + packageName);
            }

            return getClassesInPackage(packageName, directory);
        }
    }

    public static Set<String> getClassNamesFromJarFile(File givenFile) throws IOException {
        Set<String> classNames = new HashSet<>();
        try (JarFile jarFile = new JarFile(givenFile)) {
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName()
                            .replace("/", ".")
                            .replace(".class", "");
                    classNames.add(className);
                }
            }
            return classNames;
        }
    }

    private static Class<?>[] getClassesInPackage(String packageName, File directory) {
        List<Class<?>> classes = new ArrayList<>();

        for (String filename : Objects.requireNonNull(directory.list())) {
            if (filename.endsWith(".class")) {
                String classname = buildClassname(packageName, filename);
                try {
                    classes.add(Class.forName(classname));
                } catch (ClassNotFoundException e) {
                    System.err.println("Error creating class " + classname);
                }
            } else if (!filename.contains(".")) {
                String name = packageName + (packageName.endsWith(".") ? "" : ".") + filename;
                classes.addAll(Arrays.asList(getClassesInPackage(name, getPackageDirectory(name))));
            }
        }

        return classes.toArray(new Class[0]);
    }

    public static String buildClassname(String packageName, String filename) {
        return packageName + '.' + filename.replace(".class", "");
    }

    private static File getPackageDirectory(String packageName) {
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) {
            throw new IllegalStateException("Can't get class loader.");
        }

        URL resource = cld.getResource(packageName.replace('.', '/'));

        if (resource == null) {
            throw new RuntimeException("Package " + packageName + " not found on classpath.");
        }

        return new File(resource.getFile());
    }

    public static boolean dirExist(String packageName) {
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        URL resource = cld.getResource(packageName.replace('.', '/'));
        return resource != null;
    }

    public static String path() throws URISyntaxException {
        return new File(Client.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getPath();
    }

    // Probably not the best way of doing it
    public static Field accessField(String name, Class<?> clazz) {
        try {
            Field field = clazz.getField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setFinalStaticField(Class<?> clazz, String fieldName, Object value)
            throws ReflectiveOperationException {

        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, value);
    }

    public static Method accessMethod(String name, Class<?> clazz) {
        try {
            Method method = clazz.getMethod(name);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
