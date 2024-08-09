/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.Set;

public class CollectionUtils {
    public static boolean noneMatch(Set set, Set set2) {
        return !CollectionUtils.anyMatch(set, set2);
    }

    public static boolean anyMatch(Set set, Set object) {
        if (!set.isEmpty() && !object.isEmpty()) {
            if (object.size() < set.size()) {
                Set set2 = set;
                set = object;
                object = set2;
            }
            for (Object e : set) {
                if (!object.contains(e)) continue;
                return false;
            }
            return true;
        }
        return true;
    }
}

