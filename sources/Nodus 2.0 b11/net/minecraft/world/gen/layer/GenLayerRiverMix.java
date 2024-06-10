/*  1:   */ package net.minecraft.world.gen.layer;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.biome.BiomeGenBase;
/*  4:   */ 
/*  5:   */ public class GenLayerRiverMix
/*  6:   */   extends GenLayer
/*  7:   */ {
/*  8:   */   private GenLayer biomePatternGeneratorChain;
/*  9:   */   private GenLayer riverPatternGeneratorChain;
/* 10:   */   private static final String __OBFID = "CL_00000567";
/* 11:   */   
/* 12:   */   public GenLayerRiverMix(long par1, GenLayer par3GenLayer, GenLayer par4GenLayer)
/* 13:   */   {
/* 14:13 */     super(par1);
/* 15:14 */     this.biomePatternGeneratorChain = par3GenLayer;
/* 16:15 */     this.riverPatternGeneratorChain = par4GenLayer;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void initWorldGenSeed(long par1)
/* 20:   */   {
/* 21:24 */     this.biomePatternGeneratorChain.initWorldGenSeed(par1);
/* 22:25 */     this.riverPatternGeneratorChain.initWorldGenSeed(par1);
/* 23:26 */     super.initWorldGenSeed(par1);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int[] getInts(int par1, int par2, int par3, int par4)
/* 27:   */   {
/* 28:35 */     int[] var5 = this.biomePatternGeneratorChain.getInts(par1, par2, par3, par4);
/* 29:36 */     int[] var6 = this.riverPatternGeneratorChain.getInts(par1, par2, par3, par4);
/* 30:37 */     int[] var7 = IntCache.getIntCache(par3 * par4);
/* 31:39 */     for (int var8 = 0; var8 < par3 * par4; var8++) {
/* 32:41 */       if ((var5[var8] != BiomeGenBase.ocean.biomeID) && (var5[var8] != BiomeGenBase.field_150575_M.biomeID))
/* 33:   */       {
/* 34:43 */         if (var6[var8] == BiomeGenBase.river.biomeID)
/* 35:   */         {
/* 36:45 */           if (var5[var8] == BiomeGenBase.icePlains.biomeID) {
/* 37:47 */             var7[var8] = BiomeGenBase.frozenRiver.biomeID;
/* 38:49 */           } else if ((var5[var8] != BiomeGenBase.mushroomIsland.biomeID) && (var5[var8] != BiomeGenBase.mushroomIslandShore.biomeID)) {
/* 39:51 */             var6[var8] &= 0xFF;
/* 40:   */           } else {
/* 41:55 */             var7[var8] = BiomeGenBase.mushroomIslandShore.biomeID;
/* 42:   */           }
/* 43:   */         }
/* 44:   */         else {
/* 45:60 */           var7[var8] = var5[var8];
/* 46:   */         }
/* 47:   */       }
/* 48:   */       else {
/* 49:65 */         var7[var8] = var5[var8];
/* 50:   */       }
/* 51:   */     }
/* 52:69 */     return var7;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayerRiverMix
 * JD-Core Version:    0.7.0.1
 */