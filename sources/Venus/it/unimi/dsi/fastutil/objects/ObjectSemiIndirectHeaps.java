/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.Comparator;

public final class ObjectSemiIndirectHeaps {
    static final boolean $assertionsDisabled = !ObjectSemiIndirectHeaps.class.desiredAssertionStatus();

    private ObjectSemiIndirectHeaps() {
    }

    public static <K> int downHeap(K[] KArray, int[] nArray, int n, int n2, Comparator<K> comparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        K k = KArray[n3];
        if (comparator == null) {
            int n4;
            while ((n4 = (n2 << 1) + 1) < n) {
                int n5 = nArray[n4];
                int n6 = n4 + 1;
                if (n6 < n && ((Comparable)KArray[nArray[n6]]).compareTo(KArray[n5]) < 0) {
                    n4 = n6;
                    n5 = nArray[n4];
                }
                if (((Comparable)k).compareTo(KArray[n5]) > 0) {
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
                if (n9 < n && comparator.compare(KArray[nArray[n9]], KArray[n8]) < 0) {
                    n7 = n9;
                    n8 = nArray[n7];
                }
                if (comparator.compare(k, KArray[n8]) > 0) {
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

    public static <K> int upHeap(K[] KArray, int[] nArray, int n, int n2, Comparator<K> comparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        K k = KArray[n3];
        if (comparator == null) {
            int n4;
            int n5;
            while (n2 != 0 && ((Comparable)KArray[n5 = nArray[n4 = n2 - 1 >>> 1]]).compareTo(k) > 0) {
                nArray[n2] = n5;
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && comparator.compare(KArray[n7 = nArray[n6 = n2 - 1 >>> 1]], k) > 0) {
                nArray[n2] = n7;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        return n2;
    }

    public static <K> void makeHeap(K[] KArray, int n, int n2, int[] nArray, Comparator<K> comparator) {
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        if (nArray.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        int n3 = n2;
        while (n3-- != 0) {
            nArray[n3] = n + n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            ObjectSemiIndirectHeaps.downHeap(KArray, nArray, n2, n3, comparator);
        }
    }

    public static <K> int[] makeHeap(K[] KArray, int n, int n2, Comparator<K> comparator) {
        int[] nArray = n2 <= 0 ? IntArrays.EMPTY_ARRAY : new int[n2];
        ObjectSemiIndirectHeaps.makeHeap(KArray, n, n2, nArray, comparator);
        return nArray;
    }

    public static <K> void makeHeap(K[] KArray, int[] nArray, int n, Comparator<K> comparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            ObjectSemiIndirectHeaps.downHeap(KArray, nArray, n, n2, comparator);
        }
    }

    public static <K> int front(K[] KArray, int[] nArray, int n, int[] nArray2) {
        K k = KArray[nArray[0]];
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
            if (((Comparable)k).compareTo(KArray[nArray[i]]) != 0) continue;
            nArray2[n2++] = nArray[i];
            if (n3 == -1) {
                n3 = i * 2 + 1;
            }
            n4 = Math.min(n, i * 2 + 3);
        }
        return n2;
    }

    public static <K> int front(K[] KArray, int[] nArray, int n, int[] nArray2, Comparator<K> comparator) {
        K k = KArray[nArray[0]];
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
            if (comparator.compare(k, KArray[nArray[i]]) != 0) continue;
            nArray2[n2++] = nArray[i];
            if (n3 == -1) {
                n3 = i * 2 + 1;
            }
            n4 = Math.min(n, i * 2 + 3);
        }
        return n2;
    }
}

