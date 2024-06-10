/*   1:    */ package net.minecraft.world.biome;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.BlockFlower;
/*   6:    */ import net.minecraft.entity.passive.EntityWolf;
/*   7:    */ import net.minecraft.util.MathHelper;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/*  10:    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*  11:    */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*  12:    */ import net.minecraft.world.gen.feature.WorldGenCanopyTree;
/*  13:    */ import net.minecraft.world.gen.feature.WorldGenDoublePlant;
/*  14:    */ import net.minecraft.world.gen.feature.WorldGenForest;
/*  15:    */ 
/*  16:    */ public class BiomeGenForest
/*  17:    */   extends BiomeGenBase
/*  18:    */ {
/*  19:    */   private int field_150632_aF;
/*  20: 16 */   protected static final WorldGenForest field_150629_aC = new WorldGenForest(false, true);
/*  21: 17 */   protected static final WorldGenForest field_150630_aD = new WorldGenForest(false, false);
/*  22: 18 */   protected static final WorldGenCanopyTree field_150631_aE = new WorldGenCanopyTree(false);
/*  23:    */   private static final String __OBFID = "CL_00000170";
/*  24:    */   
/*  25:    */   public BiomeGenForest(int p_i45377_1_, int p_i45377_2_)
/*  26:    */   {
/*  27: 23 */     super(p_i45377_1_);
/*  28: 24 */     this.field_150632_aF = p_i45377_2_;
/*  29: 25 */     this.theBiomeDecorator.treesPerChunk = 10;
/*  30: 26 */     this.theBiomeDecorator.grassPerChunk = 2;
/*  31: 28 */     if (this.field_150632_aF == 1)
/*  32:    */     {
/*  33: 30 */       this.theBiomeDecorator.treesPerChunk = 6;
/*  34: 31 */       this.theBiomeDecorator.flowersPerChunk = 100;
/*  35: 32 */       this.theBiomeDecorator.grassPerChunk = 1;
/*  36:    */     }
/*  37: 35 */     func_76733_a(5159473);
/*  38: 36 */     setTemperatureRainfall(0.7F, 0.8F);
/*  39: 38 */     if (this.field_150632_aF == 2)
/*  40:    */     {
/*  41: 40 */       this.field_150609_ah = 353825;
/*  42: 41 */       this.color = 3175492;
/*  43: 42 */       setTemperatureRainfall(0.6F, 0.6F);
/*  44:    */     }
/*  45: 45 */     if (this.field_150632_aF == 0) {
/*  46: 47 */       this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 5, 4, 4));
/*  47:    */     }
/*  48: 50 */     if (this.field_150632_aF == 3) {
/*  49: 52 */       this.theBiomeDecorator.treesPerChunk = -999;
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected BiomeGenBase func_150557_a(int p_150557_1_, boolean p_150557_2_)
/*  54:    */   {
/*  55: 58 */     if (this.field_150632_aF == 2)
/*  56:    */     {
/*  57: 60 */       this.field_150609_ah = 353825;
/*  58: 61 */       this.color = p_150557_1_;
/*  59: 63 */       if (p_150557_2_) {
/*  60: 65 */         this.field_150609_ah = ((this.field_150609_ah & 0xFEFEFE) >> 1);
/*  61:    */       }
/*  62: 68 */       return this;
/*  63:    */     }
/*  64: 72 */     return super.func_150557_a(p_150557_1_, p_150557_2_);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
/*  68:    */   {
/*  69: 78 */     return (this.field_150632_aF != 2) && (p_150567_1_.nextInt(5) != 0) ? this.worldGeneratorTrees : (this.field_150632_aF == 3) && (p_150567_1_.nextInt(3) > 0) ? field_150631_aE : field_150630_aD;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_)
/*  73:    */   {
/*  74: 83 */     if (this.field_150632_aF == 1)
/*  75:    */     {
/*  76: 85 */       double var5 = MathHelper.clamp_double((1.0D + field_150606_ad.func_151601_a(p_150572_2_ / 48.0D, p_150572_4_ / 48.0D)) / 2.0D, 0.0D, 0.9999D);
/*  77: 86 */       int var7 = (int)(var5 * BlockFlower.field_149859_a.length);
/*  78: 88 */       if (var7 == 1) {
/*  79: 90 */         var7 = 0;
/*  80:    */       }
/*  81: 93 */       return BlockFlower.field_149859_a[var7];
/*  82:    */     }
/*  83: 97 */     return super.func_150572_a(p_150572_1_, p_150572_2_, p_150572_3_, p_150572_4_);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/*  87:    */   {
/*  88:109 */     if (this.field_150632_aF == 3) {
/*  89:111 */       for (int var5 = 0; var5 < 4; var5++) {
/*  90:113 */         for (int var6 = 0; var6 < 4; var6++)
/*  91:    */         {
/*  92:115 */           int var7 = par3 + var5 * 4 + 1 + 8 + par2Random.nextInt(3);
/*  93:116 */           int var8 = par4 + var6 * 4 + 1 + 8 + par2Random.nextInt(3);
/*  94:117 */           int var9 = par1World.getHeightValue(var7, var8);
/*  95:119 */           if (par2Random.nextInt(20) == 0)
/*  96:    */           {
/*  97:121 */             WorldGenBigMushroom var10 = new WorldGenBigMushroom();
/*  98:122 */             var10.generate(par1World, par2Random, var7, var9, var8);
/*  99:    */           }
/* 100:    */           else
/* 101:    */           {
/* 102:126 */             WorldGenAbstractTree var12 = func_150567_a(par2Random);
/* 103:127 */             var12.setScale(1.0D, 1.0D, 1.0D);
/* 104:129 */             if (var12.generate(par1World, par2Random, var7, var9, var8)) {
/* 105:131 */               var12.func_150524_b(par1World, par2Random, var7, var9, var8);
/* 106:    */             }
/* 107:    */           }
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111:138 */     int var5 = par2Random.nextInt(5) - 3;
/* 112:140 */     if (this.field_150632_aF == 1) {
/* 113:142 */       var5 += 2;
/* 114:    */     }
/* 115:145 */     int var6 = 0;
/* 116:147 */     while (var6 < var5)
/* 117:    */     {
/* 118:149 */       int var7 = par2Random.nextInt(3);
/* 119:151 */       if (var7 == 0) {
/* 120:153 */         field_150610_ae.func_150548_a(1);
/* 121:155 */       } else if (var7 == 1) {
/* 122:157 */         field_150610_ae.func_150548_a(4);
/* 123:159 */       } else if (var7 == 2) {
/* 124:161 */         field_150610_ae.func_150548_a(5);
/* 125:    */       }
/* 126:164 */       int var8 = 0;
/* 127:168 */       while (var8 < 5)
/* 128:    */       {
/* 129:170 */         int var9 = par3 + par2Random.nextInt(16) + 8;
/* 130:171 */         int var13 = par4 + par2Random.nextInt(16) + 8;
/* 131:172 */         int var11 = par2Random.nextInt(par1World.getHeightValue(var9, var13) + 32);
/* 132:174 */         if (field_150610_ae.generate(par1World, par2Random, var9, var11, var13)) {
/* 133:    */           break;
/* 134:    */         }
/* 135:176 */         var8++;
/* 136:    */       }
/* 137:181 */       var6++;
/* 138:    */     }
/* 139:186 */     super.decorate(par1World, par2Random, par3, par4);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_)
/* 143:    */   {
/* 144:194 */     int var4 = super.getBiomeGrassColor(p_150558_1_, p_150558_2_, p_150558_3_);
/* 145:195 */     return this.field_150632_aF == 3 ? (var4 & 0xFEFEFE) + 2634762 >> 1 : var4;
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected BiomeGenBase func_150566_k()
/* 149:    */   {
/* 150:200 */     if (this.biomeID == BiomeGenBase.forest.biomeID)
/* 151:    */     {
/* 152:202 */       BiomeGenForest var1 = new BiomeGenForest(this.biomeID + 128, 1);
/* 153:203 */       var1.func_150570_a(new BiomeGenBase.Height(this.minHeight, this.maxHeight + 0.2F));
/* 154:204 */       var1.setBiomeName("Flower Forest");
/* 155:205 */       var1.func_150557_a(6976549, true);
/* 156:206 */       var1.func_76733_a(8233509);
/* 157:207 */       return var1;
/* 158:    */     }
/* 159:211 */     (this.biomeID != BiomeGenBase.field_150583_P.biomeID) && (this.biomeID != BiomeGenBase.field_150582_Q.biomeID) ? new BiomeGenMutated(this.biomeID + 128, this)
/* 160:    */     
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:217 */       new BiomeGenMutated
/* 166:    */       {
/* 167:    */         public void decorate(World var1, Random var2, int var3, int var4)
/* 168:    */         {
/* 169:215 */           this.field_150611_aD.decorate(var1, var2, var3, var4);
/* 170:    */         }
/* 171:217 */       } : new BiomeGenMutated(this.biomeID + 128, this)
/* 172:    */       {
/* 173:    */         private static final String __OBFID = "CL_00000172";
/* 174:    */         
/* 175:    */         public WorldGenAbstractTree func_150567_a(Random var1)
/* 176:    */         {
/* 177:222 */           return var1.nextBoolean() ? BiomeGenForest.field_150629_aC : BiomeGenForest.field_150630_aD;
/* 178:    */         }
/* 179:    */       };
/* 180:    */     }
/* 181:    */   }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenForest
 * JD-Core Version:    0.7.0.1
 */