/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import java.util.Arrays;

public final class CollationWeights {
    private int middleLength;
    private int[] minBytes = new int[5];
    private int[] maxBytes = new int[5];
    private WeightRange[] ranges = new WeightRange[7];
    private int rangeIndex;
    private int rangeCount;
    static final boolean $assertionsDisabled = !CollationWeights.class.desiredAssertionStatus();

    public void initForPrimary(boolean bl) {
        this.middleLength = 1;
        this.minBytes[1] = 3;
        this.maxBytes[1] = 255;
        if (bl) {
            this.minBytes[2] = 4;
            this.maxBytes[2] = 254;
        } else {
            this.minBytes[2] = 2;
            this.maxBytes[2] = 255;
        }
        this.minBytes[3] = 2;
        this.maxBytes[3] = 255;
        this.minBytes[4] = 2;
        this.maxBytes[4] = 255;
    }

    public void initForSecondary() {
        this.middleLength = 3;
        this.minBytes[1] = 0;
        this.maxBytes[1] = 0;
        this.minBytes[2] = 0;
        this.maxBytes[2] = 0;
        this.minBytes[3] = 2;
        this.maxBytes[3] = 255;
        this.minBytes[4] = 2;
        this.maxBytes[4] = 255;
    }

    public void initForTertiary() {
        this.middleLength = 3;
        this.minBytes[1] = 0;
        this.maxBytes[1] = 0;
        this.minBytes[2] = 0;
        this.maxBytes[2] = 0;
        this.minBytes[3] = 2;
        this.maxBytes[3] = 63;
        this.minBytes[4] = 2;
        this.maxBytes[4] = 63;
    }

    public boolean allocWeights(long l, long l2, int n) {
        int n2;
        if (!this.getWeightRanges(l, l2)) {
            return true;
        }
        while (!this.allocWeightsInShortRanges(n, n2 = this.ranges[0].length)) {
            if (n2 == 4) {
                return true;
            }
            if (this.allocWeightsInMinLengthRanges(n, n2)) break;
            for (int i = 0; i < this.rangeCount && this.ranges[i].length == n2; ++i) {
                this.lengthenRange(this.ranges[i]);
            }
        }
        this.rangeIndex = 0;
        if (this.rangeCount < this.ranges.length) {
            this.ranges[this.rangeCount] = null;
        }
        return false;
    }

    public long nextWeight() {
        if (this.rangeIndex >= this.rangeCount) {
            return 0xFFFFFFFFL;
        }
        WeightRange weightRange = this.ranges[this.rangeIndex];
        long l = weightRange.start;
        if (--weightRange.count == 0) {
            ++this.rangeIndex;
        } else {
            weightRange.start = this.incWeight(l, weightRange.length);
            if (!$assertionsDisabled && weightRange.start > weightRange.end) {
                throw new AssertionError();
            }
        }
        return l;
    }

    public static int lengthOfWeight(long l) {
        if ((l & 0xFFFFFFL) == 0L) {
            return 0;
        }
        if ((l & 0xFFFFL) == 0L) {
            return 1;
        }
        if ((l & 0xFFL) == 0L) {
            return 0;
        }
        return 1;
    }

    private static int getWeightTrail(long l, int n) {
        return (int)(l >> 8 * (4 - n)) & 0xFF;
    }

    private static long setWeightTrail(long l, int n, int n2) {
        n = 8 * (4 - n);
        return l & 0xFFFFFF00L << n | (long)n2 << n;
    }

    private static int getWeightByte(long l, int n) {
        return CollationWeights.getWeightTrail(l, n);
    }

    private static long setWeightByte(long l, int n, int n2) {
        long l2 = (n *= 8) < 32 ? 0xFFFFFFFFL >> n : 0L;
        n = 32 - n;
        return l & (l2 |= 0xFFFFFF00L << n) | (long)n2 << n;
    }

    private static long truncateWeight(long l, int n) {
        return l & 0xFFFFFFFFL << 8 * (4 - n);
    }

    private static long incWeightTrail(long l, int n) {
        return l + (1L << 8 * (4 - n));
    }

    private static long decWeightTrail(long l, int n) {
        return l - (1L << 8 * (4 - n));
    }

    private int countBytes(int n) {
        return this.maxBytes[n] - this.minBytes[n] + 1;
    }

    private long incWeight(long l, int n) {
        do {
            int n2;
            if ((n2 = CollationWeights.getWeightByte(l, n)) < this.maxBytes[n]) {
                return CollationWeights.setWeightByte(l, n, n2 + 1);
            }
            l = CollationWeights.setWeightByte(l, n, this.minBytes[n]);
        } while ($assertionsDisabled || --n > 0);
        throw new AssertionError();
    }

    private long incWeightByOffset(long l, int n, int n2) {
        do {
            if ((n2 += CollationWeights.getWeightByte(l, n)) <= this.maxBytes[n]) {
                return CollationWeights.setWeightByte(l, n, n2);
            }
            l = CollationWeights.setWeightByte(l, n, this.minBytes[n] + (n2 -= this.minBytes[n]) % this.countBytes(n));
            n2 /= this.countBytes(n);
        } while ($assertionsDisabled || --n > 0);
        throw new AssertionError();
    }

    private void lengthenRange(WeightRange weightRange) {
        int n = weightRange.length + 1;
        weightRange.start = CollationWeights.setWeightTrail(weightRange.start, n, this.minBytes[n]);
        weightRange.end = CollationWeights.setWeightTrail(weightRange.end, n, this.maxBytes[n]);
        weightRange.count *= this.countBytes(n);
        weightRange.length = n;
    }

    private boolean getWeightRanges(long l, long l2) {
        int n;
        int n2;
        if (!$assertionsDisabled && l == 0L) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && l2 == 0L) {
            throw new AssertionError();
        }
        int n3 = CollationWeights.lengthOfWeight(l);
        int n4 = CollationWeights.lengthOfWeight(l2);
        if (!$assertionsDisabled && n3 < this.middleLength) {
            throw new AssertionError();
        }
        if (l >= l2) {
            return true;
        }
        if (n3 < n4 && l == CollationWeights.truncateWeight(l2, n3)) {
            return true;
        }
        WeightRange[] weightRangeArray = new WeightRange[5];
        WeightRange weightRange = new WeightRange(null);
        WeightRange[] weightRangeArray2 = new WeightRange[5];
        long l3 = l;
        for (n2 = n3; n2 > this.middleLength; --n2) {
            n = CollationWeights.getWeightTrail(l3, n2);
            if (n < this.maxBytes[n2]) {
                weightRangeArray[n2] = new WeightRange(null);
                weightRangeArray[n2].start = CollationWeights.incWeightTrail(l3, n2);
                weightRangeArray[n2].end = CollationWeights.setWeightTrail(l3, n2, this.maxBytes[n2]);
                weightRangeArray[n2].length = n2;
                weightRangeArray[n2].count = this.maxBytes[n2] - n;
            }
            l3 = CollationWeights.truncateWeight(l3, n2 - 1);
        }
        weightRange.start = l3 < 0xFF000000L ? CollationWeights.incWeightTrail(l3, this.middleLength) : 0xFFFFFFFFL;
        l3 = l2;
        for (n2 = n4; n2 > this.middleLength; --n2) {
            n = CollationWeights.getWeightTrail(l3, n2);
            if (n > this.minBytes[n2]) {
                weightRangeArray2[n2] = new WeightRange(null);
                weightRangeArray2[n2].start = CollationWeights.setWeightTrail(l3, n2, this.minBytes[n2]);
                weightRangeArray2[n2].end = CollationWeights.decWeightTrail(l3, n2);
                weightRangeArray2[n2].length = n2;
                weightRangeArray2[n2].count = n - this.minBytes[n2];
            }
            l3 = CollationWeights.truncateWeight(l3, n2 - 1);
        }
        weightRange.end = CollationWeights.decWeightTrail(l3, this.middleLength);
        weightRange.length = this.middleLength;
        if (weightRange.end >= weightRange.start) {
            weightRange.count = (int)(weightRange.end - weightRange.start >> 8 * (4 - this.middleLength)) + 1;
        } else {
            for (n2 = 4; n2 > this.middleLength; --n2) {
                if (weightRangeArray[n2] == null || weightRangeArray2[n2] == null || weightRangeArray[n2].count <= 0 || weightRangeArray2[n2].count <= 0) continue;
                long l4 = weightRangeArray[n2].end;
                long l5 = weightRangeArray2[n2].start;
                boolean bl = false;
                if (l4 > l5) {
                    if (!$assertionsDisabled && CollationWeights.truncateWeight(l4, n2 - 1) != CollationWeights.truncateWeight(l5, n2 - 1)) {
                        throw new AssertionError();
                    }
                    weightRangeArray[n2].end = weightRangeArray2[n2].end;
                    weightRangeArray[n2].count = CollationWeights.getWeightTrail(weightRangeArray[n2].end, n2) - CollationWeights.getWeightTrail(weightRangeArray[n2].start, n2) + 1;
                    bl = true;
                } else if (l4 == l5) {
                    if (!$assertionsDisabled && this.minBytes[n2] >= this.maxBytes[n2]) {
                        throw new AssertionError();
                    }
                } else if (this.incWeight(l4, n2) == l5) {
                    weightRangeArray[n2].end = weightRangeArray2[n2].end;
                    weightRangeArray[n2].count += weightRangeArray2[n2].count;
                    bl = true;
                }
                if (!bl) continue;
                weightRangeArray2[n2].count = 0;
                while (--n2 > this.middleLength) {
                    weightRangeArray2[n2] = null;
                    weightRangeArray[n2] = null;
                }
                break;
            }
        }
        this.rangeCount = 0;
        if (weightRange.count > 0) {
            this.ranges[0] = weightRange;
            this.rangeCount = 1;
        }
        for (n2 = this.middleLength + 1; n2 <= 4; ++n2) {
            if (weightRangeArray2[n2] != null && weightRangeArray2[n2].count > 0) {
                this.ranges[this.rangeCount++] = weightRangeArray2[n2];
            }
            if (weightRangeArray[n2] == null || weightRangeArray[n2].count <= 0) continue;
            this.ranges[this.rangeCount++] = weightRangeArray[n2];
        }
        return this.rangeCount > 0;
    }

    private boolean allocWeightsInShortRanges(int n, int n2) {
        for (int i = 0; i < this.rangeCount && this.ranges[i].length <= n2 + 1; ++i) {
            if (n <= this.ranges[i].count) {
                if (this.ranges[i].length > n2) {
                    this.ranges[i].count = n;
                }
                this.rangeCount = i + 1;
                if (this.rangeCount > 1) {
                    Arrays.sort(this.ranges, 0, this.rangeCount);
                }
                return false;
            }
            n -= this.ranges[i].count;
        }
        return true;
    }

    private boolean allocWeightsInMinLengthRanges(int n, int n2) {
        int n3;
        int n4;
        int n5 = 0;
        for (n4 = 0; n4 < this.rangeCount && this.ranges[n4].length == n2; ++n4) {
            n5 += this.ranges[n4].count;
        }
        int n6 = this.countBytes(n2 + 1);
        if (n > n5 * n6) {
            return true;
        }
        long l = this.ranges[0].start;
        long l2 = this.ranges[0].end;
        for (n3 = 1; n3 < n4; ++n3) {
            if (this.ranges[n3].start < l) {
                l = this.ranges[n3].start;
            }
            if (this.ranges[n3].end <= l2) continue;
            l2 = this.ranges[n3].end;
        }
        n3 = (n - n5) / (n6 - 1);
        int n7 = n5 - n3;
        if (!(n3 != 0 && n7 + n3 * n6 >= n || $assertionsDisabled || --n7 + ++n3 * n6 >= n)) {
            throw new AssertionError();
        }
        this.ranges[0].start = l;
        if (n7 == 0) {
            this.ranges[0].end = l2;
            this.ranges[0].count = n5;
            this.lengthenRange(this.ranges[0]);
            this.rangeCount = 1;
        } else {
            this.ranges[0].end = this.incWeightByOffset(l, n2, n7 - 1);
            this.ranges[0].count = n7;
            if (this.ranges[1] == null) {
                this.ranges[1] = new WeightRange(null);
            }
            this.ranges[1].start = this.incWeight(this.ranges[0].end, n2);
            this.ranges[1].end = l2;
            this.ranges[1].length = n2;
            this.ranges[1].count = n3;
            this.lengthenRange(this.ranges[1]);
            this.rangeCount = 2;
        }
        return false;
    }

    private static final class WeightRange
    implements Comparable<WeightRange> {
        long start;
        long end;
        int length;
        int count;

        private WeightRange() {
        }

        @Override
        public int compareTo(WeightRange weightRange) {
            long l = this.start;
            long l2 = weightRange.start;
            if (l < l2) {
                return 1;
            }
            if (l > l2) {
                return 0;
            }
            return 1;
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((WeightRange)object);
        }

        WeightRange(1 var1_1) {
            this();
        }
    }
}

