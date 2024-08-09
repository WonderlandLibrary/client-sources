/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.stream;

import java.io.IOException;

public final class MalformedJsonException
extends IOException {
    private static final long serialVersionUID = 1L;

    public MalformedJsonException(String string) {
        super(string);
    }

    public MalformedJsonException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public MalformedJsonException(Throwable throwable) {
        super(throwable);
    }
}

