/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Comparator;

public final class IntPriorityQueues {
    private IntPriorityQueues() {
    }

    public static IntPriorityQueue synchronize(IntPriorityQueue intPriorityQueue) {
        return new SynchronizedPriorityQueue(intPriorityQueue);
    }

    public static IntPriorityQueue synchronize(IntPriorityQueue intPriorityQueue, Object object) {
        return new SynchronizedPriorityQueue(intPriorityQueue, object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedPriorityQueue
    implements IntPriorityQueue {
        protected final IntPriorityQueue q;
        protected final Object sync;

        protected SynchronizedPriorityQueue(IntPriorityQueue intPriorityQueue, Object object) {
            this.q = intPriorityQueue;
            this.sync = object;
        }

        protected SynchronizedPriorityQueue(IntPriorityQueue intPriorityQueue) {
            this.q = intPriorityQueue;
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
        public int dequeueInt() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.dequeueInt();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int firstInt() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.firstInt();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastInt() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.lastInt();
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
        public IntComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.comparator();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public void enqueue(Integer n) {
            Object object = this.sync;
            synchronized (object) {
                this.q.enqueue(n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer dequeue() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.dequeue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer first() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.first();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Integer last() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.last();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            Object object2 = this.sync;
            synchronized (object2) {
                return this.q.equals(object);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.sync;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
            }
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }

        @Override
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public Object dequeue() {
            return this.dequeue();
        }

        @Override
        @Deprecated
        public void enqueue(Object object) {
            this.enqueue((Integer)object);
        }
    }
}

