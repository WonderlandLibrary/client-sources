/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtVisitor;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.lang.Assert;

public class SupportedJwtVisitor<T>
implements JwtVisitor<T> {
    @Override
    public T visit(Jwt<?, ?> jwt) {
        Assert.notNull(jwt, "JWT cannot be null.");
        Object obj = jwt.getPayload();
        if (obj instanceof byte[]) {
            return this.onUnsecuredContent(jwt);
        }
        Assert.stateIsInstance(Claims.class, obj, "Unexpected payload data type: ");
        return this.onUnsecuredClaims(jwt);
    }

    public T onUnsecuredContent(Jwt<Header, byte[]> jwt) throws UnsupportedJwtException {
        throw new UnsupportedJwtException("Unexpected unsecured content JWT.");
    }

    public T onUnsecuredClaims(Jwt<Header, Claims> jwt) {
        throw new UnsupportedJwtException("Unexpected unsecured Claims JWT.");
    }

    @Override
    public T visit(Jws<?> jws) {
        Assert.notNull(jws, "JWS cannot be null.");
        Object p = jws.getPayload();
        if (p instanceof byte[]) {
            return this.onVerifiedContent(jws);
        }
        Assert.stateIsInstance(Claims.class, p, "Unexpected payload data type: ");
        return this.onVerifiedClaims(jws);
    }

    public T onVerifiedContent(Jws<byte[]> jws) {
        throw new UnsupportedJwtException("Unexpected content JWS.");
    }

    public T onVerifiedClaims(Jws<Claims> jws) {
        throw new UnsupportedJwtException("Unexpected Claims JWS.");
    }

    @Override
    public T visit(Jwe<?> jwe) {
        Assert.notNull(jwe, "JWE cannot be null.");
        Object p = jwe.getPayload();
        if (p instanceof byte[]) {
            return this.onDecryptedContent(jwe);
        }
        Assert.stateIsInstance(Claims.class, p, "Unexpected payload data type: ");
        return this.onDecryptedClaims(jwe);
    }

    public T onDecryptedContent(Jwe<byte[]> jwe) {
        throw new UnsupportedJwtException("Unexpected content JWE.");
    }

    public T onDecryptedClaims(Jwe<Claims> jwe) {
        throw new UnsupportedJwtException("Unexpected Claims JWE.");
    }
}

