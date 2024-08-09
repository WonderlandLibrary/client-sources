/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.impl.security.DefaultKeyRequest;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.DecryptionKeyRequest;
import java.security.Key;
import java.security.Provider;
import java.security.SecureRandom;

public class DefaultDecryptionKeyRequest<K extends Key>
extends DefaultKeyRequest<byte[]>
implements DecryptionKeyRequest<K> {
    private final K decryptionKey;

    public DefaultDecryptionKeyRequest(byte[] byArray, Provider provider, SecureRandom secureRandom, JweHeader jweHeader, AeadAlgorithm aeadAlgorithm, K k) {
        super(byArray, provider, secureRandom, jweHeader, aeadAlgorithm);
        this.decryptionKey = (Key)Assert.notNull(k, "decryption key cannot be null.");
    }

    @Override
    protected void assertBytePayload(byte[] byArray) {
        Assert.notNull(byArray, "encrypted key bytes cannot be null (but may be empty.");
    }

    @Override
    public K getKey() {
        return this.decryptionKey;
    }
}

