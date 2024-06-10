/*  1:   */ package net.minecraft.item.crafting;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Blocks;
/*  4:   */ import net.minecraft.init.Items;
/*  5:   */ import net.minecraft.item.Item;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ 
/*  8:   */ public class RecipesArmor
/*  9:   */ {
/* 10:10 */   private String[][] recipePatterns = { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
/* 11:   */   private Object[][] recipeItems;
/* 12:   */   private static final String __OBFID = "CL_00000080";
/* 13:   */   
/* 14:   */   public RecipesArmor()
/* 15:   */   {
/* 16:16 */     this.recipeItems = new Object[][] { { Items.leather, Blocks.fire, Items.iron_ingot, Items.diamond, Items.gold_ingot }, { Items.leather_helmet, Items.chainmail_helmet, Items.iron_helmet, Items.diamond_helmet, Items.golden_helmet }, { Items.leather_chestplate, Items.chainmail_chestplate, Items.iron_chestplate, Items.diamond_chestplate, Items.golden_chestplate }, { Items.leather_leggings, Items.chainmail_leggings, Items.iron_leggings, Items.diamond_leggings, Items.golden_leggings }, { Items.leather_boots, Items.chainmail_boots, Items.iron_boots, Items.diamond_boots, Items.golden_boots } };
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void addRecipes(CraftingManager par1CraftingManager)
/* 20:   */   {
/* 21:24 */     for (int var2 = 0; var2 < this.recipeItems[0].length; var2++)
/* 22:   */     {
/* 23:26 */       Object var3 = this.recipeItems[0][var2];
/* 24:28 */       for (int var4 = 0; var4 < this.recipeItems.length - 1; var4++)
/* 25:   */       {
/* 26:30 */         Item var5 = (Item)this.recipeItems[(var4 + 1)][var2];
/* 27:31 */         par1CraftingManager.addRecipe(new ItemStack(var5), new Object[] { this.recipePatterns[var4], Character.valueOf('X'), var3 });
/* 28:   */       }
/* 29:   */     }
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipesArmor
 * JD-Core Version:    0.7.0.1
 */