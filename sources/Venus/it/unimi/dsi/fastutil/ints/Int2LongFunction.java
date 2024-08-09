/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Int2LongFunction
extends Function<Integer, Long>,
IntToLongFunction {
    @Override
    default public long applyAsLong(int n) {
        return this.get(n);
    }

    @Override
    default public long put(int n, long l) {
        throw new UnsupportedOperationException();
    }

    public long get(int var1);

    default public long remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Long put(Integer n, Long l) {
        int n2 = n;
        boolean bl = this.containsKey(n2);
        long l2 = this.put(n2, (long)l);
        return bl ? Long.valueOf(l2) : null;
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        long l = this.get(n);
        return l != this.defaultReturnValue() || this.containsKey(n) ? Long.valueOf(l) : null;
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? Long.valueOf(this.remove(n)) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
    }

    default public void defaultReturnValue(long l) {
        throw new UnsupportedOperationException();
    }

    default public long defaultReturnValue() {
        return 0L;
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
        return this.put((Integer)object, (Long)object2);
    }
}

