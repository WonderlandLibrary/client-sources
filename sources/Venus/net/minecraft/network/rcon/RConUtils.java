/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.rcon;

import java.nio.charset.StandardCharsets;

public class RConUtils {
    public static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getBytesAsString(byte[] byArray, int n, int n2) {
        int n3;
        int n4 = n2 - 1;
        int n5 = n3 = n > n4 ? n4 : n;
        while (0 != byArray[n3] && n3 < n4) {
            ++n3;
        }
        return new String(byArray, n, n3 - n, StandardCharsets.UTF_8);
    }

    public static int getRemainingBytesAsLEInt(byte[] byArray, int n) {
        return RConUtils.getBytesAsLEInt(byArray, n, byArray.length);
    }

    public static int getBytesAsLEInt(byte[] byArray, int n, int n2) {
        return 0 > n2 - n - 4 ? 0 : byArray[n + 3] << 24 | (byArray[n + 2] & 0xFF) << 16 | (byArray[n + 1] & 0xFF) << 8 | byArray[n] & 0xFF;
    }

    public static int getBytesAsBEint(byte[] byArray, int n, int n2) {
        return 0 > n2 - n - 4 ? 0 : byArray[n] << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF;
    }

    public static String getByteAsHexString(byte by) {
        return "" + HEX_DIGITS[(by & 0xF0) >>> 4] + HEX_DIGITS[by & 0xF];
    }
}

