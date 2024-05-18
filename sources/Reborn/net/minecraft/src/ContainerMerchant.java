package net.minecraft.src;

public class ContainerMerchant extends Container
{
    private IMerchant theMerchant;
    private InventoryMerchant merchantInventory;
    private final World theWorld;
    
    public ContainerMerchant(final InventoryPlayer par1InventoryPlayer, final IMerchant par2IMerchant, final World par3World) {
        this.theMerchant = par2IMerchant;
        this.theWorld = par3World;
        this.merchantInventory = new InventoryMerchant(par1InventoryPlayer.player, par2IMerchant);
        this.addSlotToContainer(new Slot(this.merchantInventory, 0, 36, 53));
        this.addSlotToContainer(new Slot(this.merchantInventory, 1, 62, 53));
        this.addSlotToContainer(new SlotMerchantResult(par1InventoryPlayer.player, par2IMerchant, this.merchantInventory, 2, 120, 53));
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 142));
        }
    }
    
    public InventoryMerchant getMerchantInventory() {
        return this.merchantInventory;
    }
    
    @Override
    public void addCraftingToCrafters(final ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory par1IInventory) {
        this.merchantInventory.resetRecipeAndSlots();
        super.onCraftMatrixChanged(par1IInventory);
    }
    
    public void setCurrentRecipeIndex(final int par1) {
        this.merchantInventory.setCurrentRecipeIndex(par1);
    }
    
    @Override
    public void updateProgressBar(final int par1, final int par2) {
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return this.theMerchant.getCustomer() == par1EntityPlayer;
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
                if (par2 >= 3 && par2 < 30) {
                    if (!this.mergeItemStack(var5, 30, 39, false)) {
                        return null;
                    }
                }
                else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(var5, 3, 30, false)) {
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
    
    @Override
    public void onCraftGuiClosed(final EntityPlayer par1EntityPlayer) {
        super.onCraftGuiClosed(par1EntityPlayer);
        this.theMerchant.setCustomer(null);
        super.onCraftGuiClosed(par1EntityPlayer);
        if (!this.theWorld.isRemote) {
            ItemStack var2 = this.merchantInventory.getStackInSlotOnClosing(0);
            if (var2 != null) {
                par1EntityPlayer.dropPlayerItem(var2);
            }
            var2 = this.merchantInventory.getStackInSlotOnClosing(1);
            if (var2 != null) {
                par1EntityPlayer.dropPlayerItem(var2);
            }
        }
    }
}
