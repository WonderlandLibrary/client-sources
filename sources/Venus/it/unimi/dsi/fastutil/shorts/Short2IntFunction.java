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
public interface Short2IntFunction
extends Function<Short, Integer>,
IntUnaryOperator {
    @Override
    @Deprecated
    default public int applyAsInt(int n) {
        return this.get(SafeMath.safeIntToShort(n));
    }

    @Override
    default public int put(short s, int n) {
        throw new UnsupportedOperationException();
    }

    public int get(short var1);

    default public int remove(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Integer put(Short s, Integer n) {
        short s2 = s;
        boolean bl = this.containsKey(s2);
        int n2 = this.put(s2, (int)n);
        return bl ? Integer.valueOf(n2) : null;
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        int n = this.get(s);
        return n != this.defaultReturnValue() || this.containsKey(s) ? Integer.valueOf(n) : null;
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        if (object == null) {
            return null;
        }
        short s = (Short)object;
        return this.containsKey(s) ? Integer.valueOf(this.remove(s)) : null;
    }

    default public boolean containsKey(short s) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Short)object);
    }

    default public void defaultReturnValue(int n) {
        throw new UnsupportedOperationException();
    }

    default public int defaultReturnValue() {
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
        return this.put((Short)object, (Integer)object2);
    }
}

