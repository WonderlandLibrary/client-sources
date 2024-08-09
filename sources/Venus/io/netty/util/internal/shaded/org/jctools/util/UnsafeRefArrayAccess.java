/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.util;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

public final class UnsafeRefArrayAccess {
    public static final long REF_ARRAY_BASE;
    public static final int REF_ELEMENT_SHIFT;

    public static <E> void spElement(E[] EArray, long l, E e) {
        UnsafeAccess.UNSAFE.putObject(EArray, l, e);
    }

    public static <E> void soElement(E[] EArray, long l, E e) {
        UnsafeAccess.UNSAFE.putOrderedObject(EArray, l, e);
    }

    public static <E> E lpElement(E[] EArray, long l) {
        return (E)UnsafeAccess.UNSAFE.getObject(EArray, l);
    }

    public static <E> E lvElement(E[] EArray, long l) {
        return (E)UnsafeAccess.UNSAFE.getObjectVolatile(EArray, l);
    }

    public static long calcElementOffset(long l) {
        return REF_ARRAY_BASE + (l << REF_ELEMENT_SHIFT);
    }

    static {
        int n = UnsafeAccess.UNSAFE.arrayIndexScale(Object[].class);
        if (4 == n) {
            REF_ELEMENT_SHIFT = 2;
        } else if (8 == n) {
            REF_ELEMENT_SHIFT = 3;
        } else {
            throw new IllegalStateException("Unknown pointer size");
        }
        REF_ARRAY_BASE = UnsafeAccess.UNSAFE.arrayBaseOffset(Object[].class);
    }
}

