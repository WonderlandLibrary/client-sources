/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatComparator;

public final class FloatHeaps {
    static final boolean $assertionsDisabled = !FloatHeaps.class.desiredAssertionStatus();

    private FloatHeaps() {
    }

    public static int downHeap(float[] fArray, int n, int n2, FloatComparator floatComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        float f = fArray[n2];
        if (floatComparator == null) {
            int n3;
            while ((n3 = (n2 << 1) + 1) < n) {
                float f2 = fArray[n3];
                int n4 = n3 + 1;
                if (n4 < n && Float.compare(fArray[n4], f2) < 0) {
                    n3 = n4;
                    f2 = fArray[n3];
                }
                if (Float.compare(f, f2) > 0) {
                    fArray[n2] = f2;
                    n2 = n3;
                    continue;
                }
                break;
            }
        } else {
            int n5;
            while ((n5 = (n2 << 1) + 1) < n) {
                float f3 = fArray[n5];
                int n6 = n5 + 1;
                if (n6 < n && floatComparator.compare(fArray[n6], f3) < 0) {
                    n5 = n6;
                    f3 = fArray[n5];
                }
                if (floatComparator.compare(f, f3) > 0) {
                    fArray[n2] = f3;
                    n2 = n5;
                    continue;
                }
                break;
            }
        }
        fArray[n2] = f;
        return n2;
    }

    public static int upHeap(float[] fArray, int n, int n2, FloatComparator floatComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        float f = fArray[n2];
        if (floatComparator == null) {
            int n3;
            float f2;
            while (n2 != 0 && Float.compare(f2 = fArray[n3 = n2 - 1 >>> 1], f) > 0) {
                fArray[n2] = f2;
                n2 = n3;
            }
        } else {
            int n4;
            float f3;
            while (n2 != 0 && floatComparator.compare(f3 = fArray[n4 = n2 - 1 >>> 1], f) > 0) {
                fArray[n2] = f3;
                n2 = n4;
            }
        }
        fArray[n2] = f;
        return n2;
    }

    public static void makeHeap(float[] fArray, int n, FloatComparator floatComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            FloatHeaps.downHeap(fArray, n, n2, floatComparator);
        }
    }
}

