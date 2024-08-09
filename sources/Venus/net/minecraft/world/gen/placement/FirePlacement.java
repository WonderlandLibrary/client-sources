/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

public class FirePlacement
extends SimplePlacement<FeatureSpreadConfig> {
    public FirePlacement(Codec<FeatureSpreadConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(Random random2, FeatureSpreadConfig featureSpreadConfig, BlockPos blockPos) {
        ArrayList<BlockPos> arrayList = Lists.newArrayList();
        for (int i = 0; i < random2.nextInt(random2.nextInt(featureSpreadConfig.func_242799_a().func_242259_a(random2)) + 1) + 1; ++i) {
            int n = random2.nextInt(16) + blockPos.getX();
            int n2 = random2.nextInt(16) + blockPos.getZ();
            int n3 = random2.nextInt(120) + 4;
            arrayList.add(new BlockPos(n, n3, n2));
        }
        return arrayList.stream();
    }

    @Override
    public Stream getPositions(Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.getPositions(random2, (FeatureSpreadConfig)iPlacementConfig, blockPos);
    }
}

