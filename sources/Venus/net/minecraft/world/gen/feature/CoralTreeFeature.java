/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.CoralFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class CoralTreeFeature
extends CoralFeature {
    public CoralTreeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean func_204623_a(IWorld iWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        BlockPos.Mutable mutable = blockPos.toMutable();
        int n = random2.nextInt(3) + 1;
        for (int i = 0; i < n; ++i) {
            if (!this.func_204624_b(iWorld, random2, mutable, blockState)) {
                return false;
            }
            mutable.move(Direction.UP);
        }
        BlockPos blockPos2 = mutable.toImmutable();
        int n2 = random2.nextInt(3) + 2;
        ArrayList<Direction> arrayList = Lists.newArrayList(Direction.Plane.HORIZONTAL);
        Collections.shuffle(arrayList, random2);
        for (Direction direction : arrayList.subList(0, n2)) {
            mutable.setPos(blockPos2);
            mutable.move(direction);
            int n3 = random2.nextInt(5) + 2;
            int n4 = 0;
            for (int i = 0; i < n3 && this.func_204624_b(iWorld, random2, mutable, blockState); ++i) {
                mutable.move(Direction.UP);
                if (i != 0 && (++n4 < 2 || !(random2.nextFloat() < 0.25f))) continue;
                mutable.move(direction);
                n4 = 0;
            }
        }
        return false;
    }
}

