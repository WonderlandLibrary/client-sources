/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class OreFeature
extends Feature<OreFeatureConfig> {
    public OreFeature(Codec<OreFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, OreFeatureConfig oreFeatureConfig) {
        float f = random2.nextFloat() * (float)Math.PI;
        float f2 = (float)oreFeatureConfig.size / 8.0f;
        int n = MathHelper.ceil(((float)oreFeatureConfig.size / 16.0f * 2.0f + 1.0f) / 2.0f);
        double d = (double)blockPos.getX() + Math.sin(f) * (double)f2;
        double d2 = (double)blockPos.getX() - Math.sin(f) * (double)f2;
        double d3 = (double)blockPos.getZ() + Math.cos(f) * (double)f2;
        double d4 = (double)blockPos.getZ() - Math.cos(f) * (double)f2;
        int n2 = 2;
        double d5 = blockPos.getY() + random2.nextInt(3) - 2;
        double d6 = blockPos.getY() + random2.nextInt(3) - 2;
        int n3 = blockPos.getX() - MathHelper.ceil(f2) - n;
        int n4 = blockPos.getY() - 2 - n;
        int n5 = blockPos.getZ() - MathHelper.ceil(f2) - n;
        int n6 = 2 * (MathHelper.ceil(f2) + n);
        int n7 = 2 * (2 + n);
        for (int i = n3; i <= n3 + n6; ++i) {
            for (int j = n5; j <= n5 + n6; ++j) {
                if (n4 > iSeedReader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, j)) continue;
                return this.func_207803_a(iSeedReader, random2, oreFeatureConfig, d, d2, d3, d4, d5, d6, n3, n4, n5, n6, n7);
            }
        }
        return true;
    }

    protected boolean func_207803_a(IWorld iWorld, Random random2, OreFeatureConfig oreFeatureConfig, double d, double d2, double d3, double d4, double d5, double d6, int n, int n2, int n3, int n4, int n5) {
        double d7;
        double d8;
        double d9;
        double d10;
        int n6;
        int n7 = 0;
        BitSet bitSet = new BitSet(n4 * n5 * n4);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int n8 = oreFeatureConfig.size;
        double[] dArray = new double[n8 * 4];
        for (n6 = 0; n6 < n8; ++n6) {
            float f = (float)n6 / (float)n8;
            d10 = MathHelper.lerp((double)f, d, d2);
            d9 = MathHelper.lerp((double)f, d5, d6);
            d8 = MathHelper.lerp((double)f, d3, d4);
            d7 = random2.nextDouble() * (double)n8 / 16.0;
            double d11 = ((double)(MathHelper.sin((float)Math.PI * f) + 1.0f) * d7 + 1.0) / 2.0;
            dArray[n6 * 4 + 0] = d10;
            dArray[n6 * 4 + 1] = d9;
            dArray[n6 * 4 + 2] = d8;
            dArray[n6 * 4 + 3] = d11;
        }
        for (n6 = 0; n6 < n8 - 1; ++n6) {
            if (dArray[n6 * 4 + 3] <= 0.0) continue;
            for (int i = n6 + 1; i < n8; ++i) {
                if (dArray[i * 4 + 3] <= 0.0 || !((d7 = dArray[n6 * 4 + 3] - dArray[i * 4 + 3]) * d7 > (d10 = dArray[n6 * 4 + 0] - dArray[i * 4 + 0]) * d10 + (d9 = dArray[n6 * 4 + 1] - dArray[i * 4 + 1]) * d9 + (d8 = dArray[n6 * 4 + 2] - dArray[i * 4 + 2]) * d8)) continue;
                if (d7 > 0.0) {
                    dArray[i * 4 + 3] = -1.0;
                    continue;
                }
                dArray[n6 * 4 + 3] = -1.0;
            }
        }
        for (n6 = 0; n6 < n8; ++n6) {
            double d12 = dArray[n6 * 4 + 3];
            if (d12 < 0.0) continue;
            double d13 = dArray[n6 * 4 + 0];
            double d14 = dArray[n6 * 4 + 1];
            double d15 = dArray[n6 * 4 + 2];
            int n9 = Math.max(MathHelper.floor(d13 - d12), n);
            int n10 = Math.max(MathHelper.floor(d14 - d12), n2);
            int n11 = Math.max(MathHelper.floor(d15 - d12), n3);
            int n12 = Math.max(MathHelper.floor(d13 + d12), n9);
            int n13 = Math.max(MathHelper.floor(d14 + d12), n10);
            int n14 = Math.max(MathHelper.floor(d15 + d12), n11);
            for (int i = n9; i <= n12; ++i) {
                double d16 = ((double)i + 0.5 - d13) / d12;
                if (!(d16 * d16 < 1.0)) continue;
                for (int j = n10; j <= n13; ++j) {
                    double d17 = ((double)j + 0.5 - d14) / d12;
                    if (!(d16 * d16 + d17 * d17 < 1.0)) continue;
                    for (int k = n11; k <= n14; ++k) {
                        int n15;
                        double d18 = ((double)k + 0.5 - d15) / d12;
                        if (!(d16 * d16 + d17 * d17 + d18 * d18 < 1.0) || bitSet.get(n15 = i - n + (j - n2) * n4 + (k - n3) * n4 * n5)) continue;
                        bitSet.set(n15);
                        mutable.setPos(i, j, k);
                        if (!oreFeatureConfig.target.test(iWorld.getBlockState(mutable), random2)) continue;
                        iWorld.setBlockState(mutable, oreFeatureConfig.state, 2);
                        ++n7;
                    }
                }
            }
        }
        return n7 > 0;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (OreFeatureConfig)iFeatureConfig);
    }
}

