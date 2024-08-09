/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.DefaultRequest;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.VerifyDigestRequest;
import java.io.InputStream;
import java.security.Provider;
import java.security.SecureRandom;

public class DefaultVerifyDigestRequest
extends DefaultRequest<InputStream>
implements VerifyDigestRequest {
    private final byte[] digest;

    public DefaultVerifyDigestRequest(InputStream inputStream, Provider provider, SecureRandom secureRandom, byte[] byArray) {
        super(inputStream, provider, secureRandom);
        this.digest = Assert.notEmpty(byArray, "Digest byte array cannot be null or empty.");
    }

    @Override
    public byte[] getDigest() {
        return this.digest;
    }
}

