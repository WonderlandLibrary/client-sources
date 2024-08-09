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
public interface Float2CharFunction
extends Function<Float, Character>,
DoubleToIntFunction {
    @Override
    @Deprecated
    default public int applyAsInt(double d) {
        return this.get(SafeMath.safeDoubleToFloat(d));
    }

    @Override
    default public char put(float f, char c) {
        throw new UnsupportedOperationException();
    }

    public char get(float var1);

    default public char remove(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Character put(Float f, Character c) {
        float f2 = f.floatValue();
        boolean bl = this.containsKey(f2);
        char c2 = this.put(f2, c.charValue());
        return bl ? Character.valueOf(c2) : null;
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        char c = this.get(f);
        return c != this.defaultReturnValue() || this.containsKey(f) ? Character.valueOf(c) : null;
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        if (object == null) {
            return null;
        }
        float f = ((Float)object).floatValue();
        return this.containsKey(f) ? Character.valueOf(this.remove(f)) : null;
    }

    default public boolean containsKey(float f) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Float)object).floatValue());
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
        return this.put((Float)object, (Character)object2);
    }
}

