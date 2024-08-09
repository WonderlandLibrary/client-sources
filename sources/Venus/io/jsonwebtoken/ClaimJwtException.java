/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;

public abstract class ClaimJwtException
extends JwtException {
    @Deprecated
    public static final String INCORRECT_EXPECTED_CLAIM_MESSAGE_TEMPLATE = "Expected %s claim to be: %s, but was: %s.";
    @Deprecated
    public static final String MISSING_EXPECTED_CLAIM_MESSAGE_TEMPLATE = "Expected %s claim to be: %s, but was not present in the JWT claims.";
    private final Header header;
    private final Claims claims;

    protected ClaimJwtException(Header header, Claims claims, String string) {
        super(string);
        this.header = header;
        this.claims = claims;
    }

    protected ClaimJwtException(Header header, Claims claims, String string, Throwable throwable) {
        super(string, throwable);
        this.header = header;
        this.claims = claims;
    }

    public Claims getClaims() {
        return this.claims;
    }

    public Header getHeader() {
        return this.header;
    }
}

