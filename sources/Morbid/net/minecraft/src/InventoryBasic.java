package net.minecraft.src;

import java.util.*;

public class InventoryBasic implements IInventory
{
    private String inventoryTitle;
    private int slotsCount;
    private ItemStack[] inventoryContents;
    private List field_70480_d;
    private boolean field_94051_e;
    
    public InventoryBasic(final String par1Str, final boolean par2, final int par3) {
        this.inventoryTitle = par1Str;
        this.field_94051_e = par2;
        this.slotsCount = par3;
        this.inventoryContents = new ItemStack[par3];
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return this.inventoryContents[par1];
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.inventoryContents[par1] == null) {
            return null;
        }
        if (this.inventoryContents[par1].stackSize <= par2) {
            final ItemStack var3 = this.inventoryContents[par1];
            this.inventoryContents[par1] = null;
            this.onInventoryChanged();
            return var3;
        }
        final ItemStack var3 = this.inventoryContents[par1].splitStack(par2);
        if (this.inventoryContents[par1].stackSize == 0) {
            this.inventoryContents[par1] = null;
        }
        this.onInventoryChanged();
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.inventoryContents[par1] != null) {
            final ItemStack var2 = this.inventoryContents[par1];
            this.inventoryContents[par1] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.inventoryContents[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }
    
    @Override
    public int getSizeInventory() {
        return this.slotsCount;
    }
    
    @Override
    public String getInvName() {
        return this.inventoryTitle;
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.field_94051_e;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void onInventoryChanged() {
        if (this.field_70480_d != null) {
            for (int var1 = 0; var1 < this.field_70480_d.size(); ++var1) {
                this.field_70480_d.get(var1).onInventoryChanged(this);
            }
        }
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return true;
    }
    
    @Override
    public void openChest() {
    }
    
    @Override
    public void closeChest() {
    }
    
    @Override
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
}
