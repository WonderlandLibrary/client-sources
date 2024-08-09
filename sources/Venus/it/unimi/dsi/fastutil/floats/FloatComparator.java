/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import java.util.Comparator;

@FunctionalInterface
public interface FloatComparator
extends Comparator<Float> {
    @Override
    public int compare(float var1, float var2);

    @Override
    @Deprecated
    default public int compare(Float f, Float f2) {
        return this.compare(f.floatValue(), f2.floatValue());
    }

    @Override
    @Deprecated
    default public int compare(Object object, Object object2) {
        return this.compare((Float)object, (Float)object2);
    }
}

