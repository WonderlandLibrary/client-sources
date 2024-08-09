/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractSortedMultiset;
import com.google.common.collect.BoundType;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.GeneralRange;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.Serialization;
import com.google.common.collect.SortedMultiset;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class TreeMultiset<E>
extends AbstractSortedMultiset<E>
implements Serializable {
    private final transient Reference<AvlNode<E>> rootReference;
    private final transient GeneralRange<E> range;
    private final transient AvlNode<E> header;
    @GwtIncompatible
    private static final long serialVersionUID = 1L;

    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset(Ordering.natural());
    }

    public static <E> TreeMultiset<E> create(@Nullable Comparator<? super E> comparator) {
        return comparator == null ? new TreeMultiset(Ordering.natural()) : new TreeMultiset<E>(comparator);
    }

    public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> iterable) {
        TreeMultiset<E> treeMultiset = TreeMultiset.create();
        Iterables.addAll(treeMultiset, iterable);
        return treeMultiset;
    }

    TreeMultiset(Reference<AvlNode<E>> reference, GeneralRange<E> generalRange, AvlNode<E> avlNode) {
        super(generalRange.comparator());
        this.rootReference = reference;
        this.range = generalRange;
        this.header = avlNode;
    }

    TreeMultiset(Comparator<? super E> comparator) {
        super(comparator);
        this.range = GeneralRange.all(comparator);
        this.header = new AvlNode<Object>(null, 1);
        TreeMultiset.successor(this.header, this.header);
        this.rootReference = new Reference(null);
    }

    private long aggregateForEntries(Aggregate aggregate) {
        AvlNode<E> avlNode = this.rootReference.get();
        long l = aggregate.treeAggregate(avlNode);
        if (this.range.hasLowerBound()) {
            l -= this.aggregateBelowRange(aggregate, avlNode);
        }
        if (this.range.hasUpperBound()) {
            l -= this.aggregateAboveRange(aggregate, avlNode);
        }
        return l;
    }

    private long aggregateBelowRange(Aggregate aggregate, @Nullable AvlNode<E> avlNode) {
        if (avlNode == null) {
            return 0L;
        }
        int n = this.comparator().compare(this.range.getLowerEndpoint(), AvlNode.access$500(avlNode));
        if (n < 0) {
            return this.aggregateBelowRange(aggregate, AvlNode.access$600(avlNode));
        }
        if (n == 0) {
            switch (4.$SwitchMap$com$google$common$collect$BoundType[this.range.getLowerBoundType().ordinal()]) {
                case 1: {
                    return (long)aggregate.nodeAggregate(avlNode) + aggregate.treeAggregate(AvlNode.access$600(avlNode));
                }
                case 2: {
                    return aggregate.treeAggregate(AvlNode.access$600(avlNode));
                }
            }
            throw new AssertionError();
        }
        return aggregate.treeAggregate(AvlNode.access$600(avlNode)) + (long)aggregate.nodeAggregate(avlNode) + this.aggregateBelowRange(aggregate, AvlNode.access$700(avlNode));
    }

    private long aggregateAboveRange(Aggregate aggregate, @Nullable AvlNode<E> avlNode) {
        if (avlNode == null) {
            return 0L;
        }
        int n = this.comparator().compare(this.range.getUpperEndpoint(), AvlNode.access$500(avlNode));
        if (n > 0) {
            return this.aggregateAboveRange(aggregate, AvlNode.access$700(avlNode));
        }
        if (n == 0) {
            switch (4.$SwitchMap$com$google$common$collect$BoundType[this.range.getUpperBoundType().ordinal()]) {
                case 1: {
                    return (long)aggregate.nodeAggregate(avlNode) + aggregate.treeAggregate(AvlNode.access$700(avlNode));
                }
                case 2: {
                    return aggregate.treeAggregate(AvlNode.access$700(avlNode));
                }
            }
            throw new AssertionError();
        }
        return aggregate.treeAggregate(AvlNode.access$700(avlNode)) + (long)aggregate.nodeAggregate(avlNode) + this.aggregateAboveRange(aggregate, AvlNode.access$600(avlNode));
    }

    @Override
    public int size() {
        return Ints.saturatedCast(this.aggregateForEntries(Aggregate.SIZE));
    }

    @Override
    int distinctElements() {
        return Ints.saturatedCast(this.aggregateForEntries(Aggregate.DISTINCT));
    }

    @Override
    public int count(@Nullable Object object) {
        try {
            Object object2 = object;
            AvlNode<Object> avlNode = this.rootReference.get();
            if (!this.range.contains(object2) || avlNode == null) {
                return 0;
            }
            return avlNode.count(this.comparator(), object2);
        } catch (ClassCastException classCastException) {
            return 1;
        } catch (NullPointerException nullPointerException) {
            return 1;
        }
    }

    @Override
    @CanIgnoreReturnValue
    public int add(@Nullable E e, int n) {
        CollectPreconditions.checkNonnegative(n, "occurrences");
        if (n == 0) {
            return this.count(e);
        }
        Preconditions.checkArgument(this.range.contains(e));
        AvlNode<E> avlNode = this.rootReference.get();
        if (avlNode == null) {
            this.comparator().compare(e, e);
            AvlNode<E> avlNode2 = new AvlNode<E>(e, n);
            TreeMultiset.successor(this.header, avlNode2, this.header);
            this.rootReference.checkAndSet(avlNode, avlNode2);
            return 1;
        }
        int[] nArray = new int[1];
        AvlNode<E> avlNode3 = avlNode.add(this.comparator(), e, n, nArray);
        this.rootReference.checkAndSet(avlNode, avlNode3);
        return nArray[0];
    }

    @Override
    @CanIgnoreReturnValue
    public int remove(@Nullable Object object, int n) {
        AvlNode<Object> avlNode;
        CollectPreconditions.checkNonnegative(n, "occurrences");
        if (n == 0) {
            return this.count(object);
        }
        AvlNode<Object> avlNode2 = this.rootReference.get();
        int[] nArray = new int[1];
        try {
            Object object2 = object;
            if (!this.range.contains(object2) || avlNode2 == null) {
                return 0;
            }
            avlNode = avlNode2.remove(this.comparator(), object2, n, nArray);
        } catch (ClassCastException classCastException) {
            return 1;
        } catch (NullPointerException nullPointerException) {
            return 1;
        }
        this.rootReference.checkAndSet(avlNode2, avlNode);
        return nArray[0];
    }

    @Override
    @CanIgnoreReturnValue
    public int setCount(@Nullable E e, int n) {
        CollectPreconditions.checkNonnegative(n, "count");
        if (!this.range.contains(e)) {
            Preconditions.checkArgument(n == 0);
            return 1;
        }
        AvlNode<E> avlNode = this.rootReference.get();
        if (avlNode == null) {
            if (n > 0) {
                this.add(e, n);
            }
            return 1;
        }
        int[] nArray = new int[1];
        AvlNode<E> avlNode2 = avlNode.setCount(this.comparator(), e, n, nArray);
        this.rootReference.checkAndSet(avlNode, avlNode2);
        return nArray[0];
    }

    @Override
    @CanIgnoreReturnValue
    public boolean setCount(@Nullable E e, int n, int n2) {
        CollectPreconditions.checkNonnegative(n2, "newCount");
        CollectPreconditions.checkNonnegative(n, "oldCount");
        Preconditions.checkArgument(this.range.contains(e));
        AvlNode<E> avlNode = this.rootReference.get();
        if (avlNode == null) {
            if (n == 0) {
                if (n2 > 0) {
                    this.add(e, n2);
                }
                return false;
            }
            return true;
        }
        int[] nArray = new int[1];
        AvlNode<E> avlNode2 = avlNode.setCount(this.comparator(), e, n, n2, nArray);
        this.rootReference.checkAndSet(avlNode, avlNode2);
        return nArray[0] == n;
    }

    private Multiset.Entry<E> wrapEntry(AvlNode<E> avlNode) {
        return new Multisets.AbstractEntry<E>(this, avlNode){
            final AvlNode val$baseEntry;
            final TreeMultiset this$0;
            {
                this.this$0 = treeMultiset;
                this.val$baseEntry = avlNode;
            }

            @Override
            public E getElement() {
                return this.val$baseEntry.getElement();
            }

            @Override
            public int getCount() {
                int n = this.val$baseEntry.getCount();
                if (n == 0) {
                    return this.this$0.count(this.getElement());
                }
                return n;
            }
        };
    }

    @Nullable
    private AvlNode<E> firstNode() {
        AvlNode avlNode;
        AvlNode<E> avlNode2 = this.rootReference.get();
        if (avlNode2 == null) {
            return null;
        }
        if (this.range.hasLowerBound()) {
            E e = this.range.getLowerEndpoint();
            avlNode = AvlNode.access$800(this.rootReference.get(), this.comparator(), e);
            if (avlNode == null) {
                return null;
            }
            if (this.range.getLowerBoundType() == BoundType.OPEN && this.comparator().compare(e, avlNode.getElement()) == 0) {
                avlNode = AvlNode.access$900(avlNode);
            }
        } else {
            avlNode = AvlNode.access$900(this.header);
        }
        return avlNode == this.header || !this.range.contains(avlNode.getElement()) ? null : avlNode;
    }

    @Nullable
    private AvlNode<E> lastNode() {
        AvlNode avlNode;
        AvlNode<E> avlNode2 = this.rootReference.get();
        if (avlNode2 == null) {
            return null;
        }
        if (this.range.hasUpperBound()) {
            E e = this.range.getUpperEndpoint();
            avlNode = AvlNode.access$1000(this.rootReference.get(), this.comparator(), e);
            if (avlNode == null) {
                return null;
            }
            if (this.range.getUpperBoundType() == BoundType.OPEN && this.comparator().compare(e, avlNode.getElement()) == 0) {
                avlNode = AvlNode.access$1100(avlNode);
            }
        } else {
            avlNode = AvlNode.access$1100(this.header);
        }
        return avlNode == this.header || !this.range.contains(avlNode.getElement()) ? null : avlNode;
    }

    @Override
    Iterator<Multiset.Entry<E>> entryIterator() {
        return new Iterator<Multiset.Entry<E>>(this){
            AvlNode<E> current;
            Multiset.Entry<E> prevEntry;
            final TreeMultiset this$0;
            {
                this.this$0 = treeMultiset;
                this.current = TreeMultiset.access$1200(this.this$0);
            }

            @Override
            public boolean hasNext() {
                if (this.current == null) {
                    return true;
                }
                if (TreeMultiset.access$1300(this.this$0).tooHigh(this.current.getElement())) {
                    this.current = null;
                    return true;
                }
                return false;
            }

            @Override
            public Multiset.Entry<E> next() {
                Multiset.Entry entry;
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.prevEntry = entry = TreeMultiset.access$1400(this.this$0, this.current);
                this.current = AvlNode.access$900(this.current) == TreeMultiset.access$1500(this.this$0) ? null : AvlNode.access$900(this.current);
                return entry;
            }

            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.prevEntry != null);
                this.this$0.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    @Override
    Iterator<Multiset.Entry<E>> descendingEntryIterator() {
        return new Iterator<Multiset.Entry<E>>(this){
            AvlNode<E> current;
            Multiset.Entry<E> prevEntry;
            final TreeMultiset this$0;
            {
                this.this$0 = treeMultiset;
                this.current = TreeMultiset.access$1600(this.this$0);
                this.prevEntry = null;
            }

            @Override
            public boolean hasNext() {
                if (this.current == null) {
                    return true;
                }
                if (TreeMultiset.access$1300(this.this$0).tooLow(this.current.getElement())) {
                    this.current = null;
                    return true;
                }
                return false;
            }

            @Override
            public Multiset.Entry<E> next() {
                Multiset.Entry entry;
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.prevEntry = entry = TreeMultiset.access$1400(this.this$0, this.current);
                this.current = AvlNode.access$1100(this.current) == TreeMultiset.access$1500(this.this$0) ? null : AvlNode.access$1100(this.current);
                return entry;
            }

            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.prevEntry != null);
                this.this$0.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    @Override
    public SortedMultiset<E> headMultiset(@Nullable E e, BoundType boundType) {
        return new TreeMultiset<E>(this.rootReference, this.range.intersect(GeneralRange.upTo(this.comparator(), e, boundType)), this.header);
    }

    @Override
    public SortedMultiset<E> tailMultiset(@Nullable E e, BoundType boundType) {
        return new TreeMultiset<E>(this.rootReference, this.range.intersect(GeneralRange.downTo(this.comparator(), e, boundType)), this.header);
    }

    static int distinctElements(@Nullable AvlNode<?> avlNode) {
        return avlNode == null ? 0 : AvlNode.access$400(avlNode);
    }

    private static <T> void successor(AvlNode<T> avlNode, AvlNode<T> avlNode2) {
        AvlNode.access$902(avlNode, avlNode2);
        AvlNode.access$1102(avlNode2, avlNode);
    }

    private static <T> void successor(AvlNode<T> avlNode, AvlNode<T> avlNode2, AvlNode<T> avlNode3) {
        TreeMultiset.successor(avlNode, avlNode2);
        TreeMultiset.successor(avlNode2, avlNode3);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.elementSet().comparator());
        Serialization.writeMultiset(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Comparator comparator = (Comparator)objectInputStream.readObject();
        Serialization.getFieldSetter(AbstractSortedMultiset.class, "comparator").set((AbstractSortedMultiset)this, comparator);
        Serialization.getFieldSetter(TreeMultiset.class, "range").set(this, GeneralRange.all(comparator));
        Serialization.getFieldSetter(TreeMultiset.class, "rootReference").set(this, new Reference(null));
        AvlNode<Object> avlNode = new AvlNode<Object>(null, 1);
        Serialization.getFieldSetter(TreeMultiset.class, "header").set(this, avlNode);
        TreeMultiset.successor(avlNode, avlNode);
        Serialization.populateMultiset(this, objectInputStream);
    }

    @Override
    public SortedMultiset descendingMultiset() {
        return super.descendingMultiset();
    }

    @Override
    public SortedMultiset subMultiset(@Nullable Object object, BoundType boundType, @Nullable Object object2, BoundType boundType2) {
        return super.subMultiset(object, boundType, object2, boundType2);
    }

    @Override
    public Multiset.Entry pollLastEntry() {
        return super.pollLastEntry();
    }

    @Override
    public Multiset.Entry pollFirstEntry() {
        return super.pollFirstEntry();
    }

    @Override
    public Multiset.Entry lastEntry() {
        return super.lastEntry();
    }

    @Override
    public Multiset.Entry firstEntry() {
        return super.firstEntry();
    }

    @Override
    public Comparator comparator() {
        return super.comparator();
    }

    @Override
    public NavigableSet elementSet() {
        return super.elementSet();
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
    public void clear() {
        super.clear();
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

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    static AvlNode access$1200(TreeMultiset treeMultiset) {
        return treeMultiset.firstNode();
    }

    static GeneralRange access$1300(TreeMultiset treeMultiset) {
        return treeMultiset.range;
    }

    static Multiset.Entry access$1400(TreeMultiset treeMultiset, AvlNode avlNode) {
        return treeMultiset.wrapEntry(avlNode);
    }

    static AvlNode access$1500(TreeMultiset treeMultiset) {
        return treeMultiset.header;
    }

    static AvlNode access$1600(TreeMultiset treeMultiset) {
        return treeMultiset.lastNode();
    }

    static void access$1700(AvlNode avlNode, AvlNode avlNode2, AvlNode avlNode3) {
        TreeMultiset.successor(avlNode, avlNode2, avlNode3);
    }

    static void access$1800(AvlNode avlNode, AvlNode avlNode2) {
        TreeMultiset.successor(avlNode, avlNode2);
    }

    private static final class AvlNode<E>
    extends Multisets.AbstractEntry<E> {
        @Nullable
        private final E elem;
        private int elemCount;
        private int distinctElements;
        private long totalCount;
        private int height;
        private AvlNode<E> left;
        private AvlNode<E> right;
        private AvlNode<E> pred;
        private AvlNode<E> succ;

        AvlNode(@Nullable E e, int n) {
            Preconditions.checkArgument(n > 0);
            this.elem = e;
            this.elemCount = n;
            this.totalCount = n;
            this.distinctElements = 1;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

        public int count(Comparator<? super E> comparator, E e) {
            int n = comparator.compare(e, this.elem);
            if (n < 0) {
                return this.left == null ? 0 : this.left.count(comparator, e);
            }
            if (n > 0) {
                return this.right == null ? 0 : this.right.count(comparator, e);
            }
            return this.elemCount;
        }

        private AvlNode<E> addRightChild(E e, int n) {
            this.right = new AvlNode<E>(e, n);
            TreeMultiset.access$1700(this, this.right, this.succ);
            this.height = Math.max(2, this.height);
            ++this.distinctElements;
            this.totalCount += (long)n;
            return this;
        }

        private AvlNode<E> addLeftChild(E e, int n) {
            this.left = new AvlNode<E>(e, n);
            TreeMultiset.access$1700(this.pred, this.left, this);
            this.height = Math.max(2, this.height);
            ++this.distinctElements;
            this.totalCount += (long)n;
            return this;
        }

        AvlNode<E> add(Comparator<? super E> comparator, @Nullable E e, int n, int[] nArray) {
            int n2 = comparator.compare(e, this.elem);
            if (n2 < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    nArray[0] = 0;
                    return this.addLeftChild(e, n);
                }
                int n3 = avlNode.height;
                this.left = avlNode.add(comparator, e, n, nArray);
                if (nArray[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += (long)n;
                return this.left.height == n3 ? this : this.rebalance();
            }
            if (n2 > 0) {
                AvlNode<E> avlNode = this.right;
                if (avlNode == null) {
                    nArray[0] = 0;
                    return this.addRightChild(e, n);
                }
                int n4 = avlNode.height;
                this.right = avlNode.add(comparator, e, n, nArray);
                if (nArray[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += (long)n;
                return this.right.height == n4 ? this : super.rebalance();
            }
            nArray[0] = this.elemCount;
            long l = (long)this.elemCount + (long)n;
            Preconditions.checkArgument(l <= Integer.MAX_VALUE);
            this.elemCount += n;
            this.totalCount += (long)n;
            return this;
        }

        AvlNode<E> remove(Comparator<? super E> comparator, @Nullable E e, int n, int[] nArray) {
            int n2 = comparator.compare(e, this.elem);
            if (n2 < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    nArray[0] = 0;
                    return this;
                }
                this.left = avlNode.remove(comparator, e, n, nArray);
                if (nArray[0] > 0) {
                    if (n >= nArray[0]) {
                        --this.distinctElements;
                        this.totalCount -= (long)nArray[0];
                    } else {
                        this.totalCount -= (long)n;
                    }
                }
                return nArray[0] == 0 ? this : this.rebalance();
            }
            if (n2 > 0) {
                AvlNode<E> avlNode = this.right;
                if (avlNode == null) {
                    nArray[0] = 0;
                    return this;
                }
                this.right = avlNode.remove(comparator, e, n, nArray);
                if (nArray[0] > 0) {
                    if (n >= nArray[0]) {
                        --this.distinctElements;
                        this.totalCount -= (long)nArray[0];
                    } else {
                        this.totalCount -= (long)n;
                    }
                }
                return this.rebalance();
            }
            nArray[0] = this.elemCount;
            if (n >= this.elemCount) {
                return this.deleteMe();
            }
            this.elemCount -= n;
            this.totalCount -= (long)n;
            return this;
        }

        AvlNode<E> setCount(Comparator<? super E> comparator, @Nullable E e, int n, int[] nArray) {
            int n2 = comparator.compare(e, this.elem);
            if (n2 < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    nArray[0] = 0;
                    return n > 0 ? this.addLeftChild(e, n) : this;
                }
                this.left = avlNode.setCount(comparator, e, n, nArray);
                if (n == 0 && nArray[0] != 0) {
                    --this.distinctElements;
                } else if (n > 0 && nArray[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += (long)(n - nArray[0]);
                return super.rebalance();
            }
            if (n2 > 0) {
                AvlNode<E> avlNode = this.right;
                if (avlNode == null) {
                    nArray[0] = 0;
                    return n > 0 ? super.addRightChild(e, n) : this;
                }
                this.right = avlNode.setCount(comparator, e, n, nArray);
                if (n == 0 && nArray[0] != 0) {
                    --this.distinctElements;
                } else if (n > 0 && nArray[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += (long)(n - nArray[0]);
                return super.rebalance();
            }
            nArray[0] = this.elemCount;
            if (n == 0) {
                return this.deleteMe();
            }
            this.totalCount += (long)(n - this.elemCount);
            this.elemCount = n;
            return this;
        }

        AvlNode<E> setCount(Comparator<? super E> comparator, @Nullable E e, int n, int n2, int[] nArray) {
            int n3 = comparator.compare(e, this.elem);
            if (n3 < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    nArray[0] = 0;
                    if (n == 0 && n2 > 0) {
                        return this.addLeftChild(e, n2);
                    }
                    return this;
                }
                this.left = avlNode.setCount(comparator, e, n, n2, nArray);
                if (nArray[0] == n) {
                    if (n2 == 0 && nArray[0] != 0) {
                        --this.distinctElements;
                    } else if (n2 > 0 && nArray[0] == 0) {
                        ++this.distinctElements;
                    }
                    this.totalCount += (long)(n2 - nArray[0]);
                }
                return this.rebalance();
            }
            if (n3 > 0) {
                AvlNode<E> avlNode = this.right;
                if (avlNode == null) {
                    nArray[0] = 0;
                    if (n == 0 && n2 > 0) {
                        return this.addRightChild(e, n2);
                    }
                    return this;
                }
                this.right = avlNode.setCount(comparator, e, n, n2, nArray);
                if (nArray[0] == n) {
                    if (n2 == 0 && nArray[0] != 0) {
                        --this.distinctElements;
                    } else if (n2 > 0 && nArray[0] == 0) {
                        ++this.distinctElements;
                    }
                    this.totalCount += (long)(n2 - nArray[0]);
                }
                return this.rebalance();
            }
            nArray[0] = this.elemCount;
            if (n == this.elemCount) {
                if (n2 == 0) {
                    return this.deleteMe();
                }
                this.totalCount += (long)(n2 - this.elemCount);
                this.elemCount = n2;
            }
            return this;
        }

        private AvlNode<E> deleteMe() {
            int n = this.elemCount;
            this.elemCount = 0;
            TreeMultiset.access$1800(this.pred, this.succ);
            if (this.left == null) {
                return this.right;
            }
            if (this.right == null) {
                return this.left;
            }
            if (this.left.height >= this.right.height) {
                AvlNode<E> avlNode = this.pred;
                avlNode.left = super.removeMax(avlNode);
                avlNode.right = this.right;
                avlNode.distinctElements = this.distinctElements - 1;
                avlNode.totalCount = this.totalCount - (long)n;
                return super.rebalance();
            }
            AvlNode<E> avlNode = this.succ;
            avlNode.right = super.removeMin(avlNode);
            avlNode.left = this.left;
            avlNode.distinctElements = this.distinctElements - 1;
            avlNode.totalCount = this.totalCount - (long)n;
            return super.rebalance();
        }

        private AvlNode<E> removeMin(AvlNode<E> avlNode) {
            if (this.left == null) {
                return this.right;
            }
            this.left = super.removeMin(avlNode);
            --this.distinctElements;
            this.totalCount -= (long)avlNode.elemCount;
            return this.rebalance();
        }

        private AvlNode<E> removeMax(AvlNode<E> avlNode) {
            if (this.right == null) {
                return this.left;
            }
            this.right = super.removeMax(avlNode);
            --this.distinctElements;
            this.totalCount -= (long)avlNode.elemCount;
            return this.rebalance();
        }

        private void recomputeMultiset() {
            this.distinctElements = 1 + TreeMultiset.distinctElements(this.left) + TreeMultiset.distinctElements(this.right);
            this.totalCount = (long)this.elemCount + AvlNode.totalCount(this.left) + AvlNode.totalCount(this.right);
        }

        private void recomputeHeight() {
            this.height = 1 + Math.max(AvlNode.height(this.left), AvlNode.height(this.right));
        }

        private void recompute() {
            this.recomputeMultiset();
            this.recomputeHeight();
        }

        private AvlNode<E> rebalance() {
            switch (this.balanceFactor()) {
                case -2: {
                    if (super.balanceFactor() > 0) {
                        this.right = super.rotateRight();
                    }
                    return this.rotateLeft();
                }
                case 2: {
                    if (super.balanceFactor() < 0) {
                        this.left = super.rotateLeft();
                    }
                    return this.rotateRight();
                }
            }
            this.recomputeHeight();
            return this;
        }

        private int balanceFactor() {
            return AvlNode.height(this.left) - AvlNode.height(this.right);
        }

        private AvlNode<E> rotateLeft() {
            Preconditions.checkState(this.right != null);
            AvlNode<E> avlNode = this.right;
            this.right = avlNode.left;
            avlNode.left = this;
            avlNode.totalCount = this.totalCount;
            avlNode.distinctElements = this.distinctElements;
            this.recompute();
            super.recomputeHeight();
            return avlNode;
        }

        private AvlNode<E> rotateRight() {
            Preconditions.checkState(this.left != null);
            AvlNode<E> avlNode = this.left;
            this.left = avlNode.right;
            avlNode.right = this;
            avlNode.totalCount = this.totalCount;
            avlNode.distinctElements = this.distinctElements;
            this.recompute();
            super.recomputeHeight();
            return avlNode;
        }

        private static long totalCount(@Nullable AvlNode<?> avlNode) {
            return avlNode == null ? 0L : avlNode.totalCount;
        }

        private static int height(@Nullable AvlNode<?> avlNode) {
            return avlNode == null ? 0 : avlNode.height;
        }

        @Nullable
        private AvlNode<E> ceiling(Comparator<? super E> comparator, E e) {
            int n = comparator.compare(e, this.elem);
            if (n < 0) {
                return this.left == null ? this : MoreObjects.firstNonNull(super.ceiling(comparator, e), this);
            }
            if (n == 0) {
                return this;
            }
            return this.right == null ? null : super.ceiling(comparator, e);
        }

        @Nullable
        private AvlNode<E> floor(Comparator<? super E> comparator, E e) {
            int n = comparator.compare(e, this.elem);
            if (n > 0) {
                return this.right == null ? this : MoreObjects.firstNonNull(super.floor(comparator, e), this);
            }
            if (n == 0) {
                return this;
            }
            return this.left == null ? null : super.floor(comparator, e);
        }

        @Override
        public E getElement() {
            return this.elem;
        }

        @Override
        public int getCount() {
            return this.elemCount;
        }

        @Override
        public String toString() {
            return Multisets.immutableEntry(this.getElement(), this.getCount()).toString();
        }

        static int access$200(AvlNode avlNode) {
            return avlNode.elemCount;
        }

        static long access$300(AvlNode avlNode) {
            return avlNode.totalCount;
        }

        static int access$400(AvlNode avlNode) {
            return avlNode.distinctElements;
        }

        static Object access$500(AvlNode avlNode) {
            return avlNode.elem;
        }

        static AvlNode access$600(AvlNode avlNode) {
            return avlNode.left;
        }

        static AvlNode access$700(AvlNode avlNode) {
            return avlNode.right;
        }

        static AvlNode access$800(AvlNode avlNode, Comparator comparator, Object object) {
            return avlNode.ceiling(comparator, object);
        }

        static AvlNode access$900(AvlNode avlNode) {
            return avlNode.succ;
        }

        static AvlNode access$1000(AvlNode avlNode, Comparator comparator, Object object) {
            return avlNode.floor(comparator, object);
        }

        static AvlNode access$1100(AvlNode avlNode) {
            return avlNode.pred;
        }

        static AvlNode access$902(AvlNode avlNode, AvlNode avlNode2) {
            avlNode.succ = avlNode2;
            return avlNode.succ;
        }

        static AvlNode access$1102(AvlNode avlNode, AvlNode avlNode2) {
            avlNode.pred = avlNode2;
            return avlNode.pred;
        }
    }

    private static final class Reference<T> {
        @Nullable
        private T value;

        private Reference() {
        }

        @Nullable
        public T get() {
            return this.value;
        }

        public void checkAndSet(@Nullable T t, T t2) {
            if (this.value != t) {
                throw new ConcurrentModificationException();
            }
            this.value = t2;
        }

        Reference(1 var1_1) {
            this();
        }
    }

    private static enum Aggregate {
        SIZE{

            @Override
            int nodeAggregate(AvlNode<?> avlNode) {
                return AvlNode.access$200(avlNode);
            }

            @Override
            long treeAggregate(@Nullable AvlNode<?> avlNode) {
                return avlNode == null ? 0L : AvlNode.access$300(avlNode);
            }
        }
        ,
        DISTINCT{

            @Override
            int nodeAggregate(AvlNode<?> avlNode) {
                return 0;
            }

            @Override
            long treeAggregate(@Nullable AvlNode<?> avlNode) {
                return avlNode == null ? 0L : (long)AvlNode.access$400(avlNode);
            }
        };


        private Aggregate() {
        }

        abstract int nodeAggregate(AvlNode<?> var1);

        abstract long treeAggregate(@Nullable AvlNode<?> var1);

        Aggregate(1 var3_3) {
            this();
        }
    }
}

