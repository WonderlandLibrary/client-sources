/*  1:   */ package net.minecraft.item.crafting;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Blocks;
/*  4:   */ import net.minecraft.init.Items;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class RecipesFood
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000084";
/* 10:   */   
/* 11:   */   public void addRecipes(CraftingManager par1CraftingManager)
/* 12:   */   {
/* 13:16 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.mushroom_stew), new Object[] { Blocks.brown_mushroom, Blocks.red_mushroom, Items.bowl });
/* 14:17 */     par1CraftingManager.addRecipe(new ItemStack(Items.cookie, 8), new Object[] { "#X#", Character.valueOf('X'), new ItemStack(Items.dye, 1, 3), Character.valueOf('#'), Items.wheat });
/* 15:18 */     par1CraftingManager.addRecipe(new ItemStack(Blocks.melon_block), new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), Items.melon });
/* 16:19 */     par1CraftingManager.addRecipe(new ItemStack(Items.melon_seeds), new Object[] { "M", Character.valueOf('M'), Items.melon });
/* 17:20 */     par1CraftingManager.addRecipe(new ItemStack(Items.pumpkin_seeds, 4), new Object[] { "M", Character.valueOf('M'), Blocks.pumpkin });
/* 18:21 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.pumpkin_pie), new Object[] { Blocks.pumpkin, Items.sugar, Items.egg });
/* 19:22 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.fermented_spider_eye), new Object[] { Items.spider_eye, Blocks.brown_mushroom, Items.sugar });
/* 20:23 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.blaze_powder, 2), new Object[] { Items.blaze_rod });
/* 21:24 */     par1CraftingManager.addShapelessRecipe(new ItemStack(Items.magma_cream), new Object[] { Items.blaze_powder, Items.slime_ball });
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipesFood
 * JD-Core Version:    0.7.0.1
 */