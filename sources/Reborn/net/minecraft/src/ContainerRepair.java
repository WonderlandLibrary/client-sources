package net.minecraft.src;

import java.util.*;

public class ContainerRepair extends Container
{
    private IInventory outputSlot;
    private IInventory inputSlots;
    private World theWorld;
    private int field_82861_i;
    private int field_82858_j;
    private int field_82859_k;
    public int maximumCost;
    private int stackSizeToBeUsedInRepair;
    private String repairedItemName;
    private final EntityPlayer thePlayer;
    
    public ContainerRepair(final InventoryPlayer par1InventoryPlayer, final World par2World, final int par3, final int par4, final int par5, final EntityPlayer par6EntityPlayer) {
        this.outputSlot = new InventoryCraftResult();
        this.inputSlots = new InventoryRepair(this, "Repair", true, 2);
        this.maximumCost = 0;
        this.stackSizeToBeUsedInRepair = 0;
        this.theWorld = par2World;
        this.field_82861_i = par3;
        this.field_82858_j = par4;
        this.field_82859_k = par5;
        this.thePlayer = par6EntityPlayer;
        this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
        this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
        this.addSlotToContainer(new SlotRepair(this, this.outputSlot, 2, 134, 47, par2World, par3, par4, par5));
        for (int var7 = 0; var7 < 3; ++var7) {
            for (int var8 = 0; var8 < 9; ++var8) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var8 + var7 * 9 + 9, 8 + var8 * 18, 84 + var7 * 18));
            }
        }
        for (int var7 = 0; var7 < 9; ++var7) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var7, 8 + var7 * 18, 142));
        }
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory par1IInventory) {
        super.onCraftMatrixChanged(par1IInventory);
        if (par1IInventory == this.inputSlots) {
            this.updateRepairOutput();
        }
    }
    
    public void updateRepairOutput() {
        final ItemStack var1 = this.inputSlots.getStackInSlot(0);
        this.maximumCost = 0;
        int var2 = 0;
        final byte var3 = 0;
        int var4 = 0;
        if (var1 == null) {
            this.outputSlot.setInventorySlotContents(0, null);
            this.maximumCost = 0;
        }
        else {
            ItemStack var5 = var1.copy();
            final ItemStack var6 = this.inputSlots.getStackInSlot(1);
            final Map var7 = EnchantmentHelper.getEnchantments(var5);
            boolean var8 = false;
            int var9 = var3 + var1.getRepairCost() + ((var6 == null) ? 0 : var6.getRepairCost());
            this.stackSizeToBeUsedInRepair = 0;
            if (var6 != null) {
                var8 = (var6.itemID == Item.enchantedBook.itemID && Item.enchantedBook.func_92110_g(var6).tagCount() > 0);
                if (var5.isItemStackDamageable() && Item.itemsList[var5.itemID].getIsRepairable(var1, var6)) {
                    int var10 = Math.min(var5.getItemDamageForDisplay(), var5.getMaxDamage() / 4);
                    if (var10 <= 0) {
                        this.outputSlot.setInventorySlotContents(0, null);
                        this.maximumCost = 0;
                        return;
                    }
                    int var11;
                    for (var11 = 0; var10 > 0 && var11 < var6.stackSize; var10 = Math.min(var5.getItemDamageForDisplay(), var5.getMaxDamage() / 4), ++var11) {
                        final int var12 = var5.getItemDamageForDisplay() - var10;
                        var5.setItemDamage(var12);
                        var2 += Math.max(1, var10 / 100) + var7.size();
                    }
                    this.stackSizeToBeUsedInRepair = var11;
                }
                else {
                    if (!var8 && (var5.itemID != var6.itemID || !var5.isItemStackDamageable())) {
                        this.outputSlot.setInventorySlotContents(0, null);
                        this.maximumCost = 0;
                        return;
                    }
                    if (var5.isItemStackDamageable() && !var8) {
                        final int var10 = var1.getMaxDamage() - var1.getItemDamageForDisplay();
                        final int var11 = var6.getMaxDamage() - var6.getItemDamageForDisplay();
                        final int var12 = var11 + var5.getMaxDamage() * 12 / 100;
                        final int var13 = var10 + var12;
                        int var14 = var5.getMaxDamage() - var13;
                        if (var14 < 0) {
                            var14 = 0;
                        }
                        if (var14 < var5.getItemDamage()) {
                            var5.setItemDamage(var14);
                            var2 += Math.max(1, var12 / 100);
                        }
                    }
                    final Map var15 = EnchantmentHelper.getEnchantments(var6);
                    final Iterator var16 = var15.keySet().iterator();
                    while (var16.hasNext()) {
                        final int var12 = var16.next();
                        final Enchantment var17 = Enchantment.enchantmentsList[var12];
                        final int var14 = var7.containsKey(var12) ? var7.get(var12) : 0;
                        int var18 = var15.get(var12);
                        int var19;
                        if (var14 == var18) {
                            var19 = ++var18;
                        }
                        else {
                            var19 = Math.max(var18, var14);
                        }
                        var18 = var19;
                        final int var20 = var18 - var14;
                        boolean var21 = var17.canApply(var1);
                        if (this.thePlayer.capabilities.isCreativeMode || var1.itemID == ItemEnchantedBook.enchantedBook.itemID) {
                            var21 = true;
                        }
                        for (final int var23 : var7.keySet()) {
                            if (var23 != var12 && !var17.canApplyTogether(Enchantment.enchantmentsList[var23])) {
                                var21 = false;
                                var2 += var20;
                            }
                        }
                        if (var21) {
                            if (var18 > var17.getMaxLevel()) {
                                var18 = var17.getMaxLevel();
                            }
                            var7.put(var12, var18);
                            int var24 = 0;
                            switch (var17.getWeight()) {
                                case 1: {
                                    var24 = 8;
                                    break;
                                }
                                case 2: {
                                    var24 = 4;
                                    break;
                                }
                                case 5: {
                                    var24 = 2;
                                    break;
                                }
                                case 10: {
                                    var24 = 1;
                                    break;
                                }
                            }
                            if (var8) {
                                var24 = Math.max(1, var24 / 2);
                            }
                            var2 += var24 * var20;
                        }
                    }
                }
            }
            if (this.repairedItemName != null && this.repairedItemName.length() > 0 && !this.repairedItemName.equalsIgnoreCase(this.thePlayer.getTranslator().translateNamedKey(var1.getItemName())) && !this.repairedItemName.equals(var1.getDisplayName())) {
                var4 = (var1.isItemStackDamageable() ? 7 : (var1.stackSize * 5));
                var2 += var4;
                if (var1.hasDisplayName()) {
                    var9 += var4 / 2;
                }
                var5.setItemName(this.repairedItemName);
            }
            int var10 = 0;
            final Iterator var16 = var7.keySet().iterator();
            while (var16.hasNext()) {
                final int var12 = var16.next();
                final Enchantment var17 = Enchantment.enchantmentsList[var12];
                final int var14 = var7.get(var12);
                int var18 = 0;
                ++var10;
                switch (var17.getWeight()) {
                    case 1: {
                        var18 = 8;
                        break;
                    }
                    case 2: {
                        var18 = 4;
                        break;
                    }
                    case 5: {
                        var18 = 2;
                        break;
                    }
                    case 10: {
                        var18 = 1;
                        break;
                    }
                }
                if (var8) {
                    var18 = Math.max(1, var18 / 2);
                }
                var9 += var10 + var14 * var18;
            }
            if (var8) {
                var9 = Math.max(1, var9 / 2);
            }
            this.maximumCost = var9 + var2;
            if (var2 <= 0) {
                var5 = null;
            }
            if (var4 == var2 && var4 > 0 && this.maximumCost >= 40) {
                this.theWorld.getWorldLogAgent().logInfo("Naming an item only, cost too high; giving discount to cap cost to 39 levels");
                this.maximumCost = 39;
            }
            if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode) {
                var5 = null;
            }
            if (var5 != null) {
                int var11 = var5.getRepairCost();
                if (var6 != null && var11 < var6.getRepairCost()) {
                    var11 = var6.getRepairCost();
                }
                if (var5.hasDisplayName()) {
                    var11 -= 9;
                }
                if (var11 < 0) {
                    var11 = 0;
                }
                var11 += 2;
                var5.setRepairCost(var11);
                EnchantmentHelper.setEnchantments(var7, var5);
            }
            this.outputSlot.setInventorySlotContents(0, var5);
            this.detectAndSendChanges();
        }
    }
    
    @Override
    public void addCraftingToCrafters(final ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.maximumCost);
    }
    
    @Override
    public void updateProgressBar(final int par1, final int par2) {
        if (par1 == 0) {
            this.maximumCost = par2;
        }
    }
    
    @Override
    public void onCraftGuiClosed(final EntityPlayer par1EntityPlayer) {
        super.onCraftGuiClosed(par1EntityPlayer);
        if (!this.theWorld.isRemote) {
            for (int var2 = 0; var2 < this.inputSlots.getSizeInventory(); ++var2) {
                final ItemStack var3 = this.inputSlots.getStackInSlotOnClosing(var2);
                if (var3 != null) {
                    par1EntityPlayer.dropPlayerItem(var3);
                }
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return this.theWorld.getBlockId(this.field_82861_i, this.field_82858_j, this.field_82859_k) == Block.anvil.blockID && par1EntityPlayer.getDistanceSq(this.field_82861_i + 0.5, this.field_82858_j + 0.5, this.field_82859_k + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 == 2) {
                if (!this.mergeItemStack(var5, 3, 39, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            }
            else if (par2 != 0 && par2 != 1) {
                if (par2 >= 3 && par2 < 39 && !this.mergeItemStack(var5, 0, 2, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 3, 39, false)) {
                return null;
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
    
    public void updateItemName(final String par1Str) {
        this.repairedItemName = par1Str;
        if (this.getSlot(2).getHasStack()) {
            this.getSlot(2).getStack().setItemName(this.repairedItemName);
        }
        this.updateRepairOutput();
    }
    
    static IInventory getRepairInputInventory(final ContainerRepair par0ContainerRepair) {
        return par0ContainerRepair.inputSlots;
    }
    
    static int getStackSizeUsedInRepair(final ContainerRepair par0ContainerRepair) {
        return par0ContainerRepair.stackSizeToBeUsedInRepair;
    }
}
