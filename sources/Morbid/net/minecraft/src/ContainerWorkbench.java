package net.minecraft.src;

public class ContainerWorkbench extends Container
{
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;
    
    public ContainerWorkbench(final InventoryPlayer par1InventoryPlayer, final World par2World, final int par3, final int par4, final int par5) {
        this.craftMatrix = new InventoryCrafting(this, 3, 3);
        this.craftResult = new InventoryCraftResult();
        this.worldObj = par2World;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
        this.addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        for (int var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 3; ++var7) {
                this.addSlotToContainer(new Slot(this.craftMatrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
            }
        }
        for (int var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }
        for (int var6 = 0; var6 < 9; ++var6) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory par1IInventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }
    
    @Override
    public void onCraftGuiClosed(final EntityPlayer par1EntityPlayer) {
        super.onCraftGuiClosed(par1EntityPlayer);
        if (!this.worldObj.isRemote) {
            for (int var2 = 0; var2 < 9; ++var2) {
                final ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);
                if (var3 != null) {
                    par1EntityPlayer.dropPlayerItem(var3);
                }
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlockId(this.posX, this.posY, this.posZ) == Block.workbench.blockID && par1EntityPlayer.getDistanceSq(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 == 0) {
                if (!this.mergeItemStack(var5, 10, 46, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            }
            else if (par2 >= 10 && par2 < 37) {
                if (!this.mergeItemStack(var5, 37, 46, false)) {
                    return null;
                }
            }
            else if (par2 >= 37 && par2 < 46) {
                if (!this.mergeItemStack(var5, 10, 37, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 10, 46, false)) {
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
