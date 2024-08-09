/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.security.Request;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.VerifyDigestRequest;
import io.jsonwebtoken.security.VerifySecureDigestRequest;
import java.io.InputStream;
import java.security.Key;

final class NoneSignatureAlgorithm
implements SecureDigestAlgorithm<Key, Key> {
    private static final String ID = "none";
    static final SecureDigestAlgorithm<Key, Key> INSTANCE = new NoneSignatureAlgorithm();

    private NoneSignatureAlgorithm() {
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public byte[] digest(SecureRequest<InputStream, Key> secureRequest) throws SecurityException {
        throw new SignatureException("The 'none' algorithm cannot be used to create signatures.");
    }

    @Override
    public boolean verify(VerifySecureDigestRequest<Key> verifySecureDigestRequest) throws SignatureException {
        throw new SignatureException("The 'none' algorithm cannot be used to verify signatures.");
    }

    public boolean equals(Object object) {
        return this == object || object instanceof SecureDigestAlgorithm && ID.equalsIgnoreCase(((SecureDigestAlgorithm)object).getId());
    }

    public int hashCode() {
        return this.getId().hashCode();
    }

    public String toString() {
        return ID;
    }

    @Override
    public boolean verify(VerifyDigestRequest verifyDigestRequest) throws SecurityException {
        return this.verify((VerifySecureDigestRequest)verifyDigestRequest);
    }

    @Override
    public byte[] digest(Request request) throws SecurityException {
        return this.digest((SecureRequest)request);
    }
}

