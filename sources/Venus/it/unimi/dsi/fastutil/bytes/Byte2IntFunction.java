/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Byte2IntFunction
extends Function<Byte, Integer>,
IntUnaryOperator {
    @Override
    @Deprecated
    default public int applyAsInt(int n) {
        return this.get(SafeMath.safeIntToByte(n));
    }

    @Override
    default public int put(byte by, int n) {
        throw new UnsupportedOperationException();
    }

    public int get(byte var1);

    default public int remove(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Integer put(Byte by, Integer n) {
        byte by2 = by;
        boolean bl = this.containsKey(by2);
        int n2 = this.put(by2, (int)n);
        return bl ? Integer.valueOf(n2) : null;
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        int n = this.get(by);
        return n != this.defaultReturnValue() || this.containsKey(by) ? Integer.valueOf(n) : null;
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        return this.containsKey(by) ? Integer.valueOf(this.remove(by)) : null;
    }

    default public boolean containsKey(byte by) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Byte)object);
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
        return this.put((Byte)object, (Integer)object2);
    }
}

