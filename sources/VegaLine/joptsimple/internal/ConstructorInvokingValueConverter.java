/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.internal;

import java.lang.reflect.Constructor;
import joptsimple.ValueConverter;
import joptsimple.internal.Reflection;

class ConstructorInvokingValueConverter<V>
implements ValueConverter<V> {
    private final Constructor<V> ctor;

    ConstructorInvokingValueConverter(Constructor<V> ctor) {
        this.ctor = ctor;
    }

    @Override
    public V convert(String value) {
        return Reflection.instantiate(this.ctor, value);
    }

    @Override
    public Class<V> valueType() {
        return this.ctor.getDeclaringClass();
    }

    @Override
    public String valuePattern() {
        return null;
    }
}

