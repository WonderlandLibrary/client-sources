/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.level.ColorResolver;

public class BiomeColors {
    public static final ColorResolver GRASS_COLOR = Biome::getGrassColor;
    public static final ColorResolver FOLIAGE_COLOR = BiomeColors::lambda$static$0;
    public static final ColorResolver WATER_COLOR = BiomeColors::lambda$static$1;

    private static int getBlockColor(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos, ColorResolver colorResolver) {
        return iBlockDisplayReader.getBlockColor(blockPos, colorResolver);
    }

    public static int getGrassColor(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        return BiomeColors.getBlockColor(iBlockDisplayReader, blockPos, GRASS_COLOR);
    }

    public static int getFoliageColor(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        return BiomeColors.getBlockColor(iBlockDisplayReader, blockPos, FOLIAGE_COLOR);
    }

    public static int getWaterColor(IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        return BiomeColors.getBlockColor(iBlockDisplayReader, blockPos, WATER_COLOR);
    }

    private static int lambda$static$1(Biome biome, double d, double d2) {
        return biome.getWaterColor();
    }

    private static int lambda$static$0(Biome biome, double d, double d2) {
        return biome.getFoliageColor();
    }
}

