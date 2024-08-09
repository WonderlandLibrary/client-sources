/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class LakesFeature
extends Feature<BlockStateFeatureConfig> {
    private static final BlockState AIR = Blocks.CAVE_AIR.getDefaultState();

    public LakesFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, BlockStateFeatureConfig blockStateFeatureConfig) {
        Object object;
        int n;
        int n2;
        while (blockPos.getY() > 5 && iSeedReader.isAirBlock(blockPos)) {
            blockPos = blockPos.down();
        }
        if (blockPos.getY() <= 4) {
            return true;
        }
        if (iSeedReader.func_241827_a(SectionPos.from(blockPos = blockPos.down(4)), Structure.field_236381_q_).findAny().isPresent()) {
            return true;
        }
        boolean[] blArray = new boolean[2048];
        int n3 = random2.nextInt(4) + 4;
        for (n2 = 0; n2 < n3; ++n2) {
            double d = random2.nextDouble() * 6.0 + 3.0;
            double d2 = random2.nextDouble() * 4.0 + 2.0;
            double d3 = random2.nextDouble() * 6.0 + 3.0;
            double d4 = random2.nextDouble() * (16.0 - d - 2.0) + 1.0 + d / 2.0;
            double d5 = random2.nextDouble() * (8.0 - d2 - 4.0) + 2.0 + d2 / 2.0;
            double d6 = random2.nextDouble() * (16.0 - d3 - 2.0) + 1.0 + d3 / 2.0;
            for (int i = 1; i < 15; ++i) {
                for (int j = 1; j < 15; ++j) {
                    for (int k = 1; k < 7; ++k) {
                        double d7 = ((double)i - d4) / (d / 2.0);
                        double d8 = ((double)k - d5) / (d2 / 2.0);
                        double d9 = ((double)j - d6) / (d3 / 2.0);
                        double d10 = d7 * d7 + d8 * d8 + d9 * d9;
                        if (!(d10 < 1.0)) continue;
                        blArray[(i * 16 + j) * 8 + k] = true;
                    }
                }
            }
        }
        for (n2 = 0; n2 < 16; ++n2) {
            for (int i = 0; i < 16; ++i) {
                for (n = 0; n < 8; ++n) {
                    boolean bl;
                    boolean bl2 = bl = !blArray[(n2 * 16 + i) * 8 + n] && (n2 < 15 && blArray[((n2 + 1) * 16 + i) * 8 + n] || n2 > 0 && blArray[((n2 - 1) * 16 + i) * 8 + n] || i < 15 && blArray[(n2 * 16 + i + 1) * 8 + n] || i > 0 && blArray[(n2 * 16 + (i - 1)) * 8 + n] || n < 7 && blArray[(n2 * 16 + i) * 8 + n + 1] || n > 0 && blArray[(n2 * 16 + i) * 8 + (n - 1)]);
                    if (!bl) continue;
                    object = iSeedReader.getBlockState(blockPos.add(n2, n, i)).getMaterial();
                    if (n >= 4 && ((Material)object).isLiquid()) {
                        return true;
                    }
                    if (n >= 4 || ((Material)object).isSolid() || iSeedReader.getBlockState(blockPos.add(n2, n, i)) == blockStateFeatureConfig.state) continue;
                    return true;
                }
            }
        }
        for (n2 = 0; n2 < 16; ++n2) {
            for (int i = 0; i < 16; ++i) {
                for (n = 0; n < 8; ++n) {
                    if (!blArray[(n2 * 16 + i) * 8 + n]) continue;
                    iSeedReader.setBlockState(blockPos.add(n2, n, i), n >= 4 ? AIR : blockStateFeatureConfig.state, 2);
                }
            }
        }
        for (n2 = 0; n2 < 16; ++n2) {
            for (int i = 0; i < 16; ++i) {
                for (n = 4; n < 8; ++n) {
                    BlockPos blockPos2;
                    if (!blArray[(n2 * 16 + i) * 8 + n] || !LakesFeature.isDirt(iSeedReader.getBlockState(blockPos2 = blockPos.add(n2, n - 1, i)).getBlock()) || iSeedReader.getLightFor(LightType.SKY, blockPos.add(n2, n, i)) <= 0) continue;
                    object = iSeedReader.getBiome(blockPos2);
                    if (((Biome)object).getGenerationSettings().getSurfaceBuilderConfig().getTop().isIn(Blocks.MYCELIUM)) {
                        iSeedReader.setBlockState(blockPos2, Blocks.MYCELIUM.getDefaultState(), 2);
                        continue;
                    }
                    iSeedReader.setBlockState(blockPos2, Blocks.GRASS_BLOCK.getDefaultState(), 2);
                }
            }
        }
        if (blockStateFeatureConfig.state.getMaterial() == Material.LAVA) {
            for (n2 = 0; n2 < 16; ++n2) {
                for (int i = 0; i < 16; ++i) {
                    for (n = 0; n < 8; ++n) {
                        boolean bl;
                        boolean bl3 = bl = !blArray[(n2 * 16 + i) * 8 + n] && (n2 < 15 && blArray[((n2 + 1) * 16 + i) * 8 + n] || n2 > 0 && blArray[((n2 - 1) * 16 + i) * 8 + n] || i < 15 && blArray[(n2 * 16 + i + 1) * 8 + n] || i > 0 && blArray[(n2 * 16 + (i - 1)) * 8 + n] || n < 7 && blArray[(n2 * 16 + i) * 8 + n + 1] || n > 0 && blArray[(n2 * 16 + i) * 8 + (n - 1)]);
                        if (!bl || n >= 4 && random2.nextInt(2) == 0 || !iSeedReader.getBlockState(blockPos.add(n2, n, i)).getMaterial().isSolid()) continue;
                        iSeedReader.setBlockState(blockPos.add(n2, n, i), Blocks.STONE.getDefaultState(), 2);
                    }
                }
            }
        }
        if (blockStateFeatureConfig.state.getMaterial() == Material.WATER) {
            for (n2 = 0; n2 < 16; ++n2) {
                for (int i = 0; i < 16; ++i) {
                    n = 4;
                    BlockPos blockPos3 = blockPos.add(n2, 4, i);
                    if (!iSeedReader.getBiome(blockPos3).doesWaterFreeze(iSeedReader, blockPos3, true)) continue;
                    iSeedReader.setBlockState(blockPos3, Blocks.ICE.getDefaultState(), 2);
                }
            }
        }
        return false;
    }

    @Override
    public boolean func_241855_a(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, IFeatureConfig iFeatureConfig) {
        return this.func_241855_a(iSeedReader, chunkGenerator, random2, blockPos, (BlockStateFeatureConfig)iFeatureConfig);
    }
}

