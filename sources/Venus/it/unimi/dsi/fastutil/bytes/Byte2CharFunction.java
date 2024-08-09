/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Byte2CharFunction
extends Function<Byte, Character>,
IntUnaryOperator {
    @Override
    @Deprecated
    default public int applyAsInt(int n) {
        return this.get(SafeMath.safeIntToByte(n));
    }

    @Override
    default public char put(byte by, char c) {
        throw new UnsupportedOperationException();
    }

    public char get(byte var1);

    default public char remove(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Character put(Byte by, Character c) {
        byte by2 = by;
        boolean bl = this.containsKey(by2);
        char c2 = this.put(by2, c.charValue());
        return bl ? Character.valueOf(c2) : null;
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        char c = this.get(by);
        return c != this.defaultReturnValue() || this.containsKey(by) ? Character.valueOf(c) : null;
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        return this.containsKey(by) ? Character.valueOf(this.remove(by)) : null;
    }

    default public boolean containsKey(byte by) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Byte)object);
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
        return this.put((Byte)object, (Character)object2);
    }
}

