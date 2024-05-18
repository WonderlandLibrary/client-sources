package net.minecraft.src;

public class ContainerPlayer extends Container
{
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    public boolean isLocalWorld;
    private final EntityPlayer thePlayer;
    
    public ContainerPlayer(final InventoryPlayer par1InventoryPlayer, final boolean par2, final EntityPlayer par3EntityPlayer) {
        this.craftMatrix = new InventoryCrafting(this, 2, 2);
        this.craftResult = new InventoryCraftResult();
        this.isLocalWorld = false;
        this.isLocalWorld = par2;
        this.thePlayer = par3EntityPlayer;
        this.addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 144, 36));
        for (int var4 = 0; var4 < 2; ++var4) {
            for (int var5 = 0; var5 < 2; ++var5) {
                this.addSlotToContainer(new Slot(this.craftMatrix, var5 + var4 * 2, 88 + var5 * 18, 26 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 4; ++var4) {
            this.addSlotToContainer(new SlotArmor(this, par1InventoryPlayer, par1InventoryPlayer.getSizeInventory() - 1 - var4, 8, 8 + var4 * 18, var4));
        }
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var5 + (var4 + 1) * 9, 8 + var5 * 18, 84 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 142));
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory par1IInventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }
    
    @Override
    public void onCraftGuiClosed(final EntityPlayer par1EntityPlayer) {
        super.onCraftGuiClosed(par1EntityPlayer);
        for (int var2 = 0; var2 < 4; ++var2) {
            final ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);
            if (var3 != null) {
                par1EntityPlayer.dropPlayerItem(var3);
            }
        }
        this.craftResult.setInventorySlotContents(0, null);
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 == 0) {
                if (!this.mergeItemStack(var5, 9, 45, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            }
            else if (par2 >= 1 && par2 < 5) {
                if (!this.mergeItemStack(var5, 9, 45, false)) {
                    return null;
                }
            }
            else if (par2 >= 5 && par2 < 9) {
                if (!this.mergeItemStack(var5, 9, 45, false)) {
                    return null;
                }
            }
            else if (var3.getItem() instanceof ItemArmor && !this.inventorySlots.get(5 + ((ItemArmor)var3.getItem()).armorType).getHasStack()) {
                final int var6 = 5 + ((ItemArmor)var3.getItem()).armorType;
                if (!this.mergeItemStack(var5, var6, var6 + 1, false)) {
                    return null;
                }
            }
            else if (par2 >= 9 && par2 < 36) {
                if (!this.mergeItemStack(var5, 36, 45, false)) {
                    return null;
                }
            }
            else if (par2 >= 36 && par2 < 45) {
                if (!this.mergeItemStack(var5, 9, 36, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 9, 45, false)) {
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
    
    @Override
    public boolean func_94530_a(final ItemStack par1ItemStack, final Slot par2Slot) {
        return par2Slot.inventory != this.craftResult && super.func_94530_a(par1ItemStack, par2Slot);
    }
}
