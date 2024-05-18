/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.entity.passive.EntityMooshroom;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeGenMushroomIsland
/*    */   extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenMushroomIsland(int p_i1984_1_) {
/* 10 */     super(p_i1984_1_);
/* 11 */     this.theBiomeDecorator.treesPerChunk = -100;
/* 12 */     this.theBiomeDecorator.flowersPerChunk = -100;
/* 13 */     this.theBiomeDecorator.grassPerChunk = -100;
/* 14 */     this.theBiomeDecorator.mushroomsPerChunk = 1;
/* 15 */     this.theBiomeDecorator.bigMushroomsPerChunk = 1;
/* 16 */     this.topBlock = Blocks.mycelium.getDefaultState();
/* 17 */     this.spawnableMonsterList.clear();
/* 18 */     this.spawnableCreatureList.clear();
/* 19 */     this.spawnableWaterCreatureList.clear();
/* 20 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityMooshroom.class, 8, 4, 8));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\biome\BiomeGenMushroomIsland.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */