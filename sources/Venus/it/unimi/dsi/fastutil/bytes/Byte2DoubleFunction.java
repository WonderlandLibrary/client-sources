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
public interface Byte2DoubleFunction
extends Function<Byte, Double>,
IntToDoubleFunction {
    @Override
    @Deprecated
    default public double applyAsDouble(int n) {
        return this.get(SafeMath.safeIntToByte(n));
    }

    @Override
    default public double put(byte by, double d) {
        throw new UnsupportedOperationException();
    }

    public double get(byte var1);

    default public double remove(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Double put(Byte by, Double d) {
        byte by2 = by;
        boolean bl = this.containsKey(by2);
        double d2 = this.put(by2, (double)d);
        return bl ? Double.valueOf(d2) : null;
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        double d = this.get(by);
        return d != this.defaultReturnValue() || this.containsKey(by) ? Double.valueOf(d) : null;
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        return this.containsKey(by) ? Double.valueOf(this.remove(by)) : null;
    }

    default public boolean containsKey(byte by) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Byte)object);
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
        return this.put((Byte)object, (Double)object2);
    }
}

