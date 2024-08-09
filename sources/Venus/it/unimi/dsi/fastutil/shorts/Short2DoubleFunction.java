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
public interface Short2DoubleFunction
extends Function<Short, Double>,
IntToDoubleFunction {
    @Override
    @Deprecated
    default public double applyAsDouble(int n) {
        return this.get(SafeMath.safeIntToShort(n));
    }

    @Override
    default public double put(short s, double d) {
        throw new UnsupportedOperationException();
    }

    public double get(short var1);

    default public double remove(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Double put(Short s, Double d) {
        short s2 = s;
        boolean bl = this.containsKey(s2);
        double d2 = this.put(s2, (double)d);
        return bl ? Double.valueOf(d2) : null;
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        double d = this.get(s);
        return d != this.defaultReturnValue() || this.containsKey(s) ? Double.valueOf(d) : null;
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        return this.containsKey(s) ? Double.valueOf(this.remove(s)) : null;
    }

    default public boolean containsKey(short s) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Short)object);
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
        return this.put((Short)object, (Double)object2);
    }
}

