/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Java7Support {
    private static final boolean IS_JAVA7;
    private static Method isSymbolicLink;
    private static Method delete;
    private static Method toPath;
    private static Method exists;
    private static Method toFile;
    private static Method readSymlink;
    private static Method createSymlink;
    private static Object emptyLinkOpts;
    private static Object emptyFileAttributes;

    Java7Support() {
    }

    public static boolean isSymLink(File file) {
        try {
            Object object = toPath.invoke(file, new Object[0]);
            Boolean bl = (Boolean)isSymbolicLink.invoke(null, object);
            return bl;
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException);
        }
    }

    public static File readSymbolicLink(File file) throws IOException {
        try {
            Object object = toPath.invoke(file, new Object[0]);
            Object object2 = readSymlink.invoke(null, object);
            return (File)toFile.invoke(object2, new Object[0]);
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException);
        }
    }

    private static boolean exists(File file) throws IOException {
        try {
            Object object = toPath.invoke(file, new Object[0]);
            Boolean bl = (Boolean)exists.invoke(null, object, emptyLinkOpts);
            return bl;
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            throw (RuntimeException)invocationTargetException.getTargetException();
        }
    }

    public static File createSymbolicLink(File file, File file2) throws IOException {
        try {
            if (!Java7Support.exists(file)) {
                Object object = toPath.invoke(file, new Object[0]);
                Object object2 = createSymlink.invoke(null, object, toPath.invoke(file2, new Object[0]), emptyFileAttributes);
                return (File)toFile.invoke(object2, new Object[0]);
            }
            return file;
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getTargetException();
            throw (IOException)throwable;
        }
    }

    public static void delete(File file) throws IOException {
        try {
            Object object = toPath.invoke(file, new Object[0]);
            delete.invoke(null, object);
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            throw (IOException)invocationTargetException.getTargetException();
        }
    }

    public static boolean isAtLeastJava7() {
        return IS_JAVA7;
    }

    static {
        boolean bl = true;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> clazz = classLoader.loadClass("java.nio.file.Files");
            Class<?> clazz2 = classLoader.loadClass("java.nio.file.Path");
            Class<?> clazz3 = classLoader.loadClass("java.nio.file.attribute.FileAttribute");
            Class<?> clazz4 = classLoader.loadClass("java.nio.file.LinkOption");
            isSymbolicLink = clazz.getMethod("isSymbolicLink", clazz2);
            delete = clazz.getMethod("delete", clazz2);
            readSymlink = clazz.getMethod("readSymbolicLink", clazz2);
            emptyFileAttributes = Array.newInstance(clazz3, 0);
            createSymlink = clazz.getMethod("createSymbolicLink", clazz2, clazz2, emptyFileAttributes.getClass());
            emptyLinkOpts = Array.newInstance(clazz4, 0);
            exists = clazz.getMethod("exists", clazz2, emptyLinkOpts.getClass());
            toPath = File.class.getMethod("toPath", new Class[0]);
            toFile = clazz2.getMethod("toFile", new Class[0]);
        } catch (ClassNotFoundException classNotFoundException) {
            bl = false;
        } catch (NoSuchMethodException noSuchMethodException) {
            bl = false;
        }
        IS_JAVA7 = bl;
    }
}

