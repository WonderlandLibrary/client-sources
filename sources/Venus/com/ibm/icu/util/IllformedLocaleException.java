/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

public class IllformedLocaleException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int _errIdx = -1;

    public IllformedLocaleException() {
    }

    public IllformedLocaleException(String string) {
        super(string);
    }

    public IllformedLocaleException(String string, int n) {
        super(string + (n < 0 ? "" : " [at index " + n + "]"));
        this._errIdx = n;
    }

    public int getErrorIndex() {
        return this._errIdx;
    }
}

