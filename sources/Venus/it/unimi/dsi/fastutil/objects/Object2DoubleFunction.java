/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Object2DoubleFunction<K>
extends Function<K, Double>,
ToDoubleFunction<K> {
    @Override
    default public double applyAsDouble(K k) {
        return this.getDouble(k);
    }

    @Override
    default public double put(K k, double d) {
        throw new UnsupportedOperationException();
    }

    public double getDouble(Object var1);

    default public double removeDouble(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Double put(K k, Double d) {
        K k2 = k;
        boolean bl = this.containsKey(k2);
        double d2 = this.put(k2, (double)d);
        return bl ? Double.valueOf(d2) : null;
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        Object object2 = object;
        double d = this.getDouble(object2);
        return d != this.defaultReturnValue() || this.containsKey(object2) ? Double.valueOf(d) : null;
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        Object object2 = object;
        return this.containsKey(object2) ? Double.valueOf(this.removeDouble(object2)) : null;
    }

    default public void defaultReturnValue(double d) {
        throw new UnsupportedOperationException();
    }

    default public double defaultReturnValue() {
        return 0.0;
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
        return this.put((K)object, (Double)object2);
    }
}

