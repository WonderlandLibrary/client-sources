/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.Arrays;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix3f;

public enum TriplePermutation {
    P123(0, 1, 2),
    P213(1, 0, 2),
    P132(0, 2, 1),
    P231(1, 2, 0),
    P312(2, 0, 1),
    P321(2, 1, 0);

    private final int[] field_239183_g_;
    private final Matrix3f field_239184_h_;
    private static final TriplePermutation[][] field_239185_i_;

    private TriplePermutation(int n2, int n3, int n4) {
        this.field_239183_g_ = new int[]{n2, n3, n4};
        this.field_239184_h_ = new Matrix3f();
        this.field_239184_h_.func_232605_a_(0, this.func_239187_a_(0), 1.0f);
        this.field_239184_h_.func_232605_a_(1, this.func_239187_a_(1), 1.0f);
        this.field_239184_h_.func_232605_a_(2, this.func_239187_a_(2), 1.0f);
    }

    public TriplePermutation func_239188_a_(TriplePermutation triplePermutation) {
        return field_239185_i_[this.ordinal()][triplePermutation.ordinal()];
    }

    public int func_239187_a_(int n) {
        return this.field_239183_g_[n];
    }

    public Matrix3f func_239186_a_() {
        return this.field_239184_h_;
    }

    private static void lambda$static$1(TriplePermutation[][] triplePermutationArray) {
        for (TriplePermutation triplePermutation : TriplePermutation.values()) {
            for (TriplePermutation triplePermutation2 : TriplePermutation.values()) {
                TriplePermutation triplePermutation3;
                int[] nArray = new int[3];
                for (int i = 0; i < 3; ++i) {
                    nArray[i] = triplePermutation.field_239183_g_[triplePermutation2.field_239183_g_[i]];
                }
                triplePermutationArray[triplePermutation.ordinal()][triplePermutation2.ordinal()] = triplePermutation3 = Arrays.stream(TriplePermutation.values()).filter(arg_0 -> TriplePermutation.lambda$static$0(nArray, arg_0)).findFirst().get();
            }
        }
    }

    private static boolean lambda$static$0(int[] nArray, TriplePermutation triplePermutation) {
        return Arrays.equals(triplePermutation.field_239183_g_, nArray);
    }

    static {
        field_239185_i_ = Util.make(new TriplePermutation[TriplePermutation.values().length][TriplePermutation.values().length], TriplePermutation::lambda$static$1);
    }
}

