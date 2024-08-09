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
public interface Byte2ShortFunction
extends Function<Byte, Short>,
IntUnaryOperator {
    @Override
    @Deprecated
    default public int applyAsInt(int n) {
        return this.get(SafeMath.safeIntToByte(n));
    }

    @Override
    default public short put(byte by, short s) {
        throw new UnsupportedOperationException();
    }

    public short get(byte var1);

    default public short remove(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Short put(Byte by, Short s) {
        byte by2 = by;
        boolean bl = this.containsKey(by2);
        short s2 = this.put(by2, (short)s);
        return bl ? Short.valueOf(s2) : null;
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        short s = this.get(by);
        return s != this.defaultReturnValue() || this.containsKey(by) ? Short.valueOf(s) : null;
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        return this.containsKey(by) ? Short.valueOf(this.remove(by)) : null;
    }

    default public boolean containsKey(byte by) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Byte)object);
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
        return this.put((Byte)object, (Short)object2);
    }
}

