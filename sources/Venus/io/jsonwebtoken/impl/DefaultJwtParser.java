/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.CompressionCodecResolver;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtHandler;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Locator;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.ProtectedHeader;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.CompressionCodecLocator;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwe;
import io.jsonwebtoken.impl.DefaultJweHeader;
import io.jsonwebtoken.impl.DefaultJws;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.DefaultJwt;
import io.jsonwebtoken.impl.DefaultProtectedHeader;
import io.jsonwebtoken.impl.IdLocator;
import io.jsonwebtoken.impl.JwtTokenizer;
import io.jsonwebtoken.impl.Payload;
import io.jsonwebtoken.impl.TokenizedJwe;
import io.jsonwebtoken.impl.TokenizedJwt;
import io.jsonwebtoken.impl.io.AbstractParser;
import io.jsonwebtoken.impl.io.BytesInputStream;
import io.jsonwebtoken.impl.io.CharSequenceReader;
import io.jsonwebtoken.impl.io.JsonObjectDeserializer;
import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.impl.io.UncloseableInputStream;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.security.DefaultDecryptAeadRequest;
import io.jsonwebtoken.impl.security.DefaultDecryptionKeyRequest;
import io.jsonwebtoken.impl.security.DefaultVerifySecureDigestRequest;
import io.jsonwebtoken.impl.security.LocatingKeyResolver;
import io.jsonwebtoken.impl.security.ProviderKey;
import io.jsonwebtoken.io.CompressionAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.DateFormats;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.SecretKey;

public class DefaultJwtParser
extends AbstractParser<Jwt<?, ?>>
implements JwtParser {
    static final char SEPARATOR_CHAR = '.';
    private static final JwtTokenizer jwtTokenizer = new JwtTokenizer();
    static final String PRIV_KEY_VERIFY_MSG = "PrivateKeys may not be used to verify digital signatures. PrivateKeys are used to sign, and PublicKeys are used to verify.";
    static final String PUB_KEY_DECRYPT_MSG = "PublicKeys may not be used to decrypt data. PublicKeys are used to encrypt, and PrivateKeys are used to decrypt.";
    public static final String INCORRECT_EXPECTED_CLAIM_MESSAGE_TEMPLATE = "Expected %s claim to be: %s, but was: %s.";
    public static final String MISSING_EXPECTED_CLAIM_VALUE_MESSAGE_TEMPLATE = "Missing expected '%s' value in '%s' claim %s.";
    public static final String MISSING_JWS_ALG_MSG = "JWS header does not contain a required 'alg' (Algorithm) header parameter.  This header parameter is mandatory per the JWS Specification, Section 4.1.1. See https://www.rfc-editor.org/rfc/rfc7515.html#section-4.1.1 for more information.";
    public static final String MISSING_JWE_ALG_MSG = "JWE header does not contain a required 'alg' (Algorithm) header parameter.  This header parameter is mandatory per the JWE Specification, Section 4.1.1. See https://www.rfc-editor.org/rfc/rfc7516.html#section-4.1.1 for more information.";
    public static final String MISSING_JWS_DIGEST_MSG_FMT = "The JWS header references signature algorithm '%s' but the compact JWE string is missing the required signature.";
    public static final String MISSING_JWE_DIGEST_MSG_FMT = "The JWE header references key management algorithm '%s' but the compact JWE string is missing the required AAD authentication tag.";
    private static final String MISSING_ENC_MSG = "JWE header does not contain a required 'enc' (Encryption Algorithm) header parameter.  This header parameter is mandatory per the JWE Specification, Section 4.1.2. See https://www.rfc-editor.org/rfc/rfc7516.html#section-4.1.2 for more information.";
    private static final String UNSECURED_DISABLED_MSG_PREFIX = "Unsecured JWSs (those with an " + DefaultHeader.ALGORITHM + " header value of '" + Jwts.SIG.NONE.getId() + "') are disallowed by " + "default as mandated by https://www.rfc-editor.org/rfc/rfc7518.html#section-3.6. If you wish to " + "allow them to be parsed, call the JwtParserBuilder.unsecured() method, but please read the " + "security considerations covered in that method's JavaDoc before doing so. Header: ";
    private static final String CRIT_UNSECURED_MSG = "Unsecured JWSs (those with an " + DefaultHeader.ALGORITHM + " header value of '" + Jwts.SIG.NONE.getId() + "') may not use the " + DefaultProtectedHeader.CRIT + " header parameter per https://www.rfc-editor.org/rfc/rfc7515.html#section-4.1.11 (\"the [crit] Header " + "Parameter MUST be integrity protected; therefore, it MUST occur only within [a] JWS Protected Header)\"." + " Header: %s";
    private static final String CRIT_MISSING_MSG = "Protected Header " + DefaultProtectedHeader.CRIT + " set references header name '%s', but the header does not contain an " + "associated '%s' header parameter as required by " + "https://www.rfc-editor.org/rfc/rfc7515.html#section-4.1.11. Header: %s";
    private static final String CRIT_UNSUPPORTED_MSG = "Protected Header " + DefaultProtectedHeader.CRIT + " set references unsupported header name '%s'. Application developers expecting to support a JWT " + "extension using header '%s' in their application code must indicate it " + "is supported by using the JwtParserBuilder.critical method. Header: %s";
    private static final String JWE_NONE_MSG = "JWEs do not support key management " + DefaultHeader.ALGORITHM + " header value '" + Jwts.SIG.NONE.getId() + "' per " + "https://www.rfc-editor.org/rfc/rfc7518.html#section-4.1";
    private static final String JWS_NONE_SIG_MISMATCH_MSG = "The JWS header references signature algorithm '" + Jwts.SIG.NONE.getId() + "' yet the compact JWS string contains a signature. This is not permitted " + "per https://tools.ietf.org/html/rfc7518#section-3.6.";
    private static final String B64_MISSING_PAYLOAD = "Unable to verify JWS signature: the parser has encountered an Unencoded Payload JWS with detached payload, but the detached payload value required for signature verification has not been provided. If you expect to receive and parse Unencoded Payload JWSs in your application, the overloaded JwtParser.parseSignedContent or JwtParser.parseSignedClaims methods that accept a byte[] or InputStream must be used for these kinds of JWSs. Header: %s";
    private static final String B64_DECOMPRESSION_MSG = "The JWT header references compression algorithm '%s', but payload decompression for Unencoded JWSs (those with an " + DefaultJwsHeader.B64 + " header value of false) that rely on a SigningKeyResolver are disallowed " + "by default to protect against [Denial of Service attacks](" + "https://www.usenix.org/system/files/conference/usenixsecurity15/sec15-paper-pellegrino.pdf).  If you " + "wish to enable Unencoded JWS payload decompression, configure the JwtParserBuilder." + "keyLocator(Locator) and do not configure a SigningKeyResolver.";
    private static final String UNPROTECTED_DECOMPRESSION_MSG = "The JWT header references compression algorithm '%s', but payload decompression for Unprotected JWTs (those with an " + DefaultHeader.ALGORITHM + " header value of '" + Jwts.SIG.NONE.getId() + "') or Unencoded JWSs (those with a " + DefaultJwsHeader.B64 + " header value of false) that also rely on a SigningKeyResolver are disallowed " + "by default to protect against [Denial of Service attacks](" + "https://www.usenix.org/system/files/conference/usenixsecurity15/sec15-paper-pellegrino.pdf).  If you " + "wish to enable Unsecure JWS or Unencoded JWS payload decompression, call the JwtParserBuilder." + "unsecuredDecompression() method, but please read the security considerations covered in that " + "method's JavaDoc before doing so.";
    private final Provider provider;
    private final SigningKeyResolver signingKeyResolver;
    private final boolean unsecured;
    private final boolean unsecuredDecompression;
    private final Function<JwsHeader, SecureDigestAlgorithm<?, ?>> sigAlgs;
    private final Function<JweHeader, AeadAlgorithm> encAlgs;
    private final Function<JweHeader, KeyAlgorithm<?, ?>> keyAlgs;
    private final Function<Header, CompressionAlgorithm> zipAlgs;
    private final Locator<? extends Key> keyLocator;
    private final Decoder<InputStream, InputStream> decoder;
    private final Deserializer<Map<String, ?>> deserializer;
    private final ClaimsBuilder expectedClaims;
    private final Clock clock;
    private final Set<String> critical;
    private final long allowedClockSkewMillis;

    DefaultJwtParser(Provider provider, SigningKeyResolver signingKeyResolver, boolean bl, boolean bl2, Locator<? extends Key> locator, Clock clock, Set<String> set, long l, DefaultClaims defaultClaims, Decoder<InputStream, InputStream> decoder, Deserializer<Map<String, ?>> deserializer, CompressionCodecResolver compressionCodecResolver, Registry<String, CompressionAlgorithm> registry, Registry<String, SecureDigestAlgorithm<?, ?>> registry2, Registry<String, KeyAlgorithm<?, ?>> registry3, Registry<String, AeadAlgorithm> registry4) {
        this.provider = provider;
        this.unsecured = bl;
        this.unsecuredDecompression = bl2;
        this.signingKeyResolver = signingKeyResolver;
        this.keyLocator = Assert.notNull(locator, "Key Locator cannot be null.");
        this.clock = Assert.notNull(clock, "Clock cannot be null.");
        this.critical = Collections.nullSafe(set);
        this.allowedClockSkewMillis = l;
        this.expectedClaims = (ClaimsBuilder)Jwts.claims().add(defaultClaims);
        this.decoder = Assert.notNull(decoder, "base64UrlDecoder cannot be null.");
        this.deserializer = Assert.notNull(deserializer, "JSON Deserializer cannot be null.");
        this.sigAlgs = new IdLocator(DefaultHeader.ALGORITHM, registry2, MISSING_JWS_ALG_MSG);
        this.keyAlgs = new IdLocator(DefaultHeader.ALGORITHM, registry3, MISSING_JWE_ALG_MSG);
        this.encAlgs = new IdLocator<JweHeader, AeadAlgorithm>(DefaultJweHeader.ENCRYPTION_ALGORITHM, registry4, MISSING_ENC_MSG);
        this.zipAlgs = compressionCodecResolver != null ? new CompressionCodecLocator(compressionCodecResolver) : new IdLocator(DefaultHeader.COMPRESSION_ALGORITHM, registry, null);
    }

    @Override
    public boolean isSigned(CharSequence charSequence) {
        if (!Strings.hasText(charSequence)) {
            return true;
        }
        try {
            Object t = jwtTokenizer.tokenize(new CharSequenceReader(charSequence));
            return !(t instanceof TokenizedJwe) && Strings.hasText(t.getDigest());
        } catch (MalformedJwtException malformedJwtException) {
            return true;
        }
    }

    private static boolean hasContentType(Header header) {
        return header != null && Strings.hasText(header.getContentType());
    }

    private void verifySignature(TokenizedJwt tokenizedJwt, JwsHeader jwsHeader, String string, SigningKeyResolver signingKeyResolver, Claims claims, Payload payload) {
        InputStream inputStream;
        Object object;
        Object object2;
        SecureDigestAlgorithm<?, ?> secureDigestAlgorithm;
        Assert.notNull(signingKeyResolver, "SigningKeyResolver instance cannot be null.");
        try {
            secureDigestAlgorithm = this.sigAlgs.apply(jwsHeader);
        } catch (UnsupportedJwtException unsupportedJwtException) {
            String string2 = "Unsupported signature algorithm '" + string + "'";
            throw new SignatureException(string2, unsupportedJwtException);
        }
        Assert.stateNotNull(secureDigestAlgorithm, "JWS Signature Algorithm cannot be null.");
        Key key = claims != null ? signingKeyResolver.resolveSigningKey(jwsHeader, claims) : signingKeyResolver.resolveSigningKey(jwsHeader, payload.getBytes());
        if (key == null) {
            String string3 = "Cannot verify JWS signature: unable to locate signature verification key for JWS with header: " + jwsHeader;
            throw new UnsupportedJwtException(string3);
        }
        Provider provider = ProviderKey.getProvider(key, this.provider);
        key = ProviderKey.getKey(key);
        Assert.stateNotNull(key, "ProviderKey cannot be null.");
        if (key instanceof PrivateKey) {
            throw new InvalidKeyException(PRIV_KEY_VERIFY_MSG);
        }
        byte[] byArray = this.decode(tokenizedJwt.getDigest(), "JWS signature");
        InputStream inputStream2 = null;
        if (jwsHeader.isPayloadEncoded()) {
            int n = tokenizedJwt.getProtected().length() + 1 + tokenizedJwt.getPayload().length();
            object2 = CharBuffer.allocate(n);
            ((CharBuffer)object2).put(Strings.wrap(tokenizedJwt.getProtected()));
            ((CharBuffer)object2).put('.');
            ((CharBuffer)object2).put(Strings.wrap(tokenizedJwt.getPayload()));
            ((Buffer)object2).rewind();
            object = StandardCharsets.US_ASCII.encode((CharBuffer)object2);
            ((Buffer)object).rewind();
            byte[] byArray2 = new byte[((Buffer)object).remaining()];
            ((ByteBuffer)object).get(byArray2);
            inputStream = Streams.of(byArray2);
        } else {
            ByteBuffer byteBuffer = StandardCharsets.US_ASCII.encode(Strings.wrap(tokenizedJwt.getProtected()));
            byteBuffer.rewind();
            object2 = ByteBuffer.allocate(byteBuffer.remaining() + 1);
            ((ByteBuffer)object2).put(byteBuffer);
            ((ByteBuffer)object2).put((byte)46);
            ((Buffer)object2).rewind();
            object = new byte[((Buffer)object2).remaining()];
            ((ByteBuffer)object2).get((byte[])object);
            InputStream inputStream3 = Streams.of((byte[])object);
            inputStream2 = payload.toInputStream();
            inputStream = new SequenceInputStream(inputStream3, new UncloseableInputStream(inputStream2));
        }
        try {
            DefaultVerifySecureDigestRequest<Key> defaultVerifySecureDigestRequest = new DefaultVerifySecureDigestRequest<Key>(inputStream, provider, null, key, byArray);
            if (!secureDigestAlgorithm.verify(defaultVerifySecureDigestRequest)) {
                object2 = "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.";
                throw new SignatureException((String)object2);
            }
        } catch (WeakKeyException weakKeyException) {
            throw weakKeyException;
        } catch (InvalidKeyException | IllegalArgumentException runtimeException) {
            object2 = secureDigestAlgorithm.getId();
            object = "The parsed JWT indicates it was signed with the '" + (String)object2 + "' signature " + "algorithm, but the provided " + key.getClass().getName() + " key may " + "not be used to verify " + (String)object2 + " signatures.  Because the specified " + "key reflects a specific and expected algorithm, and the JWT does not reflect " + "this algorithm, it is likely that the JWT was not expected and therefore should not be " + "trusted.  Another possibility is that the parser was provided the incorrect " + "signature verification key, but this cannot be assumed for security reasons.";
            throw new UnsupportedJwtException((String)object, runtimeException);
        } finally {
            Streams.reset(inputStream2);
        }
    }

    @Override
    public Jwt<?, ?> parse(Reader reader) {
        Assert.notNull(reader, "Reader cannot be null.");
        return this.parse(reader, Payload.EMPTY);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Jwt<?, ?> parse(Reader reader, Payload payload) {
        boolean bl;
        Object object;
        Object object2;
        Object object3;
        DefaultDecryptionKeyRequest<Key> defaultDecryptionKeyRequest;
        Object object4;
        DefaultDecryptionKeyRequest<Key> defaultDecryptionKeyRequest2;
        Object object5;
        Map<String, ?> map;
        Object object6;
        byte[] byArray;
        Object object7;
        Object object8;
        boolean bl2;
        Object object9;
        Object object10;
        Object object11;
        Header header;
        Assert.notNull(reader, "Compact reader cannot be null.");
        Assert.stateNotNull(payload, "internal error: unencodedPayload is null.");
        Object t = jwtTokenizer.tokenize(reader);
        CharSequence charSequence = t.getProtected();
        if (!Strings.hasText(charSequence)) {
            String string = "Compact JWT strings MUST always have a Base64Url protected header per https://tools.ietf.org/html/rfc7519#section-7.2 (steps 2-4).";
            throw new MalformedJwtException(string);
        }
        byte[] byArray2 = this.decode(charSequence, "protected header");
        Map<String, ?> map2 = this.deserialize(Streams.of(byArray2), "protected header");
        try {
            header = t.createHeader(map2);
        } catch (Exception exception) {
            String string = "Invalid protected header: " + exception.getMessage();
            throw new MalformedJwtException(string, exception);
        }
        String string = Strings.clean(header.getAlgorithm());
        if (!Strings.hasText(string)) {
            String string2 = t instanceof TokenizedJwe ? MISSING_JWE_ALG_MSG : MISSING_JWS_ALG_MSG;
            throw new MalformedJwtException(string2);
        }
        boolean bl3 = Jwts.SIG.NONE.getId().equalsIgnoreCase(string);
        CharSequence charSequence2 = t.getDigest();
        boolean bl4 = Strings.hasText(charSequence2);
        if (bl3) {
            if (t instanceof TokenizedJwe) {
                throw new MalformedJwtException(JWE_NONE_MSG);
            }
            if (!this.unsecured) {
                String string3 = UNSECURED_DISABLED_MSG_PREFIX + header;
                throw new UnsupportedJwtException(string3);
            }
            if (bl4) {
                throw new MalformedJwtException(JWS_NONE_SIG_MISMATCH_MSG);
            }
            if (header.containsKey(DefaultProtectedHeader.CRIT.getId())) {
                String string4 = String.format(CRIT_UNSECURED_MSG, header);
                throw new MalformedJwtException(string4);
            }
        } else if (!bl4) {
            String string5 = t instanceof TokenizedJwe ? MISSING_JWE_DIGEST_MSG_FMT : MISSING_JWS_DIGEST_MSG_FMT;
            String string6 = String.format(string5, string);
            throw new MalformedJwtException(string6);
        }
        if (header instanceof ProtectedHeader) {
            object11 = Collections.nullSafe(((ProtectedHeader)header).getCritical());
            object10 = this.critical;
            String string7 = DefaultJwsHeader.B64.getId();
            if (!payload.isEmpty() && !this.critical.contains(string7)) {
                object10 = new LinkedHashSet<String>(Collections.size(this.critical) + 1);
                object10.add(DefaultJwsHeader.B64.getId());
                object10.addAll(this.critical);
            }
            Iterator iterator2 = object11.iterator();
            while (iterator2.hasNext()) {
                object9 = (String)iterator2.next();
                if (!header.containsKey(object9)) {
                    String string8 = String.format(CRIT_MISSING_MSG, object9, object9, header);
                    throw new MalformedJwtException(string8);
                }
                if (object10.contains(object9)) continue;
                String string9 = String.format(CRIT_UNSUPPORTED_MSG, object9, object9, header);
                throw new UnsupportedJwtException(string9);
            }
        }
        object11 = t.getPayload();
        boolean bl5 = false;
        boolean bl6 = bl2 = !(header instanceof JwsHeader) || ((JwsHeader)header).isPayloadEncoded();
        if (bl2) {
            object9 = this.decode(t.getPayload(), "payload");
            object10 = new Payload((byte[])object9, header.getContentType());
        } else if (Strings.hasText((CharSequence)object11)) {
            object10 = new Payload((CharSequence)object11, header.getContentType());
        } else {
            if (payload.isEmpty()) {
                object9 = String.format(B64_MISSING_PAYLOAD, header);
                throw new SignatureException((String)object9);
            }
            object10 = payload;
        }
        if (t instanceof TokenizedJwe && ((Payload)object10).isEmpty()) {
            object9 = "Compact JWE strings MUST always contain a payload (ciphertext).";
            throw new MalformedJwtException((String)object9);
        }
        object9 = null;
        byte[] byArray3 = null;
        if (t instanceof TokenizedJwe) {
            object8 = (TokenizedJwe)t;
            object7 = Assert.stateIsInstance(JweHeader.class, header, "Not a JweHeader. ");
            byArray = Bytes.EMPTY;
            object6 = object8.getEncryptedKey();
            if (Strings.hasText((CharSequence)object6) && Bytes.isEmpty(byArray = this.decode((CharSequence)object6, "JWE encrypted key"))) {
                String string10 = "Compact JWE string represents an encrypted key, but the key is empty.";
                throw new MalformedJwtException(string10);
            }
            object6 = object8.getIv();
            if (Strings.hasText((CharSequence)object6)) {
                object9 = this.decode((CharSequence)object6, "JWE Initialization Vector");
            }
            if (Bytes.isEmpty(object9)) {
                String string11 = "Compact JWE strings must always contain an Initialization Vector.";
                throw new MalformedJwtException(string11);
            }
            map = StandardCharsets.US_ASCII.encode(Strings.wrap(charSequence));
            object5 = new byte[((Buffer)((Object)map)).remaining()];
            ((ByteBuffer)((Object)map)).get((byte[])object5);
            defaultDecryptionKeyRequest2 = Streams.of((byte[])object5);
            object6 = charSequence2;
            Assert.hasText(object6, "JWE AAD Authentication Tag cannot be null or empty.");
            byArray3 = this.decode((CharSequence)object6, "JWE AAD Authentication Tag");
            if (Bytes.isEmpty(byArray3)) {
                String string12 = "Compact JWE strings must always contain an AAD Authentication Tag.";
                throw new MalformedJwtException(string12);
            }
            String string13 = object7.getEncryptionAlgorithm();
            if (!Strings.hasText(string13)) {
                throw new MalformedJwtException(MISSING_ENC_MSG);
            }
            AeadAlgorithm aeadAlgorithm = this.encAlgs.apply((JweHeader)object7);
            Assert.stateNotNull(aeadAlgorithm, "JWE Encryption Algorithm cannot be null.");
            object4 = this.keyAlgs.apply((JweHeader)object7);
            Assert.stateNotNull(object4, "JWE Key Algorithm cannot be null.");
            Key key = this.keyLocator.locate((Header)object7);
            if (key == null) {
                String string14 = "Cannot decrypt JWE payload: unable to locate key for JWE with header: " + object7;
                throw new UnsupportedJwtException(string14);
            }
            if (key instanceof PublicKey) {
                throw new InvalidKeyException(PUB_KEY_DECRYPT_MSG);
            }
            Provider provider = ProviderKey.getProvider(key, this.provider);
            defaultDecryptionKeyRequest = new DefaultDecryptionKeyRequest<Key>(byArray, provider, null, (JweHeader)object7, aeadAlgorithm, key = ProviderKey.getKey(key));
            object3 = object4.getDecryptionKey(defaultDecryptionKeyRequest);
            if (object3 == null) {
                String string15 = "The '" + object4.getId() + "' JWE key algorithm did not return a decryption key. " + "Unable to perform '" + aeadAlgorithm.getId() + "' decryption.";
                throw new IllegalStateException(string15);
            }
            object2 = ((Payload)object10).toInputStream();
            object = new ByteArrayOutputStream(8192);
            DefaultDecryptAeadRequest defaultDecryptAeadRequest = new DefaultDecryptAeadRequest((InputStream)object2, (SecretKey)object3, (InputStream)((Object)defaultDecryptionKeyRequest2), (byte[])object9, byArray3);
            aeadAlgorithm.decrypt(defaultDecryptAeadRequest, (OutputStream)object);
            object10 = new Payload(((ByteArrayOutputStream)object).toByteArray(), header.getContentType());
            bl5 = true;
        } else if (bl4 && this.signingKeyResolver == null) {
            object8 = Assert.stateIsInstance(JwsHeader.class, header, "Not a JwsHeader. ");
            this.verifySignature((TokenizedJwt)t, (JwsHeader)object8, string, new LocatingKeyResolver(this.keyLocator), null, (Payload)object10);
            bl5 = true;
        }
        object8 = this.zipAlgs.apply(header);
        if (object8 != null) {
            if (!bl5) {
                if (!bl2) {
                    object7 = String.format(B64_DECOMPRESSION_MSG, object8.getId());
                    throw new UnsupportedJwtException((String)object7);
                }
                if (!this.unsecuredDecompression) {
                    object7 = String.format(UNPROTECTED_DECOMPRESSION_MSG, object8.getId());
                    throw new UnsupportedJwtException((String)object7);
                }
            }
            object10 = ((Payload)object10).decompress((CompressionAlgorithm)object8);
        }
        object7 = null;
        byArray = ((Payload)object10).getBytes();
        if (((Payload)object10).isConsumable()) {
            object6 = ((Payload)object10).toInputStream();
            if (!DefaultJwtParser.hasContentType(header)) {
                map = null;
                try {
                    if (!((InputStream)object6).markSupported()) {
                        object6 = new BufferedInputStream((InputStream)object6);
                        ((InputStream)object6).mark(0);
                    }
                    map = this.deserialize(new UncloseableInputStream((InputStream)object6), "claims");
                } catch (MalformedJwtException | DeserializationException jwtException) {
                } finally {
                    Streams.reset((InputStream)object6);
                }
                if (map != null) {
                    try {
                        object7 = new DefaultClaims(map);
                    } catch (Throwable throwable) {
                        defaultDecryptionKeyRequest2 = "Invalid claims: " + throwable.getMessage();
                        throw new MalformedJwtException((String)((Object)defaultDecryptionKeyRequest2));
                    }
                }
            }
            if (object7 == null) {
                byArray = Streams.bytes((InputStream)object6, "Unable to convert payload to byte array.");
            }
        }
        Map<String, ?> map3 = map = object7 != null ? object7 : (Map<String, ?>)byArray;
        if (header instanceof JweHeader) {
            object6 = new DefaultJwe<Object>((JweHeader)header, (Object)map, (byte[])object9, byArray3);
        } else if (bl4) {
            object5 = Assert.isInstanceOf(JwsHeader.class, header, "JwsHeader required.");
            object6 = new DefaultJws((JwsHeader)object5, map, charSequence2.toString());
        } else {
            object6 = new DefaultJwt<Header, Object>(header, map);
        }
        if (bl4 && this.signingKeyResolver != null) {
            object5 = Assert.stateIsInstance(JwsHeader.class, header, "Not a JwsHeader. ");
            this.verifySignature((TokenizedJwt)t, (JwsHeader)object5, string, this.signingKeyResolver, (Claims)object7, (Payload)object10);
        }
        boolean bl7 = bl = this.allowedClockSkewMillis > 0L;
        if (object7 != null) {
            Date date;
            defaultDecryptionKeyRequest2 = this.clock.now();
            long l = ((Date)((Object)defaultDecryptionKeyRequest2)).getTime();
            object4 = object7.getExpiration();
            if (object4 != null) {
                long l2 = l - this.allowedClockSkewMillis;
                DefaultDecryptionKeyRequest<Key> defaultDecryptionKeyRequest3 = defaultDecryptionKeyRequest = bl ? new Date(l2) : defaultDecryptionKeyRequest2;
                if (((Date)((Object)defaultDecryptionKeyRequest)).after((Date)object4)) {
                    object3 = DateFormats.formatIso8601((Date)object4, true);
                    object2 = DateFormats.formatIso8601((Date)((Object)defaultDecryptionKeyRequest2), true);
                    long l3 = l - ((Date)object4).getTime();
                    String string16 = "JWT expired " + l3 + " milliseconds ago at " + (String)object3 + ". " + "Current time: " + (String)object2 + ". Allowed clock skew: " + this.allowedClockSkewMillis + " milliseconds.";
                    throw new ExpiredJwtException(header, (Claims)object7, string16);
                }
            }
            if ((date = object7.getNotBefore()) != null) {
                long l4 = l + this.allowedClockSkewMillis;
                Object object12 = object3 = bl ? new Date(l4) : defaultDecryptionKeyRequest2;
                if (((Date)object3).before(date)) {
                    object2 = DateFormats.formatIso8601(date, true);
                    object = DateFormats.formatIso8601((Date)((Object)defaultDecryptionKeyRequest2), true);
                    long l5 = date.getTime() - l;
                    String string17 = "JWT early by " + l5 + " milliseconds before " + (String)object2 + ". Current time: " + (String)object + ". Allowed clock skew: " + this.allowedClockSkewMillis + " milliseconds.";
                    throw new PrematureJwtException(header, (Claims)object7, string17);
                }
            }
            this.validateExpectedClaims(header, (Claims)object7);
        }
        return object6;
    }

    private static Object normalize(Object object) {
        if (object instanceof Integer) {
            object = ((Integer)object).longValue();
        }
        return object;
    }

    private void validateExpectedClaims(Header header, Claims claims) {
        Claims claims2 = (Claims)this.expectedClaims.build();
        for (String string : claims2.keySet()) {
            Object object;
            Object object2;
            Object object3 = DefaultJwtParser.normalize(claims2.get(string));
            Object object4 = DefaultJwtParser.normalize(claims.get(string));
            if (object3 instanceof Date) {
                try {
                    object4 = claims.get(string, Date.class);
                } catch (Exception exception) {
                    object2 = "JWT Claim '" + string + "' was expected to be a Date, but its value " + "cannot be converted to a Date using current heuristics.  Value: " + object4;
                    throw new IncorrectClaimException(header, claims, string, object3, (String)object2);
                }
            }
            if (object4 == null) {
                boolean bl = object3 instanceof Collection;
                object2 = "Missing '" + string + "' claim. Expected value";
                object2 = bl ? (String)object2 + "s: " + object3 : (String)object2 + ": " + object3;
                throw new MissingClaimException(header, claims, string, object3, (String)object2);
            }
            if (object3 instanceof Collection) {
                object = (Collection)object3;
                object2 = object4 instanceof Collection ? (Set<Object>)object4 : Collections.setOf(object4);
                Iterator iterator2 = object.iterator();
                while (iterator2.hasNext()) {
                    Object e = iterator2.next();
                    if (Collections.contains(object2.iterator(), e)) continue;
                    String string2 = String.format(MISSING_EXPECTED_CLAIM_VALUE_MESSAGE_TEMPLATE, e, string, object2);
                    throw new IncorrectClaimException(header, claims, string, object3, string2);
                }
                continue;
            }
            if (object3.equals(object4)) continue;
            object = String.format(INCORRECT_EXPECTED_CLAIM_MESSAGE_TEMPLATE, string, object3, object4);
            throw new IncorrectClaimException(header, claims, string, object3, (String)object);
        }
    }

    @Override
    public <T> T parse(CharSequence charSequence, JwtHandler<T> jwtHandler) {
        return this.parse(charSequence, Payload.EMPTY).accept(jwtHandler);
    }

    private Jwt<?, ?> parse(CharSequence charSequence, Payload payload) {
        Assert.hasText(charSequence, "JWT String argument cannot be null or empty.");
        return this.parse(new CharSequenceReader(charSequence), payload);
    }

    @Override
    public Jwt<Header, byte[]> parseContentJwt(CharSequence charSequence) {
        return ((Jwt)this.parse(charSequence)).accept(Jwt.UNSECURED_CONTENT);
    }

    @Override
    public Jwt<Header, Claims> parseClaimsJwt(CharSequence charSequence) {
        return ((Jwt)this.parse(charSequence)).accept(Jwt.UNSECURED_CLAIMS);
    }

    @Override
    public Jws<byte[]> parseContentJws(CharSequence charSequence) {
        return this.parseSignedContent(charSequence);
    }

    @Override
    public Jws<Claims> parseClaimsJws(CharSequence charSequence) {
        return this.parseSignedClaims(charSequence);
    }

    @Override
    public Jwt<Header, byte[]> parseUnsecuredContent(CharSequence charSequence) throws JwtException, IllegalArgumentException {
        return ((Jwt)this.parse(charSequence)).accept(Jwt.UNSECURED_CONTENT);
    }

    @Override
    public Jwt<Header, Claims> parseUnsecuredClaims(CharSequence charSequence) throws JwtException, IllegalArgumentException {
        return ((Jwt)this.parse(charSequence)).accept(Jwt.UNSECURED_CLAIMS);
    }

    @Override
    public Jws<byte[]> parseSignedContent(CharSequence charSequence) {
        return ((Jwt)this.parse(charSequence)).accept(Jws.CONTENT);
    }

    private Jws<byte[]> parseSignedContent(CharSequence charSequence, Payload payload) {
        return this.parse(charSequence, payload).accept(Jws.CONTENT);
    }

    @Override
    public Jws<Claims> parseSignedClaims(CharSequence charSequence) {
        return ((Jwt)this.parse(charSequence)).accept(Jws.CLAIMS);
    }

    private Jws<Claims> parseSignedClaims(CharSequence charSequence, Payload payload) {
        payload.setClaimsExpected(false);
        return this.parse(charSequence, payload).accept(Jws.CLAIMS);
    }

    @Override
    public Jws<byte[]> parseSignedContent(CharSequence charSequence, byte[] byArray) {
        Assert.notEmpty(byArray, "unencodedPayload argument cannot be null or empty.");
        return this.parseSignedContent(charSequence, new Payload(byArray, null));
    }

    private static Payload payloadFor(InputStream inputStream) {
        if (inputStream instanceof BytesInputStream) {
            byte[] byArray = Streams.bytes(inputStream, "Unable to obtain payload InputStream bytes.");
            return new Payload(byArray, null);
        }
        return new Payload(inputStream, null);
    }

    @Override
    public Jws<byte[]> parseSignedContent(CharSequence charSequence, InputStream inputStream) {
        Assert.notNull(inputStream, "unencodedPayload InputStream cannot be null.");
        return this.parseSignedContent(charSequence, DefaultJwtParser.payloadFor(inputStream));
    }

    @Override
    public Jws<Claims> parseSignedClaims(CharSequence charSequence, byte[] byArray) {
        Assert.notEmpty(byArray, "unencodedPayload argument cannot be null or empty.");
        return this.parseSignedClaims(charSequence, new Payload(byArray, null));
    }

    @Override
    public Jws<Claims> parseSignedClaims(CharSequence charSequence, InputStream inputStream) {
        Assert.notNull(inputStream, "unencodedPayload InputStream cannot be null.");
        byte[] byArray = Streams.bytes(inputStream, "Unable to obtain Claims bytes from unencodedPayload InputStream");
        return this.parseSignedClaims(charSequence, new Payload(byArray, null));
    }

    @Override
    public Jwe<byte[]> parseEncryptedContent(CharSequence charSequence) throws JwtException {
        return ((Jwt)this.parse(charSequence)).accept(Jwe.CONTENT);
    }

    @Override
    public Jwe<Claims> parseEncryptedClaims(CharSequence charSequence) throws JwtException {
        return ((Jwt)this.parse(charSequence)).accept(Jwe.CLAIMS);
    }

    protected byte[] decode(CharSequence charSequence, String string) {
        try {
            InputStream inputStream = this.decoder.decode(Streams.of(Strings.utf8(charSequence)));
            return Streams.bytes(inputStream, "Unable to Base64Url-decode input.");
        } catch (Throwable throwable) {
            String string2 = "payload".equals(string) ? "<redacted>" : charSequence.toString();
            String string3 = "Invalid Base64Url " + string + ": " + string2;
            throw new MalformedJwtException(string3, throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Map<String, ?> deserialize(InputStream inputStream, String string) {
        Map<String, ?> map;
        try {
            Reader reader = Streams.reader(inputStream);
            JsonObjectDeserializer jsonObjectDeserializer = new JsonObjectDeserializer(this.deserializer, string);
            map = jsonObjectDeserializer.apply(reader);
        } catch (Throwable throwable) {
            Objects.nullSafeClose(inputStream);
            throw throwable;
        }
        Objects.nullSafeClose(inputStream);
        return map;
    }

    @Override
    public Object parse(Reader reader) {
        return this.parse(reader);
    }

    @Override
    public Jwt parse(CharSequence charSequence) throws ExpiredJwtException, MalformedJwtException, SignatureException, SecurityException, IllegalArgumentException {
        return (Jwt)super.parse(charSequence);
    }
}

