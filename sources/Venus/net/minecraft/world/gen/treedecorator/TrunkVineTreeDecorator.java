/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class TrunkVineTreeDecorator
extends TreeDecorator {
    public static final Codec<TrunkVineTreeDecorator> field_236878_a_;
    public static final TrunkVineTreeDecorator field_236879_b_;

    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return TreeDecoratorType.TRUNK_VINE;
    }

    @Override
    public void func_225576_a_(ISeedReader iSeedReader, Random random2, List<BlockPos> list, List<BlockPos> list2, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox) {
        list.forEach(arg_0 -> this.lambda$func_225576_a_$0(random2, iSeedReader, set, mutableBoundingBox, arg_0));
    }

    private static TrunkVineTreeDecorator lambda$static$1() {
        return field_236879_b_;
    }

    private void lambda$func_225576_a_$0(Random random2, ISeedReader iSeedReader, Set set, MutableBoundingBox mutableBoundingBox, BlockPos blockPos) {
        BlockPos blockPos2;
        if (random2.nextInt(3) > 0 && Feature.isAirAt(iSeedReader, blockPos2 = blockPos.west())) {
            this.func_227424_a_(iSeedReader, blockPos2, VineBlock.EAST, set, mutableBoundingBox);
        }
        if (random2.nextInt(3) > 0 && Feature.isAirAt(iSeedReader, blockPos2 = blockPos.east())) {
            this.func_227424_a_(iSeedReader, blockPos2, VineBlock.WEST, set, mutableBoundingBox);
        }
        if (random2.nextInt(3) > 0 && Feature.isAirAt(iSeedReader, blockPos2 = blockPos.north())) {
            this.func_227424_a_(iSeedReader, blockPos2, VineBlock.SOUTH, set, mutableBoundingBox);
        }
        if (random2.nextInt(3) > 0 && Feature.isAirAt(iSeedReader, blockPos2 = blockPos.south())) {
            this.func_227424_a_(iSeedReader, blockPos2, VineBlock.NORTH, set, mutableBoundingBox);
        }
    }

    static {
        field_236879_b_ = new TrunkVineTreeDecorator();
        field_236878_a_ = Codec.unit(TrunkVineTreeDecorator::lambda$static$1);
    }
}

