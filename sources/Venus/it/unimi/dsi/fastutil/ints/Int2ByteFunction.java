/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Int2ByteFunction
extends Function<Integer, Byte>,
IntUnaryOperator {
    @Override
    default public int applyAsInt(int n) {
        return this.get(n);
    }

    @Override
    default public byte put(int n, byte by) {
        throw new UnsupportedOperationException();
    }

    public byte get(int var1);

    default public byte remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Byte put(Integer n, Byte by) {
        int n2 = n;
        boolean bl = this.containsKey(n2);
        byte by2 = this.put(n2, (byte)by);
        return bl ? Byte.valueOf(by2) : null;
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        byte by = this.get(n);
        return by != this.defaultReturnValue() || this.containsKey(n) ? Byte.valueOf(by) : null;
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? Byte.valueOf(this.remove(n)) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
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
        return this.put((Integer)object, (Byte)object2);
    }
}

