/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.DecoratedPlacementConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class DecoratedPlacement
extends Placement<DecoratedPlacementConfig> {
    public DecoratedPlacement(Codec<DecoratedPlacementConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, DecoratedPlacementConfig decoratedPlacementConfig, BlockPos blockPos) {
        return decoratedPlacementConfig.func_242886_a().func_242876_a(worldDecoratingHelper, random2, blockPos).flatMap(arg_0 -> DecoratedPlacement.lambda$func_241857_a$0(decoratedPlacementConfig, worldDecoratingHelper, random2, arg_0));
    }

    @Override
    public Stream func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.func_241857_a(worldDecoratingHelper, random2, (DecoratedPlacementConfig)iPlacementConfig, blockPos);
    }

    private static Stream lambda$func_241857_a$0(DecoratedPlacementConfig decoratedPlacementConfig, WorldDecoratingHelper worldDecoratingHelper, Random random2, BlockPos blockPos) {
        return decoratedPlacementConfig.func_242888_b().func_242876_a(worldDecoratingHelper, random2, blockPos);
    }
}

