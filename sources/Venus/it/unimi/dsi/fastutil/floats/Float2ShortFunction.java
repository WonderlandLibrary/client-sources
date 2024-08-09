/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.DoubleToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Float2ShortFunction
extends Function<Float, Short>,
DoubleToIntFunction {
    @Override
    @Deprecated
    default public int applyAsInt(double d) {
        return this.get(SafeMath.safeDoubleToFloat(d));
    }

    @Override
    default public short put(float f, short s) {
        throw new UnsupportedOperationException();
    }

    public short get(float var1);

    default public short remove(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Short put(Float f, Short s) {
        float f2 = f.floatValue();
        boolean bl = this.containsKey(f2);
        short s2 = this.put(f2, (short)s);
        return bl ? Short.valueOf(s2) : null;
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        short s = this.get(f);
        return s != this.defaultReturnValue() || this.containsKey(f) ? Short.valueOf(s) : null;
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        return this.containsKey(f) ? Short.valueOf(this.remove(f)) : null;
    }

    default public boolean containsKey(float f) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Float)object).floatValue());
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
        return this.put((Float)object, (Short)object2);
    }
}

