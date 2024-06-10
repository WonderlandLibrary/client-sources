/*    1:     */ package net.minecraft.entity;
/*    2:     */ 
/*    3:     */ import java.util.Collection;
/*    4:     */ import java.util.HashMap;
/*    5:     */ import java.util.Iterator;
/*    6:     */ import java.util.List;
/*    7:     */ import java.util.Random;
/*    8:     */ import java.util.Set;
/*    9:     */ import java.util.UUID;
/*   10:     */ import net.minecraft.block.Block;
/*   11:     */ import net.minecraft.block.Block.SoundType;
/*   12:     */ import net.minecraft.block.material.Material;
/*   13:     */ import net.minecraft.enchantment.EnchantmentHelper;
/*   14:     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*   15:     */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*   16:     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*   17:     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   18:     */ import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
/*   19:     */ import net.minecraft.entity.item.EntityItem;
/*   20:     */ import net.minecraft.entity.item.EntityXPOrb;
/*   21:     */ import net.minecraft.entity.monster.EntityZombie;
/*   22:     */ import net.minecraft.entity.passive.EntityWolf;
/*   23:     */ import net.minecraft.entity.player.EntityPlayer;
/*   24:     */ import net.minecraft.entity.player.EntityPlayerMP;
/*   25:     */ import net.minecraft.entity.player.PlayerCapabilities;
/*   26:     */ import net.minecraft.entity.projectile.EntityArrow;
/*   27:     */ import net.minecraft.init.Blocks;
/*   28:     */ import net.minecraft.item.Item;
/*   29:     */ import net.minecraft.item.ItemArmor;
/*   30:     */ import net.minecraft.item.ItemStack;
/*   31:     */ import net.minecraft.nbt.NBTBase;
/*   32:     */ import net.minecraft.nbt.NBTTagCompound;
/*   33:     */ import net.minecraft.nbt.NBTTagFloat;
/*   34:     */ import net.minecraft.nbt.NBTTagList;
/*   35:     */ import net.minecraft.nbt.NBTTagShort;
/*   36:     */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*   37:     */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*   38:     */ import net.minecraft.network.play.server.S0DPacketCollectItem;
/*   39:     */ import net.minecraft.potion.Potion;
/*   40:     */ import net.minecraft.potion.PotionEffect;
/*   41:     */ import net.minecraft.potion.PotionHelper;
/*   42:     */ import net.minecraft.profiler.Profiler;
/*   43:     */ import net.minecraft.scoreboard.Team;
/*   44:     */ import net.minecraft.util.AxisAlignedBB;
/*   45:     */ import net.minecraft.util.CombatTracker;
/*   46:     */ import net.minecraft.util.DamageSource;
/*   47:     */ import net.minecraft.util.IIcon;
/*   48:     */ import net.minecraft.util.MathHelper;
/*   49:     */ import net.minecraft.util.MovingObjectPosition;
/*   50:     */ import net.minecraft.util.Vec3;
/*   51:     */ import net.minecraft.util.Vec3Pool;
/*   52:     */ import net.minecraft.world.GameRules;
/*   53:     */ import net.minecraft.world.World;
/*   54:     */ import net.minecraft.world.WorldServer;
/*   55:     */ import net.minecraft.world.chunk.Chunk;
/*   56:     */ 
/*   57:     */ public abstract class EntityLivingBase
/*   58:     */   extends Entity
/*   59:     */ {
/*   60:  52 */   private static final UUID sprintingSpeedBoostModifierUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
/*   61:  53 */   private static final AttributeModifier sprintingSpeedBoostModifier = new AttributeModifier(sprintingSpeedBoostModifierUUID, "Sprinting speed boost", 0.300000011920929D, 2).setSaved(false);
/*   62:     */   private BaseAttributeMap attributeMap;
/*   63:  55 */   private final CombatTracker _combatTracker = new CombatTracker(this);
/*   64:  56 */   private final HashMap activePotionsMap = new HashMap();
/*   65:  59 */   private final ItemStack[] previousEquipment = new ItemStack[5];
/*   66:     */   public boolean isSwingInProgress;
/*   67:     */   public int swingProgressInt;
/*   68:     */   public int arrowHitTimer;
/*   69:     */   public float prevHealth;
/*   70:     */   public int hurtTime;
/*   71:     */   public int maxHurtTime;
/*   72:     */   public float attackedAtYaw;
/*   73:     */   public int deathTime;
/*   74:     */   public int attackTime;
/*   75:     */   public float prevSwingProgress;
/*   76:     */   public float swingProgress;
/*   77:     */   public float prevLimbSwingAmount;
/*   78:     */   public float limbSwingAmount;
/*   79:     */   public float limbSwing;
/*   80:  93 */   public int maxHurtResistantTime = 20;
/*   81:     */   public float prevCameraPitch;
/*   82:     */   public float cameraPitch;
/*   83:     */   public float field_70769_ao;
/*   84:     */   public float field_70770_ap;
/*   85:     */   public float renderYawOffset;
/*   86:     */   public float prevRenderYawOffset;
/*   87:     */   public float rotationYawHead;
/*   88:     */   public float prevRotationYawHead;
/*   89: 110 */   public float jumpMovementFactor = 0.02F;
/*   90:     */   protected EntityPlayer attackingPlayer;
/*   91:     */   protected int recentlyHit;
/*   92:     */   protected boolean dead;
/*   93:     */   public int entityAge;
/*   94:     */   protected float field_70768_au;
/*   95:     */   protected float field_110154_aX;
/*   96:     */   protected float field_70764_aw;
/*   97:     */   protected float field_70763_ax;
/*   98:     */   protected float field_70741_aB;
/*   99:     */   protected int scoreValue;
/*  100:     */   protected float lastDamage;
/*  101:     */   protected boolean isJumping;
/*  102:     */   public float moveStrafing;
/*  103:     */   public float moveForward;
/*  104:     */   protected float randomYawVelocity;
/*  105:     */   protected int newPosRotationIncrements;
/*  106:     */   protected double newPosX;
/*  107:     */   protected double newPosY;
/*  108:     */   protected double newPosZ;
/*  109:     */   protected double newRotationYaw;
/*  110:     */   protected double newRotationPitch;
/*  111: 167 */   private boolean potionsNeedUpdate = true;
/*  112:     */   private EntityLivingBase entityLivingToAttack;
/*  113:     */   private int revengeTimer;
/*  114:     */   private EntityLivingBase lastAttacker;
/*  115:     */   private int lastAttackerTime;
/*  116:     */   public float landMovementFactor;
/*  117:     */   private int jumpTicks;
/*  118:     */   private float field_110151_bq;
/*  119:     */   private static final String __OBFID = "CL_00001549";
/*  120:     */   
/*  121:     */   public EntityLivingBase(World par1World)
/*  122:     */   {
/*  123: 190 */     super(par1World);
/*  124: 191 */     applyEntityAttributes();
/*  125: 192 */     setHealth(getMaxHealth());
/*  126: 193 */     this.preventEntitySpawning = true;
/*  127: 194 */     this.field_70770_ap = ((float)(Math.random() + 1.0D) * 0.01F);
/*  128: 195 */     setPosition(this.posX, this.posY, this.posZ);
/*  129: 196 */     this.field_70769_ao = ((float)Math.random() * 12398.0F);
/*  130: 197 */     this.rotationYaw = ((float)(Math.random() * 3.141592653589793D * 2.0D));
/*  131: 198 */     this.rotationYawHead = this.rotationYaw;
/*  132: 199 */     this.stepHeight = 0.5F;
/*  133:     */   }
/*  134:     */   
/*  135:     */   protected void entityInit()
/*  136:     */   {
/*  137: 204 */     this.dataWatcher.addObject(7, Integer.valueOf(0));
/*  138: 205 */     this.dataWatcher.addObject(8, Byte.valueOf((byte)0));
/*  139: 206 */     this.dataWatcher.addObject(9, Byte.valueOf((byte)0));
/*  140: 207 */     this.dataWatcher.addObject(6, Float.valueOf(1.0F));
/*  141:     */   }
/*  142:     */   
/*  143:     */   protected void applyEntityAttributes()
/*  144:     */   {
/*  145: 212 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
/*  146: 213 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
/*  147: 214 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
/*  148: 216 */     if (!isAIEnabled()) {
/*  149: 218 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.1000000014901161D);
/*  150:     */     }
/*  151:     */   }
/*  152:     */   
/*  153:     */   protected void updateFallState(double par1, boolean par3)
/*  154:     */   {
/*  155: 228 */     if (!isInWater()) {
/*  156: 230 */       handleWaterMovement();
/*  157:     */     }
/*  158: 233 */     if ((par3) && (this.fallDistance > 0.0F))
/*  159:     */     {
/*  160: 235 */       int var4 = MathHelper.floor_double(this.posX);
/*  161: 236 */       int var5 = MathHelper.floor_double(this.posY - 0.2000000029802322D - this.yOffset);
/*  162: 237 */       int var6 = MathHelper.floor_double(this.posZ);
/*  163: 238 */       Block var7 = this.worldObj.getBlock(var4, var5, var6);
/*  164: 240 */       if (var7.getMaterial() == Material.air)
/*  165:     */       {
/*  166: 242 */         int var8 = this.worldObj.getBlock(var4, var5 - 1, var6).getRenderType();
/*  167: 244 */         if ((var8 == 11) || (var8 == 32) || (var8 == 21)) {
/*  168: 246 */           var7 = this.worldObj.getBlock(var4, var5 - 1, var6);
/*  169:     */         }
/*  170:     */       }
/*  171: 249 */       else if ((!this.worldObj.isClient) && (this.fallDistance > 3.0F))
/*  172:     */       {
/*  173: 251 */         this.worldObj.playAuxSFX(2006, var4, var5, var6, MathHelper.ceiling_float_int(this.fallDistance - 3.0F));
/*  174:     */       }
/*  175: 254 */       var7.onFallenUpon(this.worldObj, var4, var5, var6, this, this.fallDistance);
/*  176:     */     }
/*  177: 257 */     super.updateFallState(par1, par3);
/*  178:     */   }
/*  179:     */   
/*  180:     */   public boolean canBreatheUnderwater()
/*  181:     */   {
/*  182: 262 */     return false;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public void onEntityUpdate()
/*  186:     */   {
/*  187: 270 */     this.prevSwingProgress = this.swingProgress;
/*  188: 271 */     super.onEntityUpdate();
/*  189: 272 */     this.worldObj.theProfiler.startSection("livingEntityBaseTick");
/*  190: 274 */     if ((isEntityAlive()) && (isEntityInsideOpaqueBlock())) {
/*  191: 276 */       attackEntityFrom(DamageSource.inWall, 1.0F);
/*  192:     */     }
/*  193: 279 */     if ((isImmuneToFire()) || (this.worldObj.isClient)) {
/*  194: 281 */       extinguish();
/*  195:     */     }
/*  196: 284 */     boolean var1 = ((this instanceof EntityPlayer)) && (((EntityPlayer)this).capabilities.disableDamage);
/*  197: 286 */     if ((isEntityAlive()) && (isInsideOfMaterial(Material.water)))
/*  198:     */     {
/*  199: 288 */       if ((!canBreatheUnderwater()) && (!isPotionActive(Potion.waterBreathing.id)) && (!var1))
/*  200:     */       {
/*  201: 290 */         setAir(decreaseAirSupply(getAir()));
/*  202: 292 */         if (getAir() == -20)
/*  203:     */         {
/*  204: 294 */           setAir(0);
/*  205: 296 */           for (int var2 = 0; var2 < 8; var2++)
/*  206:     */           {
/*  207: 298 */             float var3 = this.rand.nextFloat() - this.rand.nextFloat();
/*  208: 299 */             float var4 = this.rand.nextFloat() - this.rand.nextFloat();
/*  209: 300 */             float var5 = this.rand.nextFloat() - this.rand.nextFloat();
/*  210: 301 */             this.worldObj.spawnParticle("bubble", this.posX + var3, this.posY + var4, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
/*  211:     */           }
/*  212: 304 */           attackEntityFrom(DamageSource.drown, 2.0F);
/*  213:     */         }
/*  214:     */       }
/*  215: 308 */       if ((!this.worldObj.isClient) && (isRiding()) && ((this.ridingEntity instanceof EntityLivingBase))) {
/*  216: 310 */         mountEntity(null);
/*  217:     */       }
/*  218:     */     }
/*  219:     */     else
/*  220:     */     {
/*  221: 315 */       setAir(300);
/*  222:     */     }
/*  223: 318 */     if ((isEntityAlive()) && (isWet())) {
/*  224: 320 */       extinguish();
/*  225:     */     }
/*  226: 323 */     this.prevCameraPitch = this.cameraPitch;
/*  227: 325 */     if (this.attackTime > 0) {
/*  228: 327 */       this.attackTime -= 1;
/*  229:     */     }
/*  230: 330 */     if (this.hurtTime > 0) {
/*  231: 332 */       this.hurtTime -= 1;
/*  232:     */     }
/*  233: 335 */     if ((this.hurtResistantTime > 0) && (!(this instanceof EntityPlayerMP))) {
/*  234: 337 */       this.hurtResistantTime -= 1;
/*  235:     */     }
/*  236: 340 */     if (getHealth() <= 0.0F) {
/*  237: 342 */       onDeathUpdate();
/*  238:     */     }
/*  239: 345 */     if (this.recentlyHit > 0) {
/*  240: 347 */       this.recentlyHit -= 1;
/*  241:     */     } else {
/*  242: 351 */       this.attackingPlayer = null;
/*  243:     */     }
/*  244: 354 */     if ((this.lastAttacker != null) && (!this.lastAttacker.isEntityAlive())) {
/*  245: 356 */       this.lastAttacker = null;
/*  246:     */     }
/*  247: 359 */     if (this.entityLivingToAttack != null) {
/*  248: 361 */       if (!this.entityLivingToAttack.isEntityAlive()) {
/*  249: 363 */         setRevengeTarget(null);
/*  250: 365 */       } else if (this.ticksExisted - this.revengeTimer > 100) {
/*  251: 367 */         setRevengeTarget(null);
/*  252:     */       }
/*  253:     */     }
/*  254: 371 */     updatePotionEffects();
/*  255: 372 */     this.field_70763_ax = this.field_70764_aw;
/*  256: 373 */     this.prevRenderYawOffset = this.renderYawOffset;
/*  257: 374 */     this.prevRotationYawHead = this.rotationYawHead;
/*  258: 375 */     this.prevRotationYaw = this.rotationYaw;
/*  259: 376 */     this.prevRotationPitch = this.rotationPitch;
/*  260: 377 */     this.worldObj.theProfiler.endSection();
/*  261:     */   }
/*  262:     */   
/*  263:     */   public boolean isChild()
/*  264:     */   {
/*  265: 385 */     return false;
/*  266:     */   }
/*  267:     */   
/*  268:     */   protected void onDeathUpdate()
/*  269:     */   {
/*  270: 393 */     this.deathTime += 1;
/*  271: 395 */     if (this.deathTime == 20)
/*  272:     */     {
/*  273: 399 */       if ((!this.worldObj.isClient) && ((this.recentlyHit > 0) || (isPlayer())) && (func_146066_aG()) && (this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
/*  274:     */       {
/*  275: 401 */         int var1 = getExperiencePoints(this.attackingPlayer);
/*  276: 403 */         while (var1 > 0)
/*  277:     */         {
/*  278: 405 */           int var2 = EntityXPOrb.getXPSplit(var1);
/*  279: 406 */           var1 -= var2;
/*  280: 407 */           this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var2));
/*  281:     */         }
/*  282:     */       }
/*  283: 411 */       setDead();
/*  284: 413 */       for (int var1 = 0; var1 < 20; var1++)
/*  285:     */       {
/*  286: 415 */         double var8 = this.rand.nextGaussian() * 0.02D;
/*  287: 416 */         double var4 = this.rand.nextGaussian() * 0.02D;
/*  288: 417 */         double var6 = this.rand.nextGaussian() * 0.02D;
/*  289: 418 */         this.worldObj.spawnParticle("explode", this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var8, var4, var6);
/*  290:     */       }
/*  291:     */     }
/*  292:     */   }
/*  293:     */   
/*  294:     */   protected boolean func_146066_aG()
/*  295:     */   {
/*  296: 425 */     return !isChild();
/*  297:     */   }
/*  298:     */   
/*  299:     */   protected int decreaseAirSupply(int par1)
/*  300:     */   {
/*  301: 433 */     int var2 = EnchantmentHelper.getRespiration(this);
/*  302: 434 */     return (var2 > 0) && (this.rand.nextInt(var2 + 1) > 0) ? par1 : par1 - 1;
/*  303:     */   }
/*  304:     */   
/*  305:     */   protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
/*  306:     */   {
/*  307: 442 */     return 0;
/*  308:     */   }
/*  309:     */   
/*  310:     */   protected boolean isPlayer()
/*  311:     */   {
/*  312: 450 */     return false;
/*  313:     */   }
/*  314:     */   
/*  315:     */   public Random getRNG()
/*  316:     */   {
/*  317: 455 */     return this.rand;
/*  318:     */   }
/*  319:     */   
/*  320:     */   public EntityLivingBase getAITarget()
/*  321:     */   {
/*  322: 460 */     return this.entityLivingToAttack;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public int func_142015_aE()
/*  326:     */   {
/*  327: 465 */     return this.revengeTimer;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public void setRevengeTarget(EntityLivingBase par1EntityLivingBase)
/*  331:     */   {
/*  332: 470 */     this.entityLivingToAttack = par1EntityLivingBase;
/*  333: 471 */     this.revengeTimer = this.ticksExisted;
/*  334:     */   }
/*  335:     */   
/*  336:     */   public EntityLivingBase getLastAttacker()
/*  337:     */   {
/*  338: 476 */     return this.lastAttacker;
/*  339:     */   }
/*  340:     */   
/*  341:     */   public int getLastAttackerTime()
/*  342:     */   {
/*  343: 481 */     return this.lastAttackerTime;
/*  344:     */   }
/*  345:     */   
/*  346:     */   public void setLastAttacker(Entity par1Entity)
/*  347:     */   {
/*  348: 486 */     if ((par1Entity instanceof EntityLivingBase)) {
/*  349: 488 */       this.lastAttacker = ((EntityLivingBase)par1Entity);
/*  350:     */     } else {
/*  351: 492 */       this.lastAttacker = null;
/*  352:     */     }
/*  353: 495 */     this.lastAttackerTime = this.ticksExisted;
/*  354:     */   }
/*  355:     */   
/*  356:     */   public int getAge()
/*  357:     */   {
/*  358: 500 */     return this.entityAge;
/*  359:     */   }
/*  360:     */   
/*  361:     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  362:     */   {
/*  363: 508 */     par1NBTTagCompound.setFloat("HealF", getHealth());
/*  364: 509 */     par1NBTTagCompound.setShort("Health", (short)(int)Math.ceil(getHealth()));
/*  365: 510 */     par1NBTTagCompound.setShort("HurtTime", (short)this.hurtTime);
/*  366: 511 */     par1NBTTagCompound.setShort("DeathTime", (short)this.deathTime);
/*  367: 512 */     par1NBTTagCompound.setShort("AttackTime", (short)this.attackTime);
/*  368: 513 */     par1NBTTagCompound.setFloat("AbsorptionAmount", getAbsorptionAmount());
/*  369: 514 */     ItemStack[] var2 = getLastActiveItems();
/*  370: 515 */     int var3 = var2.length;
/*  371: 519 */     for (int var4 = 0; var4 < var3; var4++)
/*  372:     */     {
/*  373: 521 */       ItemStack var5 = var2[var4];
/*  374: 523 */       if (var5 != null) {
/*  375: 525 */         this.attributeMap.removeAttributeModifiers(var5.getAttributeModifiers());
/*  376:     */       }
/*  377:     */     }
/*  378: 529 */     par1NBTTagCompound.setTag("Attributes", SharedMonsterAttributes.writeBaseAttributeMapToNBT(getAttributeMap()));
/*  379: 530 */     var2 = getLastActiveItems();
/*  380: 531 */     var3 = var2.length;
/*  381: 533 */     for (var4 = 0; var4 < var3; var4++)
/*  382:     */     {
/*  383: 535 */       ItemStack var5 = var2[var4];
/*  384: 537 */       if (var5 != null) {
/*  385: 539 */         this.attributeMap.applyAttributeModifiers(var5.getAttributeModifiers());
/*  386:     */       }
/*  387:     */     }
/*  388: 543 */     if (!this.activePotionsMap.isEmpty())
/*  389:     */     {
/*  390: 545 */       NBTTagList var6 = new NBTTagList();
/*  391: 546 */       Iterator var7 = this.activePotionsMap.values().iterator();
/*  392: 548 */       while (var7.hasNext())
/*  393:     */       {
/*  394: 550 */         PotionEffect var8 = (PotionEffect)var7.next();
/*  395: 551 */         var6.appendTag(var8.writeCustomPotionEffectToNBT(new NBTTagCompound()));
/*  396:     */       }
/*  397: 554 */       par1NBTTagCompound.setTag("ActiveEffects", var6);
/*  398:     */     }
/*  399:     */   }
/*  400:     */   
/*  401:     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  402:     */   {
/*  403: 563 */     setAbsorptionAmount(par1NBTTagCompound.getFloat("AbsorptionAmount"));
/*  404: 565 */     if ((par1NBTTagCompound.func_150297_b("Attributes", 9)) && (this.worldObj != null) && (!this.worldObj.isClient)) {
/*  405: 567 */       SharedMonsterAttributes.func_151475_a(getAttributeMap(), par1NBTTagCompound.getTagList("Attributes", 10));
/*  406:     */     }
/*  407: 570 */     if (par1NBTTagCompound.func_150297_b("ActiveEffects", 9))
/*  408:     */     {
/*  409: 572 */       NBTTagList var2 = par1NBTTagCompound.getTagList("ActiveEffects", 10);
/*  410: 574 */       for (int var3 = 0; var3 < var2.tagCount(); var3++)
/*  411:     */       {
/*  412: 576 */         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/*  413: 577 */         PotionEffect var5 = PotionEffect.readCustomPotionEffectFromNBT(var4);
/*  414: 579 */         if (var5 != null) {
/*  415: 581 */           this.activePotionsMap.put(Integer.valueOf(var5.getPotionID()), var5);
/*  416:     */         }
/*  417:     */       }
/*  418:     */     }
/*  419: 586 */     if (par1NBTTagCompound.func_150297_b("HealF", 99))
/*  420:     */     {
/*  421: 588 */       setHealth(par1NBTTagCompound.getFloat("HealF"));
/*  422:     */     }
/*  423:     */     else
/*  424:     */     {
/*  425: 592 */       NBTBase var6 = par1NBTTagCompound.getTag("Health");
/*  426: 594 */       if (var6 == null) {
/*  427: 596 */         setHealth(getMaxHealth());
/*  428: 598 */       } else if (var6.getId() == 5) {
/*  429: 600 */         setHealth(((NBTTagFloat)var6).func_150288_h());
/*  430: 602 */       } else if (var6.getId() == 2) {
/*  431: 604 */         setHealth(((NBTTagShort)var6).func_150289_e());
/*  432:     */       }
/*  433:     */     }
/*  434: 608 */     this.hurtTime = par1NBTTagCompound.getShort("HurtTime");
/*  435: 609 */     this.deathTime = par1NBTTagCompound.getShort("DeathTime");
/*  436: 610 */     this.attackTime = par1NBTTagCompound.getShort("AttackTime");
/*  437:     */   }
/*  438:     */   
/*  439:     */   protected void updatePotionEffects()
/*  440:     */   {
/*  441: 615 */     Iterator var1 = this.activePotionsMap.keySet().iterator();
/*  442: 617 */     while (var1.hasNext())
/*  443:     */     {
/*  444: 619 */       Integer var2 = (Integer)var1.next();
/*  445: 620 */       PotionEffect var3 = (PotionEffect)this.activePotionsMap.get(var2);
/*  446: 622 */       if (!var3.onUpdate(this))
/*  447:     */       {
/*  448: 624 */         if (!this.worldObj.isClient)
/*  449:     */         {
/*  450: 626 */           var1.remove();
/*  451: 627 */           onFinishedPotionEffect(var3);
/*  452:     */         }
/*  453:     */       }
/*  454: 630 */       else if (var3.getDuration() % 600 == 0) {
/*  455: 632 */         onChangedPotionEffect(var3, false);
/*  456:     */       }
/*  457:     */     }
/*  458: 638 */     if (this.potionsNeedUpdate)
/*  459:     */     {
/*  460: 640 */       if (!this.worldObj.isClient) {
/*  461: 642 */         if (this.activePotionsMap.isEmpty())
/*  462:     */         {
/*  463: 644 */           this.dataWatcher.updateObject(8, Byte.valueOf((byte)0));
/*  464: 645 */           this.dataWatcher.updateObject(7, Integer.valueOf(0));
/*  465: 646 */           setInvisible(false);
/*  466:     */         }
/*  467:     */         else
/*  468:     */         {
/*  469: 650 */           int var11 = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
/*  470: 651 */           this.dataWatcher.updateObject(8, Byte.valueOf((byte)(PotionHelper.func_82817_b(this.activePotionsMap.values()) ? 1 : 0)));
/*  471: 652 */           this.dataWatcher.updateObject(7, Integer.valueOf(var11));
/*  472: 653 */           setInvisible(isPotionActive(Potion.invisibility.id));
/*  473:     */         }
/*  474:     */       }
/*  475: 657 */       this.potionsNeedUpdate = false;
/*  476:     */     }
/*  477: 660 */     int var11 = this.dataWatcher.getWatchableObjectInt(7);
/*  478: 661 */     boolean var12 = this.dataWatcher.getWatchableObjectByte(8) > 0;
/*  479: 663 */     if (var11 > 0)
/*  480:     */     {
/*  481: 665 */       boolean var4 = false;
/*  482: 667 */       if (!isInvisible()) {
/*  483: 669 */         var4 = this.rand.nextBoolean();
/*  484:     */       } else {
/*  485: 673 */         var4 = this.rand.nextInt(15) == 0;
/*  486:     */       }
/*  487: 676 */       if (var12) {
/*  488: 678 */         var4 &= this.rand.nextInt(5) == 0;
/*  489:     */       }
/*  490: 681 */       if ((var4) && (var11 > 0))
/*  491:     */       {
/*  492: 683 */         double var5 = (var11 >> 16 & 0xFF) / 255.0D;
/*  493: 684 */         double var7 = (var11 >> 8 & 0xFF) / 255.0D;
/*  494: 685 */         double var9 = (var11 >> 0 & 0xFF) / 255.0D;
/*  495: 686 */         this.worldObj.spawnParticle(var12 ? "mobSpellAmbient" : "mobSpell", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - this.yOffset, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, var5, var7, var9);
/*  496:     */       }
/*  497:     */     }
/*  498:     */   }
/*  499:     */   
/*  500:     */   public void clearActivePotions()
/*  501:     */   {
/*  502: 693 */     Iterator var1 = this.activePotionsMap.keySet().iterator();
/*  503: 695 */     while (var1.hasNext())
/*  504:     */     {
/*  505: 697 */       Integer var2 = (Integer)var1.next();
/*  506: 698 */       PotionEffect var3 = (PotionEffect)this.activePotionsMap.get(var2);
/*  507: 700 */       if (!this.worldObj.isClient)
/*  508:     */       {
/*  509: 702 */         var1.remove();
/*  510: 703 */         onFinishedPotionEffect(var3);
/*  511:     */       }
/*  512:     */     }
/*  513:     */   }
/*  514:     */   
/*  515:     */   public Collection getActivePotionEffects()
/*  516:     */   {
/*  517: 710 */     return this.activePotionsMap.values();
/*  518:     */   }
/*  519:     */   
/*  520:     */   public boolean isPotionActive(int par1)
/*  521:     */   {
/*  522: 715 */     return this.activePotionsMap.containsKey(Integer.valueOf(par1));
/*  523:     */   }
/*  524:     */   
/*  525:     */   public boolean isPotionActive(Potion par1Potion)
/*  526:     */   {
/*  527: 720 */     return this.activePotionsMap.containsKey(Integer.valueOf(par1Potion.id));
/*  528:     */   }
/*  529:     */   
/*  530:     */   public PotionEffect getActivePotionEffect(Potion par1Potion)
/*  531:     */   {
/*  532: 728 */     return (PotionEffect)this.activePotionsMap.get(Integer.valueOf(par1Potion.id));
/*  533:     */   }
/*  534:     */   
/*  535:     */   public void addPotionEffect(PotionEffect par1PotionEffect)
/*  536:     */   {
/*  537: 736 */     if (isPotionApplicable(par1PotionEffect)) {
/*  538: 738 */       if (this.activePotionsMap.containsKey(Integer.valueOf(par1PotionEffect.getPotionID())))
/*  539:     */       {
/*  540: 740 */         ((PotionEffect)this.activePotionsMap.get(Integer.valueOf(par1PotionEffect.getPotionID()))).combine(par1PotionEffect);
/*  541: 741 */         onChangedPotionEffect((PotionEffect)this.activePotionsMap.get(Integer.valueOf(par1PotionEffect.getPotionID())), true);
/*  542:     */       }
/*  543:     */       else
/*  544:     */       {
/*  545: 745 */         this.activePotionsMap.put(Integer.valueOf(par1PotionEffect.getPotionID()), par1PotionEffect);
/*  546: 746 */         onNewPotionEffect(par1PotionEffect);
/*  547:     */       }
/*  548:     */     }
/*  549:     */   }
/*  550:     */   
/*  551:     */   public boolean isPotionApplicable(PotionEffect par1PotionEffect)
/*  552:     */   {
/*  553: 753 */     if (getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
/*  554:     */     {
/*  555: 755 */       int var2 = par1PotionEffect.getPotionID();
/*  556: 757 */       if ((var2 == Potion.regeneration.id) || (var2 == Potion.poison.id)) {
/*  557: 759 */         return false;
/*  558:     */       }
/*  559:     */     }
/*  560: 763 */     return true;
/*  561:     */   }
/*  562:     */   
/*  563:     */   public boolean isEntityUndead()
/*  564:     */   {
/*  565: 771 */     return getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
/*  566:     */   }
/*  567:     */   
/*  568:     */   public void removePotionEffectClient(int par1)
/*  569:     */   {
/*  570: 779 */     this.activePotionsMap.remove(Integer.valueOf(par1));
/*  571:     */   }
/*  572:     */   
/*  573:     */   public void removePotionEffect(int par1)
/*  574:     */   {
/*  575: 787 */     PotionEffect var2 = (PotionEffect)this.activePotionsMap.remove(Integer.valueOf(par1));
/*  576: 789 */     if (var2 != null) {
/*  577: 791 */       onFinishedPotionEffect(var2);
/*  578:     */     }
/*  579:     */   }
/*  580:     */   
/*  581:     */   protected void onNewPotionEffect(PotionEffect par1PotionEffect)
/*  582:     */   {
/*  583: 797 */     this.potionsNeedUpdate = true;
/*  584: 799 */     if (!this.worldObj.isClient) {
/*  585: 801 */       Potion.potionTypes[par1PotionEffect.getPotionID()].applyAttributesModifiersToEntity(this, getAttributeMap(), par1PotionEffect.getAmplifier());
/*  586:     */     }
/*  587:     */   }
/*  588:     */   
/*  589:     */   protected void onChangedPotionEffect(PotionEffect par1PotionEffect, boolean par2)
/*  590:     */   {
/*  591: 807 */     this.potionsNeedUpdate = true;
/*  592: 809 */     if ((par2) && (!this.worldObj.isClient))
/*  593:     */     {
/*  594: 811 */       Potion.potionTypes[par1PotionEffect.getPotionID()].removeAttributesModifiersFromEntity(this, getAttributeMap(), par1PotionEffect.getAmplifier());
/*  595: 812 */       Potion.potionTypes[par1PotionEffect.getPotionID()].applyAttributesModifiersToEntity(this, getAttributeMap(), par1PotionEffect.getAmplifier());
/*  596:     */     }
/*  597:     */   }
/*  598:     */   
/*  599:     */   protected void onFinishedPotionEffect(PotionEffect par1PotionEffect)
/*  600:     */   {
/*  601: 818 */     this.potionsNeedUpdate = true;
/*  602: 820 */     if (!this.worldObj.isClient) {
/*  603: 822 */       Potion.potionTypes[par1PotionEffect.getPotionID()].removeAttributesModifiersFromEntity(this, getAttributeMap(), par1PotionEffect.getAmplifier());
/*  604:     */     }
/*  605:     */   }
/*  606:     */   
/*  607:     */   public void heal(float par1)
/*  608:     */   {
/*  609: 831 */     float var2 = getHealth();
/*  610: 833 */     if (var2 > 0.0F) {
/*  611: 835 */       setHealth(var2 + par1);
/*  612:     */     }
/*  613:     */   }
/*  614:     */   
/*  615:     */   public final float getHealth()
/*  616:     */   {
/*  617: 841 */     return this.dataWatcher.getWatchableObjectFloat(6);
/*  618:     */   }
/*  619:     */   
/*  620:     */   public void setHealth(float par1)
/*  621:     */   {
/*  622: 846 */     this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(par1, 0.0F, getMaxHealth())));
/*  623:     */   }
/*  624:     */   
/*  625:     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  626:     */   {
/*  627: 854 */     if (isEntityInvulnerable()) {
/*  628: 856 */       return false;
/*  629:     */     }
/*  630: 858 */     if (this.worldObj.isClient) {
/*  631: 860 */       return false;
/*  632:     */     }
/*  633: 864 */     this.entityAge = 0;
/*  634: 866 */     if (getHealth() <= 0.0F) {
/*  635: 868 */       return false;
/*  636:     */     }
/*  637: 870 */     if ((par1DamageSource.isFireDamage()) && (isPotionActive(Potion.fireResistance))) {
/*  638: 872 */       return false;
/*  639:     */     }
/*  640: 876 */     if (((par1DamageSource == DamageSource.anvil) || (par1DamageSource == DamageSource.fallingBlock)) && (getEquipmentInSlot(4) != null))
/*  641:     */     {
/*  642: 878 */       getEquipmentInSlot(4).damageItem((int)(par2 * 4.0F + this.rand.nextFloat() * par2 * 2.0F), this);
/*  643: 879 */       par2 *= 0.75F;
/*  644:     */     }
/*  645: 882 */     this.limbSwingAmount = 1.5F;
/*  646: 883 */     boolean var3 = true;
/*  647: 885 */     if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0F)
/*  648:     */     {
/*  649: 887 */       if (par2 <= this.lastDamage) {
/*  650: 889 */         return false;
/*  651:     */       }
/*  652: 892 */       damageEntity(par1DamageSource, par2 - this.lastDamage);
/*  653: 893 */       this.lastDamage = par2;
/*  654: 894 */       var3 = false;
/*  655:     */     }
/*  656:     */     else
/*  657:     */     {
/*  658: 898 */       this.lastDamage = par2;
/*  659: 899 */       this.prevHealth = getHealth();
/*  660: 900 */       this.hurtResistantTime = this.maxHurtResistantTime;
/*  661: 901 */       damageEntity(par1DamageSource, par2);
/*  662: 902 */       this.hurtTime = (this.maxHurtTime = 10);
/*  663:     */     }
/*  664: 905 */     this.attackedAtYaw = 0.0F;
/*  665: 906 */     Entity var4 = par1DamageSource.getEntity();
/*  666: 908 */     if (var4 != null)
/*  667:     */     {
/*  668: 910 */       if ((var4 instanceof EntityLivingBase)) {
/*  669: 912 */         setRevengeTarget((EntityLivingBase)var4);
/*  670:     */       }
/*  671: 915 */       if ((var4 instanceof EntityPlayer))
/*  672:     */       {
/*  673: 917 */         this.recentlyHit = 100;
/*  674: 918 */         this.attackingPlayer = ((EntityPlayer)var4);
/*  675:     */       }
/*  676: 920 */       else if ((var4 instanceof EntityWolf))
/*  677:     */       {
/*  678: 922 */         EntityWolf var5 = (EntityWolf)var4;
/*  679: 924 */         if (var5.isTamed())
/*  680:     */         {
/*  681: 926 */           this.recentlyHit = 100;
/*  682: 927 */           this.attackingPlayer = null;
/*  683:     */         }
/*  684:     */       }
/*  685:     */     }
/*  686: 932 */     if (var3)
/*  687:     */     {
/*  688: 934 */       this.worldObj.setEntityState(this, (byte)2);
/*  689: 936 */       if (par1DamageSource != DamageSource.drown) {
/*  690: 938 */         setBeenAttacked();
/*  691:     */       }
/*  692: 941 */       if (var4 != null)
/*  693:     */       {
/*  694: 943 */         double var9 = var4.posX - this.posX;
/*  695: 946 */         for (double var7 = var4.posZ - this.posZ; var9 * var9 + var7 * var7 < 0.0001D; var7 = (Math.random() - Math.random()) * 0.01D) {
/*  696: 948 */           var9 = (Math.random() - Math.random()) * 0.01D;
/*  697:     */         }
/*  698: 951 */         this.attackedAtYaw = ((float)(Math.atan2(var7, var9) * 180.0D / 3.141592653589793D) - this.rotationYaw);
/*  699: 952 */         knockBack(var4, par2, var9, var7);
/*  700:     */       }
/*  701:     */       else
/*  702:     */       {
/*  703: 956 */         this.attackedAtYaw = ((int)(Math.random() * 2.0D) * 180);
/*  704:     */       }
/*  705:     */     }
/*  706: 962 */     if (getHealth() <= 0.0F)
/*  707:     */     {
/*  708: 964 */       String var10 = getDeathSound();
/*  709: 966 */       if ((var3) && (var10 != null)) {
/*  710: 968 */         playSound(var10, getSoundVolume(), getSoundPitch());
/*  711:     */       }
/*  712: 971 */       onDeath(par1DamageSource);
/*  713:     */     }
/*  714:     */     else
/*  715:     */     {
/*  716: 975 */       String var10 = getHurtSound();
/*  717: 977 */       if ((var3) && (var10 != null)) {
/*  718: 979 */         playSound(var10, getSoundVolume(), getSoundPitch());
/*  719:     */       }
/*  720:     */     }
/*  721: 983 */     return true;
/*  722:     */   }
/*  723:     */   
/*  724:     */   public void renderBrokenItemStack(ItemStack par1ItemStack)
/*  725:     */   {
/*  726: 993 */     playSound("random.break", 0.8F, 0.8F + this.worldObj.rand.nextFloat() * 0.4F);
/*  727: 995 */     for (int var2 = 0; var2 < 5; var2++)
/*  728:     */     {
/*  729: 997 */       Vec3 var3 = this.worldObj.getWorldVec3Pool().getVecFromPool((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*  730: 998 */       var3.rotateAroundX(-this.rotationPitch * 3.141593F / 180.0F);
/*  731: 999 */       var3.rotateAroundY(-this.rotationYaw * 3.141593F / 180.0F);
/*  732:1000 */       Vec3 var4 = this.worldObj.getWorldVec3Pool().getVecFromPool((this.rand.nextFloat() - 0.5D) * 0.3D, -this.rand.nextFloat() * 0.6D - 0.3D, 0.6D);
/*  733:1001 */       var4.rotateAroundX(-this.rotationPitch * 3.141593F / 180.0F);
/*  734:1002 */       var4.rotateAroundY(-this.rotationYaw * 3.141593F / 180.0F);
/*  735:1003 */       var4 = var4.addVector(this.posX, this.posY + getEyeHeight(), this.posZ);
/*  736:1004 */       this.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(par1ItemStack.getItem()), var4.xCoord, var4.yCoord, var4.zCoord, var3.xCoord, var3.yCoord + 0.05D, var3.zCoord);
/*  737:     */     }
/*  738:     */   }
/*  739:     */   
/*  740:     */   public void onDeath(DamageSource par1DamageSource)
/*  741:     */   {
/*  742:1013 */     Entity var2 = par1DamageSource.getEntity();
/*  743:1014 */     EntityLivingBase var3 = func_94060_bK();
/*  744:1016 */     if ((this.scoreValue >= 0) && (var3 != null)) {
/*  745:1018 */       var3.addToPlayerScore(this, this.scoreValue);
/*  746:     */     }
/*  747:1021 */     if (var2 != null) {
/*  748:1023 */       var2.onKillEntity(this);
/*  749:     */     }
/*  750:1026 */     this.dead = true;
/*  751:1028 */     if (!this.worldObj.isClient)
/*  752:     */     {
/*  753:1030 */       int var4 = 0;
/*  754:1032 */       if ((var2 instanceof EntityPlayer)) {
/*  755:1034 */         var4 = EnchantmentHelper.getLootingModifier((EntityLivingBase)var2);
/*  756:     */       }
/*  757:1037 */       if ((func_146066_aG()) && (this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
/*  758:     */       {
/*  759:1039 */         dropFewItems(this.recentlyHit > 0, var4);
/*  760:1040 */         dropEquipment(this.recentlyHit > 0, var4);
/*  761:1042 */         if (this.recentlyHit > 0)
/*  762:     */         {
/*  763:1044 */           int var5 = this.rand.nextInt(200) - var4;
/*  764:1046 */           if (var5 < 5) {
/*  765:1048 */             dropRareDrop(var5 <= 0 ? 1 : 0);
/*  766:     */           }
/*  767:     */         }
/*  768:     */       }
/*  769:     */     }
/*  770:1054 */     this.worldObj.setEntityState(this, (byte)3);
/*  771:     */   }
/*  772:     */   
/*  773:     */   protected void dropEquipment(boolean par1, int par2) {}
/*  774:     */   
/*  775:     */   public void knockBack(Entity par1Entity, float par2, double par3, double par5)
/*  776:     */   {
/*  777:1067 */     if (this.rand.nextDouble() >= getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue())
/*  778:     */     {
/*  779:1069 */       this.isAirBorne = true;
/*  780:1070 */       float var7 = MathHelper.sqrt_double(par3 * par3 + par5 * par5);
/*  781:1071 */       float var8 = 0.4F;
/*  782:1072 */       this.motionX /= 2.0D;
/*  783:1073 */       this.motionY /= 2.0D;
/*  784:1074 */       this.motionZ /= 2.0D;
/*  785:1075 */       this.motionX -= par3 / var7 * var8;
/*  786:1076 */       this.motionY += var8;
/*  787:1077 */       this.motionZ -= par5 / var7 * var8;
/*  788:1079 */       if (this.motionY > 0.4000000059604645D) {
/*  789:1081 */         this.motionY = 0.4000000059604645D;
/*  790:     */       }
/*  791:     */     }
/*  792:     */   }
/*  793:     */   
/*  794:     */   protected String getHurtSound()
/*  795:     */   {
/*  796:1091 */     return "game.neutral.hurt";
/*  797:     */   }
/*  798:     */   
/*  799:     */   protected String getDeathSound()
/*  800:     */   {
/*  801:1099 */     return "game.neutral.die";
/*  802:     */   }
/*  803:     */   
/*  804:     */   protected void dropRareDrop(int par1) {}
/*  805:     */   
/*  806:     */   protected void dropFewItems(boolean par1, int par2) {}
/*  807:     */   
/*  808:     */   public boolean isOnLadder()
/*  809:     */   {
/*  810:1114 */     int var1 = MathHelper.floor_double(this.posX);
/*  811:1115 */     int var2 = MathHelper.floor_double(this.boundingBox.minY);
/*  812:1116 */     int var3 = MathHelper.floor_double(this.posZ);
/*  813:1117 */     Block var4 = this.worldObj.getBlock(var1, var2, var3);
/*  814:1118 */     return (var4 == Blocks.ladder) || (var4 == Blocks.vine);
/*  815:     */   }
/*  816:     */   
/*  817:     */   public boolean isEntityAlive()
/*  818:     */   {
/*  819:1126 */     return (!this.isDead) && (getHealth() > 0.0F);
/*  820:     */   }
/*  821:     */   
/*  822:     */   protected void fall(float par1)
/*  823:     */   {
/*  824:1134 */     super.fall(par1);
/*  825:1135 */     PotionEffect var2 = getActivePotionEffect(Potion.jump);
/*  826:1136 */     float var3 = var2 != null ? var2.getAmplifier() + 1 : 0.0F;
/*  827:1137 */     int var4 = MathHelper.ceiling_float_int(par1 - 3.0F - var3);
/*  828:1139 */     if (var4 > 0)
/*  829:     */     {
/*  830:1141 */       playSound(func_146067_o(var4), 1.0F, 1.0F);
/*  831:1142 */       attackEntityFrom(DamageSource.fall, var4);
/*  832:1143 */       int var5 = MathHelper.floor_double(this.posX);
/*  833:1144 */       int var6 = MathHelper.floor_double(this.posY - 0.2000000029802322D - this.yOffset);
/*  834:1145 */       int var7 = MathHelper.floor_double(this.posZ);
/*  835:1146 */       Block var8 = this.worldObj.getBlock(var5, var6, var7);
/*  836:1148 */       if (var8.getMaterial() != Material.air)
/*  837:     */       {
/*  838:1150 */         Block.SoundType var9 = var8.stepSound;
/*  839:1151 */         playSound(var9.func_150498_e(), var9.func_150497_c() * 0.5F, var9.func_150494_d() * 0.75F);
/*  840:     */       }
/*  841:     */     }
/*  842:     */   }
/*  843:     */   
/*  844:     */   protected String func_146067_o(int p_146067_1_)
/*  845:     */   {
/*  846:1158 */     return p_146067_1_ > 4 ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
/*  847:     */   }
/*  848:     */   
/*  849:     */   public void performHurtAnimation()
/*  850:     */   {
/*  851:1166 */     this.hurtTime = (this.maxHurtTime = 10);
/*  852:1167 */     this.attackedAtYaw = 0.0F;
/*  853:     */   }
/*  854:     */   
/*  855:     */   public int getTotalArmorValue()
/*  856:     */   {
/*  857:1175 */     int var1 = 0;
/*  858:1176 */     ItemStack[] var2 = getLastActiveItems();
/*  859:1177 */     int var3 = var2.length;
/*  860:1179 */     for (int var4 = 0; var4 < var3; var4++)
/*  861:     */     {
/*  862:1181 */       ItemStack var5 = var2[var4];
/*  863:1183 */       if ((var5 != null) && ((var5.getItem() instanceof ItemArmor)))
/*  864:     */       {
/*  865:1185 */         int var6 = ((ItemArmor)var5.getItem()).damageReduceAmount;
/*  866:1186 */         var1 += var6;
/*  867:     */       }
/*  868:     */     }
/*  869:1190 */     return var1;
/*  870:     */   }
/*  871:     */   
/*  872:     */   protected void damageArmor(float par1) {}
/*  873:     */   
/*  874:     */   protected float applyArmorCalculations(DamageSource par1DamageSource, float par2)
/*  875:     */   {
/*  876:1200 */     if (!par1DamageSource.isUnblockable())
/*  877:     */     {
/*  878:1202 */       int var3 = 25 - getTotalArmorValue();
/*  879:1203 */       float var4 = par2 * var3;
/*  880:1204 */       damageArmor(par2);
/*  881:1205 */       par2 = var4 / 25.0F;
/*  882:     */     }
/*  883:1208 */     return par2;
/*  884:     */   }
/*  885:     */   
/*  886:     */   protected float applyPotionDamageCalculations(DamageSource par1DamageSource, float par2)
/*  887:     */   {
/*  888:1216 */     if (par1DamageSource.isDamageAbsolute()) {
/*  889:1218 */       return par2;
/*  890:     */     }
/*  891:1222 */     (this instanceof EntityZombie);
/*  892:1230 */     if ((isPotionActive(Potion.resistance)) && (par1DamageSource != DamageSource.outOfWorld))
/*  893:     */     {
/*  894:1232 */       int var3 = (getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
/*  895:1233 */       int var4 = 25 - var3;
/*  896:1234 */       float var5 = par2 * var4;
/*  897:1235 */       par2 = var5 / 25.0F;
/*  898:     */     }
/*  899:1238 */     if (par2 <= 0.0F) {
/*  900:1240 */       return 0.0F;
/*  901:     */     }
/*  902:1244 */     int var3 = EnchantmentHelper.getEnchantmentModifierDamage(getLastActiveItems(), par1DamageSource);
/*  903:1246 */     if (var3 > 20) {
/*  904:1248 */       var3 = 20;
/*  905:     */     }
/*  906:1251 */     if ((var3 > 0) && (var3 <= 20))
/*  907:     */     {
/*  908:1253 */       int var4 = 25 - var3;
/*  909:1254 */       float var5 = par2 * var4;
/*  910:1255 */       par2 = var5 / 25.0F;
/*  911:     */     }
/*  912:1258 */     return par2;
/*  913:     */   }
/*  914:     */   
/*  915:     */   protected void damageEntity(DamageSource par1DamageSource, float par2)
/*  916:     */   {
/*  917:1269 */     if (!isEntityInvulnerable())
/*  918:     */     {
/*  919:1271 */       par2 = applyArmorCalculations(par1DamageSource, par2);
/*  920:1272 */       par2 = applyPotionDamageCalculations(par1DamageSource, par2);
/*  921:1273 */       float var3 = par2;
/*  922:1274 */       par2 = Math.max(par2 - getAbsorptionAmount(), 0.0F);
/*  923:1275 */       setAbsorptionAmount(getAbsorptionAmount() - (var3 - par2));
/*  924:1277 */       if (par2 != 0.0F)
/*  925:     */       {
/*  926:1279 */         float var4 = getHealth();
/*  927:1280 */         setHealth(var4 - par2);
/*  928:1281 */         func_110142_aN().func_94547_a(par1DamageSource, var4, par2);
/*  929:1282 */         setAbsorptionAmount(getAbsorptionAmount() - par2);
/*  930:     */       }
/*  931:     */     }
/*  932:     */   }
/*  933:     */   
/*  934:     */   public CombatTracker func_110142_aN()
/*  935:     */   {
/*  936:1289 */     return this._combatTracker;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public EntityLivingBase func_94060_bK()
/*  940:     */   {
/*  941:1294 */     return this.entityLivingToAttack != null ? this.entityLivingToAttack : this.attackingPlayer != null ? this.attackingPlayer : this._combatTracker.func_94550_c() != null ? this._combatTracker.func_94550_c() : null;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public final float getMaxHealth()
/*  945:     */   {
/*  946:1299 */     return (float)getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
/*  947:     */   }
/*  948:     */   
/*  949:     */   public final int getArrowCountInEntity()
/*  950:     */   {
/*  951:1307 */     return this.dataWatcher.getWatchableObjectByte(9);
/*  952:     */   }
/*  953:     */   
/*  954:     */   public final void setArrowCountInEntity(int par1)
/*  955:     */   {
/*  956:1315 */     this.dataWatcher.updateObject(9, Byte.valueOf((byte)par1));
/*  957:     */   }
/*  958:     */   
/*  959:     */   private int getArmSwingAnimationEnd()
/*  960:     */   {
/*  961:1324 */     return isPotionActive(Potion.digSlowdown) ? 6 + (1 + getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2 : isPotionActive(Potion.digSpeed) ? 6 - (1 + getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1 : 6;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public void swingItem()
/*  965:     */   {
/*  966:1332 */     if ((!this.isSwingInProgress) || (this.swingProgressInt >= getArmSwingAnimationEnd() / 2) || (this.swingProgressInt < 0))
/*  967:     */     {
/*  968:1334 */       this.swingProgressInt = -1;
/*  969:1335 */       this.isSwingInProgress = true;
/*  970:1337 */       if ((this.worldObj instanceof WorldServer)) {
/*  971:1339 */         ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S0BPacketAnimation(this, 0));
/*  972:     */       }
/*  973:     */     }
/*  974:     */   }
/*  975:     */   
/*  976:     */   public void handleHealthUpdate(byte par1)
/*  977:     */   {
/*  978:1346 */     if (par1 == 2)
/*  979:     */     {
/*  980:1348 */       this.limbSwingAmount = 1.5F;
/*  981:1349 */       this.hurtResistantTime = this.maxHurtResistantTime;
/*  982:1350 */       this.hurtTime = (this.maxHurtTime = 10);
/*  983:1351 */       this.attackedAtYaw = 0.0F;
/*  984:1352 */       playSound(getHurtSound(), getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*  985:1353 */       attackEntityFrom(DamageSource.generic, 0.0F);
/*  986:     */     }
/*  987:1355 */     else if (par1 == 3)
/*  988:     */     {
/*  989:1357 */       playSound(getDeathSound(), getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*  990:1358 */       setHealth(0.0F);
/*  991:1359 */       onDeath(DamageSource.generic);
/*  992:     */     }
/*  993:     */     else
/*  994:     */     {
/*  995:1363 */       super.handleHealthUpdate(par1);
/*  996:     */     }
/*  997:     */   }
/*  998:     */   
/*  999:     */   protected void kill()
/* 1000:     */   {
/* 1001:1372 */     attackEntityFrom(DamageSource.outOfWorld, 4.0F);
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   protected void updateArmSwingProgress()
/* 1005:     */   {
/* 1006:1380 */     int var1 = getArmSwingAnimationEnd();
/* 1007:1382 */     if (this.isSwingInProgress)
/* 1008:     */     {
/* 1009:1384 */       this.swingProgressInt += 1;
/* 1010:1386 */       if (this.swingProgressInt >= var1)
/* 1011:     */       {
/* 1012:1388 */         this.swingProgressInt = 0;
/* 1013:1389 */         this.isSwingInProgress = false;
/* 1014:     */       }
/* 1015:     */     }
/* 1016:     */     else
/* 1017:     */     {
/* 1018:1394 */       this.swingProgressInt = 0;
/* 1019:     */     }
/* 1020:1397 */     this.swingProgress = (this.swingProgressInt / var1);
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public IAttributeInstance getEntityAttribute(IAttribute par1Attribute)
/* 1024:     */   {
/* 1025:1402 */     return getAttributeMap().getAttributeInstance(par1Attribute);
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public BaseAttributeMap getAttributeMap()
/* 1029:     */   {
/* 1030:1407 */     if (this.attributeMap == null) {
/* 1031:1409 */       this.attributeMap = new ServersideAttributeMap();
/* 1032:     */     }
/* 1033:1412 */     return this.attributeMap;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public EnumCreatureAttribute getCreatureAttribute()
/* 1037:     */   {
/* 1038:1420 */     return EnumCreatureAttribute.UNDEFINED;
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public abstract ItemStack getHeldItem();
/* 1042:     */   
/* 1043:     */   public abstract ItemStack getEquipmentInSlot(int paramInt);
/* 1044:     */   
/* 1045:     */   public abstract void setCurrentItemOrArmor(int paramInt, ItemStack paramItemStack);
/* 1046:     */   
/* 1047:     */   public void setSprinting(boolean par1)
/* 1048:     */   {
/* 1049:1443 */     super.setSprinting(par1);
/* 1050:1444 */     IAttributeInstance var2 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 1051:1446 */     if (var2.getModifier(sprintingSpeedBoostModifierUUID) != null) {
/* 1052:1448 */       var2.removeModifier(sprintingSpeedBoostModifier);
/* 1053:     */     }
/* 1054:1451 */     if (par1) {
/* 1055:1453 */       var2.applyModifier(sprintingSpeedBoostModifier);
/* 1056:     */     }
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public abstract ItemStack[] getLastActiveItems();
/* 1060:     */   
/* 1061:     */   protected float getSoundVolume()
/* 1062:     */   {
/* 1063:1464 */     return 1.0F;
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   protected float getSoundPitch()
/* 1067:     */   {
/* 1068:1472 */     return isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F;
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   protected boolean isMovementBlocked()
/* 1072:     */   {
/* 1073:1480 */     return getHealth() <= 0.0F;
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public void setPositionAndUpdate(double par1, double par3, double par5)
/* 1077:     */   {
/* 1078:1488 */     setLocationAndAngles(par1, par3, par5, this.rotationYaw, this.rotationPitch);
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public void dismountEntity(Entity par1Entity)
/* 1082:     */   {
/* 1083:1496 */     double var3 = par1Entity.posX;
/* 1084:1497 */     double var5 = par1Entity.boundingBox.minY + par1Entity.height;
/* 1085:1498 */     double var7 = par1Entity.posZ;
/* 1086:1499 */     byte var9 = 3;
/* 1087:1501 */     for (int var10 = -var9; var10 <= var9; var10++) {
/* 1088:1503 */       for (int var11 = -var9; var11 < var9; var11++) {
/* 1089:1505 */         if ((var10 != 0) || (var11 != 0))
/* 1090:     */         {
/* 1091:1507 */           int var12 = (int)(this.posX + var10);
/* 1092:1508 */           int var13 = (int)(this.posZ + var11);
/* 1093:1509 */           AxisAlignedBB var2 = this.boundingBox.getOffsetBoundingBox(var10, 1.0D, var11);
/* 1094:1511 */           if (this.worldObj.func_147461_a(var2).isEmpty())
/* 1095:     */           {
/* 1096:1513 */             if (World.doesBlockHaveSolidTopSurface(this.worldObj, var12, (int)this.posY, var13))
/* 1097:     */             {
/* 1098:1515 */               setPositionAndUpdate(this.posX + var10, this.posY + 1.0D, this.posZ + var11);
/* 1099:1516 */               return;
/* 1100:     */             }
/* 1101:1519 */             if ((World.doesBlockHaveSolidTopSurface(this.worldObj, var12, (int)this.posY - 1, var13)) || (this.worldObj.getBlock(var12, (int)this.posY - 1, var13).getMaterial() == Material.water))
/* 1102:     */             {
/* 1103:1521 */               var3 = this.posX + var10;
/* 1104:1522 */               var5 = this.posY + 1.0D;
/* 1105:1523 */               var7 = this.posZ + var11;
/* 1106:     */             }
/* 1107:     */           }
/* 1108:     */         }
/* 1109:     */       }
/* 1110:     */     }
/* 1111:1530 */     setPositionAndUpdate(var3, var5, var7);
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public boolean getAlwaysRenderNameTagForRender()
/* 1115:     */   {
/* 1116:1535 */     return false;
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public IIcon getItemIcon(ItemStack par1ItemStack, int par2)
/* 1120:     */   {
/* 1121:1543 */     return par1ItemStack.getItem().requiresMultipleRenderPasses() ? par1ItemStack.getItem().getIconFromDamageForRenderPass(par1ItemStack.getItemDamage(), par2) : par1ItemStack.getIconIndex();
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   protected void jump()
/* 1125:     */   {
/* 1126:1551 */     this.motionY = 0.4199999868869782D;
/* 1127:1553 */     if (isPotionActive(Potion.jump)) {
/* 1128:1555 */       this.motionY += (getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
/* 1129:     */     }
/* 1130:1558 */     if (isSprinting())
/* 1131:     */     {
/* 1132:1560 */       float var1 = this.rotationYaw * 0.01745329F;
/* 1133:1561 */       this.motionX -= MathHelper.sin(var1) * 0.2F;
/* 1134:1562 */       this.motionZ += MathHelper.cos(var1) * 0.2F;
/* 1135:     */     }
/* 1136:1565 */     this.isAirBorne = true;
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   public void moveEntityWithHeading(float par1, float par2)
/* 1140:     */   {
/* 1141:1575 */     if ((isInWater()) && ((!(this instanceof EntityPlayer)) || (!((EntityPlayer)this).capabilities.isFlying)))
/* 1142:     */     {
/* 1143:1577 */       double var8 = this.posY;
/* 1144:1578 */       moveFlying(par1, par2, isAIEnabled() ? 0.04F : 0.02F);
/* 1145:1579 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 1146:1580 */       this.motionX *= 0.800000011920929D;
/* 1147:1581 */       this.motionY *= 0.800000011920929D;
/* 1148:1582 */       this.motionZ *= 0.800000011920929D;
/* 1149:1583 */       this.motionY -= 0.02D;
/* 1150:1585 */       if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var8, this.motionZ))) {
/* 1151:1587 */         this.motionY = 0.300000011920929D;
/* 1152:     */       }
/* 1153:     */     }
/* 1154:1590 */     else if ((handleLavaMovement()) && ((!(this instanceof EntityPlayer)) || (!((EntityPlayer)this).capabilities.isFlying)))
/* 1155:     */     {
/* 1156:1592 */       double var8 = this.posY;
/* 1157:1593 */       moveFlying(par1, par2, 0.02F);
/* 1158:1594 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 1159:1595 */       this.motionX *= 0.5D;
/* 1160:1596 */       this.motionY *= 0.5D;
/* 1161:1597 */       this.motionZ *= 0.5D;
/* 1162:1598 */       this.motionY -= 0.02D;
/* 1163:1600 */       if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + var8, this.motionZ))) {
/* 1164:1602 */         this.motionY = 0.300000011920929D;
/* 1165:     */       }
/* 1166:     */     }
/* 1167:     */     else
/* 1168:     */     {
/* 1169:1607 */       float var3 = 0.91F;
/* 1170:1609 */       if (this.onGround) {
/* 1171:1611 */         var3 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
/* 1172:     */       }
/* 1173:1614 */       float var4 = 0.1627714F / (var3 * var3 * var3);
/* 1174:     */       float var5;
/* 1175:     */       float var5;
/* 1176:1617 */       if (this.onGround) {
/* 1177:1619 */         var5 = getAIMoveSpeed() * var4;
/* 1178:     */       } else {
/* 1179:1623 */         var5 = this.jumpMovementFactor;
/* 1180:     */       }
/* 1181:1626 */       moveFlying(par1, par2, var5);
/* 1182:1627 */       var3 = 0.91F;
/* 1183:1629 */       if (this.onGround) {
/* 1184:1631 */         var3 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
/* 1185:     */       }
/* 1186:1634 */       if (isOnLadder())
/* 1187:     */       {
/* 1188:1636 */         float var6 = 0.15F;
/* 1189:1638 */         if (this.motionX < -var6) {
/* 1190:1640 */           this.motionX = (-var6);
/* 1191:     */         }
/* 1192:1643 */         if (this.motionX > var6) {
/* 1193:1645 */           this.motionX = var6;
/* 1194:     */         }
/* 1195:1648 */         if (this.motionZ < -var6) {
/* 1196:1650 */           this.motionZ = (-var6);
/* 1197:     */         }
/* 1198:1653 */         if (this.motionZ > var6) {
/* 1199:1655 */           this.motionZ = var6;
/* 1200:     */         }
/* 1201:1658 */         this.fallDistance = 0.0F;
/* 1202:1660 */         if (this.motionY < -0.15D) {
/* 1203:1662 */           this.motionY = -0.15D;
/* 1204:     */         }
/* 1205:1665 */         boolean var7 = (isSneaking()) && ((this instanceof EntityPlayer));
/* 1206:1667 */         if ((var7) && (this.motionY < 0.0D)) {
/* 1207:1669 */           this.motionY = 0.0D;
/* 1208:     */         }
/* 1209:     */       }
/* 1210:1673 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 1211:1675 */       if ((this.isCollidedHorizontally) && (isOnLadder())) {
/* 1212:1677 */         this.motionY = 0.2D;
/* 1213:     */       }
/* 1214:1680 */       if ((this.worldObj.isClient) && ((!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ)) || (!this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded)))
/* 1215:     */       {
/* 1216:1682 */         if (this.posY > 0.0D) {
/* 1217:1684 */           this.motionY = -0.1D;
/* 1218:     */         } else {
/* 1219:1688 */           this.motionY = 0.0D;
/* 1220:     */         }
/* 1221:     */       }
/* 1222:     */       else {
/* 1223:1693 */         this.motionY -= 0.08D;
/* 1224:     */       }
/* 1225:1696 */       this.motionY *= 0.9800000190734863D;
/* 1226:1697 */       this.motionX *= var3;
/* 1227:1698 */       this.motionZ *= var3;
/* 1228:     */     }
/* 1229:1701 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 1230:1702 */     double var8 = this.posX - this.prevPosX;
/* 1231:1703 */     double var9 = this.posZ - this.prevPosZ;
/* 1232:1704 */     float var10 = MathHelper.sqrt_double(var8 * var8 + var9 * var9) * 4.0F;
/* 1233:1706 */     if (var10 > 1.0F) {
/* 1234:1708 */       var10 = 1.0F;
/* 1235:     */     }
/* 1236:1711 */     this.limbSwingAmount += (var10 - this.limbSwingAmount) * 0.4F;
/* 1237:1712 */     this.limbSwing += this.limbSwingAmount;
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   protected boolean isAIEnabled()
/* 1241:     */   {
/* 1242:1720 */     return false;
/* 1243:     */   }
/* 1244:     */   
/* 1245:     */   public float getAIMoveSpeed()
/* 1246:     */   {
/* 1247:1728 */     return isAIEnabled() ? this.landMovementFactor : 0.1F;
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public void setAIMoveSpeed(float par1)
/* 1251:     */   {
/* 1252:1736 */     this.landMovementFactor = par1;
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   public boolean attackEntityAsMob(Entity par1Entity)
/* 1256:     */   {
/* 1257:1741 */     setLastAttacker(par1Entity);
/* 1258:1742 */     return false;
/* 1259:     */   }
/* 1260:     */   
/* 1261:     */   public boolean isPlayerSleeping()
/* 1262:     */   {
/* 1263:1750 */     return false;
/* 1264:     */   }
/* 1265:     */   
/* 1266:     */   public void onUpdate()
/* 1267:     */   {
/* 1268:1758 */     super.onUpdate();
/* 1269:1760 */     if (!this.worldObj.isClient)
/* 1270:     */     {
/* 1271:1762 */       int var1 = getArrowCountInEntity();
/* 1272:1764 */       if (var1 > 0)
/* 1273:     */       {
/* 1274:1766 */         if (this.arrowHitTimer <= 0) {
/* 1275:1768 */           this.arrowHitTimer = (20 * (30 - var1));
/* 1276:     */         }
/* 1277:1771 */         this.arrowHitTimer -= 1;
/* 1278:1773 */         if (this.arrowHitTimer <= 0) {
/* 1279:1775 */           setArrowCountInEntity(var1 - 1);
/* 1280:     */         }
/* 1281:     */       }
/* 1282:1779 */       for (int var2 = 0; var2 < 5; var2++)
/* 1283:     */       {
/* 1284:1781 */         ItemStack var3 = this.previousEquipment[var2];
/* 1285:1782 */         ItemStack var4 = getEquipmentInSlot(var2);
/* 1286:1784 */         if (!ItemStack.areItemStacksEqual(var4, var3))
/* 1287:     */         {
/* 1288:1786 */           ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S04PacketEntityEquipment(getEntityId(), var2, var4));
/* 1289:1788 */           if (var3 != null) {
/* 1290:1790 */             this.attributeMap.removeAttributeModifiers(var3.getAttributeModifiers());
/* 1291:     */           }
/* 1292:1793 */           if (var4 != null) {
/* 1293:1795 */             this.attributeMap.applyAttributeModifiers(var4.getAttributeModifiers());
/* 1294:     */           }
/* 1295:1798 */           this.previousEquipment[var2] = (var4 == null ? null : var4.copy());
/* 1296:     */         }
/* 1297:     */       }
/* 1298:     */     }
/* 1299:1803 */     onLivingUpdate();
/* 1300:1804 */     double var9 = this.posX - this.prevPosX;
/* 1301:1805 */     double var10 = this.posZ - this.prevPosZ;
/* 1302:1806 */     float var5 = (float)(var9 * var9 + var10 * var10);
/* 1303:1807 */     float var6 = this.renderYawOffset;
/* 1304:1808 */     float var7 = 0.0F;
/* 1305:1809 */     this.field_70768_au = this.field_110154_aX;
/* 1306:1810 */     float var8 = 0.0F;
/* 1307:1812 */     if (var5 > 0.0025F)
/* 1308:     */     {
/* 1309:1814 */       var8 = 1.0F;
/* 1310:1815 */       var7 = (float)Math.sqrt(var5) * 3.0F;
/* 1311:1816 */       var6 = (float)Math.atan2(var10, var9) * 180.0F / 3.141593F - 90.0F;
/* 1312:     */     }
/* 1313:1819 */     if (this.swingProgress > 0.0F) {
/* 1314:1821 */       var6 = this.rotationYaw;
/* 1315:     */     }
/* 1316:1824 */     if (!this.onGround) {
/* 1317:1826 */       var8 = 0.0F;
/* 1318:     */     }
/* 1319:1829 */     this.field_110154_aX += (var8 - this.field_110154_aX) * 0.3F;
/* 1320:1830 */     this.worldObj.theProfiler.startSection("headTurn");
/* 1321:1831 */     var7 = func_110146_f(var6, var7);
/* 1322:1832 */     this.worldObj.theProfiler.endSection();
/* 1323:1833 */     this.worldObj.theProfiler.startSection("rangeChecks");
/* 1324:1835 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 1325:1837 */       this.prevRotationYaw -= 360.0F;
/* 1326:     */     }
/* 1327:1840 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 1328:1842 */       this.prevRotationYaw += 360.0F;
/* 1329:     */     }
/* 1330:1845 */     while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
/* 1331:1847 */       this.prevRenderYawOffset -= 360.0F;
/* 1332:     */     }
/* 1333:1850 */     while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
/* 1334:1852 */       this.prevRenderYawOffset += 360.0F;
/* 1335:     */     }
/* 1336:1855 */     while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
/* 1337:1857 */       this.prevRotationPitch -= 360.0F;
/* 1338:     */     }
/* 1339:1860 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 1340:1862 */       this.prevRotationPitch += 360.0F;
/* 1341:     */     }
/* 1342:1865 */     while (this.rotationYawHead - this.prevRotationYawHead < -180.0F) {
/* 1343:1867 */       this.prevRotationYawHead -= 360.0F;
/* 1344:     */     }
/* 1345:1870 */     while (this.rotationYawHead - this.prevRotationYawHead >= 180.0F) {
/* 1346:1872 */       this.prevRotationYawHead += 360.0F;
/* 1347:     */     }
/* 1348:1875 */     this.worldObj.theProfiler.endSection();
/* 1349:1876 */     this.field_70764_aw += var7;
/* 1350:     */   }
/* 1351:     */   
/* 1352:     */   protected float func_110146_f(float par1, float par2)
/* 1353:     */   {
/* 1354:1881 */     float var3 = MathHelper.wrapAngleTo180_float(par1 - this.renderYawOffset);
/* 1355:1882 */     this.renderYawOffset += var3 * 0.3F;
/* 1356:1883 */     float var4 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
/* 1357:1884 */     boolean var5 = (var4 < -90.0F) || (var4 >= 90.0F);
/* 1358:1886 */     if (var4 < -75.0F) {
/* 1359:1888 */       var4 = -75.0F;
/* 1360:     */     }
/* 1361:1891 */     if (var4 >= 75.0F) {
/* 1362:1893 */       var4 = 75.0F;
/* 1363:     */     }
/* 1364:1896 */     this.renderYawOffset = (this.rotationYaw - var4);
/* 1365:1898 */     if (var4 * var4 > 2500.0F) {
/* 1366:1900 */       this.renderYawOffset += var4 * 0.2F;
/* 1367:     */     }
/* 1368:1903 */     if (var5) {
/* 1369:1905 */       par2 *= -1.0F;
/* 1370:     */     }
/* 1371:1908 */     return par2;
/* 1372:     */   }
/* 1373:     */   
/* 1374:     */   public void onLivingUpdate()
/* 1375:     */   {
/* 1376:1917 */     if (this.jumpTicks > 0) {
/* 1377:1919 */       this.jumpTicks -= 1;
/* 1378:     */     }
/* 1379:1922 */     if (this.newPosRotationIncrements > 0)
/* 1380:     */     {
/* 1381:1924 */       double var1 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
/* 1382:1925 */       double var3 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
/* 1383:1926 */       double var5 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
/* 1384:1927 */       double var7 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
/* 1385:1928 */       this.rotationYaw = ((float)(this.rotationYaw + var7 / this.newPosRotationIncrements));
/* 1386:1929 */       this.rotationPitch = ((float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements));
/* 1387:1930 */       this.newPosRotationIncrements -= 1;
/* 1388:1931 */       setPosition(var1, var3, var5);
/* 1389:1932 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 1390:     */     }
/* 1391:1934 */     else if (!isClientWorld())
/* 1392:     */     {
/* 1393:1936 */       this.motionX *= 0.98D;
/* 1394:1937 */       this.motionY *= 0.98D;
/* 1395:1938 */       this.motionZ *= 0.98D;
/* 1396:     */     }
/* 1397:1941 */     if (Math.abs(this.motionX) < 0.005D) {
/* 1398:1943 */       this.motionX = 0.0D;
/* 1399:     */     }
/* 1400:1946 */     if (Math.abs(this.motionY) < 0.005D) {
/* 1401:1948 */       this.motionY = 0.0D;
/* 1402:     */     }
/* 1403:1951 */     if (Math.abs(this.motionZ) < 0.005D) {
/* 1404:1953 */       this.motionZ = 0.0D;
/* 1405:     */     }
/* 1406:1956 */     this.worldObj.theProfiler.startSection("ai");
/* 1407:1958 */     if (isMovementBlocked())
/* 1408:     */     {
/* 1409:1960 */       this.isJumping = false;
/* 1410:1961 */       this.moveStrafing = 0.0F;
/* 1411:1962 */       this.moveForward = 0.0F;
/* 1412:1963 */       this.randomYawVelocity = 0.0F;
/* 1413:     */     }
/* 1414:1965 */     else if (isClientWorld())
/* 1415:     */     {
/* 1416:1967 */       if (isAIEnabled())
/* 1417:     */       {
/* 1418:1969 */         this.worldObj.theProfiler.startSection("newAi");
/* 1419:1970 */         updateAITasks();
/* 1420:1971 */         this.worldObj.theProfiler.endSection();
/* 1421:     */       }
/* 1422:     */       else
/* 1423:     */       {
/* 1424:1975 */         this.worldObj.theProfiler.startSection("oldAi");
/* 1425:1976 */         updateEntityActionState();
/* 1426:1977 */         this.worldObj.theProfiler.endSection();
/* 1427:1978 */         this.rotationYawHead = this.rotationYaw;
/* 1428:     */       }
/* 1429:     */     }
/* 1430:1982 */     this.worldObj.theProfiler.endSection();
/* 1431:1983 */     this.worldObj.theProfiler.startSection("jump");
/* 1432:1985 */     if (this.isJumping)
/* 1433:     */     {
/* 1434:1987 */       if ((!isInWater()) && (!handleLavaMovement()))
/* 1435:     */       {
/* 1436:1989 */         if ((this.onGround) && (this.jumpTicks == 0))
/* 1437:     */         {
/* 1438:1991 */           jump();
/* 1439:1992 */           this.jumpTicks = 10;
/* 1440:     */         }
/* 1441:     */       }
/* 1442:     */       else {
/* 1443:1997 */         this.motionY += 0.03999999910593033D;
/* 1444:     */       }
/* 1445:     */     }
/* 1446:     */     else {
/* 1447:2002 */       this.jumpTicks = 0;
/* 1448:     */     }
/* 1449:2005 */     this.worldObj.theProfiler.endSection();
/* 1450:2006 */     this.worldObj.theProfiler.startSection("travel");
/* 1451:2007 */     this.moveStrafing *= 0.98F;
/* 1452:2008 */     this.moveForward *= 0.98F;
/* 1453:2009 */     this.randomYawVelocity *= 0.9F;
/* 1454:2010 */     moveEntityWithHeading(this.moveStrafing, this.moveForward);
/* 1455:2011 */     this.worldObj.theProfiler.endSection();
/* 1456:2012 */     this.worldObj.theProfiler.startSection("push");
/* 1457:2014 */     if (!this.worldObj.isClient) {
/* 1458:2016 */       collideWithNearbyEntities();
/* 1459:     */     }
/* 1460:2019 */     this.worldObj.theProfiler.endSection();
/* 1461:     */   }
/* 1462:     */   
/* 1463:     */   protected void updateAITasks() {}
/* 1464:     */   
/* 1465:     */   protected void collideWithNearbyEntities()
/* 1466:     */   {
/* 1467:2026 */     List var1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2000000029802322D, 0.0D, 0.2000000029802322D));
/* 1468:2028 */     if ((var1 != null) && (!var1.isEmpty())) {
/* 1469:2030 */       for (int var2 = 0; var2 < var1.size(); var2++)
/* 1470:     */       {
/* 1471:2032 */         Entity var3 = (Entity)var1.get(var2);
/* 1472:2034 */         if (var3.canBePushed()) {
/* 1473:2036 */           collideWithEntity(var3);
/* 1474:     */         }
/* 1475:     */       }
/* 1476:     */     }
/* 1477:     */   }
/* 1478:     */   
/* 1479:     */   protected void collideWithEntity(Entity par1Entity)
/* 1480:     */   {
/* 1481:2044 */     par1Entity.applyEntityCollision(this);
/* 1482:     */   }
/* 1483:     */   
/* 1484:     */   public void updateRidden()
/* 1485:     */   {
/* 1486:2052 */     super.updateRidden();
/* 1487:2053 */     this.field_70768_au = this.field_110154_aX;
/* 1488:2054 */     this.field_110154_aX = 0.0F;
/* 1489:2055 */     this.fallDistance = 0.0F;
/* 1490:     */   }
/* 1491:     */   
/* 1492:     */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
/* 1493:     */   {
/* 1494:2064 */     this.yOffset = 0.0F;
/* 1495:2065 */     this.newPosX = par1;
/* 1496:2066 */     this.newPosY = par3;
/* 1497:2067 */     this.newPosZ = par5;
/* 1498:2068 */     this.newRotationYaw = par7;
/* 1499:2069 */     this.newRotationPitch = par8;
/* 1500:2070 */     this.newPosRotationIncrements = par9;
/* 1501:     */   }
/* 1502:     */   
/* 1503:     */   protected void updateAITick() {}
/* 1504:     */   
/* 1505:     */   protected void updateEntityActionState()
/* 1506:     */   {
/* 1507:2080 */     this.entityAge += 1;
/* 1508:     */   }
/* 1509:     */   
/* 1510:     */   public void setJumping(boolean par1)
/* 1511:     */   {
/* 1512:2085 */     this.isJumping = par1;
/* 1513:     */   }
/* 1514:     */   
/* 1515:     */   public void onItemPickup(Entity par1Entity, int par2)
/* 1516:     */   {
/* 1517:2093 */     if ((!par1Entity.isDead) && (!this.worldObj.isClient))
/* 1518:     */     {
/* 1519:2095 */       EntityTracker var3 = ((WorldServer)this.worldObj).getEntityTracker();
/* 1520:2097 */       if ((par1Entity instanceof EntityItem)) {
/* 1521:2099 */         var3.func_151247_a(par1Entity, new S0DPacketCollectItem(par1Entity.getEntityId(), getEntityId()));
/* 1522:     */       }
/* 1523:2102 */       if ((par1Entity instanceof EntityArrow)) {
/* 1524:2104 */         var3.func_151247_a(par1Entity, new S0DPacketCollectItem(par1Entity.getEntityId(), getEntityId()));
/* 1525:     */       }
/* 1526:2107 */       if ((par1Entity instanceof EntityXPOrb)) {
/* 1527:2109 */         var3.func_151247_a(par1Entity, new S0DPacketCollectItem(par1Entity.getEntityId(), getEntityId()));
/* 1528:     */       }
/* 1529:     */     }
/* 1530:     */   }
/* 1531:     */   
/* 1532:     */   public boolean canEntityBeSeen(Entity par1Entity)
/* 1533:     */   {
/* 1534:2119 */     return this.worldObj.rayTraceBlocks(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY + getEyeHeight(), this.posZ), this.worldObj.getWorldVec3Pool().getVecFromPool(par1Entity.posX, par1Entity.posY + par1Entity.getEyeHeight(), par1Entity.posZ)) == null;
/* 1535:     */   }
/* 1536:     */   
/* 1537:     */   public Vec3 getLookVec()
/* 1538:     */   {
/* 1539:2127 */     return getLook(1.0F);
/* 1540:     */   }
/* 1541:     */   
/* 1542:     */   public Vec3 getLook(float par1)
/* 1543:     */   {
/* 1544:2140 */     if (par1 == 1.0F)
/* 1545:     */     {
/* 1546:2142 */       float var2 = MathHelper.cos(-this.rotationYaw * 0.01745329F - 3.141593F);
/* 1547:2143 */       float var3 = MathHelper.sin(-this.rotationYaw * 0.01745329F - 3.141593F);
/* 1548:2144 */       float var4 = -MathHelper.cos(-this.rotationPitch * 0.01745329F);
/* 1549:2145 */       float var5 = MathHelper.sin(-this.rotationPitch * 0.01745329F);
/* 1550:2146 */       return this.worldObj.getWorldVec3Pool().getVecFromPool(var3 * var4, var5, var2 * var4);
/* 1551:     */     }
/* 1552:2150 */     float var2 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * par1;
/* 1553:2151 */     float var3 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * par1;
/* 1554:2152 */     float var4 = MathHelper.cos(-var3 * 0.01745329F - 3.141593F);
/* 1555:2153 */     float var5 = MathHelper.sin(-var3 * 0.01745329F - 3.141593F);
/* 1556:2154 */     float var6 = -MathHelper.cos(-var2 * 0.01745329F);
/* 1557:2155 */     float var7 = MathHelper.sin(-var2 * 0.01745329F);
/* 1558:2156 */     return this.worldObj.getWorldVec3Pool().getVecFromPool(var5 * var6, var7, var4 * var6);
/* 1559:     */   }
/* 1560:     */   
/* 1561:     */   public float getSwingProgress(float par1)
/* 1562:     */   {
/* 1563:2165 */     float var2 = this.swingProgress - this.prevSwingProgress;
/* 1564:2167 */     if (var2 < 0.0F) {
/* 1565:2169 */       var2 += 1.0F;
/* 1566:     */     }
/* 1567:2172 */     return this.prevSwingProgress + var2 * par1;
/* 1568:     */   }
/* 1569:     */   
/* 1570:     */   public Vec3 getPosition(float par1)
/* 1571:     */   {
/* 1572:2180 */     if (par1 == 1.0F) {
/* 1573:2182 */       return this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
/* 1574:     */     }
/* 1575:2186 */     double var2 = this.prevPosX + (this.posX - this.prevPosX) * par1;
/* 1576:2187 */     double var4 = this.prevPosY + (this.posY - this.prevPosY) * par1;
/* 1577:2188 */     double var6 = this.prevPosZ + (this.posZ - this.prevPosZ) * par1;
/* 1578:2189 */     return this.worldObj.getWorldVec3Pool().getVecFromPool(var2, var4, var6);
/* 1579:     */   }
/* 1580:     */   
/* 1581:     */   public MovingObjectPosition rayTrace(double par1, float par3)
/* 1582:     */   {
/* 1583:2198 */     Vec3 var4 = getPosition(par3);
/* 1584:2199 */     Vec3 var5 = getLook(par3);
/* 1585:2200 */     Vec3 var6 = var4.addVector(var5.xCoord * par1, var5.yCoord * par1, var5.zCoord * par1);
/* 1586:2201 */     return this.worldObj.func_147447_a(var4, var6, false, false, true);
/* 1587:     */   }
/* 1588:     */   
/* 1589:     */   public boolean isClientWorld()
/* 1590:     */   {
/* 1591:2209 */     return !this.worldObj.isClient;
/* 1592:     */   }
/* 1593:     */   
/* 1594:     */   public boolean canBeCollidedWith()
/* 1595:     */   {
/* 1596:2217 */     return !this.isDead;
/* 1597:     */   }
/* 1598:     */   
/* 1599:     */   public boolean canBePushed()
/* 1600:     */   {
/* 1601:2225 */     return !this.isDead;
/* 1602:     */   }
/* 1603:     */   
/* 1604:     */   public float getEyeHeight()
/* 1605:     */   {
/* 1606:2230 */     return this.height * 0.85F;
/* 1607:     */   }
/* 1608:     */   
/* 1609:     */   protected void setBeenAttacked()
/* 1610:     */   {
/* 1611:2238 */     this.velocityChanged = (this.rand.nextDouble() >= getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
/* 1612:     */   }
/* 1613:     */   
/* 1614:     */   public float getRotationYawHead()
/* 1615:     */   {
/* 1616:2243 */     return this.rotationYawHead;
/* 1617:     */   }
/* 1618:     */   
/* 1619:     */   public void setRotationYawHead(float par1)
/* 1620:     */   {
/* 1621:2251 */     this.rotationYawHead = par1;
/* 1622:     */   }
/* 1623:     */   
/* 1624:     */   public float getAbsorptionAmount()
/* 1625:     */   {
/* 1626:2256 */     return this.field_110151_bq;
/* 1627:     */   }
/* 1628:     */   
/* 1629:     */   public void setAbsorptionAmount(float par1)
/* 1630:     */   {
/* 1631:2261 */     if (par1 < 0.0F) {
/* 1632:2263 */       par1 = 0.0F;
/* 1633:     */     }
/* 1634:2266 */     this.field_110151_bq = par1;
/* 1635:     */   }
/* 1636:     */   
/* 1637:     */   public Team getTeam()
/* 1638:     */   {
/* 1639:2271 */     return null;
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   public boolean isOnSameTeam(EntityLivingBase par1EntityLivingBase)
/* 1643:     */   {
/* 1644:2276 */     return isOnTeam(par1EntityLivingBase.getTeam());
/* 1645:     */   }
/* 1646:     */   
/* 1647:     */   public boolean isOnTeam(Team par1Team)
/* 1648:     */   {
/* 1649:2284 */     return getTeam() != null ? getTeam().isSameTeam(par1Team) : false;
/* 1650:     */   }
/* 1651:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityLivingBase
 * JD-Core Version:    0.7.0.1
 */