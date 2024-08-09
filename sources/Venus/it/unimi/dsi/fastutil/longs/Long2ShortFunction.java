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
public interface Long2ShortFunction
extends Function<Long, Short>,
LongToIntFunction {
    @Override
    default public int applyAsInt(long l) {
        return this.get(l);
    }

    @Override
    default public short put(long l, short s) {
        throw new UnsupportedOperationException();
    }

    public short get(long var1);

    default public short remove(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Short put(Long l, Short s) {
        long l2 = l;
        boolean bl = this.containsKey(l2);
        short s2 = this.put(l2, (short)s);
        return bl ? Short.valueOf(s2) : null;
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        short s = this.get(l);
        return s != this.defaultReturnValue() || this.containsKey(l) ? Short.valueOf(s) : null;
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        return this.containsKey(l) ? Short.valueOf(this.remove(l)) : null;
    }

    default public boolean containsKey(long l) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Long)object);
    }

    default public void defaultReturnValue(short s) {
        throw new UnsupportedOperationException();
    }

    default public short defaultReturnValue() {
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
        return this.put((Long)object, (Short)object2);
    }
}

