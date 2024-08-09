/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Int2DoubleFunction
extends Function<Integer, Double>,
IntToDoubleFunction {
    @Override
    default public double applyAsDouble(int n) {
        return this.get(n);
    }

    @Override
    default public double put(int n, double d) {
        throw new UnsupportedOperationException();
    }

    public double get(int var1);

    default public double remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Double put(Integer n, Double d) {
        int n2 = n;
        boolean bl = this.containsKey(n2);
        double d2 = this.put(n2, (double)d);
        return bl ? Double.valueOf(d2) : null;
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        double d = this.get(n);
        return d != this.defaultReturnValue() || this.containsKey(n) ? Double.valueOf(d) : null;
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? Double.valueOf(this.remove(n)) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
    }

    default public void defaultReturnValue(double d) {
        throw new UnsupportedOperationException();
    }

    default public double defaultReturnValue() {
        return 0.0;
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
        return this.put((Integer)object, (Double)object2);
    }
}

