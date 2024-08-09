/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

public final class CheckIntrinsics {
    private CheckIntrinsics() {
    }

    public static int checkIndex(int n, int n2) {
        if (n < 0 || n2 <= n) {
            throw new IndexOutOfBoundsException();
        }
        return n;
    }

    public static int checkFromToIndex(int n, int n2, int n3) {
        if (n < 0 || n2 < n || n3 < n2) {
            throw new IndexOutOfBoundsException();
        }
        return n;
    }

    public static int checkFromIndexSize(int n, int n2, int n3) {
        if ((n3 | n | n2) < 0 || n3 - n < n2) {
            throw new IndexOutOfBoundsException();
        }
        return n;
    }
}

