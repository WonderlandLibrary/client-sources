package net.minecraft.src;

import java.util.*;

public class ContainerEnchantment extends Container
{
    public IInventory tableInventory;
    private World worldPointer;
    private int posX;
    private int posY;
    private int posZ;
    private Random rand;
    public long nameSeed;
    public int[] enchantLevels;
    
    public ContainerEnchantment(final InventoryPlayer par1InventoryPlayer, final World par2World, final int par3, final int par4, final int par5) {
        this.tableInventory = new SlotEnchantmentTable(this, "Enchant", true, 1);
        this.rand = new Random();
        this.enchantLevels = new int[3];
        this.worldPointer = par2World;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
        this.addSlotToContainer(new SlotEnchantment(this, this.tableInventory, 0, 25, 47));
        for (int var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }
        for (int var6 = 0; var6 < 9; ++var6) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
        }
    }
    
    @Override
    public void addCraftingToCrafters(final ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int var1 = 0; var1 < this.crafters.size(); ++var1) {
            final ICrafting var2 = this.crafters.get(var1);
            var2.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
            var2.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
            var2.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
        }
    }
    
    @Override
    public void updateProgressBar(final int par1, final int par2) {
        if (par1 >= 0 && par1 <= 2) {
            this.enchantLevels[par1] = par2;
        }
        else {
            super.updateProgressBar(par1, par2);
        }
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory par1IInventory) {
        if (par1IInventory == this.tableInventory) {
            final ItemStack var2 = par1IInventory.getStackInSlot(0);
            if (var2 != null && var2.isItemEnchantable()) {
                this.nameSeed = this.rand.nextLong();
                if (!this.worldPointer.isRemote) {
                    int var3 = 0;
                    for (int var4 = -1; var4 <= 1; ++var4) {
                        for (int var5 = -1; var5 <= 1; ++var5) {
                            if ((var4 != 0 || var5 != 0) && this.worldPointer.isAirBlock(this.posX + var5, this.posY, this.posZ + var4) && this.worldPointer.isAirBlock(this.posX + var5, this.posY + 1, this.posZ + var4)) {
                                if (this.worldPointer.getBlockId(this.posX + var5 * 2, this.posY, this.posZ + var4 * 2) == Block.bookShelf.blockID) {
                                    ++var3;
                                }
                                if (this.worldPointer.getBlockId(this.posX + var5 * 2, this.posY + 1, this.posZ + var4 * 2) == Block.bookShelf.blockID) {
                                    ++var3;
                                }
                                if (var5 != 0 && var4 != 0) {
                                    if (this.worldPointer.getBlockId(this.posX + var5 * 2, this.posY, this.posZ + var4) == Block.bookShelf.blockID) {
                                        ++var3;
                                    }
                                    if (this.worldPointer.getBlockId(this.posX + var5 * 2, this.posY + 1, this.posZ + var4) == Block.bookShelf.blockID) {
                                        ++var3;
                                    }
                                    if (this.worldPointer.getBlockId(this.posX + var5, this.posY, this.posZ + var4 * 2) == Block.bookShelf.blockID) {
                                        ++var3;
                                    }
                                    if (this.worldPointer.getBlockId(this.posX + var5, this.posY + 1, this.posZ + var4 * 2) == Block.bookShelf.blockID) {
                                        ++var3;
                                    }
                                }
                            }
                        }
                    }
                    for (int var4 = 0; var4 < 3; ++var4) {
                        this.enchantLevels[var4] = EnchantmentHelper.calcItemStackEnchantability(this.rand, var4, var3, var2);
                    }
                    this.detectAndSendChanges();
                }
            }
            else {
                for (int var3 = 0; var3 < 3; ++var3) {
                    this.enchantLevels[var3] = 0;
                }
            }
        }
    }
    
    @Override
    public boolean enchantItem(final EntityPlayer par1EntityPlayer, final int par2) {
        final ItemStack var3 = this.tableInventory.getStackInSlot(0);
        if (this.enchantLevels[par2] > 0 && var3 != null && (par1EntityPlayer.experienceLevel >= this.enchantLevels[par2] || par1EntityPlayer.capabilities.isCreativeMode)) {
            if (!this.worldPointer.isRemote) {
                final List var4 = EnchantmentHelper.buildEnchantmentList(this.rand, var3, this.enchantLevels[par2]);
                final boolean var5 = var3.itemID == Item.book.itemID;
                if (var4 != null) {
                    par1EntityPlayer.addExperienceLevel(-this.enchantLevels[par2]);
                    if (var5) {
                        var3.itemID = Item.enchantedBook.itemID;
                    }
                    final int var6 = var5 ? this.rand.nextInt(var4.size()) : -1;
                    for (int var7 = 0; var7 < var4.size(); ++var7) {
                        final EnchantmentData var8 = var4.get(var7);
                        if (!var5 || var7 == var6) {
                            if (var5) {
                                Item.enchantedBook.func_92115_a(var3, var8);
                            }
                            else {
                                var3.addEnchantment(var8.enchantmentobj, var8.enchantmentLevel);
                            }
                        }
                    }
                    this.onCraftMatrixChanged(this.tableInventory);
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void onCraftGuiClosed(final EntityPlayer par1EntityPlayer) {
        super.onCraftGuiClosed(par1EntityPlayer);
        if (!this.worldPointer.isRemote) {
            final ItemStack var2 = this.tableInventory.getStackInSlotOnClosing(0);
            if (var2 != null) {
                par1EntityPlayer.dropPlayerItem(var2);
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return this.worldPointer.getBlockId(this.posX, this.posY, this.posZ) == Block.enchantmentTable.blockID && par1EntityPlayer.getDistanceSq(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 == 0) {
                if (!this.mergeItemStack(var5, 1, 37, true)) {
                    return null;
                }
            }
            else {
                if (this.inventorySlots.get(0).getHasStack() || !this.inventorySlots.get(0).isItemValid(var5)) {
                    return null;
                }
                if (var5.hasTagCompound() && var5.stackSize == 1) {
                    this.inventorySlots.get(0).putStack(var5.copy());
                    var5.stackSize = 0;
                }
                else if (var5.stackSize >= 1) {
                    this.inventorySlots.get(0).putStack(new ItemStack(var5.itemID, 1, var5.getItemDamage()));
                    final ItemStack itemStack = var5;
                    --itemStack.stackSize;
                }
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            }
            else {
                var4.onSlotChanged();
            }
            if (var5.stackSize == var3.stackSize) {
                return null;
            }
            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }
        return var3;
    }
}
