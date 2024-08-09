/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtHandler;
import io.jsonwebtoken.SupportedJwtVisitor;

public abstract class JwtHandlerAdapter<T>
extends SupportedJwtVisitor<T>
implements JwtHandler<T> {
    @Override
    public T onUnsecuredContent(Jwt<Header, byte[]> jwt) {
        return this.onContentJwt(jwt);
    }

    @Override
    public T onUnsecuredClaims(Jwt<Header, Claims> jwt) {
        return this.onClaimsJwt(jwt);
    }

    @Override
    public T onVerifiedContent(Jws<byte[]> jws) {
        return this.onContentJws(jws);
    }

    @Override
    public T onVerifiedClaims(Jws<Claims> jws) {
        return this.onClaimsJws(jws);
    }

    @Override
    public T onDecryptedContent(Jwe<byte[]> jwe) {
        return this.onContentJwe(jwe);
    }

    @Override
    public T onDecryptedClaims(Jwe<Claims> jwe) {
        return this.onClaimsJwe(jwe);
    }

    @Override
    public T onContentJwt(Jwt<Header, byte[]> jwt) {
        return super.onUnsecuredContent(jwt);
    }

    @Override
    public T onClaimsJwt(Jwt<Header, Claims> jwt) {
        return super.onUnsecuredClaims(jwt);
    }

    @Override
    public T onContentJws(Jws<byte[]> jws) {
        return super.onVerifiedContent(jws);
    }

    @Override
    public T onClaimsJws(Jws<Claims> jws) {
        return super.onVerifiedClaims(jws);
    }

    @Override
    public T onContentJwe(Jwe<byte[]> jwe) {
        return super.onDecryptedContent(jwe);
    }

    @Override
    public T onClaimsJwe(Jwe<Claims> jwe) {
        return super.onDecryptedClaims(jwe);
    }
}

