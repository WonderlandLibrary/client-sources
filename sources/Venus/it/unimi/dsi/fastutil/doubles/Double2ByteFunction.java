/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoubleToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Double2ByteFunction
extends Function<Double, Byte>,
DoubleToIntFunction {
    @Override
    default public int applyAsInt(double d) {
        return this.get(d);
    }

    @Override
    default public byte put(double d, byte by) {
        throw new UnsupportedOperationException();
    }

    public byte get(double var1);

    default public byte remove(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Byte put(Double d, Byte by) {
        double d2 = d;
        boolean bl = this.containsKey(d2);
        byte by2 = this.put(d2, (byte)by);
        return bl ? Byte.valueOf(by2) : null;
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        byte by = this.get(d);
        return by != this.defaultReturnValue() || this.containsKey(d) ? Byte.valueOf(by) : null;
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        return this.containsKey(d) ? Byte.valueOf(this.remove(d)) : null;
    }

    default public boolean containsKey(double d) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Double)object);
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
        return this.put((Double)object, (Byte)object2);
    }
}

