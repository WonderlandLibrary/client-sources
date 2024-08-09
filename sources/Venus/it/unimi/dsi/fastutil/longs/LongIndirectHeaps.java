/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongComparator;
import java.util.Arrays;

public final class LongIndirectHeaps {
    static final boolean $assertionsDisabled = !LongIndirectHeaps.class.desiredAssertionStatus();

    private LongIndirectHeaps() {
    }

    public static int downHeap(long[] lArray, int[] nArray, int[] nArray2, int n, int n2, LongComparator longComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        long l = lArray[n3];
        if (longComparator == null) {
            int n4;
            while ((n4 = (n2 << 1) + 1) < n) {
                int n5 = nArray[n4];
                int n6 = n4 + 1;
                if (n6 < n && lArray[nArray[n6]] < lArray[n5]) {
                    n4 = n6;
                    n5 = nArray[n4];
                }
                if (l > lArray[n5]) {
                    nArray[n2] = n5;
                    nArray2[nArray[n2]] = n2;
                    n2 = n4;
                    continue;
                }
                break;
            }
        } else {
            int n7;
            while ((n7 = (n2 << 1) + 1) < n) {
                int n8 = nArray[n7];
                int n9 = n7 + 1;
                if (n9 < n && longComparator.compare(lArray[nArray[n9]], lArray[n8]) < 0) {
                    n7 = n9;
                    n8 = nArray[n7];
                }
                if (longComparator.compare(l, lArray[n8]) > 0) {
                    nArray[n2] = n8;
                    nArray2[nArray[n2]] = n2;
                    n2 = n7;
                    continue;
                }
                break;
            }
        }
        nArray[n2] = n3;
        nArray2[n3] = n2;
        return n2;
    }

    public static int upHeap(long[] lArray, int[] nArray, int[] nArray2, int n, int n2, LongComparator longComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        long l = lArray[n3];
        if (longComparator == null) {
            int n4;
            int n5;
            while (n2 != 0 && lArray[n5 = nArray[n4 = n2 - 1 >>> 1]] > l) {
                nArray[n2] = n5;
                nArray2[nArray[n2]] = n2;
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && longComparator.compare(lArray[n7 = nArray[n6 = n2 - 1 >>> 1]], l) > 0) {
                nArray[n2] = n7;
                nArray2[nArray[n2]] = n2;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        nArray2[n3] = n2;
        return n2;
    }

    public static void makeHeap(long[] lArray, int n, int n2, int[] nArray, int[] nArray2, LongComparator longComparator) {
        LongArrays.ensureOffsetLength(lArray, n, n2);
        if (nArray.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        if (nArray2.length < lArray.length) {
            throw new IllegalArgumentException("The inversion array length (" + nArray.length + ") is smaller than the length of the reference array (" + lArray.length + ")");
        }
        Arrays.fill(nArray2, 0, lArray.length, -1);
        int n3 = n2;
        while (n3-- != 0) {
            nArray[n3] = n + n3;
            nArray2[nArray[n3]] = n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            LongIndirectHeaps.downHeap(lArray, nArray, nArray2, n2, n3, longComparator);
        }
    }

    public static void makeHeap(long[] lArray, int[] nArray, int[] nArray2, int n, LongComparator longComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            LongIndirectHeaps.downHeap(lArray, nArray, nArray2, n, n2, longComparator);
        }
    }
}

