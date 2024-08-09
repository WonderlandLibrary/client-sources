/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoubleToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Float2LongFunction
extends Function<Float, Long>,
DoubleToLongFunction {
    @Override
    @Deprecated
    default public long applyAsLong(double d) {
        return this.get(SafeMath.safeDoubleToFloat(d));
    }

    @Override
    default public long put(float f, long l) {
        throw new UnsupportedOperationException();
    }

    public long get(float var1);

    default public long remove(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Long put(Float f, Long l) {
        float f2 = f.floatValue();
        boolean bl = this.containsKey(f2);
        long l2 = this.put(f2, (long)l);
        return bl ? Long.valueOf(l2) : null;
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        long l = this.get(f);
        return l != this.defaultReturnValue() || this.containsKey(f) ? Long.valueOf(l) : null;
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        return this.containsKey(f) ? Long.valueOf(this.remove(f)) : null;
    }

    default public boolean containsKey(float f) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Float)object).floatValue());
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
        return this.put((Float)object, (Long)object2);
    }
}

