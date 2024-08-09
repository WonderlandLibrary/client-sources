/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

final class NativeLibraryUtil {
    public static void loadLibrary(String string, boolean bl) {
        if (bl) {
            System.load(string);
        } else {
            System.loadLibrary(string);
        }
    }

    private NativeLibraryUtil() {
    }
}

