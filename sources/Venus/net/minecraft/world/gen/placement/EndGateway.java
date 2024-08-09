/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class EndGateway
extends Placement<NoPlacementConfig> {
    public EndGateway(Codec<NoPlacementConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, NoPlacementConfig noPlacementConfig, BlockPos blockPos) {
        int n;
        int n2;
        int n3;
        if (random2.nextInt(700) == 0 && (n3 = worldDecoratingHelper.func_242893_a(Heightmap.Type.MOTION_BLOCKING, n2 = random2.nextInt(16) + blockPos.getX(), n = random2.nextInt(16) + blockPos.getZ())) > 0) {
            int n4 = n3 + 3 + random2.nextInt(7);
            return Stream.of(new BlockPos(n2, n4, n));
        }
        return Stream.empty();
    }

    @Override
    public Stream func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.func_241857_a(worldDecoratingHelper, random2, (NoPlacementConfig)iPlacementConfig, blockPos);
    }
}

