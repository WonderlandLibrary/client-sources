/*    1:     */ package net.minecraft.entity.player;
/*    2:     */ 
/*    3:     */ import com.google.common.base.Charsets;
/*    4:     */ import com.mojang.authlib.GameProfile;
/*    5:     */ import java.util.Collection;
/*    6:     */ import java.util.HashMap;
/*    7:     */ import java.util.Iterator;
/*    8:     */ import java.util.List;
/*    9:     */ import java.util.Random;
/*   10:     */ import java.util.UUID;
/*   11:     */ import net.minecraft.block.Block;
/*   12:     */ import net.minecraft.block.BlockBed;
/*   13:     */ import net.minecraft.block.material.Material;
/*   14:     */ import net.minecraft.command.ICommandSender;
/*   15:     */ import net.minecraft.command.server.CommandBlockLogic;
/*   16:     */ import net.minecraft.enchantment.EnchantmentHelper;
/*   17:     */ import net.minecraft.entity.DataWatcher;
/*   18:     */ import net.minecraft.entity.Entity;
/*   19:     */ import net.minecraft.entity.EntityList;
/*   20:     */ import net.minecraft.entity.EntityList.EntityEggInfo;
/*   21:     */ import net.minecraft.entity.EntityLivingBase;
/*   22:     */ import net.minecraft.entity.IEntityMultiPart;
/*   23:     */ import net.minecraft.entity.IMerchant;
/*   24:     */ import net.minecraft.entity.SharedMonsterAttributes;
/*   25:     */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*   26:     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   27:     */ import net.minecraft.entity.boss.EntityDragonPart;
/*   28:     */ import net.minecraft.entity.item.EntityBoat;
/*   29:     */ import net.minecraft.entity.item.EntityItem;
/*   30:     */ import net.minecraft.entity.item.EntityMinecart;
/*   31:     */ import net.minecraft.entity.item.EntityMinecartHopper;
/*   32:     */ import net.minecraft.entity.monster.EntityMob;
/*   33:     */ import net.minecraft.entity.monster.IMob;
/*   34:     */ import net.minecraft.entity.passive.EntityHorse;
/*   35:     */ import net.minecraft.entity.passive.EntityPig;
/*   36:     */ import net.minecraft.entity.projectile.EntityArrow;
/*   37:     */ import net.minecraft.entity.projectile.EntityFishHook;
/*   38:     */ import net.minecraft.event.ClickEvent;
/*   39:     */ import net.minecraft.event.ClickEvent.Action;
/*   40:     */ import net.minecraft.init.Blocks;
/*   41:     */ import net.minecraft.init.Items;
/*   42:     */ import net.minecraft.inventory.Container;
/*   43:     */ import net.minecraft.inventory.ContainerPlayer;
/*   44:     */ import net.minecraft.inventory.IInventory;
/*   45:     */ import net.minecraft.inventory.InventoryEnderChest;
/*   46:     */ import net.minecraft.item.EnumAction;
/*   47:     */ import net.minecraft.item.Item;
/*   48:     */ import net.minecraft.item.ItemBow;
/*   49:     */ import net.minecraft.item.ItemFishingRod;
/*   50:     */ import net.minecraft.item.ItemStack;
/*   51:     */ import net.minecraft.nbt.NBTTagCompound;
/*   52:     */ import net.minecraft.nbt.NBTTagList;
/*   53:     */ import net.minecraft.potion.Potion;
/*   54:     */ import net.minecraft.potion.PotionEffect;
/*   55:     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*   56:     */ import net.minecraft.scoreboard.Score;
/*   57:     */ import net.minecraft.scoreboard.ScoreObjective;
/*   58:     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*   59:     */ import net.minecraft.scoreboard.Scoreboard;
/*   60:     */ import net.minecraft.scoreboard.Team;
/*   61:     */ import net.minecraft.stats.AchievementList;
/*   62:     */ import net.minecraft.stats.StatBase;
/*   63:     */ import net.minecraft.stats.StatList;
/*   64:     */ import net.minecraft.tileentity.TileEntity;
/*   65:     */ import net.minecraft.tileentity.TileEntityBeacon;
/*   66:     */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*   67:     */ import net.minecraft.tileentity.TileEntityDispenser;
/*   68:     */ import net.minecraft.tileentity.TileEntityFurnace;
/*   69:     */ import net.minecraft.tileentity.TileEntityHopper;
/*   70:     */ import net.minecraft.util.AABBPool;
/*   71:     */ import net.minecraft.util.AxisAlignedBB;
/*   72:     */ import net.minecraft.util.ChatComponentText;
/*   73:     */ import net.minecraft.util.ChatStyle;
/*   74:     */ import net.minecraft.util.ChunkCoordinates;
/*   75:     */ import net.minecraft.util.CombatTracker;
/*   76:     */ import net.minecraft.util.DamageSource;
/*   77:     */ import net.minecraft.util.FoodStats;
/*   78:     */ import net.minecraft.util.IChatComponent;
/*   79:     */ import net.minecraft.util.IIcon;
/*   80:     */ import net.minecraft.util.MathHelper;
/*   81:     */ import net.minecraft.util.Util;
/*   82:     */ import net.minecraft.util.Vec3;
/*   83:     */ import net.minecraft.util.Vec3Pool;
/*   84:     */ import net.minecraft.world.EnumDifficulty;
/*   85:     */ import net.minecraft.world.GameRules;
/*   86:     */ import net.minecraft.world.World;
/*   87:     */ import net.minecraft.world.WorldProvider;
/*   88:     */ import net.minecraft.world.WorldSettings.GameType;
/*   89:     */ import net.minecraft.world.chunk.IChunkProvider;
/*   90:     */ 
/*   91:     */ public abstract class EntityPlayer
/*   92:     */   extends EntityLivingBase
/*   93:     */   implements ICommandSender
/*   94:     */ {
/*   95:  79 */   public InventoryPlayer inventory = new InventoryPlayer(this);
/*   96:  80 */   private InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();
/*   97:     */   public Container inventoryContainer;
/*   98:     */   public Container openContainer;
/*   99:  91 */   protected FoodStats foodStats = new FoodStats();
/*  100:     */   protected int flyToggleTimer;
/*  101:     */   public float prevCameraYaw;
/*  102:     */   public float cameraYaw;
/*  103:     */   public int xpCooldown;
/*  104:     */   public double field_71091_bM;
/*  105:     */   public double field_71096_bN;
/*  106:     */   public double field_71097_bO;
/*  107:     */   public double field_71094_bP;
/*  108:     */   public double field_71095_bQ;
/*  109:     */   public double field_71085_bR;
/*  110:     */   protected boolean sleeping;
/*  111:     */   public ChunkCoordinates playerLocation;
/*  112:     */   private int sleepTimer;
/*  113:     */   public float field_71079_bU;
/*  114:     */   public float field_71082_cx;
/*  115:     */   public float field_71089_bV;
/*  116:     */   private ChunkCoordinates spawnChunk;
/*  117:     */   private boolean spawnForced;
/*  118:     */   private ChunkCoordinates startMinecartRidingCoordinate;
/*  119: 134 */   public PlayerCapabilities capabilities = new PlayerCapabilities();
/*  120:     */   public int experienceLevel;
/*  121:     */   public int experienceTotal;
/*  122:     */   public float experience;
/*  123:     */   private ItemStack itemInUse;
/*  124:     */   private int itemInUseCount;
/*  125: 159 */   protected float speedOnGround = 0.1F;
/*  126: 160 */   protected float speedInAir = 0.02F;
/*  127:     */   private int field_82249_h;
/*  128:     */   private final GameProfile field_146106_i;
/*  129:     */   public EntityFishHook fishEntity;
/*  130:     */   private static final String __OBFID = "CL_00001711";
/*  131:     */   
/*  132:     */   public EntityPlayer(World p_i45324_1_, GameProfile p_i45324_2_)
/*  133:     */   {
/*  134: 172 */     super(p_i45324_1_);
/*  135: 173 */     this.entityUniqueID = func_146094_a(p_i45324_2_);
/*  136: 174 */     this.field_146106_i = p_i45324_2_;
/*  137: 175 */     this.inventoryContainer = new ContainerPlayer(this.inventory, !p_i45324_1_.isClient, this);
/*  138: 176 */     this.openContainer = this.inventoryContainer;
/*  139: 177 */     this.yOffset = 1.62F;
/*  140: 178 */     ChunkCoordinates var3 = p_i45324_1_.getSpawnPoint();
/*  141: 179 */     setLocationAndAngles(var3.posX + 0.5D, var3.posY + 1, var3.posZ + 0.5D, 0.0F, 0.0F);
/*  142: 180 */     this.field_70741_aB = 180.0F;
/*  143: 181 */     this.fireResistance = 20;
/*  144:     */   }
/*  145:     */   
/*  146:     */   protected void applyEntityAttributes()
/*  147:     */   {
/*  148: 186 */     super.applyEntityAttributes();
/*  149: 187 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
/*  150:     */   }
/*  151:     */   
/*  152:     */   protected void entityInit()
/*  153:     */   {
/*  154: 192 */     super.entityInit();
/*  155: 193 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  156: 194 */     this.dataWatcher.addObject(17, Float.valueOf(0.0F));
/*  157: 195 */     this.dataWatcher.addObject(18, Integer.valueOf(0));
/*  158:     */   }
/*  159:     */   
/*  160:     */   public ItemStack getItemInUse()
/*  161:     */   {
/*  162: 203 */     return this.itemInUse;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public int getItemInUseCount()
/*  166:     */   {
/*  167: 211 */     return this.itemInUseCount;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public boolean isUsingItem()
/*  171:     */   {
/*  172: 219 */     return this.itemInUse != null;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public int getItemInUseDuration()
/*  176:     */   {
/*  177: 227 */     return isUsingItem() ? this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount : 0;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public void stopUsingItem()
/*  181:     */   {
/*  182: 232 */     if (this.itemInUse != null) {
/*  183: 234 */       this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
/*  184:     */     }
/*  185: 237 */     clearItemInUse();
/*  186:     */   }
/*  187:     */   
/*  188:     */   public void clearItemInUse()
/*  189:     */   {
/*  190: 242 */     this.itemInUse = null;
/*  191: 243 */     this.itemInUseCount = 0;
/*  192: 245 */     if (!this.worldObj.isClient) {
/*  193: 247 */       setEating(false);
/*  194:     */     }
/*  195:     */   }
/*  196:     */   
/*  197:     */   public boolean isBlocking()
/*  198:     */   {
/*  199: 253 */     return (isUsingItem()) && (this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.block);
/*  200:     */   }
/*  201:     */   
/*  202:     */   public void onUpdate()
/*  203:     */   {
/*  204: 261 */     if (this.itemInUse != null)
/*  205:     */     {
/*  206: 263 */       ItemStack var1 = this.inventory.getCurrentItem();
/*  207: 265 */       if (var1 == this.itemInUse)
/*  208:     */       {
/*  209: 267 */         if ((this.itemInUseCount <= 25) && (this.itemInUseCount % 4 == 0)) {
/*  210: 269 */           updateItemUse(var1, 5);
/*  211:     */         }
/*  212: 272 */         if ((--this.itemInUseCount == 0) && (!this.worldObj.isClient)) {
/*  213: 274 */           onItemUseFinish();
/*  214:     */         }
/*  215:     */       }
/*  216:     */       else
/*  217:     */       {
/*  218: 279 */         clearItemInUse();
/*  219:     */       }
/*  220:     */     }
/*  221: 283 */     if (this.xpCooldown > 0) {
/*  222: 285 */       this.xpCooldown -= 1;
/*  223:     */     }
/*  224: 288 */     if (isPlayerSleeping())
/*  225:     */     {
/*  226: 290 */       this.sleepTimer += 1;
/*  227: 292 */       if (this.sleepTimer > 100) {
/*  228: 294 */         this.sleepTimer = 100;
/*  229:     */       }
/*  230: 297 */       if (!this.worldObj.isClient) {
/*  231: 299 */         if (!isInBed()) {
/*  232: 301 */           wakeUpPlayer(true, true, false);
/*  233: 303 */         } else if (this.worldObj.isDaytime()) {
/*  234: 305 */           wakeUpPlayer(false, true, true);
/*  235:     */         }
/*  236:     */       }
/*  237:     */     }
/*  238: 309 */     else if (this.sleepTimer > 0)
/*  239:     */     {
/*  240: 311 */       this.sleepTimer += 1;
/*  241: 313 */       if (this.sleepTimer >= 110) {
/*  242: 315 */         this.sleepTimer = 0;
/*  243:     */       }
/*  244:     */     }
/*  245: 319 */     super.onUpdate();
/*  246: 321 */     if ((!this.worldObj.isClient) && (this.openContainer != null) && (!this.openContainer.canInteractWith(this)))
/*  247:     */     {
/*  248: 323 */       closeScreen();
/*  249: 324 */       this.openContainer = this.inventoryContainer;
/*  250:     */     }
/*  251: 327 */     if ((isBurning()) && (this.capabilities.disableDamage)) {
/*  252: 329 */       extinguish();
/*  253:     */     }
/*  254: 332 */     this.field_71091_bM = this.field_71094_bP;
/*  255: 333 */     this.field_71096_bN = this.field_71095_bQ;
/*  256: 334 */     this.field_71097_bO = this.field_71085_bR;
/*  257: 335 */     double var9 = this.posX - this.field_71094_bP;
/*  258: 336 */     double var3 = this.posY - this.field_71095_bQ;
/*  259: 337 */     double var5 = this.posZ - this.field_71085_bR;
/*  260: 338 */     double var7 = 10.0D;
/*  261: 340 */     if (var9 > var7) {
/*  262: 342 */       this.field_71091_bM = (this.field_71094_bP = this.posX);
/*  263:     */     }
/*  264: 345 */     if (var5 > var7) {
/*  265: 347 */       this.field_71097_bO = (this.field_71085_bR = this.posZ);
/*  266:     */     }
/*  267: 350 */     if (var3 > var7) {
/*  268: 352 */       this.field_71096_bN = (this.field_71095_bQ = this.posY);
/*  269:     */     }
/*  270: 355 */     if (var9 < -var7) {
/*  271: 357 */       this.field_71091_bM = (this.field_71094_bP = this.posX);
/*  272:     */     }
/*  273: 360 */     if (var5 < -var7) {
/*  274: 362 */       this.field_71097_bO = (this.field_71085_bR = this.posZ);
/*  275:     */     }
/*  276: 365 */     if (var3 < -var7) {
/*  277: 367 */       this.field_71096_bN = (this.field_71095_bQ = this.posY);
/*  278:     */     }
/*  279: 370 */     this.field_71094_bP += var9 * 0.25D;
/*  280: 371 */     this.field_71085_bR += var5 * 0.25D;
/*  281: 372 */     this.field_71095_bQ += var3 * 0.25D;
/*  282: 374 */     if (this.ridingEntity == null) {
/*  283: 376 */       this.startMinecartRidingCoordinate = null;
/*  284:     */     }
/*  285: 379 */     if (!this.worldObj.isClient)
/*  286:     */     {
/*  287: 381 */       this.foodStats.onUpdate(this);
/*  288: 382 */       addStat(StatList.minutesPlayedStat, 1);
/*  289:     */     }
/*  290:     */   }
/*  291:     */   
/*  292:     */   public int getMaxInPortalTime()
/*  293:     */   {
/*  294: 391 */     return this.capabilities.disableDamage ? 0 : 80;
/*  295:     */   }
/*  296:     */   
/*  297:     */   protected String getSwimSound()
/*  298:     */   {
/*  299: 396 */     return "game.player.swim";
/*  300:     */   }
/*  301:     */   
/*  302:     */   protected String getSplashSound()
/*  303:     */   {
/*  304: 401 */     return "game.player.swim.splash";
/*  305:     */   }
/*  306:     */   
/*  307:     */   public int getPortalCooldown()
/*  308:     */   {
/*  309: 409 */     return 10;
/*  310:     */   }
/*  311:     */   
/*  312:     */   public void playSound(String par1Str, float par2, float par3)
/*  313:     */   {
/*  314: 414 */     this.worldObj.playSoundToNearExcept(this, par1Str, par2, par3);
/*  315:     */   }
/*  316:     */   
/*  317:     */   protected void updateItemUse(ItemStack par1ItemStack, int par2)
/*  318:     */   {
/*  319: 422 */     if (par1ItemStack.getItemUseAction() == EnumAction.drink) {
/*  320: 424 */       playSound("random.drink", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*  321:     */     }
/*  322: 427 */     if (par1ItemStack.getItemUseAction() == EnumAction.eat)
/*  323:     */     {
/*  324: 429 */       for (int var3 = 0; var3 < par2; var3++)
/*  325:     */       {
/*  326: 431 */         Vec3 var4 = this.worldObj.getWorldVec3Pool().getVecFromPool((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*  327: 432 */         var4.rotateAroundX(-this.rotationPitch * 3.141593F / 180.0F);
/*  328: 433 */         var4.rotateAroundY(-this.rotationYaw * 3.141593F / 180.0F);
/*  329: 434 */         Vec3 var5 = this.worldObj.getWorldVec3Pool().getVecFromPool((this.rand.nextFloat() - 0.5D) * 0.3D, -this.rand.nextFloat() * 0.6D - 0.3D, 0.6D);
/*  330: 435 */         var5.rotateAroundX(-this.rotationPitch * 3.141593F / 180.0F);
/*  331: 436 */         var5.rotateAroundY(-this.rotationYaw * 3.141593F / 180.0F);
/*  332: 437 */         var5 = var5.addVector(this.posX, this.posY + getEyeHeight(), this.posZ);
/*  333: 438 */         String var6 = "iconcrack_" + Item.getIdFromItem(par1ItemStack.getItem());
/*  334: 440 */         if (par1ItemStack.getHasSubtypes()) {
/*  335: 442 */           var6 = var6 + "_" + par1ItemStack.getItemDamage();
/*  336:     */         }
/*  337: 445 */         this.worldObj.spawnParticle(var6, var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05D, var4.zCoord);
/*  338:     */       }
/*  339: 448 */       playSound("random.eat", 0.5F + 0.5F * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*  340:     */     }
/*  341:     */   }
/*  342:     */   
/*  343:     */   protected void onItemUseFinish()
/*  344:     */   {
/*  345: 457 */     if (this.itemInUse != null)
/*  346:     */     {
/*  347: 459 */       updateItemUse(this.itemInUse, 16);
/*  348: 460 */       int var1 = this.itemInUse.stackSize;
/*  349: 461 */       ItemStack var2 = this.itemInUse.onFoodEaten(this.worldObj, this);
/*  350: 463 */       if ((var2 != this.itemInUse) || ((var2 != null) && (var2.stackSize != var1)))
/*  351:     */       {
/*  352: 465 */         this.inventory.mainInventory[this.inventory.currentItem] = var2;
/*  353: 467 */         if (var2.stackSize == 0) {
/*  354: 469 */           this.inventory.mainInventory[this.inventory.currentItem] = null;
/*  355:     */         }
/*  356:     */       }
/*  357: 473 */       clearItemInUse();
/*  358:     */     }
/*  359:     */   }
/*  360:     */   
/*  361:     */   public void handleHealthUpdate(byte par1)
/*  362:     */   {
/*  363: 479 */     if (par1 == 9) {
/*  364: 481 */       onItemUseFinish();
/*  365:     */     } else {
/*  366: 485 */       super.handleHealthUpdate(par1);
/*  367:     */     }
/*  368:     */   }
/*  369:     */   
/*  370:     */   protected boolean isMovementBlocked()
/*  371:     */   {
/*  372: 494 */     return (getHealth() <= 0.0F) || (isPlayerSleeping());
/*  373:     */   }
/*  374:     */   
/*  375:     */   protected void closeScreen()
/*  376:     */   {
/*  377: 502 */     this.openContainer = this.inventoryContainer;
/*  378:     */   }
/*  379:     */   
/*  380:     */   public void mountEntity(Entity par1Entity)
/*  381:     */   {
/*  382: 510 */     if ((this.ridingEntity != null) && (par1Entity == null))
/*  383:     */     {
/*  384: 512 */       if (!this.worldObj.isClient) {
/*  385: 514 */         dismountEntity(this.ridingEntity);
/*  386:     */       }
/*  387: 517 */       if (this.ridingEntity != null) {
/*  388: 519 */         this.ridingEntity.riddenByEntity = null;
/*  389:     */       }
/*  390: 522 */       this.ridingEntity = null;
/*  391:     */     }
/*  392:     */     else
/*  393:     */     {
/*  394: 526 */       super.mountEntity(par1Entity);
/*  395:     */     }
/*  396:     */   }
/*  397:     */   
/*  398:     */   public void updateRidden()
/*  399:     */   {
/*  400: 535 */     if ((!this.worldObj.isClient) && (isSneaking()))
/*  401:     */     {
/*  402: 537 */       mountEntity(null);
/*  403: 538 */       setSneaking(false);
/*  404:     */     }
/*  405:     */     else
/*  406:     */     {
/*  407: 542 */       double var1 = this.posX;
/*  408: 543 */       double var3 = this.posY;
/*  409: 544 */       double var5 = this.posZ;
/*  410: 545 */       float var7 = this.rotationYaw;
/*  411: 546 */       float var8 = this.rotationPitch;
/*  412: 547 */       super.updateRidden();
/*  413: 548 */       this.prevCameraYaw = this.cameraYaw;
/*  414: 549 */       this.cameraYaw = 0.0F;
/*  415: 550 */       addMountedMovementStat(this.posX - var1, this.posY - var3, this.posZ - var5);
/*  416: 552 */       if ((this.ridingEntity instanceof EntityPig))
/*  417:     */       {
/*  418: 554 */         this.rotationPitch = var8;
/*  419: 555 */         this.rotationYaw = var7;
/*  420: 556 */         this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
/*  421:     */       }
/*  422:     */     }
/*  423:     */   }
/*  424:     */   
/*  425:     */   public void preparePlayerToSpawn()
/*  426:     */   {
/*  427: 567 */     this.yOffset = 1.62F;
/*  428: 568 */     setSize(0.6F, 1.8F);
/*  429: 569 */     super.preparePlayerToSpawn();
/*  430: 570 */     setHealth(getMaxHealth());
/*  431: 571 */     this.deathTime = 0;
/*  432:     */   }
/*  433:     */   
/*  434:     */   protected void updateEntityActionState()
/*  435:     */   {
/*  436: 576 */     super.updateEntityActionState();
/*  437: 577 */     updateArmSwingProgress();
/*  438:     */   }
/*  439:     */   
/*  440:     */   public void onLivingUpdate()
/*  441:     */   {
/*  442: 586 */     if (this.flyToggleTimer > 0) {
/*  443: 588 */       this.flyToggleTimer -= 1;
/*  444:     */     }
/*  445: 591 */     if ((this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) && (getHealth() < getMaxHealth()) && (this.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration")) && (this.ticksExisted % 20 * 12 == 0)) {
/*  446: 593 */       heal(1.0F);
/*  447:     */     }
/*  448: 596 */     this.inventory.decrementAnimations();
/*  449: 597 */     this.prevCameraYaw = this.cameraYaw;
/*  450: 598 */     super.onLivingUpdate();
/*  451: 599 */     IAttributeInstance var1 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*  452: 601 */     if (!this.worldObj.isClient) {
/*  453: 603 */       var1.setBaseValue(this.capabilities.getWalkSpeed());
/*  454:     */     }
/*  455: 606 */     this.jumpMovementFactor = this.speedInAir;
/*  456: 608 */     if (isSprinting()) {
/*  457: 610 */       this.jumpMovementFactor = ((float)(this.jumpMovementFactor + this.speedInAir * 0.3D));
/*  458:     */     }
/*  459: 613 */     setAIMoveSpeed((float)var1.getAttributeValue());
/*  460: 614 */     float var2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  461: 615 */     float var3 = (float)Math.atan(-this.motionY * 0.2000000029802322D) * 15.0F;
/*  462: 617 */     if (var2 > 0.1F) {
/*  463: 619 */       var2 = 0.1F;
/*  464:     */     }
/*  465: 622 */     if ((!this.onGround) || (getHealth() <= 0.0F)) {
/*  466: 624 */       var2 = 0.0F;
/*  467:     */     }
/*  468: 627 */     if ((this.onGround) || (getHealth() <= 0.0F)) {
/*  469: 629 */       var3 = 0.0F;
/*  470:     */     }
/*  471: 632 */     this.cameraYaw += (var2 - this.cameraYaw) * 0.4F;
/*  472: 633 */     this.cameraPitch += (var3 - this.cameraPitch) * 0.8F;
/*  473: 635 */     if (getHealth() > 0.0F)
/*  474:     */     {
/*  475: 637 */       AxisAlignedBB var4 = null;
/*  476: 639 */       if ((this.ridingEntity != null) && (!this.ridingEntity.isDead)) {
/*  477: 641 */         var4 = this.boundingBox.func_111270_a(this.ridingEntity.boundingBox).expand(1.0D, 0.0D, 1.0D);
/*  478:     */       } else {
/*  479: 645 */         var4 = this.boundingBox.expand(1.0D, 0.5D, 1.0D);
/*  480:     */       }
/*  481: 648 */       List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, var4);
/*  482: 650 */       if (var5 != null) {
/*  483: 652 */         for (int var6 = 0; var6 < var5.size(); var6++)
/*  484:     */         {
/*  485: 654 */           Entity var7 = (Entity)var5.get(var6);
/*  486: 656 */           if (!var7.isDead) {
/*  487: 658 */             collideWithPlayer(var7);
/*  488:     */           }
/*  489:     */         }
/*  490:     */       }
/*  491:     */     }
/*  492:     */   }
/*  493:     */   
/*  494:     */   private void collideWithPlayer(Entity par1Entity)
/*  495:     */   {
/*  496: 667 */     par1Entity.onCollideWithPlayer(this);
/*  497:     */   }
/*  498:     */   
/*  499:     */   public int getScore()
/*  500:     */   {
/*  501: 672 */     return this.dataWatcher.getWatchableObjectInt(18);
/*  502:     */   }
/*  503:     */   
/*  504:     */   public void setScore(int par1)
/*  505:     */   {
/*  506: 680 */     this.dataWatcher.updateObject(18, Integer.valueOf(par1));
/*  507:     */   }
/*  508:     */   
/*  509:     */   public void addScore(int par1)
/*  510:     */   {
/*  511: 688 */     int var2 = getScore();
/*  512: 689 */     this.dataWatcher.updateObject(18, Integer.valueOf(var2 + par1));
/*  513:     */   }
/*  514:     */   
/*  515:     */   public void onDeath(DamageSource par1DamageSource)
/*  516:     */   {
/*  517: 697 */     super.onDeath(par1DamageSource);
/*  518: 698 */     setSize(0.2F, 0.2F);
/*  519: 699 */     setPosition(this.posX, this.posY, this.posZ);
/*  520: 700 */     this.motionY = 0.1000000014901161D;
/*  521: 702 */     if (getCommandSenderName().equals("Notch")) {
/*  522: 704 */       func_146097_a(new ItemStack(Items.apple, 1), true, false);
/*  523:     */     }
/*  524: 707 */     if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
/*  525: 709 */       this.inventory.dropAllItems();
/*  526:     */     }
/*  527: 712 */     if (par1DamageSource != null)
/*  528:     */     {
/*  529: 714 */       this.motionX = (-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.141593F / 180.0F) * 0.1F);
/*  530: 715 */       this.motionZ = (-MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.141593F / 180.0F) * 0.1F);
/*  531:     */     }
/*  532:     */     else
/*  533:     */     {
/*  534: 719 */       this.motionX = (this.motionZ = 0.0D);
/*  535:     */     }
/*  536: 722 */     this.yOffset = 0.1F;
/*  537: 723 */     addStat(StatList.deathsStat, 1);
/*  538:     */   }
/*  539:     */   
/*  540:     */   protected String getHurtSound()
/*  541:     */   {
/*  542: 731 */     return "game.player.hurt";
/*  543:     */   }
/*  544:     */   
/*  545:     */   protected String getDeathSound()
/*  546:     */   {
/*  547: 739 */     return "game.player.die";
/*  548:     */   }
/*  549:     */   
/*  550:     */   public void addToPlayerScore(Entity par1Entity, int par2)
/*  551:     */   {
/*  552: 748 */     addScore(par2);
/*  553: 749 */     Collection var3 = getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.totalKillCount);
/*  554: 751 */     if ((par1Entity instanceof EntityPlayer))
/*  555:     */     {
/*  556: 753 */       addStat(StatList.playerKillsStat, 1);
/*  557: 754 */       var3.addAll(getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.playerKillCount));
/*  558:     */     }
/*  559:     */     else
/*  560:     */     {
/*  561: 758 */       addStat(StatList.mobKillsStat, 1);
/*  562:     */     }
/*  563: 761 */     Iterator var4 = var3.iterator();
/*  564: 763 */     while (var4.hasNext())
/*  565:     */     {
/*  566: 765 */       ScoreObjective var5 = (ScoreObjective)var4.next();
/*  567: 766 */       Score var6 = getWorldScoreboard().func_96529_a(getCommandSenderName(), var5);
/*  568: 767 */       var6.func_96648_a();
/*  569:     */     }
/*  570:     */   }
/*  571:     */   
/*  572:     */   public EntityItem dropOneItem(boolean par1)
/*  573:     */   {
/*  574: 776 */     return func_146097_a(this.inventory.decrStackSize(this.inventory.currentItem, (par1) && (this.inventory.getCurrentItem() != null) ? this.inventory.getCurrentItem().stackSize : 1), false, true);
/*  575:     */   }
/*  576:     */   
/*  577:     */   public EntityItem dropPlayerItemWithRandomChoice(ItemStack par1ItemStack, boolean par2)
/*  578:     */   {
/*  579: 784 */     return func_146097_a(par1ItemStack, false, false);
/*  580:     */   }
/*  581:     */   
/*  582:     */   public EntityItem func_146097_a(ItemStack p_146097_1_, boolean p_146097_2_, boolean p_146097_3_)
/*  583:     */   {
/*  584: 789 */     if (p_146097_1_ == null) {
/*  585: 791 */       return null;
/*  586:     */     }
/*  587: 793 */     if (p_146097_1_.stackSize == 0) {
/*  588: 795 */       return null;
/*  589:     */     }
/*  590: 799 */     EntityItem var4 = new EntityItem(this.worldObj, this.posX, this.posY - 0.300000011920929D + getEyeHeight(), this.posZ, p_146097_1_);
/*  591: 800 */     var4.delayBeforeCanPickup = 40;
/*  592: 802 */     if (p_146097_3_) {
/*  593: 804 */       var4.func_145799_b(getCommandSenderName());
/*  594:     */     }
/*  595: 807 */     float var5 = 0.1F;
/*  596: 810 */     if (p_146097_2_)
/*  597:     */     {
/*  598: 812 */       float var6 = this.rand.nextFloat() * 0.5F;
/*  599: 813 */       float var7 = this.rand.nextFloat() * 3.141593F * 2.0F;
/*  600: 814 */       var4.motionX = (-MathHelper.sin(var7) * var6);
/*  601: 815 */       var4.motionZ = (MathHelper.cos(var7) * var6);
/*  602: 816 */       var4.motionY = 0.2000000029802322D;
/*  603:     */     }
/*  604:     */     else
/*  605:     */     {
/*  606: 820 */       var5 = 0.3F;
/*  607: 821 */       var4.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F) * var5);
/*  608: 822 */       var4.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F) * var5);
/*  609: 823 */       var4.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.141593F) * var5 + 0.1F);
/*  610: 824 */       var5 = 0.02F;
/*  611: 825 */       float var6 = this.rand.nextFloat() * 3.141593F * 2.0F;
/*  612: 826 */       var5 *= this.rand.nextFloat();
/*  613: 827 */       var4.motionX += Math.cos(var6) * var5;
/*  614: 828 */       var4.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
/*  615: 829 */       var4.motionZ += Math.sin(var6) * var5;
/*  616:     */     }
/*  617: 832 */     joinEntityItemWithWorld(var4);
/*  618: 833 */     addStat(StatList.dropStat, 1);
/*  619: 834 */     return var4;
/*  620:     */   }
/*  621:     */   
/*  622:     */   protected void joinEntityItemWithWorld(EntityItem par1EntityItem)
/*  623:     */   {
/*  624: 843 */     this.worldObj.spawnEntityInWorld(par1EntityItem);
/*  625:     */   }
/*  626:     */   
/*  627:     */   public float getCurrentPlayerStrVsBlock(Block p_146096_1_, boolean p_146096_2_)
/*  628:     */   {
/*  629: 851 */     float var3 = this.inventory.func_146023_a(p_146096_1_);
/*  630: 853 */     if (var3 > 1.0F)
/*  631:     */     {
/*  632: 855 */       int var4 = EnchantmentHelper.getEfficiencyModifier(this);
/*  633: 856 */       ItemStack var5 = this.inventory.getCurrentItem();
/*  634: 858 */       if ((var4 > 0) && (var5 != null))
/*  635:     */       {
/*  636: 860 */         float var6 = var4 * var4 + 1;
/*  637: 862 */         if ((!var5.func_150998_b(p_146096_1_)) && (var3 <= 1.0F)) {
/*  638: 864 */           var3 += var6 * 0.08F;
/*  639:     */         } else {
/*  640: 868 */           var3 += var6;
/*  641:     */         }
/*  642:     */       }
/*  643:     */     }
/*  644: 873 */     if (isPotionActive(Potion.digSpeed)) {
/*  645: 875 */       var3 *= (1.0F + (getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F);
/*  646:     */     }
/*  647: 878 */     if (isPotionActive(Potion.digSlowdown)) {
/*  648: 880 */       var3 *= (1.0F - (getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1) * 0.2F);
/*  649:     */     }
/*  650: 883 */     if ((isInsideOfMaterial(Material.water)) && (!EnchantmentHelper.getAquaAffinityModifier(this))) {
/*  651: 885 */       var3 /= 5.0F;
/*  652:     */     }
/*  653: 888 */     if (!this.onGround) {
/*  654: 890 */       var3 /= 5.0F;
/*  655:     */     }
/*  656: 893 */     return var3;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public boolean canHarvestBlock(Block p_146099_1_)
/*  660:     */   {
/*  661: 901 */     return this.inventory.func_146025_b(p_146099_1_);
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  665:     */   {
/*  666: 909 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  667: 910 */     this.entityUniqueID = func_146094_a(this.field_146106_i);
/*  668: 911 */     NBTTagList var2 = par1NBTTagCompound.getTagList("Inventory", 10);
/*  669: 912 */     this.inventory.readFromNBT(var2);
/*  670: 913 */     this.inventory.currentItem = par1NBTTagCompound.getInteger("SelectedItemSlot");
/*  671: 914 */     this.sleeping = par1NBTTagCompound.getBoolean("Sleeping");
/*  672: 915 */     this.sleepTimer = par1NBTTagCompound.getShort("SleepTimer");
/*  673: 916 */     this.experience = par1NBTTagCompound.getFloat("XpP");
/*  674: 917 */     this.experienceLevel = par1NBTTagCompound.getInteger("XpLevel");
/*  675: 918 */     this.experienceTotal = par1NBTTagCompound.getInteger("XpTotal");
/*  676: 919 */     setScore(par1NBTTagCompound.getInteger("Score"));
/*  677: 921 */     if (this.sleeping)
/*  678:     */     {
/*  679: 923 */       this.playerLocation = new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
/*  680: 924 */       wakeUpPlayer(true, true, false);
/*  681:     */     }
/*  682: 927 */     if ((par1NBTTagCompound.func_150297_b("SpawnX", 99)) && (par1NBTTagCompound.func_150297_b("SpawnY", 99)) && (par1NBTTagCompound.func_150297_b("SpawnZ", 99)))
/*  683:     */     {
/*  684: 929 */       this.spawnChunk = new ChunkCoordinates(par1NBTTagCompound.getInteger("SpawnX"), par1NBTTagCompound.getInteger("SpawnY"), par1NBTTagCompound.getInteger("SpawnZ"));
/*  685: 930 */       this.spawnForced = par1NBTTagCompound.getBoolean("SpawnForced");
/*  686:     */     }
/*  687: 933 */     this.foodStats.readNBT(par1NBTTagCompound);
/*  688: 934 */     this.capabilities.readCapabilitiesFromNBT(par1NBTTagCompound);
/*  689: 936 */     if (par1NBTTagCompound.func_150297_b("EnderItems", 9))
/*  690:     */     {
/*  691: 938 */       NBTTagList var3 = par1NBTTagCompound.getTagList("EnderItems", 10);
/*  692: 939 */       this.theInventoryEnderChest.loadInventoryFromNBT(var3);
/*  693:     */     }
/*  694:     */   }
/*  695:     */   
/*  696:     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  697:     */   {
/*  698: 948 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  699: 949 */     par1NBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
/*  700: 950 */     par1NBTTagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
/*  701: 951 */     par1NBTTagCompound.setBoolean("Sleeping", this.sleeping);
/*  702: 952 */     par1NBTTagCompound.setShort("SleepTimer", (short)this.sleepTimer);
/*  703: 953 */     par1NBTTagCompound.setFloat("XpP", this.experience);
/*  704: 954 */     par1NBTTagCompound.setInteger("XpLevel", this.experienceLevel);
/*  705: 955 */     par1NBTTagCompound.setInteger("XpTotal", this.experienceTotal);
/*  706: 956 */     par1NBTTagCompound.setInteger("Score", getScore());
/*  707: 958 */     if (this.spawnChunk != null)
/*  708:     */     {
/*  709: 960 */       par1NBTTagCompound.setInteger("SpawnX", this.spawnChunk.posX);
/*  710: 961 */       par1NBTTagCompound.setInteger("SpawnY", this.spawnChunk.posY);
/*  711: 962 */       par1NBTTagCompound.setInteger("SpawnZ", this.spawnChunk.posZ);
/*  712: 963 */       par1NBTTagCompound.setBoolean("SpawnForced", this.spawnForced);
/*  713:     */     }
/*  714: 966 */     this.foodStats.writeNBT(par1NBTTagCompound);
/*  715: 967 */     this.capabilities.writeCapabilitiesToNBT(par1NBTTagCompound);
/*  716: 968 */     par1NBTTagCompound.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
/*  717:     */   }
/*  718:     */   
/*  719:     */   public void displayGUIChest(IInventory par1IInventory) {}
/*  720:     */   
/*  721:     */   public void func_146093_a(TileEntityHopper p_146093_1_) {}
/*  722:     */   
/*  723:     */   public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper) {}
/*  724:     */   
/*  725:     */   public void displayGUIHorse(EntityHorse par1EntityHorse, IInventory par2IInventory) {}
/*  726:     */   
/*  727:     */   public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str) {}
/*  728:     */   
/*  729:     */   public void displayGUIAnvil(int par1, int par2, int par3) {}
/*  730:     */   
/*  731:     */   public void displayGUIWorkbench(int par1, int par2, int par3) {}
/*  732:     */   
/*  733:     */   public float getEyeHeight()
/*  734:     */   {
/*  735: 996 */     return 0.12F;
/*  736:     */   }
/*  737:     */   
/*  738:     */   protected void resetHeight()
/*  739:     */   {
/*  740:1004 */     this.yOffset = 1.62F;
/*  741:     */   }
/*  742:     */   
/*  743:     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  744:     */   {
/*  745:1012 */     if (isEntityInvulnerable()) {
/*  746:1014 */       return false;
/*  747:     */     }
/*  748:1016 */     if ((this.capabilities.disableDamage) && (!par1DamageSource.canHarmInCreative())) {
/*  749:1018 */       return false;
/*  750:     */     }
/*  751:1022 */     this.entityAge = 0;
/*  752:1024 */     if (getHealth() <= 0.0F) {
/*  753:1026 */       return false;
/*  754:     */     }
/*  755:1030 */     if ((isPlayerSleeping()) && (!this.worldObj.isClient)) {
/*  756:1032 */       wakeUpPlayer(true, true, false);
/*  757:     */     }
/*  758:1035 */     if (par1DamageSource.isDifficultyScaled())
/*  759:     */     {
/*  760:1037 */       if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
/*  761:1039 */         par2 = 0.0F;
/*  762:     */       }
/*  763:1042 */       if (this.worldObj.difficultySetting == EnumDifficulty.EASY) {
/*  764:1044 */         par2 = par2 / 2.0F + 1.0F;
/*  765:     */       }
/*  766:1047 */       if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
/*  767:1049 */         par2 = par2 * 3.0F / 2.0F;
/*  768:     */       }
/*  769:     */     }
/*  770:1053 */     if (par2 == 0.0F) {
/*  771:1055 */       return false;
/*  772:     */     }
/*  773:1059 */     Entity var3 = par1DamageSource.getEntity();
/*  774:1061 */     if (((var3 instanceof EntityArrow)) && (((EntityArrow)var3).shootingEntity != null)) {
/*  775:1063 */       var3 = ((EntityArrow)var3).shootingEntity;
/*  776:     */     }
/*  777:1066 */     addStat(StatList.damageTakenStat, Math.round(par2 * 10.0F));
/*  778:1067 */     return super.attackEntityFrom(par1DamageSource, par2);
/*  779:     */   }
/*  780:     */   
/*  781:     */   public boolean canAttackPlayer(EntityPlayer par1EntityPlayer)
/*  782:     */   {
/*  783:1075 */     Team var2 = getTeam();
/*  784:1076 */     Team var3 = par1EntityPlayer.getTeam();
/*  785:1077 */     return !var2.isSameTeam(var3) ? true : var2 == null ? true : var2.getAllowFriendlyFire();
/*  786:     */   }
/*  787:     */   
/*  788:     */   protected void damageArmor(float par1)
/*  789:     */   {
/*  790:1082 */     this.inventory.damageArmor(par1);
/*  791:     */   }
/*  792:     */   
/*  793:     */   public int getTotalArmorValue()
/*  794:     */   {
/*  795:1090 */     return this.inventory.getTotalArmorValue();
/*  796:     */   }
/*  797:     */   
/*  798:     */   public float getArmorVisibility()
/*  799:     */   {
/*  800:1099 */     int var1 = 0;
/*  801:1100 */     ItemStack[] var2 = this.inventory.armorInventory;
/*  802:1101 */     int var3 = var2.length;
/*  803:1103 */     for (int var4 = 0; var4 < var3; var4++)
/*  804:     */     {
/*  805:1105 */       ItemStack var5 = var2[var4];
/*  806:1107 */       if (var5 != null) {
/*  807:1109 */         var1++;
/*  808:     */       }
/*  809:     */     }
/*  810:1113 */     return var1 / this.inventory.armorInventory.length;
/*  811:     */   }
/*  812:     */   
/*  813:     */   protected void damageEntity(DamageSource par1DamageSource, float par2)
/*  814:     */   {
/*  815:1122 */     if (!isEntityInvulnerable())
/*  816:     */     {
/*  817:1124 */       if ((!par1DamageSource.isUnblockable()) && (isBlocking()) && (par2 > 0.0F)) {
/*  818:1126 */         par2 = (1.0F + par2) * 0.5F;
/*  819:     */       }
/*  820:1129 */       par2 = applyArmorCalculations(par1DamageSource, par2);
/*  821:1130 */       par2 = applyPotionDamageCalculations(par1DamageSource, par2);
/*  822:1131 */       float var3 = par2;
/*  823:1132 */       par2 = Math.max(par2 - getAbsorptionAmount(), 0.0F);
/*  824:1133 */       setAbsorptionAmount(getAbsorptionAmount() - (var3 - par2));
/*  825:1135 */       if (par2 != 0.0F)
/*  826:     */       {
/*  827:1137 */         addExhaustion(par1DamageSource.getHungerDamage());
/*  828:1138 */         float var4 = getHealth();
/*  829:1139 */         setHealth(getHealth() - par2);
/*  830:1140 */         func_110142_aN().func_94547_a(par1DamageSource, var4, par2);
/*  831:     */       }
/*  832:     */     }
/*  833:     */   }
/*  834:     */   
/*  835:     */   public void func_146101_a(TileEntityFurnace p_146101_1_) {}
/*  836:     */   
/*  837:     */   public void func_146102_a(TileEntityDispenser p_146102_1_) {}
/*  838:     */   
/*  839:     */   public void func_146100_a(TileEntity p_146100_1_) {}
/*  840:     */   
/*  841:     */   public void func_146095_a(CommandBlockLogic p_146095_1_) {}
/*  842:     */   
/*  843:     */   public void func_146098_a(TileEntityBrewingStand p_146098_1_) {}
/*  844:     */   
/*  845:     */   public void func_146104_a(TileEntityBeacon p_146104_1_) {}
/*  846:     */   
/*  847:     */   public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str) {}
/*  848:     */   
/*  849:     */   public void displayGUIBook(ItemStack par1ItemStack) {}
/*  850:     */   
/*  851:     */   public boolean interactWith(Entity par1Entity)
/*  852:     */   {
/*  853:1166 */     ItemStack var2 = getCurrentEquippedItem();
/*  854:1167 */     ItemStack var3 = var2 != null ? var2.copy() : null;
/*  855:1169 */     if (!par1Entity.interactFirst(this))
/*  856:     */     {
/*  857:1171 */       if ((var2 != null) && ((par1Entity instanceof EntityLivingBase)))
/*  858:     */       {
/*  859:1173 */         if (this.capabilities.isCreativeMode) {
/*  860:1175 */           var2 = var3;
/*  861:     */         }
/*  862:1178 */         if (var2.interactWithEntity(this, (EntityLivingBase)par1Entity))
/*  863:     */         {
/*  864:1180 */           if ((var2.stackSize <= 0) && (!this.capabilities.isCreativeMode)) {
/*  865:1182 */             destroyCurrentEquippedItem();
/*  866:     */           }
/*  867:1185 */           return true;
/*  868:     */         }
/*  869:     */       }
/*  870:1189 */       return false;
/*  871:     */     }
/*  872:1193 */     if ((var2 != null) && (var2 == getCurrentEquippedItem())) {
/*  873:1195 */       if ((var2.stackSize <= 0) && (!this.capabilities.isCreativeMode)) {
/*  874:1197 */         destroyCurrentEquippedItem();
/*  875:1199 */       } else if ((var2.stackSize < var3.stackSize) && (this.capabilities.isCreativeMode)) {
/*  876:1201 */         var2.stackSize = var3.stackSize;
/*  877:     */       }
/*  878:     */     }
/*  879:1205 */     return true;
/*  880:     */   }
/*  881:     */   
/*  882:     */   public ItemStack getCurrentEquippedItem()
/*  883:     */   {
/*  884:1214 */     return this.inventory.getCurrentItem();
/*  885:     */   }
/*  886:     */   
/*  887:     */   public void destroyCurrentEquippedItem()
/*  888:     */   {
/*  889:1222 */     this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
/*  890:     */   }
/*  891:     */   
/*  892:     */   public double getYOffset()
/*  893:     */   {
/*  894:1230 */     return this.yOffset - 0.5F;
/*  895:     */   }
/*  896:     */   
/*  897:     */   public void attackTargetEntityWithCurrentItem(Entity par1Entity)
/*  898:     */   {
/*  899:1239 */     if (par1Entity.canAttackWithItem()) {
/*  900:1241 */       if (!par1Entity.hitByEntity(this))
/*  901:     */       {
/*  902:1243 */         float var2 = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
/*  903:1244 */         int var3 = 0;
/*  904:1245 */         float var4 = 0.0F;
/*  905:1247 */         if ((par1Entity instanceof EntityLivingBase))
/*  906:     */         {
/*  907:1249 */           var4 = EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)par1Entity);
/*  908:1250 */           var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)par1Entity);
/*  909:     */         }
/*  910:1253 */         if (isSprinting()) {
/*  911:1255 */           var3++;
/*  912:     */         }
/*  913:1258 */         if ((var2 > 0.0F) || (var4 > 0.0F))
/*  914:     */         {
/*  915:1260 */           boolean var5 = (this.fallDistance > 0.0F) && (!this.onGround) && (!isOnLadder()) && (!isInWater()) && (!isPotionActive(Potion.blindness)) && (this.ridingEntity == null) && ((par1Entity instanceof EntityLivingBase));
/*  916:1262 */           if ((var5) && (var2 > 0.0F)) {
/*  917:1264 */             var2 *= 1.5F;
/*  918:     */           }
/*  919:1267 */           var2 += var4;
/*  920:1268 */           boolean var6 = false;
/*  921:1269 */           int var7 = EnchantmentHelper.getFireAspectModifier(this);
/*  922:1271 */           if (((par1Entity instanceof EntityLivingBase)) && (var7 > 0) && (!par1Entity.isBurning()))
/*  923:     */           {
/*  924:1273 */             var6 = true;
/*  925:1274 */             par1Entity.setFire(1);
/*  926:     */           }
/*  927:1277 */           boolean var8 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(this), var2);
/*  928:1279 */           if (var8)
/*  929:     */           {
/*  930:1281 */             if (var3 > 0)
/*  931:     */             {
/*  932:1283 */               par1Entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.141593F / 180.0F) * var3 * 0.5F, 0.1D, MathHelper.cos(this.rotationYaw * 3.141593F / 180.0F) * var3 * 0.5F);
/*  933:1284 */               this.motionX *= 0.6D;
/*  934:1285 */               this.motionZ *= 0.6D;
/*  935:1286 */               setSprinting(false);
/*  936:     */             }
/*  937:1289 */             if (var5) {
/*  938:1291 */               onCriticalHit(par1Entity);
/*  939:     */             }
/*  940:1294 */             if (var4 > 0.0F) {
/*  941:1296 */               onEnchantmentCritical(par1Entity);
/*  942:     */             }
/*  943:1299 */             if (var2 >= 18.0F) {
/*  944:1301 */               triggerAchievement(AchievementList.overkill);
/*  945:     */             }
/*  946:1304 */             setLastAttacker(par1Entity);
/*  947:1306 */             if ((par1Entity instanceof EntityLivingBase)) {
/*  948:1308 */               EnchantmentHelper.func_151384_a((EntityLivingBase)par1Entity, this);
/*  949:     */             }
/*  950:1311 */             EnchantmentHelper.func_151385_b(this, par1Entity);
/*  951:1312 */             ItemStack var9 = getCurrentEquippedItem();
/*  952:1313 */             Object var10 = par1Entity;
/*  953:1315 */             if ((par1Entity instanceof EntityDragonPart))
/*  954:     */             {
/*  955:1317 */               IEntityMultiPart var11 = ((EntityDragonPart)par1Entity).entityDragonObj;
/*  956:1319 */               if ((var11 != null) && ((var11 instanceof EntityLivingBase))) {
/*  957:1321 */                 var10 = (EntityLivingBase)var11;
/*  958:     */               }
/*  959:     */             }
/*  960:1325 */             if ((var9 != null) && ((var10 instanceof EntityLivingBase)))
/*  961:     */             {
/*  962:1327 */               var9.hitEntity((EntityLivingBase)var10, this);
/*  963:1329 */               if (var9.stackSize <= 0) {
/*  964:1331 */                 destroyCurrentEquippedItem();
/*  965:     */               }
/*  966:     */             }
/*  967:1335 */             if ((par1Entity instanceof EntityLivingBase))
/*  968:     */             {
/*  969:1337 */               addStat(StatList.damageDealtStat, Math.round(var2 * 10.0F));
/*  970:1339 */               if (var7 > 0) {
/*  971:1341 */                 par1Entity.setFire(var7 * 4);
/*  972:     */               }
/*  973:     */             }
/*  974:1345 */             addExhaustion(0.3F);
/*  975:     */           }
/*  976:1347 */           else if (var6)
/*  977:     */           {
/*  978:1349 */             par1Entity.extinguish();
/*  979:     */           }
/*  980:     */         }
/*  981:     */       }
/*  982:     */     }
/*  983:     */   }
/*  984:     */   
/*  985:     */   public void onCriticalHit(Entity par1Entity) {}
/*  986:     */   
/*  987:     */   public void onEnchantmentCritical(Entity par1Entity) {}
/*  988:     */   
/*  989:     */   public void respawnPlayer() {}
/*  990:     */   
/*  991:     */   public void setDead()
/*  992:     */   {
/*  993:1370 */     super.setDead();
/*  994:1371 */     this.inventoryContainer.onContainerClosed(this);
/*  995:1373 */     if (this.openContainer != null) {
/*  996:1375 */       this.openContainer.onContainerClosed(this);
/*  997:     */     }
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public boolean isEntityInsideOpaqueBlock()
/* 1001:     */   {
/* 1002:1384 */     return (!this.sleeping) && (super.isEntityInsideOpaqueBlock());
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public GameProfile getGameProfile()
/* 1006:     */   {
/* 1007:1392 */     return this.field_146106_i;
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public EnumStatus sleepInBedAt(int par1, int par2, int par3)
/* 1011:     */   {
/* 1012:1400 */     if (!this.worldObj.isClient)
/* 1013:     */     {
/* 1014:1402 */       if ((isPlayerSleeping()) || (!isEntityAlive())) {
/* 1015:1404 */         return EnumStatus.OTHER_PROBLEM;
/* 1016:     */       }
/* 1017:1407 */       if (!this.worldObj.provider.isSurfaceWorld()) {
/* 1018:1409 */         return EnumStatus.NOT_POSSIBLE_HERE;
/* 1019:     */       }
/* 1020:1412 */       if (this.worldObj.isDaytime()) {
/* 1021:1414 */         return EnumStatus.NOT_POSSIBLE_NOW;
/* 1022:     */       }
/* 1023:1417 */       if ((Math.abs(this.posX - par1) > 3.0D) || (Math.abs(this.posY - par2) > 2.0D) || (Math.abs(this.posZ - par3) > 3.0D)) {
/* 1024:1419 */         return EnumStatus.TOO_FAR_AWAY;
/* 1025:     */       }
/* 1026:1422 */       double var4 = 8.0D;
/* 1027:1423 */       double var6 = 5.0D;
/* 1028:1424 */       List var8 = this.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getAABBPool().getAABB(par1 - var4, par2 - var6, par3 - var4, par1 + var4, par2 + var6, par3 + var4));
/* 1029:1426 */       if (!var8.isEmpty()) {
/* 1030:1428 */         return EnumStatus.NOT_SAFE;
/* 1031:     */       }
/* 1032:     */     }
/* 1033:1432 */     if (isRiding()) {
/* 1034:1434 */       mountEntity(null);
/* 1035:     */     }
/* 1036:1437 */     setSize(0.2F, 0.2F);
/* 1037:1438 */     this.yOffset = 0.2F;
/* 1038:1440 */     if (this.worldObj.blockExists(par1, par2, par3))
/* 1039:     */     {
/* 1040:1442 */       int var9 = this.worldObj.getBlockMetadata(par1, par2, par3);
/* 1041:1443 */       int var5 = BlockBed.func_149895_l(var9);
/* 1042:1444 */       float var10 = 0.5F;
/* 1043:1445 */       float var7 = 0.5F;
/* 1044:1447 */       switch (var5)
/* 1045:     */       {
/* 1046:     */       case 0: 
/* 1047:1450 */         var7 = 0.9F;
/* 1048:1451 */         break;
/* 1049:     */       case 1: 
/* 1050:1454 */         var10 = 0.1F;
/* 1051:1455 */         break;
/* 1052:     */       case 2: 
/* 1053:1458 */         var7 = 0.1F;
/* 1054:1459 */         break;
/* 1055:     */       case 3: 
/* 1056:1462 */         var10 = 0.9F;
/* 1057:     */       }
/* 1058:1465 */       func_71013_b(var5);
/* 1059:1466 */       setPosition(par1 + var10, par2 + 0.9375F, par3 + var7);
/* 1060:     */     }
/* 1061:     */     else
/* 1062:     */     {
/* 1063:1470 */       setPosition(par1 + 0.5F, par2 + 0.9375F, par3 + 0.5F);
/* 1064:     */     }
/* 1065:1473 */     this.sleeping = true;
/* 1066:1474 */     this.sleepTimer = 0;
/* 1067:1475 */     this.playerLocation = new ChunkCoordinates(par1, par2, par3);
/* 1068:1476 */     this.motionX = (this.motionZ = this.motionY = 0.0D);
/* 1069:1478 */     if (!this.worldObj.isClient) {
/* 1070:1480 */       this.worldObj.updateAllPlayersSleepingFlag();
/* 1071:     */     }
/* 1072:1483 */     return EnumStatus.OK;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   private void func_71013_b(int par1)
/* 1076:     */   {
/* 1077:1488 */     this.field_71079_bU = 0.0F;
/* 1078:1489 */     this.field_71089_bV = 0.0F;
/* 1079:1491 */     switch (par1)
/* 1080:     */     {
/* 1081:     */     case 0: 
/* 1082:1494 */       this.field_71089_bV = -1.8F;
/* 1083:1495 */       break;
/* 1084:     */     case 1: 
/* 1085:1498 */       this.field_71079_bU = 1.8F;
/* 1086:1499 */       break;
/* 1087:     */     case 2: 
/* 1088:1502 */       this.field_71089_bV = 1.8F;
/* 1089:1503 */       break;
/* 1090:     */     case 3: 
/* 1091:1506 */       this.field_71079_bU = -1.8F;
/* 1092:     */     }
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   public void wakeUpPlayer(boolean par1, boolean par2, boolean par3)
/* 1096:     */   {
/* 1097:1515 */     setSize(0.6F, 1.8F);
/* 1098:1516 */     resetHeight();
/* 1099:1517 */     ChunkCoordinates var4 = this.playerLocation;
/* 1100:1518 */     ChunkCoordinates var5 = this.playerLocation;
/* 1101:1520 */     if ((var4 != null) && (this.worldObj.getBlock(var4.posX, var4.posY, var4.posZ) == Blocks.bed))
/* 1102:     */     {
/* 1103:1522 */       BlockBed.func_149979_a(this.worldObj, var4.posX, var4.posY, var4.posZ, false);
/* 1104:1523 */       var5 = BlockBed.func_149977_a(this.worldObj, var4.posX, var4.posY, var4.posZ, 0);
/* 1105:1525 */       if (var5 == null) {
/* 1106:1527 */         var5 = new ChunkCoordinates(var4.posX, var4.posY + 1, var4.posZ);
/* 1107:     */       }
/* 1108:1530 */       setPosition(var5.posX + 0.5F, var5.posY + this.yOffset + 0.1F, var5.posZ + 0.5F);
/* 1109:     */     }
/* 1110:1533 */     this.sleeping = false;
/* 1111:1535 */     if ((!this.worldObj.isClient) && (par2)) {
/* 1112:1537 */       this.worldObj.updateAllPlayersSleepingFlag();
/* 1113:     */     }
/* 1114:1540 */     if (par1) {
/* 1115:1542 */       this.sleepTimer = 0;
/* 1116:     */     } else {
/* 1117:1546 */       this.sleepTimer = 100;
/* 1118:     */     }
/* 1119:1549 */     if (par3) {
/* 1120:1551 */       setSpawnChunk(this.playerLocation, false);
/* 1121:     */     }
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   private boolean isInBed()
/* 1125:     */   {
/* 1126:1560 */     return this.worldObj.getBlock(this.playerLocation.posX, this.playerLocation.posY, this.playerLocation.posZ) == Blocks.bed;
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   public static ChunkCoordinates verifyRespawnCoordinates(World par0World, ChunkCoordinates par1ChunkCoordinates, boolean par2)
/* 1130:     */   {
/* 1131:1569 */     IChunkProvider var3 = par0World.getChunkProvider();
/* 1132:1570 */     var3.loadChunk(par1ChunkCoordinates.posX - 3 >> 4, par1ChunkCoordinates.posZ - 3 >> 4);
/* 1133:1571 */     var3.loadChunk(par1ChunkCoordinates.posX + 3 >> 4, par1ChunkCoordinates.posZ - 3 >> 4);
/* 1134:1572 */     var3.loadChunk(par1ChunkCoordinates.posX - 3 >> 4, par1ChunkCoordinates.posZ + 3 >> 4);
/* 1135:1573 */     var3.loadChunk(par1ChunkCoordinates.posX + 3 >> 4, par1ChunkCoordinates.posZ + 3 >> 4);
/* 1136:1575 */     if (par0World.getBlock(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ) == Blocks.bed)
/* 1137:     */     {
/* 1138:1577 */       ChunkCoordinates var8 = BlockBed.func_149977_a(par0World, par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ, 0);
/* 1139:1578 */       return var8;
/* 1140:     */     }
/* 1141:1582 */     Material var4 = par0World.getBlock(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ).getMaterial();
/* 1142:1583 */     Material var5 = par0World.getBlock(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY + 1, par1ChunkCoordinates.posZ).getMaterial();
/* 1143:1584 */     boolean var6 = (!var4.isSolid()) && (!var4.isLiquid());
/* 1144:1585 */     boolean var7 = (!var5.isSolid()) && (!var5.isLiquid());
/* 1145:1586 */     return (par2) && (var6) && (var7) ? par1ChunkCoordinates : null;
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   public float getBedOrientationInDegrees()
/* 1149:     */   {
/* 1150:1595 */     if (this.playerLocation != null)
/* 1151:     */     {
/* 1152:1597 */       int var1 = this.worldObj.getBlockMetadata(this.playerLocation.posX, this.playerLocation.posY, this.playerLocation.posZ);
/* 1153:1598 */       int var2 = BlockBed.func_149895_l(var1);
/* 1154:1600 */       switch (var2)
/* 1155:     */       {
/* 1156:     */       case 0: 
/* 1157:1603 */         return 90.0F;
/* 1158:     */       case 1: 
/* 1159:1606 */         return 0.0F;
/* 1160:     */       case 2: 
/* 1161:1609 */         return 270.0F;
/* 1162:     */       case 3: 
/* 1163:1612 */         return 180.0F;
/* 1164:     */       }
/* 1165:     */     }
/* 1166:1616 */     return 0.0F;
/* 1167:     */   }
/* 1168:     */   
/* 1169:     */   public boolean isPlayerSleeping()
/* 1170:     */   {
/* 1171:1624 */     return this.sleeping;
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public boolean isPlayerFullyAsleep()
/* 1175:     */   {
/* 1176:1632 */     return (this.sleeping) && (this.sleepTimer >= 100);
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   public int getSleepTimer()
/* 1180:     */   {
/* 1181:1637 */     return this.sleepTimer;
/* 1182:     */   }
/* 1183:     */   
/* 1184:     */   protected boolean getHideCape(int par1)
/* 1185:     */   {
/* 1186:1642 */     return (this.dataWatcher.getWatchableObjectByte(16) & 1 << par1) != 0;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   protected void setHideCape(int par1, boolean par2)
/* 1190:     */   {
/* 1191:1647 */     byte var3 = this.dataWatcher.getWatchableObjectByte(16);
/* 1192:1649 */     if (par2) {
/* 1193:1651 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var3 | 1 << par1)));
/* 1194:     */     } else {
/* 1195:1655 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var3 & (1 << par1 ^ 0xFFFFFFFF))));
/* 1196:     */     }
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public void addChatComponentMessage(IChatComponent p_146105_1_) {}
/* 1200:     */   
/* 1201:     */   public ChunkCoordinates getBedLocation()
/* 1202:     */   {
/* 1203:1666 */     return this.spawnChunk;
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   public boolean isSpawnForced()
/* 1207:     */   {
/* 1208:1671 */     return this.spawnForced;
/* 1209:     */   }
/* 1210:     */   
/* 1211:     */   public void setSpawnChunk(ChunkCoordinates par1ChunkCoordinates, boolean par2)
/* 1212:     */   {
/* 1213:1679 */     if (par1ChunkCoordinates != null)
/* 1214:     */     {
/* 1215:1681 */       this.spawnChunk = new ChunkCoordinates(par1ChunkCoordinates);
/* 1216:1682 */       this.spawnForced = par2;
/* 1217:     */     }
/* 1218:     */     else
/* 1219:     */     {
/* 1220:1686 */       this.spawnChunk = null;
/* 1221:1687 */       this.spawnForced = false;
/* 1222:     */     }
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   public void triggerAchievement(StatBase par1StatBase)
/* 1226:     */   {
/* 1227:1696 */     addStat(par1StatBase, 1);
/* 1228:     */   }
/* 1229:     */   
/* 1230:     */   public void addStat(StatBase par1StatBase, int par2) {}
/* 1231:     */   
/* 1232:     */   public void jump()
/* 1233:     */   {
/* 1234:1709 */     super.jump();
/* 1235:1710 */     addStat(StatList.jumpStat, 1);
/* 1236:1712 */     if (isSprinting()) {
/* 1237:1714 */       addExhaustion(0.8F);
/* 1238:     */     } else {
/* 1239:1718 */       addExhaustion(0.2F);
/* 1240:     */     }
/* 1241:     */   }
/* 1242:     */   
/* 1243:     */   public void moveEntityWithHeading(float par1, float par2)
/* 1244:     */   {
/* 1245:1727 */     double var3 = this.posX;
/* 1246:1728 */     double var5 = this.posY;
/* 1247:1729 */     double var7 = this.posZ;
/* 1248:1731 */     if ((this.capabilities.isFlying) && (this.ridingEntity == null))
/* 1249:     */     {
/* 1250:1733 */       double var9 = this.motionY;
/* 1251:1734 */       float var11 = this.jumpMovementFactor;
/* 1252:1735 */       this.jumpMovementFactor = this.capabilities.getFlySpeed();
/* 1253:1736 */       super.moveEntityWithHeading(par1, par2);
/* 1254:1737 */       this.motionY = (var9 * 0.6D);
/* 1255:1738 */       this.jumpMovementFactor = var11;
/* 1256:     */     }
/* 1257:     */     else
/* 1258:     */     {
/* 1259:1742 */       super.moveEntityWithHeading(par1, par2);
/* 1260:     */     }
/* 1261:1745 */     addMovementStat(this.posX - var3, this.posY - var5, this.posZ - var7);
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   public float getAIMoveSpeed()
/* 1265:     */   {
/* 1266:1753 */     return (float)getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
/* 1267:     */   }
/* 1268:     */   
/* 1269:     */   public void addMovementStat(double par1, double par3, double par5)
/* 1270:     */   {
/* 1271:1761 */     if (this.ridingEntity == null) {
/* 1272:1765 */       if (isInsideOfMaterial(Material.water))
/* 1273:     */       {
/* 1274:1767 */         int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5) * 100.0F);
/* 1275:1769 */         if (var7 > 0)
/* 1276:     */         {
/* 1277:1771 */           addStat(StatList.distanceDoveStat, var7);
/* 1278:1772 */           addExhaustion(0.015F * var7 * 0.01F);
/* 1279:     */         }
/* 1280:     */       }
/* 1281:1775 */       else if (isInWater())
/* 1282:     */       {
/* 1283:1777 */         int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0F);
/* 1284:1779 */         if (var7 > 0)
/* 1285:     */         {
/* 1286:1781 */           addStat(StatList.distanceSwumStat, var7);
/* 1287:1782 */           addExhaustion(0.015F * var7 * 0.01F);
/* 1288:     */         }
/* 1289:     */       }
/* 1290:1785 */       else if (isOnLadder())
/* 1291:     */       {
/* 1292:1787 */         if (par3 > 0.0D) {
/* 1293:1789 */           addStat(StatList.distanceClimbedStat, (int)Math.round(par3 * 100.0D));
/* 1294:     */         }
/* 1295:     */       }
/* 1296:1792 */       else if (this.onGround)
/* 1297:     */       {
/* 1298:1794 */         int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0F);
/* 1299:1796 */         if (var7 > 0)
/* 1300:     */         {
/* 1301:1798 */           addStat(StatList.distanceWalkedStat, var7);
/* 1302:1800 */           if (isSprinting()) {
/* 1303:1802 */             addExhaustion(0.09999999F * var7 * 0.01F);
/* 1304:     */           } else {
/* 1305:1806 */             addExhaustion(0.01F * var7 * 0.01F);
/* 1306:     */           }
/* 1307:     */         }
/* 1308:     */       }
/* 1309:     */       else
/* 1310:     */       {
/* 1311:1812 */         int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0F);
/* 1312:1814 */         if (var7 > 25) {
/* 1313:1816 */           addStat(StatList.distanceFlownStat, var7);
/* 1314:     */         }
/* 1315:     */       }
/* 1316:     */     }
/* 1317:     */   }
/* 1318:     */   
/* 1319:     */   private void addMountedMovementStat(double par1, double par3, double par5)
/* 1320:     */   {
/* 1321:1827 */     if (this.ridingEntity != null)
/* 1322:     */     {
/* 1323:1829 */       int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5) * 100.0F);
/* 1324:1831 */       if (var7 > 0) {
/* 1325:1833 */         if ((this.ridingEntity instanceof EntityMinecart))
/* 1326:     */         {
/* 1327:1835 */           addStat(StatList.distanceByMinecartStat, var7);
/* 1328:1837 */           if (this.startMinecartRidingCoordinate == null) {
/* 1329:1839 */             this.startMinecartRidingCoordinate = new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
/* 1330:1841 */           } else if (this.startMinecartRidingCoordinate.getDistanceSquared(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) >= 1000000.0D) {
/* 1331:1843 */             addStat(AchievementList.onARail, 1);
/* 1332:     */           }
/* 1333:     */         }
/* 1334:1846 */         else if ((this.ridingEntity instanceof EntityBoat))
/* 1335:     */         {
/* 1336:1848 */           addStat(StatList.distanceByBoatStat, var7);
/* 1337:     */         }
/* 1338:1850 */         else if ((this.ridingEntity instanceof EntityPig))
/* 1339:     */         {
/* 1340:1852 */           addStat(StatList.distanceByPigStat, var7);
/* 1341:     */         }
/* 1342:1854 */         else if ((this.ridingEntity instanceof EntityHorse))
/* 1343:     */         {
/* 1344:1856 */           addStat(StatList.field_151185_q, var7);
/* 1345:     */         }
/* 1346:     */       }
/* 1347:     */     }
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   protected void fall(float par1)
/* 1351:     */   {
/* 1352:1867 */     if (!this.capabilities.allowFlying)
/* 1353:     */     {
/* 1354:1869 */       if (par1 >= 2.0F) {
/* 1355:1871 */         addStat(StatList.distanceFallenStat, (int)Math.round(par1 * 100.0D));
/* 1356:     */       }
/* 1357:1874 */       super.fall(par1);
/* 1358:     */     }
/* 1359:     */   }
/* 1360:     */   
/* 1361:     */   protected String func_146067_o(int p_146067_1_)
/* 1362:     */   {
/* 1363:1880 */     return p_146067_1_ > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   public void onKillEntity(EntityLivingBase par1EntityLivingBase)
/* 1367:     */   {
/* 1368:1888 */     if ((par1EntityLivingBase instanceof IMob)) {
/* 1369:1890 */       triggerAchievement(AchievementList.killEnemy);
/* 1370:     */     }
/* 1371:1893 */     int var2 = EntityList.getEntityID(par1EntityLivingBase);
/* 1372:1894 */     EntityList.EntityEggInfo var3 = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(var2));
/* 1373:1896 */     if (var3 != null) {
/* 1374:1898 */       addStat(var3.field_151512_d, 1);
/* 1375:     */     }
/* 1376:     */   }
/* 1377:     */   
/* 1378:     */   public void setInWeb()
/* 1379:     */   {
/* 1380:1907 */     if (!this.capabilities.isFlying) {
/* 1381:1909 */       super.setInWeb();
/* 1382:     */     }
/* 1383:     */   }
/* 1384:     */   
/* 1385:     */   public IIcon getItemIcon(ItemStack par1ItemStack, int par2)
/* 1386:     */   {
/* 1387:1918 */     IIcon var3 = super.getItemIcon(par1ItemStack, par2);
/* 1388:1920 */     if ((par1ItemStack.getItem() == Items.fishing_rod) && (this.fishEntity != null))
/* 1389:     */     {
/* 1390:1922 */       var3 = Items.fishing_rod.func_94597_g();
/* 1391:     */     }
/* 1392:     */     else
/* 1393:     */     {
/* 1394:1926 */       if (par1ItemStack.getItem().requiresMultipleRenderPasses()) {
/* 1395:1928 */         return par1ItemStack.getItem().getIconFromDamageForRenderPass(par1ItemStack.getItemDamage(), par2);
/* 1396:     */       }
/* 1397:1931 */       if ((this.itemInUse != null) && (par1ItemStack.getItem() == Items.bow))
/* 1398:     */       {
/* 1399:1933 */         int var4 = par1ItemStack.getMaxItemUseDuration() - this.itemInUseCount;
/* 1400:1935 */         if (var4 >= 18) {
/* 1401:1937 */           return Items.bow.getItemIconForUseDuration(2);
/* 1402:     */         }
/* 1403:1940 */         if (var4 > 13) {
/* 1404:1942 */           return Items.bow.getItemIconForUseDuration(1);
/* 1405:     */         }
/* 1406:1945 */         if (var4 > 0) {
/* 1407:1947 */           return Items.bow.getItemIconForUseDuration(0);
/* 1408:     */         }
/* 1409:     */       }
/* 1410:     */     }
/* 1411:1952 */     return var3;
/* 1412:     */   }
/* 1413:     */   
/* 1414:     */   public ItemStack getCurrentArmor(int par1)
/* 1415:     */   {
/* 1416:1957 */     return this.inventory.armorItemInSlot(par1);
/* 1417:     */   }
/* 1418:     */   
/* 1419:     */   public void addExperience(int par1)
/* 1420:     */   {
/* 1421:1965 */     addScore(par1);
/* 1422:1966 */     int var2 = 2147483647 - this.experienceTotal;
/* 1423:1968 */     if (par1 > var2) {
/* 1424:1970 */       par1 = var2;
/* 1425:     */     }
/* 1426:1973 */     this.experience += par1 / xpBarCap();
/* 1427:1975 */     for (this.experienceTotal += par1; this.experience >= 1.0F; this.experience /= xpBarCap())
/* 1428:     */     {
/* 1429:1977 */       this.experience = ((this.experience - 1.0F) * xpBarCap());
/* 1430:1978 */       addExperienceLevel(1);
/* 1431:     */     }
/* 1432:     */   }
/* 1433:     */   
/* 1434:     */   public void addExperienceLevel(int par1)
/* 1435:     */   {
/* 1436:1987 */     this.experienceLevel += par1;
/* 1437:1989 */     if (this.experienceLevel < 0)
/* 1438:     */     {
/* 1439:1991 */       this.experienceLevel = 0;
/* 1440:1992 */       this.experience = 0.0F;
/* 1441:1993 */       this.experienceTotal = 0;
/* 1442:     */     }
/* 1443:1996 */     if ((par1 > 0) && (this.experienceLevel % 5 == 0) && (this.field_82249_h < this.ticksExisted - 100.0F))
/* 1444:     */     {
/* 1445:1998 */       float var2 = this.experienceLevel > 30 ? 1.0F : this.experienceLevel / 30.0F;
/* 1446:1999 */       this.worldObj.playSoundAtEntity(this, "random.levelup", var2 * 0.75F, 1.0F);
/* 1447:2000 */       this.field_82249_h = this.ticksExisted;
/* 1448:     */     }
/* 1449:     */   }
/* 1450:     */   
/* 1451:     */   public int xpBarCap()
/* 1452:     */   {
/* 1453:2010 */     return this.experienceLevel >= 15 ? 17 + (this.experienceLevel - 15) * 3 : this.experienceLevel >= 30 ? 62 + (this.experienceLevel - 30) * 7 : 17;
/* 1454:     */   }
/* 1455:     */   
/* 1456:     */   public void addExhaustion(float par1)
/* 1457:     */   {
/* 1458:2018 */     if (!this.capabilities.disableDamage) {
/* 1459:2020 */       if (!this.worldObj.isClient) {
/* 1460:2022 */         this.foodStats.addExhaustion(par1);
/* 1461:     */       }
/* 1462:     */     }
/* 1463:     */   }
/* 1464:     */   
/* 1465:     */   public FoodStats getFoodStats()
/* 1466:     */   {
/* 1467:2032 */     return this.foodStats;
/* 1468:     */   }
/* 1469:     */   
/* 1470:     */   public boolean canEat(boolean par1)
/* 1471:     */   {
/* 1472:2037 */     return ((par1) || (this.foodStats.needFood())) && (!this.capabilities.disableDamage);
/* 1473:     */   }
/* 1474:     */   
/* 1475:     */   public boolean shouldHeal()
/* 1476:     */   {
/* 1477:2045 */     return (getHealth() > 0.0F) && (getHealth() < getMaxHealth());
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   public void setItemInUse(ItemStack par1ItemStack, int par2)
/* 1481:     */   {
/* 1482:2053 */     if (par1ItemStack != this.itemInUse)
/* 1483:     */     {
/* 1484:2055 */       this.itemInUse = par1ItemStack;
/* 1485:2056 */       this.itemInUseCount = par2;
/* 1486:2058 */       if (!this.worldObj.isClient) {
/* 1487:2060 */         setEating(true);
/* 1488:     */       }
/* 1489:     */     }
/* 1490:     */   }
/* 1491:     */   
/* 1492:     */   public boolean isCurrentToolAdventureModeExempt(int par1, int par2, int par3)
/* 1493:     */   {
/* 1494:2070 */     if (this.capabilities.allowEdit) {
/* 1495:2072 */       return true;
/* 1496:     */     }
/* 1497:2076 */     Block var4 = this.worldObj.getBlock(par1, par2, par3);
/* 1498:2078 */     if (var4.getMaterial() != Material.air)
/* 1499:     */     {
/* 1500:2080 */       if (var4.getMaterial().isAdventureModeExempt()) {
/* 1501:2082 */         return true;
/* 1502:     */       }
/* 1503:2085 */       if (getCurrentEquippedItem() != null)
/* 1504:     */       {
/* 1505:2087 */         ItemStack var5 = getCurrentEquippedItem();
/* 1506:2089 */         if ((var5.func_150998_b(var4)) || (var5.func_150997_a(var4) > 1.0F)) {
/* 1507:2091 */           return true;
/* 1508:     */         }
/* 1509:     */       }
/* 1510:     */     }
/* 1511:2096 */     return false;
/* 1512:     */   }
/* 1513:     */   
/* 1514:     */   public boolean canPlayerEdit(int par1, int par2, int par3, int par4, ItemStack par5ItemStack)
/* 1515:     */   {
/* 1516:2102 */     return this.capabilities.allowEdit;
/* 1517:     */   }
/* 1518:     */   
/* 1519:     */   protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
/* 1520:     */   {
/* 1521:2110 */     if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
/* 1522:2112 */       return 0;
/* 1523:     */     }
/* 1524:2116 */     int var2 = this.experienceLevel * 7;
/* 1525:2117 */     return var2 > 100 ? 100 : var2;
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   protected boolean isPlayer()
/* 1529:     */   {
/* 1530:2126 */     return true;
/* 1531:     */   }
/* 1532:     */   
/* 1533:     */   public boolean getAlwaysRenderNameTagForRender()
/* 1534:     */   {
/* 1535:2131 */     return true;
/* 1536:     */   }
/* 1537:     */   
/* 1538:     */   public void clonePlayer(EntityPlayer par1EntityPlayer, boolean par2)
/* 1539:     */   {
/* 1540:2140 */     if (par2)
/* 1541:     */     {
/* 1542:2142 */       this.inventory.copyInventory(par1EntityPlayer.inventory);
/* 1543:2143 */       setHealth(par1EntityPlayer.getHealth());
/* 1544:2144 */       this.foodStats = par1EntityPlayer.foodStats;
/* 1545:2145 */       this.experienceLevel = par1EntityPlayer.experienceLevel;
/* 1546:2146 */       this.experienceTotal = par1EntityPlayer.experienceTotal;
/* 1547:2147 */       this.experience = par1EntityPlayer.experience;
/* 1548:2148 */       setScore(par1EntityPlayer.getScore());
/* 1549:2149 */       this.teleportDirection = par1EntityPlayer.teleportDirection;
/* 1550:     */     }
/* 1551:2151 */     else if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
/* 1552:     */     {
/* 1553:2153 */       this.inventory.copyInventory(par1EntityPlayer.inventory);
/* 1554:2154 */       this.experienceLevel = par1EntityPlayer.experienceLevel;
/* 1555:2155 */       this.experienceTotal = par1EntityPlayer.experienceTotal;
/* 1556:2156 */       this.experience = par1EntityPlayer.experience;
/* 1557:2157 */       setScore(par1EntityPlayer.getScore());
/* 1558:     */     }
/* 1559:2160 */     this.theInventoryEnderChest = par1EntityPlayer.theInventoryEnderChest;
/* 1560:     */   }
/* 1561:     */   
/* 1562:     */   protected boolean canTriggerWalking()
/* 1563:     */   {
/* 1564:2169 */     return !this.capabilities.isFlying;
/* 1565:     */   }
/* 1566:     */   
/* 1567:     */   public void sendPlayerAbilities() {}
/* 1568:     */   
/* 1569:     */   public void setGameType(WorldSettings.GameType par1EnumGameType) {}
/* 1570:     */   
/* 1571:     */   public String getCommandSenderName()
/* 1572:     */   {
/* 1573:2187 */     return this.field_146106_i.getName();
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   public World getEntityWorld()
/* 1577:     */   {
/* 1578:2192 */     return this.worldObj;
/* 1579:     */   }
/* 1580:     */   
/* 1581:     */   public InventoryEnderChest getInventoryEnderChest()
/* 1582:     */   {
/* 1583:2200 */     return this.theInventoryEnderChest;
/* 1584:     */   }
/* 1585:     */   
/* 1586:     */   public ItemStack getEquipmentInSlot(int par1)
/* 1587:     */   {
/* 1588:2208 */     return par1 == 0 ? this.inventory.getCurrentItem() : this.inventory.armorInventory[(par1 - 1)];
/* 1589:     */   }
/* 1590:     */   
/* 1591:     */   public ItemStack getHeldItem()
/* 1592:     */   {
/* 1593:2216 */     return this.inventory.getCurrentItem();
/* 1594:     */   }
/* 1595:     */   
/* 1596:     */   public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
/* 1597:     */   {
/* 1598:2224 */     this.inventory.armorInventory[par1] = par2ItemStack;
/* 1599:     */   }
/* 1600:     */   
/* 1601:     */   public boolean isInvisibleToPlayer(EntityPlayer par1EntityPlayer)
/* 1602:     */   {
/* 1603:2234 */     if (!isInvisible()) {
/* 1604:2236 */       return false;
/* 1605:     */     }
/* 1606:2240 */     Team var2 = getTeam();
/* 1607:2241 */     return (var2 == null) || (par1EntityPlayer == null) || (par1EntityPlayer.getTeam() != var2) || (!var2.func_98297_h());
/* 1608:     */   }
/* 1609:     */   
/* 1610:     */   public ItemStack[] getLastActiveItems()
/* 1611:     */   {
/* 1612:2247 */     return this.inventory.armorInventory;
/* 1613:     */   }
/* 1614:     */   
/* 1615:     */   public boolean getHideCape()
/* 1616:     */   {
/* 1617:2252 */     return getHideCape(1);
/* 1618:     */   }
/* 1619:     */   
/* 1620:     */   public boolean isPushedByWater()
/* 1621:     */   {
/* 1622:2257 */     return !this.capabilities.isFlying;
/* 1623:     */   }
/* 1624:     */   
/* 1625:     */   public Scoreboard getWorldScoreboard()
/* 1626:     */   {
/* 1627:2262 */     return this.worldObj.getScoreboard();
/* 1628:     */   }
/* 1629:     */   
/* 1630:     */   public Team getTeam()
/* 1631:     */   {
/* 1632:2267 */     return getWorldScoreboard().getPlayersTeam(getCommandSenderName());
/* 1633:     */   }
/* 1634:     */   
/* 1635:     */   public IChatComponent func_145748_c_()
/* 1636:     */   {
/* 1637:2272 */     ChatComponentText var1 = new ChatComponentText(ScorePlayerTeam.formatPlayerName(getTeam(), getCommandSenderName()));
/* 1638:2273 */     var1.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + getCommandSenderName() + " "));
/* 1639:2274 */     return var1;
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   public void setAbsorptionAmount(float par1)
/* 1643:     */   {
/* 1644:2279 */     if (par1 < 0.0F) {
/* 1645:2281 */       par1 = 0.0F;
/* 1646:     */     }
/* 1647:2284 */     getDataWatcher().updateObject(17, Float.valueOf(par1));
/* 1648:     */   }
/* 1649:     */   
/* 1650:     */   public float getAbsorptionAmount()
/* 1651:     */   {
/* 1652:2289 */     return getDataWatcher().getWatchableObjectFloat(17);
/* 1653:     */   }
/* 1654:     */   
/* 1655:     */   public static UUID func_146094_a(GameProfile p_146094_0_)
/* 1656:     */   {
/* 1657:2294 */     UUID var1 = Util.tryGetUUIDFromString(p_146094_0_.getId());
/* 1658:2296 */     if (var1 == null) {
/* 1659:2298 */       var1 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_146094_0_.getName()).getBytes(Charsets.UTF_8));
/* 1660:     */     }
/* 1661:2301 */     return var1;
/* 1662:     */   }
/* 1663:     */   
/* 1664:     */   public static enum EnumStatus
/* 1665:     */   {
/* 1666:2306 */     OK("OK", 0),  NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1),  NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW", 2),  TOO_FAR_AWAY("TOO_FAR_AWAY", 3),  OTHER_PROBLEM("OTHER_PROBLEM", 4),  NOT_SAFE("NOT_SAFE", 5);
/* 1667:     */     
/* 1668:2313 */     private static final EnumStatus[] $VALUES = { OK, NOT_POSSIBLE_HERE, NOT_POSSIBLE_NOW, TOO_FAR_AWAY, OTHER_PROBLEM, NOT_SAFE };
/* 1669:     */     private static final String __OBFID = "CL_00001712";
/* 1670:     */     
/* 1671:     */     private EnumStatus(String par1Str, int par2) {}
/* 1672:     */   }
/* 1673:     */   
/* 1674:     */   public static enum EnumChatVisibility
/* 1675:     */   {
/* 1676:2321 */     FULL("FULL", 0, 0, "options.chat.visibility.full"),  SYSTEM("SYSTEM", 1, 1, "options.chat.visibility.system"),  HIDDEN("HIDDEN", 2, 2, "options.chat.visibility.hidden");
/* 1677:     */     
/* 1678:     */     private static final EnumChatVisibility[] field_151432_d;
/* 1679:     */     private final int chatVisibility;
/* 1680:     */     private final String resourceKey;
/* 1681:     */     private static final EnumChatVisibility[] $VALUES;
/* 1682:     */     private static final String __OBFID = "CL_00001714";
/* 1683:     */     
/* 1684:     */     private EnumChatVisibility(String p_i45323_1_, int p_i45323_2_, int p_i45323_3_, String p_i45323_4_)
/* 1685:     */     {
/* 1686:2333 */       this.chatVisibility = p_i45323_3_;
/* 1687:2334 */       this.resourceKey = p_i45323_4_;
/* 1688:     */     }
/* 1689:     */     
/* 1690:     */     public int getChatVisibility()
/* 1691:     */     {
/* 1692:2339 */       return this.chatVisibility;
/* 1693:     */     }
/* 1694:     */     
/* 1695:     */     public static EnumChatVisibility getEnumChatVisibility(int p_151426_0_)
/* 1696:     */     {
/* 1697:2344 */       return field_151432_d[(p_151426_0_ % field_151432_d.length)];
/* 1698:     */     }
/* 1699:     */     
/* 1700:     */     public String getResourceKey()
/* 1701:     */     {
/* 1702:2349 */       return this.resourceKey;
/* 1703:     */     }
/* 1704:     */     
/* 1705:     */     static
/* 1706:     */     {
/* 1707:2324 */       field_151432_d = new EnumChatVisibility[values().length];
/* 1708:     */       
/* 1709:     */ 
/* 1710:     */ 
/* 1711:2328 */       $VALUES = new EnumChatVisibility[] { FULL, SYSTEM, HIDDEN };
/* 1712:     */       
/* 1713:     */ 
/* 1714:     */ 
/* 1715:     */ 
/* 1716:     */ 
/* 1717:     */ 
/* 1718:     */ 
/* 1719:     */ 
/* 1720:     */ 
/* 1721:     */ 
/* 1722:     */ 
/* 1723:     */ 
/* 1724:     */ 
/* 1725:     */ 
/* 1726:     */ 
/* 1727:     */ 
/* 1728:     */ 
/* 1729:     */ 
/* 1730:     */ 
/* 1731:     */ 
/* 1732:     */ 
/* 1733:     */ 
/* 1734:     */ 
/* 1735:     */ 
/* 1736:2353 */       EnumChatVisibility[] var0 = values();
/* 1737:2354 */       int var1 = var0.length;
/* 1738:2356 */       for (int var2 = 0; var2 < var1; var2++)
/* 1739:     */       {
/* 1740:2358 */         EnumChatVisibility var3 = var0[var2];
/* 1741:2359 */         field_151432_d[var3.chatVisibility] = var3;
/* 1742:     */       }
/* 1743:     */     }
/* 1744:     */   }
/* 1745:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.player.EntityPlayer
 * JD-Core Version:    0.7.0.1
 */