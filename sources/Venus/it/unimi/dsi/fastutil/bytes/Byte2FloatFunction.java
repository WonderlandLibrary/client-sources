/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Byte2FloatFunction
extends Function<Byte, Float>,
IntToDoubleFunction {
    @Override
    @Deprecated
    default public double applyAsDouble(int n) {
        return this.get(SafeMath.safeIntToByte(n));
    }

    @Override
    default public float put(byte by, float f) {
        throw new UnsupportedOperationException();
    }

    public float get(byte var1);

    default public float remove(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Float put(Byte by, Float f) {
        byte by2 = by;
        boolean bl = this.containsKey(by2);
        float f2 = this.put(by2, f.floatValue());
        return bl ? Float.valueOf(f2) : null;
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        float f = this.get(by);
        return f != this.defaultReturnValue() || this.containsKey(by) ? Float.valueOf(f) : null;
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        return this.containsKey(by) ? Float.valueOf(this.remove(by)) : null;
    }

    default public boolean containsKey(byte by) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Byte)object);
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
        return this.put((Byte)object, (Float)object2);
    }
}

