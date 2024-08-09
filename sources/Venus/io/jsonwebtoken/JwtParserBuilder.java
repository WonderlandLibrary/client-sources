/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Clock;
import io.jsonwebtoken.CompressionCodecResolver;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Locator;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.io.CompressionAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.lang.Builder;
import io.jsonwebtoken.lang.NestedCollection;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import java.io.InputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;

public interface JwtParserBuilder
extends Builder<JwtParser> {
    public JwtParserBuilder unsecured();

    public JwtParserBuilder unsecuredDecompression();

    public NestedCollection<String, JwtParserBuilder> critical();

    public JwtParserBuilder provider(Provider var1);

    public JwtParserBuilder requireId(String var1);

    public JwtParserBuilder requireSubject(String var1);

    public JwtParserBuilder requireAudience(String var1);

    public JwtParserBuilder requireIssuer(String var1);

    public JwtParserBuilder requireIssuedAt(Date var1);

    public JwtParserBuilder requireExpiration(Date var1);

    public JwtParserBuilder requireNotBefore(Date var1);

    public JwtParserBuilder require(String var1, Object var2);

    @Deprecated
    public JwtParserBuilder setClock(Clock var1);

    public JwtParserBuilder clock(Clock var1);

    @Deprecated
    public JwtParserBuilder setAllowedClockSkewSeconds(long var1) throws IllegalArgumentException;

    public JwtParserBuilder clockSkewSeconds(long var1) throws IllegalArgumentException;

    @Deprecated
    public JwtParserBuilder setSigningKey(byte[] var1);

    @Deprecated
    public JwtParserBuilder setSigningKey(String var1);

    @Deprecated
    public JwtParserBuilder setSigningKey(Key var1);

    public JwtParserBuilder verifyWith(SecretKey var1);

    public JwtParserBuilder verifyWith(PublicKey var1);

    public JwtParserBuilder decryptWith(SecretKey var1);

    public JwtParserBuilder decryptWith(PrivateKey var1);

    public JwtParserBuilder keyLocator(Locator<Key> var1);

    @Deprecated
    public JwtParserBuilder setSigningKeyResolver(SigningKeyResolver var1);

    public NestedCollection<AeadAlgorithm, JwtParserBuilder> enc();

    public NestedCollection<KeyAlgorithm<?, ?>, JwtParserBuilder> key();

    public NestedCollection<SecureDigestAlgorithm<?, ?>, JwtParserBuilder> sig();

    public NestedCollection<CompressionAlgorithm, JwtParserBuilder> zip();

    @Deprecated
    public JwtParserBuilder setCompressionCodecResolver(CompressionCodecResolver var1);

    @Deprecated
    public JwtParserBuilder base64UrlDecodeWith(Decoder<CharSequence, byte[]> var1);

    public JwtParserBuilder b64Url(Decoder<InputStream, InputStream> var1);

    @Deprecated
    public JwtParserBuilder deserializeJsonWith(Deserializer<Map<String, ?>> var1);

    public JwtParserBuilder json(Deserializer<Map<String, ?>> var1);

    @Override
    public JwtParser build();
}

