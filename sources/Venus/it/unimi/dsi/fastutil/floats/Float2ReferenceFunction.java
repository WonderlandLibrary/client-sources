/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoubleFunction;

@FunctionalInterface
public interface Float2ReferenceFunction<V>
extends Function<Float, V>,
DoubleFunction<V> {
    @Override
    @Deprecated
    default public V apply(double d) {
        return this.get(SafeMath.safeDoubleToFloat(d));
    }

    @Override
    default public V put(float f, V v) {
        throw new UnsupportedOperationException();
    }

    public V get(float var1);

    default public V remove(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public V put(Float f, V v) {
        float f2 = f.floatValue();
        boolean bl = this.containsKey(f2);
        V v2 = this.put(f2, v);
        return (V)(bl ? v2 : null);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        V v = this.get(f);
        return (V)(v != this.defaultReturnValue() || this.containsKey(f) ? v : null);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        return this.containsKey(f) ? (V)this.remove(f) : null;
    }

    default public boolean containsKey(float f) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Float)object).floatValue());
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
        return this.put((Float)object, (V)object2);
    }
}

