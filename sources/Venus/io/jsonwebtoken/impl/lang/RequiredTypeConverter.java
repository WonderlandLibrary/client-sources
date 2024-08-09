/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.Assert;

public class RequiredTypeConverter<T>
implements Converter<T, Object> {
    private final Class<T> type;

    public RequiredTypeConverter(Class<T> clazz) {
        this.type = Assert.notNull(clazz, "type argument cannot be null.");
    }

    @Override
    public Object applyTo(T t) {
        return t;
    }

    @Override
    public T applyFrom(Object object) {
        if (object == null) {
            return null;
        }
        Class<?> clazz = object.getClass();
        if (!this.type.isAssignableFrom(clazz)) {
            String string = "Unsupported value type. Expected: " + this.type.getName() + ", found: " + clazz.getName();
            throw new IllegalArgumentException(string);
        }
        return this.type.cast(object);
    }
}

