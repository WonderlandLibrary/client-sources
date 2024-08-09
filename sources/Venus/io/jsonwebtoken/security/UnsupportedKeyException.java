/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.KeyException;

public class UnsupportedKeyException
extends KeyException {
    public UnsupportedKeyException(String string) {
        super(string);
    }

    public UnsupportedKeyException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

