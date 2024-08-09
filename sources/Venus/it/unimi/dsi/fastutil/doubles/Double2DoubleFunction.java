/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Double2DoubleFunction
extends Function<Double, Double>,
DoubleUnaryOperator {
    @Override
    default public double applyAsDouble(double d) {
        return this.get(d);
    }

    @Override
    default public double put(double d, double d2) {
        throw new UnsupportedOperationException();
    }

    public double get(double var1);

    default public double remove(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Double put(Double d, Double d2) {
        double d3 = d;
        boolean bl = this.containsKey(d3);
        double d4 = this.put(d3, (double)d2);
        return bl ? Double.valueOf(d4) : null;
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        double d2 = this.get(d);
        return d2 != this.defaultReturnValue() || this.containsKey(d) ? Double.valueOf(d2) : null;
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        return this.containsKey(d) ? Double.valueOf(this.remove(d)) : null;
    }

    default public boolean containsKey(double d) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Double)object);
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
        return this.put((Double)object, (Double)object2);
    }
}

