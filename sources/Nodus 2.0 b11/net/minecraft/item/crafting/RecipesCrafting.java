/*  1:   */ package net.minecraft.item.crafting;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Blocks;
/*  4:   */ import net.minecraft.init.Items;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class RecipesCrafting
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000095";
/* 10:   */   
/* 11:   */   public void addRecipes(CraftingManager par1CraftingManager)
/* 12:   */   {
/* 13:16 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.chest), new Object[] { "###", "# #", "###", Character.valueOf('#'), Blocks.planks });
/* 14:17 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.trapped_chest), new Object[] { "#-", Character.valueOf('#'), Blocks.chest, Character.valueOf('-'), Blocks.tripwire_hook });
/* 15:18 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.ender_chest), new Object[] { "###", "#E#", "###", Character.valueOf('#'), Blocks.obsidian, Character.valueOf('E'), Items.ender_eye });
/* 16:19 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.furnace), new Object[] { "###", "# #", "###", Character.valueOf('#'), Blocks.cobblestone });
/* 17:20 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.crafting_table), new Object[] { "##", "##", Character.valueOf('#'), Blocks.planks });
/* 18:21 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.sandstone), new Object[] { "##", "##", Character.valueOf('#'), new ItemStack(Blocks.sand, 1, 0) });
/* 19:22 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.sandstone, 4, 2), new Object[] { "##", "##", Character.valueOf('#'), Blocks.sandstone });
/* 20:23 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.sandstone, 1, 1), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack(Blocks.stone_slab, 1, 1) });
/* 21:24 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.quartz_block, 1, 1), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack(Blocks.stone_slab, 1, 7) });
/* 22:25 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.quartz_block, 2, 2), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack(Blocks.quartz_block, 1, 0) });
/* 23:26 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.stonebrick, 4), new Object[] { "##", "##", Character.valueOf('#'), Blocks.stone });
/* 24:27 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.iron_bars, 16), new Object[] { "###", "###", Character.valueOf('#'), Items.iron_ingot });
/* 25:28 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.glass_pane, 16), new Object[] { "###", "###", Character.valueOf('#'), Blocks.glass });
/* 26:29 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.redstone_lamp, 1), new Object[] { " R ", "RGR", " R ", Character.valueOf('R'), Items.redstone, Character.valueOf('G'), Blocks.glowstone });
/* 27:30 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.beacon, 1), new Object[] { "GGG", "GSG", "OOO", Character.valueOf('G'), Blocks.glass, Character.valueOf('S'), Items.nether_star, Character.valueOf('O'), Blocks.obsidian });
/* 28:31 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.nether_brick, 1), new Object[] { "NN", "NN", Character.valueOf('N'), Items.netherbrick });
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipesCrafting
 * JD-Core Version:    0.7.0.1
 */