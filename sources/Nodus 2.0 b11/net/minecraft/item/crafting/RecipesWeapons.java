/*  1:   */ package net.minecraft.item.crafting;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Blocks;
/*  4:   */ import net.minecraft.init.Items;
/*  5:   */ import net.minecraft.item.Item;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ 
/*  8:   */ public class RecipesWeapons
/*  9:   */ {
/* 10:10 */   private String[][] recipePatterns = { { "X", "X", "#" } };
/* 11:   */   private Object[][] recipeItems;
/* 12:   */   private static final String __OBFID = "CL_00000097";
/* 13:   */   
/* 14:   */   public RecipesWeapons()
/* 15:   */   {
/* 16:16 */     this.recipeItems = new Object[][] { { Blocks.planks, Blocks.cobblestone, Items.iron_ingot, Items.diamond, Items.gold_ingot }, { Items.wooden_sword, Items.stone_sword, Items.iron_sword, Items.diamond_sword, Items.golden_sword } };
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
/* 27:31 */         par1CraftingManager.addRecipe(new ItemStack(var5), new Object[] { this.recipePatterns[var4], Character.valueOf('#'), Items.stick, Character.valueOf('X'), var3 });
/* 28:   */       }
/* 29:   */     }
/* 30:35 */     par1CraftingManager.addRecipe(new ItemStack(Items.bow, 1), new Object[] { " #X", "# X", " #X", Character.valueOf('X'), Items.string, Character.valueOf('#'), Items.stick });
/* 31:36 */     par1CraftingManager.addRecipe(new ItemStack(Items.arrow, 4), new Object[] { "X", "#", "Y", Character.valueOf('Y'), Items.feather, Character.valueOf('X'), Items.flint, Character.valueOf('#'), Items.stick });
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipesWeapons
 * JD-Core Version:    0.7.0.1
 */