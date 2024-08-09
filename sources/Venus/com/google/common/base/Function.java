/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import javax.annotation.Nullable;

@FunctionalInterface
@GwtCompatible
public interface Function<F, T>
extends java.util.function.Function<F, T> {
    @Override
    @Nullable
    @CanIgnoreReturnValue
    public T apply(@Nullable F var1);

    public boolean equals(@Nullable Object var1);
}

