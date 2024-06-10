/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.passive.EntityMooshroom;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ 
/*  7:   */ public class BiomeGenMushroomIsland
/*  8:   */   extends BiomeGenBase
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000177";
/* 11:   */   
/* 12:   */   public BiomeGenMushroomIsland(int par1)
/* 13:   */   {
/* 14:12 */     super(par1);
/* 15:13 */     this.theBiomeDecorator.treesPerChunk = -100;
/* 16:14 */     this.theBiomeDecorator.flowersPerChunk = -100;
/* 17:15 */     this.theBiomeDecorator.grassPerChunk = -100;
/* 18:16 */     this.theBiomeDecorator.mushroomsPerChunk = 1;
/* 19:17 */     this.theBiomeDecorator.bigMushroomsPerChunk = 1;
/* 20:18 */     this.topBlock = Blocks.mycelium;
/* 21:19 */     this.spawnableMonsterList.clear();
/* 22:20 */     this.spawnableCreatureList.clear();
/* 23:21 */     this.spawnableWaterCreatureList.clear();
/* 24:22 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityMooshroom.class, 8, 4, 8));
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenMushroomIsland
 * JD-Core Version:    0.7.0.1
 */