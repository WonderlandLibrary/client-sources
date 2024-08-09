/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Byte2LongFunction
extends Function<Byte, Long>,
IntToLongFunction {
    @Override
    @Deprecated
    default public long applyAsLong(int n) {
        return this.get(SafeMath.safeIntToByte(n));
    }

    @Override
    default public long put(byte by, long l) {
        throw new UnsupportedOperationException();
    }

    public long get(byte var1);

    default public long remove(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Long put(Byte by, Long l) {
        byte by2 = by;
        boolean bl = this.containsKey(by2);
        long l2 = this.put(by2, (long)l);
        return bl ? Long.valueOf(l2) : null;
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        long l = this.get(by);
        return l != this.defaultReturnValue() || this.containsKey(by) ? Long.valueOf(l) : null;
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        return this.containsKey(by) ? Long.valueOf(this.remove(by)) : null;
    }

    default public boolean containsKey(byte by) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Byte)object);
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
        return this.put((Byte)object, (Long)object2);
    }
}

