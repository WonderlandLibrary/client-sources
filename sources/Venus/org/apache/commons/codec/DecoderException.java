/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec;

public class DecoderException
extends Exception {
    private static final long serialVersionUID = 1L;

    public DecoderException() {
    }

    public DecoderException(String string) {
        super(string);
    }

    public DecoderException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public DecoderException(Throwable throwable) {
        super(throwable);
    }
}

