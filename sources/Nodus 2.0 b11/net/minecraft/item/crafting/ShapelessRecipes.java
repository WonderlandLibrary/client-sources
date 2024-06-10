/*  1:   */ package net.minecraft.item.crafting;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import net.minecraft.inventory.InventoryCrafting;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class ShapelessRecipes
/* 11:   */   implements IRecipe
/* 12:   */ {
/* 13:   */   private final ItemStack recipeOutput;
/* 14:   */   private final List recipeItems;
/* 15:   */   private static final String __OBFID = "CL_00000094";
/* 16:   */   
/* 17:   */   public ShapelessRecipes(ItemStack par1ItemStack, List par2List)
/* 18:   */   {
/* 19:21 */     this.recipeOutput = par1ItemStack;
/* 20:22 */     this.recipeItems = par2List;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ItemStack getRecipeOutput()
/* 24:   */   {
/* 25:27 */     return this.recipeOutput;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/* 29:   */   {
/* 30:35 */     ArrayList var3 = new ArrayList(this.recipeItems);
/* 31:37 */     for (int var4 = 0; var4 < 3; var4++) {
/* 32:39 */       for (int var5 = 0; var5 < 3; var5++)
/* 33:   */       {
/* 34:41 */         ItemStack var6 = par1InventoryCrafting.getStackInRowAndColumn(var5, var4);
/* 35:43 */         if (var6 != null)
/* 36:   */         {
/* 37:45 */           boolean var7 = false;
/* 38:46 */           Iterator var8 = var3.iterator();
/* 39:48 */           while (var8.hasNext())
/* 40:   */           {
/* 41:50 */             ItemStack var9 = (ItemStack)var8.next();
/* 42:52 */             if ((var6.getItem() == var9.getItem()) && ((var9.getItemDamage() == 32767) || (var6.getItemDamage() == var9.getItemDamage())))
/* 43:   */             {
/* 44:54 */               var7 = true;
/* 45:55 */               var3.remove(var9);
/* 46:56 */               break;
/* 47:   */             }
/* 48:   */           }
/* 49:60 */           if (!var7) {
/* 50:62 */             return false;
/* 51:   */           }
/* 52:   */         }
/* 53:   */       }
/* 54:   */     }
/* 55:68 */     return var3.isEmpty();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/* 59:   */   {
/* 60:76 */     return this.recipeOutput.copy();
/* 61:   */   }
/* 62:   */   
/* 63:   */   public int getRecipeSize()
/* 64:   */   {
/* 65:84 */     return this.recipeItems.size();
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.ShapelessRecipes
 * JD-Core Version:    0.7.0.1
 */