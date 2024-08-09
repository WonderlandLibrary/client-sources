/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public interface ComponentMessageThrowable {
    @Nullable
    public static Component getMessage(@Nullable Throwable throwable) {
        if (throwable instanceof ComponentMessageThrowable) {
            return ((ComponentMessageThrowable)((Object)throwable)).componentMessage();
        }
        return null;
    }

    @Nullable
    public static Component getOrConvertMessage(@Nullable Throwable throwable) {
        String string;
        if (throwable instanceof ComponentMessageThrowable) {
            return ((ComponentMessageThrowable)((Object)throwable)).componentMessage();
        }
        if (throwable != null && (string = throwable.getMessage()) != null) {
            return Component.text(string);
        }
        return null;
    }

    @Nullable
    public Component componentMessage();
}

