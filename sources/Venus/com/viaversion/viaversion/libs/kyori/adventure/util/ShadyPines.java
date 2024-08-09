/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
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
    public static <E extends Enum<E>> Set<E> enumSet(Class<E> clazz, E @NotNull ... EArray) {
        return MonkeyBars.enumSet(clazz, EArray);
    }

    public static boolean equals(double d, double d2) {
        return Double.doubleToLongBits(d) == Double.doubleToLongBits(d2);
    }

    public static boolean equals(float f, float f2) {
        return Float.floatToIntBits(f) == Float.floatToIntBits(f2);
    }
}

