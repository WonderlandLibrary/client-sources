/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*  8:   */ 
/*  9:   */ public class BiomeGenMutated
/* 10:   */   extends BiomeGenBase
/* 11:   */ {
/* 12:   */   protected BiomeGenBase field_150611_aD;
/* 13:   */   private static final String __OBFID = "CL_00000178";
/* 14:   */   
/* 15:   */   public BiomeGenMutated(int p_i45381_1_, BiomeGenBase p_i45381_2_)
/* 16:   */   {
/* 17:16 */     super(p_i45381_1_);
/* 18:17 */     this.field_150611_aD = p_i45381_2_;
/* 19:18 */     func_150557_a(p_i45381_2_.color, true);
/* 20:19 */     this.biomeName = (p_i45381_2_.biomeName + " M");
/* 21:20 */     this.topBlock = p_i45381_2_.topBlock;
/* 22:21 */     this.fillerBlock = p_i45381_2_.fillerBlock;
/* 23:22 */     this.field_76754_C = p_i45381_2_.field_76754_C;
/* 24:23 */     this.minHeight = p_i45381_2_.minHeight;
/* 25:24 */     this.maxHeight = p_i45381_2_.maxHeight;
/* 26:25 */     this.temperature = p_i45381_2_.temperature;
/* 27:26 */     this.rainfall = p_i45381_2_.rainfall;
/* 28:27 */     this.waterColorMultiplier = p_i45381_2_.waterColorMultiplier;
/* 29:28 */     this.enableSnow = p_i45381_2_.enableSnow;
/* 30:29 */     this.enableRain = p_i45381_2_.enableRain;
/* 31:30 */     this.spawnableCreatureList = new ArrayList(p_i45381_2_.spawnableCreatureList);
/* 32:31 */     this.spawnableMonsterList = new ArrayList(p_i45381_2_.spawnableMonsterList);
/* 33:32 */     this.spawnableCaveCreatureList = new ArrayList(p_i45381_2_.spawnableCaveCreatureList);
/* 34:33 */     this.spawnableWaterCreatureList = new ArrayList(p_i45381_2_.spawnableWaterCreatureList);
/* 35:34 */     this.temperature = p_i45381_2_.temperature;
/* 36:35 */     this.rainfall = p_i45381_2_.rainfall;
/* 37:36 */     this.minHeight = (p_i45381_2_.minHeight + 0.1F);
/* 38:37 */     this.maxHeight = (p_i45381_2_.maxHeight + 0.2F);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/* 42:   */   {
/* 43:42 */     this.field_150611_aD.theBiomeDecorator.func_150512_a(par1World, par2Random, this, par3, par4);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
/* 47:   */   {
/* 48:47 */     this.field_150611_aD.func_150573_a(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public float getSpawningChance()
/* 52:   */   {
/* 53:55 */     return this.field_150611_aD.getSpawningChance();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
/* 57:   */   {
/* 58:60 */     return this.field_150611_aD.func_150567_a(p_150567_1_);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_)
/* 62:   */   {
/* 63:68 */     return this.field_150611_aD.getBiomeFoliageColor(p_150571_1_, p_150571_2_, p_150571_2_);
/* 64:   */   }
/* 65:   */   
/* 66:   */   public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_)
/* 67:   */   {
/* 68:76 */     return this.field_150611_aD.getBiomeGrassColor(p_150558_1_, p_150558_2_, p_150558_2_);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public Class func_150562_l()
/* 72:   */   {
/* 73:81 */     return this.field_150611_aD.func_150562_l();
/* 74:   */   }
/* 75:   */   
/* 76:   */   public boolean func_150569_a(BiomeGenBase p_150569_1_)
/* 77:   */   {
/* 78:86 */     return this.field_150611_aD.func_150569_a(p_150569_1_);
/* 79:   */   }
/* 80:   */   
/* 81:   */   public BiomeGenBase.TempCategory func_150561_m()
/* 82:   */   {
/* 83:91 */     return this.field_150611_aD.func_150561_m();
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenMutated
 * JD-Core Version:    0.7.0.1
 */