/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.entity.passive.EntityHorse;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/* 10:   */ import net.minecraft.world.gen.feature.WorldGenDoublePlant;
/* 11:   */ import net.minecraft.world.gen.feature.WorldGenSavannaTree;
/* 12:   */ 
/* 13:   */ public class BiomeGenSavanna
/* 14:   */   extends BiomeGenBase
/* 15:   */ {
/* 16:13 */   private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);
/* 17:   */   private static final String __OBFID = "CL_00000182";
/* 18:   */   
/* 19:   */   protected BiomeGenSavanna(int p_i45383_1_)
/* 20:   */   {
/* 21:18 */     super(p_i45383_1_);
/* 22:19 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 1, 2, 6));
/* 23:20 */     this.theBiomeDecorator.treesPerChunk = 1;
/* 24:21 */     this.theBiomeDecorator.flowersPerChunk = 4;
/* 25:22 */     this.theBiomeDecorator.grassPerChunk = 20;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
/* 29:   */   {
/* 30:27 */     return p_150567_1_.nextInt(5) > 0 ? field_150627_aC : this.worldGeneratorTrees;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected BiomeGenBase func_150566_k()
/* 34:   */   {
/* 35:32 */     Mutated var1 = new Mutated(this.biomeID + 128, this);
/* 36:33 */     var1.temperature = ((this.temperature + 1.0F) * 0.5F);
/* 37:34 */     var1.minHeight = (this.minHeight * 0.5F + 0.3F);
/* 38:35 */     var1.maxHeight = (this.maxHeight * 0.5F + 1.2F);
/* 39:36 */     return var1;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/* 43:   */   {
/* 44:41 */     field_150610_ae.func_150548_a(2);
/* 45:43 */     for (int var5 = 0; var5 < 7; var5++)
/* 46:   */     {
/* 47:45 */       int var6 = par3 + par2Random.nextInt(16) + 8;
/* 48:46 */       int var7 = par4 + par2Random.nextInt(16) + 8;
/* 49:47 */       int var8 = par2Random.nextInt(par1World.getHeightValue(var6, var7) + 32);
/* 50:48 */       field_150610_ae.generate(par1World, par2Random, var6, var8, var7);
/* 51:   */     }
/* 52:51 */     super.decorate(par1World, par2Random, par3, par4);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static class Mutated
/* 56:   */     extends BiomeGenMutated
/* 57:   */   {
/* 58:   */     private static final String __OBFID = "CL_00000183";
/* 59:   */     
/* 60:   */     public Mutated(int p_i45382_1_, BiomeGenBase p_i45382_2_)
/* 61:   */     {
/* 62:60 */       super(p_i45382_2_);
/* 63:61 */       this.theBiomeDecorator.treesPerChunk = 2;
/* 64:62 */       this.theBiomeDecorator.flowersPerChunk = 2;
/* 65:63 */       this.theBiomeDecorator.grassPerChunk = 5;
/* 66:   */     }
/* 67:   */     
/* 68:   */     public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
/* 69:   */     {
/* 70:68 */       this.topBlock = Blocks.grass;
/* 71:69 */       this.field_150604_aj = 0;
/* 72:70 */       this.fillerBlock = Blocks.dirt;
/* 73:72 */       if (p_150573_7_ > 1.75D)
/* 74:   */       {
/* 75:74 */         this.topBlock = Blocks.stone;
/* 76:75 */         this.fillerBlock = Blocks.stone;
/* 77:   */       }
/* 78:77 */       else if (p_150573_7_ > -0.5D)
/* 79:   */       {
/* 80:79 */         this.topBlock = Blocks.dirt;
/* 81:80 */         this.field_150604_aj = 1;
/* 82:   */       }
/* 83:83 */       func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
/* 84:   */     }
/* 85:   */     
/* 86:   */     public void decorate(World par1World, Random par2Random, int par3, int par4)
/* 87:   */     {
/* 88:88 */       this.theBiomeDecorator.func_150512_a(par1World, par2Random, this, par3, par4);
/* 89:   */     }
/* 90:   */   }
/* 91:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenSavanna
 * JD-Core Version:    0.7.0.1
 */