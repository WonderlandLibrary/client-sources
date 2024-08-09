/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.ints.IntArrays;

public final class CharSemiIndirectHeaps {
    static final boolean $assertionsDisabled = !CharSemiIndirectHeaps.class.desiredAssertionStatus();

    private CharSemiIndirectHeaps() {
    }

    public static int downHeap(char[] cArray, int[] nArray, int n, int n2, CharComparator charComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        char c = cArray[n3];
        if (charComparator == null) {
            int n4;
            while ((n4 = (n2 << 1) + 1) < n) {
                int n5 = nArray[n4];
                int n6 = n4 + 1;
                if (n6 < n && cArray[nArray[n6]] < cArray[n5]) {
                    n4 = n6;
                    n5 = nArray[n4];
                }
                if (c > cArray[n5]) {
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
                if (n9 < n && charComparator.compare(cArray[nArray[n9]], cArray[n8]) < 0) {
                    n7 = n9;
                    n8 = nArray[n7];
                }
                if (charComparator.compare(c, cArray[n8]) > 0) {
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

    public static int upHeap(char[] cArray, int[] nArray, int n, int n2, CharComparator charComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        char c = cArray[n3];
        if (charComparator == null) {
            int n4;
            int n5;
            while (n2 != 0 && cArray[n5 = nArray[n4 = n2 - 1 >>> 1]] > c) {
                nArray[n2] = n5;
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && charComparator.compare(cArray[n7 = nArray[n6 = n2 - 1 >>> 1]], c) > 0) {
                nArray[n2] = n7;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        return n2;
    }

    public static void makeHeap(char[] cArray, int n, int n2, int[] nArray, CharComparator charComparator) {
        CharArrays.ensureOffsetLength(cArray, n, n2);
        if (nArray.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        int n3 = n2;
        while (n3-- != 0) {
            nArray[n3] = n + n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            CharSemiIndirectHeaps.downHeap(cArray, nArray, n2, n3, charComparator);
        }
    }

    public static int[] makeHeap(char[] cArray, int n, int n2, CharComparator charComparator) {
        int[] nArray = n2 <= 0 ? IntArrays.EMPTY_ARRAY : new int[n2];
        CharSemiIndirectHeaps.makeHeap(cArray, n, n2, nArray, charComparator);
        return nArray;
    }

    public static void makeHeap(char[] cArray, int[] nArray, int n, CharComparator charComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            CharSemiIndirectHeaps.downHeap(cArray, nArray, n, n2, charComparator);
        }
    }

    public static int front(char[] cArray, int[] nArray, int n, int[] nArray2) {
        char c = cArray[nArray[0]];
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
            if (c != cArray[nArray[i]]) continue;
            nArray2[n2++] = nArray[i];
            if (n3 == -1) {
                n3 = i * 2 + 1;
            }
            n4 = Math.min(n, i * 2 + 3);
        }
        return n2;
    }

    public static int front(char[] cArray, int[] nArray, int n, int[] nArray2, CharComparator charComparator) {
        char c = cArray[nArray[0]];
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
            if (charComparator.compare(c, cArray[nArray[i]]) != 0) continue;
            nArray2[n2++] = nArray[i];
            if (n3 == -1) {
                n3 = i * 2 + 1;
            }
            n4 = Math.min(n, i * 2 + 3);
        }
        return n2;
    }
}

