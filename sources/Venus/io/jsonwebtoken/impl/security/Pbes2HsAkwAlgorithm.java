/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.impl.DefaultJweHeader;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.AesWrapKeyAlgorithm;
import io.jsonwebtoken.impl.security.CryptoAlgorithm;
import io.jsonwebtoken.impl.security.DefaultDecryptionKeyRequest;
import io.jsonwebtoken.impl.security.DefaultKeyRequest;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.DecryptionKeyRequest;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.KeyResult;
import io.jsonwebtoken.security.Password;
import io.jsonwebtoken.security.SecurityException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Pbes2HsAkwAlgorithm
extends CryptoAlgorithm
implements KeyAlgorithm<Password, Password> {
    private static final int DEFAULT_SHA256_ITERATIONS = 310000;
    private static final int DEFAULT_SHA384_ITERATIONS = 210000;
    private static final int DEFAULT_SHA512_ITERATIONS = 120000;
    private static final int MIN_RECOMMENDED_ITERATIONS = 1000;
    private static final String MIN_ITERATIONS_MSG_PREFIX = "[JWA RFC 7518, Section 4.8.1.2](https://www.rfc-editor.org/rfc/rfc7518.html#section-4.8.1.2) recommends password-based-encryption iterations be greater than or equal to 1000. Provided: ";
    private final int HASH_BYTE_LENGTH;
    private final int DERIVED_KEY_BIT_LENGTH;
    private final byte[] SALT_PREFIX;
    private final int DEFAULT_ITERATIONS;
    private final KeyAlgorithm<SecretKey, SecretKey> wrapAlg;

    private static byte[] toRfcSaltPrefix(byte[] byArray) {
        byte[] byArray2 = new byte[byArray.length + 1];
        System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        return byArray2;
    }

    private static int hashBitLength(int n) {
        return n * 2;
    }

    private static String idFor(int n, KeyAlgorithm<SecretKey, SecretKey> keyAlgorithm) {
        Assert.notNull(keyAlgorithm, "wrapAlg argument cannot be null.");
        return "PBES2-HS" + n + "+" + keyAlgorithm.getId();
    }

    public static int assertIterations(int n) {
        if (n < 1000) {
            String string = MIN_ITERATIONS_MSG_PREFIX + n;
            throw new IllegalArgumentException(string);
        }
        return n;
    }

    public Pbes2HsAkwAlgorithm(int n) {
        this(Pbes2HsAkwAlgorithm.hashBitLength(n), new AesWrapKeyAlgorithm(n));
    }

    protected Pbes2HsAkwAlgorithm(int n, KeyAlgorithm<SecretKey, SecretKey> keyAlgorithm) {
        super(Pbes2HsAkwAlgorithm.idFor(n, keyAlgorithm), "PBKDF2WithHmacSHA" + n);
        this.wrapAlg = keyAlgorithm;
        this.HASH_BYTE_LENGTH = n / 8;
        this.DEFAULT_ITERATIONS = n >= 512 ? 120000 : (n >= 384 ? 210000 : 310000);
        this.DERIVED_KEY_BIT_LENGTH = n / 2;
        this.SALT_PREFIX = Pbes2HsAkwAlgorithm.toRfcSaltPrefix(this.getId().getBytes(StandardCharsets.UTF_8));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected SecretKey deriveKey(SecretKeyFactory secretKeyFactory, char[] cArray, byte[] byArray, int n) throws Exception {
        PBEKeySpec pBEKeySpec = new PBEKeySpec(cArray, byArray, n, this.DERIVED_KEY_BIT_LENGTH);
        try {
            SecretKey secretKey = secretKeyFactory.generateSecret(pBEKeySpec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            return secretKeySpec;
        } finally {
            pBEKeySpec.clearPassword();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private SecretKey deriveKey(KeyRequest<?> keyRequest, char[] cArray, byte[] byArray, int n) {
        try {
            Assert.notEmpty(cArray, "Key password character array cannot be null or empty.");
            SecretKey secretKey = this.jca(keyRequest).withSecretKeyFactory(new CheckedFunction<SecretKeyFactory, SecretKey>(this, cArray, byArray, n){
                final char[] val$password;
                final byte[] val$salt;
                final int val$iterations;
                final Pbes2HsAkwAlgorithm this$0;
                {
                    this.this$0 = pbes2HsAkwAlgorithm;
                    this.val$password = cArray;
                    this.val$salt = byArray;
                    this.val$iterations = n;
                }

                @Override
                public SecretKey apply(SecretKeyFactory secretKeyFactory) throws Exception {
                    return this.this$0.deriveKey(secretKeyFactory, this.val$password, this.val$salt, this.val$iterations);
                }

                @Override
                public Object apply(Object object) throws Exception {
                    return this.apply((SecretKeyFactory)object);
                }
            });
            return secretKey;
        } finally {
            Arrays.fill(cArray, '\u0000');
        }
    }

    protected byte[] generateInputSalt(KeyRequest<?> keyRequest) {
        byte[] byArray = new byte[this.HASH_BYTE_LENGTH];
        Pbes2HsAkwAlgorithm.ensureSecureRandom(keyRequest).nextBytes(byArray);
        return byArray;
    }

    protected byte[] toRfcSalt(byte[] byArray) {
        return Bytes.concat(this.SALT_PREFIX, byArray);
    }

    @Override
    public KeyResult getEncryptionKey(KeyRequest<Password> keyRequest) throws SecurityException {
        Assert.notNull(keyRequest, "request cannot be null.");
        Password password = (Password)Assert.notNull(keyRequest.getPayload(), "Encryption Password cannot be null.");
        JweHeader jweHeader = Assert.notNull(keyRequest.getHeader(), "JweHeader cannot be null.");
        Integer n = jweHeader.getPbes2Count();
        if (n == null) {
            n = this.DEFAULT_ITERATIONS;
            jweHeader.put(DefaultJweHeader.P2C.getId(), n);
        }
        int n2 = Pbes2HsAkwAlgorithm.assertIterations(n);
        byte[] byArray = this.generateInputSalt(keyRequest);
        byte[] byArray2 = this.toRfcSalt(byArray);
        char[] cArray = password.toCharArray();
        SecretKey secretKey = this.deriveKey(keyRequest, cArray, byArray2, n2);
        DefaultKeyRequest<SecretKey> defaultKeyRequest = new DefaultKeyRequest<SecretKey>(secretKey, keyRequest.getProvider(), keyRequest.getSecureRandom(), keyRequest.getHeader(), keyRequest.getEncryptionAlgorithm());
        KeyResult keyResult = this.wrapAlg.getEncryptionKey(defaultKeyRequest);
        keyRequest.getHeader().put(DefaultJweHeader.P2S.getId(), byArray);
        return keyResult;
    }

    @Override
    public SecretKey getDecryptionKey(DecryptionKeyRequest<Password> decryptionKeyRequest) throws SecurityException {
        JweHeader jweHeader = Assert.notNull(decryptionKeyRequest.getHeader(), "Request JweHeader cannot be null.");
        Password password = (Password)Assert.notNull(decryptionKeyRequest.getKey(), "Decryption Password cannot be null.");
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(jweHeader);
        byte[] byArray = requiredParameterReader.get(DefaultJweHeader.P2S);
        int n = requiredParameterReader.get(DefaultJweHeader.P2C);
        byte[] byArray2 = Bytes.concat(this.SALT_PREFIX, byArray);
        char[] cArray = password.toCharArray();
        SecretKey secretKey = this.deriveKey(decryptionKeyRequest, cArray, byArray2, n);
        DefaultDecryptionKeyRequest<SecretKey> defaultDecryptionKeyRequest = new DefaultDecryptionKeyRequest<SecretKey>((byte[])decryptionKeyRequest.getPayload(), decryptionKeyRequest.getProvider(), decryptionKeyRequest.getSecureRandom(), jweHeader, decryptionKeyRequest.getEncryptionAlgorithm(), secretKey);
        return this.wrapAlg.getDecryptionKey(defaultDecryptionKeyRequest);
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

