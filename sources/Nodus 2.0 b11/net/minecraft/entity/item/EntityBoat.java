/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.entity.DataWatcher;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityLivingBase;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.init.Items;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.nbt.NBTTagCompound;
/*  16:    */ import net.minecraft.util.AABBPool;
/*  17:    */ import net.minecraft.util.AxisAlignedBB;
/*  18:    */ import net.minecraft.util.DamageSource;
/*  19:    */ import net.minecraft.util.MathHelper;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ 
/*  22:    */ public class EntityBoat
/*  23:    */   extends Entity
/*  24:    */ {
/*  25:    */   private boolean isBoatEmpty;
/*  26:    */   private double speedMultiplier;
/*  27:    */   private int boatPosRotationIncrements;
/*  28:    */   private double boatX;
/*  29:    */   private double boatY;
/*  30:    */   private double boatZ;
/*  31:    */   private double boatYaw;
/*  32:    */   private double boatPitch;
/*  33:    */   private double velocityX;
/*  34:    */   private double velocityY;
/*  35:    */   private double velocityZ;
/*  36:    */   private static final String __OBFID = "CL_00001667";
/*  37:    */   
/*  38:    */   public EntityBoat(World par1World)
/*  39:    */   {
/*  40: 36 */     super(par1World);
/*  41: 37 */     this.isBoatEmpty = true;
/*  42: 38 */     this.speedMultiplier = 0.07000000000000001D;
/*  43: 39 */     this.preventEntitySpawning = true;
/*  44: 40 */     setSize(1.5F, 0.6F);
/*  45: 41 */     this.yOffset = (this.height / 2.0F);
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected boolean canTriggerWalking()
/*  49:    */   {
/*  50: 50 */     return false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected void entityInit()
/*  54:    */   {
/*  55: 55 */     this.dataWatcher.addObject(17, new Integer(0));
/*  56: 56 */     this.dataWatcher.addObject(18, new Integer(1));
/*  57: 57 */     this.dataWatcher.addObject(19, new Float(0.0F));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public AxisAlignedBB getCollisionBox(Entity par1Entity)
/*  61:    */   {
/*  62: 66 */     return par1Entity.boundingBox;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public AxisAlignedBB getBoundingBox()
/*  66:    */   {
/*  67: 74 */     return this.boundingBox;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean canBePushed()
/*  71:    */   {
/*  72: 82 */     return true;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public EntityBoat(World par1World, double par2, double par4, double par6)
/*  76:    */   {
/*  77: 87 */     this(par1World);
/*  78: 88 */     setPosition(par2, par4 + this.yOffset, par6);
/*  79: 89 */     this.motionX = 0.0D;
/*  80: 90 */     this.motionY = 0.0D;
/*  81: 91 */     this.motionZ = 0.0D;
/*  82: 92 */     this.prevPosX = par2;
/*  83: 93 */     this.prevPosY = par4;
/*  84: 94 */     this.prevPosZ = par6;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public double getMountedYOffset()
/*  88:    */   {
/*  89:102 */     return this.height * 0.0D - 0.300000011920929D;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  93:    */   {
/*  94:110 */     if (isEntityInvulnerable()) {
/*  95:112 */       return false;
/*  96:    */     }
/*  97:114 */     if ((!this.worldObj.isClient) && (!this.isDead))
/*  98:    */     {
/*  99:116 */       setForwardDirection(-getForwardDirection());
/* 100:117 */       setTimeSinceHit(10);
/* 101:118 */       setDamageTaken(getDamageTaken() + par2 * 10.0F);
/* 102:119 */       setBeenAttacked();
/* 103:120 */       boolean var3 = ((par1DamageSource.getEntity() instanceof EntityPlayer)) && (((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode);
/* 104:122 */       if ((var3) || (getDamageTaken() > 40.0F))
/* 105:    */       {
/* 106:124 */         if (this.riddenByEntity != null) {
/* 107:126 */           this.riddenByEntity.mountEntity(this);
/* 108:    */         }
/* 109:129 */         if (!var3) {
/* 110:131 */           func_145778_a(Items.boat, 1, 0.0F);
/* 111:    */         }
/* 112:134 */         setDead();
/* 113:    */       }
/* 114:137 */       return true;
/* 115:    */     }
/* 116:141 */     return true;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void performHurtAnimation()
/* 120:    */   {
/* 121:150 */     setForwardDirection(-getForwardDirection());
/* 122:151 */     setTimeSinceHit(10);
/* 123:152 */     setDamageTaken(getDamageTaken() * 11.0F);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean canBeCollidedWith()
/* 127:    */   {
/* 128:160 */     return !this.isDead;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
/* 132:    */   {
/* 133:169 */     if (this.isBoatEmpty)
/* 134:    */     {
/* 135:171 */       this.boatPosRotationIncrements = (par9 + 5);
/* 136:    */     }
/* 137:    */     else
/* 138:    */     {
/* 139:175 */       double var10 = par1 - this.posX;
/* 140:176 */       double var12 = par3 - this.posY;
/* 141:177 */       double var14 = par5 - this.posZ;
/* 142:178 */       double var16 = var10 * var10 + var12 * var12 + var14 * var14;
/* 143:180 */       if (var16 <= 1.0D) {
/* 144:182 */         return;
/* 145:    */       }
/* 146:185 */       this.boatPosRotationIncrements = 3;
/* 147:    */     }
/* 148:188 */     this.boatX = par1;
/* 149:189 */     this.boatY = par3;
/* 150:190 */     this.boatZ = par5;
/* 151:191 */     this.boatYaw = par7;
/* 152:192 */     this.boatPitch = par8;
/* 153:193 */     this.motionX = this.velocityX;
/* 154:194 */     this.motionY = this.velocityY;
/* 155:195 */     this.motionZ = this.velocityZ;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setVelocity(double par1, double par3, double par5)
/* 159:    */   {
/* 160:203 */     this.velocityX = (this.motionX = par1);
/* 161:204 */     this.velocityY = (this.motionY = par3);
/* 162:205 */     this.velocityZ = (this.motionZ = par5);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void onUpdate()
/* 166:    */   {
/* 167:213 */     super.onUpdate();
/* 168:215 */     if (getTimeSinceHit() > 0) {
/* 169:217 */       setTimeSinceHit(getTimeSinceHit() - 1);
/* 170:    */     }
/* 171:220 */     if (getDamageTaken() > 0.0F) {
/* 172:222 */       setDamageTaken(getDamageTaken() - 1.0F);
/* 173:    */     }
/* 174:225 */     this.prevPosX = this.posX;
/* 175:226 */     this.prevPosY = this.posY;
/* 176:227 */     this.prevPosZ = this.posZ;
/* 177:228 */     byte var1 = 5;
/* 178:229 */     double var2 = 0.0D;
/* 179:231 */     for (int var4 = 0; var4 < var1; var4++)
/* 180:    */     {
/* 181:233 */       double var5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var4 + 0) / var1 - 0.125D;
/* 182:234 */       double var7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var4 + 1) / var1 - 0.125D;
/* 183:235 */       AxisAlignedBB var9 = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, var5, this.boundingBox.minZ, this.boundingBox.maxX, var7, this.boundingBox.maxZ);
/* 184:237 */       if (this.worldObj.isAABBInMaterial(var9, Material.water)) {
/* 185:239 */         var2 += 1.0D / var1;
/* 186:    */       }
/* 187:    */     }
/* 188:243 */     double var19 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 189:248 */     if (var19 > 0.2625D)
/* 190:    */     {
/* 191:250 */       double var6 = Math.cos(this.rotationYaw * 3.141592653589793D / 180.0D);
/* 192:251 */       double var8 = Math.sin(this.rotationYaw * 3.141592653589793D / 180.0D);
/* 193:253 */       for (int var10 = 0; var10 < 1.0D + var19 * 60.0D; var10++)
/* 194:    */       {
/* 195:255 */         double var11 = this.rand.nextFloat() * 2.0F - 1.0F;
/* 196:256 */         double var13 = (this.rand.nextInt(2) * 2 - 1) * 0.7D;
/* 197:260 */         if (this.rand.nextBoolean())
/* 198:    */         {
/* 199:262 */           double var15 = this.posX - var6 * var11 * 0.8D + var8 * var13;
/* 200:263 */           double var17 = this.posZ - var8 * var11 * 0.8D - var6 * var13;
/* 201:264 */           this.worldObj.spawnParticle("splash", var15, this.posY - 0.125D, var17, this.motionX, this.motionY, this.motionZ);
/* 202:    */         }
/* 203:    */         else
/* 204:    */         {
/* 205:268 */           double var15 = this.posX + var6 + var8 * var11 * 0.7D;
/* 206:269 */           double var17 = this.posZ + var8 - var6 * var11 * 0.7D;
/* 207:270 */           this.worldObj.spawnParticle("splash", var15, this.posY - 0.125D, var17, this.motionX, this.motionY, this.motionZ);
/* 208:    */         }
/* 209:    */       }
/* 210:    */     }
/* 211:278 */     if ((this.worldObj.isClient) && (this.isBoatEmpty))
/* 212:    */     {
/* 213:280 */       if (this.boatPosRotationIncrements > 0)
/* 214:    */       {
/* 215:282 */         double var6 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
/* 216:283 */         double var8 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
/* 217:284 */         double var24 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
/* 218:285 */         double var26 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
/* 219:286 */         this.rotationYaw = ((float)(this.rotationYaw + var26 / this.boatPosRotationIncrements));
/* 220:287 */         this.rotationPitch = ((float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements));
/* 221:288 */         this.boatPosRotationIncrements -= 1;
/* 222:289 */         setPosition(var6, var8, var24);
/* 223:290 */         setRotation(this.rotationYaw, this.rotationPitch);
/* 224:    */       }
/* 225:    */       else
/* 226:    */       {
/* 227:294 */         double var6 = this.posX + this.motionX;
/* 228:295 */         double var8 = this.posY + this.motionY;
/* 229:296 */         double var24 = this.posZ + this.motionZ;
/* 230:297 */         setPosition(var6, var8, var24);
/* 231:299 */         if (this.onGround)
/* 232:    */         {
/* 233:301 */           this.motionX *= 0.5D;
/* 234:302 */           this.motionY *= 0.5D;
/* 235:303 */           this.motionZ *= 0.5D;
/* 236:    */         }
/* 237:306 */         this.motionX *= 0.9900000095367432D;
/* 238:307 */         this.motionY *= 0.949999988079071D;
/* 239:308 */         this.motionZ *= 0.9900000095367432D;
/* 240:    */       }
/* 241:    */     }
/* 242:    */     else
/* 243:    */     {
/* 244:313 */       if (var2 < 1.0D)
/* 245:    */       {
/* 246:315 */         double var6 = var2 * 2.0D - 1.0D;
/* 247:316 */         this.motionY += 0.03999999910593033D * var6;
/* 248:    */       }
/* 249:    */       else
/* 250:    */       {
/* 251:320 */         if (this.motionY < 0.0D) {
/* 252:322 */           this.motionY /= 2.0D;
/* 253:    */         }
/* 254:325 */         this.motionY += 0.007000000216066837D;
/* 255:    */       }
/* 256:328 */       if ((this.riddenByEntity != null) && ((this.riddenByEntity instanceof EntityLivingBase)))
/* 257:    */       {
/* 258:330 */         EntityLivingBase var20 = (EntityLivingBase)this.riddenByEntity;
/* 259:331 */         float var21 = this.riddenByEntity.rotationYaw + -var20.moveStrafing * 90.0F;
/* 260:332 */         this.motionX += -Math.sin(var21 * 3.141593F / 180.0F) * this.speedMultiplier * var20.moveForward * 0.0500000007450581D;
/* 261:333 */         this.motionZ += Math.cos(var21 * 3.141593F / 180.0F) * this.speedMultiplier * var20.moveForward * 0.0500000007450581D;
/* 262:    */       }
/* 263:336 */       double var6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 264:338 */       if (var6 > 0.35D)
/* 265:    */       {
/* 266:340 */         double var8 = 0.35D / var6;
/* 267:341 */         this.motionX *= var8;
/* 268:342 */         this.motionZ *= var8;
/* 269:343 */         var6 = 0.35D;
/* 270:    */       }
/* 271:346 */       if ((var6 > var19) && (this.speedMultiplier < 0.35D))
/* 272:    */       {
/* 273:348 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/* 274:350 */         if (this.speedMultiplier > 0.35D) {
/* 275:352 */           this.speedMultiplier = 0.35D;
/* 276:    */         }
/* 277:    */       }
/* 278:    */       else
/* 279:    */       {
/* 280:357 */         this.speedMultiplier -= (this.speedMultiplier - 0.07000000000000001D) / 35.0D;
/* 281:359 */         if (this.speedMultiplier < 0.07000000000000001D) {
/* 282:361 */           this.speedMultiplier = 0.07000000000000001D;
/* 283:    */         }
/* 284:    */       }
/* 285:367 */       for (int var22 = 0; var22 < 4; var22++)
/* 286:    */       {
/* 287:369 */         int var23 = MathHelper.floor_double(this.posX + (var22 % 2 - 0.5D) * 0.8D);
/* 288:370 */         int var10 = MathHelper.floor_double(this.posZ + (var22 / 2 - 0.5D) * 0.8D);
/* 289:372 */         for (int var25 = 0; var25 < 2; var25++)
/* 290:    */         {
/* 291:374 */           int var12 = MathHelper.floor_double(this.posY) + var25;
/* 292:375 */           Block var27 = this.worldObj.getBlock(var23, var12, var10);
/* 293:377 */           if (var27 == Blocks.snow_layer)
/* 294:    */           {
/* 295:379 */             this.worldObj.setBlockToAir(var23, var12, var10);
/* 296:380 */             this.isCollidedHorizontally = false;
/* 297:    */           }
/* 298:382 */           else if (var27 == Blocks.waterlily)
/* 299:    */           {
/* 300:384 */             this.worldObj.func_147480_a(var23, var12, var10, true);
/* 301:385 */             this.isCollidedHorizontally = false;
/* 302:    */           }
/* 303:    */         }
/* 304:    */       }
/* 305:390 */       if (this.onGround)
/* 306:    */       {
/* 307:392 */         this.motionX *= 0.5D;
/* 308:393 */         this.motionY *= 0.5D;
/* 309:394 */         this.motionZ *= 0.5D;
/* 310:    */       }
/* 311:397 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 312:399 */       if ((this.isCollidedHorizontally) && (var19 > 0.2D))
/* 313:    */       {
/* 314:401 */         if ((!this.worldObj.isClient) && (!this.isDead))
/* 315:    */         {
/* 316:403 */           setDead();
/* 317:405 */           for (var22 = 0; var22 < 3; var22++) {
/* 318:407 */             func_145778_a(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
/* 319:    */           }
/* 320:410 */           for (var22 = 0; var22 < 2; var22++) {
/* 321:412 */             func_145778_a(Items.stick, 1, 0.0F);
/* 322:    */           }
/* 323:    */         }
/* 324:    */       }
/* 325:    */       else
/* 326:    */       {
/* 327:418 */         this.motionX *= 0.9900000095367432D;
/* 328:419 */         this.motionY *= 0.949999988079071D;
/* 329:420 */         this.motionZ *= 0.9900000095367432D;
/* 330:    */       }
/* 331:423 */       this.rotationPitch = 0.0F;
/* 332:424 */       double var8 = this.rotationYaw;
/* 333:425 */       double var24 = this.prevPosX - this.posX;
/* 334:426 */       double var26 = this.prevPosZ - this.posZ;
/* 335:428 */       if (var24 * var24 + var26 * var26 > 0.001D) {
/* 336:430 */         var8 = (float)(Math.atan2(var26, var24) * 180.0D / 3.141592653589793D);
/* 337:    */       }
/* 338:433 */       double var14 = MathHelper.wrapAngleTo180_double(var8 - this.rotationYaw);
/* 339:435 */       if (var14 > 20.0D) {
/* 340:437 */         var14 = 20.0D;
/* 341:    */       }
/* 342:440 */       if (var14 < -20.0D) {
/* 343:442 */         var14 = -20.0D;
/* 344:    */       }
/* 345:445 */       this.rotationYaw = ((float)(this.rotationYaw + var14));
/* 346:446 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 347:448 */       if (!this.worldObj.isClient)
/* 348:    */       {
/* 349:450 */         List var16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2000000029802322D, 0.0D, 0.2000000029802322D));
/* 350:452 */         if ((var16 != null) && (!var16.isEmpty())) {
/* 351:454 */           for (int var28 = 0; var28 < var16.size(); var28++)
/* 352:    */           {
/* 353:456 */             Entity var18 = (Entity)var16.get(var28);
/* 354:458 */             if ((var18 != this.riddenByEntity) && (var18.canBePushed()) && ((var18 instanceof EntityBoat))) {
/* 355:460 */               var18.applyEntityCollision(this);
/* 356:    */             }
/* 357:    */           }
/* 358:    */         }
/* 359:465 */         if ((this.riddenByEntity != null) && (this.riddenByEntity.isDead)) {
/* 360:467 */           this.riddenByEntity = null;
/* 361:    */         }
/* 362:    */       }
/* 363:    */     }
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void updateRiderPosition()
/* 367:    */   {
/* 368:475 */     if (this.riddenByEntity != null)
/* 369:    */     {
/* 370:477 */       double var1 = Math.cos(this.rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
/* 371:478 */       double var3 = Math.sin(this.rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
/* 372:479 */       this.riddenByEntity.setPosition(this.posX + var1, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
/* 373:    */     }
/* 374:    */   }
/* 375:    */   
/* 376:    */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}
/* 377:    */   
/* 378:    */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}
/* 379:    */   
/* 380:    */   public float getShadowSize()
/* 381:    */   {
/* 382:495 */     return 0.0F;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public boolean interactFirst(EntityPlayer par1EntityPlayer)
/* 386:    */   {
/* 387:503 */     if ((this.riddenByEntity != null) && ((this.riddenByEntity instanceof EntityPlayer)) && (this.riddenByEntity != par1EntityPlayer)) {
/* 388:505 */       return true;
/* 389:    */     }
/* 390:509 */     if (!this.worldObj.isClient) {
/* 391:511 */       par1EntityPlayer.mountEntity(this);
/* 392:    */     }
/* 393:514 */     return true;
/* 394:    */   }
/* 395:    */   
/* 396:    */   protected void updateFallState(double par1, boolean par3)
/* 397:    */   {
/* 398:524 */     int var4 = MathHelper.floor_double(this.posX);
/* 399:525 */     int var5 = MathHelper.floor_double(this.posY);
/* 400:526 */     int var6 = MathHelper.floor_double(this.posZ);
/* 401:528 */     if (par3)
/* 402:    */     {
/* 403:530 */       if (this.fallDistance > 3.0F)
/* 404:    */       {
/* 405:532 */         fall(this.fallDistance);
/* 406:534 */         if ((!this.worldObj.isClient) && (!this.isDead))
/* 407:    */         {
/* 408:536 */           setDead();
/* 409:539 */           for (int var7 = 0; var7 < 3; var7++) {
/* 410:541 */             func_145778_a(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
/* 411:    */           }
/* 412:544 */           for (var7 = 0; var7 < 2; var7++) {
/* 413:546 */             func_145778_a(Items.stick, 1, 0.0F);
/* 414:    */           }
/* 415:    */         }
/* 416:550 */         this.fallDistance = 0.0F;
/* 417:    */       }
/* 418:    */     }
/* 419:553 */     else if ((this.worldObj.getBlock(var4, var5 - 1, var6).getMaterial() != Material.water) && (par1 < 0.0D)) {
/* 420:555 */       this.fallDistance = ((float)(this.fallDistance - par1));
/* 421:    */     }
/* 422:    */   }
/* 423:    */   
/* 424:    */   public void setDamageTaken(float par1)
/* 425:    */   {
/* 426:564 */     this.dataWatcher.updateObject(19, Float.valueOf(par1));
/* 427:    */   }
/* 428:    */   
/* 429:    */   public float getDamageTaken()
/* 430:    */   {
/* 431:572 */     return this.dataWatcher.getWatchableObjectFloat(19);
/* 432:    */   }
/* 433:    */   
/* 434:    */   public void setTimeSinceHit(int par1)
/* 435:    */   {
/* 436:580 */     this.dataWatcher.updateObject(17, Integer.valueOf(par1));
/* 437:    */   }
/* 438:    */   
/* 439:    */   public int getTimeSinceHit()
/* 440:    */   {
/* 441:588 */     return this.dataWatcher.getWatchableObjectInt(17);
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void setForwardDirection(int par1)
/* 445:    */   {
/* 446:596 */     this.dataWatcher.updateObject(18, Integer.valueOf(par1));
/* 447:    */   }
/* 448:    */   
/* 449:    */   public int getForwardDirection()
/* 450:    */   {
/* 451:604 */     return this.dataWatcher.getWatchableObjectInt(18);
/* 452:    */   }
/* 453:    */   
/* 454:    */   public void setIsBoatEmpty(boolean par1)
/* 455:    */   {
/* 456:612 */     this.isBoatEmpty = par1;
/* 457:    */   }
/* 458:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityBoat
 * JD-Core Version:    0.7.0.1
 */