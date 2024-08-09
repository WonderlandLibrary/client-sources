/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.SerialException;

public class SerializationException
extends SerialException {
    public SerializationException(String string) {
        super(string);
    }

    public SerializationException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

