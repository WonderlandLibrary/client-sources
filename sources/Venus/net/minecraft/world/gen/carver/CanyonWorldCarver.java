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

public class CanyonWorldCarver
extends WorldCarver<ProbabilityConfig> {
    private final float[] field_202536_i = new float[1024];

    public CanyonWorldCarver(Codec<ProbabilityConfig> codec) {
        super(codec, 256);
    }

    @Override
    public boolean shouldCarve(Random random2, int n, int n2, ProbabilityConfig probabilityConfig) {
        return random2.nextFloat() <= probabilityConfig.probability;
    }

    @Override
    public boolean carveRegion(IChunk iChunk, Function<BlockPos, Biome> function, Random random2, int n, int n2, int n3, int n4, int n5, BitSet bitSet, ProbabilityConfig probabilityConfig) {
        int n6 = (this.func_222704_c() * 2 - 1) * 16;
        double d = n2 * 16 + random2.nextInt(16);
        double d2 = random2.nextInt(random2.nextInt(40) + 8) + 20;
        double d3 = n3 * 16 + random2.nextInt(16);
        float f = random2.nextFloat() * ((float)Math.PI * 2);
        float f2 = (random2.nextFloat() - 0.5f) * 2.0f / 8.0f;
        double d4 = 3.0;
        float f3 = (random2.nextFloat() * 2.0f + random2.nextFloat()) * 2.0f;
        int n7 = n6 - random2.nextInt(n6 / 4);
        boolean bl = false;
        this.func_227204_a_(iChunk, function, random2.nextLong(), n, n4, n5, d, d2, d3, f3, f, f2, 0, n7, 3.0, bitSet);
        return false;
    }

    private void func_227204_a_(IChunk iChunk, Function<BlockPos, Biome> function, long l, int n, int n2, int n3, double d, double d2, double d3, float f, float f2, float f3, int n4, int n5, double d4, BitSet bitSet) {
        Random random2 = new Random(l);
        float f4 = 1.0f;
        for (int i = 0; i < 256; ++i) {
            if (i == 0 || random2.nextInt(3) == 0) {
                f4 = 1.0f + random2.nextFloat() * random2.nextFloat();
            }
            this.field_202536_i[i] = f4 * f4;
        }
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (int i = n4; i < n5; ++i) {
            double d5 = 1.5 + (double)(MathHelper.sin((float)i * (float)Math.PI / (float)n5) * f);
            double d6 = d5 * d4;
            d5 *= (double)random2.nextFloat() * 0.25 + 0.75;
            d6 *= (double)random2.nextFloat() * 0.25 + 0.75;
            float f7 = MathHelper.cos(f3);
            float f8 = MathHelper.sin(f3);
            d += (double)(MathHelper.cos(f2) * f7);
            d2 += (double)f8;
            d3 += (double)(MathHelper.sin(f2) * f7);
            f3 *= 0.7f;
            f3 += f6 * 0.05f;
            f2 += f5 * 0.05f;
            f6 *= 0.8f;
            f5 *= 0.5f;
            f6 += (random2.nextFloat() - random2.nextFloat()) * random2.nextFloat() * 2.0f;
            f5 += (random2.nextFloat() - random2.nextFloat()) * random2.nextFloat() * 4.0f;
            if (random2.nextInt(4) == 0) continue;
            if (!this.func_222702_a(n2, n3, d, d3, i, n5, f)) {
                return;
            }
            this.func_227208_a_(iChunk, function, l, n, n2, n3, d, d2, d3, d5, d6, bitSet);
        }
    }

    @Override
    protected boolean func_222708_a(double d, double d2, double d3, int n) {
        return (d * d + d3 * d3) * (double)this.field_202536_i[n - 1] + d2 * d2 / 6.0 >= 1.0;
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

