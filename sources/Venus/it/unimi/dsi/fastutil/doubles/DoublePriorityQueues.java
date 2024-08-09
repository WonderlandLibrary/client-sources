/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoublePriorityQueue;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Comparator;

public final class DoublePriorityQueues {
    private DoublePriorityQueues() {
    }

    public static DoublePriorityQueue synchronize(DoublePriorityQueue doublePriorityQueue) {
        return new SynchronizedPriorityQueue(doublePriorityQueue);
    }

    public static DoublePriorityQueue synchronize(DoublePriorityQueue doublePriorityQueue, Object object) {
        return new SynchronizedPriorityQueue(doublePriorityQueue, object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedPriorityQueue
    implements DoublePriorityQueue {
        protected final DoublePriorityQueue q;
        protected final Object sync;

        protected SynchronizedPriorityQueue(DoublePriorityQueue doublePriorityQueue, Object object) {
            this.q = doublePriorityQueue;
            this.sync = object;
        }

        protected SynchronizedPriorityQueue(DoublePriorityQueue doublePriorityQueue) {
            this.q = doublePriorityQueue;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void enqueue(double d) {
            Object object = this.sync;
            synchronized (object) {
                this.q.enqueue(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double dequeueDouble() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.dequeueDouble();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double firstDouble() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.firstDouble();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public double lastDouble() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.lastDouble();
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
        public DoubleComparator comparator() {
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
        public void enqueue(Double d) {
            Object object = this.sync;
            synchronized (object) {
                this.q.enqueue(d);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Double dequeue() {
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
        public Double first() {
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
        public Double last() {
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
            this.enqueue((Double)object);
        }
    }
}

