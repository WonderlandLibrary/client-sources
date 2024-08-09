/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteComparator;

public final class ByteHeaps {
    static final boolean $assertionsDisabled = !ByteHeaps.class.desiredAssertionStatus();

    private ByteHeaps() {
    }

    public static int downHeap(byte[] byArray, int n, int n2, ByteComparator byteComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        byte by = byArray[n2];
        if (byteComparator == null) {
            int n3;
            while ((n3 = (n2 << 1) + 1) < n) {
                byte by2 = byArray[n3];
                int n4 = n3 + 1;
                if (n4 < n && byArray[n4] < by2) {
                    n3 = n4;
                    by2 = byArray[n3];
                }
                if (by > by2) {
                    byArray[n2] = by2;
                    n2 = n3;
                    continue;
                }
                break;
            }
        } else {
            int n5;
            while ((n5 = (n2 << 1) + 1) < n) {
                byte by3 = byArray[n5];
                int n6 = n5 + 1;
                if (n6 < n && byteComparator.compare(byArray[n6], by3) < 0) {
                    n5 = n6;
                    by3 = byArray[n5];
                }
                if (byteComparator.compare(by, by3) > 0) {
                    byArray[n2] = by3;
                    n2 = n5;
                    continue;
                }
                break;
            }
        }
        byArray[n2] = by;
        return n2;
    }

    public static int upHeap(byte[] byArray, int n, int n2, ByteComparator byteComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        byte by = byArray[n2];
        if (byteComparator == null) {
            int n3;
            byte by2;
            while (n2 != 0 && (by2 = byArray[n3 = n2 - 1 >>> 1]) > by) {
                byArray[n2] = by2;
                n2 = n3;
            }
        } else {
            int n4;
            byte by3;
            while (n2 != 0 && byteComparator.compare(by3 = byArray[n4 = n2 - 1 >>> 1], by) > 0) {
                byArray[n2] = by3;
                n2 = n4;
            }
        }
        byArray[n2] = by;
        return n2;
    }

    public static void makeHeap(byte[] byArray, int n, ByteComparator byteComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            ByteHeaps.downHeap(byArray, n, n2, byteComparator);
        }
    }
}

