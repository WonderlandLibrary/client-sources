/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteIterable;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteCollection
extends Collection<Byte>,
ByteIterable {
    @Override
    public ByteIterator iterator();

    @Override
    public boolean add(byte var1);

    public boolean contains(byte var1);

    public boolean rem(byte var1);

    @Override
    @Deprecated
    default public boolean add(Byte by) {
        return this.add((byte)by);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        if (object == null) {
            return true;
        }
        return this.contains((Byte)object);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        if (object == null) {
            return true;
        }
        return this.rem((Byte)object);
    }

    public byte[] toByteArray();

    @Deprecated
    public byte[] toByteArray(byte[] var1);

    public byte[] toArray(byte[] var1);

    public boolean addAll(ByteCollection var1);

    public boolean containsAll(ByteCollection var1);

    public boolean removeAll(ByteCollection var1);

    @Override
    @Deprecated
    default public boolean removeIf(Predicate<? super Byte> predicate) {
        return this.removeIf(arg_0 -> ByteCollection.lambda$removeIf$0(predicate, arg_0));
    }

    default public boolean removeIf(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        boolean bl = false;
        ByteIterator byteIterator = this.iterator();
        while (byteIterator.hasNext()) {
            if (!intPredicate.test(byteIterator.nextByte())) continue;
            byteIterator.remove();
            bl = true;
        }
        return bl;
    }

    public boolean retainAll(ByteCollection var1);

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Byte)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    private static boolean lambda$removeIf$0(Predicate predicate, int n) {
        return predicate.test(SafeMath.safeIntToByte(n));
    }
}

