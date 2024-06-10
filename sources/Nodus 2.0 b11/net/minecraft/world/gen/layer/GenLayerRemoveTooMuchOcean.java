/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ public class GenLayerRemoveTooMuchOcean
/*  4:   */   extends GenLayer
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000564";
/*  7:   */   
/*  8:   */   public GenLayerRemoveTooMuchOcean(long p_i45480_1_, GenLayer p_i45480_3_)
/*  9:   */   {
/* 10: 9 */     super(p_i45480_1_);
/* 11:10 */     this.parent = p_i45480_3_;
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
/* 25:30 */         int var13 = var9[(var12 + 1 + (var11 + 1 - 1) * (par3 + 2))];
/* 26:31 */         int var14 = var9[(var12 + 1 + 1 + (var11 + 1) * (par3 + 2))];
/* 27:32 */         int var15 = var9[(var12 + 1 - 1 + (var11 + 1) * (par3 + 2))];
/* 28:33 */         int var16 = var9[(var12 + 1 + (var11 + 1 + 1) * (par3 + 2))];
/* 29:34 */         int var17 = var9[(var12 + 1 + (var11 + 1) * var7)];
/* 30:35 */         var10[(var12 + var11 * par3)] = var17;
/* 31:36 */         initChunkSeed(var12 + par1, var11 + par2);
/* 32:38 */         if ((var17 == 0) && (var13 == 0) && (var14 == 0) && (var15 == 0) && (var16 == 0) && (nextInt(2) == 0)) {
/* 33:40 */           var10[(var12 + var11 * par3)] = 1;
/* 34:   */         }
/* 35:   */       }
/* 36:   */     }
/* 37:45 */     return var10;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean
 * JD-Core Version:    0.7.0.1
 */