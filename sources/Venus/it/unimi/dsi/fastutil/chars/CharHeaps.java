/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharComparator;

public final class CharHeaps {
    static final boolean $assertionsDisabled = !CharHeaps.class.desiredAssertionStatus();

    private CharHeaps() {
    }

    public static int downHeap(char[] cArray, int n, int n2, CharComparator charComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        char c = cArray[n2];
        if (charComparator == null) {
            int n3;
            while ((n3 = (n2 << 1) + 1) < n) {
                char c2 = cArray[n3];
                int n4 = n3 + 1;
                if (n4 < n && cArray[n4] < c2) {
                    n3 = n4;
                    c2 = cArray[n3];
                }
                if (c > c2) {
                    cArray[n2] = c2;
                    n2 = n3;
                    continue;
                }
                break;
            }
        } else {
            int n5;
            while ((n5 = (n2 << 1) + 1) < n) {
                char c3 = cArray[n5];
                int n6 = n5 + 1;
                if (n6 < n && charComparator.compare(cArray[n6], c3) < 0) {
                    n5 = n6;
                    c3 = cArray[n5];
                }
                if (charComparator.compare(c, c3) > 0) {
                    cArray[n2] = c3;
                    n2 = n5;
                    continue;
                }
                break;
            }
        }
        cArray[n2] = c;
        return n2;
    }

    public static int upHeap(char[] cArray, int n, int n2, CharComparator charComparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        char c = cArray[n2];
        if (charComparator == null) {
            int n3;
            char c2;
            while (n2 != 0 && (c2 = cArray[n3 = n2 - 1 >>> 1]) > c) {
                cArray[n2] = c2;
                n2 = n3;
            }
        } else {
            int n4;
            char c3;
            while (n2 != 0 && charComparator.compare(c3 = cArray[n4 = n2 - 1 >>> 1], c) > 0) {
                cArray[n2] = c3;
                n2 = n4;
            }
        }
        cArray[n2] = c;
        return n2;
    }

    public static void makeHeap(char[] cArray, int n, CharComparator charComparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            CharHeaps.downHeap(cArray, n, n2, charComparator);
        }
    }
}

