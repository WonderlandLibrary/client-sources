/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.init.Blocks;
/*  5:   */ 
/*  6:   */ public class BiomeGenStoneBeach
/*  7:   */   extends BiomeGenBase
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000184";
/* 10:   */   
/* 11:   */   public BiomeGenStoneBeach(int p_i45384_1_)
/* 12:   */   {
/* 13:11 */     super(p_i45384_1_);
/* 14:12 */     this.spawnableCreatureList.clear();
/* 15:13 */     this.topBlock = Blocks.stone;
/* 16:14 */     this.fillerBlock = Blocks.stone;
/* 17:15 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 18:16 */     this.theBiomeDecorator.deadBushPerChunk = 0;
/* 19:17 */     this.theBiomeDecorator.reedsPerChunk = 0;
/* 20:18 */     this.theBiomeDecorator.cactiPerChunk = 0;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenStoneBeach
 * JD-Core Version:    0.7.0.1
 */