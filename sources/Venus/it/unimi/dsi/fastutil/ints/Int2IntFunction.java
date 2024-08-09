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
public interface Int2IntFunction
extends Function<Integer, Integer>,
IntUnaryOperator {
    @Override
    default public int applyAsInt(int n) {
        return this.get(n);
    }

    @Override
    default public int put(int n, int n2) {
        throw new UnsupportedOperationException();
    }

    public int get(int var1);

    default public int remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Integer put(Integer n, Integer n2) {
        int n3 = n;
        boolean bl = this.containsKey(n3);
        int n4 = this.put(n3, (int)n2);
        return bl ? Integer.valueOf(n4) : null;
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        int n2 = this.get(n);
        return n2 != this.defaultReturnValue() || this.containsKey(n) ? Integer.valueOf(n2) : null;
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? Integer.valueOf(this.remove(n)) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
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
        return this.put((Integer)object, (Integer)object2);
    }
}

