/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.ints.IntArrays;

public final class FloatSemiIndirectHeaps {
    static final boolean $assertionsDisabled = !FloatSemiIndirectHeaps.class.desiredAssertionStatus();

    private FloatSemiIndirectHeaps() {
    }

    public static int downHeap(float[] fArray, int[] nArray, int n, int n2, FloatComparator floatComparator) {
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
                    n2 = n7;
                    continue;
                }
                break;
            }
        }
        nArray[n2] = n3;
        return n2;
    }

    public static int upHeap(float[] fArray, int[] nArray, int n, int n2, FloatComparator floatComparator) {
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
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && floatComparator.compare(fArray[n7 = nArray[n6 = n2 - 1 >>> 1]], f) > 0) {
                nArray[n2] = n7;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        return n2;
    }

    public static void makeHeap(float[] fArray, int n, int n2, int[] nArray, FloatComparator floatComparator) {
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        if (nArray.length < n2) {
            throw new IllegalArgumentException("The heap length (" + nArray.length + ") is smaller than the number of elements (" + n2 + ")");
        }
        int n3 = n2;
        while (n3-- != 0) {
            nArray[n3] = n + n3;
        }
        n3 = n2 >>> 1;
        while (n3-- != 0) {
            FloatSemiIndirectHeaps.downHeap(fArray, nArray, n2, n3, floatComparator);
        }
    }

    public static int[] makeHeap(float[] fArray, int n, int n2, FloatComparator floatComparator) {
        int[] nArray = n2 <= 0 ? IntArrays.EMPTY_ARRAY : new int[n2];
        FloatSemiIndirectHeaps.makeHeap(fArray, n, n2, nArray, floatComparator);
        return nArray;
    }

    public static void makeHeap(float[] fArray, int[] nArray, int n, FloatComparator floatComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            FloatSemiIndirectHeaps.downHeap(fArray, nArray, n, n2, floatComparator);
        }
    }

    public static int front(float[] fArray, int[] nArray, int n, int[] nArray2) {
        float f = fArray[nArray[0]];
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
            if (Float.compare(f, fArray[nArray[i]]) != 0) continue;
            nArray2[n2++] = nArray[i];
            if (n3 == -1) {
                n3 = i * 2 + 1;
            }
            n4 = Math.min(n, i * 2 + 3);
        }
        return n2;
    }

    public static int front(float[] fArray, int[] nArray, int n, int[] nArray2, FloatComparator floatComparator) {
        float f = fArray[nArray[0]];
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
            if (floatComparator.compare(f, fArray[nArray[i]]) != 0) continue;
            nArray2[n2++] = nArray[i];
            if (n3 == -1) {
                n3 = i * 2 + 1;
            }
            n4 = Math.min(n, i * 2 + 3);
        }
        return n2;
    }
}

