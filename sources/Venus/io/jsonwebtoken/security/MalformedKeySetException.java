/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.SecurityException;

public class MalformedKeySetException
extends SecurityException {
    public MalformedKeySetException(String string) {
        super(string);
    }

    public MalformedKeySetException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

