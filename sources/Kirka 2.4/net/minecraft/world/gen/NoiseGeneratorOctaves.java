/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorImproved;

public class NoiseGeneratorOctaves
extends NoiseGenerator {
    private NoiseGeneratorImproved[] generatorCollection;
    private int octaves;
    private static final String __OBFID = "CL_00000535";

    public NoiseGeneratorOctaves(Random p_i2111_1_, int p_i2111_2_) {
        this.octaves = p_i2111_2_;
        this.generatorCollection = new NoiseGeneratorImproved[p_i2111_2_];
        for (int var3 = 0; var3 < p_i2111_2_; ++var3) {
            this.generatorCollection[var3] = new NoiseGeneratorImproved(p_i2111_1_);
        }
    }

    public double[] generateNoiseOctaves(double[] p_76304_1_, int p_76304_2_, int p_76304_3_, int p_76304_4_, int p_76304_5_, int p_76304_6_, int p_76304_7_, double p_76304_8_, double p_76304_10_, double p_76304_12_) {
        if (p_76304_1_ == null) {
            p_76304_1_ = new double[p_76304_5_ * p_76304_6_ * p_76304_7_];
        } else {
            for (int var14 = 0; var14 < p_76304_1_.length; ++var14) {
                p_76304_1_[var14] = 0.0;
            }
        }
        double var27 = 1.0;
        for (int var16 = 0; var16 < this.octaves; ++var16) {
            double var17 = (double)p_76304_2_ * var27 * p_76304_8_;
            double var19 = (double)p_76304_3_ * var27 * p_76304_10_;
            double var21 = (double)p_76304_4_ * var27 * p_76304_12_;
            long var23 = MathHelper.floor_double_long(var17);
            long var25 = MathHelper.floor_double_long(var21);
            var17 -= (double)var23;
            var21 -= (double)var25;
            this.generatorCollection[var16].populateNoiseArray(p_76304_1_, var17 += (double)(var23 %= 0x1000000L), var19, var21 += (double)(var25 %= 0x1000000L), p_76304_5_, p_76304_6_, p_76304_7_, p_76304_8_ * var27, p_76304_10_ * var27, p_76304_12_ * var27, var27);
            var27 /= 2.0;
        }
        return p_76304_1_;
    }

    public double[] generateNoiseOctaves(double[] p_76305_1_, int p_76305_2_, int p_76305_3_, int p_76305_4_, int p_76305_5_, double p_76305_6_, double p_76305_8_, double p_76305_10_) {
        return this.generateNoiseOctaves(p_76305_1_, p_76305_2_, 10, p_76305_3_, p_76305_4_, 1, p_76305_5_, p_76305_6_, 1.0, p_76305_8_);
    }
}

