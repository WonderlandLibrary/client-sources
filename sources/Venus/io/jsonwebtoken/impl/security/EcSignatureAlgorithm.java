/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.security.AbstractSignatureAlgorithm;
import io.jsonwebtoken.impl.security.DefaultKeyPairBuilder;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.impl.security.Randoms;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.KeyPairBuilder;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.VerifySecureDigestRequest;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.ECKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

final class EcSignatureAlgorithm
extends AbstractSignatureAlgorithm {
    private static final String REQD_ORDER_BIT_LENGTH_MSG = "orderBitLength must equal 256, 384, or 521.";
    private static final String DER_ENCODING_SYS_PROPERTY_NAME = "io.jsonwebtoken.impl.crypto.EllipticCurveSignatureValidator.derEncodingSupported";
    private static final String ES256_OID = "1.2.840.10045.4.3.2";
    private static final String ES384_OID = "1.2.840.10045.4.3.3";
    private static final String ES512_OID = "1.2.840.10045.4.3.4";
    private static final Set<String> KEY_ALG_NAMES = Collections.setOf("EC", "ECDSA", "1.2.840.10045.4.3.2", "1.2.840.10045.4.3.3", "1.2.840.10045.4.3.4");
    private final ECGenParameterSpec KEY_PAIR_GEN_PARAMS;
    private final int orderBitLength;
    private final String OID;
    private final int signatureByteLength;
    private final int sigFieldByteLength;
    static final EcSignatureAlgorithm ES256 = new EcSignatureAlgorithm(256, "1.2.840.10045.4.3.2");
    static final EcSignatureAlgorithm ES384 = new EcSignatureAlgorithm(384, "1.2.840.10045.4.3.3");
    static final EcSignatureAlgorithm ES512 = new EcSignatureAlgorithm(521, "1.2.840.10045.4.3.4");
    private static final Map<String, SignatureAlgorithm> BY_OID = new LinkedHashMap<String, SignatureAlgorithm>(3);

    private static int shaSize(int n) {
        return n == 521 ? 512 : n;
    }

    private static boolean isSupportedOrderBitLength(int n) {
        return n == 256 || n == 384 || n == 521;
    }

    static SignatureAlgorithm findByKey(Key key) {
        String string = KeysBridge.findAlgorithm(key);
        if (!Strings.hasText(string)) {
            return null;
        }
        SignatureAlgorithm signatureAlgorithm = BY_OID.get(string = string.toUpperCase(Locale.ENGLISH));
        if (signatureAlgorithm != null) {
            return signatureAlgorithm;
        }
        if ("EC".equalsIgnoreCase(string) || "ECDSA".equalsIgnoreCase(string)) {
            int n = KeysBridge.findBitLength(key);
            if (n == EcSignatureAlgorithm.ES512.orderBitLength) {
                return ES512;
            }
            if (n == EcSignatureAlgorithm.ES384.orderBitLength) {
                return ES384;
            }
            if (n == EcSignatureAlgorithm.ES256.orderBitLength) {
                return ES256;
            }
        }
        return null;
    }

    private EcSignatureAlgorithm(int n, String string) {
        super("ES" + EcSignatureAlgorithm.shaSize(n), "SHA" + EcSignatureAlgorithm.shaSize(n) + "withECDSA");
        Assert.isTrue(EcSignatureAlgorithm.isSupportedOrderBitLength(n), REQD_ORDER_BIT_LENGTH_MSG);
        this.OID = Assert.hasText(string, "Invalid OID.");
        String string2 = "secp" + n + "r1";
        this.KEY_PAIR_GEN_PARAMS = new ECGenParameterSpec(string2);
        this.orderBitLength = n;
        this.sigFieldByteLength = Bytes.length(this.orderBitLength);
        this.signatureByteLength = this.sigFieldByteLength * 2;
    }

    @Override
    public KeyPairBuilder keyPair() {
        return (KeyPairBuilder)new DefaultKeyPairBuilder("EC", this.KEY_PAIR_GEN_PARAMS).random(Randoms.secureRandom());
    }

    @Override
    protected void validateKey(Key key, boolean bl) {
        super.validateKey(key, bl);
        if (!KEY_ALG_NAMES.contains(KeysBridge.findAlgorithm(key))) {
            throw new InvalidKeyException("Unrecognized EC key algorithm name.");
        }
        int n = KeysBridge.findBitLength(key);
        if (n < 0) {
            return;
        }
        int n2 = Bytes.length(n);
        int n3 = n2 * 2;
        if (n3 != this.signatureByteLength) {
            String string = "The provided Elliptic Curve " + EcSignatureAlgorithm.keyType(bl) + " key size (aka order bit length) is " + Bytes.bitsMsg(n) + ", but the '" + this.getId() + "' algorithm requires EC Keys with " + Bytes.bitsMsg(this.orderBitLength) + " per [RFC 7518, Section 3.4](https://www.rfc-editor.org/rfc/rfc7518.html#section-3.4).";
            throw new InvalidKeyException(string);
        }
    }

    @Override
    protected byte[] doDigest(SecureRequest<InputStream, PrivateKey> secureRequest) {
        return this.jca(secureRequest).withSignature(new CheckedFunction<Signature, byte[]>(this, secureRequest){
            final SecureRequest val$request;
            final EcSignatureAlgorithm this$0;
            {
                this.this$0 = ecSignatureAlgorithm;
                this.val$request = secureRequest;
            }

            @Override
            public byte[] apply(Signature signature) throws Exception {
                signature.initSign((PrivateKey)KeysBridge.root(this.val$request));
                byte[] byArray = this.this$0.sign(signature, (InputStream)this.val$request.getPayload());
                return EcSignatureAlgorithm.transcodeDERToConcat(byArray, EcSignatureAlgorithm.access$000(this.this$0));
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Signature)object);
            }
        });
    }

    boolean isValidRAndS(PublicKey publicKey, byte[] byArray) {
        if (publicKey instanceof ECKey) {
            ECKey eCKey = (ECKey)((Object)publicKey);
            BigInteger bigInteger = eCKey.getParams().getOrder();
            BigInteger bigInteger2 = new BigInteger(1, Arrays.copyOfRange(byArray, 0, this.sigFieldByteLength));
            BigInteger bigInteger3 = new BigInteger(1, Arrays.copyOfRange(byArray, this.sigFieldByteLength, byArray.length));
            return bigInteger2.signum() >= 1 && bigInteger3.signum() >= 1 && bigInteger2.compareTo(bigInteger) < 0 && bigInteger3.compareTo(bigInteger) < 0;
        }
        return false;
    }

    @Override
    protected boolean doVerify(VerifySecureDigestRequest<PublicKey> verifySecureDigestRequest) {
        PublicKey publicKey = (PublicKey)verifySecureDigestRequest.getKey();
        return this.jca(verifySecureDigestRequest).withSignature(new CheckedFunction<Signature, Boolean>(this, verifySecureDigestRequest, publicKey){
            final VerifySecureDigestRequest val$request;
            final PublicKey val$key;
            final EcSignatureAlgorithm this$0;
            {
                this.this$0 = ecSignatureAlgorithm;
                this.val$request = verifySecureDigestRequest;
                this.val$key = publicKey;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Boolean apply(Signature signature) {
                byte[] byArray = this.val$request.getDigest();
                try {
                    byte[] byArray2;
                    block6: {
                        if (EcSignatureAlgorithm.access$000(this.this$0) != byArray.length) {
                            if (byArray[0] == 48 && "true".equalsIgnoreCase(System.getProperty(EcSignatureAlgorithm.DER_ENCODING_SYS_PROPERTY_NAME))) {
                                byArray2 = byArray;
                                break block6;
                            } else {
                                String string = "Provided signature is " + Bytes.bytesMsg(byArray.length) + " but " + this.this$0.getId() + " signatures must be exactly " + Bytes.bytesMsg(EcSignatureAlgorithm.access$000(this.this$0)) + " per [RFC 7518, Section 3.4 (validation)]" + "(https://www.rfc-editor.org/rfc/rfc7518.html#section-3.4).";
                                throw new SignatureException(string);
                            }
                        }
                        if (!this.this$0.isValidRAndS(this.val$key, byArray)) {
                            return false;
                        }
                        byArray2 = EcSignatureAlgorithm.transcodeConcatToDER(byArray);
                    }
                    signature.initVerify(this.val$key);
                    return this.this$0.verify(signature, (InputStream)this.val$request.getPayload(), byArray2);
                } catch (Exception exception) {
                    String string = "Unable to verify Elliptic Curve signature using provided ECPublicKey: " + exception.getMessage();
                    throw new SignatureException(string, exception);
                }
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Signature)object);
            }
        });
    }

    public static byte[] transcodeDERToConcat(byte[] byArray, int n) throws JwtException {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        if (byArray.length < 8 || byArray[0] != 48) {
            throw new JwtException("Invalid ECDSA signature format");
        }
        if (byArray[1] > 0) {
            n6 = 2;
        } else if (byArray[1] == -127) {
            n6 = 3;
        } else {
            throw new JwtException("Invalid ECDSA signature format");
        }
        for (n5 = n4 = byArray[n6 + 1]; n5 > 0 && byArray[n6 + 2 + n4 - n5] == 0; --n5) {
        }
        for (n3 = n2 = byArray[n6 + 2 + n4 + 1]; n3 > 0 && byArray[n6 + 2 + n4 + 2 + n2 - n3] == 0; --n3) {
        }
        int n7 = Math.max(n5, n3);
        n7 = Math.max(n7, n / 2);
        if ((byArray[n6 - 1] & 0xFF) != byArray.length - n6 || (byArray[n6 - 1] & 0xFF) != 2 + n4 + 2 + n2 || byArray[n6] != 2 || byArray[n6 + 2 + n4] != 2) {
            throw new JwtException("Invalid ECDSA signature format");
        }
        byte[] byArray2 = new byte[2 * n7];
        System.arraycopy(byArray, n6 + 2 + n4 - n5, byArray2, n7 - n5, n5);
        System.arraycopy(byArray, n6 + 2 + n4 + 2 + n2 - n3, byArray2, 2 * n7 - n3, n3);
        return byArray2;
    }

    public static byte[] transcodeConcatToDER(byte[] byArray) throws JwtException {
        try {
            return EcSignatureAlgorithm.concatToDER(byArray);
        } catch (Exception exception) {
            String string = "Invalid ECDSA signature format.";
            throw new SignatureException(string, exception);
        }
    }

    private static byte[] concatToDER(byte[] byArray) throws ArrayIndexOutOfBoundsException {
        int n;
        byte[] byArray2;
        int n2;
        int n3;
        int n4;
        int n5;
        for (n5 = n4 = byArray.length / 2; n5 > 0 && byArray[n4 - n5] == 0; --n5) {
        }
        int n6 = n5;
        if (byArray[n4 - n5] < 0) {
            ++n6;
        }
        for (n3 = n4; n3 > 0 && byArray[2 * n4 - n3] == 0; --n3) {
        }
        int n7 = n3;
        if (byArray[2 * n4 - n3] < 0) {
            ++n7;
        }
        if ((n2 = 2 + n6 + 2 + n7) > 255) {
            throw new JwtException("Invalid ECDSA signature format");
        }
        if (n2 < 128) {
            byArray2 = new byte[4 + n6 + 2 + n7];
            n = 1;
        } else {
            byArray2 = new byte[5 + n6 + 2 + n7];
            byArray2[1] = -127;
            n = 2;
        }
        byArray2[0] = 48;
        byArray2[n++] = (byte)n2;
        byArray2[n++] = 2;
        byArray2[n++] = (byte)n6;
        System.arraycopy(byArray, n4 - n5, byArray2, n + n6 - n5, n5);
        n += n6;
        byArray2[n++] = 2;
        byArray2[n++] = (byte)n7;
        System.arraycopy(byArray, 2 * n4 - n3, byArray2, n + n7 - n3, n3);
        return byArray2;
    }

    static int access$000(EcSignatureAlgorithm ecSignatureAlgorithm) {
        return ecSignatureAlgorithm.signatureByteLength;
    }

    static {
        for (EcSignatureAlgorithm ecSignatureAlgorithm : Collections.of(ES256, ES384, ES512)) {
            BY_OID.put(ecSignatureAlgorithm.OID, ecSignatureAlgorithm);
        }
    }
}

