/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.DefaultNestedCollection;
import io.jsonwebtoken.impl.lang.DelegatingMapMutator;
import io.jsonwebtoken.impl.lang.IdRegistry;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractJwk;
import io.jsonwebtoken.impl.security.DefaultMacAlgorithm;
import io.jsonwebtoken.impl.security.DispatchingJwkFactory;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.JwkFactory;
import io.jsonwebtoken.impl.security.KeyOperationConverter;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.NestedCollection;
import io.jsonwebtoken.security.HashAlgorithm;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkBuilder;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.KeyOperation;
import io.jsonwebtoken.security.KeyOperationPolicied;
import io.jsonwebtoken.security.KeyOperationPolicy;
import io.jsonwebtoken.security.MalformedKeyException;
import io.jsonwebtoken.security.SecretJwk;
import io.jsonwebtoken.security.SecretJwkBuilder;
import io.jsonwebtoken.security.SecurityBuilder;
import java.security.Key;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Map;
import javax.crypto.SecretKey;

abstract class AbstractJwkBuilder<K extends Key, J extends Jwk<K>, T extends JwkBuilder<K, J, T>>
extends DelegatingMapMutator<String, Object, JwkContext<K>, T>
implements JwkBuilder<K, J, T> {
    protected final JwkFactory<K, J> jwkFactory;
    static final KeyOperationPolicy DEFAULT_OPERATION_POLICY = (KeyOperationPolicy)Jwks.OP.policy().build();
    protected KeyOperationPolicy opsPolicy = DEFAULT_OPERATION_POLICY;

    protected AbstractJwkBuilder(JwkContext<K> jwkContext) {
        this(jwkContext, DispatchingJwkFactory.DEFAULT_INSTANCE);
    }

    protected AbstractJwkBuilder(JwkContext<K> jwkContext, JwkFactory<K, J> jwkFactory) {
        super(jwkContext);
        this.jwkFactory = Assert.notNull(jwkFactory, "JwkFactory cannot be null.");
    }

    protected <A extends Key> JwkContext<A> newContext(A a) {
        return this.jwkFactory.newContext((JwkContext)this.DELEGATE, a);
    }

    @Override
    public T provider(Provider provider) {
        ((JwkContext)this.DELEGATE).setProvider(provider);
        return (T)((JwkBuilder)this.self());
    }

    @Override
    public T random(SecureRandom secureRandom) {
        ((JwkContext)this.DELEGATE).setRandom(secureRandom);
        return (T)((JwkBuilder)this.self());
    }

    @Override
    public T algorithm(String string) {
        Assert.hasText(string, "Algorithm cannot be null or empty.");
        ((JwkContext)this.DELEGATE).setAlgorithm(string);
        return (T)((JwkBuilder)this.self());
    }

    @Override
    public T id(String string) {
        Assert.hasText(string, "Id cannot be null or empty.");
        ((JwkContext)this.DELEGATE).setIdThumbprintAlgorithm(null);
        ((JwkContext)this.DELEGATE).setId(string);
        return (T)((JwkBuilder)this.self());
    }

    @Override
    public T idFromThumbprint() {
        return this.idFromThumbprint(Jwks.HASH.SHA256);
    }

    @Override
    public T idFromThumbprint(HashAlgorithm hashAlgorithm) {
        Assert.notNull(hashAlgorithm, "Thumbprint HashAlgorithm cannot be null.");
        Assert.notNull(hashAlgorithm.getId(), "Thumbprint HashAlgorithm ID cannot be null.");
        ((JwkContext)this.DELEGATE).setId(null);
        ((JwkContext)this.DELEGATE).setIdThumbprintAlgorithm(hashAlgorithm);
        return (T)((JwkBuilder)this.self());
    }

    @Override
    public NestedCollection<KeyOperation, T> operations() {
        return new DefaultNestedCollection<KeyOperation, T>(this, (JwkBuilder)this.self(), ((JwkContext)this.DELEGATE).getOperations()){
            final AbstractJwkBuilder this$0;
            {
                this.this$0 = abstractJwkBuilder;
                super(jwkBuilder, collection);
            }

            @Override
            public T and() {
                Collection collection = this.getCollection();
                this.this$0.opsPolicy.validate(collection);
                ((JwkContext)AbstractJwkBuilder.access$000(this.this$0)).setOperations(collection);
                return (JwkBuilder)super.and();
            }

            @Override
            public Object and() {
                return this.and();
            }
        };
    }

    @Override
    public T operationPolicy(KeyOperationPolicy keyOperationPolicy) throws IllegalArgumentException {
        Assert.notNull(keyOperationPolicy, "Policy cannot be null.");
        Collection<KeyOperation> collection = keyOperationPolicy.getOperations();
        Assert.notEmpty(collection, "Policy operations cannot be null or empty.");
        this.opsPolicy = keyOperationPolicy;
        IdRegistry<KeyOperation> idRegistry = new IdRegistry<KeyOperation>("JSON Web Key Operation", collection);
        Parameter parameter = (Parameter)Parameters.builder(KeyOperation.class).setConverter(new KeyOperationConverter(idRegistry)).set().setId(AbstractJwk.KEY_OPS.getId()).setName(AbstractJwk.KEY_OPS.getName()).build();
        this.setDelegate(((JwkContext)this.DELEGATE).parameter(parameter));
        return (T)((JwkBuilder)this.self());
    }

    @Override
    public J build() {
        Assert.stateNotNull(this.DELEGATE, "JwkContext should always be non-null");
        Object k = ((JwkContext)this.DELEGATE).getKey();
        if (k == null && this.isEmpty()) {
            String string = "A " + Key.class.getName() + " or one or more name/value pairs must be provided to create a JWK.";
            throw new IllegalStateException(string);
        }
        try {
            this.opsPolicy.validate((Collection<? extends KeyOperation>)((JwkContext)this.DELEGATE).get(AbstractJwk.KEY_OPS));
            return this.jwkFactory.createJwk((JwkContext)this.DELEGATE);
        } catch (IllegalArgumentException illegalArgumentException) {
            String string = "Unable to create JWK: " + illegalArgumentException.getMessage();
            throw new MalformedKeyException(string, illegalArgumentException);
        }
    }

    @Override
    public SecurityBuilder random(SecureRandom secureRandom) {
        return this.random(secureRandom);
    }

    @Override
    public SecurityBuilder provider(Provider provider) {
        return this.provider(provider);
    }

    @Override
    public Object build() {
        return this.build();
    }

    @Override
    public KeyOperationPolicied operationPolicy(KeyOperationPolicy keyOperationPolicy) throws IllegalArgumentException {
        return this.operationPolicy(keyOperationPolicy);
    }

    static Map access$000(AbstractJwkBuilder abstractJwkBuilder) {
        return abstractJwkBuilder.DELEGATE;
    }

    static class DefaultSecretJwkBuilder
    extends AbstractJwkBuilder<SecretKey, SecretJwk, SecretJwkBuilder>
    implements SecretJwkBuilder {
        public DefaultSecretJwkBuilder(JwkContext<SecretKey> jwkContext) {
            super(jwkContext);
            Key key = Assert.notNull(jwkContext.getKey(), "SecretKey cannot be null.");
            DefaultMacAlgorithm defaultMacAlgorithm = DefaultMacAlgorithm.findByKey(key);
            if (defaultMacAlgorithm != null) {
                this.algorithm(defaultMacAlgorithm.getId());
            }
        }

        @Override
        public SecurityBuilder random(SecureRandom secureRandom) {
            return super.random(secureRandom);
        }

        @Override
        public SecurityBuilder provider(Provider provider) {
            return super.provider(provider);
        }

        @Override
        public Object build() {
            return super.build();
        }

        @Override
        public KeyOperationPolicied operationPolicy(KeyOperationPolicy keyOperationPolicy) throws IllegalArgumentException {
            return super.operationPolicy(keyOperationPolicy);
        }
    }
}

