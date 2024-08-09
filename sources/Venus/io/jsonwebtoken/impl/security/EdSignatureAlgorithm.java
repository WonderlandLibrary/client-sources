/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.AbstractSignatureAlgorithm;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.KeyPairBuilder;
import io.jsonwebtoken.security.Request;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.VerifyDigestRequest;
import java.security.Key;
import java.security.PrivateKey;

final class EdSignatureAlgorithm
extends AbstractSignatureAlgorithm {
    private static final String ID = "EdDSA";
    private final EdwardsCurve preferredCurve = EdwardsCurve.Ed448;
    static final EdSignatureAlgorithm INSTANCE = new EdSignatureAlgorithm();

    static boolean isSigningKey(PrivateKey privateKey) {
        EdwardsCurve edwardsCurve = EdwardsCurve.findByKey(privateKey);
        return edwardsCurve != null && edwardsCurve.isSignatureCurve();
    }

    private EdSignatureAlgorithm() {
        super(ID, ID);
        Assert.isTrue(this.preferredCurve.isSignatureCurve(), "Must be signature curve, not key agreement curve.");
    }

    @Override
    protected String getJcaName(Request<?> request) {
        SecureRequest secureRequest = Assert.isInstanceOf(SecureRequest.class, request, "SecureRequests are required.");
        Key key = (Key)Assert.notNull(secureRequest.getKey(), "Request key cannot be null.");
        String string = this.getJcaName();
        if (!(request instanceof VerifyDigestRequest)) {
            string = EdwardsCurve.forKey(key).getJcaName();
        }
        return string;
    }

    @Override
    public KeyPairBuilder keyPair() {
        return this.preferredCurve.keyPair();
    }

    @Override
    protected void validateKey(Key key, boolean bl) {
        super.validateKey(key, bl);
        EdwardsCurve edwardsCurve = EdwardsCurve.forKey(key);
        if (!edwardsCurve.isSignatureCurve()) {
            String string = edwardsCurve.getId() + " keys may not be used with " + this.getId() + " digital signatures per " + "https://www.rfc-editor.org/rfc/rfc8037.html#section-3.2";
            throw new InvalidKeyException(string);
        }
    }
}

