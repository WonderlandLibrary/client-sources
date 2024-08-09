/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Char2CharFunction
extends Function<Character, Character>,
IntUnaryOperator {
    @Override
    @Deprecated
    default public int applyAsInt(int n) {
        return this.get(SafeMath.safeIntToChar(n));
    }

    @Override
    default public char put(char c, char c2) {
        throw new UnsupportedOperationException();
    }

    public char get(char var1);

    default public char remove(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Character put(Character c, Character c2) {
        char c3 = c.charValue();
        boolean bl = this.containsKey(c3);
        char c4 = this.put(c3, c2.charValue());
        return bl ? Character.valueOf(c4) : null;
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        char c2 = this.get(c);
        return c2 != this.defaultReturnValue() || this.containsKey(c) ? Character.valueOf(c2) : null;
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        return this.containsKey(c) ? Character.valueOf(this.remove(c)) : null;
    }

    default public boolean containsKey(char c) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Character)object).charValue());
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
        return this.put((Character)object, (Character)object2);
    }
}

