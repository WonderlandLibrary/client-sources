/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.JwtException;

public class UnsupportedJwtException
extends JwtException {
    public UnsupportedJwtException(String string) {
        super(string);
    }

    public UnsupportedJwtException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

