/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class CocoaTreeDecorator
extends TreeDecorator {
    public static final Codec<CocoaTreeDecorator> field_236866_a_ = ((MapCodec)Codec.floatRange(0.0f, 1.0f).fieldOf("probability")).xmap(CocoaTreeDecorator::new, CocoaTreeDecorator::lambda$static$0).codec();
    private final float field_227417_b_;

    public CocoaTreeDecorator(float f) {
        this.field_227417_b_ = f;
    }

    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return TreeDecoratorType.COCOA;
    }

    @Override
    public void func_225576_a_(ISeedReader iSeedReader, Random random2, List<BlockPos> list, List<BlockPos> list2, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox) {
        if (!(random2.nextFloat() >= this.field_227417_b_)) {
            int n = list.get(0).getY();
            list.stream().filter(arg_0 -> CocoaTreeDecorator.lambda$func_225576_a_$1(n, arg_0)).forEach(arg_0 -> this.lambda$func_225576_a_$2(random2, iSeedReader, set, mutableBoundingBox, arg_0));
        }
    }

    private void lambda$func_225576_a_$2(Random random2, ISeedReader iSeedReader, Set set, MutableBoundingBox mutableBoundingBox, BlockPos blockPos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            Direction direction2;
            BlockPos blockPos2;
            if (!(random2.nextFloat() <= 0.25f) || !Feature.isAirAt(iSeedReader, blockPos2 = blockPos.add((direction2 = direction.getOpposite()).getXOffset(), 0, direction2.getZOffset()))) continue;
            BlockState blockState = (BlockState)((BlockState)Blocks.COCOA.getDefaultState().with(CocoaBlock.AGE, random2.nextInt(3))).with(CocoaBlock.HORIZONTAL_FACING, direction);
            this.func_227423_a_(iSeedReader, blockPos2, blockState, set, mutableBoundingBox);
        }
    }

    private static boolean lambda$func_225576_a_$1(int n, BlockPos blockPos) {
        return blockPos.getY() - n <= 2;
    }

    private static Float lambda$static$0(CocoaTreeDecorator cocoaTreeDecorator) {
        return Float.valueOf(cocoaTreeDecorator.field_227417_b_);
    }
}

