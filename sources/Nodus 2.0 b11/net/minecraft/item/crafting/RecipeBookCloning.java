/*   1:    */ package net.minecraft.item.crafting;
/*   2:    */ 
/*   3:    */ import net.minecraft.init.Items;
/*   4:    */ import net.minecraft.inventory.InventoryCrafting;
/*   5:    */ import net.minecraft.item.ItemStack;
/*   6:    */ import net.minecraft.nbt.NBTTagCompound;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class RecipeBookCloning
/*  10:    */   implements IRecipe
/*  11:    */ {
/*  12:    */   private static final String __OBFID = "CL_00000081";
/*  13:    */   
/*  14:    */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/*  15:    */   {
/*  16: 18 */     int var3 = 0;
/*  17: 19 */     ItemStack var4 = null;
/*  18: 21 */     for (int var5 = 0; var5 < par1InventoryCrafting.getSizeInventory(); var5++)
/*  19:    */     {
/*  20: 23 */       ItemStack var6 = par1InventoryCrafting.getStackInSlot(var5);
/*  21: 25 */       if (var6 != null) {
/*  22: 27 */         if (var6.getItem() == Items.written_book)
/*  23:    */         {
/*  24: 29 */           if (var4 != null) {
/*  25: 31 */             return false;
/*  26:    */           }
/*  27: 34 */           var4 = var6;
/*  28:    */         }
/*  29:    */         else
/*  30:    */         {
/*  31: 38 */           if (var6.getItem() != Items.writable_book) {
/*  32: 40 */             return false;
/*  33:    */           }
/*  34: 43 */           var3++;
/*  35:    */         }
/*  36:    */       }
/*  37:    */     }
/*  38: 48 */     return (var4 != null) && (var3 > 0);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/*  42:    */   {
/*  43: 56 */     int var2 = 0;
/*  44: 57 */     ItemStack var3 = null;
/*  45: 59 */     for (int var4 = 0; var4 < par1InventoryCrafting.getSizeInventory(); var4++)
/*  46:    */     {
/*  47: 61 */       ItemStack var5 = par1InventoryCrafting.getStackInSlot(var4);
/*  48: 63 */       if (var5 != null) {
/*  49: 65 */         if (var5.getItem() == Items.written_book)
/*  50:    */         {
/*  51: 67 */           if (var3 != null) {
/*  52: 69 */             return null;
/*  53:    */           }
/*  54: 72 */           var3 = var5;
/*  55:    */         }
/*  56:    */         else
/*  57:    */         {
/*  58: 76 */           if (var5.getItem() != Items.writable_book) {
/*  59: 78 */             return null;
/*  60:    */           }
/*  61: 81 */           var2++;
/*  62:    */         }
/*  63:    */       }
/*  64:    */     }
/*  65: 86 */     if ((var3 != null) && (var2 >= 1))
/*  66:    */     {
/*  67: 88 */       ItemStack var6 = new ItemStack(Items.written_book, var2 + 1);
/*  68: 89 */       var6.setTagCompound((NBTTagCompound)var3.getTagCompound().copy());
/*  69: 91 */       if (var3.hasDisplayName()) {
/*  70: 93 */         var6.setStackDisplayName(var3.getDisplayName());
/*  71:    */       }
/*  72: 96 */       return var6;
/*  73:    */     }
/*  74:100 */     return null;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getRecipeSize()
/*  78:    */   {
/*  79:109 */     return 9;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public ItemStack getRecipeOutput()
/*  83:    */   {
/*  84:114 */     return null;
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipeBookCloning
 * JD-Core Version:    0.7.0.1
 */