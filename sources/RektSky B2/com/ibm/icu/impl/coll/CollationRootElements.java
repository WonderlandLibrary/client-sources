package com.ibm.icu.impl.coll;

public final class CollationRootElements
{
    public static final long PRIMARY_SENTINEL = 4294967040L;
    public static final int SEC_TER_DELTA_FLAG = 128;
    public static final int PRIMARY_STEP_MASK = 127;
    public static final int IX_FIRST_TERTIARY_INDEX = 0;
    static final int IX_FIRST_SECONDARY_INDEX = 1;
    static final int IX_FIRST_PRIMARY_INDEX = 2;
    static final int IX_COMMON_SEC_AND_TER_CE = 3;
    static final int IX_SEC_TER_BOUNDARIES = 4;
    static final int IX_COUNT = 5;
    private long[] elements;
    static final /* synthetic */ boolean $assertionsDisabled;
    
    public CollationRootElements(final long[] rootElements) {
        this.elements = rootElements;
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
    
    long lastCEWithPrimaryBefore(long p) {
        if (p == 0L) {
            return 0L;
        }
        assert p > this.elements[(int)this.elements[2]];
        int index = this.findP(p);
        long q = this.elements[index];
        long secTer;
        if (p == (q & 0xFFFFFF00L)) {
            assert (q & 0x7FL) == 0x0L;
            secTer = this.elements[index - 1];
            if ((secTer & 0x80L) == 0x0L) {
                p = (secTer & 0xFFFFFF00L);
                secTer = 83887360L;
            }
            else {
                index -= 2;
                while (true) {
                    p = this.elements[index];
                    if ((p & 0x80L) == 0x0L) {
                        break;
                    }
                    --index;
                }
                p &= 0xFFFFFF00L;
            }
        }
        else {
            p = (q & 0xFFFFFF00L);
            secTer = 83887360L;
            while (true) {
                q = this.elements[++index];
                if ((q & 0x80L) == 0x0L) {
                    break;
                }
                secTer = q;
            }
            assert (q & 0x7FL) == 0x0L;
        }
        return p << 32 | (secTer & 0xFFFFFFFFFFFFFF7FL);
    }
    
    long firstCEWithPrimaryAtLeast(long p) {
        if (p == 0L) {
            return 0L;
        }
        int index = this.findP(p);
        if (p != (this.elements[index] & 0xFFFFFF00L)) {
            do {
                p = this.elements[++index];
            } while ((p & 0x80L) != 0x0L);
            assert (p & 0x7FL) == 0x0L;
        }
        return p << 32 | 0x5000500L;
    }
    
    long getPrimaryBefore(long p, final boolean isCompressible) {
        int index = this.findPrimary(p);
        final long q = this.elements[index];
        int step;
        if (p == (q & 0xFFFFFF00L)) {
            step = ((int)q & 0x7F);
            if (step == 0) {
                do {
                    p = this.elements[--index];
                } while ((p & 0x80L) != 0x0L);
                return p & 0xFFFFFF00L;
            }
        }
        else {
            final long nextElement = this.elements[index + 1];
            assert isEndOfPrimaryRange(nextElement);
            step = ((int)nextElement & 0x7F);
        }
        if ((p & 0xFFFFL) == 0x0L) {
            return Collation.decTwoBytePrimaryByOneStep(p, isCompressible, step);
        }
        return Collation.decThreeBytePrimaryByOneStep(p, isCompressible, step);
    }
    
    int getSecondaryBefore(final long p, final int s) {
        int index;
        int previousSec;
        int sec;
        if (p == 0L) {
            index = (int)this.elements[1];
            previousSec = 0;
            sec = (int)(this.elements[index] >> 16);
        }
        else {
            index = this.findPrimary(p) + 1;
            previousSec = 256;
            sec = (int)this.getFirstSecTerForPrimary(index) >>> 16;
        }
        assert s >= sec;
        while (s > sec) {
            previousSec = sec;
            assert (this.elements[index] & 0x80L) != 0x0L;
            sec = (int)(this.elements[index++] >> 16);
        }
        assert sec == s;
        return previousSec;
    }
    
    int getTertiaryBefore(final long p, final int s, final int t) {
        assert (t & 0xFFFFC0C0) == 0x0;
        int index;
        int previousTer;
        long secTer;
        if (p == 0L) {
            if (s == 0) {
                index = (int)this.elements[0];
                previousTer = 0;
            }
            else {
                index = (int)this.elements[1];
                previousTer = 256;
            }
            secTer = (this.elements[index] & 0xFFFFFFFFFFFFFF7FL);
        }
        else {
            index = this.findPrimary(p) + 1;
            previousTer = 256;
            secTer = this.getFirstSecTerForPrimary(index);
        }
        long st;
        for (st = ((long)s << 16 | (long)t); st > secTer; secTer = (this.elements[index++] & 0xFFFFFFFFFFFFFF7FL)) {
            if ((int)(secTer >> 16) == s) {
                previousTer = (int)secTer;
            }
            assert (this.elements[index] & 0x80L) != 0x0L;
        }
        assert secTer == st;
        return previousTer & 0xFFFF;
    }
    
    int findPrimary(final long p) {
        assert (p & 0xFFL) == 0x0L;
        final int index = this.findP(p);
        if (!CollationRootElements.$assertionsDisabled && !isEndOfPrimaryRange(this.elements[index + 1]) && p != (this.elements[index] & 0xFFFFFF00L)) {
            throw new AssertionError();
        }
        return index;
    }
    
    long getPrimaryAfter(final long p, int index, final boolean isCompressible) {
        assert !(!isEndOfPrimaryRange(this.elements[index + 1]));
        long q = this.elements[++index];
        final int step;
        if ((q & 0x80L) == 0x0L && (step = ((int)q & 0x7F)) != 0) {
            if ((p & 0xFFFFL) == 0x0L) {
                return Collation.incTwoBytePrimaryByOffset(p, isCompressible, step);
            }
            return Collation.incThreeBytePrimaryByOffset(p, isCompressible, step);
        }
        else {
            while ((q & 0x80L) != 0x0L) {
                q = this.elements[++index];
            }
            assert (q & 0x7FL) == 0x0L;
            return q;
        }
    }
    
    int getSecondaryAfter(int index, final int s) {
        long secTer;
        int secLimit;
        if (index == 0) {
            assert s != 0;
            index = (int)this.elements[1];
            secTer = this.elements[index];
            secLimit = 65536;
        }
        else {
            assert index >= (int)this.elements[2];
            secTer = this.getFirstSecTerForPrimary(index + 1);
            secLimit = this.getSecondaryBoundary();
        }
        while (true) {
            final int sec = (int)(secTer >> 16);
            if (sec > s) {
                return sec;
            }
            secTer = this.elements[++index];
            if ((secTer & 0x80L) == 0x0L) {
                return secLimit;
            }
        }
    }
    
    int getTertiaryAfter(int index, final int s, final int t) {
        int terLimit;
        long secTer;
        if (index == 0) {
            if (s == 0) {
                assert t != 0;
                index = (int)this.elements[0];
                terLimit = 16384;
            }
            else {
                index = (int)this.elements[1];
                terLimit = this.getTertiaryBoundary();
            }
            secTer = (this.elements[index] & 0xFFFFFFFFFFFFFF7FL);
        }
        else {
            assert index >= (int)this.elements[2];
            secTer = this.getFirstSecTerForPrimary(index + 1);
            terLimit = this.getTertiaryBoundary();
        }
        for (long st = ((long)s & 0xFFFFFFFFL) << 16 | (long)t; secTer <= st; secTer &= 0xFFFFFFFFFFFFFF7FL) {
            secTer = this.elements[++index];
            if ((secTer & 0x80L) == 0x0L || secTer >> 16 > s) {
                return terLimit;
            }
        }
        assert secTer >> 16 == s;
        return (int)secTer & 0xFFFF;
    }
    
    private long getFirstSecTerForPrimary(final int index) {
        long secTer = this.elements[index];
        if ((secTer & 0x80L) == 0x0L) {
            return 83887360L;
        }
        secTer &= 0xFFFFFFFFFFFFFF7FL;
        if (secTer > 83887360L) {
            return 83887360L;
        }
        return secTer;
    }
    
    private int findP(final long p) {
        assert p >> 24 != 254L;
        int start = (int)this.elements[2];
        assert p >= this.elements[start];
        int limit = this.elements.length - 1;
        assert this.elements[limit] >= 4294967040L;
        assert p < this.elements[limit];
    Label_0308:
        while (start + 1 < limit) {
            int i = (int)((start + (long)limit) / 2L);
            long q = this.elements[i];
            Label_0284: {
                if ((q & 0x80L) != 0x0L) {
                    int j = i + 1;
                    while (true) {
                        while (j != limit) {
                            q = this.elements[j];
                            if ((q & 0x80L) == 0x0L) {
                                i = j;
                                if ((q & 0x80L) != 0x0L) {
                                    j = i - 1;
                                    while (true) {
                                        while (j != start) {
                                            q = this.elements[j];
                                            if ((q & 0x80L) == 0x0L) {
                                                i = j;
                                                if ((q & 0x80L) != 0x0L) {
                                                    break Label_0308;
                                                }
                                                break Label_0284;
                                            }
                                            else {
                                                --j;
                                            }
                                        }
                                        continue;
                                    }
                                }
                                break Label_0284;
                            }
                            else {
                                ++j;
                            }
                        }
                        continue;
                    }
                }
            }
            if (p < (q & 0xFFFFFF00L)) {
                limit = i;
            }
            else {
                start = i;
            }
        }
        return start;
    }
    
    private static boolean isEndOfPrimaryRange(final long q) {
        return (q & 0x80L) == 0x0L && (q & 0x7FL) != 0x0L;
    }
}
