/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

public final class Key {
    public static String stripNamespace(String identifier) {
        int index = identifier.indexOf(58);
        if (index == -1) {
            return identifier;
        }
        return identifier.substring(index + 1);
    }

    public static String stripMinecraftNamespace(String identifier) {
        if (identifier.startsWith("minecraft:")) {
            return identifier.substring(10);
        }
        return identifier;
    }

    public static String namespaced(String identifier) {
        if (identifier.indexOf(58) == -1) {
            return "minecraft:" + identifier;
        }
        return identifier;
    }
}

