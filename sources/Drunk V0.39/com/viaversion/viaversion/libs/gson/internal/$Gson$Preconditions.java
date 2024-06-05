/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.internal;

public final class $Gson$Preconditions {
    private $Gson$Preconditions() {
        throw new UnsupportedOperationException();
    }

    public static <T> T checkNotNull(T obj) {
        if (obj != null) return obj;
        throw new NullPointerException();
    }

    public static void checkArgument(boolean condition) {
        if (condition) return;
        throw new IllegalArgumentException();
    }
}

