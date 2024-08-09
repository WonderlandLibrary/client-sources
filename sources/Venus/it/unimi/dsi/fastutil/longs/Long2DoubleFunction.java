/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Long2DoubleFunction
extends Function<Long, Double>,
LongToDoubleFunction {
    @Override
    default public double applyAsDouble(long l) {
        return this.get(l);
    }

    @Override
    default public double put(long l, double d) {
        throw new UnsupportedOperationException();
    }

    public double get(long var1);

    default public double remove(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Double put(Long l, Double d) {
        long l2 = l;
        boolean bl = this.containsKey(l2);
        double d2 = this.put(l2, (double)d);
        return bl ? Double.valueOf(d2) : null;
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        double d = this.get(l);
        return d != this.defaultReturnValue() || this.containsKey(l) ? Double.valueOf(d) : null;
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        return this.containsKey(l) ? Double.valueOf(this.remove(l)) : null;
    }

    default public boolean containsKey(long l) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Long)object);
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
        return this.put((Long)object, (Double)object2);
    }
}

