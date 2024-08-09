/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

public class ICUException
extends RuntimeException {
    private static final long serialVersionUID = -3067399656455755650L;

    public ICUException() {
    }

    public ICUException(String string) {
        super(string);
    }

    public ICUException(Throwable throwable) {
        super(throwable);
    }

    public ICUException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

