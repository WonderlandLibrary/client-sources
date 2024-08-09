/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.security.AesAlgorithm;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.AeadRequest;
import io.jsonwebtoken.security.AeadResult;
import io.jsonwebtoken.security.DecryptAeadRequest;
import io.jsonwebtoken.security.SecretKeyBuilder;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class GcmAesAeadAlgorithm
extends AesAlgorithm
implements AeadAlgorithm {
    private static final String TRANSFORMATION_STRING = "AES/GCM/NoPadding";

    public GcmAesAeadAlgorithm(int n) {
        super("A" + n + "GCM", TRANSFORMATION_STRING, n);
    }

    @Override
    public void encrypt(AeadRequest aeadRequest, AeadResult aeadResult) throws SecurityException {
        Assert.notNull(aeadRequest, "Request cannot be null.");
        Assert.notNull(aeadResult, "Result cannot be null.");
        SecretKey secretKey = this.assertKey((SecretKey)aeadRequest.getKey());
        InputStream inputStream = (InputStream)Assert.notNull(aeadRequest.getPayload(), "Request content (plaintext) InputStream cannot be null.");
        OutputStream outputStream = Assert.notNull(aeadResult.getOutputStream(), "Result ciphertext OutputStream cannot be null.");
        InputStream inputStream2 = aeadRequest.getAssociatedData();
        byte[] byArray = this.ensureInitializationVector(aeadRequest);
        AlgorithmParameterSpec algorithmParameterSpec = this.getIvSpec(byArray);
        byte[] byArray2 = this.jca(aeadRequest).withCipher(new CheckedFunction<Cipher, byte[]>(this, secretKey, algorithmParameterSpec, inputStream, inputStream2, outputStream){
            final SecretKey val$key;
            final AlgorithmParameterSpec val$ivSpec;
            final InputStream val$plaintext;
            final InputStream val$aad;
            final OutputStream val$out;
            final GcmAesAeadAlgorithm this$0;
            {
                this.this$0 = gcmAesAeadAlgorithm;
                this.val$key = secretKey;
                this.val$ivSpec = algorithmParameterSpec;
                this.val$plaintext = inputStream;
                this.val$aad = inputStream2;
                this.val$out = outputStream;
            }

            @Override
            public byte[] apply(Cipher cipher) throws Exception {
                cipher.init(1, (Key)this.val$key, this.val$ivSpec);
                byte[] byArray = this.this$0.withCipher(cipher, this.val$plaintext, this.val$aad, this.val$out);
                int n = Bytes.length(byArray) - 16;
                Streams.write(this.val$out, byArray, 0, n, "Ciphertext write failure.");
                byte[] byArray2 = new byte[16];
                System.arraycopy(byArray, n, byArray2, 0, 16);
                return byArray2;
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Cipher)object);
            }
        });
        Streams.flush(outputStream);
        Streams.reset(inputStream);
        aeadResult.setTag(byArray2).setIv(byArray);
    }

    @Override
    public void decrypt(DecryptAeadRequest decryptAeadRequest, OutputStream outputStream) throws SecurityException {
        Assert.notNull(decryptAeadRequest, "Request cannot be null.");
        Assert.notNull(outputStream, "Plaintext OutputStream cannot be null.");
        SecretKey secretKey = this.assertKey((SecretKey)decryptAeadRequest.getKey());
        InputStream inputStream = (InputStream)Assert.notNull(decryptAeadRequest.getPayload(), "Decryption request content (ciphertext) InputStream cannot be null.");
        InputStream inputStream2 = decryptAeadRequest.getAssociatedData();
        byte[] byArray = Assert.notEmpty(decryptAeadRequest.getDigest(), "Decryption request authentication tag cannot be null or empty.");
        byte[] byArray2 = this.assertDecryptionIv(decryptAeadRequest);
        AlgorithmParameterSpec algorithmParameterSpec = this.getIvSpec(byArray2);
        SequenceInputStream sequenceInputStream = new SequenceInputStream(inputStream, Streams.of(byArray));
        this.jca(decryptAeadRequest).withCipher(new CheckedFunction<Cipher, byte[]>(this, secretKey, algorithmParameterSpec, (InputStream)sequenceInputStream, inputStream2, outputStream){
            final SecretKey val$key;
            final AlgorithmParameterSpec val$ivSpec;
            final InputStream val$taggedCiphertext;
            final InputStream val$aad;
            final OutputStream val$out;
            final GcmAesAeadAlgorithm this$0;
            {
                this.this$0 = gcmAesAeadAlgorithm;
                this.val$key = secretKey;
                this.val$ivSpec = algorithmParameterSpec;
                this.val$taggedCiphertext = inputStream;
                this.val$aad = inputStream2;
                this.val$out = outputStream;
            }

            @Override
            public byte[] apply(Cipher cipher) throws Exception {
                cipher.init(2, (Key)this.val$key, this.val$ivSpec);
                byte[] byArray = this.this$0.withCipher(cipher, this.val$taggedCiphertext, this.val$aad, this.val$out);
                Streams.write(this.val$out, byArray, "GcmAesAeadAlgorithm#decrypt plaintext write failure.");
                return Bytes.EMPTY;
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Cipher)object);
            }
        });
        Streams.flush(outputStream);
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

