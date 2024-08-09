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
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.CoralFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class CoralClawFeature
extends CoralFeature {
    public CoralClawFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean func_204623_a(IWorld iWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        if (!this.func_204624_b(iWorld, random2, blockPos, blockState)) {
            return true;
        }
        Direction direction = Direction.Plane.HORIZONTAL.random(random2);
        int n = random2.nextInt(2) + 2;
        ArrayList<Direction> arrayList = Lists.newArrayList(direction, direction.rotateY(), direction.rotateYCCW());
        Collections.shuffle(arrayList, random2);
        block0: for (Direction direction2 : arrayList.subList(0, n)) {
            int n2;
            int n3;
            Direction direction3;
            BlockPos.Mutable mutable = blockPos.toMutable();
            int n4 = random2.nextInt(2) + 1;
            mutable.move(direction2);
            if (direction2 == direction) {
                direction3 = direction;
                n3 = random2.nextInt(3) + 2;
            } else {
                mutable.move(Direction.UP);
                Direction[] directionArray = new Direction[]{direction2, Direction.UP};
                direction3 = Util.getRandomObject(directionArray, random2);
                n3 = random2.nextInt(3) + 3;
            }
            for (n2 = 0; n2 < n4 && this.func_204624_b(iWorld, random2, mutable, blockState); ++n2) {
                mutable.move(direction3);
            }
            mutable.move(direction3.getOpposite());
            mutable.move(Direction.UP);
            for (n2 = 0; n2 < n3; ++n2) {
                mutable.move(direction);
                if (!this.func_204624_b(iWorld, random2, mutable, blockState)) continue block0;
                if (!(random2.nextFloat() < 0.25f)) continue;
                mutable.move(Direction.UP);
            }
        }
        return false;
    }
}

