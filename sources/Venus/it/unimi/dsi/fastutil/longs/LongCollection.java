/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongIterable;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongCollection
extends Collection<Long>,
LongIterable {
    @Override
    public LongIterator iterator();

    @Override
    public boolean add(long var1);

    public boolean contains(long var1);

    public boolean rem(long var1);

    @Override
    @Deprecated
    default public boolean add(Long l) {
        return this.add((long)l);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        if (object == null) {
            return true;
        }
        return this.contains((Long)object);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        if (object == null) {
            return true;
        }
        return this.rem((Long)object);
    }

    public long[] toLongArray();

    @Deprecated
    public long[] toLongArray(long[] var1);

    public long[] toArray(long[] var1);

    public boolean addAll(LongCollection var1);

    public boolean containsAll(LongCollection var1);

    public boolean removeAll(LongCollection var1);

    @Override
    @Deprecated
    default public boolean removeIf(Predicate<? super Long> predicate) {
        return this.removeIf(arg_0 -> LongCollection.lambda$removeIf$0(predicate, arg_0));
    }

    default public boolean removeIf(LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        boolean bl = false;
        LongIterator longIterator = this.iterator();
        while (longIterator.hasNext()) {
            if (!longPredicate.test(longIterator.nextLong())) continue;
            longIterator.remove();
            bl = true;
        }
        return bl;
    }

    public boolean retainAll(LongCollection var1);

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Long)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    private static boolean lambda$removeIf$0(Predicate predicate, long l) {
        return predicate.test(l);
    }
}

