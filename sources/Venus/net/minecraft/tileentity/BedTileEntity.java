/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.block.BedBlock;
import net.minecraft.item.DyeColor;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BedTileEntity
extends TileEntity {
    private DyeColor color;

    public BedTileEntity() {
        super(TileEntityType.BED);
    }

    public BedTileEntity(DyeColor dyeColor) {
        this();
        this.setColor(dyeColor);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 11, this.getUpdateTag());
    }

    public DyeColor getColor() {
        if (this.color == null) {
            this.color = ((BedBlock)this.getBlockState().getBlock()).getColor();
        }
        return this.color;
    }

    public void setColor(DyeColor dyeColor) {
        this.color = dyeColor;
    }
}

