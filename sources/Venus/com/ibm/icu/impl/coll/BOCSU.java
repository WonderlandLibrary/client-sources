/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.util.ByteArrayWrapper;

public class BOCSU {
    private static final int SLOPE_MIN_ = 3;
    private static final int SLOPE_MAX_ = 255;
    private static final int SLOPE_MIDDLE_ = 129;
    private static final int SLOPE_TAIL_COUNT_ = 253;
    private static final int SLOPE_MAX_BYTES_ = 4;
    private static final int SLOPE_SINGLE_ = 80;
    private static final int SLOPE_LEAD_2_ = 42;
    private static final int SLOPE_LEAD_3_ = 3;
    private static final int SLOPE_REACH_POS_1_ = 80;
    private static final int SLOPE_REACH_NEG_1_ = -80;
    private static final int SLOPE_REACH_POS_2_ = 10667;
    private static final int SLOPE_REACH_NEG_2_ = -10668;
    private static final int SLOPE_REACH_POS_3_ = 192785;
    private static final int SLOPE_REACH_NEG_3_ = -192786;
    private static final int SLOPE_START_POS_2_ = 210;
    private static final int SLOPE_START_POS_3_ = 252;
    private static final int SLOPE_START_NEG_2_ = 49;
    private static final int SLOPE_START_NEG_3_ = 7;

    public static int writeIdenticalLevelRun(int n, CharSequence charSequence, int n2, int n3, ByteArrayWrapper byteArrayWrapper) {
        while (n2 < n3) {
            BOCSU.ensureAppendCapacity(byteArrayWrapper, 16, charSequence.length() * 2);
            byte[] byArray = byteArrayWrapper.bytes;
            int n4 = byArray.length;
            int n5 = byteArrayWrapper.size;
            int n6 = n4 - 4;
            while (n2 < n3 && n5 <= n6) {
                n = n < 19968 || n >= 40960 ? (n & 0xFFFFFF80) - -80 : 30292;
                int n7 = Character.codePointAt(charSequence, n2);
                n2 += Character.charCount(n7);
                if (n7 == 65534) {
                    byArray[n5++] = 2;
                    n = 0;
                    continue;
                }
                n5 = BOCSU.writeDiff(n7 - n, byArray, n5);
                n = n7;
            }
            byteArrayWrapper.size = n5;
        }
        return n;
    }

    private static void ensureAppendCapacity(ByteArrayWrapper byteArrayWrapper, int n, int n2) {
        int n3 = byteArrayWrapper.bytes.length - byteArrayWrapper.size;
        if (n3 >= n) {
            return;
        }
        if (n2 < n) {
            n2 = n;
        }
        byteArrayWrapper.ensureCapacity(byteArrayWrapper.size + n2);
    }

    private BOCSU() {
    }

    private static final long getNegDivMod(int n, int n2) {
        int n3 = n % n2;
        long l = n / n2;
        if (n3 < 0) {
            --l;
            n3 += n2;
        }
        return l << 32 | (long)n3;
    }

    private static final int writeDiff(int n, byte[] byArray, int n2) {
        if (n >= -80) {
            if (n <= 80) {
                byArray[n2++] = (byte)(129 + n);
            } else if (n <= 10667) {
                byArray[n2++] = (byte)(210 + n / 253);
                byArray[n2++] = (byte)(3 + n % 253);
            } else if (n <= 192785) {
                byArray[n2 + 2] = (byte)(3 + n % 253);
                byArray[n2 + 1] = (byte)(3 + (n /= 253) % 253);
                byArray[n2] = (byte)(252 + n / 253);
                n2 += 3;
            } else {
                byArray[n2 + 3] = (byte)(3 + n % 253);
                byArray[n2 + 2] = (byte)(3 + (n /= 253) % 253);
                byArray[n2 + 1] = (byte)(3 + (n /= 253) % 253);
                byArray[n2] = -1;
                n2 += 4;
            }
        } else {
            long l = BOCSU.getNegDivMod(n, 253);
            int n3 = (int)l;
            if (n >= -10668) {
                n = (int)(l >> 32);
                byArray[n2++] = (byte)(49 + n);
                byArray[n2++] = (byte)(3 + n3);
            } else if (n >= -192786) {
                byArray[n2 + 2] = (byte)(3 + n3);
                n = (int)(l >> 32);
                l = BOCSU.getNegDivMod(n, 253);
                n3 = (int)l;
                n = (int)(l >> 32);
                byArray[n2 + 1] = (byte)(3 + n3);
                byArray[n2] = (byte)(7 + n);
                n2 += 3;
            } else {
                byArray[n2 + 3] = (byte)(3 + n3);
                n = (int)(l >> 32);
                l = BOCSU.getNegDivMod(n, 253);
                n3 = (int)l;
                n = (int)(l >> 32);
                byArray[n2 + 2] = (byte)(3 + n3);
                l = BOCSU.getNegDivMod(n, 253);
                n3 = (int)l;
                byArray[n2 + 1] = (byte)(3 + n3);
                byArray[n2] = 3;
                n2 += 4;
            }
        }
        return n2;
    }
}

