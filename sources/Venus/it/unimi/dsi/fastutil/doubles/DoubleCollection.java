/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleIterable;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface DoubleCollection
extends Collection<Double>,
DoubleIterable {
    @Override
    public DoubleIterator iterator();

    @Override
    public boolean add(double var1);

    public boolean contains(double var1);

    public boolean rem(double var1);

    @Override
    @Deprecated
    default public boolean add(Double d) {
        return this.add((double)d);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        if (object == null) {
            return true;
        }
        return this.contains((Double)object);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        if (object == null) {
            return true;
        }
        return this.rem((Double)object);
    }

    public double[] toDoubleArray();

    @Deprecated
    public double[] toDoubleArray(double[] var1);

    public double[] toArray(double[] var1);

    public boolean addAll(DoubleCollection var1);

    public boolean containsAll(DoubleCollection var1);

    public boolean removeAll(DoubleCollection var1);

    @Override
    @Deprecated
    default public boolean removeIf(Predicate<? super Double> predicate) {
        return this.removeIf(arg_0 -> DoubleCollection.lambda$removeIf$0(predicate, arg_0));
    }

    default public boolean removeIf(DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        boolean bl = false;
        DoubleIterator doubleIterator = this.iterator();
        while (doubleIterator.hasNext()) {
            if (!doublePredicate.test(doubleIterator.nextDouble())) continue;
            doubleIterator.remove();
            bl = true;
        }
        return bl;
    }

    public boolean retainAll(DoubleCollection var1);

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Double)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    private static boolean lambda$removeIf$0(Predicate predicate, double d) {
        return predicate.test(d);
    }
}

