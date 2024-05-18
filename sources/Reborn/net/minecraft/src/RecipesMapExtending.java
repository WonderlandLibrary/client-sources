package net.minecraft.src;

public class RecipesMapExtending extends ShapedRecipes
{
    public RecipesMapExtending() {
        super(3, 3, new ItemStack[] { new ItemStack(Item.paper), new ItemStack(Item.paper), new ItemStack(Item.paper), new ItemStack(Item.paper), new ItemStack(Item.map, 0, 32767), new ItemStack(Item.paper), new ItemStack(Item.paper), new ItemStack(Item.paper), new ItemStack(Item.paper) }, new ItemStack(Item.emptyMap, 0, 0));
    }
    
    @Override
    public boolean matches(final InventoryCrafting par1InventoryCrafting, final World par2World) {
        if (!super.matches(par1InventoryCrafting, par2World)) {
            return false;
        }
        ItemStack var3 = null;
        for (int var4 = 0; var4 < par1InventoryCrafting.getSizeInventory() && var3 == null; ++var4) {
            final ItemStack var5 = par1InventoryCrafting.getStackInSlot(var4);
            if (var5 != null && var5.itemID == Item.map.itemID) {
                var3 = var5;
            }
        }
        if (var3 == null) {
            return false;
        }
        final MapData var6 = Item.map.getMapData(var3, par2World);
        return var6 != null && var6.scale < 4;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting par1InventoryCrafting) {
        ItemStack var2 = null;
        for (int var3 = 0; var3 < par1InventoryCrafting.getSizeInventory() && var2 == null; ++var3) {
            final ItemStack var4 = par1InventoryCrafting.getStackInSlot(var3);
            if (var4 != null && var4.itemID == Item.map.itemID) {
                var2 = var4;
            }
        }
        var2 = var2.copy();
        var2.stackSize = 1;
        if (var2.getTagCompound() == null) {
            var2.setTagCompound(new NBTTagCompound());
        }
        var2.getTagCompound().setBoolean("map_is_scaling", true);
        return var2;
    }
}
