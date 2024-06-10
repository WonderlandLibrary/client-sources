/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.entity.EnumCreatureType;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.util.IProgressUpdate;
/*  10:    */ import net.minecraft.util.MathHelper;
/*  11:    */ import net.minecraft.world.ChunkPosition;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  14:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  15:    */ import net.minecraft.world.chunk.Chunk;
/*  16:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  17:    */ 
/*  18:    */ public class ChunkProviderEnd
/*  19:    */   implements IChunkProvider
/*  20:    */ {
/*  21:    */   private Random endRNG;
/*  22:    */   private NoiseGeneratorOctaves noiseGen1;
/*  23:    */   private NoiseGeneratorOctaves noiseGen2;
/*  24:    */   private NoiseGeneratorOctaves noiseGen3;
/*  25:    */   public NoiseGeneratorOctaves noiseGen4;
/*  26:    */   public NoiseGeneratorOctaves noiseGen5;
/*  27:    */   private World endWorld;
/*  28:    */   private double[] densities;
/*  29:    */   private BiomeGenBase[] biomesForGeneration;
/*  30:    */   double[] noiseData1;
/*  31:    */   double[] noiseData2;
/*  32:    */   double[] noiseData3;
/*  33:    */   double[] noiseData4;
/*  34:    */   double[] noiseData5;
/*  35: 36 */   int[][] field_73203_h = new int[32][32];
/*  36:    */   private static final String __OBFID = "CL_00000397";
/*  37:    */   
/*  38:    */   public ChunkProviderEnd(World par1World, long par2)
/*  39:    */   {
/*  40: 41 */     this.endWorld = par1World;
/*  41: 42 */     this.endRNG = new Random(par2);
/*  42: 43 */     this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
/*  43: 44 */     this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
/*  44: 45 */     this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
/*  45: 46 */     this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
/*  46: 47 */     this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void func_147420_a(int p_147420_1_, int p_147420_2_, Block[] p_147420_3_, BiomeGenBase[] p_147420_4_)
/*  50:    */   {
/*  51: 52 */     byte var5 = 2;
/*  52: 53 */     int var6 = var5 + 1;
/*  53: 54 */     byte var7 = 33;
/*  54: 55 */     int var8 = var5 + 1;
/*  55: 56 */     this.densities = initializeNoiseField(this.densities, p_147420_1_ * var5, 0, p_147420_2_ * var5, var6, var7, var8);
/*  56: 58 */     for (int var9 = 0; var9 < var5; var9++) {
/*  57: 60 */       for (int var10 = 0; var10 < var5; var10++) {
/*  58: 62 */         for (int var11 = 0; var11 < 32; var11++)
/*  59:    */         {
/*  60: 64 */           double var12 = 0.25D;
/*  61: 65 */           double var14 = this.densities[(((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0)];
/*  62: 66 */           double var16 = this.densities[(((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0)];
/*  63: 67 */           double var18 = this.densities[(((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0)];
/*  64: 68 */           double var20 = this.densities[(((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0)];
/*  65: 69 */           double var22 = (this.densities[(((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1)] - var14) * var12;
/*  66: 70 */           double var24 = (this.densities[(((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1)] - var16) * var12;
/*  67: 71 */           double var26 = (this.densities[(((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1)] - var18) * var12;
/*  68: 72 */           double var28 = (this.densities[(((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1)] - var20) * var12;
/*  69: 74 */           for (int var30 = 0; var30 < 4; var30++)
/*  70:    */           {
/*  71: 76 */             double var31 = 0.125D;
/*  72: 77 */             double var33 = var14;
/*  73: 78 */             double var35 = var16;
/*  74: 79 */             double var37 = (var18 - var14) * var31;
/*  75: 80 */             double var39 = (var20 - var16) * var31;
/*  76: 82 */             for (int var41 = 0; var41 < 8; var41++)
/*  77:    */             {
/*  78: 84 */               int var42 = var41 + var9 * 8 << 11 | 0 + var10 * 8 << 7 | var11 * 4 + var30;
/*  79: 85 */               short var43 = 128;
/*  80: 86 */               double var44 = 0.125D;
/*  81: 87 */               double var46 = var33;
/*  82: 88 */               double var48 = (var35 - var33) * var44;
/*  83: 90 */               for (int var50 = 0; var50 < 8; var50++)
/*  84:    */               {
/*  85: 92 */                 Block var51 = null;
/*  86: 94 */                 if (var46 > 0.0D) {
/*  87: 96 */                   var51 = Blocks.end_stone;
/*  88:    */                 }
/*  89: 99 */                 p_147420_3_[var42] = var51;
/*  90:100 */                 var42 += var43;
/*  91:101 */                 var46 += var48;
/*  92:    */               }
/*  93:104 */               var33 += var37;
/*  94:105 */               var35 += var39;
/*  95:    */             }
/*  96:108 */             var14 += var22;
/*  97:109 */             var16 += var24;
/*  98:110 */             var18 += var26;
/*  99:111 */             var20 += var28;
/* 100:    */           }
/* 101:    */         }
/* 102:    */       }
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void func_147421_b(int p_147421_1_, int p_147421_2_, Block[] p_147421_3_, BiomeGenBase[] p_147421_4_)
/* 107:    */   {
/* 108:120 */     for (int var5 = 0; var5 < 16; var5++) {
/* 109:122 */       for (int var6 = 0; var6 < 16; var6++)
/* 110:    */       {
/* 111:124 */         byte var7 = 1;
/* 112:125 */         int var8 = -1;
/* 113:126 */         Block var9 = Blocks.end_stone;
/* 114:127 */         Block var10 = Blocks.end_stone;
/* 115:129 */         for (int var11 = 127; var11 >= 0; var11--)
/* 116:    */         {
/* 117:131 */           int var12 = (var6 * 16 + var5) * 128 + var11;
/* 118:132 */           Block var13 = p_147421_3_[var12];
/* 119:134 */           if ((var13 != null) && (var13.getMaterial() != Material.air))
/* 120:    */           {
/* 121:136 */             if (var13 == Blocks.stone) {
/* 122:138 */               if (var8 == -1)
/* 123:    */               {
/* 124:140 */                 if (var7 <= 0)
/* 125:    */                 {
/* 126:142 */                   var9 = null;
/* 127:143 */                   var10 = Blocks.end_stone;
/* 128:    */                 }
/* 129:146 */                 var8 = var7;
/* 130:148 */                 if (var11 >= 0) {
/* 131:150 */                   p_147421_3_[var12] = var9;
/* 132:    */                 } else {
/* 133:154 */                   p_147421_3_[var12] = var10;
/* 134:    */                 }
/* 135:    */               }
/* 136:157 */               else if (var8 > 0)
/* 137:    */               {
/* 138:159 */                 var8--;
/* 139:160 */                 p_147421_3_[var12] = var10;
/* 140:    */               }
/* 141:    */             }
/* 142:    */           }
/* 143:    */           else {
/* 144:166 */             var8 = -1;
/* 145:    */           }
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Chunk loadChunk(int par1, int par2)
/* 152:    */   {
/* 153:178 */     return provideChunk(par1, par2);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Chunk provideChunk(int par1, int par2)
/* 157:    */   {
/* 158:187 */     this.endRNG.setSeed(par1 * 341873128712L + par2 * 132897987541L);
/* 159:188 */     Block[] var3 = new Block[32768];
/* 160:189 */     this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
/* 161:190 */     func_147420_a(par1, par2, var3, this.biomesForGeneration);
/* 162:191 */     func_147421_b(par1, par2, var3, this.biomesForGeneration);
/* 163:192 */     Chunk var4 = new Chunk(this.endWorld, var3, par1, par2);
/* 164:193 */     byte[] var5 = var4.getBiomeArray();
/* 165:195 */     for (int var6 = 0; var6 < var5.length; var6++) {
/* 166:197 */       var5[var6] = ((byte)this.biomesForGeneration[var6].biomeID);
/* 167:    */     }
/* 168:200 */     var4.generateSkylightMap();
/* 169:201 */     return var4;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
/* 173:    */   {
/* 174:210 */     if (par1ArrayOfDouble == null) {
/* 175:212 */       par1ArrayOfDouble = new double[par5 * par6 * par7];
/* 176:    */     }
/* 177:215 */     double var8 = 684.41200000000003D;
/* 178:216 */     double var10 = 684.41200000000003D;
/* 179:217 */     this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
/* 180:218 */     this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
/* 181:219 */     var8 *= 2.0D;
/* 182:220 */     this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, var8 / 80.0D, var10 / 160.0D, var8 / 80.0D);
/* 183:221 */     this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, var8, var10, var8);
/* 184:222 */     this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, var8, var10, var8);
/* 185:223 */     int var12 = 0;
/* 186:224 */     int var13 = 0;
/* 187:226 */     for (int var14 = 0; var14 < par5; var14++) {
/* 188:228 */       for (int var15 = 0; var15 < par7; var15++)
/* 189:    */       {
/* 190:230 */         double var16 = (this.noiseData4[var13] + 256.0D) / 512.0D;
/* 191:232 */         if (var16 > 1.0D) {
/* 192:234 */           var16 = 1.0D;
/* 193:    */         }
/* 194:237 */         double var18 = this.noiseData5[var13] / 8000.0D;
/* 195:239 */         if (var18 < 0.0D) {
/* 196:241 */           var18 = -var18 * 0.3D;
/* 197:    */         }
/* 198:244 */         var18 = var18 * 3.0D - 2.0D;
/* 199:245 */         float var20 = (var14 + par2 - 0) / 1.0F;
/* 200:246 */         float var21 = (var15 + par4 - 0) / 1.0F;
/* 201:247 */         float var22 = 100.0F - MathHelper.sqrt_float(var20 * var20 + var21 * var21) * 8.0F;
/* 202:249 */         if (var22 > 80.0F) {
/* 203:251 */           var22 = 80.0F;
/* 204:    */         }
/* 205:254 */         if (var22 < -100.0F) {
/* 206:256 */           var22 = -100.0F;
/* 207:    */         }
/* 208:259 */         if (var18 > 1.0D) {
/* 209:261 */           var18 = 1.0D;
/* 210:    */         }
/* 211:264 */         var18 /= 8.0D;
/* 212:265 */         var18 = 0.0D;
/* 213:267 */         if (var16 < 0.0D) {
/* 214:269 */           var16 = 0.0D;
/* 215:    */         }
/* 216:272 */         var16 += 0.5D;
/* 217:273 */         var18 = var18 * par6 / 16.0D;
/* 218:274 */         var13++;
/* 219:275 */         double var23 = par6 / 2.0D;
/* 220:277 */         for (int var25 = 0; var25 < par6; var25++)
/* 221:    */         {
/* 222:279 */           double var26 = 0.0D;
/* 223:280 */           double var28 = (var25 - var23) * 8.0D / var16;
/* 224:282 */           if (var28 < 0.0D) {
/* 225:284 */             var28 *= -1.0D;
/* 226:    */           }
/* 227:287 */           double var30 = this.noiseData2[var12] / 512.0D;
/* 228:288 */           double var32 = this.noiseData3[var12] / 512.0D;
/* 229:289 */           double var34 = (this.noiseData1[var12] / 10.0D + 1.0D) / 2.0D;
/* 230:291 */           if (var34 < 0.0D) {
/* 231:293 */             var26 = var30;
/* 232:295 */           } else if (var34 > 1.0D) {
/* 233:297 */             var26 = var32;
/* 234:    */           } else {
/* 235:301 */             var26 = var30 + (var32 - var30) * var34;
/* 236:    */           }
/* 237:304 */           var26 -= 8.0D;
/* 238:305 */           var26 += var22;
/* 239:306 */           byte var36 = 2;
/* 240:309 */           if (var25 > par6 / 2 - var36)
/* 241:    */           {
/* 242:311 */             double var37 = (var25 - (par6 / 2 - var36)) / 64.0F;
/* 243:313 */             if (var37 < 0.0D) {
/* 244:315 */               var37 = 0.0D;
/* 245:    */             }
/* 246:318 */             if (var37 > 1.0D) {
/* 247:320 */               var37 = 1.0D;
/* 248:    */             }
/* 249:323 */             var26 = var26 * (1.0D - var37) + -3000.0D * var37;
/* 250:    */           }
/* 251:326 */           var36 = 8;
/* 252:328 */           if (var25 < var36)
/* 253:    */           {
/* 254:330 */             double var37 = (var36 - var25) / (var36 - 1.0F);
/* 255:331 */             var26 = var26 * (1.0D - var37) + -30.0D * var37;
/* 256:    */           }
/* 257:334 */           par1ArrayOfDouble[var12] = var26;
/* 258:335 */           var12++;
/* 259:    */         }
/* 260:    */       }
/* 261:    */     }
/* 262:340 */     return par1ArrayOfDouble;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public boolean chunkExists(int par1, int par2)
/* 266:    */   {
/* 267:348 */     return true;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
/* 271:    */   {
/* 272:356 */     net.minecraft.block.BlockFalling.field_149832_M = true;
/* 273:357 */     int var4 = par2 * 16;
/* 274:358 */     int var5 = par3 * 16;
/* 275:359 */     BiomeGenBase var6 = this.endWorld.getBiomeGenForCoords(var4 + 16, var5 + 16);
/* 276:360 */     var6.decorate(this.endWorld, this.endWorld.rand, var4, var5);
/* 277:361 */     net.minecraft.block.BlockFalling.field_149832_M = false;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
/* 281:    */   {
/* 282:370 */     return true;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void saveExtraData() {}
/* 286:    */   
/* 287:    */   public boolean unloadQueuedChunks()
/* 288:    */   {
/* 289:384 */     return false;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public boolean canSave()
/* 293:    */   {
/* 294:392 */     return true;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public String makeString()
/* 298:    */   {
/* 299:400 */     return "RandomLevelSource";
/* 300:    */   }
/* 301:    */   
/* 302:    */   public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
/* 303:    */   {
/* 304:408 */     BiomeGenBase var5 = this.endWorld.getBiomeGenForCoords(par2, par4);
/* 305:409 */     return var5.getSpawnableList(par1EnumCreatureType);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
/* 309:    */   {
/* 310:414 */     return null;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public int getLoadedChunkCount()
/* 314:    */   {
/* 315:419 */     return 0;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void recreateStructures(int par1, int par2) {}
/* 319:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.ChunkProviderEnd
 * JD-Core Version:    0.7.0.1
 */