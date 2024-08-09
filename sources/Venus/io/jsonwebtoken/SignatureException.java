/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.security.SecurityException;

@Deprecated
public class SignatureException
extends SecurityException {
    public SignatureException(String string) {
        super(string);
    }

    public SignatureException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

