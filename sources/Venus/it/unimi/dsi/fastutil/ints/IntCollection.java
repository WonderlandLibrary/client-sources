/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntIterable;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntCollection
extends Collection<Integer>,
IntIterable {
    @Override
    public IntIterator iterator();

    @Override
    public boolean add(int var1);

    public boolean contains(int var1);

    public boolean rem(int var1);

    @Override
    @Deprecated
    default public boolean add(Integer n) {
        return this.add((int)n);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        if (object == null) {
            return true;
        }
        return this.contains((Integer)object);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        if (object == null) {
            return true;
        }
        return this.rem((Integer)object);
    }

    public int[] toIntArray();

    @Deprecated
    public int[] toIntArray(int[] var1);

    public int[] toArray(int[] var1);

    public boolean addAll(IntCollection var1);

    public boolean containsAll(IntCollection var1);

    public boolean removeAll(IntCollection var1);

    @Override
    @Deprecated
    default public boolean removeIf(Predicate<? super Integer> predicate) {
        return this.removeIf(arg_0 -> IntCollection.lambda$removeIf$0(predicate, arg_0));
    }

    default public boolean removeIf(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        boolean bl = false;
        IntIterator intIterator = this.iterator();
        while (intIterator.hasNext()) {
            if (!intPredicate.test(intIterator.nextInt())) continue;
            intIterator.remove();
            bl = true;
        }
        return bl;
    }

    public boolean retainAll(IntCollection var1);

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Integer)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    private static boolean lambda$removeIf$0(Predicate predicate, int n) {
        return predicate.test(n);
    }
}

