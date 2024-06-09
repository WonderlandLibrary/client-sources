/*    */ package net.minecraft.item.crafting;
/*    */ 
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RecipesArmor
/*    */ {
/*  9 */   private String[][] recipePatterns = new String[][] { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
/* 10 */   private Item[][] recipeItems = new Item[][] { { Items.leather, Items.iron_ingot, Items.diamond, Items.gold_ingot }, { (Item)Items.leather_helmet, (Item)Items.iron_helmet, (Item)Items.diamond_helmet, (Item)Items.golden_helmet }, { (Item)Items.leather_chestplate, (Item)Items.iron_chestplate, (Item)Items.diamond_chestplate, (Item)Items.golden_chestplate }, { (Item)Items.leather_leggings, (Item)Items.iron_leggings, (Item)Items.diamond_leggings, (Item)Items.golden_leggings }, { (Item)Items.leather_boots, (Item)Items.iron_boots, (Item)Items.diamond_boots, (Item)Items.golden_boots } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addRecipes(CraftingManager craftManager) {
/* 17 */     for (int i = 0; i < (this.recipeItems[0]).length; i++) {
/*    */       
/* 19 */       Item item = this.recipeItems[0][i];
/*    */       
/* 21 */       for (int j = 0; j < this.recipeItems.length - 1; j++) {
/*    */         
/* 23 */         Item item1 = this.recipeItems[j + 1][i];
/* 24 */         craftManager.addRecipe(new ItemStack(item1), new Object[] { this.recipePatterns[j], Character.valueOf('X'), item });
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\crafting\RecipesArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */