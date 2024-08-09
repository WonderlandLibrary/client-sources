/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.security.CryptoAlgorithm;
import io.jsonwebtoken.impl.security.DefaultKeyResult;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.impl.security.RsaSignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.DecryptionKeyRequest;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.KeyResult;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.WeakKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class DefaultRsaKeyAlgorithm
extends CryptoAlgorithm
implements KeyAlgorithm<PublicKey, PrivateKey> {
    private final AlgorithmParameterSpec SPEC;
    private static final int MIN_KEY_BIT_LENGTH = 2048;

    public DefaultRsaKeyAlgorithm(String string, String string2) {
        this(string, string2, null);
    }

    public DefaultRsaKeyAlgorithm(String string, String string2, AlgorithmParameterSpec algorithmParameterSpec) {
        super(string, string2);
        this.SPEC = algorithmParameterSpec;
    }

    private static String keyType(boolean bl) {
        return bl ? "encryption" : "decryption";
    }

    protected void validate(Key key, boolean bl) {
        if (!RsaSignatureAlgorithm.isRsaAlgorithmName(key)) {
            throw new InvalidKeyException("Invalid RSA key algorithm name.");
        }
        if (RsaSignatureAlgorithm.isPss(key)) {
            String string = "RSASSA-PSS keys may not be used for " + DefaultRsaKeyAlgorithm.keyType(bl) + ", only digital signature algorithms.";
            throw new InvalidKeyException(string);
        }
        int n = KeysBridge.findBitLength(key);
        if (n < 0) {
            return;
        }
        if (n < 2048) {
            String string = this.getId();
            String string2 = string.startsWith("RSA1") ? "4.2" : "4.3";
            String string3 = "The RSA " + DefaultRsaKeyAlgorithm.keyType(bl) + " key size (aka modulus bit length) is " + n + " bits which is not secure enough for the " + string + " algorithm. " + "The JWT JWA Specification (RFC 7518, Section " + string2 + ") states that RSA keys MUST " + "have a size >= " + 2048 + " bits. See " + "https://www.rfc-editor.org/rfc/rfc7518.html#section-" + string2 + " for more information.";
            throw new WeakKeyException(string3);
        }
    }

    @Override
    public KeyResult getEncryptionKey(KeyRequest<PublicKey> keyRequest) throws SecurityException {
        Assert.notNull(keyRequest, "Request cannot be null.");
        PublicKey publicKey = (PublicKey)Assert.notNull(keyRequest.getPayload(), "RSA PublicKey encryption key cannot be null.");
        this.validate(publicKey, false);
        SecretKey secretKey = this.generateCek(keyRequest);
        byte[] byArray = this.jca(keyRequest).withCipher(new CheckedFunction<Cipher, byte[]>(this, publicKey, keyRequest, secretKey){
            final PublicKey val$kek;
            final KeyRequest val$request;
            final SecretKey val$cek;
            final DefaultRsaKeyAlgorithm this$0;
            {
                this.this$0 = defaultRsaKeyAlgorithm;
                this.val$kek = publicKey;
                this.val$request = keyRequest;
                this.val$cek = secretKey;
            }

            @Override
            public byte[] apply(Cipher cipher) throws Exception {
                if (DefaultRsaKeyAlgorithm.access$000(this.this$0) == null) {
                    cipher.init(3, (Key)this.val$kek, CryptoAlgorithm.ensureSecureRandom(this.val$request));
                } else {
                    cipher.init(3, (Key)this.val$kek, DefaultRsaKeyAlgorithm.access$000(this.this$0), CryptoAlgorithm.ensureSecureRandom(this.val$request));
                }
                return cipher.wrap(this.val$cek);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Cipher)object);
            }
        });
        return new DefaultKeyResult(secretKey, byArray);
    }

    @Override
    public SecretKey getDecryptionKey(DecryptionKeyRequest<PrivateKey> decryptionKeyRequest) throws SecurityException {
        Assert.notNull(decryptionKeyRequest, "request cannot be null.");
        PrivateKey privateKey = (PrivateKey)Assert.notNull(decryptionKeyRequest.getKey(), "RSA PrivateKey decryption key cannot be null.");
        this.validate(privateKey, true);
        byte[] byArray = Assert.notEmpty((byte[])decryptionKeyRequest.getPayload(), "Request content (encrypted key) cannot be null or empty.");
        return this.jca(decryptionKeyRequest).withCipher(new CheckedFunction<Cipher, SecretKey>(this, privateKey, byArray){
            final PrivateKey val$kek;
            final byte[] val$cekBytes;
            final DefaultRsaKeyAlgorithm this$0;
            {
                this.this$0 = defaultRsaKeyAlgorithm;
                this.val$kek = privateKey;
                this.val$cekBytes = byArray;
            }

            @Override
            public SecretKey apply(Cipher cipher) throws Exception {
                if (DefaultRsaKeyAlgorithm.access$000(this.this$0) == null) {
                    cipher.init(4, this.val$kek);
                } else {
                    cipher.init(4, (Key)this.val$kek, DefaultRsaKeyAlgorithm.access$000(this.this$0));
                }
                Key key = cipher.unwrap(this.val$cekBytes, "AES", 3);
                return Assert.isInstanceOf(SecretKey.class, key, "Cipher unwrap must return a SecretKey instance.");
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Cipher)object);
            }
        });
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

    static AlgorithmParameterSpec access$000(DefaultRsaKeyAlgorithm defaultRsaKeyAlgorithm) {
        return defaultRsaKeyAlgorithm.SPEC;
    }
}

