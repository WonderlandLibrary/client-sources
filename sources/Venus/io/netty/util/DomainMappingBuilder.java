/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.DomainNameMapping;
import io.netty.util.DomainNameMappingBuilder;

@Deprecated
public final class DomainMappingBuilder<V> {
    private final DomainNameMappingBuilder<V> builder;

    public DomainMappingBuilder(V v) {
        this.builder = new DomainNameMappingBuilder<V>(v);
    }

    public DomainMappingBuilder(int n, V v) {
        this.builder = new DomainNameMappingBuilder<V>(n, v);
    }

    public DomainMappingBuilder<V> add(String string, V v) {
        this.builder.add(string, v);
        return this;
    }

    public DomainNameMapping<V> build() {
        return this.builder.build();
    }
}

