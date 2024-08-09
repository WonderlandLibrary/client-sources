/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleComparator;

public final class DoubleHeaps {
    static final boolean $assertionsDisabled = !DoubleHeaps.class.desiredAssertionStatus();

    private DoubleHeaps() {
    }

    public static int downHeap(double[] dArray, int n, int n2, DoubleComparator doubleComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        double d = dArray[n2];
        if (doubleComparator == null) {
            int n3;
            while ((n3 = (n2 << 1) + 1) < n) {
                double d2 = dArray[n3];
                int n4 = n3 + 1;
                if (n4 < n && Double.compare(dArray[n4], d2) < 0) {
                    n3 = n4;
                    d2 = dArray[n3];
                }
                if (Double.compare(d, d2) > 0) {
                    dArray[n2] = d2;
                    n2 = n3;
                    continue;
                }
                break;
            }
        } else {
            int n5;
            while ((n5 = (n2 << 1) + 1) < n) {
                double d3 = dArray[n5];
                int n6 = n5 + 1;
                if (n6 < n && doubleComparator.compare(dArray[n6], d3) < 0) {
                    n5 = n6;
                    d3 = dArray[n5];
                }
                if (doubleComparator.compare(d, d3) > 0) {
                    dArray[n2] = d3;
                    n2 = n5;
                    continue;
                }
                break;
            }
        }
        dArray[n2] = d;
        return n2;
    }

    public static int upHeap(double[] dArray, int n, int n2, DoubleComparator doubleComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        double d = dArray[n2];
        if (doubleComparator == null) {
            int n3;
            double d2;
            while (n2 != 0 && Double.compare(d2 = dArray[n3 = n2 - 1 >>> 1], d) > 0) {
                dArray[n2] = d2;
                n2 = n3;
            }
        } else {
            int n4;
            double d3;
            while (n2 != 0 && doubleComparator.compare(d3 = dArray[n4 = n2 - 1 >>> 1], d) > 0) {
                dArray[n2] = d3;
                n2 = n4;
            }
        }
        dArray[n2] = d;
        return n2;
    }

    public static void makeHeap(double[] dArray, int n, DoubleComparator doubleComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            DoubleHeaps.downHeap(dArray, n, n2, doubleComparator);
        }
    }
}

