/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.AbstractAsymmetricJwkBuilder;
import io.jsonwebtoken.impl.security.AbstractJwk;
import io.jsonwebtoken.impl.security.AbstractJwkBuilder;
import io.jsonwebtoken.impl.security.DefaultJwkContext;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.KeyPairs;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.NestedCollection;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.DynamicJwkBuilder;
import io.jsonwebtoken.security.EcPrivateJwkBuilder;
import io.jsonwebtoken.security.EcPublicJwkBuilder;
import io.jsonwebtoken.security.Jwk;
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

public class DefaultDynamicJwkBuilder<K extends Key, J extends Jwk<K>>
extends AbstractJwkBuilder<K, J, DynamicJwkBuilder<K, J>>
implements DynamicJwkBuilder<K, J> {
    public DefaultDynamicJwkBuilder() {
        this(new DefaultJwkContext());
    }

    public DefaultDynamicJwkBuilder(JwkContext<K> jwkContext) {
        super(jwkContext);
    }

    @Override
    public SecretJwkBuilder key(SecretKey secretKey) {
        return new AbstractJwkBuilder.DefaultSecretJwkBuilder(this.newContext(secretKey));
    }

    @Override
    public RsaPublicJwkBuilder key(RSAPublicKey rSAPublicKey) {
        return new AbstractAsymmetricJwkBuilder.DefaultRsaPublicJwkBuilder(this.newContext(rSAPublicKey));
    }

    @Override
    public RsaPrivateJwkBuilder key(RSAPrivateKey rSAPrivateKey) {
        return new AbstractAsymmetricJwkBuilder.DefaultRsaPrivateJwkBuilder(this.newContext(rSAPrivateKey));
    }

    @Override
    public EcPublicJwkBuilder key(ECPublicKey eCPublicKey) {
        return new AbstractAsymmetricJwkBuilder.DefaultEcPublicJwkBuilder(this.newContext(eCPublicKey));
    }

    @Override
    public EcPrivateJwkBuilder key(ECPrivateKey eCPrivateKey) {
        return new AbstractAsymmetricJwkBuilder.DefaultEcPrivateJwkBuilder(this.newContext(eCPrivateKey));
    }

    private static UnsupportedKeyException unsupportedKey(Key key, Exception exception) {
        String string = "There is no builder that supports specified key [" + KeysBridge.toString(key) + "].";
        return new UnsupportedKeyException(string, exception);
    }

    @Override
    public <A extends PublicKey, B extends PrivateKey> PublicJwkBuilder<A, B, ?, ?, ?, ?> key(A a) {
        if (a instanceof RSAPublicKey) {
            return this.key((RSAPublicKey)a);
        }
        if (a instanceof ECPublicKey) {
            return this.key((ECPublicKey)a);
        }
        try {
            return this.octetKey(a);
        } catch (Exception exception) {
            throw DefaultDynamicJwkBuilder.unsupportedKey(a, exception);
        }
    }

    @Override
    public <A extends PublicKey, B extends PrivateKey> PrivateJwkBuilder<B, A, ?, ?, ?> key(B b) {
        Assert.notNull(b, "Key cannot be null.");
        if (b instanceof RSAPrivateKey) {
            return this.key((RSAPrivateKey)b);
        }
        if (b instanceof ECPrivateKey) {
            return this.key((ECPrivateKey)b);
        }
        try {
            return this.octetKey(b);
        } catch (Exception exception) {
            throw DefaultDynamicJwkBuilder.unsupportedKey(b, exception);
        }
    }

    @Override
    public <A extends PublicKey, B extends PrivateKey> OctetPublicJwkBuilder<A, B> octetKey(A a) {
        return new AbstractAsymmetricJwkBuilder.DefaultOctetPublicJwkBuilder(this.newContext(a));
    }

    @Override
    public <A extends PrivateKey, B extends PublicKey> OctetPrivateJwkBuilder<A, B> octetKey(A a) {
        return new AbstractAsymmetricJwkBuilder.DefaultOctetPrivateJwkBuilder(this.newContext(a));
    }

    @Override
    public <A extends PublicKey, B extends PrivateKey> PublicJwkBuilder<A, B, ?, ?, ?, ?> chain(List<X509Certificate> list) throws UnsupportedKeyException {
        Assert.notEmpty(list, "chain cannot be null or empty.");
        X509Certificate x509Certificate = Assert.notNull(list.get(0), "The first X509Certificate cannot be null.");
        PublicKey publicKey = Assert.notNull(x509Certificate.getPublicKey(), "The first X509Certificate's PublicKey cannot be null.");
        return (PublicJwkBuilder)this.key(publicKey).x509Chain(list);
    }

    @Override
    public RsaPublicJwkBuilder rsaChain(List<X509Certificate> list) {
        Assert.notEmpty(list, "X509Certificate chain cannot be empty.");
        X509Certificate x509Certificate = list.get(0);
        PublicKey publicKey = x509Certificate.getPublicKey();
        RSAPublicKey rSAPublicKey = KeyPairs.assertKey(publicKey, RSAPublicKey.class, "The first X509Certificate's ");
        return (RsaPublicJwkBuilder)this.key(rSAPublicKey).x509Chain(list);
    }

    @Override
    public EcPublicJwkBuilder ecChain(List<X509Certificate> list) {
        Assert.notEmpty(list, "X509Certificate chain cannot be empty.");
        X509Certificate x509Certificate = list.get(0);
        PublicKey publicKey = x509Certificate.getPublicKey();
        ECPublicKey eCPublicKey = KeyPairs.assertKey(publicKey, ECPublicKey.class, "The first X509Certificate's ");
        return (EcPublicJwkBuilder)this.key(eCPublicKey).x509Chain(list);
    }

    @Override
    public <A extends PrivateKey, B extends PublicKey> OctetPrivateJwkBuilder<A, B> octetKeyPair(KeyPair keyPair) {
        PublicKey publicKey = KeyPairs.getKey(keyPair, PublicKey.class);
        PrivateKey privateKey = KeyPairs.getKey(keyPair, PrivateKey.class);
        EdwardsCurve.assertEdwards(publicKey);
        EdwardsCurve.assertEdwards(privateKey);
        return (OctetPrivateJwkBuilder)this.octetKey(privateKey).publicKey(publicKey);
    }

    @Override
    public <A extends PublicKey, B extends PrivateKey> OctetPublicJwkBuilder<A, B> octetChain(List<X509Certificate> list) {
        Assert.notEmpty(list, "X509Certificate chain cannot be empty.");
        X509Certificate x509Certificate = list.get(0);
        PublicKey publicKey = x509Certificate.getPublicKey();
        Assert.notNull(publicKey, "The first X509Certificate's PublicKey cannot be null.");
        EdwardsCurve.assertEdwards(publicKey);
        return (OctetPublicJwkBuilder)this.octetKey(publicKey).x509Chain(list);
    }

    @Override
    public RsaPrivateJwkBuilder rsaKeyPair(KeyPair keyPair) {
        RSAPublicKey rSAPublicKey = KeyPairs.getKey(keyPair, RSAPublicKey.class);
        RSAPrivateKey rSAPrivateKey = KeyPairs.getKey(keyPair, RSAPrivateKey.class);
        return (RsaPrivateJwkBuilder)this.key(rSAPrivateKey).publicKey(rSAPublicKey);
    }

    @Override
    public EcPrivateJwkBuilder ecKeyPair(KeyPair keyPair) {
        ECPublicKey eCPublicKey = KeyPairs.getKey(keyPair, ECPublicKey.class);
        ECPrivateKey eCPrivateKey = KeyPairs.getKey(keyPair, ECPrivateKey.class);
        return (EcPrivateJwkBuilder)this.key(eCPrivateKey).publicKey(eCPublicKey);
    }

    @Override
    public <A extends PublicKey, B extends PrivateKey> PrivateJwkBuilder<B, A, ?, ?, ?> keyPair(KeyPair keyPair) throws UnsupportedKeyException {
        PublicKey publicKey = KeyPairs.getKey(keyPair, PublicKey.class);
        PrivateKey privateKey = KeyPairs.getKey(keyPair, PrivateKey.class);
        return this.key(privateKey).publicKey(publicKey);
    }

    @Override
    public J build() {
        if (Strings.hasText(((JwkContext)this.DELEGATE).get(AbstractJwk.KTY))) {
            this.setDelegate(this.jwkFactory.newContext((JwkContext)this.DELEGATE, ((JwkContext)this.DELEGATE).getKey()));
        }
        return (J)super.build();
    }

    @Override
    public NestedCollection operations() {
        return super.operations();
    }

    @Override
    public Object build() {
        return this.build();
    }
}

