/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import java.util.Locale;

public enum UploadSpeed {
    B,
    KB,
    MB,
    GB;


    public static UploadSpeed func_237682_a_(long l) {
        if (l < 1024L) {
            return B;
        }
        try {
            int n = (int)(Math.log(l) / Math.log(1024.0));
            String string = String.valueOf("KMGTPE".charAt(n - 1));
            return UploadSpeed.valueOf(string + "B");
        } catch (Exception exception) {
            return GB;
        }
    }

    public static double func_237683_a_(long l, UploadSpeed uploadSpeed) {
        return uploadSpeed == B ? (double)l : (double)l / Math.pow(1024.0, uploadSpeed.ordinal());
    }

    public static String func_237684_b_(long l) {
        int n = 1024;
        if (l < 1024L) {
            return l + " B";
        }
        int n2 = (int)(Math.log(l) / Math.log(1024.0));
        String string = "" + "KMGTPE".charAt(n2 - 1);
        return String.format(Locale.ROOT, "%.1f %sB", (double)l / Math.pow(1024.0, n2), string);
    }

    public static String func_237685_b_(long l, UploadSpeed uploadSpeed) {
        return String.format("%." + (uploadSpeed == GB ? "1" : "0") + "f %s", UploadSpeed.func_237683_a_(l, uploadSpeed), uploadSpeed.name());
    }
}

