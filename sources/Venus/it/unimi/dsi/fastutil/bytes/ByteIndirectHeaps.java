/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import java.util.Arrays;

public final class ByteIndirectHeaps {
    static final boolean $assertionsDisabled = !ByteIndirectHeaps.class.desiredAssertionStatus();

    private ByteIndirectHeaps() {
    }

    public static int downHeap(byte[] byArray, int[] nArray, int[] nArray2, int n, int n2, ByteComparator byteComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        byte by = byArray[n3];
        if (byteComparator == null) {
            int n4;
            while ((n4 = (n2 << 1) + 1) < n) {
                int n5 = nArray[n4];
                int n6 = n4 + 1;
                if (n6 < n && byArray[nArray[n6]] < byArray[n5]) {
                    n4 = n6;
                    n5 = nArray[n4];
                }
                if (by > byArray[n5]) {
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
                if (n9 < n && byteComparator.compare(byArray[nArray[n9]], byArray[n8]) < 0) {
                    n7 = n9;
                    n8 = nArray[n7];
                }
                if (byteComparator.compare(by, byArray[n8]) > 0) {
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

    public static int upHeap(byte[] byArray, int[] nArray, int[] nArray2, int n, int n2, ByteComparator byteComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        byte by = byArray[n3];
        if (byteComparator == null) {
            int n4;
            int n5;
            while (n2 != 0 && byArray[n5 = nArray[n4 = n2 - 1 >>> 1]] > by) {
                nArray[n2] = n5;
                nArray2[nArray[n2]] = n2;
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && byteComparator.compare(byArray[n7 = nArray[n6 = n2 - 1 >>> 1]], by) > 0) {
                nArray[n2] = n7;
                nArray2[nArray[n2]] = n2;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        nArray2[n3] = n2;
        return n2;
    }

    public static void makeHeap(byte[] byArray, int n, int n2, int[] nArray, int[] nArray2, ByteComparator byteComparator) {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        if (nArray.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        if (nArray2.length < byArray.length) {
            throw new IllegalArgumentException("The inversion array length (" + nArray.length + ") is smaller than the length of the reference array (" + byArray.length + ")");
        }
        Arrays.fill(nArray2, 0, byArray.length, -1);
        int n3 = n2;
        while (n3-- != 0) {
            nArray[n3] = n + n3;
            nArray2[nArray[n3]] = n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            ByteIndirectHeaps.downHeap(byArray, nArray, nArray2, n2, n3, byteComparator);
        }
    }

    public static void makeHeap(byte[] byArray, int[] nArray, int[] nArray2, int n, ByteComparator byteComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            ByteIndirectHeaps.downHeap(byArray, nArray, nArray2, n, n2, byteComparator);
        }
    }
}

