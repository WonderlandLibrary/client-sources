/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsMutator;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJweHeader;
import io.jsonwebtoken.impl.DefaultJweHeaderBuilder;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.DefaultJwtHeaderBuilder;
import io.jsonwebtoken.impl.DefaultMutableJweHeader;
import io.jsonwebtoken.impl.DefaultProtectedHeader;
import io.jsonwebtoken.impl.DelegateAudienceCollection;
import io.jsonwebtoken.impl.DelegatingClaimsMutator;
import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.Payload;
import io.jsonwebtoken.impl.io.Base64UrlStreamEncoder;
import io.jsonwebtoken.impl.io.ByteBase64UrlStreamEncoder;
import io.jsonwebtoken.impl.io.CountingInputStream;
import io.jsonwebtoken.impl.io.EncodingOutputStream;
import io.jsonwebtoken.impl.io.NamedSerializer;
import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.impl.io.UncloseableInputStream;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.lang.Functions;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Services;
import io.jsonwebtoken.impl.security.DefaultAeadRequest;
import io.jsonwebtoken.impl.security.DefaultAeadResult;
import io.jsonwebtoken.impl.security.DefaultKeyRequest;
import io.jsonwebtoken.impl.security.DefaultSecureRequest;
import io.jsonwebtoken.impl.security.Pbes2HsAkwAlgorithm;
import io.jsonwebtoken.impl.security.ProviderKey;
import io.jsonwebtoken.impl.security.StandardSecureDigestAlgorithms;
import io.jsonwebtoken.io.CompressionAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.Serializer;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.NestedCollection;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.AeadRequest;
import io.jsonwebtoken.security.AeadResult;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.KeyResult;
import io.jsonwebtoken.security.Password;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SecureRequest;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.UnsupportedKeyException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultJwtBuilder
implements JwtBuilder {
    private static final String PUB_KEY_SIGN_MSG = "PublicKeys may not be used to create digital signatures. PrivateKeys are used to sign, and PublicKeys are used to verify.";
    private static final String PRIV_KEY_ENC_MSG = "PrivateKeys may not be used to encrypt data. PublicKeys are used to encrypt, and PrivateKeys are used to decrypt.";
    protected Provider provider;
    protected SecureRandom secureRandom;
    private final DefaultBuilderHeader headerBuilder;
    private final DefaultBuilderClaims claimsBuilder;
    private Payload payload = Payload.EMPTY;
    private SecureDigestAlgorithm<Key, ?> sigAlg = Jwts.SIG.NONE;
    private Function<SecureRequest<InputStream, Key>, byte[]> signFunction;
    private AeadAlgorithm enc;
    private KeyAlgorithm<Key, ?> keyAlg;
    private Function<KeyRequest<Key>, KeyResult> keyAlgFunction;
    private Key key;
    private Serializer<Map<String, ?>> serializer;
    protected Encoder<OutputStream, OutputStream> encoder = Base64UrlStreamEncoder.INSTANCE;
    private boolean encodePayload = true;
    protected CompressionAlgorithm compressionAlgorithm;

    public DefaultJwtBuilder() {
        this.headerBuilder = new DefaultBuilderHeader(this, null);
        this.claimsBuilder = new DefaultBuilderClaims(this, null);
    }

    @Override
    public JwtBuilder.BuilderHeader header() {
        return this.headerBuilder;
    }

    @Override
    public JwtBuilder.BuilderClaims claims() {
        return this.claimsBuilder;
    }

    @Override
    public JwtBuilder provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    @Override
    public JwtBuilder random(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
        return this;
    }

    @Override
    public JwtBuilder serializeToJsonWith(Serializer<Map<String, ?>> serializer) {
        return this.json(serializer);
    }

    @Override
    public JwtBuilder json(Serializer<Map<String, ?>> serializer) {
        this.serializer = Assert.notNull(serializer, "JSON Serializer cannot be null.");
        return this;
    }

    @Override
    public JwtBuilder base64UrlEncodeWith(Encoder<byte[], String> encoder) {
        return this.b64Url(new ByteBase64UrlStreamEncoder(encoder));
    }

    @Override
    public JwtBuilder b64Url(Encoder<OutputStream, OutputStream> encoder) {
        Assert.notNull(encoder, "encoder cannot be null.");
        this.encoder = encoder;
        return this;
    }

    @Override
    public JwtBuilder encodePayload(boolean bl) {
        this.encodePayload = bl;
        String string = DefaultProtectedHeader.CRIT.getId();
        String string2 = DefaultJwsHeader.B64.getId();
        LinkedHashSet linkedHashSet = (LinkedHashSet)DefaultBuilderHeader.access$200(this.headerBuilder, DefaultProtectedHeader.CRIT);
        linkedHashSet = new LinkedHashSet(Collections.nullSafe(linkedHashSet));
        linkedHashSet.remove(string2);
        return (JwtBuilder)((JwtBuilder.BuilderHeader)((JwtBuilder.BuilderHeader)this.header().delete(string2)).add(string, linkedHashSet)).and();
    }

    @Override
    public JwtBuilder setHeader(Map<String, ?> map) {
        return (JwtBuilder)((JwtBuilder.BuilderHeader)((JwtBuilder.BuilderHeader)this.header().empty()).add(map)).and();
    }

    @Override
    public JwtBuilder setHeaderParams(Map<String, ?> map) {
        return (JwtBuilder)((JwtBuilder.BuilderHeader)this.header().add(map)).and();
    }

    @Override
    public JwtBuilder setHeaderParam(String string, Object object) {
        return (JwtBuilder)((JwtBuilder.BuilderHeader)this.header().add(string, object)).and();
    }

    protected static <K extends Key> SecureDigestAlgorithm<K, ?> forSigningKey(K k) {
        Assert.notNull(k, "Key cannot be null.");
        SecureDigestAlgorithm<K, ?> secureDigestAlgorithm = StandardSecureDigestAlgorithms.findBySigningKey(k);
        if (secureDigestAlgorithm == null) {
            String string = "Unable to determine a suitable MAC or Signature algorithm for the specified key using available heuristics: either the key size is too weak be used with available algorithms, or the key size is unavailable (e.g. if using a PKCS11 or HSM (Hardware Security Module) key store). If you are using a PKCS11 or HSM keystore, consider using the JwtBuilder.signWith(Key, SecureDigestAlgorithm) method instead.";
            throw new UnsupportedKeyException(string);
        }
        return secureDigestAlgorithm;
    }

    @Override
    public JwtBuilder signWith(Key key) throws InvalidKeyException {
        Assert.notNull(key, "Key argument cannot be null.");
        SecureDigestAlgorithm<Key, ?> secureDigestAlgorithm = DefaultJwtBuilder.forSigningKey(key);
        return this.signWith(key, secureDigestAlgorithm);
    }

    @Override
    public <K extends Key> JwtBuilder signWith(K k, SecureDigestAlgorithm<? super K, ?> secureDigestAlgorithm) throws InvalidKeyException {
        Assert.notNull(k, "Key argument cannot be null.");
        if (k instanceof PublicKey) {
            throw new IllegalArgumentException(PUB_KEY_SIGN_MSG);
        }
        Assert.notNull(secureDigestAlgorithm, "SignatureAlgorithm cannot be null.");
        String string = Assert.hasText(secureDigestAlgorithm.getId(), "SignatureAlgorithm id cannot be null or empty.");
        if (Jwts.SIG.NONE.getId().equalsIgnoreCase(string)) {
            String string2 = "The 'none' JWS algorithm cannot be used to sign JWTs.";
            throw new IllegalArgumentException(string2);
        }
        this.key = k;
        this.sigAlg = secureDigestAlgorithm;
        this.signFunction = Functions.wrap(new Function<SecureRequest<InputStream, Key>, byte[]>(this){
            final DefaultJwtBuilder this$0;
            {
                this.this$0 = defaultJwtBuilder;
            }

            @Override
            public byte[] apply(SecureRequest<InputStream, Key> secureRequest) {
                return DefaultJwtBuilder.access$300(this.this$0).digest(secureRequest);
            }

            @Override
            public Object apply(Object object) {
                return this.apply((SecureRequest)object);
            }
        }, SignatureException.class, "Unable to compute %s signature.", string);
        return this;
    }

    @Override
    public JwtBuilder signWith(Key key, SignatureAlgorithm signatureAlgorithm) throws InvalidKeyException {
        Assert.notNull(signatureAlgorithm, "SignatureAlgorithm cannot be null.");
        signatureAlgorithm.assertValidSigningKey(key);
        return this.signWith(key, Jwts.SIG.get().forKey(signatureAlgorithm.getValue()));
    }

    @Override
    public JwtBuilder signWith(SignatureAlgorithm signatureAlgorithm, byte[] byArray) throws InvalidKeyException {
        Assert.notNull(signatureAlgorithm, "SignatureAlgorithm cannot be null.");
        Assert.notEmpty(byArray, "secret key byte array cannot be null or empty.");
        Assert.isTrue(signatureAlgorithm.isHmac(), "Key bytes may only be specified for HMAC signatures.  If using RSA or Elliptic Curve, use the signWith(SignatureAlgorithm, Key) method instead.");
        SecretKeySpec secretKeySpec = new SecretKeySpec(byArray, signatureAlgorithm.getJcaName());
        return this.signWith(secretKeySpec, signatureAlgorithm);
    }

    @Override
    public JwtBuilder signWith(SignatureAlgorithm signatureAlgorithm, String string) throws InvalidKeyException {
        Assert.hasText(string, "base64-encoded secret key cannot be null or empty.");
        Assert.isTrue(signatureAlgorithm.isHmac(), "Base64-encoded key bytes may only be specified for HMAC signatures.  If using RSA or Elliptic Curve, use the signWith(SignatureAlgorithm, Key) method instead.");
        byte[] byArray = Decoders.BASE64.decode(string);
        return this.signWith(signatureAlgorithm, byArray);
    }

    @Override
    public JwtBuilder signWith(SignatureAlgorithm signatureAlgorithm, Key key) {
        return this.signWith(key, signatureAlgorithm);
    }

    @Override
    public JwtBuilder encryptWith(SecretKey secretKey, AeadAlgorithm aeadAlgorithm) {
        if (secretKey instanceof Password) {
            return this.encryptWith((Password)secretKey, new Pbes2HsAkwAlgorithm(aeadAlgorithm.getKeyBitLength()), aeadAlgorithm);
        }
        return this.encryptWith(secretKey, Jwts.KEY.DIRECT, aeadAlgorithm);
    }

    @Override
    public <K extends Key> JwtBuilder encryptWith(K k, KeyAlgorithm<? super K, ?> keyAlgorithm, AeadAlgorithm aeadAlgorithm) {
        this.enc = Assert.notNull(aeadAlgorithm, "Encryption algorithm cannot be null.");
        Assert.hasText(aeadAlgorithm.getId(), "Encryption algorithm id cannot be null or empty.");
        Assert.notNull(k, "Encryption key cannot be null.");
        if (k instanceof PrivateKey) {
            throw new IllegalArgumentException(PRIV_KEY_ENC_MSG);
        }
        Assert.notNull(keyAlgorithm, "KeyAlgorithm cannot be null.");
        String string = Assert.hasText(keyAlgorithm.getId(), "KeyAlgorithm id cannot be null or empty.");
        this.key = k;
        KeyAlgorithm<Key, ?> keyAlgorithm2 = this.keyAlg = keyAlgorithm;
        String string2 = "Unable to obtain content encryption key from key management algorithm '%s'.";
        this.keyAlgFunction = Functions.wrap(new Function<KeyRequest<Key>, KeyResult>(this, keyAlgorithm2){
            final KeyAlgorithm val$alg;
            final DefaultJwtBuilder this$0;
            {
                this.this$0 = defaultJwtBuilder;
                this.val$alg = keyAlgorithm;
            }

            @Override
            public KeyResult apply(KeyRequest<Key> keyRequest) {
                return this.val$alg.getEncryptionKey(keyRequest);
            }

            @Override
            public Object apply(Object object) {
                return this.apply((KeyRequest)object);
            }
        }, SecurityException.class, "Unable to obtain content encryption key from key management algorithm '%s'.", string);
        return this;
    }

    @Override
    public JwtBuilder compressWith(CompressionAlgorithm compressionAlgorithm) {
        Assert.notNull(compressionAlgorithm, "CompressionAlgorithm cannot be null");
        Assert.hasText(compressionAlgorithm.getId(), "CompressionAlgorithm id cannot be null or empty.");
        this.compressionAlgorithm = compressionAlgorithm;
        return (JwtBuilder)((JwtBuilder.BuilderHeader)this.header().delete(DefaultHeader.COMPRESSION_ALGORITHM.getId())).and();
    }

    @Override
    public JwtBuilder setPayload(String string) {
        return this.content(string);
    }

    @Override
    public JwtBuilder content(String string) {
        if (Strings.hasText(string)) {
            this.payload = new Payload(string, null);
        }
        return this;
    }

    @Override
    public JwtBuilder content(byte[] byArray) {
        if (!Bytes.isEmpty(byArray)) {
            this.payload = new Payload(byArray, null);
        }
        return this;
    }

    @Override
    public JwtBuilder content(InputStream inputStream) {
        if (inputStream != null) {
            this.payload = new Payload(inputStream, null);
        }
        return this;
    }

    @Override
    public JwtBuilder content(byte[] byArray, String string) {
        Assert.notEmpty(byArray, "content byte array cannot be null or empty.");
        Assert.hasText(string, "Content Type String cannot be null or empty.");
        this.payload = new Payload(byArray, string);
        return (JwtBuilder)((JwtBuilder.BuilderHeader)this.header().delete(DefaultHeader.CONTENT_TYPE.getId())).and();
    }

    @Override
    public JwtBuilder content(String string, String string2) throws IllegalArgumentException {
        Assert.hasText(string, "Content string cannot be null or empty.");
        Assert.hasText(string2, "ContentType string cannot be null or empty.");
        this.payload = new Payload(string, string2);
        return (JwtBuilder)((JwtBuilder.BuilderHeader)this.header().delete(DefaultHeader.CONTENT_TYPE.getId())).and();
    }

    @Override
    public JwtBuilder content(InputStream inputStream, String string) throws IllegalArgumentException {
        Assert.notNull(inputStream, "Payload InputStream cannot be null.");
        Assert.hasText(string, "ContentType string cannot be null or empty.");
        this.payload = new Payload(inputStream, string);
        return (JwtBuilder)((JwtBuilder.BuilderHeader)this.header().delete(DefaultHeader.CONTENT_TYPE.getId())).and();
    }

    @Override
    public JwtBuilder setClaims(Map<String, ?> map) {
        Assert.notNull(map, "Claims map cannot be null.");
        return (JwtBuilder)((JwtBuilder.BuilderClaims)((JwtBuilder.BuilderClaims)this.claims().empty()).add(map)).and();
    }

    @Override
    public JwtBuilder addClaims(Map<String, ?> map) {
        return this.claims(map);
    }

    @Override
    public JwtBuilder claims(Map<String, ?> map) {
        return (JwtBuilder)((JwtBuilder.BuilderClaims)this.claims().add(map)).and();
    }

    @Override
    public JwtBuilder claim(String string, Object object) {
        return (JwtBuilder)((JwtBuilder.BuilderClaims)this.claims().add(string, object)).and();
    }

    @Override
    public JwtBuilder setIssuer(String string) {
        return this.issuer(string);
    }

    @Override
    public JwtBuilder issuer(String string) {
        return (JwtBuilder)((JwtBuilder.BuilderClaims)this.claims().issuer(string)).and();
    }

    @Override
    public JwtBuilder setSubject(String string) {
        return this.subject(string);
    }

    @Override
    public JwtBuilder subject(String string) {
        return (JwtBuilder)((JwtBuilder.BuilderClaims)this.claims().subject(string)).and();
    }

    @Override
    public JwtBuilder setAudience(String string) {
        return (JwtBuilder)((JwtBuilder.BuilderClaims)this.claims().setAudience(string)).and();
    }

    @Override
    public ClaimsMutator.AudienceCollection<JwtBuilder> audience() {
        return new DelegateAudienceCollection<JwtBuilder>(this, this.claims().audience());
    }

    @Override
    public JwtBuilder setExpiration(Date date) {
        return this.expiration(date);
    }

    @Override
    public JwtBuilder expiration(Date date) {
        return (JwtBuilder)((JwtBuilder.BuilderClaims)this.claims().expiration(date)).and();
    }

    @Override
    public JwtBuilder setNotBefore(Date date) {
        return this.notBefore(date);
    }

    @Override
    public JwtBuilder notBefore(Date date) {
        return (JwtBuilder)((JwtBuilder.BuilderClaims)this.claims().notBefore(date)).and();
    }

    @Override
    public JwtBuilder setIssuedAt(Date date) {
        return this.issuedAt(date);
    }

    @Override
    public JwtBuilder issuedAt(Date date) {
        return (JwtBuilder)((JwtBuilder.BuilderClaims)this.claims().issuedAt(date)).and();
    }

    @Override
    public JwtBuilder setId(String string) {
        return this.id(string);
    }

    @Override
    public JwtBuilder id(String string) {
        return (JwtBuilder)((JwtBuilder.BuilderClaims)this.claims().id(string)).and();
    }

    private void assertPayloadEncoding(String string) {
        if (!this.encodePayload) {
            String string2 = "Payload encoding may not be disabled for " + string + "s, only JWSs.";
            throw new IllegalArgumentException(string2);
        }
    }

    @Override
    public String compact() {
        boolean bl;
        boolean bl2 = bl = this.enc != null;
        if (bl && this.signFunction != null) {
            String string = "Both 'signWith' and 'encryptWith' cannot be specified. Choose either one.";
            throw new IllegalStateException(string);
        }
        Payload payload = Assert.stateNotNull(this.payload, "Payload instance null, internal error");
        Claims claims = DefaultBuilderClaims.access$400(this.claimsBuilder);
        if (bl && payload.isEmpty() && Collections.isEmpty(claims)) {
            String string = "Encrypted JWTs must have either 'claims' or non-empty 'content'.";
            throw new IllegalStateException(string);
        }
        if (!payload.isEmpty() && !Collections.isEmpty(claims)) {
            throw new IllegalStateException("Both 'content' and 'claims' cannot be specified. Choose either one.");
        }
        if (this.serializer == null) {
            this.json(Services.loadFirst(Serializer.class));
        }
        if (!Collections.isEmpty(claims)) {
            payload = new Payload(claims);
        }
        if (this.compressionAlgorithm != null && !payload.isEmpty()) {
            payload.setZip(this.compressionAlgorithm);
            this.headerBuilder.put(DefaultHeader.COMPRESSION_ALGORITHM.getId(), this.compressionAlgorithm.getId());
        }
        if (Strings.hasText(payload.getContentType())) {
            this.headerBuilder.contentType(payload.getContentType());
        }
        Provider provider = ProviderKey.getProvider(this.key, this.provider);
        Key key = ProviderKey.getKey(this.key);
        if (bl) {
            return this.encrypt(payload, key, provider);
        }
        if (key != null) {
            return this.sign(payload, key, provider);
        }
        return this.unprotected(payload);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void writeAndClose(String string, Map<String, ?> map, OutputStream outputStream) {
        try {
            NamedSerializer namedSerializer = new NamedSerializer(string, this.serializer);
            namedSerializer.serialize(map, outputStream);
        } catch (Throwable throwable) {
            Objects.nullSafeClose(outputStream);
            throw throwable;
        }
        Objects.nullSafeClose(outputStream);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeAndClose(String string, Payload payload, OutputStream outputStream) {
        outputStream = payload.compress(outputStream);
        if (payload.isClaims()) {
            this.writeAndClose(string, payload.getRequiredClaims(), outputStream);
            return;
        }
        try {
            InputStream inputStream = payload.toInputStream();
            Streams.copy(inputStream, outputStream, new byte[4096], "Unable to copy payload.");
        } catch (Throwable throwable) {
            Objects.nullSafeClose(outputStream);
            throw throwable;
        }
        Objects.nullSafeClose(outputStream);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String sign(Payload payload, Key key, Provider provider) {
        Object object;
        Object object2;
        InputStream inputStream;
        Object object3;
        Assert.stateNotNull(key, "Key is required.");
        Assert.stateNotNull(this.sigAlg, "SignatureAlgorithm is required.");
        Assert.stateNotNull(this.signFunction, "Signature Algorithm function cannot be null.");
        Assert.stateNotNull(payload, "Payload argument cannot be null.");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        this.headerBuilder.add(DefaultHeader.ALGORITHM.getId(), this.sigAlg.getId());
        if (!this.encodePayload) {
            object3 = DefaultJwsHeader.B64.getId();
            ((JwtBuilder.BuilderHeader)((NestedCollection)this.headerBuilder.critical().add((String)object3)).and()).add(object3, false);
        }
        object3 = Assert.isInstanceOf(JwsHeader.class, DefaultBuilderHeader.access$500(this.headerBuilder));
        this.encodeAndWrite("JWS Protected Header", (Map<String, ?>)object3, (OutputStream)byteArrayOutputStream);
        byteArrayOutputStream.write(46);
        InputStream inputStream2 = null;
        if (this.encodePayload) {
            this.encodeAndWrite("JWS Payload", payload, (OutputStream)byteArrayOutputStream);
            inputStream = Streams.of(byteArrayOutputStream.toByteArray());
        } else {
            object2 = Streams.of(byteArrayOutputStream.toByteArray());
            if (payload.isClaims() || payload.isCompressed()) {
                object = new ByteArrayOutputStream(8192);
                this.writeAndClose("JWS Unencoded Payload", payload, (OutputStream)object);
                inputStream2 = Streams.of(((ByteArrayOutputStream)object).toByteArray());
            } else {
                inputStream2 = Assert.stateNotNull(payload.toInputStream(), "Payload InputStream cannot be null.");
            }
            if (!payload.isClaims()) {
                inputStream2 = new CountingInputStream(inputStream2);
            }
            if (inputStream2.markSupported()) {
                inputStream2.mark(0);
            }
            inputStream = new SequenceInputStream((InputStream)object2, new UncloseableInputStream(inputStream2));
        }
        try {
            object = new DefaultSecureRequest<InputStream, Key>(inputStream, provider, this.secureRandom, key);
            object2 = this.signFunction.apply((SecureRequest<InputStream, Key>)object);
            if (!this.encodePayload) {
                if (!payload.isCompressed() && (payload.isClaims() || payload.isString())) {
                    Streams.copy(inputStream2, byteArrayOutputStream, new byte[8192], "Unable to copy attached Payload InputStream.");
                }
                if (inputStream2 instanceof CountingInputStream && ((CountingInputStream)inputStream2).getCount() <= 0L) {
                    String string = "'b64' Unencoded payload option has been specified, but payload is empty.";
                    throw new IllegalStateException(string);
                }
            }
        } catch (Throwable throwable) {
            Streams.reset(inputStream2);
            throw throwable;
        }
        Streams.reset(inputStream2);
        byteArrayOutputStream.write(46);
        this.encodeAndWrite("JWS Signature", (byte[])object2, (OutputStream)byteArrayOutputStream);
        return Strings.utf8(byteArrayOutputStream.toByteArray());
    }

    private String unprotected(Payload payload) {
        Assert.stateNotNull(payload, "Content argument cannot be null.");
        this.assertPayloadEncoding("unprotected JWT");
        this.headerBuilder.add(DefaultHeader.ALGORITHM.getId(), Jwts.SIG.NONE.getId());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        Header header = DefaultBuilderHeader.access$500(this.headerBuilder);
        this.encodeAndWrite("JWT Header", header, (OutputStream)byteArrayOutputStream);
        byteArrayOutputStream.write(46);
        this.encodeAndWrite("JWT Payload", payload, (OutputStream)byteArrayOutputStream);
        byteArrayOutputStream.write(46);
        return Strings.ascii(byteArrayOutputStream.toByteArray());
    }

    private void encrypt(AeadRequest aeadRequest, AeadResult aeadResult) throws SecurityException {
        Function<Object, Object> function = Functions.wrap(new Function<Object, Object>(this, aeadRequest, aeadResult){
            final AeadRequest val$req;
            final AeadResult val$res;
            final DefaultJwtBuilder this$0;
            {
                this.this$0 = defaultJwtBuilder;
                this.val$req = aeadRequest;
                this.val$res = aeadResult;
            }

            @Override
            public Object apply(Object object) {
                DefaultJwtBuilder.access$600(this.this$0).encrypt(this.val$req, this.val$res);
                return null;
            }
        }, SecurityException.class, "%s encryption failed.", this.enc.getId());
        function.apply(null);
    }

    private String encrypt(Payload payload, Key key, Provider provider) {
        InputStream inputStream;
        Object object;
        Assert.stateNotNull(payload, "Payload argument cannot be null.");
        Assert.stateNotNull(key, "Key is required.");
        Assert.stateNotNull(this.enc, "Encryption algorithm is required.");
        Assert.stateNotNull(this.keyAlg, "KeyAlgorithm is required.");
        Assert.stateNotNull(this.keyAlgFunction, "KeyAlgorithm function cannot be null.");
        this.assertPayloadEncoding("JWE");
        if (payload.isClaims()) {
            object = new ByteArrayOutputStream(4096);
            this.writeAndClose("JWE Claims", payload, (OutputStream)object);
            inputStream = Streams.of(((ByteArrayOutputStream)object).toByteArray());
        } else {
            inputStream = payload.toInputStream();
        }
        object = new DefaultMutableJweHeader(this.headerBuilder);
        DefaultKeyRequest<Key> defaultKeyRequest = new DefaultKeyRequest<Key>(key, provider, this.secureRandom, (JweHeader)object, this.enc);
        KeyResult keyResult = this.keyAlgFunction.apply(defaultKeyRequest);
        Assert.stateNotNull(keyResult, "KeyAlgorithm must return a KeyResult.");
        SecretKey secretKey = (SecretKey)Assert.notNull(keyResult.getKey(), "KeyResult must return a content encryption key.");
        byte[] byArray = (byte[])Assert.notNull(keyResult.getPayload(), "KeyResult must return an encrypted key byte array, even if empty.");
        this.headerBuilder.add(DefaultHeader.ALGORITHM.getId(), this.keyAlg.getId());
        this.headerBuilder.put(DefaultJweHeader.ENCRYPTION_ALGORITHM.getId(), this.enc.getId());
        JweHeader jweHeader = Assert.isInstanceOf(JweHeader.class, DefaultBuilderHeader.access$500(this.headerBuilder), "Invalid header created: ");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
        this.encodeAndWrite("JWE Protected Header", jweHeader, (OutputStream)byteArrayOutputStream);
        InputStream inputStream2 = Streams.of(byteArrayOutputStream.toByteArray());
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(8192);
        DefaultAeadRequest defaultAeadRequest = new DefaultAeadRequest(inputStream, null, this.secureRandom, secretKey, inputStream2);
        DefaultAeadResult defaultAeadResult = new DefaultAeadResult(byteArrayOutputStream2);
        this.encrypt(defaultAeadRequest, defaultAeadResult);
        byte[] byArray2 = Assert.notEmpty(defaultAeadResult.getIv(), "Encryption result must have a non-empty initialization vector.");
        byte[] byArray3 = Assert.notEmpty(defaultAeadResult.getDigest(), "Encryption result must have a non-empty authentication tag.");
        byte[] byArray4 = Assert.notEmpty(byteArrayOutputStream2.toByteArray(), "Encryption result must have non-empty ciphertext.");
        byteArrayOutputStream.write(46);
        this.encodeAndWrite("JWE Encrypted CEK", byArray, (OutputStream)byteArrayOutputStream);
        byteArrayOutputStream.write(46);
        this.encodeAndWrite("JWE Initialization Vector", byArray2, (OutputStream)byteArrayOutputStream);
        byteArrayOutputStream.write(46);
        this.encodeAndWrite("JWE Ciphertext", byArray4, (OutputStream)byteArrayOutputStream);
        byteArrayOutputStream.write(46);
        this.encodeAndWrite("JWE AAD Tag", byArray3, (OutputStream)byteArrayOutputStream);
        return Strings.utf8(byteArrayOutputStream.toByteArray());
    }

    private OutputStream encode(OutputStream outputStream, String string) {
        outputStream = this.encoder.encode(outputStream);
        return new EncodingOutputStream(outputStream, "base64url", string);
    }

    private void encodeAndWrite(String string, Map<String, ?> map, OutputStream outputStream) {
        outputStream = this.encode(outputStream, string);
        this.writeAndClose(string, map, outputStream);
    }

    private void encodeAndWrite(String string, Payload payload, OutputStream outputStream) {
        outputStream = this.encode(outputStream, string);
        this.writeAndClose(string, payload, outputStream);
    }

    private void encodeAndWrite(String string, byte[] byArray, OutputStream outputStream) {
        outputStream = this.encode(outputStream, string);
        Streams.writeAndClose(outputStream, byArray, "Unable to write bytes");
    }

    @Override
    public ClaimsMutator id(String string) {
        return this.id(string);
    }

    @Override
    public ClaimsMutator setId(String string) {
        return this.setId(string);
    }

    @Override
    public ClaimsMutator issuedAt(Date date) {
        return this.issuedAt(date);
    }

    @Override
    public ClaimsMutator setIssuedAt(Date date) {
        return this.setIssuedAt(date);
    }

    @Override
    public ClaimsMutator notBefore(Date date) {
        return this.notBefore(date);
    }

    @Override
    public ClaimsMutator setNotBefore(Date date) {
        return this.setNotBefore(date);
    }

    @Override
    public ClaimsMutator expiration(Date date) {
        return this.expiration(date);
    }

    @Override
    public ClaimsMutator setExpiration(Date date) {
        return this.setExpiration(date);
    }

    @Override
    public ClaimsMutator setAudience(String string) {
        return this.setAudience(string);
    }

    @Override
    public ClaimsMutator subject(String string) {
        return this.subject(string);
    }

    @Override
    public ClaimsMutator setSubject(String string) {
        return this.setSubject(string);
    }

    @Override
    public ClaimsMutator issuer(String string) {
        return this.issuer(string);
    }

    @Override
    public ClaimsMutator setIssuer(String string) {
        return this.setIssuer(string);
    }

    static SecureDigestAlgorithm access$300(DefaultJwtBuilder defaultJwtBuilder) {
        return defaultJwtBuilder.sigAlg;
    }

    static AeadAlgorithm access$600(DefaultJwtBuilder defaultJwtBuilder) {
        return defaultJwtBuilder.enc;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class DefaultBuilderHeader
    extends DefaultJweHeaderBuilder<JwtBuilder.BuilderHeader>
    implements JwtBuilder.BuilderHeader {
        private final JwtBuilder builder;

        private DefaultBuilderHeader(JwtBuilder jwtBuilder) {
            this.builder = Assert.notNull(jwtBuilder, "JwtBuilder cannot be null.");
        }

        @Override
        public JwtBuilder and() {
            return this.builder;
        }

        private <T> T get(Parameter<T> parameter) {
            return ((ParameterMap)this.DELEGATE).get(parameter);
        }

        private Header build() {
            return new DefaultJwtHeaderBuilder(this).build();
        }

        @Override
        public Object and() {
            return this.and();
        }

        DefaultBuilderHeader(JwtBuilder jwtBuilder, 1 var2_2) {
            this(jwtBuilder);
        }

        static Object access$200(DefaultBuilderHeader defaultBuilderHeader, Parameter parameter) {
            return defaultBuilderHeader.get(parameter);
        }

        static Header access$500(DefaultBuilderHeader defaultBuilderHeader) {
            return defaultBuilderHeader.build();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class DefaultBuilderClaims
    extends DelegatingClaimsMutator<JwtBuilder.BuilderClaims>
    implements JwtBuilder.BuilderClaims {
        private final JwtBuilder builder;

        private DefaultBuilderClaims(JwtBuilder jwtBuilder) {
            this.builder = jwtBuilder;
        }

        @Override
        public JwtBuilder and() {
            return this.builder;
        }

        private Claims build() {
            return new DefaultClaims((ParameterMap)this.DELEGATE);
        }

        @Override
        public Object and() {
            return this.and();
        }

        DefaultBuilderClaims(JwtBuilder jwtBuilder, 1 var2_2) {
            this(jwtBuilder);
        }

        static Claims access$400(DefaultBuilderClaims defaultBuilderClaims) {
            return defaultBuilderClaims.build();
        }
    }
}

