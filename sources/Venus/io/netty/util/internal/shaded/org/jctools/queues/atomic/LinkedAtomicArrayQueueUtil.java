/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues.atomic;

import io.netty.util.internal.shaded.org.jctools.queues.atomic.AtomicReferenceArrayQueue;
import java.util.concurrent.atomic.AtomicReferenceArray;

final class LinkedAtomicArrayQueueUtil {
    private LinkedAtomicArrayQueueUtil() {
    }

    public static <E> E lvElement(AtomicReferenceArray<E> atomicReferenceArray, int n) {
        return AtomicReferenceArrayQueue.lvElement(atomicReferenceArray, n);
    }

    public static <E> E lpElement(AtomicReferenceArray<E> atomicReferenceArray, int n) {
        return AtomicReferenceArrayQueue.lpElement(atomicReferenceArray, n);
    }

    public static <E> void spElement(AtomicReferenceArray<E> atomicReferenceArray, int n, E e) {
        AtomicReferenceArrayQueue.spElement(atomicReferenceArray, n, e);
    }

    public static <E> void svElement(AtomicReferenceArray<E> atomicReferenceArray, int n, E e) {
        AtomicReferenceArrayQueue.svElement(atomicReferenceArray, n, e);
    }

    static <E> void soElement(AtomicReferenceArray atomicReferenceArray, int n, Object object) {
        atomicReferenceArray.lazySet(n, object);
    }

    static int calcElementOffset(long l, long l2) {
        return (int)(l & l2);
    }

    static <E> AtomicReferenceArray<E> allocate(int n) {
        return new AtomicReferenceArray(n);
    }

    static int length(AtomicReferenceArray<?> atomicReferenceArray) {
        return atomicReferenceArray.length();
    }

    static int modifiedCalcElementOffset(long l, long l2) {
        return (int)(l & l2) >> 1;
    }

    static int nextArrayOffset(AtomicReferenceArray<?> atomicReferenceArray) {
        return LinkedAtomicArrayQueueUtil.length(atomicReferenceArray) - 1;
    }
}

