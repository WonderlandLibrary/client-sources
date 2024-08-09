/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.CoralFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class CoralMushroomFeature
extends CoralFeature {
    public CoralMushroomFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean func_204623_a(IWorld iWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        int n = random2.nextInt(3) + 3;
        int n2 = random2.nextInt(3) + 3;
        int n3 = random2.nextInt(3) + 3;
        int n4 = random2.nextInt(3) + 1;
        BlockPos.Mutable mutable = blockPos.toMutable();
        for (int i = 0; i <= n2; ++i) {
            for (int j = 0; j <= n; ++j) {
                for (int k = 0; k <= n3; ++k) {
                    mutable.setPos(i + blockPos.getX(), j + blockPos.getY(), k + blockPos.getZ());
                    mutable.move(Direction.DOWN, n4);
                    if ((i != 0 && i != n2 || j != 0 && j != n) && (k != 0 && k != n3 || j != 0 && j != n) && (i != 0 && i != n2 || k != 0 && k != n3) && (i == 0 || i == n2 || j == 0 || j == n || k == 0 || k == n3) && !(random2.nextFloat() < 0.1f) && this.func_204624_b(iWorld, random2, mutable, blockState)) continue;
                }
            }
        }
        return false;
    }
}

