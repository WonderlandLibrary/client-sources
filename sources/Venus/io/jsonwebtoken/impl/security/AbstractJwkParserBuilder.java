/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.io.AbstractParserBuilder;
import io.jsonwebtoken.impl.security.AbstractJwkBuilder;
import io.jsonwebtoken.io.ParserBuilder;
import io.jsonwebtoken.security.KeyOperationPolicied;
import io.jsonwebtoken.security.KeyOperationPolicy;

abstract class AbstractJwkParserBuilder<T, B extends ParserBuilder<T, B> & KeyOperationPolicied<B>>
extends AbstractParserBuilder<T, B>
implements KeyOperationPolicied<B> {
    protected KeyOperationPolicy operationPolicy = AbstractJwkBuilder.DEFAULT_OPERATION_POLICY;

    AbstractJwkParserBuilder() {
    }

    @Override
    public B operationPolicy(KeyOperationPolicy keyOperationPolicy) throws IllegalArgumentException {
        this.operationPolicy = keyOperationPolicy;
        return this.self();
    }

    @Override
    public KeyOperationPolicied operationPolicy(KeyOperationPolicy keyOperationPolicy) throws IllegalArgumentException {
        return (KeyOperationPolicied)this.operationPolicy(keyOperationPolicy);
    }
}

