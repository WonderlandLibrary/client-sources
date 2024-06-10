/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.biome.BiomeGenBase;
/*  4:   */ 
/*  5:   */ public class GenLayerRareBiome
/*  6:   */   extends GenLayer
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00000562";
/*  9:   */   
/* 10:   */   public GenLayerRareBiome(long p_i45478_1_, GenLayer p_i45478_3_)
/* 11:   */   {
/* 12:11 */     super(p_i45478_1_);
/* 13:12 */     this.parent = p_i45478_3_;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public int[] getInts(int par1, int par2, int par3, int par4)
/* 17:   */   {
/* 18:21 */     int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
/* 19:22 */     int[] var6 = IntCache.getIntCache(par3 * par4);
/* 20:24 */     for (int var7 = 0; var7 < par4; var7++) {
/* 21:26 */       for (int var8 = 0; var8 < par3; var8++)
/* 22:   */       {
/* 23:28 */         initChunkSeed(var8 + par1, var7 + par2);
/* 24:29 */         int var9 = var5[(var8 + 1 + (var7 + 1) * (par3 + 2))];
/* 25:31 */         if (nextInt(57) == 0)
/* 26:   */         {
/* 27:33 */           if (var9 == BiomeGenBase.plains.biomeID) {
/* 28:35 */             var6[(var8 + var7 * par3)] = (BiomeGenBase.plains.biomeID + 128);
/* 29:   */           } else {
/* 30:39 */             var6[(var8 + var7 * par3)] = var9;
/* 31:   */           }
/* 32:   */         }
/* 33:   */         else {
/* 34:44 */           var6[(var8 + var7 * par3)] = var9;
/* 35:   */         }
/* 36:   */       }
/* 37:   */     }
/* 38:49 */     return var6;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerRareBiome
 * JD-Core Version:    0.7.0.1
 */