/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Int2CharFunction
extends Function<Integer, Character>,
IntUnaryOperator {
    @Override
    default public int applyAsInt(int n) {
        return this.get(n);
    }

    @Override
    default public char put(int n, char c) {
        throw new UnsupportedOperationException();
    }

    public char get(int var1);

    default public char remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Character put(Integer n, Character c) {
        int n2 = n;
        boolean bl = this.containsKey(n2);
        char c2 = this.put(n2, c.charValue());
        return bl ? Character.valueOf(c2) : null;
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        char c = this.get(n);
        return c != this.defaultReturnValue() || this.containsKey(n) ? Character.valueOf(c) : null;
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? Character.valueOf(this.remove(n)) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
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
        return this.put((Integer)object, (Character)object2);
    }
}

