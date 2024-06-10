/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ import net.minecraft.world.gen.feature.WorldGenDesertWells;
/*  8:   */ 
/*  9:   */ public class BiomeGenDesert
/* 10:   */   extends BiomeGenBase
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000167";
/* 13:   */   
/* 14:   */   public BiomeGenDesert(int par1)
/* 15:   */   {
/* 16:14 */     super(par1);
/* 17:15 */     this.spawnableCreatureList.clear();
/* 18:16 */     this.topBlock = Blocks.sand;
/* 19:17 */     this.fillerBlock = Blocks.sand;
/* 20:18 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 21:19 */     this.theBiomeDecorator.deadBushPerChunk = 2;
/* 22:20 */     this.theBiomeDecorator.reedsPerChunk = 50;
/* 23:21 */     this.theBiomeDecorator.cactiPerChunk = 10;
/* 24:22 */     this.spawnableCreatureList.clear();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/* 28:   */   {
/* 29:27 */     super.decorate(par1World, par2Random, par3, par4);
/* 30:29 */     if (par2Random.nextInt(1000) == 0)
/* 31:   */     {
/* 32:31 */       int var5 = par3 + par2Random.nextInt(16) + 8;
/* 33:32 */       int var6 = par4 + par2Random.nextInt(16) + 8;
/* 34:33 */       WorldGenDesertWells var7 = new WorldGenDesertWells();
/* 35:34 */       var7.generate(par1World, par2Random, var5, par1World.getHeightValue(var5, var6) + 1, var6);
/* 36:   */     }
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenDesert
 * JD-Core Version:    0.7.0.1
 */