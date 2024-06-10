/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.biome.BiomeGenBase;
/*  4:   */ 
/*  5:   */ public class GenLayerDeepOcean
/*  6:   */   extends GenLayer
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00000546";
/*  9:   */   
/* 10:   */   public GenLayerDeepOcean(long p_i45472_1_, GenLayer p_i45472_3_)
/* 11:   */   {
/* 12:11 */     super(p_i45472_1_);
/* 13:12 */     this.parent = p_i45472_3_;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public int[] getInts(int par1, int par2, int par3, int par4)
/* 17:   */   {
/* 18:21 */     int var5 = par1 - 1;
/* 19:22 */     int var6 = par2 - 1;
/* 20:23 */     int var7 = par3 + 2;
/* 21:24 */     int var8 = par4 + 2;
/* 22:25 */     int[] var9 = this.parent.getInts(var5, var6, var7, var8);
/* 23:26 */     int[] var10 = IntCache.getIntCache(par3 * par4);
/* 24:28 */     for (int var11 = 0; var11 < par4; var11++) {
/* 25:30 */       for (int var12 = 0; var12 < par3; var12++)
/* 26:   */       {
/* 27:32 */         int var13 = var9[(var12 + 1 + (var11 + 1 - 1) * (par3 + 2))];
/* 28:33 */         int var14 = var9[(var12 + 1 + 1 + (var11 + 1) * (par3 + 2))];
/* 29:34 */         int var15 = var9[(var12 + 1 - 1 + (var11 + 1) * (par3 + 2))];
/* 30:35 */         int var16 = var9[(var12 + 1 + (var11 + 1 + 1) * (par3 + 2))];
/* 31:36 */         int var17 = var9[(var12 + 1 + (var11 + 1) * var7)];
/* 32:37 */         int var18 = 0;
/* 33:39 */         if (var13 == 0) {
/* 34:41 */           var18++;
/* 35:   */         }
/* 36:44 */         if (var14 == 0) {
/* 37:46 */           var18++;
/* 38:   */         }
/* 39:49 */         if (var15 == 0) {
/* 40:51 */           var18++;
/* 41:   */         }
/* 42:54 */         if (var16 == 0) {
/* 43:56 */           var18++;
/* 44:   */         }
/* 45:59 */         if ((var17 == 0) && (var18 > 3)) {
/* 46:61 */           var10[(var12 + var11 * par3)] = BiomeGenBase.field_150575_M.biomeID;
/* 47:   */         } else {
/* 48:65 */           var10[(var12 + var11 * par3)] = var17;
/* 49:   */         }
/* 50:   */       }
/* 51:   */     }
/* 52:70 */     return var10;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerDeepOcean
 * JD-Core Version:    0.7.0.1
 */