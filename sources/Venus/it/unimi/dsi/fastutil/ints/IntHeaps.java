/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntComparator;

public final class IntHeaps {
    static final boolean $assertionsDisabled = !IntHeaps.class.desiredAssertionStatus();

    private IntHeaps() {
    }

    public static int downHeap(int[] nArray, int n, int n2, IntComparator intComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        if (intComparator == null) {
            int n4;
            while ((n4 = (n2 << 1) + 1) < n) {
                int n5 = nArray[n4];
                int n6 = n4 + 1;
                if (n6 < n && nArray[n6] < n5) {
                    n4 = n6;
                    n5 = nArray[n4];
                }
                if (n3 > n5) {
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
                if (n9 < n && intComparator.compare(nArray[n9], n8) < 0) {
                    n7 = n9;
                    n8 = nArray[n7];
                }
                if (intComparator.compare(n3, n8) > 0) {
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

    public static int upHeap(int[] nArray, int n, int n2, IntComparator intComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        int n3 = nArray[n2];
        if (intComparator == null) {
            int n4;
            int n5;
            while (n2 != 0 && (n5 = nArray[n4 = n2 - 1 >>> 1]) > n3) {
                nArray[n2] = n5;
                n2 = n4;
            }
        } else {
            int n6;
            int n7;
            while (n2 != 0 && intComparator.compare(n7 = nArray[n6 = n2 - 1 >>> 1], n3) > 0) {
                nArray[n2] = n7;
                n2 = n6;
            }
        }
        nArray[n2] = n3;
        return n2;
    }

    public static void makeHeap(int[] nArray, int n, IntComparator intComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            IntHeaps.downHeap(nArray, n, n2, intComparator);
        }
    }
}

