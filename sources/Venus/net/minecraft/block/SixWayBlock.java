/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class SixWayBlock
extends Block {
    private static final Direction[] FACING_VALUES = Direction.values();
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), SixWayBlock::lambda$static$0);
    protected final VoxelShape[] shapes;

    protected SixWayBlock(float f, AbstractBlock.Properties properties) {
        super(properties);
        this.shapes = this.makeShapes(f);
    }

    private VoxelShape[] makeShapes(float f) {
        float f2 = 0.5f - f;
        float f3 = 0.5f + f;
        VoxelShape voxelShape = Block.makeCuboidShape(f2 * 16.0f, f2 * 16.0f, f2 * 16.0f, f3 * 16.0f, f3 * 16.0f, f3 * 16.0f);
        VoxelShape[] voxelShapeArray = new VoxelShape[FACING_VALUES.length];
        for (int i = 0; i < FACING_VALUES.length; ++i) {
            Direction direction = FACING_VALUES[i];
            voxelShapeArray[i] = VoxelShapes.create(0.5 + Math.min((double)(-f), (double)direction.getXOffset() * 0.5), 0.5 + Math.min((double)(-f), (double)direction.getYOffset() * 0.5), 0.5 + Math.min((double)(-f), (double)direction.getZOffset() * 0.5), 0.5 + Math.max((double)f, (double)direction.getXOffset() * 0.5), 0.5 + Math.max((double)f, (double)direction.getYOffset() * 0.5), 0.5 + Math.max((double)f, (double)direction.getZOffset() * 0.5));
        }
        VoxelShape[] voxelShapeArray2 = new VoxelShape[64];
        for (int i = 0; i < 64; ++i) {
            VoxelShape voxelShape2 = voxelShape;
            for (int j = 0; j < FACING_VALUES.length; ++j) {
                if ((i & 1 << j) == 0) continue;
                voxelShape2 = VoxelShapes.or(voxelShape2, voxelShapeArray[j]);
            }
            voxelShapeArray2[i] = voxelShape2;
        }
        return voxelShapeArray2;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.shapes[this.getShapeIndex(blockState)];
    }

    protected int getShapeIndex(BlockState blockState) {
        int n = 0;
        for (int i = 0; i < FACING_VALUES.length; ++i) {
            if (!((Boolean)blockState.get(FACING_TO_PROPERTY_MAP.get(FACING_VALUES[i]))).booleanValue()) continue;
            n |= 1 << i;
        }
        return n;
    }

    private static void lambda$static$0(EnumMap enumMap) {
        enumMap.put(Direction.NORTH, NORTH);
        enumMap.put(Direction.EAST, EAST);
        enumMap.put(Direction.SOUTH, SOUTH);
        enumMap.put(Direction.WEST, WEST);
        enumMap.put(Direction.UP, UP);
        enumMap.put(Direction.DOWN, DOWN);
    }
}

