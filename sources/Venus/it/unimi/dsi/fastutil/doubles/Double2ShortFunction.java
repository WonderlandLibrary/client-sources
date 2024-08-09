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
public interface Double2ShortFunction
extends Function<Double, Short>,
DoubleToIntFunction {
    @Override
    default public int applyAsInt(double d) {
        return this.get(d);
    }

    @Override
    default public short put(double d, short s) {
        throw new UnsupportedOperationException();
    }

    public short get(double var1);

    default public short remove(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Short put(Double d, Short s) {
        double d2 = d;
        boolean bl = this.containsKey(d2);
        short s2 = this.put(d2, (short)s);
        return bl ? Short.valueOf(s2) : null;
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        short s = this.get(d);
        return s != this.defaultReturnValue() || this.containsKey(d) ? Short.valueOf(s) : null;
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        return this.containsKey(d) ? Short.valueOf(this.remove(d)) : null;
    }

    default public boolean containsKey(double d) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Double)object);
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
        return this.put((Double)object, (Short)object2);
    }
}

