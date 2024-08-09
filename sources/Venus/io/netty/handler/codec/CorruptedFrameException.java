/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.handler.codec.DecoderException;

public class CorruptedFrameException
extends DecoderException {
    private static final long serialVersionUID = 3918052232492988408L;

    public CorruptedFrameException() {
    }

    public CorruptedFrameException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CorruptedFrameException(String string) {
        super(string);
    }

    public CorruptedFrameException(Throwable throwable) {
        super(throwable);
    }
}

