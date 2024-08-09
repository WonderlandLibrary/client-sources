/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.DefaultSecureRequest;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.VerifySecureDigestRequest;
import java.io.InputStream;
import java.security.Key;
import java.security.Provider;
import java.security.SecureRandom;

public class DefaultVerifySecureDigestRequest<K extends Key>
extends DefaultSecureRequest<InputStream, K>
implements VerifySecureDigestRequest<K> {
    private final byte[] digest;

    public DefaultVerifySecureDigestRequest(InputStream inputStream, Provider provider, SecureRandom secureRandom, K k, byte[] byArray) {
        super(inputStream, provider, secureRandom, k);
        this.digest = Assert.notEmpty(byArray, "Digest byte array cannot be null or empty.");
    }

    @Override
    public byte[] getDigest() {
        return this.digest;
    }
}

