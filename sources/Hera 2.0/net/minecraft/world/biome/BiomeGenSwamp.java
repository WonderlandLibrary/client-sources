/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockFlower;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.monster.EntitySlime;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ 
/*    */ public class BiomeGenSwamp
/*    */   extends BiomeGenBase
/*    */ {
/*    */   protected BiomeGenSwamp(int p_i1988_1_) {
/* 17 */     super(p_i1988_1_);
/* 18 */     this.theBiomeDecorator.treesPerChunk = 2;
/* 19 */     this.theBiomeDecorator.flowersPerChunk = 1;
/* 20 */     this.theBiomeDecorator.deadBushPerChunk = 1;
/* 21 */     this.theBiomeDecorator.mushroomsPerChunk = 8;
/* 22 */     this.theBiomeDecorator.reedsPerChunk = 10;
/* 23 */     this.theBiomeDecorator.clayPerChunk = 1;
/* 24 */     this.theBiomeDecorator.waterlilyPerChunk = 4;
/* 25 */     this.theBiomeDecorator.sandPerChunk2 = 0;
/* 26 */     this.theBiomeDecorator.sandPerChunk = 0;
/* 27 */     this.theBiomeDecorator.grassPerChunk = 5;
/* 28 */     this.waterColorMultiplier = 14745518;
/* 29 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntitySlime.class, 1, 1, 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 34 */     return (WorldGenAbstractTree)this.worldGeneratorSwamp;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrassColorAtPos(BlockPos pos) {
/* 39 */     double d0 = GRASS_COLOR_NOISE.func_151601_a(pos.getX() * 0.0225D, pos.getZ() * 0.0225D);
/* 40 */     return (d0 < -0.1D) ? 5011004 : 6975545;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFoliageColorAtPos(BlockPos pos) {
/* 45 */     return 6975545;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/* 50 */     return BlockFlower.EnumFlowerType.BLUE_ORCHID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int p_180622_4_, int p_180622_5_, double p_180622_6_) {
/* 55 */     double d0 = GRASS_COLOR_NOISE.func_151601_a(p_180622_4_ * 0.25D, p_180622_5_ * 0.25D);
/*    */     
/* 57 */     if (d0 > 0.0D) {
/*    */       
/* 59 */       int i = p_180622_4_ & 0xF;
/* 60 */       int j = p_180622_5_ & 0xF;
/*    */       
/* 62 */       for (int k = 255; k >= 0; k--) {
/*    */         
/* 64 */         if (chunkPrimerIn.getBlockState(j, k, i).getBlock().getMaterial() != Material.air) {
/*    */           
/* 66 */           if (k == 62 && chunkPrimerIn.getBlockState(j, k, i).getBlock() != Blocks.water) {
/*    */             
/* 68 */             chunkPrimerIn.setBlockState(j, k, i, Blocks.water.getDefaultState());
/*    */             
/* 70 */             if (d0 < 0.12D)
/*    */             {
/* 72 */               chunkPrimerIn.setBlockState(j, k + 1, i, Blocks.waterlily.getDefaultState());
/*    */             }
/*    */           } 
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 81 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, p_180622_4_, p_180622_5_, p_180622_6_);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\biome\BiomeGenSwamp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */