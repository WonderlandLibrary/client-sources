/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.entity.passive.EntityChicken;
/*  6:   */ import net.minecraft.entity.passive.EntityOcelot;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/* 10:   */ import net.minecraft.world.gen.feature.WorldGenMegaJungle;
/* 11:   */ import net.minecraft.world.gen.feature.WorldGenMelon;
/* 12:   */ import net.minecraft.world.gen.feature.WorldGenShrub;
/* 13:   */ import net.minecraft.world.gen.feature.WorldGenTallGrass;
/* 14:   */ import net.minecraft.world.gen.feature.WorldGenTrees;
/* 15:   */ import net.minecraft.world.gen.feature.WorldGenVines;
/* 16:   */ import net.minecraft.world.gen.feature.WorldGenerator;
/* 17:   */ 
/* 18:   */ public class BiomeGenJungle
/* 19:   */   extends BiomeGenBase
/* 20:   */ {
/* 21:   */   private boolean field_150614_aC;
/* 22:   */   private static final String __OBFID = "CL_00000175";
/* 23:   */   
/* 24:   */   public BiomeGenJungle(int p_i45379_1_, boolean p_i45379_2_)
/* 25:   */   {
/* 26:24 */     super(p_i45379_1_);
/* 27:25 */     this.field_150614_aC = p_i45379_2_;
/* 28:27 */     if (p_i45379_2_) {
/* 29:29 */       this.theBiomeDecorator.treesPerChunk = 2;
/* 30:   */     } else {
/* 31:33 */       this.theBiomeDecorator.treesPerChunk = 50;
/* 32:   */     }
/* 33:36 */     this.theBiomeDecorator.grassPerChunk = 25;
/* 34:37 */     this.theBiomeDecorator.flowersPerChunk = 4;
/* 35:39 */     if (!p_i45379_2_) {
/* 36:41 */       this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
/* 37:   */     }
/* 38:44 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 4, 4));
/* 39:   */   }
/* 40:   */   
/* 41:   */   public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
/* 42:   */   {
/* 43:49 */     return (!this.field_150614_aC) && (p_150567_1_.nextInt(3) == 0) ? new WorldGenMegaJungle(false, 10, 20, 3, 3) : p_150567_1_.nextInt(2) == 0 ? new WorldGenShrub(3, 0) : p_150567_1_.nextInt(10) == 0 ? this.worldGeneratorBigTree : new WorldGenTrees(false, 4 + p_150567_1_.nextInt(7), 3, 3, true);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
/* 47:   */   {
/* 48:57 */     return par1Random.nextInt(4) == 0 ? new WorldGenTallGrass(Blocks.tallgrass, 2) : new WorldGenTallGrass(Blocks.tallgrass, 1);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/* 52:   */   {
/* 53:62 */     super.decorate(par1World, par2Random, par3, par4);
/* 54:63 */     int var5 = par3 + par2Random.nextInt(16) + 8;
/* 55:64 */     int var6 = par4 + par2Random.nextInt(16) + 8;
/* 56:65 */     int var7 = par2Random.nextInt(par1World.getHeightValue(var5, var6) * 2);
/* 57:66 */     new WorldGenMelon().generate(par1World, par2Random, var5, var7, var6);
/* 58:67 */     WorldGenVines var10 = new WorldGenVines();
/* 59:69 */     for (var6 = 0; var6 < 50; var6++)
/* 60:   */     {
/* 61:71 */       var7 = par3 + par2Random.nextInt(16) + 8;
/* 62:72 */       short var8 = 128;
/* 63:73 */       int var9 = par4 + par2Random.nextInt(16) + 8;
/* 64:74 */       var10.generate(par1World, par2Random, var7, var8, var9);
/* 65:   */     }
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenJungle
 * JD-Core Version:    0.7.0.1
 */