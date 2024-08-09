/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.impl.security.JcaTemplate;
import io.jsonwebtoken.impl.security.Randoms;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.Request;
import io.jsonwebtoken.security.SecretKeyBuilder;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.SecretKey;

abstract class CryptoAlgorithm
implements Identifiable {
    private final String ID;
    private final String jcaName;

    CryptoAlgorithm(String string, String string2) {
        Assert.hasText(string, "id cannot be null or empty.");
        this.ID = string;
        Assert.hasText(string2, "jcaName cannot be null or empty.");
        this.jcaName = string2;
    }

    @Override
    public String getId() {
        return this.ID;
    }

    String getJcaName() {
        return this.jcaName;
    }

    static SecureRandom ensureSecureRandom(Request<?> request) {
        SecureRandom secureRandom = request != null ? request.getSecureRandom() : null;
        return secureRandom != null ? secureRandom : Randoms.secureRandom();
    }

    protected JcaTemplate jca() {
        return new JcaTemplate(this.getJcaName());
    }

    protected JcaTemplate jca(Request<?> request) {
        Assert.notNull(request, "request cannot be null.");
        String string = Assert.hasText(this.getJcaName(request), "Request jcaName cannot be null or empty.");
        Provider provider = request.getProvider();
        SecureRandom secureRandom = CryptoAlgorithm.ensureSecureRandom(request);
        return new JcaTemplate(string, provider, secureRandom);
    }

    protected String getJcaName(Request<?> request) {
        return this.getJcaName();
    }

    protected SecretKey generateCek(KeyRequest<?> keyRequest) {
        AeadAlgorithm aeadAlgorithm = Assert.notNull(keyRequest.getEncryptionAlgorithm(), "Request encryptionAlgorithm cannot be null.");
        SecretKeyBuilder secretKeyBuilder = (SecretKeyBuilder)Assert.notNull(aeadAlgorithm.key(), "Request encryptionAlgorithm KeyBuilder cannot be null.");
        SecretKey secretKey = (SecretKey)((SecretKeyBuilder)secretKeyBuilder.random(keyRequest.getSecureRandom())).build();
        return Assert.notNull(secretKey, "Request encryptionAlgorithm SecretKeyBuilder cannot produce null keys.");
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof CryptoAlgorithm) {
            CryptoAlgorithm cryptoAlgorithm = (CryptoAlgorithm)object;
            return this.ID.equals(cryptoAlgorithm.getId()) && this.jcaName.equals(cryptoAlgorithm.getJcaName());
        }
        return true;
    }

    public int hashCode() {
        int n = 7;
        n = 31 * n + this.ID.hashCode();
        n = 31 * n + this.jcaName.hashCode();
        return n;
    }

    public String toString() {
        return this.ID;
    }
}

