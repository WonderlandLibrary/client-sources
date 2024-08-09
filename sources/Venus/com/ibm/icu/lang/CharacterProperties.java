/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.lang;

import com.ibm.icu.impl.CharacterPropertiesImpl;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CodePointMap;
import com.ibm.icu.util.CodePointTrie;
import com.ibm.icu.util.MutableCodePointTrie;

public final class CharacterProperties {
    private static final UnicodeSet[] sets = new UnicodeSet[65];
    private static final CodePointMap[] maps = new CodePointMap[25];

    private CharacterProperties() {
    }

    private static UnicodeSet makeSet(int n) {
        UnicodeSet unicodeSet = new UnicodeSet();
        UnicodeSet unicodeSet2 = CharacterPropertiesImpl.getInclusionsForProperty(n);
        int n2 = unicodeSet2.getRangeCount();
        int n3 = -1;
        for (int i = 0; i < n2; ++i) {
            int n4 = unicodeSet2.getRangeEnd(i);
            for (int j = unicodeSet2.getRangeStart(i); j <= n4; ++j) {
                if (UCharacter.hasBinaryProperty(j, n)) {
                    if (n3 >= 0) continue;
                    n3 = j;
                    continue;
                }
                if (n3 < 0) continue;
                unicodeSet.add(n3, j - 1);
                n3 = -1;
            }
        }
        if (n3 >= 0) {
            unicodeSet.add(n3, 0x10FFFF);
        }
        return unicodeSet.freeze();
    }

    private static CodePointMap makeMap(int n) {
        int n2;
        int n3 = n == 4106 ? 103 : 0;
        MutableCodePointTrie mutableCodePointTrie = new MutableCodePointTrie(n3, n3);
        UnicodeSet unicodeSet = CharacterPropertiesImpl.getInclusionsForProperty(n);
        int n4 = unicodeSet.getRangeCount();
        int n5 = 0;
        int n6 = n3;
        for (int i = 0; i < n4; ++i) {
            int n7 = unicodeSet.getRangeEnd(i);
            for (n2 = unicodeSet.getRangeStart(i); n2 <= n7; ++n2) {
                int n8 = UCharacter.getIntPropertyValue(n2, n);
                if (n6 == n8) continue;
                if (n6 != n3) {
                    mutableCodePointTrie.setRange(n5, n2 - 1, n6);
                }
                n5 = n2;
                n6 = n8;
            }
        }
        if (n6 != 0) {
            mutableCodePointTrie.setRange(n5, 0x10FFFF, n6);
        }
        CodePointTrie.Type type = n == 4096 || n == 4101 ? CodePointTrie.Type.FAST : CodePointTrie.Type.SMALL;
        n2 = UCharacter.getIntPropertyMaxValue(n);
        CodePointTrie.ValueWidth valueWidth = n2 <= 255 ? CodePointTrie.ValueWidth.BITS_8 : (n2 <= 65535 ? CodePointTrie.ValueWidth.BITS_16 : CodePointTrie.ValueWidth.BITS_32);
        return mutableCodePointTrie.buildImmutable(type, valueWidth);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final UnicodeSet getBinaryPropertySet(int n) {
        if (n < 0 || 65 <= n) {
            throw new IllegalArgumentException("" + n + " is not a constant for a UProperty binary property");
        }
        UnicodeSet[] unicodeSetArray = sets;
        synchronized (sets) {
            UnicodeSet unicodeSet = sets[n];
            if (unicodeSet == null) {
                CharacterProperties.sets[n] = unicodeSet = CharacterProperties.makeSet(n);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return unicodeSet;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final CodePointMap getIntPropertyMap(int n) {
        if (n < 4096 || 4121 <= n) {
            throw new IllegalArgumentException("" + n + " is not a constant for a UProperty int property");
        }
        CodePointMap[] codePointMapArray = maps;
        synchronized (maps) {
            CodePointMap codePointMap = maps[n - 4096];
            if (codePointMap == null) {
                CharacterProperties.maps[n - 4096] = codePointMap = CharacterProperties.makeMap(n);
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return codePointMap;
        }
    }
}

