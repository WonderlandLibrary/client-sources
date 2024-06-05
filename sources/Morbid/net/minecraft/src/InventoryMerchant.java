package net.minecraft.src;

public class InventoryMerchant implements IInventory
{
    private final IMerchant theMerchant;
    private ItemStack[] theInventory;
    private final EntityPlayer thePlayer;
    private MerchantRecipe currentRecipe;
    private int currentRecipeIndex;
    
    public InventoryMerchant(final EntityPlayer par1EntityPlayer, final IMerchant par2IMerchant) {
        this.theInventory = new ItemStack[3];
        this.thePlayer = par1EntityPlayer;
        this.theMerchant = par2IMerchant;
    }
    
    @Override
    public int getSizeInventory() {
        return this.theInventory.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return this.theInventory[par1];
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.theInventory[par1] == null) {
            return null;
        }
        if (par1 == 2) {
            final ItemStack var3 = this.theInventory[par1];
            this.theInventory[par1] = null;
            return var3;
        }
        if (this.theInventory[par1].stackSize <= par2) {
            final ItemStack var3 = this.theInventory[par1];
            this.theInventory[par1] = null;
            if (this.inventoryResetNeededOnSlotChange(par1)) {
                this.resetRecipeAndSlots();
            }
            return var3;
        }
        final ItemStack var3 = this.theInventory[par1].splitStack(par2);
        if (this.theInventory[par1].stackSize == 0) {
            this.theInventory[par1] = null;
        }
        if (this.inventoryResetNeededOnSlotChange(par1)) {
            this.resetRecipeAndSlots();
        }
        return var3;
    }
    
    private boolean inventoryResetNeededOnSlotChange(final int par1) {
        return par1 == 0 || par1 == 1;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.theInventory[par1] != null) {
            final ItemStack var2 = this.theInventory[par1];
            this.theInventory[par1] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.theInventory[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
        if (this.inventoryResetNeededOnSlotChange(par1)) {
            this.resetRecipeAndSlots();
        }
    }
    
    @Override
    public String getInvName() {
        return "mob.villager";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return false;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return this.theMerchant.getCustomer() == par1EntityPlayer;
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
    
    @Override
    public void onInventoryChanged() {
        this.resetRecipeAndSlots();
    }
    
    public void resetRecipeAndSlots() {
        this.currentRecipe = null;
        ItemStack var1 = this.theInventory[0];
        ItemStack var2 = this.theInventory[1];
        if (var1 == null) {
            var1 = var2;
            var2 = null;
        }
        if (var1 == null) {
            this.setInventorySlotContents(2, null);
        }
        else {
            final MerchantRecipeList var3 = this.theMerchant.getRecipes(this.thePlayer);
            if (var3 != null) {
                MerchantRecipe var4 = var3.canRecipeBeUsed(var1, var2, this.currentRecipeIndex);
                if (var4 != null && !var4.func_82784_g()) {
                    this.currentRecipe = var4;
                    this.setInventorySlotContents(2, var4.getItemToSell().copy());
                }
                else if (var2 != null) {
                    var4 = var3.canRecipeBeUsed(var2, var1, this.currentRecipeIndex);
                    if (var4 != null && !var4.func_82784_g()) {
                        this.currentRecipe = var4;
                        this.setInventorySlotContents(2, var4.getItemToSell().copy());
                    }
                    else {
                        this.setInventorySlotContents(2, null);
                    }
                }
                else {
                    this.setInventorySlotContents(2, null);
                }
            }
        }
    }
    
    public MerchantRecipe getCurrentRecipe() {
        return this.currentRecipe;
    }
    
    public void setCurrentRecipeIndex(final int par1) {
        this.currentRecipeIndex = par1;
        this.resetRecipeAndSlots();
    }
}
