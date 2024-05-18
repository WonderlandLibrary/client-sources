package net.minecraft.src;

import java.util.*;

public class ShapelessRecipes implements IRecipe
{
    private final ItemStack recipeOutput;
    private final List recipeItems;
    
    public ShapelessRecipes(final ItemStack par1ItemStack, final List par2List) {
        this.recipeOutput = par1ItemStack;
        this.recipeItems = par2List;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }
    
    @Override
    public boolean matches(final InventoryCrafting par1InventoryCrafting, final World par2World) {
        final ArrayList var3 = new ArrayList(this.recipeItems);
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 3; ++var5) {
                final ItemStack var6 = par1InventoryCrafting.getStackInRowAndColumn(var5, var4);
                if (var6 != null) {
                    boolean var7 = false;
                    for (final ItemStack var9 : var3) {
                        if (var6.itemID == var9.itemID && (var9.getItemDamage() == 32767 || var6.getItemDamage() == var9.getItemDamage())) {
                            var7 = true;
                            var3.remove(var9);
                            break;
                        }
                    }
                    if (!var7) {
                        return false;
                    }
                }
            }
        }
        return var3.isEmpty();
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting par1InventoryCrafting) {
        return this.recipeOutput.copy();
    }
    
    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }
}
