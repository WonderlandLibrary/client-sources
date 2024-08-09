/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.HashAlgorithm;
import io.jsonwebtoken.security.JwkThumbprint;
import java.net.URI;
import java.security.MessageDigest;

class DefaultJwkThumbprint
implements JwkThumbprint {
    private static final String URI_PREFIX = "urn:ietf:params:oauth:jwk-thumbprint:";
    private final byte[] digest;
    private final HashAlgorithm alg;
    private final URI uri;
    private final int hashcode;
    private final String sval;

    DefaultJwkThumbprint(byte[] byArray, HashAlgorithm hashAlgorithm) {
        this.digest = Assert.notEmpty(byArray, "Thumbprint digest byte array cannot be null or empty.");
        this.alg = Assert.notNull(hashAlgorithm, "Thumbprint HashAlgorithm cannot be null.");
        String string = Assert.hasText(Strings.clean(hashAlgorithm.getId()), "Thumbprint HashAlgorithm id cannot be null or empty.");
        String string2 = Encoders.BASE64URL.encode(byArray);
        String string3 = URI_PREFIX + string + ":" + string2;
        this.uri = URI.create(string3);
        this.hashcode = Objects.nullSafeHashCode(this.digest, this.alg);
        this.sval = Encoders.BASE64URL.encode(byArray);
    }

    @Override
    public HashAlgorithm getHashAlgorithm() {
        return this.alg;
    }

    @Override
    public byte[] toByteArray() {
        return (byte[])this.digest.clone();
    }

    @Override
    public URI toURI() {
        return this.uri;
    }

    @Override
    public String toString() {
        return this.sval;
    }

    public int hashCode() {
        return this.hashcode;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof DefaultJwkThumbprint) {
            DefaultJwkThumbprint defaultJwkThumbprint = (DefaultJwkThumbprint)object;
            return this.alg.equals(defaultJwkThumbprint.alg) && MessageDigest.isEqual(this.digest, defaultJwkThumbprint.digest);
        }
        return true;
    }
}

