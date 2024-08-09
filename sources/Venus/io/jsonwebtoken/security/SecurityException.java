/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.JwtException;

public class SecurityException
extends JwtException {
    public SecurityException(String string) {
        super(string);
    }

    public SecurityException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

