/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.ICUException;

public class ICUCloneNotSupportedException
extends ICUException {
    private static final long serialVersionUID = -4824446458488194964L;

    public ICUCloneNotSupportedException() {
    }

    public ICUCloneNotSupportedException(String string) {
        super(string);
    }

    public ICUCloneNotSupportedException(Throwable throwable) {
        super(throwable);
    }

    public ICUCloneNotSupportedException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

