/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class DaylightDetectorTileEntity
extends TileEntity
implements ITickableTileEntity {
    public DaylightDetectorTileEntity() {
        super(TileEntityType.DAYLIGHT_DETECTOR);
    }

    @Override
    public void tick() {
        BlockState blockState;
        Block block;
        if (this.world != null && !this.world.isRemote && this.world.getGameTime() % 20L == 0L && (block = (blockState = this.getBlockState()).getBlock()) instanceof DaylightDetectorBlock) {
            DaylightDetectorBlock.updatePower(blockState, this.world, this.pos);
        }
    }
}

