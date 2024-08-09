package net.minecraft.block.trees;

import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;

import javax.annotation.Nullable;
import java.util.Random;

public class JungleTree extends BigTree
{
    @Nullable
    protected ConfiguredFeature < BaseTreeFeatureConfig, ? > getTreeFeature(Random randomIn, boolean largeHive)
    {
        return Features.JUNGLE_TREE_NO_VINE;
    }

    @Nullable
    protected ConfiguredFeature < BaseTreeFeatureConfig, ? > getHugeTreeFeature(Random rand)
    {
        return Features.MEGA_JUNGLE_TREE;
    }
}
