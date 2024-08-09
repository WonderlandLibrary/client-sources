/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.DefaultKeyResult;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.DecryptionKeyRequest;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.KeyResult;
import io.jsonwebtoken.security.SecurityException;
import javax.crypto.SecretKey;

public class DirectKeyAlgorithm
implements KeyAlgorithm<SecretKey, SecretKey> {
    static final String ID = "dir";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public KeyResult getEncryptionKey(KeyRequest<SecretKey> keyRequest) throws SecurityException {
        Assert.notNull(keyRequest, "request cannot be null.");
        SecretKey secretKey = (SecretKey)Assert.notNull(keyRequest.getPayload(), "Encryption key cannot be null.");
        return new DefaultKeyResult(secretKey);
    }

    @Override
    public SecretKey getDecryptionKey(DecryptionKeyRequest<SecretKey> decryptionKeyRequest) throws SecurityException {
        Assert.notNull(decryptionKeyRequest, "request cannot be null.");
        return (SecretKey)Assert.notNull(decryptionKeyRequest.getKey(), "Decryption key cannot be null.");
    }
}

