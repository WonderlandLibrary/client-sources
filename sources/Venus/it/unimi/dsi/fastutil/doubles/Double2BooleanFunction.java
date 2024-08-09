/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Function;
import java.util.function.DoublePredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Double2BooleanFunction
extends Function<Double, Boolean>,
DoublePredicate {
    @Override
    default public boolean test(double d) {
        return this.get(d);
    }

    @Override
    default public boolean put(double d, boolean bl) {
        throw new UnsupportedOperationException();
    }

    public boolean get(double var1);

    default public boolean remove(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Boolean put(Double d, Boolean bl) {
        double d2 = d;
        boolean bl2 = this.containsKey(d2);
        boolean bl3 = this.put(d2, (boolean)bl);
        return bl2 ? Boolean.valueOf(bl3) : null;
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        boolean bl = this.get(d);
        return bl != this.defaultReturnValue() || this.containsKey(d) ? Boolean.valueOf(bl) : null;
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        if (object == null) {
            return null;
        }
        double d = (Double)object;
        return this.containsKey(d) ? Boolean.valueOf(this.remove(d)) : null;
    }

    default public boolean containsKey(double d) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Double)object);
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
        return this.put((Double)object, (Boolean)object2);
    }
}

