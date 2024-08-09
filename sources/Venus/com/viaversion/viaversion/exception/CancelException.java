/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.exception;

import com.viaversion.viaversion.api.Via;

public class CancelException
extends Exception {
    public static final CancelException CACHED = new CancelException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these"){

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    };

    public CancelException() {
    }

    public CancelException(String string) {
        super(string);
    }

    public CancelException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CancelException(Throwable throwable) {
        super(throwable);
    }

    public CancelException(String string, Throwable throwable, boolean bl, boolean bl2) {
        super(string, throwable, bl, bl2);
    }

    public static CancelException generate() {
        return Via.getManager().isDebug() ? new CancelException() : CACHED;
    }
}

