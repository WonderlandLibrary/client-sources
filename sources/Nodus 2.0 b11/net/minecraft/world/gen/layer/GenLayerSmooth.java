/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ public class GenLayerSmooth
/*  4:   */   extends GenLayer
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000569";
/*  7:   */   
/*  8:   */   public GenLayerSmooth(long par1, GenLayer par3GenLayer)
/*  9:   */   {
/* 10: 9 */     super(par1);
/* 11:10 */     this.parent = par3GenLayer;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int[] getInts(int par1, int par2, int par3, int par4)
/* 15:   */   {
/* 16:19 */     int var5 = par1 - 1;
/* 17:20 */     int var6 = par2 - 1;
/* 18:21 */     int var7 = par3 + 2;
/* 19:22 */     int var8 = par4 + 2;
/* 20:23 */     int[] var9 = this.parent.getInts(var5, var6, var7, var8);
/* 21:24 */     int[] var10 = IntCache.getIntCache(par3 * par4);
/* 22:26 */     for (int var11 = 0; var11 < par4; var11++) {
/* 23:28 */       for (int var12 = 0; var12 < par3; var12++)
/* 24:   */       {
/* 25:30 */         int var13 = var9[(var12 + 0 + (var11 + 1) * var7)];
/* 26:31 */         int var14 = var9[(var12 + 2 + (var11 + 1) * var7)];
/* 27:32 */         int var15 = var9[(var12 + 1 + (var11 + 0) * var7)];
/* 28:33 */         int var16 = var9[(var12 + 1 + (var11 + 2) * var7)];
/* 29:34 */         int var17 = var9[(var12 + 1 + (var11 + 1) * var7)];
/* 30:36 */         if ((var13 == var14) && (var15 == var16))
/* 31:   */         {
/* 32:38 */           initChunkSeed(var12 + par1, var11 + par2);
/* 33:40 */           if (nextInt(2) == 0) {
/* 34:42 */             var17 = var13;
/* 35:   */           } else {
/* 36:46 */             var17 = var15;
/* 37:   */           }
/* 38:   */         }
/* 39:   */         else
/* 40:   */         {
/* 41:51 */           if (var13 == var14) {
/* 42:53 */             var17 = var13;
/* 43:   */           }
/* 44:56 */           if (var15 == var16) {
/* 45:58 */             var17 = var15;
/* 46:   */           }
/* 47:   */         }
/* 48:62 */         var10[(var12 + var11 * par3)] = var17;
/* 49:   */       }
/* 50:   */     }
/* 51:66 */     return var10;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerSmooth
 * JD-Core Version:    0.7.0.1
 */