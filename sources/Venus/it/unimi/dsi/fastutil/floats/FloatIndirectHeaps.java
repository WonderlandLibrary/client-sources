/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import java.util.Arrays;

public final class FloatIndirectHeaps {
    static final boolean $assertionsDisabled = !FloatIndirectHeaps.class.desiredAssertionStatus();

    private FloatIndirectHeaps() {
    }

    public static int downHeap(float[] fArray, int[] nArray, int[] nArray2, int n, int n2, FloatComparator floatComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        float f = fArray[n3];
        if (floatComparator == null) {
            int n4;
            while ((n4 = (n2 << 1) + 1) < n) {
                int n5 = nArray[n4];
                int n6 = n4 + 1;
                if (n6 < n && Float.compare(fArray[nArray[n6]], fArray[n5]) < 0) {
                    n4 = n6;
                    n5 = nArray[n4];
                }
                if (Float.compare(f, fArray[n5]) > 0) {
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
                if (n9 < n && floatComparator.compare(fArray[nArray[n9]], fArray[n8]) < 0) {
                    n7 = n9;
                    n8 = nArray[n7];
                }
                if (floatComparator.compare(f, fArray[n8]) > 0) {
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

    public static int upHeap(float[] fArray, int[] nArray, int[] nArray2, int n, int n2, FloatComparator floatComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        float f = fArray[n3];
        if (floatComparator == null) {
            int n4;
            int n5;
            while (n2 != 0 && Float.compare(fArray[n5 = nArray[n4 = n2 - 1 >>> 1]], f) > 0) {
                nArray[n2] = n5;
                nArray2[nArray[n2]] = n2;
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && floatComparator.compare(fArray[n7 = nArray[n6 = n2 - 1 >>> 1]], f) > 0) {
                nArray[n2] = n7;
                nArray2[nArray[n2]] = n2;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        nArray2[n3] = n2;
        return n2;
    }

    public static void makeHeap(float[] fArray, int n, int n2, int[] nArray, int[] nArray2, FloatComparator floatComparator) {
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        if (nArray.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        if (nArray2.length < fArray.length) {
            throw new IllegalArgumentException("The inversion array length (" + nArray.length + ") is smaller than the length of the reference array (" + fArray.length + ")");
        }
        Arrays.fill(nArray2, 0, fArray.length, -1);
        int n3 = n2;
        while (n3-- != 0) {
            nArray[n3] = n + n3;
            nArray2[nArray[n3]] = n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            FloatIndirectHeaps.downHeap(fArray, nArray, nArray2, n2, n3, floatComparator);
        }
    }

    public static void makeHeap(float[] fArray, int[] nArray, int[] nArray2, int n, FloatComparator floatComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            FloatIndirectHeaps.downHeap(fArray, nArray, nArray2, n, n2, floatComparator);
        }
    }
}

