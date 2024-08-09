/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.util.HashSet;
import java.util.Set;

public final class SetUtils {
    private SetUtils() {
    }

    public static String[] prefixSet(Set<String> set, String string) {
        HashSet<String> hashSet = new HashSet<String>();
        for (String string2 : set) {
            if (!string2.startsWith(string)) continue;
            hashSet.add(string2);
        }
        return hashSet.toArray(new String[hashSet.size()]);
    }
}

