/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class NetherMagma
extends Placement<NoPlacementConfig> {
    public NetherMagma(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, NoPlacementConfig noPlacementConfig, BlockPos blockPos) {
        int n = worldDecoratingHelper.func_242895_b();
        int n2 = n - 5 + random2.nextInt(10);
        return Stream.of(new BlockPos(blockPos.getX(), n2, blockPos.getZ()));
    }

    @Override
    public Stream func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.func_241857_a(worldDecoratingHelper, random2, (NoPlacementConfig)iPlacementConfig, blockPos);
    }
}

