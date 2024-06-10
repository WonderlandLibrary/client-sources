/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.entity.EnumCreatureType;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.util.IProgressUpdate;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.ChunkPosition;
/*  11:    */ import net.minecraft.world.SpawnerAnimals;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ import net.minecraft.world.WorldType;
/*  14:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  15:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  16:    */ import net.minecraft.world.chunk.Chunk;
/*  17:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  18:    */ import net.minecraft.world.gen.feature.WorldGenDungeons;
/*  19:    */ import net.minecraft.world.gen.feature.WorldGenLakes;
/*  20:    */ import net.minecraft.world.gen.structure.MapGenMineshaft;
/*  21:    */ import net.minecraft.world.gen.structure.MapGenScatteredFeature;
/*  22:    */ import net.minecraft.world.gen.structure.MapGenStronghold;
/*  23:    */ import net.minecraft.world.gen.structure.MapGenVillage;
/*  24:    */ import net.minecraft.world.storage.WorldInfo;
/*  25:    */ 
/*  26:    */ public class ChunkProviderGenerate
/*  27:    */   implements IChunkProvider
/*  28:    */ {
/*  29:    */   private Random rand;
/*  30:    */   private NoiseGeneratorOctaves field_147431_j;
/*  31:    */   private NoiseGeneratorOctaves field_147432_k;
/*  32:    */   private NoiseGeneratorOctaves field_147429_l;
/*  33:    */   private NoiseGeneratorPerlin field_147430_m;
/*  34:    */   public NoiseGeneratorOctaves noiseGen5;
/*  35:    */   public NoiseGeneratorOctaves noiseGen6;
/*  36:    */   public NoiseGeneratorOctaves mobSpawnerNoise;
/*  37:    */   private World worldObj;
/*  38:    */   private final boolean mapFeaturesEnabled;
/*  39:    */   private WorldType field_147435_p;
/*  40:    */   private final double[] field_147434_q;
/*  41:    */   private final float[] parabolicField;
/*  42: 49 */   private double[] stoneNoise = new double[256];
/*  43: 50 */   private MapGenBase caveGenerator = new MapGenCaves();
/*  44: 53 */   private MapGenStronghold strongholdGenerator = new MapGenStronghold();
/*  45: 56 */   private MapGenVillage villageGenerator = new MapGenVillage();
/*  46: 59 */   private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
/*  47: 60 */   private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
/*  48: 63 */   private MapGenBase ravineGenerator = new MapGenRavine();
/*  49:    */   private BiomeGenBase[] biomesForGeneration;
/*  50:    */   double[] field_147427_d;
/*  51:    */   double[] field_147428_e;
/*  52:    */   double[] field_147425_f;
/*  53:    */   double[] field_147426_g;
/*  54: 71 */   int[][] field_73219_j = new int[32][32];
/*  55:    */   private static final String __OBFID = "CL_00000396";
/*  56:    */   
/*  57:    */   public ChunkProviderGenerate(World par1World, long par2, boolean par4)
/*  58:    */   {
/*  59: 76 */     this.worldObj = par1World;
/*  60: 77 */     this.mapFeaturesEnabled = par4;
/*  61: 78 */     this.field_147435_p = par1World.getWorldInfo().getTerrainType();
/*  62: 79 */     this.rand = new Random(par2);
/*  63: 80 */     this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
/*  64: 81 */     this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
/*  65: 82 */     this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
/*  66: 83 */     this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
/*  67: 84 */     this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
/*  68: 85 */     this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
/*  69: 86 */     this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
/*  70: 87 */     this.field_147434_q = new double[825];
/*  71: 88 */     this.parabolicField = new float[25];
/*  72: 90 */     for (int var5 = -2; var5 <= 2; var5++) {
/*  73: 92 */       for (int var6 = -2; var6 <= 2; var6++)
/*  74:    */       {
/*  75: 94 */         float var7 = 10.0F / MathHelper.sqrt_float(var5 * var5 + var6 * var6 + 0.2F);
/*  76: 95 */         this.parabolicField[(var5 + 2 + (var6 + 2) * 5)] = var7;
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void func_147424_a(int p_147424_1_, int p_147424_2_, Block[] p_147424_3_)
/*  82:    */   {
/*  83:102 */     byte var4 = 63;
/*  84:103 */     this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, p_147424_1_ * 4 - 2, p_147424_2_ * 4 - 2, 10, 10);
/*  85:104 */     func_147423_a(p_147424_1_ * 4, 0, p_147424_2_ * 4);
/*  86:106 */     for (int var5 = 0; var5 < 4; var5++)
/*  87:    */     {
/*  88:108 */       int var6 = var5 * 5;
/*  89:109 */       int var7 = (var5 + 1) * 5;
/*  90:111 */       for (int var8 = 0; var8 < 4; var8++)
/*  91:    */       {
/*  92:113 */         int var9 = (var6 + var8) * 33;
/*  93:114 */         int var10 = (var6 + var8 + 1) * 33;
/*  94:115 */         int var11 = (var7 + var8) * 33;
/*  95:116 */         int var12 = (var7 + var8 + 1) * 33;
/*  96:118 */         for (int var13 = 0; var13 < 32; var13++)
/*  97:    */         {
/*  98:120 */           double var14 = 0.125D;
/*  99:121 */           double var16 = this.field_147434_q[(var9 + var13)];
/* 100:122 */           double var18 = this.field_147434_q[(var10 + var13)];
/* 101:123 */           double var20 = this.field_147434_q[(var11 + var13)];
/* 102:124 */           double var22 = this.field_147434_q[(var12 + var13)];
/* 103:125 */           double var24 = (this.field_147434_q[(var9 + var13 + 1)] - var16) * var14;
/* 104:126 */           double var26 = (this.field_147434_q[(var10 + var13 + 1)] - var18) * var14;
/* 105:127 */           double var28 = (this.field_147434_q[(var11 + var13 + 1)] - var20) * var14;
/* 106:128 */           double var30 = (this.field_147434_q[(var12 + var13 + 1)] - var22) * var14;
/* 107:130 */           for (int var32 = 0; var32 < 8; var32++)
/* 108:    */           {
/* 109:132 */             double var33 = 0.25D;
/* 110:133 */             double var35 = var16;
/* 111:134 */             double var37 = var18;
/* 112:135 */             double var39 = (var20 - var16) * var33;
/* 113:136 */             double var41 = (var22 - var18) * var33;
/* 114:138 */             for (int var43 = 0; var43 < 4; var43++)
/* 115:    */             {
/* 116:140 */               int var44 = var43 + var5 * 4 << 12 | 0 + var8 * 4 << 8 | var13 * 8 + var32;
/* 117:141 */               short var45 = 256;
/* 118:142 */               var44 -= var45;
/* 119:143 */               double var46 = 0.25D;
/* 120:144 */               double var50 = (var37 - var35) * var46;
/* 121:145 */               double var48 = var35 - var50;
/* 122:147 */               for (int var52 = 0; var52 < 4; var52++) {
/* 123:149 */                 if (var48 += var50 > 0.0D)
/* 124:    */                 {
/* 125:151 */                   int tmp391_390 = (var44 + var45);var44 = tmp391_390;p_147424_3_[tmp391_390] = Blocks.stone;
/* 126:    */                 }
/* 127:153 */                 else if (var13 * 8 + var32 < var4)
/* 128:    */                 {
/* 129:155 */                   int tmp420_419 = (var44 + var45);var44 = tmp420_419;p_147424_3_[tmp420_419] = Blocks.water;
/* 130:    */                 }
/* 131:    */                 else
/* 132:    */                 {
/* 133:159 */                   int tmp436_435 = (var44 + var45);var44 = tmp436_435;p_147424_3_[tmp436_435] = null;
/* 134:    */                 }
/* 135:    */               }
/* 136:163 */               var35 += var39;
/* 137:164 */               tmp420_419 += var41;
/* 138:    */             }
/* 139:167 */             var16 += var24;
/* 140:168 */             var18 += var26;
/* 141:169 */             var20 += var28;
/* 142:170 */             var22 += var30;
/* 143:    */           }
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void func_147422_a(int p_147422_1_, int p_147422_2_, Block[] p_147422_3_, byte[] p_147422_4_, BiomeGenBase[] p_147422_5_)
/* 150:    */   {
/* 151:179 */     double var6 = 0.03125D;
/* 152:180 */     this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, p_147422_1_ * 16, p_147422_2_ * 16, 16, 16, var6 * 2.0D, var6 * 2.0D, 1.0D);
/* 153:182 */     for (int var8 = 0; var8 < 16; var8++) {
/* 154:184 */       for (int var9 = 0; var9 < 16; var9++)
/* 155:    */       {
/* 156:186 */         BiomeGenBase var10 = p_147422_5_[(var9 + var8 * 16)];
/* 157:187 */         var10.func_150573_a(this.worldObj, this.rand, p_147422_3_, p_147422_4_, p_147422_1_ * 16 + var8, p_147422_2_ * 16 + var9, this.stoneNoise[(var9 + var8 * 16)]);
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Chunk loadChunk(int par1, int par2)
/* 163:    */   {
/* 164:197 */     return provideChunk(par1, par2);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Chunk provideChunk(int par1, int par2)
/* 168:    */   {
/* 169:206 */     this.rand.setSeed(par1 * 341873128712L + par2 * 132897987541L);
/* 170:207 */     Block[] var3 = new Block[65536];
/* 171:208 */     byte[] var4 = new byte[65536];
/* 172:209 */     func_147424_a(par1, par2, var3);
/* 173:210 */     this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
/* 174:211 */     func_147422_a(par1, par2, var3, var4, this.biomesForGeneration);
/* 175:212 */     this.caveGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
/* 176:213 */     this.ravineGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
/* 177:215 */     if (this.mapFeaturesEnabled)
/* 178:    */     {
/* 179:217 */       this.mineshaftGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
/* 180:218 */       this.villageGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
/* 181:219 */       this.strongholdGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
/* 182:220 */       this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
/* 183:    */     }
/* 184:223 */     Chunk var5 = new Chunk(this.worldObj, var3, var4, par1, par2);
/* 185:224 */     byte[] var6 = var5.getBiomeArray();
/* 186:226 */     for (int var7 = 0; var7 < var6.length; var7++) {
/* 187:228 */       var6[var7] = ((byte)this.biomesForGeneration[var7].biomeID);
/* 188:    */     }
/* 189:231 */     var5.generateSkylightMap();
/* 190:232 */     return var5;
/* 191:    */   }
/* 192:    */   
/* 193:    */   private void func_147423_a(int p_147423_1_, int p_147423_2_, int p_147423_3_)
/* 194:    */   {
/* 195:237 */     double var4 = 684.41200000000003D;
/* 196:238 */     double var6 = 684.41200000000003D;
/* 197:239 */     double var8 = 512.0D;
/* 198:240 */     double var10 = 512.0D;
/* 199:241 */     this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, p_147423_1_, p_147423_3_, 5, 5, 200.0D, 200.0D, 0.5D);
/* 200:242 */     this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
/* 201:243 */     this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.41200000000003D, 684.41200000000003D, 684.41200000000003D);
/* 202:244 */     this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.41200000000003D, 684.41200000000003D, 684.41200000000003D);
/* 203:245 */     boolean var45 = false;
/* 204:246 */     boolean var44 = false;
/* 205:247 */     int var12 = 0;
/* 206:248 */     int var13 = 0;
/* 207:249 */     double var14 = 8.5D;
/* 208:251 */     for (int var16 = 0; var16 < 5; var16++) {
/* 209:253 */       for (int var17 = 0; var17 < 5; var17++)
/* 210:    */       {
/* 211:255 */         float var18 = 0.0F;
/* 212:256 */         float var19 = 0.0F;
/* 213:257 */         float var20 = 0.0F;
/* 214:258 */         byte var21 = 2;
/* 215:259 */         BiomeGenBase var22 = this.biomesForGeneration[(var16 + 2 + (var17 + 2) * 10)];
/* 216:261 */         for (int var23 = -var21; var23 <= var21; var23++) {
/* 217:263 */           for (int var24 = -var21; var24 <= var21; var24++)
/* 218:    */           {
/* 219:265 */             BiomeGenBase var25 = this.biomesForGeneration[(var16 + var23 + 2 + (var17 + var24 + 2) * 10)];
/* 220:266 */             float var26 = var25.minHeight;
/* 221:267 */             float var27 = var25.maxHeight;
/* 222:269 */             if ((this.field_147435_p == WorldType.field_151360_e) && (var26 > 0.0F))
/* 223:    */             {
/* 224:271 */               var26 = 1.0F + var26 * 2.0F;
/* 225:272 */               var27 = 1.0F + var27 * 4.0F;
/* 226:    */             }
/* 227:275 */             float var28 = this.parabolicField[(var23 + 2 + (var24 + 2) * 5)] / (var26 + 2.0F);
/* 228:277 */             if (var25.minHeight > var22.minHeight) {
/* 229:279 */               var28 /= 2.0F;
/* 230:    */             }
/* 231:282 */             var18 += var27 * var28;
/* 232:283 */             var19 += var26 * var28;
/* 233:284 */             var20 += var28;
/* 234:    */           }
/* 235:    */         }
/* 236:288 */         var18 /= var20;
/* 237:289 */         var19 /= var20;
/* 238:290 */         var18 = var18 * 0.9F + 0.1F;
/* 239:291 */         var19 = (var19 * 4.0F - 1.0F) / 8.0F;
/* 240:292 */         double var47 = this.field_147426_g[var13] / 8000.0D;
/* 241:294 */         if (var47 < 0.0D) {
/* 242:296 */           var47 = -var47 * 0.3D;
/* 243:    */         }
/* 244:299 */         var47 = var47 * 3.0D - 2.0D;
/* 245:301 */         if (var47 < 0.0D)
/* 246:    */         {
/* 247:303 */           var47 /= 2.0D;
/* 248:305 */           if (var47 < -1.0D) {
/* 249:307 */             var47 = -1.0D;
/* 250:    */           }
/* 251:310 */           var47 /= 1.4D;
/* 252:311 */           var47 /= 2.0D;
/* 253:    */         }
/* 254:    */         else
/* 255:    */         {
/* 256:315 */           if (var47 > 1.0D) {
/* 257:317 */             var47 = 1.0D;
/* 258:    */           }
/* 259:320 */           var47 /= 8.0D;
/* 260:    */         }
/* 261:323 */         var13++;
/* 262:324 */         double var46 = var19;
/* 263:325 */         double var48 = var18;
/* 264:326 */         var46 += var47 * 0.2D;
/* 265:327 */         var46 = var46 * 8.5D / 8.0D;
/* 266:328 */         double var29 = 8.5D + var46 * 4.0D;
/* 267:330 */         for (int var31 = 0; var31 < 33; var31++)
/* 268:    */         {
/* 269:332 */           double var32 = (var31 - var29) * 12.0D * 128.0D / 256.0D / var48;
/* 270:334 */           if (var32 < 0.0D) {
/* 271:336 */             var32 *= 4.0D;
/* 272:    */           }
/* 273:339 */           double var34 = this.field_147428_e[var12] / 512.0D;
/* 274:340 */           double var36 = this.field_147425_f[var12] / 512.0D;
/* 275:341 */           double var38 = (this.field_147427_d[var12] / 10.0D + 1.0D) / 2.0D;
/* 276:342 */           double var40 = MathHelper.denormalizeClamp(var34, var36, var38) - var32;
/* 277:344 */           if (var31 > 29)
/* 278:    */           {
/* 279:346 */             double var42 = (var31 - 29) / 3.0F;
/* 280:347 */             var40 = var40 * (1.0D - var42) + -10.0D * var42;
/* 281:    */           }
/* 282:350 */           this.field_147434_q[var12] = var40;
/* 283:351 */           var12++;
/* 284:    */         }
/* 285:    */       }
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public boolean chunkExists(int par1, int par2)
/* 290:    */   {
/* 291:362 */     return true;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
/* 295:    */   {
/* 296:370 */     net.minecraft.block.BlockFalling.field_149832_M = true;
/* 297:371 */     int var4 = par2 * 16;
/* 298:372 */     int var5 = par3 * 16;
/* 299:373 */     BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
/* 300:374 */     this.rand.setSeed(this.worldObj.getSeed());
/* 301:375 */     long var7 = this.rand.nextLong() / 2L * 2L + 1L;
/* 302:376 */     long var9 = this.rand.nextLong() / 2L * 2L + 1L;
/* 303:377 */     this.rand.setSeed(par2 * var7 + par3 * var9 ^ this.worldObj.getSeed());
/* 304:378 */     boolean var11 = false;
/* 305:380 */     if (this.mapFeaturesEnabled)
/* 306:    */     {
/* 307:382 */       this.mineshaftGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
/* 308:383 */       var11 = this.villageGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
/* 309:384 */       this.strongholdGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
/* 310:385 */       this.scatteredFeatureGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
/* 311:    */     }
/* 312:392 */     if ((var6 != BiomeGenBase.desert) && (var6 != BiomeGenBase.desertHills) && (!var11) && (this.rand.nextInt(4) == 0))
/* 313:    */     {
/* 314:394 */       int var12 = var4 + this.rand.nextInt(16) + 8;
/* 315:395 */       int var13 = this.rand.nextInt(256);
/* 316:396 */       int var14 = var5 + this.rand.nextInt(16) + 8;
/* 317:397 */       new WorldGenLakes(Blocks.water).generate(this.worldObj, this.rand, var12, var13, var14);
/* 318:    */     }
/* 319:400 */     if ((!var11) && (this.rand.nextInt(8) == 0))
/* 320:    */     {
/* 321:402 */       int var12 = var4 + this.rand.nextInt(16) + 8;
/* 322:403 */       int var13 = this.rand.nextInt(this.rand.nextInt(248) + 8);
/* 323:404 */       int var14 = var5 + this.rand.nextInt(16) + 8;
/* 324:406 */       if ((var13 < 63) || (this.rand.nextInt(10) == 0)) {
/* 325:408 */         new WorldGenLakes(Blocks.lava).generate(this.worldObj, this.rand, var12, var13, var14);
/* 326:    */       }
/* 327:    */     }
/* 328:412 */     for (int var12 = 0; var12 < 8; var12++)
/* 329:    */     {
/* 330:414 */       int var13 = var4 + this.rand.nextInt(16) + 8;
/* 331:415 */       int var14 = this.rand.nextInt(256);
/* 332:416 */       int var15 = var5 + this.rand.nextInt(16) + 8;
/* 333:417 */       new WorldGenDungeons().generate(this.worldObj, this.rand, var13, var14, var15);
/* 334:    */     }
/* 335:420 */     var6.decorate(this.worldObj, this.rand, var4, var5);
/* 336:421 */     SpawnerAnimals.performWorldGenSpawning(this.worldObj, var6, var4 + 8, var5 + 8, 16, 16, this.rand);
/* 337:422 */     var4 += 8;
/* 338:423 */     var5 += 8;
/* 339:425 */     for (var12 = 0; var12 < 16; var12++) {
/* 340:427 */       for (int var13 = 0; var13 < 16; var13++)
/* 341:    */       {
/* 342:429 */         int var14 = this.worldObj.getPrecipitationHeight(var4 + var12, var5 + var13);
/* 343:431 */         if (this.worldObj.isBlockFreezable(var12 + var4, var14 - 1, var13 + var5)) {
/* 344:433 */           this.worldObj.setBlock(var12 + var4, var14 - 1, var13 + var5, Blocks.ice, 0, 2);
/* 345:    */         }
/* 346:436 */         if (this.worldObj.func_147478_e(var12 + var4, var14, var13 + var5, true)) {
/* 347:438 */           this.worldObj.setBlock(var12 + var4, var14, var13 + var5, Blocks.snow_layer, 0, 2);
/* 348:    */         }
/* 349:    */       }
/* 350:    */     }
/* 351:443 */     net.minecraft.block.BlockFalling.field_149832_M = false;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
/* 355:    */   {
/* 356:452 */     return true;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void saveExtraData() {}
/* 360:    */   
/* 361:    */   public boolean unloadQueuedChunks()
/* 362:    */   {
/* 363:466 */     return false;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public boolean canSave()
/* 367:    */   {
/* 368:474 */     return true;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public String makeString()
/* 372:    */   {
/* 373:482 */     return "RandomLevelSource";
/* 374:    */   }
/* 375:    */   
/* 376:    */   public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
/* 377:    */   {
/* 378:490 */     BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
/* 379:491 */     return (par1EnumCreatureType == EnumCreatureType.monster) && (this.scatteredFeatureGenerator.func_143030_a(par2, par3, par4)) ? this.scatteredFeatureGenerator.getScatteredFeatureSpawnList() : var5.getSpawnableList(par1EnumCreatureType);
/* 380:    */   }
/* 381:    */   
/* 382:    */   public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
/* 383:    */   {
/* 384:496 */     return ("Stronghold".equals(p_147416_2_)) && (this.strongholdGenerator != null) ? this.strongholdGenerator.func_151545_a(p_147416_1_, p_147416_3_, p_147416_4_, p_147416_5_) : null;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public int getLoadedChunkCount()
/* 388:    */   {
/* 389:501 */     return 0;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void recreateStructures(int par1, int par2)
/* 393:    */   {
/* 394:506 */     if (this.mapFeaturesEnabled)
/* 395:    */     {
/* 396:508 */       this.mineshaftGenerator.func_151539_a(this, this.worldObj, par1, par2, null);
/* 397:509 */       this.villageGenerator.func_151539_a(this, this.worldObj, par1, par2, null);
/* 398:510 */       this.strongholdGenerator.func_151539_a(this, this.worldObj, par1, par2, null);
/* 399:511 */       this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, par1, par2, null);
/* 400:    */     }
/* 401:    */   }
/* 402:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.ChunkProviderGenerate
 * JD-Core Version:    0.7.0.1
 */