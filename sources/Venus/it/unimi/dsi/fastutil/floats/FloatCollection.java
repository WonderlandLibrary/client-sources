/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.FloatIterable;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatCollection
extends Collection<Float>,
FloatIterable {
    @Override
    public FloatIterator iterator();

    @Override
    public boolean add(float var1);

    public boolean contains(float var1);

    public boolean rem(float var1);

    @Override
    @Deprecated
    default public boolean add(Float f) {
        return this.add(f.floatValue());
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        if (object == null) {
            return true;
        }
        return this.contains(((Float)object).floatValue());
    }

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        if (object == null) {
            return true;
        }
        return this.rem(((Float)object).floatValue());
    }

    public float[] toFloatArray();

    @Deprecated
    public float[] toFloatArray(float[] var1);

    public float[] toArray(float[] var1);

    public boolean addAll(FloatCollection var1);

    public boolean containsAll(FloatCollection var1);

    public boolean removeAll(FloatCollection var1);

    @Override
    @Deprecated
    default public boolean removeIf(Predicate<? super Float> predicate) {
        return this.removeIf(arg_0 -> FloatCollection.lambda$removeIf$0(predicate, arg_0));
    }

    default public boolean removeIf(DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        boolean bl = false;
        FloatIterator floatIterator = this.iterator();
        while (floatIterator.hasNext()) {
            if (!doublePredicate.test(floatIterator.nextFloat())) continue;
            floatIterator.remove();
            bl = true;
        }
        return bl;
    }

    public boolean retainAll(FloatCollection var1);

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Float)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    private static boolean lambda$removeIf$0(Predicate predicate, double d) {
        return predicate.test(Float.valueOf(SafeMath.safeDoubleToFloat(d)));
    }
}

