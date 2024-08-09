/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

public class JwtException
extends RuntimeException {
    public JwtException(String string) {
        super(string);
    }

    public JwtException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

