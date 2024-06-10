/*   1:    */ package net.minecraft.world.gen.structure;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.BlockDirectional;
/*   8:    */ import net.minecraft.block.material.Material;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.item.ItemDoor;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.tileentity.TileEntityChest;
/*  13:    */ import net.minecraft.tileentity.TileEntityDispenser;
/*  14:    */ import net.minecraft.util.WeightedRandomChestContent;
/*  15:    */ import net.minecraft.world.ChunkPosition;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public abstract class StructureComponent
/*  19:    */ {
/*  20:    */   protected StructureBoundingBox boundingBox;
/*  21:    */   protected int coordBaseMode;
/*  22:    */   protected int componentType;
/*  23:    */   private static final String __OBFID = "CL_00000511";
/*  24:    */   
/*  25:    */   public StructureComponent() {}
/*  26:    */   
/*  27:    */   protected StructureComponent(int par1)
/*  28:    */   {
/*  29: 35 */     this.componentType = par1;
/*  30: 36 */     this.coordBaseMode = -1;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public NBTTagCompound func_143010_b()
/*  34:    */   {
/*  35: 41 */     NBTTagCompound var1 = new NBTTagCompound();
/*  36: 42 */     var1.setString("id", MapGenStructureIO.func_143036_a(this));
/*  37: 43 */     var1.setTag("BB", this.boundingBox.func_151535_h());
/*  38: 44 */     var1.setInteger("O", this.coordBaseMode);
/*  39: 45 */     var1.setInteger("GD", this.componentType);
/*  40: 46 */     func_143012_a(var1);
/*  41: 47 */     return var1;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected abstract void func_143012_a(NBTTagCompound paramNBTTagCompound);
/*  45:    */   
/*  46:    */   public void func_143009_a(World par1World, NBTTagCompound par2NBTTagCompound)
/*  47:    */   {
/*  48: 54 */     if (par2NBTTagCompound.hasKey("BB")) {
/*  49: 56 */       this.boundingBox = new StructureBoundingBox(par2NBTTagCompound.getIntArray("BB"));
/*  50:    */     }
/*  51: 59 */     this.coordBaseMode = par2NBTTagCompound.getInteger("O");
/*  52: 60 */     this.componentType = par2NBTTagCompound.getInteger("GD");
/*  53: 61 */     func_143011_b(par2NBTTagCompound);
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected abstract void func_143011_b(NBTTagCompound paramNBTTagCompound);
/*  57:    */   
/*  58:    */   public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random) {}
/*  59:    */   
/*  60:    */   public abstract boolean addComponentParts(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox);
/*  61:    */   
/*  62:    */   public StructureBoundingBox getBoundingBox()
/*  63:    */   {
/*  64: 79 */     return this.boundingBox;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getComponentType()
/*  68:    */   {
/*  69: 87 */     return this.componentType;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static StructureComponent findIntersecting(List par0List, StructureBoundingBox par1StructureBoundingBox)
/*  73:    */   {
/*  74: 95 */     Iterator var2 = par0List.iterator();
/*  75:    */     StructureComponent var3;
/*  76:    */     do
/*  77:    */     {
/*  78:100 */       if (!var2.hasNext()) {
/*  79:102 */         return null;
/*  80:    */       }
/*  81:105 */       var3 = (StructureComponent)var2.next();
/*  82:107 */     } while ((var3.getBoundingBox() == null) || (!var3.getBoundingBox().intersectsWith(par1StructureBoundingBox)));
/*  83:109 */     return var3;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public ChunkPosition func_151553_a()
/*  87:    */   {
/*  88:114 */     return new ChunkPosition(this.boundingBox.getCenterX(), this.boundingBox.getCenterY(), this.boundingBox.getCenterZ());
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected boolean isLiquidInStructureBoundingBox(World par1World, StructureBoundingBox par2StructureBoundingBox)
/*  92:    */   {
/*  93:122 */     int var3 = Math.max(this.boundingBox.minX - 1, par2StructureBoundingBox.minX);
/*  94:123 */     int var4 = Math.max(this.boundingBox.minY - 1, par2StructureBoundingBox.minY);
/*  95:124 */     int var5 = Math.max(this.boundingBox.minZ - 1, par2StructureBoundingBox.minZ);
/*  96:125 */     int var6 = Math.min(this.boundingBox.maxX + 1, par2StructureBoundingBox.maxX);
/*  97:126 */     int var7 = Math.min(this.boundingBox.maxY + 1, par2StructureBoundingBox.maxY);
/*  98:127 */     int var8 = Math.min(this.boundingBox.maxZ + 1, par2StructureBoundingBox.maxZ);
/*  99:131 */     for (int var9 = var3; var9 <= var6; var9++) {
/* 100:133 */       for (int var10 = var5; var10 <= var8; var10++)
/* 101:    */       {
/* 102:135 */         if (par1World.getBlock(var9, var4, var10).getMaterial().isLiquid()) {
/* 103:137 */           return true;
/* 104:    */         }
/* 105:140 */         if (par1World.getBlock(var9, var7, var10).getMaterial().isLiquid()) {
/* 106:142 */           return true;
/* 107:    */         }
/* 108:    */       }
/* 109:    */     }
/* 110:147 */     for (var9 = var3; var9 <= var6; var9++) {
/* 111:149 */       for (int var10 = var4; var10 <= var7; var10++)
/* 112:    */       {
/* 113:151 */         if (par1World.getBlock(var9, var10, var5).getMaterial().isLiquid()) {
/* 114:153 */           return true;
/* 115:    */         }
/* 116:156 */         if (par1World.getBlock(var9, var10, var8).getMaterial().isLiquid()) {
/* 117:158 */           return true;
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:163 */     for (var9 = var5; var9 <= var8; var9++) {
/* 122:165 */       for (int var10 = var4; var10 <= var7; var10++)
/* 123:    */       {
/* 124:167 */         if (par1World.getBlock(var3, var10, var9).getMaterial().isLiquid()) {
/* 125:169 */           return true;
/* 126:    */         }
/* 127:172 */         if (par1World.getBlock(var6, var10, var9).getMaterial().isLiquid()) {
/* 128:174 */           return true;
/* 129:    */         }
/* 130:    */       }
/* 131:    */     }
/* 132:179 */     return false;
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected int getXWithOffset(int par1, int par2)
/* 136:    */   {
/* 137:184 */     switch (this.coordBaseMode)
/* 138:    */     {
/* 139:    */     case 0: 
/* 140:    */     case 2: 
/* 141:188 */       return this.boundingBox.minX + par1;
/* 142:    */     case 1: 
/* 143:191 */       return this.boundingBox.maxX - par2;
/* 144:    */     case 3: 
/* 145:194 */       return this.boundingBox.minX + par2;
/* 146:    */     }
/* 147:197 */     return par1;
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected int getYWithOffset(int par1)
/* 151:    */   {
/* 152:203 */     return this.coordBaseMode == -1 ? par1 : par1 + this.boundingBox.minY;
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected int getZWithOffset(int par1, int par2)
/* 156:    */   {
/* 157:208 */     switch (this.coordBaseMode)
/* 158:    */     {
/* 159:    */     case 0: 
/* 160:211 */       return this.boundingBox.minZ + par2;
/* 161:    */     case 1: 
/* 162:    */     case 3: 
/* 163:215 */       return this.boundingBox.minZ + par1;
/* 164:    */     case 2: 
/* 165:218 */       return this.boundingBox.maxZ - par2;
/* 166:    */     }
/* 167:221 */     return par2;
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected int func_151555_a(Block p_151555_1_, int p_151555_2_)
/* 171:    */   {
/* 172:227 */     if (p_151555_1_ == Blocks.rail)
/* 173:    */     {
/* 174:229 */       if ((this.coordBaseMode == 1) || (this.coordBaseMode == 3))
/* 175:    */       {
/* 176:231 */         if (p_151555_2_ == 1) {
/* 177:233 */           return 0;
/* 178:    */         }
/* 179:236 */         return 1;
/* 180:    */       }
/* 181:    */     }
/* 182:239 */     else if ((p_151555_1_ != Blocks.wooden_door) && (p_151555_1_ != Blocks.iron_door))
/* 183:    */     {
/* 184:241 */       if ((p_151555_1_ != Blocks.stone_stairs) && (p_151555_1_ != Blocks.oak_stairs) && (p_151555_1_ != Blocks.nether_brick_stairs) && (p_151555_1_ != Blocks.stone_brick_stairs) && (p_151555_1_ != Blocks.sandstone_stairs))
/* 185:    */       {
/* 186:243 */         if (p_151555_1_ == Blocks.ladder)
/* 187:    */         {
/* 188:245 */           if (this.coordBaseMode == 0)
/* 189:    */           {
/* 190:247 */             if (p_151555_2_ == 2) {
/* 191:249 */               return 3;
/* 192:    */             }
/* 193:252 */             if (p_151555_2_ == 3) {
/* 194:254 */               return 2;
/* 195:    */             }
/* 196:    */           }
/* 197:257 */           else if (this.coordBaseMode == 1)
/* 198:    */           {
/* 199:259 */             if (p_151555_2_ == 2) {
/* 200:261 */               return 4;
/* 201:    */             }
/* 202:264 */             if (p_151555_2_ == 3) {
/* 203:266 */               return 5;
/* 204:    */             }
/* 205:269 */             if (p_151555_2_ == 4) {
/* 206:271 */               return 2;
/* 207:    */             }
/* 208:274 */             if (p_151555_2_ == 5) {
/* 209:276 */               return 3;
/* 210:    */             }
/* 211:    */           }
/* 212:279 */           else if (this.coordBaseMode == 3)
/* 213:    */           {
/* 214:281 */             if (p_151555_2_ == 2) {
/* 215:283 */               return 5;
/* 216:    */             }
/* 217:286 */             if (p_151555_2_ == 3) {
/* 218:288 */               return 4;
/* 219:    */             }
/* 220:291 */             if (p_151555_2_ == 4) {
/* 221:293 */               return 2;
/* 222:    */             }
/* 223:296 */             if (p_151555_2_ == 5) {
/* 224:298 */               return 3;
/* 225:    */             }
/* 226:    */           }
/* 227:    */         }
/* 228:302 */         else if (p_151555_1_ == Blocks.stone_button)
/* 229:    */         {
/* 230:304 */           if (this.coordBaseMode == 0)
/* 231:    */           {
/* 232:306 */             if (p_151555_2_ == 3) {
/* 233:308 */               return 4;
/* 234:    */             }
/* 235:311 */             if (p_151555_2_ == 4) {
/* 236:313 */               return 3;
/* 237:    */             }
/* 238:    */           }
/* 239:316 */           else if (this.coordBaseMode == 1)
/* 240:    */           {
/* 241:318 */             if (p_151555_2_ == 3) {
/* 242:320 */               return 1;
/* 243:    */             }
/* 244:323 */             if (p_151555_2_ == 4) {
/* 245:325 */               return 2;
/* 246:    */             }
/* 247:328 */             if (p_151555_2_ == 2) {
/* 248:330 */               return 3;
/* 249:    */             }
/* 250:333 */             if (p_151555_2_ == 1) {
/* 251:335 */               return 4;
/* 252:    */             }
/* 253:    */           }
/* 254:338 */           else if (this.coordBaseMode == 3)
/* 255:    */           {
/* 256:340 */             if (p_151555_2_ == 3) {
/* 257:342 */               return 2;
/* 258:    */             }
/* 259:345 */             if (p_151555_2_ == 4) {
/* 260:347 */               return 1;
/* 261:    */             }
/* 262:350 */             if (p_151555_2_ == 2) {
/* 263:352 */               return 3;
/* 264:    */             }
/* 265:355 */             if (p_151555_2_ == 1) {
/* 266:357 */               return 4;
/* 267:    */             }
/* 268:    */           }
/* 269:    */         }
/* 270:361 */         else if ((p_151555_1_ != Blocks.tripwire_hook) && (!(p_151555_1_ instanceof BlockDirectional)))
/* 271:    */         {
/* 272:363 */           if ((p_151555_1_ == Blocks.piston) || (p_151555_1_ == Blocks.sticky_piston) || (p_151555_1_ == Blocks.lever) || (p_151555_1_ == Blocks.dispenser)) {
/* 273:365 */             if (this.coordBaseMode == 0)
/* 274:    */             {
/* 275:367 */               if ((p_151555_2_ == 2) || (p_151555_2_ == 3)) {
/* 276:369 */                 return net.minecraft.util.Facing.oppositeSide[p_151555_2_];
/* 277:    */               }
/* 278:    */             }
/* 279:372 */             else if (this.coordBaseMode == 1)
/* 280:    */             {
/* 281:374 */               if (p_151555_2_ == 2) {
/* 282:376 */                 return 4;
/* 283:    */               }
/* 284:379 */               if (p_151555_2_ == 3) {
/* 285:381 */                 return 5;
/* 286:    */               }
/* 287:384 */               if (p_151555_2_ == 4) {
/* 288:386 */                 return 2;
/* 289:    */               }
/* 290:389 */               if (p_151555_2_ == 5) {
/* 291:391 */                 return 3;
/* 292:    */               }
/* 293:    */             }
/* 294:394 */             else if (this.coordBaseMode == 3)
/* 295:    */             {
/* 296:396 */               if (p_151555_2_ == 2) {
/* 297:398 */                 return 5;
/* 298:    */               }
/* 299:401 */               if (p_151555_2_ == 3) {
/* 300:403 */                 return 4;
/* 301:    */               }
/* 302:406 */               if (p_151555_2_ == 4) {
/* 303:408 */                 return 2;
/* 304:    */               }
/* 305:411 */               if (p_151555_2_ == 5) {
/* 306:413 */                 return 3;
/* 307:    */               }
/* 308:    */             }
/* 309:    */           }
/* 310:    */         }
/* 311:418 */         else if (this.coordBaseMode == 0)
/* 312:    */         {
/* 313:420 */           if ((p_151555_2_ == 0) || (p_151555_2_ == 2)) {
/* 314:422 */             return net.minecraft.util.Direction.rotateOpposite[p_151555_2_];
/* 315:    */           }
/* 316:    */         }
/* 317:425 */         else if (this.coordBaseMode == 1)
/* 318:    */         {
/* 319:427 */           if (p_151555_2_ == 2) {
/* 320:429 */             return 1;
/* 321:    */           }
/* 322:432 */           if (p_151555_2_ == 0) {
/* 323:434 */             return 3;
/* 324:    */           }
/* 325:437 */           if (p_151555_2_ == 1) {
/* 326:439 */             return 2;
/* 327:    */           }
/* 328:442 */           if (p_151555_2_ == 3) {
/* 329:444 */             return 0;
/* 330:    */           }
/* 331:    */         }
/* 332:447 */         else if (this.coordBaseMode == 3)
/* 333:    */         {
/* 334:449 */           if (p_151555_2_ == 2) {
/* 335:451 */             return 3;
/* 336:    */           }
/* 337:454 */           if (p_151555_2_ == 0) {
/* 338:456 */             return 1;
/* 339:    */           }
/* 340:459 */           if (p_151555_2_ == 1) {
/* 341:461 */             return 2;
/* 342:    */           }
/* 343:464 */           if (p_151555_2_ == 3) {
/* 344:466 */             return 0;
/* 345:    */           }
/* 346:    */         }
/* 347:    */       }
/* 348:470 */       else if (this.coordBaseMode == 0)
/* 349:    */       {
/* 350:472 */         if (p_151555_2_ == 2) {
/* 351:474 */           return 3;
/* 352:    */         }
/* 353:477 */         if (p_151555_2_ == 3) {
/* 354:479 */           return 2;
/* 355:    */         }
/* 356:    */       }
/* 357:482 */       else if (this.coordBaseMode == 1)
/* 358:    */       {
/* 359:484 */         if (p_151555_2_ == 0) {
/* 360:486 */           return 2;
/* 361:    */         }
/* 362:489 */         if (p_151555_2_ == 1) {
/* 363:491 */           return 3;
/* 364:    */         }
/* 365:494 */         if (p_151555_2_ == 2) {
/* 366:496 */           return 0;
/* 367:    */         }
/* 368:499 */         if (p_151555_2_ == 3) {
/* 369:501 */           return 1;
/* 370:    */         }
/* 371:    */       }
/* 372:504 */       else if (this.coordBaseMode == 3)
/* 373:    */       {
/* 374:506 */         if (p_151555_2_ == 0) {
/* 375:508 */           return 2;
/* 376:    */         }
/* 377:511 */         if (p_151555_2_ == 1) {
/* 378:513 */           return 3;
/* 379:    */         }
/* 380:516 */         if (p_151555_2_ == 2) {
/* 381:518 */           return 1;
/* 382:    */         }
/* 383:521 */         if (p_151555_2_ == 3) {
/* 384:523 */           return 0;
/* 385:    */         }
/* 386:    */       }
/* 387:    */     }
/* 388:527 */     else if (this.coordBaseMode == 0)
/* 389:    */     {
/* 390:529 */       if (p_151555_2_ == 0) {
/* 391:531 */         return 2;
/* 392:    */       }
/* 393:534 */       if (p_151555_2_ == 2) {
/* 394:536 */         return 0;
/* 395:    */       }
/* 396:    */     }
/* 397:    */     else
/* 398:    */     {
/* 399:541 */       if (this.coordBaseMode == 1) {
/* 400:543 */         return p_151555_2_ + 1 & 0x3;
/* 401:    */       }
/* 402:546 */       if (this.coordBaseMode == 3) {
/* 403:548 */         return p_151555_2_ + 3 & 0x3;
/* 404:    */       }
/* 405:    */     }
/* 406:552 */     return p_151555_2_;
/* 407:    */   }
/* 408:    */   
/* 409:    */   protected void func_151550_a(World p_151550_1_, Block p_151550_2_, int p_151550_3_, int p_151550_4_, int p_151550_5_, int p_151550_6_, StructureBoundingBox p_151550_7_)
/* 410:    */   {
/* 411:557 */     int var8 = getXWithOffset(p_151550_4_, p_151550_6_);
/* 412:558 */     int var9 = getYWithOffset(p_151550_5_);
/* 413:559 */     int var10 = getZWithOffset(p_151550_4_, p_151550_6_);
/* 414:561 */     if (p_151550_7_.isVecInside(var8, var9, var10)) {
/* 415:563 */       p_151550_1_.setBlock(var8, var9, var10, p_151550_2_, p_151550_3_, 2);
/* 416:    */     }
/* 417:    */   }
/* 418:    */   
/* 419:    */   protected Block func_151548_a(World p_151548_1_, int p_151548_2_, int p_151548_3_, int p_151548_4_, StructureBoundingBox p_151548_5_)
/* 420:    */   {
/* 421:569 */     int var6 = getXWithOffset(p_151548_2_, p_151548_4_);
/* 422:570 */     int var7 = getYWithOffset(p_151548_3_);
/* 423:571 */     int var8 = getZWithOffset(p_151548_2_, p_151548_4_);
/* 424:572 */     return !p_151548_5_.isVecInside(var6, var7, var8) ? Blocks.air : p_151548_1_.getBlock(var6, var7, var8);
/* 425:    */   }
/* 426:    */   
/* 427:    */   protected void fillWithAir(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8)
/* 428:    */   {
/* 429:581 */     for (int var9 = par4; var9 <= par7; var9++) {
/* 430:583 */       for (int var10 = par3; var10 <= par6; var10++) {
/* 431:585 */         for (int var11 = par5; var11 <= par8; var11++) {
/* 432:587 */           func_151550_a(par1World, Blocks.air, 0, var10, var9, var11, par2StructureBoundingBox);
/* 433:    */         }
/* 434:    */       }
/* 435:    */     }
/* 436:    */   }
/* 437:    */   
/* 438:    */   protected void func_151549_a(World p_151549_1_, StructureBoundingBox p_151549_2_, int p_151549_3_, int p_151549_4_, int p_151549_5_, int p_151549_6_, int p_151549_7_, int p_151549_8_, Block p_151549_9_, Block p_151549_10_, boolean p_151549_11_)
/* 439:    */   {
/* 440:595 */     for (int var12 = p_151549_4_; var12 <= p_151549_7_; var12++) {
/* 441:597 */       for (int var13 = p_151549_3_; var13 <= p_151549_6_; var13++) {
/* 442:599 */         for (int var14 = p_151549_5_; var14 <= p_151549_8_; var14++) {
/* 443:601 */           if ((!p_151549_11_) || (func_151548_a(p_151549_1_, var13, var12, var14, p_151549_2_).getMaterial() != Material.air)) {
/* 444:603 */             if ((var12 != p_151549_4_) && (var12 != p_151549_7_) && (var13 != p_151549_3_) && (var13 != p_151549_6_) && (var14 != p_151549_5_) && (var14 != p_151549_8_)) {
/* 445:605 */               func_151550_a(p_151549_1_, p_151549_10_, 0, var13, var12, var14, p_151549_2_);
/* 446:    */             } else {
/* 447:609 */               func_151550_a(p_151549_1_, p_151549_9_, 0, var13, var12, var14, p_151549_2_);
/* 448:    */             }
/* 449:    */           }
/* 450:    */         }
/* 451:    */       }
/* 452:    */     }
/* 453:    */   }
/* 454:    */   
/* 455:    */   protected void func_151556_a(World p_151556_1_, StructureBoundingBox p_151556_2_, int p_151556_3_, int p_151556_4_, int p_151556_5_, int p_151556_6_, int p_151556_7_, int p_151556_8_, Block p_151556_9_, int p_151556_10_, Block p_151556_11_, int p_151556_12_, boolean p_151556_13_)
/* 456:    */   {
/* 457:619 */     for (int var14 = p_151556_4_; var14 <= p_151556_7_; var14++) {
/* 458:621 */       for (int var15 = p_151556_3_; var15 <= p_151556_6_; var15++) {
/* 459:623 */         for (int var16 = p_151556_5_; var16 <= p_151556_8_; var16++) {
/* 460:625 */           if ((!p_151556_13_) || (func_151548_a(p_151556_1_, var15, var14, var16, p_151556_2_).getMaterial() != Material.air)) {
/* 461:627 */             if ((var14 != p_151556_4_) && (var14 != p_151556_7_) && (var15 != p_151556_3_) && (var15 != p_151556_6_) && (var16 != p_151556_5_) && (var16 != p_151556_8_)) {
/* 462:629 */               func_151550_a(p_151556_1_, p_151556_11_, p_151556_12_, var15, var14, var16, p_151556_2_);
/* 463:    */             } else {
/* 464:633 */               func_151550_a(p_151556_1_, p_151556_9_, p_151556_10_, var15, var14, var16, p_151556_2_);
/* 465:    */             }
/* 466:    */           }
/* 467:    */         }
/* 468:    */       }
/* 469:    */     }
/* 470:    */   }
/* 471:    */   
/* 472:    */   protected void fillWithRandomizedBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, boolean par9, Random par10Random, BlockSelector par11StructurePieceBlockSelector)
/* 473:    */   {
/* 474:647 */     for (int var12 = par4; var12 <= par7; var12++) {
/* 475:649 */       for (int var13 = par3; var13 <= par6; var13++) {
/* 476:651 */         for (int var14 = par5; var14 <= par8; var14++) {
/* 477:653 */           if ((!par9) || (func_151548_a(par1World, var13, var12, var14, par2StructureBoundingBox).getMaterial() != Material.air))
/* 478:    */           {
/* 479:655 */             par11StructurePieceBlockSelector.selectBlocks(par10Random, var13, var12, var14, (var12 == par4) || (var12 == par7) || (var13 == par3) || (var13 == par6) || (var14 == par5) || (var14 == par8));
/* 480:656 */             func_151550_a(par1World, par11StructurePieceBlockSelector.func_151561_a(), par11StructurePieceBlockSelector.getSelectedBlockMetaData(), var13, var12, var14, par2StructureBoundingBox);
/* 481:    */           }
/* 482:    */         }
/* 483:    */       }
/* 484:    */     }
/* 485:    */   }
/* 486:    */   
/* 487:    */   protected void func_151551_a(World p_151551_1_, StructureBoundingBox p_151551_2_, Random p_151551_3_, float p_151551_4_, int p_151551_5_, int p_151551_6_, int p_151551_7_, int p_151551_8_, int p_151551_9_, int p_151551_10_, Block p_151551_11_, Block p_151551_12_, boolean p_151551_13_)
/* 488:    */   {
/* 489:665 */     for (int var14 = p_151551_6_; var14 <= p_151551_9_; var14++) {
/* 490:667 */       for (int var15 = p_151551_5_; var15 <= p_151551_8_; var15++) {
/* 491:669 */         for (int var16 = p_151551_7_; var16 <= p_151551_10_; var16++) {
/* 492:671 */           if ((p_151551_3_.nextFloat() <= p_151551_4_) && ((!p_151551_13_) || (func_151548_a(p_151551_1_, var15, var14, var16, p_151551_2_).getMaterial() != Material.air))) {
/* 493:673 */             if ((var14 != p_151551_6_) && (var14 != p_151551_9_) && (var15 != p_151551_5_) && (var15 != p_151551_8_) && (var16 != p_151551_7_) && (var16 != p_151551_10_)) {
/* 494:675 */               func_151550_a(p_151551_1_, p_151551_12_, 0, var15, var14, var16, p_151551_2_);
/* 495:    */             } else {
/* 496:679 */               func_151550_a(p_151551_1_, p_151551_11_, 0, var15, var14, var16, p_151551_2_);
/* 497:    */             }
/* 498:    */           }
/* 499:    */         }
/* 500:    */       }
/* 501:    */     }
/* 502:    */   }
/* 503:    */   
/* 504:    */   protected void func_151552_a(World p_151552_1_, StructureBoundingBox p_151552_2_, Random p_151552_3_, float p_151552_4_, int p_151552_5_, int p_151552_6_, int p_151552_7_, Block p_151552_8_, int p_151552_9_)
/* 505:    */   {
/* 506:689 */     if (p_151552_3_.nextFloat() < p_151552_4_) {
/* 507:691 */       func_151550_a(p_151552_1_, p_151552_8_, p_151552_9_, p_151552_5_, p_151552_6_, p_151552_7_, p_151552_2_);
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   protected void func_151547_a(World p_151547_1_, StructureBoundingBox p_151547_2_, int p_151547_3_, int p_151547_4_, int p_151547_5_, int p_151547_6_, int p_151547_7_, int p_151547_8_, Block p_151547_9_, boolean p_151547_10_)
/* 512:    */   {
/* 513:697 */     float var11 = p_151547_6_ - p_151547_3_ + 1;
/* 514:698 */     float var12 = p_151547_7_ - p_151547_4_ + 1;
/* 515:699 */     float var13 = p_151547_8_ - p_151547_5_ + 1;
/* 516:700 */     float var14 = p_151547_3_ + var11 / 2.0F;
/* 517:701 */     float var15 = p_151547_5_ + var13 / 2.0F;
/* 518:703 */     for (int var16 = p_151547_4_; var16 <= p_151547_7_; var16++)
/* 519:    */     {
/* 520:705 */       float var17 = (var16 - p_151547_4_) / var12;
/* 521:707 */       for (int var18 = p_151547_3_; var18 <= p_151547_6_; var18++)
/* 522:    */       {
/* 523:709 */         float var19 = (var18 - var14) / (var11 * 0.5F);
/* 524:711 */         for (int var20 = p_151547_5_; var20 <= p_151547_8_; var20++)
/* 525:    */         {
/* 526:713 */           float var21 = (var20 - var15) / (var13 * 0.5F);
/* 527:715 */           if ((!p_151547_10_) || (func_151548_a(p_151547_1_, var18, var16, var20, p_151547_2_).getMaterial() != Material.air))
/* 528:    */           {
/* 529:717 */             float var22 = var19 * var19 + var17 * var17 + var21 * var21;
/* 530:719 */             if (var22 <= 1.05F) {
/* 531:721 */               func_151550_a(p_151547_1_, p_151547_9_, 0, var18, var16, var20, p_151547_2_);
/* 532:    */             }
/* 533:    */           }
/* 534:    */         }
/* 535:    */       }
/* 536:    */     }
/* 537:    */   }
/* 538:    */   
/* 539:    */   protected void clearCurrentPositionBlocksUpwards(World par1World, int par2, int par3, int par4, StructureBoundingBox par5StructureBoundingBox)
/* 540:    */   {
/* 541:734 */     int var6 = getXWithOffset(par2, par4);
/* 542:735 */     int var7 = getYWithOffset(par3);
/* 543:736 */     int var8 = getZWithOffset(par2, par4);
/* 544:738 */     if (par5StructureBoundingBox.isVecInside(var6, var7, var8)) {
/* 545:740 */       while ((!par1World.isAirBlock(var6, var7, var8)) && (var7 < 255))
/* 546:    */       {
/* 547:742 */         par1World.setBlock(var6, var7, var8, Blocks.air, 0, 2);
/* 548:743 */         var7++;
/* 549:    */       }
/* 550:    */     }
/* 551:    */   }
/* 552:    */   
/* 553:    */   protected void func_151554_b(World p_151554_1_, Block p_151554_2_, int p_151554_3_, int p_151554_4_, int p_151554_5_, int p_151554_6_, StructureBoundingBox p_151554_7_)
/* 554:    */   {
/* 555:750 */     int var8 = getXWithOffset(p_151554_4_, p_151554_6_);
/* 556:751 */     int var9 = getYWithOffset(p_151554_5_);
/* 557:752 */     int var10 = getZWithOffset(p_151554_4_, p_151554_6_);
/* 558:754 */     if (p_151554_7_.isVecInside(var8, var9, var10)) {
/* 559:756 */       while (((p_151554_1_.isAirBlock(var8, var9, var10)) || (p_151554_1_.getBlock(var8, var9, var10).getMaterial().isLiquid())) && (var9 > 1))
/* 560:    */       {
/* 561:758 */         p_151554_1_.setBlock(var8, var9, var10, p_151554_2_, p_151554_3_, 2);
/* 562:759 */         var9--;
/* 563:    */       }
/* 564:    */     }
/* 565:    */   }
/* 566:    */   
/* 567:    */   protected boolean generateStructureChestContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, int par8)
/* 568:    */   {
/* 569:769 */     int var9 = getXWithOffset(par4, par6);
/* 570:770 */     int var10 = getYWithOffset(par5);
/* 571:771 */     int var11 = getZWithOffset(par4, par6);
/* 572:773 */     if ((par2StructureBoundingBox.isVecInside(var9, var10, var11)) && (par1World.getBlock(var9, var10, var11) != Blocks.chest))
/* 573:    */     {
/* 574:775 */       par1World.setBlock(var9, var10, var11, Blocks.chest, 0, 2);
/* 575:776 */       TileEntityChest var12 = (TileEntityChest)par1World.getTileEntity(var9, var10, var11);
/* 576:778 */       if (var12 != null) {
/* 577:780 */         WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, var12, par8);
/* 578:    */       }
/* 579:783 */       return true;
/* 580:    */     }
/* 581:787 */     return false;
/* 582:    */   }
/* 583:    */   
/* 584:    */   protected boolean generateStructureDispenserContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, int par7, WeightedRandomChestContent[] par8ArrayOfWeightedRandomChestContent, int par9)
/* 585:    */   {
/* 586:796 */     int var10 = getXWithOffset(par4, par6);
/* 587:797 */     int var11 = getYWithOffset(par5);
/* 588:798 */     int var12 = getZWithOffset(par4, par6);
/* 589:800 */     if ((par2StructureBoundingBox.isVecInside(var10, var11, var12)) && (par1World.getBlock(var10, var11, var12) != Blocks.dispenser))
/* 590:    */     {
/* 591:802 */       par1World.setBlock(var10, var11, var12, Blocks.dispenser, func_151555_a(Blocks.dispenser, par7), 2);
/* 592:803 */       TileEntityDispenser var13 = (TileEntityDispenser)par1World.getTileEntity(var10, var11, var12);
/* 593:805 */       if (var13 != null) {
/* 594:807 */         WeightedRandomChestContent.func_150706_a(par3Random, par8ArrayOfWeightedRandomChestContent, var13, par9);
/* 595:    */       }
/* 596:810 */       return true;
/* 597:    */     }
/* 598:814 */     return false;
/* 599:    */   }
/* 600:    */   
/* 601:    */   protected void placeDoorAtCurrentPosition(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, int par7)
/* 602:    */   {
/* 603:820 */     int var8 = getXWithOffset(par4, par6);
/* 604:821 */     int var9 = getYWithOffset(par5);
/* 605:822 */     int var10 = getZWithOffset(par4, par6);
/* 606:824 */     if (par2StructureBoundingBox.isVecInside(var8, var9, var10)) {
/* 607:826 */       ItemDoor.func_150924_a(par1World, var8, var9, var10, par7, Blocks.wooden_door);
/* 608:    */     }
/* 609:    */   }
/* 610:    */   
/* 611:    */   public static abstract class BlockSelector
/* 612:    */   {
/* 613:    */     protected Block field_151562_a;
/* 614:    */     protected int selectedBlockMetaData;
/* 615:    */     private static final String __OBFID = "CL_00000512";
/* 616:    */     
/* 617:    */     protected BlockSelector()
/* 618:    */     {
/* 619:838 */       this.field_151562_a = Blocks.air;
/* 620:    */     }
/* 621:    */     
/* 622:    */     public abstract void selectBlocks(Random paramRandom, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
/* 623:    */     
/* 624:    */     public Block func_151561_a()
/* 625:    */     {
/* 626:845 */       return this.field_151562_a;
/* 627:    */     }
/* 628:    */     
/* 629:    */     public int getSelectedBlockMetaData()
/* 630:    */     {
/* 631:850 */       return this.selectedBlockMetaData;
/* 632:    */     }
/* 633:    */   }
/* 634:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.StructureComponent
 * JD-Core Version:    0.7.0.1
 */