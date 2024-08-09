/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Char2ObjectFunction<V>
extends Function<Character, V>,
IntFunction<V> {
    @Override
    @Deprecated
    default public V apply(int n) {
        return this.get(SafeMath.safeIntToChar(n));
    }

    @Override
    default public V put(char c, V v) {
        throw new UnsupportedOperationException();
    }

    public V get(char var1);

    default public V remove(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public V put(Character c, V v) {
        char c2 = c.charValue();
        boolean bl = this.containsKey(c2);
        V v2 = this.put(c2, v);
        return (V)(bl ? v2 : null);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        V v = this.get(c);
        return (V)(v != this.defaultReturnValue() || this.containsKey(c) ? v : null);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        return this.containsKey(c) ? (V)this.remove(c) : null;
    }

    default public boolean containsKey(char c) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Character)object).charValue());
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
        return this.put((Character)object, (V)object2);
    }
}

