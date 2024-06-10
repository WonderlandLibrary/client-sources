/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ public class GenLayerAddSnow
/*  4:   */   extends GenLayer
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000553";
/*  7:   */   
/*  8:   */   public GenLayerAddSnow(long par1, GenLayer par3GenLayer)
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
/* 25:30 */         int var13 = var9[(var12 + 1 + (var11 + 1) * var7)];
/* 26:31 */         initChunkSeed(var12 + par1, var11 + par2);
/* 27:33 */         if (var13 == 0)
/* 28:   */         {
/* 29:35 */           var10[(var12 + var11 * par3)] = 0;
/* 30:   */         }
/* 31:   */         else
/* 32:   */         {
/* 33:39 */           int var14 = nextInt(6);
/* 34:   */           byte var15;
/* 35:   */           byte var15;
/* 36:42 */           if (var14 == 0)
/* 37:   */           {
/* 38:44 */             var15 = 4;
/* 39:   */           }
/* 40:   */           else
/* 41:   */           {
/* 42:   */             byte var15;
/* 43:46 */             if (var14 <= 1) {
/* 44:48 */               var15 = 3;
/* 45:   */             } else {
/* 46:52 */               var15 = 1;
/* 47:   */             }
/* 48:   */           }
/* 49:55 */           var10[(var12 + var11 * par3)] = var15;
/* 50:   */         }
/* 51:   */       }
/* 52:   */     }
/* 53:60 */     return var10;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerAddSnow
 * JD-Core Version:    0.7.0.1
 */