package net.minecraft.src;

public class RecipesCrafting
{
    public void addRecipes(final CraftingManager par1CraftingManager) {
        par1CraftingManager.addRecipe(new ItemStack(Block.chest), "###", "# #", "###", '#', Block.planks);
        par1CraftingManager.addRecipe(new ItemStack(Block.chestTrapped), "#-", '#', Block.chest, '-', Block.tripWireSource);
        par1CraftingManager.addRecipe(new ItemStack(Block.enderChest), "###", "#E#", "###", '#', Block.obsidian, 'E', Item.eyeOfEnder);
        par1CraftingManager.addRecipe(new ItemStack(Block.furnaceIdle), "###", "# #", "###", '#', Block.cobblestone);
        par1CraftingManager.addRecipe(new ItemStack(Block.workbench), "##", "##", '#', Block.planks);
        par1CraftingManager.addRecipe(new ItemStack(Block.sandStone), "##", "##", '#', Block.sand);
        par1CraftingManager.addRecipe(new ItemStack(Block.sandStone, 4, 2), "##", "##", '#', Block.sandStone);
        par1CraftingManager.addRecipe(new ItemStack(Block.sandStone, 1, 1), "#", "#", '#', new ItemStack(Block.stoneSingleSlab, 1, 1));
        par1CraftingManager.addRecipe(new ItemStack(Block.blockNetherQuartz, 1, 1), "#", "#", '#', new ItemStack(Block.stoneSingleSlab, 1, 7));
        par1CraftingManager.addRecipe(new ItemStack(Block.blockNetherQuartz, 2, 2), "#", "#", '#', new ItemStack(Block.blockNetherQuartz, 1, 0));
        par1CraftingManager.addRecipe(new ItemStack(Block.stoneBrick, 4), "##", "##", '#', Block.stone);
        par1CraftingManager.addRecipe(new ItemStack(Block.fenceIron, 16), "###", "###", '#', Item.ingotIron);
        par1CraftingManager.addRecipe(new ItemStack(Block.thinGlass, 16), "###", "###", '#', Block.glass);
        par1CraftingManager.addRecipe(new ItemStack(Block.redstoneLampIdle, 1), " R ", "RGR", " R ", 'R', Item.redstone, 'G', Block.glowStone);
        par1CraftingManager.addRecipe(new ItemStack(Block.beacon, 1), "GGG", "GSG", "OOO", 'G', Block.glass, 'S', Item.netherStar, 'O', Block.obsidian);
        par1CraftingManager.addRecipe(new ItemStack(Block.netherBrick, 1), "NN", "NN", 'N', Item.netherrackBrick);
    }
}
