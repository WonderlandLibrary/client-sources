package net.minecraft.src;

public class NpcMerchant implements IMerchant
{
    private InventoryMerchant theMerchantInventory;
    private EntityPlayer customer;
    private MerchantRecipeList recipeList;
    
    public NpcMerchant(final EntityPlayer par1EntityPlayer) {
        this.customer = par1EntityPlayer;
        this.theMerchantInventory = new InventoryMerchant(par1EntityPlayer, this);
    }
    
    @Override
    public EntityPlayer getCustomer() {
        return this.customer;
    }
    
    @Override
    public void setCustomer(final EntityPlayer par1EntityPlayer) {
    }
    
    @Override
    public MerchantRecipeList getRecipes(final EntityPlayer par1EntityPlayer) {
        return this.recipeList;
    }
    
    @Override
    public void setRecipes(final MerchantRecipeList par1MerchantRecipeList) {
        this.recipeList = par1MerchantRecipeList;
    }
    
    @Override
    public void useRecipe(final MerchantRecipe par1MerchantRecipe) {
    }
}
