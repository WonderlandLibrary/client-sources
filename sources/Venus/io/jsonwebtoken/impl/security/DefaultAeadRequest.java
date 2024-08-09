/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.DefaultSecureRequest;
import io.jsonwebtoken.security.AeadRequest;
import io.jsonwebtoken.security.IvSupplier;
import java.io.InputStream;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.SecretKey;

public class DefaultAeadRequest
extends DefaultSecureRequest<InputStream, SecretKey>
implements AeadRequest,
IvSupplier {
    private final byte[] IV;
    private final InputStream AAD;

    DefaultAeadRequest(InputStream inputStream, Provider provider, SecureRandom secureRandom, SecretKey secretKey, InputStream inputStream2, byte[] byArray) {
        super(inputStream, provider, secureRandom, secretKey);
        this.AAD = inputStream2;
        this.IV = byArray;
    }

    public DefaultAeadRequest(InputStream inputStream, Provider provider, SecureRandom secureRandom, SecretKey secretKey, InputStream inputStream2) {
        this(inputStream, provider, secureRandom, secretKey, inputStream2, null);
    }

    @Override
    public InputStream getAssociatedData() {
        return this.AAD;
    }

    @Override
    public byte[] getIv() {
        return this.IV;
    }
}

