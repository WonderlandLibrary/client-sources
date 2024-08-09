/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.impl.io.TeeOutputStream;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.security.AesAlgorithm;
import io.jsonwebtoken.impl.security.DefaultMacAlgorithm;
import io.jsonwebtoken.impl.security.DefaultSecureRequest;
import io.jsonwebtoken.impl.security.RandomSecretKeyBuilder;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.AeadRequest;
import io.jsonwebtoken.security.AeadResult;
import io.jsonwebtoken.security.DecryptAeadRequest;
import io.jsonwebtoken.security.KeyBuilder;
import io.jsonwebtoken.security.SecretKeyBuilder;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.SignatureException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class HmacAesAeadAlgorithm
extends AesAlgorithm
implements AeadAlgorithm {
    private static final String TRANSFORMATION_STRING = "AES/CBC/PKCS5Padding";
    private final DefaultMacAlgorithm SIGALG;

    private static int digestLength(int n) {
        return n * 2;
    }

    private static String id(int n) {
        return "A" + n + "CBC-HS" + HmacAesAeadAlgorithm.digestLength(n);
    }

    public HmacAesAeadAlgorithm(String string, DefaultMacAlgorithm defaultMacAlgorithm) {
        super(string, TRANSFORMATION_STRING, defaultMacAlgorithm.getKeyBitLength());
        this.SIGALG = defaultMacAlgorithm;
    }

    public HmacAesAeadAlgorithm(int n) {
        this(HmacAesAeadAlgorithm.id(n), new DefaultMacAlgorithm(HmacAesAeadAlgorithm.id(n), "HmacSHA" + HmacAesAeadAlgorithm.digestLength(n), n));
    }

    @Override
    public int getKeyBitLength() {
        return super.getKeyBitLength() * 2;
    }

    @Override
    public SecretKeyBuilder key() {
        return new RandomSecretKeyBuilder("AES", this.getKeyBitLength());
    }

    byte[] assertKeyBytes(SecureRequest<?, SecretKey> secureRequest) {
        SecretKey secretKey = (SecretKey)Assert.notNull(secureRequest.getKey(), "Request key cannot be null.");
        return this.validateLength(secretKey, this.keyBitLength * 2, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void encrypt(AeadRequest aeadRequest, AeadResult aeadResult) {
        SecretKeySpec secretKeySpec;
        Assert.notNull(aeadRequest, "Request cannot be null.");
        Assert.notNull(aeadResult, "Result cannot be null.");
        byte[] byArray = this.assertKeyBytes(aeadRequest);
        int n = byArray.length / 2;
        byte[] byArray2 = Arrays.copyOfRange(byArray, 0, n);
        byte[] byArray3 = Arrays.copyOfRange(byArray, n, byArray.length);
        try {
            secretKeySpec = new SecretKeySpec(byArray3, "AES");
        } finally {
            Bytes.clear(byArray3);
            Bytes.clear(byArray);
        }
        InputStream inputStream = (InputStream)Assert.notNull(aeadRequest.getPayload(), "Request content (plaintext) InputStream cannot be null.");
        OutputStream outputStream = Assert.notNull(aeadResult.getOutputStream(), "Result ciphertext OutputStream cannot be null.");
        InputStream inputStream2 = aeadRequest.getAssociatedData();
        byte[] byArray4 = this.ensureInitializationVector(aeadRequest);
        AlgorithmParameterSpec algorithmParameterSpec = this.getIvSpec(byArray4);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
        TeeOutputStream teeOutputStream = new TeeOutputStream(outputStream, byteArrayOutputStream);
        this.jca(aeadRequest).withCipher(new CheckedFunction<Cipher, Object>(this, (SecretKey)secretKeySpec, algorithmParameterSpec, inputStream, (OutputStream)teeOutputStream){
            final SecretKey val$encryptionKey;
            final AlgorithmParameterSpec val$ivSpec;
            final InputStream val$plaintext;
            final OutputStream val$tee;
            final HmacAesAeadAlgorithm this$0;
            {
                this.this$0 = hmacAesAeadAlgorithm;
                this.val$encryptionKey = secretKey;
                this.val$ivSpec = algorithmParameterSpec;
                this.val$plaintext = inputStream;
                this.val$tee = outputStream;
            }

            @Override
            public Object apply(Cipher cipher) throws Exception {
                cipher.init(1, (Key)this.val$encryptionKey, this.val$ivSpec);
                this.this$0.withCipher(cipher, this.val$plaintext, this.val$tee);
                return null;
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Cipher)object);
            }
        });
        byte[] byArray5 = inputStream2 == null ? Bytes.EMPTY : Streams.bytes(inputStream2, "Unable to read AAD bytes.");
        try {
            byte[] byArray6 = this.sign(byArray5, byArray4, Streams.of(byteArrayOutputStream.toByteArray()), byArray2);
            aeadResult.setTag(byArray6).setIv(byArray4);
        } finally {
            Bytes.clear(byArray2);
        }
    }

    private byte[] sign(byte[] byArray, byte[] byArray2, InputStream inputStream, byte[] byArray3) {
        long l = io.jsonwebtoken.lang.Arrays.length(byArray);
        long l2 = l * 8L;
        long l3 = l2 & 0xFFFFFFFFL;
        byte[] byArray4 = Bytes.toBytes(l3);
        ArrayList<InputStream> arrayList = new ArrayList<InputStream>(4);
        if (!Bytes.isEmpty(byArray)) {
            arrayList.add(Streams.of(byArray));
        }
        arrayList.add(Streams.of(byArray2));
        arrayList.add(inputStream);
        arrayList.add(Streams.of(byArray4));
        SequenceInputStream sequenceInputStream = new SequenceInputStream(Collections.enumeration(arrayList));
        SecretKeySpec secretKeySpec = new SecretKeySpec(byArray3, this.SIGALG.getJcaName());
        DefaultSecureRequest<SequenceInputStream, SecretKeySpec> defaultSecureRequest = new DefaultSecureRequest<SequenceInputStream, SecretKeySpec>(sequenceInputStream, null, null, secretKeySpec);
        byte[] byArray5 = this.SIGALG.digest(defaultSecureRequest);
        return this.assertTag(Arrays.copyOfRange(byArray5, 0, byArray3.length));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void decrypt(DecryptAeadRequest decryptAeadRequest, OutputStream outputStream) {
        byte[] byArray;
        SecretKeySpec secretKeySpec;
        Assert.notNull(decryptAeadRequest, "Request cannot be null.");
        Assert.notNull(outputStream, "Plaintext OutputStream cannot be null.");
        byte[] byArray2 = this.assertKeyBytes(decryptAeadRequest);
        int n = byArray2.length / 2;
        byte[] byArray3 = Arrays.copyOfRange(byArray2, 0, n);
        byte[] byArray4 = Arrays.copyOfRange(byArray2, n, byArray2.length);
        try {
            secretKeySpec = new SecretKeySpec(byArray4, "AES");
        } finally {
            Bytes.clear(byArray4);
            Bytes.clear(byArray2);
        }
        InputStream inputStream = (InputStream)Assert.notNull(decryptAeadRequest.getPayload(), "Decryption request content (ciphertext) InputStream cannot be null.");
        InputStream inputStream2 = decryptAeadRequest.getAssociatedData();
        byte[] byArray5 = this.assertTag(decryptAeadRequest.getDigest());
        byte[] byArray6 = this.assertDecryptionIv(decryptAeadRequest);
        AlgorithmParameterSpec algorithmParameterSpec = this.getIvSpec(byArray6);
        byte[] byArray7 = inputStream2 == null ? Bytes.EMPTY : Streams.bytes(inputStream2, "Unable to read AAD bytes.");
        try {
            byArray = this.sign(byArray7, byArray6, inputStream, byArray3);
        } finally {
            Bytes.clear(byArray3);
        }
        if (!MessageDigest.isEqual(byArray, byArray5)) {
            String string = "Ciphertext decryption failed: Authentication tag verification failed.";
            throw new SignatureException(string);
        }
        Streams.reset(inputStream);
        InputStream inputStream3 = inputStream;
        this.jca(decryptAeadRequest).withCipher(new CheckedFunction<Cipher, byte[]>(this, (SecretKey)secretKeySpec, algorithmParameterSpec, inputStream3, outputStream){
            final SecretKey val$decryptionKey;
            final AlgorithmParameterSpec val$ivSpec;
            final InputStream val$ciphertext;
            final OutputStream val$plaintext;
            final HmacAesAeadAlgorithm this$0;
            {
                this.this$0 = hmacAesAeadAlgorithm;
                this.val$decryptionKey = secretKey;
                this.val$ivSpec = algorithmParameterSpec;
                this.val$ciphertext = inputStream;
                this.val$plaintext = outputStream;
            }

            @Override
            public byte[] apply(Cipher cipher) throws Exception {
                cipher.init(2, (Key)this.val$decryptionKey, this.val$ivSpec);
                this.this$0.withCipher(cipher, this.val$ciphertext, this.val$plaintext);
                return Bytes.EMPTY;
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Cipher)object);
            }
        });
    }

    @Override
    public KeyBuilder key() {
        return this.key();
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

