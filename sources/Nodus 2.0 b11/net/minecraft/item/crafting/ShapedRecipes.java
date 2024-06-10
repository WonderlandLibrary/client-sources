/*   1:    */ package net.minecraft.item.crafting;
/*   2:    */ 
/*   3:    */ import net.minecraft.inventory.InventoryCrafting;
/*   4:    */ import net.minecraft.item.ItemStack;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ import net.minecraft.world.World;
/*   7:    */ 
/*   8:    */ public class ShapedRecipes
/*   9:    */   implements IRecipe
/*  10:    */ {
/*  11:    */   private int recipeWidth;
/*  12:    */   private int recipeHeight;
/*  13:    */   private ItemStack[] recipeItems;
/*  14:    */   private ItemStack recipeOutput;
/*  15:    */   private boolean field_92101_f;
/*  16:    */   private static final String __OBFID = "CL_00000093";
/*  17:    */   
/*  18:    */   public ShapedRecipes(int par1, int par2, ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack)
/*  19:    */   {
/*  20: 26 */     this.recipeWidth = par1;
/*  21: 27 */     this.recipeHeight = par2;
/*  22: 28 */     this.recipeItems = par3ArrayOfItemStack;
/*  23: 29 */     this.recipeOutput = par4ItemStack;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ItemStack getRecipeOutput()
/*  27:    */   {
/*  28: 34 */     return this.recipeOutput;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/*  32:    */   {
/*  33: 42 */     for (int var3 = 0; var3 <= 3 - this.recipeWidth; var3++) {
/*  34: 44 */       for (int var4 = 0; var4 <= 3 - this.recipeHeight; var4++)
/*  35:    */       {
/*  36: 46 */         if (checkMatch(par1InventoryCrafting, var3, var4, true)) {
/*  37: 48 */           return true;
/*  38:    */         }
/*  39: 51 */         if (checkMatch(par1InventoryCrafting, var3, var4, false)) {
/*  40: 53 */           return true;
/*  41:    */         }
/*  42:    */       }
/*  43:    */     }
/*  44: 58 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   private boolean checkMatch(InventoryCrafting par1InventoryCrafting, int par2, int par3, boolean par4)
/*  48:    */   {
/*  49: 66 */     for (int var5 = 0; var5 < 3; var5++) {
/*  50: 68 */       for (int var6 = 0; var6 < 3; var6++)
/*  51:    */       {
/*  52: 70 */         int var7 = var5 - par2;
/*  53: 71 */         int var8 = var6 - par3;
/*  54: 72 */         ItemStack var9 = null;
/*  55: 74 */         if ((var7 >= 0) && (var8 >= 0) && (var7 < this.recipeWidth) && (var8 < this.recipeHeight)) {
/*  56: 76 */           if (par4) {
/*  57: 78 */             var9 = this.recipeItems[(this.recipeWidth - var7 - 1 + var8 * this.recipeWidth)];
/*  58:    */           } else {
/*  59: 82 */             var9 = this.recipeItems[(var7 + var8 * this.recipeWidth)];
/*  60:    */           }
/*  61:    */         }
/*  62: 86 */         ItemStack var10 = par1InventoryCrafting.getStackInRowAndColumn(var5, var6);
/*  63: 88 */         if ((var10 != null) || (var9 != null))
/*  64:    */         {
/*  65: 90 */           if (((var10 == null) && (var9 != null)) || ((var10 != null) && (var9 == null))) {
/*  66: 92 */             return false;
/*  67:    */           }
/*  68: 95 */           if (var9.getItem() != var10.getItem()) {
/*  69: 97 */             return false;
/*  70:    */           }
/*  71:100 */           if ((var9.getItemDamage() != 32767) && (var9.getItemDamage() != var10.getItemDamage())) {
/*  72:102 */             return false;
/*  73:    */           }
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77:108 */     return true;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/*  81:    */   {
/*  82:116 */     ItemStack var2 = getRecipeOutput().copy();
/*  83:118 */     if (this.field_92101_f) {
/*  84:120 */       for (int var3 = 0; var3 < par1InventoryCrafting.getSizeInventory(); var3++)
/*  85:    */       {
/*  86:122 */         ItemStack var4 = par1InventoryCrafting.getStackInSlot(var3);
/*  87:124 */         if ((var4 != null) && (var4.hasTagCompound())) {
/*  88:126 */           var2.setTagCompound((NBTTagCompound)var4.stackTagCompound.copy());
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:131 */     return var2;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getRecipeSize()
/*  96:    */   {
/*  97:139 */     return this.recipeWidth * this.recipeHeight;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ShapedRecipes func_92100_c()
/* 101:    */   {
/* 102:144 */     this.field_92101_f = true;
/* 103:145 */     return this;
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.ShapedRecipes
 * JD-Core Version:    0.7.0.1
 */