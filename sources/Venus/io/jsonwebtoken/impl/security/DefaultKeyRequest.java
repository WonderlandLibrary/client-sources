/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.impl.security.DefaultRequest;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyRequest;
import java.security.Provider;
import java.security.SecureRandom;

public class DefaultKeyRequest<T>
extends DefaultRequest<T>
implements KeyRequest<T> {
    private final JweHeader header;
    private final AeadAlgorithm encryptionAlgorithm;

    public DefaultKeyRequest(T t, Provider provider, SecureRandom secureRandom, JweHeader jweHeader, AeadAlgorithm aeadAlgorithm) {
        super(t, provider, secureRandom);
        this.header = Assert.notNull(jweHeader, "JweHeader/Builder cannot be null.");
        this.encryptionAlgorithm = Assert.notNull(aeadAlgorithm, "AeadAlgorithm argument cannot be null.");
    }

    @Override
    public JweHeader getHeader() {
        return this.header;
    }

    @Override
    public AeadAlgorithm getEncryptionAlgorithm() {
        return this.encryptionAlgorithm;
    }
}

