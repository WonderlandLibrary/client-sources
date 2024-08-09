/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.security.AbstractSecureDigestAlgorithm;
import io.jsonwebtoken.impl.security.DefaultSecretKeyBuilder;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.KeyBuilder;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.Password;
import io.jsonwebtoken.security.SecretKeyBuilder;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.VerifySecureDigestRequest;
import io.jsonwebtoken.security.WeakKeyException;
import java.io.InputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class DefaultMacAlgorithm
extends AbstractSecureDigestAlgorithm<SecretKey, SecretKey>
implements MacAlgorithm {
    private static final String HS256_OID = "1.2.840.113549.2.9";
    private static final String HS384_OID = "1.2.840.113549.2.10";
    private static final String HS512_OID = "1.2.840.113549.2.11";
    private static final Set<String> JWA_STANDARD_IDS = new LinkedHashSet<String>(Collections.of("HS256", "HS384", "HS512"));
    static final DefaultMacAlgorithm HS256 = new DefaultMacAlgorithm(256);
    static final DefaultMacAlgorithm HS384 = new DefaultMacAlgorithm(384);
    static final DefaultMacAlgorithm HS512 = new DefaultMacAlgorithm(512);
    private static final Map<String, DefaultMacAlgorithm> JCA_NAME_MAP = new LinkedHashMap<String, DefaultMacAlgorithm>(6);
    private final int minKeyBitLength;

    private DefaultMacAlgorithm(int n) {
        this("HS" + n, "HmacSHA" + n, n);
    }

    DefaultMacAlgorithm(String string, String string2, int n) {
        super(string, string2);
        Assert.isTrue(n > 0, "minKeyLength must be greater than zero.");
        this.minKeyBitLength = n;
    }

    @Override
    public int getKeyBitLength() {
        return this.minKeyBitLength;
    }

    private boolean isJwaStandard() {
        return JWA_STANDARD_IDS.contains(this.getId());
    }

    private static boolean isJwaStandardJcaName(String string) {
        String string2 = string.toUpperCase(Locale.ENGLISH);
        return JCA_NAME_MAP.containsKey(string2);
    }

    static DefaultMacAlgorithm findByKey(Key key) {
        String string = KeysBridge.findAlgorithm(key);
        if (!Strings.hasText(string)) {
            return null;
        }
        String string2 = string.toUpperCase(Locale.ENGLISH);
        DefaultMacAlgorithm defaultMacAlgorithm = JCA_NAME_MAP.get(string2);
        if (defaultMacAlgorithm == null) {
            return null;
        }
        byte[] byArray = KeysBridge.findEncoded(key);
        long l = Bytes.bitLength(byArray);
        if (l >= (long)defaultMacAlgorithm.getKeyBitLength()) {
            return defaultMacAlgorithm;
        }
        return null;
    }

    @Override
    public SecretKeyBuilder key() {
        return new DefaultSecretKeyBuilder(this.getJcaName(), this.getKeyBitLength());
    }

    private void assertAlgorithmName(SecretKey secretKey, boolean bl) {
        String string = secretKey.getAlgorithm();
        if (!Strings.hasText(string)) {
            String string2 = "The " + DefaultMacAlgorithm.keyType(bl) + " key's algorithm cannot be null or empty.";
            throw new InvalidKeyException(string2);
        }
        boolean bl2 = KeysBridge.isSunPkcs11GenericSecret(secretKey);
        if (!bl2 && this.isJwaStandard() && !DefaultMacAlgorithm.isJwaStandardJcaName(string)) {
            throw new InvalidKeyException("The " + DefaultMacAlgorithm.keyType(bl) + " key's algorithm '" + string + "' does not equal a valid HmacSHA* algorithm name or PKCS12 OID and cannot be used with " + this.getId() + ".");
        }
    }

    @Override
    protected void validateKey(Key key, boolean bl) {
        String string = DefaultMacAlgorithm.keyType(bl);
        if (key == null) {
            throw new IllegalArgumentException("MAC " + string + " key cannot be null.");
        }
        if (!(key instanceof SecretKey)) {
            String string2 = "MAC " + string + " keys must be SecretKey instances.  Specified key is of type " + key.getClass().getName();
            throw new InvalidKeyException(string2);
        }
        if (key instanceof Password) {
            String string3 = "Passwords are intended for use with key derivation algorithms only.";
            throw new InvalidKeyException(string3);
        }
        SecretKey secretKey = (SecretKey)key;
        String string4 = this.getId();
        this.assertAlgorithmName(secretKey, bl);
        int n = KeysBridge.findBitLength(secretKey);
        if (n < 0) {
            return;
        }
        if (n < this.minKeyBitLength) {
            String string5 = "The " + string + " key's size is " + n + " bits which " + "is not secure enough for the " + string4 + " algorithm.";
            string5 = this.isJwaStandard() && DefaultMacAlgorithm.isJwaStandardJcaName(this.getJcaName()) ? string5 + " The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with " + string4 + " MUST have a " + "size >= " + this.minKeyBitLength + " bits (the key size must be greater than or equal to the hash " + "output size). Consider using the Jwts.SIG." + string4 + ".key() " + "builder to create a key guaranteed to be secure enough for " + string4 + ".  See " + "https://tools.ietf.org/html/rfc7518#section-3.2 for more information." : string5 + " The " + string4 + " algorithm requires keys to have a size >= " + this.minKeyBitLength + " bits.";
            throw new WeakKeyException(string5);
        }
    }

    @Override
    public byte[] doDigest(SecureRequest<InputStream, SecretKey> secureRequest) {
        return this.jca(secureRequest).withMac(new CheckedFunction<Mac, byte[]>(this, secureRequest){
            final SecureRequest val$request;
            final DefaultMacAlgorithm this$0;
            {
                this.this$0 = defaultMacAlgorithm;
                this.val$request = secureRequest;
            }

            @Override
            public byte[] apply(Mac mac) throws Exception {
                mac.init((Key)this.val$request.getKey());
                InputStream inputStream = (InputStream)this.val$request.getPayload();
                byte[] byArray = new byte[1024];
                int n = 0;
                while (n != -1) {
                    n = inputStream.read(byArray);
                    if (n <= 0) continue;
                    mac.update(byArray, 0, n);
                }
                return mac.doFinal();
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Mac)object);
            }
        });
    }

    @Override
    protected boolean doVerify(VerifySecureDigestRequest<SecretKey> verifySecureDigestRequest) {
        byte[] byArray = verifySecureDigestRequest.getDigest();
        Assert.notEmpty(byArray, "Request signature byte array cannot be null or empty.");
        byte[] byArray2 = this.digest(verifySecureDigestRequest);
        return MessageDigest.isEqual(byArray, byArray2);
    }

    @Override
    public KeyBuilder key() {
        return this.key();
    }

    static {
        JCA_NAME_MAP.put(HS256.getJcaName().toUpperCase(Locale.ENGLISH), HS256);
        JCA_NAME_MAP.put(HS256_OID, HS256);
        JCA_NAME_MAP.put(HS384.getJcaName().toUpperCase(Locale.ENGLISH), HS384);
        JCA_NAME_MAP.put(HS384_OID, HS384);
        JCA_NAME_MAP.put(HS512.getJcaName().toUpperCase(Locale.ENGLISH), HS512);
        JCA_NAME_MAP.put(HS512_OID, HS512);
    }
}

