package net.minecraft.src;

public class RecipesFood
{
    public void addRecipes(final CraftingManager par1CraftingManager) {
        par1CraftingManager.addShapelessRecipe(new ItemStack(Item.bowlSoup), Block.mushroomBrown, Block.mushroomRed, Item.bowlEmpty);
        par1CraftingManager.addRecipe(new ItemStack(Item.cookie, 8), "#X#", 'X', new ItemStack(Item.dyePowder, 1, 3), '#', Item.wheat);
        par1CraftingManager.addRecipe(new ItemStack(Block.melon), "MMM", "MMM", "MMM", 'M', Item.melon);
        par1CraftingManager.addRecipe(new ItemStack(Item.melonSeeds), "M", 'M', Item.melon);
        par1CraftingManager.addRecipe(new ItemStack(Item.pumpkinSeeds, 4), "M", 'M', Block.pumpkin);
        par1CraftingManager.addShapelessRecipe(new ItemStack(Item.pumpkinPie), Block.pumpkin, Item.sugar, Item.egg);
        par1CraftingManager.addShapelessRecipe(new ItemStack(Item.fermentedSpiderEye), Item.spiderEye, Block.mushroomBrown, Item.sugar);
        par1CraftingManager.addShapelessRecipe(new ItemStack(Item.speckledMelon), Item.melon, Item.goldNugget);
        par1CraftingManager.addShapelessRecipe(new ItemStack(Item.blazePowder, 2), Item.blazeRod);
        par1CraftingManager.addShapelessRecipe(new ItemStack(Item.magmaCream), Item.blazePowder, Item.slimeBall);
    }
}
