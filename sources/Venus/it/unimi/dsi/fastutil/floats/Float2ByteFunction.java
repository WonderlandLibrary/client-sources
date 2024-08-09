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
public interface Float2ByteFunction
extends Function<Float, Byte>,
DoubleToIntFunction {
    @Override
    @Deprecated
    default public int applyAsInt(double d) {
        return this.get(SafeMath.safeDoubleToFloat(d));
    }

    @Override
    default public byte put(float f, byte by) {
        throw new UnsupportedOperationException();
    }

    public byte get(float var1);

    default public byte remove(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Byte put(Float f, Byte by) {
        float f2 = f.floatValue();
        boolean bl = this.containsKey(f2);
        byte by2 = this.put(f2, (byte)by);
        return bl ? Byte.valueOf(by2) : null;
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        byte by = this.get(f);
        return by != this.defaultReturnValue() || this.containsKey(f) ? Byte.valueOf(by) : null;
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        return this.containsKey(f) ? Byte.valueOf(this.remove(f)) : null;
    }

    default public boolean containsKey(float f) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Float)object).floatValue());
    }

    default public void defaultReturnValue(byte by) {
        throw new UnsupportedOperationException();
    }

    default public byte defaultReturnValue() {
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
        return this.put((Float)object, (Byte)object2);
    }
}

