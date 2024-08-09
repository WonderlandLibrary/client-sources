/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectCollectors;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableEnumSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.RegularImmutableAsList;
import com.google.common.collect.RegularImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.SingletonImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collector;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
public abstract class ImmutableSet<E>
extends ImmutableCollection<E>
implements Set<E> {
    static final int SPLITERATOR_CHARACTERISTICS = 1297;
    static final int MAX_TABLE_SIZE = 0x40000000;
    private static final double DESIRED_LOAD_FACTOR = 0.7;
    private static final int CUTOFF = 0x2CCCCCCC;
    @LazyInit
    @RetainedWith
    private transient ImmutableList<E> asList;

    @Beta
    public static <E> Collector<E, ?, ImmutableSet<E>> toImmutableSet() {
        return CollectCollectors.toImmutableSet();
    }

    public static <E> ImmutableSet<E> of() {
        return RegularImmutableSet.EMPTY;
    }

    public static <E> ImmutableSet<E> of(E e) {
        return new SingletonImmutableSet<E>(e);
    }

    public static <E> ImmutableSet<E> of(E e, E e2) {
        return ImmutableSet.construct(2, e, e2);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3) {
        return ImmutableSet.construct(3, e, e2, e3);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4) {
        return ImmutableSet.construct(4, e, e2, e3, e4);
    }

    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5) {
        return ImmutableSet.construct(5, e, e2, e3, e4, e5);
    }

    @SafeVarargs
    public static <E> ImmutableSet<E> of(E e, E e2, E e3, E e4, E e5, E e6, E ... EArray) {
        int n = 6;
        Object[] objectArray = new Object[6 + EArray.length];
        objectArray[0] = e;
        objectArray[1] = e2;
        objectArray[2] = e3;
        objectArray[3] = e4;
        objectArray[4] = e5;
        objectArray[5] = e6;
        System.arraycopy(EArray, 0, objectArray, 6, EArray.length);
        return ImmutableSet.construct(objectArray.length, objectArray);
    }

    private static <E> ImmutableSet<E> construct(int n, Object ... objectArray) {
        switch (n) {
            case 0: {
                return ImmutableSet.of();
            }
            case 1: {
                Object object = objectArray[0];
                return ImmutableSet.of(object);
            }
        }
        int n2 = ImmutableSet.chooseTableSize(n);
        Object[] objectArray2 = new Object[n2];
        int n3 = n2 - 1;
        int n4 = 0;
        int n5 = 0;
        block4: for (int i = 0; i < n; ++i) {
            Object object = ObjectArrays.checkElementNotNull(objectArray[i], i);
            int n6 = object.hashCode();
            int n7 = Hashing.smear(n6);
            while (true) {
                int n8;
                Object object2;
                if ((object2 = objectArray2[n8 = n7 & n3]) == null) {
                    objectArray[n5++] = object;
                    objectArray2[n8] = object;
                    n4 += n6;
                    continue block4;
                }
                if (object2.equals(object)) continue block4;
                ++n7;
            }
        }
        Arrays.fill(objectArray, n5, n, null);
        if (n5 == 1) {
            Object object = objectArray[0];
            return new SingletonImmutableSet<Object>(object, n4);
        }
        if (n2 != ImmutableSet.chooseTableSize(n5)) {
            return ImmutableSet.construct(n5, objectArray);
        }
        Object[] objectArray3 = n5 < objectArray.length ? Arrays.copyOf(objectArray, n5) : objectArray;
        return new RegularImmutableSet(objectArray3, n4, objectArray2, n3);
    }

    @VisibleForTesting
    static int chooseTableSize(int n) {
        if (n < 0x2CCCCCCC) {
            int n2 = Integer.highestOneBit(n - 1) << 1;
            while ((double)n2 * 0.7 < (double)n) {
                n2 <<= 1;
            }
            return n2;
        }
        Preconditions.checkArgument(n < 0x40000000, "collection too large");
        return 1;
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> collection) {
        Object[] objectArray;
        if (collection instanceof ImmutableSet && !(collection instanceof ImmutableSortedSet)) {
            objectArray = (Object[])collection;
            if (!objectArray.isPartialView()) {
                return objectArray;
            }
        } else if (collection instanceof EnumSet) {
            return ImmutableSet.copyOfEnumSet((EnumSet)collection);
        }
        objectArray = collection.toArray();
        return ImmutableSet.construct(objectArray.length, objectArray);
    }

    public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> iterable) {
        return iterable instanceof Collection ? ImmutableSet.copyOf((Collection)iterable) : ImmutableSet.copyOf(iterable.iterator());
    }

    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> iterator2) {
        if (!iterator2.hasNext()) {
            return ImmutableSet.of();
        }
        E e = iterator2.next();
        if (!iterator2.hasNext()) {
            return ImmutableSet.of(e);
        }
        return ((Builder)((Builder)new Builder().add((Object)e)).addAll(iterator2)).build();
    }

    public static <E> ImmutableSet<E> copyOf(E[] EArray) {
        switch (EArray.length) {
            case 0: {
                return ImmutableSet.of();
            }
            case 1: {
                return ImmutableSet.of(EArray[0]);
            }
        }
        return ImmutableSet.construct(EArray.length, (Object[])EArray.clone());
    }

    private static ImmutableSet copyOfEnumSet(EnumSet enumSet) {
        return ImmutableEnumSet.asImmutable(EnumSet.copyOf(enumSet));
    }

    ImmutableSet() {
    }

    boolean isHashCodeFast() {
        return true;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof ImmutableSet && this.isHashCodeFast() && ((ImmutableSet)object).isHashCodeFast() && this.hashCode() != object.hashCode()) {
            return true;
        }
        return Sets.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    @Override
    public abstract UnmodifiableIterator<E> iterator();

    @Override
    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.asList;
        return immutableList == null ? (this.asList = this.createAsList()) : immutableList;
    }

    ImmutableList<E> createAsList() {
        return new RegularImmutableAsList(this, this.toArray());
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this.toArray());
    }

    public static <E> Builder<E> builder() {
        return new Builder();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    static ImmutableSet access$000(int n, Object[] objectArray) {
        return ImmutableSet.construct(n, objectArray);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder<E>
    extends ImmutableCollection.ArrayBasedBuilder<E> {
        public Builder() {
            this(4);
        }

        Builder(int n) {
            super(n);
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            super.add((Object)e);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> add(E ... EArray) {
            super.add(EArray);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll(iterable);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            super.addAll(iterator2);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        Builder<E> combine(ImmutableCollection.ArrayBasedBuilder<E> arrayBasedBuilder) {
            super.combine(arrayBasedBuilder);
            return this;
        }

        @Override
        public ImmutableSet<E> build() {
            ImmutableSet immutableSet = ImmutableSet.access$000(this.size, this.contents);
            this.size = immutableSet.size();
            return immutableSet;
        }

        @Override
        @CanIgnoreReturnValue
        ImmutableCollection.ArrayBasedBuilder combine(ImmutableCollection.ArrayBasedBuilder arrayBasedBuilder) {
            return this.combine(arrayBasedBuilder);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder addAll(Iterable iterable) {
            return this.addAll(iterable);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder add(Object[] objectArray) {
            return this.add(objectArray);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.ArrayBasedBuilder add(Object object) {
            return this.add(object);
        }

        @Override
        public ImmutableCollection build() {
            return this.build();
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder addAll(Iterator iterator2) {
            return this.addAll(iterator2);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder add(Object object) {
            return this.add(object);
        }
    }

    private static class SerializedForm
    implements Serializable {
        final Object[] elements;
        private static final long serialVersionUID = 0L;

        SerializedForm(Object[] objectArray) {
            this.elements = objectArray;
        }

        Object readResolve() {
            return ImmutableSet.copyOf(this.elements);
        }
    }

    static abstract class Indexed<E>
    extends ImmutableSet<E> {
        Indexed() {
        }

        abstract E get(int var1);

        @Override
        public UnmodifiableIterator<E> iterator() {
            return this.asList().iterator();
        }

        @Override
        public Spliterator<E> spliterator() {
            return CollectSpliterators.indexed(this.size(), 1297, this::get);
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            Preconditions.checkNotNull(consumer);
            int n = this.size();
            for (int i = 0; i < n; ++i) {
                consumer.accept(this.get(i));
            }
        }

        @Override
        ImmutableList<E> createAsList() {
            return new ImmutableAsList<E>(this){
                final Indexed this$0;
                {
                    this.this$0 = indexed;
                }

                @Override
                public E get(int n) {
                    return this.this$0.get(n);
                }

                @Override
                Indexed<E> delegateCollection() {
                    return this.this$0;
                }

                @Override
                ImmutableCollection delegateCollection() {
                    return this.delegateCollection();
                }
            };
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}

