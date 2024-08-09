/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.RegularImmutableAsList;
import com.google.common.collect.RegularImmutableMultiset;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
public abstract class ImmutableMultiset<E>
extends ImmutableCollection<E>
implements Multiset<E> {
    @LazyInit
    private transient ImmutableList<E> asList;
    @LazyInit
    private transient ImmutableSet<Multiset.Entry<E>> entrySet;

    @Beta
    public static <E> Collector<E, ?, ImmutableMultiset<E>> toImmutableMultiset() {
        return ImmutableMultiset.toImmutableMultiset(Function.identity(), ImmutableMultiset::lambda$toImmutableMultiset$0);
    }

    private static <T, E> Collector<T, ?, ImmutableMultiset<E>> toImmutableMultiset(Function<? super T, ? extends E> function, ToIntFunction<? super T> toIntFunction) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(toIntFunction);
        return Collector.of(LinkedHashMultiset::create, (arg_0, arg_1) -> ImmutableMultiset.lambda$toImmutableMultiset$1(function, toIntFunction, arg_0, arg_1), ImmutableMultiset::lambda$toImmutableMultiset$2, ImmutableMultiset::lambda$toImmutableMultiset$3, new Collector.Characteristics[0]);
    }

    public static <E> ImmutableMultiset<E> of() {
        return RegularImmutableMultiset.EMPTY;
    }

    public static <E> ImmutableMultiset<E> of(E e) {
        return ImmutableMultiset.copyFromElements(e);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2) {
        return ImmutableMultiset.copyFromElements(e, e2);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3) {
        return ImmutableMultiset.copyFromElements(e, e2, e3);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4) {
        return ImmutableMultiset.copyFromElements(e, e2, e3, e4);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4, E e5) {
        return ImmutableMultiset.copyFromElements(e, e2, e3, e4, e5);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4, E e5, E e6, E ... EArray) {
        return ((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)new Builder().add((Object)e)).add((Object)e2)).add((Object)e3)).add((Object)e4)).add((Object)e5)).add((Object)e6)).add((Object[])EArray)).build();
    }

    public static <E> ImmutableMultiset<E> copyOf(E[] EArray) {
        return ImmutableMultiset.copyFromElements(EArray);
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterable<? extends E> iterable) {
        Multiset<Object> multiset;
        if (iterable instanceof ImmutableMultiset && !((ImmutableCollection)((Object)(multiset = (ImmutableMultiset)iterable))).isPartialView()) {
            return multiset;
        }
        multiset = iterable instanceof Multiset ? Multisets.cast(iterable) : LinkedHashMultiset.create(iterable);
        return ImmutableMultiset.copyFromEntries(multiset.entrySet());
    }

    private static <E> ImmutableMultiset<E> copyFromElements(E ... EArray) {
        LinkedHashMultiset linkedHashMultiset = LinkedHashMultiset.create();
        Collections.addAll(linkedHashMultiset, EArray);
        return ImmutableMultiset.copyFromEntries(linkedHashMultiset.entrySet());
    }

    static <E> ImmutableMultiset<E> copyFromEntries(Collection<? extends Multiset.Entry<? extends E>> collection) {
        if (collection.isEmpty()) {
            return ImmutableMultiset.of();
        }
        return new RegularImmutableMultiset(collection);
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterator<? extends E> iterator2) {
        LinkedHashMultiset linkedHashMultiset = LinkedHashMultiset.create();
        Iterators.addAll(linkedHashMultiset, iterator2);
        return ImmutableMultiset.copyFromEntries(linkedHashMultiset.entrySet());
    }

    ImmutableMultiset() {
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        Iterator iterator2 = ((ImmutableSet)this.entrySet()).iterator();
        return new UnmodifiableIterator<E>(this, iterator2){
            int remaining;
            E element;
            final Iterator val$entryIterator;
            final ImmutableMultiset this$0;
            {
                this.this$0 = immutableMultiset;
                this.val$entryIterator = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.remaining > 0 || this.val$entryIterator.hasNext();
            }

            @Override
            public E next() {
                if (this.remaining <= 0) {
                    Multiset.Entry entry = (Multiset.Entry)this.val$entryIterator.next();
                    this.element = entry.getElement();
                    this.remaining = entry.getCount();
                }
                --this.remaining;
                return this.element;
            }
        };
    }

    @Override
    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.asList;
        return immutableList == null ? (this.asList = this.createAsList()) : immutableList;
    }

    ImmutableList<E> createAsList() {
        if (this.isEmpty()) {
            return ImmutableList.of();
        }
        return new RegularImmutableAsList(this, this.toArray());
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return this.count(object) > 0;
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final int add(E e, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final int remove(Object object, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final int setCount(E e, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final boolean setCount(E e, int n, int n2) {
        throw new UnsupportedOperationException();
    }

    @Override
    @GwtIncompatible
    int copyIntoArray(Object[] objectArray, int n) {
        for (Multiset.Entry entry : this.entrySet()) {
            Arrays.fill(objectArray, n, n + entry.getCount(), entry.getElement());
            n += entry.getCount();
        }
        return n;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return Multisets.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }

    @Override
    public String toString() {
        return ((AbstractCollection)((Object)this.entrySet())).toString();
    }

    @Override
    public abstract ImmutableSet<E> elementSet();

    @Override
    public ImmutableSet<Multiset.Entry<E>> entrySet() {
        ImmutableSet<Multiset.Entry<Multiset.Entry<E>>> immutableSet = this.entrySet;
        return immutableSet == null ? (this.entrySet = this.createEntrySet()) : immutableSet;
    }

    private final ImmutableSet<Multiset.Entry<E>> createEntrySet() {
        return this.isEmpty() ? ImmutableSet.of() : new EntrySet(this, null);
    }

    abstract Multiset.Entry<E> getEntry(int var1);

    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }

    public static <E> Builder<E> builder() {
        return new Builder();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public Set entrySet() {
        return this.entrySet();
    }

    @Override
    public Set elementSet() {
        return this.elementSet();
    }

    private static ImmutableMultiset lambda$toImmutableMultiset$3(Multiset multiset) {
        return ImmutableMultiset.copyFromEntries(multiset.entrySet());
    }

    private static Multiset lambda$toImmutableMultiset$2(Multiset multiset, Multiset multiset2) {
        multiset.addAll(multiset2);
        return multiset;
    }

    private static void lambda$toImmutableMultiset$1(Function function, ToIntFunction toIntFunction, Multiset multiset, Object object) {
        multiset.add(function.apply(object), toIntFunction.applyAsInt(object));
    }

    private static int lambda$toImmutableMultiset$0(Object object) {
        return 0;
    }

    public static class Builder<E>
    extends ImmutableCollection.Builder<E> {
        final Multiset<E> contents;

        public Builder() {
            this(LinkedHashMultiset.create());
        }

        Builder(Multiset<E> multiset) {
            this.contents = multiset;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            this.contents.add(Preconditions.checkNotNull(e));
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addCopies(E e, int n) {
            this.contents.add(Preconditions.checkNotNull(e), n);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> setCount(E e, int n) {
            this.contents.setCount(Preconditions.checkNotNull(e), n);
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
            if (iterable instanceof Multiset) {
                Multiset<E> multiset = Multisets.cast(iterable);
                for (Multiset.Entry<E> entry : multiset.entrySet()) {
                    this.addCopies(entry.getElement(), entry.getCount());
                }
            } else {
                super.addAll(iterable);
            }
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            super.addAll(iterator2);
            return this;
        }

        @Override
        public ImmutableMultiset<E> build() {
            return ImmutableMultiset.copyOf(this.contents);
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
        public ImmutableCollection.Builder add(Object object) {
            return this.add(object);
        }
    }

    private static class SerializedForm
    implements Serializable {
        final Object[] elements;
        final int[] counts;
        private static final long serialVersionUID = 0L;

        SerializedForm(Multiset<?> multiset) {
            int n = multiset.entrySet().size();
            this.elements = new Object[n];
            this.counts = new int[n];
            int n2 = 0;
            for (Multiset.Entry<?> entry : multiset.entrySet()) {
                this.elements[n2] = entry.getElement();
                this.counts[n2] = entry.getCount();
                ++n2;
            }
        }

        Object readResolve() {
            LinkedHashMultiset linkedHashMultiset = LinkedHashMultiset.create(this.elements.length);
            for (int i = 0; i < this.elements.length; ++i) {
                linkedHashMultiset.add(this.elements[i], this.counts[i]);
            }
            return ImmutableMultiset.copyOf(linkedHashMultiset);
        }
    }

    static class EntrySetSerializedForm<E>
    implements Serializable {
        final ImmutableMultiset<E> multiset;

        EntrySetSerializedForm(ImmutableMultiset<E> immutableMultiset) {
            this.multiset = immutableMultiset;
        }

        Object readResolve() {
            return this.multiset.entrySet();
        }
    }

    private final class EntrySet
    extends ImmutableSet.Indexed<Multiset.Entry<E>> {
        private static final long serialVersionUID = 0L;
        final ImmutableMultiset this$0;

        private EntrySet(ImmutableMultiset immutableMultiset) {
            this.this$0 = immutableMultiset;
        }

        @Override
        boolean isPartialView() {
            return this.this$0.isPartialView();
        }

        @Override
        Multiset.Entry<E> get(int n) {
            return this.this$0.getEntry(n);
        }

        @Override
        public int size() {
            return ((AbstractCollection)((Object)this.this$0.elementSet())).size();
        }

        @Override
        public boolean contains(Object object) {
            if (object instanceof Multiset.Entry) {
                Multiset.Entry entry = (Multiset.Entry)object;
                if (entry.getCount() <= 0) {
                    return true;
                }
                int n = this.this$0.count(entry.getElement());
                return n == entry.getCount();
            }
            return true;
        }

        @Override
        public int hashCode() {
            return this.this$0.hashCode();
        }

        @Override
        Object writeReplace() {
            return new EntrySetSerializedForm(this.this$0);
        }

        @Override
        Object get(int n) {
            return this.get(n);
        }

        EntrySet(ImmutableMultiset immutableMultiset, 1 var2_2) {
            this(immutableMultiset);
        }
    }
}

