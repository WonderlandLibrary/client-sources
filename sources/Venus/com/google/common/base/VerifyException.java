/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public class VerifyException
extends RuntimeException {
    public VerifyException() {
    }

    public VerifyException(@Nullable String string) {
        super(string);
    }

    public VerifyException(@Nullable Throwable throwable) {
        super(throwable);
    }

    public VerifyException(@Nullable String string, @Nullable Throwable throwable) {
        super(string, throwable);
    }
}

