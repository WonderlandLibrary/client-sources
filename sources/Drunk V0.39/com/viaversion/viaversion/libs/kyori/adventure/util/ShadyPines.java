/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public final class ShadyPines {
    private ShadyPines() {
    }

    @Deprecated
    @SafeVarargs
    @NotNull
    public static <E extends Enum<E>> Set<E> enumSet(Class<E> type, E ... constants) {
        return MonkeyBars.enumSet(type, constants);
    }

    public static boolean equals(double a, double b) {
        if (Double.doubleToLongBits(a) != Double.doubleToLongBits(b)) return false;
        return true;
    }

    public static boolean equals(float a, float b) {
        if (Float.floatToIntBits(a) != Float.floatToIntBits(b)) return false;
        return true;
    }
}

