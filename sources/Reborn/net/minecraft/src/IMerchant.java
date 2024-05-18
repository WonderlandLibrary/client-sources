package net.minecraft.src;

public interface IMerchant
{
    void setCustomer(final EntityPlayer p0);
    
    EntityPlayer getCustomer();
    
    MerchantRecipeList getRecipes(final EntityPlayer p0);
    
    void setRecipes(final MerchantRecipeList p0);
    
    void useRecipe(final MerchantRecipe p0);
}
