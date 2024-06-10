/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.biome.BiomeGenBase;
/*  4:   */ 
/*  5:   */ public class GenLayerRiver
/*  6:   */   extends GenLayer
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00000566";
/*  9:   */   
/* 10:   */   public GenLayerRiver(long par1, GenLayer par3GenLayer)
/* 11:   */   {
/* 12:11 */     super(par1);
/* 13:12 */     this.parent = par3GenLayer;
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
/* 27:32 */         int var13 = func_151630_c(var9[(var12 + 0 + (var11 + 1) * var7)]);
/* 28:33 */         int var14 = func_151630_c(var9[(var12 + 2 + (var11 + 1) * var7)]);
/* 29:34 */         int var15 = func_151630_c(var9[(var12 + 1 + (var11 + 0) * var7)]);
/* 30:35 */         int var16 = func_151630_c(var9[(var12 + 1 + (var11 + 2) * var7)]);
/* 31:36 */         int var17 = func_151630_c(var9[(var12 + 1 + (var11 + 1) * var7)]);
/* 32:38 */         if ((var17 == var13) && (var17 == var15) && (var17 == var14) && (var17 == var16)) {
/* 33:40 */           var10[(var12 + var11 * par3)] = -1;
/* 34:   */         } else {
/* 35:44 */           var10[(var12 + var11 * par3)] = BiomeGenBase.river.biomeID;
/* 36:   */         }
/* 37:   */       }
/* 38:   */     }
/* 39:49 */     return var10;
/* 40:   */   }
/* 41:   */   
/* 42:   */   private int func_151630_c(int p_151630_1_)
/* 43:   */   {
/* 44:54 */     return p_151630_1_ >= 2 ? 2 + (p_151630_1_ & 0x1) : p_151630_1_;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerRiver
 * JD-Core Version:    0.7.0.1
 */