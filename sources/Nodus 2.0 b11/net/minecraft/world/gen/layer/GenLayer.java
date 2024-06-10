/*   1:    */ package net.minecraft.world.gen.layer;
/*   2:    */ 
/*   3:    */ import java.util.concurrent.Callable;
/*   4:    */ import net.minecraft.crash.CrashReport;
/*   5:    */ import net.minecraft.crash.CrashReportCategory;
/*   6:    */ import net.minecraft.util.ReportedException;
/*   7:    */ import net.minecraft.world.WorldType;
/*   8:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   9:    */ 
/*  10:    */ public abstract class GenLayer
/*  11:    */ {
/*  12:    */   private long worldGenSeed;
/*  13:    */   protected GenLayer parent;
/*  14:    */   private long chunkSeed;
/*  15:    */   protected long baseSeed;
/*  16:    */   private static final String __OBFID = "CL_00000559";
/*  17:    */   
/*  18:    */   public static GenLayer[] initializeAllBiomeGenerators(long par0, WorldType par2WorldType)
/*  19:    */   {
/*  20: 34 */     boolean var3 = false;
/*  21: 35 */     GenLayerIsland var4 = new GenLayerIsland(1L);
/*  22: 36 */     GenLayerFuzzyZoom var11 = new GenLayerFuzzyZoom(2000L, var4);
/*  23: 37 */     GenLayerAddIsland var12 = new GenLayerAddIsland(1L, var11);
/*  24: 38 */     GenLayerZoom var13 = new GenLayerZoom(2001L, var12);
/*  25: 39 */     var12 = new GenLayerAddIsland(2L, var13);
/*  26: 40 */     var12 = new GenLayerAddIsland(50L, var12);
/*  27: 41 */     var12 = new GenLayerAddIsland(70L, var12);
/*  28: 42 */     GenLayerRemoveTooMuchOcean var16 = new GenLayerRemoveTooMuchOcean(2L, var12);
/*  29: 43 */     GenLayerAddSnow var14 = new GenLayerAddSnow(2L, var16);
/*  30: 44 */     var12 = new GenLayerAddIsland(3L, var14);
/*  31: 45 */     GenLayerEdge var19 = new GenLayerEdge(2L, var12, GenLayerEdge.Mode.COOL_WARM);
/*  32: 46 */     var19 = new GenLayerEdge(2L, var19, GenLayerEdge.Mode.HEAT_ICE);
/*  33: 47 */     var19 = new GenLayerEdge(3L, var19, GenLayerEdge.Mode.SPECIAL);
/*  34: 48 */     var13 = new GenLayerZoom(2002L, var19);
/*  35: 49 */     var13 = new GenLayerZoom(2003L, var13);
/*  36: 50 */     var12 = new GenLayerAddIsland(4L, var13);
/*  37: 51 */     GenLayerAddMushroomIsland var22 = new GenLayerAddMushroomIsland(5L, var12);
/*  38: 52 */     GenLayerDeepOcean var24 = new GenLayerDeepOcean(4L, var22);
/*  39: 53 */     GenLayer var23 = GenLayerZoom.magnify(1000L, var24, 0);
/*  40: 54 */     byte var5 = 4;
/*  41: 56 */     if (par2WorldType == WorldType.LARGE_BIOMES) {
/*  42: 58 */       var5 = 6;
/*  43:    */     }
/*  44: 61 */     if (var3) {
/*  45: 63 */       var5 = 4;
/*  46:    */     }
/*  47: 66 */     GenLayer var6 = GenLayerZoom.magnify(1000L, var23, 0);
/*  48: 67 */     GenLayerRiverInit var15 = new GenLayerRiverInit(100L, var6);
/*  49: 68 */     Object var7 = new GenLayerBiome(200L, var23, par2WorldType);
/*  50: 70 */     if (!var3)
/*  51:    */     {
/*  52: 72 */       GenLayer var18 = GenLayerZoom.magnify(1000L, (GenLayer)var7, 2);
/*  53: 73 */       var7 = new GenLayerBiomeEdge(1000L, var18);
/*  54:    */     }
/*  55: 76 */     GenLayer var8 = GenLayerZoom.magnify(1000L, var15, 2);
/*  56: 77 */     GenLayerHills var21 = new GenLayerHills(1000L, (GenLayer)var7, var8);
/*  57: 78 */     var6 = GenLayerZoom.magnify(1000L, var15, 2);
/*  58: 79 */     var6 = GenLayerZoom.magnify(1000L, var6, var5);
/*  59: 80 */     GenLayerRiver var17 = new GenLayerRiver(1L, var6);
/*  60: 81 */     GenLayerSmooth var20 = new GenLayerSmooth(1000L, var17);
/*  61: 82 */     var7 = new GenLayerRareBiome(1001L, var21);
/*  62: 84 */     for (int var9 = 0; var9 < var5; var9++)
/*  63:    */     {
/*  64: 86 */       var7 = new GenLayerZoom(1000 + var9, (GenLayer)var7);
/*  65: 88 */       if (var9 == 0) {
/*  66: 90 */         var7 = new GenLayerAddIsland(3L, (GenLayer)var7);
/*  67:    */       }
/*  68: 93 */       if (var9 == 1) {
/*  69: 95 */         var7 = new GenLayerShore(1000L, (GenLayer)var7);
/*  70:    */       }
/*  71:    */     }
/*  72: 99 */     GenLayerSmooth var25 = new GenLayerSmooth(1000L, (GenLayer)var7);
/*  73:100 */     GenLayerRiverMix var26 = new GenLayerRiverMix(100L, var25, var20);
/*  74:101 */     GenLayerVoronoiZoom var10 = new GenLayerVoronoiZoom(10L, var26);
/*  75:102 */     var26.initWorldGenSeed(par0);
/*  76:103 */     var10.initWorldGenSeed(par0);
/*  77:104 */     return new GenLayer[] { var26, var10, var26 };
/*  78:    */   }
/*  79:    */   
/*  80:    */   public GenLayer(long par1)
/*  81:    */   {
/*  82:109 */     this.baseSeed = par1;
/*  83:110 */     this.baseSeed *= (this.baseSeed * 6364136223846793005L + 1442695040888963407L);
/*  84:111 */     this.baseSeed += par1;
/*  85:112 */     this.baseSeed *= (this.baseSeed * 6364136223846793005L + 1442695040888963407L);
/*  86:113 */     this.baseSeed += par1;
/*  87:114 */     this.baseSeed *= (this.baseSeed * 6364136223846793005L + 1442695040888963407L);
/*  88:115 */     this.baseSeed += par1;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void initWorldGenSeed(long par1)
/*  92:    */   {
/*  93:124 */     this.worldGenSeed = par1;
/*  94:126 */     if (this.parent != null) {
/*  95:128 */       this.parent.initWorldGenSeed(par1);
/*  96:    */     }
/*  97:131 */     this.worldGenSeed *= (this.worldGenSeed * 6364136223846793005L + 1442695040888963407L);
/*  98:132 */     this.worldGenSeed += this.baseSeed;
/*  99:133 */     this.worldGenSeed *= (this.worldGenSeed * 6364136223846793005L + 1442695040888963407L);
/* 100:134 */     this.worldGenSeed += this.baseSeed;
/* 101:135 */     this.worldGenSeed *= (this.worldGenSeed * 6364136223846793005L + 1442695040888963407L);
/* 102:136 */     this.worldGenSeed += this.baseSeed;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void initChunkSeed(long par1, long par3)
/* 106:    */   {
/* 107:144 */     this.chunkSeed = this.worldGenSeed;
/* 108:145 */     this.chunkSeed *= (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
/* 109:146 */     this.chunkSeed += par1;
/* 110:147 */     this.chunkSeed *= (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
/* 111:148 */     this.chunkSeed += par3;
/* 112:149 */     this.chunkSeed *= (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
/* 113:150 */     this.chunkSeed += par1;
/* 114:151 */     this.chunkSeed *= (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
/* 115:152 */     this.chunkSeed += par3;
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected int nextInt(int par1)
/* 119:    */   {
/* 120:160 */     int var2 = (int)((this.chunkSeed >> 24) % par1);
/* 121:162 */     if (var2 < 0) {
/* 122:164 */       var2 += par1;
/* 123:    */     }
/* 124:167 */     this.chunkSeed *= (this.chunkSeed * 6364136223846793005L + 1442695040888963407L);
/* 125:168 */     this.chunkSeed += this.worldGenSeed;
/* 126:169 */     return var2;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public abstract int[] getInts(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/* 130:    */   
/* 131:    */   protected static boolean func_151616_a(int p_151616_0_, int p_151616_1_)
/* 132:    */   {
/* 133:180 */     if (p_151616_0_ == p_151616_1_) {
/* 134:182 */       return true;
/* 135:    */     }
/* 136:184 */     if ((p_151616_0_ != BiomeGenBase.field_150607_aa.biomeID) && (p_151616_0_ != BiomeGenBase.field_150608_ab.biomeID)) {
/* 137:    */       try
/* 138:    */       {
/* 139:188 */         return (BiomeGenBase.func_150568_d(p_151616_0_) != null) && (BiomeGenBase.func_150568_d(p_151616_1_) != null) ? BiomeGenBase.func_150568_d(p_151616_0_).func_150569_a(BiomeGenBase.func_150568_d(p_151616_1_)) : false;
/* 140:    */       }
/* 141:    */       catch (Throwable var5)
/* 142:    */       {
/* 143:192 */         CrashReport var3 = CrashReport.makeCrashReport(var5, "Comparing biomes");
/* 144:193 */         CrashReportCategory var4 = var3.makeCategory("Biomes being compared");
/* 145:194 */         var4.addCrashSection("Biome A ID", Integer.valueOf(p_151616_0_));
/* 146:195 */         var4.addCrashSection("Biome B ID", Integer.valueOf(p_151616_1_));
/* 147:196 */         var4.addCrashSectionCallable("Biome A", new Callable()
/* 148:    */         {
/* 149:    */           private static final String __OBFID = "CL_00000560";
/* 150:    */           
/* 151:    */           public String call()
/* 152:    */           {
/* 153:201 */             return String.valueOf(BiomeGenBase.func_150568_d(this.val$p_151616_0_));
/* 154:    */           }
/* 155:203 */         });
/* 156:204 */         var4.addCrashSectionCallable("Biome B", new Callable()
/* 157:    */         {
/* 158:    */           private static final String __OBFID = "CL_00000561";
/* 159:    */           
/* 160:    */           public String call()
/* 161:    */           {
/* 162:209 */             return String.valueOf(BiomeGenBase.func_150568_d(this.val$p_151616_1_));
/* 163:    */           }
/* 164:211 */         });
/* 165:212 */         throw new ReportedException(var3);
/* 166:    */       }
/* 167:    */     }
/* 168:217 */     return (p_151616_1_ == BiomeGenBase.field_150607_aa.biomeID) || (p_151616_1_ == BiomeGenBase.field_150608_ab.biomeID);
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected static boolean func_151618_b(int p_151618_0_)
/* 172:    */   {
/* 173:223 */     return (p_151618_0_ == BiomeGenBase.ocean.biomeID) || (p_151618_0_ == BiomeGenBase.field_150575_M.biomeID) || (p_151618_0_ == BiomeGenBase.frozenOcean.biomeID);
/* 174:    */   }
/* 175:    */   
/* 176:    */   protected int func_151619_a(int... p_151619_1_)
/* 177:    */   {
/* 178:228 */     return p_151619_1_[nextInt(p_151619_1_.length)];
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected int func_151617_b(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_)
/* 182:    */   {
/* 183:233 */     return (p_151617_3_ == p_151617_4_) && (p_151617_1_ != p_151617_2_) ? p_151617_3_ : (p_151617_2_ == p_151617_4_) && (p_151617_1_ != p_151617_3_) ? p_151617_2_ : (p_151617_2_ == p_151617_3_) && (p_151617_1_ != p_151617_4_) ? p_151617_2_ : (p_151617_1_ == p_151617_4_) && (p_151617_2_ != p_151617_3_) ? p_151617_1_ : (p_151617_1_ == p_151617_3_) && (p_151617_2_ != p_151617_4_) ? p_151617_1_ : (p_151617_1_ == p_151617_2_) && (p_151617_3_ != p_151617_4_) ? p_151617_1_ : (p_151617_1_ == p_151617_3_) && (p_151617_1_ == p_151617_4_) ? p_151617_1_ : (p_151617_1_ == p_151617_2_) && (p_151617_1_ == p_151617_4_) ? p_151617_1_ : (p_151617_1_ == p_151617_2_) && (p_151617_1_ == p_151617_3_) ? p_151617_1_ : (p_151617_2_ == p_151617_3_) && (p_151617_3_ == p_151617_4_) ? p_151617_2_ : func_151619_a(new int[] { p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_ });
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.GenLayer
 * JD-Core Version:    0.7.0.1
 */