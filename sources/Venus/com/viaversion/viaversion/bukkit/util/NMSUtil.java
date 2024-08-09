/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package com.viaversion.viaversion.bukkit.util;

import org.bukkit.Bukkit;

public final class NMSUtil {
    private static final String BASE = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS = BASE.replace("org.bukkit.craftbukkit", "net.minecraft.server");
    private static final boolean DEBUG_PROPERTY = NMSUtil.loadDebugProperty();

    private static boolean loadDebugProperty() {
        try {
            Class<?> clazz = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
            Object object = clazz.getDeclaredMethod("getServer", new Class[0]).invoke(null, new Object[0]);
            return (Boolean)clazz.getMethod("isDebugging", new Class[0]).invoke(object, new Object[0]);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            return true;
        }
    }

    public static Class<?> nms(String string) throws ClassNotFoundException {
        return Class.forName(NMS + "." + string);
    }

    public static Class<?> nms(String string, String string2) throws ClassNotFoundException {
        try {
            return Class.forName(NMS + "." + string);
        } catch (ClassNotFoundException classNotFoundException) {
            return Class.forName(string2);
        }
    }

    public static Class<?> obc(String string) throws ClassNotFoundException {
        return Class.forName(BASE + "." + string);
    }

    public static boolean isDebugPropertySet() {
        return DEBUG_PROPERTY;
    }
}

