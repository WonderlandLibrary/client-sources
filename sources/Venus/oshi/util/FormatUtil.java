/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package oshi.util;

import java.math.BigDecimal;

public abstract class FormatUtil {
    private static final long kibiByte = 1024L;
    private static final long mebiByte = 0x100000L;
    private static final long gibiByte = 0x40000000L;
    private static final long tebiByte = 0x10000000000L;
    private static final long pebiByte = 0x4000000000000L;

    public static String formatBytes(long l) {
        if (l == 1L) {
            return String.format("%d byte", l);
        }
        if (l < 1024L) {
            return String.format("%d bytes", l);
        }
        if (l < 0x100000L && l % 1024L == 0L) {
            return String.format("%.0f KB", (double)l / 1024.0);
        }
        if (l < 0x100000L) {
            return String.format("%.1f KB", (double)l / 1024.0);
        }
        if (l < 0x40000000L && l % 0x100000L == 0L) {
            return String.format("%.0f MB", (double)l / 1048576.0);
        }
        if (l < 0x40000000L) {
            return String.format("%.1f MB", (double)l / 1048576.0);
        }
        if (l % 0x40000000L == 0L && l < 0x10000000000L) {
            return String.format("%.0f GB", (double)l / 1.073741824E9);
        }
        if (l < 0x10000000000L) {
            return String.format("%.1f GB", (double)l / 1.073741824E9);
        }
        if (l % 0x10000000000L == 0L && l < 0x4000000000000L) {
            return String.format("%.0f TiB", (double)l / 1.099511627776E12);
        }
        if (l < 0x4000000000000L) {
            return String.format("%.1f TiB", (double)l / 1.099511627776E12);
        }
        return String.format("%d bytes", l);
    }

    public static float round(float f, int n) {
        BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
        bigDecimal = bigDecimal.setScale(n, 4);
        return bigDecimal.floatValue();
    }
}

