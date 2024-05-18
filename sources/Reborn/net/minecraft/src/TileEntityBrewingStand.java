package net.minecraft.src;

import java.util.*;

public class TileEntityBrewingStand extends TileEntity implements ISidedInventory
{
    private static final int[] field_102017_a;
    private static final int[] field_102016_b;
    private ItemStack[] brewingItemStacks;
    private int brewTime;
    private int filledSlots;
    private int ingredientID;
    private String field_94132_e;
    
    static {
        field_102017_a = new int[] { 3 };
        field_102016_b = new int[] { 0, 1, 2 };
    }
    
    public TileEntityBrewingStand() {
        this.brewingItemStacks = new ItemStack[4];
    }
    
    @Override
    public String getInvName() {
        return this.isInvNameLocalized() ? this.field_94132_e : "container.brewing";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.field_94132_e != null && this.field_94132_e.length() > 0;
    }
    
    public void func_94131_a(final String par1Str) {
        this.field_94132_e = par1Str;
    }
    
    @Override
    public int getSizeInventory() {
        return this.brewingItemStacks.length;
    }
    
    @Override
    public void updateEntity() {
        if (this.brewTime > 0) {
            --this.brewTime;
            if (this.brewTime == 0) {
                this.brewPotions();
                this.onInventoryChanged();
            }
            else if (!this.canBrew()) {
                this.brewTime = 0;
                this.onInventoryChanged();
            }
            else if (this.ingredientID != this.brewingItemStacks[3].itemID) {
                this.brewTime = 0;
                this.onInventoryChanged();
            }
        }
        else if (this.canBrew()) {
            this.brewTime = 400;
            this.ingredientID = this.brewingItemStacks[3].itemID;
        }
        final int var1 = this.getFilledSlots();
        if (var1 != this.filledSlots) {
            this.filledSlots = var1;
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, var1, 2);
        }
        super.updateEntity();
    }
    
    public int getBrewTime() {
        return this.brewTime;
    }
    
    private boolean canBrew() {
        if (this.brewingItemStacks[3] == null || this.brewingItemStacks[3].stackSize <= 0) {
            return false;
        }
        final ItemStack var1 = this.brewingItemStacks[3];
        if (!Item.itemsList[var1.itemID].isPotionIngredient()) {
            return false;
        }
        boolean var2 = false;
        for (int var3 = 0; var3 < 3; ++var3) {
            if (this.brewingItemStacks[var3] != null && this.brewingItemStacks[var3].itemID == Item.potion.itemID) {
                final int var4 = this.brewingItemStacks[var3].getItemDamage();
                final int var5 = this.getPotionResult(var4, var1);
                if (!ItemPotion.isSplash(var4) && ItemPotion.isSplash(var5)) {
                    var2 = true;
                    break;
                }
                final List var6 = Item.potion.getEffects(var4);
                final List var7 = Item.potion.getEffects(var5);
                if ((var4 <= 0 || var6 != var7) && (var6 == null || (!var6.equals(var7) && var7 != null)) && var4 != var5) {
                    var2 = true;
                    break;
                }
            }
        }
        return var2;
    }
    
    private void brewPotions() {
        if (this.canBrew()) {
            final ItemStack var1 = this.brewingItemStacks[3];
            for (int var2 = 0; var2 < 3; ++var2) {
                if (this.brewingItemStacks[var2] != null && this.brewingItemStacks[var2].itemID == Item.potion.itemID) {
                    final int var3 = this.brewingItemStacks[var2].getItemDamage();
                    final int var4 = this.getPotionResult(var3, var1);
                    final List var5 = Item.potion.getEffects(var3);
                    final List var6 = Item.potion.getEffects(var4);
                    if ((var3 <= 0 || var5 != var6) && (var5 == null || (!var5.equals(var6) && var6 != null))) {
                        if (var3 != var4) {
                            this.brewingItemStacks[var2].setItemDamage(var4);
                        }
                    }
                    else if (!ItemPotion.isSplash(var3) && ItemPotion.isSplash(var4)) {
                        this.brewingItemStacks[var2].setItemDamage(var4);
                    }
                }
            }
            if (Item.itemsList[var1.itemID].hasContainerItem()) {
                this.brewingItemStacks[3] = new ItemStack(Item.itemsList[var1.itemID].getContainerItem());
            }
            else {
                final ItemStack itemStack = this.brewingItemStacks[3];
                --itemStack.stackSize;
                if (this.brewingItemStacks[3].stackSize <= 0) {
                    this.brewingItemStacks[3] = null;
                }
            }
        }
    }
    
    private int getPotionResult(final int par1, final ItemStack par2ItemStack) {
        return (par2ItemStack == null) ? par1 : (Item.itemsList[par2ItemStack.itemID].isPotionIngredient() ? PotionHelper.applyIngredient(par1, Item.itemsList[par2ItemStack.itemID].getPotionEffect()) : par1);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.brewingItemStacks = new ItemStack[this.getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            final byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.brewingItemStacks.length) {
                this.brewingItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        this.brewTime = par1NBTTagCompound.getShort("BrewTime");
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.field_94132_e = par1NBTTagCompound.getString("CustomName");
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BrewTime", (short)this.brewTime);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.brewingItemStacks.length; ++var3) {
            if (this.brewingItemStacks[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.brewingItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
        if (this.isInvNameLocalized()) {
            par1NBTTagCompound.setString("CustomName", this.field_94132_e);
        }
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return (par1 >= 0 && par1 < this.brewingItemStacks.length) ? this.brewingItemStacks[par1] : null;
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (par1 >= 0 && par1 < this.brewingItemStacks.length) {
            final ItemStack var3 = this.brewingItemStacks[par1];
            this.brewingItemStacks[par1] = null;
            return var3;
        }
        return null;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (par1 >= 0 && par1 < this.brewingItemStacks.length) {
            final ItemStack var2 = this.brewingItemStacks[par1];
            this.brewingItemStacks[par1] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        if (par1 >= 0 && par1 < this.brewingItemStacks.length) {
            this.brewingItemStacks[par1] = par2ItemStack;
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
        return (par1 == 3) ? Item.itemsList[par2ItemStack.itemID].isPotionIngredient() : (par2ItemStack.itemID == Item.potion.itemID || par2ItemStack.itemID == Item.glassBottle.itemID);
    }
    
    public void setBrewTime(final int par1) {
        this.brewTime = par1;
    }
    
    public int getFilledSlots() {
        int var1 = 0;
        for (int var2 = 0; var2 < 3; ++var2) {
            if (this.brewingItemStacks[var2] != null) {
                var1 |= 1 << var2;
            }
        }
        return var1;
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(final int par1) {
        return (par1 == 1) ? TileEntityBrewingStand.field_102017_a : TileEntityBrewingStand.field_102016_b;
    }
    
    @Override
    public boolean canInsertItem(final int par1, final ItemStack par2ItemStack, final int par3) {
        return this.isStackValidForSlot(par1, par2ItemStack);
    }
    
    @Override
    public boolean canExtractItem(final int par1, final ItemStack par2ItemStack, final int par3) {
        return true;
    }
}
