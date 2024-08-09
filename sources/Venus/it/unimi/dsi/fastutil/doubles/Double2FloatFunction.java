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
public interface Double2FloatFunction
extends Function<Double, Float>,
DoubleUnaryOperator {
    @Override
    default public double applyAsDouble(double d) {
        return this.get(d);
    }

    @Override
    default public float put(double d, float f) {
        throw new UnsupportedOperationException();
    }

    public float get(double var1);

    default public float remove(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Float put(Double d, Float f) {
        double d2 = d;
        boolean bl = this.containsKey(d2);
        float f2 = this.put(d2, f.floatValue());
        return bl ? Float.valueOf(f2) : null;
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        float f = this.get(d);
        return f != this.defaultReturnValue() || this.containsKey(d) ? Float.valueOf(f) : null;
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        return this.containsKey(d) ? Float.valueOf(this.remove(d)) : null;
    }

    default public boolean containsKey(double d) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Double)object);
    }

    default public void defaultReturnValue(float f) {
        throw new UnsupportedOperationException();
    }

    default public float defaultReturnValue() {
        return 0.0f;
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
        return this.put((Double)object, (Float)object2);
    }
}

