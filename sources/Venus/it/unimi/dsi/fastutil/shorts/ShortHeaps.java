/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortComparator;

public final class ShortHeaps {
    static final boolean $assertionsDisabled = !ShortHeaps.class.desiredAssertionStatus();

    private ShortHeaps() {
    }

    public static int downHeap(short[] sArray, int n, int n2, ShortComparator shortComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        short s = sArray[n2];
        if (shortComparator == null) {
            int n3;
            while ((n3 = (n2 << 1) + 1) < n) {
                short s2 = sArray[n3];
                int n4 = n3 + 1;
                if (n4 < n && sArray[n4] < s2) {
                    n3 = n4;
                    s2 = sArray[n3];
                }
                if (s > s2) {
                    sArray[n2] = s2;
                    n2 = n3;
                    continue;
                }
                break;
            }
        } else {
            int n5;
            while ((n5 = (n2 << 1) + 1) < n) {
                short s3 = sArray[n5];
                int n6 = n5 + 1;
                if (n6 < n && shortComparator.compare(sArray[n6], s3) < 0) {
                    n5 = n6;
                    s3 = sArray[n5];
                }
                if (shortComparator.compare(s, s3) > 0) {
                    sArray[n2] = s3;
                    n2 = n5;
                    continue;
                }
                break;
            }
        }
        sArray[n2] = s;
        return n2;
    }

    public static int upHeap(short[] sArray, int n, int n2, ShortComparator shortComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        short s = sArray[n2];
        if (shortComparator == null) {
            int n3;
            short s2;
            while (n2 != 0 && (s2 = sArray[n3 = n2 - 1 >>> 1]) > s) {
                sArray[n2] = s2;
                n2 = n3;
            }
        } else {
            int n4;
            short s3;
            while (n2 != 0 && shortComparator.compare(s3 = sArray[n4 = n2 - 1 >>> 1], s) > 0) {
                sArray[n2] = s3;
                n2 = n4;
            }
        }
        sArray[n2] = s;
        return n2;
    }

    public static void makeHeap(short[] sArray, int n, ShortComparator shortComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            ShortHeaps.downHeap(sArray, n, n2, shortComparator);
        }
    }
}

