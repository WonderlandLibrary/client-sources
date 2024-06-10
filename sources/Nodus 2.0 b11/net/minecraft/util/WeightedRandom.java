/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Random;
/*   6:    */ 
/*   7:    */ public class WeightedRandom
/*   8:    */ {
/*   9:    */   private static final String __OBFID = "CL_00001503";
/*  10:    */   
/*  11:    */   public static int getTotalWeight(Collection par0Collection)
/*  12:    */   {
/*  13: 16 */     int var1 = 0;
/*  14:    */     Item var3;
/*  15: 19 */     for (Iterator var2 = par0Collection.iterator(); var2.hasNext(); var1 += var3.itemWeight) {
/*  16: 21 */       var3 = (Item)var2.next();
/*  17:    */     }
/*  18: 24 */     return var1;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static Item getRandomItem(Random par0Random, Collection par1Collection, int par2)
/*  22:    */   {
/*  23: 32 */     if (par2 <= 0) {
/*  24: 34 */       throw new IllegalArgumentException();
/*  25:    */     }
/*  26: 38 */     int var3 = par0Random.nextInt(par2);
/*  27: 39 */     Iterator var4 = par1Collection.iterator();
/*  28:    */     Item var5;
/*  29:    */     do
/*  30:    */     {
/*  31: 44 */       if (!var4.hasNext()) {
/*  32: 46 */         return null;
/*  33:    */       }
/*  34: 49 */       var5 = (Item)var4.next();
/*  35: 50 */       var3 -= var5.itemWeight;
/*  36: 42 */     } while (
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46: 52 */       var3 >= 0);
/*  47: 54 */     return var5;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static Item getRandomItem(Random par0Random, Collection par1Collection)
/*  51:    */   {
/*  52: 63 */     return getRandomItem(par0Random, par1Collection, getTotalWeight(par1Collection));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static int getTotalWeight(Item[] par0ArrayOfWeightedRandomItem)
/*  56:    */   {
/*  57: 71 */     int var1 = 0;
/*  58: 72 */     Item[] var2 = par0ArrayOfWeightedRandomItem;
/*  59: 73 */     int var3 = par0ArrayOfWeightedRandomItem.length;
/*  60: 75 */     for (int var4 = 0; var4 < var3; var4++)
/*  61:    */     {
/*  62: 77 */       Item var5 = var2[var4];
/*  63: 78 */       var1 += var5.itemWeight;
/*  64:    */     }
/*  65: 81 */     return var1;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static Item getRandomItem(Random par0Random, Item[] par1ArrayOfWeightedRandomItem, int par2)
/*  69:    */   {
/*  70: 89 */     if (par2 <= 0) {
/*  71: 91 */       throw new IllegalArgumentException();
/*  72:    */     }
/*  73: 95 */     int var3 = par0Random.nextInt(par2);
/*  74: 96 */     Item[] var4 = par1ArrayOfWeightedRandomItem;
/*  75: 97 */     int var5 = par1ArrayOfWeightedRandomItem.length;
/*  76: 99 */     for (int var6 = 0; var6 < var5; var6++)
/*  77:    */     {
/*  78:101 */       Item var7 = var4[var6];
/*  79:102 */       var3 -= var7.itemWeight;
/*  80:104 */       if (var3 < 0) {
/*  81:106 */         return var7;
/*  82:    */       }
/*  83:    */     }
/*  84:110 */     return null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static Item getRandomItem(Random par0Random, Item[] par1ArrayOfWeightedRandomItem)
/*  88:    */   {
/*  89:119 */     return getRandomItem(par0Random, par1ArrayOfWeightedRandomItem, getTotalWeight(par1ArrayOfWeightedRandomItem));
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static class Item
/*  93:    */   {
/*  94:    */     protected int itemWeight;
/*  95:    */     private static final String __OBFID = "CL_00001504";
/*  96:    */     
/*  97:    */     public Item(int par1)
/*  98:    */     {
/*  99:129 */       this.itemWeight = par1;
/* 100:    */     }
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.WeightedRandom
 * JD-Core Version:    0.7.0.1
 */