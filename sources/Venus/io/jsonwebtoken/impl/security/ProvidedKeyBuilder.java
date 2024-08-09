/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.AbstractSecurityBuilder;
import io.jsonwebtoken.impl.security.ProviderKey;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.KeyBuilder;
import java.security.Key;

abstract class ProvidedKeyBuilder<K extends Key, B extends KeyBuilder<K, B>>
extends AbstractSecurityBuilder<K, B>
implements KeyBuilder<K, B> {
    protected final K key;

    ProvidedKeyBuilder(K k) {
        this.key = (Key)Assert.notNull(k, "Key cannot be null.");
    }

    @Override
    public final K build() {
        if (this.key instanceof ProviderKey) {
            return this.key;
        }
        return this.doBuild();
    }

    abstract K doBuild();

    @Override
    public Object build() {
        return this.build();
    }
}

