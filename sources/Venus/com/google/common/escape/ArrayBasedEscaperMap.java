/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Map;

@Beta
@GwtCompatible
public final class ArrayBasedEscaperMap {
    private final char[][] replacementArray;
    private static final char[][] EMPTY_REPLACEMENT_ARRAY = new char[0][0];

    public static ArrayBasedEscaperMap create(Map<Character, String> map) {
        return new ArrayBasedEscaperMap(ArrayBasedEscaperMap.createReplacementArray(map));
    }

    private ArrayBasedEscaperMap(char[][] cArray) {
        this.replacementArray = cArray;
    }

    char[][] getReplacementArray() {
        return this.replacementArray;
    }

    @VisibleForTesting
    static char[][] createReplacementArray(Map<Character, String> map) {
        Preconditions.checkNotNull(map);
        if (map.isEmpty()) {
            return EMPTY_REPLACEMENT_ARRAY;
        }
        char c = Collections.max(map.keySet()).charValue();
        char[][] cArrayArray = new char[c + '\u0001'][];
        for (char c2 : map.keySet()) {
            cArrayArray[c2] = map.get(Character.valueOf(c2)).toCharArray();
        }
        return cArrayArray;
    }
}

