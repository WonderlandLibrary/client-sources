/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Double2LongFunction
extends Function<Double, Long>,
DoubleToLongFunction {
    @Override
    default public long applyAsLong(double d) {
        return this.get(d);
    }

    @Override
    default public long put(double d, long l) {
        throw new UnsupportedOperationException();
    }

    public long get(double var1);

    default public long remove(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Long put(Double d, Long l) {
        double d2 = d;
        boolean bl = this.containsKey(d2);
        long l2 = this.put(d2, (long)l);
        return bl ? Long.valueOf(l2) : null;
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        long l = this.get(d);
        return l != this.defaultReturnValue() || this.containsKey(d) ? Long.valueOf(l) : null;
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        return this.containsKey(d) ? Long.valueOf(this.remove(d)) : null;
    }

    default public boolean containsKey(double d) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Double)object);
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
        return this.put((Double)object, (Long)object2);
    }
}

