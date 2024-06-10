/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.util.AxisAlignedBB;
/*  10:    */ import net.minecraft.util.IIcon;
/*  11:    */ import net.minecraft.util.Vec3;
/*  12:    */ import net.minecraft.util.Vec3Pool;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.WorldProvider;
/*  16:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  17:    */ 
/*  18:    */ public abstract class BlockLiquid
/*  19:    */   extends Block
/*  20:    */ {
/*  21:    */   private IIcon[] field_149806_a;
/*  22:    */   private static final String __OBFID = "CL_00000265";
/*  23:    */   
/*  24:    */   protected BlockLiquid(Material p_i45413_1_)
/*  25:    */   {
/*  26: 22 */     super(p_i45413_1_);
/*  27: 23 */     float var2 = 0.0F;
/*  28: 24 */     float var3 = 0.0F;
/*  29: 25 */     setBlockBounds(0.0F + var3, 0.0F + var2, 0.0F + var3, 1.0F + var3, 1.0F + var2, 1.0F + var3);
/*  30: 26 */     setTickRandomly(true);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
/*  34:    */   {
/*  35: 31 */     return this.blockMaterial != Material.lava;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getBlockColor()
/*  39:    */   {
/*  40: 36 */     return 16777215;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/*  44:    */   {
/*  45: 45 */     if (this.blockMaterial != Material.water) {
/*  46: 47 */       return 16777215;
/*  47:    */     }
/*  48: 51 */     int var5 = 0;
/*  49: 52 */     int var6 = 0;
/*  50: 53 */     int var7 = 0;
/*  51: 55 */     for (int var8 = -1; var8 <= 1; var8++) {
/*  52: 57 */       for (int var9 = -1; var9 <= 1; var9++)
/*  53:    */       {
/*  54: 59 */         int var10 = p_149720_1_.getBiomeGenForCoords(p_149720_2_ + var9, p_149720_4_ + var8).waterColorMultiplier;
/*  55: 60 */         var5 += ((var10 & 0xFF0000) >> 16);
/*  56: 61 */         var6 += ((var10 & 0xFF00) >> 8);
/*  57: 62 */         var7 += (var10 & 0xFF);
/*  58:    */       }
/*  59:    */     }
/*  60: 66 */     return (var5 / 9 & 0xFF) << 16 | (var6 / 9 & 0xFF) << 8 | var7 / 9 & 0xFF;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static float func_149801_b(int p_149801_0_)
/*  64:    */   {
/*  65: 72 */     if (p_149801_0_ >= 8) {
/*  66: 74 */       p_149801_0_ = 0;
/*  67:    */     }
/*  68: 77 */     return (p_149801_0_ + 1) / 9.0F;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  72:    */   {
/*  73: 85 */     return (p_149691_1_ != 0) && (p_149691_1_ != 1) ? this.field_149806_a[1] : this.field_149806_a[0];
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected int func_149804_e(World p_149804_1_, int p_149804_2_, int p_149804_3_, int p_149804_4_)
/*  77:    */   {
/*  78: 90 */     return p_149804_1_.getBlock(p_149804_2_, p_149804_3_, p_149804_4_).getMaterial() == this.blockMaterial ? p_149804_1_.getBlockMetadata(p_149804_2_, p_149804_3_, p_149804_4_) : -1;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected int func_149798_e(IBlockAccess p_149798_1_, int p_149798_2_, int p_149798_3_, int p_149798_4_)
/*  82:    */   {
/*  83: 95 */     if (p_149798_1_.getBlock(p_149798_2_, p_149798_3_, p_149798_4_).getMaterial() != this.blockMaterial) {
/*  84: 97 */       return -1;
/*  85:    */     }
/*  86:101 */     int var5 = p_149798_1_.getBlockMetadata(p_149798_2_, p_149798_3_, p_149798_4_);
/*  87:103 */     if (var5 >= 8) {
/*  88:105 */       var5 = 0;
/*  89:    */     }
/*  90:108 */     return var5;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean renderAsNormalBlock()
/*  94:    */   {
/*  95:114 */     return false;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isOpaqueCube()
/*  99:    */   {
/* 100:119 */     return false;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
/* 104:    */   {
/* 105:128 */     return (p_149678_2_) && (p_149678_1_ == 0);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_)
/* 109:    */   {
/* 110:133 */     Material var6 = p_149747_1_.getBlock(p_149747_2_, p_149747_3_, p_149747_4_).getMaterial();
/* 111:134 */     return var6 == Material.ice ? false : p_149747_5_ == 1 ? true : var6 == this.blockMaterial ? false : super.isBlockSolid(p_149747_1_, p_149747_2_, p_149747_3_, p_149747_4_, p_149747_5_);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 115:    */   {
/* 116:139 */     Material var6 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_).getMaterial();
/* 117:140 */     return p_149646_5_ == 1 ? true : var6 == this.blockMaterial ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 121:    */   {
/* 122:149 */     return null;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getRenderType()
/* 126:    */   {
/* 127:157 */     return 4;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 131:    */   {
/* 132:162 */     return null;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int quantityDropped(Random p_149745_1_)
/* 136:    */   {
/* 137:170 */     return 0;
/* 138:    */   }
/* 139:    */   
/* 140:    */   private Vec3 func_149800_f(IBlockAccess p_149800_1_, int p_149800_2_, int p_149800_3_, int p_149800_4_)
/* 141:    */   {
/* 142:175 */     Vec3 var5 = p_149800_1_.getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);
/* 143:176 */     int var6 = func_149798_e(p_149800_1_, p_149800_2_, p_149800_3_, p_149800_4_);
/* 144:178 */     for (int var7 = 0; var7 < 4; var7++)
/* 145:    */     {
/* 146:180 */       int var8 = p_149800_2_;
/* 147:181 */       int var10 = p_149800_4_;
/* 148:183 */       if (var7 == 0) {
/* 149:185 */         var8 = p_149800_2_ - 1;
/* 150:    */       }
/* 151:188 */       if (var7 == 1) {
/* 152:190 */         var10 = p_149800_4_ - 1;
/* 153:    */       }
/* 154:193 */       if (var7 == 2) {
/* 155:195 */         var8++;
/* 156:    */       }
/* 157:198 */       if (var7 == 3) {
/* 158:200 */         var10++;
/* 159:    */       }
/* 160:203 */       int var11 = func_149798_e(p_149800_1_, var8, p_149800_3_, var10);
/* 161:206 */       if (var11 < 0)
/* 162:    */       {
/* 163:208 */         if (!p_149800_1_.getBlock(var8, p_149800_3_, var10).getMaterial().blocksMovement())
/* 164:    */         {
/* 165:210 */           var11 = func_149798_e(p_149800_1_, var8, p_149800_3_ - 1, var10);
/* 166:212 */           if (var11 >= 0)
/* 167:    */           {
/* 168:214 */             int var12 = var11 - (var6 - 8);
/* 169:215 */             var5 = var5.addVector((var8 - p_149800_2_) * var12, (p_149800_3_ - p_149800_3_) * var12, (var10 - p_149800_4_) * var12);
/* 170:    */           }
/* 171:    */         }
/* 172:    */       }
/* 173:219 */       else if (var11 >= 0)
/* 174:    */       {
/* 175:221 */         int var12 = var11 - var6;
/* 176:222 */         var5 = var5.addVector((var8 - p_149800_2_) * var12, (p_149800_3_ - p_149800_3_) * var12, (var10 - p_149800_4_) * var12);
/* 177:    */       }
/* 178:    */     }
/* 179:226 */     if (p_149800_1_.getBlockMetadata(p_149800_2_, p_149800_3_, p_149800_4_) >= 8)
/* 180:    */     {
/* 181:228 */       boolean var13 = false;
/* 182:230 */       if ((var13) || (isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_, p_149800_4_ - 1, 2))) {
/* 183:232 */         var13 = true;
/* 184:    */       }
/* 185:235 */       if ((var13) || (isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_, p_149800_4_ + 1, 3))) {
/* 186:237 */         var13 = true;
/* 187:    */       }
/* 188:240 */       if ((var13) || (isBlockSolid(p_149800_1_, p_149800_2_ - 1, p_149800_3_, p_149800_4_, 4))) {
/* 189:242 */         var13 = true;
/* 190:    */       }
/* 191:245 */       if ((var13) || (isBlockSolid(p_149800_1_, p_149800_2_ + 1, p_149800_3_, p_149800_4_, 5))) {
/* 192:247 */         var13 = true;
/* 193:    */       }
/* 194:250 */       if ((var13) || (isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_ + 1, p_149800_4_ - 1, 2))) {
/* 195:252 */         var13 = true;
/* 196:    */       }
/* 197:255 */       if ((var13) || (isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_ + 1, p_149800_4_ + 1, 3))) {
/* 198:257 */         var13 = true;
/* 199:    */       }
/* 200:260 */       if ((var13) || (isBlockSolid(p_149800_1_, p_149800_2_ - 1, p_149800_3_ + 1, p_149800_4_, 4))) {
/* 201:262 */         var13 = true;
/* 202:    */       }
/* 203:265 */       if ((var13) || (isBlockSolid(p_149800_1_, p_149800_2_ + 1, p_149800_3_ + 1, p_149800_4_, 5))) {
/* 204:267 */         var13 = true;
/* 205:    */       }
/* 206:270 */       if (var13) {
/* 207:272 */         var5 = var5.normalize().addVector(0.0D, -6.0D, 0.0D);
/* 208:    */       }
/* 209:    */     }
/* 210:276 */     var5 = var5.normalize();
/* 211:277 */     return var5;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void velocityToAddToEntity(World p_149640_1_, int p_149640_2_, int p_149640_3_, int p_149640_4_, Entity p_149640_5_, Vec3 p_149640_6_)
/* 215:    */   {
/* 216:282 */     Vec3 var7 = func_149800_f(p_149640_1_, p_149640_2_, p_149640_3_, p_149640_4_);
/* 217:283 */     p_149640_6_.xCoord += var7.xCoord;
/* 218:284 */     p_149640_6_.yCoord += var7.yCoord;
/* 219:285 */     p_149640_6_.zCoord += var7.zCoord;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public int func_149738_a(World p_149738_1_)
/* 223:    */   {
/* 224:290 */     return this.blockMaterial == Material.lava ? 30 : p_149738_1_.provider.hasNoSky ? 10 : this.blockMaterial == Material.water ? 5 : 0;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public int getBlockBrightness(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_)
/* 228:    */   {
/* 229:295 */     int var5 = p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, 0);
/* 230:296 */     int var6 = p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_ + 1, p_149677_4_, 0);
/* 231:297 */     int var7 = var5 & 0xFF;
/* 232:298 */     int var8 = var6 & 0xFF;
/* 233:299 */     int var9 = var5 >> 16 & 0xFF;
/* 234:300 */     int var10 = var6 >> 16 & 0xFF;
/* 235:301 */     return (var7 > var8 ? var7 : var8) | (var9 > var10 ? var9 : var10) << 16;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public int getRenderBlockPass()
/* 239:    */   {
/* 240:309 */     return this.blockMaterial == Material.water ? 1 : 0;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/* 244:    */   {
/* 245:319 */     if (this.blockMaterial == Material.water)
/* 246:    */     {
/* 247:321 */       if (p_149734_5_.nextInt(10) == 0)
/* 248:    */       {
/* 249:323 */         int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
/* 250:325 */         if ((var6 <= 0) || (var6 >= 8)) {
/* 251:327 */           p_149734_1_.spawnParticle("suspended", p_149734_2_ + p_149734_5_.nextFloat(), p_149734_3_ + p_149734_5_.nextFloat(), p_149734_4_ + p_149734_5_.nextFloat(), 0.0D, 0.0D, 0.0D);
/* 252:    */         }
/* 253:    */       }
/* 254:331 */       for (int var6 = 0; var6 < 0; var6++)
/* 255:    */       {
/* 256:333 */         int var7 = p_149734_5_.nextInt(4);
/* 257:334 */         int var8 = p_149734_2_;
/* 258:335 */         int var9 = p_149734_4_;
/* 259:337 */         if (var7 == 0) {
/* 260:339 */           var8 = p_149734_2_ - 1;
/* 261:    */         }
/* 262:342 */         if (var7 == 1) {
/* 263:344 */           var8++;
/* 264:    */         }
/* 265:347 */         if (var7 == 2) {
/* 266:349 */           var9 = p_149734_4_ - 1;
/* 267:    */         }
/* 268:352 */         if (var7 == 3) {
/* 269:354 */           var9++;
/* 270:    */         }
/* 271:357 */         if ((p_149734_1_.getBlock(var8, p_149734_3_, var9).getMaterial() == Material.air) && ((p_149734_1_.getBlock(var8, p_149734_3_ - 1, var9).getMaterial().blocksMovement()) || (p_149734_1_.getBlock(var8, p_149734_3_ - 1, var9).getMaterial().isLiquid())))
/* 272:    */         {
/* 273:359 */           float var10 = 0.0625F;
/* 274:360 */           double var11 = p_149734_2_ + p_149734_5_.nextFloat();
/* 275:361 */           double var13 = p_149734_3_ + p_149734_5_.nextFloat();
/* 276:362 */           double var15 = p_149734_4_ + p_149734_5_.nextFloat();
/* 277:364 */           if (var7 == 0) {
/* 278:366 */             var11 = p_149734_2_ - var10;
/* 279:    */           }
/* 280:369 */           if (var7 == 1) {
/* 281:371 */             var11 = p_149734_2_ + 1 + var10;
/* 282:    */           }
/* 283:374 */           if (var7 == 2) {
/* 284:376 */             var15 = p_149734_4_ - var10;
/* 285:    */           }
/* 286:379 */           if (var7 == 3) {
/* 287:381 */             var15 = p_149734_4_ + 1 + var10;
/* 288:    */           }
/* 289:384 */           double var17 = 0.0D;
/* 290:385 */           double var19 = 0.0D;
/* 291:387 */           if (var7 == 0) {
/* 292:389 */             var17 = -var10;
/* 293:    */           }
/* 294:392 */           if (var7 == 1) {
/* 295:394 */             var17 = var10;
/* 296:    */           }
/* 297:397 */           if (var7 == 2) {
/* 298:399 */             var19 = -var10;
/* 299:    */           }
/* 300:402 */           if (var7 == 3) {
/* 301:404 */             var19 = var10;
/* 302:    */           }
/* 303:407 */           p_149734_1_.spawnParticle("splash", var11, var13, var15, var17, 0.0D, var19);
/* 304:    */         }
/* 305:    */       }
/* 306:    */     }
/* 307:412 */     if ((this.blockMaterial == Material.water) && (p_149734_5_.nextInt(64) == 0))
/* 308:    */     {
/* 309:414 */       int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
/* 310:416 */       if ((var6 > 0) && (var6 < 8)) {
/* 311:418 */         p_149734_1_.playSound(p_149734_2_ + 0.5F, p_149734_3_ + 0.5F, p_149734_4_ + 0.5F, "liquid.water", p_149734_5_.nextFloat() * 0.25F + 0.75F, p_149734_5_.nextFloat() * 1.0F + 0.5F, false);
/* 312:    */       }
/* 313:    */     }
/* 314:426 */     if ((this.blockMaterial == Material.lava) && (p_149734_1_.getBlock(p_149734_2_, p_149734_3_ + 1, p_149734_4_).getMaterial() == Material.air) && (!p_149734_1_.getBlock(p_149734_2_, p_149734_3_ + 1, p_149734_4_).isOpaqueCube()))
/* 315:    */     {
/* 316:428 */       if (p_149734_5_.nextInt(100) == 0)
/* 317:    */       {
/* 318:430 */         double var21 = p_149734_2_ + p_149734_5_.nextFloat();
/* 319:431 */         double var22 = p_149734_3_ + this.field_149756_F;
/* 320:432 */         double var23 = p_149734_4_ + p_149734_5_.nextFloat();
/* 321:433 */         p_149734_1_.spawnParticle("lava", var21, var22, var23, 0.0D, 0.0D, 0.0D);
/* 322:434 */         p_149734_1_.playSound(var21, var22, var23, "liquid.lavapop", 0.2F + p_149734_5_.nextFloat() * 0.2F, 0.9F + p_149734_5_.nextFloat() * 0.15F, false);
/* 323:    */       }
/* 324:437 */       if (p_149734_5_.nextInt(200) == 0) {
/* 325:439 */         p_149734_1_.playSound(p_149734_2_, p_149734_3_, p_149734_4_, "liquid.lava", 0.2F + p_149734_5_.nextFloat() * 0.2F, 0.9F + p_149734_5_.nextFloat() * 0.15F, false);
/* 326:    */       }
/* 327:    */     }
/* 328:443 */     if ((p_149734_5_.nextInt(10) == 0) && (World.doesBlockHaveSolidTopSurface(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_)) && (!p_149734_1_.getBlock(p_149734_2_, p_149734_3_ - 2, p_149734_4_).getMaterial().blocksMovement()))
/* 329:    */     {
/* 330:445 */       double var21 = p_149734_2_ + p_149734_5_.nextFloat();
/* 331:446 */       double var22 = p_149734_3_ - 1.05D;
/* 332:447 */       double var23 = p_149734_4_ + p_149734_5_.nextFloat();
/* 333:449 */       if (this.blockMaterial == Material.water) {
/* 334:451 */         p_149734_1_.spawnParticle("dripWater", var21, var22, var23, 0.0D, 0.0D, 0.0D);
/* 335:    */       } else {
/* 336:455 */         p_149734_1_.spawnParticle("dripLava", var21, var22, var23, 0.0D, 0.0D, 0.0D);
/* 337:    */       }
/* 338:    */     }
/* 339:    */   }
/* 340:    */   
/* 341:    */   public static double func_149802_a(IBlockAccess p_149802_0_, int p_149802_1_, int p_149802_2_, int p_149802_3_, Material p_149802_4_)
/* 342:    */   {
/* 343:462 */     Vec3 var5 = null;
/* 344:464 */     if (p_149802_4_ == Material.water) {
/* 345:466 */       var5 = Blocks.flowing_water.func_149800_f(p_149802_0_, p_149802_1_, p_149802_2_, p_149802_3_);
/* 346:    */     }
/* 347:469 */     if (p_149802_4_ == Material.lava) {
/* 348:471 */       var5 = Blocks.flowing_lava.func_149800_f(p_149802_0_, p_149802_1_, p_149802_2_, p_149802_3_);
/* 349:    */     }
/* 350:474 */     return (var5.xCoord == 0.0D) && (var5.zCoord == 0.0D) ? -1000.0D : Math.atan2(var5.zCoord, var5.xCoord) - 1.570796326794897D;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/* 354:    */   {
/* 355:479 */     func_149805_n(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 359:    */   {
/* 360:484 */     func_149805_n(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/* 361:    */   }
/* 362:    */   
/* 363:    */   private void func_149805_n(World p_149805_1_, int p_149805_2_, int p_149805_3_, int p_149805_4_)
/* 364:    */   {
/* 365:489 */     if (p_149805_1_.getBlock(p_149805_2_, p_149805_3_, p_149805_4_) == this) {
/* 366:491 */       if (this.blockMaterial == Material.lava)
/* 367:    */       {
/* 368:493 */         boolean var5 = false;
/* 369:495 */         if ((var5) || (p_149805_1_.getBlock(p_149805_2_, p_149805_3_, p_149805_4_ - 1).getMaterial() == Material.water)) {
/* 370:497 */           var5 = true;
/* 371:    */         }
/* 372:500 */         if ((var5) || (p_149805_1_.getBlock(p_149805_2_, p_149805_3_, p_149805_4_ + 1).getMaterial() == Material.water)) {
/* 373:502 */           var5 = true;
/* 374:    */         }
/* 375:505 */         if ((var5) || (p_149805_1_.getBlock(p_149805_2_ - 1, p_149805_3_, p_149805_4_).getMaterial() == Material.water)) {
/* 376:507 */           var5 = true;
/* 377:    */         }
/* 378:510 */         if ((var5) || (p_149805_1_.getBlock(p_149805_2_ + 1, p_149805_3_, p_149805_4_).getMaterial() == Material.water)) {
/* 379:512 */           var5 = true;
/* 380:    */         }
/* 381:515 */         if ((var5) || (p_149805_1_.getBlock(p_149805_2_, p_149805_3_ + 1, p_149805_4_).getMaterial() == Material.water)) {
/* 382:517 */           var5 = true;
/* 383:    */         }
/* 384:520 */         if (var5)
/* 385:    */         {
/* 386:522 */           int var6 = p_149805_1_.getBlockMetadata(p_149805_2_, p_149805_3_, p_149805_4_);
/* 387:524 */           if (var6 == 0) {
/* 388:526 */             p_149805_1_.setBlock(p_149805_2_, p_149805_3_, p_149805_4_, Blocks.obsidian);
/* 389:528 */           } else if (var6 <= 4) {
/* 390:530 */             p_149805_1_.setBlock(p_149805_2_, p_149805_3_, p_149805_4_, Blocks.cobblestone);
/* 391:    */           }
/* 392:533 */           func_149799_m(p_149805_1_, p_149805_2_, p_149805_3_, p_149805_4_);
/* 393:    */         }
/* 394:    */       }
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:    */   protected void func_149799_m(World p_149799_1_, int p_149799_2_, int p_149799_3_, int p_149799_4_)
/* 399:    */   {
/* 400:541 */     p_149799_1_.playSoundEffect(p_149799_2_ + 0.5F, p_149799_3_ + 0.5F, p_149799_4_ + 0.5F, "random.fizz", 0.5F, 2.6F + (p_149799_1_.rand.nextFloat() - p_149799_1_.rand.nextFloat()) * 0.8F);
/* 401:543 */     for (int var5 = 0; var5 < 8; var5++) {
/* 402:545 */       p_149799_1_.spawnParticle("largesmoke", p_149799_2_ + Math.random(), p_149799_3_ + 1.2D, p_149799_4_ + Math.random(), 0.0D, 0.0D, 0.0D);
/* 403:    */     }
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 407:    */   {
/* 408:551 */     if (this.blockMaterial == Material.lava) {
/* 409:553 */       this.field_149806_a = new IIcon[] { p_149651_1_.registerIcon("lava_still"), p_149651_1_.registerIcon("lava_flow") };
/* 410:    */     } else {
/* 411:557 */       this.field_149806_a = new IIcon[] { p_149651_1_.registerIcon("water_still"), p_149651_1_.registerIcon("water_flow") };
/* 412:    */     }
/* 413:    */   }
/* 414:    */   
/* 415:    */   public static IIcon func_149803_e(String p_149803_0_)
/* 416:    */   {
/* 417:563 */     return p_149803_0_ == "lava_flow" ? Blocks.flowing_lava.field_149806_a[1] : p_149803_0_ == "lava_still" ? Blocks.flowing_lava.field_149806_a[0] : p_149803_0_ == "water_flow" ? Blocks.flowing_water.field_149806_a[1] : p_149803_0_ == "water_still" ? Blocks.flowing_water.field_149806_a[0] : null;
/* 418:    */   }
/* 419:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockLiquid
 * JD-Core Version:    0.7.0.1
 */