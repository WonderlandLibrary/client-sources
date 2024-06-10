/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ public class GenLayerRiverInit
/*  4:   */   extends GenLayer
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000565";
/*  7:   */   
/*  8:   */   public GenLayerRiverInit(long par1, GenLayer par3GenLayer)
/*  9:   */   {
/* 10: 9 */     super(par1);
/* 11:10 */     this.parent = par3GenLayer;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int[] getInts(int par1, int par2, int par3, int par4)
/* 15:   */   {
/* 16:19 */     int[] var5 = this.parent.getInts(par1, par2, par3, par4);
/* 17:20 */     int[] var6 = IntCache.getIntCache(par3 * par4);
/* 18:22 */     for (int var7 = 0; var7 < par4; var7++) {
/* 19:24 */       for (int var8 = 0; var8 < par3; var8++)
/* 20:   */       {
/* 21:26 */         initChunkSeed(var8 + par1, var7 + par2);
/* 22:27 */         var6[(var8 + var7 * par3)] = (var5[(var8 + var7 * par3)] > 0 ? nextInt(299999) + 2 : 0);
/* 23:   */       }
/* 24:   */     }
/* 25:31 */     return var6;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerRiverInit
 * JD-Core Version:    0.7.0.1
 */