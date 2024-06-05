package net.minecraft.src;

public class RecipesTools
{
    private String[][] recipePatterns;
    private Object[][] recipeItems;
    
    public RecipesTools() {
        this.recipePatterns = new String[][] { { "XXX", " # ", " # " }, { "X", "#", "#" }, { "XX", "X#", " #" }, { "XX", " #", " #" } };
        this.recipeItems = new Object[][] { { Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold }, { Item.pickaxeWood, Item.pickaxeStone, Item.pickaxeIron, Item.pickaxeDiamond, Item.pickaxeGold }, { Item.shovelWood, Item.shovelStone, Item.shovelIron, Item.shovelDiamond, Item.shovelGold }, { Item.axeWood, Item.axeStone, Item.axeIron, Item.axeDiamond, Item.axeGold }, { Item.hoeWood, Item.hoeStone, Item.hoeIron, Item.hoeDiamond, Item.hoeGold } };
    }
    
    public void addRecipes(final CraftingManager par1CraftingManager) {
        for (int var2 = 0; var2 < this.recipeItems[0].length; ++var2) {
            final Object var3 = this.recipeItems[0][var2];
            for (int var4 = 0; var4 < this.recipeItems.length - 1; ++var4) {
                final Item var5 = (Item)this.recipeItems[var4 + 1][var2];
                par1CraftingManager.addRecipe(new ItemStack(var5), this.recipePatterns[var4], '#', Item.stick, 'X', var3);
            }
        }
        par1CraftingManager.addRecipe(new ItemStack(Item.shears), " #", "# ", '#', Item.ingotIron);
    }
}
