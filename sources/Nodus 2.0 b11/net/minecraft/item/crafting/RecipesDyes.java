/*  1:   */ package net.minecraft.item.crafting;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.BlockColored;
/*  4:   */ import net.minecraft.init.Blocks;
/*  5:   */ import net.minecraft.init.Items;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ 
/*  9:   */ public class RecipesDyes
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000082";
/* 12:   */   
/* 13:   */   public void addRecipes(CraftingManager par1CraftingManager)
/* 14:   */   {
/* 15:20 */     for (int var2 = 0; var2 < 16; var2++)
/* 16:   */     {
/* 17:22 */       par1CraftingManager.addShapelessRecipe(new ItemStack(Blocks.wool, 1, BlockColored.func_150031_c(var2)), new Object[] { new ItemStack(Items.dye, 1, var2), new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0) });
/* 18:23 */       par1CraftingManager.addRecipe(new ItemStack(Blocks.stained_hardened_clay, 8, BlockColored.func_150031_c(var2)), new Object[] { "###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.hardened_clay), Character.valueOf('X'), new ItemStack(Items.dye, 1, var2) });
/* 19:24 */       par1CraftingManager.addRecipe(new ItemStack(Blocks.stained_glass, 8, BlockColored.func_150031_c(var2)), new Object[] { "###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.glass), Character.valueOf('X'), new ItemStack(Items.dye, 1, var2) });
/* 20:25 */       par1CraftingManager.addRecipe(new ItemStack(Blocks.stained_glass_pane, 16, var2), new Object[] { "###", "###", Character.valueOf('#'), new ItemStack(Blocks.stained_glass, 1, var2) });
/* 21:   */     }
/* 22:28 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, 11), new Object[] { new ItemStack(Blocks.yellow_flower, 1, 0) });
/* 23:29 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, 1), new Object[] { new ItemStack(Blocks.red_flower, 1, 0) });
/* 24:30 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 3, 15), new Object[] { Items.bone });
/* 25:31 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 9), new Object[] { new ItemStack(Items.dye, 1, 1), new ItemStack(Items.dye, 1, 15) });
/* 26:32 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 14), new Object[] { new ItemStack(Items.dye, 1, 1), new ItemStack(Items.dye, 1, 11) });
/* 27:33 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 10), new Object[] { new ItemStack(Items.dye, 1, 2), new ItemStack(Items.dye, 1, 15) });
/* 28:34 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 8), new Object[] { new ItemStack(Items.dye, 1, 0), new ItemStack(Items.dye, 1, 15) });
/* 29:35 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 7), new Object[] { new ItemStack(Items.dye, 1, 8), new ItemStack(Items.dye, 1, 15) });
/* 30:36 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 3, 7), new Object[] { new ItemStack(Items.dye, 1, 0), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15) });
/* 31:37 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 12), new Object[] { new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 15) });
/* 32:38 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 6), new Object[] { new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 2) });
/* 33:39 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 5), new Object[] { new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 1) });
/* 34:40 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 13), new Object[] { new ItemStack(Items.dye, 1, 5), new ItemStack(Items.dye, 1, 9) });
/* 35:41 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 3, 13), new Object[] { new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 1), new ItemStack(Items.dye, 1, 9) });
/* 36:42 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 4, 13), new Object[] { new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 1), new ItemStack(Items.dye, 1, 1), new ItemStack(Items.dye, 1, 15) });
/* 37:43 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, 12), new Object[] { new ItemStack(Blocks.red_flower, 1, 1) });
/* 38:44 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, 13), new Object[] { new ItemStack(Blocks.red_flower, 1, 2) });
/* 39:45 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, 7), new Object[] { new ItemStack(Blocks.red_flower, 1, 3) });
/* 40:46 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, 1), new Object[] { new ItemStack(Blocks.red_flower, 1, 4) });
/* 41:47 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new Object[] { new ItemStack(Blocks.red_flower, 1, 5) });
/* 42:48 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, 7), new Object[] { new ItemStack(Blocks.red_flower, 1, 6) });
/* 43:49 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, 9), new Object[] { new ItemStack(Blocks.red_flower, 1, 7) });
/* 44:50 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 1, 7), new Object[] { new ItemStack(Blocks.red_flower, 1, 8) });
/* 45:51 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 11), new Object[] { new ItemStack(Blocks.double_plant, 1, 0) });
/* 46:52 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 13), new Object[] { new ItemStack(Blocks.double_plant, 1, 1) });
/* 47:53 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 1), new Object[] { new ItemStack(Blocks.double_plant, 1, 4) });
/* 48:54 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.dye, 2, 9), new Object[] { new ItemStack(Blocks.double_plant, 1, 5) });
/* 49:56 */     for (var2 = 0; var2 < 16; var2++) {
/* 50:58 */       par1CraftingManager.addRecipe(new ItemStack(Blocks.carpet, 3, var2), new Object[] { "##", Character.valueOf('#'), new ItemStack(Blocks.wool, 1, var2) });
/* 51:   */     }
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipesDyes
 * JD-Core Version:    0.7.0.1
 */