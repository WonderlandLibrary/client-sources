/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import java.util.Comparator;

public final class ObjectHeaps {
    static final boolean $assertionsDisabled = !ObjectHeaps.class.desiredAssertionStatus();

    private ObjectHeaps() {
    }

    public static <K> int downHeap(K[] KArray, int n, int n2, Comparator<? super K> comparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        K k = KArray[n2];
        if (comparator == null) {
            int n3;
            while ((n3 = (n2 << 1) + 1) < n) {
                K k2 = KArray[n3];
                int n4 = n3 + 1;
                if (n4 < n && ((Comparable)KArray[n4]).compareTo(k2) < 0) {
                    n3 = n4;
                    k2 = KArray[n3];
                }
                if (((Comparable)k).compareTo(k2) > 0) {
                    KArray[n2] = k2;
                    n2 = n3;
                    continue;
                }
                break;
            }
        } else {
            int n5;
            while ((n5 = (n2 << 1) + 1) < n) {
                K k3 = KArray[n5];
                int n6 = n5 + 1;
                if (n6 < n && comparator.compare(KArray[n6], k3) < 0) {
                    n5 = n6;
                    k3 = KArray[n5];
                }
                if (comparator.compare(k, k3) > 0) {
                    KArray[n2] = k3;
                    n2 = n5;
                    continue;
                }
                break;
            }
        }
        KArray[n2] = k;
        return n2;
    }

    public static <K> int upHeap(K[] KArray, int n, int n2, Comparator<K> comparator) {
        if (!$assertionsDisabled && n2 >= n) {
            throw new AssertionError();
        }
        K k = KArray[n2];
        if (comparator == null) {
            int n3;
            K k2;
            while (n2 != 0 && ((Comparable)(k2 = KArray[n3 = n2 - 1 >>> 1])).compareTo(k) > 0) {
                KArray[n2] = k2;
                n2 = n3;
            }
        } else {
            int n4;
            K k3;
            while (n2 != 0 && comparator.compare(k3 = KArray[n4 = n2 - 1 >>> 1], k) > 0) {
                KArray[n2] = k3;
                n2 = n4;
            }
        }
        KArray[n2] = k;
        return n2;
    }

    public static <K> void makeHeap(K[] KArray, int n, Comparator<K> comparator) {
        int n2 = n >>> 1;
        while (n2-- != 0) {
            ObjectHeaps.downHeap(KArray, n, n2, comparator);
        }
    }
}

