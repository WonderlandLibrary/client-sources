/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

public class LocaleSyntaxException
extends Exception {
    private static final long serialVersionUID = 1L;
    private int _index = -1;

    public LocaleSyntaxException(String string) {
        this(string, 0);
    }

    public LocaleSyntaxException(String string, int n) {
        super(string);
        this._index = n;
    }

    public int getErrorIndex() {
        return this._index;
    }
}

