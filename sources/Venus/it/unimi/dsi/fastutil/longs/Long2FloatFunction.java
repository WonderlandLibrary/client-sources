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
public interface Long2FloatFunction
extends Function<Long, Float>,
LongToDoubleFunction {
    @Override
    default public double applyAsDouble(long l) {
        return this.get(l);
    }

    @Override
    default public float put(long l, float f) {
        throw new UnsupportedOperationException();
    }

    public float get(long var1);

    default public float remove(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Float put(Long l, Float f) {
        long l2 = l;
        boolean bl = this.containsKey(l2);
        float f2 = this.put(l2, f.floatValue());
        return bl ? Float.valueOf(f2) : null;
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        float f = this.get(l);
        return f != this.defaultReturnValue() || this.containsKey(l) ? Float.valueOf(f) : null;
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        return this.containsKey(l) ? Float.valueOf(this.remove(l)) : null;
    }

    default public boolean containsKey(long l) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Long)object);
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
        return this.put((Long)object, (Float)object2);
    }
}

