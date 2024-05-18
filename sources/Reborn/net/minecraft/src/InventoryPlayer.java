package net.minecraft.src;

import java.util.concurrent.*;

public class InventoryPlayer implements IInventory
{
    public ItemStack[] mainInventory;
    public ItemStack[] armorInventory;
    public int currentItem;
    private ItemStack currentItemStack;
    public EntityPlayer player;
    private ItemStack itemStack;
    public boolean inventoryChanged;
    
    public InventoryPlayer(final EntityPlayer par1EntityPlayer) {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];
        this.currentItem = 0;
        this.inventoryChanged = false;
        this.player = par1EntityPlayer;
    }
    
    public ItemStack getCurrentItem() {
        return (this.currentItem < 9 && this.currentItem >= 0) ? this.mainInventory[this.currentItem] : null;
    }
    
    public static int getHotbarSize() {
        return 9;
    }
    
    private int getInventorySlotContainItem(final int par1) {
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2) {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].itemID == par1) {
                return var2;
            }
        }
        return -1;
    }
    
    private int getInventorySlotContainItemAndDamage(final int par1, final int par2) {
        for (int var3 = 0; var3 < this.mainInventory.length; ++var3) {
            if (this.mainInventory[var3] != null && this.mainInventory[var3].itemID == par1 && this.mainInventory[var3].getItemDamage() == par2) {
                return var3;
            }
        }
        return -1;
    }
    
    private int storeItemStack(final ItemStack par1ItemStack) {
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2) {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].itemID == par1ItemStack.itemID && this.mainInventory[var2].isStackable() && this.mainInventory[var2].stackSize < this.mainInventory[var2].getMaxStackSize() && this.mainInventory[var2].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[var2].getHasSubtypes() || this.mainInventory[var2].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.mainInventory[var2], par1ItemStack)) {
                return var2;
            }
        }
        return -1;
    }
    
    public int getFirstEmptyStack() {
        for (int var1 = 0; var1 < this.mainInventory.length; ++var1) {
            if (this.mainInventory[var1] == null) {
                return var1;
            }
        }
        return -1;
    }
    
    public void setCurrentItem(final int par1, final int par2, final boolean par3, final boolean par4) {
        final boolean var5 = true;
        this.currentItemStack = this.getCurrentItem();
        int var6;
        if (par3) {
            var6 = this.getInventorySlotContainItemAndDamage(par1, par2);
        }
        else {
            var6 = this.getInventorySlotContainItem(par1);
        }
        if (var6 >= 0 && var6 < 9) {
            this.currentItem = var6;
        }
        else if (par4 && par1 > 0) {
            final int var7 = this.getFirstEmptyStack();
            if (var7 >= 0 && var7 < 9) {
                this.currentItem = var7;
            }
            this.func_70439_a(Item.itemsList[par1], par2);
        }
    }
    
    public void changeCurrentItem(int par1) {
        if (par1 > 0) {
            par1 = 1;
        }
        if (par1 < 0) {
            par1 = -1;
        }
        this.currentItem -= par1;
        while (this.currentItem < 0) {
            this.currentItem += 9;
        }
        while (this.currentItem >= 9) {
            this.currentItem -= 9;
        }
    }
    
    public int clearInventory(final int par1, final int par2) {
        int var3 = 0;
        for (int var4 = 0; var4 < this.mainInventory.length; ++var4) {
            final ItemStack var5 = this.mainInventory[var4];
            if (var5 != null && (par1 <= -1 || var5.itemID == par1) && (par2 <= -1 || var5.getItemDamage() == par2)) {
                var3 += var5.stackSize;
                this.mainInventory[var4] = null;
            }
        }
        for (int var4 = 0; var4 < this.armorInventory.length; ++var4) {
            final ItemStack var5 = this.armorInventory[var4];
            if (var5 != null && (par1 <= -1 || var5.itemID == par1) && (par2 <= -1 || var5.getItemDamage() == par2)) {
                var3 += var5.stackSize;
                this.armorInventory[var4] = null;
            }
        }
        return var3;
    }
    
    public void func_70439_a(final Item par1Item, final int par2) {
        if (par1Item != null) {
            final int var3 = this.getInventorySlotContainItemAndDamage(par1Item.itemID, par2);
            if (var3 >= 0) {
                this.mainInventory[var3] = this.mainInventory[this.currentItem];
            }
            if (this.currentItemStack != null && this.currentItemStack.isItemEnchantable() && this.getInventorySlotContainItemAndDamage(this.currentItemStack.itemID, this.currentItemStack.getItemDamageForDisplay()) == this.currentItem) {
                return;
            }
            this.mainInventory[this.currentItem] = new ItemStack(Item.itemsList[par1Item.itemID], 1, par2);
        }
    }
    
    private int storePartialItemStack(final ItemStack par1ItemStack) {
        final int var2 = par1ItemStack.itemID;
        int var3 = par1ItemStack.stackSize;
        if (par1ItemStack.getMaxStackSize() == 1) {
            final int var4 = this.getFirstEmptyStack();
            if (var4 < 0) {
                return var3;
            }
            if (this.mainInventory[var4] == null) {
                this.mainInventory[var4] = ItemStack.copyItemStack(par1ItemStack);
            }
            return 0;
        }
        else {
            int var4 = this.storeItemStack(par1ItemStack);
            if (var4 < 0) {
                var4 = this.getFirstEmptyStack();
            }
            if (var4 < 0) {
                return var3;
            }
            if (this.mainInventory[var4] == null) {
                this.mainInventory[var4] = new ItemStack(var2, 0, par1ItemStack.getItemDamage());
                if (par1ItemStack.hasTagCompound()) {
                    this.mainInventory[var4].setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
                }
            }
            int var5;
            if ((var5 = var3) > this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize) {
                var5 = this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize;
            }
            if (var5 > this.getInventoryStackLimit() - this.mainInventory[var4].stackSize) {
                var5 = this.getInventoryStackLimit() - this.mainInventory[var4].stackSize;
            }
            if (var5 == 0) {
                return var3;
            }
            var3 -= var5;
            final ItemStack itemStack = this.mainInventory[var4];
            itemStack.stackSize += var5;
            this.mainInventory[var4].animationsToGo = 5;
            return var3;
        }
    }
    
    public void decrementAnimations() {
        for (int var1 = 0; var1 < this.mainInventory.length; ++var1) {
            if (this.mainInventory[var1] != null) {
                this.mainInventory[var1].updateAnimation(this.player.worldObj, this.player, var1, this.currentItem == var1);
            }
        }
    }
    
    public boolean consumeInventoryItem(final int par1) {
        final int var2 = this.getInventorySlotContainItem(par1);
        if (var2 < 0) {
            return false;
        }
        final ItemStack itemStack = this.mainInventory[var2];
        if (--itemStack.stackSize <= 0) {
            this.mainInventory[var2] = null;
        }
        return true;
    }
    
    public boolean hasItem(final int par1) {
        final int var2 = this.getInventorySlotContainItem(par1);
        return var2 >= 0;
    }
    
    public boolean addItemStackToInventory(final ItemStack par1ItemStack) {
        if (par1ItemStack == null) {
            return false;
        }
        try {
            if (par1ItemStack.isItemDamaged()) {
                final int var2 = this.getFirstEmptyStack();
                if (var2 >= 0) {
                    this.mainInventory[var2] = ItemStack.copyItemStack(par1ItemStack);
                    this.mainInventory[var2].animationsToGo = 5;
                    par1ItemStack.stackSize = 0;
                    return true;
                }
                if (this.player.capabilities.isCreativeMode) {
                    par1ItemStack.stackSize = 0;
                    return true;
                }
                return false;
            }
            else {
                int var2;
                do {
                    var2 = par1ItemStack.stackSize;
                    par1ItemStack.stackSize = this.storePartialItemStack(par1ItemStack);
                } while (par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < var2);
                if (par1ItemStack.stackSize == var2 && this.player.capabilities.isCreativeMode) {
                    par1ItemStack.stackSize = 0;
                    return true;
                }
                return par1ItemStack.stackSize < var2;
            }
        }
        catch (Throwable var4) {
            final CrashReport var3 = CrashReport.makeCrashReport(var4, "Adding item to inventory");
            final CrashReportCategory var5 = var3.makeCategory("Item being added");
            var5.addCrashSection("Item ID", par1ItemStack.itemID);
            var5.addCrashSection("Item data", par1ItemStack.getItemDamage());
            var5.addCrashSectionCallable("Item name", new CallableItemName(this, par1ItemStack));
            throw new ReportedException(var3);
        }
    }
    
    @Override
    public ItemStack decrStackSize(int par1, final int par2) {
        ItemStack[] var3 = this.mainInventory;
        if (par1 >= this.mainInventory.length) {
            var3 = this.armorInventory;
            par1 -= this.mainInventory.length;
        }
        if (var3[par1] == null) {
            return null;
        }
        if (var3[par1].stackSize <= par2) {
            final ItemStack var4 = var3[par1];
            var3[par1] = null;
            return var4;
        }
        final ItemStack var4 = var3[par1].splitStack(par2);
        if (var3[par1].stackSize == 0) {
            var3[par1] = null;
        }
        return var4;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        ItemStack[] var2 = this.mainInventory;
        if (par1 >= this.mainInventory.length) {
            var2 = this.armorInventory;
            par1 -= this.mainInventory.length;
        }
        if (var2[par1] != null) {
            final ItemStack var3 = var2[par1];
            var2[par1] = null;
            return var3;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(int par1, final ItemStack par2ItemStack) {
        ItemStack[] var3 = this.mainInventory;
        if (par1 >= var3.length) {
            par1 -= var3.length;
            var3 = this.armorInventory;
        }
        var3[par1] = par2ItemStack;
    }
    
    public float getStrVsBlock(final Block par1Block) {
        float var2 = 1.0f;
        if (this.mainInventory[this.currentItem] != null) {
            var2 *= this.mainInventory[this.currentItem].getStrVsBlock(par1Block);
        }
        return var2;
    }
    
    public NBTTagList writeToNBT(final NBTTagList par1NBTTagList) {
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2) {
            if (this.mainInventory[var2] != null) {
                final NBTTagCompound var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)var2);
                this.mainInventory[var2].writeToNBT(var3);
                par1NBTTagList.appendTag(var3);
            }
        }
        for (int var2 = 0; var2 < this.armorInventory.length; ++var2) {
            if (this.armorInventory[var2] != null) {
                final NBTTagCompound var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)(var2 + 100));
                this.armorInventory[var2].writeToNBT(var3);
                par1NBTTagList.appendTag(var3);
            }
        }
        return par1NBTTagList;
    }
    
    public void readFromNBT(final NBTTagList par1NBTTagList) {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];
        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2) {
            final NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.tagAt(var2);
            final int var4 = var3.getByte("Slot") & 0xFF;
            final ItemStack var5 = ItemStack.loadItemStackFromNBT(var3);
            if (var5 != null) {
                if (var4 >= 0 && var4 < this.mainInventory.length) {
                    this.mainInventory[var4] = var5;
                }
                if (var4 >= 100 && var4 < this.armorInventory.length + 100) {
                    this.armorInventory[var4 - 100] = var5;
                }
            }
        }
    }
    
    @Override
    public int getSizeInventory() {
        return this.mainInventory.length + 4;
    }
    
    @Override
    public ItemStack getStackInSlot(int par1) {
        ItemStack[] var2 = this.mainInventory;
        if (par1 >= var2.length) {
            par1 -= var2.length;
            var2 = this.armorInventory;
        }
        return var2[par1];
    }
    
    @Override
    public String getInvName() {
        return "container.inventory";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return false;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public int getDamageVsEntity(final Entity par1Entity) {
        final ItemStack var2 = this.getStackInSlot(this.currentItem);
        return (var2 != null) ? var2.getDamageVsEntity(par1Entity) : 1;
    }
    
    public boolean canHarvestBlock(final Block par1Block) {
        if (par1Block.blockMaterial.isToolNotRequired()) {
            return true;
        }
        final ItemStack var2 = this.getStackInSlot(this.currentItem);
        return var2 != null && var2.canHarvestBlock(par1Block);
    }
    
    public ItemStack armorItemInSlot(final int par1) {
        return this.armorInventory[par1];
    }
    
    public int getTotalArmorValue() {
        int var1 = 0;
        for (int var2 = 0; var2 < this.armorInventory.length; ++var2) {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor) {
                final int var3 = ((ItemArmor)this.armorInventory[var2].getItem()).damageReduceAmount;
                var1 += var3;
            }
        }
        return var1;
    }
    
    public void damageArmor(int par1) {
        par1 /= 4;
        if (par1 < 1) {
            par1 = 1;
        }
        for (int var2 = 0; var2 < this.armorInventory.length; ++var2) {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor) {
                this.armorInventory[var2].damageItem(par1, this.player);
                if (this.armorInventory[var2].stackSize == 0) {
                    this.armorInventory[var2] = null;
                }
            }
        }
    }
    
    public void dropAllItems() {
        for (int var1 = 0; var1 < this.mainInventory.length; ++var1) {
            if (this.mainInventory[var1] != null) {
                this.player.dropPlayerItemWithRandomChoice(this.mainInventory[var1], true);
                this.mainInventory[var1] = null;
            }
        }
        for (int var1 = 0; var1 < this.armorInventory.length; ++var1) {
            if (this.armorInventory[var1] != null) {
                this.player.dropPlayerItemWithRandomChoice(this.armorInventory[var1], true);
                this.armorInventory[var1] = null;
            }
        }
    }
    
    @Override
    public void onInventoryChanged() {
        this.inventoryChanged = true;
    }
    
    public void setItemStack(final ItemStack par1ItemStack) {
        this.itemStack = par1ItemStack;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return !this.player.isDead && par1EntityPlayer.getDistanceSqToEntity(this.player) <= 64.0;
    }
    
    public boolean hasItemStack(final ItemStack par1ItemStack) {
        for (int var2 = 0; var2 < this.armorInventory.length; ++var2) {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].isItemEqual(par1ItemStack)) {
                return true;
            }
        }
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2) {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].isItemEqual(par1ItemStack)) {
                return true;
            }
        }
        return false;
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
    
    public void copyInventory(final InventoryPlayer par1InventoryPlayer) {
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2) {
            this.mainInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.mainInventory[var2]);
        }
        for (int var2 = 0; var2 < this.armorInventory.length; ++var2) {
            this.armorInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.armorInventory[var2]);
        }
        this.currentItem = par1InventoryPlayer.currentItem;
    }
}
