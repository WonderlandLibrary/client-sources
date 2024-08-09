/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.time.Duration;
import org.jetbrains.annotations.NotNull;

public interface Ticks {
    public static final int TICKS_PER_SECOND = 20;
    public static final long SINGLE_TICK_DURATION_MS = 50L;

    @NotNull
    public static Duration duration(long l) {
        return Duration.ofMillis(l * 50L);
    }
}

