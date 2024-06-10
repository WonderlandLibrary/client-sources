/*   1:    */ package net.minecraft.world.biome;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.world.World;
/*   7:    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*   8:    */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*   9:    */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*  10:    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*  11:    */ 
/*  12:    */ public class BiomeGenHills
/*  13:    */   extends BiomeGenBase
/*  14:    */ {
/*  15:    */   private WorldGenerator theWorldGenerator;
/*  16:    */   private WorldGenTaiga2 field_150634_aD;
/*  17:    */   private int field_150635_aE;
/*  18:    */   private int field_150636_aF;
/*  19:    */   private int field_150637_aG;
/*  20:    */   private int field_150638_aH;
/*  21:    */   private static final String __OBFID = "CL_00000168";
/*  22:    */   
/*  23:    */   protected BiomeGenHills(int p_i45373_1_, boolean p_i45373_2_)
/*  24:    */   {
/*  25: 24 */     super(p_i45373_1_);
/*  26: 25 */     this.theWorldGenerator = new WorldGenMinable(Blocks.monster_egg, 8);
/*  27: 26 */     this.field_150634_aD = new WorldGenTaiga2(false);
/*  28: 27 */     this.field_150635_aE = 0;
/*  29: 28 */     this.field_150636_aF = 1;
/*  30: 29 */     this.field_150637_aG = 2;
/*  31: 30 */     this.field_150638_aH = this.field_150635_aE;
/*  32: 32 */     if (p_i45373_2_)
/*  33:    */     {
/*  34: 34 */       this.theBiomeDecorator.treesPerChunk = 3;
/*  35: 35 */       this.field_150638_aH = this.field_150636_aF;
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
/*  40:    */   {
/*  41: 41 */     return p_150567_1_.nextInt(3) > 0 ? this.field_150634_aD : super.func_150567_a(p_150567_1_);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/*  45:    */   {
/*  46: 46 */     super.decorate(par1World, par2Random, par3, par4);
/*  47: 47 */     int var5 = 3 + par2Random.nextInt(6);
/*  48: 52 */     for (int var6 = 0; var6 < var5; var6++)
/*  49:    */     {
/*  50: 54 */       int var7 = par3 + par2Random.nextInt(16);
/*  51: 55 */       int var8 = par2Random.nextInt(28) + 4;
/*  52: 56 */       int var9 = par4 + par2Random.nextInt(16);
/*  53: 58 */       if (par1World.getBlock(var7, var8, var9) == Blocks.stone) {
/*  54: 60 */         par1World.setBlock(var7, var8, var9, Blocks.emerald_ore, 0, 2);
/*  55:    */       }
/*  56:    */     }
/*  57: 64 */     for (var5 = 0; var5 < 7; var5++)
/*  58:    */     {
/*  59: 66 */       var6 = par3 + par2Random.nextInt(16);
/*  60: 67 */       int var7 = par2Random.nextInt(64);
/*  61: 68 */       int var8 = par4 + par2Random.nextInt(16);
/*  62: 69 */       this.theWorldGenerator.generate(par1World, par2Random, var6, var7, var8);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
/*  67:    */   {
/*  68: 75 */     this.topBlock = Blocks.grass;
/*  69: 76 */     this.field_150604_aj = 0;
/*  70: 77 */     this.fillerBlock = Blocks.dirt;
/*  71: 79 */     if (((p_150573_7_ < -1.0D) || (p_150573_7_ > 2.0D)) && (this.field_150638_aH == this.field_150637_aG))
/*  72:    */     {
/*  73: 81 */       this.topBlock = Blocks.gravel;
/*  74: 82 */       this.fillerBlock = Blocks.gravel;
/*  75:    */     }
/*  76: 84 */     else if ((p_150573_7_ > 1.0D) && (this.field_150638_aH != this.field_150636_aF))
/*  77:    */     {
/*  78: 86 */       this.topBlock = Blocks.stone;
/*  79: 87 */       this.fillerBlock = Blocks.stone;
/*  80:    */     }
/*  81: 90 */     func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
/*  82:    */   }
/*  83:    */   
/*  84:    */   private BiomeGenHills func_150633_b(BiomeGenBase p_150633_1_)
/*  85:    */   {
/*  86: 95 */     this.field_150638_aH = this.field_150637_aG;
/*  87: 96 */     func_150557_a(p_150633_1_.color, true);
/*  88: 97 */     setBiomeName(p_150633_1_.biomeName + " M");
/*  89: 98 */     func_150570_a(new BiomeGenBase.Height(p_150633_1_.minHeight, p_150633_1_.maxHeight));
/*  90: 99 */     setTemperatureRainfall(p_150633_1_.temperature, p_150633_1_.rainfall);
/*  91:100 */     return this;
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected BiomeGenBase func_150566_k()
/*  95:    */   {
/*  96:105 */     return new BiomeGenHills(this.biomeID + 128, false).func_150633_b(this);
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenHills
 * JD-Core Version:    0.7.0.1
 */