/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

public final class JsonUtils {
    private static final char[] HC = "0123456789ABCDEF".toCharArray();
    private static final int[] ESC_CODES;
    private static final ThreadLocal<char[]> _qbufLocal;

    private static char[] getQBuf() {
        char[] cArray = _qbufLocal.get();
        if (cArray == null) {
            cArray = new char[6];
            cArray[0] = 92;
            cArray[2] = 48;
            cArray[3] = 48;
            _qbufLocal.set(cArray);
        }
        return cArray;
    }

    public static void quoteAsString(CharSequence charSequence, StringBuilder stringBuilder) {
        char[] cArray = JsonUtils.getQBuf();
        int n = ESC_CODES.length;
        int n2 = 0;
        int n3 = charSequence.length();
        block0: while (n2 < n3) {
            char c;
            while ((c = charSequence.charAt(n2)) >= n || ESC_CODES[c] == 0) {
                stringBuilder.append(c);
                if (++n2 < n3) continue;
                break block0;
            }
            c = charSequence.charAt(n2++);
            int n4 = ESC_CODES[c];
            int n5 = n4 < 0 ? JsonUtils._appendNumeric(c, cArray) : JsonUtils._appendNamed(n4, cArray);
            stringBuilder.append(cArray, 0, n5);
        }
    }

    private static int _appendNumeric(int n, char[] cArray) {
        cArray[1] = 117;
        cArray[4] = HC[n >> 4];
        cArray[5] = HC[n & 0xF];
        return 1;
    }

    private static int _appendNamed(int n, char[] cArray) {
        cArray[1] = (char)n;
        return 1;
    }

    static {
        int[] nArray = new int[128];
        for (int i = 0; i < 32; ++i) {
            nArray[i] = -1;
        }
        nArray[34] = 34;
        nArray[92] = 92;
        nArray[8] = 98;
        nArray[9] = 116;
        nArray[12] = 102;
        nArray[10] = 110;
        nArray[13] = 114;
        ESC_CODES = nArray;
        _qbufLocal = new ThreadLocal();
    }
}

