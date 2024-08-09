/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAKey;
import java.util.Collections;
import java.util.List;
import javax.crypto.SecretKey;

@Deprecated
public enum SignatureAlgorithm {
    NONE("none", "No digital signature or MAC performed", "None", null, false, 0, 0),
    HS256("HS256", "HMAC using SHA-256", "HMAC", "HmacSHA256", true, 256, 256, "1.2.840.113549.2.9"),
    HS384("HS384", "HMAC using SHA-384", "HMAC", "HmacSHA384", true, 384, 384, "1.2.840.113549.2.10"),
    HS512("HS512", "HMAC using SHA-512", "HMAC", "HmacSHA512", true, 512, 512, "1.2.840.113549.2.11"),
    RS256("RS256", "RSASSA-PKCS-v1_5 using SHA-256", "RSA", "SHA256withRSA", true, 256, 2048),
    RS384("RS384", "RSASSA-PKCS-v1_5 using SHA-384", "RSA", "SHA384withRSA", true, 384, 2048),
    RS512("RS512", "RSASSA-PKCS-v1_5 using SHA-512", "RSA", "SHA512withRSA", true, 512, 2048),
    ES256("ES256", "ECDSA using P-256 and SHA-256", "ECDSA", "SHA256withECDSA", true, 256, 256),
    ES384("ES384", "ECDSA using P-384 and SHA-384", "ECDSA", "SHA384withECDSA", true, 384, 384),
    ES512("ES512", "ECDSA using P-521 and SHA-512", "ECDSA", "SHA512withECDSA", true, 512, 521),
    PS256("PS256", "RSASSA-PSS using SHA-256 and MGF1 with SHA-256", "RSA", "RSASSA-PSS", false, 256, 2048),
    PS384("PS384", "RSASSA-PSS using SHA-384 and MGF1 with SHA-384", "RSA", "RSASSA-PSS", false, 384, 2048),
    PS512("PS512", "RSASSA-PSS using SHA-512 and MGF1 with SHA-512", "RSA", "RSASSA-PSS", false, 512, 2048);

    private static final List<SignatureAlgorithm> PREFERRED_HMAC_ALGS;
    private static final List<SignatureAlgorithm> PREFERRED_EC_ALGS;
    private final String value;
    private final String description;
    private final String familyName;
    private final String jcaName;
    private final boolean jdkStandard;
    private final int digestLength;
    private final int minKeyLength;
    @Deprecated
    private final String pkcs12Name;

    private SignatureAlgorithm(String string2, String string3, String string4, String string5, boolean bl, int n2, int n3) {
        this(string2, string3, string4, string5, bl, n2, n3, string5);
    }

    private SignatureAlgorithm(String string2, String string3, String string4, String string5, boolean bl, int n2, int n3, String string6) {
        this.value = string2;
        this.description = string3;
        this.familyName = string4;
        this.jcaName = string5;
        this.jdkStandard = bl;
        this.digestLength = n2;
        this.minKeyLength = n3;
        this.pkcs12Name = string6;
    }

    public String getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public String getJcaName() {
        return this.jcaName;
    }

    public boolean isJdkStandard() {
        return this.jdkStandard;
    }

    public boolean isHmac() {
        return this.familyName.equals("HMAC");
    }

    public boolean isRsa() {
        return this.familyName.equals("RSA");
    }

    public boolean isEllipticCurve() {
        return this.familyName.equals("ECDSA");
    }

    public int getMinKeyLength() {
        return this.minKeyLength;
    }

    public void assertValidSigningKey(Key key) throws InvalidKeyException {
        this.assertValid(key, true);
    }

    public void assertValidVerificationKey(Key key) throws InvalidKeyException {
        this.assertValid(key, false);
    }

    private static String keyType(boolean bl) {
        return bl ? "signing" : "verification";
    }

    private void assertValid(Key key, boolean bl) throws InvalidKeyException {
        if (this == NONE) {
            String string = "The 'NONE' signature algorithm does not support cryptographic keys.";
            throw new InvalidKeyException(string);
        }
        if (this.isHmac()) {
            if (!(key instanceof SecretKey)) {
                String string = this.familyName + " " + SignatureAlgorithm.keyType(bl) + " keys must be SecretKey instances.";
                throw new InvalidKeyException(string);
            }
            SecretKey secretKey = (SecretKey)key;
            byte[] byArray = secretKey.getEncoded();
            if (byArray == null) {
                throw new InvalidKeyException("The " + SignatureAlgorithm.keyType(bl) + " key's encoded bytes cannot be null.");
            }
            String string = secretKey.getAlgorithm();
            if (string == null) {
                throw new InvalidKeyException("The " + SignatureAlgorithm.keyType(bl) + " key's algorithm cannot be null.");
            }
            if (!(SignatureAlgorithm.HS256.jcaName.equalsIgnoreCase(string) || SignatureAlgorithm.HS384.jcaName.equalsIgnoreCase(string) || SignatureAlgorithm.HS512.jcaName.equalsIgnoreCase(string) || SignatureAlgorithm.HS256.pkcs12Name.equals(string) || SignatureAlgorithm.HS384.pkcs12Name.equals(string) || SignatureAlgorithm.HS512.pkcs12Name.equals(string))) {
                throw new InvalidKeyException("The " + SignatureAlgorithm.keyType(bl) + " key's algorithm '" + string + "' does not equal a valid HmacSHA* algorithm name and cannot be used with " + this.name() + ".");
            }
            int n = byArray.length * 8;
            if (n < this.minKeyLength) {
                String string2 = "The " + SignatureAlgorithm.keyType(bl) + " key's size is " + n + " bits which " + "is not secure enough for the " + this.name() + " algorithm.  The JWT " + "JWA Specification (RFC 7518, Section 3.2) states that keys used with " + this.name() + " MUST have a " + "size >= " + this.minKeyLength + " bits (the key size must be greater than or equal to the hash " + "output size).  Consider using the " + Keys.class.getName() + " class's " + "'secretKeyFor(SignatureAlgorithm." + this.name() + ")' method to create a key guaranteed to be " + "secure enough for " + this.name() + ".  See " + "https://tools.ietf.org/html/rfc7518#section-3.2 for more information.";
                throw new WeakKeyException(string2);
            }
        } else {
            if (bl && !(key instanceof PrivateKey)) {
                String string = this.familyName + " signing keys must be PrivateKey instances.";
                throw new InvalidKeyException(string);
            }
            if (this.isEllipticCurve()) {
                if (!(key instanceof ECKey)) {
                    String string = this.familyName + " " + SignatureAlgorithm.keyType(bl) + " keys must be ECKey instances.";
                    throw new InvalidKeyException(string);
                }
                ECKey eCKey = (ECKey)((Object)key);
                int n = eCKey.getParams().getOrder().bitLength();
                if (n < this.minKeyLength) {
                    String string = "The " + SignatureAlgorithm.keyType(bl) + " key's size (ECParameterSpec order) is " + n + " bits which is not secure enough for the " + this.name() + " algorithm.  The JWT " + "JWA Specification (RFC 7518, Section 3.4) states that keys used with " + this.name() + " MUST have a size >= " + this.minKeyLength + " bits.  Consider using the " + Keys.class.getName() + " class's " + "'keyPairFor(SignatureAlgorithm." + this.name() + ")' method to create a key pair guaranteed " + "to be secure enough for " + this.name() + ".  See " + "https://tools.ietf.org/html/rfc7518#section-3.4 for more information.";
                    throw new WeakKeyException(string);
                }
            } else {
                if (!(key instanceof RSAKey)) {
                    String string = this.familyName + " " + SignatureAlgorithm.keyType(bl) + " keys must be RSAKey instances.";
                    throw new InvalidKeyException(string);
                }
                RSAKey rSAKey = (RSAKey)((Object)key);
                int n = rSAKey.getModulus().bitLength();
                if (n < this.minKeyLength) {
                    String string = this.name().startsWith("P") ? "3.5" : "3.3";
                    String string3 = "The " + SignatureAlgorithm.keyType(bl) + " key's size is " + n + " bits which is not secure " + "enough for the " + this.name() + " algorithm.  The JWT JWA Specification (RFC 7518, Section " + string + ") states that keys used with " + this.name() + " MUST have a size >= " + this.minKeyLength + " bits.  Consider using the " + Keys.class.getName() + " class's " + "'keyPairFor(SignatureAlgorithm." + this.name() + ")' method to create a key pair guaranteed " + "to be secure enough for " + this.name() + ".  See " + "https://tools.ietf.org/html/rfc7518#section-" + string + " for more information.";
                    throw new WeakKeyException(string3);
                }
            }
        }
    }

    public static SignatureAlgorithm forSigningKey(Key key) throws InvalidKeyException {
        if (key == null) {
            throw new InvalidKeyException("Key argument cannot be null.");
        }
        if (!(key instanceof SecretKey || key instanceof PrivateKey && (key instanceof ECKey || key instanceof RSAKey))) {
            String string = "JWT standard signing algorithms require either 1) a SecretKey for HMAC-SHA algorithms or 2) a private RSAKey for RSA algorithms or 3) a private ECKey for Elliptic Curve algorithms.  The specified key is of type " + key.getClass().getName();
            throw new InvalidKeyException(string);
        }
        if (key instanceof SecretKey) {
            SecretKey secretKey = (SecretKey)key;
            int n = Arrays.length(secretKey.getEncoded()) * 8;
            for (SignatureAlgorithm signatureAlgorithm : PREFERRED_HMAC_ALGS) {
                if (n < signatureAlgorithm.minKeyLength) continue;
                return signatureAlgorithm;
            }
            String string = "The specified SecretKey is not strong enough to be used with JWT HMAC signature algorithms.  The JWT specification requires HMAC keys to be >= 256 bits long.  The specified key is " + n + " bits.  See https://tools.ietf.org/html/rfc7518#section-3.2 for more " + "information.";
            throw new WeakKeyException(string);
        }
        if (key instanceof RSAKey) {
            RSAKey rSAKey = (RSAKey)((Object)key);
            int n = rSAKey.getModulus().bitLength();
            if (n >= 4096) {
                RS512.assertValidSigningKey(key);
                return RS512;
            }
            if (n >= 3072) {
                RS384.assertValidSigningKey(key);
                return RS384;
            }
            if (n >= SignatureAlgorithm.RS256.minKeyLength) {
                RS256.assertValidSigningKey(key);
                return RS256;
            }
            String string = "The specified RSA signing key is not strong enough to be used with JWT RSA signature algorithms.  The JWT specification requires RSA keys to be >= 2048 bits long.  The specified RSA key is " + n + " bits.  See https://tools.ietf.org/html/rfc7518#section-3.3 for more " + "information.";
            throw new WeakKeyException(string);
        }
        ECKey eCKey = (ECKey)((Object)key);
        int n = eCKey.getParams().getOrder().bitLength();
        for (SignatureAlgorithm signatureAlgorithm : PREFERRED_EC_ALGS) {
            if (n < signatureAlgorithm.minKeyLength) continue;
            signatureAlgorithm.assertValidSigningKey(key);
            return signatureAlgorithm;
        }
        String string = "The specified Elliptic Curve signing key is not strong enough to be used with JWT ECDSA signature algorithms.  The JWT specification requires ECDSA keys to be >= 256 bits long.  The specified ECDSA key is " + n + " bits.  See " + "https://tools.ietf.org/html/rfc7518#section-3.4 for more information.";
        throw new WeakKeyException(string);
    }

    public static SignatureAlgorithm forName(String string) throws SignatureException {
        for (SignatureAlgorithm signatureAlgorithm : SignatureAlgorithm.values()) {
            if (!signatureAlgorithm.getValue().equalsIgnoreCase(string)) continue;
            return signatureAlgorithm;
        }
        throw new SignatureException("Unsupported signature algorithm '" + string + "'");
    }

    static {
        PREFERRED_HMAC_ALGS = Collections.unmodifiableList(java.util.Arrays.asList(HS512, HS384, HS256));
        PREFERRED_EC_ALGS = Collections.unmodifiableList(java.util.Arrays.asList(ES512, ES384, ES256));
    }
}

