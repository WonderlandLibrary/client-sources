/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class CaveWorldCarver
extends WorldCarver<ProbabilityConfig> {
    public CaveWorldCarver(Codec<ProbabilityConfig> codec, int n) {
        super(codec, n);
    }

    @Override
    public boolean shouldCarve(Random random2, int n, int n2, ProbabilityConfig probabilityConfig) {
        return random2.nextFloat() <= probabilityConfig.probability;
    }

    @Override
    public boolean carveRegion(IChunk iChunk, Function<BlockPos, Biome> function, Random random2, int n, int n2, int n3, int n4, int n5, BitSet bitSet, ProbabilityConfig probabilityConfig) {
        int n6 = (this.func_222704_c() * 2 - 1) * 16;
        int n7 = random2.nextInt(random2.nextInt(random2.nextInt(this.func_230357_a_()) + 1) + 1);
        for (int i = 0; i < n7; ++i) {
            float f;
            double d = n2 * 16 + random2.nextInt(16);
            double d2 = this.func_230361_b_(random2);
            double d3 = n3 * 16 + random2.nextInt(16);
            int n8 = 1;
            if (random2.nextInt(4) == 0) {
                double d4 = 0.5;
                f = 1.0f + random2.nextFloat() * 6.0f;
                this.func_227205_a_(iChunk, function, random2.nextLong(), n, n4, n5, d, d2, d3, f, 0.5, bitSet);
                n8 += random2.nextInt(4);
            }
            for (int j = 0; j < n8; ++j) {
                float f2 = random2.nextFloat() * ((float)Math.PI * 2);
                f = (random2.nextFloat() - 0.5f) / 4.0f;
                float f3 = this.func_230359_a_(random2);
                int n9 = n6 - random2.nextInt(n6 / 4);
                boolean bl = false;
                this.func_227206_a_(iChunk, function, random2.nextLong(), n, n4, n5, d, d2, d3, f3, f2, f, 0, n9, this.func_230360_b_(), bitSet);
            }
        }
        return false;
    }

    protected int func_230357_a_() {
        return 0;
    }

    protected float func_230359_a_(Random random2) {
        float f = random2.nextFloat() * 2.0f + random2.nextFloat();
        if (random2.nextInt(10) == 0) {
            f *= random2.nextFloat() * random2.nextFloat() * 3.0f + 1.0f;
        }
        return f;
    }

    protected double func_230360_b_() {
        return 1.0;
    }

    protected int func_230361_b_(Random random2) {
        return random2.nextInt(random2.nextInt(120) + 8);
    }

    protected void func_227205_a_(IChunk iChunk, Function<BlockPos, Biome> function, long l, int n, int n2, int n3, double d, double d2, double d3, float f, double d4, BitSet bitSet) {
        double d5 = 1.5 + (double)(MathHelper.sin(1.5707964f) * f);
        double d6 = d5 * d4;
        this.func_227208_a_(iChunk, function, l, n, n2, n3, d + 1.0, d2, d3, d5, d6, bitSet);
    }

    protected void func_227206_a_(IChunk iChunk, Function<BlockPos, Biome> function, long l, int n, int n2, int n3, double d, double d2, double d3, float f, float f2, float f3, int n4, int n5, double d4, BitSet bitSet) {
        Random random2 = new Random(l);
        int n6 = random2.nextInt(n5 / 2) + n5 / 4;
        boolean bl = random2.nextInt(6) == 0;
        float f4 = 0.0f;
        float f5 = 0.0f;
        for (int i = n4; i < n5; ++i) {
            double d5 = 1.5 + (double)(MathHelper.sin((float)Math.PI * (float)i / (float)n5) * f);
            double d6 = d5 * d4;
            float f6 = MathHelper.cos(f3);
            d += (double)(MathHelper.cos(f2) * f6);
            d2 += (double)MathHelper.sin(f3);
            d3 += (double)(MathHelper.sin(f2) * f6);
            f3 *= bl ? 0.92f : 0.7f;
            f3 += f5 * 0.1f;
            f2 += f4 * 0.1f;
            f5 *= 0.9f;
            f4 *= 0.75f;
            f5 += (random2.nextFloat() - random2.nextFloat()) * random2.nextFloat() * 2.0f;
            f4 += (random2.nextFloat() - random2.nextFloat()) * random2.nextFloat() * 4.0f;
            if (i == n6 && f > 1.0f) {
                this.func_227206_a_(iChunk, function, random2.nextLong(), n, n2, n3, d, d2, d3, random2.nextFloat() * 0.5f + 0.5f, f2 - 1.5707964f, f3 / 3.0f, i, n5, 1.0, bitSet);
                this.func_227206_a_(iChunk, function, random2.nextLong(), n, n2, n3, d, d2, d3, random2.nextFloat() * 0.5f + 0.5f, f2 + 1.5707964f, f3 / 3.0f, i, n5, 1.0, bitSet);
                return;
            }
            if (random2.nextInt(4) == 0) continue;
            if (!this.func_222702_a(n2, n3, d, d3, i, n5, f)) {
                return;
            }
            this.func_227208_a_(iChunk, function, l, n, n2, n3, d, d2, d3, d5, d6, bitSet);
        }
    }

    @Override
    protected boolean func_222708_a(double d, double d2, double d3, int n) {
        return d2 <= -0.7 || d * d + d2 * d2 + d3 * d3 >= 1.0;
    }

    @Override
    public boolean shouldCarve(Random random2, int n, int n2, ICarverConfig iCarverConfig) {
        return this.shouldCarve(random2, n, n2, (ProbabilityConfig)iCarverConfig);
    }

    @Override
    public boolean carveRegion(IChunk iChunk, Function function, Random random2, int n, int n2, int n3, int n4, int n5, BitSet bitSet, ICarverConfig iCarverConfig) {
        return this.carveRegion(iChunk, (Function<BlockPos, Biome>)function, random2, n, n2, n3, n4, n5, bitSet, (ProbabilityConfig)iCarverConfig);
    }
}

