/*   1:    */ package net.minecraft.world.gen.feature;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.util.MathHelper;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class WorldGenBigTree
/*  11:    */   extends WorldGenAbstractTree
/*  12:    */ {
/*  13: 16 */   static final byte[] otherCoordPairs = { 2, 0, 0, 1, 2, 1 };
/*  14: 19 */   Random rand = new Random();
/*  15:    */   World worldObj;
/*  16: 23 */   int[] basePos = new int[3];
/*  17:    */   int heightLimit;
/*  18:    */   int height;
/*  19: 26 */   double heightAttenuation = 0.618D;
/*  20: 27 */   double branchDensity = 1.0D;
/*  21: 28 */   double branchSlope = 0.381D;
/*  22: 29 */   double scaleWidth = 1.0D;
/*  23: 30 */   double leafDensity = 1.0D;
/*  24: 35 */   int trunkSize = 1;
/*  25: 40 */   int heightLimitLimit = 12;
/*  26: 45 */   int leafDistanceLimit = 4;
/*  27:    */   int[][] leafNodes;
/*  28:    */   private static final String __OBFID = "CL_00000400";
/*  29:    */   
/*  30:    */   public WorldGenBigTree(boolean par1)
/*  31:    */   {
/*  32: 53 */     super(par1);
/*  33:    */   }
/*  34:    */   
/*  35:    */   void generateLeafNodeList()
/*  36:    */   {
/*  37: 61 */     this.height = ((int)(this.heightLimit * this.heightAttenuation));
/*  38: 63 */     if (this.height >= this.heightLimit) {
/*  39: 65 */       this.height = (this.heightLimit - 1);
/*  40:    */     }
/*  41: 68 */     int var1 = (int)(1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));
/*  42: 70 */     if (var1 < 1) {
/*  43: 72 */       var1 = 1;
/*  44:    */     }
/*  45: 75 */     int[][] var2 = new int[var1 * this.heightLimit][4];
/*  46: 76 */     int var3 = this.basePos[1] + this.heightLimit - this.leafDistanceLimit;
/*  47: 77 */     int var4 = 1;
/*  48: 78 */     int var5 = this.basePos[1] + this.height;
/*  49: 79 */     int var6 = var3 - this.basePos[1];
/*  50: 80 */     var2[0][0] = this.basePos[0];
/*  51: 81 */     var2[0][1] = var3;
/*  52: 82 */     var2[0][2] = this.basePos[2];
/*  53: 83 */     var2[0][3] = var5;
/*  54: 84 */     var3--;
/*  55: 86 */     while (var6 >= 0)
/*  56:    */     {
/*  57: 88 */       int var7 = 0;
/*  58: 89 */       float var8 = layerSize(var6);
/*  59: 91 */       if (var8 < 0.0F)
/*  60:    */       {
/*  61: 93 */         var3--;
/*  62: 94 */         var6--;
/*  63:    */       }
/*  64:    */       else
/*  65:    */       {
/*  66: 98 */         for (double var9 = 0.5D; var7 < var1; var7++)
/*  67:    */         {
/*  68:100 */           double var11 = this.scaleWidth * var8 * (this.rand.nextFloat() + 0.328D);
/*  69:101 */           double var13 = this.rand.nextFloat() * 2.0D * 3.141592653589793D;
/*  70:102 */           int var15 = MathHelper.floor_double(var11 * Math.sin(var13) + this.basePos[0] + var9);
/*  71:103 */           int var16 = MathHelper.floor_double(var11 * Math.cos(var13) + this.basePos[2] + var9);
/*  72:104 */           int[] var17 = { var15, var3, var16 };
/*  73:105 */           int[] var18 = { var15, var3 + this.leafDistanceLimit, var16 };
/*  74:107 */           if (checkBlockLine(var17, var18) == -1)
/*  75:    */           {
/*  76:109 */             int[] var19 = { this.basePos[0], this.basePos[1], this.basePos[2] };
/*  77:110 */             double var20 = Math.sqrt(Math.pow(Math.abs(this.basePos[0] - var17[0]), 2.0D) + Math.pow(Math.abs(this.basePos[2] - var17[2]), 2.0D));
/*  78:111 */             double var22 = var20 * this.branchSlope;
/*  79:113 */             if (var17[1] - var22 > var5) {
/*  80:115 */               var19[1] = var5;
/*  81:    */             } else {
/*  82:119 */               var19[1] = ((int)(var17[1] - var22));
/*  83:    */             }
/*  84:122 */             if (checkBlockLine(var19, var17) == -1)
/*  85:    */             {
/*  86:124 */               var2[var4][0] = var15;
/*  87:125 */               var2[var4][1] = var3;
/*  88:126 */               var2[var4][2] = var16;
/*  89:127 */               var2[var4][3] = var19[1];
/*  90:128 */               var4++;
/*  91:    */             }
/*  92:    */           }
/*  93:    */         }
/*  94:133 */         var3--;
/*  95:134 */         var6--;
/*  96:    */       }
/*  97:    */     }
/*  98:138 */     this.leafNodes = new int[var4][4];
/*  99:139 */     System.arraycopy(var2, 0, this.leafNodes, 0, var4);
/* 100:    */   }
/* 101:    */   
/* 102:    */   void func_150529_a(int p_150529_1_, int p_150529_2_, int p_150529_3_, float p_150529_4_, byte p_150529_5_, Block p_150529_6_)
/* 103:    */   {
/* 104:144 */     int var7 = (int)(p_150529_4_ + 0.618D);
/* 105:145 */     byte var8 = otherCoordPairs[p_150529_5_];
/* 106:146 */     byte var9 = otherCoordPairs[(p_150529_5_ + 3)];
/* 107:147 */     int[] var10 = { p_150529_1_, p_150529_2_, p_150529_3_ };
/* 108:148 */     int[] var11 = new int[3];
/* 109:149 */     int var12 = -var7;
/* 110:150 */     int var13 = -var7;
/* 111:152 */     for (var11[p_150529_5_] = var10[p_150529_5_]; var12 <= var7; var12++)
/* 112:    */     {
/* 113:154 */       var10[var8] += var12;
/* 114:155 */       var13 = -var7;
/* 115:157 */       while (var13 <= var7)
/* 116:    */       {
/* 117:159 */         double var15 = Math.pow(Math.abs(var12) + 0.5D, 2.0D) + Math.pow(Math.abs(var13) + 0.5D, 2.0D);
/* 118:161 */         if (var15 > p_150529_4_ * p_150529_4_)
/* 119:    */         {
/* 120:163 */           var13++;
/* 121:    */         }
/* 122:    */         else
/* 123:    */         {
/* 124:167 */           var10[var9] += var13;
/* 125:168 */           Block var14 = this.worldObj.getBlock(var11[0], var11[1], var11[2]);
/* 126:170 */           if ((var14.getMaterial() != Material.air) && (var14.getMaterial() != Material.leaves))
/* 127:    */           {
/* 128:172 */             var13++;
/* 129:    */           }
/* 130:    */           else
/* 131:    */           {
/* 132:176 */             func_150516_a(this.worldObj, var11[0], var11[1], var11[2], p_150529_6_, 0);
/* 133:177 */             var13++;
/* 134:    */           }
/* 135:    */         }
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   float layerSize(int par1)
/* 141:    */   {
/* 142:189 */     if (par1 < this.heightLimit * 0.3D) {
/* 143:191 */       return -1.618F;
/* 144:    */     }
/* 145:195 */     float var2 = this.heightLimit / 2.0F;
/* 146:196 */     float var3 = this.heightLimit / 2.0F - par1;
/* 147:    */     float var4;
/* 148:    */     float var4;
/* 149:199 */     if (var3 == 0.0F)
/* 150:    */     {
/* 151:201 */       var4 = var2;
/* 152:    */     }
/* 153:    */     else
/* 154:    */     {
/* 155:    */       float var4;
/* 156:203 */       if (Math.abs(var3) >= var2) {
/* 157:205 */         var4 = 0.0F;
/* 158:    */       } else {
/* 159:209 */         var4 = (float)Math.sqrt(Math.pow(Math.abs(var2), 2.0D) - Math.pow(Math.abs(var3), 2.0D));
/* 160:    */       }
/* 161:    */     }
/* 162:212 */     var4 *= 0.5F;
/* 163:213 */     return var4;
/* 164:    */   }
/* 165:    */   
/* 166:    */   float leafSize(int par1)
/* 167:    */   {
/* 168:219 */     return (par1 >= 0) && (par1 < this.leafDistanceLimit) ? 2.0F : (par1 != 0) && (par1 != this.leafDistanceLimit - 1) ? 3.0F : -1.0F;
/* 169:    */   }
/* 170:    */   
/* 171:    */   void generateLeafNode(int par1, int par2, int par3)
/* 172:    */   {
/* 173:227 */     int var4 = par2;
/* 174:229 */     for (int var5 = par2 + this.leafDistanceLimit; var4 < var5; var4++)
/* 175:    */     {
/* 176:231 */       float var6 = leafSize(var4 - par2);
/* 177:232 */       func_150529_a(par1, var4, par3, var6, (byte)1, Blocks.leaves);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   void func_150530_a(int[] p_150530_1_, int[] p_150530_2_, Block p_150530_3_)
/* 182:    */   {
/* 183:238 */     int[] var4 = new int[3];
/* 184:239 */     byte var5 = 0;
/* 185:242 */     for (byte var6 = 0; var5 < 3; var5 = (byte)(var5 + 1))
/* 186:    */     {
/* 187:244 */       p_150530_2_[var5] -= p_150530_1_[var5];
/* 188:246 */       if (Math.abs(var4[var5]) > Math.abs(var4[var6])) {
/* 189:248 */         var6 = var5;
/* 190:    */       }
/* 191:    */     }
/* 192:252 */     if (var4[var6] != 0)
/* 193:    */     {
/* 194:254 */       byte var7 = otherCoordPairs[var6];
/* 195:255 */       byte var8 = otherCoordPairs[(var6 + 3)];
/* 196:    */       byte var9;
/* 197:    */       byte var9;
/* 198:258 */       if (var4[var6] > 0) {
/* 199:260 */         var9 = 1;
/* 200:    */       } else {
/* 201:264 */         var9 = -1;
/* 202:    */       }
/* 203:267 */       double var10 = var4[var7] / var4[var6];
/* 204:268 */       double var12 = var4[var8] / var4[var6];
/* 205:269 */       int[] var14 = new int[3];
/* 206:270 */       int var15 = 0;
/* 207:272 */       for (int var16 = var4[var6] + var9; var15 != var16; var15 += var9)
/* 208:    */       {
/* 209:274 */         var14[var6] = MathHelper.floor_double(p_150530_1_[var6] + var15 + 0.5D);
/* 210:275 */         var14[var7] = MathHelper.floor_double(p_150530_1_[var7] + var15 * var10 + 0.5D);
/* 211:276 */         var14[var8] = MathHelper.floor_double(p_150530_1_[var8] + var15 * var12 + 0.5D);
/* 212:277 */         byte var17 = 0;
/* 213:278 */         int var18 = Math.abs(var14[0] - p_150530_1_[0]);
/* 214:279 */         int var19 = Math.abs(var14[2] - p_150530_1_[2]);
/* 215:280 */         int var20 = Math.max(var18, var19);
/* 216:282 */         if (var20 > 0) {
/* 217:284 */           if (var18 == var20) {
/* 218:286 */             var17 = 4;
/* 219:288 */           } else if (var19 == var20) {
/* 220:290 */             var17 = 8;
/* 221:    */           }
/* 222:    */         }
/* 223:294 */         func_150516_a(this.worldObj, var14[0], var14[1], var14[2], p_150530_3_, var17);
/* 224:    */       }
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   void generateLeaves()
/* 229:    */   {
/* 230:304 */     int var1 = 0;
/* 231:306 */     for (int var2 = this.leafNodes.length; var1 < var2; var1++)
/* 232:    */     {
/* 233:308 */       int var3 = this.leafNodes[var1][0];
/* 234:309 */       int var4 = this.leafNodes[var1][1];
/* 235:310 */       int var5 = this.leafNodes[var1][2];
/* 236:311 */       generateLeafNode(var3, var4, var5);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   boolean leafNodeNeedsBase(int par1)
/* 241:    */   {
/* 242:320 */     return par1 >= this.heightLimit * 0.2D;
/* 243:    */   }
/* 244:    */   
/* 245:    */   void generateTrunk()
/* 246:    */   {
/* 247:329 */     int var1 = this.basePos[0];
/* 248:330 */     int var2 = this.basePos[1];
/* 249:331 */     int var3 = this.basePos[1] + this.height;
/* 250:332 */     int var4 = this.basePos[2];
/* 251:333 */     int[] var5 = { var1, var2, var4 };
/* 252:334 */     int[] var6 = { var1, var3, var4 };
/* 253:335 */     func_150530_a(var5, var6, Blocks.log);
/* 254:337 */     if (this.trunkSize == 2)
/* 255:    */     {
/* 256:339 */       var5[0] += 1;
/* 257:340 */       var6[0] += 1;
/* 258:341 */       func_150530_a(var5, var6, Blocks.log);
/* 259:342 */       var5[2] += 1;
/* 260:343 */       var6[2] += 1;
/* 261:344 */       func_150530_a(var5, var6, Blocks.log);
/* 262:345 */       var5[0] += -1;
/* 263:346 */       var6[0] += -1;
/* 264:347 */       func_150530_a(var5, var6, Blocks.log);
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   void generateLeafNodeBases()
/* 269:    */   {
/* 270:356 */     int var1 = 0;
/* 271:357 */     int var2 = this.leafNodes.length;
/* 272:359 */     for (int[] var3 = { this.basePos[0], this.basePos[1], this.basePos[2] }; var1 < var2; var1++)
/* 273:    */     {
/* 274:361 */       int[] var4 = this.leafNodes[var1];
/* 275:362 */       int[] var5 = { var4[0], var4[1], var4[2] };
/* 276:363 */       var3[1] = var4[3];
/* 277:364 */       int var6 = var3[1] - this.basePos[1];
/* 278:366 */       if (leafNodeNeedsBase(var6)) {
/* 279:368 */         func_150530_a(var3, var5, Blocks.log);
/* 280:    */       }
/* 281:    */     }
/* 282:    */   }
/* 283:    */   
/* 284:    */   int checkBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger)
/* 285:    */   {
/* 286:379 */     int[] var3 = new int[3];
/* 287:380 */     byte var4 = 0;
/* 288:383 */     for (byte var5 = 0; var4 < 3; var4 = (byte)(var4 + 1))
/* 289:    */     {
/* 290:385 */       par2ArrayOfInteger[var4] -= par1ArrayOfInteger[var4];
/* 291:387 */       if (Math.abs(var3[var4]) > Math.abs(var3[var5])) {
/* 292:389 */         var5 = var4;
/* 293:    */       }
/* 294:    */     }
/* 295:393 */     if (var3[var5] == 0) {
/* 296:395 */       return -1;
/* 297:    */     }
/* 298:399 */     byte var6 = otherCoordPairs[var5];
/* 299:400 */     byte var7 = otherCoordPairs[(var5 + 3)];
/* 300:    */     byte var8;
/* 301:    */     byte var8;
/* 302:403 */     if (var3[var5] > 0) {
/* 303:405 */       var8 = 1;
/* 304:    */     } else {
/* 305:409 */       var8 = -1;
/* 306:    */     }
/* 307:412 */     double var9 = var3[var6] / var3[var5];
/* 308:413 */     double var11 = var3[var7] / var3[var5];
/* 309:414 */     int[] var13 = new int[3];
/* 310:415 */     int var14 = 0;
/* 311:418 */     for (int var15 = var3[var5] + var8; var14 != var15; var14 += var8)
/* 312:    */     {
/* 313:420 */       par1ArrayOfInteger[var5] += var14;
/* 314:421 */       var13[var6] = MathHelper.floor_double(par1ArrayOfInteger[var6] + var14 * var9);
/* 315:422 */       var13[var7] = MathHelper.floor_double(par1ArrayOfInteger[var7] + var14 * var11);
/* 316:423 */       Block var16 = this.worldObj.getBlock(var13[0], var13[1], var13[2]);
/* 317:425 */       if (!func_150523_a(var16)) {
/* 318:    */         break;
/* 319:    */       }
/* 320:    */     }
/* 321:431 */     return var14 == var15 ? -1 : Math.abs(var14);
/* 322:    */   }
/* 323:    */   
/* 324:    */   boolean validTreeLocation()
/* 325:    */   {
/* 326:441 */     int[] var1 = { this.basePos[0], this.basePos[1], this.basePos[2] };
/* 327:442 */     int[] var2 = { this.basePos[0], this.basePos[1] + this.heightLimit - 1, this.basePos[2] };
/* 328:443 */     Block var3 = this.worldObj.getBlock(this.basePos[0], this.basePos[1] - 1, this.basePos[2]);
/* 329:445 */     if ((var3 != Blocks.dirt) && (var3 != Blocks.grass) && (var3 != Blocks.farmland)) {
/* 330:447 */       return false;
/* 331:    */     }
/* 332:451 */     int var4 = checkBlockLine(var1, var2);
/* 333:453 */     if (var4 == -1) {
/* 334:455 */       return true;
/* 335:    */     }
/* 336:457 */     if (var4 < 6) {
/* 337:459 */       return false;
/* 338:    */     }
/* 339:463 */     this.heightLimit = var4;
/* 340:464 */     return true;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void setScale(double par1, double par3, double par5)
/* 344:    */   {
/* 345:474 */     this.heightLimitLimit = ((int)(par1 * 12.0D));
/* 346:476 */     if (par1 > 0.5D) {
/* 347:478 */       this.leafDistanceLimit = 5;
/* 348:    */     }
/* 349:481 */     this.scaleWidth = par3;
/* 350:482 */     this.leafDensity = par5;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 354:    */   {
/* 355:487 */     this.worldObj = par1World;
/* 356:488 */     long var6 = par2Random.nextLong();
/* 357:489 */     this.rand.setSeed(var6);
/* 358:490 */     this.basePos[0] = par3;
/* 359:491 */     this.basePos[1] = par4;
/* 360:492 */     this.basePos[2] = par5;
/* 361:494 */     if (this.heightLimit == 0) {
/* 362:496 */       this.heightLimit = (5 + this.rand.nextInt(this.heightLimitLimit));
/* 363:    */     }
/* 364:499 */     if (!validTreeLocation()) {
/* 365:501 */       return false;
/* 366:    */     }
/* 367:505 */     generateLeafNodeList();
/* 368:506 */     generateLeaves();
/* 369:507 */     generateTrunk();
/* 370:508 */     generateLeafNodeBases();
/* 371:509 */     return true;
/* 372:    */   }
/* 373:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenBigTree
 * JD-Core Version:    0.7.0.1
 */