/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Object2IntFunction<K>
extends Function<K, Integer>,
ToIntFunction<K> {
    @Override
    default public int applyAsInt(K k) {
        return this.getInt(k);
    }

    @Override
    default public int put(K k, int n) {
        throw new UnsupportedOperationException();
    }

    public int getInt(Object var1);

    default public int removeInt(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Integer put(K k, Integer n) {
        K k2 = k;
        boolean bl = this.containsKey(k2);
        int n2 = this.put(k2, (int)n);
        return bl ? Integer.valueOf(n2) : null;
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        Object object2 = object;
        int n = this.getInt(object2);
        return n != this.defaultReturnValue() || this.containsKey(object2) ? Integer.valueOf(n) : null;
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        Object object2 = object;
        return this.containsKey(object2) ? Integer.valueOf(this.removeInt(object2)) : null;
    }

    default public void defaultReturnValue(int n) {
        throw new UnsupportedOperationException();
    }

    default public int defaultReturnValue() {
        return 1;
    }

    @Override
    @Deprecated
    default public Object remove(Object object) {
        return this.remove(object);
    }

    @Override
    @Deprecated
    default public Object get(Object object) {
        return this.get(object);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((K)object, (Integer)object2);
    }
}

