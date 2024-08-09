/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;

public class AbstractSphereReplaceConfig
extends Feature<SphereReplaceConfig> {
    public AbstractSphereReplaceConfig(Codec<SphereReplaceConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, SphereReplaceConfig sphereReplaceConfig) {
        boolean bl = false;
        int n = sphereReplaceConfig.radius.func_242259_a(random2);
        for (int i = blockPos.getX() - n; i <= blockPos.getX() + n; ++i) {
            for (int j = blockPos.getZ() - n; j <= blockPos.getZ() + n; ++j) {
                int n2;
                int n3 = i - blockPos.getX();
                if (n3 * n3 + (n2 = j - blockPos.getZ()) * n2 > n * n) continue;
                block2: for (int k = blockPos.getY() - sphereReplaceConfig.field_242809_d; k <= blockPos.getY() + sphereReplaceConfig.field_242809_d; ++k) {
                    BlockPos blockPos2 = new BlockPos(i, k, j);
                    Block block = iSeedReader.getBlockState(blockPos2).getBlock();
                    for (BlockState blockState : sphereReplaceConfig.targets) {
                        if (!blockState.isIn(block)) continue;
                        iSeedReader.setBlockState(blockPos2, sphereReplaceConfig.state, 2);
                        bl = true;
                        continue block2;
                    }
                }
            }
        }
        return bl;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (SphereReplaceConfig)iFeatureConfig);
    }
}

