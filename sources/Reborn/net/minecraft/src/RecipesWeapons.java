package net.minecraft.src;

public class RecipesWeapons
{
    private String[][] recipePatterns;
    private Object[][] recipeItems;
    
    public RecipesWeapons() {
        this.recipePatterns = new String[][] { { "X", "X", "#" } };
        this.recipeItems = new Object[][] { { Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold }, { Item.swordWood, Item.swordStone, Item.swordIron, Item.swordDiamond, Item.swordGold } };
    }
    
    public void addRecipes(final CraftingManager par1CraftingManager) {
        for (int var2 = 0; var2 < this.recipeItems[0].length; ++var2) {
            final Object var3 = this.recipeItems[0][var2];
            for (int var4 = 0; var4 < this.recipeItems.length - 1; ++var4) {
                final Item var5 = (Item)this.recipeItems[var4 + 1][var2];
                par1CraftingManager.addRecipe(new ItemStack(var5), this.recipePatterns[var4], '#', Item.stick, 'X', var3);
            }
        }
        par1CraftingManager.addRecipe(new ItemStack(Item.bow, 1), " #X", "# X", " #X", 'X', Item.silk, '#', Item.stick);
        par1CraftingManager.addRecipe(new ItemStack(Item.arrow, 4), "X", "#", "Y", 'Y', Item.feather, 'X', Item.flint, '#', Item.stick);
    }
}
