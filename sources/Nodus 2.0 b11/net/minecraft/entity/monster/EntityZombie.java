/*   1:    */ package net.minecraft.entity.monster;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import java.util.UUID;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.entity.DataWatcher;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.EntityLivingBase;
/*  11:    */ import net.minecraft.entity.EnumCreatureAttribute;
/*  12:    */ import net.minecraft.entity.IEntityLivingData;
/*  13:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  14:    */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*  15:    */ import net.minecraft.entity.ai.EntityAIBreakDoor;
/*  16:    */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*  17:    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*  18:    */ import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
/*  19:    */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*  20:    */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*  21:    */ import net.minecraft.entity.ai.EntityAISwimming;
/*  22:    */ import net.minecraft.entity.ai.EntityAITasks;
/*  23:    */ import net.minecraft.entity.ai.EntityAIWander;
/*  24:    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*  25:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  26:    */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*  27:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  28:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  29:    */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*  30:    */ import net.minecraft.entity.passive.EntityVillager;
/*  31:    */ import net.minecraft.entity.player.EntityPlayer;
/*  32:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  33:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  34:    */ import net.minecraft.init.Blocks;
/*  35:    */ import net.minecraft.init.Items;
/*  36:    */ import net.minecraft.item.Item;
/*  37:    */ import net.minecraft.item.ItemStack;
/*  38:    */ import net.minecraft.nbt.NBTTagCompound;
/*  39:    */ import net.minecraft.pathfinding.PathNavigate;
/*  40:    */ import net.minecraft.potion.Potion;
/*  41:    */ import net.minecraft.potion.PotionEffect;
/*  42:    */ import net.minecraft.util.DamageSource;
/*  43:    */ import net.minecraft.util.MathHelper;
/*  44:    */ import net.minecraft.world.EnumDifficulty;
/*  45:    */ import net.minecraft.world.World;
/*  46:    */ 
/*  47:    */ public class EntityZombie
/*  48:    */   extends EntityMob
/*  49:    */ {
/*  50: 41 */   protected static final IAttribute field_110186_bp = new RangedAttribute("zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D).setDescription("Spawn Reinforcements Chance");
/*  51: 42 */   private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
/*  52: 43 */   private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
/*  53: 44 */   private final EntityAIBreakDoor field_146075_bs = new EntityAIBreakDoor(this);
/*  54:    */   private int conversionTime;
/*  55: 50 */   private boolean field_146076_bu = false;
/*  56: 51 */   private float field_146074_bv = -1.0F;
/*  57:    */   private float field_146073_bw;
/*  58:    */   private static final String __OBFID = "CL_00001702";
/*  59:    */   
/*  60:    */   public EntityZombie(World par1World)
/*  61:    */   {
/*  62: 57 */     super(par1World);
/*  63: 58 */     getNavigator().setBreakDoors(true);
/*  64: 59 */     this.tasks.addTask(0, new EntityAISwimming(this));
/*  65: 60 */     this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  66: 61 */     this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
/*  67: 62 */     this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  68: 63 */     this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
/*  69: 64 */     this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
/*  70: 65 */     this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  71: 66 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  72: 67 */     this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
/*  73: 68 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
/*  74: 69 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
/*  75: 70 */     setSize(0.6F, 1.8F);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void applyEntityAttributes()
/*  79:    */   {
/*  80: 75 */     super.applyEntityAttributes();
/*  81: 76 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
/*  82: 77 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2300000041723251D);
/*  83: 78 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
/*  84: 79 */     getAttributeMap().registerAttribute(field_110186_bp).setBaseValue(this.rand.nextDouble() * 0.1000000014901161D);
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void entityInit()
/*  88:    */   {
/*  89: 84 */     super.entityInit();
/*  90: 85 */     getDataWatcher().addObject(12, Byte.valueOf((byte)0));
/*  91: 86 */     getDataWatcher().addObject(13, Byte.valueOf((byte)0));
/*  92: 87 */     getDataWatcher().addObject(14, Byte.valueOf((byte)0));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getTotalArmorValue()
/*  96:    */   {
/*  97: 95 */     int var1 = super.getTotalArmorValue() + 2;
/*  98: 97 */     if (var1 > 20) {
/*  99: 99 */       var1 = 20;
/* 100:    */     }
/* 101:102 */     return var1;
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected boolean isAIEnabled()
/* 105:    */   {
/* 106:110 */     return true;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean func_146072_bX()
/* 110:    */   {
/* 111:115 */     return this.field_146076_bu;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void func_146070_a(boolean p_146070_1_)
/* 115:    */   {
/* 116:120 */     if (this.field_146076_bu != p_146070_1_)
/* 117:    */     {
/* 118:122 */       this.field_146076_bu = p_146070_1_;
/* 119:124 */       if (p_146070_1_) {
/* 120:126 */         this.tasks.addTask(1, this.field_146075_bs);
/* 121:    */       } else {
/* 122:130 */         this.tasks.removeTask(this.field_146075_bs);
/* 123:    */       }
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isChild()
/* 128:    */   {
/* 129:140 */     return getDataWatcher().getWatchableObjectByte(12) == 1;
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
/* 133:    */   {
/* 134:148 */     if (isChild()) {
/* 135:150 */       this.experienceValue = ((int)(this.experienceValue * 2.5F));
/* 136:    */     }
/* 137:153 */     return super.getExperiencePoints(par1EntityPlayer);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setChild(boolean par1)
/* 141:    */   {
/* 142:161 */     getDataWatcher().updateObject(12, Byte.valueOf((byte)(par1 ? 1 : 0)));
/* 143:163 */     if ((this.worldObj != null) && (!this.worldObj.isClient))
/* 144:    */     {
/* 145:165 */       IAttributeInstance var2 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 146:166 */       var2.removeModifier(babySpeedBoostModifier);
/* 147:168 */       if (par1) {
/* 148:170 */         var2.applyModifier(babySpeedBoostModifier);
/* 149:    */       }
/* 150:    */     }
/* 151:174 */     func_146071_k(par1);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isVillager()
/* 155:    */   {
/* 156:182 */     return getDataWatcher().getWatchableObjectByte(13) == 1;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setVillager(boolean par1)
/* 160:    */   {
/* 161:190 */     getDataWatcher().updateObject(13, Byte.valueOf((byte)(par1 ? 1 : 0)));
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void onLivingUpdate()
/* 165:    */   {
/* 166:199 */     if ((this.worldObj.isDaytime()) && (!this.worldObj.isClient) && (!isChild()))
/* 167:    */     {
/* 168:201 */       float var1 = getBrightness(1.0F);
/* 169:203 */       if ((var1 > 0.5F) && (this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) && (this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))))
/* 170:    */       {
/* 171:205 */         boolean var2 = true;
/* 172:206 */         ItemStack var3 = getEquipmentInSlot(4);
/* 173:208 */         if (var3 != null)
/* 174:    */         {
/* 175:210 */           if (var3.isItemStackDamageable())
/* 176:    */           {
/* 177:212 */             var3.setItemDamage(var3.getItemDamageForDisplay() + this.rand.nextInt(2));
/* 178:214 */             if (var3.getItemDamageForDisplay() >= var3.getMaxDamage())
/* 179:    */             {
/* 180:216 */               renderBrokenItemStack(var3);
/* 181:217 */               setCurrentItemOrArmor(4, null);
/* 182:    */             }
/* 183:    */           }
/* 184:221 */           var2 = false;
/* 185:    */         }
/* 186:224 */         if (var2) {
/* 187:226 */           setFire(8);
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:231 */     super.onLivingUpdate();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/* 195:    */   {
/* 196:239 */     if (!super.attackEntityFrom(par1DamageSource, par2)) {
/* 197:241 */       return false;
/* 198:    */     }
/* 199:245 */     EntityLivingBase var3 = getAttackTarget();
/* 200:247 */     if ((var3 == null) && ((getEntityToAttack() instanceof EntityLivingBase))) {
/* 201:249 */       var3 = (EntityLivingBase)getEntityToAttack();
/* 202:    */     }
/* 203:252 */     if ((var3 == null) && ((par1DamageSource.getEntity() instanceof EntityLivingBase))) {
/* 204:254 */       var3 = (EntityLivingBase)par1DamageSource.getEntity();
/* 205:    */     }
/* 206:257 */     if ((var3 != null) && (this.worldObj.difficultySetting == EnumDifficulty.HARD) && (this.rand.nextFloat() < getEntityAttribute(field_110186_bp).getAttributeValue()))
/* 207:    */     {
/* 208:259 */       int var4 = MathHelper.floor_double(this.posX);
/* 209:260 */       int var5 = MathHelper.floor_double(this.posY);
/* 210:261 */       int var6 = MathHelper.floor_double(this.posZ);
/* 211:262 */       EntityZombie var7 = new EntityZombie(this.worldObj);
/* 212:264 */       for (int var8 = 0; var8 < 50; var8++)
/* 213:    */       {
/* 214:266 */         int var9 = var4 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
/* 215:267 */         int var10 = var5 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
/* 216:268 */         int var11 = var6 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
/* 217:270 */         if ((World.doesBlockHaveSolidTopSurface(this.worldObj, var9, var10 - 1, var11)) && (this.worldObj.getBlockLightValue(var9, var10, var11) < 10))
/* 218:    */         {
/* 219:272 */           var7.setPosition(var9, var10, var11);
/* 220:274 */           if ((this.worldObj.checkNoEntityCollision(var7.boundingBox)) && (this.worldObj.getCollidingBoundingBoxes(var7, var7.boundingBox).isEmpty()) && (!this.worldObj.isAnyLiquid(var7.boundingBox)))
/* 221:    */           {
/* 222:276 */             this.worldObj.spawnEntityInWorld(var7);
/* 223:277 */             var7.setAttackTarget(var3);
/* 224:278 */             var7.onSpawnWithEgg(null);
/* 225:279 */             getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.0500000007450581D, 0));
/* 226:280 */             var7.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.0500000007450581D, 0));
/* 227:281 */             break;
/* 228:    */           }
/* 229:    */         }
/* 230:    */       }
/* 231:    */     }
/* 232:287 */     return true;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void onUpdate()
/* 236:    */   {
/* 237:296 */     if ((!this.worldObj.isClient) && (isConverting()))
/* 238:    */     {
/* 239:298 */       int var1 = getConversionTimeBoost();
/* 240:299 */       this.conversionTime -= var1;
/* 241:301 */       if (this.conversionTime <= 0) {
/* 242:303 */         convertToVillager();
/* 243:    */       }
/* 244:    */     }
/* 245:307 */     super.onUpdate();
/* 246:    */   }
/* 247:    */   
/* 248:    */   public boolean attackEntityAsMob(Entity par1Entity)
/* 249:    */   {
/* 250:312 */     boolean var2 = super.attackEntityAsMob(par1Entity);
/* 251:314 */     if (var2)
/* 252:    */     {
/* 253:316 */       int var3 = this.worldObj.difficultySetting.getDifficultyId();
/* 254:318 */       if ((getHeldItem() == null) && (isBurning()) && (this.rand.nextFloat() < var3 * 0.3F)) {
/* 255:320 */         par1Entity.setFire(2 * var3);
/* 256:    */       }
/* 257:    */     }
/* 258:324 */     return var2;
/* 259:    */   }
/* 260:    */   
/* 261:    */   protected String getLivingSound()
/* 262:    */   {
/* 263:332 */     return "mob.zombie.say";
/* 264:    */   }
/* 265:    */   
/* 266:    */   protected String getHurtSound()
/* 267:    */   {
/* 268:340 */     return "mob.zombie.hurt";
/* 269:    */   }
/* 270:    */   
/* 271:    */   protected String getDeathSound()
/* 272:    */   {
/* 273:348 */     return "mob.zombie.death";
/* 274:    */   }
/* 275:    */   
/* 276:    */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/* 277:    */   {
/* 278:353 */     playSound("mob.zombie.step", 0.15F, 1.0F);
/* 279:    */   }
/* 280:    */   
/* 281:    */   protected Item func_146068_u()
/* 282:    */   {
/* 283:358 */     return Items.rotten_flesh;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public EnumCreatureAttribute getCreatureAttribute()
/* 287:    */   {
/* 288:366 */     return EnumCreatureAttribute.UNDEAD;
/* 289:    */   }
/* 290:    */   
/* 291:    */   protected void dropRareDrop(int par1)
/* 292:    */   {
/* 293:371 */     switch (this.rand.nextInt(3))
/* 294:    */     {
/* 295:    */     case 0: 
/* 296:374 */       func_145779_a(Items.iron_ingot, 1);
/* 297:375 */       break;
/* 298:    */     case 1: 
/* 299:378 */       func_145779_a(Items.carrot, 1);
/* 300:379 */       break;
/* 301:    */     case 2: 
/* 302:382 */       func_145779_a(Items.potato, 1);
/* 303:    */     }
/* 304:    */   }
/* 305:    */   
/* 306:    */   protected void addRandomArmor()
/* 307:    */   {
/* 308:391 */     super.addRandomArmor();
/* 309:393 */     if (this.rand.nextFloat() < (this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.05F : 0.01F))
/* 310:    */     {
/* 311:395 */       int var1 = this.rand.nextInt(3);
/* 312:397 */       if (var1 == 0) {
/* 313:399 */         setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
/* 314:    */       } else {
/* 315:403 */         setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
/* 316:    */       }
/* 317:    */     }
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 321:    */   {
/* 322:413 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 323:415 */     if (isChild()) {
/* 324:417 */       par1NBTTagCompound.setBoolean("IsBaby", true);
/* 325:    */     }
/* 326:420 */     if (isVillager()) {
/* 327:422 */       par1NBTTagCompound.setBoolean("IsVillager", true);
/* 328:    */     }
/* 329:425 */     par1NBTTagCompound.setInteger("ConversionTime", isConverting() ? this.conversionTime : -1);
/* 330:426 */     par1NBTTagCompound.setBoolean("CanBreakDoors", func_146072_bX());
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 334:    */   {
/* 335:434 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 336:436 */     if (par1NBTTagCompound.getBoolean("IsBaby")) {
/* 337:438 */       setChild(true);
/* 338:    */     }
/* 339:441 */     if (par1NBTTagCompound.getBoolean("IsVillager")) {
/* 340:443 */       setVillager(true);
/* 341:    */     }
/* 342:446 */     if ((par1NBTTagCompound.func_150297_b("ConversionTime", 99)) && (par1NBTTagCompound.getInteger("ConversionTime") > -1)) {
/* 343:448 */       startConversion(par1NBTTagCompound.getInteger("ConversionTime"));
/* 344:    */     }
/* 345:451 */     func_146070_a(par1NBTTagCompound.getBoolean("CanBreakDoors"));
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void onKillEntity(EntityLivingBase par1EntityLivingBase)
/* 349:    */   {
/* 350:459 */     super.onKillEntity(par1EntityLivingBase);
/* 351:461 */     if (((this.worldObj.difficultySetting == EnumDifficulty.NORMAL) || (this.worldObj.difficultySetting == EnumDifficulty.HARD)) && ((par1EntityLivingBase instanceof EntityVillager)))
/* 352:    */     {
/* 353:463 */       if (this.rand.nextBoolean()) {
/* 354:465 */         return;
/* 355:    */       }
/* 356:468 */       EntityZombie var2 = new EntityZombie(this.worldObj);
/* 357:469 */       var2.copyLocationAndAnglesFrom(par1EntityLivingBase);
/* 358:470 */       this.worldObj.removeEntity(par1EntityLivingBase);
/* 359:471 */       var2.onSpawnWithEgg(null);
/* 360:472 */       var2.setVillager(true);
/* 361:474 */       if (par1EntityLivingBase.isChild()) {
/* 362:476 */         var2.setChild(true);
/* 363:    */       }
/* 364:479 */       this.worldObj.spawnEntityInWorld(var2);
/* 365:480 */       this.worldObj.playAuxSFXAtEntity(null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 366:    */     }
/* 367:    */   }
/* 368:    */   
/* 369:    */   public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
/* 370:    */   {
/* 371:486 */     Object par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);
/* 372:487 */     float var2 = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
/* 373:488 */     setCanPickUpLoot(this.rand.nextFloat() < 0.55F * var2);
/* 374:490 */     if (par1EntityLivingData1 == null) {
/* 375:492 */       par1EntityLivingData1 = new GroupData(this.worldObj.rand.nextFloat() < 0.05F, this.worldObj.rand.nextFloat() < 0.05F, null);
/* 376:    */     }
/* 377:495 */     if ((par1EntityLivingData1 instanceof GroupData))
/* 378:    */     {
/* 379:497 */       GroupData var3 = (GroupData)par1EntityLivingData1;
/* 380:499 */       if (var3.field_142046_b) {
/* 381:501 */         setVillager(true);
/* 382:    */       }
/* 383:504 */       if (var3.field_142048_a) {
/* 384:506 */         setChild(true);
/* 385:    */       }
/* 386:    */     }
/* 387:510 */     func_146070_a(this.rand.nextFloat() < var2 * 0.1F);
/* 388:511 */     addRandomArmor();
/* 389:512 */     enchantEquipment();
/* 390:514 */     if (getEquipmentInSlot(4) == null)
/* 391:    */     {
/* 392:516 */       Calendar var6 = this.worldObj.getCurrentDate();
/* 393:518 */       if ((var6.get(2) + 1 == 10) && (var6.get(5) == 31) && (this.rand.nextFloat() < 0.25F))
/* 394:    */       {
/* 395:520 */         setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
/* 396:521 */         this.equipmentDropChances[4] = 0.0F;
/* 397:    */       }
/* 398:    */     }
/* 399:525 */     getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.0500000007450581D, 0));
/* 400:526 */     double var7 = this.rand.nextDouble() * 1.5D * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
/* 401:528 */     if (var7 > 1.0D) {
/* 402:530 */       getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", var7, 2));
/* 403:    */     }
/* 404:533 */     if (this.rand.nextFloat() < var2 * 0.05F)
/* 405:    */     {
/* 406:535 */       getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
/* 407:536 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
/* 408:537 */       func_146070_a(true);
/* 409:    */     }
/* 410:540 */     return (IEntityLivingData)par1EntityLivingData1;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public boolean interact(EntityPlayer par1EntityPlayer)
/* 414:    */   {
/* 415:548 */     ItemStack var2 = par1EntityPlayer.getCurrentEquippedItem();
/* 416:550 */     if ((var2 != null) && (var2.getItem() == Items.golden_apple) && (var2.getItemDamage() == 0) && (isVillager()) && (isPotionActive(Potion.weakness)))
/* 417:    */     {
/* 418:552 */       if (!par1EntityPlayer.capabilities.isCreativeMode) {
/* 419:554 */         var2.stackSize -= 1;
/* 420:    */       }
/* 421:557 */       if (var2.stackSize <= 0) {
/* 422:559 */         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
/* 423:    */       }
/* 424:562 */       if (!this.worldObj.isClient) {
/* 425:564 */         startConversion(this.rand.nextInt(2401) + 3600);
/* 426:    */       }
/* 427:567 */       return true;
/* 428:    */     }
/* 429:571 */     return false;
/* 430:    */   }
/* 431:    */   
/* 432:    */   protected void startConversion(int par1)
/* 433:    */   {
/* 434:581 */     this.conversionTime = par1;
/* 435:582 */     getDataWatcher().updateObject(14, Byte.valueOf((byte)1));
/* 436:583 */     removePotionEffect(Potion.weakness.id);
/* 437:584 */     addPotionEffect(new PotionEffect(Potion.damageBoost.id, par1, Math.min(this.worldObj.difficultySetting.getDifficultyId() - 1, 0)));
/* 438:585 */     this.worldObj.setEntityState(this, (byte)16);
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void handleHealthUpdate(byte par1)
/* 442:    */   {
/* 443:590 */     if (par1 == 16) {
/* 444:592 */       this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
/* 445:    */     } else {
/* 446:596 */       super.handleHealthUpdate(par1);
/* 447:    */     }
/* 448:    */   }
/* 449:    */   
/* 450:    */   protected boolean canDespawn()
/* 451:    */   {
/* 452:605 */     return !isConverting();
/* 453:    */   }
/* 454:    */   
/* 455:    */   public boolean isConverting()
/* 456:    */   {
/* 457:613 */     return getDataWatcher().getWatchableObjectByte(14) == 1;
/* 458:    */   }
/* 459:    */   
/* 460:    */   protected void convertToVillager()
/* 461:    */   {
/* 462:621 */     EntityVillager var1 = new EntityVillager(this.worldObj);
/* 463:622 */     var1.copyLocationAndAnglesFrom(this);
/* 464:623 */     var1.onSpawnWithEgg(null);
/* 465:624 */     var1.setLookingForHome();
/* 466:626 */     if (isChild()) {
/* 467:628 */       var1.setGrowingAge(-24000);
/* 468:    */     }
/* 469:631 */     this.worldObj.removeEntity(this);
/* 470:632 */     this.worldObj.spawnEntityInWorld(var1);
/* 471:633 */     var1.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
/* 472:634 */     this.worldObj.playAuxSFXAtEntity(null, 1017, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
/* 473:    */   }
/* 474:    */   
/* 475:    */   protected int getConversionTimeBoost()
/* 476:    */   {
/* 477:642 */     int var1 = 1;
/* 478:644 */     if (this.rand.nextFloat() < 0.01F)
/* 479:    */     {
/* 480:646 */       int var2 = 0;
/* 481:648 */       for (int var3 = (int)this.posX - 4; (var3 < (int)this.posX + 4) && (var2 < 14); var3++) {
/* 482:650 */         for (int var4 = (int)this.posY - 4; (var4 < (int)this.posY + 4) && (var2 < 14); var4++) {
/* 483:652 */           for (int var5 = (int)this.posZ - 4; (var5 < (int)this.posZ + 4) && (var2 < 14); var5++)
/* 484:    */           {
/* 485:654 */             Block var6 = this.worldObj.getBlock(var3, var4, var5);
/* 486:656 */             if ((var6 == Blocks.iron_bars) || (var6 == Blocks.bed))
/* 487:    */             {
/* 488:658 */               if (this.rand.nextFloat() < 0.3F) {
/* 489:660 */                 var1++;
/* 490:    */               }
/* 491:663 */               var2++;
/* 492:    */             }
/* 493:    */           }
/* 494:    */         }
/* 495:    */       }
/* 496:    */     }
/* 497:670 */     return var1;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public void func_146071_k(boolean p_146071_1_)
/* 501:    */   {
/* 502:675 */     func_146069_a(p_146071_1_ ? 0.5F : 1.0F);
/* 503:    */   }
/* 504:    */   
/* 505:    */   protected final void setSize(float par1, float par2)
/* 506:    */   {
/* 507:683 */     boolean var3 = (this.field_146074_bv > 0.0F) && (this.field_146073_bw > 0.0F);
/* 508:684 */     this.field_146074_bv = par1;
/* 509:685 */     this.field_146073_bw = par2;
/* 510:687 */     if (!var3) {
/* 511:689 */       func_146069_a(1.0F);
/* 512:    */     }
/* 513:    */   }
/* 514:    */   
/* 515:    */   protected final void func_146069_a(float p_146069_1_)
/* 516:    */   {
/* 517:695 */     super.setSize(this.field_146074_bv * p_146069_1_, this.field_146073_bw * p_146069_1_);
/* 518:    */   }
/* 519:    */   
/* 520:    */   class GroupData
/* 521:    */     implements IEntityLivingData
/* 522:    */   {
/* 523:    */     public boolean field_142048_a;
/* 524:    */     public boolean field_142046_b;
/* 525:    */     private static final String __OBFID = "CL_00001704";
/* 526:    */     
/* 527:    */     private GroupData(boolean par2, boolean par3)
/* 528:    */     {
/* 529:706 */       this.field_142048_a = false;
/* 530:707 */       this.field_142046_b = false;
/* 531:708 */       this.field_142048_a = par2;
/* 532:709 */       this.field_142046_b = par3;
/* 533:    */     }
/* 534:    */     
/* 535:    */     GroupData(boolean par2, boolean par3, Object par4EntityZombieINNER1)
/* 536:    */     {
/* 537:714 */       this(par2, par3);
/* 538:    */     }
/* 539:    */   }
/* 540:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.monster.EntityZombie
 * JD-Core Version:    0.7.0.1
 */