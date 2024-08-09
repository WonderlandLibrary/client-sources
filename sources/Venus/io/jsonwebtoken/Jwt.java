/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtVisitor;
import io.jsonwebtoken.SupportedJwtVisitor;
import io.jsonwebtoken.UnsupportedJwtException;

public interface Jwt<H extends Header, P> {
    public static final JwtVisitor<Jwt<Header, byte[]>> UNSECURED_CONTENT = new SupportedJwtVisitor<Jwt<Header, byte[]>>(){

        @Override
        public Jwt<Header, byte[]> onUnsecuredContent(Jwt<Header, byte[]> jwt) {
            return jwt;
        }

        @Override
        public Object onUnsecuredContent(Jwt jwt) throws UnsupportedJwtException {
            return this.onUnsecuredContent(jwt);
        }
    };
    public static final JwtVisitor<Jwt<Header, Claims>> UNSECURED_CLAIMS = new SupportedJwtVisitor<Jwt<Header, Claims>>(){

        @Override
        public Jwt<Header, Claims> onUnsecuredClaims(Jwt<Header, Claims> jwt) {
            return jwt;
        }

        @Override
        public Object onUnsecuredClaims(Jwt jwt) {
            return this.onUnsecuredClaims(jwt);
        }
    };

    public H getHeader();

    @Deprecated
    public P getBody();

    public P getPayload();

    public <T> T accept(JwtVisitor<T> var1);
}

