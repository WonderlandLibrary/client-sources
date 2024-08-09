/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StaticMap {
    private static final Map<String, Object> MAP = Collections.synchronizedMap(new HashMap());

    public static boolean contains(String string) {
        return MAP.containsKey(string);
    }

    public static Object get(String string) {
        return MAP.get(string);
    }

    public static void put(String string, Object object) {
        MAP.put(string, object);
    }

    public static int getInt(String string, int n) {
        Object object = MAP.get(string);
        if (!(object instanceof Integer)) {
            return n;
        }
        Integer n2 = (Integer)object;
        return n2;
    }

    public static int putInt(String string, int n) {
        int n2 = StaticMap.getInt(string, 0);
        Integer n3 = n;
        MAP.put(string, n3);
        return n2;
    }

    public static long getLong(String string, long l) {
        Object object = MAP.get(string);
        if (!(object instanceof Long)) {
            return l;
        }
        Long l2 = (Long)object;
        return l2;
    }

    public static void putLong(String string, long l) {
        Long l2 = l;
        MAP.put(string, l2);
    }

    public static long putLong(String string, long l, long l2) {
        long l3 = StaticMap.getLong(string, l2);
        Long l4 = l;
        MAP.put(string, l4);
        return l3;
    }

    public static long addLong(String string, long l, long l2) {
        long l3 = StaticMap.getLong(string, l2);
        StaticMap.putLong(string, l3 += l);
        return l3;
    }
}

