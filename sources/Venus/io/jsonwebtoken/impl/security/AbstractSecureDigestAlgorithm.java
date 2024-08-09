/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.CryptoAlgorithm;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.KeyException;
import io.jsonwebtoken.security.Request;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.VerifyDigestRequest;
import io.jsonwebtoken.security.VerifySecureDigestRequest;
import java.io.InputStream;
import java.security.Key;

abstract class AbstractSecureDigestAlgorithm<S extends Key, V extends Key>
extends CryptoAlgorithm
implements SecureDigestAlgorithm<S, V> {
    AbstractSecureDigestAlgorithm(String string, String string2) {
        super(string, string2);
    }

    protected static String keyType(boolean bl) {
        return bl ? "signing" : "verification";
    }

    protected abstract void validateKey(Key var1, boolean var2);

    @Override
    public final byte[] digest(SecureRequest<InputStream, S> secureRequest) throws SecurityException {
        Assert.notNull(secureRequest, "Request cannot be null.");
        Key key = (Key)Assert.notNull(secureRequest.getKey(), "Signing key cannot be null.");
        Assert.notNull(secureRequest.getPayload(), "Request content cannot be null.");
        try {
            this.validateKey(key, false);
            return this.doDigest(secureRequest);
        } catch (KeyException | SignatureException securityException) {
            throw securityException;
        } catch (Exception exception) {
            String string = "Unable to compute " + this.getId() + " signature with JCA algorithm '" + this.getJcaName() + "' " + "using key {" + KeysBridge.toString(key) + "}: " + exception.getMessage();
            throw new SignatureException(string, exception);
        }
    }

    protected abstract byte[] doDigest(SecureRequest<InputStream, S> var1) throws Exception;

    @Override
    public final boolean verify(VerifySecureDigestRequest<V> verifySecureDigestRequest) throws SecurityException {
        Assert.notNull(verifySecureDigestRequest, "Request cannot be null.");
        Key key = (Key)Assert.notNull(verifySecureDigestRequest.getKey(), "Verification key cannot be null.");
        Assert.notNull(verifySecureDigestRequest.getPayload(), "Request content cannot be null or empty.");
        Assert.notEmpty(verifySecureDigestRequest.getDigest(), "Request signature byte array cannot be null or empty.");
        try {
            this.validateKey(key, true);
            return this.doVerify(verifySecureDigestRequest);
        } catch (KeyException | SignatureException securityException) {
            throw securityException;
        } catch (Exception exception) {
            String string = "Unable to verify " + this.getId() + " signature with JCA algorithm '" + this.getJcaName() + "' " + "using key {" + KeysBridge.toString(key) + "}: " + exception.getMessage();
            throw new SignatureException(string, exception);
        }
    }

    protected abstract boolean doVerify(VerifySecureDigestRequest<V> var1);

    @Override
    public boolean verify(VerifyDigestRequest verifyDigestRequest) throws SecurityException {
        return this.verify((VerifySecureDigestRequest)verifyDigestRequest);
    }

    @Override
    public byte[] digest(Request request) throws SecurityException {
        return this.digest((SecureRequest)request);
    }
}

