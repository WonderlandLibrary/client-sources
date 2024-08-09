/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.IClearable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class JukeboxTileEntity
extends TileEntity
implements IClearable {
    private ItemStack record = ItemStack.EMPTY;

    public JukeboxTileEntity() {
        super(TileEntityType.JUKEBOX);
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        if (compoundNBT.contains("RecordItem", 1)) {
            this.setRecord(ItemStack.read(compoundNBT.getCompound("RecordItem")));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        if (!this.getRecord().isEmpty()) {
            compoundNBT.put("RecordItem", this.getRecord().write(new CompoundNBT()));
        }
        return compoundNBT;
    }

    public ItemStack getRecord() {
        return this.record;
    }

    public void setRecord(ItemStack itemStack) {
        this.record = itemStack;
        this.markDirty();
    }

    @Override
    public void clear() {
        this.setRecord(ItemStack.EMPTY);
    }
}

