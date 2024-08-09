/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.Assert;

public class NullSafeConverter<A, B>
implements Converter<A, B> {
    private final Converter<A, B> converter;

    public NullSafeConverter(Converter<A, B> converter) {
        this.converter = Assert.notNull(converter, "Delegate converter cannot be null.");
    }

    @Override
    public B applyTo(A a) {
        return a == null ? null : (B)this.converter.applyTo(a);
    }

    @Override
    public A applyFrom(B b) {
        return b == null ? null : (A)this.converter.applyFrom(b);
    }
}

