/*    1:     */ package net.minecraft.entity.passive;
/*    2:     */ 
/*    3:     */ import java.util.Iterator;
/*    4:     */ import java.util.List;
/*    5:     */ import java.util.Random;
/*    6:     */ import net.minecraft.block.Block;
/*    7:     */ import net.minecraft.block.Block.SoundType;
/*    8:     */ import net.minecraft.block.material.Material;
/*    9:     */ import net.minecraft.command.IEntitySelector;
/*   10:     */ import net.minecraft.entity.DataWatcher;
/*   11:     */ import net.minecraft.entity.Entity;
/*   12:     */ import net.minecraft.entity.EntityAgeable;
/*   13:     */ import net.minecraft.entity.EntityLivingBase;
/*   14:     */ import net.minecraft.entity.IEntityLivingData;
/*   15:     */ import net.minecraft.entity.SharedMonsterAttributes;
/*   16:     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*   17:     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*   18:     */ import net.minecraft.entity.ai.EntityAIMate;
/*   19:     */ import net.minecraft.entity.ai.EntityAIPanic;
/*   20:     */ import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
/*   21:     */ import net.minecraft.entity.ai.EntityAISwimming;
/*   22:     */ import net.minecraft.entity.ai.EntityAITasks;
/*   23:     */ import net.minecraft.entity.ai.EntityAIWander;
/*   24:     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*   25:     */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*   26:     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*   27:     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   28:     */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*   29:     */ import net.minecraft.entity.player.EntityPlayer;
/*   30:     */ import net.minecraft.entity.player.InventoryPlayer;
/*   31:     */ import net.minecraft.entity.player.PlayerCapabilities;
/*   32:     */ import net.minecraft.init.Blocks;
/*   33:     */ import net.minecraft.init.Items;
/*   34:     */ import net.minecraft.inventory.AnimalChest;
/*   35:     */ import net.minecraft.inventory.IInvBasic;
/*   36:     */ import net.minecraft.inventory.InventoryBasic;
/*   37:     */ import net.minecraft.item.Item;
/*   38:     */ import net.minecraft.item.ItemStack;
/*   39:     */ import net.minecraft.nbt.NBTTagCompound;
/*   40:     */ import net.minecraft.nbt.NBTTagList;
/*   41:     */ import net.minecraft.pathfinding.PathEntity;
/*   42:     */ import net.minecraft.pathfinding.PathNavigate;
/*   43:     */ import net.minecraft.potion.Potion;
/*   44:     */ import net.minecraft.potion.PotionEffect;
/*   45:     */ import net.minecraft.util.AxisAlignedBB;
/*   46:     */ import net.minecraft.util.DamageSource;
/*   47:     */ import net.minecraft.util.MathHelper;
/*   48:     */ import net.minecraft.util.StatCollector;
/*   49:     */ import net.minecraft.world.World;
/*   50:     */ 
/*   51:     */ public class EntityHorse
/*   52:     */   extends EntityAnimal
/*   53:     */   implements IInvBasic
/*   54:     */ {
/*   55:  43 */   private static final IEntitySelector horseBreedingSelector = new IEntitySelector()
/*   56:     */   {
/*   57:     */     private static final String __OBFID = "CL_00001642";
/*   58:     */     
/*   59:     */     public boolean isEntityApplicable(Entity par1Entity)
/*   60:     */     {
/*   61:  48 */       return ((par1Entity instanceof EntityHorse)) && (((EntityHorse)par1Entity).func_110205_ce());
/*   62:     */     }
/*   63:     */   };
/*   64:  51 */   private static final IAttribute horseJumpStrength = new RangedAttribute("horse.jumpStrength", 0.7D, 0.0D, 2.0D).setDescription("Jump Strength").setShouldWatch(true);
/*   65:  52 */   private static final String[] horseArmorTextures = { 0, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png" };
/*   66:  53 */   private static final String[] field_110273_bx = { "", "meo", "goo", "dio" };
/*   67:  54 */   private static final int[] armorValues = { 0, 5, 7, 11 };
/*   68:  55 */   private static final String[] horseTextures = { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
/*   69:  56 */   private static final String[] field_110269_bA = { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
/*   70:  57 */   private static final String[] horseMarkingTextures = { 0, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
/*   71:  58 */   private static final String[] field_110292_bC = { "", "wo_", "wmo", "wdo", "bdo" };
/*   72:     */   private int eatingHaystackCounter;
/*   73:     */   private int openMouthCounter;
/*   74:     */   private int jumpRearingCounter;
/*   75:     */   public int field_110278_bp;
/*   76:     */   public int field_110279_bq;
/*   77:     */   protected boolean horseJumping;
/*   78:     */   private AnimalChest horseChest;
/*   79:     */   private boolean hasReproduced;
/*   80:     */   protected int temper;
/*   81:     */   protected float jumpPower;
/*   82:     */   private boolean field_110294_bI;
/*   83:     */   private float headLean;
/*   84:     */   private float prevHeadLean;
/*   85:     */   private float rearingAmount;
/*   86:     */   private float prevRearingAmount;
/*   87:     */   private float mouthOpenness;
/*   88:     */   private float prevMouthOpenness;
/*   89:     */   private int field_110285_bP;
/*   90:     */   private String field_110286_bQ;
/*   91:  82 */   private String[] field_110280_bR = new String[3];
/*   92:     */   private static final String __OBFID = "CL_00001641";
/*   93:     */   
/*   94:     */   public EntityHorse(World par1World)
/*   95:     */   {
/*   96:  87 */     super(par1World);
/*   97:  88 */     setSize(1.4F, 1.6F);
/*   98:  89 */     this.isImmuneToFire = false;
/*   99:  90 */     setChested(false);
/*  100:  91 */     getNavigator().setAvoidsWater(true);
/*  101:  92 */     this.tasks.addTask(0, new EntityAISwimming(this));
/*  102:  93 */     this.tasks.addTask(1, new EntityAIPanic(this, 1.2D));
/*  103:  94 */     this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
/*  104:  95 */     this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
/*  105:  96 */     this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0D));
/*  106:  97 */     this.tasks.addTask(6, new EntityAIWander(this, 0.7D));
/*  107:  98 */     this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  108:  99 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  109: 100 */     func_110226_cD();
/*  110:     */   }
/*  111:     */   
/*  112:     */   protected void entityInit()
/*  113:     */   {
/*  114: 105 */     super.entityInit();
/*  115: 106 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/*  116: 107 */     this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
/*  117: 108 */     this.dataWatcher.addObject(20, Integer.valueOf(0));
/*  118: 109 */     this.dataWatcher.addObject(21, String.valueOf(""));
/*  119: 110 */     this.dataWatcher.addObject(22, Integer.valueOf(0));
/*  120:     */   }
/*  121:     */   
/*  122:     */   public void setHorseType(int par1)
/*  123:     */   {
/*  124: 115 */     this.dataWatcher.updateObject(19, Byte.valueOf((byte)par1));
/*  125: 116 */     func_110230_cF();
/*  126:     */   }
/*  127:     */   
/*  128:     */   public int getHorseType()
/*  129:     */   {
/*  130: 124 */     return this.dataWatcher.getWatchableObjectByte(19);
/*  131:     */   }
/*  132:     */   
/*  133:     */   public void setHorseVariant(int par1)
/*  134:     */   {
/*  135: 129 */     this.dataWatcher.updateObject(20, Integer.valueOf(par1));
/*  136: 130 */     func_110230_cF();
/*  137:     */   }
/*  138:     */   
/*  139:     */   public int getHorseVariant()
/*  140:     */   {
/*  141: 135 */     return this.dataWatcher.getWatchableObjectInt(20);
/*  142:     */   }
/*  143:     */   
/*  144:     */   public String getCommandSenderName()
/*  145:     */   {
/*  146: 143 */     if (hasCustomNameTag()) {
/*  147: 145 */       return getCustomNameTag();
/*  148:     */     }
/*  149: 149 */     int var1 = getHorseType();
/*  150: 151 */     switch (var1)
/*  151:     */     {
/*  152:     */     case 0: 
/*  153:     */     default: 
/*  154: 155 */       return StatCollector.translateToLocal("entity.horse.name");
/*  155:     */     case 1: 
/*  156: 158 */       return StatCollector.translateToLocal("entity.donkey.name");
/*  157:     */     case 2: 
/*  158: 161 */       return StatCollector.translateToLocal("entity.mule.name");
/*  159:     */     case 3: 
/*  160: 164 */       return StatCollector.translateToLocal("entity.zombiehorse.name");
/*  161:     */     }
/*  162: 167 */     return StatCollector.translateToLocal("entity.skeletonhorse.name");
/*  163:     */   }
/*  164:     */   
/*  165:     */   private boolean getHorseWatchableBoolean(int par1)
/*  166:     */   {
/*  167: 174 */     return (this.dataWatcher.getWatchableObjectInt(16) & par1) != 0;
/*  168:     */   }
/*  169:     */   
/*  170:     */   private void setHorseWatchableBoolean(int par1, boolean par2)
/*  171:     */   {
/*  172: 179 */     int var3 = this.dataWatcher.getWatchableObjectInt(16);
/*  173: 181 */     if (par2) {
/*  174: 183 */       this.dataWatcher.updateObject(16, Integer.valueOf(var3 | par1));
/*  175:     */     } else {
/*  176: 187 */       this.dataWatcher.updateObject(16, Integer.valueOf(var3 & (par1 ^ 0xFFFFFFFF)));
/*  177:     */     }
/*  178:     */   }
/*  179:     */   
/*  180:     */   public boolean isAdultHorse()
/*  181:     */   {
/*  182: 193 */     return !isChild();
/*  183:     */   }
/*  184:     */   
/*  185:     */   public boolean isTame()
/*  186:     */   {
/*  187: 198 */     return getHorseWatchableBoolean(2);
/*  188:     */   }
/*  189:     */   
/*  190:     */   public boolean func_110253_bW()
/*  191:     */   {
/*  192: 203 */     return isAdultHorse();
/*  193:     */   }
/*  194:     */   
/*  195:     */   public String getOwnerName()
/*  196:     */   {
/*  197: 208 */     return this.dataWatcher.getWatchableObjectString(21);
/*  198:     */   }
/*  199:     */   
/*  200:     */   public void setOwnerName(String par1Str)
/*  201:     */   {
/*  202: 213 */     this.dataWatcher.updateObject(21, par1Str);
/*  203:     */   }
/*  204:     */   
/*  205:     */   public float getHorseSize()
/*  206:     */   {
/*  207: 218 */     int var1 = getGrowingAge();
/*  208: 219 */     return var1 >= 0 ? 1.0F : 0.5F + (-24000 - var1) / -24000.0F * 0.5F;
/*  209:     */   }
/*  210:     */   
/*  211:     */   public void setScaleForAge(boolean par1)
/*  212:     */   {
/*  213: 227 */     if (par1) {
/*  214: 229 */       setScale(getHorseSize());
/*  215:     */     } else {
/*  216: 233 */       setScale(1.0F);
/*  217:     */     }
/*  218:     */   }
/*  219:     */   
/*  220:     */   public boolean isHorseJumping()
/*  221:     */   {
/*  222: 239 */     return this.horseJumping;
/*  223:     */   }
/*  224:     */   
/*  225:     */   public void setHorseTamed(boolean par1)
/*  226:     */   {
/*  227: 244 */     setHorseWatchableBoolean(2, par1);
/*  228:     */   }
/*  229:     */   
/*  230:     */   public void setHorseJumping(boolean par1)
/*  231:     */   {
/*  232: 249 */     this.horseJumping = par1;
/*  233:     */   }
/*  234:     */   
/*  235:     */   public boolean allowLeashing()
/*  236:     */   {
/*  237: 254 */     return (!func_110256_cu()) && (super.allowLeashing());
/*  238:     */   }
/*  239:     */   
/*  240:     */   protected void func_142017_o(float par1)
/*  241:     */   {
/*  242: 259 */     if ((par1 > 6.0F) && (isEatingHaystack())) {
/*  243: 261 */       setEatingHaystack(false);
/*  244:     */     }
/*  245:     */   }
/*  246:     */   
/*  247:     */   public boolean isChested()
/*  248:     */   {
/*  249: 267 */     return getHorseWatchableBoolean(8);
/*  250:     */   }
/*  251:     */   
/*  252:     */   public int func_110241_cb()
/*  253:     */   {
/*  254: 272 */     return this.dataWatcher.getWatchableObjectInt(22);
/*  255:     */   }
/*  256:     */   
/*  257:     */   private int getHorseArmorIndex(ItemStack par1ItemStack)
/*  258:     */   {
/*  259: 280 */     if (par1ItemStack == null) {
/*  260: 282 */       return 0;
/*  261:     */     }
/*  262: 286 */     Item var2 = par1ItemStack.getItem();
/*  263: 287 */     return var2 == Items.diamond_horse_armor ? 3 : var2 == Items.golden_horse_armor ? 2 : var2 == Items.iron_horse_armor ? 1 : 0;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public boolean isEatingHaystack()
/*  267:     */   {
/*  268: 293 */     return getHorseWatchableBoolean(32);
/*  269:     */   }
/*  270:     */   
/*  271:     */   public boolean isRearing()
/*  272:     */   {
/*  273: 298 */     return getHorseWatchableBoolean(64);
/*  274:     */   }
/*  275:     */   
/*  276:     */   public boolean func_110205_ce()
/*  277:     */   {
/*  278: 303 */     return getHorseWatchableBoolean(16);
/*  279:     */   }
/*  280:     */   
/*  281:     */   public boolean getHasReproduced()
/*  282:     */   {
/*  283: 308 */     return this.hasReproduced;
/*  284:     */   }
/*  285:     */   
/*  286:     */   public void func_146086_d(ItemStack p_146086_1_)
/*  287:     */   {
/*  288: 313 */     this.dataWatcher.updateObject(22, Integer.valueOf(getHorseArmorIndex(p_146086_1_)));
/*  289: 314 */     func_110230_cF();
/*  290:     */   }
/*  291:     */   
/*  292:     */   public void func_110242_l(boolean par1)
/*  293:     */   {
/*  294: 319 */     setHorseWatchableBoolean(16, par1);
/*  295:     */   }
/*  296:     */   
/*  297:     */   public void setChested(boolean par1)
/*  298:     */   {
/*  299: 324 */     setHorseWatchableBoolean(8, par1);
/*  300:     */   }
/*  301:     */   
/*  302:     */   public void setHasReproduced(boolean par1)
/*  303:     */   {
/*  304: 329 */     this.hasReproduced = par1;
/*  305:     */   }
/*  306:     */   
/*  307:     */   public void setHorseSaddled(boolean par1)
/*  308:     */   {
/*  309: 334 */     setHorseWatchableBoolean(4, par1);
/*  310:     */   }
/*  311:     */   
/*  312:     */   public int getTemper()
/*  313:     */   {
/*  314: 339 */     return this.temper;
/*  315:     */   }
/*  316:     */   
/*  317:     */   public void setTemper(int par1)
/*  318:     */   {
/*  319: 344 */     this.temper = par1;
/*  320:     */   }
/*  321:     */   
/*  322:     */   public int increaseTemper(int par1)
/*  323:     */   {
/*  324: 349 */     int var2 = MathHelper.clamp_int(getTemper() + par1, 0, getMaxTemper());
/*  325: 350 */     setTemper(var2);
/*  326: 351 */     return var2;
/*  327:     */   }
/*  328:     */   
/*  329:     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  330:     */   {
/*  331: 359 */     Entity var3 = par1DamageSource.getEntity();
/*  332: 360 */     return (this.riddenByEntity != null) && (this.riddenByEntity.equals(var3)) ? false : super.attackEntityFrom(par1DamageSource, par2);
/*  333:     */   }
/*  334:     */   
/*  335:     */   public int getTotalArmorValue()
/*  336:     */   {
/*  337: 368 */     return armorValues[func_110241_cb()];
/*  338:     */   }
/*  339:     */   
/*  340:     */   public boolean canBePushed()
/*  341:     */   {
/*  342: 376 */     return this.riddenByEntity == null;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public boolean prepareChunkForSpawn()
/*  346:     */   {
/*  347: 381 */     int var1 = MathHelper.floor_double(this.posX);
/*  348: 382 */     int var2 = MathHelper.floor_double(this.posZ);
/*  349: 383 */     this.worldObj.getBiomeGenForCoords(var1, var2);
/*  350: 384 */     return true;
/*  351:     */   }
/*  352:     */   
/*  353:     */   public void dropChests()
/*  354:     */   {
/*  355: 389 */     if ((!this.worldObj.isClient) && (isChested()))
/*  356:     */     {
/*  357: 391 */       func_145779_a(Item.getItemFromBlock(Blocks.chest), 1);
/*  358: 392 */       setChested(false);
/*  359:     */     }
/*  360:     */   }
/*  361:     */   
/*  362:     */   private void func_110266_cB()
/*  363:     */   {
/*  364: 398 */     openHorseMouth();
/*  365: 399 */     this.worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*  366:     */   }
/*  367:     */   
/*  368:     */   protected void fall(float par1)
/*  369:     */   {
/*  370: 407 */     if (par1 > 1.0F) {
/*  371: 409 */       playSound("mob.horse.land", 0.4F, 1.0F);
/*  372:     */     }
/*  373: 412 */     int var2 = MathHelper.ceiling_float_int(par1 * 0.5F - 3.0F);
/*  374: 414 */     if (var2 > 0)
/*  375:     */     {
/*  376: 416 */       attackEntityFrom(DamageSource.fall, var2);
/*  377: 418 */       if (this.riddenByEntity != null) {
/*  378: 420 */         this.riddenByEntity.attackEntityFrom(DamageSource.fall, var2);
/*  379:     */       }
/*  380: 423 */       Block var3 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.2D - this.prevRotationYaw), MathHelper.floor_double(this.posZ));
/*  381: 425 */       if (var3.getMaterial() != Material.air)
/*  382:     */       {
/*  383: 427 */         Block.SoundType var4 = var3.stepSound;
/*  384: 428 */         this.worldObj.playSoundAtEntity(this, var4.func_150498_e(), var4.func_150497_c() * 0.5F, var4.func_150494_d() * 0.75F);
/*  385:     */       }
/*  386:     */     }
/*  387:     */   }
/*  388:     */   
/*  389:     */   private int func_110225_cC()
/*  390:     */   {
/*  391: 435 */     int var1 = getHorseType();
/*  392: 436 */     return (isChested()) && ((var1 == 1) || (var1 == 2)) ? 17 : 2;
/*  393:     */   }
/*  394:     */   
/*  395:     */   private void func_110226_cD()
/*  396:     */   {
/*  397: 441 */     AnimalChest var1 = this.horseChest;
/*  398: 442 */     this.horseChest = new AnimalChest("HorseChest", func_110225_cC());
/*  399: 443 */     this.horseChest.func_110133_a(getCommandSenderName());
/*  400: 445 */     if (var1 != null)
/*  401:     */     {
/*  402: 447 */       var1.func_110132_b(this);
/*  403: 448 */       int var2 = Math.min(var1.getSizeInventory(), this.horseChest.getSizeInventory());
/*  404: 450 */       for (int var3 = 0; var3 < var2; var3++)
/*  405:     */       {
/*  406: 452 */         ItemStack var4 = var1.getStackInSlot(var3);
/*  407: 454 */         if (var4 != null) {
/*  408: 456 */           this.horseChest.setInventorySlotContents(var3, var4.copy());
/*  409:     */         }
/*  410:     */       }
/*  411: 460 */       var1 = null;
/*  412:     */     }
/*  413: 463 */     this.horseChest.func_110134_a(this);
/*  414: 464 */     func_110232_cE();
/*  415:     */   }
/*  416:     */   
/*  417:     */   private void func_110232_cE()
/*  418:     */   {
/*  419: 469 */     if (!this.worldObj.isClient)
/*  420:     */     {
/*  421: 471 */       setHorseSaddled(this.horseChest.getStackInSlot(0) != null);
/*  422: 473 */       if (func_110259_cr()) {
/*  423: 475 */         func_146086_d(this.horseChest.getStackInSlot(1));
/*  424:     */       }
/*  425:     */     }
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void onInventoryChanged(InventoryBasic par1InventoryBasic)
/*  429:     */   {
/*  430: 485 */     int var2 = func_110241_cb();
/*  431: 486 */     boolean var3 = isHorseSaddled();
/*  432: 487 */     func_110232_cE();
/*  433: 489 */     if (this.ticksExisted > 20)
/*  434:     */     {
/*  435: 491 */       if ((var2 == 0) && (var2 != func_110241_cb())) {
/*  436: 493 */         playSound("mob.horse.armor", 0.5F, 1.0F);
/*  437: 495 */       } else if (var2 != func_110241_cb()) {
/*  438: 497 */         playSound("mob.horse.armor", 0.5F, 1.0F);
/*  439:     */       }
/*  440: 500 */       if ((!var3) && (isHorseSaddled())) {
/*  441: 502 */         playSound("mob.horse.leather", 0.5F, 1.0F);
/*  442:     */       }
/*  443:     */     }
/*  444:     */   }
/*  445:     */   
/*  446:     */   public boolean getCanSpawnHere()
/*  447:     */   {
/*  448: 512 */     prepareChunkForSpawn();
/*  449: 513 */     return super.getCanSpawnHere();
/*  450:     */   }
/*  451:     */   
/*  452:     */   protected EntityHorse getClosestHorse(Entity par1Entity, double par2)
/*  453:     */   {
/*  454: 518 */     double var4 = 1.7976931348623157E+308D;
/*  455: 519 */     Entity var6 = null;
/*  456: 520 */     List var7 = this.worldObj.getEntitiesWithinAABBExcludingEntity(par1Entity, par1Entity.boundingBox.addCoord(par2, par2, par2), horseBreedingSelector);
/*  457: 521 */     Iterator var8 = var7.iterator();
/*  458: 523 */     while (var8.hasNext())
/*  459:     */     {
/*  460: 525 */       Entity var9 = (Entity)var8.next();
/*  461: 526 */       double var10 = var9.getDistanceSq(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
/*  462: 528 */       if (var10 < var4)
/*  463:     */       {
/*  464: 530 */         var6 = var9;
/*  465: 531 */         var4 = var10;
/*  466:     */       }
/*  467:     */     }
/*  468: 535 */     return (EntityHorse)var6;
/*  469:     */   }
/*  470:     */   
/*  471:     */   public double getHorseJumpStrength()
/*  472:     */   {
/*  473: 540 */     return getEntityAttribute(horseJumpStrength).getAttributeValue();
/*  474:     */   }
/*  475:     */   
/*  476:     */   protected String getDeathSound()
/*  477:     */   {
/*  478: 548 */     openHorseMouth();
/*  479: 549 */     int var1 = getHorseType();
/*  480: 550 */     return (var1 != 1) && (var1 != 2) ? "mob.horse.death" : var1 == 4 ? "mob.horse.skeleton.death" : var1 == 3 ? "mob.horse.zombie.death" : "mob.horse.donkey.death";
/*  481:     */   }
/*  482:     */   
/*  483:     */   protected Item func_146068_u()
/*  484:     */   {
/*  485: 555 */     boolean var1 = this.rand.nextInt(4) == 0;
/*  486: 556 */     int var2 = getHorseType();
/*  487: 557 */     return var2 == 3 ? Items.rotten_flesh : var1 ? Item.getItemById(0) : var2 == 4 ? Items.bone : Items.leather;
/*  488:     */   }
/*  489:     */   
/*  490:     */   protected String getHurtSound()
/*  491:     */   {
/*  492: 565 */     openHorseMouth();
/*  493: 567 */     if (this.rand.nextInt(3) == 0) {
/*  494: 569 */       makeHorseRear();
/*  495:     */     }
/*  496: 572 */     int var1 = getHorseType();
/*  497: 573 */     return (var1 != 1) && (var1 != 2) ? "mob.horse.hit" : var1 == 4 ? "mob.horse.skeleton.hit" : var1 == 3 ? "mob.horse.zombie.hit" : "mob.horse.donkey.hit";
/*  498:     */   }
/*  499:     */   
/*  500:     */   public boolean isHorseSaddled()
/*  501:     */   {
/*  502: 578 */     return getHorseWatchableBoolean(4);
/*  503:     */   }
/*  504:     */   
/*  505:     */   protected String getLivingSound()
/*  506:     */   {
/*  507: 586 */     openHorseMouth();
/*  508: 588 */     if ((this.rand.nextInt(10) == 0) && (!isMovementBlocked())) {
/*  509: 590 */       makeHorseRear();
/*  510:     */     }
/*  511: 593 */     int var1 = getHorseType();
/*  512: 594 */     return (var1 != 1) && (var1 != 2) ? "mob.horse.idle" : var1 == 4 ? "mob.horse.skeleton.idle" : var1 == 3 ? "mob.horse.zombie.idle" : "mob.horse.donkey.idle";
/*  513:     */   }
/*  514:     */   
/*  515:     */   protected String getAngrySoundName()
/*  516:     */   {
/*  517: 599 */     openHorseMouth();
/*  518: 600 */     makeHorseRear();
/*  519: 601 */     int var1 = getHorseType();
/*  520: 602 */     return (var1 != 3) && (var1 != 4) ? "mob.horse.donkey.angry" : (var1 != 1) && (var1 != 2) ? "mob.horse.angry" : null;
/*  521:     */   }
/*  522:     */   
/*  523:     */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/*  524:     */   {
/*  525: 607 */     Block.SoundType var5 = p_145780_4_.stepSound;
/*  526: 609 */     if (this.worldObj.getBlock(p_145780_1_, p_145780_2_ + 1, p_145780_3_) == Blocks.snow_layer) {
/*  527: 611 */       var5 = Blocks.snow_layer.stepSound;
/*  528:     */     }
/*  529: 614 */     if (!p_145780_4_.getMaterial().isLiquid())
/*  530:     */     {
/*  531: 616 */       int var6 = getHorseType();
/*  532: 618 */       if ((this.riddenByEntity != null) && (var6 != 1) && (var6 != 2))
/*  533:     */       {
/*  534: 620 */         this.field_110285_bP += 1;
/*  535: 622 */         if ((this.field_110285_bP > 5) && (this.field_110285_bP % 3 == 0))
/*  536:     */         {
/*  537: 624 */           playSound("mob.horse.gallop", var5.func_150497_c() * 0.15F, var5.func_150494_d());
/*  538: 626 */           if ((var6 == 0) && (this.rand.nextInt(10) == 0)) {
/*  539: 628 */             playSound("mob.horse.breathe", var5.func_150497_c() * 0.6F, var5.func_150494_d());
/*  540:     */           }
/*  541:     */         }
/*  542: 631 */         else if (this.field_110285_bP <= 5)
/*  543:     */         {
/*  544: 633 */           playSound("mob.horse.wood", var5.func_150497_c() * 0.15F, var5.func_150494_d());
/*  545:     */         }
/*  546:     */       }
/*  547: 636 */       else if (var5 == Block.soundTypeWood)
/*  548:     */       {
/*  549: 638 */         playSound("mob.horse.wood", var5.func_150497_c() * 0.15F, var5.func_150494_d());
/*  550:     */       }
/*  551:     */       else
/*  552:     */       {
/*  553: 642 */         playSound("mob.horse.soft", var5.func_150497_c() * 0.15F, var5.func_150494_d());
/*  554:     */       }
/*  555:     */     }
/*  556:     */   }
/*  557:     */   
/*  558:     */   protected void applyEntityAttributes()
/*  559:     */   {
/*  560: 649 */     super.applyEntityAttributes();
/*  561: 650 */     getAttributeMap().registerAttribute(horseJumpStrength);
/*  562: 651 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(53.0D);
/*  563: 652 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2249999940395355D);
/*  564:     */   }
/*  565:     */   
/*  566:     */   public int getMaxSpawnedInChunk()
/*  567:     */   {
/*  568: 660 */     return 6;
/*  569:     */   }
/*  570:     */   
/*  571:     */   public int getMaxTemper()
/*  572:     */   {
/*  573: 665 */     return 100;
/*  574:     */   }
/*  575:     */   
/*  576:     */   protected float getSoundVolume()
/*  577:     */   {
/*  578: 673 */     return 0.8F;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public int getTalkInterval()
/*  582:     */   {
/*  583: 681 */     return 400;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public boolean func_110239_cn()
/*  587:     */   {
/*  588: 686 */     return (getHorseType() == 0) || (func_110241_cb() > 0);
/*  589:     */   }
/*  590:     */   
/*  591:     */   private void func_110230_cF()
/*  592:     */   {
/*  593: 691 */     this.field_110286_bQ = null;
/*  594:     */   }
/*  595:     */   
/*  596:     */   private void setHorseTexturePaths()
/*  597:     */   {
/*  598: 696 */     this.field_110286_bQ = "horse/";
/*  599: 697 */     this.field_110280_bR[0] = null;
/*  600: 698 */     this.field_110280_bR[1] = null;
/*  601: 699 */     this.field_110280_bR[2] = null;
/*  602: 700 */     int var1 = getHorseType();
/*  603: 701 */     int var2 = getHorseVariant();
/*  604: 704 */     if (var1 == 0)
/*  605:     */     {
/*  606: 706 */       int var3 = var2 & 0xFF;
/*  607: 707 */       int var4 = (var2 & 0xFF00) >> 8;
/*  608: 708 */       this.field_110280_bR[0] = horseTextures[var3];
/*  609: 709 */       this.field_110286_bQ += field_110269_bA[var3];
/*  610: 710 */       this.field_110280_bR[1] = horseMarkingTextures[var4];
/*  611: 711 */       this.field_110286_bQ += field_110292_bC[var4];
/*  612:     */     }
/*  613:     */     else
/*  614:     */     {
/*  615: 715 */       this.field_110280_bR[0] = "";
/*  616: 716 */       this.field_110286_bQ = (this.field_110286_bQ + "_" + var1 + "_");
/*  617:     */     }
/*  618: 719 */     int var3 = func_110241_cb();
/*  619: 720 */     this.field_110280_bR[2] = horseArmorTextures[var3];
/*  620: 721 */     this.field_110286_bQ += field_110273_bx[var3];
/*  621:     */   }
/*  622:     */   
/*  623:     */   public String getHorseTexture()
/*  624:     */   {
/*  625: 726 */     if (this.field_110286_bQ == null) {
/*  626: 728 */       setHorseTexturePaths();
/*  627:     */     }
/*  628: 731 */     return this.field_110286_bQ;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public String[] getVariantTexturePaths()
/*  632:     */   {
/*  633: 736 */     if (this.field_110286_bQ == null) {
/*  634: 738 */       setHorseTexturePaths();
/*  635:     */     }
/*  636: 741 */     return this.field_110280_bR;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public void openGUI(EntityPlayer par1EntityPlayer)
/*  640:     */   {
/*  641: 746 */     if ((!this.worldObj.isClient) && ((this.riddenByEntity == null) || (this.riddenByEntity == par1EntityPlayer)) && (isTame()))
/*  642:     */     {
/*  643: 748 */       this.horseChest.func_110133_a(getCommandSenderName());
/*  644: 749 */       par1EntityPlayer.displayGUIHorse(this, this.horseChest);
/*  645:     */     }
/*  646:     */   }
/*  647:     */   
/*  648:     */   public boolean interact(EntityPlayer par1EntityPlayer)
/*  649:     */   {
/*  650: 758 */     ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
/*  651: 760 */     if ((var2 != null) && (var2.getItem() == Items.spawn_egg)) {
/*  652: 762 */       return super.interact(par1EntityPlayer);
/*  653:     */     }
/*  654: 764 */     if ((!isTame()) && (func_110256_cu())) {
/*  655: 766 */       return false;
/*  656:     */     }
/*  657: 768 */     if ((isTame()) && (isAdultHorse()) && (par1EntityPlayer.isSneaking()))
/*  658:     */     {
/*  659: 770 */       openGUI(par1EntityPlayer);
/*  660: 771 */       return true;
/*  661:     */     }
/*  662: 773 */     if ((func_110253_bW()) && (this.riddenByEntity != null)) {
/*  663: 775 */       return super.interact(par1EntityPlayer);
/*  664:     */     }
/*  665: 779 */     if (var2 != null)
/*  666:     */     {
/*  667: 781 */       boolean var3 = false;
/*  668: 783 */       if (func_110259_cr())
/*  669:     */       {
/*  670: 785 */         byte var4 = -1;
/*  671: 787 */         if (var2.getItem() == Items.iron_horse_armor) {
/*  672: 789 */           var4 = 1;
/*  673: 791 */         } else if (var2.getItem() == Items.golden_horse_armor) {
/*  674: 793 */           var4 = 2;
/*  675: 795 */         } else if (var2.getItem() == Items.diamond_horse_armor) {
/*  676: 797 */           var4 = 3;
/*  677:     */         }
/*  678: 800 */         if (var4 >= 0)
/*  679:     */         {
/*  680: 802 */           if (!isTame())
/*  681:     */           {
/*  682: 804 */             makeHorseRearWithSound();
/*  683: 805 */             return true;
/*  684:     */           }
/*  685: 808 */           openGUI(par1EntityPlayer);
/*  686: 809 */           return true;
/*  687:     */         }
/*  688:     */       }
/*  689: 813 */       if ((!var3) && (!func_110256_cu()))
/*  690:     */       {
/*  691: 815 */         float var7 = 0.0F;
/*  692: 816 */         short var5 = 0;
/*  693: 817 */         byte var6 = 0;
/*  694: 819 */         if (var2.getItem() == Items.wheat)
/*  695:     */         {
/*  696: 821 */           var7 = 2.0F;
/*  697: 822 */           var5 = 60;
/*  698: 823 */           var6 = 3;
/*  699:     */         }
/*  700: 825 */         else if (var2.getItem() == Items.sugar)
/*  701:     */         {
/*  702: 827 */           var7 = 1.0F;
/*  703: 828 */           var5 = 30;
/*  704: 829 */           var6 = 3;
/*  705:     */         }
/*  706: 831 */         else if (var2.getItem() == Items.bread)
/*  707:     */         {
/*  708: 833 */           var7 = 7.0F;
/*  709: 834 */           var5 = 180;
/*  710: 835 */           var6 = 3;
/*  711:     */         }
/*  712: 837 */         else if (Block.getBlockFromItem(var2.getItem()) == Blocks.hay_block)
/*  713:     */         {
/*  714: 839 */           var7 = 20.0F;
/*  715: 840 */           var5 = 180;
/*  716:     */         }
/*  717: 842 */         else if (var2.getItem() == Items.apple)
/*  718:     */         {
/*  719: 844 */           var7 = 3.0F;
/*  720: 845 */           var5 = 60;
/*  721: 846 */           var6 = 3;
/*  722:     */         }
/*  723: 848 */         else if (var2.getItem() == Items.golden_carrot)
/*  724:     */         {
/*  725: 850 */           var7 = 4.0F;
/*  726: 851 */           var5 = 60;
/*  727: 852 */           var6 = 5;
/*  728: 854 */           if ((isTame()) && (getGrowingAge() == 0))
/*  729:     */           {
/*  730: 856 */             var3 = true;
/*  731: 857 */             func_146082_f(par1EntityPlayer);
/*  732:     */           }
/*  733:     */         }
/*  734: 860 */         else if (var2.getItem() == Items.golden_apple)
/*  735:     */         {
/*  736: 862 */           var7 = 10.0F;
/*  737: 863 */           var5 = 240;
/*  738: 864 */           var6 = 10;
/*  739: 866 */           if ((isTame()) && (getGrowingAge() == 0))
/*  740:     */           {
/*  741: 868 */             var3 = true;
/*  742: 869 */             func_146082_f(par1EntityPlayer);
/*  743:     */           }
/*  744:     */         }
/*  745: 873 */         if ((getHealth() < getMaxHealth()) && (var7 > 0.0F))
/*  746:     */         {
/*  747: 875 */           heal(var7);
/*  748: 876 */           var3 = true;
/*  749:     */         }
/*  750: 879 */         if ((!isAdultHorse()) && (var5 > 0))
/*  751:     */         {
/*  752: 881 */           addGrowth(var5);
/*  753: 882 */           var3 = true;
/*  754:     */         }
/*  755: 885 */         if ((var6 > 0) && ((var3) || (!isTame())) && (var6 < getMaxTemper()))
/*  756:     */         {
/*  757: 887 */           var3 = true;
/*  758: 888 */           increaseTemper(var6);
/*  759:     */         }
/*  760: 891 */         if (var3) {
/*  761: 893 */           func_110266_cB();
/*  762:     */         }
/*  763:     */       }
/*  764: 897 */       if ((!isTame()) && (!var3))
/*  765:     */       {
/*  766: 899 */         if ((var2 != null) && (var2.interactWithEntity(par1EntityPlayer, this))) {
/*  767: 901 */           return true;
/*  768:     */         }
/*  769: 904 */         makeHorseRearWithSound();
/*  770: 905 */         return true;
/*  771:     */       }
/*  772: 908 */       if ((!var3) && (func_110229_cs()) && (!isChested()) && (var2.getItem() == Item.getItemFromBlock(Blocks.chest)))
/*  773:     */       {
/*  774: 910 */         setChested(true);
/*  775: 911 */         playSound("mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*  776: 912 */         var3 = true;
/*  777: 913 */         func_110226_cD();
/*  778:     */       }
/*  779: 916 */       if ((!var3) && (func_110253_bW()) && (!isHorseSaddled()) && (var2.getItem() == Items.saddle))
/*  780:     */       {
/*  781: 918 */         openGUI(par1EntityPlayer);
/*  782: 919 */         return true;
/*  783:     */       }
/*  784: 922 */       if (var3)
/*  785:     */       {
/*  786: 924 */         if (!par1EntityPlayer.capabilities.isCreativeMode) {
/*  787: 924 */           if (--var2.stackSize == 0) {
/*  788: 926 */             par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
/*  789:     */           }
/*  790:     */         }
/*  791: 929 */         return true;
/*  792:     */       }
/*  793:     */     }
/*  794: 933 */     if ((func_110253_bW()) && (this.riddenByEntity == null))
/*  795:     */     {
/*  796: 935 */       if ((var2 != null) && (var2.interactWithEntity(par1EntityPlayer, this))) {
/*  797: 937 */         return true;
/*  798:     */       }
/*  799: 941 */       func_110237_h(par1EntityPlayer);
/*  800: 942 */       return true;
/*  801:     */     }
/*  802: 947 */     return super.interact(par1EntityPlayer);
/*  803:     */   }
/*  804:     */   
/*  805:     */   private void func_110237_h(EntityPlayer par1EntityPlayer)
/*  806:     */   {
/*  807: 954 */     par1EntityPlayer.rotationYaw = this.rotationYaw;
/*  808: 955 */     par1EntityPlayer.rotationPitch = this.rotationPitch;
/*  809: 956 */     setEatingHaystack(false);
/*  810: 957 */     setRearing(false);
/*  811: 959 */     if (!this.worldObj.isClient) {
/*  812: 961 */       par1EntityPlayer.mountEntity(this);
/*  813:     */     }
/*  814:     */   }
/*  815:     */   
/*  816:     */   public boolean func_110259_cr()
/*  817:     */   {
/*  818: 967 */     return getHorseType() == 0;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public boolean func_110229_cs()
/*  822:     */   {
/*  823: 972 */     int var1 = getHorseType();
/*  824: 973 */     return (var1 == 2) || (var1 == 1);
/*  825:     */   }
/*  826:     */   
/*  827:     */   protected boolean isMovementBlocked()
/*  828:     */   {
/*  829: 981 */     return (this.riddenByEntity != null) && (isHorseSaddled());
/*  830:     */   }
/*  831:     */   
/*  832:     */   public boolean func_110256_cu()
/*  833:     */   {
/*  834: 986 */     int var1 = getHorseType();
/*  835: 987 */     return (var1 == 3) || (var1 == 4);
/*  836:     */   }
/*  837:     */   
/*  838:     */   public boolean func_110222_cv()
/*  839:     */   {
/*  840: 992 */     return (func_110256_cu()) || (getHorseType() == 2);
/*  841:     */   }
/*  842:     */   
/*  843:     */   public boolean isBreedingItem(ItemStack par1ItemStack)
/*  844:     */   {
/*  845:1001 */     return false;
/*  846:     */   }
/*  847:     */   
/*  848:     */   private void func_110210_cH()
/*  849:     */   {
/*  850:1006 */     this.field_110278_bp = 1;
/*  851:     */   }
/*  852:     */   
/*  853:     */   public void onDeath(DamageSource par1DamageSource)
/*  854:     */   {
/*  855:1014 */     super.onDeath(par1DamageSource);
/*  856:1016 */     if (!this.worldObj.isClient) {
/*  857:1018 */       dropChestItems();
/*  858:     */     }
/*  859:     */   }
/*  860:     */   
/*  861:     */   public void onLivingUpdate()
/*  862:     */   {
/*  863:1028 */     if (this.rand.nextInt(200) == 0) {
/*  864:1030 */       func_110210_cH();
/*  865:     */     }
/*  866:1033 */     super.onLivingUpdate();
/*  867:1035 */     if (!this.worldObj.isClient)
/*  868:     */     {
/*  869:1037 */       if ((this.rand.nextInt(900) == 0) && (this.deathTime == 0)) {
/*  870:1039 */         heal(1.0F);
/*  871:     */       }
/*  872:1042 */       if ((!isEatingHaystack()) && (this.riddenByEntity == null) && (this.rand.nextInt(300) == 0) && (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)) == Blocks.grass)) {
/*  873:1044 */         setEatingHaystack(true);
/*  874:     */       }
/*  875:1047 */       if ((isEatingHaystack()) && (++this.eatingHaystackCounter > 50))
/*  876:     */       {
/*  877:1049 */         this.eatingHaystackCounter = 0;
/*  878:1050 */         setEatingHaystack(false);
/*  879:     */       }
/*  880:1053 */       if ((func_110205_ce()) && (!isAdultHorse()) && (!isEatingHaystack()))
/*  881:     */       {
/*  882:1055 */         EntityHorse var1 = getClosestHorse(this, 16.0D);
/*  883:1057 */         if ((var1 != null) && (getDistanceSqToEntity(var1) > 4.0D))
/*  884:     */         {
/*  885:1059 */           PathEntity var2 = this.worldObj.getPathEntityToEntity(this, var1, 16.0F, true, false, false, true);
/*  886:1060 */           setPathToEntity(var2);
/*  887:     */         }
/*  888:     */       }
/*  889:     */     }
/*  890:     */   }
/*  891:     */   
/*  892:     */   public void onUpdate()
/*  893:     */   {
/*  894:1071 */     super.onUpdate();
/*  895:1073 */     if ((this.worldObj.isClient) && (this.dataWatcher.hasChanges()))
/*  896:     */     {
/*  897:1075 */       this.dataWatcher.func_111144_e();
/*  898:1076 */       func_110230_cF();
/*  899:     */     }
/*  900:1079 */     if ((this.openMouthCounter > 0) && (++this.openMouthCounter > 30))
/*  901:     */     {
/*  902:1081 */       this.openMouthCounter = 0;
/*  903:1082 */       setHorseWatchableBoolean(128, false);
/*  904:     */     }
/*  905:1085 */     if ((!this.worldObj.isClient) && (this.jumpRearingCounter > 0) && (++this.jumpRearingCounter > 20))
/*  906:     */     {
/*  907:1087 */       this.jumpRearingCounter = 0;
/*  908:1088 */       setRearing(false);
/*  909:     */     }
/*  910:1091 */     if ((this.field_110278_bp > 0) && (++this.field_110278_bp > 8)) {
/*  911:1093 */       this.field_110278_bp = 0;
/*  912:     */     }
/*  913:1096 */     if (this.field_110279_bq > 0)
/*  914:     */     {
/*  915:1098 */       this.field_110279_bq += 1;
/*  916:1100 */       if (this.field_110279_bq > 300) {
/*  917:1102 */         this.field_110279_bq = 0;
/*  918:     */       }
/*  919:     */     }
/*  920:1106 */     this.prevHeadLean = this.headLean;
/*  921:1108 */     if (isEatingHaystack())
/*  922:     */     {
/*  923:1110 */       this.headLean += (1.0F - this.headLean) * 0.4F + 0.05F;
/*  924:1112 */       if (this.headLean > 1.0F) {
/*  925:1114 */         this.headLean = 1.0F;
/*  926:     */       }
/*  927:     */     }
/*  928:     */     else
/*  929:     */     {
/*  930:1119 */       this.headLean += (0.0F - this.headLean) * 0.4F - 0.05F;
/*  931:1121 */       if (this.headLean < 0.0F) {
/*  932:1123 */         this.headLean = 0.0F;
/*  933:     */       }
/*  934:     */     }
/*  935:1127 */     this.prevRearingAmount = this.rearingAmount;
/*  936:1129 */     if (isRearing())
/*  937:     */     {
/*  938:1131 */       this.prevHeadLean = (this.headLean = 0.0F);
/*  939:1132 */       this.rearingAmount += (1.0F - this.rearingAmount) * 0.4F + 0.05F;
/*  940:1134 */       if (this.rearingAmount > 1.0F) {
/*  941:1136 */         this.rearingAmount = 1.0F;
/*  942:     */       }
/*  943:     */     }
/*  944:     */     else
/*  945:     */     {
/*  946:1141 */       this.field_110294_bI = false;
/*  947:1142 */       this.rearingAmount += (0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6F - 0.05F;
/*  948:1144 */       if (this.rearingAmount < 0.0F) {
/*  949:1146 */         this.rearingAmount = 0.0F;
/*  950:     */       }
/*  951:     */     }
/*  952:1150 */     this.prevMouthOpenness = this.mouthOpenness;
/*  953:1152 */     if (getHorseWatchableBoolean(128))
/*  954:     */     {
/*  955:1154 */       this.mouthOpenness += (1.0F - this.mouthOpenness) * 0.7F + 0.05F;
/*  956:1156 */       if (this.mouthOpenness > 1.0F) {
/*  957:1158 */         this.mouthOpenness = 1.0F;
/*  958:     */       }
/*  959:     */     }
/*  960:     */     else
/*  961:     */     {
/*  962:1163 */       this.mouthOpenness += (0.0F - this.mouthOpenness) * 0.7F - 0.05F;
/*  963:1165 */       if (this.mouthOpenness < 0.0F) {
/*  964:1167 */         this.mouthOpenness = 0.0F;
/*  965:     */       }
/*  966:     */     }
/*  967:     */   }
/*  968:     */   
/*  969:     */   private void openHorseMouth()
/*  970:     */   {
/*  971:1174 */     if (!this.worldObj.isClient)
/*  972:     */     {
/*  973:1176 */       this.openMouthCounter = 1;
/*  974:1177 */       setHorseWatchableBoolean(128, true);
/*  975:     */     }
/*  976:     */   }
/*  977:     */   
/*  978:     */   private boolean func_110200_cJ()
/*  979:     */   {
/*  980:1183 */     return (this.riddenByEntity == null) && (this.ridingEntity == null) && (isTame()) && (isAdultHorse()) && (!func_110222_cv()) && (getHealth() >= getMaxHealth());
/*  981:     */   }
/*  982:     */   
/*  983:     */   public void setEating(boolean par1)
/*  984:     */   {
/*  985:1188 */     setHorseWatchableBoolean(32, par1);
/*  986:     */   }
/*  987:     */   
/*  988:     */   public void setEatingHaystack(boolean par1)
/*  989:     */   {
/*  990:1193 */     setEating(par1);
/*  991:     */   }
/*  992:     */   
/*  993:     */   public void setRearing(boolean par1)
/*  994:     */   {
/*  995:1198 */     if (par1) {
/*  996:1200 */       setEatingHaystack(false);
/*  997:     */     }
/*  998:1203 */     setHorseWatchableBoolean(64, par1);
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   private void makeHorseRear()
/* 1002:     */   {
/* 1003:1208 */     if (!this.worldObj.isClient)
/* 1004:     */     {
/* 1005:1210 */       this.jumpRearingCounter = 1;
/* 1006:1211 */       setRearing(true);
/* 1007:     */     }
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public void makeHorseRearWithSound()
/* 1011:     */   {
/* 1012:1217 */     makeHorseRear();
/* 1013:1218 */     String var1 = getAngrySoundName();
/* 1014:1220 */     if (var1 != null) {
/* 1015:1222 */       playSound(var1, getSoundVolume(), getSoundPitch());
/* 1016:     */     }
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public void dropChestItems()
/* 1020:     */   {
/* 1021:1228 */     dropItemsInChest(this, this.horseChest);
/* 1022:1229 */     dropChests();
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   private void dropItemsInChest(Entity par1Entity, AnimalChest par2AnimalChest)
/* 1026:     */   {
/* 1027:1234 */     if ((par2AnimalChest != null) && (!this.worldObj.isClient)) {
/* 1028:1236 */       for (int var3 = 0; var3 < par2AnimalChest.getSizeInventory(); var3++)
/* 1029:     */       {
/* 1030:1238 */         ItemStack var4 = par2AnimalChest.getStackInSlot(var3);
/* 1031:1240 */         if (var4 != null) {
/* 1032:1242 */           entityDropItem(var4, 0.0F);
/* 1033:     */         }
/* 1034:     */       }
/* 1035:     */     }
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   public boolean setTamedBy(EntityPlayer par1EntityPlayer)
/* 1039:     */   {
/* 1040:1250 */     setOwnerName(par1EntityPlayer.getCommandSenderName());
/* 1041:1251 */     setHorseTamed(true);
/* 1042:1252 */     return true;
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public void moveEntityWithHeading(float par1, float par2)
/* 1046:     */   {
/* 1047:1260 */     if ((this.riddenByEntity != null) && (isHorseSaddled()))
/* 1048:     */     {
/* 1049:1262 */       this.prevRotationYaw = (this.rotationYaw = this.riddenByEntity.rotationYaw);
/* 1050:1263 */       this.rotationPitch = (this.riddenByEntity.rotationPitch * 0.5F);
/* 1051:1264 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 1052:1265 */       this.rotationYawHead = (this.renderYawOffset = this.rotationYaw);
/* 1053:1266 */       par1 = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
/* 1054:1267 */       par2 = ((EntityLivingBase)this.riddenByEntity).moveForward;
/* 1055:1269 */       if (par2 <= 0.0F)
/* 1056:     */       {
/* 1057:1271 */         par2 *= 0.25F;
/* 1058:1272 */         this.field_110285_bP = 0;
/* 1059:     */       }
/* 1060:1275 */       if ((this.onGround) && (this.jumpPower == 0.0F) && (isRearing()) && (!this.field_110294_bI))
/* 1061:     */       {
/* 1062:1277 */         par1 = 0.0F;
/* 1063:1278 */         par2 = 0.0F;
/* 1064:     */       }
/* 1065:1281 */       if ((this.jumpPower > 0.0F) && (!isHorseJumping()) && (this.onGround))
/* 1066:     */       {
/* 1067:1283 */         this.motionY = (getHorseJumpStrength() * this.jumpPower);
/* 1068:1285 */         if (isPotionActive(Potion.jump)) {
/* 1069:1287 */           this.motionY += (getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
/* 1070:     */         }
/* 1071:1290 */         setHorseJumping(true);
/* 1072:1291 */         this.isAirBorne = true;
/* 1073:1293 */         if (par2 > 0.0F)
/* 1074:     */         {
/* 1075:1295 */           float var3 = MathHelper.sin(this.rotationYaw * 3.141593F / 180.0F);
/* 1076:1296 */           float var4 = MathHelper.cos(this.rotationYaw * 3.141593F / 180.0F);
/* 1077:1297 */           this.motionX += -0.4F * var3 * this.jumpPower;
/* 1078:1298 */           this.motionZ += 0.4F * var4 * this.jumpPower;
/* 1079:1299 */           playSound("mob.horse.jump", 0.4F, 1.0F);
/* 1080:     */         }
/* 1081:1302 */         this.jumpPower = 0.0F;
/* 1082:     */       }
/* 1083:1305 */       this.stepHeight = 1.0F;
/* 1084:1306 */       this.jumpMovementFactor = (getAIMoveSpeed() * 0.1F);
/* 1085:1308 */       if (!this.worldObj.isClient)
/* 1086:     */       {
/* 1087:1310 */         setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
/* 1088:1311 */         super.moveEntityWithHeading(par1, par2);
/* 1089:     */       }
/* 1090:1314 */       if (this.onGround)
/* 1091:     */       {
/* 1092:1316 */         this.jumpPower = 0.0F;
/* 1093:1317 */         setHorseJumping(false);
/* 1094:     */       }
/* 1095:1320 */       this.prevLimbSwingAmount = this.limbSwingAmount;
/* 1096:1321 */       double var8 = this.posX - this.prevPosX;
/* 1097:1322 */       double var5 = this.posZ - this.prevPosZ;
/* 1098:1323 */       float var7 = MathHelper.sqrt_double(var8 * var8 + var5 * var5) * 4.0F;
/* 1099:1325 */       if (var7 > 1.0F) {
/* 1100:1327 */         var7 = 1.0F;
/* 1101:     */       }
/* 1102:1330 */       this.limbSwingAmount += (var7 - this.limbSwingAmount) * 0.4F;
/* 1103:1331 */       this.limbSwing += this.limbSwingAmount;
/* 1104:     */     }
/* 1105:     */     else
/* 1106:     */     {
/* 1107:1335 */       this.stepHeight = 0.5F;
/* 1108:1336 */       this.jumpMovementFactor = 0.02F;
/* 1109:1337 */       super.moveEntityWithHeading(par1, par2);
/* 1110:     */     }
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 1114:     */   {
/* 1115:1346 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 1116:1347 */     par1NBTTagCompound.setBoolean("EatingHaystack", isEatingHaystack());
/* 1117:1348 */     par1NBTTagCompound.setBoolean("ChestedHorse", isChested());
/* 1118:1349 */     par1NBTTagCompound.setBoolean("HasReproduced", getHasReproduced());
/* 1119:1350 */     par1NBTTagCompound.setBoolean("Bred", func_110205_ce());
/* 1120:1351 */     par1NBTTagCompound.setInteger("Type", getHorseType());
/* 1121:1352 */     par1NBTTagCompound.setInteger("Variant", getHorseVariant());
/* 1122:1353 */     par1NBTTagCompound.setInteger("Temper", getTemper());
/* 1123:1354 */     par1NBTTagCompound.setBoolean("Tame", isTame());
/* 1124:1355 */     par1NBTTagCompound.setString("OwnerName", getOwnerName());
/* 1125:1357 */     if (isChested())
/* 1126:     */     {
/* 1127:1359 */       NBTTagList var2 = new NBTTagList();
/* 1128:1361 */       for (int var3 = 2; var3 < this.horseChest.getSizeInventory(); var3++)
/* 1129:     */       {
/* 1130:1363 */         ItemStack var4 = this.horseChest.getStackInSlot(var3);
/* 1131:1365 */         if (var4 != null)
/* 1132:     */         {
/* 1133:1367 */           NBTTagCompound var5 = new NBTTagCompound();
/* 1134:1368 */           var5.setByte("Slot", (byte)var3);
/* 1135:1369 */           var4.writeToNBT(var5);
/* 1136:1370 */           var2.appendTag(var5);
/* 1137:     */         }
/* 1138:     */       }
/* 1139:1374 */       par1NBTTagCompound.setTag("Items", var2);
/* 1140:     */     }
/* 1141:1377 */     if (this.horseChest.getStackInSlot(1) != null) {
/* 1142:1379 */       par1NBTTagCompound.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
/* 1143:     */     }
/* 1144:1382 */     if (this.horseChest.getStackInSlot(0) != null) {
/* 1145:1384 */       par1NBTTagCompound.setTag("SaddleItem", this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
/* 1146:     */     }
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 1150:     */   {
/* 1151:1393 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 1152:1394 */     setEatingHaystack(par1NBTTagCompound.getBoolean("EatingHaystack"));
/* 1153:1395 */     func_110242_l(par1NBTTagCompound.getBoolean("Bred"));
/* 1154:1396 */     setChested(par1NBTTagCompound.getBoolean("ChestedHorse"));
/* 1155:1397 */     setHasReproduced(par1NBTTagCompound.getBoolean("HasReproduced"));
/* 1156:1398 */     setHorseType(par1NBTTagCompound.getInteger("Type"));
/* 1157:1399 */     setHorseVariant(par1NBTTagCompound.getInteger("Variant"));
/* 1158:1400 */     setTemper(par1NBTTagCompound.getInteger("Temper"));
/* 1159:1401 */     setHorseTamed(par1NBTTagCompound.getBoolean("Tame"));
/* 1160:1403 */     if (par1NBTTagCompound.func_150297_b("OwnerName", 8)) {
/* 1161:1405 */       setOwnerName(par1NBTTagCompound.getString("OwnerName"));
/* 1162:     */     }
/* 1163:1408 */     IAttributeInstance var2 = getAttributeMap().getAttributeInstanceByName("Speed");
/* 1164:1410 */     if (var2 != null) {
/* 1165:1412 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(var2.getBaseValue() * 0.25D);
/* 1166:     */     }
/* 1167:1415 */     if (isChested())
/* 1168:     */     {
/* 1169:1417 */       NBTTagList var3 = par1NBTTagCompound.getTagList("Items", 10);
/* 1170:1418 */       func_110226_cD();
/* 1171:1420 */       for (int var4 = 0; var4 < var3.tagCount(); var4++)
/* 1172:     */       {
/* 1173:1422 */         NBTTagCompound var5 = var3.getCompoundTagAt(var4);
/* 1174:1423 */         int var6 = var5.getByte("Slot") & 0xFF;
/* 1175:1425 */         if ((var6 >= 2) && (var6 < this.horseChest.getSizeInventory())) {
/* 1176:1427 */           this.horseChest.setInventorySlotContents(var6, ItemStack.loadItemStackFromNBT(var5));
/* 1177:     */         }
/* 1178:     */       }
/* 1179:     */     }
/* 1180:1434 */     if (par1NBTTagCompound.func_150297_b("ArmorItem", 10))
/* 1181:     */     {
/* 1182:1436 */       ItemStack var7 = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("ArmorItem"));
/* 1183:1438 */       if ((var7 != null) && (func_146085_a(var7.getItem()))) {
/* 1184:1440 */         this.horseChest.setInventorySlotContents(1, var7);
/* 1185:     */       }
/* 1186:     */     }
/* 1187:1444 */     if (par1NBTTagCompound.func_150297_b("SaddleItem", 10))
/* 1188:     */     {
/* 1189:1446 */       ItemStack var7 = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("SaddleItem"));
/* 1190:1448 */       if ((var7 != null) && (var7.getItem() == Items.saddle)) {
/* 1191:1450 */         this.horseChest.setInventorySlotContents(0, var7);
/* 1192:     */       }
/* 1193:     */     }
/* 1194:1453 */     else if (par1NBTTagCompound.getBoolean("Saddle"))
/* 1195:     */     {
/* 1196:1455 */       this.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
/* 1197:     */     }
/* 1198:1458 */     func_110232_cE();
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   public boolean canMateWith(EntityAnimal par1EntityAnimal)
/* 1202:     */   {
/* 1203:1466 */     if (par1EntityAnimal == this) {
/* 1204:1468 */       return false;
/* 1205:     */     }
/* 1206:1470 */     if (par1EntityAnimal.getClass() != getClass()) {
/* 1207:1472 */       return false;
/* 1208:     */     }
/* 1209:1476 */     EntityHorse var2 = (EntityHorse)par1EntityAnimal;
/* 1210:1478 */     if ((func_110200_cJ()) && (var2.func_110200_cJ()))
/* 1211:     */     {
/* 1212:1480 */       int var3 = getHorseType();
/* 1213:1481 */       int var4 = var2.getHorseType();
/* 1214:1482 */       return (var3 == var4) || ((var3 == 0) && (var4 == 1)) || ((var3 == 1) && (var4 == 0));
/* 1215:     */     }
/* 1216:1486 */     return false;
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
/* 1220:     */   {
/* 1221:1493 */     EntityHorse var2 = (EntityHorse)par1EntityAgeable;
/* 1222:1494 */     EntityHorse var3 = new EntityHorse(this.worldObj);
/* 1223:1495 */     int var4 = getHorseType();
/* 1224:1496 */     int var5 = var2.getHorseType();
/* 1225:1497 */     int var6 = 0;
/* 1226:1499 */     if (var4 == var5) {
/* 1227:1501 */       var6 = var4;
/* 1228:1503 */     } else if (((var4 == 0) && (var5 == 1)) || ((var4 == 1) && (var5 == 0))) {
/* 1229:1505 */       var6 = 2;
/* 1230:     */     }
/* 1231:1508 */     if (var6 == 0)
/* 1232:     */     {
/* 1233:1510 */       int var8 = this.rand.nextInt(9);
/* 1234:     */       int var7;
/* 1235:     */       int var7;
/* 1236:1513 */       if (var8 < 4)
/* 1237:     */       {
/* 1238:1515 */         var7 = getHorseVariant() & 0xFF;
/* 1239:     */       }
/* 1240:     */       else
/* 1241:     */       {
/* 1242:     */         int var7;
/* 1243:1517 */         if (var8 < 8) {
/* 1244:1519 */           var7 = var2.getHorseVariant() & 0xFF;
/* 1245:     */         } else {
/* 1246:1523 */           var7 = this.rand.nextInt(7);
/* 1247:     */         }
/* 1248:     */       }
/* 1249:1526 */       int var9 = this.rand.nextInt(5);
/* 1250:1528 */       if (var9 < 2) {
/* 1251:1530 */         var7 |= getHorseVariant() & 0xFF00;
/* 1252:1532 */       } else if (var9 < 4) {
/* 1253:1534 */         var7 |= var2.getHorseVariant() & 0xFF00;
/* 1254:     */       } else {
/* 1255:1538 */         var7 |= this.rand.nextInt(5) << 8 & 0xFF00;
/* 1256:     */       }
/* 1257:1541 */       var3.setHorseVariant(var7);
/* 1258:     */     }
/* 1259:1544 */     var3.setHorseType(var6);
/* 1260:1545 */     double var14 = getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + par1EntityAgeable.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + func_110267_cL();
/* 1261:1546 */     var3.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(var14 / 3.0D);
/* 1262:1547 */     double var13 = getEntityAttribute(horseJumpStrength).getBaseValue() + par1EntityAgeable.getEntityAttribute(horseJumpStrength).getBaseValue() + func_110245_cM();
/* 1263:1548 */     var3.getEntityAttribute(horseJumpStrength).setBaseValue(var13 / 3.0D);
/* 1264:1549 */     double var11 = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + par1EntityAgeable.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + func_110203_cN();
/* 1265:1550 */     var3.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(var11 / 3.0D);
/* 1266:1551 */     return var3;
/* 1267:     */   }
/* 1268:     */   
/* 1269:     */   public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
/* 1270:     */   {
/* 1271:1556 */     Object par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);
/* 1272:1557 */     boolean var2 = false;
/* 1273:1558 */     int var3 = 0;
/* 1274:     */     int var7;
/* 1275:1561 */     if ((par1EntityLivingData1 instanceof GroupData))
/* 1276:     */     {
/* 1277:1563 */       int var7 = ((GroupData)par1EntityLivingData1).field_111107_a;
/* 1278:1564 */       var3 = ((GroupData)par1EntityLivingData1).field_111106_b & 0xFF | this.rand.nextInt(5) << 8;
/* 1279:     */     }
/* 1280:     */     else
/* 1281:     */     {
/* 1282:     */       int var7;
/* 1283:1568 */       if (this.rand.nextInt(10) == 0)
/* 1284:     */       {
/* 1285:1570 */         var7 = 1;
/* 1286:     */       }
/* 1287:     */       else
/* 1288:     */       {
/* 1289:1574 */         int var4 = this.rand.nextInt(7);
/* 1290:1575 */         int var5 = this.rand.nextInt(5);
/* 1291:1576 */         var7 = 0;
/* 1292:1577 */         var3 = var4 | var5 << 8;
/* 1293:     */       }
/* 1294:1580 */       par1EntityLivingData1 = new GroupData(var7, var3);
/* 1295:     */     }
/* 1296:1583 */     setHorseType(var7);
/* 1297:1584 */     setHorseVariant(var3);
/* 1298:1586 */     if (this.rand.nextInt(5) == 0) {
/* 1299:1588 */       setGrowingAge(-24000);
/* 1300:     */     }
/* 1301:1591 */     if ((var7 != 4) && (var7 != 3))
/* 1302:     */     {
/* 1303:1593 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(func_110267_cL());
/* 1304:1595 */       if (var7 == 0) {
/* 1305:1597 */         getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(func_110203_cN());
/* 1306:     */       } else {
/* 1307:1601 */         getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.1749999970197678D);
/* 1308:     */       }
/* 1309:     */     }
/* 1310:     */     else
/* 1311:     */     {
/* 1312:1606 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
/* 1313:1607 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2000000029802322D);
/* 1314:     */     }
/* 1315:1610 */     if ((var7 != 2) && (var7 != 1)) {
/* 1316:1612 */       getEntityAttribute(horseJumpStrength).setBaseValue(func_110245_cM());
/* 1317:     */     } else {
/* 1318:1616 */       getEntityAttribute(horseJumpStrength).setBaseValue(0.5D);
/* 1319:     */     }
/* 1320:1619 */     setHealth(getMaxHealth());
/* 1321:1620 */     return (IEntityLivingData)par1EntityLivingData1;
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   public float getGrassEatingAmount(float par1)
/* 1325:     */   {
/* 1326:1625 */     return this.prevHeadLean + (this.headLean - this.prevHeadLean) * par1;
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   public float getRearingAmount(float par1)
/* 1330:     */   {
/* 1331:1630 */     return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * par1;
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   public float func_110201_q(float par1)
/* 1335:     */   {
/* 1336:1635 */     return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * par1;
/* 1337:     */   }
/* 1338:     */   
/* 1339:     */   protected boolean isAIEnabled()
/* 1340:     */   {
/* 1341:1643 */     return true;
/* 1342:     */   }
/* 1343:     */   
/* 1344:     */   public void setJumpPower(int par1)
/* 1345:     */   {
/* 1346:1648 */     if (isHorseSaddled())
/* 1347:     */     {
/* 1348:1650 */       if (par1 < 0)
/* 1349:     */       {
/* 1350:1652 */         par1 = 0;
/* 1351:     */       }
/* 1352:     */       else
/* 1353:     */       {
/* 1354:1656 */         this.field_110294_bI = true;
/* 1355:1657 */         makeHorseRear();
/* 1356:     */       }
/* 1357:1660 */       if (par1 >= 90) {
/* 1358:1662 */         this.jumpPower = 1.0F;
/* 1359:     */       } else {
/* 1360:1666 */         this.jumpPower = (0.4F + 0.4F * par1 / 90.0F);
/* 1361:     */       }
/* 1362:     */     }
/* 1363:     */   }
/* 1364:     */   
/* 1365:     */   protected void spawnHorseParticles(boolean par1)
/* 1366:     */   {
/* 1367:1676 */     String var2 = par1 ? "heart" : "smoke";
/* 1368:1678 */     for (int var3 = 0; var3 < 7; var3++)
/* 1369:     */     {
/* 1370:1680 */       double var4 = this.rand.nextGaussian() * 0.02D;
/* 1371:1681 */       double var6 = this.rand.nextGaussian() * 0.02D;
/* 1372:1682 */       double var8 = this.rand.nextGaussian() * 0.02D;
/* 1373:1683 */       this.worldObj.spawnParticle(var2, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var4, var6, var8);
/* 1374:     */     }
/* 1375:     */   }
/* 1376:     */   
/* 1377:     */   public void handleHealthUpdate(byte par1)
/* 1378:     */   {
/* 1379:1689 */     if (par1 == 7) {
/* 1380:1691 */       spawnHorseParticles(true);
/* 1381:1693 */     } else if (par1 == 6) {
/* 1382:1695 */       spawnHorseParticles(false);
/* 1383:     */     } else {
/* 1384:1699 */       super.handleHealthUpdate(par1);
/* 1385:     */     }
/* 1386:     */   }
/* 1387:     */   
/* 1388:     */   public void updateRiderPosition()
/* 1389:     */   {
/* 1390:1705 */     super.updateRiderPosition();
/* 1391:1707 */     if (this.prevRearingAmount > 0.0F)
/* 1392:     */     {
/* 1393:1709 */       float var1 = MathHelper.sin(this.renderYawOffset * 3.141593F / 180.0F);
/* 1394:1710 */       float var2 = MathHelper.cos(this.renderYawOffset * 3.141593F / 180.0F);
/* 1395:1711 */       float var3 = 0.7F * this.prevRearingAmount;
/* 1396:1712 */       float var4 = 0.15F * this.prevRearingAmount;
/* 1397:1713 */       this.riddenByEntity.setPosition(this.posX + var3 * var1, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset() + var4, this.posZ - var3 * var2);
/* 1398:1715 */       if ((this.riddenByEntity instanceof EntityLivingBase)) {
/* 1399:1717 */         ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
/* 1400:     */       }
/* 1401:     */     }
/* 1402:     */   }
/* 1403:     */   
/* 1404:     */   private float func_110267_cL()
/* 1405:     */   {
/* 1406:1724 */     return 15.0F + this.rand.nextInt(8) + this.rand.nextInt(9);
/* 1407:     */   }
/* 1408:     */   
/* 1409:     */   private double func_110245_cM()
/* 1410:     */   {
/* 1411:1729 */     return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
/* 1412:     */   }
/* 1413:     */   
/* 1414:     */   private double func_110203_cN()
/* 1415:     */   {
/* 1416:1734 */     return (0.449999988079071D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
/* 1417:     */   }
/* 1418:     */   
/* 1419:     */   public static boolean func_146085_a(Item p_146085_0_)
/* 1420:     */   {
/* 1421:1739 */     return (p_146085_0_ == Items.iron_horse_armor) || (p_146085_0_ == Items.golden_horse_armor) || (p_146085_0_ == Items.diamond_horse_armor);
/* 1422:     */   }
/* 1423:     */   
/* 1424:     */   public boolean isOnLadder()
/* 1425:     */   {
/* 1426:1747 */     return false;
/* 1427:     */   }
/* 1428:     */   
/* 1429:     */   public static class GroupData
/* 1430:     */     implements IEntityLivingData
/* 1431:     */   {
/* 1432:     */     public int field_111107_a;
/* 1433:     */     public int field_111106_b;
/* 1434:     */     private static final String __OBFID = "CL_00001643";
/* 1435:     */     
/* 1436:     */     public GroupData(int par1, int par2)
/* 1437:     */     {
/* 1438:1758 */       this.field_111107_a = par1;
/* 1439:1759 */       this.field_111106_b = par2;
/* 1440:     */     }
/* 1441:     */   }
/* 1442:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityHorse
 * JD-Core Version:    0.7.0.1
 */