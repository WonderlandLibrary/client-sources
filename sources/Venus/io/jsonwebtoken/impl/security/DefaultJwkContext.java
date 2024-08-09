/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.AbstractX509Context;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractAsymmetricJwk;
import io.jsonwebtoken.impl.security.AbstractJwk;
import io.jsonwebtoken.impl.security.DefaultEcPrivateJwk;
import io.jsonwebtoken.impl.security.DefaultEcPublicJwk;
import io.jsonwebtoken.impl.security.DefaultOctetPrivateJwk;
import io.jsonwebtoken.impl.security.DefaultRsaPrivateJwk;
import io.jsonwebtoken.impl.security.DefaultSecretJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.HashAlgorithm;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.KeyOperation;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class DefaultJwkContext<K extends Key>
extends AbstractX509Context<JwkContext<K>>
implements JwkContext<K> {
    private static final Set<Parameter<?>> DEFAULT_PARAMS;
    private K key;
    private PublicKey publicKey;
    private Provider provider;
    private SecureRandom random;
    private HashAlgorithm idThumbprintAlgorithm;

    public DefaultJwkContext() {
        this(DEFAULT_PARAMS);
    }

    public DefaultJwkContext(Set<Parameter<?>> set) {
        super(set);
    }

    public DefaultJwkContext(Set<Parameter<?>> set, JwkContext<?> jwkContext) {
        this(set, jwkContext, true);
    }

    public DefaultJwkContext(Set<Parameter<?>> set, JwkContext<?> jwkContext, K k) {
        this(set, jwkContext, k == null || k instanceof PublicKey);
        this.key = (Key)Assert.notNull(k, "Key cannot be null.");
    }

    public DefaultJwkContext(Set<Parameter<?>> set, JwkContext<?> jwkContext, boolean bl) {
        super(Assert.notEmpty(set, "Parameters cannot be null or empty."));
        Assert.notNull(jwkContext, "JwkContext cannot be null.");
        Assert.isInstanceOf(DefaultJwkContext.class, jwkContext, "JwkContext must be a DefaultJwkContext instance.");
        DefaultJwkContext defaultJwkContext = (DefaultJwkContext)jwkContext;
        this.provider = jwkContext.getProvider();
        this.random = jwkContext.getRandom();
        this.idThumbprintAlgorithm = jwkContext.getIdThumbprintAlgorithm();
        this.values.putAll(defaultJwkContext.values);
        for (Map.Entry object : defaultJwkContext.idiomaticValues.entrySet()) {
            String string = (String)object.getKey();
            Object v = object.getValue();
            Parameter parameter = (Parameter)this.PARAMS.get(string);
            if (parameter != null && !parameter.supports(v)) {
                v = this.values.get(parameter.getId());
                this.put(parameter, v);
                continue;
            }
            this.idiomaticValues.put(string, v);
        }
        if (bl) {
            for (Parameter parameter : defaultJwkContext.PARAMS.values()) {
                if (!parameter.isSecret()) continue;
                this.remove(parameter.getId());
            }
        }
    }

    @Override
    public JwkContext<K> parameter(Parameter<?> parameter) {
        Registry<String, Parameter<?>> registry = Parameters.replace(this.PARAMS, parameter);
        LinkedHashSet linkedHashSet = new LinkedHashSet(registry.values());
        return this.key != null ? new DefaultJwkContext<K>(linkedHashSet, this, this.key) : new DefaultJwkContext<K>((Set<Parameter<?>>)linkedHashSet, (JwkContext<?>)this, false);
    }

    @Override
    public String getName() {
        String string = this.get(AbstractJwk.KTY);
        if ("oct".equals(string)) {
            string = "Secret";
        } else if ("OKP".equals(string)) {
            string = "Octet";
        }
        StringBuilder stringBuilder = string != null ? new StringBuilder(string) : new StringBuilder();
        K k = this.getKey();
        if (k instanceof PublicKey) {
            Strings.nespace(stringBuilder).append("Public");
        } else if (k instanceof PrivateKey) {
            Strings.nespace(stringBuilder).append("Private");
        }
        Strings.nespace(stringBuilder).append("JWK");
        return stringBuilder.toString();
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        Assert.notEmpty(map, "JWK values cannot be null or empty.");
        super.putAll(map);
    }

    @Override
    public String getAlgorithm() {
        return this.get(AbstractJwk.ALG);
    }

    @Override
    public JwkContext<K> setAlgorithm(String string) {
        this.put(AbstractJwk.ALG, (Object)string);
        return this;
    }

    @Override
    public String getId() {
        return this.get(AbstractJwk.KID);
    }

    @Override
    public JwkContext<K> setId(String string) {
        this.put(AbstractJwk.KID, (Object)string);
        return this;
    }

    @Override
    public JwkContext<K> setIdThumbprintAlgorithm(HashAlgorithm hashAlgorithm) {
        this.idThumbprintAlgorithm = hashAlgorithm;
        return this;
    }

    @Override
    public HashAlgorithm getIdThumbprintAlgorithm() {
        return this.idThumbprintAlgorithm;
    }

    @Override
    public Set<KeyOperation> getOperations() {
        return this.get(AbstractJwk.KEY_OPS);
    }

    @Override
    public JwkContext<K> setOperations(Collection<? extends KeyOperation> collection) {
        this.put(AbstractJwk.KEY_OPS, (Object)collection);
        return this;
    }

    @Override
    public String getType() {
        return this.get(AbstractJwk.KTY);
    }

    @Override
    public JwkContext<K> setType(String string) {
        this.put(AbstractJwk.KTY, (Object)string);
        return this;
    }

    @Override
    public String getPublicKeyUse() {
        return this.get(AbstractAsymmetricJwk.USE);
    }

    @Override
    public JwkContext<K> setPublicKeyUse(String string) {
        this.put(AbstractAsymmetricJwk.USE, (Object)string);
        return this;
    }

    @Override
    public boolean isSigUse() {
        if ("sig".equals(this.getPublicKeyUse())) {
            return false;
        }
        Set<KeyOperation> set = this.getOperations();
        if (Collections.isEmpty(set)) {
            return true;
        }
        return set.contains(Jwks.OP.SIGN) || set.contains(Jwks.OP.VERIFY);
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public JwkContext<K> setKey(K k) {
        this.key = k;
        return this;
    }

    @Override
    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public JwkContext<K> setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    @Override
    public Provider getProvider() {
        return this.provider;
    }

    @Override
    public JwkContext<K> setProvider(Provider provider) {
        this.provider = provider;
        return this;
    }

    @Override
    public SecureRandom getRandom() {
        return this.random;
    }

    @Override
    public JwkContext<K> setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    static {
        LinkedHashSet<Parameter<Object>> linkedHashSet = new LinkedHashSet<Parameter<Object>>();
        linkedHashSet.addAll(DefaultSecretJwk.PARAMS);
        linkedHashSet.addAll(DefaultEcPrivateJwk.PARAMS);
        linkedHashSet.addAll(DefaultRsaPrivateJwk.PARAMS);
        linkedHashSet.addAll(DefaultOctetPrivateJwk.PARAMS);
        linkedHashSet.remove(DefaultEcPublicJwk.X);
        linkedHashSet.remove(DefaultEcPrivateJwk.D);
        linkedHashSet.add(Parameters.string(DefaultEcPublicJwk.X.getId(), "Elliptic Curve public key X coordinate"));
        linkedHashSet.add((Parameter<Object>)Parameters.builder(String.class).setSecret(true).setId(DefaultEcPrivateJwk.D.getId()).setName("Elliptic Curve private key").build());
        DEFAULT_PARAMS = Collections.immutable(linkedHashSet);
    }
}

