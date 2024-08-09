/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortComparator;

public final class ShortSemiIndirectHeaps {
    static final boolean $assertionsDisabled = !ShortSemiIndirectHeaps.class.desiredAssertionStatus();

    private ShortSemiIndirectHeaps() {
    }

    public static int downHeap(short[] sArray, int[] nArray, int n, int n2, ShortComparator shortComparator) {
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
                    n2 = n7;
                    continue;
                }
                break;
            }
        }
        nArray[n2] = n3;
        return n2;
    }

    public static int upHeap(short[] sArray, int[] nArray, int n, int n2, ShortComparator shortComparator) {
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
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && shortComparator.compare(sArray[n7 = nArray[n6 = n2 - 1 >>> 1]], s) > 0) {
                nArray[n2] = n7;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        return n2;
    }

    public static void makeHeap(short[] sArray, int n, int n2, int[] nArray, ShortComparator shortComparator) {
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        if (nArray.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        int n3 = n2;
        while (n3-- != 0) {
            nArray[n3] = n + n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            ShortSemiIndirectHeaps.downHeap(sArray, nArray, n2, n3, shortComparator);
        }
    }

    public static int[] makeHeap(short[] sArray, int n, int n2, ShortComparator shortComparator) {
        int[] nArray = n2 <= 0 ? IntArrays.EMPTY_ARRAY : new int[n2];
        ShortSemiIndirectHeaps.makeHeap(sArray, n, n2, nArray, shortComparator);
        return nArray;
    }

    public static void makeHeap(short[] sArray, int[] nArray, int n, ShortComparator shortComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            ShortSemiIndirectHeaps.downHeap(sArray, nArray, n, n2, shortComparator);
        }
    }

    public static int front(short[] sArray, int[] nArray, int n, int[] nArray2) {
        short s = sArray[nArray[0]];
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
            if (s != sArray[nArray[i]]) continue;
            nArray2[n2++] = nArray[i];
            if (n3 == -1) {
                n3 = i * 2 + 1;
            }
            n4 = Math.min(n, i * 2 + 3);
        }
        return n2;
    }

    public static int front(short[] sArray, int[] nArray, int n, int[] nArray2, ShortComparator shortComparator) {
        short s = sArray[nArray[0]];
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
            if (shortComparator.compare(s, sArray[nArray[i]]) != 0) continue;
            nArray2[n2++] = nArray[i];
            if (n3 == -1) {
                n3 = i * 2 + 1;
            }
            n4 = Math.min(n, i * 2 + 3);
        }
        return n2;
    }
}

