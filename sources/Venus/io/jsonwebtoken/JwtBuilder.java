/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.ClaimsMutator;
import io.jsonwebtoken.JweHeaderMutator;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.CompressionAlgorithm;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.Serializer;
import io.jsonwebtoken.lang.Conjunctor;
import io.jsonwebtoken.lang.MapMutator;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.X509Builder;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;

public interface JwtBuilder
extends ClaimsMutator<JwtBuilder> {
    public JwtBuilder provider(Provider var1);

    public JwtBuilder random(SecureRandom var1);

    public BuilderHeader header();

    @Deprecated
    public JwtBuilder setHeader(Map<String, ?> var1);

    @Deprecated
    public JwtBuilder setHeaderParams(Map<String, ?> var1);

    @Deprecated
    public JwtBuilder setHeaderParam(String var1, Object var2);

    @Deprecated
    public JwtBuilder setPayload(String var1);

    public JwtBuilder content(String var1);

    public JwtBuilder content(byte[] var1);

    public JwtBuilder content(InputStream var1);

    public JwtBuilder content(String var1, String var2) throws IllegalArgumentException;

    public JwtBuilder content(byte[] var1, String var2) throws IllegalArgumentException;

    public JwtBuilder content(InputStream var1, String var2) throws IllegalArgumentException;

    public BuilderClaims claims();

    @Deprecated
    public JwtBuilder setClaims(Map<String, ?> var1);

    @Deprecated
    public JwtBuilder addClaims(Map<String, ?> var1);

    public JwtBuilder claim(String var1, Object var2);

    public JwtBuilder claims(Map<String, ?> var1);

    @Override
    public JwtBuilder issuer(String var1);

    @Override
    public JwtBuilder subject(String var1);

    @Override
    public JwtBuilder expiration(Date var1);

    @Override
    public JwtBuilder notBefore(Date var1);

    @Override
    public JwtBuilder issuedAt(Date var1);

    @Override
    public JwtBuilder id(String var1);

    public JwtBuilder signWith(Key var1) throws InvalidKeyException;

    @Deprecated
    public JwtBuilder signWith(SignatureAlgorithm var1, byte[] var2) throws InvalidKeyException;

    @Deprecated
    public JwtBuilder signWith(SignatureAlgorithm var1, String var2) throws InvalidKeyException;

    @Deprecated
    public JwtBuilder signWith(SignatureAlgorithm var1, Key var2) throws InvalidKeyException;

    @Deprecated
    public JwtBuilder signWith(Key var1, SignatureAlgorithm var2) throws InvalidKeyException;

    public <K extends Key> JwtBuilder signWith(K var1, SecureDigestAlgorithm<? super K, ?> var2) throws InvalidKeyException;

    public JwtBuilder encryptWith(SecretKey var1, AeadAlgorithm var2);

    public <K extends Key> JwtBuilder encryptWith(K var1, KeyAlgorithm<? super K, ?> var2, AeadAlgorithm var3);

    public JwtBuilder compressWith(CompressionAlgorithm var1);

    @Deprecated
    public JwtBuilder base64UrlEncodeWith(Encoder<byte[], String> var1);

    public JwtBuilder b64Url(Encoder<OutputStream, OutputStream> var1);

    public JwtBuilder encodePayload(boolean var1);

    @Deprecated
    public JwtBuilder serializeToJsonWith(Serializer<Map<String, ?>> var1);

    public JwtBuilder json(Serializer<Map<String, ?>> var1);

    public String compact();

    public static interface BuilderHeader
    extends JweHeaderMutator<BuilderHeader>,
    X509Builder<BuilderHeader>,
    Conjunctor<JwtBuilder> {
    }

    public static interface BuilderClaims
    extends MapMutator<String, Object, BuilderClaims>,
    ClaimsMutator<BuilderClaims>,
    Conjunctor<JwtBuilder> {
    }
}

