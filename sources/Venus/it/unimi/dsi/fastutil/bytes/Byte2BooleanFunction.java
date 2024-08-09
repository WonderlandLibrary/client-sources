/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntPredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Byte2BooleanFunction
extends Function<Byte, Boolean>,
IntPredicate {
    @Override
    @Deprecated
    default public boolean test(int n) {
        return this.get(SafeMath.safeIntToByte(n));
    }

    @Override
    default public boolean put(byte by, boolean bl) {
        throw new UnsupportedOperationException();
    }

    public boolean get(byte var1);

    default public boolean remove(byte by) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Boolean put(Byte by, Boolean bl) {
        byte by2 = by;
        boolean bl2 = this.containsKey(by2);
        boolean bl3 = this.put(by2, (boolean)bl);
        return bl2 ? Boolean.valueOf(bl3) : null;
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        boolean bl = this.get(by);
        return bl != this.defaultReturnValue() || this.containsKey(by) ? Boolean.valueOf(bl) : null;
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        if (object == null) {
            return null;
        }
        byte by = (Byte)object;
        return this.containsKey(by) ? Boolean.valueOf(this.remove(by)) : null;
    }

    default public boolean containsKey(byte by) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Byte)object);
    }

    default public void defaultReturnValue(boolean bl) {
        throw new UnsupportedOperationException();
    }

    default public boolean defaultReturnValue() {
        return true;
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
        return this.put((Byte)object, (Boolean)object2);
    }
}

