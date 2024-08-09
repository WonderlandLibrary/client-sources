/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Long2IntFunction
extends Function<Long, Integer>,
LongToIntFunction {
    @Override
    default public int applyAsInt(long l) {
        return this.get(l);
    }

    @Override
    default public int put(long l, int n) {
        throw new UnsupportedOperationException();
    }

    public int get(long var1);

    default public int remove(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Integer put(Long l, Integer n) {
        long l2 = l;
        boolean bl = this.containsKey(l2);
        int n2 = this.put(l2, (int)n);
        return bl ? Integer.valueOf(n2) : null;
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        int n = this.get(l);
        return n != this.defaultReturnValue() || this.containsKey(l) ? Integer.valueOf(n) : null;
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        return this.containsKey(l) ? Integer.valueOf(this.remove(l)) : null;
    }

    default public boolean containsKey(long l) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Long)object);
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
        return this.put((Long)object, (Integer)object2);
    }
}

