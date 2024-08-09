/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.exception;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.exception.CancelCodecException;
import io.netty.handler.codec.DecoderException;

public class CancelDecoderException
extends DecoderException
implements CancelCodecException {
    public static final CancelDecoderException CACHED = new CancelDecoderException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these"){

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    };

    public CancelDecoderException() {
    }

    public CancelDecoderException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CancelDecoderException(String string) {
        super(string);
    }

    public CancelDecoderException(Throwable throwable) {
        super(throwable);
    }

    public static CancelDecoderException generate(Throwable throwable) {
        return Via.getManager().isDebug() ? new CancelDecoderException(throwable) : CACHED;
    }
}

