/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Long2LongFunction
extends Function<Long, Long>,
LongUnaryOperator {
    @Override
    default public long applyAsLong(long l) {
        return this.get(l);
    }

    @Override
    default public long put(long l, long l2) {
        throw new UnsupportedOperationException();
    }

    public long get(long var1);

    default public long remove(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Long put(Long l, Long l2) {
        long l3 = l;
        boolean bl = this.containsKey(l3);
        long l4 = this.put(l3, (long)l2);
        return bl ? Long.valueOf(l4) : null;
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        long l2 = this.get(l);
        return l2 != this.defaultReturnValue() || this.containsKey(l) ? Long.valueOf(l2) : null;
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        return this.containsKey(l) ? Long.valueOf(this.remove(l)) : null;
    }

    default public boolean containsKey(long l) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Long)object);
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
        return this.put((Long)object, (Long)object2);
    }
}

