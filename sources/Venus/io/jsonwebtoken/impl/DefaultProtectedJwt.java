/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.ProtectedHeader;
import io.jsonwebtoken.ProtectedJwt;
import io.jsonwebtoken.impl.DefaultJwt;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Objects;
import java.security.MessageDigest;

abstract class DefaultProtectedJwt<H extends ProtectedHeader, P>
extends DefaultJwt<H, P>
implements ProtectedJwt<H, P> {
    protected final byte[] digest;
    private final String digestName;

    protected DefaultProtectedJwt(H h, P p, byte[] byArray, String string) {
        super(h, p);
        this.digest = Assert.notEmpty(byArray, "Digest byte array cannot be null or empty.");
        this.digestName = Assert.hasText(string, "digestName cannot be null or empty.");
    }

    @Override
    public byte[] getDigest() {
        return (byte[])this.digest.clone();
    }

    @Override
    protected StringBuilder toStringBuilder() {
        String string = Encoders.BASE64URL.encode(this.digest);
        return super.toStringBuilder().append(',').append(this.digestName).append('=').append(string);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof DefaultProtectedJwt) {
            DefaultProtectedJwt defaultProtectedJwt = (DefaultProtectedJwt)object;
            return super.equals(defaultProtectedJwt) && MessageDigest.isEqual(this.digest, defaultProtectedJwt.digest);
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.nullSafeHashCode(this.getHeader(), this.getPayload(), this.digest);
    }
}

