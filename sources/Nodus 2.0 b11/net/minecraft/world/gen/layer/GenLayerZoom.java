/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ public class GenLayerZoom
/*  4:   */   extends GenLayer
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000572";
/*  7:   */   
/*  8:   */   public GenLayerZoom(long par1, GenLayer par3GenLayer)
/*  9:   */   {
/* 10: 9 */     super(par1);
/* 11:10 */     this.parent = par3GenLayer;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int[] getInts(int par1, int par2, int par3, int par4)
/* 15:   */   {
/* 16:19 */     int var5 = par1 >> 1;
/* 17:20 */     int var6 = par2 >> 1;
/* 18:21 */     int var7 = (par3 >> 1) + 2;
/* 19:22 */     int var8 = (par4 >> 1) + 2;
/* 20:23 */     int[] var9 = this.parent.getInts(var5, var6, var7, var8);
/* 21:24 */     int var10 = var7 - 1 << 1;
/* 22:25 */     int var11 = var8 - 1 << 1;
/* 23:26 */     int[] var12 = IntCache.getIntCache(var10 * var11);
/* 24:29 */     for (int var13 = 0; var13 < var8 - 1; var13++)
/* 25:   */     {
/* 26:31 */       int var14 = (var13 << 1) * var10;
/* 27:32 */       int var15 = 0;
/* 28:33 */       int var16 = var9[(var15 + 0 + (var13 + 0) * var7)];
/* 29:35 */       for (int var17 = var9[(var15 + 0 + (var13 + 1) * var7)]; var15 < var7 - 1; var15++)
/* 30:   */       {
/* 31:37 */         initChunkSeed(var15 + var5 << 1, var13 + var6 << 1);
/* 32:38 */         int var18 = var9[(var15 + 1 + (var13 + 0) * var7)];
/* 33:39 */         int var19 = var9[(var15 + 1 + (var13 + 1) * var7)];
/* 34:40 */         var12[var14] = var16;
/* 35:41 */         var12[(var14++ + var10)] = func_151619_a(new int[] { var16, var17 });
/* 36:42 */         var12[var14] = func_151619_a(new int[] { var16, var18 });
/* 37:43 */         var12[(var14++ + var10)] = func_151617_b(var16, var18, var17, var19);
/* 38:44 */         var16 = var18;
/* 39:45 */         var17 = var19;
/* 40:   */       }
/* 41:   */     }
/* 42:49 */     int[] var20 = IntCache.getIntCache(par3 * par4);
/* 43:51 */     for (int var14 = 0; var14 < par4; var14++) {
/* 44:53 */       System.arraycopy(var12, (var14 + (par2 & 0x1)) * var10 + (par1 & 0x1), var20, var14 * par3, par3);
/* 45:   */     }
/* 46:56 */     return var20;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static GenLayer magnify(long par0, GenLayer par2GenLayer, int par3)
/* 50:   */   {
/* 51:64 */     Object var4 = par2GenLayer;
/* 52:66 */     for (int var5 = 0; var5 < par3; var5++) {
/* 53:68 */       var4 = new GenLayerZoom(par0 + var5, (GenLayer)var4);
/* 54:   */     }
/* 55:71 */     return (GenLayer)var4;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerZoom
 * JD-Core Version:    0.7.0.1
 */