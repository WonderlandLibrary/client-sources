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
public interface Double2CharFunction
extends Function<Double, Character>,
DoubleToIntFunction {
    @Override
    default public int applyAsInt(double d) {
        return this.get(d);
    }

    @Override
    default public char put(double d, char c) {
        throw new UnsupportedOperationException();
    }

    public char get(double var1);

    default public char remove(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Character put(Double d, Character c) {
        double d2 = d;
        boolean bl = this.containsKey(d2);
        char c2 = this.put(d2, c.charValue());
        return bl ? Character.valueOf(c2) : null;
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        char c = this.get(d);
        return c != this.defaultReturnValue() || this.containsKey(d) ? Character.valueOf(c) : null;
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        return this.containsKey(d) ? Character.valueOf(this.remove(d)) : null;
    }

    default public boolean containsKey(double d) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Double)object);
    }

    default public void defaultReturnValue(char c) {
        throw new UnsupportedOperationException();
    }

    default public char defaultReturnValue() {
        return '\u0001';
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
        return this.put((Double)object, (Character)object2);
    }
}

