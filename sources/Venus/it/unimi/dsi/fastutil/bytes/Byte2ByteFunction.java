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
public interface Byte2ByteFunction
extends Function<Byte, Byte>,
IntUnaryOperator {
    @Override
    @Deprecated
    default public int applyAsInt(int n) {
        return this.get(SafeMath.safeIntToByte(n));
    }

    @Override
    default public byte put(byte by, byte by2) {
        throw new UnsupportedOperationException();
    }

    public byte get(byte var1);

    default public byte remove(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Byte put(Byte by, Byte by2) {
        byte by3 = by;
        boolean bl = this.containsKey(by3);
        byte by4 = this.put(by3, (byte)by2);
        return bl ? Byte.valueOf(by4) : null;
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        byte by2 = this.get(by);
        return by2 != this.defaultReturnValue() || this.containsKey(by) ? Byte.valueOf(by2) : null;
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        return this.containsKey(by) ? Byte.valueOf(this.remove(by)) : null;
    }

    default public boolean containsKey(byte by) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Byte)object);
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
        return this.put((Byte)object, (Byte)object2);
    }
}

