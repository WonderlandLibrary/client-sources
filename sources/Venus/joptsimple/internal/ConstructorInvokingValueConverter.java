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

    ConstructorInvokingValueConverter(Constructor<V> constructor) {
        this.ctor = constructor;
    }

    @Override
    public V convert(String string) {
        return Reflection.instantiate(this.ctor, string);
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

