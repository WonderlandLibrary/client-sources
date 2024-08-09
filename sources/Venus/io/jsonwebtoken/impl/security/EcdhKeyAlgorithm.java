/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.impl.DefaultJweHeader;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.AbstractCurve;
import io.jsonwebtoken.impl.security.ConcatKDF;
import io.jsonwebtoken.impl.security.CryptoAlgorithm;
import io.jsonwebtoken.impl.security.DefaultDecryptionKeyRequest;
import io.jsonwebtoken.impl.security.DefaultKeyRequest;
import io.jsonwebtoken.impl.security.DirectKeyAlgorithm;
import io.jsonwebtoken.impl.security.ECCurve;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.impl.security.StandardCurves;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Curve;
import io.jsonwebtoken.security.DecryptionKeyRequest;
import io.jsonwebtoken.security.DynamicJwkBuilder;
import io.jsonwebtoken.security.EcPublicJwk;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.KeyLengthSupplier;
import io.jsonwebtoken.security.KeyPairBuilder;
import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.KeyResult;
import io.jsonwebtoken.security.OctetPublicJwk;
import io.jsonwebtoken.security.PublicJwk;
import io.jsonwebtoken.security.Request;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.SecurityException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECKey;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

class EcdhKeyAlgorithm
extends CryptoAlgorithm
implements KeyAlgorithm<PublicKey, PrivateKey> {
    protected static final String JCA_NAME = "ECDH";
    protected static final String XDH_JCA_NAME = "XDH";
    protected static final String DEFAULT_ID = "ECDH-ES";
    private static final String CONCAT_KDF_HASH_ALG_NAME = "SHA-256";
    private static final ConcatKDF CONCAT_KDF = new ConcatKDF("SHA-256");
    private final KeyAlgorithm<SecretKey, SecretKey> WRAP_ALG;

    private static String idFor(KeyAlgorithm<SecretKey, SecretKey> keyAlgorithm) {
        return keyAlgorithm instanceof DirectKeyAlgorithm ? DEFAULT_ID : "ECDH-ES+" + keyAlgorithm.getId();
    }

    EcdhKeyAlgorithm() {
        this(new DirectKeyAlgorithm());
    }

    EcdhKeyAlgorithm(KeyAlgorithm<SecretKey, SecretKey> keyAlgorithm) {
        super(EcdhKeyAlgorithm.idFor(keyAlgorithm), JCA_NAME);
        this.WRAP_ALG = Assert.notNull(keyAlgorithm, "Wrap algorithm cannot be null.");
    }

    protected KeyPair generateKeyPair(Curve curve, Provider provider, SecureRandom secureRandom) {
        return (KeyPair)((KeyPairBuilder)((KeyPairBuilder)curve.keyPair().provider(provider)).random(secureRandom)).build();
    }

    protected byte[] generateZ(KeyRequest<?> keyRequest, PublicKey publicKey, PrivateKey privateKey) {
        return this.jca(keyRequest).withKeyAgreement(new CheckedFunction<KeyAgreement, byte[]>(this, privateKey, keyRequest, publicKey){
            final PrivateKey val$priv;
            final KeyRequest val$request;
            final PublicKey val$pub;
            final EcdhKeyAlgorithm this$0;
            {
                this.this$0 = ecdhKeyAlgorithm;
                this.val$priv = privateKey;
                this.val$request = keyRequest;
                this.val$pub = publicKey;
            }

            @Override
            public byte[] apply(KeyAgreement keyAgreement) throws Exception {
                keyAgreement.init((Key)KeysBridge.root(this.val$priv), CryptoAlgorithm.ensureSecureRandom(this.val$request));
                keyAgreement.doPhase(this.val$pub, false);
                return keyAgreement.generateSecret();
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyAgreement)object);
            }
        });
    }

    protected String getConcatKDFAlgorithmId(AeadAlgorithm aeadAlgorithm) {
        return this.WRAP_ALG instanceof DirectKeyAlgorithm ? Assert.hasText(aeadAlgorithm.getId(), "AeadAlgorithm id cannot be null or empty.") : this.getId();
    }

    private byte[] createOtherInfo(int n, String string, byte[] byArray, byte[] byArray2) {
        Assert.hasText(string, "AlgorithmId cannot be null or empty.");
        byte[] byArray3 = string.getBytes(StandardCharsets.US_ASCII);
        byArray = Arrays.length(byArray) == 0 ? Bytes.EMPTY : byArray;
        byArray2 = Arrays.length(byArray2) == 0 ? Bytes.EMPTY : byArray2;
        return Bytes.concat(Bytes.toBytes(byArray3.length), byArray3, Bytes.toBytes(byArray.length), byArray, Bytes.toBytes(byArray2.length), byArray2, Bytes.toBytes(n), Bytes.EMPTY);
    }

    private int getKeyBitLength(AeadAlgorithm aeadAlgorithm) {
        int n = this.WRAP_ALG instanceof KeyLengthSupplier ? ((KeyLengthSupplier)((Object)this.WRAP_ALG)).getKeyBitLength() : aeadAlgorithm.getKeyBitLength();
        return Assert.gt(n, 0, "Algorithm keyBitLength must be > 0");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private SecretKey deriveKey(KeyRequest<?> keyRequest, PublicKey publicKey, PrivateKey privateKey) {
        AeadAlgorithm aeadAlgorithm = Assert.notNull(keyRequest.getEncryptionAlgorithm(), "Request encryptionAlgorithm cannot be null.");
        int n = this.getKeyBitLength(aeadAlgorithm);
        String string = this.getConcatKDFAlgorithmId(aeadAlgorithm);
        byte[] byArray = keyRequest.getHeader().getAgreementPartyUInfo();
        byte[] byArray2 = keyRequest.getHeader().getAgreementPartyVInfo();
        byte[] byArray3 = this.createOtherInfo(n, string, byArray, byArray2);
        byte[] byArray4 = this.generateZ(keyRequest, publicKey, privateKey);
        try {
            SecretKey secretKey = CONCAT_KDF.deriveKey(byArray4, n, byArray3);
            return secretKey;
        } finally {
            Bytes.clear(byArray4);
        }
    }

    @Override
    protected String getJcaName(Request<?> request) {
        if (request instanceof SecureRequest) {
            return ((SecureRequest)request).getKey() instanceof ECKey ? super.getJcaName(request) : XDH_JCA_NAME;
        }
        return request.getPayload() instanceof ECKey ? super.getJcaName(request) : XDH_JCA_NAME;
    }

    private static AbstractCurve assertCurve(Key key) {
        Curve curve = StandardCurves.findByKey(key);
        if (curve == null) {
            String string = key instanceof PublicKey ? "encryption " : "decryption ";
            String string2 = "Unable to determine JWA-standard Elliptic Curve for " + string + "key [" + KeysBridge.toString(key) + "]";
            throw new InvalidKeyException(string2);
        }
        if (curve instanceof EdwardsCurve && ((EdwardsCurve)curve).isSignatureCurve()) {
            String string = curve.getId() + " keys may not be used with ECDH-ES key agreement algorithms per " + "https://www.rfc-editor.org/rfc/rfc8037#section-3.1.";
            throw new InvalidKeyException(string);
        }
        return Assert.isInstanceOf(AbstractCurve.class, curve, "AbstractCurve instance expected.");
    }

    @Override
    public KeyResult getEncryptionKey(KeyRequest<PublicKey> keyRequest) throws SecurityException {
        Assert.notNull(keyRequest, "Request cannot be null.");
        JweHeader jweHeader = Assert.notNull(keyRequest.getHeader(), "Request JweHeader cannot be null.");
        PublicKey publicKey = (PublicKey)Assert.notNull(keyRequest.getPayload(), "Encryption PublicKey cannot be null.");
        AbstractCurve abstractCurve = EcdhKeyAlgorithm.assertCurve(publicKey);
        Assert.stateNotNull(abstractCurve, "Internal implementation state: Curve cannot be null.");
        SecureRandom secureRandom = EcdhKeyAlgorithm.ensureSecureRandom(keyRequest);
        DynamicJwkBuilder dynamicJwkBuilder = (DynamicJwkBuilder)Jwks.builder().random(secureRandom);
        KeyPair keyPair = this.generateKeyPair(abstractCurve, null, secureRandom);
        Assert.stateNotNull(keyPair, "Internal implementation state: KeyPair cannot be null.");
        PublicJwk publicJwk = (PublicJwk)dynamicJwkBuilder.key(keyPair.getPublic()).build();
        SecretKey secretKey = this.deriveKey(keyRequest, publicKey, keyPair.getPrivate());
        DefaultKeyRequest<SecretKey> defaultKeyRequest = new DefaultKeyRequest<SecretKey>(secretKey, keyRequest.getProvider(), keyRequest.getSecureRandom(), keyRequest.getHeader(), keyRequest.getEncryptionAlgorithm());
        KeyResult keyResult = this.WRAP_ALG.getEncryptionKey(defaultKeyRequest);
        jweHeader.put(DefaultJweHeader.EPK.getId(), publicJwk);
        return keyResult;
    }

    @Override
    public SecretKey getDecryptionKey(DecryptionKeyRequest<PrivateKey> decryptionKeyRequest) throws SecurityException {
        Class clazz;
        Assert.notNull(decryptionKeyRequest, "Request cannot be null.");
        JweHeader jweHeader = Assert.notNull(decryptionKeyRequest.getHeader(), "Request JweHeader cannot be null.");
        PrivateKey privateKey = (PrivateKey)Assert.notNull(decryptionKeyRequest.getKey(), "Decryption PrivateKey cannot be null.");
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(jweHeader);
        PublicJwk<?> publicJwk = requiredParameterReader.get(DefaultJweHeader.EPK);
        AbstractCurve abstractCurve = EcdhKeyAlgorithm.assertCurve(privateKey);
        Assert.stateNotNull(abstractCurve, "Internal implementation state: Curve cannot be null.");
        Class clazz2 = clazz = abstractCurve instanceof ECCurve ? EcPublicJwk.class : OctetPublicJwk.class;
        if (!clazz.isInstance(publicJwk)) {
            String string = "JWE Header " + DefaultJweHeader.EPK + " value is not an Elliptic Curve " + "Public JWK. Value: " + publicJwk;
            throw new InvalidKeyException(string);
        }
        if (!abstractCurve.contains((Key)publicJwk.toKey())) {
            String string = "JWE Header " + DefaultJweHeader.EPK + " value does not represent " + "a point on the expected curve. Value: " + publicJwk;
            throw new InvalidKeyException(string);
        }
        SecretKey secretKey = this.deriveKey(decryptionKeyRequest, (PublicKey)publicJwk.toKey(), privateKey);
        DefaultDecryptionKeyRequest<SecretKey> defaultDecryptionKeyRequest = new DefaultDecryptionKeyRequest<SecretKey>((byte[])decryptionKeyRequest.getPayload(), null, decryptionKeyRequest.getSecureRandom(), jweHeader, decryptionKeyRequest.getEncryptionAlgorithm(), secretKey);
        return this.WRAP_ALG.getDecryptionKey(defaultDecryptionKeyRequest);
    }
}

