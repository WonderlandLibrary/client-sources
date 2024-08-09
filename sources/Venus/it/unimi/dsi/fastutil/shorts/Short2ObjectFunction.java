/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Short2ObjectFunction<V>
extends Function<Short, V>,
IntFunction<V> {
    @Override
    @Deprecated
    default public V apply(int n) {
        return this.get(SafeMath.safeIntToShort(n));
    }

    @Override
    default public V put(short s, V v) {
        throw new UnsupportedOperationException();
    }

    public V get(short var1);

    default public V remove(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public V put(Short s, V v) {
        short s2 = s;
        boolean bl = this.containsKey(s2);
        V v2 = this.put(s2, v);
        return (V)(bl ? v2 : null);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        V v = this.get(s);
        return (V)(v != this.defaultReturnValue() || this.containsKey(s) ? v : null);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        return this.containsKey(s) ? (V)this.remove(s) : null;
    }

    default public boolean containsKey(short s) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Short)object);
    }

    default public void defaultReturnValue(V v) {
        throw new UnsupportedOperationException();
    }

    default public V defaultReturnValue() {
        return null;
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Short)object, (V)object2);
    }
}

