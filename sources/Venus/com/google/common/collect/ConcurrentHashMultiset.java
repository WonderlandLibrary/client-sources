/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Serialization;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

@GwtIncompatible
public final class ConcurrentHashMultiset<E>
extends AbstractMultiset<E>
implements Serializable {
    private final transient ConcurrentMap<E, AtomicInteger> countMap;
    private static final long serialVersionUID = 1L;

    public static <E> ConcurrentHashMultiset<E> create() {
        return new ConcurrentHashMultiset(new ConcurrentHashMap());
    }

    public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> iterable) {
        ConcurrentHashMultiset<E> concurrentHashMultiset = ConcurrentHashMultiset.create();
        Iterables.addAll(concurrentHashMultiset, iterable);
        return concurrentHashMultiset;
    }

    @Beta
    public static <E> ConcurrentHashMultiset<E> create(ConcurrentMap<E, AtomicInteger> concurrentMap) {
        return new ConcurrentHashMultiset<E>(concurrentMap);
    }

    @VisibleForTesting
    ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> concurrentMap) {
        Preconditions.checkArgument(concurrentMap.isEmpty(), "the backing map (%s) must be empty", concurrentMap);
        this.countMap = concurrentMap;
    }

    @Override
    public int count(@Nullable Object object) {
        AtomicInteger atomicInteger = Maps.safeGet(this.countMap, object);
        return atomicInteger == null ? 0 : atomicInteger.get();
    }

    @Override
    public int size() {
        long l = 0L;
        for (AtomicInteger atomicInteger : this.countMap.values()) {
            l += (long)atomicInteger.get();
        }
        return Ints.saturatedCast(l);
    }

    @Override
    public Object[] toArray() {
        return this.snapshot().toArray();
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        return this.snapshot().toArray(TArray);
    }

    private List<E> snapshot() {
        ArrayList arrayList = Lists.newArrayListWithExpectedSize(this.size());
        for (Multiset.Entry entry : this.entrySet()) {
            Object e = entry.getElement();
            for (int i = entry.getCount(); i > 0; --i) {
                arrayList.add(e);
            }
        }
        return arrayList;
    }

    @Override
    @CanIgnoreReturnValue
    public int add(E e, int n) {
        AtomicInteger atomicInteger;
        AtomicInteger atomicInteger2;
        Preconditions.checkNotNull(e);
        if (n == 0) {
            return this.count(e);
        }
        CollectPreconditions.checkPositive(n, "occurences");
        do {
            int n2;
            if ((atomicInteger = Maps.safeGet(this.countMap, e)) == null && (atomicInteger = this.countMap.putIfAbsent(e, new AtomicInteger(n))) == null) {
                return 1;
            }
            while ((n2 = atomicInteger.get()) != 0) {
                try {
                    int n3 = IntMath.checkedAdd(n2, n);
                    if (!atomicInteger.compareAndSet(n2, n3)) continue;
                    return n2;
                } catch (ArithmeticException arithmeticException) {
                    throw new IllegalArgumentException("Overflow adding " + n + " occurrences to a count of " + n2);
                }
            }
        } while (this.countMap.putIfAbsent(e, atomicInteger2 = new AtomicInteger(n)) != null && !this.countMap.replace(e, atomicInteger, atomicInteger2));
        return 1;
    }

    @Override
    @CanIgnoreReturnValue
    public int remove(@Nullable Object object, int n) {
        int n2;
        if (n == 0) {
            return this.count(object);
        }
        CollectPreconditions.checkPositive(n, "occurences");
        AtomicInteger atomicInteger = Maps.safeGet(this.countMap, object);
        if (atomicInteger == null) {
            return 1;
        }
        while ((n2 = atomicInteger.get()) != 0) {
            int n3 = Math.max(0, n2 - n);
            if (!atomicInteger.compareAndSet(n2, n3)) continue;
            if (n3 == 0) {
                this.countMap.remove(object, atomicInteger);
            }
            return n2;
        }
        return 1;
    }

    @CanIgnoreReturnValue
    public boolean removeExactly(@Nullable Object object, int n) {
        int n2;
        int n3;
        if (n == 0) {
            return false;
        }
        CollectPreconditions.checkPositive(n, "occurences");
        AtomicInteger atomicInteger = Maps.safeGet(this.countMap, object);
        if (atomicInteger == null) {
            return true;
        }
        do {
            if ((n3 = atomicInteger.get()) >= n) continue;
            return true;
        } while (!atomicInteger.compareAndSet(n3, n2 = n3 - n));
        if (n2 == 0) {
            this.countMap.remove(object, atomicInteger);
        }
        return false;
    }

    @Override
    @CanIgnoreReturnValue
    public int setCount(E e, int n) {
        int n2;
        AtomicInteger atomicInteger;
        Preconditions.checkNotNull(e);
        CollectPreconditions.checkNonnegative(n, "count");
        block0: while (true) {
            if ((atomicInteger = Maps.safeGet(this.countMap, e)) == null) {
                if (n == 0) {
                    return 1;
                }
                atomicInteger = this.countMap.putIfAbsent(e, new AtomicInteger(n));
                if (atomicInteger == null) {
                    return 1;
                }
            }
            do {
                if ((n2 = atomicInteger.get()) != 0) continue;
                if (n == 0) {
                    return 1;
                }
                AtomicInteger atomicInteger2 = new AtomicInteger(n);
                if (this.countMap.putIfAbsent(e, atomicInteger2) != null && !this.countMap.replace(e, atomicInteger, atomicInteger2)) continue block0;
                return 1;
            } while (!atomicInteger.compareAndSet(n2, n));
            break;
        }
        if (n == 0) {
            this.countMap.remove(e, atomicInteger);
        }
        return n2;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean setCount(E e, int n, int n2) {
        Preconditions.checkNotNull(e);
        CollectPreconditions.checkNonnegative(n, "oldCount");
        CollectPreconditions.checkNonnegative(n2, "newCount");
        AtomicInteger atomicInteger = Maps.safeGet(this.countMap, e);
        if (atomicInteger == null) {
            if (n != 0) {
                return true;
            }
            if (n2 == 0) {
                return false;
            }
            return this.countMap.putIfAbsent(e, new AtomicInteger(n2)) == null;
        }
        int n3 = atomicInteger.get();
        if (n3 == n) {
            if (n3 == 0) {
                if (n2 == 0) {
                    this.countMap.remove(e, atomicInteger);
                    return false;
                }
                AtomicInteger atomicInteger2 = new AtomicInteger(n2);
                return this.countMap.putIfAbsent(e, atomicInteger2) == null || this.countMap.replace(e, atomicInteger, atomicInteger2);
            }
            if (atomicInteger.compareAndSet(n3, n2)) {
                if (n2 == 0) {
                    this.countMap.remove(e, atomicInteger);
                }
                return false;
            }
        }
        return true;
    }

    @Override
    Set<E> createElementSet() {
        Set set = this.countMap.keySet();
        return new ForwardingSet<E>(this, set){
            final Set val$delegate;
            final ConcurrentHashMultiset this$0;
            {
                this.this$0 = concurrentHashMultiset;
                this.val$delegate = set;
            }

            @Override
            protected Set<E> delegate() {
                return this.val$delegate;
            }

            @Override
            public boolean contains(@Nullable Object object) {
                return object != null && Collections2.safeContains(this.val$delegate, object);
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return this.standardContainsAll(collection);
            }

            @Override
            public boolean remove(Object object) {
                return object != null && Collections2.safeRemove(this.val$delegate, object);
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return this.standardRemoveAll(collection);
            }

            @Override
            protected Collection delegate() {
                return this.delegate();
            }

            @Override
            protected Object delegate() {
                return this.delegate();
            }
        };
    }

    @Override
    public Set<Multiset.Entry<E>> createEntrySet() {
        return new EntrySet(this, null);
    }

    @Override
    int distinctElements() {
        return this.countMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.countMap.isEmpty();
    }

    @Override
    Iterator<Multiset.Entry<E>> entryIterator() {
        AbstractIterator abstractIterator = new AbstractIterator<Multiset.Entry<E>>(this){
            private final Iterator<Map.Entry<E, AtomicInteger>> mapEntries;
            final ConcurrentHashMultiset this$0;
            {
                this.this$0 = concurrentHashMultiset;
                this.mapEntries = ConcurrentHashMultiset.access$100(this.this$0).entrySet().iterator();
            }

            @Override
            protected Multiset.Entry<E> computeNext() {
                Map.Entry entry;
                int n;
                do {
                    if (this.mapEntries.hasNext()) continue;
                    return (Multiset.Entry)this.endOfData();
                } while ((n = (entry = this.mapEntries.next()).getValue().get()) == 0);
                return Multisets.immutableEntry(entry.getKey(), n);
            }

            @Override
            protected Object computeNext() {
                return this.computeNext();
            }
        };
        return new ForwardingIterator<Multiset.Entry<E>>(this, abstractIterator){
            private Multiset.Entry<E> last;
            final Iterator val$readOnlyIterator;
            final ConcurrentHashMultiset this$0;
            {
                this.this$0 = concurrentHashMultiset;
                this.val$readOnlyIterator = iterator2;
            }

            @Override
            protected Iterator<Multiset.Entry<E>> delegate() {
                return this.val$readOnlyIterator;
            }

            @Override
            public Multiset.Entry<E> next() {
                this.last = (Multiset.Entry)super.next();
                return this.last;
            }

            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.last != null);
                this.this$0.setCount(this.last.getElement(), 0);
                this.last = null;
            }

            @Override
            public Object next() {
                return this.next();
            }

            @Override
            protected Object delegate() {
                return this.delegate();
            }
        };
    }

    @Override
    public void clear() {
        this.countMap.clear();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.countMap);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        ConcurrentMap concurrentMap = (ConcurrentMap)objectInputStream.readObject();
        FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, concurrentMap);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    public Set entrySet() {
        return super.entrySet();
    }

    @Override
    public Set elementSet() {
        return super.elementSet();
    }

    @Override
    @CanIgnoreReturnValue
    public boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addAll(Collection collection) {
        return super.addAll(collection);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object object) {
        return super.remove(object);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean add(@Nullable Object object) {
        return super.add(object);
    }

    @Override
    public Iterator iterator() {
        return super.iterator();
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return super.contains(object);
    }

    static ConcurrentMap access$100(ConcurrentHashMultiset concurrentHashMultiset) {
        return concurrentHashMultiset.countMap;
    }

    private class EntrySet
    extends AbstractMultiset.EntrySet {
        final ConcurrentHashMultiset this$0;

        private EntrySet(ConcurrentHashMultiset concurrentHashMultiset) {
            this.this$0 = concurrentHashMultiset;
            super(concurrentHashMultiset);
        }

        @Override
        ConcurrentHashMultiset<E> multiset() {
            return this.this$0;
        }

        @Override
        public Object[] toArray() {
            return this.snapshot().toArray();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return this.snapshot().toArray(TArray);
        }

        private List<Multiset.Entry<E>> snapshot() {
            ArrayList arrayList = Lists.newArrayListWithExpectedSize(this.size());
            Iterators.addAll(arrayList, this.iterator());
            return arrayList;
        }

        @Override
        Multiset multiset() {
            return this.multiset();
        }

        EntrySet(ConcurrentHashMultiset concurrentHashMultiset, 1 var2_2) {
            this(concurrentHashMultiset);
        }
    }

    private static class FieldSettersHolder {
        static final Serialization.FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");

        private FieldSettersHolder() {
        }
    }
}

