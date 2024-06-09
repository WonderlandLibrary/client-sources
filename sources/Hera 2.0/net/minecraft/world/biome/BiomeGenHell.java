/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.entity.monster.EntityGhast;
/*    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*    */ import net.minecraft.entity.monster.EntityPigZombie;
/*    */ 
/*    */ public class BiomeGenHell
/*    */   extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenHell(int p_i1981_1_) {
/* 11 */     super(p_i1981_1_);
/* 12 */     this.spawnableMonsterList.clear();
/* 13 */     this.spawnableCreatureList.clear();
/* 14 */     this.spawnableWaterCreatureList.clear();
/* 15 */     this.spawnableCaveCreatureList.clear();
/* 16 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntityGhast.class, 50, 4, 4));
/* 17 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntityPigZombie.class, 100, 4, 4));
/* 18 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntityMagmaCube.class, 1, 4, 4));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\biome\BiomeGenHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */