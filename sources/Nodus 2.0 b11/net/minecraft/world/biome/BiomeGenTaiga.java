/*   1:    */ package net.minecraft.world.biome;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.entity.passive.EntityWolf;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*  10:    */ import net.minecraft.world.gen.feature.WorldGenBlockBlob;
/*  11:    */ import net.minecraft.world.gen.feature.WorldGenDoublePlant;
/*  12:    */ import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
/*  13:    */ import net.minecraft.world.gen.feature.WorldGenTaiga1;
/*  14:    */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*  15:    */ import net.minecraft.world.gen.feature.WorldGenTallGrass;
/*  16:    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*  17:    */ 
/*  18:    */ public class BiomeGenTaiga
/*  19:    */   extends BiomeGenBase
/*  20:    */ {
/*  21: 18 */   private static final WorldGenTaiga1 field_150639_aC = new WorldGenTaiga1();
/*  22: 19 */   private static final WorldGenTaiga2 field_150640_aD = new WorldGenTaiga2(false);
/*  23: 20 */   private static final WorldGenMegaPineTree field_150641_aE = new WorldGenMegaPineTree(false, false);
/*  24: 21 */   private static final WorldGenMegaPineTree field_150642_aF = new WorldGenMegaPineTree(false, true);
/*  25: 22 */   private static final WorldGenBlockBlob field_150643_aG = new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0);
/*  26:    */   private int field_150644_aH;
/*  27:    */   private static final String __OBFID = "CL_00000186";
/*  28:    */   
/*  29:    */   public BiomeGenTaiga(int p_i45385_1_, int p_i45385_2_)
/*  30:    */   {
/*  31: 28 */     super(p_i45385_1_);
/*  32: 29 */     this.field_150644_aH = p_i45385_2_;
/*  33: 30 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 8, 4, 4));
/*  34: 31 */     this.theBiomeDecorator.treesPerChunk = 10;
/*  35: 33 */     if ((p_i45385_2_ != 1) && (p_i45385_2_ != 2))
/*  36:    */     {
/*  37: 35 */       this.theBiomeDecorator.grassPerChunk = 1;
/*  38: 36 */       this.theBiomeDecorator.mushroomsPerChunk = 1;
/*  39:    */     }
/*  40:    */     else
/*  41:    */     {
/*  42: 40 */       this.theBiomeDecorator.grassPerChunk = 7;
/*  43: 41 */       this.theBiomeDecorator.deadBushPerChunk = 1;
/*  44: 42 */       this.theBiomeDecorator.mushroomsPerChunk = 3;
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
/*  49:    */   {
/*  50: 48 */     return p_150567_1_.nextInt(3) == 0 ? field_150639_aC : ((this.field_150644_aH == 1) || (this.field_150644_aH == 2)) && (p_150567_1_.nextInt(3) == 0) ? field_150642_aF : (this.field_150644_aH != 2) && (p_150567_1_.nextInt(13) != 0) ? field_150641_aE : field_150640_aD;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
/*  54:    */   {
/*  55: 56 */     return par1Random.nextInt(5) > 0 ? new WorldGenTallGrass(Blocks.tallgrass, 2) : new WorldGenTallGrass(Blocks.tallgrass, 1);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/*  59:    */   {
/*  60: 66 */     if ((this.field_150644_aH == 1) || (this.field_150644_aH == 2))
/*  61:    */     {
/*  62: 68 */       int var5 = par2Random.nextInt(3);
/*  63: 70 */       for (int var6 = 0; var6 < var5; var6++)
/*  64:    */       {
/*  65: 72 */         int var7 = par3 + par2Random.nextInt(16) + 8;
/*  66: 73 */         int var8 = par4 + par2Random.nextInt(16) + 8;
/*  67: 74 */         int var9 = par1World.getHeightValue(var7, var8);
/*  68: 75 */         field_150643_aG.generate(par1World, par2Random, var7, var9, var8);
/*  69:    */       }
/*  70:    */     }
/*  71: 79 */     field_150610_ae.func_150548_a(3);
/*  72: 81 */     for (int var5 = 0; var5 < 7; var5++)
/*  73:    */     {
/*  74: 83 */       int var6 = par3 + par2Random.nextInt(16) + 8;
/*  75: 84 */       int var7 = par4 + par2Random.nextInt(16) + 8;
/*  76: 85 */       int var8 = par2Random.nextInt(par1World.getHeightValue(var6, var7) + 32);
/*  77: 86 */       field_150610_ae.generate(par1World, par2Random, var6, var8, var7);
/*  78:    */     }
/*  79: 89 */     super.decorate(par1World, par2Random, par3, par4);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
/*  83:    */   {
/*  84: 94 */     if ((this.field_150644_aH == 1) || (this.field_150644_aH == 2))
/*  85:    */     {
/*  86: 96 */       this.topBlock = Blocks.grass;
/*  87: 97 */       this.field_150604_aj = 0;
/*  88: 98 */       this.fillerBlock = Blocks.dirt;
/*  89:100 */       if (p_150573_7_ > 1.75D)
/*  90:    */       {
/*  91:102 */         this.topBlock = Blocks.dirt;
/*  92:103 */         this.field_150604_aj = 1;
/*  93:    */       }
/*  94:105 */       else if (p_150573_7_ > -0.95D)
/*  95:    */       {
/*  96:107 */         this.topBlock = Blocks.dirt;
/*  97:108 */         this.field_150604_aj = 2;
/*  98:    */       }
/*  99:    */     }
/* 100:112 */     func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected BiomeGenBase func_150566_k()
/* 104:    */   {
/* 105:117 */     return this.biomeID == BiomeGenBase.field_150578_U.biomeID ? new BiomeGenTaiga(this.biomeID + 128, 2).func_150557_a(5858897, true).setBiomeName("Mega Spruce Taiga").func_76733_a(5159473).setTemperatureRainfall(0.25F, 0.8F).func_150570_a(new BiomeGenBase.Height(this.minHeight, this.maxHeight)) : super.func_150566_k();
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenTaiga
 * JD-Core Version:    0.7.0.1
 */