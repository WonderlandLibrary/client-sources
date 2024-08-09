/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.impl.DefaultJweHeader;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.ParameterReadable;
import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.AesAlgorithm;
import io.jsonwebtoken.impl.security.DefaultKeyResult;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.DecryptionKeyRequest;
import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.KeyResult;
import io.jsonwebtoken.security.SecretKeyAlgorithm;
import io.jsonwebtoken.security.SecretKeyBuilder;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class AesGcmKeyAlgorithm
extends AesAlgorithm
implements SecretKeyAlgorithm {
    public static final String TRANSFORMATION = "AES/GCM/NoPadding";

    public AesGcmKeyAlgorithm(int n) {
        super("A" + n + "GCMKW", TRANSFORMATION, n);
    }

    @Override
    public KeyResult getEncryptionKey(KeyRequest<SecretKey> keyRequest) throws SecurityException {
        Assert.notNull(keyRequest, "request cannot be null.");
        JweHeader jweHeader = Assert.notNull(keyRequest.getHeader(), "Request JweHeader cannot be null.");
        SecretKey secretKey = this.assertKey((SecretKey)keyRequest.getPayload());
        SecretKey secretKey2 = this.generateCek(keyRequest);
        byte[] byArray = this.ensureInitializationVector(keyRequest);
        AlgorithmParameterSpec algorithmParameterSpec = this.getIvSpec(byArray);
        byte[] byArray2 = this.jca(keyRequest).withCipher(new CheckedFunction<Cipher, byte[]>(this, secretKey, algorithmParameterSpec, secretKey2){
            final SecretKey val$kek;
            final AlgorithmParameterSpec val$ivSpec;
            final SecretKey val$cek;
            final AesGcmKeyAlgorithm this$0;
            {
                this.this$0 = aesGcmKeyAlgorithm;
                this.val$kek = secretKey;
                this.val$ivSpec = algorithmParameterSpec;
                this.val$cek = secretKey2;
            }

            @Override
            public byte[] apply(Cipher cipher) throws Exception {
                cipher.init(3, (Key)this.val$kek, this.val$ivSpec);
                return cipher.wrap(this.val$cek);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Cipher)object);
            }
        });
        int n = this.tagBitLength / 8;
        int n2 = byArray2.length - n;
        byte[] byArray3 = new byte[n2];
        System.arraycopy(byArray2, 0, byArray3, 0, n2);
        byte[] byArray4 = new byte[n];
        System.arraycopy(byArray2, n2, byArray4, 0, n);
        String string = Encoders.BASE64URL.encode(byArray);
        String string2 = Encoders.BASE64URL.encode(byArray4);
        jweHeader.put(DefaultJweHeader.IV.getId(), string);
        jweHeader.put(DefaultJweHeader.TAG.getId(), string2);
        return new DefaultKeyResult(secretKey2, byArray3);
    }

    @Override
    public SecretKey getDecryptionKey(DecryptionKeyRequest<SecretKey> decryptionKeyRequest) throws SecurityException {
        Assert.notNull(decryptionKeyRequest, "request cannot be null.");
        SecretKey secretKey = this.assertKey((SecretKey)decryptionKeyRequest.getKey());
        byte[] byArray = Assert.notEmpty((byte[])decryptionKeyRequest.getPayload(), "Decryption request content (ciphertext) cannot be null or empty.");
        JweHeader jweHeader = Assert.notNull(decryptionKeyRequest.getHeader(), "Request JweHeader cannot be null.");
        ParameterReadable parameterReadable = Assert.isInstanceOf(ParameterReadable.class, jweHeader, "Header must implement ParameterReadable.");
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(parameterReadable);
        byte[] byArray2 = requiredParameterReader.get(DefaultJweHeader.TAG);
        byte[] byArray3 = requiredParameterReader.get(DefaultJweHeader.IV);
        AlgorithmParameterSpec algorithmParameterSpec = this.getIvSpec(byArray3);
        byte[] byArray4 = Bytes.concat(byArray, byArray2);
        return this.jca(decryptionKeyRequest).withCipher(new CheckedFunction<Cipher, SecretKey>(this, secretKey, algorithmParameterSpec, byArray4){
            final SecretKey val$kek;
            final AlgorithmParameterSpec val$ivSpec;
            final byte[] val$taggedCiphertext;
            final AesGcmKeyAlgorithm this$0;
            {
                this.this$0 = aesGcmKeyAlgorithm;
                this.val$kek = secretKey;
                this.val$ivSpec = algorithmParameterSpec;
                this.val$taggedCiphertext = byArray;
            }

            @Override
            public SecretKey apply(Cipher cipher) throws Exception {
                cipher.init(4, (Key)this.val$kek, this.val$ivSpec);
                Key key = cipher.unwrap(this.val$taggedCiphertext, "AES", 3);
                Assert.state(key instanceof SecretKey, "cipher.unwrap must produce a SecretKey instance.");
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

