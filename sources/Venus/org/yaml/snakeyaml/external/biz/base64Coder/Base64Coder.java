/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.external.biz.base64Coder;

public class Base64Coder {
    private static final String systemLineSeparator;
    private static final char[] map1;
    private static final byte[] map2;

    public static String encodeString(String string) {
        return new String(Base64Coder.encode(string.getBytes()));
    }

    public static String encodeLines(byte[] byArray) {
        return Base64Coder.encodeLines(byArray, 0, byArray.length, 76, systemLineSeparator);
    }

    public static String encodeLines(byte[] byArray, int n, int n2, int n3, String string) {
        int n4;
        int n5 = n3 * 3 / 4;
        if (n5 <= 0) {
            throw new IllegalArgumentException();
        }
        int n6 = (n2 + n5 - 1) / n5;
        int n7 = (n2 + 2) / 3 * 4 + n6 * string.length();
        StringBuilder stringBuilder = new StringBuilder(n7);
        for (int i = 0; i < n2; i += n4) {
            n4 = Math.min(n2 - i, n5);
            stringBuilder.append(Base64Coder.encode(byArray, n + i, n4));
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static char[] encode(byte[] byArray) {
        return Base64Coder.encode(byArray, 0, byArray.length);
    }

    public static char[] encode(byte[] byArray, int n) {
        return Base64Coder.encode(byArray, 0, n);
    }

    public static char[] encode(byte[] byArray, int n, int n2) {
        int n3 = (n2 * 4 + 2) / 3;
        int n4 = (n2 + 2) / 3 * 4;
        char[] cArray = new char[n4];
        int n5 = n;
        int n6 = n + n2;
        int n7 = 0;
        while (n5 < n6) {
            int n8 = byArray[n5++] & 0xFF;
            int n9 = n5 < n6 ? byArray[n5++] & 0xFF : 0;
            int n10 = n5 < n6 ? byArray[n5++] & 0xFF : 0;
            int n11 = n8 >>> 2;
            int n12 = (n8 & 3) << 4 | n9 >>> 4;
            int n13 = (n9 & 0xF) << 2 | n10 >>> 6;
            int n14 = n10 & 0x3F;
            cArray[n7++] = map1[n11];
            cArray[n7++] = map1[n12];
            cArray[n7] = n7 < n3 ? map1[n13] : 61;
            int n15 = ++n7 < n3 ? map1[n14] : 61;
            cArray[n7] = n15;
            ++n7;
        }
        return cArray;
    }

    public static String decodeString(String string) {
        return new String(Base64Coder.decode(string));
    }

    public static byte[] decodeLines(String string) {
        char[] cArray = new char[string.length()];
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == ' ' || c == '\r' || c == '\n' || c == '\t') continue;
            cArray[n++] = c;
        }
        return Base64Coder.decode(cArray, 0, n);
    }

    public static byte[] decode(String string) {
        return Base64Coder.decode(string.toCharArray());
    }

    public static byte[] decode(char[] cArray) {
        return Base64Coder.decode(cArray, 0, cArray.length);
    }

    public static byte[] decode(char[] cArray, int n, int n2) {
        if (n2 % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        while (n2 > 0 && cArray[n + n2 - 1] == '=') {
            --n2;
        }
        int n3 = n2 * 3 / 4;
        byte[] byArray = new byte[n3];
        int n4 = n;
        int n5 = n + n2;
        int n6 = 0;
        while (n4 < n5) {
            int n7;
            char c = cArray[n4++];
            char c2 = cArray[n4++];
            int n8 = n4 < n5 ? cArray[n4++] : 65;
            int n9 = n7 = n4 < n5 ? cArray[n4++] : 65;
            if (c > '\u007f' || c2 > '\u007f' || n8 > 127 || n7 > 127) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            byte by = map2[c];
            byte by2 = map2[c2];
            byte by3 = map2[n8];
            byte by4 = map2[n7];
            if (by < 0 || by2 < 0 || by3 < 0 || by4 < 0) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            int n10 = by << 2 | by2 >>> 4;
            int n11 = (by2 & 0xF) << 4 | by3 >>> 2;
            int n12 = (by3 & 3) << 6 | by4;
            byArray[n6++] = (byte)n10;
            if (n6 < n3) {
                byArray[n6++] = (byte)n11;
            }
            if (n6 >= n3) continue;
            byArray[n6++] = (byte)n12;
        }
        return byArray;
    }

    private Base64Coder() {
    }

    static {
        int n;
        systemLineSeparator = System.getProperty("line.separator");
        map1 = new char[64];
        int n2 = 0;
        for (n = 65; n <= 90; n = (int)((char)(n + 1))) {
            Base64Coder.map1[n2++] = n;
        }
        for (n = 97; n <= 122; n = (int)((char)(n + 1))) {
            Base64Coder.map1[n2++] = n;
        }
        for (n = 48; n <= 57; n = (int)((char)(n + 1))) {
            Base64Coder.map1[n2++] = n;
        }
        Base64Coder.map1[n2++] = 43;
        Base64Coder.map1[n2++] = 47;
        map2 = new byte[128];
        for (n2 = 0; n2 < map2.length; ++n2) {
            Base64Coder.map2[n2] = -1;
        }
        for (n2 = 0; n2 < 64; ++n2) {
            Base64Coder.map2[Base64Coder.map1[n2]] = (byte)n2;
        }
    }
}

