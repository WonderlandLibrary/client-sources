/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.BoundType;
import com.google.common.collect.CartesianList;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingNavigableSet;
import com.google.common.collect.ForwardingSortedSet;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableEnumSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.Synchronized;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Sets {
    private Sets() {
    }

    @GwtCompatible(serializable=true)
    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(E e, E ... EArray) {
        return ImmutableEnumSet.asImmutable(EnumSet.of(e, EArray));
    }

    @GwtCompatible(serializable=true)
    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(Iterable<E> iterable) {
        if (iterable instanceof ImmutableEnumSet) {
            return (ImmutableEnumSet)iterable;
        }
        if (iterable instanceof Collection) {
            Collection collection = (Collection)iterable;
            if (collection.isEmpty()) {
                return ImmutableSet.of();
            }
            return ImmutableEnumSet.asImmutable(EnumSet.copyOf(collection));
        }
        Iterator<E> iterator2 = iterable.iterator();
        if (iterator2.hasNext()) {
            EnumSet<Enum> enumSet = EnumSet.of((Enum)iterator2.next());
            Iterators.addAll(enumSet, iterator2);
            return ImmutableEnumSet.asImmutable(enumSet);
        }
        return ImmutableSet.of();
    }

    @Beta
    public static <E extends Enum<E>> Collector<E, ?, ImmutableSet<E>> toImmutableEnumSet() {
        return Accumulator.TO_IMMUTABLE_ENUM_SET;
    }

    public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> iterable, Class<E> clazz) {
        EnumSet<E> enumSet = EnumSet.noneOf(clazz);
        Iterables.addAll(enumSet, iterable);
        return enumSet;
    }

    public static <E> HashSet<E> newHashSet() {
        return new HashSet();
    }

    public static <E> HashSet<E> newHashSet(E ... EArray) {
        HashSet<E> hashSet = Sets.newHashSetWithExpectedSize(EArray.length);
        Collections.addAll(hashSet, EArray);
        return hashSet;
    }

    public static <E> HashSet<E> newHashSetWithExpectedSize(int n) {
        return new HashSet(Maps.capacity(n));
    }

    public static <E> HashSet<E> newHashSet(Iterable<? extends E> iterable) {
        return iterable instanceof Collection ? new HashSet<E>(Collections2.cast(iterable)) : Sets.newHashSet(iterable.iterator());
    }

    public static <E> HashSet<E> newHashSet(Iterator<? extends E> iterator2) {
        HashSet<E> hashSet = Sets.newHashSet();
        Iterators.addAll(hashSet, iterator2);
        return hashSet;
    }

    public static <E> Set<E> newConcurrentHashSet() {
        return Collections.newSetFromMap(new ConcurrentHashMap());
    }

    public static <E> Set<E> newConcurrentHashSet(Iterable<? extends E> iterable) {
        Set<E> set = Sets.newConcurrentHashSet();
        Iterables.addAll(set, iterable);
        return set;
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet();
    }

    public static <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int n) {
        return new LinkedHashSet(Maps.capacity(n));
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new LinkedHashSet<E>(Collections2.cast(iterable));
        }
        LinkedHashSet<E> linkedHashSet = Sets.newLinkedHashSet();
        Iterables.addAll(linkedHashSet, iterable);
        return linkedHashSet;
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet() {
        return new TreeSet();
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet(Iterable<? extends E> iterable) {
        TreeSet<E> treeSet = Sets.newTreeSet();
        Iterables.addAll(treeSet, iterable);
        return treeSet;
    }

    public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
        return new TreeSet<E>(Preconditions.checkNotNull(comparator));
    }

    public static <E> Set<E> newIdentityHashSet() {
        return Collections.newSetFromMap(Maps.newIdentityHashMap());
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet();
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(Iterable<? extends E> iterable) {
        Collection<? extends E> collection = iterable instanceof Collection ? Collections2.cast(iterable) : Lists.newArrayList(iterable);
        return new CopyOnWriteArraySet<E>(collection);
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection) {
        if (collection instanceof EnumSet) {
            return EnumSet.complementOf((EnumSet)collection);
        }
        Preconditions.checkArgument(!collection.isEmpty(), "collection is empty; use the other version of this method");
        Class clazz = ((Enum)collection.iterator().next()).getDeclaringClass();
        return Sets.makeComplementByHand(collection, clazz);
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection, Class<E> clazz) {
        Preconditions.checkNotNull(collection);
        return collection instanceof EnumSet ? EnumSet.complementOf((EnumSet)collection) : Sets.makeComplementByHand(collection, clazz);
    }

    private static <E extends Enum<E>> EnumSet<E> makeComplementByHand(Collection<E> collection, Class<E> clazz) {
        EnumSet<E> enumSet = EnumSet.allOf(clazz);
        enumSet.removeAll(collection);
        return enumSet;
    }

    @Deprecated
    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return Collections.newSetFromMap(map);
    }

    public static <E> SetView<E> union(Set<? extends E> set, Set<? extends E> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        SetView<? extends E> setView = Sets.difference(set2, set);
        return new SetView<E>(set, setView, set2){
            final Set val$set1;
            final Set val$set2minus1;
            final Set val$set2;
            {
                this.val$set1 = set;
                this.val$set2minus1 = set2;
                this.val$set2 = set3;
                super(null);
            }

            @Override
            public int size() {
                return IntMath.saturatedAdd(this.val$set1.size(), this.val$set2minus1.size());
            }

            @Override
            public boolean isEmpty() {
                return this.val$set1.isEmpty() && this.val$set2.isEmpty();
            }

            @Override
            public UnmodifiableIterator<E> iterator() {
                return Iterators.unmodifiableIterator(Iterators.concat(this.val$set1.iterator(), this.val$set2minus1.iterator()));
            }

            @Override
            public Stream<E> stream() {
                return Stream.concat(this.val$set1.stream(), this.val$set2minus1.stream());
            }

            @Override
            public Stream<E> parallelStream() {
                return Stream.concat(this.val$set1.parallelStream(), this.val$set2minus1.parallelStream());
            }

            @Override
            public boolean contains(Object object) {
                return this.val$set1.contains(object) || this.val$set2.contains(object);
            }

            @Override
            public <S extends Set<E>> S copyInto(S s) {
                s.addAll(this.val$set1);
                s.addAll(this.val$set2);
                return s;
            }

            @Override
            public ImmutableSet<E> immutableCopy() {
                return ((ImmutableSet.Builder)((ImmutableSet.Builder)new ImmutableSet.Builder().addAll((Iterable)this.val$set1)).addAll((Iterable)this.val$set2)).build();
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    public static <E> SetView<E> intersection(Set<E> set, Set<?> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        com.google.common.base.Predicate<?> predicate = Predicates.in(set2);
        return new SetView<E>(set, predicate, set2){
            final Set val$set1;
            final com.google.common.base.Predicate val$inSet2;
            final Set val$set2;
            {
                this.val$set1 = set;
                this.val$inSet2 = predicate;
                this.val$set2 = set2;
                super(null);
            }

            @Override
            public UnmodifiableIterator<E> iterator() {
                return Iterators.filter(this.val$set1.iterator(), this.val$inSet2);
            }

            @Override
            public Stream<E> stream() {
                return this.val$set1.stream().filter(this.val$inSet2);
            }

            @Override
            public Stream<E> parallelStream() {
                return this.val$set1.parallelStream().filter(this.val$inSet2);
            }

            @Override
            public int size() {
                return Iterators.size(this.iterator());
            }

            @Override
            public boolean isEmpty() {
                return !this.iterator().hasNext();
            }

            @Override
            public boolean contains(Object object) {
                return this.val$set1.contains(object) && this.val$set2.contains(object);
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return this.val$set1.containsAll(collection) && this.val$set2.containsAll(collection);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    public static <E> SetView<E> difference(Set<E> set, Set<?> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        com.google.common.base.Predicate<?> predicate = Predicates.not(Predicates.in(set2));
        return new SetView<E>(set, predicate, set2){
            final Set val$set1;
            final com.google.common.base.Predicate val$notInSet2;
            final Set val$set2;
            {
                this.val$set1 = set;
                this.val$notInSet2 = predicate;
                this.val$set2 = set2;
                super(null);
            }

            @Override
            public UnmodifiableIterator<E> iterator() {
                return Iterators.filter(this.val$set1.iterator(), this.val$notInSet2);
            }

            @Override
            public Stream<E> stream() {
                return this.val$set1.stream().filter(this.val$notInSet2);
            }

            @Override
            public Stream<E> parallelStream() {
                return this.val$set1.parallelStream().filter(this.val$notInSet2);
            }

            @Override
            public int size() {
                return Iterators.size(this.iterator());
            }

            @Override
            public boolean isEmpty() {
                return this.val$set2.containsAll(this.val$set1);
            }

            @Override
            public boolean contains(Object object) {
                return this.val$set1.contains(object) && !this.val$set2.contains(object);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    public static <E> SetView<E> symmetricDifference(Set<? extends E> set, Set<? extends E> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>(set, set2){
            final Set val$set1;
            final Set val$set2;
            {
                this.val$set1 = set;
                this.val$set2 = set2;
                super(null);
            }

            @Override
            public UnmodifiableIterator<E> iterator() {
                Iterator iterator2 = this.val$set1.iterator();
                Iterator iterator3 = this.val$set2.iterator();
                return new AbstractIterator<E>(this, iterator2, iterator3){
                    final Iterator val$itr1;
                    final Iterator val$itr2;
                    final 4 this$0;
                    {
                        this.this$0 = var1_1;
                        this.val$itr1 = iterator2;
                        this.val$itr2 = iterator3;
                    }

                    @Override
                    public E computeNext() {
                        Object e;
                        while (this.val$itr1.hasNext()) {
                            e = this.val$itr1.next();
                            if (this.this$0.val$set2.contains(e)) continue;
                            return e;
                        }
                        while (this.val$itr2.hasNext()) {
                            e = this.val$itr2.next();
                            if (this.this$0.val$set1.contains(e)) continue;
                            return e;
                        }
                        return this.endOfData();
                    }
                };
            }

            @Override
            public int size() {
                return Iterators.size(this.iterator());
            }

            @Override
            public boolean isEmpty() {
                return this.val$set1.equals(this.val$set2);
            }

            @Override
            public boolean contains(Object object) {
                return this.val$set1.contains(object) ^ this.val$set2.contains(object);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    public static <E> Set<E> filter(Set<E> set, com.google.common.base.Predicate<? super E> predicate) {
        if (set instanceof SortedSet) {
            return Sets.filter((SortedSet)set, predicate);
        }
        if (set instanceof FilteredSet) {
            FilteredSet filteredSet = (FilteredSet)set;
            com.google.common.base.Predicate<? super E> predicate2 = Predicates.and(filteredSet.predicate, predicate);
            return new FilteredSet<E>((Set)filteredSet.unfiltered, predicate2);
        }
        return new FilteredSet<E>(Preconditions.checkNotNull(set), Preconditions.checkNotNull(predicate));
    }

    public static <E> SortedSet<E> filter(SortedSet<E> sortedSet, com.google.common.base.Predicate<? super E> predicate) {
        if (sortedSet instanceof FilteredSet) {
            FilteredSet filteredSet = (FilteredSet)((Object)sortedSet);
            com.google.common.base.Predicate<? super E> predicate2 = Predicates.and(filteredSet.predicate, predicate);
            return new FilteredSortedSet<E>((SortedSet)filteredSet.unfiltered, predicate2);
        }
        return new FilteredSortedSet<E>(Preconditions.checkNotNull(sortedSet), Preconditions.checkNotNull(predicate));
    }

    @GwtIncompatible
    public static <E> NavigableSet<E> filter(NavigableSet<E> navigableSet, com.google.common.base.Predicate<? super E> predicate) {
        if (navigableSet instanceof FilteredSet) {
            FilteredSet filteredSet = (FilteredSet)((Object)navigableSet);
            com.google.common.base.Predicate<? super E> predicate2 = Predicates.and(filteredSet.predicate, predicate);
            return new FilteredNavigableSet<E>((NavigableSet)filteredSet.unfiltered, predicate2);
        }
        return new FilteredNavigableSet<E>(Preconditions.checkNotNull(navigableSet), Preconditions.checkNotNull(predicate));
    }

    public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> list) {
        return CartesianSet.create(list);
    }

    public static <B> Set<List<B>> cartesianProduct(Set<? extends B> ... setArray) {
        return Sets.cartesianProduct(Arrays.asList(setArray));
    }

    @GwtCompatible(serializable=false)
    public static <E> Set<Set<E>> powerSet(Set<E> set) {
        return new PowerSet<E>(set);
    }

    static int hashCodeImpl(Set<?> set) {
        int n = 0;
        for (Object obj : set) {
            n += obj != null ? obj.hashCode() : 0;
            n = ~(~n);
        }
        return n;
    }

    static boolean equalsImpl(Set<?> set, @Nullable Object object) {
        if (set == object) {
            return false;
        }
        if (object instanceof Set) {
            Set set2 = (Set)object;
            try {
                return set.size() == set2.size() && set.containsAll(set2);
            } catch (NullPointerException nullPointerException) {
                return true;
            } catch (ClassCastException classCastException) {
                return true;
            }
        }
        return true;
    }

    public static <E> NavigableSet<E> unmodifiableNavigableSet(NavigableSet<E> navigableSet) {
        if (navigableSet instanceof ImmutableSortedSet || navigableSet instanceof UnmodifiableNavigableSet) {
            return navigableSet;
        }
        return new UnmodifiableNavigableSet<E>(navigableSet);
    }

    @GwtIncompatible
    public static <E> NavigableSet<E> synchronizedNavigableSet(NavigableSet<E> navigableSet) {
        return Synchronized.navigableSet(navigableSet);
    }

    static boolean removeAllImpl(Set<?> set, Iterator<?> iterator2) {
        boolean bl = false;
        while (iterator2.hasNext()) {
            bl |= set.remove(iterator2.next());
        }
        return bl;
    }

    static boolean removeAllImpl(Set<?> set, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            collection = ((Multiset)collection).elementSet();
        }
        if (collection instanceof Set && collection.size() > set.size()) {
            return Iterators.removeAll(set.iterator(), collection);
        }
        return Sets.removeAllImpl(set, collection.iterator());
    }

    @Beta
    @GwtIncompatible
    public static <K extends Comparable<? super K>> NavigableSet<K> subSet(NavigableSet<K> navigableSet, Range<K> range) {
        if (navigableSet.comparator() != null && navigableSet.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            Preconditions.checkArgument(navigableSet.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0, "set is using a custom comparator which is inconsistent with the natural ordering.");
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            return navigableSet.subSet(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED, range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED);
        }
        if (range.hasLowerBound()) {
            return navigableSet.tailSet(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED);
        }
        if (range.hasUpperBound()) {
            return navigableSet.headSet(range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED);
        }
        return Preconditions.checkNotNull(navigableSet);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @GwtIncompatible
    static class DescendingSet<E>
    extends ForwardingNavigableSet<E> {
        private final NavigableSet<E> forward;

        DescendingSet(NavigableSet<E> navigableSet) {
            this.forward = navigableSet;
        }

        @Override
        protected NavigableSet<E> delegate() {
            return this.forward;
        }

        @Override
        public E lower(E e) {
            return this.forward.higher(e);
        }

        @Override
        public E floor(E e) {
            return this.forward.ceiling(e);
        }

        @Override
        public E ceiling(E e) {
            return this.forward.floor(e);
        }

        @Override
        public E higher(E e) {
            return this.forward.lower(e);
        }

        @Override
        public E pollFirst() {
            return this.forward.pollLast();
        }

        @Override
        public E pollLast() {
            return this.forward.pollFirst();
        }

        @Override
        public NavigableSet<E> descendingSet() {
            return this.forward;
        }

        @Override
        public Iterator<E> descendingIterator() {
            return this.forward.iterator();
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return this.forward.subSet(e2, bl2, e, bl).descendingSet();
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return this.forward.tailSet(e, bl).descendingSet();
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return this.forward.headSet(e, bl).descendingSet();
        }

        @Override
        public Comparator<? super E> comparator() {
            Comparator comparator = this.forward.comparator();
            if (comparator == null) {
                return Ordering.natural().reverse();
            }
            return DescendingSet.reverse(comparator);
        }

        private static <T> Ordering<T> reverse(Comparator<T> comparator) {
            return Ordering.from(comparator).reverse();
        }

        @Override
        public E first() {
            return this.forward.last();
        }

        @Override
        public SortedSet<E> headSet(E e) {
            return this.standardHeadSet(e);
        }

        @Override
        public E last() {
            return this.forward.first();
        }

        @Override
        public SortedSet<E> subSet(E e, E e2) {
            return this.standardSubSet(e, e2);
        }

        @Override
        public SortedSet<E> tailSet(E e) {
            return this.standardTailSet(e);
        }

        @Override
        public Iterator<E> iterator() {
            return this.forward.descendingIterator();
        }

        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return this.standardToArray(TArray);
        }

        @Override
        public String toString() {
            return this.standardToString();
        }

        @Override
        protected SortedSet delegate() {
            return this.delegate();
        }

        @Override
        protected Set delegate() {
            return this.delegate();
        }

        @Override
        protected Collection delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class UnmodifiableNavigableSet<E>
    extends ForwardingSortedSet<E>
    implements NavigableSet<E>,
    Serializable {
        private final NavigableSet<E> delegate;
        private transient UnmodifiableNavigableSet<E> descendingSet;
        private static final long serialVersionUID = 0L;

        UnmodifiableNavigableSet(NavigableSet<E> navigableSet) {
            this.delegate = Preconditions.checkNotNull(navigableSet);
        }

        @Override
        protected SortedSet<E> delegate() {
            return Collections.unmodifiableSortedSet(this.delegate);
        }

        @Override
        public E lower(E e) {
            return this.delegate.lower(e);
        }

        @Override
        public E floor(E e) {
            return this.delegate.floor(e);
        }

        @Override
        public E ceiling(E e) {
            return this.delegate.ceiling(e);
        }

        @Override
        public E higher(E e) {
            return this.delegate.higher(e);
        }

        @Override
        public E pollFirst() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E pollLast() {
            throw new UnsupportedOperationException();
        }

        @Override
        public NavigableSet<E> descendingSet() {
            UnmodifiableNavigableSet<E> unmodifiableNavigableSet = this.descendingSet;
            if (unmodifiableNavigableSet == null) {
                unmodifiableNavigableSet = this.descendingSet = new UnmodifiableNavigableSet<E>(this.delegate.descendingSet());
                unmodifiableNavigableSet.descendingSet = this;
            }
            return unmodifiableNavigableSet;
        }

        @Override
        public Iterator<E> descendingIterator() {
            return Iterators.unmodifiableIterator(this.delegate.descendingIterator());
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return Sets.unmodifiableNavigableSet(this.delegate.subSet(e, bl, e2, bl2));
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return Sets.unmodifiableNavigableSet(this.delegate.headSet(e, bl));
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return Sets.unmodifiableNavigableSet(this.delegate.tailSet(e, bl));
        }

        @Override
        protected Set delegate() {
            return this.delegate();
        }

        @Override
        protected Collection delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    private static final class PowerSet<E>
    extends AbstractSet<Set<E>> {
        final ImmutableMap<E, Integer> inputSet;

        PowerSet(Set<E> set) {
            this.inputSet = Maps.indexMap(set);
            Preconditions.checkArgument(this.inputSet.size() <= 30, "Too many elements to create power set: %s > 30", this.inputSet.size());
        }

        @Override
        public int size() {
            return 1 << this.inputSet.size();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Iterator<Set<E>> iterator() {
            return new AbstractIndexedListIterator<Set<E>>(this, this.size()){
                final PowerSet this$0;
                {
                    this.this$0 = powerSet;
                    super(n);
                }

                @Override
                protected Set<E> get(int n) {
                    return new SubSet(this.this$0.inputSet, n);
                }

                @Override
                protected Object get(int n) {
                    return this.get(n);
                }
            };
        }

        @Override
        public boolean contains(@Nullable Object object) {
            if (object instanceof Set) {
                Set set = (Set)object;
                return ((AbstractCollection)((Object)this.inputSet.keySet())).containsAll(set);
            }
            return true;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof PowerSet) {
                PowerSet powerSet = (PowerSet)object;
                return this.inputSet.equals(powerSet.inputSet);
            }
            return super.equals(object);
        }

        @Override
        public int hashCode() {
            return ((ImmutableSet)this.inputSet.keySet()).hashCode() << this.inputSet.size() - 1;
        }

        @Override
        public String toString() {
            return "powerSet(" + this.inputSet + ")";
        }
    }

    private static final class SubSet<E>
    extends AbstractSet<E> {
        private final ImmutableMap<E, Integer> inputSet;
        private final int mask;

        SubSet(ImmutableMap<E, Integer> immutableMap, int n) {
            this.inputSet = immutableMap;
            this.mask = n;
        }

        @Override
        public Iterator<E> iterator() {
            return new UnmodifiableIterator<E>(this){
                final ImmutableList<E> elements;
                int remainingSetBits;
                final SubSet this$0;
                {
                    this.this$0 = subSet;
                    this.elements = ((ImmutableSet)SubSet.access$100(this.this$0).keySet()).asList();
                    this.remainingSetBits = SubSet.access$200(this.this$0);
                }

                @Override
                public boolean hasNext() {
                    return this.remainingSetBits != 0;
                }

                @Override
                public E next() {
                    int n = Integer.numberOfTrailingZeros(this.remainingSetBits);
                    if (n == 32) {
                        throw new NoSuchElementException();
                    }
                    this.remainingSetBits &= ~(1 << n);
                    return this.elements.get(n);
                }
            };
        }

        @Override
        public int size() {
            return Integer.bitCount(this.mask);
        }

        @Override
        public boolean contains(@Nullable Object object) {
            Integer n = this.inputSet.get(object);
            return n != null && (this.mask & 1 << n) != 0;
        }

        static ImmutableMap access$100(SubSet subSet) {
            return subSet.inputSet;
        }

        static int access$200(SubSet subSet) {
            return subSet.mask;
        }
    }

    private static final class CartesianSet<E>
    extends ForwardingCollection<List<E>>
    implements Set<List<E>> {
        private final transient ImmutableList<ImmutableSet<E>> axes;
        private final transient CartesianList<E> delegate;

        static <E> Set<List<E>> create(List<? extends Set<? extends E>> list) {
            ImmutableList.Builder builder = new ImmutableList.Builder(list.size());
            for (Set<E> collection2 : list) {
                ImmutableSet<E> immutableSet = ImmutableSet.copyOf(collection2);
                if (immutableSet.isEmpty()) {
                    return ImmutableSet.of();
                }
                builder.add(immutableSet);
            }
            ImmutableCollection immutableCollection = builder.build();
            ImmutableList immutableList = new ImmutableList<List<E>>((ImmutableList)immutableCollection){
                final ImmutableList val$axes;
                {
                    this.val$axes = immutableList;
                }

                @Override
                public int size() {
                    return this.val$axes.size();
                }

                @Override
                public List<E> get(int n) {
                    return ((ImmutableSet)this.val$axes.get(n)).asList();
                }

                @Override
                boolean isPartialView() {
                    return false;
                }

                @Override
                public Object get(int n) {
                    return this.get(n);
                }
            };
            return new CartesianSet(immutableCollection, new CartesianList(immutableList));
        }

        private CartesianSet(ImmutableList<ImmutableSet<E>> immutableList, CartesianList<E> cartesianList) {
            this.axes = immutableList;
            this.delegate = cartesianList;
        }

        @Override
        protected Collection<List<E>> delegate() {
            return this.delegate;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof CartesianSet) {
                CartesianSet cartesianSet = (CartesianSet)object;
                return this.axes.equals(cartesianSet.axes);
            }
            return super.equals(object);
        }

        @Override
        public int hashCode() {
            int n;
            int n2 = this.size() - 1;
            for (n = 0; n < this.axes.size(); ++n) {
                n2 *= 31;
                n2 = ~(~n2);
            }
            n = 1;
            for (Set set : this.axes) {
                n = 31 * n + this.size() / set.size() * set.hashCode();
                n = ~(~n);
            }
            return ~(~(n += n2));
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    @GwtIncompatible
    private static class FilteredNavigableSet<E>
    extends FilteredSortedSet<E>
    implements NavigableSet<E> {
        FilteredNavigableSet(NavigableSet<E> navigableSet, com.google.common.base.Predicate<? super E> predicate) {
            super(navigableSet, predicate);
        }

        NavigableSet<E> unfiltered() {
            return (NavigableSet)this.unfiltered;
        }

        @Override
        @Nullable
        public E lower(E e) {
            return Iterators.getNext(this.headSet(e, true).descendingIterator(), null);
        }

        @Override
        @Nullable
        public E floor(E e) {
            return Iterators.getNext(this.headSet(e, false).descendingIterator(), null);
        }

        @Override
        public E ceiling(E e) {
            return Iterables.getFirst(this.tailSet(e, false), null);
        }

        @Override
        public E higher(E e) {
            return Iterables.getFirst(this.tailSet(e, true), null);
        }

        @Override
        public E pollFirst() {
            return Iterables.removeFirstMatching(this.unfiltered(), this.predicate);
        }

        @Override
        public E pollLast() {
            return Iterables.removeFirstMatching(this.unfiltered().descendingSet(), this.predicate);
        }

        @Override
        public NavigableSet<E> descendingSet() {
            return Sets.filter(this.unfiltered().descendingSet(), this.predicate);
        }

        @Override
        public Iterator<E> descendingIterator() {
            return Iterators.filter(this.unfiltered().descendingIterator(), this.predicate);
        }

        @Override
        public E last() {
            return this.descendingIterator().next();
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return Sets.filter(this.unfiltered().subSet(e, bl, e2, bl2), this.predicate);
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return Sets.filter(this.unfiltered().headSet(e, bl), this.predicate);
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return Sets.filter(this.unfiltered().tailSet(e, bl), this.predicate);
        }
    }

    private static class FilteredSortedSet<E>
    extends FilteredSet<E>
    implements SortedSet<E> {
        FilteredSortedSet(SortedSet<E> sortedSet, com.google.common.base.Predicate<? super E> predicate) {
            super(sortedSet, predicate);
        }

        @Override
        public Comparator<? super E> comparator() {
            return ((SortedSet)this.unfiltered).comparator();
        }

        @Override
        public SortedSet<E> subSet(E e, E e2) {
            return new FilteredSortedSet<E>(((SortedSet)this.unfiltered).subSet(e, e2), this.predicate);
        }

        @Override
        public SortedSet<E> headSet(E e) {
            return new FilteredSortedSet<E>(((SortedSet)this.unfiltered).headSet(e), this.predicate);
        }

        @Override
        public SortedSet<E> tailSet(E e) {
            return new FilteredSortedSet<E>(((SortedSet)this.unfiltered).tailSet(e), this.predicate);
        }

        @Override
        public E first() {
            return this.iterator().next();
        }

        @Override
        public E last() {
            SortedSet sortedSet = (SortedSet)this.unfiltered;
            Object e;
            while (!this.predicate.apply(e = sortedSet.last())) {
                sortedSet = sortedSet.headSet(e);
            }
            return e;
        }
    }

    private static class FilteredSet<E>
    extends Collections2.FilteredCollection<E>
    implements Set<E> {
        FilteredSet(Set<E> set, com.google.common.base.Predicate<? super E> predicate) {
            super(set, predicate);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return Sets.equalsImpl(this, object);
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    public static abstract class SetView<E>
    extends AbstractSet<E> {
        private SetView() {
        }

        public ImmutableSet<E> immutableCopy() {
            return ImmutableSet.copyOf(this);
        }

        @CanIgnoreReturnValue
        public <S extends Set<E>> S copyInto(S s) {
            s.addAll(this);
            return s;
        }

        @Override
        @Deprecated
        @CanIgnoreReturnValue
        public final boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        @CanIgnoreReturnValue
        public final boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        @CanIgnoreReturnValue
        public final boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        @CanIgnoreReturnValue
        public final boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        @CanIgnoreReturnValue
        public final boolean removeIf(Predicate<? super E> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        @CanIgnoreReturnValue
        public final boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public abstract UnmodifiableIterator<E> iterator();

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        SetView(1 var1_1) {
            this();
        }
    }

    private static final class Accumulator<E extends Enum<E>> {
        static final Collector<Enum<?>, ?, ImmutableSet<? extends Enum<?>>> TO_IMMUTABLE_ENUM_SET = Collector.of(Accumulator::new, Accumulator::add, Accumulator::combine, Accumulator::toImmutableSet, Collector.Characteristics.UNORDERED);
        private EnumSet<E> set;

        private Accumulator() {
        }

        void add(E e) {
            if (this.set == null) {
                this.set = EnumSet.of(e);
            } else {
                this.set.add(e);
            }
        }

        Accumulator<E> combine(Accumulator<E> accumulator) {
            if (this.set == null) {
                return accumulator;
            }
            if (accumulator.set == null) {
                return this;
            }
            this.set.addAll(accumulator.set);
            return this;
        }

        ImmutableSet<E> toImmutableSet() {
            return this.set == null ? ImmutableSet.of() : ImmutableEnumSet.asImmutable(this.set);
        }
    }

    static abstract class ImprovedAbstractSet<E>
    extends AbstractSet<E> {
        ImprovedAbstractSet() {
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return Sets.removeAllImpl(this, collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return super.retainAll(Preconditions.checkNotNull(collection));
        }
    }
}

