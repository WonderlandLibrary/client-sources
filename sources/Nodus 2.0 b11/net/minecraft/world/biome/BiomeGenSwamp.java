/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.block.material.Material;
/*  7:   */ import net.minecraft.entity.monster.EntitySlime;
/*  8:   */ import net.minecraft.init.Blocks;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/* 11:   */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/* 12:   */ 
/* 13:   */ public class BiomeGenSwamp
/* 14:   */   extends BiomeGenBase
/* 15:   */ {
/* 16:   */   private static final String __OBFID = "CL_00000185";
/* 17:   */   
/* 18:   */   protected BiomeGenSwamp(int par1)
/* 19:   */   {
/* 20:18 */     super(par1);
/* 21:19 */     this.theBiomeDecorator.treesPerChunk = 2;
/* 22:20 */     this.theBiomeDecorator.flowersPerChunk = 1;
/* 23:21 */     this.theBiomeDecorator.deadBushPerChunk = 1;
/* 24:22 */     this.theBiomeDecorator.mushroomsPerChunk = 8;
/* 25:23 */     this.theBiomeDecorator.reedsPerChunk = 10;
/* 26:24 */     this.theBiomeDecorator.clayPerChunk = 1;
/* 27:25 */     this.theBiomeDecorator.waterlilyPerChunk = 4;
/* 28:26 */     this.theBiomeDecorator.sandPerChunk2 = 0;
/* 29:27 */     this.theBiomeDecorator.sandPerChunk = 0;
/* 30:28 */     this.theBiomeDecorator.grassPerChunk = 5;
/* 31:29 */     this.waterColorMultiplier = 14745518;
/* 32:30 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 1, 1, 1));
/* 33:   */   }
/* 34:   */   
/* 35:   */   public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
/* 36:   */   {
/* 37:35 */     return this.worldGeneratorSwamp;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_)
/* 41:   */   {
/* 42:43 */     double var4 = field_150606_ad.func_151601_a(p_150558_1_ * 0.0225D, p_150558_3_ * 0.0225D);
/* 43:44 */     return var4 < -0.1D ? 5011004 : 6975545;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_)
/* 47:   */   {
/* 48:52 */     return 6975545;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_)
/* 52:   */   {
/* 53:57 */     return net.minecraft.block.BlockFlower.field_149859_a[1];
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
/* 57:   */   {
/* 58:62 */     double var9 = field_150606_ad.func_151601_a(p_150573_5_ * 0.25D, p_150573_6_ * 0.25D);
/* 59:64 */     if (var9 > 0.0D)
/* 60:   */     {
/* 61:66 */       int var11 = p_150573_5_ & 0xF;
/* 62:67 */       int var12 = p_150573_6_ & 0xF;
/* 63:68 */       int var13 = p_150573_3_.length / 256;
/* 64:70 */       for (int var14 = 255; var14 >= 0; var14--)
/* 65:   */       {
/* 66:72 */         int var15 = (var12 * 16 + var11) * var13 + var14;
/* 67:74 */         if ((p_150573_3_[var15] == null) || (p_150573_3_[var15].getMaterial() != Material.air))
/* 68:   */         {
/* 69:76 */           if ((var14 != 62) || (p_150573_3_[var15] == Blocks.water)) {
/* 70:   */             break;
/* 71:   */           }
/* 72:78 */           p_150573_3_[var15] = Blocks.water;
/* 73:80 */           if (var9 >= 0.12D) {
/* 74:   */             break;
/* 75:   */           }
/* 76:82 */           p_150573_3_[(var15 + 1)] = Blocks.waterlily;
/* 77:   */           
/* 78:   */ 
/* 79:   */ 
/* 80:86 */           break;
/* 81:   */         }
/* 82:   */       }
/* 83:   */     }
/* 84:91 */     func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
/* 85:   */   }
/* 86:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenSwamp
 * JD-Core Version:    0.7.0.1
 */