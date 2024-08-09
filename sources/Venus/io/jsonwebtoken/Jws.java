/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtVisitor;
import io.jsonwebtoken.ProtectedJwt;
import io.jsonwebtoken.SupportedJwtVisitor;

public interface Jws<P>
extends ProtectedJwt<JwsHeader, P> {
    public static final JwtVisitor<Jws<byte[]>> CONTENT = new SupportedJwtVisitor<Jws<byte[]>>(){

        @Override
        public Jws<byte[]> onVerifiedContent(Jws<byte[]> jws) {
            return jws;
        }

        @Override
        public Object onVerifiedContent(Jws jws) {
            return this.onVerifiedContent(jws);
        }
    };
    public static final JwtVisitor<Jws<Claims>> CLAIMS = new SupportedJwtVisitor<Jws<Claims>>(){

        @Override
        public Jws<Claims> onVerifiedClaims(Jws<Claims> jws) {
            return jws;
        }

        @Override
        public Object onVerifiedClaims(Jws jws) {
            return this.onVerifiedClaims(jws);
        }
    };

    @Deprecated
    public String getSignature();
}

