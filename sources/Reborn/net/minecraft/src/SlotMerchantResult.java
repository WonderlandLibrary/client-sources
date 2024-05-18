package net.minecraft.src;

public class SlotMerchantResult extends Slot
{
    private final InventoryMerchant theMerchantInventory;
    private EntityPlayer thePlayer;
    private int field_75231_g;
    private final IMerchant theMerchant;
    
    public SlotMerchantResult(final EntityPlayer par1EntityPlayer, final IMerchant par2IMerchant, final InventoryMerchant par3InventoryMerchant, final int par4, final int par5, final int par6) {
        super(par3InventoryMerchant, par4, par5, par6);
        this.thePlayer = par1EntityPlayer;
        this.theMerchant = par2IMerchant;
        this.theMerchantInventory = par3InventoryMerchant;
    }
    
    @Override
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(final int par1) {
        if (this.getHasStack()) {
            this.field_75231_g += Math.min(par1, this.getStack().stackSize);
        }
        return super.decrStackSize(par1);
    }
    
    @Override
    protected void onCrafting(final ItemStack par1ItemStack, final int par2) {
        this.field_75231_g += par2;
        this.onCrafting(par1ItemStack);
    }
    
    @Override
    protected void onCrafting(final ItemStack par1ItemStack) {
        par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75231_g);
        this.field_75231_g = 0;
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack) {
        this.onCrafting(par2ItemStack);
        final MerchantRecipe var3 = this.theMerchantInventory.getCurrentRecipe();
        if (var3 != null) {
            ItemStack var4 = this.theMerchantInventory.getStackInSlot(0);
            ItemStack var5 = this.theMerchantInventory.getStackInSlot(1);
            if (this.func_75230_a(var3, var4, var5) || this.func_75230_a(var3, var5, var4)) {
                if (var4 != null && var4.stackSize <= 0) {
                    var4 = null;
                }
                if (var5 != null && var5.stackSize <= 0) {
                    var5 = null;
                }
                this.theMerchantInventory.setInventorySlotContents(0, var4);
                this.theMerchantInventory.setInventorySlotContents(1, var5);
                this.theMerchant.useRecipe(var3);
            }
        }
    }
    
    private boolean func_75230_a(final MerchantRecipe par1MerchantRecipe, final ItemStack par2ItemStack, final ItemStack par3ItemStack) {
        final ItemStack var4 = par1MerchantRecipe.getItemToBuy();
        final ItemStack var5 = par1MerchantRecipe.getSecondItemToBuy();
        if (par2ItemStack != null && par2ItemStack.itemID == var4.itemID) {
            if (var5 != null && par3ItemStack != null && var5.itemID == par3ItemStack.itemID) {
                par2ItemStack.stackSize -= var4.stackSize;
                par3ItemStack.stackSize -= var5.stackSize;
                return true;
            }
            if (var5 == null && par3ItemStack == null) {
                par2ItemStack.stackSize -= var4.stackSize;
                return true;
            }
        }
        return false;
    }
}
