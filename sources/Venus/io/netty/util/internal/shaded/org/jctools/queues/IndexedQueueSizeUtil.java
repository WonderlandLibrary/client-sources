/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

public final class IndexedQueueSizeUtil {
    public static int size(IndexedQueue indexedQueue) {
        long l;
        long l2;
        long l3 = indexedQueue.lvConsumerIndex();
        do {
            l2 = l3;
            l = indexedQueue.lvProducerIndex();
        } while (l2 != (l3 = indexedQueue.lvConsumerIndex()));
        long l4 = l - l3;
        if (l4 > Integer.MAX_VALUE) {
            return 0;
        }
        return (int)l4;
    }

    public static boolean isEmpty(IndexedQueue indexedQueue) {
        return indexedQueue.lvConsumerIndex() == indexedQueue.lvProducerIndex();
    }

    public static interface IndexedQueue {
        public long lvConsumerIndex();

        public long lvProducerIndex();
    }
}

