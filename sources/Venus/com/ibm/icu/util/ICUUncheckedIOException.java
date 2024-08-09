/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

public class ICUUncheckedIOException
extends RuntimeException {
    private static final long serialVersionUID = 1210263498513384449L;

    public ICUUncheckedIOException() {
    }

    public ICUUncheckedIOException(String string) {
        super(string);
    }

    public ICUUncheckedIOException(Throwable throwable) {
        super(throwable);
    }

    public ICUUncheckedIOException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

