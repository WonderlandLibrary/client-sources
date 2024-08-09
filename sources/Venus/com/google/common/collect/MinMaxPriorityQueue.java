/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Ordering;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

@Beta
@GwtCompatible
public final class MinMaxPriorityQueue<E>
extends AbstractQueue<E> {
    private final Heap minHeap;
    private final Heap maxHeap;
    @VisibleForTesting
    final int maximumSize;
    private Object[] queue;
    private int size;
    private int modCount;
    private static final int EVEN_POWERS_OF_TWO = 0x55555555;
    private static final int ODD_POWERS_OF_TWO = -1431655766;
    private static final int DEFAULT_CAPACITY = 11;

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create() {
        return new Builder(Ordering.natural(), null).create();
    }

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create(Iterable<? extends E> iterable) {
        return new Builder(Ordering.natural(), null).create(iterable);
    }

    public static <B> Builder<B> orderedBy(Comparator<B> comparator) {
        return new Builder(comparator, null);
    }

    public static Builder<Comparable> expectedSize(int n) {
        return new Builder(Ordering.natural(), null).expectedSize(n);
    }

    public static Builder<Comparable> maximumSize(int n) {
        return new Builder(Ordering.natural(), null).maximumSize(n);
    }

    private MinMaxPriorityQueue(Builder<? super E> builder, int n) {
        Ordering ordering = Builder.access$200(builder);
        this.minHeap = new Heap(this, ordering);
        this.minHeap.otherHeap = this.maxHeap = new Heap(this, ordering.reverse());
        this.maxHeap.otherHeap = this.minHeap;
        this.maximumSize = Builder.access$300(builder);
        this.queue = new Object[n];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean add(E e) {
        this.offer(e);
        return false;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addAll(Collection<? extends E> collection) {
        boolean bl = false;
        for (E e : collection) {
            this.offer(e);
            bl = true;
        }
        return bl;
    }

    @Override
    @CanIgnoreReturnValue
    public boolean offer(E e) {
        Preconditions.checkNotNull(e);
        ++this.modCount;
        int n = this.size++;
        this.growIfNeeded();
        this.heapForIndex(n).bubbleUp(n, e);
        return this.size <= this.maximumSize || this.pollLast() != e;
    }

    @Override
    @CanIgnoreReturnValue
    public E poll() {
        return this.isEmpty() ? null : (E)this.removeAndGet(0);
    }

    E elementData(int n) {
        return (E)this.queue[n];
    }

    @Override
    public E peek() {
        return this.isEmpty() ? null : (E)this.elementData(0);
    }

    private int getMaxElementIndex() {
        switch (this.size) {
            case 1: {
                return 1;
            }
            case 2: {
                return 0;
            }
        }
        return this.maxHeap.compareElements(1, 2) <= 0 ? 1 : 2;
    }

    @CanIgnoreReturnValue
    public E pollFirst() {
        return this.poll();
    }

    @CanIgnoreReturnValue
    public E removeFirst() {
        return this.remove();
    }

    public E peekFirst() {
        return this.peek();
    }

    @CanIgnoreReturnValue
    public E pollLast() {
        return this.isEmpty() ? null : (E)this.removeAndGet(this.getMaxElementIndex());
    }

    @CanIgnoreReturnValue
    public E removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeAndGet(this.getMaxElementIndex());
    }

    public E peekLast() {
        return this.isEmpty() ? null : (E)this.elementData(this.getMaxElementIndex());
    }

    @VisibleForTesting
    @CanIgnoreReturnValue
    MoveDesc<E> removeAt(int n) {
        Preconditions.checkPositionIndex(n, this.size);
        ++this.modCount;
        --this.size;
        if (this.size == n) {
            this.queue[this.size] = null;
            return null;
        }
        E e = this.elementData(this.size);
        int n2 = this.heapForIndex(this.size).swapWithConceptuallyLastElement(e);
        if (n2 == n) {
            this.queue[this.size] = null;
            return null;
        }
        E e2 = this.elementData(this.size);
        this.queue[this.size] = null;
        MoveDesc<E> moveDesc = this.fillHole(n, e2);
        if (n2 < n) {
            if (moveDesc == null) {
                return new MoveDesc<E>(e, e2);
            }
            return new MoveDesc<E>(e, moveDesc.replaced);
        }
        return moveDesc;
    }

    private MoveDesc<E> fillHole(int n, E e) {
        int n2;
        Heap heap = this.heapForIndex(n);
        int n3 = heap.bubbleUpAlternatingLevels(n2 = heap.fillHoleAt(n), e);
        if (n3 == n2) {
            return heap.tryCrossOverAndBubbleUp(n, n2, e);
        }
        return n3 < n ? new MoveDesc<E>(e, this.elementData(n)) : null;
    }

    private E removeAndGet(int n) {
        E e = this.elementData(n);
        this.removeAt(n);
        return e;
    }

    private Heap heapForIndex(int n) {
        return MinMaxPriorityQueue.isEvenLevel(n) ? this.minHeap : this.maxHeap;
    }

    @VisibleForTesting
    static boolean isEvenLevel(int n) {
        int n2 = ~(~(n + 1));
        Preconditions.checkState(n2 > 0, "negative index");
        return (n2 & 0x55555555) > (n2 & 0xAAAAAAAA);
    }

    @VisibleForTesting
    boolean isIntact() {
        for (int i = 1; i < this.size; ++i) {
            if (Heap.access$400(this.heapForIndex(i), i)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new QueueIterator(this, null);
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.size; ++i) {
            this.queue[i] = null;
        }
        this.size = 0;
    }

    @Override
    public Object[] toArray() {
        Object[] objectArray = new Object[this.size];
        System.arraycopy(this.queue, 0, objectArray, 0, this.size);
        return objectArray;
    }

    public Comparator<? super E> comparator() {
        return this.minHeap.ordering;
    }

    @VisibleForTesting
    int capacity() {
        return this.queue.length;
    }

    @VisibleForTesting
    static int initialQueueSize(int n, int n2, Iterable<?> iterable) {
        int n3;
        int n4 = n3 = n == -1 ? 11 : n;
        if (iterable instanceof Collection) {
            int n5 = ((Collection)iterable).size();
            n3 = Math.max(n3, n5);
        }
        return MinMaxPriorityQueue.capAtMaximumSize(n3, n2);
    }

    private void growIfNeeded() {
        if (this.size > this.queue.length) {
            int n = this.calculateNewCapacity();
            Object[] objectArray = new Object[n];
            System.arraycopy(this.queue, 0, objectArray, 0, this.queue.length);
            this.queue = objectArray;
        }
    }

    private int calculateNewCapacity() {
        int n = this.queue.length;
        int n2 = n < 64 ? (n + 1) * 2 : IntMath.checkedMultiply(n / 2, 3);
        return MinMaxPriorityQueue.capAtMaximumSize(n2, this.maximumSize);
    }

    private static int capAtMaximumSize(int n, int n2) {
        return Math.min(n - 1, n2) + 1;
    }

    MinMaxPriorityQueue(Builder builder, int n, 1 var3_3) {
        this(builder, n);
    }

    static Object[] access$500(MinMaxPriorityQueue minMaxPriorityQueue) {
        return minMaxPriorityQueue.queue;
    }

    static int access$600(MinMaxPriorityQueue minMaxPriorityQueue) {
        return minMaxPriorityQueue.size;
    }

    static int access$700(MinMaxPriorityQueue minMaxPriorityQueue) {
        return minMaxPriorityQueue.modCount;
    }

    private class QueueIterator
    implements Iterator<E> {
        private int cursor;
        private int nextCursor;
        private int expectedModCount;
        private Queue<E> forgetMeNot;
        private List<E> skipMe;
        private E lastFromForgetMeNot;
        private boolean canRemove;
        final MinMaxPriorityQueue this$0;

        private QueueIterator(MinMaxPriorityQueue minMaxPriorityQueue) {
            this.this$0 = minMaxPriorityQueue;
            this.cursor = -1;
            this.nextCursor = -1;
            this.expectedModCount = MinMaxPriorityQueue.access$700(this.this$0);
        }

        @Override
        public boolean hasNext() {
            this.checkModCount();
            this.nextNotInSkipMe(this.cursor + 1);
            return this.nextCursor < this.this$0.size() || this.forgetMeNot != null && !this.forgetMeNot.isEmpty();
        }

        @Override
        public E next() {
            this.checkModCount();
            this.nextNotInSkipMe(this.cursor + 1);
            if (this.nextCursor < this.this$0.size()) {
                this.cursor = this.nextCursor;
                this.canRemove = true;
                return this.this$0.elementData(this.cursor);
            }
            if (this.forgetMeNot != null) {
                this.cursor = this.this$0.size();
                this.lastFromForgetMeNot = this.forgetMeNot.poll();
                if (this.lastFromForgetMeNot != null) {
                    this.canRemove = true;
                    return this.lastFromForgetMeNot;
                }
            }
            throw new NoSuchElementException("iterator moved past last element in queue.");
        }

        @Override
        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            this.checkModCount();
            this.canRemove = false;
            ++this.expectedModCount;
            if (this.cursor < this.this$0.size()) {
                MoveDesc moveDesc = this.this$0.removeAt(this.cursor);
                if (moveDesc != null) {
                    if (this.forgetMeNot == null) {
                        this.forgetMeNot = new ArrayDeque();
                        this.skipMe = new ArrayList(3);
                    }
                    if (!this.foundAndRemovedExactReference(this.skipMe, moveDesc.toTrickle)) {
                        this.forgetMeNot.add(moveDesc.toTrickle);
                    }
                    if (!this.foundAndRemovedExactReference(this.forgetMeNot, moveDesc.replaced)) {
                        this.skipMe.add(moveDesc.replaced);
                    }
                }
                --this.cursor;
                --this.nextCursor;
            } else {
                Preconditions.checkState(this.removeExact(this.lastFromForgetMeNot));
                this.lastFromForgetMeNot = null;
            }
        }

        private boolean foundAndRemovedExactReference(Iterable<E> iterable, E e) {
            Iterator iterator2 = iterable.iterator();
            while (iterator2.hasNext()) {
                Object e2 = iterator2.next();
                if (e2 != e) continue;
                iterator2.remove();
                return false;
            }
            return true;
        }

        private boolean removeExact(Object object) {
            for (int i = 0; i < MinMaxPriorityQueue.access$600(this.this$0); ++i) {
                if (MinMaxPriorityQueue.access$500(this.this$0)[i] != object) continue;
                this.this$0.removeAt(i);
                return false;
            }
            return true;
        }

        private void checkModCount() {
            if (MinMaxPriorityQueue.access$700(this.this$0) != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        private void nextNotInSkipMe(int n) {
            if (this.nextCursor < n) {
                if (this.skipMe != null) {
                    while (n < this.this$0.size() && this.foundAndRemovedExactReference(this.skipMe, this.this$0.elementData(n))) {
                        ++n;
                    }
                }
                this.nextCursor = n;
            }
        }

        QueueIterator(MinMaxPriorityQueue minMaxPriorityQueue, 1 var2_2) {
            this(minMaxPriorityQueue);
        }
    }

    private class Heap {
        final Ordering<E> ordering;
        @Weak
        Heap otherHeap;
        final MinMaxPriorityQueue this$0;

        Heap(MinMaxPriorityQueue minMaxPriorityQueue, Ordering<E> ordering) {
            this.this$0 = minMaxPriorityQueue;
            this.ordering = ordering;
        }

        int compareElements(int n, int n2) {
            return this.ordering.compare(this.this$0.elementData(n), this.this$0.elementData(n2));
        }

        MoveDesc<E> tryCrossOverAndBubbleUp(int n, int n2, E e) {
            int n3 = this.crossOver(n2, e);
            if (n3 == n2) {
                return null;
            }
            Object e2 = n3 < n ? this.this$0.elementData(n) : this.this$0.elementData(this.getParentIndex(n));
            if (this.otherHeap.bubbleUpAlternatingLevels(n3, e) < n) {
                return new MoveDesc(e, e2);
            }
            return null;
        }

        void bubbleUp(int n, E e) {
            Heap heap;
            int n2 = this.crossOverUp(n, e);
            if (n2 == n) {
                heap = this;
            } else {
                n = n2;
                heap = this.otherHeap;
            }
            heap.bubbleUpAlternatingLevels(n, e);
        }

        @CanIgnoreReturnValue
        int bubbleUpAlternatingLevels(int n, E e) {
            int n2;
            Object e2;
            while (n > 2 && this.ordering.compare(e2 = this.this$0.elementData(n2 = this.getGrandparentIndex(n)), e) > 0) {
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[n] = e2;
                n = n2;
            }
            MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[n] = e;
            return n;
        }

        int findMin(int n, int n2) {
            if (n >= MinMaxPriorityQueue.access$600(this.this$0)) {
                return 1;
            }
            Preconditions.checkState(n > 0);
            int n3 = Math.min(n, MinMaxPriorityQueue.access$600(this.this$0) - n2) + n2;
            int n4 = n;
            for (int i = n + 1; i < n3; ++i) {
                if (this.compareElements(i, n4) >= 0) continue;
                n4 = i;
            }
            return n4;
        }

        int findMinChild(int n) {
            return this.findMin(this.getLeftChildIndex(n), 2);
        }

        int findMinGrandChild(int n) {
            int n2 = this.getLeftChildIndex(n);
            if (n2 < 0) {
                return 1;
            }
            return this.findMin(this.getLeftChildIndex(n2), 4);
        }

        int crossOverUp(int n, E e) {
            Object e2;
            int n2;
            int n3;
            if (n == 0) {
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[0] = e;
                return 1;
            }
            int n4 = this.getParentIndex(n);
            Object e3 = this.this$0.elementData(n4);
            if (n4 != 0 && (n3 = this.getRightChildIndex(n2 = this.getParentIndex(n4))) != n4 && this.getLeftChildIndex(n3) >= MinMaxPriorityQueue.access$600(this.this$0) && this.ordering.compare(e2 = this.this$0.elementData(n3), e3) < 0) {
                n4 = n3;
                e3 = e2;
            }
            if (this.ordering.compare(e3, e) < 0) {
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[n] = e3;
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[n4] = e;
                return n4;
            }
            MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[n] = e;
            return n;
        }

        int swapWithConceptuallyLastElement(E e) {
            Object e2;
            int n;
            int n2;
            int n3 = this.getParentIndex(MinMaxPriorityQueue.access$600(this.this$0));
            if (n3 != 0 && (n2 = this.getRightChildIndex(n = this.getParentIndex(n3))) != n3 && this.getLeftChildIndex(n2) >= MinMaxPriorityQueue.access$600(this.this$0) && this.ordering.compare(e2 = this.this$0.elementData(n2), e) < 0) {
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[n2] = e;
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[MinMaxPriorityQueue.access$600((MinMaxPriorityQueue)this.this$0)] = e2;
                return n2;
            }
            return MinMaxPriorityQueue.access$600(this.this$0);
        }

        int crossOver(int n, E e) {
            int n2 = this.findMinChild(n);
            if (n2 > 0 && this.ordering.compare(this.this$0.elementData(n2), e) < 0) {
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[n] = this.this$0.elementData(n2);
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[n2] = e;
                return n2;
            }
            return this.crossOverUp(n, e);
        }

        int fillHoleAt(int n) {
            int n2;
            while ((n2 = this.findMinGrandChild(n)) > 0) {
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)this.this$0)[n] = this.this$0.elementData(n2);
                n = n2;
            }
            return n;
        }

        private boolean verifyIndex(int n) {
            if (this.getLeftChildIndex(n) < MinMaxPriorityQueue.access$600(this.this$0) && this.compareElements(n, this.getLeftChildIndex(n)) > 0) {
                return true;
            }
            if (this.getRightChildIndex(n) < MinMaxPriorityQueue.access$600(this.this$0) && this.compareElements(n, this.getRightChildIndex(n)) > 0) {
                return true;
            }
            if (n > 0 && this.compareElements(n, this.getParentIndex(n)) > 0) {
                return true;
            }
            return n > 2 && this.compareElements(this.getGrandparentIndex(n), n) > 0;
        }

        private int getLeftChildIndex(int n) {
            return n * 2 + 1;
        }

        private int getRightChildIndex(int n) {
            return n * 2 + 2;
        }

        private int getParentIndex(int n) {
            return (n - 1) / 2;
        }

        private int getGrandparentIndex(int n) {
            return this.getParentIndex(this.getParentIndex(n));
        }

        static boolean access$400(Heap heap, int n) {
            return heap.verifyIndex(n);
        }
    }

    static class MoveDesc<E> {
        final E toTrickle;
        final E replaced;

        MoveDesc(E e, E e2) {
            this.toTrickle = e;
            this.replaced = e2;
        }
    }

    @Beta
    public static final class Builder<B> {
        private static final int UNSET_EXPECTED_SIZE = -1;
        private final Comparator<B> comparator;
        private int expectedSize = -1;
        private int maximumSize = Integer.MAX_VALUE;

        private Builder(Comparator<B> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }

        @CanIgnoreReturnValue
        public Builder<B> expectedSize(int n) {
            Preconditions.checkArgument(n >= 0);
            this.expectedSize = n;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<B> maximumSize(int n) {
            Preconditions.checkArgument(n > 0);
            this.maximumSize = n;
            return this;
        }

        public <T extends B> MinMaxPriorityQueue<T> create() {
            return this.create(Collections.emptySet());
        }

        public <T extends B> MinMaxPriorityQueue<T> create(Iterable<? extends T> iterable) {
            MinMaxPriorityQueue<T> minMaxPriorityQueue = new MinMaxPriorityQueue<T>(this, MinMaxPriorityQueue.initialQueueSize(this.expectedSize, this.maximumSize, iterable), null);
            for (T t : iterable) {
                minMaxPriorityQueue.offer(t);
            }
            return minMaxPriorityQueue;
        }

        private <T extends B> Ordering<T> ordering() {
            return Ordering.from(this.comparator);
        }

        Builder(Comparator comparator, 1 var2_2) {
            this(comparator);
        }

        static Ordering access$200(Builder builder) {
            return builder.ordering();
        }

        static int access$300(Builder builder) {
            return builder.maximumSize;
        }
    }
}

