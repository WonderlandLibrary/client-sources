/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.SerialException;

public class DeserializationException
extends SerialException {
    public DeserializationException(String string) {
        super(string);
    }

    public DeserializationException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

