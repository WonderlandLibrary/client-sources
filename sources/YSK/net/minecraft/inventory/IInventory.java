package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public interface IInventory extends IWorldNameable
{
    int getSizeInventory();
    
    void closeInventory(final EntityPlayer p0);
    
    void clear();
    
    void markDirty();
    
    boolean isItemValidForSlot(final int p0, final ItemStack p1);
    
    int getFieldCount();
    
    boolean isUseableByPlayer(final EntityPlayer p0);
    
    void setField(final int p0, final int p1);
    
    int getField(final int p0);
    
    void setInventorySlotContents(final int p0, final ItemStack p1);
    
    int getInventoryStackLimit();
    
    ItemStack getStackInSlot(final int p0);
    
    void openInventory(final EntityPlayer p0);
    
    ItemStack decrStackSize(final int p0, final int p1);
    
    ItemStack removeStackFromSlot(final int p0);
}
