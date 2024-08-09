/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

public final class CircularArrayOffsetCalculator {
    public static <E> E[] allocate(int n) {
        return new Object[n];
    }

    public static long calcElementOffset(long l, long l2) {
        return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((l & l2) << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT);
    }
}

