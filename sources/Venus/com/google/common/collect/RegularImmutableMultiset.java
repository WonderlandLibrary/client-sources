/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true)
class RegularImmutableMultiset<E>
extends ImmutableMultiset<E> {
    static final RegularImmutableMultiset<Object> EMPTY = new RegularImmutableMultiset(ImmutableList.of());
    private final transient Multisets.ImmutableEntry<E>[] entries;
    private final transient Multisets.ImmutableEntry<E>[] hashTable;
    private final transient int size;
    private final transient int hashCode;
    @LazyInit
    private transient ImmutableSet<E> elementSet;

    RegularImmutableMultiset(Collection<? extends Multiset.Entry<? extends E>> collection) {
        int n = collection.size();
        Multisets.ImmutableEntry[] immutableEntryArray = new Multisets.ImmutableEntry[n];
        if (n == 0) {
            this.entries = immutableEntryArray;
            this.hashTable = null;
            this.size = 0;
            this.hashCode = 0;
            this.elementSet = ImmutableSet.of();
        } else {
            int n2 = Hashing.closedTableSize(n, 1.0);
            int n3 = n2 - 1;
            Multisets.ImmutableEntry[] immutableEntryArray2 = new Multisets.ImmutableEntry[n2];
            int n4 = 0;
            int n5 = 0;
            long l = 0L;
            for (Multiset.Entry<E> entry : collection) {
                Multisets.ImmutableEntry immutableEntry;
                E e = Preconditions.checkNotNull(entry.getElement());
                int n6 = entry.getCount();
                int n7 = e.hashCode();
                int n8 = Hashing.smear(n7) & n3;
                Multisets.ImmutableEntry immutableEntry2 = immutableEntryArray2[n8];
                if (immutableEntry2 == null) {
                    boolean bl = entry instanceof Multisets.ImmutableEntry && !(entry instanceof NonTerminalEntry);
                    immutableEntry = bl ? (Multisets.ImmutableEntry)entry : new Multisets.ImmutableEntry<E>(e, n6);
                } else {
                    immutableEntry = new NonTerminalEntry<E>(e, n6, immutableEntry2);
                }
                n5 += n7 ^ n6;
                immutableEntryArray[n4++] = immutableEntry;
                immutableEntryArray2[n8] = immutableEntry;
                l += (long)n6;
            }
            this.entries = immutableEntryArray;
            this.hashTable = immutableEntryArray2;
            this.size = Ints.saturatedCast(l);
            this.hashCode = n5;
        }
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    @Override
    public int count(@Nullable Object object) {
        Multisets.ImmutableEntry<E>[] immutableEntryArray = this.hashTable;
        if (object == null || immutableEntryArray == null) {
            return 1;
        }
        int n = Hashing.smearedHash(object);
        int n2 = immutableEntryArray.length - 1;
        for (Multisets.ImmutableEntry<E> immutableEntry = immutableEntryArray[n & n2]; immutableEntry != null; immutableEntry = immutableEntry.nextInBucket()) {
            if (!Objects.equal(object, immutableEntry.getElement())) continue;
            return immutableEntry.getCount();
        }
        return 1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ImmutableSet<E> elementSet() {
        ElementSet elementSet = this.elementSet;
        return elementSet == null ? (this.elementSet = new ElementSet(this, null)) : elementSet;
    }

    @Override
    Multiset.Entry<E> getEntry(int n) {
        return this.entries[n];
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public Set elementSet() {
        return this.elementSet();
    }

    static Multisets.ImmutableEntry[] access$100(RegularImmutableMultiset regularImmutableMultiset) {
        return regularImmutableMultiset.entries;
    }

    private final class ElementSet
    extends ImmutableSet.Indexed<E> {
        final RegularImmutableMultiset this$0;

        private ElementSet(RegularImmutableMultiset regularImmutableMultiset) {
            this.this$0 = regularImmutableMultiset;
        }

        @Override
        E get(int n) {
            return RegularImmutableMultiset.access$100(this.this$0)[n].getElement();
        }

        @Override
        public boolean contains(@Nullable Object object) {
            return this.this$0.contains(object);
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        public int size() {
            return RegularImmutableMultiset.access$100(this.this$0).length;
        }

        ElementSet(RegularImmutableMultiset regularImmutableMultiset, 1 var2_2) {
            this(regularImmutableMultiset);
        }
    }

    private static final class NonTerminalEntry<E>
    extends Multisets.ImmutableEntry<E> {
        private final Multisets.ImmutableEntry<E> nextInBucket;

        NonTerminalEntry(E e, int n, Multisets.ImmutableEntry<E> immutableEntry) {
            super(e, n);
            this.nextInBucket = immutableEntry;
        }

        @Override
        public Multisets.ImmutableEntry<E> nextInBucket() {
            return this.nextInBucket;
        }
    }
}

