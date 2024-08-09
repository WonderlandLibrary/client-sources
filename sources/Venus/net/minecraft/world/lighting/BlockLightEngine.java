/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.BlockLightStorage;
import net.minecraft.world.lighting.LightEngine;
import org.apache.commons.lang3.mutable.MutableInt;

public final class BlockLightEngine
extends LightEngine<BlockLightStorage.StorageMap, BlockLightStorage> {
    private static final Direction[] DIRECTIONS = Direction.values();
    private final BlockPos.Mutable scratchPos = new BlockPos.Mutable();

    public BlockLightEngine(IChunkLightProvider iChunkLightProvider) {
        super(iChunkLightProvider, LightType.BLOCK, new BlockLightStorage(iChunkLightProvider));
    }

    private int getLightValue(long l) {
        int n = BlockPos.unpackX(l);
        int n2 = BlockPos.unpackY(l);
        int n3 = BlockPos.unpackZ(l);
        IBlockReader iBlockReader = this.chunkProvider.getChunkForLight(n >> 4, n3 >> 4);
        return iBlockReader != null ? iBlockReader.getLightValue(this.scratchPos.setPos(n, n2, n3)) : 0;
    }

    @Override
    protected int getEdgeLevel(long l, long l2, int n) {
        VoxelShape voxelShape;
        int n2;
        int n3;
        if (l2 == Long.MAX_VALUE) {
            return 0;
        }
        if (l == Long.MAX_VALUE) {
            return n + 15 - this.getLightValue(l2);
        }
        if (n >= 15) {
            return n;
        }
        int n4 = Integer.signum(BlockPos.unpackX(l2) - BlockPos.unpackX(l));
        Direction direction = Direction.byLong(n4, n3 = Integer.signum(BlockPos.unpackY(l2) - BlockPos.unpackY(l)), n2 = Integer.signum(BlockPos.unpackZ(l2) - BlockPos.unpackZ(l)));
        if (direction == null) {
            return 0;
        }
        MutableInt mutableInt = new MutableInt();
        BlockState blockState = this.getBlockAndOpacity(l2, mutableInt);
        if (mutableInt.getValue() >= 15) {
            return 0;
        }
        BlockState blockState2 = this.getBlockAndOpacity(l, null);
        VoxelShape voxelShape2 = this.getVoxelShape(blockState2, l, direction);
        return VoxelShapes.faceShapeCovers(voxelShape2, voxelShape = this.getVoxelShape(blockState, l2, direction.getOpposite())) ? 15 : n + Math.max(1, mutableInt.getValue());
    }

    @Override
    protected void notifyNeighbors(long l, int n, boolean bl) {
        long l2 = SectionPos.worldToSection(l);
        for (Direction direction : DIRECTIONS) {
            long l3 = BlockPos.offset(l, direction);
            long l4 = SectionPos.worldToSection(l3);
            if (l2 != l4 && !((BlockLightStorage)this.storage).hasSection(l4)) continue;
            this.propagateLevel(l, l3, n, bl);
        }
    }

    @Override
    protected int computeLevel(long l, long l2, int n) {
        int n2 = n;
        if (Long.MAX_VALUE != l2) {
            int n3 = this.getEdgeLevel(Long.MAX_VALUE, l, 0);
            if (n > n3) {
                n2 = n3;
            }
            if (n2 == 0) {
                return n2;
            }
        }
        long l3 = SectionPos.worldToSection(l);
        NibbleArray nibbleArray = ((BlockLightStorage)this.storage).getArray(l3, false);
        for (Direction direction : DIRECTIONS) {
            long l4;
            NibbleArray nibbleArray2;
            long l5 = BlockPos.offset(l, direction);
            if (l5 == l2 || (nibbleArray2 = l3 == (l4 = SectionPos.worldToSection(l5)) ? nibbleArray : ((BlockLightStorage)this.storage).getArray(l4, false)) == null) continue;
            int n4 = this.getEdgeLevel(l5, l, this.getLevelFromArray(nibbleArray2, l5));
            if (n2 > n4) {
                n2 = n4;
            }
            if (n2 != 0) continue;
            return n2;
        }
        return n2;
    }

    @Override
    public void func_215623_a(BlockPos blockPos, int n) {
        ((BlockLightStorage)this.storage).processAllLevelUpdates();
        this.scheduleUpdate(Long.MAX_VALUE, blockPos.toLong(), 15 - n, false);
    }
}

