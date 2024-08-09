/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

public class CodecException
extends RuntimeException {
    private static final long serialVersionUID = -1464830400709348473L;

    public CodecException() {
    }

    public CodecException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CodecException(String string) {
        super(string);
    }

    public CodecException(Throwable throwable) {
        super(throwable);
    }
}

