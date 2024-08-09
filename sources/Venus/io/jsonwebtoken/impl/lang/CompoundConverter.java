/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.Assert;

public class CompoundConverter<A, B, C>
implements Converter<A, C> {
    private final Converter<A, B> first;
    private final Converter<B, C> second;

    public CompoundConverter(Converter<A, B> converter, Converter<B, C> converter2) {
        this.first = Assert.notNull(converter, "First converter cannot be null.");
        this.second = Assert.notNull(converter2, "Second converter cannot be null.");
    }

    @Override
    public C applyTo(A a) {
        B b = this.first.applyTo(a);
        return this.second.applyTo(b);
    }

    @Override
    public A applyFrom(C c) {
        B b = this.second.applyFrom(c);
        return this.first.applyFrom(b);
    }
}

