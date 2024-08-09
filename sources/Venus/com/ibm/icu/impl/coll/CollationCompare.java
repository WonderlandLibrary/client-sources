/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.coll.CollationIterator;
import com.ibm.icu.impl.coll.CollationSettings;

public final class CollationCompare {
    static final boolean $assertionsDisabled = !CollationCompare.class.desiredAssertionStatus();

    public static int compareUpToQuaternary(CollationIterator collationIterator, CollationIterator collationIterator2, CollationSettings collationSettings) {
        int n;
        int n2;
        int n3;
        int n4;
        long l;
        int n5 = collationSettings.options;
        long l2 = (n5 & 0xC) == 0 ? 0L : collationSettings.variableTop + 1L;
        boolean bl = false;
        while (true) {
            long l3;
            if ((l3 = (l = collationIterator.nextCE()) >>> 32) < l2 && l3 > 0x2000000L) {
                bl = true;
                do {
                    collationIterator.setCurrentCE(l & 0xFFFFFFFF00000000L);
                    while ((l3 = (l = collationIterator.nextCE()) >>> 32) == 0L) {
                        collationIterator.setCurrentCE(0L);
                    }
                } while (l3 < l2 && l3 > 0x2000000L);
            }
            if (l3 == 0L) continue;
            do {
                long l4;
                if ((l = (l4 = collationIterator2.nextCE()) >>> 32) >= l2 || l <= 0x2000000L) continue;
                bl = true;
                do {
                    collationIterator2.setCurrentCE(l4 & 0xFFFFFFFF00000000L);
                    while ((l = (l4 = collationIterator2.nextCE()) >>> 32) == 0L) {
                        collationIterator2.setCurrentCE(0L);
                    }
                } while (l < l2 && l > 0x2000000L);
            } while (l == 0L);
            if (l3 != l) {
                if (collationSettings.hasReordering()) {
                    l3 = collationSettings.reorder(l3);
                    l = collationSettings.reorder(l);
                }
                return l3 < l ? -1 : 1;
            }
            if (l3 == 1L) break;
        }
        if (CollationSettings.getStrength(n5) >= 1) {
            if ((n5 & 0x800) == 0) {
                int n6 = 0;
                n4 = 0;
                while (true) {
                    int n7;
                    if ((n7 = (int)collationIterator.getCE(n6++) >>> 16) == 0) {
                        continue;
                    }
                    while ((n3 = (int)collationIterator2.getCE(n4++) >>> 16) == 0) {
                    }
                    if (n7 != n3) {
                        return n7 < n3 ? -1 : 1;
                    }
                    if (n7 == 256) break;
                }
            } else {
                int n8 = 0;
                n4 = 0;
                while (true) {
                    int n9;
                    int n10 = n8;
                    while ((l = collationIterator.getCE(n10) >>> 32) > 0x2000000L || l == 0L) {
                        ++n10;
                    }
                    n2 = n4;
                    while ((l = collationIterator2.getCE(n2) >>> 32) > 0x2000000L || l == 0L) {
                        ++n2;
                    }
                    int n11 = n10;
                    n = n2;
                    do {
                        n9 = 0;
                        while (n9 == 0 && n11 > n8) {
                            n9 = (int)collationIterator.getCE(--n11) >>> 16;
                        }
                        int n12 = 0;
                        while (n12 == 0 && n > n4) {
                            n12 = (int)collationIterator2.getCE(--n) >>> 16;
                        }
                        if (n9 == n12) continue;
                        return n9 < n12 ? -1 : 1;
                    } while (n9 != 0);
                    if (!$assertionsDisabled && collationIterator.getCE(n10) != collationIterator2.getCE(n2)) {
                        throw new AssertionError();
                    }
                    if (l == 1L) break;
                    n8 = n10 + 1;
                    n4 = n2 + 1;
                }
            }
        }
        if ((n5 & 0x400) != 0) {
            int n13;
            int n14 = CollationSettings.getStrength(n5);
            n4 = 0;
            int n15 = 0;
            do {
                if (n14 == 0) {
                    long l5;
                    do {
                        l5 = collationIterator.getCE(n4++);
                        n3 = (int)l5;
                    } while (l5 >>> 32 == 0L || n3 == 0);
                    n13 = n3;
                    n3 &= 0xC000;
                    do {
                        l5 = collationIterator2.getCE(n15++);
                        n2 = (int)l5;
                    } while (l5 >>> 32 == 0L || n2 == 0);
                    n2 &= 0xC000;
                } else {
                    while (((n3 = (int)collationIterator.getCE(n4++)) & 0xFFFF0000) == 0) {
                    }
                    n13 = n3;
                    n3 &= 0xC000;
                    while (((n2 = (int)collationIterator2.getCE(n15++)) & 0xFFFF0000) == 0) {
                    }
                    n2 &= 0xC000;
                }
                if (n3 == n2) continue;
                if ((n5 & 0x100) == 0) {
                    return n3 < n2 ? -1 : 1;
                }
                return n3 < n2 ? 1 : -1;
            } while (n13 >>> 16 != 256);
        }
        if (CollationSettings.getStrength(n5) <= 1) {
            return 1;
        }
        int n16 = CollationSettings.getTertiaryMask(n5);
        n4 = 0;
        int n17 = 0;
        n3 = 0;
        while (true) {
            int n18;
            int n19 = (int)collationIterator.getCE(n4++);
            n3 |= n19;
            if (!$assertionsDisabled && (n19 & 0x3F3F) == 0 && (n19 & 0xC0C0) != 0) {
                throw new AssertionError();
            }
            n2 = n19 & n16;
            if (n2 == 0) continue;
            do {
                n18 = (int)collationIterator2.getCE(n17++);
                n3 |= n18;
                if (!$assertionsDisabled && (n18 & 0x3F3F) == 0 && (n18 & 0xC0C0) != 0) {
                    throw new AssertionError();
                }
            } while ((n = n18 & n16) == 0);
            if (n2 != n) {
                if (CollationSettings.sortsTertiaryUpperCaseFirst(n5)) {
                    if (n2 > 256) {
                        n2 = (n19 & 0xFFFF0000) != 0 ? (n2 ^= 0xC000) : (n2 += 16384);
                    }
                    if (n > 256) {
                        n = (n18 & 0xFFFF0000) != 0 ? (n ^= 0xC000) : (n += 16384);
                    }
                }
                return n2 < n ? -1 : 1;
            }
            if (n2 == 256) break;
        }
        if (CollationSettings.getStrength(n5) <= 2) {
            return 1;
        }
        if (!bl && (n3 & 0xC0) == 0) {
            return 1;
        }
        n4 = 0;
        n17 = 0;
        while (true) {
            long l6;
            long l7;
            l7 = (l7 = (l6 = collationIterator.getCE(n4++)) & 0xFFFFL) <= 256L ? l6 >>> 32 : (l7 |= 0xFFFFFF3FL);
            if (l7 == 0L) continue;
            do {
                long l8;
                if ((l6 = (l8 = collationIterator2.getCE(n17++)) & 0xFFFFL) <= 256L) {
                    l6 = l8 >>> 32;
                    continue;
                }
                l6 |= 0xFFFFFF3FL;
            } while (l6 == 0L);
            if (l7 != l6) {
                if (collationSettings.hasReordering()) {
                    l7 = collationSettings.reorder(l7);
                    l6 = collationSettings.reorder(l6);
                }
                return l7 < l6 ? -1 : 1;
            }
            if (l7 == 1L) break;
        }
        return 1;
    }
}

