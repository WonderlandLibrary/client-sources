/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class CountMultilayerPlacement
extends Placement<FeatureSpreadConfig> {
    public CountMultilayerPlacement(Codec<FeatureSpreadConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, FeatureSpreadConfig featureSpreadConfig, BlockPos blockPos) {
        boolean bl;
        ArrayList<BlockPos> arrayList = Lists.newArrayList();
        int n = 0;
        do {
            bl = false;
            for (int i = 0; i < featureSpreadConfig.func_242799_a().func_242259_a(random2); ++i) {
                int n2;
                int n3;
                int n4 = random2.nextInt(16) + blockPos.getX();
                int n5 = CountMultilayerPlacement.func_242915_a(worldDecoratingHelper, n4, n3 = worldDecoratingHelper.func_242893_a(Heightmap.Type.MOTION_BLOCKING, n4, n2 = random2.nextInt(16) + blockPos.getZ()), n2, n);
                if (n5 == Integer.MAX_VALUE) continue;
                arrayList.add(new BlockPos(n4, n5, n2));
                bl = true;
            }
            ++n;
        } while (bl);
        return arrayList.stream();
    }

    private static int func_242915_a(WorldDecoratingHelper worldDecoratingHelper, int n, int n2, int n3, int n4) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(n, n2, n3);
        int n5 = 0;
        BlockState blockState = worldDecoratingHelper.func_242894_a(mutable);
        for (int i = n2; i >= 1; --i) {
            mutable.setY(i - 1);
            BlockState blockState2 = worldDecoratingHelper.func_242894_a(mutable);
            if (!CountMultilayerPlacement.func_242914_a(blockState2) && CountMultilayerPlacement.func_242914_a(blockState) && !blockState2.isIn(Blocks.BEDROCK)) {
                if (n5 == n4) {
                    return mutable.getY() + 1;
                }
                ++n5;
            }
            blockState = blockState2;
        }
        return 0;
    }

    private static boolean func_242914_a(BlockState blockState) {
        return blockState.isAir() || blockState.isIn(Blocks.WATER) || blockState.isIn(Blocks.LAVA);
    }

    @Override
    public Stream func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.func_241857_a(worldDecoratingHelper, random2, (FeatureSpreadConfig)iPlacementConfig, blockPos);
    }
}

