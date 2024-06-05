package net.minecraft.src;

public class RecipesMapCloning implements IRecipe
{
    @Override
    public boolean matches(final InventoryCrafting par1InventoryCrafting, final World par2World) {
        int var3 = 0;
        ItemStack var4 = null;
        for (int var5 = 0; var5 < par1InventoryCrafting.getSizeInventory(); ++var5) {
            final ItemStack var6 = par1InventoryCrafting.getStackInSlot(var5);
            if (var6 != null) {
                if (var6.itemID == Item.map.itemID) {
                    if (var4 != null) {
                        return false;
                    }
                    var4 = var6;
                }
                else {
                    if (var6.itemID != Item.emptyMap.itemID) {
                        return false;
                    }
                    ++var3;
                }
            }
        }
        return var4 != null && var3 > 0;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting par1InventoryCrafting) {
        int var2 = 0;
        ItemStack var3 = null;
        for (int var4 = 0; var4 < par1InventoryCrafting.getSizeInventory(); ++var4) {
            final ItemStack var5 = par1InventoryCrafting.getStackInSlot(var4);
            if (var5 != null) {
                if (var5.itemID == Item.map.itemID) {
                    if (var3 != null) {
                        return null;
                    }
                    var3 = var5;
                }
                else {
                    if (var5.itemID != Item.emptyMap.itemID) {
                        return null;
                    }
                    ++var2;
                }
            }
        }
        if (var3 != null && var2 >= 1) {
            final ItemStack var6 = new ItemStack(Item.map, var2 + 1, var3.getItemDamage());
            if (var3.hasDisplayName()) {
                var6.setItemName(var3.getDisplayName());
            }
            return var6;
        }
        return null;
    }
    
    @Override
    public int getRecipeSize() {
        return 9;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
