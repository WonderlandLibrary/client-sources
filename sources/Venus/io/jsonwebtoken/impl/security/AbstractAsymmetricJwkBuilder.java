/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.security.AbstractJwkBuilder;
import io.jsonwebtoken.impl.security.DefaultKeyUseStrategy;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.KeyUseStrategy;
import io.jsonwebtoken.impl.security.X509BuilderSupport;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.AsymmetricJwk;
import io.jsonwebtoken.security.AsymmetricJwkBuilder;
import io.jsonwebtoken.security.EcPrivateJwk;
import io.jsonwebtoken.security.EcPrivateJwkBuilder;
import io.jsonwebtoken.security.EcPublicJwk;
import io.jsonwebtoken.security.EcPublicJwkBuilder;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.MalformedKeyException;
import io.jsonwebtoken.security.OctetPrivateJwk;
import io.jsonwebtoken.security.OctetPrivateJwkBuilder;
import io.jsonwebtoken.security.OctetPublicJwk;
import io.jsonwebtoken.security.OctetPublicJwkBuilder;
import io.jsonwebtoken.security.PrivateJwk;
import io.jsonwebtoken.security.PrivateJwkBuilder;
import io.jsonwebtoken.security.PublicJwk;
import io.jsonwebtoken.security.PublicJwkBuilder;
import io.jsonwebtoken.security.RsaPrivateJwk;
import io.jsonwebtoken.security.RsaPrivateJwkBuilder;
import io.jsonwebtoken.security.RsaPublicJwk;
import io.jsonwebtoken.security.RsaPublicJwkBuilder;
import io.jsonwebtoken.security.X509Builder;
import io.jsonwebtoken.security.X509Mutator;
import java.net.URI;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
abstract class AbstractAsymmetricJwkBuilder<K extends Key, J extends AsymmetricJwk<K>, T extends AsymmetricJwkBuilder<K, J, T>>
extends AbstractJwkBuilder<K, J, T>
implements AsymmetricJwkBuilder<K, J, T> {
    protected Boolean applyX509KeyUse = null;
    private KeyUseStrategy keyUseStrategy = DefaultKeyUseStrategy.INSTANCE;
    private final X509BuilderSupport x509;

    public AbstractAsymmetricJwkBuilder(JwkContext<K> jwkContext) {
        super(jwkContext);
        ParameterMap parameterMap = Assert.isInstanceOf(ParameterMap.class, this.DELEGATE);
        this.x509 = new X509BuilderSupport(parameterMap, MalformedKeyException.class);
    }

    AbstractAsymmetricJwkBuilder(AbstractAsymmetricJwkBuilder<?, ?, ?> abstractAsymmetricJwkBuilder, JwkContext<K> jwkContext) {
        this(jwkContext);
        this.applyX509KeyUse = abstractAsymmetricJwkBuilder.applyX509KeyUse;
        this.keyUseStrategy = abstractAsymmetricJwkBuilder.keyUseStrategy;
    }

    @Override
    public T publicKeyUse(String string) {
        Assert.hasText(string, "publicKeyUse cannot be null or empty.");
        ((JwkContext)this.DELEGATE).setPublicKeyUse(string);
        return (T)((AsymmetricJwkBuilder)this.self());
    }

    @Override
    public T x509Chain(List<X509Certificate> list) {
        Assert.notEmpty(list, "X509Certificate chain cannot be null or empty.");
        this.x509.x509Chain((List)list);
        return (T)((AsymmetricJwkBuilder)this.self());
    }

    @Override
    public T x509Url(URI uRI) {
        Assert.notNull(uRI, "X509Url cannot be null.");
        this.x509.x509Url(uRI);
        return (T)((AsymmetricJwkBuilder)this.self());
    }

    @Override
    public T x509Sha1Thumbprint(byte[] byArray) {
        this.x509.x509Sha1Thumbprint(byArray);
        return (T)((AsymmetricJwkBuilder)this.self());
    }

    @Override
    public T x509Sha256Thumbprint(byte[] byArray) {
        this.x509.x509Sha256Thumbprint(byArray);
        return (T)((AsymmetricJwkBuilder)this.self());
    }

    @Override
    public T x509Sha1Thumbprint(boolean bl) {
        this.x509.x509Sha1Thumbprint(bl);
        return (T)((AsymmetricJwkBuilder)this.self());
    }

    @Override
    public T x509Sha256Thumbprint(boolean bl) {
        this.x509.x509Sha256Thumbprint(bl);
        return (T)((AsymmetricJwkBuilder)this.self());
    }

    @Override
    public J build() {
        this.x509.apply();
        return (J)((AsymmetricJwk)super.build());
    }

    @Override
    public Jwk build() {
        return this.build();
    }

    @Override
    public Object build() {
        return this.build();
    }

    @Override
    public X509Builder x509Sha256Thumbprint(boolean bl) {
        return this.x509Sha256Thumbprint(bl);
    }

    @Override
    public X509Builder x509Sha1Thumbprint(boolean bl) {
        return this.x509Sha1Thumbprint(bl);
    }

    @Override
    public X509Mutator x509Sha256Thumbprint(byte[] byArray) {
        return this.x509Sha256Thumbprint(byArray);
    }

    @Override
    public X509Mutator x509Sha1Thumbprint(byte[] byArray) {
        return this.x509Sha1Thumbprint(byArray);
    }

    @Override
    public X509Mutator x509Chain(List list) {
        return this.x509Chain(list);
    }

    @Override
    public X509Mutator x509Url(URI uRI) {
        return this.x509Url(uRI);
    }

    static Map access$000(AbstractAsymmetricJwkBuilder abstractAsymmetricJwkBuilder) {
        return abstractAsymmetricJwkBuilder.DELEGATE;
    }

    static class DefaultOctetPrivateJwkBuilder<A extends PrivateKey, B extends PublicKey>
    extends DefaultPrivateJwkBuilder<A, B, OctetPublicJwk<B>, OctetPrivateJwk<A, B>, OctetPrivateJwkBuilder<A, B>>
    implements OctetPrivateJwkBuilder<A, B> {
        DefaultOctetPrivateJwkBuilder(JwkContext<A> jwkContext) {
            super(jwkContext);
            EdwardsCurve.assertEdwards(jwkContext.getKey());
        }

        DefaultOctetPrivateJwkBuilder(DefaultOctetPublicJwkBuilder<B, A> defaultOctetPublicJwkBuilder, JwkContext<A> jwkContext) {
            super(defaultOctetPublicJwkBuilder, jwkContext);
            EdwardsCurve.assertEdwards(jwkContext.getKey());
            EdwardsCurve.assertEdwards(jwkContext.getPublicKey());
        }
    }

    static class DefaultEcPrivateJwkBuilder
    extends DefaultPrivateJwkBuilder<ECPrivateKey, ECPublicKey, EcPublicJwk, EcPrivateJwk, EcPrivateJwkBuilder>
    implements EcPrivateJwkBuilder {
        DefaultEcPrivateJwkBuilder(JwkContext<ECPrivateKey> jwkContext) {
            super(jwkContext);
        }

        DefaultEcPrivateJwkBuilder(DefaultEcPublicJwkBuilder defaultEcPublicJwkBuilder, JwkContext<ECPrivateKey> jwkContext) {
            super(defaultEcPublicJwkBuilder, jwkContext);
        }
    }

    static class DefaultRsaPrivateJwkBuilder
    extends DefaultPrivateJwkBuilder<RSAPrivateKey, RSAPublicKey, RsaPublicJwk, RsaPrivateJwk, RsaPrivateJwkBuilder>
    implements RsaPrivateJwkBuilder {
        DefaultRsaPrivateJwkBuilder(JwkContext<RSAPrivateKey> jwkContext) {
            super(jwkContext);
        }

        DefaultRsaPrivateJwkBuilder(DefaultRsaPublicJwkBuilder defaultRsaPublicJwkBuilder, JwkContext<RSAPrivateKey> jwkContext) {
            super(defaultRsaPublicJwkBuilder, jwkContext);
        }
    }

    static class DefaultOctetPublicJwkBuilder<A extends PublicKey, B extends PrivateKey>
    extends DefaultPublicJwkBuilder<A, B, OctetPublicJwk<A>, OctetPrivateJwk<B, A>, OctetPrivateJwkBuilder<B, A>, OctetPublicJwkBuilder<A, B>>
    implements OctetPublicJwkBuilder<A, B> {
        DefaultOctetPublicJwkBuilder(JwkContext<A> jwkContext) {
            super(jwkContext);
            EdwardsCurve.assertEdwards(jwkContext.getKey());
        }

        @Override
        protected OctetPrivateJwkBuilder<B, A> newPrivateBuilder(JwkContext<B> jwkContext) {
            return new DefaultOctetPrivateJwkBuilder(this, jwkContext);
        }

        @Override
        protected PrivateJwkBuilder newPrivateBuilder(JwkContext jwkContext) {
            return this.newPrivateBuilder(jwkContext);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static class DefaultEcPublicJwkBuilder
    extends DefaultPublicJwkBuilder<ECPublicKey, ECPrivateKey, EcPublicJwk, EcPrivateJwk, EcPrivateJwkBuilder, EcPublicJwkBuilder>
    implements EcPublicJwkBuilder {
        DefaultEcPublicJwkBuilder(JwkContext<ECPublicKey> jwkContext) {
            super(jwkContext);
        }

        @Override
        protected EcPrivateJwkBuilder newPrivateBuilder(JwkContext<ECPrivateKey> jwkContext) {
            return new DefaultEcPrivateJwkBuilder(this, jwkContext);
        }

        @Override
        protected PrivateJwkBuilder newPrivateBuilder(JwkContext jwkContext) {
            return this.newPrivateBuilder(jwkContext);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static class DefaultRsaPublicJwkBuilder
    extends DefaultPublicJwkBuilder<RSAPublicKey, RSAPrivateKey, RsaPublicJwk, RsaPrivateJwk, RsaPrivateJwkBuilder, RsaPublicJwkBuilder>
    implements RsaPublicJwkBuilder {
        DefaultRsaPublicJwkBuilder(JwkContext<RSAPublicKey> jwkContext) {
            super(jwkContext);
        }

        @Override
        protected RsaPrivateJwkBuilder newPrivateBuilder(JwkContext<RSAPrivateKey> jwkContext) {
            return new DefaultRsaPrivateJwkBuilder(this, jwkContext);
        }

        @Override
        protected PrivateJwkBuilder newPrivateBuilder(JwkContext jwkContext) {
            return this.newPrivateBuilder(jwkContext);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static abstract class DefaultPrivateJwkBuilder<K extends PrivateKey, L extends PublicKey, J extends PublicJwk<L>, M extends PrivateJwk<K, L, J>, T extends PrivateJwkBuilder<K, L, J, M, T>>
    extends AbstractAsymmetricJwkBuilder<K, M, T>
    implements PrivateJwkBuilder<K, L, J, M, T> {
        DefaultPrivateJwkBuilder(JwkContext<K> jwkContext) {
            super(jwkContext);
        }

        DefaultPrivateJwkBuilder(DefaultPublicJwkBuilder<L, K, J, M, ?, ?> defaultPublicJwkBuilder, JwkContext<K> jwkContext) {
            super(defaultPublicJwkBuilder, jwkContext);
            ((JwkContext)this.DELEGATE).setPublicKey((PublicKey)((JwkContext)AbstractAsymmetricJwkBuilder.access$000(defaultPublicJwkBuilder)).getKey());
        }

        @Override
        public T publicKey(L l) {
            ((JwkContext)this.DELEGATE).setPublicKey((PublicKey)l);
            return (T)((PrivateJwkBuilder)this.self());
        }

        @Override
        public Object build() {
            return super.build();
        }

        @Override
        public X509Builder x509Sha256Thumbprint(boolean bl) {
            return super.x509Sha256Thumbprint(bl);
        }

        @Override
        public X509Builder x509Sha1Thumbprint(boolean bl) {
            return super.x509Sha1Thumbprint(bl);
        }

        @Override
        public X509Mutator x509Sha256Thumbprint(byte[] byArray) {
            return super.x509Sha256Thumbprint(byArray);
        }

        @Override
        public X509Mutator x509Sha1Thumbprint(byte[] byArray) {
            return super.x509Sha1Thumbprint(byArray);
        }

        @Override
        public X509Mutator x509Chain(List list) {
            return super.x509Chain(list);
        }

        @Override
        public X509Mutator x509Url(URI uRI) {
            return super.x509Url(uRI);
        }

        @Override
        public Jwk build() {
            return super.build();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static abstract class DefaultPublicJwkBuilder<K extends PublicKey, L extends PrivateKey, J extends PublicJwk<K>, M extends PrivateJwk<L, K, J>, P extends PrivateJwkBuilder<L, K, J, M, P>, T extends PublicJwkBuilder<K, L, J, M, P, T>>
    extends AbstractAsymmetricJwkBuilder<K, J, T>
    implements PublicJwkBuilder<K, L, J, M, P, T> {
        DefaultPublicJwkBuilder(JwkContext<K> jwkContext) {
            super(jwkContext);
        }

        @Override
        public P privateKey(L l) {
            Assert.notNull(l, "PrivateKey argument cannot be null.");
            PublicKey publicKey = (PublicKey)Assert.notNull(((JwkContext)this.DELEGATE).getKey(), "PublicKey cannot be null.");
            return (P)this.newPrivateBuilder(this.newContext(l)).publicKey((PublicKey)publicKey);
        }

        protected abstract P newPrivateBuilder(JwkContext<L> var1);

        @Override
        public Object build() {
            return super.build();
        }

        @Override
        public X509Builder x509Sha256Thumbprint(boolean bl) {
            return super.x509Sha256Thumbprint(bl);
        }

        @Override
        public X509Builder x509Sha1Thumbprint(boolean bl) {
            return super.x509Sha1Thumbprint(bl);
        }

        @Override
        public X509Mutator x509Sha256Thumbprint(byte[] byArray) {
            return super.x509Sha256Thumbprint(byArray);
        }

        @Override
        public X509Mutator x509Sha1Thumbprint(byte[] byArray) {
            return super.x509Sha1Thumbprint(byArray);
        }

        @Override
        public X509Mutator x509Chain(List list) {
            return super.x509Chain(list);
        }

        @Override
        public X509Mutator x509Url(URI uRI) {
            return super.x509Url(uRI);
        }

        @Override
        public Jwk build() {
            return super.build();
        }
    }
}

