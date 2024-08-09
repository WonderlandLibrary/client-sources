/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class LakeLava
extends Placement<ChanceConfig> {
    public LakeLava(Codec<ChanceConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, ChanceConfig chanceConfig, BlockPos blockPos) {
        if (random2.nextInt(chanceConfig.chance / 10) == 0) {
            int n = random2.nextInt(16) + blockPos.getX();
            int n2 = random2.nextInt(16) + blockPos.getZ();
            int n3 = random2.nextInt(random2.nextInt(worldDecoratingHelper.func_242891_a() - 8) + 8);
            if (n3 < worldDecoratingHelper.func_242895_b() || random2.nextInt(chanceConfig.chance / 8) == 0) {
                return Stream.of(new BlockPos(n, n3, n2));
            }
        }
        return Stream.empty();
    }

    @Override
    public Stream func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, IPlacementConfig iPlacementConfig, BlockPos blockPos) {
        return this.func_241857_a(worldDecoratingHelper, random2, (ChanceConfig)iPlacementConfig, blockPos);
    }
}

