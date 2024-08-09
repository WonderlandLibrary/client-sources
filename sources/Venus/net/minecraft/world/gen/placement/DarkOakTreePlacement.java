/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.HeightmapBasedPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;

public class DarkOakTreePlacement
extends HeightmapBasedPlacement<NoPlacementConfig> {
    public DarkOakTreePlacement(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    @Override
    protected Heightmap.Type func_241858_a(NoPlacementConfig noPlacementConfig) {
        return Heightmap.Type.MOTION_BLOCKING;
    }

    @Override
    public Stream<BlockPos> func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, NoPlacementConfig noPlacementConfig, BlockPos blockPos) {
        return IntStream.range(0, 16).mapToObj(arg_0 -> this.lambda$func_241857_a$0(random2, blockPos, worldDecoratingHelper, noPlacementConfig, arg_0));
    }

    @Override
    protected Heightmap.Type func_241858_a(IPlacementConfig iPlacementConfig) {
        return this.func_241858_a((NoPlacementConfig)iPlacementConfig);
    }

    @Override
    public Stream func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.func_241857_a(worldDecoratingHelper, random2, (NoPlacementConfig)iPlacementConfig, blockPos);
    }

    private BlockPos lambda$func_241857_a$0(Random random2, BlockPos blockPos, WorldDecoratingHelper worldDecoratingHelper, NoPlacementConfig noPlacementConfig, int n) {
        int n2 = n / 4;
        int n3 = n % 4;
        int n4 = n2 * 4 + 1 + random2.nextInt(3) + blockPos.getX();
        int n5 = n3 * 4 + 1 + random2.nextInt(3) + blockPos.getZ();
        int n6 = worldDecoratingHelper.func_242893_a(this.func_241858_a(noPlacementConfig), n4, n5);
        return new BlockPos(n4, n6, n5);
    }
}

