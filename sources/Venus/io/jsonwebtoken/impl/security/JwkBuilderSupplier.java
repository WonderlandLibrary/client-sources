/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Supplier;
import io.jsonwebtoken.security.DynamicJwkBuilder;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.KeyOperationPolicy;
import java.security.Provider;

public class JwkBuilderSupplier
implements Supplier<DynamicJwkBuilder<?, ?>> {
    public static final JwkBuilderSupplier DEFAULT = new JwkBuilderSupplier(null, null);
    private final Provider provider;
    private final KeyOperationPolicy operationPolicy;

    public JwkBuilderSupplier(Provider provider, KeyOperationPolicy keyOperationPolicy) {
        this.provider = provider;
        this.operationPolicy = keyOperationPolicy;
    }

    @Override
    public DynamicJwkBuilder<?, ?> get() {
        DynamicJwkBuilder dynamicJwkBuilder = (DynamicJwkBuilder)Jwks.builder().provider(this.provider);
        if (this.operationPolicy != null) {
            dynamicJwkBuilder.operationPolicy(this.operationPolicy);
        }
        return dynamicJwkBuilder;
    }

    @Override
    public Object get() {
        return this.get();
    }
}

