/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeGenBeach
/*    */   extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenBeach(int p_i1969_1_) {
/*  9 */     super(p_i1969_1_);
/* 10 */     this.spawnableCreatureList.clear();
/* 11 */     this.topBlock = Blocks.sand.getDefaultState();
/* 12 */     this.fillerBlock = Blocks.sand.getDefaultState();
/* 13 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 14 */     this.theBiomeDecorator.deadBushPerChunk = 0;
/* 15 */     this.theBiomeDecorator.reedsPerChunk = 0;
/* 16 */     this.theBiomeDecorator.cactiPerChunk = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\biome\BiomeGenBeach.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */