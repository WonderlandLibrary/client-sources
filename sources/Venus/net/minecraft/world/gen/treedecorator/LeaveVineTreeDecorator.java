/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.VineBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class LeaveVineTreeDecorator
extends TreeDecorator {
    public static final Codec<LeaveVineTreeDecorator> field_236870_a_;
    public static final LeaveVineTreeDecorator field_236871_b_;

    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return TreeDecoratorType.LEAVE_VINE;
    }

    @Override
    public void func_225576_a_(ISeedReader iSeedReader, Random random2, List<BlockPos> list, List<BlockPos> list2, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox) {
        list2.forEach(arg_0 -> this.lambda$func_225576_a_$0(random2, iSeedReader, set, mutableBoundingBox, arg_0));
    }

    private void func_227420_a_(IWorldGenerationReader iWorldGenerationReader, BlockPos blockPos, BooleanProperty booleanProperty, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox) {
        this.func_227424_a_(iWorldGenerationReader, blockPos, booleanProperty, set, mutableBoundingBox);
        BlockPos blockPos2 = blockPos.down();
        for (int i = 4; Feature.isAirAt(iWorldGenerationReader, blockPos2) && i > 0; --i) {
            this.func_227424_a_(iWorldGenerationReader, blockPos2, booleanProperty, set, mutableBoundingBox);
            blockPos2 = blockPos2.down();
        }
    }

    private static LeaveVineTreeDecorator lambda$static$1() {
        return field_236871_b_;
    }

    private void lambda$func_225576_a_$0(Random random2, ISeedReader iSeedReader, Set set, MutableBoundingBox mutableBoundingBox, BlockPos blockPos) {
        BlockPos blockPos2;
        if (random2.nextInt(4) == 0 && Feature.isAirAt(iSeedReader, blockPos2 = blockPos.west())) {
            this.func_227420_a_(iSeedReader, blockPos2, VineBlock.EAST, set, mutableBoundingBox);
        }
        if (random2.nextInt(4) == 0 && Feature.isAirAt(iSeedReader, blockPos2 = blockPos.east())) {
            this.func_227420_a_(iSeedReader, blockPos2, VineBlock.WEST, set, mutableBoundingBox);
        }
        if (random2.nextInt(4) == 0 && Feature.isAirAt(iSeedReader, blockPos2 = blockPos.north())) {
            this.func_227420_a_(iSeedReader, blockPos2, VineBlock.SOUTH, set, mutableBoundingBox);
        }
        if (random2.nextInt(4) == 0 && Feature.isAirAt(iSeedReader, blockPos2 = blockPos.south())) {
            this.func_227420_a_(iSeedReader, blockPos2, VineBlock.NORTH, set, mutableBoundingBox);
        }
    }

    static {
        field_236871_b_ = new LeaveVineTreeDecorator();
        field_236870_a_ = Codec.unit(LeaveVineTreeDecorator::lambda$static$1);
    }
}

