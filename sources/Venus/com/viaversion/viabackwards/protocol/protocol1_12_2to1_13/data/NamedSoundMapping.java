/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.NamedSoundRewriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class NamedSoundMapping {
    private static final Map<String, String> SOUNDS = new HashMap<String, String>();

    public static String getOldId(String string) {
        if (string.startsWith("minecraft:")) {
            string = string.substring(10);
        }
        return SOUNDS.get(string);
    }

    private static void lambda$static$0(String string, String string2) {
        SOUNDS.put(string2, string);
    }

    static {
        try {
            Field field = NamedSoundRewriter.class.getDeclaredField("oldToNew");
            field.setAccessible(false);
            Map map = (Map)field.get(null);
            map.forEach(NamedSoundMapping::lambda$static$0);
        } catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
        }
    }
}

