/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
public class ExecutionError
extends Error {
    private static final long serialVersionUID = 0L;

    protected ExecutionError() {
    }

    protected ExecutionError(@Nullable String string) {
        super(string);
    }

    public ExecutionError(@Nullable String string, @Nullable Error error2) {
        super(string, error2);
    }

    public ExecutionError(@Nullable Error error2) {
        super(error2);
    }
}

