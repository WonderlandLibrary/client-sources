/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoubleUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Float2DoubleFunction
extends Function<Float, Double>,
DoubleUnaryOperator {
    @Override
    @Deprecated
    default public double applyAsDouble(double d) {
        return this.get(SafeMath.safeDoubleToFloat(d));
    }

    @Override
    default public double put(float f, double d) {
        throw new UnsupportedOperationException();
    }

    public double get(float var1);

    default public double remove(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Double put(Float f, Double d) {
        float f2 = f.floatValue();
        boolean bl = this.containsKey(f2);
        double d2 = this.put(f2, (double)d);
        return bl ? Double.valueOf(d2) : null;
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        double d = this.get(f);
        return d != this.defaultReturnValue() || this.containsKey(f) ? Double.valueOf(d) : null;
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        return this.containsKey(f) ? Double.valueOf(this.remove(f)) : null;
    }

    default public boolean containsKey(float f) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Float)object).floatValue());
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
        return this.put((Float)object, (Double)object2);
    }
}

