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
public interface Int2FloatFunction
extends Function<Integer, Float>,
IntToDoubleFunction {
    @Override
    default public double applyAsDouble(int n) {
        return this.get(n);
    }

    @Override
    default public float put(int n, float f) {
        throw new UnsupportedOperationException();
    }

    public float get(int var1);

    default public float remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Float put(Integer n, Float f) {
        int n2 = n;
        boolean bl = this.containsKey(n2);
        float f2 = this.put(n2, f.floatValue());
        return bl ? Float.valueOf(f2) : null;
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        float f = this.get(n);
        return f != this.defaultReturnValue() || this.containsKey(n) ? Float.valueOf(f) : null;
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? Float.valueOf(this.remove(n)) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
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
        return this.put((Integer)object, (Float)object2);
    }
}

