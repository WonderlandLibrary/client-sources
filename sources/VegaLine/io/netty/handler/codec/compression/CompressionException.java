/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.handler.codec.EncoderException;

public class CompressionException
extends EncoderException {
    private static final long serialVersionUID = 5603413481274811897L;

    public CompressionException() {
    }

    public CompressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompressionException(String message) {
        super(message);
    }

    public CompressionException(Throwable cause) {
        super(cause);
    }
}

