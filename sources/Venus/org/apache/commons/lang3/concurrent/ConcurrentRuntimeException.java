/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.concurrent.ConcurrentUtils;

public class ConcurrentRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = -6582182735562919670L;

    protected ConcurrentRuntimeException() {
    }

    public ConcurrentRuntimeException(Throwable throwable) {
        super(ConcurrentUtils.checkedException(throwable));
    }

    public ConcurrentRuntimeException(String string, Throwable throwable) {
        super(string, ConcurrentUtils.checkedException(throwable));
    }
}

