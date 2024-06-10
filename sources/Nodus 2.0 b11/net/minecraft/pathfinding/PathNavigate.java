/*   1:    */ package net.minecraft.pathfinding;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.EntityLiving;
/*   7:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   8:    */ import net.minecraft.entity.ai.EntityMoveHelper;
/*   9:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.util.MathHelper;
/*  13:    */ import net.minecraft.util.Vec3;
/*  14:    */ import net.minecraft.util.Vec3Pool;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class PathNavigate
/*  18:    */ {
/*  19:    */   private EntityLiving theEntity;
/*  20:    */   private World worldObj;
/*  21:    */   private PathEntity currentPath;
/*  22:    */   private double speed;
/*  23:    */   private IAttributeInstance pathSearchRange;
/*  24:    */   private boolean noSunPathfind;
/*  25:    */   private int totalTicks;
/*  26:    */   private int ticksAtLastPos;
/*  27: 40 */   private Vec3 lastPosCheck = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
/*  28: 45 */   private boolean canPassOpenWoodenDoors = true;
/*  29:    */   private boolean canPassClosedWoodenDoors;
/*  30:    */   private boolean avoidsWater;
/*  31:    */   private boolean canSwim;
/*  32:    */   private static final String __OBFID = "CL_00001627";
/*  33:    */   
/*  34:    */   public PathNavigate(EntityLiving par1EntityLiving, World par2World)
/*  35:    */   {
/*  36: 62 */     this.theEntity = par1EntityLiving;
/*  37: 63 */     this.worldObj = par2World;
/*  38: 64 */     this.pathSearchRange = par1EntityLiving.getEntityAttribute(SharedMonsterAttributes.followRange);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setAvoidsWater(boolean par1)
/*  42:    */   {
/*  43: 69 */     this.avoidsWater = par1;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean getAvoidsWater()
/*  47:    */   {
/*  48: 74 */     return this.avoidsWater;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setBreakDoors(boolean par1)
/*  52:    */   {
/*  53: 79 */     this.canPassClosedWoodenDoors = par1;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setEnterDoors(boolean par1)
/*  57:    */   {
/*  58: 87 */     this.canPassOpenWoodenDoors = par1;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean getCanBreakDoors()
/*  62:    */   {
/*  63: 95 */     return this.canPassClosedWoodenDoors;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setAvoidSun(boolean par1)
/*  67:    */   {
/*  68:103 */     this.noSunPathfind = par1;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setSpeed(double par1)
/*  72:    */   {
/*  73:111 */     this.speed = par1;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setCanSwim(boolean par1)
/*  77:    */   {
/*  78:119 */     this.canSwim = par1;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public float func_111269_d()
/*  82:    */   {
/*  83:124 */     return (float)this.pathSearchRange.getAttributeValue();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public PathEntity getPathToXYZ(double par1, double par3, double par5)
/*  87:    */   {
/*  88:132 */     return !canNavigate() ? null : this.worldObj.getEntityPathToXYZ(this.theEntity, MathHelper.floor_double(par1), (int)par3, MathHelper.floor_double(par5), func_111269_d(), this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean tryMoveToXYZ(double par1, double par3, double par5, double par7)
/*  92:    */   {
/*  93:140 */     PathEntity var9 = getPathToXYZ(MathHelper.floor_double(par1), (int)par3, MathHelper.floor_double(par5));
/*  94:141 */     return setPath(var9, par7);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public PathEntity getPathToEntityLiving(Entity par1Entity)
/*  98:    */   {
/*  99:149 */     return !canNavigate() ? null : this.worldObj.getPathEntityToEntity(this.theEntity, par1Entity, func_111269_d(), this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean tryMoveToEntityLiving(Entity par1Entity, double par2)
/* 103:    */   {
/* 104:157 */     PathEntity var4 = getPathToEntityLiving(par1Entity);
/* 105:158 */     return var4 != null ? setPath(var4, par2) : false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean setPath(PathEntity par1PathEntity, double par2)
/* 109:    */   {
/* 110:167 */     if (par1PathEntity == null)
/* 111:    */     {
/* 112:169 */       this.currentPath = null;
/* 113:170 */       return false;
/* 114:    */     }
/* 115:174 */     if (!par1PathEntity.isSamePath(this.currentPath)) {
/* 116:176 */       this.currentPath = par1PathEntity;
/* 117:    */     }
/* 118:179 */     if (this.noSunPathfind) {
/* 119:181 */       removeSunnyPath();
/* 120:    */     }
/* 121:184 */     if (this.currentPath.getCurrentPathLength() == 0) {
/* 122:186 */       return false;
/* 123:    */     }
/* 124:190 */     this.speed = par2;
/* 125:191 */     Vec3 var4 = getEntityPosition();
/* 126:192 */     this.ticksAtLastPos = this.totalTicks;
/* 127:193 */     this.lastPosCheck.xCoord = var4.xCoord;
/* 128:194 */     this.lastPosCheck.yCoord = var4.yCoord;
/* 129:195 */     this.lastPosCheck.zCoord = var4.zCoord;
/* 130:196 */     return true;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public PathEntity getPath()
/* 134:    */   {
/* 135:206 */     return this.currentPath;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void onUpdateNavigation()
/* 139:    */   {
/* 140:211 */     this.totalTicks += 1;
/* 141:213 */     if (!noPath())
/* 142:    */     {
/* 143:215 */       if (canNavigate()) {
/* 144:217 */         pathFollow();
/* 145:    */       }
/* 146:220 */       if (!noPath())
/* 147:    */       {
/* 148:222 */         Vec3 var1 = this.currentPath.getPosition(this.theEntity);
/* 149:224 */         if (var1 != null) {
/* 150:226 */           this.theEntity.getMoveHelper().setMoveTo(var1.xCoord, var1.yCoord, var1.zCoord, this.speed);
/* 151:    */         }
/* 152:    */       }
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void pathFollow()
/* 157:    */   {
/* 158:234 */     Vec3 var1 = getEntityPosition();
/* 159:235 */     int var2 = this.currentPath.getCurrentPathLength();
/* 160:237 */     for (int var3 = this.currentPath.getCurrentPathIndex(); var3 < this.currentPath.getCurrentPathLength(); var3++) {
/* 161:239 */       if (this.currentPath.getPathPointFromIndex(var3).yCoord != (int)var1.yCoord)
/* 162:    */       {
/* 163:241 */         var2 = var3;
/* 164:242 */         break;
/* 165:    */       }
/* 166:    */     }
/* 167:246 */     float var8 = this.theEntity.width * this.theEntity.width;
/* 168:249 */     for (int var4 = this.currentPath.getCurrentPathIndex(); var4 < var2; var4++) {
/* 169:251 */       if (var1.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, var4)) < var8) {
/* 170:253 */         this.currentPath.setCurrentPathIndex(var4 + 1);
/* 171:    */       }
/* 172:    */     }
/* 173:257 */     var4 = MathHelper.ceiling_float_int(this.theEntity.width);
/* 174:258 */     int var5 = (int)this.theEntity.height + 1;
/* 175:259 */     int var6 = var4;
/* 176:261 */     for (int var7 = var2 - 1; var7 >= this.currentPath.getCurrentPathIndex(); var7--) {
/* 177:263 */       if (isDirectPathBetweenPoints(var1, this.currentPath.getVectorFromIndex(this.theEntity, var7), var4, var5, var6))
/* 178:    */       {
/* 179:265 */         this.currentPath.setCurrentPathIndex(var7);
/* 180:266 */         break;
/* 181:    */       }
/* 182:    */     }
/* 183:270 */     if (this.totalTicks - this.ticksAtLastPos > 100)
/* 184:    */     {
/* 185:272 */       if (var1.squareDistanceTo(this.lastPosCheck) < 2.25D) {
/* 186:274 */         clearPathEntity();
/* 187:    */       }
/* 188:277 */       this.ticksAtLastPos = this.totalTicks;
/* 189:278 */       this.lastPosCheck.xCoord = var1.xCoord;
/* 190:279 */       this.lastPosCheck.yCoord = var1.yCoord;
/* 191:280 */       this.lastPosCheck.zCoord = var1.zCoord;
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean noPath()
/* 196:    */   {
/* 197:289 */     return (this.currentPath == null) || (this.currentPath.isFinished());
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void clearPathEntity()
/* 201:    */   {
/* 202:297 */     this.currentPath = null;
/* 203:    */   }
/* 204:    */   
/* 205:    */   private Vec3 getEntityPosition()
/* 206:    */   {
/* 207:302 */     return this.worldObj.getWorldVec3Pool().getVecFromPool(this.theEntity.posX, getPathableYPos(), this.theEntity.posZ);
/* 208:    */   }
/* 209:    */   
/* 210:    */   private int getPathableYPos()
/* 211:    */   {
/* 212:310 */     if ((this.theEntity.isInWater()) && (this.canSwim))
/* 213:    */     {
/* 214:312 */       int var1 = (int)this.theEntity.boundingBox.minY;
/* 215:313 */       Block var2 = this.worldObj.getBlock(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
/* 216:314 */       int var3 = 0;
/* 217:    */       do
/* 218:    */       {
/* 219:318 */         if ((var2 != Blocks.flowing_water) && (var2 != Blocks.water)) {
/* 220:320 */           return var1;
/* 221:    */         }
/* 222:323 */         var1++;
/* 223:324 */         var2 = this.worldObj.getBlock(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
/* 224:325 */         var3++;
/* 225:316 */       } while (
/* 226:    */       
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:327 */         var3 <= 16);
/* 237:329 */       return (int)this.theEntity.boundingBox.minY;
/* 238:    */     }
/* 239:333 */     return (int)(this.theEntity.boundingBox.minY + 0.5D);
/* 240:    */   }
/* 241:    */   
/* 242:    */   private boolean canNavigate()
/* 243:    */   {
/* 244:342 */     return (this.theEntity.onGround) || ((this.canSwim) && (isInFluid()));
/* 245:    */   }
/* 246:    */   
/* 247:    */   private boolean isInFluid()
/* 248:    */   {
/* 249:350 */     return (this.theEntity.isInWater()) || (this.theEntity.handleLavaMovement());
/* 250:    */   }
/* 251:    */   
/* 252:    */   private void removeSunnyPath()
/* 253:    */   {
/* 254:358 */     if (!this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.boundingBox.minY + 0.5D), MathHelper.floor_double(this.theEntity.posZ))) {
/* 255:360 */       for (int var1 = 0; var1 < this.currentPath.getCurrentPathLength(); var1++)
/* 256:    */       {
/* 257:362 */         PathPoint var2 = this.currentPath.getPathPointFromIndex(var1);
/* 258:364 */         if (this.worldObj.canBlockSeeTheSky(var2.xCoord, var2.yCoord, var2.zCoord))
/* 259:    */         {
/* 260:366 */           this.currentPath.setCurrentPathLength(var1 - 1);
/* 261:367 */           return;
/* 262:    */         }
/* 263:    */       }
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   private boolean isDirectPathBetweenPoints(Vec3 par1Vec3, Vec3 par2Vec3, int par3, int par4, int par5)
/* 268:    */   {
/* 269:379 */     int var6 = MathHelper.floor_double(par1Vec3.xCoord);
/* 270:380 */     int var7 = MathHelper.floor_double(par1Vec3.zCoord);
/* 271:381 */     double var8 = par2Vec3.xCoord - par1Vec3.xCoord;
/* 272:382 */     double var10 = par2Vec3.zCoord - par1Vec3.zCoord;
/* 273:383 */     double var12 = var8 * var8 + var10 * var10;
/* 274:385 */     if (var12 < 1.0E-008D) {
/* 275:387 */       return false;
/* 276:    */     }
/* 277:391 */     double var14 = 1.0D / Math.sqrt(var12);
/* 278:392 */     var8 *= var14;
/* 279:393 */     var10 *= var14;
/* 280:394 */     par3 += 2;
/* 281:395 */     par5 += 2;
/* 282:397 */     if (!isSafeToStandAt(var6, (int)par1Vec3.yCoord, var7, par3, par4, par5, par1Vec3, var8, var10)) {
/* 283:399 */       return false;
/* 284:    */     }
/* 285:403 */     par3 -= 2;
/* 286:404 */     par5 -= 2;
/* 287:405 */     double var16 = 1.0D / Math.abs(var8);
/* 288:406 */     double var18 = 1.0D / Math.abs(var10);
/* 289:407 */     double var20 = var6 * 1 - par1Vec3.xCoord;
/* 290:408 */     double var22 = var7 * 1 - par1Vec3.zCoord;
/* 291:410 */     if (var8 >= 0.0D) {
/* 292:412 */       var20 += 1.0D;
/* 293:    */     }
/* 294:415 */     if (var10 >= 0.0D) {
/* 295:417 */       var22 += 1.0D;
/* 296:    */     }
/* 297:420 */     var20 /= var8;
/* 298:421 */     var22 /= var10;
/* 299:422 */     int var24 = var8 < 0.0D ? -1 : 1;
/* 300:423 */     int var25 = var10 < 0.0D ? -1 : 1;
/* 301:424 */     int var26 = MathHelper.floor_double(par2Vec3.xCoord);
/* 302:425 */     int var27 = MathHelper.floor_double(par2Vec3.zCoord);
/* 303:426 */     int var28 = var26 - var6;
/* 304:427 */     int var29 = var27 - var7;
/* 305:    */     do
/* 306:    */     {
/* 307:431 */       if ((var28 * var24 <= 0) && (var29 * var25 <= 0)) {
/* 308:433 */         return true;
/* 309:    */       }
/* 310:436 */       if (var20 < var22)
/* 311:    */       {
/* 312:438 */         var20 += var16;
/* 313:439 */         var6 += var24;
/* 314:440 */         var28 = var26 - var6;
/* 315:    */       }
/* 316:    */       else
/* 317:    */       {
/* 318:444 */         var22 += var18;
/* 319:445 */         var7 += var25;
/* 320:446 */         var29 = var27 - var7;
/* 321:    */       }
/* 322:449 */     } while (isSafeToStandAt(var6, (int)par1Vec3.yCoord, var7, par3, par4, par5, par1Vec3, var8, var10));
/* 323:451 */     return false;
/* 324:    */   }
/* 325:    */   
/* 326:    */   private boolean isSafeToStandAt(int par1, int par2, int par3, int par4, int par5, int par6, Vec3 par7Vec3, double par8, double par10)
/* 327:    */   {
/* 328:462 */     int var12 = par1 - par4 / 2;
/* 329:463 */     int var13 = par3 - par6 / 2;
/* 330:465 */     if (!isPositionClear(var12, par2, var13, par4, par5, par6, par7Vec3, par8, par10)) {
/* 331:467 */       return false;
/* 332:    */     }
/* 333:471 */     for (int var14 = var12; var14 < var12 + par4; var14++) {
/* 334:473 */       for (int var15 = var13; var15 < var13 + par6; var15++)
/* 335:    */       {
/* 336:475 */         double var16 = var14 + 0.5D - par7Vec3.xCoord;
/* 337:476 */         double var18 = var15 + 0.5D - par7Vec3.zCoord;
/* 338:478 */         if (var16 * par8 + var18 * par10 >= 0.0D)
/* 339:    */         {
/* 340:480 */           Block var20 = this.worldObj.getBlock(var14, par2 - 1, var15);
/* 341:481 */           Material var21 = var20.getMaterial();
/* 342:483 */           if (var21 == Material.air) {
/* 343:485 */             return false;
/* 344:    */           }
/* 345:488 */           if ((var21 == Material.water) && (!this.theEntity.isInWater())) {
/* 346:490 */             return false;
/* 347:    */           }
/* 348:493 */           if (var21 == Material.lava) {
/* 349:495 */             return false;
/* 350:    */           }
/* 351:    */         }
/* 352:    */       }
/* 353:    */     }
/* 354:501 */     return true;
/* 355:    */   }
/* 356:    */   
/* 357:    */   private boolean isPositionClear(int par1, int par2, int par3, int par4, int par5, int par6, Vec3 par7Vec3, double par8, double par10)
/* 358:    */   {
/* 359:511 */     for (int var12 = par1; var12 < par1 + par4; var12++) {
/* 360:513 */       for (int var13 = par2; var13 < par2 + par5; var13++) {
/* 361:515 */         for (int var14 = par3; var14 < par3 + par6; var14++)
/* 362:    */         {
/* 363:517 */           double var15 = var12 + 0.5D - par7Vec3.xCoord;
/* 364:518 */           double var17 = var14 + 0.5D - par7Vec3.zCoord;
/* 365:520 */           if (var15 * par8 + var17 * par10 >= 0.0D)
/* 366:    */           {
/* 367:522 */             Block var19 = this.worldObj.getBlock(var12, var13, var14);
/* 368:524 */             if (!var19.getBlocksMovement(this.worldObj, var12, var13, var14)) {
/* 369:526 */               return false;
/* 370:    */             }
/* 371:    */           }
/* 372:    */         }
/* 373:    */       }
/* 374:    */     }
/* 375:533 */     return true;
/* 376:    */   }
/* 377:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.pathfinding.PathNavigate
 * JD-Core Version:    0.7.0.1
 */