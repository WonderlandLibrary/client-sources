/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

public final class Key {
    public static String stripNamespace(String string) {
        int n = string.indexOf(58);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1);
    }

    public static String stripMinecraftNamespace(String string) {
        if (string.startsWith("minecraft:")) {
            return string.substring(10);
        }
        return string;
    }

    public static String namespaced(String string) {
        if (string.indexOf(58) == -1) {
            return "minecraft:" + string;
        }
        return string;
    }
}

