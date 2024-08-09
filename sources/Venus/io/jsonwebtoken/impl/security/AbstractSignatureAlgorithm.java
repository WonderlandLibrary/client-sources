/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.security.AbstractSecureDigestAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.SignatureAlgorithm;
import io.jsonwebtoken.security.VerifySecureDigestRequest;
import java.io.InputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.text.MessageFormat;

abstract class AbstractSignatureAlgorithm
extends AbstractSecureDigestAlgorithm<PrivateKey, PublicKey>
implements SignatureAlgorithm {
    private static final String KEY_TYPE_MSG_PATTERN = "{0} {1} keys must be {2}s (implement {3}). Provided key type: {4}.";

    AbstractSignatureAlgorithm(String string, String string2) {
        super(string, string2);
    }

    @Override
    protected void validateKey(Key key, boolean bl) {
        Class clazz;
        Class clazz2 = clazz = bl ? PrivateKey.class : PublicKey.class;
        if (!clazz.isInstance(key)) {
            String string = MessageFormat.format(KEY_TYPE_MSG_PATTERN, this.getId(), AbstractSignatureAlgorithm.keyType(bl), clazz.getSimpleName(), clazz.getName(), key.getClass().getName());
            throw new InvalidKeyException(string);
        }
    }

    protected final byte[] sign(Signature signature, InputStream inputStream) throws Exception {
        byte[] byArray = new byte[2048];
        int n = 0;
        while (n != -1) {
            n = inputStream.read(byArray);
            if (n <= 0) continue;
            signature.update(byArray, 0, n);
        }
        return signature.sign();
    }

    @Override
    protected byte[] doDigest(SecureRequest<InputStream, PrivateKey> secureRequest) {
        return this.jca(secureRequest).withSignature(new CheckedFunction<Signature, byte[]>(this, secureRequest){
            final SecureRequest val$request;
            final AbstractSignatureAlgorithm this$0;
            {
                this.this$0 = abstractSignatureAlgorithm;
                this.val$request = secureRequest;
            }

            @Override
            public byte[] apply(Signature signature) throws Exception {
                signature.initSign((PrivateKey)this.val$request.getKey());
                return this.this$0.sign(signature, (InputStream)this.val$request.getPayload());
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Signature)object);
            }
        });
    }

    protected boolean verify(Signature signature, InputStream inputStream, byte[] byArray) throws Exception {
        byte[] byArray2 = new byte[1024];
        int n = 0;
        while (n != -1) {
            n = inputStream.read(byArray2);
            if (n <= 0) continue;
            signature.update(byArray2, 0, n);
        }
        return signature.verify(byArray);
    }

    @Override
    protected boolean doVerify(VerifySecureDigestRequest<PublicKey> verifySecureDigestRequest) {
        return this.jca(verifySecureDigestRequest).withSignature(new CheckedFunction<Signature, Boolean>(this, verifySecureDigestRequest){
            final VerifySecureDigestRequest val$request;
            final AbstractSignatureAlgorithm this$0;
            {
                this.this$0 = abstractSignatureAlgorithm;
                this.val$request = verifySecureDigestRequest;
            }

            @Override
            public Boolean apply(Signature signature) throws Exception {
                signature.initVerify((PublicKey)this.val$request.getKey());
                return this.this$0.verify(signature, (InputStream)this.val$request.getPayload(), this.val$request.getDigest());
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Signature)object);
            }
        });
    }
}

