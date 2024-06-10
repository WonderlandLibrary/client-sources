/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ public class GenLayerIsland
/*  4:   */   extends GenLayer
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000558";
/*  7:   */   
/*  8:   */   public GenLayerIsland(long par1)
/*  9:   */   {
/* 10: 9 */     super(par1);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int[] getInts(int par1, int par2, int par3, int par4)
/* 14:   */   {
/* 15:18 */     int[] var5 = IntCache.getIntCache(par3 * par4);
/* 16:20 */     for (int var6 = 0; var6 < par4; var6++) {
/* 17:22 */       for (int var7 = 0; var7 < par3; var7++)
/* 18:   */       {
/* 19:24 */         initChunkSeed(par1 + var7, par2 + var6);
/* 20:25 */         var5[(var7 + var6 * par3)] = (nextInt(10) == 0 ? 1 : 0);
/* 21:   */       }
/* 22:   */     }
/* 23:29 */     if ((par1 > -par3) && (par1 <= 0) && (par2 > -par4) && (par2 <= 0)) {
/* 24:31 */       var5[(-par1 + -par2 * par3)] = 1;
/* 25:   */     }
/* 26:34 */     return var5;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerIsland
 * JD-Core Version:    0.7.0.1
 */