package net.minecraft.src;

import java.util.*;

public class TileEntityDispenser extends TileEntity implements IInventory
{
    private ItemStack[] dispenserContents;
    private Random dispenserRandom;
    protected String customName;
    
    public TileEntityDispenser() {
        this.dispenserContents = new ItemStack[9];
        this.dispenserRandom = new Random();
    }
    
    @Override
    public int getSizeInventory() {
        return 9;
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return this.dispenserContents[par1];
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.dispenserContents[par1] == null) {
            return null;
        }
        if (this.dispenserContents[par1].stackSize <= par2) {
            final ItemStack var3 = this.dispenserContents[par1];
            this.dispenserContents[par1] = null;
            this.onInventoryChanged();
            return var3;
        }
        final ItemStack var3 = this.dispenserContents[par1].splitStack(par2);
        if (this.dispenserContents[par1].stackSize == 0) {
            this.dispenserContents[par1] = null;
        }
        this.onInventoryChanged();
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.dispenserContents[par1] != null) {
            final ItemStack var2 = this.dispenserContents[par1];
            this.dispenserContents[par1] = null;
            return var2;
        }
        return null;
    }
    
    public int getRandomStackFromInventory() {
        int var1 = -1;
        int var2 = 1;
        for (int var3 = 0; var3 < this.dispenserContents.length; ++var3) {
            if (this.dispenserContents[var3] != null && this.dispenserRandom.nextInt(var2++) == 0) {
                var1 = var3;
            }
        }
        return var1;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.dispenserContents[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }
    
    public int addItem(final ItemStack par1ItemStack) {
        for (int var2 = 0; var2 < this.dispenserContents.length; ++var2) {
            if (this.dispenserContents[var2] == null || this.dispenserContents[var2].itemID == 0) {
                this.setInventorySlotContents(var2, par1ItemStack);
                return var2;
            }
        }
        return -1;
    }
    
    @Override
    public String getInvName() {
        return this.isInvNameLocalized() ? this.customName : "container.dispenser";
    }
    
    public void setCustomName(final String par1Str) {
        this.customName = par1Str;
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.customName != null;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.dispenserContents = new ItemStack[this.getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            final int var5 = var4.getByte("Slot") & 0xFF;
            if (var5 >= 0 && var5 < this.dispenserContents.length) {
                this.dispenserContents[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.customName = par1NBTTagCompound.getString("CustomName");
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.dispenserContents.length; ++var3) {
            if (this.dispenserContents[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.dispenserContents[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
        if (this.isInvNameLocalized()) {
            par1NBTTagCompound.setString("CustomName", this.customName);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
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
