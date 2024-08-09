/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec;

public class EncoderException
extends Exception {
    private static final long serialVersionUID = 1L;

    public EncoderException() {
    }

    public EncoderException(String string) {
        super(string);
    }

    public EncoderException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public EncoderException(Throwable throwable) {
        super(throwable);
    }
}

