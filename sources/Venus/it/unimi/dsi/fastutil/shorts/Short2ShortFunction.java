/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Short2ShortFunction
extends Function<Short, Short>,
IntUnaryOperator {
    @Override
    @Deprecated
    default public int applyAsInt(int n) {
        return this.get(SafeMath.safeIntToShort(n));
    }

    @Override
    default public short put(short s, short s2) {
        throw new UnsupportedOperationException();
    }

    public short get(short var1);

    default public short remove(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Short put(Short s, Short s2) {
        short s3 = s;
        boolean bl = this.containsKey(s3);
        short s4 = this.put(s3, (short)s2);
        return bl ? Short.valueOf(s4) : null;
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        short s2 = this.get(s);
        return s2 != this.defaultReturnValue() || this.containsKey(s) ? Short.valueOf(s2) : null;
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        return this.containsKey(s) ? Short.valueOf(this.remove(s)) : null;
    }

    default public boolean containsKey(short s) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Short)object);
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
        return this.put((Short)object, (Short)object2);
    }
}

