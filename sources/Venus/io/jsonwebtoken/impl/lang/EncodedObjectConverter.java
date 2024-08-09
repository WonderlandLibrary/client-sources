/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.Assert;

public class EncodedObjectConverter<T>
implements Converter<T, Object> {
    private final Class<T> type;
    private final Converter<T, ? super CharSequence> converter;

    public EncodedObjectConverter(Class<T> clazz, Converter<T, ? super CharSequence> converter) {
        this.type = Assert.notNull(clazz, "Value type cannot be null.");
        this.converter = Assert.notNull(converter, "Value converter cannot be null.");
    }

    @Override
    public Object applyTo(T t) {
        Assert.notNull(t, "Value argument cannot be null.");
        return this.converter.applyTo(t);
    }

    @Override
    public T applyFrom(Object object) {
        Assert.notNull(object, "Value argument cannot be null.");
        if (this.type.isInstance(object)) {
            return this.type.cast(object);
        }
        if (object instanceof CharSequence) {
            return this.converter.applyFrom((CharSequence)object);
        }
        String string = "Values must be either String or " + this.type.getName() + " instances. Value type found: " + object.getClass().getName() + ".";
        throw new IllegalArgumentException(string);
    }
}

