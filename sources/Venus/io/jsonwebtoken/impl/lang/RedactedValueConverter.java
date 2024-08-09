/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.RedactedSupplier;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Supplier;

public class RedactedValueConverter<T>
implements Converter<T, Object> {
    private final Converter<T, Object> delegate;

    public RedactedValueConverter(Converter<T, Object> converter) {
        this.delegate = Assert.notNull(converter, "Delegate cannot be null.");
    }

    @Override
    public Object applyTo(T t) {
        RedactedSupplier<Object> redactedSupplier = this.delegate.applyTo(t);
        if (redactedSupplier != null && !(redactedSupplier instanceof RedactedSupplier)) {
            redactedSupplier = new RedactedSupplier<Object>(redactedSupplier);
        }
        return redactedSupplier;
    }

    @Override
    public T applyFrom(Object object) {
        if (object instanceof RedactedSupplier) {
            object = ((Supplier)object).get();
        }
        return this.delegate.applyFrom(object);
    }
}

