/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.JwtVisitor;
import io.jsonwebtoken.impl.DefaultProtectedJwt;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Objects;
import java.security.MessageDigest;

public class DefaultJwe<P>
extends DefaultProtectedJwt<JweHeader, P>
implements Jwe<P> {
    private static final String DIGEST_NAME = "tag";
    private final byte[] iv;

    public DefaultJwe(JweHeader jweHeader, P p, byte[] byArray, byte[] byArray2) {
        super(jweHeader, p, byArray2, DIGEST_NAME);
        this.iv = Assert.notEmpty(byArray, "Initialization vector cannot be null or empty.");
    }

    @Override
    public byte[] getInitializationVector() {
        return (byte[])this.iv.clone();
    }

    @Override
    protected StringBuilder toStringBuilder() {
        return super.toStringBuilder().append(",iv=").append(Encoders.BASE64URL.encode(this.iv));
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof Jwe) {
            Jwe jwe = (Jwe)object;
            return super.equals(jwe) && MessageDigest.isEqual(this.iv, jwe.getInitializationVector());
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.nullSafeHashCode(this.getHeader(), this.getPayload(), this.iv, this.digest);
    }

    @Override
    public <T> T accept(JwtVisitor<T> jwtVisitor) {
        return jwtVisitor.visit(this);
    }

    @Override
    public byte[] getDigest() {
        return super.getDigest();
    }
}

