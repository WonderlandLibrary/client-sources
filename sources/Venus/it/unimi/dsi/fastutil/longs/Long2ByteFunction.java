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
public interface Long2ByteFunction
extends Function<Long, Byte>,
LongToIntFunction {
    @Override
    default public int applyAsInt(long l) {
        return this.get(l);
    }

    @Override
    default public byte put(long l, byte by) {
        throw new UnsupportedOperationException();
    }

    public byte get(long var1);

    default public byte remove(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Byte put(Long l, Byte by) {
        long l2 = l;
        boolean bl = this.containsKey(l2);
        byte by2 = this.put(l2, (byte)by);
        return bl ? Byte.valueOf(by2) : null;
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        byte by = this.get(l);
        return by != this.defaultReturnValue() || this.containsKey(l) ? Byte.valueOf(by) : null;
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        return this.containsKey(l) ? Byte.valueOf(this.remove(l)) : null;
    }

    default public boolean containsKey(long l) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Long)object);
    }

    default public void defaultReturnValue(byte by) {
        throw new UnsupportedOperationException();
    }

    default public byte defaultReturnValue() {
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
        return this.put((Long)object, (Byte)object2);
    }
}

