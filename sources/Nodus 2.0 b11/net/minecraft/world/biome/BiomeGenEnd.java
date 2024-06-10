/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.monster.EntityEnderman;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ 
/*  7:   */ public class BiomeGenEnd
/*  8:   */   extends BiomeGenBase
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000187";
/* 11:   */   
/* 12:   */   public BiomeGenEnd(int par1)
/* 13:   */   {
/* 14:12 */     super(par1);
/* 15:13 */     this.spawnableMonsterList.clear();
/* 16:14 */     this.spawnableCreatureList.clear();
/* 17:15 */     this.spawnableWaterCreatureList.clear();
/* 18:16 */     this.spawnableCaveCreatureList.clear();
/* 19:17 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
/* 20:18 */     this.topBlock = Blocks.dirt;
/* 21:19 */     this.fillerBlock = Blocks.dirt;
/* 22:20 */     this.theBiomeDecorator = new BiomeEndDecorator();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getSkyColorByTemp(float par1)
/* 26:   */   {
/* 27:28 */     return 0;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenEnd
 * JD-Core Version:    0.7.0.1
 */