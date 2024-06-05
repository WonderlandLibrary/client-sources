/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public interface ComponentMessageThrowable {
    @Nullable
    public static Component getMessage(@Nullable Throwable throwable) {
        if (!(throwable instanceof ComponentMessageThrowable)) return null;
        return ((ComponentMessageThrowable)((Object)throwable)).componentMessage();
    }

    @Nullable
    public static Component getOrConvertMessage(@Nullable Throwable throwable) {
        if (throwable instanceof ComponentMessageThrowable) {
            return ((ComponentMessageThrowable)((Object)throwable)).componentMessage();
        }
        if (throwable == null) return null;
        String message = throwable.getMessage();
        if (message == null) return null;
        return Component.text(message);
    }

    @Nullable
    public Component componentMessage();
}

