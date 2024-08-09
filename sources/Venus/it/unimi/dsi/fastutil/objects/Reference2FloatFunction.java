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
public interface Reference2FloatFunction<K>
extends Function<K, Float>,
ToDoubleFunction<K> {
    @Override
    default public double applyAsDouble(K k) {
        return this.getFloat(k);
    }

    @Override
    default public float put(K k, float f) {
        throw new UnsupportedOperationException();
    }

    public float getFloat(Object var1);

    default public float removeFloat(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Float put(K k, Float f) {
        K k2 = k;
        boolean bl = this.containsKey(k2);
        float f2 = this.put(k2, f.floatValue());
        return bl ? Float.valueOf(f2) : null;
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        Object object2 = object;
        float f = this.getFloat(object2);
        return f != this.defaultReturnValue() || this.containsKey(object2) ? Float.valueOf(f) : null;
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        Object object2 = object;
        return this.containsKey(object2) ? Float.valueOf(this.removeFloat(object2)) : null;
    }

    default public void defaultReturnValue(float f) {
        throw new UnsupportedOperationException();
    }

    default public float defaultReturnValue() {
        return 0.0f;
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
        return this.put((K)object, (Float)object2);
    }
}

