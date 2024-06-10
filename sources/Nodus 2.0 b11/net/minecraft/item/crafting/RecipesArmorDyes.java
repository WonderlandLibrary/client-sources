/*   1:    */ package net.minecraft.item.crafting;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import net.minecraft.init.Items;
/*   5:    */ import net.minecraft.inventory.InventoryCrafting;
/*   6:    */ import net.minecraft.item.ItemArmor;
/*   7:    */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class RecipesArmorDyes
/*  12:    */   implements IRecipe
/*  13:    */ {
/*  14:    */   private static final String __OBFID = "CL_00000079";
/*  15:    */   
/*  16:    */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/*  17:    */   {
/*  18: 21 */     ItemStack var3 = null;
/*  19: 22 */     ArrayList var4 = new ArrayList();
/*  20: 24 */     for (int var5 = 0; var5 < par1InventoryCrafting.getSizeInventory(); var5++)
/*  21:    */     {
/*  22: 26 */       ItemStack var6 = par1InventoryCrafting.getStackInSlot(var5);
/*  23: 28 */       if (var6 != null) {
/*  24: 30 */         if ((var6.getItem() instanceof ItemArmor))
/*  25:    */         {
/*  26: 32 */           ItemArmor var7 = (ItemArmor)var6.getItem();
/*  27: 34 */           if ((var7.getArmorMaterial() != ItemArmor.ArmorMaterial.CLOTH) || (var3 != null)) {
/*  28: 36 */             return false;
/*  29:    */           }
/*  30: 39 */           var3 = var6;
/*  31:    */         }
/*  32:    */         else
/*  33:    */         {
/*  34: 43 */           if (var6.getItem() != Items.dye) {
/*  35: 45 */             return false;
/*  36:    */           }
/*  37: 48 */           var4.add(var6);
/*  38:    */         }
/*  39:    */       }
/*  40:    */     }
/*  41: 53 */     return (var3 != null) && (!var4.isEmpty());
/*  42:    */   }
/*  43:    */   
/*  44:    */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/*  45:    */   {
/*  46: 61 */     ItemStack var2 = null;
/*  47: 62 */     int[] var3 = new int[3];
/*  48: 63 */     int var4 = 0;
/*  49: 64 */     int var5 = 0;
/*  50: 65 */     ItemArmor var6 = null;
/*  51: 72 */     for (int var7 = 0; var7 < par1InventoryCrafting.getSizeInventory(); var7++)
/*  52:    */     {
/*  53: 74 */       ItemStack var8 = par1InventoryCrafting.getStackInSlot(var7);
/*  54: 76 */       if (var8 != null) {
/*  55: 78 */         if ((var8.getItem() instanceof ItemArmor))
/*  56:    */         {
/*  57: 80 */           var6 = (ItemArmor)var8.getItem();
/*  58: 82 */           if ((var6.getArmorMaterial() != ItemArmor.ArmorMaterial.CLOTH) || (var2 != null)) {
/*  59: 84 */             return null;
/*  60:    */           }
/*  61: 87 */           var2 = var8.copy();
/*  62: 88 */           var2.stackSize = 1;
/*  63: 90 */           if (var6.hasColor(var8))
/*  64:    */           {
/*  65: 92 */             int var9 = var6.getColor(var2);
/*  66: 93 */             float var10 = (var9 >> 16 & 0xFF) / 255.0F;
/*  67: 94 */             float var11 = (var9 >> 8 & 0xFF) / 255.0F;
/*  68: 95 */             float var12 = (var9 & 0xFF) / 255.0F;
/*  69: 96 */             var4 = (int)(var4 + Math.max(var10, Math.max(var11, var12)) * 255.0F);
/*  70: 97 */             var3[0] = ((int)(var3[0] + var10 * 255.0F));
/*  71: 98 */             var3[1] = ((int)(var3[1] + var11 * 255.0F));
/*  72: 99 */             var3[2] = ((int)(var3[2] + var12 * 255.0F));
/*  73:100 */             var5++;
/*  74:    */           }
/*  75:    */         }
/*  76:    */         else
/*  77:    */         {
/*  78:105 */           if (var8.getItem() != Items.dye) {
/*  79:107 */             return null;
/*  80:    */           }
/*  81:110 */           float[] var14 = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[net.minecraft.block.BlockColored.func_150032_b(var8.getItemDamage())];
/*  82:111 */           int var16 = (int)(var14[0] * 255.0F);
/*  83:112 */           int var15 = (int)(var14[1] * 255.0F);
/*  84:113 */           int var17 = (int)(var14[2] * 255.0F);
/*  85:114 */           var4 += Math.max(var16, Math.max(var15, var17));
/*  86:115 */           var3[0] += var16;
/*  87:116 */           var3[1] += var15;
/*  88:117 */           var3[2] += var17;
/*  89:118 */           var5++;
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:123 */     if (var6 == null) {
/*  94:125 */       return null;
/*  95:    */     }
/*  96:129 */     var7 = var3[0] / var5;
/*  97:130 */     int var13 = var3[1] / var5;
/*  98:131 */     int var9 = var3[2] / var5;
/*  99:132 */     float var10 = var4 / var5;
/* 100:133 */     float var11 = Math.max(var7, Math.max(var13, var9));
/* 101:134 */     var7 = (int)(var7 * var10 / var11);
/* 102:135 */     var13 = (int)(var13 * var10 / var11);
/* 103:136 */     var9 = (int)(var9 * var10 / var11);
/* 104:137 */     int var17 = (var7 << 8) + var13;
/* 105:138 */     var17 = (var17 << 8) + var9;
/* 106:139 */     var6.func_82813_b(var2, var17);
/* 107:140 */     return var2;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getRecipeSize()
/* 111:    */   {
/* 112:149 */     return 10;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public ItemStack getRecipeOutput()
/* 116:    */   {
/* 117:154 */     return null;
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.RecipesArmorDyes
 * JD-Core Version:    0.7.0.1
 */