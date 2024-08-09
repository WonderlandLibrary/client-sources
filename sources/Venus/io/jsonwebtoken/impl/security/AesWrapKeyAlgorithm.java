/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.security.AesAlgorithm;
import io.jsonwebtoken.impl.security.DefaultKeyResult;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.DecryptionKeyRequest;
import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.KeyResult;
import io.jsonwebtoken.security.SecretKeyAlgorithm;
import io.jsonwebtoken.security.SecretKeyBuilder;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class AesWrapKeyAlgorithm
extends AesAlgorithm
implements SecretKeyAlgorithm {
    private static final String TRANSFORMATION = "AESWrap";

    public AesWrapKeyAlgorithm(int n) {
        super("A" + n + "KW", TRANSFORMATION, n);
    }

    @Override
    public KeyResult getEncryptionKey(KeyRequest<SecretKey> keyRequest) throws SecurityException {
        Assert.notNull(keyRequest, "request cannot be null.");
        SecretKey secretKey = this.assertKey((SecretKey)keyRequest.getPayload());
        SecretKey secretKey2 = this.generateCek(keyRequest);
        byte[] byArray = this.jca(keyRequest).withCipher(new CheckedFunction<Cipher, byte[]>(this, secretKey, secretKey2){
            final SecretKey val$kek;
            final SecretKey val$cek;
            final AesWrapKeyAlgorithm this$0;
            {
                this.this$0 = aesWrapKeyAlgorithm;
                this.val$kek = secretKey;
                this.val$cek = secretKey2;
            }

            @Override
            public byte[] apply(Cipher cipher) throws Exception {
                cipher.init(3, this.val$kek);
                return cipher.wrap(this.val$cek);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Cipher)object);
            }
        });
        return new DefaultKeyResult(secretKey2, byArray);
    }

    @Override
    public SecretKey getDecryptionKey(DecryptionKeyRequest<SecretKey> decryptionKeyRequest) throws SecurityException {
        Assert.notNull(decryptionKeyRequest, "request cannot be null.");
        SecretKey secretKey = this.assertKey((SecretKey)decryptionKeyRequest.getKey());
        byte[] byArray = Assert.notEmpty((byte[])decryptionKeyRequest.getPayload(), "Request content (encrypted key) cannot be null or empty.");
        return this.jca(decryptionKeyRequest).withCipher(new CheckedFunction<Cipher, SecretKey>(this, secretKey, byArray){
            final SecretKey val$kek;
            final byte[] val$cekBytes;
            final AesWrapKeyAlgorithm this$0;
            {
                this.this$0 = aesWrapKeyAlgorithm;
                this.val$kek = secretKey;
                this.val$cekBytes = byArray;
            }

            @Override
            public SecretKey apply(Cipher cipher) throws Exception {
                cipher.init(4, this.val$kek);
                Key key = cipher.unwrap(this.val$cekBytes, "AES", 3);
                Assert.state(key instanceof SecretKey, "Cipher unwrap must return a SecretKey instance.");
                return (SecretKey)key;
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Cipher)object);
            }
        });
    }

    @Override
    public SecretKeyBuilder key() {
        return super.key();
    }

    @Override
    public int getKeyBitLength() {
        return super.getKeyBitLength();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public String getId() {
        return super.getId();
    }
}

