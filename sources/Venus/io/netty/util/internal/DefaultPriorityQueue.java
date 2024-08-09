/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PriorityQueue;
import io.netty.util.internal.PriorityQueueNode;
import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class DefaultPriorityQueue<T extends PriorityQueueNode>
extends AbstractQueue<T>
implements PriorityQueue<T> {
    private static final PriorityQueueNode[] EMPTY_ARRAY = new PriorityQueueNode[0];
    private final Comparator<T> comparator;
    private T[] queue;
    private int size;

    public DefaultPriorityQueue(Comparator<T> comparator, int n) {
        this.comparator = ObjectUtil.checkNotNull(comparator, "comparator");
        this.queue = n != 0 ? new PriorityQueueNode[n] : EMPTY_ARRAY;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object object) {
        if (!(object instanceof PriorityQueueNode)) {
            return true;
        }
        PriorityQueueNode priorityQueueNode = (PriorityQueueNode)object;
        return this.contains(priorityQueueNode, priorityQueueNode.priorityQueueIndex(this));
    }

    @Override
    public boolean containsTyped(T t) {
        return this.contains((PriorityQueueNode)t, t.priorityQueueIndex(this));
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.size; ++i) {
            T t = this.queue[i];
            if (t == null) continue;
            t.priorityQueueIndex(this, -1);
            this.queue[i] = null;
        }
        this.size = 0;
    }

    @Override
    public void clearIgnoringIndexes() {
        this.size = 0;
    }

    @Override
    public boolean offer(T t) {
        if (t.priorityQueueIndex(this) != -1) {
            throw new IllegalArgumentException("e.priorityQueueIndex(): " + t.priorityQueueIndex(this) + " (expected: " + -1 + ") + e: " + t);
        }
        if (this.size >= this.queue.length) {
            this.queue = (PriorityQueueNode[])Arrays.copyOf(this.queue, this.queue.length + (this.queue.length < 64 ? this.queue.length + 2 : this.queue.length >>> 1));
        }
        this.bubbleUp(this.size++, t);
        return false;
    }

    @Override
    public T poll() {
        if (this.size == 0) {
            return null;
        }
        T t = this.queue[0];
        t.priorityQueueIndex(this, -1);
        T t2 = this.queue[--this.size];
        this.queue[this.size] = null;
        if (this.size != 0) {
            this.bubbleDown(0, t2);
        }
        return t;
    }

    @Override
    public T peek() {
        return this.size == 0 ? null : (T)this.queue[0];
    }

    @Override
    public boolean remove(Object object) {
        PriorityQueueNode priorityQueueNode;
        try {
            priorityQueueNode = (PriorityQueueNode)object;
        } catch (ClassCastException classCastException) {
            return true;
        }
        return this.removeTyped((T)priorityQueueNode);
    }

    @Override
    public boolean removeTyped(T t) {
        int n = t.priorityQueueIndex(this);
        if (!this.contains((PriorityQueueNode)t, n)) {
            return true;
        }
        t.priorityQueueIndex(this, -1);
        if (--this.size == 0 || this.size == n) {
            this.queue[n] = null;
            return false;
        }
        T t2 = this.queue[n] = this.queue[this.size];
        this.queue[this.size] = null;
        if (this.comparator.compare(t, t2) < 0) {
            this.bubbleDown(n, t2);
        } else {
            this.bubbleUp(n, t2);
        }
        return false;
    }

    @Override
    public void priorityChanged(T t) {
        int n = t.priorityQueueIndex(this);
        if (!this.contains((PriorityQueueNode)t, n)) {
            return;
        }
        if (n == 0) {
            this.bubbleDown(n, t);
        } else {
            int n2 = n - 1 >>> 1;
            T t2 = this.queue[n2];
            if (this.comparator.compare(t, t2) < 0) {
                this.bubbleUp(n, t);
            } else {
                this.bubbleDown(n, t);
            }
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.queue, this.size);
    }

    @Override
    public <X> X[] toArray(X[] XArray) {
        if (XArray.length < this.size) {
            return Arrays.copyOf(this.queue, this.size, XArray.getClass());
        }
        System.arraycopy(this.queue, 0, XArray, 0, this.size);
        if (XArray.length > this.size) {
            XArray[this.size] = null;
        }
        return XArray;
    }

    @Override
    public Iterator<T> iterator() {
        return new PriorityQueueIterator(this, null);
    }

    private boolean contains(PriorityQueueNode priorityQueueNode, int n) {
        return n >= 0 && n < this.size && priorityQueueNode.equals(this.queue[n]);
    }

    private void bubbleDown(int n, T t) {
        int n2 = this.size >>> 1;
        while (n < n2) {
            int n3 = (n << 1) + 1;
            T t2 = this.queue[n3];
            int n4 = n3 + 1;
            if (n4 < this.size && this.comparator.compare(t2, this.queue[n4]) > 0) {
                n3 = n4;
                t2 = this.queue[n3];
            }
            if (this.comparator.compare(t, t2) <= 0) break;
            this.queue[n] = t2;
            t2.priorityQueueIndex(this, n);
            n = n3;
        }
        this.queue[n] = t;
        t.priorityQueueIndex(this, n);
    }

    private void bubbleUp(int n, T t) {
        int n2;
        T t2;
        while (n > 0 && this.comparator.compare(t, t2 = this.queue[n2 = n - 1 >>> 1]) < 0) {
            this.queue[n] = t2;
            t2.priorityQueueIndex(this, n);
            n = n2;
        }
        this.queue[n] = t;
        t.priorityQueueIndex(this, n);
    }

    @Override
    public Object peek() {
        return this.peek();
    }

    @Override
    public Object poll() {
        return this.poll();
    }

    @Override
    public boolean offer(Object object) {
        return this.offer((T)((PriorityQueueNode)object));
    }

    @Override
    public void priorityChanged(Object object) {
        this.priorityChanged((T)((PriorityQueueNode)object));
    }

    @Override
    public boolean containsTyped(Object object) {
        return this.containsTyped((T)((PriorityQueueNode)object));
    }

    @Override
    public boolean removeTyped(Object object) {
        return this.removeTyped((T)((PriorityQueueNode)object));
    }

    static int access$100(DefaultPriorityQueue defaultPriorityQueue) {
        return defaultPriorityQueue.size;
    }

    static PriorityQueueNode[] access$200(DefaultPriorityQueue defaultPriorityQueue) {
        return defaultPriorityQueue.queue;
    }

    private final class PriorityQueueIterator
    implements Iterator<T> {
        private int index;
        final DefaultPriorityQueue this$0;

        private PriorityQueueIterator(DefaultPriorityQueue defaultPriorityQueue) {
            this.this$0 = defaultPriorityQueue;
        }

        @Override
        public boolean hasNext() {
            return this.index < DefaultPriorityQueue.access$100(this.this$0);
        }

        @Override
        public T next() {
            if (this.index >= DefaultPriorityQueue.access$100(this.this$0)) {
                throw new NoSuchElementException();
            }
            return DefaultPriorityQueue.access$200(this.this$0)[this.index++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public Object next() {
            return this.next();
        }

        PriorityQueueIterator(DefaultPriorityQueue defaultPriorityQueue, 1 var2_2) {
            this(defaultPriorityQueue);
        }
    }
}

