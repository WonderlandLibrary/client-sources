/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntComparator;
import java.util.Arrays;

public final class IntIndirectHeaps {
    static final boolean $assertionsDisabled = !IntIndirectHeaps.class.desiredAssertionStatus();

    private IntIndirectHeaps() {
    }

    public static int downHeap(int[] nArray, int[] nArray2, int[] nArray3, int n, int n2, IntComparator intComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray2[n2];
        int n4 = nArray[n3];
        if (intComparator == null) {
            int n5;
            while ((n5 = (n2 << 1) + 1) < n) {
                int n6 = nArray2[n5];
                int n7 = n5 + 1;
                if (n7 < n && nArray[nArray2[n7]] < nArray[n6]) {
                    n5 = n7;
                    n6 = nArray2[n5];
                }
                if (n4 > nArray[n6]) {
                    nArray2[n2] = n6;
                    nArray3[nArray2[n2]] = n2;
                    n2 = n5;
                    continue;
                }
                break;
            }
        } else {
            int n8;
            while ((n8 = (n2 << 1) + 1) < n) {
                int n9 = nArray2[n8];
                int n10 = n8 + 1;
                if (n10 < n && intComparator.compare(nArray[nArray2[n10]], nArray[n9]) < 0) {
                    n8 = n10;
                    n9 = nArray2[n8];
                }
                if (intComparator.compare(n4, nArray[n9]) > 0) {
                    nArray2[n2] = n9;
                    nArray3[nArray2[n2]] = n2;
                    n2 = n8;
                    continue;
                }
                break;
            }
        }
        nArray2[n2] = n3;
        nArray3[n3] = n2;
        return n2;
    }

    public static int upHeap(int[] nArray, int[] nArray2, int[] nArray3, int n, int n2, IntComparator intComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray2[n2];
        int n4 = nArray[n3];
        if (intComparator == null) {
            int n5;
            int n6;
            while (n2 != 0 && nArray[n6 = nArray2[n5 = n2 - 1 >>> 1]] > n4) {
                nArray2[n2] = n6;
                nArray3[nArray2[n2]] = n2;
                n2 = n5;
            }
        } else {
            int n7;
            int n8;
            while (n2 != 0 && intComparator.compare(nArray[n8 = nArray2[n7 = n2 - 1 >>> 1]], n4) > 0) {
                nArray2[n2] = n8;
                nArray3[nArray2[n2]] = n2;
                n2 = n7;
            }
        }
        nArray2[n2] = n3;
        nArray3[n3] = n2;
        return n2;
    }

    public static void makeHeap(int[] nArray, int n, int n2, int[] nArray2, int[] nArray3, IntComparator intComparator) {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        if (nArray2.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray2.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        if (nArray3.length < nArray.length) {
            throw new IllegalArgumentException("The inversion array length (" + nArray2.length + ") is smaller than the length of the reference array (" + nArray.length + ")");
        }
        Arrays.fill(nArray3, 0, nArray.length, -1);
        int n3 = n2;
        while (n3-- != 0) {
            nArray2[n3] = n + n3;
            nArray3[nArray2[n3]] = n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            IntIndirectHeaps.downHeap(nArray, nArray2, nArray3, n2, n3, intComparator);
        }
    }

    public static void makeHeap(int[] nArray, int[] nArray2, int[] nArray3, int n, IntComparator intComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            IntIndirectHeaps.downHeap(nArray, nArray2, nArray3, n, n2, intComparator);
        }
    }
}

