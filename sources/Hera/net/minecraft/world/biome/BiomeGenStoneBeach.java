/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeGenStoneBeach
/*    */   extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenStoneBeach(int p_i45384_1_) {
/*  9 */     super(p_i45384_1_);
/* 10 */     this.spawnableCreatureList.clear();
/* 11 */     this.topBlock = Blocks.stone.getDefaultState();
/* 12 */     this.fillerBlock = Blocks.stone.getDefaultState();
/* 13 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 14 */     this.theBiomeDecorator.deadBushPerChunk = 0;
/* 15 */     this.theBiomeDecorator.reedsPerChunk = 0;
/* 16 */     this.theBiomeDecorator.cactiPerChunk = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\biome\BiomeGenStoneBeach.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */