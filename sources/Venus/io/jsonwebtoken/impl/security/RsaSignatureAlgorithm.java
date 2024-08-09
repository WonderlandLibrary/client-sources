/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

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
import io.jsonwebtoken.security.VerifySecureDigestRequest;
import io.jsonwebtoken.security.WeakKeyException;
import java.io.InputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

final class RsaSignatureAlgorithm
extends AbstractSignatureAlgorithm {
    static final String PSS_JCA_NAME = "RSASSA-PSS";
    static final String PSS_OID = "1.2.840.113549.1.1.10";
    private static final String RS256_OID = "1.2.840.113549.1.1.11";
    private static final String RS384_OID = "1.2.840.113549.1.1.12";
    private static final String RS512_OID = "1.2.840.113549.1.1.13";
    private static final Set<String> PSS_ALG_NAMES = Collections.setOf("RSASSA-PSS", "1.2.840.113549.1.1.10");
    private static final Set<String> KEY_ALG_NAMES = Collections.setOf("RSA", "RSASSA-PSS", "1.2.840.113549.1.1.10", "1.2.840.113549.1.1.11", "1.2.840.113549.1.1.12", "1.2.840.113549.1.1.13");
    private static final int MIN_KEY_BIT_LENGTH = 2048;
    static final SignatureAlgorithm RS256 = new RsaSignatureAlgorithm(256);
    static final SignatureAlgorithm RS384 = new RsaSignatureAlgorithm(384);
    static final SignatureAlgorithm RS512 = new RsaSignatureAlgorithm(512);
    static final SignatureAlgorithm PS256 = RsaSignatureAlgorithm.rsaSsaPss(256);
    static final SignatureAlgorithm PS384 = RsaSignatureAlgorithm.rsaSsaPss(384);
    static final SignatureAlgorithm PS512 = RsaSignatureAlgorithm.rsaSsaPss(512);
    private static final Map<String, SignatureAlgorithm> PKCSv15_ALGS = new LinkedHashMap<String, SignatureAlgorithm>();
    private final int preferredKeyBitLength;
    private final AlgorithmParameterSpec algorithmParameterSpec;

    private static AlgorithmParameterSpec pssParamSpec(int n) {
        MGF1ParameterSpec mGF1ParameterSpec = new MGF1ParameterSpec("SHA-" + n);
        int n2 = n / 8;
        return new PSSParameterSpec(mGF1ParameterSpec.getDigestAlgorithm(), "MGF1", mGF1ParameterSpec, n2, 1);
    }

    private static SignatureAlgorithm rsaSsaPss(int n) {
        return new RsaSignatureAlgorithm(n, RsaSignatureAlgorithm.pssParamSpec(n));
    }

    private RsaSignatureAlgorithm(String string, String string2, int n, AlgorithmParameterSpec algorithmParameterSpec) {
        super(string, string2);
        this.preferredKeyBitLength = n * 8;
        Assert.state(this.preferredKeyBitLength >= 2048);
        this.algorithmParameterSpec = algorithmParameterSpec;
    }

    private RsaSignatureAlgorithm(int n) {
        this("RS" + n, "SHA" + n + "withRSA", n, null);
    }

    private RsaSignatureAlgorithm(int n, AlgorithmParameterSpec algorithmParameterSpec) {
        this("PS" + n, PSS_JCA_NAME, n, algorithmParameterSpec);
    }

    static SignatureAlgorithm findByKey(Key key) {
        SignatureAlgorithm signatureAlgorithm;
        String string = KeysBridge.findAlgorithm(key);
        if (!Strings.hasText(string)) {
            return null;
        }
        string = string.toUpperCase(Locale.ENGLISH);
        int n = KeysBridge.findBitLength(key);
        if (PSS_ALG_NAMES.contains(string)) {
            if (n >= 4096) {
                return PS512;
            }
            if (n >= 3072) {
                return PS384;
            }
            if (n >= 2048) {
                return PS256;
            }
        }
        if ((signatureAlgorithm = PKCSv15_ALGS.get(string)) != null) {
            return signatureAlgorithm;
        }
        if ("RSA".equals(string)) {
            if (n >= 4096) {
                return RS512;
            }
            if (n >= 3072) {
                return RS384;
            }
            if (n >= 2048) {
                return RS256;
            }
        }
        return null;
    }

    static boolean isPss(Key key) {
        String string = KeysBridge.findAlgorithm(key);
        return PSS_ALG_NAMES.contains(string);
    }

    static boolean isRsaAlgorithmName(Key key) {
        String string = KeysBridge.findAlgorithm(key);
        return KEY_ALG_NAMES.contains(string);
    }

    @Override
    public KeyPairBuilder keyPair() {
        String string = this.algorithmParameterSpec != null ? PSS_JCA_NAME : "RSA";
        return (KeyPairBuilder)new DefaultKeyPairBuilder(string, this.preferredKeyBitLength).random(Randoms.secureRandom());
    }

    @Override
    protected void validateKey(Key key, boolean bl) {
        super.validateKey(key, bl);
        if (!RsaSignatureAlgorithm.isRsaAlgorithmName(key)) {
            throw new InvalidKeyException("Unrecognized RSA or RSASSA-PSS key algorithm name.");
        }
        int n = KeysBridge.findBitLength(key);
        if (n < 0) {
            return;
        }
        if (n < 2048) {
            String string = this.getId();
            String string2 = string.startsWith("PS") ? "3.5" : "3.3";
            String string3 = "The RSA " + RsaSignatureAlgorithm.keyType(bl) + " key size (aka modulus bit length) is " + n + " bits " + "which is not secure enough for the " + string + " algorithm.  The JWT JWA Specification " + "(RFC 7518, Section " + string2 + ") states that RSA keys MUST have a size >= " + 2048 + " bits.  Consider using the Jwts.SIG." + string + ".keyPair() builder to create a KeyPair guaranteed to be secure enough for " + string + ".  See " + "https://tools.ietf.org/html/rfc7518#section-" + string2 + " for more information.";
            throw new WeakKeyException(string3);
        }
    }

    @Override
    protected byte[] doDigest(SecureRequest<InputStream, PrivateKey> secureRequest) {
        return this.jca(secureRequest).withSignature(new CheckedFunction<Signature, byte[]>(this, secureRequest){
            final SecureRequest val$request;
            final RsaSignatureAlgorithm this$0;
            {
                this.this$0 = rsaSignatureAlgorithm;
                this.val$request = secureRequest;
            }

            @Override
            public byte[] apply(Signature signature) throws Exception {
                if (RsaSignatureAlgorithm.access$000(this.this$0) != null) {
                    signature.setParameter(RsaSignatureAlgorithm.access$000(this.this$0));
                }
                signature.initSign((PrivateKey)this.val$request.getKey());
                return this.this$0.sign(signature, (InputStream)this.val$request.getPayload());
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Signature)object);
            }
        });
    }

    @Override
    protected boolean doVerify(VerifySecureDigestRequest<PublicKey> verifySecureDigestRequest) {
        return this.jca(verifySecureDigestRequest).withSignature(new CheckedFunction<Signature, Boolean>(this, verifySecureDigestRequest){
            final VerifySecureDigestRequest val$request;
            final RsaSignatureAlgorithm this$0;
            {
                this.this$0 = rsaSignatureAlgorithm;
                this.val$request = verifySecureDigestRequest;
            }

            @Override
            public Boolean apply(Signature signature) throws Exception {
                if (RsaSignatureAlgorithm.access$000(this.this$0) != null) {
                    signature.setParameter(RsaSignatureAlgorithm.access$000(this.this$0));
                }
                signature.initVerify((PublicKey)this.val$request.getKey());
                return this.this$0.verify(signature, (InputStream)this.val$request.getPayload(), this.val$request.getDigest());
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((Signature)object);
            }
        });
    }

    static AlgorithmParameterSpec access$000(RsaSignatureAlgorithm rsaSignatureAlgorithm) {
        return rsaSignatureAlgorithm.algorithmParameterSpec;
    }

    static {
        PKCSv15_ALGS.put(RS256_OID, RS256);
        PKCSv15_ALGS.put(RS384_OID, RS384);
        PKCSv15_ALGS.put(RS512_OID, RS512);
    }
}

