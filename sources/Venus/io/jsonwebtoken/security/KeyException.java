/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.SecurityException;

public class KeyException
extends SecurityException {
    public KeyException(String string) {
        super(string);
    }

    public KeyException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

