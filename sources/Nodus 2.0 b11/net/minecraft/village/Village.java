/*   1:    */ package net.minecraft.village;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Random;
/*   7:    */ import java.util.Set;
/*   8:    */ import java.util.TreeMap;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.entity.EntityLivingBase;
/*  11:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*  12:    */ import net.minecraft.entity.passive.EntityVillager;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.init.Blocks;
/*  15:    */ import net.minecraft.nbt.NBTTagCompound;
/*  16:    */ import net.minecraft.nbt.NBTTagList;
/*  17:    */ import net.minecraft.util.AABBPool;
/*  18:    */ import net.minecraft.util.AxisAlignedBB;
/*  19:    */ import net.minecraft.util.ChunkCoordinates;
/*  20:    */ import net.minecraft.util.MathHelper;
/*  21:    */ import net.minecraft.util.Vec3;
/*  22:    */ import net.minecraft.util.Vec3Pool;
/*  23:    */ import net.minecraft.world.World;
/*  24:    */ 
/*  25:    */ public class Village
/*  26:    */ {
/*  27:    */   private World worldObj;
/*  28: 25 */   private final List villageDoorInfoList = new ArrayList();
/*  29: 31 */   private final ChunkCoordinates centerHelper = new ChunkCoordinates(0, 0, 0);
/*  30: 34 */   private final ChunkCoordinates center = new ChunkCoordinates(0, 0, 0);
/*  31:    */   private int villageRadius;
/*  32:    */   private int lastAddDoorTimestamp;
/*  33:    */   private int tickCounter;
/*  34:    */   private int numVillagers;
/*  35:    */   private int noBreedTicks;
/*  36: 44 */   private TreeMap playerReputation = new TreeMap();
/*  37: 45 */   private List villageAgressors = new ArrayList();
/*  38:    */   private int numIronGolems;
/*  39:    */   private static final String __OBFID = "CL_00001631";
/*  40:    */   
/*  41:    */   public Village() {}
/*  42:    */   
/*  43:    */   public Village(World par1World)
/*  44:    */   {
/*  45: 53 */     this.worldObj = par1World;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void func_82691_a(World par1World)
/*  49:    */   {
/*  50: 58 */     this.worldObj = par1World;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void tick(int par1)
/*  54:    */   {
/*  55: 66 */     this.tickCounter = par1;
/*  56: 67 */     removeDeadAndOutOfRangeDoors();
/*  57: 68 */     removeDeadAndOldAgressors();
/*  58: 70 */     if (par1 % 20 == 0) {
/*  59: 72 */       updateNumVillagers();
/*  60:    */     }
/*  61: 75 */     if (par1 % 30 == 0) {
/*  62: 77 */       updateNumIronGolems();
/*  63:    */     }
/*  64: 80 */     int var2 = this.numVillagers / 10;
/*  65: 82 */     if ((this.numIronGolems < var2) && (this.villageDoorInfoList.size() > 20) && (this.worldObj.rand.nextInt(7000) == 0))
/*  66:    */     {
/*  67: 84 */       Vec3 var3 = tryGetIronGolemSpawningLocation(MathHelper.floor_float(this.center.posX), MathHelper.floor_float(this.center.posY), MathHelper.floor_float(this.center.posZ), 2, 4, 2);
/*  68: 86 */       if (var3 != null)
/*  69:    */       {
/*  70: 88 */         EntityIronGolem var4 = new EntityIronGolem(this.worldObj);
/*  71: 89 */         var4.setPosition(var3.xCoord, var3.yCoord, var3.zCoord);
/*  72: 90 */         this.worldObj.spawnEntityInWorld(var4);
/*  73: 91 */         this.numIronGolems += 1;
/*  74:    */       }
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   private Vec3 tryGetIronGolemSpawningLocation(int par1, int par2, int par3, int par4, int par5, int par6)
/*  79:    */   {
/*  80:101 */     for (int var7 = 0; var7 < 10; var7++)
/*  81:    */     {
/*  82:103 */       int var8 = par1 + this.worldObj.rand.nextInt(16) - 8;
/*  83:104 */       int var9 = par2 + this.worldObj.rand.nextInt(6) - 3;
/*  84:105 */       int var10 = par3 + this.worldObj.rand.nextInt(16) - 8;
/*  85:107 */       if ((isInRange(var8, var9, var10)) && (isValidIronGolemSpawningLocation(var8, var9, var10, par4, par5, par6))) {
/*  86:109 */         return this.worldObj.getWorldVec3Pool().getVecFromPool(var8, var9, var10);
/*  87:    */       }
/*  88:    */     }
/*  89:113 */     return null;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private boolean isValidIronGolemSpawningLocation(int par1, int par2, int par3, int par4, int par5, int par6)
/*  93:    */   {
/*  94:118 */     if (!World.doesBlockHaveSolidTopSurface(this.worldObj, par1, par2 - 1, par3)) {
/*  95:120 */       return false;
/*  96:    */     }
/*  97:124 */     int var7 = par1 - par4 / 2;
/*  98:125 */     int var8 = par3 - par6 / 2;
/*  99:127 */     for (int var9 = var7; var9 < var7 + par4; var9++) {
/* 100:129 */       for (int var10 = par2; var10 < par2 + par5; var10++) {
/* 101:131 */         for (int var11 = var8; var11 < var8 + par6; var11++) {
/* 102:133 */           if (this.worldObj.getBlock(var9, var10, var11).isNormalCube()) {
/* 103:135 */             return false;
/* 104:    */           }
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:141 */     return true;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void updateNumIronGolems()
/* 112:    */   {
/* 113:147 */     List var1 = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, AxisAlignedBB.getAABBPool().getAABB(this.center.posX - this.villageRadius, this.center.posY - 4, this.center.posZ - this.villageRadius, this.center.posX + this.villageRadius, this.center.posY + 4, this.center.posZ + this.villageRadius));
/* 114:148 */     this.numIronGolems = var1.size();
/* 115:    */   }
/* 116:    */   
/* 117:    */   private void updateNumVillagers()
/* 118:    */   {
/* 119:153 */     List var1 = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getAABBPool().getAABB(this.center.posX - this.villageRadius, this.center.posY - 4, this.center.posZ - this.villageRadius, this.center.posX + this.villageRadius, this.center.posY + 4, this.center.posZ + this.villageRadius));
/* 120:154 */     this.numVillagers = var1.size();
/* 121:156 */     if (this.numVillagers == 0) {
/* 122:158 */       this.playerReputation.clear();
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public ChunkCoordinates getCenter()
/* 127:    */   {
/* 128:164 */     return this.center;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getVillageRadius()
/* 132:    */   {
/* 133:169 */     return this.villageRadius;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getNumVillageDoors()
/* 137:    */   {
/* 138:178 */     return this.villageDoorInfoList.size();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getTicksSinceLastDoorAdding()
/* 142:    */   {
/* 143:183 */     return this.tickCounter - this.lastAddDoorTimestamp;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getNumVillagers()
/* 147:    */   {
/* 148:188 */     return this.numVillagers;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean isInRange(int par1, int par2, int par3)
/* 152:    */   {
/* 153:196 */     return this.center.getDistanceSquared(par1, par2, par3) < this.villageRadius * this.villageRadius;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public List getVillageDoorInfoList()
/* 157:    */   {
/* 158:204 */     return this.villageDoorInfoList;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public VillageDoorInfo findNearestDoor(int par1, int par2, int par3)
/* 162:    */   {
/* 163:209 */     VillageDoorInfo var4 = null;
/* 164:210 */     int var5 = 2147483647;
/* 165:211 */     Iterator var6 = this.villageDoorInfoList.iterator();
/* 166:213 */     while (var6.hasNext())
/* 167:    */     {
/* 168:215 */       VillageDoorInfo var7 = (VillageDoorInfo)var6.next();
/* 169:216 */       int var8 = var7.getDistanceSquared(par1, par2, par3);
/* 170:218 */       if (var8 < var5)
/* 171:    */       {
/* 172:220 */         var4 = var7;
/* 173:221 */         var5 = var8;
/* 174:    */       }
/* 175:    */     }
/* 176:225 */     return var4;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public VillageDoorInfo findNearestDoorUnrestricted(int par1, int par2, int par3)
/* 180:    */   {
/* 181:235 */     VillageDoorInfo var4 = null;
/* 182:236 */     int var5 = 2147483647;
/* 183:237 */     Iterator var6 = this.villageDoorInfoList.iterator();
/* 184:239 */     while (var6.hasNext())
/* 185:    */     {
/* 186:241 */       VillageDoorInfo var7 = (VillageDoorInfo)var6.next();
/* 187:242 */       int var8 = var7.getDistanceSquared(par1, par2, par3);
/* 188:244 */       if (var8 > 256) {
/* 189:246 */         var8 *= 1000;
/* 190:    */       } else {
/* 191:250 */         var8 = var7.getDoorOpeningRestrictionCounter();
/* 192:    */       }
/* 193:253 */       if (var8 < var5)
/* 194:    */       {
/* 195:255 */         var4 = var7;
/* 196:256 */         var5 = var8;
/* 197:    */       }
/* 198:    */     }
/* 199:260 */     return var4;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public VillageDoorInfo getVillageDoorAt(int par1, int par2, int par3)
/* 203:    */   {
/* 204:265 */     if (this.center.getDistanceSquared(par1, par2, par3) > this.villageRadius * this.villageRadius) {
/* 205:267 */       return null;
/* 206:    */     }
/* 207:271 */     Iterator var4 = this.villageDoorInfoList.iterator();
/* 208:    */     VillageDoorInfo var5;
/* 209:    */     do
/* 210:    */     {
/* 211:276 */       if (!var4.hasNext()) {
/* 212:278 */         return null;
/* 213:    */       }
/* 214:281 */       var5 = (VillageDoorInfo)var4.next();
/* 215:283 */     } while ((var5.posX != par1) || (var5.posZ != par3) || (Math.abs(var5.posY - par2) > 1));
/* 216:285 */     return var5;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void addVillageDoorInfo(VillageDoorInfo par1VillageDoorInfo)
/* 220:    */   {
/* 221:291 */     this.villageDoorInfoList.add(par1VillageDoorInfo);
/* 222:292 */     this.centerHelper.posX += par1VillageDoorInfo.posX;
/* 223:293 */     this.centerHelper.posY += par1VillageDoorInfo.posY;
/* 224:294 */     this.centerHelper.posZ += par1VillageDoorInfo.posZ;
/* 225:295 */     updateVillageRadiusAndCenter();
/* 226:296 */     this.lastAddDoorTimestamp = par1VillageDoorInfo.lastActivityTimestamp;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public boolean isAnnihilated()
/* 230:    */   {
/* 231:304 */     return this.villageDoorInfoList.isEmpty();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void addOrRenewAgressor(EntityLivingBase par1EntityLivingBase)
/* 235:    */   {
/* 236:309 */     Iterator var2 = this.villageAgressors.iterator();
/* 237:    */     VillageAgressor var3;
/* 238:    */     do
/* 239:    */     {
/* 240:314 */       if (!var2.hasNext())
/* 241:    */       {
/* 242:316 */         this.villageAgressors.add(new VillageAgressor(par1EntityLivingBase, this.tickCounter));
/* 243:317 */         return;
/* 244:    */       }
/* 245:320 */       var3 = (VillageAgressor)var2.next();
/* 246:322 */     } while (var3.agressor != par1EntityLivingBase);
/* 247:324 */     var3.agressionTime = this.tickCounter;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public EntityLivingBase findNearestVillageAggressor(EntityLivingBase par1EntityLivingBase)
/* 251:    */   {
/* 252:329 */     double var2 = 1.7976931348623157E+308D;
/* 253:330 */     VillageAgressor var4 = null;
/* 254:332 */     for (int var5 = 0; var5 < this.villageAgressors.size(); var5++)
/* 255:    */     {
/* 256:334 */       VillageAgressor var6 = (VillageAgressor)this.villageAgressors.get(var5);
/* 257:335 */       double var7 = var6.agressor.getDistanceSqToEntity(par1EntityLivingBase);
/* 258:337 */       if (var7 <= var2)
/* 259:    */       {
/* 260:339 */         var4 = var6;
/* 261:340 */         var2 = var7;
/* 262:    */       }
/* 263:    */     }
/* 264:344 */     return var4 != null ? var4.agressor : null;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public EntityPlayer func_82685_c(EntityLivingBase par1EntityLivingBase)
/* 268:    */   {
/* 269:349 */     double var2 = 1.7976931348623157E+308D;
/* 270:350 */     EntityPlayer var4 = null;
/* 271:351 */     Iterator var5 = this.playerReputation.keySet().iterator();
/* 272:353 */     while (var5.hasNext())
/* 273:    */     {
/* 274:355 */       String var6 = (String)var5.next();
/* 275:357 */       if (isPlayerReputationTooLow(var6))
/* 276:    */       {
/* 277:359 */         EntityPlayer var7 = this.worldObj.getPlayerEntityByName(var6);
/* 278:361 */         if (var7 != null)
/* 279:    */         {
/* 280:363 */           double var8 = var7.getDistanceSqToEntity(par1EntityLivingBase);
/* 281:365 */           if (var8 <= var2)
/* 282:    */           {
/* 283:367 */             var4 = var7;
/* 284:368 */             var2 = var8;
/* 285:    */           }
/* 286:    */         }
/* 287:    */       }
/* 288:    */     }
/* 289:374 */     return var4;
/* 290:    */   }
/* 291:    */   
/* 292:    */   private void removeDeadAndOldAgressors()
/* 293:    */   {
/* 294:379 */     Iterator var1 = this.villageAgressors.iterator();
/* 295:381 */     while (var1.hasNext())
/* 296:    */     {
/* 297:383 */       VillageAgressor var2 = (VillageAgressor)var1.next();
/* 298:385 */       if ((!var2.agressor.isEntityAlive()) || (Math.abs(this.tickCounter - var2.agressionTime) > 300)) {
/* 299:387 */         var1.remove();
/* 300:    */       }
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   private void removeDeadAndOutOfRangeDoors()
/* 305:    */   {
/* 306:394 */     boolean var1 = false;
/* 307:395 */     boolean var2 = this.worldObj.rand.nextInt(50) == 0;
/* 308:396 */     Iterator var3 = this.villageDoorInfoList.iterator();
/* 309:398 */     while (var3.hasNext())
/* 310:    */     {
/* 311:400 */       VillageDoorInfo var4 = (VillageDoorInfo)var3.next();
/* 312:402 */       if (var2) {
/* 313:404 */         var4.resetDoorOpeningRestrictionCounter();
/* 314:    */       }
/* 315:407 */       if ((!isBlockDoor(var4.posX, var4.posY, var4.posZ)) || (Math.abs(this.tickCounter - var4.lastActivityTimestamp) > 1200))
/* 316:    */       {
/* 317:409 */         this.centerHelper.posX -= var4.posX;
/* 318:410 */         this.centerHelper.posY -= var4.posY;
/* 319:411 */         this.centerHelper.posZ -= var4.posZ;
/* 320:412 */         var1 = true;
/* 321:413 */         var4.isDetachedFromVillageFlag = true;
/* 322:414 */         var3.remove();
/* 323:    */       }
/* 324:    */     }
/* 325:418 */     if (var1) {
/* 326:420 */       updateVillageRadiusAndCenter();
/* 327:    */     }
/* 328:    */   }
/* 329:    */   
/* 330:    */   private boolean isBlockDoor(int par1, int par2, int par3)
/* 331:    */   {
/* 332:426 */     return this.worldObj.getBlock(par1, par2, par3) == Blocks.wooden_door;
/* 333:    */   }
/* 334:    */   
/* 335:    */   private void updateVillageRadiusAndCenter()
/* 336:    */   {
/* 337:431 */     int var1 = this.villageDoorInfoList.size();
/* 338:433 */     if (var1 == 0)
/* 339:    */     {
/* 340:435 */       this.center.set(0, 0, 0);
/* 341:436 */       this.villageRadius = 0;
/* 342:    */     }
/* 343:    */     else
/* 344:    */     {
/* 345:440 */       this.center.set(this.centerHelper.posX / var1, this.centerHelper.posY / var1, this.centerHelper.posZ / var1);
/* 346:441 */       int var2 = 0;
/* 347:    */       VillageDoorInfo var4;
/* 348:444 */       for (Iterator var3 = this.villageDoorInfoList.iterator(); var3.hasNext(); var2 = Math.max(var4.getDistanceSquared(this.center.posX, this.center.posY, this.center.posZ), var2)) {
/* 349:446 */         var4 = (VillageDoorInfo)var3.next();
/* 350:    */       }
/* 351:449 */       this.villageRadius = Math.max(32, (int)Math.sqrt(var2) + 1);
/* 352:    */     }
/* 353:    */   }
/* 354:    */   
/* 355:    */   public int getReputationForPlayer(String par1Str)
/* 356:    */   {
/* 357:458 */     Integer var2 = (Integer)this.playerReputation.get(par1Str);
/* 358:459 */     return var2 != null ? var2.intValue() : 0;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public int setReputationForPlayer(String par1Str, int par2)
/* 362:    */   {
/* 363:467 */     int var3 = getReputationForPlayer(par1Str);
/* 364:468 */     int var4 = MathHelper.clamp_int(var3 + par2, -30, 10);
/* 365:469 */     this.playerReputation.put(par1Str, Integer.valueOf(var4));
/* 366:470 */     return var4;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public boolean isPlayerReputationTooLow(String par1Str)
/* 370:    */   {
/* 371:478 */     return getReputationForPlayer(par1Str) <= -15;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void readVillageDataFromNBT(NBTTagCompound par1NBTTagCompound)
/* 375:    */   {
/* 376:486 */     this.numVillagers = par1NBTTagCompound.getInteger("PopSize");
/* 377:487 */     this.villageRadius = par1NBTTagCompound.getInteger("Radius");
/* 378:488 */     this.numIronGolems = par1NBTTagCompound.getInteger("Golems");
/* 379:489 */     this.lastAddDoorTimestamp = par1NBTTagCompound.getInteger("Stable");
/* 380:490 */     this.tickCounter = par1NBTTagCompound.getInteger("Tick");
/* 381:491 */     this.noBreedTicks = par1NBTTagCompound.getInteger("MTick");
/* 382:492 */     this.center.posX = par1NBTTagCompound.getInteger("CX");
/* 383:493 */     this.center.posY = par1NBTTagCompound.getInteger("CY");
/* 384:494 */     this.center.posZ = par1NBTTagCompound.getInteger("CZ");
/* 385:495 */     this.centerHelper.posX = par1NBTTagCompound.getInteger("ACX");
/* 386:496 */     this.centerHelper.posY = par1NBTTagCompound.getInteger("ACY");
/* 387:497 */     this.centerHelper.posZ = par1NBTTagCompound.getInteger("ACZ");
/* 388:498 */     NBTTagList var2 = par1NBTTagCompound.getTagList("Doors", 10);
/* 389:500 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/* 390:    */     {
/* 391:502 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 392:503 */       VillageDoorInfo var5 = new VillageDoorInfo(var4.getInteger("X"), var4.getInteger("Y"), var4.getInteger("Z"), var4.getInteger("IDX"), var4.getInteger("IDZ"), var4.getInteger("TS"));
/* 393:504 */       this.villageDoorInfoList.add(var5);
/* 394:    */     }
/* 395:507 */     NBTTagList var6 = par1NBTTagCompound.getTagList("Players", 10);
/* 396:509 */     for (int var7 = 0; var7 < var6.tagCount(); var7++)
/* 397:    */     {
/* 398:511 */       NBTTagCompound var8 = var6.getCompoundTagAt(var7);
/* 399:512 */       this.playerReputation.put(var8.getString("Name"), Integer.valueOf(var8.getInteger("S")));
/* 400:    */     }
/* 401:    */   }
/* 402:    */   
/* 403:    */   public void writeVillageDataToNBT(NBTTagCompound par1NBTTagCompound)
/* 404:    */   {
/* 405:521 */     par1NBTTagCompound.setInteger("PopSize", this.numVillagers);
/* 406:522 */     par1NBTTagCompound.setInteger("Radius", this.villageRadius);
/* 407:523 */     par1NBTTagCompound.setInteger("Golems", this.numIronGolems);
/* 408:524 */     par1NBTTagCompound.setInteger("Stable", this.lastAddDoorTimestamp);
/* 409:525 */     par1NBTTagCompound.setInteger("Tick", this.tickCounter);
/* 410:526 */     par1NBTTagCompound.setInteger("MTick", this.noBreedTicks);
/* 411:527 */     par1NBTTagCompound.setInteger("CX", this.center.posX);
/* 412:528 */     par1NBTTagCompound.setInteger("CY", this.center.posY);
/* 413:529 */     par1NBTTagCompound.setInteger("CZ", this.center.posZ);
/* 414:530 */     par1NBTTagCompound.setInteger("ACX", this.centerHelper.posX);
/* 415:531 */     par1NBTTagCompound.setInteger("ACY", this.centerHelper.posY);
/* 416:532 */     par1NBTTagCompound.setInteger("ACZ", this.centerHelper.posZ);
/* 417:533 */     NBTTagList var2 = new NBTTagList();
/* 418:534 */     Iterator var3 = this.villageDoorInfoList.iterator();
/* 419:536 */     while (var3.hasNext())
/* 420:    */     {
/* 421:538 */       VillageDoorInfo var4 = (VillageDoorInfo)var3.next();
/* 422:539 */       NBTTagCompound var5 = new NBTTagCompound();
/* 423:540 */       var5.setInteger("X", var4.posX);
/* 424:541 */       var5.setInteger("Y", var4.posY);
/* 425:542 */       var5.setInteger("Z", var4.posZ);
/* 426:543 */       var5.setInteger("IDX", var4.insideDirectionX);
/* 427:544 */       var5.setInteger("IDZ", var4.insideDirectionZ);
/* 428:545 */       var5.setInteger("TS", var4.lastActivityTimestamp);
/* 429:546 */       var2.appendTag(var5);
/* 430:    */     }
/* 431:549 */     par1NBTTagCompound.setTag("Doors", var2);
/* 432:550 */     NBTTagList var7 = new NBTTagList();
/* 433:551 */     Iterator var8 = this.playerReputation.keySet().iterator();
/* 434:553 */     while (var8.hasNext())
/* 435:    */     {
/* 436:555 */       String var9 = (String)var8.next();
/* 437:556 */       NBTTagCompound var6 = new NBTTagCompound();
/* 438:557 */       var6.setString("Name", var9);
/* 439:558 */       var6.setInteger("S", ((Integer)this.playerReputation.get(var9)).intValue());
/* 440:559 */       var7.appendTag(var6);
/* 441:    */     }
/* 442:562 */     par1NBTTagCompound.setTag("Players", var7);
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void endMatingSeason()
/* 446:    */   {
/* 447:570 */     this.noBreedTicks = this.tickCounter;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public boolean isMatingSeason()
/* 451:    */   {
/* 452:578 */     return (this.noBreedTicks == 0) || (this.tickCounter - this.noBreedTicks >= 3600);
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setDefaultPlayerReputation(int par1)
/* 456:    */   {
/* 457:583 */     Iterator var2 = this.playerReputation.keySet().iterator();
/* 458:585 */     while (var2.hasNext())
/* 459:    */     {
/* 460:587 */       String var3 = (String)var2.next();
/* 461:588 */       setReputationForPlayer(var3, par1);
/* 462:    */     }
/* 463:    */   }
/* 464:    */   
/* 465:    */   class VillageAgressor
/* 466:    */   {
/* 467:    */     public EntityLivingBase agressor;
/* 468:    */     public int agressionTime;
/* 469:    */     private static final String __OBFID = "CL_00001632";
/* 470:    */     
/* 471:    */     VillageAgressor(EntityLivingBase par2EntityLivingBase, int par3)
/* 472:    */     {
/* 473:600 */       this.agressor = par2EntityLivingBase;
/* 474:601 */       this.agressionTime = par3;
/* 475:    */     }
/* 476:    */   }
/* 477:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.village.Village
 * JD-Core Version:    0.7.0.1
 */