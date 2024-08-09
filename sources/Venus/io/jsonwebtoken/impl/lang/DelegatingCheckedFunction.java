/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.Function;

public class DelegatingCheckedFunction<T, R>
implements CheckedFunction<T, R> {
    final Function<T, R> delegate;

    public DelegatingCheckedFunction(Function<T, R> function) {
        this.delegate = function;
    }

    @Override
    public R apply(T t) throws Exception {
        return this.delegate.apply(t);
    }
}

