/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleFunction;

@FunctionalInterface
public interface Double2ObjectFunction<V>
extends Function<Double, V>,
DoubleFunction<V> {
    @Override
    default public V apply(double d) {
        return this.get(d);
    }

    @Override
    default public V put(double d, V v) {
        throw new UnsupportedOperationException();
    }

    public V get(double var1);

    default public V remove(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public V put(Double d, V v) {
        double d2 = d;
        boolean bl = this.containsKey(d2);
        V v2 = this.put(d2, v);
        return (V)(bl ? v2 : null);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        V v = this.get(d);
        return (V)(v != this.defaultReturnValue() || this.containsKey(d) ? v : null);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        return this.containsKey(d) ? (V)this.remove(d) : null;
    }

    default public boolean containsKey(double d) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Double)object);
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
        return this.put((Double)object, (V)object2);
    }
}

