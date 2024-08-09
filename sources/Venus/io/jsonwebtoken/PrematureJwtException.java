/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;

public class PrematureJwtException
extends ClaimJwtException {
    public PrematureJwtException(Header header, Claims claims, String string) {
        super(header, claims, string);
    }

    @Deprecated
    public PrematureJwtException(Header header, Claims claims, String string, Throwable throwable) {
        super(header, claims, string, throwable);
    }
}

