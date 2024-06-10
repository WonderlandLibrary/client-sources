/*   1:    */ package net.minecraft.world.gen.layer;
/*   2:    */ 
/*   3:    */ public class GenLayerAddIsland
/*   4:    */   extends GenLayer
/*   5:    */ {
/*   6:    */   private static final String __OBFID = "CL_00000551";
/*   7:    */   
/*   8:    */   public GenLayerAddIsland(long par1, GenLayer par3GenLayer)
/*   9:    */   {
/*  10:  9 */     super(par1);
/*  11: 10 */     this.parent = par3GenLayer;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public int[] getInts(int par1, int par2, int par3, int par4)
/*  15:    */   {
/*  16: 19 */     int var5 = par1 - 1;
/*  17: 20 */     int var6 = par2 - 1;
/*  18: 21 */     int var7 = par3 + 2;
/*  19: 22 */     int var8 = par4 + 2;
/*  20: 23 */     int[] var9 = this.parent.getInts(var5, var6, var7, var8);
/*  21: 24 */     int[] var10 = IntCache.getIntCache(par3 * par4);
/*  22: 26 */     for (int var11 = 0; var11 < par4; var11++) {
/*  23: 28 */       for (int var12 = 0; var12 < par3; var12++)
/*  24:    */       {
/*  25: 30 */         int var13 = var9[(var12 + 0 + (var11 + 0) * var7)];
/*  26: 31 */         int var14 = var9[(var12 + 2 + (var11 + 0) * var7)];
/*  27: 32 */         int var15 = var9[(var12 + 0 + (var11 + 2) * var7)];
/*  28: 33 */         int var16 = var9[(var12 + 2 + (var11 + 2) * var7)];
/*  29: 34 */         int var17 = var9[(var12 + 1 + (var11 + 1) * var7)];
/*  30: 35 */         initChunkSeed(var12 + par1, var11 + par2);
/*  31: 37 */         if ((var17 == 0) && ((var13 != 0) || (var14 != 0) || (var15 != 0) || (var16 != 0)))
/*  32:    */         {
/*  33: 39 */           int var18 = 1;
/*  34: 40 */           int var19 = 1;
/*  35: 42 */           if ((var13 != 0) && (nextInt(var18++) == 0)) {
/*  36: 44 */             var19 = var13;
/*  37:    */           }
/*  38: 47 */           if ((var14 != 0) && (nextInt(var18++) == 0)) {
/*  39: 49 */             var19 = var14;
/*  40:    */           }
/*  41: 52 */           if ((var15 != 0) && (nextInt(var18++) == 0)) {
/*  42: 54 */             var19 = var15;
/*  43:    */           }
/*  44: 57 */           if ((var16 != 0) && (nextInt(var18++) == 0)) {
/*  45: 59 */             var19 = var16;
/*  46:    */           }
/*  47: 62 */           if (nextInt(3) == 0) {
/*  48: 64 */             var10[(var12 + var11 * par3)] = var19;
/*  49: 66 */           } else if (var19 == 4) {
/*  50: 68 */             var10[(var12 + var11 * par3)] = 4;
/*  51:    */           } else {
/*  52: 72 */             var10[(var12 + var11 * par3)] = 0;
/*  53:    */           }
/*  54:    */         }
/*  55: 75 */         else if ((var17 > 0) && ((var13 == 0) || (var14 == 0) || (var15 == 0) || (var16 == 0)))
/*  56:    */         {
/*  57: 77 */           if (nextInt(5) == 0)
/*  58:    */           {
/*  59: 79 */             if (var17 == 4) {
/*  60: 81 */               var10[(var12 + var11 * par3)] = 4;
/*  61:    */             } else {
/*  62: 85 */               var10[(var12 + var11 * par3)] = 0;
/*  63:    */             }
/*  64:    */           }
/*  65:    */           else {
/*  66: 90 */             var10[(var12 + var11 * par3)] = var17;
/*  67:    */           }
/*  68:    */         }
/*  69:    */         else
/*  70:    */         {
/*  71: 95 */           var10[(var12 + var11 * par3)] = var17;
/*  72:    */         }
/*  73:    */       }
/*  74:    */     }
/*  75:100 */     return var10;
/*  76:    */   }
/*  77:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerAddIsland
 * JD-Core Version:    0.7.0.1
 */