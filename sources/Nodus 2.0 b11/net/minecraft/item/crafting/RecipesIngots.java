/*  1:   */ package net.minecraft.item.crafting;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.init.Blocks;
/*  5:   */ import net.minecraft.init.Items;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ 
/*  8:   */ public class RecipesIngots
/*  9:   */ {
/* 10:   */   private Object[][] recipeItems;
/* 11:   */   private static final String __OBFID = "CL_00000089";
/* 12:   */   
/* 13:   */   public RecipesIngots()
/* 14:   */   {
/* 15:15 */     this.recipeItems = new Object[][] { { Blocks.gold_block, new ItemStack(Items.gold_ingot, 9) }, { Blocks.iron_block, new ItemStack(Items.iron_ingot, 9) }, { Blocks.diamond_block, new ItemStack(Items.diamond, 9) }, { Blocks.emerald_block, new ItemStack(Items.emerald, 9) }, { Blocks.lapis_block, new ItemStack(Items.dye, 9, 4) }, { Blocks.redstone_block, new ItemStack(Items.redstone, 9) }, { Blocks.coal_block, new ItemStack(Items.coal, 9, 0) }, { Blocks.hay_block, new ItemStack(Items.wheat, 9) } };
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void addRecipes(CraftingManager par1CraftingManager)
/* 19:   */   {
/* 20:23 */     for (int var2 = 0; var2 < this.recipeItems.length; var2++)
/* 21:   */     {
/* 22:25 */       Block var3 = (Block)this.recipeItems[var2][0];
/* 23:26 */       ItemStack var4 = (ItemStack)this.recipeItems[var2][1];
/* 24:27 */       par1CraftingManager.addRecipe(new ItemStack(var3), new Object[] { "###", "###", "###", Character.valueOf('#'), var4 });
/* 25:28 */       par1CraftingManager.addRecipe(var4, new Object[] { "#", Character.valueOf('#'), var3 });
/* 26:   */     }
/* 27:31 */     par1CraftingManager.addRecipe(new ItemStack(Items.gold_ingot), new Object[] { "###", "###", "###", Character.valueOf('#'), Items.gold_nugget });
/* 28:32 */     par1CraftingManager.addRecipe(new ItemStack(Items.gold_nugget, 9), new Object[] { "#", Character.valueOf('#'), Items.gold_ingot });
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipesIngots
 * JD-Core Version:    0.7.0.1
 */