/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.internal;

import java.lang.reflect.Method;
import joptsimple.ValueConverter;
import joptsimple.internal.Reflection;

class MethodInvokingValueConverter<V>
implements ValueConverter<V> {
    private final Method method;
    private final Class<V> clazz;

    MethodInvokingValueConverter(Method method, Class<V> clazz) {
        this.method = method;
        this.clazz = clazz;
    }

    @Override
    public V convert(String string) {
        return this.clazz.cast(Reflection.invoke(this.method, string));
    }

    @Override
    public Class<V> valueType() {
        return this.clazz;
    }

    @Override
    public String valuePattern() {
        return null;
    }
}

