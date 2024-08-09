/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.concurrent.ConcurrentUtils;

public class ConcurrentException
extends Exception {
    private static final long serialVersionUID = 6622707671812226130L;

    protected ConcurrentException() {
    }

    public ConcurrentException(Throwable throwable) {
        super(ConcurrentUtils.checkedException(throwable));
    }

    public ConcurrentException(String string, Throwable throwable) {
        super(string, ConcurrentUtils.checkedException(throwable));
    }
}

