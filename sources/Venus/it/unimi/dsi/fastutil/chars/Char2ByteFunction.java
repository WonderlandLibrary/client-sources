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
public interface Char2ByteFunction
extends Function<Character, Byte>,
IntUnaryOperator {
    @Override
    @Deprecated
    default public int applyAsInt(int n) {
        return this.get(SafeMath.safeIntToChar(n));
    }

    @Override
    default public byte put(char c, byte by) {
        throw new UnsupportedOperationException();
    }

    public byte get(char var1);

    default public byte remove(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Byte put(Character c, Byte by) {
        char c2 = c.charValue();
        boolean bl = this.containsKey(c2);
        byte by2 = this.put(c2, (byte)by);
        return bl ? Byte.valueOf(by2) : null;
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        byte by = this.get(c);
        return by != this.defaultReturnValue() || this.containsKey(c) ? Byte.valueOf(by) : null;
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        return this.containsKey(c) ? Byte.valueOf(this.remove(c)) : null;
    }

    default public boolean containsKey(char c) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Character)object).charValue());
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
        return this.put((Character)object, (Byte)object2);
    }
}

