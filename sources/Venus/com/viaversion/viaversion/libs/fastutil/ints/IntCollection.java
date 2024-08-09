/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterable;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntPredicate;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntCollection
extends Collection<Integer>,
IntIterable {
    @Override
    public IntIterator iterator();

    @Override
    default public IntIterator intIterator() {
        return this.iterator();
    }

    @Override
    default public IntSpliterator spliterator() {
        return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 320);
    }

    @Override
    default public IntSpliterator intSpliterator() {
        return this.spliterator();
    }

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
    default public int[] toIntArray(int[] nArray) {
        return this.toArray(nArray);
    }

    public int[] toArray(int[] var1);

    public boolean addAll(IntCollection var1);

    public boolean containsAll(IntCollection var1);

    public boolean removeAll(IntCollection var1);

    @Override
    @Deprecated
    default public boolean removeIf(Predicate<? super Integer> predicate) {
        return this.removeIf(predicate instanceof java.util.function.IntPredicate ? (java.util.function.IntPredicate)((Object)predicate) : arg_0 -> IntCollection.lambda$removeIf$0(predicate, arg_0));
    }

    default public boolean removeIf(java.util.function.IntPredicate intPredicate) {
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

    default public boolean removeIf(IntPredicate intPredicate) {
        return this.removeIf((java.util.function.IntPredicate)intPredicate);
    }

    public boolean retainAll(IntCollection var1);

    @Override
    @Deprecated
    default public Stream<Integer> stream() {
        return Collection.super.stream();
    }

    default public IntStream intStream() {
        return StreamSupport.intStream(this.intSpliterator(), false);
    }

    @Override
    @Deprecated
    default public Stream<Integer> parallelStream() {
        return Collection.super.parallelStream();
    }

    default public IntStream intParallelStream() {
        return StreamSupport.intStream(this.intSpliterator(), true);
    }

    @Override
    default public Spliterator spliterator() {
        return this.spliterator();
    }

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

