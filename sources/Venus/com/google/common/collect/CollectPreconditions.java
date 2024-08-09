/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
final class CollectPreconditions {
    CollectPreconditions() {
    }

    static void checkEntryNotNull(Object object, Object object2) {
        if (object == null) {
            throw new NullPointerException("null key in entry: null=" + object2);
        }
        if (object2 == null) {
            throw new NullPointerException("null value in entry: " + object + "=null");
        }
    }

    @CanIgnoreReturnValue
    static int checkNonnegative(int n, String string) {
        if (n < 0) {
            throw new IllegalArgumentException(string + " cannot be negative but was: " + n);
        }
        return n;
    }

    @CanIgnoreReturnValue
    static long checkNonnegative(long l, String string) {
        if (l < 0L) {
            throw new IllegalArgumentException(string + " cannot be negative but was: " + l);
        }
        return l;
    }

    static void checkPositive(int n, String string) {
        if (n <= 0) {
            throw new IllegalArgumentException(string + " must be positive but was: " + n);
        }
    }

    static void checkRemove(boolean bl) {
        Preconditions.checkState(bl, "no calls to next() since the last call to remove()");
    }
}

