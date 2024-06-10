/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.monster.EntityGhast;
/*  5:   */ import net.minecraft.entity.monster.EntityMagmaCube;
/*  6:   */ import net.minecraft.entity.monster.EntityPigZombie;
/*  7:   */ 
/*  8:   */ public class BiomeGenHell
/*  9:   */   extends BiomeGenBase
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000173";
/* 12:   */   
/* 13:   */   public BiomeGenHell(int par1)
/* 14:   */   {
/* 15:13 */     super(par1);
/* 16:14 */     this.spawnableMonsterList.clear();
/* 17:15 */     this.spawnableCreatureList.clear();
/* 18:16 */     this.spawnableWaterCreatureList.clear();
/* 19:17 */     this.spawnableCaveCreatureList.clear();
/* 20:18 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityGhast.class, 50, 4, 4));
/* 21:19 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
/* 22:20 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 1, 4, 4));
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenHell
 * JD-Core Version:    0.7.0.1
 */