/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.exception;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.exception.CancelCodecException;
import io.netty.handler.codec.EncoderException;

public class CancelEncoderException
extends EncoderException
implements CancelCodecException {
    public static final CancelEncoderException CACHED = new CancelEncoderException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these"){

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    };

    public CancelEncoderException() {
    }

    public CancelEncoderException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CancelEncoderException(String string) {
        super(string);
    }

    public CancelEncoderException(Throwable throwable) {
        super(throwable);
    }

    public static CancelEncoderException generate(Throwable throwable) {
        return Via.getManager().isDebug() ? new CancelEncoderException(throwable) : CACHED;
    }
}

