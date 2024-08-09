/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.IOException;

public class SerialException
extends IOException {
    public SerialException(String string) {
        super(string);
    }

    public SerialException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

