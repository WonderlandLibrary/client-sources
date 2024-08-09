/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.util.Util;

public interface IStatFormatter {
    public static final DecimalFormat DECIMAL_FORMAT = Util.make(new DecimalFormat("########0.00"), IStatFormatter::lambda$static$0);
    public static final IStatFormatter DEFAULT = NumberFormat.getIntegerInstance(Locale.US)::format;
    public static final IStatFormatter DIVIDE_BY_TEN = IStatFormatter::lambda$static$1;
    public static final IStatFormatter DISTANCE = IStatFormatter::lambda$static$2;
    public static final IStatFormatter TIME = IStatFormatter::lambda$static$3;

    public String format(int var1);

    private static String lambda$static$3(int n) {
        double d = (double)n / 20.0;
        double d2 = d / 60.0;
        double d3 = d2 / 60.0;
        double d4 = d3 / 24.0;
        double d5 = d4 / 365.0;
        if (d5 > 0.5) {
            return DECIMAL_FORMAT.format(d5) + " y";
        }
        if (d4 > 0.5) {
            return DECIMAL_FORMAT.format(d4) + " d";
        }
        if (d3 > 0.5) {
            return DECIMAL_FORMAT.format(d3) + " h";
        }
        return d2 > 0.5 ? DECIMAL_FORMAT.format(d2) + " m" : d + " s";
    }

    private static String lambda$static$2(int n) {
        double d = (double)n / 100.0;
        double d2 = d / 1000.0;
        if (d2 > 0.5) {
            return DECIMAL_FORMAT.format(d2) + " km";
        }
        return d > 0.5 ? DECIMAL_FORMAT.format(d) + " m" : n + " cm";
    }

    private static String lambda$static$1(int n) {
        return DECIMAL_FORMAT.format((double)n * 0.1);
    }

    private static void lambda$static$0(DecimalFormat decimalFormat) {
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    }
}

