/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public abstract class TreeDecorator {
    public static final Codec<TreeDecorator> field_236874_c_ = Registry.TREE_DECORATOR_TYPE.dispatch(TreeDecorator::func_230380_a_, TreeDecoratorType::func_236876_a_);

    protected abstract TreeDecoratorType<?> func_230380_a_();

    public abstract void func_225576_a_(ISeedReader var1, Random var2, List<BlockPos> var3, List<BlockPos> var4, Set<BlockPos> var5, MutableBoundingBox var6);

    protected void func_227424_a_(IWorldWriter iWorldWriter, BlockPos blockPos, BooleanProperty booleanProperty, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox) {
        this.func_227423_a_(iWorldWriter, blockPos, (BlockState)Blocks.VINE.getDefaultState().with(booleanProperty, true), set, mutableBoundingBox);
    }

    protected void func_227423_a_(IWorldWriter iWorldWriter, BlockPos blockPos, BlockState blockState, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox) {
        iWorldWriter.setBlockState(blockPos, blockState, 19);
        set.add(blockPos);
        mutableBoundingBox.expandTo(new MutableBoundingBox(blockPos, blockPos));
    }
}

