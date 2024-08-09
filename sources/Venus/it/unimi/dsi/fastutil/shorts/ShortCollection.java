/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.shorts.ShortIterable;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortCollection
extends Collection<Short>,
ShortIterable {
    @Override
    public ShortIterator iterator();

    @Override
    public boolean add(short var1);

    public boolean contains(short var1);

    public boolean rem(short var1);

    @Override
    @Deprecated
    default public boolean add(Short s) {
        return this.add((short)s);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        if (object == null) {
            return true;
        }
        return this.contains((Short)object);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        if (object == null) {
            return true;
        }
        return this.rem((Short)object);
    }

    public short[] toShortArray();

    @Deprecated
    public short[] toShortArray(short[] var1);

    public short[] toArray(short[] var1);

    public boolean addAll(ShortCollection var1);

    public boolean containsAll(ShortCollection var1);

    public boolean removeAll(ShortCollection var1);

    @Override
    @Deprecated
    default public boolean removeIf(Predicate<? super Short> predicate) {
        return this.removeIf(arg_0 -> ShortCollection.lambda$removeIf$0(predicate, arg_0));
    }

    default public boolean removeIf(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        boolean bl = false;
        ShortIterator shortIterator = this.iterator();
        while (shortIterator.hasNext()) {
            if (!intPredicate.test(shortIterator.nextShort())) continue;
            shortIterator.remove();
            bl = true;
        }
        return bl;
    }

    public boolean retainAll(ShortCollection var1);

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Short)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    private static boolean lambda$removeIf$0(Predicate predicate, int n) {
        return predicate.test(SafeMath.safeIntToShort(n));
    }
}

