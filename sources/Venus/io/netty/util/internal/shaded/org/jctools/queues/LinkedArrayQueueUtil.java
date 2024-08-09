/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

final class LinkedArrayQueueUtil {
    private LinkedArrayQueueUtil() {
    }

    static int length(Object[] objectArray) {
        return objectArray.length;
    }

    static long modifiedCalcElementOffset(long l, long l2) {
        return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((l & l2) << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT - 1);
    }

    static long nextArrayOffset(Object[] objectArray) {
        return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((long)(LinkedArrayQueueUtil.length(objectArray) - 1) << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT);
    }
}

