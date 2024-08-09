/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
public class UncheckedExecutionException
extends RuntimeException {
    private static final long serialVersionUID = 0L;

    protected UncheckedExecutionException() {
    }

    protected UncheckedExecutionException(@Nullable String string) {
        super(string);
    }

    public UncheckedExecutionException(@Nullable String string, @Nullable Throwable throwable) {
        super(string, throwable);
    }

    public UncheckedExecutionException(@Nullable Throwable throwable) {
        super(throwable);
    }
}

