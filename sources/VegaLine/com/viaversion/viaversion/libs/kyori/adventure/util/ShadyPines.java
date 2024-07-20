/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public final class ShadyPines {
    private ShadyPines() {
    }

    @Deprecated
    @SafeVarargs
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static <E extends Enum<E>> Set<E> enumSet(Class<E> type2, E @NotNull ... constants) {
        return MonkeyBars.enumSet(type2, constants);
    }

    public static boolean equals(double a, double b) {
        return Double.doubleToLongBits(a) == Double.doubleToLongBits(b);
    }

    public static boolean equals(float a, float b) {
        return Float.floatToIntBits(a) == Float.floatToIntBits(b);
    }
}

