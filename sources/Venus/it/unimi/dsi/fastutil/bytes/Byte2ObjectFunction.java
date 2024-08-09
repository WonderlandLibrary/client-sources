/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Byte2ObjectFunction<V>
extends Function<Byte, V>,
IntFunction<V> {
    @Override
    @Deprecated
    default public V apply(int n) {
        return this.get(SafeMath.safeIntToByte(n));
    }

    @Override
    default public V put(byte by, V v) {
        throw new UnsupportedOperationException();
    }

    public V get(byte var1);

    default public V remove(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public V put(Byte by, V v) {
        byte by2 = by;
        boolean bl = this.containsKey(by2);
        V v2 = this.put(by2, v);
        return (V)(bl ? v2 : null);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        V v = this.get(by);
        return (V)(v != this.defaultReturnValue() || this.containsKey(by) ? v : null);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        return this.containsKey(by) ? (V)this.remove(by) : null;
    }

    default public boolean containsKey(byte by) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Byte)object);
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
        return this.put((Byte)object, (V)object2);
    }
}

