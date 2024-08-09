/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;

public class InvalidClaimException
extends ClaimJwtException {
    private final String claimName;
    private final Object claimValue;

    protected InvalidClaimException(Header header, Claims claims, String string, Object object, String string2) {
        super(header, claims, string2);
        this.claimName = string;
        this.claimValue = object;
    }

    protected InvalidClaimException(Header header, Claims claims, String string, Object object, String string2, Throwable throwable) {
        super(header, claims, string2, throwable);
        this.claimName = string;
        this.claimValue = object;
    }

    public String getClaimName() {
        return this.claimName;
    }

    public Object getClaimValue() {
        return this.claimValue;
    }
}

