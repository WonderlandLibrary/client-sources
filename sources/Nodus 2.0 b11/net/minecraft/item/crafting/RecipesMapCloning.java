/*   1:    */ package net.minecraft.item.crafting;
/*   2:    */ 
/*   3:    */ import net.minecraft.init.Items;
/*   4:    */ import net.minecraft.inventory.InventoryCrafting;
/*   5:    */ import net.minecraft.item.ItemStack;
/*   6:    */ import net.minecraft.world.World;
/*   7:    */ 
/*   8:    */ public class RecipesMapCloning
/*   9:    */   implements IRecipe
/*  10:    */ {
/*  11:    */   private static final String __OBFID = "CL_00000087";
/*  12:    */   
/*  13:    */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/*  14:    */   {
/*  15: 17 */     int var3 = 0;
/*  16: 18 */     ItemStack var4 = null;
/*  17: 20 */     for (int var5 = 0; var5 < par1InventoryCrafting.getSizeInventory(); var5++)
/*  18:    */     {
/*  19: 22 */       ItemStack var6 = par1InventoryCrafting.getStackInSlot(var5);
/*  20: 24 */       if (var6 != null) {
/*  21: 26 */         if (var6.getItem() == Items.filled_map)
/*  22:    */         {
/*  23: 28 */           if (var4 != null) {
/*  24: 30 */             return false;
/*  25:    */           }
/*  26: 33 */           var4 = var6;
/*  27:    */         }
/*  28:    */         else
/*  29:    */         {
/*  30: 37 */           if (var6.getItem() != Items.map) {
/*  31: 39 */             return false;
/*  32:    */           }
/*  33: 42 */           var3++;
/*  34:    */         }
/*  35:    */       }
/*  36:    */     }
/*  37: 47 */     return (var4 != null) && (var3 > 0);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/*  41:    */   {
/*  42: 55 */     int var2 = 0;
/*  43: 56 */     ItemStack var3 = null;
/*  44: 58 */     for (int var4 = 0; var4 < par1InventoryCrafting.getSizeInventory(); var4++)
/*  45:    */     {
/*  46: 60 */       ItemStack var5 = par1InventoryCrafting.getStackInSlot(var4);
/*  47: 62 */       if (var5 != null) {
/*  48: 64 */         if (var5.getItem() == Items.filled_map)
/*  49:    */         {
/*  50: 66 */           if (var3 != null) {
/*  51: 68 */             return null;
/*  52:    */           }
/*  53: 71 */           var3 = var5;
/*  54:    */         }
/*  55:    */         else
/*  56:    */         {
/*  57: 75 */           if (var5.getItem() != Items.map) {
/*  58: 77 */             return null;
/*  59:    */           }
/*  60: 80 */           var2++;
/*  61:    */         }
/*  62:    */       }
/*  63:    */     }
/*  64: 85 */     if ((var3 != null) && (var2 >= 1))
/*  65:    */     {
/*  66: 87 */       ItemStack var6 = new ItemStack(Items.filled_map, var2 + 1, var3.getItemDamage());
/*  67: 89 */       if (var3.hasDisplayName()) {
/*  68: 91 */         var6.setStackDisplayName(var3.getDisplayName());
/*  69:    */       }
/*  70: 94 */       return var6;
/*  71:    */     }
/*  72: 98 */     return null;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getRecipeSize()
/*  76:    */   {
/*  77:107 */     return 9;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public ItemStack getRecipeOutput()
/*  81:    */   {
/*  82:112 */     return null;
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipesMapCloning
 * JD-Core Version:    0.7.0.1
 */