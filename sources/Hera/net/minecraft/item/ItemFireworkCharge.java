/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagIntArray;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ public class ItemFireworkCharge
/*     */   extends Item
/*     */ {
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  14 */     if (renderPass != 1)
/*     */     {
/*  16 */       return super.getColorFromItemStack(stack, renderPass);
/*     */     }
/*     */ 
/*     */     
/*  20 */     NBTBase nbtbase = getExplosionTag(stack, "Colors");
/*     */     
/*  22 */     if (!(nbtbase instanceof NBTTagIntArray))
/*     */     {
/*  24 */       return 9079434;
/*     */     }
/*     */ 
/*     */     
/*  28 */     NBTTagIntArray nbttagintarray = (NBTTagIntArray)nbtbase;
/*  29 */     int[] aint = nbttagintarray.getIntArray();
/*     */     
/*  31 */     if (aint.length == 1)
/*     */     {
/*  33 */       return aint[0];
/*     */     }
/*     */ 
/*     */     
/*  37 */     int i = 0;
/*  38 */     int j = 0;
/*  39 */     int k = 0; byte b;
/*     */     int m, arrayOfInt1[];
/*  41 */     for (m = (arrayOfInt1 = aint).length, b = 0; b < m; ) { int l = arrayOfInt1[b];
/*     */       
/*  43 */       i += (l & 0xFF0000) >> 16;
/*  44 */       j += (l & 0xFF00) >> 8;
/*  45 */       k += (l & 0xFF) >> 0;
/*     */       b++; }
/*     */     
/*  48 */     i /= aint.length;
/*  49 */     j /= aint.length;
/*  50 */     k /= aint.length;
/*  51 */     return i << 16 | j << 8 | k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTBase getExplosionTag(ItemStack stack, String key) {
/*  59 */     if (stack.hasTagCompound()) {
/*     */       
/*  61 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion");
/*     */       
/*  63 */       if (nbttagcompound != null)
/*     */       {
/*  65 */         return nbttagcompound.getTag(key);
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  77 */     if (stack.hasTagCompound()) {
/*     */       
/*  79 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Explosion");
/*     */       
/*  81 */       if (nbttagcompound != null)
/*     */       {
/*  83 */         addExplosionInfo(nbttagcompound, tooltip);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addExplosionInfo(NBTTagCompound nbt, List<String> tooltip) {
/*  90 */     byte b0 = nbt.getByte("Type");
/*     */     
/*  92 */     if (b0 >= 0 && b0 <= 4) {
/*     */       
/*  94 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.type." + b0).trim());
/*     */     }
/*     */     else {
/*     */       
/*  98 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
/*     */     } 
/*     */     
/* 101 */     int[] aint = nbt.getIntArray("Colors");
/*     */     
/* 103 */     if (aint.length > 0) {
/*     */       
/* 105 */       boolean flag = true;
/* 106 */       String s = ""; byte b;
/*     */       int i, arrayOfInt[];
/* 108 */       for (i = (arrayOfInt = aint).length, b = 0; b < i; ) { int k = arrayOfInt[b];
/*     */         
/* 110 */         if (!flag)
/*     */         {
/* 112 */           s = String.valueOf(s) + ", ";
/*     */         }
/*     */         
/* 115 */         flag = false;
/* 116 */         boolean flag1 = false;
/*     */         
/* 118 */         for (int j = 0; j < ItemDye.dyeColors.length; j++) {
/*     */           
/* 120 */           if (k == ItemDye.dyeColors[j]) {
/*     */             
/* 122 */             flag1 = true;
/* 123 */             s = String.valueOf(s) + StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(j).getUnlocalizedName());
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 128 */         if (!flag1)
/*     */         {
/* 130 */           s = String.valueOf(s) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
/*     */         }
/*     */         b++; }
/*     */       
/* 134 */       tooltip.add(s);
/*     */     } 
/*     */     
/* 137 */     int[] aint1 = nbt.getIntArray("FadeColors");
/*     */     
/* 139 */     if (aint1.length > 0) {
/*     */       
/* 141 */       boolean flag2 = true;
/* 142 */       String s1 = String.valueOf(StatCollector.translateToLocal("item.fireworksCharge.fadeTo")) + " "; byte b;
/*     */       int i, arrayOfInt[];
/* 144 */       for (i = (arrayOfInt = aint1).length, b = 0; b < i; ) { int l = arrayOfInt[b];
/*     */         
/* 146 */         if (!flag2)
/*     */         {
/* 148 */           s1 = String.valueOf(s1) + ", ";
/*     */         }
/*     */         
/* 151 */         flag2 = false;
/* 152 */         boolean flag5 = false;
/*     */         
/* 154 */         for (int k = 0; k < 16; k++) {
/*     */           
/* 156 */           if (l == ItemDye.dyeColors[k]) {
/*     */             
/* 158 */             flag5 = true;
/* 159 */             s1 = String.valueOf(s1) + StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(k).getUnlocalizedName());
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 164 */         if (!flag5)
/*     */         {
/* 166 */           s1 = String.valueOf(s1) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
/*     */         }
/*     */         b++; }
/*     */       
/* 170 */       tooltip.add(s1);
/*     */     } 
/*     */     
/* 173 */     boolean flag3 = nbt.getBoolean("Trail");
/*     */     
/* 175 */     if (flag3)
/*     */     {
/* 177 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
/*     */     }
/*     */     
/* 180 */     boolean flag4 = nbt.getBoolean("Flicker");
/*     */     
/* 182 */     if (flag4)
/*     */     {
/* 184 */       tooltip.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemFireworkCharge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */