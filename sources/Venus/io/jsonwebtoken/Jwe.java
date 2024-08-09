/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.JwtVisitor;
import io.jsonwebtoken.ProtectedJwt;
import io.jsonwebtoken.SupportedJwtVisitor;

public interface Jwe<B>
extends ProtectedJwt<JweHeader, B> {
    public static final JwtVisitor<Jwe<byte[]>> CONTENT = new SupportedJwtVisitor<Jwe<byte[]>>(){

        @Override
        public Jwe<byte[]> onDecryptedContent(Jwe<byte[]> jwe) {
            return jwe;
        }

        @Override
        public Object onDecryptedContent(Jwe jwe) {
            return this.onDecryptedContent(jwe);
        }
    };
    public static final JwtVisitor<Jwe<Claims>> CLAIMS = new SupportedJwtVisitor<Jwe<Claims>>(){

        @Override
        public Jwe<Claims> onDecryptedClaims(Jwe<Claims> jwe) {
            return jwe;
        }

        @Override
        public Object onDecryptedClaims(Jwe jwe) {
            return this.onDecryptedClaims(jwe);
        }
    };

    public byte[] getInitializationVector();
}

