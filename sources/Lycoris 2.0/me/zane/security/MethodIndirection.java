/*
 * Decompiled with CFR 0.150.
 */
package me.zane.security;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;

public final class MethodIndirection {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final Class<?> INT_CLASS = Integer.TYPE;
    private static final Class<?> SYSTEM_CLASS = System.class;
    private static final Class<?> STRING_CLASS = String.class;
    private static final Class<?> BASE64_CLASS = Base64.class;
    private static System system;
    private static Base64 base64;

    public static String getenv(String property) {
        try {
            return (String)SYSTEM_CLASS.getDeclaredMethod("getenv", STRING_CLASS).invoke(system, property);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {
            return null;
        }
    }

    public static String getProperty(String property) {
        try {
            return (String)SYSTEM_CLASS.getDeclaredMethod("getProperty", STRING_CLASS).invoke(system, property);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {
            return null;
        }
    }

    public static Base64.Encoder getEncoder() {
        try {
            return (Base64.Encoder)BASE64_CLASS.getDeclaredMethod("getEncoder", new Class[0]).invoke(base64, new Object[0]);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {
            return null;
        }
    }

    public static void exit() {
        try {
            MethodHandle exitMethod = LOOKUP.findStatic(SYSTEM_CLASS, "exit", MethodType.methodType(Void.TYPE, INT_CLASS));
            exitMethod.invoke(0);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    static {
        try {
            system = (System)SYSTEM_CLASS.newInstance();
            base64 = (Base64)BASE64_CLASS.newInstance();
        }
        catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
            // empty catch block
        }
    }
}

