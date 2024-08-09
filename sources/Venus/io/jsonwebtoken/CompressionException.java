/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.io.IOException;

public class CompressionException
extends IOException {
    public CompressionException(String string) {
        super(string);
    }

    public CompressionException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

