/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtVisitor;
import io.jsonwebtoken.impl.DefaultProtectedJwt;
import io.jsonwebtoken.io.Decoders;

public class DefaultJws<P>
extends DefaultProtectedJwt<JwsHeader, P>
implements Jws<P> {
    private static final String DIGEST_NAME = "signature";
    private final String signature;

    public DefaultJws(JwsHeader jwsHeader, P p, String string) {
        super(jwsHeader, p, Decoders.BASE64URL.decode(string), DIGEST_NAME);
        this.signature = string;
    }

    @Override
    public String getSignature() {
        return this.signature;
    }

    @Override
    public <T> T accept(JwtVisitor<T> jwtVisitor) {
        return jwtVisitor.visit(this);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public byte[] getDigest() {
        return super.getDigest();
    }
}

