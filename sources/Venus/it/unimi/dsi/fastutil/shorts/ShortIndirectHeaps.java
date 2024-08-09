/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import java.util.Arrays;

public final class ShortIndirectHeaps {
    static final boolean $assertionsDisabled = !ShortIndirectHeaps.class.desiredAssertionStatus();

    private ShortIndirectHeaps() {
    }

    public static int downHeap(short[] sArray, int[] nArray, int[] nArray2, int n, int n2, ShortComparator shortComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        short s = sArray[n3];
        if (shortComparator == null) {
            int n4;
            while ((n4 = (n2 << 1) + 1) < n) {
                int n5 = nArray[n4];
                int n6 = n4 + 1;
                if (n6 < n && sArray[nArray[n6]] < sArray[n5]) {
                    n4 = n6;
                    n5 = nArray[n4];
                }
                if (s > sArray[n5]) {
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
                if (n9 < n && shortComparator.compare(sArray[nArray[n9]], sArray[n8]) < 0) {
                    n7 = n9;
                    n8 = nArray[n7];
                }
                if (shortComparator.compare(s, sArray[n8]) > 0) {
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

    public static int upHeap(short[] sArray, int[] nArray, int[] nArray2, int n, int n2, ShortComparator shortComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        short s = sArray[n3];
        if (shortComparator == null) {
            int n4;
            int n5;
            while (n2 != 0 && sArray[n5 = nArray[n4 = n2 - 1 >>> 1]] > s) {
                nArray[n2] = n5;
                nArray2[nArray[n2]] = n2;
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && shortComparator.compare(sArray[n7 = nArray[n6 = n2 - 1 >>> 1]], s) > 0) {
                nArray[n2] = n7;
                nArray2[nArray[n2]] = n2;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        nArray2[n3] = n2;
        return n2;
    }

    public static void makeHeap(short[] sArray, int n, int n2, int[] nArray, int[] nArray2, ShortComparator shortComparator) {
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        if (nArray.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        if (nArray2.length < sArray.length) {
            throw new IllegalArgumentException("The inversion array length (" + nArray.length + ") is smaller than the length of the reference array (" + sArray.length + ")");
        }
        Arrays.fill(nArray2, 0, sArray.length, -1);
        int n3 = n2;
        while (n3-- != 0) {
            nArray[n3] = n + n3;
            nArray2[nArray[n3]] = n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            ShortIndirectHeaps.downHeap(sArray, nArray, nArray2, n2, n3, shortComparator);
        }
    }

    public static void makeHeap(short[] sArray, int[] nArray, int[] nArray2, int n, ShortComparator shortComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            ShortIndirectHeaps.downHeap(sArray, nArray, nArray2, n, n2, shortComparator);
        }
    }
}

