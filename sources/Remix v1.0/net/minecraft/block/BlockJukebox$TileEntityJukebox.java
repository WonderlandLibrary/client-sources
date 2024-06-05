package net.minecraft.block;

import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public static class TileEntityJukebox extends TileEntity
{
    private ItemStack record;
    
    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("RecordItem", 10)) {
            this.setRecord(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("RecordItem")));
        }
        else if (compound.getInteger("Record") > 0) {
            this.setRecord(new ItemStack(Item.getItemById(compound.getInteger("Record")), 1, 0));
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (this.getRecord() != null) {
            compound.setTag("RecordItem", this.getRecord().writeToNBT(new NBTTagCompound()));
        }
    }
    
    public ItemStack getRecord() {
        return this.record;
    }
    
    public void setRecord(final ItemStack recordStack) {
        this.record = recordStack;
        this.markDirty();
    }
}
