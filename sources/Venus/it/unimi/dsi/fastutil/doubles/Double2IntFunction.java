/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Double2IntFunction
extends Function<Double, Integer>,
DoubleToIntFunction {
    @Override
    default public int applyAsInt(double d) {
        return this.get(d);
    }

    @Override
    default public int put(double d, int n) {
        throw new UnsupportedOperationException();
    }

    public int get(double var1);

    default public int remove(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Integer put(Double d, Integer n) {
        double d2 = d;
        boolean bl = this.containsKey(d2);
        int n2 = this.put(d2, (int)n);
        return bl ? Integer.valueOf(n2) : null;
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        int n = this.get(d);
        return n != this.defaultReturnValue() || this.containsKey(d) ? Integer.valueOf(n) : null;
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        return this.containsKey(d) ? Integer.valueOf(this.remove(d)) : null;
    }

    default public boolean containsKey(double d) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Double)object);
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
        return this.put((Double)object, (Integer)object2);
    }
}

