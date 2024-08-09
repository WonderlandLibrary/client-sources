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
public interface Char2ShortFunction
extends Function<Character, Short>,
IntUnaryOperator {
    @Override
    @Deprecated
    default public int applyAsInt(int n) {
        return this.get(SafeMath.safeIntToChar(n));
    }

    @Override
    default public short put(char c, short s) {
        throw new UnsupportedOperationException();
    }

    public short get(char var1);

    default public short remove(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Short put(Character c, Short s) {
        char c2 = c.charValue();
        boolean bl = this.containsKey(c2);
        short s2 = this.put(c2, (short)s);
        return bl ? Short.valueOf(s2) : null;
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        short s = this.get(c);
        return s != this.defaultReturnValue() || this.containsKey(c) ? Short.valueOf(s) : null;
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        return this.containsKey(c) ? Short.valueOf(this.remove(c)) : null;
    }

    default public boolean containsKey(char c) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Character)object).charValue());
    }

    default public void defaultReturnValue(short s) {
        throw new UnsupportedOperationException();
    }

    default public short defaultReturnValue() {
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
        return this.put((Character)object, (Short)object2);
    }
}

