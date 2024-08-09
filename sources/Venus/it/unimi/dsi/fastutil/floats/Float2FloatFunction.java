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
public interface Float2FloatFunction
extends Function<Float, Float>,
DoubleUnaryOperator {
    @Override
    @Deprecated
    default public double applyAsDouble(double d) {
        return this.get(SafeMath.safeDoubleToFloat(d));
    }

    @Override
    default public float put(float f, float f2) {
        throw new UnsupportedOperationException();
    }

    public float get(float var1);

    default public float remove(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Float put(Float f, Float f2) {
        float f3 = f.floatValue();
        boolean bl = this.containsKey(f3);
        float f4 = this.put(f3, f2.floatValue());
        return bl ? Float.valueOf(f4) : null;
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        float f2 = this.get(f);
        return f2 != this.defaultReturnValue() || this.containsKey(f) ? Float.valueOf(f2) : null;
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        return this.containsKey(f) ? Float.valueOf(this.remove(f)) : null;
    }

    default public boolean containsKey(float f) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Float)object).floatValue());
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
        return this.put((Float)object, (Float)object2);
    }
}

