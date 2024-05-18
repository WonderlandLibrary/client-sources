/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.gen.feature.WorldGenDesertWells;
/*    */ 
/*    */ public class BiomeGenDesert
/*    */   extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenDesert(int p_i1977_1_) {
/* 13 */     super(p_i1977_1_);
/* 14 */     this.spawnableCreatureList.clear();
/* 15 */     this.topBlock = Blocks.sand.getDefaultState();
/* 16 */     this.fillerBlock = Blocks.sand.getDefaultState();
/* 17 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 18 */     this.theBiomeDecorator.deadBushPerChunk = 2;
/* 19 */     this.theBiomeDecorator.reedsPerChunk = 50;
/* 20 */     this.theBiomeDecorator.cactiPerChunk = 10;
/* 21 */     this.spawnableCreatureList.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 26 */     super.decorate(worldIn, rand, pos);
/*    */     
/* 28 */     if (rand.nextInt(1000) == 0) {
/*    */       
/* 30 */       int i = rand.nextInt(16) + 8;
/* 31 */       int j = rand.nextInt(16) + 8;
/* 32 */       BlockPos blockpos = worldIn.getHeight(pos.add(i, 0, j)).up();
/* 33 */       (new WorldGenDesertWells()).generate(worldIn, rand, blockpos);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\biome\BiomeGenDesert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */