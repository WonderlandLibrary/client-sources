/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.ints.IntArrays;

public final class ByteSemiIndirectHeaps {
    static final boolean $assertionsDisabled = !ByteSemiIndirectHeaps.class.desiredAssertionStatus();

    private ByteSemiIndirectHeaps() {
    }

    public static int downHeap(byte[] byArray, int[] nArray, int n, int n2, ByteComparator byteComparator) {
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
                    n2 = n7;
                    continue;
                }
                break;
            }
        }
        nArray[n2] = n3;
        return n2;
    }

    public static int upHeap(byte[] byArray, int[] nArray, int n, int n2, ByteComparator byteComparator) {
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
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && byteComparator.compare(byArray[n7 = nArray[n6 = n2 - 1 >>> 1]], by) > 0) {
                nArray[n2] = n7;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        return n2;
    }

    public static void makeHeap(byte[] byArray, int n, int n2, int[] nArray, ByteComparator byteComparator) {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        if (nArray.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        int n3 = n2;
        while (n3-- != 0) {
            nArray[n3] = n + n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            ByteSemiIndirectHeaps.downHeap(byArray, nArray, n2, n3, byteComparator);
        }
    }

    public static int[] makeHeap(byte[] byArray, int n, int n2, ByteComparator byteComparator) {
        int[] nArray = n2 <= 0 ? IntArrays.EMPTY_ARRAY : new int[n2];
        ByteSemiIndirectHeaps.makeHeap(byArray, n, n2, nArray, byteComparator);
        return nArray;
    }

    public static void makeHeap(byte[] byArray, int[] nArray, int n, ByteComparator byteComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            ByteSemiIndirectHeaps.downHeap(byArray, nArray, n, n2, byteComparator);
        }
    }

    public static int front(byte[] byArray, int[] nArray, int n, int[] nArray2) {
        byte by = byArray[nArray[0]];
        int n2 = 0;
        int n3 = 0;
        int n4 = 1;
        int n5 = 0;
        for (int i = 0; i < n4; ++i) {
            if (i == n5) {
                if (n3 >= n4) break;
                n5 = (n5 << 1) + 1;
                i = n3;
                n3 = -1;
            }
            if (by != byArray[nArray[i]]) continue;
            nArray2[n2++] = nArray[i];
            if (n3 == -1) {
                n3 = i * 2 + 1;
            }
            n4 = Math.min(n, i * 2 + 3);
        }
        return n2;
    }

    public static int front(byte[] byArray, int[] nArray, int n, int[] nArray2, ByteComparator byteComparator) {
        byte by = byArray[nArray[0]];
        int n2 = 0;
        int n3 = 0;
        int n4 = 1;
        int n5 = 0;
        for (int i = 0; i < n4; ++i) {
            if (i == n5) {
                if (n3 >= n4) break;
                n5 = (n5 << 1) + 1;
                i = n3;
                n3 = -1;
            }
            if (byteComparator.compare(by, byArray[nArray[i]]) != 0) continue;
            nArray2[n2++] = nArray[i];
            if (n3 == -1) {
                n3 = i * 2 + 1;
            }
            n4 = Math.min(n, i * 2 + 3);
        }
        return n2;
    }
}

