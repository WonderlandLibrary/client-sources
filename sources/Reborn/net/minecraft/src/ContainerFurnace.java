package net.minecraft.src;

public class ContainerFurnace extends Container
{
    private TileEntityFurnace furnace;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
    
    public ContainerFurnace(final InventoryPlayer par1InventoryPlayer, final TileEntityFurnace par2TileEntityFurnace) {
        this.lastCookTime = 0;
        this.lastBurnTime = 0;
        this.lastItemBurnTime = 0;
        this.furnace = par2TileEntityFurnace;
        this.addSlotToContainer(new Slot(par2TileEntityFurnace, 0, 56, 17));
        this.addSlotToContainer(new Slot(par2TileEntityFurnace, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, par2TileEntityFurnace, 2, 116, 35));
        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }
    
    @Override
    public void addCraftingToCrafters(final ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int var1 = 0; var1 < this.crafters.size(); ++var1) {
            final ICrafting var2 = this.crafters.get(var1);
            if (this.lastCookTime != this.furnace.furnaceCookTime) {
                var2.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
            }
            if (this.lastBurnTime != this.furnace.furnaceBurnTime) {
                var2.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
            }
            if (this.lastItemBurnTime != this.furnace.currentItemBurnTime) {
                var2.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
            }
        }
        this.lastCookTime = this.furnace.furnaceCookTime;
        this.lastBurnTime = this.furnace.furnaceBurnTime;
        this.lastItemBurnTime = this.furnace.currentItemBurnTime;
    }
    
    @Override
    public void updateProgressBar(final int par1, final int par2) {
        if (par1 == 0) {
            this.furnace.furnaceCookTime = par2;
        }
        if (par1 == 1) {
            this.furnace.furnaceBurnTime = par2;
        }
        if (par1 == 2) {
            this.furnace.currentItemBurnTime = par2;
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return this.furnace.isUseableByPlayer(par1EntityPlayer);
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
            else if (par2 != 1 && par2 != 0) {
                if (FurnaceRecipes.smelting().getSmeltingResult(var5.getItem().itemID) != null) {
                    if (!this.mergeItemStack(var5, 0, 1, false)) {
                        return null;
                    }
                }
                else if (TileEntityFurnace.isItemFuel(var5)) {
                    if (!this.mergeItemStack(var5, 1, 2, false)) {
                        return null;
                    }
                }
                else if (par2 >= 3 && par2 < 30) {
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
}
