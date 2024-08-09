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
import net.minecraft.world.gen.placement.Placement;

public abstract class SimplePlacement<DC extends IPlacementConfig>
extends Placement<DC> {
    public SimplePlacement(Codec<DC> codec) {
        super(codec);
    }

    @Override
    public final Stream<BlockPos> func_241857_a(WorldDecoratingHelper worldDecoratingHelper, Random random2, DC DC, BlockPos blockPos) {
        return this.getPositions(random2, DC, blockPos);
    }

    protected abstract Stream<BlockPos> getPositions(Random var1, DC var2, BlockPos var3);
}

