/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*  8:   */ import net.minecraft.world.gen.feature.WorldGenIcePath;
/*  9:   */ import net.minecraft.world.gen.feature.WorldGenIceSpike;
/* 10:   */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/* 11:   */ 
/* 12:   */ public class BiomeGenSnow
/* 13:   */   extends BiomeGenBase
/* 14:   */ {
/* 15:   */   private boolean field_150615_aC;
/* 16:14 */   private WorldGenIceSpike field_150616_aD = new WorldGenIceSpike();
/* 17:15 */   private WorldGenIcePath field_150617_aE = new WorldGenIcePath(4);
/* 18:   */   private static final String __OBFID = "CL_00000174";
/* 19:   */   
/* 20:   */   public BiomeGenSnow(int p_i45378_1_, boolean p_i45378_2_)
/* 21:   */   {
/* 22:20 */     super(p_i45378_1_);
/* 23:21 */     this.field_150615_aC = p_i45378_2_;
/* 24:23 */     if (p_i45378_2_) {
/* 25:25 */       this.topBlock = Blocks.snow;
/* 26:   */     }
/* 27:28 */     this.spawnableCreatureList.clear();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/* 31:   */   {
/* 32:33 */     if (this.field_150615_aC)
/* 33:   */     {
/* 34:39 */       for (int var5 = 0; var5 < 3; var5++)
/* 35:   */       {
/* 36:41 */         int var6 = par3 + par2Random.nextInt(16) + 8;
/* 37:42 */         int var7 = par4 + par2Random.nextInt(16) + 8;
/* 38:43 */         this.field_150616_aD.generate(par1World, par2Random, var6, par1World.getHeightValue(var6, var7), var7);
/* 39:   */       }
/* 40:46 */       for (var5 = 0; var5 < 2; var5++)
/* 41:   */       {
/* 42:48 */         int var6 = par3 + par2Random.nextInt(16) + 8;
/* 43:49 */         int var7 = par4 + par2Random.nextInt(16) + 8;
/* 44:50 */         this.field_150617_aE.generate(par1World, par2Random, var6, par1World.getHeightValue(var6, var7), var7);
/* 45:   */       }
/* 46:   */     }
/* 47:54 */     super.decorate(par1World, par2Random, par3, par4);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
/* 51:   */   {
/* 52:59 */     return new WorldGenTaiga2(false);
/* 53:   */   }
/* 54:   */   
/* 55:   */   protected BiomeGenBase func_150566_k()
/* 56:   */   {
/* 57:64 */     BiomeGenBase var1 = new BiomeGenSnow(this.biomeID + 128, true).func_150557_a(13828095, true).setBiomeName(this.biomeName + " Spikes").setEnableSnow().setTemperatureRainfall(0.0F, 0.5F).func_150570_a(new BiomeGenBase.Height(this.minHeight + 0.1F, this.maxHeight + 0.1F));
/* 58:65 */     var1.minHeight = (this.minHeight + 0.3F);
/* 59:66 */     var1.maxHeight = (this.maxHeight + 0.4F);
/* 60:67 */     return var1;
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenSnow
 * JD-Core Version:    0.7.0.1
 */