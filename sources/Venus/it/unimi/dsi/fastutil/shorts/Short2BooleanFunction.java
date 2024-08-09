/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntPredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Short2BooleanFunction
extends Function<Short, Boolean>,
IntPredicate {
    @Override
    @Deprecated
    default public boolean test(int n) {
        return this.get(SafeMath.safeIntToShort(n));
    }

    @Override
    default public boolean put(short s, boolean bl) {
        throw new UnsupportedOperationException();
    }

    public boolean get(short var1);

    default public boolean remove(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Boolean put(Short s, Boolean bl) {
        short s2 = s;
        boolean bl2 = this.containsKey(s2);
        boolean bl3 = this.put(s2, (boolean)bl);
        return bl2 ? Boolean.valueOf(bl3) : null;
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        boolean bl = this.get(s);
        return bl != this.defaultReturnValue() || this.containsKey(s) ? Boolean.valueOf(bl) : null;
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        return this.containsKey(s) ? Boolean.valueOf(this.remove(s)) : null;
    }

    default public boolean containsKey(short s) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Short)object);
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
        return this.put((Short)object, (Boolean)object2);
    }
}

