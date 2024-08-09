/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortPriorityQueue;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Comparator;

public final class ShortPriorityQueues {
    private ShortPriorityQueues() {
    }

    public static ShortPriorityQueue synchronize(ShortPriorityQueue shortPriorityQueue) {
        return new SynchronizedPriorityQueue(shortPriorityQueue);
    }

    public static ShortPriorityQueue synchronize(ShortPriorityQueue shortPriorityQueue, Object object) {
        return new SynchronizedPriorityQueue(shortPriorityQueue, object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedPriorityQueue
    implements ShortPriorityQueue {
        protected final ShortPriorityQueue q;
        protected final Object sync;

        protected SynchronizedPriorityQueue(ShortPriorityQueue shortPriorityQueue, Object object) {
            this.q = shortPriorityQueue;
            this.sync = object;
        }

        protected SynchronizedPriorityQueue(ShortPriorityQueue shortPriorityQueue) {
            this.q = shortPriorityQueue;
            this.sync = this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void enqueue(short s) {
            Object object = this.sync;
            synchronized (object) {
                this.q.enqueue(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short dequeueShort() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.dequeueShort();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short firstShort() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.firstShort();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public short lastShort() {
            Object object = this.sync;
            synchronized (object) {
                return this.q.lastShort();
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
        public ShortComparator comparator() {
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
        public void enqueue(Short s) {
            Object object = this.sync;
            synchronized (object) {
                this.q.enqueue(s);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Short dequeue() {
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
        public Short first() {
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
        public Short last() {
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
            this.enqueue((Short)object);
        }
    }
}

