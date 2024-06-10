/*   1:    */ package net.minecraft.world.biome;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/*  11:    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*  12:    */ 
/*  13:    */ public class BiomeGenMesa
/*  14:    */   extends BiomeGenBase
/*  15:    */ {
/*  16:    */   private byte[] field_150621_aC;
/*  17:    */   private long field_150622_aD;
/*  18:    */   private NoiseGeneratorPerlin field_150623_aE;
/*  19:    */   private NoiseGeneratorPerlin field_150624_aF;
/*  20:    */   private NoiseGeneratorPerlin field_150625_aG;
/*  21:    */   private boolean field_150626_aH;
/*  22:    */   private boolean field_150620_aI;
/*  23:    */   private static final String __OBFID = "CL_00000176";
/*  24:    */   
/*  25:    */   public BiomeGenMesa(int p_i45380_1_, boolean p_i45380_2_, boolean p_i45380_3_)
/*  26:    */   {
/*  27: 25 */     super(p_i45380_1_);
/*  28: 26 */     this.field_150626_aH = p_i45380_2_;
/*  29: 27 */     this.field_150620_aI = p_i45380_3_;
/*  30: 28 */     setDisableRain();
/*  31: 29 */     setTemperatureRainfall(2.0F, 0.0F);
/*  32: 30 */     this.spawnableCreatureList.clear();
/*  33: 31 */     this.topBlock = Blocks.sand;
/*  34: 32 */     this.field_150604_aj = 1;
/*  35: 33 */     this.fillerBlock = Blocks.stained_hardened_clay;
/*  36: 34 */     this.theBiomeDecorator.treesPerChunk = -999;
/*  37: 35 */     this.theBiomeDecorator.deadBushPerChunk = 20;
/*  38: 36 */     this.theBiomeDecorator.reedsPerChunk = 3;
/*  39: 37 */     this.theBiomeDecorator.cactiPerChunk = 5;
/*  40: 38 */     this.theBiomeDecorator.flowersPerChunk = 0;
/*  41: 39 */     this.spawnableCreatureList.clear();
/*  42: 41 */     if (p_i45380_3_) {
/*  43: 43 */       this.theBiomeDecorator.treesPerChunk = 5;
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
/*  48:    */   {
/*  49: 49 */     return this.worldGeneratorTrees;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_)
/*  53:    */   {
/*  54: 57 */     return 10387789;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_)
/*  58:    */   {
/*  59: 65 */     return 9470285;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void decorate(World par1World, Random par2Random, int par3, int par4)
/*  63:    */   {
/*  64: 70 */     super.decorate(par1World, par2Random, par3, par4);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
/*  68:    */   {
/*  69: 75 */     if ((this.field_150621_aC == null) || (this.field_150622_aD != p_150573_1_.getSeed())) {
/*  70: 77 */       func_150619_a(p_150573_1_.getSeed());
/*  71:    */     }
/*  72: 80 */     if ((this.field_150623_aE == null) || (this.field_150624_aF == null) || (this.field_150622_aD != p_150573_1_.getSeed()))
/*  73:    */     {
/*  74: 82 */       Random var9 = new Random(this.field_150622_aD);
/*  75: 83 */       this.field_150623_aE = new NoiseGeneratorPerlin(var9, 4);
/*  76: 84 */       this.field_150624_aF = new NoiseGeneratorPerlin(var9, 1);
/*  77:    */     }
/*  78: 87 */     this.field_150622_aD = p_150573_1_.getSeed();
/*  79: 88 */     double var25 = 0.0D;
/*  80: 92 */     if (this.field_150626_aH)
/*  81:    */     {
/*  82: 94 */       int var11 = (p_150573_5_ & 0xFFFFFFF0) + (p_150573_6_ & 0xF);
/*  83: 95 */       int var12 = (p_150573_6_ & 0xFFFFFFF0) + (p_150573_5_ & 0xF);
/*  84: 96 */       double var13 = Math.min(Math.abs(p_150573_7_), this.field_150623_aE.func_151601_a(var11 * 0.25D, var12 * 0.25D));
/*  85: 98 */       if (var13 > 0.0D)
/*  86:    */       {
/*  87:100 */         double var15 = 0.001953125D;
/*  88:101 */         double var17 = Math.abs(this.field_150624_aF.func_151601_a(var11 * var15, var12 * var15));
/*  89:102 */         var25 = var13 * var13 * 2.5D;
/*  90:103 */         double var19 = Math.ceil(var17 * 50.0D) + 14.0D;
/*  91:105 */         if (var25 > var19) {
/*  92:107 */           var25 = var19;
/*  93:    */         }
/*  94:110 */         var25 += 64.0D;
/*  95:    */       }
/*  96:    */     }
/*  97:114 */     int var11 = p_150573_5_ & 0xF;
/*  98:115 */     int var12 = p_150573_6_ & 0xF;
/*  99:116 */     boolean var26 = true;
/* 100:117 */     Block var14 = Blocks.stained_hardened_clay;
/* 101:118 */     Block var27 = this.fillerBlock;
/* 102:119 */     int var16 = (int)(p_150573_7_ / 3.0D + 3.0D + p_150573_2_.nextDouble() * 0.25D);
/* 103:120 */     boolean var28 = Math.cos(p_150573_7_ / 3.0D * 3.141592653589793D) > 0.0D;
/* 104:121 */     int var18 = -1;
/* 105:122 */     boolean var29 = false;
/* 106:123 */     int var20 = p_150573_3_.length / 256;
/* 107:125 */     for (int var21 = 255; var21 >= 0; var21--)
/* 108:    */     {
/* 109:127 */       int var22 = (var12 * 16 + var11) * var20 + var21;
/* 110:129 */       if (((p_150573_3_[var22] == null) || (p_150573_3_[var22].getMaterial() == Material.air)) && (var21 < (int)var25)) {
/* 111:131 */         p_150573_3_[var22] = Blocks.stone;
/* 112:    */       }
/* 113:134 */       if (var21 <= 0 + p_150573_2_.nextInt(5))
/* 114:    */       {
/* 115:136 */         p_150573_3_[var22] = Blocks.bedrock;
/* 116:    */       }
/* 117:    */       else
/* 118:    */       {
/* 119:140 */         Block var23 = p_150573_3_[var22];
/* 120:142 */         if ((var23 != null) && (var23.getMaterial() != Material.air))
/* 121:    */         {
/* 122:144 */           if (var23 == Blocks.stone) {
/* 123:148 */             if (var18 == -1)
/* 124:    */             {
/* 125:150 */               var29 = false;
/* 126:152 */               if (var16 <= 0)
/* 127:    */               {
/* 128:154 */                 var14 = null;
/* 129:155 */                 var27 = Blocks.stone;
/* 130:    */               }
/* 131:157 */               else if ((var21 >= 59) && (var21 <= 64))
/* 132:    */               {
/* 133:159 */                 var14 = Blocks.stained_hardened_clay;
/* 134:160 */                 var27 = this.fillerBlock;
/* 135:    */               }
/* 136:163 */               if ((var21 < 63) && ((var14 == null) || (var14.getMaterial() == Material.air))) {
/* 137:165 */                 var14 = Blocks.water;
/* 138:    */               }
/* 139:168 */               var18 = var16 + Math.max(0, var21 - 63);
/* 140:170 */               if (var21 >= 62)
/* 141:    */               {
/* 142:172 */                 if ((this.field_150620_aI) && (var21 > 86 + var16 * 2))
/* 143:    */                 {
/* 144:174 */                   if (var28)
/* 145:    */                   {
/* 146:176 */                     p_150573_3_[var22] = Blocks.dirt;
/* 147:177 */                     p_150573_4_[var22] = 1;
/* 148:    */                   }
/* 149:    */                   else
/* 150:    */                   {
/* 151:181 */                     p_150573_3_[var22] = Blocks.grass;
/* 152:    */                   }
/* 153:    */                 }
/* 154:184 */                 else if (var21 > 66 + var16)
/* 155:    */                 {
/* 156:186 */                   byte var24 = 16;
/* 157:188 */                   if ((var21 >= 64) && (var21 <= 127))
/* 158:    */                   {
/* 159:190 */                     if (!var28) {
/* 160:192 */                       var24 = func_150618_d(p_150573_5_, var21, p_150573_6_);
/* 161:    */                     }
/* 162:    */                   }
/* 163:    */                   else {
/* 164:197 */                     var24 = 1;
/* 165:    */                   }
/* 166:200 */                   if (var24 < 16)
/* 167:    */                   {
/* 168:202 */                     p_150573_3_[var22] = Blocks.stained_hardened_clay;
/* 169:203 */                     p_150573_4_[var22] = var24;
/* 170:    */                   }
/* 171:    */                   else
/* 172:    */                   {
/* 173:207 */                     p_150573_3_[var22] = Blocks.hardened_clay;
/* 174:    */                   }
/* 175:    */                 }
/* 176:    */                 else
/* 177:    */                 {
/* 178:212 */                   p_150573_3_[var22] = this.topBlock;
/* 179:213 */                   p_150573_4_[var22] = ((byte)this.field_150604_aj);
/* 180:214 */                   var29 = true;
/* 181:    */                 }
/* 182:    */               }
/* 183:    */               else
/* 184:    */               {
/* 185:219 */                 p_150573_3_[var22] = var27;
/* 186:221 */                 if (var27 == Blocks.stained_hardened_clay) {
/* 187:223 */                   p_150573_4_[var22] = 1;
/* 188:    */                 }
/* 189:    */               }
/* 190:    */             }
/* 191:227 */             else if (var18 > 0)
/* 192:    */             {
/* 193:229 */               var18--;
/* 194:231 */               if (var29)
/* 195:    */               {
/* 196:233 */                 p_150573_3_[var22] = Blocks.stained_hardened_clay;
/* 197:234 */                 p_150573_4_[var22] = 1;
/* 198:    */               }
/* 199:    */               else
/* 200:    */               {
/* 201:238 */                 byte var24 = func_150618_d(p_150573_5_, var21, p_150573_6_);
/* 202:240 */                 if (var24 < 16)
/* 203:    */                 {
/* 204:242 */                   p_150573_3_[var22] = Blocks.stained_hardened_clay;
/* 205:243 */                   p_150573_4_[var22] = var24;
/* 206:    */                 }
/* 207:    */                 else
/* 208:    */                 {
/* 209:247 */                   p_150573_3_[var22] = Blocks.hardened_clay;
/* 210:    */                 }
/* 211:    */               }
/* 212:    */             }
/* 213:    */           }
/* 214:    */         }
/* 215:    */         else {
/* 216:255 */           var18 = -1;
/* 217:    */         }
/* 218:    */       }
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   private void func_150619_a(long p_150619_1_)
/* 223:    */   {
/* 224:263 */     this.field_150621_aC = new byte[64];
/* 225:264 */     Arrays.fill(this.field_150621_aC, (byte)16);
/* 226:265 */     Random var3 = new Random(p_150619_1_);
/* 227:266 */     this.field_150625_aG = new NoiseGeneratorPerlin(var3, 1);
/* 228:269 */     for (int var4 = 0; var4 < 64; var4++)
/* 229:    */     {
/* 230:271 */       var4 += var3.nextInt(5) + 1;
/* 231:273 */       if (var4 < 64) {
/* 232:275 */         this.field_150621_aC[var4] = 1;
/* 233:    */       }
/* 234:    */     }
/* 235:279 */     var4 = var3.nextInt(4) + 2;
/* 236:285 */     for (int var5 = 0; var5 < var4; var5++)
/* 237:    */     {
/* 238:287 */       int var6 = var3.nextInt(3) + 1;
/* 239:288 */       int var7 = var3.nextInt(64);
/* 240:290 */       for (int var8 = 0; (var7 + var8 < 64) && (var8 < var6); var8++) {
/* 241:292 */         this.field_150621_aC[(var7 + var8)] = 4;
/* 242:    */       }
/* 243:    */     }
/* 244:296 */     var5 = var3.nextInt(4) + 2;
/* 245:299 */     for (int var6 = 0; var6 < var5; var6++)
/* 246:    */     {
/* 247:301 */       int var7 = var3.nextInt(3) + 2;
/* 248:302 */       int var8 = var3.nextInt(64);
/* 249:304 */       for (int var9 = 0; (var8 + var9 < 64) && (var9 < var7); var9++) {
/* 250:306 */         this.field_150621_aC[(var8 + var9)] = 12;
/* 251:    */       }
/* 252:    */     }
/* 253:310 */     var6 = var3.nextInt(4) + 2;
/* 254:312 */     for (int var7 = 0; var7 < var6; var7++)
/* 255:    */     {
/* 256:314 */       int var8 = var3.nextInt(3) + 1;
/* 257:315 */       int var9 = var3.nextInt(64);
/* 258:317 */       for (int var10 = 0; (var9 + var10 < 64) && (var10 < var8); var10++) {
/* 259:319 */         this.field_150621_aC[(var9 + var10)] = 14;
/* 260:    */       }
/* 261:    */     }
/* 262:323 */     var7 = var3.nextInt(3) + 3;
/* 263:324 */     int var8 = 0;
/* 264:326 */     for (int var9 = 0; var9 < var7; var9++)
/* 265:    */     {
/* 266:328 */       byte var12 = 1;
/* 267:329 */       var8 += var3.nextInt(16) + 4;
/* 268:331 */       for (int var11 = 0; (var8 + var11 < 64) && (var11 < var12); var11++)
/* 269:    */       {
/* 270:333 */         this.field_150621_aC[(var8 + var11)] = 0;
/* 271:335 */         if ((var8 + var11 > 1) && (var3.nextBoolean())) {
/* 272:337 */           this.field_150621_aC[(var8 + var11 - 1)] = 8;
/* 273:    */         }
/* 274:340 */         if ((var8 + var11 < 63) && (var3.nextBoolean())) {
/* 275:342 */           this.field_150621_aC[(var8 + var11 + 1)] = 8;
/* 276:    */         }
/* 277:    */       }
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   private byte func_150618_d(int p_150618_1_, int p_150618_2_, int p_150618_3_)
/* 282:    */   {
/* 283:350 */     int var4 = (int)Math.round(this.field_150625_aG.func_151601_a(p_150618_1_ * 1.0D / 512.0D, p_150618_1_ * 1.0D / 512.0D) * 2.0D);
/* 284:351 */     return this.field_150621_aC[((p_150618_2_ + var4 + 64) % 64)];
/* 285:    */   }
/* 286:    */   
/* 287:    */   protected BiomeGenBase func_150566_k()
/* 288:    */   {
/* 289:356 */     boolean var1 = this.biomeID == BiomeGenBase.field_150589_Z.biomeID;
/* 290:357 */     BiomeGenMesa var2 = new BiomeGenMesa(this.biomeID + 128, var1, this.field_150620_aI);
/* 291:359 */     if (!var1)
/* 292:    */     {
/* 293:361 */       var2.func_150570_a(field_150591_g);
/* 294:362 */       var2.setBiomeName(this.biomeName + " M");
/* 295:    */     }
/* 296:    */     else
/* 297:    */     {
/* 298:366 */       var2.setBiomeName(this.biomeName + " (Bryce)");
/* 299:    */     }
/* 300:369 */     var2.func_150557_a(this.color, true);
/* 301:370 */     return var2;
/* 302:    */   }
/* 303:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenMesa
 * JD-Core Version:    0.7.0.1
 */