/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.Validate;

public class ArbitraryBitLengthIntArray {
    private final long[] field_233043_a_;
    private final int field_233044_b_;
    private final long field_233045_c_;
    private final int field_233046_d_;

    public ArbitraryBitLengthIntArray(int n, int n2) {
        this(n, n2, new long[MathHelper.roundUp(n2 * n, 64) / 64]);
    }

    public ArbitraryBitLengthIntArray(int n, int n2, long[] lArray) {
        Validate.inclusiveBetween(1L, 32L, n);
        this.field_233046_d_ = n2;
        this.field_233044_b_ = n;
        this.field_233043_a_ = lArray;
        this.field_233045_c_ = (1L << n) - 1L;
        int n3 = MathHelper.roundUp(n2 * n, 64) / 64;
        if (lArray.length != n3) {
            throw new IllegalArgumentException("Invalid length given for storage, got: " + lArray.length + " but expected: " + n3);
        }
    }

    public void func_233049_a_(int n, int n2) {
        Validate.inclusiveBetween(0L, this.field_233046_d_ - 1, n);
        Validate.inclusiveBetween(0L, this.field_233045_c_, n2);
        int n3 = n * this.field_233044_b_;
        int n4 = n3 >> 6;
        int n5 = (n + 1) * this.field_233044_b_ - 1 >> 6;
        int n6 = n3 ^ n4 << 6;
        this.field_233043_a_[n4] = this.field_233043_a_[n4] & (this.field_233045_c_ << n6 ^ 0xFFFFFFFFFFFFFFFFL) | ((long)n2 & this.field_233045_c_) << n6;
        if (n4 != n5) {
            int n7 = 64 - n6;
            int n8 = this.field_233044_b_ - n7;
            this.field_233043_a_[n5] = this.field_233043_a_[n5] >>> n8 << n8 | ((long)n2 & this.field_233045_c_) >> n7;
        }
    }

    public int func_233048_a_(int n) {
        Validate.inclusiveBetween(0L, this.field_233046_d_ - 1, n);
        int n2 = n * this.field_233044_b_;
        int n3 = n2 >> 6;
        int n4 = (n + 1) * this.field_233044_b_ - 1 >> 6;
        int n5 = n2 ^ n3 << 6;
        if (n3 == n4) {
            return (int)(this.field_233043_a_[n3] >>> n5 & this.field_233045_c_);
        }
        int n6 = 64 - n5;
        return (int)((this.field_233043_a_[n3] >>> n5 | this.field_233043_a_[n4] << n6) & this.field_233045_c_);
    }

    public long[] func_233047_a_() {
        return this.field_233043_a_;
    }

    public int func_233050_b_() {
        return this.field_233044_b_;
    }
}

