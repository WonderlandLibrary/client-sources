/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

import it.unimi.dsi.fastutil.IndirectPriorityQueue;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class IndirectPriorityQueues {
    public static final EmptyIndirectPriorityQueue EMPTY_QUEUE = new EmptyIndirectPriorityQueue();

    private IndirectPriorityQueues() {
    }

    public static <K> IndirectPriorityQueue<K> synchronize(IndirectPriorityQueue<K> indirectPriorityQueue) {
        return new SynchronizedIndirectPriorityQueue<K>(indirectPriorityQueue);
    }

    public static <K> IndirectPriorityQueue<K> synchronize(IndirectPriorityQueue<K> indirectPriorityQueue, Object object) {
        return new SynchronizedIndirectPriorityQueue<K>(indirectPriorityQueue, object);
    }

    public static class SynchronizedIndirectPriorityQueue<K>
    implements IndirectPriorityQueue<K> {
        public static final long serialVersionUID = -7046029254386353129L;
        protected final IndirectPriorityQueue<K> q;
        protected final Object sync;

        protected SynchronizedIndirectPriorityQueue(IndirectPriorityQueue<K> indirectPriorityQueue, Object object) {
            this.q = indirectPriorityQueue;
            this.sync = object;
        }

        protected SynchronizedIndirectPriorityQueue(IndirectPriorityQueue<K> indirectPriorityQueue) {
            this.q = indirectPriorityQueue;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void enqueue(int n) {
            Object object = this.sync;
            synchronized (object) {
                this.q.enqueue(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int dequeue() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.dequeue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.q.contains(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int first() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.first();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int last() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.last();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isEmpty() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.isEmpty();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int size() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.size();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void clear() {
            Object object = this.sync;
            synchronized (object) {
                this.q.clear();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void changed() {
            Object object = this.sync;
            synchronized (object) {
                this.q.changed();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void allChanged() {
            Object object = this.sync;
            synchronized (object) {
                this.q.allChanged();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void changed(int n) {
            Object object = this.sync;
            synchronized (object) {
                this.q.changed(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(int n) {
            Object object = this.sync;
            synchronized (object) {
                return this.q.remove(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Comparator<? super K> comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.comparator();
            }
        }

        @Override
        public int front(int[] nArray) {
            return this.q.front(nArray);
        }
    }

    public static class EmptyIndirectPriorityQueue
    implements IndirectPriorityQueue {
        protected EmptyIndirectPriorityQueue() {
        }

        @Override
        public void enqueue(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int dequeue() {
            throw new NoSuchElementException();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean contains(int n) {
            return true;
        }

        @Override
        public void clear() {
        }

        @Override
        public int first() {
            throw new NoSuchElementException();
        }

        @Override
        public int last() {
            throw new NoSuchElementException();
        }

        @Override
        public void changed() {
            throw new NoSuchElementException();
        }

        @Override
        public void allChanged() {
        }

        public Comparator<?> comparator() {
            return null;
        }

        @Override
        public void changed(int n) {
            throw new IllegalArgumentException("Index " + n + " is not in the queue");
        }

        @Override
        public boolean remove(int n) {
            return true;
        }

        @Override
        public int front(int[] nArray) {
            return 1;
        }
    }
}

