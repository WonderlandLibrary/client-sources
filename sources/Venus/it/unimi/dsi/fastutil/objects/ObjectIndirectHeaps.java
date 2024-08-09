/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.Arrays;
import java.util.Comparator;

public final class ObjectIndirectHeaps {
    static final boolean $assertionsDisabled = !ObjectIndirectHeaps.class.desiredAssertionStatus();

    private ObjectIndirectHeaps() {
    }

    public static <K> int downHeap(K[] KArray, int[] nArray, int[] nArray2, int n, int n2, Comparator<K> comparator) {
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
                if (n9 < n && comparator.compare(KArray[nArray[n9]], KArray[n8]) < 0) {
                    n7 = n9;
                    n8 = nArray[n7];
                }
                if (comparator.compare(k, KArray[n8]) > 0) {
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

    public static <K> int upHeap(K[] KArray, int[] nArray, int[] nArray2, int n, int n2, Comparator<K> comparator) {
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
                nArray2[nArray[n2]] = n2;
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && comparator.compare(KArray[n7 = nArray[n6 = n2 - 1 >>> 1]], k) > 0) {
                nArray[n2] = n7;
                nArray2[nArray[n2]] = n2;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        nArray2[n3] = n2;
        return n2;
    }

    public static <K> void makeHeap(K[] KArray, int n, int n2, int[] nArray, int[] nArray2, Comparator<K> comparator) {
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        if (nArray.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        if (nArray2.length < KArray.length) {
            throw new IllegalArgumentException("The inversion array length (" + nArray.length + ") is smaller than the length of the reference array (" + KArray.length + ")");
        }
        Arrays.fill(nArray2, 0, KArray.length, -1);
        int n3 = n2;
        while (n3-- != 0) {
            nArray[n3] = n + n3;
            nArray2[nArray[n3]] = n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            ObjectIndirectHeaps.downHeap(KArray, nArray, nArray2, n2, n3, comparator);
        }
    }

    public static <K> void makeHeap(K[] KArray, int[] nArray, int[] nArray2, int n, Comparator<K> comparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            ObjectIndirectHeaps.downHeap(KArray, nArray, nArray2, n, n2, comparator);
        }
    }
}

