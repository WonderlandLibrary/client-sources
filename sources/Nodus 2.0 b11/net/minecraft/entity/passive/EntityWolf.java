/*   1:    */ package net.minecraft.entity.passive;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.BlockColored;
/*   6:    */ import net.minecraft.entity.DataWatcher;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.EntityAgeable;
/*   9:    */ import net.minecraft.entity.EntityLivingBase;
/*  10:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  11:    */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*  12:    */ import net.minecraft.entity.ai.EntityAIBeg;
/*  13:    */ import net.minecraft.entity.ai.EntityAIFollowOwner;
/*  14:    */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*  15:    */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*  16:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*  17:    */ import net.minecraft.entity.ai.EntityAIMate;
/*  18:    */ import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
/*  19:    */ import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
/*  20:    */ import net.minecraft.entity.ai.EntityAISit;
/*  21:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  22:    */ import net.minecraft.entity.ai.EntityAITargetNonTamed;
/*  23:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  24:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  25:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  26:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  27:    */ import net.minecraft.entity.monster.EntityCreeper;
/*  28:    */ import net.minecraft.entity.monster.EntityGhast;
/*  29:    */ import net.minecraft.entity.player.EntityPlayer;
/*  30:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  31:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  32:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  33:    */ import net.minecraft.init.Items;
/*  34:    */ import net.minecraft.item.Item;
/*  35:    */ import net.minecraft.item.ItemFood;
/*  36:    */ import net.minecraft.item.ItemStack;
/*  37:    */ import net.minecraft.nbt.NBTTagCompound;
/*  38:    */ import net.minecraft.pathfinding.PathNavigate;
/*  39:    */ import net.minecraft.util.AxisAlignedBB;
/*  40:    */ import net.minecraft.util.DamageSource;
/*  41:    */ import net.minecraft.util.MathHelper;
/*  42:    */ import net.minecraft.world.World;
/*  43:    */ 
/*  44:    */ public class EntityWolf
/*  45:    */   extends EntityTameable
/*  46:    */ {
/*  47:    */   private float field_70926_e;
/*  48:    */   private float field_70924_f;
/*  49:    */   private boolean isShaking;
/*  50:    */   private boolean field_70928_h;
/*  51:    */   private float timeWolfIsShaking;
/*  52:    */   private float prevTimeWolfIsShaking;
/*  53:    */   private static final String __OBFID = "CL_00001654";
/*  54:    */   
/*  55:    */   public EntityWolf(World par1World)
/*  56:    */   {
/*  57: 54 */     super(par1World);
/*  58: 55 */     setSize(0.6F, 0.8F);
/*  59: 56 */     getNavigator().setAvoidsWater(true);
/*  60: 57 */     this.tasks.addTask(1, new EntityAISwimming(this));
/*  61: 58 */     this.tasks.addTask(2, this.aiSit);
/*  62: 59 */     this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
/*  63: 60 */     this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
/*  64: 61 */     this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
/*  65: 62 */     this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
/*  66: 63 */     this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
/*  67: 64 */     this.tasks.addTask(8, new EntityAIBeg(this, 8.0F));
/*  68: 65 */     this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  69: 66 */     this.tasks.addTask(9, new EntityAILookIdle(this));
/*  70: 67 */     this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
/*  71: 68 */     this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
/*  72: 69 */     this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
/*  73: 70 */     this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));
/*  74: 71 */     setTamed(false);
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void applyEntityAttributes()
/*  78:    */   {
/*  79: 76 */     super.applyEntityAttributes();
/*  80: 77 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.300000011920929D);
/*  81: 79 */     if (isTamed()) {
/*  82: 81 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
/*  83:    */     } else {
/*  84: 85 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isAIEnabled()
/*  89:    */   {
/*  90: 94 */     return true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
/*  94:    */   {
/*  95:102 */     super.setAttackTarget(par1EntityLivingBase);
/*  96:104 */     if (par1EntityLivingBase == null) {
/*  97:106 */       setAngry(false);
/*  98:108 */     } else if (!isTamed()) {
/*  99:110 */       setAngry(true);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected void updateAITick()
/* 104:    */   {
/* 105:119 */     this.dataWatcher.updateObject(18, Float.valueOf(getHealth()));
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void entityInit()
/* 109:    */   {
/* 110:124 */     super.entityInit();
/* 111:125 */     this.dataWatcher.addObject(18, new Float(getHealth()));
/* 112:126 */     this.dataWatcher.addObject(19, new Byte((byte)0));
/* 113:127 */     this.dataWatcher.addObject(20, new Byte((byte)BlockColored.func_150032_b(1)));
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/* 117:    */   {
/* 118:132 */     playSound("mob.wolf.step", 0.15F, 1.0F);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 122:    */   {
/* 123:140 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 124:141 */     par1NBTTagCompound.setBoolean("Angry", isAngry());
/* 125:142 */     par1NBTTagCompound.setByte("CollarColor", (byte)getCollarColor());
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 129:    */   {
/* 130:150 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 131:151 */     setAngry(par1NBTTagCompound.getBoolean("Angry"));
/* 132:153 */     if (par1NBTTagCompound.func_150297_b("CollarColor", 99)) {
/* 133:155 */       setCollarColor(par1NBTTagCompound.getByte("CollarColor"));
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected String getLivingSound()
/* 138:    */   {
/* 139:164 */     return this.rand.nextInt(3) == 0 ? "mob.wolf.panting" : (isTamed()) && (this.dataWatcher.getWatchableObjectFloat(18) < 10.0F) ? "mob.wolf.whine" : isAngry() ? "mob.wolf.growl" : "mob.wolf.bark";
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected String getHurtSound()
/* 143:    */   {
/* 144:172 */     return "mob.wolf.hurt";
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected String getDeathSound()
/* 148:    */   {
/* 149:180 */     return "mob.wolf.death";
/* 150:    */   }
/* 151:    */   
/* 152:    */   protected float getSoundVolume()
/* 153:    */   {
/* 154:188 */     return 0.4F;
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected Item func_146068_u()
/* 158:    */   {
/* 159:193 */     return Item.getItemById(-1);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void onLivingUpdate()
/* 163:    */   {
/* 164:202 */     super.onLivingUpdate();
/* 165:204 */     if ((!this.worldObj.isClient) && (this.isShaking) && (!this.field_70928_h) && (!hasPath()) && (this.onGround))
/* 166:    */     {
/* 167:206 */       this.field_70928_h = true;
/* 168:207 */       this.timeWolfIsShaking = 0.0F;
/* 169:208 */       this.prevTimeWolfIsShaking = 0.0F;
/* 170:209 */       this.worldObj.setEntityState(this, (byte)8);
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void onUpdate()
/* 175:    */   {
/* 176:218 */     super.onUpdate();
/* 177:219 */     this.field_70924_f = this.field_70926_e;
/* 178:221 */     if (func_70922_bv()) {
/* 179:223 */       this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
/* 180:    */     } else {
/* 181:227 */       this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
/* 182:    */     }
/* 183:230 */     if (func_70922_bv()) {
/* 184:232 */       this.numTicksToChaseTarget = 10;
/* 185:    */     }
/* 186:235 */     if (isWet())
/* 187:    */     {
/* 188:237 */       this.isShaking = true;
/* 189:238 */       this.field_70928_h = false;
/* 190:239 */       this.timeWolfIsShaking = 0.0F;
/* 191:240 */       this.prevTimeWolfIsShaking = 0.0F;
/* 192:    */     }
/* 193:242 */     else if (((this.isShaking) || (this.field_70928_h)) && (this.field_70928_h))
/* 194:    */     {
/* 195:244 */       if (this.timeWolfIsShaking == 0.0F) {
/* 196:246 */         playSound("mob.wolf.shake", getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/* 197:    */       }
/* 198:249 */       this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
/* 199:250 */       this.timeWolfIsShaking += 0.05F;
/* 200:252 */       if (this.prevTimeWolfIsShaking >= 2.0F)
/* 201:    */       {
/* 202:254 */         this.isShaking = false;
/* 203:255 */         this.field_70928_h = false;
/* 204:256 */         this.prevTimeWolfIsShaking = 0.0F;
/* 205:257 */         this.timeWolfIsShaking = 0.0F;
/* 206:    */       }
/* 207:260 */       if (this.timeWolfIsShaking > 0.4F)
/* 208:    */       {
/* 209:262 */         float var1 = (float)this.boundingBox.minY;
/* 210:263 */         int var2 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * 3.141593F) * 7.0F);
/* 211:265 */         for (int var3 = 0; var3 < var2; var3++)
/* 212:    */         {
/* 213:267 */           float var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/* 214:268 */           float var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/* 215:269 */           this.worldObj.spawnParticle("splash", this.posX + var4, var1 + 0.8F, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
/* 216:    */         }
/* 217:    */       }
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean getWolfShaking()
/* 222:    */   {
/* 223:277 */     return this.isShaking;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public float getShadingWhileShaking(float par1)
/* 227:    */   {
/* 228:285 */     return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * par1) / 2.0F * 0.25F;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public float getShakeAngle(float par1, float par2)
/* 232:    */   {
/* 233:290 */     float var3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * par1 + par2) / 1.8F;
/* 234:292 */     if (var3 < 0.0F) {
/* 235:294 */       var3 = 0.0F;
/* 236:296 */     } else if (var3 > 1.0F) {
/* 237:298 */       var3 = 1.0F;
/* 238:    */     }
/* 239:301 */     return MathHelper.sin(var3 * 3.141593F) * MathHelper.sin(var3 * 3.141593F * 11.0F) * 0.15F * 3.141593F;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public float getInterestedAngle(float par1)
/* 243:    */   {
/* 244:306 */     return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * par1) * 0.15F * 3.141593F;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public float getEyeHeight()
/* 248:    */   {
/* 249:311 */     return this.height * 0.8F;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public int getVerticalFaceSpeed()
/* 253:    */   {
/* 254:320 */     return isSitting() ? 20 : super.getVerticalFaceSpeed();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 258:    */   {
/* 259:328 */     if (isEntityInvulnerable()) {
/* 260:330 */       return false;
/* 261:    */     }
/* 262:334 */     Entity var3 = par1DamageSource.getEntity();
/* 263:335 */     this.aiSit.setSitting(false);
/* 264:337 */     if ((var3 != null) && (!(var3 instanceof EntityPlayer)) && (!(var3 instanceof EntityArrow))) {
/* 265:339 */       par2 = (par2 + 1.0F) / 2.0F;
/* 266:    */     }
/* 267:342 */     return super.attackEntityFrom(par1DamageSource, par2);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public boolean attackEntityAsMob(Entity par1Entity)
/* 271:    */   {
/* 272:348 */     int var2 = isTamed() ? 4 : 2;
/* 273:349 */     return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setTamed(boolean par1)
/* 277:    */   {
/* 278:354 */     super.setTamed(par1);
/* 279:356 */     if (par1) {
/* 280:358 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
/* 281:    */     } else {
/* 282:362 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   public boolean interact(EntityPlayer par1EntityPlayer)
/* 287:    */   {
/* 288:371 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/* 289:373 */     if (isTamed())
/* 290:    */     {
/* 291:375 */       if (var2 != null) {
/* 292:377 */         if ((var2.getItem() instanceof ItemFood))
/* 293:    */         {
/* 294:379 */           ItemFood var3 = (ItemFood)var2.getItem();
/* 295:381 */           if ((var3.isWolfsFavoriteMeat()) && (this.dataWatcher.getWatchableObjectFloat(18) < 20.0F))
/* 296:    */           {
/* 297:383 */             if (!par1EntityPlayer.capabilities.isCreativeMode) {
/* 298:385 */               var2.stackSize -= 1;
/* 299:    */             }
/* 300:388 */             heal(var3.func_150905_g(var2));
/* 301:390 */             if (var2.stackSize <= 0) {
/* 302:392 */               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
/* 303:    */             }
/* 304:395 */             return true;
/* 305:    */           }
/* 306:    */         }
/* 307:398 */         else if (var2.getItem() == Items.dye)
/* 308:    */         {
/* 309:400 */           int var4 = BlockColored.func_150032_b(var2.getItemDamage());
/* 310:402 */           if (var4 != getCollarColor())
/* 311:    */           {
/* 312:404 */             setCollarColor(var4);
/* 313:406 */             if (!par1EntityPlayer.capabilities.isCreativeMode) {
/* 314:406 */               if (--var2.stackSize <= 0) {
/* 315:408 */                 par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
/* 316:    */               }
/* 317:    */             }
/* 318:411 */             return true;
/* 319:    */           }
/* 320:    */         }
/* 321:    */       }
/* 322:416 */       if ((par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(getOwnerName())) && (!this.worldObj.isClient) && (!isBreedingItem(var2)))
/* 323:    */       {
/* 324:418 */         this.aiSit.setSitting(!isSitting());
/* 325:419 */         this.isJumping = false;
/* 326:420 */         setPathToEntity(null);
/* 327:421 */         setTarget(null);
/* 328:422 */         setAttackTarget(null);
/* 329:    */       }
/* 330:    */     }
/* 331:425 */     else if ((var2 != null) && (var2.getItem() == Items.bone) && (!isAngry()))
/* 332:    */     {
/* 333:427 */       if (!par1EntityPlayer.capabilities.isCreativeMode) {
/* 334:429 */         var2.stackSize -= 1;
/* 335:    */       }
/* 336:432 */       if (var2.stackSize <= 0) {
/* 337:434 */         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
/* 338:    */       }
/* 339:437 */       if (!this.worldObj.isClient) {
/* 340:439 */         if (this.rand.nextInt(3) == 0)
/* 341:    */         {
/* 342:441 */           setTamed(true);
/* 343:442 */           setPathToEntity(null);
/* 344:443 */           setAttackTarget(null);
/* 345:444 */           this.aiSit.setSitting(true);
/* 346:445 */           setHealth(20.0F);
/* 347:446 */           setOwner(par1EntityPlayer.getCommandSenderName());
/* 348:447 */           playTameEffect(true);
/* 349:448 */           this.worldObj.setEntityState(this, (byte)7);
/* 350:    */         }
/* 351:    */         else
/* 352:    */         {
/* 353:452 */           playTameEffect(false);
/* 354:453 */           this.worldObj.setEntityState(this, (byte)6);
/* 355:    */         }
/* 356:    */       }
/* 357:457 */       return true;
/* 358:    */     }
/* 359:460 */     return super.interact(par1EntityPlayer);
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void handleHealthUpdate(byte par1)
/* 363:    */   {
/* 364:465 */     if (par1 == 8)
/* 365:    */     {
/* 366:467 */       this.field_70928_h = true;
/* 367:468 */       this.timeWolfIsShaking = 0.0F;
/* 368:469 */       this.prevTimeWolfIsShaking = 0.0F;
/* 369:    */     }
/* 370:    */     else
/* 371:    */     {
/* 372:473 */       super.handleHealthUpdate(par1);
/* 373:    */     }
/* 374:    */   }
/* 375:    */   
/* 376:    */   public float getTailRotation()
/* 377:    */   {
/* 378:479 */     return isTamed() ? (0.55F - (20.0F - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02F) * 3.141593F : isAngry() ? 1.53938F : 0.6283186F;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public boolean isBreedingItem(ItemStack par1ItemStack)
/* 382:    */   {
/* 383:488 */     return !(par1ItemStack.getItem() instanceof ItemFood) ? false : par1ItemStack == null ? false : ((ItemFood)par1ItemStack.getItem()).isWolfsFavoriteMeat();
/* 384:    */   }
/* 385:    */   
/* 386:    */   public int getMaxSpawnedInChunk()
/* 387:    */   {
/* 388:496 */     return 8;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public boolean isAngry()
/* 392:    */   {
/* 393:504 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x2) != 0;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void setAngry(boolean par1)
/* 397:    */   {
/* 398:512 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 399:514 */     if (par1) {
/* 400:516 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x2)));
/* 401:    */     } else {
/* 402:520 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFD)));
/* 403:    */     }
/* 404:    */   }
/* 405:    */   
/* 406:    */   public int getCollarColor()
/* 407:    */   {
/* 408:529 */     return this.dataWatcher.getWatchableObjectByte(20) & 0xF;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public void setCollarColor(int par1)
/* 412:    */   {
/* 413:537 */     this.dataWatcher.updateObject(20, Byte.valueOf((byte)(par1 & 0xF)));
/* 414:    */   }
/* 415:    */   
/* 416:    */   public EntityWolf createChild(EntityAgeable par1EntityAgeable)
/* 417:    */   {
/* 418:542 */     EntityWolf var2 = new EntityWolf(this.worldObj);
/* 419:543 */     String var3 = getOwnerName();
/* 420:545 */     if ((var3 != null) && (var3.trim().length() > 0))
/* 421:    */     {
/* 422:547 */       var2.setOwner(var3);
/* 423:548 */       var2.setTamed(true);
/* 424:    */     }
/* 425:551 */     return var2;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public void func_70918_i(boolean par1)
/* 429:    */   {
/* 430:556 */     if (par1) {
/* 431:558 */       this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
/* 432:    */     } else {
/* 433:562 */       this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
/* 434:    */     }
/* 435:    */   }
/* 436:    */   
/* 437:    */   public boolean canMateWith(EntityAnimal par1EntityAnimal)
/* 438:    */   {
/* 439:571 */     if (par1EntityAnimal == this) {
/* 440:573 */       return false;
/* 441:    */     }
/* 442:575 */     if (!isTamed()) {
/* 443:577 */       return false;
/* 444:    */     }
/* 445:579 */     if (!(par1EntityAnimal instanceof EntityWolf)) {
/* 446:581 */       return false;
/* 447:    */     }
/* 448:585 */     EntityWolf var2 = (EntityWolf)par1EntityAnimal;
/* 449:586 */     return var2.isTamed();
/* 450:    */   }
/* 451:    */   
/* 452:    */   public boolean func_70922_bv()
/* 453:    */   {
/* 454:592 */     return this.dataWatcher.getWatchableObjectByte(19) == 1;
/* 455:    */   }
/* 456:    */   
/* 457:    */   protected boolean canDespawn()
/* 458:    */   {
/* 459:600 */     return (!isTamed()) && (this.ticksExisted > 2400);
/* 460:    */   }
/* 461:    */   
/* 462:    */   public boolean func_142018_a(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase)
/* 463:    */   {
/* 464:605 */     if ((!(par1EntityLivingBase instanceof EntityCreeper)) && (!(par1EntityLivingBase instanceof EntityGhast)))
/* 465:    */     {
/* 466:607 */       if ((par1EntityLivingBase instanceof EntityWolf))
/* 467:    */       {
/* 468:609 */         EntityWolf var3 = (EntityWolf)par1EntityLivingBase;
/* 469:611 */         if ((var3.isTamed()) && (var3.getOwner() == par2EntityLivingBase)) {
/* 470:613 */           return false;
/* 471:    */         }
/* 472:    */       }
/* 473:617 */       return (!(par1EntityLivingBase instanceof EntityPlayer)) || (!(par2EntityLivingBase instanceof EntityPlayer)) || (((EntityPlayer)par2EntityLivingBase).canAttackPlayer((EntityPlayer)par1EntityLivingBase));
/* 474:    */     }
/* 475:621 */     return false;
/* 476:    */   }
/* 477:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityWolf
 * JD-Core Version:    0.7.0.1
 */