/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Function;
import java.util.function.IntPredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Int2BooleanFunction
extends Function<Integer, Boolean>,
IntPredicate {
    @Override
    default public boolean test(int n) {
        return this.get(n);
    }

    @Override
    default public boolean put(int n, boolean bl) {
        throw new UnsupportedOperationException();
    }

    public boolean get(int var1);

    default public boolean remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Boolean put(Integer n, Boolean bl) {
        int n2 = n;
        boolean bl2 = this.containsKey(n2);
        boolean bl3 = this.put(n2, (boolean)bl);
        return bl2 ? Boolean.valueOf(bl3) : null;
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        boolean bl = this.get(n);
        return bl != this.defaultReturnValue() || this.containsKey(n) ? Boolean.valueOf(bl) : null;
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? Boolean.valueOf(this.remove(n)) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
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
        return this.put((Integer)object, (Boolean)object2);
    }
}

