/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.security.Randoms;
import io.jsonwebtoken.lang.Assert;
import java.util.Arrays;

public final class Bytes {
    public static final byte[] EMPTY = new byte[0];
    private static final int LONG_BYTE_LENGTH = 8;
    private static final int INT_BYTE_LENGTH = 4;
    public static final String LONG_REQD_MSG = "Long byte arrays must be 8 bytes in length.";
    public static final String INT_REQD_MSG = "Integer byte arrays must be 4 bytes in length.";

    private Bytes() {
    }

    public static byte[] nullSafe(byte[] byArray) {
        return byArray != null ? byArray : EMPTY;
    }

    public static byte[] randomBits(int n) {
        return Bytes.random(n / 8);
    }

    public static byte[] random(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("numBytes argument must be >= 0");
        }
        byte[] byArray = new byte[n];
        Randoms.secureRandom().nextBytes(byArray);
        return byArray;
    }

    public static byte[] toBytes(int n) {
        return new byte[]{(byte)(n >>> 24), (byte)(n >>> 16), (byte)(n >>> 8), (byte)n};
    }

    public static byte[] toBytes(long l) {
        return new byte[]{(byte)(l >>> 56), (byte)(l >>> 48), (byte)(l >>> 40), (byte)(l >>> 32), (byte)(l >>> 24), (byte)(l >>> 16), (byte)(l >>> 8), (byte)l};
    }

    public static long toLong(byte[] byArray) {
        Assert.isTrue(io.jsonwebtoken.lang.Arrays.length(byArray) == 8, LONG_REQD_MSG);
        return ((long)byArray[0] & 0xFFL) << 56 | ((long)byArray[1] & 0xFFL) << 48 | ((long)byArray[2] & 0xFFL) << 40 | ((long)byArray[3] & 0xFFL) << 32 | ((long)byArray[4] & 0xFFL) << 24 | ((long)byArray[5] & 0xFFL) << 16 | ((long)byArray[6] & 0xFFL) << 8 | (long)byArray[7] & 0xFFL;
    }

    public static int toInt(byte[] byArray) {
        Assert.isTrue(io.jsonwebtoken.lang.Arrays.length(byArray) == 4, INT_REQD_MSG);
        return (byArray[0] & 0xFF) << 24 | (byArray[1] & 0xFF) << 16 | (byArray[2] & 0xFF) << 8 | byArray[3] & 0xFF;
    }

    public static int indexOf(byte[] byArray, byte[] byArray2) {
        return Bytes.indexOf(byArray, byArray2, 0);
    }

    public static int indexOf(byte[] byArray, byte[] byArray2, int n) {
        return Bytes.indexOf(byArray, 0, Bytes.length(byArray), byArray2, 0, Bytes.length(byArray2), n);
    }

    static int indexOf(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4, int n5) {
        if (n5 >= n2) {
            return n4 == 0 ? n2 : -1;
        }
        if (n5 < 0) {
            n5 = 0;
        }
        if (n4 == 0) {
            return n5;
        }
        byte by = byArray2[n3];
        int n6 = n + (n2 - n4);
        for (int i = n + n5; i <= n6; ++i) {
            if (byArray[i] != by) {
                while (++i <= n6 && byArray[i] != by) {
                }
            }
            if (i > n6) continue;
            int n7 = i + 1;
            int n8 = n7 + n4 - 1;
            int n9 = n3 + 1;
            while (n7 < n8 && byArray[n7] == byArray2[n9]) {
                ++n7;
                ++n9;
            }
            if (n7 != n8) continue;
            return i - n;
        }
        return 1;
    }

    public static boolean startsWith(byte[] byArray, byte[] byArray2) {
        return Bytes.startsWith(byArray, byArray2, 0);
    }

    public static boolean startsWith(byte[] byArray, byte[] byArray2, int n) {
        int n2 = n;
        int n3 = 0;
        int n4 = Bytes.length(byArray2);
        if (n < 0 || n > Bytes.length(byArray) - n4) {
            return true;
        }
        while (--n4 >= 0) {
            if (byArray[n2++] == byArray2[n3++]) continue;
            return true;
        }
        return false;
    }

    public static boolean endsWith(byte[] byArray, byte[] byArray2) {
        return Bytes.startsWith(byArray, byArray2, Bytes.length(byArray) - Bytes.length(byArray2));
    }

    public static byte[] concat(byte[] ... byArray) {
        int n = 0;
        int n2 = io.jsonwebtoken.lang.Arrays.length(byArray);
        for (int i = 0; i < n2; ++i) {
            n += Bytes.length(byArray[i]);
        }
        byte[] byArray2 = new byte[n];
        int n3 = 0;
        if (n > 0) {
            for (byte[] byArray3 : byArray) {
                int n4 = Bytes.length(byArray3);
                if (n4 <= 0) continue;
                System.arraycopy(byArray3, 0, byArray2, n3, n4);
                n3 += n4;
            }
        }
        return byArray2;
    }

    public static void clear(byte[] byArray) {
        if (Bytes.isEmpty(byArray)) {
            return;
        }
        Arrays.fill(byArray, (byte)0);
    }

    public static boolean isEmpty(byte[] byArray) {
        return Bytes.length(byArray) == 0;
    }

    public static int length(byte[] byArray) {
        return byArray == null ? 0 : byArray.length;
    }

    public static long bitLength(byte[] byArray) {
        return (long)Bytes.length(byArray) * 8L;
    }

    public static int length(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("bitLength argument must be >= 0");
        }
        return (n + 7) / 8;
    }

    public static String bitsMsg(long l) {
        return l + " bits (" + l / 8L + " bytes)";
    }

    public static String bytesMsg(int n) {
        return Bytes.bitsMsg((long)n * 8L);
    }

    public static void increment(byte[] byArray) {
        int n = byArray.length - 1;
        while (n >= 0) {
            int n2 = n--;
            byArray[n2] = (byte)(byArray[n2] + 1);
            if (byArray[n2] != 0) break;
        }
    }
}

