/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import javax.annotation.Nullable;

@GwtIncompatible
public class UncheckedTimeoutException
extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public UncheckedTimeoutException() {
    }

    public UncheckedTimeoutException(@Nullable String string) {
        super(string);
    }

    public UncheckedTimeoutException(@Nullable Throwable throwable) {
        super(throwable);
    }

    public UncheckedTimeoutException(@Nullable String string, @Nullable Throwable throwable) {
        super(string, throwable);
    }
}

