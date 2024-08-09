/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractJwkBuilder;
import io.jsonwebtoken.impl.security.AbstractSecurityBuilder;
import io.jsonwebtoken.impl.security.DefaultJwkSet;
import io.jsonwebtoken.impl.security.JwkBuilderSupplier;
import io.jsonwebtoken.impl.security.JwkConverter;
import io.jsonwebtoken.impl.security.JwkSetConverter;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.MapMutator;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkSet;
import io.jsonwebtoken.security.JwkSetBuilder;
import io.jsonwebtoken.security.KeyOperationPolicied;
import io.jsonwebtoken.security.KeyOperationPolicy;
import io.jsonwebtoken.security.SecurityBuilder;
import java.security.Provider;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultJwkSetBuilder
extends AbstractSecurityBuilder<JwkSet, JwkSetBuilder>
implements JwkSetBuilder {
    private KeyOperationPolicy operationPolicy = AbstractJwkBuilder.DEFAULT_OPERATION_POLICY;
    private JwkSetConverter converter = new JwkSetConverter();
    private ParameterMap map = new ParameterMap(Parameters.registry(DefaultJwkSet.KEYS));

    @Override
    public JwkSetBuilder delete(String string) {
        this.map.remove(string);
        return this;
    }

    @Override
    public JwkSetBuilder empty() {
        this.map.clear();
        return this;
    }

    @Override
    public JwkSetBuilder add(String string, Object object) {
        this.map.put(string, object);
        return this;
    }

    @Override
    public JwkSetBuilder add(Map<? extends String, ?> map) {
        this.map.putAll(map);
        return this;
    }

    private JwkSetBuilder refresh() {
        JwkConverter jwkConverter = new JwkConverter(new JwkBuilderSupplier(this.provider, this.operationPolicy));
        this.converter = new JwkSetConverter(jwkConverter, this.converter.isIgnoreUnsupported());
        Parameter<Set<Jwk<?>>> parameter = DefaultJwkSet.param(jwkConverter);
        this.map = new ParameterMap(Parameters.registry(parameter), this.map, true);
        Set<Jwk<?>> set = this.map.get(parameter);
        if (!Collections.isEmpty(set)) {
            for (Jwk<?> jwk : set) {
                this.operationPolicy.validate(jwk.getOperations());
            }
        }
        return this;
    }

    @Override
    public JwkSetBuilder provider(Provider provider) {
        super.provider(provider);
        return this.refresh();
    }

    @Override
    public JwkSetBuilder operationPolicy(KeyOperationPolicy keyOperationPolicy) throws IllegalArgumentException {
        this.operationPolicy = keyOperationPolicy != null ? keyOperationPolicy : AbstractJwkBuilder.DEFAULT_OPERATION_POLICY;
        return this.refresh();
    }

    Collection<Jwk<?>> ensureKeys() {
        Collection collection = this.map.get(DefaultJwkSet.KEYS);
        return Collections.isEmpty(collection) ? new LinkedHashSet() : collection;
    }

    @Override
    public JwkSetBuilder add(Jwk<?> jwk) {
        if (jwk != null) {
            this.operationPolicy.validate(jwk.getOperations());
            Collection<Jwk<?>> collection = this.ensureKeys();
            collection.add(jwk);
            this.keys(collection);
        }
        return this;
    }

    @Override
    public JwkSetBuilder add(Collection<Jwk<?>> collection) {
        if (!Collections.isEmpty(collection)) {
            for (Jwk<?> jwk : collection) {
                this.add(jwk);
            }
        }
        return this;
    }

    @Override
    public JwkSetBuilder keys(Collection<Jwk<?>> collection) {
        return this.add(DefaultJwkSet.KEYS.getId(), (Object)collection);
    }

    @Override
    public JwkSet build() {
        return this.converter.applyFrom(this.map);
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
    public MapMutator add(Map map) {
        return this.add(map);
    }

    @Override
    public MapMutator add(Object object, Object object2) {
        return this.add((String)object, object2);
    }

    @Override
    public MapMutator empty() {
        return this.empty();
    }

    @Override
    public MapMutator delete(Object object) {
        return this.delete((String)object);
    }

    @Override
    public KeyOperationPolicied operationPolicy(KeyOperationPolicy keyOperationPolicy) throws IllegalArgumentException {
        return this.operationPolicy(keyOperationPolicy);
    }
}

