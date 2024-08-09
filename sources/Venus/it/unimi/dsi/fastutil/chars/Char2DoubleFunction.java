/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Char2DoubleFunction
extends Function<Character, Double>,
IntToDoubleFunction {
    @Override
    @Deprecated
    default public double applyAsDouble(int n) {
        return this.get(SafeMath.safeIntToChar(n));
    }

    @Override
    default public double put(char c, double d) {
        throw new UnsupportedOperationException();
    }

    public double get(char var1);

    default public double remove(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Double put(Character c, Double d) {
        char c2 = c.charValue();
        boolean bl = this.containsKey(c2);
        double d2 = this.put(c2, (double)d);
        return bl ? Double.valueOf(d2) : null;
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        double d = this.get(c);
        return d != this.defaultReturnValue() || this.containsKey(c) ? Double.valueOf(d) : null;
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        return this.containsKey(c) ? Double.valueOf(this.remove(c)) : null;
    }

    default public boolean containsKey(char c) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Character)object).charValue());
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
        return this.put((Character)object, (Double)object2);
    }
}

