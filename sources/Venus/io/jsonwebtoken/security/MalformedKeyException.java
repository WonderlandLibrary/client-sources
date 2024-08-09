/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.InvalidKeyException;

public class MalformedKeyException
extends InvalidKeyException {
    public MalformedKeyException(String string) {
        super(string);
    }

    public MalformedKeyException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

