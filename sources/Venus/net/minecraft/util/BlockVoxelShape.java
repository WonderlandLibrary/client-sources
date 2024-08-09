/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public enum BlockVoxelShape {
    FULL{

        @Override
        public boolean func_241854_a(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
            return Block.doesSideFillSquare(blockState.getRenderShape(iBlockReader, blockPos), direction);
        }
    }
    ,
    CENTER{
        private final int field_242680_d = 1;
        private final VoxelShape field_242681_e = Block.makeCuboidShape(7.0, 0.0, 7.0, 9.0, 10.0, 9.0);

        @Override
        public boolean func_241854_a(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
            return !VoxelShapes.compare(blockState.getRenderShape(iBlockReader, blockPos).project(direction), this.field_242681_e, IBooleanFunction.ONLY_SECOND);
        }
    }
    ,
    RIGID{
        private final int field_242682_d = 2;
        private final VoxelShape field_242683_e = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0), IBooleanFunction.ONLY_FIRST);

        @Override
        public boolean func_241854_a(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
            return !VoxelShapes.compare(blockState.getRenderShape(iBlockReader, blockPos).project(direction), this.field_242683_e, IBooleanFunction.ONLY_SECOND);
        }
    };


    public abstract boolean func_241854_a(BlockState var1, IBlockReader var2, BlockPos var3, Direction var4);
}

