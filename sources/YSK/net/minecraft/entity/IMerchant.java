package net.minecraft.entity;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.village.*;
import net.minecraft.item.*;

public interface IMerchant
{
    void setRecipes(final MerchantRecipeList p0);
    
    IChatComponent getDisplayName();
    
    EntityPlayer getCustomer();
    
    void useRecipe(final MerchantRecipe p0);
    
    void verifySellingItem(final ItemStack p0);
    
    void setCustomer(final EntityPlayer p0);
    
    MerchantRecipeList getRecipes(final EntityPlayer p0);
}
