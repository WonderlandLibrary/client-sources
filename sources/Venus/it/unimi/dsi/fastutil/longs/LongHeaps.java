/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongComparator;

public final class LongHeaps {
    static final boolean $assertionsDisabled = !LongHeaps.class.desiredAssertionStatus();

    private LongHeaps() {
    }

    public static int downHeap(long[] lArray, int n, int n2, LongComparator longComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        long l = lArray[n2];
        if (longComparator == null) {
            int n3;
            while ((n3 = (n2 << 1) + 1) < n) {
                long l2 = lArray[n3];
                int n4 = n3 + 1;
                if (n4 < n && lArray[n4] < l2) {
                    n3 = n4;
                    l2 = lArray[n3];
                }
                if (l > l2) {
                    lArray[n2] = l2;
                    n2 = n3;
                    continue;
                }
                break;
            }
        } else {
            int n5;
            while ((n5 = (n2 << 1) + 1) < n) {
                long l3 = lArray[n5];
                int n6 = n5 + 1;
                if (n6 < n && longComparator.compare(lArray[n6], l3) < 0) {
                    n5 = n6;
                    l3 = lArray[n5];
                }
                if (longComparator.compare(l, l3) > 0) {
                    lArray[n2] = l3;
                    n2 = n5;
                    continue;
                }
                break;
            }
        }
        lArray[n2] = l;
        return n2;
    }

    public static int upHeap(long[] lArray, int n, int n2, LongComparator longComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        long l = lArray[n2];
        if (longComparator == null) {
            int n3;
            long l2;
            while (n2 != 0 && (l2 = lArray[n3 = n2 - 1 >>> 1]) > l) {
                lArray[n2] = l2;
                n2 = n3;
            }
        } else {
            int n4;
            long l3;
            while (n2 != 0 && longComparator.compare(l3 = lArray[n4 = n2 - 1 >>> 1], l) > 0) {
                lArray[n2] = l3;
                n2 = n4;
            }
        }
        lArray[n2] = l;
        return n2;
    }

    public static void makeHeap(long[] lArray, int n, LongComparator longComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            LongHeaps.downHeap(lArray, n, n2, longComparator);
        }
    }
}

