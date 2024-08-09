/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;

public class OakTree
extends Tree {
    @Override
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random random2, boolean bl) {
        if (random2.nextInt(10) == 0) {
            return bl ? Features.FANCY_OAK_BEES_005 : Features.FANCY_OAK;
        }
        return bl ? Features.OAK_BEES_005 : Features.OAK;
    }
}

