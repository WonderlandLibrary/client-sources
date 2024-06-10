/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.entity.passive.EntityHorse;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/*  8:   */ import net.minecraft.world.gen.feature.WorldGenDoublePlant;
/*  9:   */ 
/* 10:   */ public class BiomeGenPlains
/* 11:   */   extends BiomeGenBase
/* 12:   */ {
/* 13:   */   protected boolean field_150628_aC;
/* 14:   */   private static final String __OBFID = "CL_00000180";
/* 15:   */   
/* 16:   */   protected BiomeGenPlains(int par1)
/* 17:   */   {
/* 18:15 */     super(par1);
/* 19:16 */     setTemperatureRainfall(0.8F, 0.4F);
/* 20:17 */     func_150570_a(field_150593_e);
/* 21:18 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 5, 2, 6));
/* 22:19 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 23:20 */     this.theBiomeDecorator.flowersPerChunk = 4;
/* 24:21 */     this.theBiomeDecorator.grassPerChunk = 10;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_)
/* 28:   */   {
/* 29:26 */     double var5 = field_150606_ad.func_151601_a(p_150572_2_ / 200.0D, p_150572_4_ / 200.0D);
/* 30:29 */     if (var5 < -0.8D)
/* 31:   */     {
/* 32:31 */       int var7 = p_150572_1_.nextInt(4);
/* 33:32 */       return net.minecraft.block.BlockFlower.field_149859_a[(4 + var7)];
/* 34:   */     }
/* 35:34 */     if (p_150572_1_.nextInt(3) > 0)
/* 36:   */     {
/* 37:36 */       int var7 = p_150572_1_.nextInt(3);
/* 38:37 */       return var7 == 1 ? net.minecraft.block.BlockFlower.field_149859_a[3] : var7 == 0 ? net.minecraft.block.BlockFlower.field_149859_a[0] : net.minecraft.block.BlockFlower.field_149859_a[8];
/* 39:   */     }
/* 40:41 */     return net.minecraft.block.BlockFlower.field_149858_b[0];
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/* 44:   */   {
/* 45:47 */     double var5 = field_150606_ad.func_151601_a((par3 + 8) / 200.0D, (par4 + 8) / 200.0D);
/* 46:53 */     if (var5 < -0.8D)
/* 47:   */     {
/* 48:55 */       this.theBiomeDecorator.flowersPerChunk = 15;
/* 49:56 */       this.theBiomeDecorator.grassPerChunk = 5;
/* 50:   */     }
/* 51:   */     else
/* 52:   */     {
/* 53:60 */       this.theBiomeDecorator.flowersPerChunk = 4;
/* 54:61 */       this.theBiomeDecorator.grassPerChunk = 10;
/* 55:62 */       field_150610_ae.func_150548_a(2);
/* 56:64 */       for (int var7 = 0; var7 < 7; var7++)
/* 57:   */       {
/* 58:66 */         int var8 = par3 + par2Random.nextInt(16) + 8;
/* 59:67 */         int var9 = par4 + par2Random.nextInt(16) + 8;
/* 60:68 */         int var10 = par2Random.nextInt(par1World.getHeightValue(var8, var9) + 32);
/* 61:69 */         field_150610_ae.generate(par1World, par2Random, var8, var10, var9);
/* 62:   */       }
/* 63:   */     }
/* 64:73 */     if (this.field_150628_aC)
/* 65:   */     {
/* 66:75 */       field_150610_ae.func_150548_a(0);
/* 67:77 */       for (int var7 = 0; var7 < 10; var7++)
/* 68:   */       {
/* 69:79 */         int var8 = par3 + par2Random.nextInt(16) + 8;
/* 70:80 */         int var9 = par4 + par2Random.nextInt(16) + 8;
/* 71:81 */         int var10 = par2Random.nextInt(par1World.getHeightValue(var8, var9) + 32);
/* 72:82 */         field_150610_ae.generate(par1World, par2Random, var8, var10, var9);
/* 73:   */       }
/* 74:   */     }
/* 75:86 */     super.decorate(par1World, par2Random, par3, par4);
/* 76:   */   }
/* 77:   */   
/* 78:   */   protected BiomeGenBase func_150566_k()
/* 79:   */   {
/* 80:91 */     BiomeGenPlains var1 = new BiomeGenPlains(this.biomeID + 128);
/* 81:92 */     var1.setBiomeName("Sunflower Plains");
/* 82:93 */     var1.field_150628_aC = true;
/* 83:94 */     var1.setColor(9286496);
/* 84:95 */     var1.field_150609_ah = 14273354;
/* 85:96 */     return var1;
/* 86:   */   }
/* 87:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenPlains
 * JD-Core Version:    0.7.0.1
 */