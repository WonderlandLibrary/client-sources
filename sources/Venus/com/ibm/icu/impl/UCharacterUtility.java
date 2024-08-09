/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

public final class UCharacterUtility {
    private static final int NON_CHARACTER_SUFFIX_MIN_3_0_ = 65534;
    private static final int NON_CHARACTER_MIN_3_1_ = 64976;
    private static final int NON_CHARACTER_MAX_3_1_ = 65007;

    public static boolean isNonCharacter(int n) {
        if ((n & 0xFFFE) == 65534) {
            return false;
        }
        return n >= 64976 && n <= 65007;
    }

    static int toInt(char c, char c2) {
        return c << 16 | c2;
    }

    static int getNullTermByteSubString(StringBuffer stringBuffer, byte[] byArray, int n) {
        byte by = 1;
        while (by != 0) {
            by = byArray[n];
            if (by != 0) {
                stringBuffer.append((char)(by & 0xFF));
            }
            ++n;
        }
        return n;
    }

    static int compareNullTermByteSubString(String string, byte[] byArray, int n, int n2) {
        byte by = 1;
        int n3 = string.length();
        while (by != 0) {
            by = byArray[n2];
            ++n2;
            if (by == 0) break;
            if (n == n3 || string.charAt(n) != (char)(by & 0xFF)) {
                return 1;
            }
            ++n;
        }
        return n;
    }

    static int skipNullTermByteSubString(byte[] byArray, int n, int n2) {
        for (int i = 0; i < n2; ++i) {
            byte by = 1;
            while (by != 0) {
                by = byArray[n];
                ++n;
            }
        }
        return n;
    }

    static int skipByteSubString(byte[] byArray, int n, int n2, byte by) {
        int n3;
        for (n3 = 0; n3 < n2; ++n3) {
            byte by2 = byArray[n + n3];
            if (by2 != by) continue;
            ++n3;
            break;
        }
        return n3;
    }

    private UCharacterUtility() {
    }
}

