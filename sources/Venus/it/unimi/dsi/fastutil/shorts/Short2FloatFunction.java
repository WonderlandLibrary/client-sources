/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Short2FloatFunction
extends Function<Short, Float>,
IntToDoubleFunction {
    @Override
    @Deprecated
    default public double applyAsDouble(int n) {
        return this.get(SafeMath.safeIntToShort(n));
    }

    @Override
    default public float put(short s, float f) {
        throw new UnsupportedOperationException();
    }

    public float get(short var1);

    default public float remove(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Float put(Short s, Float f) {
        short s2 = s;
        boolean bl = this.containsKey(s2);
        float f2 = this.put(s2, f.floatValue());
        return bl ? Float.valueOf(f2) : null;
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        float f = this.get(s);
        return f != this.defaultReturnValue() || this.containsKey(s) ? Float.valueOf(f) : null;
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        return this.containsKey(s) ? Float.valueOf(this.remove(s)) : null;
    }

    default public boolean containsKey(short s) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Short)object);
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
        return this.put((Short)object, (Float)object2);
    }
}

