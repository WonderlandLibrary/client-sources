/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.coll.Collation;

public final class CollationRootElements {
    public static final long PRIMARY_SENTINEL = 0xFFFFFF00L;
    public static final int SEC_TER_DELTA_FLAG = 128;
    public static final int PRIMARY_STEP_MASK = 127;
    public static final int IX_FIRST_TERTIARY_INDEX = 0;
    static final int IX_FIRST_SECONDARY_INDEX = 1;
    static final int IX_FIRST_PRIMARY_INDEX = 2;
    static final int IX_COMMON_SEC_AND_TER_CE = 3;
    static final int IX_SEC_TER_BOUNDARIES = 4;
    static final int IX_COUNT = 5;
    private long[] elements;
    static final boolean $assertionsDisabled = !CollationRootElements.class.desiredAssertionStatus();

    public CollationRootElements(long[] lArray) {
        this.elements = lArray;
    }

    public int getTertiaryBoundary() {
        return (int)this.elements[4] << 8 & 0xFF00;
    }

    long getFirstTertiaryCE() {
        return this.elements[(int)this.elements[0]] & 0xFFFFFFFFFFFFFF7FL;
    }

    long getLastTertiaryCE() {
        return this.elements[(int)this.elements[1] - 1] & 0xFFFFFFFFFFFFFF7FL;
    }

    public int getLastCommonSecondary() {
        return (int)this.elements[4] >> 16 & 0xFF00;
    }

    public int getSecondaryBoundary() {
        return (int)this.elements[4] >> 8 & 0xFF00;
    }

    long getFirstSecondaryCE() {
        return this.elements[(int)this.elements[1]] & 0xFFFFFFFFFFFFFF7FL;
    }

    long getLastSecondaryCE() {
        return this.elements[(int)this.elements[2] - 1] & 0xFFFFFFFFFFFFFF7FL;
    }

    long getFirstPrimary() {
        return this.elements[(int)this.elements[2]];
    }

    long getFirstPrimaryCE() {
        return Collation.makeCE(this.getFirstPrimary());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    long lastCEWithPrimaryBefore(long l) {
        long l2;
        if (l == 0L) {
            return 0L;
        }
        if (!$assertionsDisabled && l <= this.elements[(int)this.elements[2]]) {
            throw new AssertionError();
        }
        int n = this.findP(l);
        long l3 = this.elements[n];
        if (l == (l3 & 0xFFFFFF00L)) {
            if (!$assertionsDisabled && (l3 & 0x7FL) != 0L) {
                throw new AssertionError();
            }
            l2 = this.elements[n - 1];
            if ((l2 & 0x80L) == 0L) {
                l = l2 & 0xFFFFFF00L;
                l2 = 0x5000500L;
                return l << 32 | l2 & 0xFFFFFFFFFFFFFF7FL;
            }
            n -= 2;
            while (true) {
                if (((l = this.elements[n]) & 0x80L) == 0L) {
                    l &= 0xFFFFFF00L;
                    return l << 32 | l2 & 0xFFFFFFFFFFFFFF7FL;
                }
                --n;
            }
        }
        l = l3 & 0xFFFFFF00L;
        l2 = 0x5000500L;
        while (true) {
            if (((l3 = this.elements[++n]) & 0x80L) == 0L) {
                if ($assertionsDisabled || (l3 & 0x7FL) == 0L) return l << 32 | l2 & 0xFFFFFFFFFFFFFF7FL;
                throw new AssertionError();
            }
            l2 = l3;
        }
    }

    long firstCEWithPrimaryAtLeast(long l) {
        if (l == 0L) {
            return 0L;
        }
        int n = this.findP(l);
        if (l != (this.elements[n] & 0xFFFFFF00L)) {
            while (((l = this.elements[++n]) & 0x80L) != 0L) {
            }
            if (!$assertionsDisabled && (l & 0x7FL) != 0L) {
                throw new AssertionError();
            }
        }
        return l << 32 | 0x5000500L;
    }

    long getPrimaryBefore(long l, boolean bl) {
        int n;
        int n2 = this.findPrimary(l);
        long l2 = this.elements[n2];
        if (l == (l2 & 0xFFFFFF00L)) {
            n = (int)l2 & 0x7F;
            if (n == 0) {
                while (((l = this.elements[--n2]) & 0x80L) != 0L) {
                }
                return l & 0xFFFFFF00L;
            }
        } else {
            long l3 = this.elements[n2 + 1];
            if (!$assertionsDisabled && !CollationRootElements.isEndOfPrimaryRange(l3)) {
                throw new AssertionError();
            }
            n = (int)l3 & 0x7F;
        }
        if ((l & 0xFFFFL) == 0L) {
            return Collation.decTwoBytePrimaryByOneStep(l, bl, n);
        }
        return Collation.decThreeBytePrimaryByOneStep(l, bl, n);
    }

    int getSecondaryBefore(long l, int n) {
        int n2;
        int n3;
        int n4;
        if (l == 0L) {
            n4 = (int)this.elements[1];
            n3 = 0;
            n2 = (int)(this.elements[n4] >> 16);
        } else {
            n4 = this.findPrimary(l) + 1;
            n3 = 256;
            n2 = (int)this.getFirstSecTerForPrimary(n4) >>> 16;
        }
        if (!$assertionsDisabled && n < n2) {
            throw new AssertionError();
        }
        while (n > n2) {
            n3 = n2;
            if (!$assertionsDisabled && (this.elements[n4] & 0x80L) == 0L) {
                throw new AssertionError();
            }
            n2 = (int)(this.elements[n4++] >> 16);
        }
        if (!$assertionsDisabled && n2 != n) {
            throw new AssertionError();
        }
        return n3;
    }

    int getTertiaryBefore(long l, int n, int n2) {
        long l2;
        int n3;
        int n4;
        if (!$assertionsDisabled && (n2 & 0xFFFFC0C0) != 0) {
            throw new AssertionError();
        }
        if (l == 0L) {
            if (n == 0) {
                n4 = (int)this.elements[0];
                n3 = 0;
            } else {
                n4 = (int)this.elements[1];
                n3 = 256;
            }
            l2 = this.elements[n4] & 0xFFFFFFFFFFFFFF7FL;
        } else {
            n4 = this.findPrimary(l) + 1;
            n3 = 256;
            l2 = this.getFirstSecTerForPrimary(n4);
        }
        long l3 = (long)n << 16 | (long)n2;
        while (l3 > l2) {
            if ((int)(l2 >> 16) == n) {
                n3 = (int)l2;
            }
            if (!$assertionsDisabled && (this.elements[n4] & 0x80L) == 0L) {
                throw new AssertionError();
            }
            l2 = this.elements[n4++] & 0xFFFFFFFFFFFFFF7FL;
        }
        if (!$assertionsDisabled && l2 != l3) {
            throw new AssertionError();
        }
        return n3 & 0xFFFF;
    }

    int findPrimary(long l) {
        if (!$assertionsDisabled && (l & 0xFFL) != 0L) {
            throw new AssertionError();
        }
        int n = this.findP(l);
        if (!$assertionsDisabled && !CollationRootElements.isEndOfPrimaryRange(this.elements[n + 1]) && l != (this.elements[n] & 0xFFFFFF00L)) {
            throw new AssertionError();
        }
        return n;
    }

    long getPrimaryAfter(long l, int n, boolean bl) {
        int n2;
        long l2;
        if (!$assertionsDisabled && l != (this.elements[n] & 0xFFFFFF00L) && !CollationRootElements.isEndOfPrimaryRange(this.elements[n + 1])) {
            throw new AssertionError();
        }
        if (((l2 = this.elements[++n]) & 0x80L) == 0L && (n2 = (int)l2 & 0x7F) != 0) {
            if ((l & 0xFFFFL) == 0L) {
                return Collation.incTwoBytePrimaryByOffset(l, bl, n2);
            }
            return Collation.incThreeBytePrimaryByOffset(l, bl, n2);
        }
        while ((l2 & 0x80L) != 0L) {
            l2 = this.elements[++n];
        }
        if (!$assertionsDisabled && (l2 & 0x7FL) != 0L) {
            throw new AssertionError();
        }
        return l2;
    }

    int getSecondaryAfter(int n, int n2) {
        int n3;
        long l;
        if (n == 0) {
            if (!$assertionsDisabled && n2 == 0) {
                throw new AssertionError();
            }
            n = (int)this.elements[1];
            l = this.elements[n];
            n3 = 65536;
        } else {
            if (!$assertionsDisabled && n < (int)this.elements[2]) {
                throw new AssertionError();
            }
            l = this.getFirstSecTerForPrimary(n + 1);
            n3 = this.getSecondaryBoundary();
        }
        do {
            int n4;
            if ((n4 = (int)(l >> 16)) <= n2) continue;
            return n4;
        } while (((l = this.elements[++n]) & 0x80L) != 0L);
        return n3;
    }

    int getTertiaryAfter(int n, int n2, int n3) {
        long l;
        int n4;
        if (n == 0) {
            if (n2 == 0) {
                if (!$assertionsDisabled && n3 == 0) {
                    throw new AssertionError();
                }
                n = (int)this.elements[0];
                n4 = 16384;
            } else {
                n = (int)this.elements[1];
                n4 = this.getTertiaryBoundary();
            }
            l = this.elements[n] & 0xFFFFFFFFFFFFFF7FL;
        } else {
            if (!$assertionsDisabled && n < (int)this.elements[2]) {
                throw new AssertionError();
            }
            l = this.getFirstSecTerForPrimary(n + 1);
            n4 = this.getTertiaryBoundary();
        }
        long l2 = ((long)n2 & 0xFFFFFFFFL) << 16 | (long)n3;
        while (true) {
            if (l > l2) {
                if (!$assertionsDisabled && l >> 16 != (long)n2) {
                    throw new AssertionError();
                }
                return (int)l & 0xFFFF;
            }
            if (((l = this.elements[++n]) & 0x80L) == 0L || l >> 16 > (long)n2) {
                return n4;
            }
            l &= 0xFFFFFFFFFFFFFF7FL;
        }
    }

    private long getFirstSecTerForPrimary(int n) {
        long l = this.elements[n];
        if ((l & 0x80L) == 0L) {
            return 0x5000500L;
        }
        if ((l &= 0xFFFFFFFFFFFFFF7FL) > 0x5000500L) {
            return 0x5000500L;
        }
        return l;
    }

    private int findP(long l) {
        if (!$assertionsDisabled && l >> 24 == 254L) {
            throw new AssertionError();
        }
        int n = (int)this.elements[2];
        if (!$assertionsDisabled && l < this.elements[n]) {
            throw new AssertionError();
        }
        int n2 = this.elements.length - 1;
        if (!$assertionsDisabled && this.elements[n2] < 0xFFFFFF00L) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && l >= this.elements[n2]) {
            throw new AssertionError();
        }
        while (n + 1 < n2) {
            int n3 = (int)(((long)n + (long)n2) / 2L);
            long l2 = this.elements[n3];
            if ((l2 & 0x80L) != 0L) {
                int n4;
                for (n4 = n3 + 1; n4 != n2; ++n4) {
                    l2 = this.elements[n4];
                    if ((l2 & 0x80L) != 0L) continue;
                    n3 = n4;
                    break;
                }
                if ((l2 & 0x80L) != 0L) {
                    for (n4 = n3 - 1; n4 != n; --n4) {
                        l2 = this.elements[n4];
                        if ((l2 & 0x80L) != 0L) continue;
                        n3 = n4;
                        break;
                    }
                    if ((l2 & 0x80L) != 0L) break;
                }
            }
            if (l < (l2 & 0xFFFFFF00L)) {
                n2 = n3;
                continue;
            }
            n = n3;
        }
        return n;
    }

    private static boolean isEndOfPrimaryRange(long l) {
        return (l & 0x80L) == 0L && (l & 0x7FL) != 0L;
    }
}

