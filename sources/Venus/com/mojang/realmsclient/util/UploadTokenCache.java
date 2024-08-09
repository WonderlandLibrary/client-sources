/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.util;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

public class UploadTokenCache {
    private static final Long2ObjectMap<String> field_225236_a = new Long2ObjectOpenHashMap<String>();

    public static String func_225235_a(long l) {
        return (String)field_225236_a.get(l);
    }

    public static void func_225233_b(long l) {
        field_225236_a.remove(l);
    }

    public static void func_225234_a(long l, String string) {
        field_225236_a.put(l, string);
    }
}

