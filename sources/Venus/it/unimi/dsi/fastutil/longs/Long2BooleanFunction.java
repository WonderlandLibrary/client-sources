/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongPredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Long2BooleanFunction
extends Function<Long, Boolean>,
LongPredicate {
    @Override
    default public boolean test(long l) {
        return this.get(l);
    }

    @Override
    default public boolean put(long l, boolean bl) {
        throw new UnsupportedOperationException();
    }

    public boolean get(long var1);

    default public boolean remove(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Boolean put(Long l, Boolean bl) {
        long l2 = l;
        boolean bl2 = this.containsKey(l2);
        boolean bl3 = this.put(l2, (boolean)bl);
        return bl2 ? Boolean.valueOf(bl3) : null;
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        boolean bl = this.get(l);
        return bl != this.defaultReturnValue() || this.containsKey(l) ? Boolean.valueOf(bl) : null;
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        return this.containsKey(l) ? Boolean.valueOf(this.remove(l)) : null;
    }

    default public boolean containsKey(long l) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Long)object);
    }

    default public void defaultReturnValue(boolean bl) {
        throw new UnsupportedOperationException();
    }

    default public boolean defaultReturnValue() {
        return true;
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
        return this.put((Long)object, (Boolean)object2);
    }
}

