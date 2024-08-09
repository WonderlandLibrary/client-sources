/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Function;

public final class ConstantFunction<T, R>
implements Function<T, R> {
    private final R value;

    public ConstantFunction(R r) {
        this.value = r;
    }

    @Override
    public R apply(T t) {
        return this.value;
    }
}

