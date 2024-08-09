/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Short2LongFunction
extends Function<Short, Long>,
IntToLongFunction {
    @Override
    @Deprecated
    default public long applyAsLong(int n) {
        return this.get(SafeMath.safeIntToShort(n));
    }

    @Override
    default public long put(short s, long l) {
        throw new UnsupportedOperationException();
    }

    public long get(short var1);

    default public long remove(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Long put(Short s, Long l) {
        short s2 = s;
        boolean bl = this.containsKey(s2);
        long l2 = this.put(s2, (long)l);
        return bl ? Long.valueOf(l2) : null;
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        long l = this.get(s);
        return l != this.defaultReturnValue() || this.containsKey(s) ? Long.valueOf(l) : null;
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        return this.containsKey(s) ? Long.valueOf(this.remove(s)) : null;
    }

    default public boolean containsKey(short s) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Short)object);
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
        return this.put((Short)object, (Long)object2);
    }
}

