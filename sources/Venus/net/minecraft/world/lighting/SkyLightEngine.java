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
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.lighting.SkyLightStorage;
import org.apache.commons.lang3.mutable.MutableInt;

public final class SkyLightEngine
extends LightEngine<SkyLightStorage.StorageMap, SkyLightStorage> {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final Direction[] CARDINALS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    public SkyLightEngine(IChunkLightProvider iChunkLightProvider) {
        super(iChunkLightProvider, LightType.SKY, new SkyLightStorage(iChunkLightProvider));
    }

    @Override
    protected int getEdgeLevel(long l, long l2, int n) {
        if (l2 == Long.MAX_VALUE) {
            return 0;
        }
        if (l == Long.MAX_VALUE) {
            if (!((SkyLightStorage)this.storage).func_215551_l(l2)) {
                return 0;
            }
            n = 0;
        }
        if (n >= 15) {
            return n;
        }
        MutableInt mutableInt = new MutableInt();
        BlockState blockState = this.getBlockAndOpacity(l2, mutableInt);
        if (mutableInt.getValue() >= 15) {
            return 0;
        }
        int n2 = BlockPos.unpackX(l);
        int n3 = BlockPos.unpackY(l);
        int n4 = BlockPos.unpackZ(l);
        int n5 = BlockPos.unpackX(l2);
        int n6 = BlockPos.unpackY(l2);
        int n7 = BlockPos.unpackZ(l2);
        boolean bl = n2 == n5 && n4 == n7;
        int n8 = Integer.signum(n5 - n2);
        int n9 = Integer.signum(n6 - n3);
        int n10 = Integer.signum(n7 - n4);
        Direction direction = l == Long.MAX_VALUE ? Direction.DOWN : Direction.byLong(n8, n9, n10);
        BlockState blockState2 = this.getBlockAndOpacity(l, null);
        if (direction != null) {
            VoxelShape voxelShape;
            var20_18 = this.getVoxelShape(blockState2, l, direction);
            if (VoxelShapes.faceShapeCovers(var20_18, voxelShape = this.getVoxelShape(blockState, l2, direction.getOpposite()))) {
                return 0;
            }
        } else {
            var20_18 = this.getVoxelShape(blockState2, l, Direction.DOWN);
            if (VoxelShapes.faceShapeCovers(var20_18, VoxelShapes.empty())) {
                return 0;
            }
            int n11 = bl ? -1 : 0;
            Direction direction2 = Direction.byLong(n8, n11, n10);
            if (direction2 == null) {
                return 0;
            }
            VoxelShape voxelShape = this.getVoxelShape(blockState, l2, direction2.getOpposite());
            if (VoxelShapes.faceShapeCovers(VoxelShapes.empty(), voxelShape)) {
                return 0;
            }
        }
        boolean bl2 = l == Long.MAX_VALUE || bl && n3 > n6;
        return bl2 && n == 0 && mutableInt.getValue() == 0 ? 0 : n + Math.max(1, mutableInt.getValue());
    }

    @Override
    protected void notifyNeighbors(long l, int n, boolean bl) {
        long l2;
        long l3;
        int n2;
        long l4 = SectionPos.worldToSection(l);
        int n3 = BlockPos.unpackY(l);
        int n4 = SectionPos.mask(n3);
        int n5 = SectionPos.toChunk(n3);
        if (n4 != 0) {
            n2 = 0;
        } else {
            int n6 = 0;
            while (!((SkyLightStorage)this.storage).hasSection(SectionPos.withOffset(l4, 0, -n6 - 1, 0)) && ((SkyLightStorage)this.storage).isAboveBottom(n5 - n6 - 1)) {
                ++n6;
            }
            n2 = n6;
        }
        long l5 = BlockPos.offset(l, 0, -1 - n2 * 16, 0);
        long l6 = SectionPos.worldToSection(l5);
        if (l4 == l6 || ((SkyLightStorage)this.storage).hasSection(l6)) {
            this.propagateLevel(l, l5, n, bl);
        }
        if (l4 == (l3 = SectionPos.worldToSection(l2 = BlockPos.offset(l, Direction.UP))) || ((SkyLightStorage)this.storage).hasSection(l3)) {
            this.propagateLevel(l, l2, n, bl);
        }
        block1: for (Direction direction : CARDINALS) {
            int n7 = 0;
            do {
                long l7;
                long l8;
                if (l4 == (l8 = SectionPos.worldToSection(l7 = BlockPos.offset(l, direction.getXOffset(), -n7, direction.getZOffset())))) {
                    this.propagateLevel(l, l7, n, bl);
                    continue block1;
                }
                if (!((SkyLightStorage)this.storage).hasSection(l8)) continue;
                this.propagateLevel(l, l7, n, bl);
            } while (++n7 <= n2 * 16);
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
        NibbleArray nibbleArray = ((SkyLightStorage)this.storage).getArray(l3, false);
        for (Direction direction : DIRECTIONS) {
            int n4;
            long l4 = BlockPos.offset(l, direction);
            long l5 = SectionPos.worldToSection(l4);
            NibbleArray nibbleArray2 = l3 == l5 ? nibbleArray : ((SkyLightStorage)this.storage).getArray(l5, false);
            if (nibbleArray2 != null) {
                if (l4 == l2) continue;
                int n5 = this.getEdgeLevel(l4, l, this.getLevelFromArray(nibbleArray2, l4));
                if (n2 > n5) {
                    n2 = n5;
                }
                if (n2 != 0) continue;
                return n2;
            }
            if (direction == Direction.DOWN) continue;
            l4 = BlockPos.atSectionBottomY(l4);
            while (!((SkyLightStorage)this.storage).hasSection(l5) && !((SkyLightStorage)this.storage).isAboveWorld(l5)) {
                l5 = SectionPos.withOffset(l5, Direction.UP);
                l4 = BlockPos.offset(l4, 0, 16, 0);
            }
            NibbleArray nibbleArray3 = ((SkyLightStorage)this.storage).getArray(l5, false);
            if (l4 == l2) continue;
            if (nibbleArray3 != null) {
                n4 = this.getEdgeLevel(l4, l, this.getLevelFromArray(nibbleArray3, l4));
            } else {
                int n6 = n4 = ((SkyLightStorage)this.storage).isSectionEnabled(l5) ? 0 : 15;
            }
            if (n2 > n4) {
                n2 = n4;
            }
            if (n2 != 0) continue;
            return n2;
        }
        return n2;
    }

    @Override
    protected void scheduleUpdate(long l) {
        ((SkyLightStorage)this.storage).processAllLevelUpdates();
        long l2 = SectionPos.worldToSection(l);
        if (((SkyLightStorage)this.storage).hasSection(l2)) {
            super.scheduleUpdate(l);
        } else {
            l = BlockPos.atSectionBottomY(l);
            while (!((SkyLightStorage)this.storage).hasSection(l2) && !((SkyLightStorage)this.storage).isAboveWorld(l2)) {
                l2 = SectionPos.withOffset(l2, Direction.UP);
                l = BlockPos.offset(l, 0, 16, 0);
            }
            if (((SkyLightStorage)this.storage).hasSection(l2)) {
                super.scheduleUpdate(l);
            }
        }
    }

    @Override
    public String getDebugString(long l) {
        return super.getDebugString(l) + (((SkyLightStorage)this.storage).isAboveWorld(l) ? "*" : "");
    }
}

