/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ public class GenLayerVoronoiZoom
/*  4:   */   extends GenLayer
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000571";
/*  7:   */   
/*  8:   */   public GenLayerVoronoiZoom(long par1, GenLayer par3GenLayer)
/*  9:   */   {
/* 10: 9 */     super(par1);
/* 11:10 */     this.parent = par3GenLayer;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int[] getInts(int par1, int par2, int par3, int par4)
/* 15:   */   {
/* 16:19 */     par1 -= 2;
/* 17:20 */     par2 -= 2;
/* 18:21 */     int var5 = par1 >> 2;
/* 19:22 */     int var6 = par2 >> 2;
/* 20:23 */     int var7 = (par3 >> 2) + 2;
/* 21:24 */     int var8 = (par4 >> 2) + 2;
/* 22:25 */     int[] var9 = this.parent.getInts(var5, var6, var7, var8);
/* 23:26 */     int var10 = var7 - 1 << 2;
/* 24:27 */     int var11 = var8 - 1 << 2;
/* 25:28 */     int[] var12 = IntCache.getIntCache(var10 * var11);
/* 26:31 */     for (int var13 = 0; var13 < var8 - 1; var13++)
/* 27:   */     {
/* 28:33 */       int var14 = 0;
/* 29:34 */       int var15 = var9[(var14 + 0 + (var13 + 0) * var7)];
/* 30:36 */       for (int var16 = var9[(var14 + 0 + (var13 + 1) * var7)]; var14 < var7 - 1; var14++)
/* 31:   */       {
/* 32:38 */         double var17 = 3.6D;
/* 33:39 */         initChunkSeed(var14 + var5 << 2, var13 + var6 << 2);
/* 34:40 */         double var19 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
/* 35:41 */         double var21 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
/* 36:42 */         initChunkSeed(var14 + var5 + 1 << 2, var13 + var6 << 2);
/* 37:43 */         double var23 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 38:44 */         double var25 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
/* 39:45 */         initChunkSeed(var14 + var5 << 2, var13 + var6 + 1 << 2);
/* 40:46 */         double var27 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
/* 41:47 */         double var29 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 42:48 */         initChunkSeed(var14 + var5 + 1 << 2, var13 + var6 + 1 << 2);
/* 43:49 */         double var31 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 44:50 */         double var33 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 45:51 */         int var35 = var9[(var14 + 1 + (var13 + 0) * var7)] & 0xFF;
/* 46:52 */         int var36 = var9[(var14 + 1 + (var13 + 1) * var7)] & 0xFF;
/* 47:54 */         for (int var37 = 0; var37 < 4; var37++)
/* 48:   */         {
/* 49:56 */           int var38 = ((var13 << 2) + var37) * var10 + (var14 << 2);
/* 50:58 */           for (int var39 = 0; var39 < 4; var39++)
/* 51:   */           {
/* 52:60 */             double var40 = (var37 - var21) * (var37 - var21) + (var39 - var19) * (var39 - var19);
/* 53:61 */             double var42 = (var37 - var25) * (var37 - var25) + (var39 - var23) * (var39 - var23);
/* 54:62 */             double var44 = (var37 - var29) * (var37 - var29) + (var39 - var27) * (var39 - var27);
/* 55:63 */             double var46 = (var37 - var33) * (var37 - var33) + (var39 - var31) * (var39 - var31);
/* 56:65 */             if ((var40 < var42) && (var40 < var44) && (var40 < var46)) {
/* 57:67 */               var12[(var38++)] = var15;
/* 58:69 */             } else if ((var42 < var40) && (var42 < var44) && (var42 < var46)) {
/* 59:71 */               var12[(var38++)] = var35;
/* 60:73 */             } else if ((var44 < var40) && (var44 < var42) && (var44 < var46)) {
/* 61:75 */               var12[(var38++)] = var16;
/* 62:   */             } else {
/* 63:79 */               var12[(var38++)] = var36;
/* 64:   */             }
/* 65:   */           }
/* 66:   */         }
/* 67:84 */         var15 = var35;
/* 68:85 */         var16 = var36;
/* 69:   */       }
/* 70:   */     }
/* 71:89 */     int[] var48 = IntCache.getIntCache(par3 * par4);
/* 72:91 */     for (int var14 = 0; var14 < par4; var14++) {
/* 73:93 */       System.arraycopy(var12, (var14 + (par2 & 0x3)) * var10 + (par1 & 0x3), var48, var14 * par3, par3);
/* 74:   */     }
/* 75:96 */     return var48;
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerVoronoiZoom
 * JD-Core Version:    0.7.0.1
 */