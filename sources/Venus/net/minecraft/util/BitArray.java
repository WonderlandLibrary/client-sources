/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.function.IntConsumer;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import org.apache.commons.lang3.Validate;

public class BitArray {
    private static final int[] field_232981_a_ = new int[]{-1, -1, 0, Integer.MIN_VALUE, 0, 0, 0x55555555, 0x55555555, 0, Integer.MIN_VALUE, 0, 1, 0x33333333, 0x33333333, 0, 0x2AAAAAAA, 0x2AAAAAAA, 0, 0x24924924, 0x24924924, 0, Integer.MIN_VALUE, 0, 2, 0x1C71C71C, 0x1C71C71C, 0, 0x19999999, 0x19999999, 0, 390451572, 390451572, 0, 0x15555555, 0x15555555, 0, 0x13B13B13, 0x13B13B13, 0, 306783378, 306783378, 0, 0x11111111, 0x11111111, 0, Integer.MIN_VALUE, 0, 3, 0xF0F0F0F, 0xF0F0F0F, 0, 0xE38E38E, 0xE38E38E, 0, 226050910, 226050910, 0, 0xCCCCCCC, 0xCCCCCCC, 0, 0xC30C30C, 0xC30C30C, 0, 195225786, 195225786, 0, 186737708, 186737708, 0, 0xAAAAAAA, 0xAAAAAAA, 0, 171798691, 171798691, 0, 0x9D89D89, 0x9D89D89, 0, 159072862, 159072862, 0, 0x9249249, 0x9249249, 0, 148102320, 148102320, 0, 0x8888888, 0x8888888, 0, 138547332, 138547332, 0, Integer.MIN_VALUE, 0, 4, 130150524, 130150524, 0, 0x7878787, 0x7878787, 0, 0x7507507, 0x7507507, 0, 0x71C71C7, 0x71C71C7, 0, 116080197, 116080197, 0, 113025455, 113025455, 0, 0x6906906, 0x6906906, 0, 0x6666666, 0x6666666, 0, 104755299, 104755299, 0, 0x6186186, 0x6186186, 0, 99882960, 99882960, 0, 97612893, 97612893, 0, 0x5B05B05, 0x5B05B05, 0, 93368854, 93368854, 0, 91382282, 91382282, 0, 0x5555555, 0x5555555, 0, 87652393, 87652393, 0, 85899345, 85899345, 0, 0x5050505, 0x5050505, 0, 0x4EC4EC4, 0x4EC4EC4, 0, 81037118, 81037118, 0, 79536431, 79536431, 0, 78090314, 78090314, 0, 0x4924924, 0x4924924, 0, 75350303, 75350303, 0, 74051160, 74051160, 0, 72796055, 72796055, 0, 0x4444444, 0x4444444, 0, 70409299, 70409299, 0, 69273666, 69273666, 0, 0x4104104, 0x4104104, 0, Integer.MIN_VALUE, 0, 5};
    private final long[] longArray;
    private final int bitsPerEntry;
    private final long maxEntryValue;
    private final int arraySize;
    private final int field_232982_f_;
    private final int field_232983_g_;
    private final int field_232984_h_;
    private final int field_232985_i_;

    public BitArray(int n, int n2) {
        this(n, n2, null);
    }

    public BitArray(int n, int n2, @Nullable long[] lArray) {
        Validate.inclusiveBetween(1L, 32L, n);
        this.arraySize = n2;
        this.bitsPerEntry = n;
        this.maxEntryValue = (1L << n) - 1L;
        this.field_232982_f_ = (char)(64 / n);
        int n3 = 3 * (this.field_232982_f_ - 1);
        this.field_232983_g_ = field_232981_a_[n3 + 0];
        this.field_232984_h_ = field_232981_a_[n3 + 1];
        this.field_232985_i_ = field_232981_a_[n3 + 2];
        int n4 = (n2 + this.field_232982_f_ - 1) / this.field_232982_f_;
        if (lArray != null) {
            if (lArray.length != n4) {
                throw Util.pauseDevMode(new RuntimeException("Invalid length given for storage, got: " + lArray.length + " but expected: " + n4));
            }
            this.longArray = lArray;
        } else {
            this.longArray = new long[n4];
        }
    }

    private int func_232986_b_(int n) {
        long l = Integer.toUnsignedLong(this.field_232983_g_);
        long l2 = Integer.toUnsignedLong(this.field_232984_h_);
        return (int)((long)n * l + l2 >> 32 >> this.field_232985_i_);
    }

    public int swapAt(int n, int n2) {
        Validate.inclusiveBetween(0L, this.arraySize - 1, n);
        Validate.inclusiveBetween(0L, this.maxEntryValue, n2);
        int n3 = this.func_232986_b_(n);
        long l = this.longArray[n3];
        int n4 = (n - n3 * this.field_232982_f_) * this.bitsPerEntry;
        int n5 = (int)(l >> n4 & this.maxEntryValue);
        this.longArray[n3] = l & (this.maxEntryValue << n4 ^ 0xFFFFFFFFFFFFFFFFL) | ((long)n2 & this.maxEntryValue) << n4;
        return n5;
    }

    public void setAt(int n, int n2) {
        Validate.inclusiveBetween(0L, this.arraySize - 1, n);
        Validate.inclusiveBetween(0L, this.maxEntryValue, n2);
        int n3 = this.func_232986_b_(n);
        long l = this.longArray[n3];
        int n4 = (n - n3 * this.field_232982_f_) * this.bitsPerEntry;
        this.longArray[n3] = l & (this.maxEntryValue << n4 ^ 0xFFFFFFFFFFFFFFFFL) | ((long)n2 & this.maxEntryValue) << n4;
    }

    public int getAt(int n) {
        Validate.inclusiveBetween(0L, this.arraySize - 1, n);
        int n2 = this.func_232986_b_(n);
        long l = this.longArray[n2];
        int n3 = (n - n2 * this.field_232982_f_) * this.bitsPerEntry;
        return (int)(l >> n3 & this.maxEntryValue);
    }

    public long[] getBackingLongArray() {
        return this.longArray;
    }

    public int size() {
        return this.arraySize;
    }

    public void getAll(IntConsumer intConsumer) {
        int n = 0;
        for (long l : this.longArray) {
            for (int i = 0; i < this.field_232982_f_; ++i) {
                intConsumer.accept((int)(l & this.maxEntryValue));
                l >>= this.bitsPerEntry;
                if (++n < this.arraySize) continue;
                return;
            }
        }
    }
}

