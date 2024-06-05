/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Iterator;

public final class ObjectIterables {
    private ObjectIterables() {
    }

    public static <K> long size(Iterable<K> iterable) {
        long c = 0L;
        Iterator<K> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            K dummy = iterator.next();
            ++c;
        }
        return c;
    }
}

