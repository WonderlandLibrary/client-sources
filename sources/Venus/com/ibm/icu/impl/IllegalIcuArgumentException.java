/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IllegalIcuArgumentException
extends IllegalArgumentException {
    private static final long serialVersionUID = 3789261542830211225L;

    public IllegalIcuArgumentException(String string) {
        super(string);
    }

    public IllegalIcuArgumentException(Throwable throwable) {
        super(throwable);
    }

    public IllegalIcuArgumentException(String string, Throwable throwable) {
        super(string, throwable);
    }

    @Override
    public synchronized IllegalIcuArgumentException initCause(Throwable throwable) {
        return (IllegalIcuArgumentException)super.initCause(throwable);
    }

    @Override
    public Throwable initCause(Throwable throwable) {
        return this.initCause(throwable);
    }
}

