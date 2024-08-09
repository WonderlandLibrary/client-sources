/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
final class Platform {
    static boolean isInstanceOfThrowableClass(@Nullable Throwable throwable, Class<? extends Throwable> clazz) {
        return clazz.isInstance(throwable);
    }

    private Platform() {
    }
}

