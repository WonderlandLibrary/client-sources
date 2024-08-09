/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.EcPrivateJwkBuilder;
import io.jsonwebtoken.security.EcPublicJwkBuilder;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkBuilder;
import io.jsonwebtoken.security.OctetPrivateJwkBuilder;
import io.jsonwebtoken.security.OctetPublicJwkBuilder;
import io.jsonwebtoken.security.PrivateJwkBuilder;
import io.jsonwebtoken.security.PublicJwkBuilder;
import io.jsonwebtoken.security.RsaPrivateJwkBuilder;
import io.jsonwebtoken.security.RsaPublicJwkBuilder;
import io.jsonwebtoken.security.SecretJwkBuilder;
import io.jsonwebtoken.security.UnsupportedKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import javax.crypto.SecretKey;

public interface DynamicJwkBuilder<K extends Key, J extends Jwk<K>>
extends JwkBuilder<K, J, DynamicJwkBuilder<K, J>> {
    public <A extends PublicKey, B extends PrivateKey> PublicJwkBuilder<A, B, ?, ?, ?, ?> chain(List<X509Certificate> var1) throws UnsupportedKeyException;

    public SecretJwkBuilder key(SecretKey var1);

    public RsaPublicJwkBuilder key(RSAPublicKey var1);

    public RsaPrivateJwkBuilder key(RSAPrivateKey var1);

    public EcPublicJwkBuilder key(ECPublicKey var1);

    public EcPrivateJwkBuilder key(ECPrivateKey var1);

    public <A extends PublicKey, B extends PrivateKey> PublicJwkBuilder<A, B, ?, ?, ?, ?> key(A var1) throws UnsupportedKeyException;

    public <A extends PublicKey, B extends PrivateKey> PrivateJwkBuilder<B, A, ?, ?, ?> key(B var1) throws UnsupportedKeyException;

    public <A extends PublicKey, B extends PrivateKey> PrivateJwkBuilder<B, A, ?, ?, ?> keyPair(KeyPair var1) throws UnsupportedKeyException;

    public <A extends PublicKey, B extends PrivateKey> OctetPublicJwkBuilder<A, B> octetKey(A var1);

    public <A extends PrivateKey, B extends PublicKey> OctetPrivateJwkBuilder<A, B> octetKey(A var1);

    public <A extends PublicKey, B extends PrivateKey> OctetPublicJwkBuilder<A, B> octetChain(List<X509Certificate> var1);

    public <A extends PrivateKey, B extends PublicKey> OctetPrivateJwkBuilder<A, B> octetKeyPair(KeyPair var1);

    public EcPublicJwkBuilder ecChain(List<X509Certificate> var1);

    public EcPrivateJwkBuilder ecKeyPair(KeyPair var1) throws IllegalArgumentException;

    public RsaPublicJwkBuilder rsaChain(List<X509Certificate> var1);

    public RsaPrivateJwkBuilder rsaKeyPair(KeyPair var1) throws IllegalArgumentException;
}

