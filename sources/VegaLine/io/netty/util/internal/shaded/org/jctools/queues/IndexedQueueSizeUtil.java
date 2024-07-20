/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

final class IndexedQueueSizeUtil {
    private IndexedQueueSizeUtil() {
    }

    static int size(IndexedQueue iq) {
        long currentProducerIndex;
        long before;
        long after = iq.lvConsumerIndex();
        do {
            before = after;
            currentProducerIndex = iq.lvProducerIndex();
        } while (before != (after = iq.lvConsumerIndex()));
        long size = currentProducerIndex - after;
        if (size > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int)size;
    }

    static boolean isEmpty(IndexedQueue iq) {
        return iq.lvConsumerIndex() == iq.lvProducerIndex();
    }

    protected static interface IndexedQueue {
        public long lvConsumerIndex();

        public long lvProducerIndex();
    }
}

