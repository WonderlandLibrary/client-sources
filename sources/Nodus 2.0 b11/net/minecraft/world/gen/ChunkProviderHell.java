/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.entity.EnumCreatureType;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.util.IProgressUpdate;
/*  10:    */ import net.minecraft.world.ChunkPosition;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  13:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  14:    */ import net.minecraft.world.chunk.Chunk;
/*  15:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  16:    */ import net.minecraft.world.gen.feature.WorldGenFire;
/*  17:    */ import net.minecraft.world.gen.feature.WorldGenFlowers;
/*  18:    */ import net.minecraft.world.gen.feature.WorldGenGlowStone1;
/*  19:    */ import net.minecraft.world.gen.feature.WorldGenGlowStone2;
/*  20:    */ import net.minecraft.world.gen.feature.WorldGenHellLava;
/*  21:    */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*  22:    */ import net.minecraft.world.gen.structure.MapGenNetherBridge;
/*  23:    */ 
/*  24:    */ public class ChunkProviderHell
/*  25:    */   implements IChunkProvider
/*  26:    */ {
/*  27:    */   private Random hellRNG;
/*  28:    */   private NoiseGeneratorOctaves netherNoiseGen1;
/*  29:    */   private NoiseGeneratorOctaves netherNoiseGen2;
/*  30:    */   private NoiseGeneratorOctaves netherNoiseGen3;
/*  31:    */   private NoiseGeneratorOctaves slowsandGravelNoiseGen;
/*  32:    */   private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
/*  33:    */   public NoiseGeneratorOctaves netherNoiseGen6;
/*  34:    */   public NoiseGeneratorOctaves netherNoiseGen7;
/*  35:    */   private World worldObj;
/*  36:    */   private double[] noiseField;
/*  37: 46 */   public MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
/*  38: 51 */   private double[] slowsandNoise = new double[256];
/*  39: 52 */   private double[] gravelNoise = new double[256];
/*  40: 57 */   private double[] netherrackExclusivityNoise = new double[256];
/*  41: 58 */   private MapGenBase netherCaveGenerator = new MapGenCavesHell();
/*  42:    */   double[] noiseData1;
/*  43:    */   double[] noiseData2;
/*  44:    */   double[] noiseData3;
/*  45:    */   double[] noiseData4;
/*  46:    */   double[] noiseData5;
/*  47:    */   private static final String __OBFID = "CL_00000392";
/*  48:    */   
/*  49:    */   public ChunkProviderHell(World par1World, long par2)
/*  50:    */   {
/*  51: 68 */     this.worldObj = par1World;
/*  52: 69 */     this.hellRNG = new Random(par2);
/*  53: 70 */     this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
/*  54: 71 */     this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
/*  55: 72 */     this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
/*  56: 73 */     this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
/*  57: 74 */     this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
/*  58: 75 */     this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
/*  59: 76 */     this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void func_147419_a(int p_147419_1_, int p_147419_2_, Block[] p_147419_3_)
/*  63:    */   {
/*  64: 81 */     byte var4 = 4;
/*  65: 82 */     byte var5 = 32;
/*  66: 83 */     int var6 = var4 + 1;
/*  67: 84 */     byte var7 = 17;
/*  68: 85 */     int var8 = var4 + 1;
/*  69: 86 */     this.noiseField = initializeNoiseField(this.noiseField, p_147419_1_ * var4, 0, p_147419_2_ * var4, var6, var7, var8);
/*  70: 88 */     for (int var9 = 0; var9 < var4; var9++) {
/*  71: 90 */       for (int var10 = 0; var10 < var4; var10++) {
/*  72: 92 */         for (int var11 = 0; var11 < 16; var11++)
/*  73:    */         {
/*  74: 94 */           double var12 = 0.125D;
/*  75: 95 */           double var14 = this.noiseField[(((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0)];
/*  76: 96 */           double var16 = this.noiseField[(((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0)];
/*  77: 97 */           double var18 = this.noiseField[(((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0)];
/*  78: 98 */           double var20 = this.noiseField[(((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0)];
/*  79: 99 */           double var22 = (this.noiseField[(((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1)] - var14) * var12;
/*  80:100 */           double var24 = (this.noiseField[(((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1)] - var16) * var12;
/*  81:101 */           double var26 = (this.noiseField[(((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1)] - var18) * var12;
/*  82:102 */           double var28 = (this.noiseField[(((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1)] - var20) * var12;
/*  83:104 */           for (int var30 = 0; var30 < 8; var30++)
/*  84:    */           {
/*  85:106 */             double var31 = 0.25D;
/*  86:107 */             double var33 = var14;
/*  87:108 */             double var35 = var16;
/*  88:109 */             double var37 = (var18 - var14) * var31;
/*  89:110 */             double var39 = (var20 - var16) * var31;
/*  90:112 */             for (int var41 = 0; var41 < 4; var41++)
/*  91:    */             {
/*  92:114 */               int var42 = var41 + var9 * 4 << 11 | 0 + var10 * 4 << 7 | var11 * 8 + var30;
/*  93:115 */               short var43 = 128;
/*  94:116 */               double var44 = 0.25D;
/*  95:117 */               double var46 = var33;
/*  96:118 */               double var48 = (var35 - var33) * var44;
/*  97:120 */               for (int var50 = 0; var50 < 4; var50++)
/*  98:    */               {
/*  99:122 */                 Block var51 = null;
/* 100:124 */                 if (var11 * 8 + var30 < var5) {
/* 101:126 */                   var51 = Blocks.lava;
/* 102:    */                 }
/* 103:129 */                 if (var46 > 0.0D) {
/* 104:131 */                   var51 = Blocks.netherrack;
/* 105:    */                 }
/* 106:134 */                 p_147419_3_[var42] = var51;
/* 107:135 */                 var42 += var43;
/* 108:136 */                 var46 += var48;
/* 109:    */               }
/* 110:139 */               var33 += var37;
/* 111:140 */               var35 += var39;
/* 112:    */             }
/* 113:143 */             var14 += var22;
/* 114:144 */             var16 += var24;
/* 115:145 */             var18 += var26;
/* 116:146 */             var20 += var28;
/* 117:    */           }
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void func_147418_b(int p_147418_1_, int p_147418_2_, Block[] p_147418_3_)
/* 124:    */   {
/* 125:155 */     byte var4 = 64;
/* 126:156 */     double var5 = 0.03125D;
/* 127:157 */     this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, p_147418_1_ * 16, p_147418_2_ * 16, 0, 16, 16, 1, var5, var5, 1.0D);
/* 128:158 */     this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, p_147418_1_ * 16, 109, p_147418_2_ * 16, 16, 1, 16, var5, 1.0D, var5);
/* 129:159 */     this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, p_147418_1_ * 16, p_147418_2_ * 16, 0, 16, 16, 1, var5 * 2.0D, var5 * 2.0D, var5 * 2.0D);
/* 130:161 */     for (int var7 = 0; var7 < 16; var7++) {
/* 131:163 */       for (int var8 = 0; var8 < 16; var8++)
/* 132:    */       {
/* 133:165 */         boolean var9 = this.slowsandNoise[(var7 + var8 * 16)] + this.hellRNG.nextDouble() * 0.2D > 0.0D;
/* 134:166 */         boolean var10 = this.gravelNoise[(var7 + var8 * 16)] + this.hellRNG.nextDouble() * 0.2D > 0.0D;
/* 135:167 */         int var11 = (int)(this.netherrackExclusivityNoise[(var7 + var8 * 16)] / 3.0D + 3.0D + this.hellRNG.nextDouble() * 0.25D);
/* 136:168 */         int var12 = -1;
/* 137:169 */         Block var13 = Blocks.netherrack;
/* 138:170 */         Block var14 = Blocks.netherrack;
/* 139:172 */         for (int var15 = 127; var15 >= 0; var15--)
/* 140:    */         {
/* 141:174 */           int var16 = (var8 * 16 + var7) * 128 + var15;
/* 142:176 */           if ((var15 < 127 - this.hellRNG.nextInt(5)) && (var15 > 0 + this.hellRNG.nextInt(5)))
/* 143:    */           {
/* 144:178 */             Block var17 = p_147418_3_[var16];
/* 145:180 */             if ((var17 != null) && (var17.getMaterial() != Material.air))
/* 146:    */             {
/* 147:182 */               if (var17 == Blocks.netherrack) {
/* 148:184 */                 if (var12 == -1)
/* 149:    */                 {
/* 150:186 */                   if (var11 <= 0)
/* 151:    */                   {
/* 152:188 */                     var13 = null;
/* 153:189 */                     var14 = Blocks.netherrack;
/* 154:    */                   }
/* 155:191 */                   else if ((var15 >= var4 - 4) && (var15 <= var4 + 1))
/* 156:    */                   {
/* 157:193 */                     var13 = Blocks.netherrack;
/* 158:194 */                     var14 = Blocks.netherrack;
/* 159:196 */                     if (var10)
/* 160:    */                     {
/* 161:198 */                       var13 = Blocks.gravel;
/* 162:199 */                       var14 = Blocks.netherrack;
/* 163:    */                     }
/* 164:202 */                     if (var9)
/* 165:    */                     {
/* 166:204 */                       var13 = Blocks.soul_sand;
/* 167:205 */                       var14 = Blocks.soul_sand;
/* 168:    */                     }
/* 169:    */                   }
/* 170:209 */                   if ((var15 < var4) && ((var13 == null) || (var13.getMaterial() == Material.air))) {
/* 171:211 */                     var13 = Blocks.lava;
/* 172:    */                   }
/* 173:214 */                   var12 = var11;
/* 174:216 */                   if (var15 >= var4 - 1) {
/* 175:218 */                     p_147418_3_[var16] = var13;
/* 176:    */                   } else {
/* 177:222 */                     p_147418_3_[var16] = var14;
/* 178:    */                   }
/* 179:    */                 }
/* 180:225 */                 else if (var12 > 0)
/* 181:    */                 {
/* 182:227 */                   var12--;
/* 183:228 */                   p_147418_3_[var16] = var14;
/* 184:    */                 }
/* 185:    */               }
/* 186:    */             }
/* 187:    */             else {
/* 188:234 */               var12 = -1;
/* 189:    */             }
/* 190:    */           }
/* 191:    */           else
/* 192:    */           {
/* 193:239 */             p_147418_3_[var16] = Blocks.bedrock;
/* 194:    */           }
/* 195:    */         }
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Chunk loadChunk(int par1, int par2)
/* 201:    */   {
/* 202:251 */     return provideChunk(par1, par2);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public Chunk provideChunk(int par1, int par2)
/* 206:    */   {
/* 207:260 */     this.hellRNG.setSeed(par1 * 341873128712L + par2 * 132897987541L);
/* 208:261 */     Block[] var3 = new Block[32768];
/* 209:262 */     func_147419_a(par1, par2, var3);
/* 210:263 */     func_147418_b(par1, par2, var3);
/* 211:264 */     this.netherCaveGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
/* 212:265 */     this.genNetherBridge.func_151539_a(this, this.worldObj, par1, par2, var3);
/* 213:266 */     Chunk var4 = new Chunk(this.worldObj, var3, par1, par2);
/* 214:267 */     BiomeGenBase[] var5 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, par1 * 16, par2 * 16, 16, 16);
/* 215:268 */     byte[] var6 = var4.getBiomeArray();
/* 216:270 */     for (int var7 = 0; var7 < var6.length; var7++) {
/* 217:272 */       var6[var7] = ((byte)var5[var7].biomeID);
/* 218:    */     }
/* 219:275 */     var4.resetRelightChecks();
/* 220:276 */     return var4;
/* 221:    */   }
/* 222:    */   
/* 223:    */   private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
/* 224:    */   {
/* 225:285 */     if (par1ArrayOfDouble == null) {
/* 226:287 */       par1ArrayOfDouble = new double[par5 * par6 * par7];
/* 227:    */     }
/* 228:290 */     double var8 = 684.41200000000003D;
/* 229:291 */     double var10 = 2053.2359999999999D;
/* 230:292 */     this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, par2, par3, par4, par5, 1, par7, 1.0D, 0.0D, 1.0D);
/* 231:293 */     this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, par2, par3, par4, par5, 1, par7, 100.0D, 0.0D, 100.0D);
/* 232:294 */     this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, var8 / 80.0D, var10 / 60.0D, var8 / 80.0D);
/* 233:295 */     this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, var8, var10, var8);
/* 234:296 */     this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, var8, var10, var8);
/* 235:297 */     int var12 = 0;
/* 236:298 */     int var13 = 0;
/* 237:299 */     double[] var14 = new double[par6];
/* 238:302 */     for (int var15 = 0; var15 < par6; var15++)
/* 239:    */     {
/* 240:304 */       var14[var15] = (Math.cos(var15 * 3.141592653589793D * 6.0D / par6) * 2.0D);
/* 241:305 */       double var16 = var15;
/* 242:307 */       if (var15 > par6 / 2) {
/* 243:309 */         var16 = par6 - 1 - var15;
/* 244:    */       }
/* 245:312 */       if (var16 < 4.0D)
/* 246:    */       {
/* 247:314 */         var16 = 4.0D - var16;
/* 248:315 */         var14[var15] -= var16 * var16 * var16 * 10.0D;
/* 249:    */       }
/* 250:    */     }
/* 251:319 */     for (var15 = 0; var15 < par5; var15++) {
/* 252:321 */       for (int var36 = 0; var36 < par7; var36++)
/* 253:    */       {
/* 254:323 */         double var17 = (this.noiseData4[var13] + 256.0D) / 512.0D;
/* 255:325 */         if (var17 > 1.0D) {
/* 256:327 */           var17 = 1.0D;
/* 257:    */         }
/* 258:330 */         double var19 = 0.0D;
/* 259:331 */         double var21 = this.noiseData5[var13] / 8000.0D;
/* 260:333 */         if (var21 < 0.0D) {
/* 261:335 */           var21 = -var21;
/* 262:    */         }
/* 263:338 */         var21 = var21 * 3.0D - 3.0D;
/* 264:340 */         if (var21 < 0.0D)
/* 265:    */         {
/* 266:342 */           var21 /= 2.0D;
/* 267:344 */           if (var21 < -1.0D) {
/* 268:346 */             var21 = -1.0D;
/* 269:    */           }
/* 270:349 */           var21 /= 1.4D;
/* 271:350 */           var21 /= 2.0D;
/* 272:351 */           var17 = 0.0D;
/* 273:    */         }
/* 274:    */         else
/* 275:    */         {
/* 276:355 */           if (var21 > 1.0D) {
/* 277:357 */             var21 = 1.0D;
/* 278:    */           }
/* 279:360 */           var21 /= 6.0D;
/* 280:    */         }
/* 281:363 */         var17 += 0.5D;
/* 282:364 */         var21 = var21 * par6 / 16.0D;
/* 283:365 */         var13++;
/* 284:367 */         for (int var23 = 0; var23 < par6; var23++)
/* 285:    */         {
/* 286:369 */           double var24 = 0.0D;
/* 287:370 */           double var26 = var14[var23];
/* 288:371 */           double var28 = this.noiseData2[var12] / 512.0D;
/* 289:372 */           double var30 = this.noiseData3[var12] / 512.0D;
/* 290:373 */           double var32 = (this.noiseData1[var12] / 10.0D + 1.0D) / 2.0D;
/* 291:375 */           if (var32 < 0.0D) {
/* 292:377 */             var24 = var28;
/* 293:379 */           } else if (var32 > 1.0D) {
/* 294:381 */             var24 = var30;
/* 295:    */           } else {
/* 296:385 */             var24 = var28 + (var30 - var28) * var32;
/* 297:    */           }
/* 298:388 */           var24 -= var26;
/* 299:391 */           if (var23 > par6 - 4)
/* 300:    */           {
/* 301:393 */             double var34 = (var23 - (par6 - 4)) / 3.0F;
/* 302:394 */             var24 = var24 * (1.0D - var34) + -10.0D * var34;
/* 303:    */           }
/* 304:397 */           if (var23 < var19)
/* 305:    */           {
/* 306:399 */             double var34 = (var19 - var23) / 4.0D;
/* 307:401 */             if (var34 < 0.0D) {
/* 308:403 */               var34 = 0.0D;
/* 309:    */             }
/* 310:406 */             if (var34 > 1.0D) {
/* 311:408 */               var34 = 1.0D;
/* 312:    */             }
/* 313:411 */             var24 = var24 * (1.0D - var34) + -10.0D * var34;
/* 314:    */           }
/* 315:414 */           par1ArrayOfDouble[var12] = var24;
/* 316:415 */           var12++;
/* 317:    */         }
/* 318:    */       }
/* 319:    */     }
/* 320:420 */     return par1ArrayOfDouble;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public boolean chunkExists(int par1, int par2)
/* 324:    */   {
/* 325:428 */     return true;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
/* 329:    */   {
/* 330:436 */     net.minecraft.block.BlockFalling.field_149832_M = true;
/* 331:437 */     int var4 = par2 * 16;
/* 332:438 */     int var5 = par3 * 16;
/* 333:439 */     this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.hellRNG, par2, par3);
/* 334:445 */     for (int var6 = 0; var6 < 8; var6++)
/* 335:    */     {
/* 336:447 */       int var7 = var4 + this.hellRNG.nextInt(16) + 8;
/* 337:448 */       int var8 = this.hellRNG.nextInt(120) + 4;
/* 338:449 */       int var9 = var5 + this.hellRNG.nextInt(16) + 8;
/* 339:450 */       new WorldGenHellLava(Blocks.flowing_lava, false).generate(this.worldObj, this.hellRNG, var7, var8, var9);
/* 340:    */     }
/* 341:453 */     var6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1;
/* 342:456 */     for (int var7 = 0; var7 < var6; var7++)
/* 343:    */     {
/* 344:458 */       int var8 = var4 + this.hellRNG.nextInt(16) + 8;
/* 345:459 */       int var9 = this.hellRNG.nextInt(120) + 4;
/* 346:460 */       int var10 = var5 + this.hellRNG.nextInt(16) + 8;
/* 347:461 */       new WorldGenFire().generate(this.worldObj, this.hellRNG, var8, var9, var10);
/* 348:    */     }
/* 349:464 */     var6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1);
/* 350:466 */     for (var7 = 0; var7 < var6; var7++)
/* 351:    */     {
/* 352:468 */       int var8 = var4 + this.hellRNG.nextInt(16) + 8;
/* 353:469 */       int var9 = this.hellRNG.nextInt(120) + 4;
/* 354:470 */       int var10 = var5 + this.hellRNG.nextInt(16) + 8;
/* 355:471 */       new WorldGenGlowStone1().generate(this.worldObj, this.hellRNG, var8, var9, var10);
/* 356:    */     }
/* 357:474 */     for (var7 = 0; var7 < 10; var7++)
/* 358:    */     {
/* 359:476 */       int var8 = var4 + this.hellRNG.nextInt(16) + 8;
/* 360:477 */       int var9 = this.hellRNG.nextInt(128);
/* 361:478 */       int var10 = var5 + this.hellRNG.nextInt(16) + 8;
/* 362:479 */       new WorldGenGlowStone2().generate(this.worldObj, this.hellRNG, var8, var9, var10);
/* 363:    */     }
/* 364:482 */     if (this.hellRNG.nextInt(1) == 0)
/* 365:    */     {
/* 366:484 */       var7 = var4 + this.hellRNG.nextInt(16) + 8;
/* 367:485 */       int var8 = this.hellRNG.nextInt(128);
/* 368:486 */       int var9 = var5 + this.hellRNG.nextInt(16) + 8;
/* 369:487 */       new WorldGenFlowers(Blocks.brown_mushroom).generate(this.worldObj, this.hellRNG, var7, var8, var9);
/* 370:    */     }
/* 371:490 */     if (this.hellRNG.nextInt(1) == 0)
/* 372:    */     {
/* 373:492 */       var7 = var4 + this.hellRNG.nextInt(16) + 8;
/* 374:493 */       int var8 = this.hellRNG.nextInt(128);
/* 375:494 */       int var9 = var5 + this.hellRNG.nextInt(16) + 8;
/* 376:495 */       new WorldGenFlowers(Blocks.red_mushroom).generate(this.worldObj, this.hellRNG, var7, var8, var9);
/* 377:    */     }
/* 378:498 */     WorldGenMinable var12 = new WorldGenMinable(Blocks.quartz_ore, 13, Blocks.netherrack);
/* 379:501 */     for (int var8 = 0; var8 < 16; var8++)
/* 380:    */     {
/* 381:503 */       int var9 = var4 + this.hellRNG.nextInt(16);
/* 382:504 */       int var10 = this.hellRNG.nextInt(108) + 10;
/* 383:505 */       int var11 = var5 + this.hellRNG.nextInt(16);
/* 384:506 */       var12.generate(this.worldObj, this.hellRNG, var9, var10, var11);
/* 385:    */     }
/* 386:509 */     for (var8 = 0; var8 < 16; var8++)
/* 387:    */     {
/* 388:511 */       int var9 = var4 + this.hellRNG.nextInt(16);
/* 389:512 */       int var10 = this.hellRNG.nextInt(108) + 10;
/* 390:513 */       int var11 = var5 + this.hellRNG.nextInt(16);
/* 391:514 */       new WorldGenHellLava(Blocks.flowing_lava, true).generate(this.worldObj, this.hellRNG, var9, var10, var11);
/* 392:    */     }
/* 393:517 */     net.minecraft.block.BlockFalling.field_149832_M = false;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
/* 397:    */   {
/* 398:526 */     return true;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void saveExtraData() {}
/* 402:    */   
/* 403:    */   public boolean unloadQueuedChunks()
/* 404:    */   {
/* 405:540 */     return false;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public boolean canSave()
/* 409:    */   {
/* 410:548 */     return true;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public String makeString()
/* 414:    */   {
/* 415:556 */     return "HellRandomLevelSource";
/* 416:    */   }
/* 417:    */   
/* 418:    */   public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
/* 419:    */   {
/* 420:564 */     if (par1EnumCreatureType == EnumCreatureType.monster)
/* 421:    */     {
/* 422:566 */       if (this.genNetherBridge.hasStructureAt(par2, par3, par4)) {
/* 423:568 */         return this.genNetherBridge.getSpawnList();
/* 424:    */       }
/* 425:571 */       if ((this.genNetherBridge.func_142038_b(par2, par3, par4)) && (this.worldObj.getBlock(par2, par3 - 1, par4) == Blocks.nether_brick)) {
/* 426:573 */         return this.genNetherBridge.getSpawnList();
/* 427:    */       }
/* 428:    */     }
/* 429:577 */     BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
/* 430:578 */     return var5.getSpawnableList(par1EnumCreatureType);
/* 431:    */   }
/* 432:    */   
/* 433:    */   public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
/* 434:    */   {
/* 435:583 */     return null;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public int getLoadedChunkCount()
/* 439:    */   {
/* 440:588 */     return 0;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void recreateStructures(int par1, int par2)
/* 444:    */   {
/* 445:593 */     this.genNetherBridge.func_151539_a(this, this.worldObj, par1, par2, null);
/* 446:    */   }
/* 447:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.ChunkProviderHell
 * JD-Core Version:    0.7.0.1
 */