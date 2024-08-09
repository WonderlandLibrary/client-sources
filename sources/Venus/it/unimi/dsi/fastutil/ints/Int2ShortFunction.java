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
public interface Int2ShortFunction
extends Function<Integer, Short>,
IntUnaryOperator {
    @Override
    default public int applyAsInt(int n) {
        return this.get(n);
    }

    @Override
    default public short put(int n, short s) {
        throw new UnsupportedOperationException();
    }

    public short get(int var1);

    default public short remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Short put(Integer n, Short s) {
        int n2 = n;
        boolean bl = this.containsKey(n2);
        short s2 = this.put(n2, (short)s);
        return bl ? Short.valueOf(s2) : null;
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        short s = this.get(n);
        return s != this.defaultReturnValue() || this.containsKey(n) ? Short.valueOf(s) : null;
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? Short.valueOf(this.remove(n)) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
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
        return this.put((Integer)object, (Short)object2);
    }
}

