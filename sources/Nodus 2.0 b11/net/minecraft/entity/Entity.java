/*    1:     */ package net.minecraft.entity;
/*    2:     */ 
/*    3:     */ import java.util.List;
/*    4:     */ import java.util.Random;
/*    5:     */ import java.util.UUID;
/*    6:     */ import java.util.concurrent.Callable;
/*    7:     */ import me.connorm.Nodus.Nodus;
/*    8:     */ import me.connorm.Nodus.module.NodusModuleManager;
/*    9:     */ import me.connorm.Nodus.module.modules.SafeWalk;
/*   10:     */ import net.minecraft.block.Block;
/*   11:     */ import net.minecraft.block.Block.SoundType;
/*   12:     */ import net.minecraft.block.BlockLiquid;
/*   13:     */ import net.minecraft.block.material.Material;
/*   14:     */ import net.minecraft.crash.CrashReport;
/*   15:     */ import net.minecraft.crash.CrashReportCategory;
/*   16:     */ import net.minecraft.enchantment.EnchantmentProtection;
/*   17:     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*   18:     */ import net.minecraft.entity.item.EntityItem;
/*   19:     */ import net.minecraft.entity.player.EntityPlayer;
/*   20:     */ import net.minecraft.init.Blocks;
/*   21:     */ import net.minecraft.item.Item;
/*   22:     */ import net.minecraft.item.ItemStack;
/*   23:     */ import net.minecraft.nbt.NBTTagCompound;
/*   24:     */ import net.minecraft.nbt.NBTTagDouble;
/*   25:     */ import net.minecraft.nbt.NBTTagFloat;
/*   26:     */ import net.minecraft.nbt.NBTTagList;
/*   27:     */ import net.minecraft.profiler.Profiler;
/*   28:     */ import net.minecraft.server.MinecraftServer;
/*   29:     */ import net.minecraft.server.management.ServerConfigurationManager;
/*   30:     */ import net.minecraft.util.AxisAlignedBB;
/*   31:     */ import net.minecraft.util.ChatComponentText;
/*   32:     */ import net.minecraft.util.ChunkCoordinates;
/*   33:     */ import net.minecraft.util.DamageSource;
/*   34:     */ import net.minecraft.util.Direction;
/*   35:     */ import net.minecraft.util.IChatComponent;
/*   36:     */ import net.minecraft.util.MathHelper;
/*   37:     */ import net.minecraft.util.ReportedException;
/*   38:     */ import net.minecraft.util.StatCollector;
/*   39:     */ import net.minecraft.util.Vec3;
/*   40:     */ import net.minecraft.world.Explosion;
/*   41:     */ import net.minecraft.world.World;
/*   42:     */ import net.minecraft.world.WorldProvider;
/*   43:     */ import net.minecraft.world.WorldServer;
/*   44:     */ import net.minecraft.world.storage.WorldInfo;
/*   45:     */ 
/*   46:     */ public abstract class Entity
/*   47:     */ {
/*   48:     */   private static int nextEntityID;
/*   49:     */   private int field_145783_c;
/*   50:     */   public double renderDistanceWeight;
/*   51:     */   public boolean preventEntitySpawning;
/*   52:     */   public Entity riddenByEntity;
/*   53:     */   public Entity ridingEntity;
/*   54:     */   public boolean forceSpawn;
/*   55:     */   public World worldObj;
/*   56:     */   public double prevPosX;
/*   57:     */   public double prevPosY;
/*   58:     */   public double prevPosZ;
/*   59:     */   public double posX;
/*   60:     */   public double posY;
/*   61:     */   public double posZ;
/*   62:     */   public double motionX;
/*   63:     */   public double motionY;
/*   64:     */   public double motionZ;
/*   65:     */   public float rotationYaw;
/*   66:     */   public float rotationPitch;
/*   67:     */   public float prevRotationYaw;
/*   68:     */   public float prevRotationPitch;
/*   69:     */   public final AxisAlignedBB boundingBox;
/*   70:     */   public boolean onGround;
/*   71:     */   public boolean isCollidedHorizontally;
/*   72:     */   public boolean isCollidedVertically;
/*   73:     */   public boolean isCollided;
/*   74:     */   public boolean velocityChanged;
/*   75:     */   protected boolean isInWeb;
/*   76:     */   public boolean field_70135_K;
/*   77:     */   public boolean isDead;
/*   78:     */   public float yOffset;
/*   79:     */   public float width;
/*   80:     */   public float height;
/*   81:     */   public float prevDistanceWalkedModified;
/*   82:     */   public float distanceWalkedModified;
/*   83:     */   public float distanceWalkedOnStepModified;
/*   84:     */   public float fallDistance;
/*   85:     */   private int nextStepDistance;
/*   86:     */   public double lastTickPosX;
/*   87:     */   public double lastTickPosY;
/*   88:     */   public double lastTickPosZ;
/*   89:     */   public float ySize;
/*   90:     */   public float stepHeight;
/*   91:     */   public boolean noClip;
/*   92:     */   public float entityCollisionReduction;
/*   93:     */   protected Random rand;
/*   94:     */   public int ticksExisted;
/*   95:     */   public int fireResistance;
/*   96:     */   private int fire;
/*   97:     */   protected boolean inWater;
/*   98:     */   public int hurtResistantTime;
/*   99:     */   private boolean firstUpdate;
/*  100:     */   protected boolean isImmuneToFire;
/*  101:     */   protected DataWatcher dataWatcher;
/*  102:     */   private double entityRiderPitchDelta;
/*  103:     */   private double entityRiderYawDelta;
/*  104:     */   public boolean addedToChunk;
/*  105:     */   public int chunkCoordX;
/*  106:     */   public int chunkCoordY;
/*  107:     */   public int chunkCoordZ;
/*  108:     */   public int serverPosX;
/*  109:     */   public int serverPosY;
/*  110:     */   public int serverPosZ;
/*  111:     */   public boolean ignoreFrustumCheck;
/*  112:     */   public boolean isAirBorne;
/*  113:     */   public int timeUntilPortal;
/*  114:     */   protected boolean inPortal;
/*  115:     */   protected int portalCounter;
/*  116:     */   public int dimension;
/*  117:     */   protected int teleportDirection;
/*  118:     */   private boolean invulnerable;
/*  119:     */   protected UUID entityUniqueID;
/*  120:     */   public EnumEntitySize myEntitySize;
/*  121:     */   private static final String __OBFID = "CL_00001533";
/*  122:     */   
/*  123:     */   public int getEntityId()
/*  124:     */   {
/*  125: 226 */     return this.field_145783_c;
/*  126:     */   }
/*  127:     */   
/*  128:     */   public void setEntityId(int p_145769_1_)
/*  129:     */   {
/*  130: 231 */     this.field_145783_c = p_145769_1_;
/*  131:     */   }
/*  132:     */   
/*  133:     */   public Entity(World par1World)
/*  134:     */   {
/*  135: 236 */     this.field_145783_c = (nextEntityID++);
/*  136: 237 */     this.renderDistanceWeight = 1.0D;
/*  137: 238 */     this.boundingBox = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*  138: 239 */     this.field_70135_K = true;
/*  139: 240 */     this.width = 0.6F;
/*  140: 241 */     this.height = 1.8F;
/*  141: 242 */     this.nextStepDistance = 1;
/*  142: 243 */     this.rand = new Random();
/*  143: 244 */     this.fireResistance = 1;
/*  144: 245 */     this.firstUpdate = true;
/*  145: 246 */     this.entityUniqueID = UUID.randomUUID();
/*  146: 247 */     this.myEntitySize = EnumEntitySize.SIZE_2;
/*  147: 248 */     this.worldObj = par1World;
/*  148: 249 */     setPosition(0.0D, 0.0D, 0.0D);
/*  149: 251 */     if (par1World != null) {
/*  150: 253 */       this.dimension = par1World.provider.dimensionId;
/*  151:     */     }
/*  152: 256 */     this.dataWatcher = new DataWatcher(this);
/*  153: 257 */     this.dataWatcher.addObject(0, Byte.valueOf((byte)0));
/*  154: 258 */     this.dataWatcher.addObject(1, Short.valueOf((short)300));
/*  155: 259 */     entityInit();
/*  156:     */   }
/*  157:     */   
/*  158:     */   protected abstract void entityInit();
/*  159:     */   
/*  160:     */   public DataWatcher getDataWatcher()
/*  161:     */   {
/*  162: 266 */     return this.dataWatcher;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public boolean equals(Object par1Obj)
/*  166:     */   {
/*  167: 271 */     return ((Entity)par1Obj).field_145783_c == this.field_145783_c;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public int hashCode()
/*  171:     */   {
/*  172: 276 */     return this.field_145783_c;
/*  173:     */   }
/*  174:     */   
/*  175:     */   protected void preparePlayerToSpawn()
/*  176:     */   {
/*  177: 285 */     if (this.worldObj != null)
/*  178:     */     {
/*  179: 287 */       while (this.posY > 0.0D)
/*  180:     */       {
/*  181: 289 */         setPosition(this.posX, this.posY, this.posZ);
/*  182: 291 */         if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
/*  183:     */           break;
/*  184:     */         }
/*  185: 296 */         this.posY += 1.0D;
/*  186:     */       }
/*  187: 299 */       this.motionX = (this.motionY = this.motionZ = 0.0D);
/*  188: 300 */       this.rotationPitch = 0.0F;
/*  189:     */     }
/*  190:     */   }
/*  191:     */   
/*  192:     */   public void setDead()
/*  193:     */   {
/*  194: 309 */     this.isDead = true;
/*  195:     */   }
/*  196:     */   
/*  197:     */   protected void setSize(float par1, float par2)
/*  198:     */   {
/*  199: 319 */     if ((par1 != this.width) || (par2 != this.height))
/*  200:     */     {
/*  201: 321 */       float var3 = this.width;
/*  202: 322 */       this.width = par1;
/*  203: 323 */       this.height = par2;
/*  204: 324 */       this.boundingBox.maxX = (this.boundingBox.minX + this.width);
/*  205: 325 */       this.boundingBox.maxZ = (this.boundingBox.minZ + this.width);
/*  206: 326 */       this.boundingBox.maxY = (this.boundingBox.minY + this.height);
/*  207: 328 */       if ((this.width > var3) && (!this.firstUpdate) && (!this.worldObj.isClient)) {
/*  208: 330 */         moveEntity(var3 - this.width, 0.0D, var3 - this.width);
/*  209:     */       }
/*  210:     */     }
/*  211: 334 */     float var3 = par1 % 2.0F;
/*  212: 336 */     if (var3 < 0.375D) {
/*  213: 338 */       this.myEntitySize = EnumEntitySize.SIZE_1;
/*  214: 340 */     } else if (var3 < 0.75D) {
/*  215: 342 */       this.myEntitySize = EnumEntitySize.SIZE_2;
/*  216: 344 */     } else if (var3 < 1.0D) {
/*  217: 346 */       this.myEntitySize = EnumEntitySize.SIZE_3;
/*  218: 348 */     } else if (var3 < 1.375D) {
/*  219: 350 */       this.myEntitySize = EnumEntitySize.SIZE_4;
/*  220: 352 */     } else if (var3 < 1.75D) {
/*  221: 354 */       this.myEntitySize = EnumEntitySize.SIZE_5;
/*  222:     */     } else {
/*  223: 358 */       this.myEntitySize = EnumEntitySize.SIZE_6;
/*  224:     */     }
/*  225:     */   }
/*  226:     */   
/*  227:     */   protected void setRotation(float par1, float par2)
/*  228:     */   {
/*  229: 367 */     this.rotationYaw = (par1 % 360.0F);
/*  230: 368 */     this.rotationPitch = (par2 % 360.0F);
/*  231:     */   }
/*  232:     */   
/*  233:     */   public void setPosition(double par1, double par3, double par5)
/*  234:     */   {
/*  235: 376 */     this.posX = par1;
/*  236: 377 */     this.posY = par3;
/*  237: 378 */     this.posZ = par5;
/*  238: 379 */     float var7 = this.width / 2.0F;
/*  239: 380 */     float var8 = this.height;
/*  240: 381 */     this.boundingBox.setBounds(par1 - var7, par3 - this.yOffset + this.ySize, par5 - var7, par1 + var7, par3 - this.yOffset + this.ySize + var8, par5 + var7);
/*  241:     */   }
/*  242:     */   
/*  243:     */   public void setAngles(float par1, float par2)
/*  244:     */   {
/*  245: 390 */     float var3 = this.rotationPitch;
/*  246: 391 */     float var4 = this.rotationYaw;
/*  247: 392 */     this.rotationYaw = ((float)(this.rotationYaw + par1 * 0.15D));
/*  248: 393 */     this.rotationPitch = ((float)(this.rotationPitch - par2 * 0.15D));
/*  249: 395 */     if (this.rotationPitch < -90.0F) {
/*  250: 397 */       this.rotationPitch = -90.0F;
/*  251:     */     }
/*  252: 400 */     if (this.rotationPitch > 90.0F) {
/*  253: 402 */       this.rotationPitch = 90.0F;
/*  254:     */     }
/*  255: 405 */     this.prevRotationPitch += this.rotationPitch - var3;
/*  256: 406 */     this.prevRotationYaw += this.rotationYaw - var4;
/*  257:     */   }
/*  258:     */   
/*  259:     */   public void onUpdate()
/*  260:     */   {
/*  261: 414 */     onEntityUpdate();
/*  262:     */   }
/*  263:     */   
/*  264:     */   public void onEntityUpdate()
/*  265:     */   {
/*  266: 422 */     this.worldObj.theProfiler.startSection("entityBaseTick");
/*  267: 424 */     if ((this.ridingEntity != null) && (this.ridingEntity.isDead)) {
/*  268: 426 */       this.ridingEntity = null;
/*  269:     */     }
/*  270: 429 */     this.prevDistanceWalkedModified = this.distanceWalkedModified;
/*  271: 430 */     this.prevPosX = this.posX;
/*  272: 431 */     this.prevPosY = this.posY;
/*  273: 432 */     this.prevPosZ = this.posZ;
/*  274: 433 */     this.prevRotationPitch = this.rotationPitch;
/*  275: 434 */     this.prevRotationYaw = this.rotationYaw;
/*  276: 437 */     if ((!this.worldObj.isClient) && ((this.worldObj instanceof WorldServer)))
/*  277:     */     {
/*  278: 439 */       this.worldObj.theProfiler.startSection("portal");
/*  279: 440 */       MinecraftServer var1 = ((WorldServer)this.worldObj).func_73046_m();
/*  280: 441 */       int var2 = getMaxInPortalTime();
/*  281: 443 */       if (this.inPortal)
/*  282:     */       {
/*  283: 445 */         if (var1.getAllowNether())
/*  284:     */         {
/*  285: 447 */           if ((this.ridingEntity == null) && (this.portalCounter++ >= var2))
/*  286:     */           {
/*  287: 449 */             this.portalCounter = var2;
/*  288: 450 */             this.timeUntilPortal = getPortalCooldown();
/*  289:     */             byte var3;
/*  290:     */             byte var3;
/*  291: 453 */             if (this.worldObj.provider.dimensionId == -1) {
/*  292: 455 */               var3 = 0;
/*  293:     */             } else {
/*  294: 459 */               var3 = -1;
/*  295:     */             }
/*  296: 462 */             travelToDimension(var3);
/*  297:     */           }
/*  298: 465 */           this.inPortal = false;
/*  299:     */         }
/*  300:     */       }
/*  301:     */       else
/*  302:     */       {
/*  303: 470 */         if (this.portalCounter > 0) {
/*  304: 472 */           this.portalCounter -= 4;
/*  305:     */         }
/*  306: 475 */         if (this.portalCounter < 0) {
/*  307: 477 */           this.portalCounter = 0;
/*  308:     */         }
/*  309:     */       }
/*  310: 481 */       if (this.timeUntilPortal > 0) {
/*  311: 483 */         this.timeUntilPortal -= 1;
/*  312:     */       }
/*  313: 486 */       this.worldObj.theProfiler.endSection();
/*  314:     */     }
/*  315: 489 */     if ((isSprinting()) && (!isInWater()))
/*  316:     */     {
/*  317: 491 */       int var5 = MathHelper.floor_double(this.posX);
/*  318: 492 */       int var2 = MathHelper.floor_double(this.posY - 0.2000000029802322D - this.yOffset);
/*  319: 493 */       int var6 = MathHelper.floor_double(this.posZ);
/*  320: 494 */       Block var4 = this.worldObj.getBlock(var5, var2, var6);
/*  321: 496 */       if (var4.getMaterial() != Material.air) {
/*  322: 498 */         this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(var4) + "_" + this.worldObj.getBlockMetadata(var5, var2, var6), this.posX + (this.rand.nextFloat() - 0.5D) * this.width, this.boundingBox.minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, -this.motionX * 4.0D, 1.5D, -this.motionZ * 4.0D);
/*  323:     */       }
/*  324:     */     }
/*  325: 502 */     handleWaterMovement();
/*  326: 504 */     if (this.worldObj.isClient) {
/*  327: 506 */       this.fire = 0;
/*  328: 508 */     } else if (this.fire > 0) {
/*  329: 510 */       if (this.isImmuneToFire)
/*  330:     */       {
/*  331: 512 */         this.fire -= 4;
/*  332: 514 */         if (this.fire < 0) {
/*  333: 516 */           this.fire = 0;
/*  334:     */         }
/*  335:     */       }
/*  336:     */       else
/*  337:     */       {
/*  338: 521 */         if (this.fire % 20 == 0) {
/*  339: 523 */           attackEntityFrom(DamageSource.onFire, 1.0F);
/*  340:     */         }
/*  341: 526 */         this.fire -= 1;
/*  342:     */       }
/*  343:     */     }
/*  344: 530 */     if (handleLavaMovement())
/*  345:     */     {
/*  346: 532 */       setOnFireFromLava();
/*  347: 533 */       this.fallDistance *= 0.5F;
/*  348:     */     }
/*  349: 536 */     if (this.posY < -64.0D) {
/*  350: 538 */       kill();
/*  351:     */     }
/*  352: 541 */     if (!this.worldObj.isClient) {
/*  353: 543 */       setFlag(0, this.fire > 0);
/*  354:     */     }
/*  355: 546 */     this.firstUpdate = false;
/*  356: 547 */     this.worldObj.theProfiler.endSection();
/*  357:     */   }
/*  358:     */   
/*  359:     */   public int getMaxInPortalTime()
/*  360:     */   {
/*  361: 555 */     return 0;
/*  362:     */   }
/*  363:     */   
/*  364:     */   protected void setOnFireFromLava()
/*  365:     */   {
/*  366: 563 */     if (!this.isImmuneToFire)
/*  367:     */     {
/*  368: 565 */       attackEntityFrom(DamageSource.lava, 4.0F);
/*  369: 566 */       setFire(15);
/*  370:     */     }
/*  371:     */   }
/*  372:     */   
/*  373:     */   public void setFire(int par1)
/*  374:     */   {
/*  375: 575 */     int var2 = par1 * 20;
/*  376: 576 */     var2 = EnchantmentProtection.getFireTimeForEntity(this, var2);
/*  377: 578 */     if (this.fire < var2) {
/*  378: 580 */       this.fire = var2;
/*  379:     */     }
/*  380:     */   }
/*  381:     */   
/*  382:     */   public void extinguish()
/*  383:     */   {
/*  384: 589 */     this.fire = 0;
/*  385:     */   }
/*  386:     */   
/*  387:     */   protected void kill()
/*  388:     */   {
/*  389: 597 */     setDead();
/*  390:     */   }
/*  391:     */   
/*  392:     */   public boolean isOffsetPositionInLiquid(double par1, double par3, double par5)
/*  393:     */   {
/*  394: 605 */     AxisAlignedBB var7 = this.boundingBox.getOffsetBoundingBox(par1, par3, par5);
/*  395: 606 */     List var8 = this.worldObj.getCollidingBoundingBoxes(this, var7);
/*  396: 607 */     return var8.isEmpty();
/*  397:     */   }
/*  398:     */   
/*  399:     */   public void moveEntity(double par1, double par3, double par5)
/*  400:     */   {
/*  401: 615 */     if (this.noClip)
/*  402:     */     {
/*  403: 617 */       this.boundingBox.offset(par1, par3, par5);
/*  404: 618 */       this.posX = ((this.boundingBox.minX + this.boundingBox.maxX) / 2.0D);
/*  405: 619 */       this.posY = (this.boundingBox.minY + this.yOffset - this.ySize);
/*  406: 620 */       this.posZ = ((this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D);
/*  407:     */     }
/*  408:     */     else
/*  409:     */     {
/*  410: 624 */       this.worldObj.theProfiler.startSection("move");
/*  411: 625 */       this.ySize *= 0.4F;
/*  412: 626 */       double var7 = this.posX;
/*  413: 627 */       double var9 = this.posY;
/*  414: 628 */       double var11 = this.posZ;
/*  415: 630 */       if (this.isInWeb)
/*  416:     */       {
/*  417: 632 */         this.isInWeb = false;
/*  418: 633 */         par1 *= 0.25D;
/*  419: 634 */         par3 *= 0.0500000007450581D;
/*  420: 635 */         par5 *= 0.25D;
/*  421: 636 */         this.motionX = 0.0D;
/*  422: 637 */         this.motionY = 0.0D;
/*  423: 638 */         this.motionZ = 0.0D;
/*  424:     */       }
/*  425: 641 */       double var13 = par1;
/*  426: 642 */       double var15 = par3;
/*  427: 643 */       double var17 = par5;
/*  428: 644 */       AxisAlignedBB var19 = this.boundingBox.copy();
/*  429:     */       
/*  430:     */ 
/*  431: 647 */       boolean var20 = ((this.onGround) && (isSneaking()) && ((this instanceof EntityPlayer))) || ((this.onGround) && (Nodus.theNodus.moduleManager.safeWalkModule.isToggled()));
/*  432: 649 */       if (var20)
/*  433:     */       {
/*  434: 653 */         double var21 = 0.05D;
/*  435:     */         for (;;)
/*  436:     */         {
/*  437: 655 */           if ((par1 < var21) && (par1 >= -var21)) {
/*  438: 657 */             par1 = 0.0D;
/*  439: 659 */           } else if (par1 > 0.0D) {
/*  440: 661 */             par1 -= var21;
/*  441:     */           } else {
/*  442: 665 */             par1 += var21;
/*  443:     */           }
/*  444: 653 */           var13 = par1;
/*  445: 653 */           if (par1 != 0.0D) {
/*  446: 653 */             if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(par1, -1.0D, 0.0D)).isEmpty()) {
/*  447:     */               break;
/*  448:     */             }
/*  449:     */           }
/*  450:     */         }
/*  451:     */         do
/*  452:     */         {
/*  453: 671 */           if ((par5 < var21) && (par5 >= -var21)) {
/*  454: 673 */             par5 = 0.0D;
/*  455: 675 */           } else if (par5 > 0.0D) {
/*  456: 677 */             par5 -= var21;
/*  457:     */           } else {
/*  458: 681 */             par5 += var21;
/*  459:     */           }
/*  460: 669 */           var17 = par5;
/*  461: 669 */           if (par5 == 0.0D) {
/*  462:     */             break;
/*  463:     */           }
/*  464: 669 */         } while (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(0.0D, -1.0D, par5)).isEmpty());
/*  465: 685 */         while ((par1 != 0.0D) && (par5 != 0.0D) && (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(par1, -1.0D, par5)).isEmpty()))
/*  466:     */         {
/*  467: 687 */           if ((par1 < var21) && (par1 >= -var21)) {
/*  468: 689 */             par1 = 0.0D;
/*  469: 691 */           } else if (par1 > 0.0D) {
/*  470: 693 */             par1 -= var21;
/*  471:     */           } else {
/*  472: 697 */             par1 += var21;
/*  473:     */           }
/*  474: 700 */           if ((par5 < var21) && (par5 >= -var21)) {
/*  475: 702 */             par5 = 0.0D;
/*  476: 704 */           } else if (par5 > 0.0D) {
/*  477: 706 */             par5 -= var21;
/*  478:     */           } else {
/*  479: 710 */             par5 += var21;
/*  480:     */           }
/*  481: 713 */           var13 = par1;
/*  482: 714 */           var17 = par5;
/*  483:     */         }
/*  484:     */       }
/*  485: 718 */       List var37 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(par1, par3, par5));
/*  486: 720 */       for (int var22 = 0; var22 < var37.size(); var22++) {
/*  487: 722 */         par3 = ((AxisAlignedBB)var37.get(var22)).calculateYOffset(this.boundingBox, par3);
/*  488:     */       }
/*  489: 725 */       this.boundingBox.offset(0.0D, par3, 0.0D);
/*  490: 727 */       if ((!this.field_70135_K) && (var15 != par3))
/*  491:     */       {
/*  492: 729 */         par5 = 0.0D;
/*  493: 730 */         par3 = 0.0D;
/*  494: 731 */         par1 = 0.0D;
/*  495:     */       }
/*  496: 734 */       boolean var36 = (this.onGround) || ((var15 != par3) && (var15 < 0.0D));
/*  497: 737 */       for (int var23 = 0; var23 < var37.size(); var23++) {
/*  498: 739 */         par1 = ((AxisAlignedBB)var37.get(var23)).calculateXOffset(this.boundingBox, par1);
/*  499:     */       }
/*  500: 742 */       this.boundingBox.offset(par1, 0.0D, 0.0D);
/*  501: 744 */       if ((!this.field_70135_K) && (var13 != par1))
/*  502:     */       {
/*  503: 746 */         par5 = 0.0D;
/*  504: 747 */         par3 = 0.0D;
/*  505: 748 */         par1 = 0.0D;
/*  506:     */       }
/*  507: 751 */       for (var23 = 0; var23 < var37.size(); var23++) {
/*  508: 753 */         par5 = ((AxisAlignedBB)var37.get(var23)).calculateZOffset(this.boundingBox, par5);
/*  509:     */       }
/*  510: 756 */       this.boundingBox.offset(0.0D, 0.0D, par5);
/*  511: 758 */       if ((!this.field_70135_K) && (var17 != par5))
/*  512:     */       {
/*  513: 760 */         par5 = 0.0D;
/*  514: 761 */         par3 = 0.0D;
/*  515: 762 */         par1 = 0.0D;
/*  516:     */       }
/*  517: 770 */       if ((this.stepHeight > 0.0F) && (var36) && ((var20) || (this.ySize < 0.05F)) && ((var13 != par1) || (var17 != par5)))
/*  518:     */       {
/*  519: 772 */         double var38 = par1;
/*  520: 773 */         double var25 = par3;
/*  521: 774 */         double var27 = par5;
/*  522: 775 */         par1 = var13;
/*  523: 776 */         par3 = this.stepHeight;
/*  524: 777 */         par5 = var17;
/*  525: 778 */         AxisAlignedBB var29 = this.boundingBox.copy();
/*  526: 779 */         this.boundingBox.setBB(var19);
/*  527: 780 */         var37 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(var13, par3, var17));
/*  528: 782 */         for (int var30 = 0; var30 < var37.size(); var30++) {
/*  529: 784 */           par3 = ((AxisAlignedBB)var37.get(var30)).calculateYOffset(this.boundingBox, par3);
/*  530:     */         }
/*  531: 787 */         this.boundingBox.offset(0.0D, par3, 0.0D);
/*  532: 789 */         if ((!this.field_70135_K) && (var15 != par3))
/*  533:     */         {
/*  534: 791 */           par5 = 0.0D;
/*  535: 792 */           par3 = 0.0D;
/*  536: 793 */           par1 = 0.0D;
/*  537:     */         }
/*  538: 796 */         for (var30 = 0; var30 < var37.size(); var30++) {
/*  539: 798 */           par1 = ((AxisAlignedBB)var37.get(var30)).calculateXOffset(this.boundingBox, par1);
/*  540:     */         }
/*  541: 801 */         this.boundingBox.offset(par1, 0.0D, 0.0D);
/*  542: 803 */         if ((!this.field_70135_K) && (var13 != par1))
/*  543:     */         {
/*  544: 805 */           par5 = 0.0D;
/*  545: 806 */           par3 = 0.0D;
/*  546: 807 */           par1 = 0.0D;
/*  547:     */         }
/*  548: 810 */         for (var30 = 0; var30 < var37.size(); var30++) {
/*  549: 812 */           par5 = ((AxisAlignedBB)var37.get(var30)).calculateZOffset(this.boundingBox, par5);
/*  550:     */         }
/*  551: 815 */         this.boundingBox.offset(0.0D, 0.0D, par5);
/*  552: 817 */         if ((!this.field_70135_K) && (var17 != par5))
/*  553:     */         {
/*  554: 819 */           par5 = 0.0D;
/*  555: 820 */           par3 = 0.0D;
/*  556: 821 */           par1 = 0.0D;
/*  557:     */         }
/*  558: 824 */         if ((!this.field_70135_K) && (var15 != par3))
/*  559:     */         {
/*  560: 826 */           par5 = 0.0D;
/*  561: 827 */           par3 = 0.0D;
/*  562: 828 */           par1 = 0.0D;
/*  563:     */         }
/*  564:     */         else
/*  565:     */         {
/*  566: 832 */           par3 = -this.stepHeight;
/*  567: 834 */           for (var30 = 0; var30 < var37.size(); var30++) {
/*  568: 836 */             par3 = ((AxisAlignedBB)var37.get(var30)).calculateYOffset(this.boundingBox, par3);
/*  569:     */           }
/*  570: 839 */           this.boundingBox.offset(0.0D, par3, 0.0D);
/*  571:     */         }
/*  572: 842 */         if (var38 * var38 + var27 * var27 >= par1 * par1 + par5 * par5)
/*  573:     */         {
/*  574: 844 */           par1 = var38;
/*  575: 845 */           par3 = var25;
/*  576: 846 */           par5 = var27;
/*  577: 847 */           this.boundingBox.setBB(var29);
/*  578:     */         }
/*  579:     */       }
/*  580: 851 */       this.worldObj.theProfiler.endSection();
/*  581: 852 */       this.worldObj.theProfiler.startSection("rest");
/*  582: 853 */       this.posX = ((this.boundingBox.minX + this.boundingBox.maxX) / 2.0D);
/*  583: 854 */       this.posY = (this.boundingBox.minY + this.yOffset - this.ySize);
/*  584: 855 */       this.posZ = ((this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D);
/*  585: 856 */       this.isCollidedHorizontally = ((var13 != par1) || (var17 != par5));
/*  586: 857 */       this.isCollidedVertically = (var15 != par3);
/*  587: 858 */       this.onGround = ((var15 != par3) && (var15 < 0.0D));
/*  588: 859 */       this.isCollided = ((this.isCollidedHorizontally) || (this.isCollidedVertically));
/*  589: 860 */       updateFallState(par3, this.onGround);
/*  590: 862 */       if (var13 != par1) {
/*  591: 864 */         this.motionX = 0.0D;
/*  592:     */       }
/*  593: 867 */       if (var15 != par3) {
/*  594: 869 */         this.motionY = 0.0D;
/*  595:     */       }
/*  596: 872 */       if (var17 != par5) {
/*  597: 874 */         this.motionZ = 0.0D;
/*  598:     */       }
/*  599: 877 */       double var38 = this.posX - var7;
/*  600: 878 */       double var25 = this.posY - var9;
/*  601: 879 */       double var27 = this.posZ - var11;
/*  602: 881 */       if ((canTriggerWalking()) && (!var20) && (this.ridingEntity == null))
/*  603:     */       {
/*  604: 883 */         int var40 = MathHelper.floor_double(this.posX);
/*  605: 884 */         int var30 = MathHelper.floor_double(this.posY - 0.2000000029802322D - this.yOffset);
/*  606: 885 */         int var31 = MathHelper.floor_double(this.posZ);
/*  607: 886 */         Block var32 = this.worldObj.getBlock(var40, var30, var31);
/*  608: 887 */         int var33 = this.worldObj.getBlock(var40, var30 - 1, var31).getRenderType();
/*  609: 889 */         if ((var33 == 11) || (var33 == 32) || (var33 == 21)) {
/*  610: 891 */           var32 = this.worldObj.getBlock(var40, var30 - 1, var31);
/*  611:     */         }
/*  612: 894 */         if (var32 != Blocks.ladder) {
/*  613: 896 */           var25 = 0.0D;
/*  614:     */         }
/*  615: 899 */         this.distanceWalkedModified = ((float)(this.distanceWalkedModified + MathHelper.sqrt_double(var38 * var38 + var27 * var27) * 0.6D));
/*  616: 900 */         this.distanceWalkedOnStepModified = ((float)(this.distanceWalkedOnStepModified + MathHelper.sqrt_double(var38 * var38 + var25 * var25 + var27 * var27) * 0.6D));
/*  617: 902 */         if ((this.distanceWalkedOnStepModified > this.nextStepDistance) && (var32.getMaterial() != Material.air))
/*  618:     */         {
/*  619: 904 */           this.nextStepDistance = ((int)this.distanceWalkedOnStepModified + 1);
/*  620: 906 */           if (isInWater())
/*  621:     */           {
/*  622: 908 */             float var34 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.2000000029802322D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.2000000029802322D) * 0.35F;
/*  623: 910 */             if (var34 > 1.0F) {
/*  624: 912 */               var34 = 1.0F;
/*  625:     */             }
/*  626: 915 */             playSound(getSwimSound(), var34, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*  627:     */           }
/*  628: 918 */           func_145780_a(var40, var30, var31, var32);
/*  629: 919 */           var32.onEntityWalking(this.worldObj, var40, var30, var31, this);
/*  630:     */         }
/*  631:     */       }
/*  632:     */       try
/*  633:     */       {
/*  634: 925 */         func_145775_I();
/*  635:     */       }
/*  636:     */       catch (Throwable var35)
/*  637:     */       {
/*  638: 929 */         CrashReport var42 = CrashReport.makeCrashReport(var35, "Checking entity block collision");
/*  639: 930 */         CrashReportCategory var39 = var42.makeCategory("Entity being checked for collision");
/*  640: 931 */         addEntityCrashInfo(var39);
/*  641: 932 */         throw new ReportedException(var42);
/*  642:     */       }
/*  643: 935 */       boolean var41 = isWet();
/*  644: 937 */       if (this.worldObj.func_147470_e(this.boundingBox.contract(0.001D, 0.001D, 0.001D)))
/*  645:     */       {
/*  646: 939 */         dealFireDamage(1);
/*  647: 941 */         if (!var41)
/*  648:     */         {
/*  649: 943 */           this.fire += 1;
/*  650: 945 */           if (this.fire == 0) {
/*  651: 947 */             setFire(8);
/*  652:     */           }
/*  653:     */         }
/*  654:     */       }
/*  655: 951 */       else if (this.fire <= 0)
/*  656:     */       {
/*  657: 953 */         this.fire = (-this.fireResistance);
/*  658:     */       }
/*  659: 956 */       if ((var41) && (this.fire > 0))
/*  660:     */       {
/*  661: 958 */         playSound("random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*  662: 959 */         this.fire = (-this.fireResistance);
/*  663:     */       }
/*  664: 962 */       this.worldObj.theProfiler.endSection();
/*  665:     */     }
/*  666:     */   }
/*  667:     */   
/*  668:     */   protected String getSwimSound()
/*  669:     */   {
/*  670: 968 */     return "game.neutral.swim";
/*  671:     */   }
/*  672:     */   
/*  673:     */   protected void func_145775_I()
/*  674:     */   {
/*  675: 973 */     int var1 = MathHelper.floor_double(this.boundingBox.minX + 0.001D);
/*  676: 974 */     int var2 = MathHelper.floor_double(this.boundingBox.minY + 0.001D);
/*  677: 975 */     int var3 = MathHelper.floor_double(this.boundingBox.minZ + 0.001D);
/*  678: 976 */     int var4 = MathHelper.floor_double(this.boundingBox.maxX - 0.001D);
/*  679: 977 */     int var5 = MathHelper.floor_double(this.boundingBox.maxY - 0.001D);
/*  680: 978 */     int var6 = MathHelper.floor_double(this.boundingBox.maxZ - 0.001D);
/*  681: 980 */     if (this.worldObj.checkChunksExist(var1, var2, var3, var4, var5, var6)) {
/*  682: 982 */       for (int var7 = var1; var7 <= var4; var7++) {
/*  683: 984 */         for (int var8 = var2; var8 <= var5; var8++) {
/*  684: 986 */           for (int var9 = var3; var9 <= var6; var9++)
/*  685:     */           {
/*  686: 988 */             Block var10 = this.worldObj.getBlock(var7, var8, var9);
/*  687:     */             try
/*  688:     */             {
/*  689: 992 */               var10.onEntityCollidedWithBlock(this.worldObj, var7, var8, var9, this);
/*  690:     */             }
/*  691:     */             catch (Throwable var14)
/*  692:     */             {
/*  693: 996 */               CrashReport var12 = CrashReport.makeCrashReport(var14, "Colliding entity with block");
/*  694: 997 */               CrashReportCategory var13 = var12.makeCategory("Block being collided with");
/*  695: 998 */               CrashReportCategory.func_147153_a(var13, var7, var8, var9, var10, this.worldObj.getBlockMetadata(var7, var8, var9));
/*  696: 999 */               throw new ReportedException(var12);
/*  697:     */             }
/*  698:     */           }
/*  699:     */         }
/*  700:     */       }
/*  701:     */     }
/*  702:     */   }
/*  703:     */   
/*  704:     */   protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
/*  705:     */   {
/*  706:1009 */     Block.SoundType var5 = p_145780_4_.stepSound;
/*  707:1011 */     if (this.worldObj.getBlock(p_145780_1_, p_145780_2_ + 1, p_145780_3_) == Blocks.snow_layer)
/*  708:     */     {
/*  709:1013 */       var5 = Blocks.snow_layer.stepSound;
/*  710:1014 */       playSound(var5.func_150498_e(), var5.func_150497_c() * 0.15F, var5.func_150494_d());
/*  711:     */     }
/*  712:1016 */     else if (!p_145780_4_.getMaterial().isLiquid())
/*  713:     */     {
/*  714:1018 */       playSound(var5.func_150498_e(), var5.func_150497_c() * 0.15F, var5.func_150494_d());
/*  715:     */     }
/*  716:     */   }
/*  717:     */   
/*  718:     */   public void playSound(String par1Str, float par2, float par3)
/*  719:     */   {
/*  720:1024 */     this.worldObj.playSoundAtEntity(this, par1Str, par2, par3);
/*  721:     */   }
/*  722:     */   
/*  723:     */   protected boolean canTriggerWalking()
/*  724:     */   {
/*  725:1033 */     return true;
/*  726:     */   }
/*  727:     */   
/*  728:     */   protected void updateFallState(double par1, boolean par3)
/*  729:     */   {
/*  730:1042 */     if (par3)
/*  731:     */     {
/*  732:1044 */       if (this.fallDistance > 0.0F)
/*  733:     */       {
/*  734:1046 */         fall(this.fallDistance);
/*  735:1047 */         this.fallDistance = 0.0F;
/*  736:     */       }
/*  737:     */     }
/*  738:1050 */     else if (par1 < 0.0D) {
/*  739:1052 */       this.fallDistance = ((float)(this.fallDistance - par1));
/*  740:     */     }
/*  741:     */   }
/*  742:     */   
/*  743:     */   public AxisAlignedBB getBoundingBox()
/*  744:     */   {
/*  745:1061 */     return null;
/*  746:     */   }
/*  747:     */   
/*  748:     */   protected void dealFireDamage(int par1)
/*  749:     */   {
/*  750:1070 */     if (!this.isImmuneToFire) {
/*  751:1072 */       attackEntityFrom(DamageSource.inFire, par1);
/*  752:     */     }
/*  753:     */   }
/*  754:     */   
/*  755:     */   public final boolean isImmuneToFire()
/*  756:     */   {
/*  757:1078 */     return this.isImmuneToFire;
/*  758:     */   }
/*  759:     */   
/*  760:     */   protected void fall(float par1)
/*  761:     */   {
/*  762:1086 */     if (this.riddenByEntity != null) {
/*  763:1088 */       this.riddenByEntity.fall(par1);
/*  764:     */     }
/*  765:     */   }
/*  766:     */   
/*  767:     */   public boolean isWet()
/*  768:     */   {
/*  769:1097 */     return (this.inWater) || (this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) || (this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + this.height), MathHelper.floor_double(this.posZ)));
/*  770:     */   }
/*  771:     */   
/*  772:     */   public boolean isInWater()
/*  773:     */   {
/*  774:1106 */     return this.inWater;
/*  775:     */   }
/*  776:     */   
/*  777:     */   public boolean handleWaterMovement()
/*  778:     */   {
/*  779:1114 */     if (this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, this))
/*  780:     */     {
/*  781:1116 */       if ((!this.inWater) && (!this.firstUpdate))
/*  782:     */       {
/*  783:1118 */         float var1 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.2000000029802322D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.2000000029802322D) * 0.2F;
/*  784:1120 */         if (var1 > 1.0F) {
/*  785:1122 */           var1 = 1.0F;
/*  786:     */         }
/*  787:1125 */         playSound(getSplashSound(), var1, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*  788:1126 */         float var2 = MathHelper.floor_double(this.boundingBox.minY);
/*  789:1131 */         for (int var3 = 0; var3 < 1.0F + this.width * 20.0F; var3++)
/*  790:     */         {
/*  791:1133 */           float var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/*  792:1134 */           float var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/*  793:1135 */           this.worldObj.spawnParticle("bubble", this.posX + var4, var2 + 1.0F, this.posZ + var5, this.motionX, this.motionY - this.rand.nextFloat() * 0.2F, this.motionZ);
/*  794:     */         }
/*  795:1138 */         for (var3 = 0; var3 < 1.0F + this.width * 20.0F; var3++)
/*  796:     */         {
/*  797:1140 */           float var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/*  798:1141 */           float var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/*  799:1142 */           this.worldObj.spawnParticle("splash", this.posX + var4, var2 + 1.0F, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
/*  800:     */         }
/*  801:     */       }
/*  802:1146 */       this.fallDistance = 0.0F;
/*  803:1147 */       this.inWater = true;
/*  804:1148 */       this.fire = 0;
/*  805:     */     }
/*  806:     */     else
/*  807:     */     {
/*  808:1152 */       this.inWater = false;
/*  809:     */     }
/*  810:1155 */     return this.inWater;
/*  811:     */   }
/*  812:     */   
/*  813:     */   protected String getSplashSound()
/*  814:     */   {
/*  815:1160 */     return "game.neutral.swim.splash";
/*  816:     */   }
/*  817:     */   
/*  818:     */   public boolean isInsideOfMaterial(Material par1Material)
/*  819:     */   {
/*  820:1168 */     double var2 = this.posY + getEyeHeight();
/*  821:1169 */     int var4 = MathHelper.floor_double(this.posX);
/*  822:1170 */     int var5 = MathHelper.floor_float(MathHelper.floor_double(var2));
/*  823:1171 */     int var6 = MathHelper.floor_double(this.posZ);
/*  824:1172 */     Block var7 = this.worldObj.getBlock(var4, var5, var6);
/*  825:1174 */     if (var7.getMaterial() == par1Material)
/*  826:     */     {
/*  827:1176 */       float var8 = BlockLiquid.func_149801_b(this.worldObj.getBlockMetadata(var4, var5, var6)) - 0.1111111F;
/*  828:1177 */       float var9 = var5 + 1 - var8;
/*  829:1178 */       return var2 < var9;
/*  830:     */     }
/*  831:1182 */     return false;
/*  832:     */   }
/*  833:     */   
/*  834:     */   public float getEyeHeight()
/*  835:     */   {
/*  836:1188 */     return 0.0F;
/*  837:     */   }
/*  838:     */   
/*  839:     */   public boolean handleLavaMovement()
/*  840:     */   {
/*  841:1196 */     return this.worldObj.isMaterialInBB(this.boundingBox.expand(-0.1000000014901161D, -0.4000000059604645D, -0.1000000014901161D), Material.lava);
/*  842:     */   }
/*  843:     */   
/*  844:     */   public void moveFlying(float par1, float par2, float par3)
/*  845:     */   {
/*  846:1204 */     float var4 = par1 * par1 + par2 * par2;
/*  847:1206 */     if (var4 >= 1.0E-004F)
/*  848:     */     {
/*  849:1208 */       var4 = MathHelper.sqrt_float(var4);
/*  850:1210 */       if (var4 < 1.0F) {
/*  851:1212 */         var4 = 1.0F;
/*  852:     */       }
/*  853:1215 */       var4 = par3 / var4;
/*  854:1216 */       par1 *= var4;
/*  855:1217 */       par2 *= var4;
/*  856:1218 */       float var5 = MathHelper.sin(this.rotationYaw * 3.141593F / 180.0F);
/*  857:1219 */       float var6 = MathHelper.cos(this.rotationYaw * 3.141593F / 180.0F);
/*  858:1220 */       this.motionX += par1 * var6 - par2 * var5;
/*  859:1221 */       this.motionZ += par2 * var6 + par1 * var5;
/*  860:     */     }
/*  861:     */   }
/*  862:     */   
/*  863:     */   public int getBrightnessForRender(float par1)
/*  864:     */   {
/*  865:1227 */     int var2 = MathHelper.floor_double(this.posX);
/*  866:1228 */     int var3 = MathHelper.floor_double(this.posZ);
/*  867:1230 */     if (this.worldObj.blockExists(var2, 0, var3))
/*  868:     */     {
/*  869:1232 */       double var4 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66D;
/*  870:1233 */       int var6 = MathHelper.floor_double(this.posY - this.yOffset + var4);
/*  871:1234 */       return this.worldObj.getLightBrightnessForSkyBlocks(var2, var6, var3, 0);
/*  872:     */     }
/*  873:1238 */     return 0;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public float getBrightness(float par1)
/*  877:     */   {
/*  878:1247 */     int var2 = MathHelper.floor_double(this.posX);
/*  879:1248 */     int var3 = MathHelper.floor_double(this.posZ);
/*  880:1250 */     if (this.worldObj.blockExists(var2, 0, var3))
/*  881:     */     {
/*  882:1252 */       double var4 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66D;
/*  883:1253 */       int var6 = MathHelper.floor_double(this.posY - this.yOffset + var4);
/*  884:1254 */       return this.worldObj.getLightBrightness(var2, var6, var3);
/*  885:     */     }
/*  886:1258 */     return 0.0F;
/*  887:     */   }
/*  888:     */   
/*  889:     */   public void setWorld(World par1World)
/*  890:     */   {
/*  891:1267 */     this.worldObj = par1World;
/*  892:     */   }
/*  893:     */   
/*  894:     */   public void setPositionAndRotation(double par1, double par3, double par5, float par7, float par8)
/*  895:     */   {
/*  896:1275 */     this.prevPosX = (this.posX = par1);
/*  897:1276 */     this.prevPosY = (this.posY = par3);
/*  898:1277 */     this.prevPosZ = (this.posZ = par5);
/*  899:1278 */     this.prevRotationYaw = (this.rotationYaw = par7);
/*  900:1279 */     this.prevRotationPitch = (this.rotationPitch = par8);
/*  901:1280 */     this.ySize = 0.0F;
/*  902:1281 */     double var9 = this.prevRotationYaw - par7;
/*  903:1283 */     if (var9 < -180.0D) {
/*  904:1285 */       this.prevRotationYaw += 360.0F;
/*  905:     */     }
/*  906:1288 */     if (var9 >= 180.0D) {
/*  907:1290 */       this.prevRotationYaw -= 360.0F;
/*  908:     */     }
/*  909:1293 */     setPosition(this.posX, this.posY, this.posZ);
/*  910:1294 */     setRotation(par7, par8);
/*  911:     */   }
/*  912:     */   
/*  913:     */   public void setLocationAndAngles(double par1, double par3, double par5, float par7, float par8)
/*  914:     */   {
/*  915:1302 */     this.lastTickPosX = (this.prevPosX = this.posX = par1);
/*  916:1303 */     this.lastTickPosY = (this.prevPosY = this.posY = par3 + this.yOffset);
/*  917:1304 */     this.lastTickPosZ = (this.prevPosZ = this.posZ = par5);
/*  918:1305 */     this.rotationYaw = par7;
/*  919:1306 */     this.rotationPitch = par8;
/*  920:1307 */     setPosition(this.posX, this.posY, this.posZ);
/*  921:     */   }
/*  922:     */   
/*  923:     */   public float getDistanceToEntity(Entity par1Entity)
/*  924:     */   {
/*  925:1315 */     float var2 = (float)(this.posX - par1Entity.posX);
/*  926:1316 */     float var3 = (float)(this.posY - par1Entity.posY);
/*  927:1317 */     float var4 = (float)(this.posZ - par1Entity.posZ);
/*  928:1318 */     return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
/*  929:     */   }
/*  930:     */   
/*  931:     */   public double getDistanceSq(double par1, double par3, double par5)
/*  932:     */   {
/*  933:1326 */     double var7 = this.posX - par1;
/*  934:1327 */     double var9 = this.posY - par3;
/*  935:1328 */     double var11 = this.posZ - par5;
/*  936:1329 */     return var7 * var7 + var9 * var9 + var11 * var11;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public double getDistance(double par1, double par3, double par5)
/*  940:     */   {
/*  941:1337 */     double var7 = this.posX - par1;
/*  942:1338 */     double var9 = this.posY - par3;
/*  943:1339 */     double var11 = this.posZ - par5;
/*  944:1340 */     return MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
/*  945:     */   }
/*  946:     */   
/*  947:     */   public double getDistanceSqToEntity(Entity par1Entity)
/*  948:     */   {
/*  949:1348 */     double var2 = this.posX - par1Entity.posX;
/*  950:1349 */     double var4 = this.posY - par1Entity.posY;
/*  951:1350 */     double var6 = this.posZ - par1Entity.posZ;
/*  952:1351 */     return var2 * var2 + var4 * var4 + var6 * var6;
/*  953:     */   }
/*  954:     */   
/*  955:     */   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {}
/*  956:     */   
/*  957:     */   public void applyEntityCollision(Entity par1Entity)
/*  958:     */   {
/*  959:1364 */     if ((par1Entity.riddenByEntity != this) && (par1Entity.ridingEntity != this))
/*  960:     */     {
/*  961:1366 */       double var2 = par1Entity.posX - this.posX;
/*  962:1367 */       double var4 = par1Entity.posZ - this.posZ;
/*  963:1368 */       double var6 = MathHelper.abs_max(var2, var4);
/*  964:1370 */       if (var6 >= 0.009999999776482582D)
/*  965:     */       {
/*  966:1372 */         var6 = MathHelper.sqrt_double(var6);
/*  967:1373 */         var2 /= var6;
/*  968:1374 */         var4 /= var6;
/*  969:1375 */         double var8 = 1.0D / var6;
/*  970:1377 */         if (var8 > 1.0D) {
/*  971:1379 */           var8 = 1.0D;
/*  972:     */         }
/*  973:1382 */         var2 *= var8;
/*  974:1383 */         var4 *= var8;
/*  975:1384 */         var2 *= 0.0500000007450581D;
/*  976:1385 */         var4 *= 0.0500000007450581D;
/*  977:1386 */         var2 *= (1.0F - this.entityCollisionReduction);
/*  978:1387 */         var4 *= (1.0F - this.entityCollisionReduction);
/*  979:1388 */         addVelocity(-var2, 0.0D, -var4);
/*  980:1389 */         par1Entity.addVelocity(var2, 0.0D, var4);
/*  981:     */       }
/*  982:     */     }
/*  983:     */   }
/*  984:     */   
/*  985:     */   public void addVelocity(double par1, double par3, double par5)
/*  986:     */   {
/*  987:1399 */     this.motionX += par1;
/*  988:1400 */     this.motionY += par3;
/*  989:1401 */     this.motionZ += par5;
/*  990:1402 */     this.isAirBorne = true;
/*  991:     */   }
/*  992:     */   
/*  993:     */   protected void setBeenAttacked()
/*  994:     */   {
/*  995:1410 */     this.velocityChanged = true;
/*  996:     */   }
/*  997:     */   
/*  998:     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  999:     */   {
/* 1000:1418 */     if (isEntityInvulnerable()) {
/* 1001:1420 */       return false;
/* 1002:     */     }
/* 1003:1424 */     setBeenAttacked();
/* 1004:1425 */     return false;
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public boolean canBeCollidedWith()
/* 1008:     */   {
/* 1009:1434 */     return false;
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   public boolean canBePushed()
/* 1013:     */   {
/* 1014:1442 */     return false;
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public void addToPlayerScore(Entity par1Entity, int par2) {}
/* 1018:     */   
/* 1019:     */   public boolean isInRangeToRender3d(double p_145770_1_, double p_145770_3_, double p_145770_5_)
/* 1020:     */   {
/* 1021:1453 */     double var7 = this.posX - p_145770_1_;
/* 1022:1454 */     double var9 = this.posY - p_145770_3_;
/* 1023:1455 */     double var11 = this.posZ - p_145770_5_;
/* 1024:1456 */     double var13 = var7 * var7 + var9 * var9 + var11 * var11;
/* 1025:1457 */     return isInRangeToRenderDist(var13);
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public boolean isInRangeToRenderDist(double par1)
/* 1029:     */   {
/* 1030:1466 */     double var3 = this.boundingBox.getAverageEdgeLength();
/* 1031:1467 */     var3 *= 64.0D * this.renderDistanceWeight;
/* 1032:1468 */     return par1 < var3 * var3;
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public boolean writeMountToNBT(NBTTagCompound par1NBTTagCompound)
/* 1036:     */   {
/* 1037:1477 */     String var2 = getEntityString();
/* 1038:1479 */     if ((!this.isDead) && (var2 != null))
/* 1039:     */     {
/* 1040:1481 */       par1NBTTagCompound.setString("id", var2);
/* 1041:1482 */       writeToNBT(par1NBTTagCompound);
/* 1042:1483 */       return true;
/* 1043:     */     }
/* 1044:1487 */     return false;
/* 1045:     */   }
/* 1046:     */   
/* 1047:     */   public boolean writeToNBTOptional(NBTTagCompound par1NBTTagCompound)
/* 1048:     */   {
/* 1049:1498 */     String var2 = getEntityString();
/* 1050:1500 */     if ((!this.isDead) && (var2 != null) && (this.riddenByEntity == null))
/* 1051:     */     {
/* 1052:1502 */       par1NBTTagCompound.setString("id", var2);
/* 1053:1503 */       writeToNBT(par1NBTTagCompound);
/* 1054:1504 */       return true;
/* 1055:     */     }
/* 1056:1508 */     return false;
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/* 1060:     */   {
/* 1061:     */     try
/* 1062:     */     {
/* 1063:1519 */       par1NBTTagCompound.setTag("Pos", newDoubleNBTList(new double[] { this.posX, this.posY + this.ySize, this.posZ }));
/* 1064:1520 */       par1NBTTagCompound.setTag("Motion", newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/* 1065:1521 */       par1NBTTagCompound.setTag("Rotation", newFloatNBTList(new float[] { this.rotationYaw, this.rotationPitch }));
/* 1066:1522 */       par1NBTTagCompound.setFloat("FallDistance", this.fallDistance);
/* 1067:1523 */       par1NBTTagCompound.setShort("Fire", (short)this.fire);
/* 1068:1524 */       par1NBTTagCompound.setShort("Air", (short)getAir());
/* 1069:1525 */       par1NBTTagCompound.setBoolean("OnGround", this.onGround);
/* 1070:1526 */       par1NBTTagCompound.setInteger("Dimension", this.dimension);
/* 1071:1527 */       par1NBTTagCompound.setBoolean("Invulnerable", this.invulnerable);
/* 1072:1528 */       par1NBTTagCompound.setInteger("PortalCooldown", this.timeUntilPortal);
/* 1073:1529 */       par1NBTTagCompound.setLong("UUIDMost", getUniqueID().getMostSignificantBits());
/* 1074:1530 */       par1NBTTagCompound.setLong("UUIDLeast", getUniqueID().getLeastSignificantBits());
/* 1075:1531 */       writeEntityToNBT(par1NBTTagCompound);
/* 1076:1533 */       if (this.ridingEntity != null)
/* 1077:     */       {
/* 1078:1535 */         NBTTagCompound var2 = new NBTTagCompound();
/* 1079:1537 */         if (this.ridingEntity.writeMountToNBT(var2)) {
/* 1080:1539 */           par1NBTTagCompound.setTag("Riding", var2);
/* 1081:     */         }
/* 1082:     */       }
/* 1083:     */     }
/* 1084:     */     catch (Throwable var5)
/* 1085:     */     {
/* 1086:1545 */       CrashReport var3 = CrashReport.makeCrashReport(var5, "Saving entity NBT");
/* 1087:1546 */       CrashReportCategory var4 = var3.makeCategory("Entity being saved");
/* 1088:1547 */       addEntityCrashInfo(var4);
/* 1089:1548 */       throw new ReportedException(var3);
/* 1090:     */     }
/* 1091:     */   }
/* 1092:     */   
/* 1093:     */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/* 1094:     */   {
/* 1095:     */     try
/* 1096:     */     {
/* 1097:1559 */       NBTTagList var2 = par1NBTTagCompound.getTagList("Pos", 6);
/* 1098:1560 */       NBTTagList var6 = par1NBTTagCompound.getTagList("Motion", 6);
/* 1099:1561 */       NBTTagList var7 = par1NBTTagCompound.getTagList("Rotation", 5);
/* 1100:1562 */       this.motionX = var6.func_150309_d(0);
/* 1101:1563 */       this.motionY = var6.func_150309_d(1);
/* 1102:1564 */       this.motionZ = var6.func_150309_d(2);
/* 1103:1566 */       if (Math.abs(this.motionX) > 10.0D) {
/* 1104:1568 */         this.motionX = 0.0D;
/* 1105:     */       }
/* 1106:1571 */       if (Math.abs(this.motionY) > 10.0D) {
/* 1107:1573 */         this.motionY = 0.0D;
/* 1108:     */       }
/* 1109:1576 */       if (Math.abs(this.motionZ) > 10.0D) {
/* 1110:1578 */         this.motionZ = 0.0D;
/* 1111:     */       }
/* 1112:1581 */       this.prevPosX = (this.lastTickPosX = this.posX = var2.func_150309_d(0));
/* 1113:1582 */       this.prevPosY = (this.lastTickPosY = this.posY = var2.func_150309_d(1));
/* 1114:1583 */       this.prevPosZ = (this.lastTickPosZ = this.posZ = var2.func_150309_d(2));
/* 1115:1584 */       this.prevRotationYaw = (this.rotationYaw = var7.func_150308_e(0));
/* 1116:1585 */       this.prevRotationPitch = (this.rotationPitch = var7.func_150308_e(1));
/* 1117:1586 */       this.fallDistance = par1NBTTagCompound.getFloat("FallDistance");
/* 1118:1587 */       this.fire = par1NBTTagCompound.getShort("Fire");
/* 1119:1588 */       setAir(par1NBTTagCompound.getShort("Air"));
/* 1120:1589 */       this.onGround = par1NBTTagCompound.getBoolean("OnGround");
/* 1121:1590 */       this.dimension = par1NBTTagCompound.getInteger("Dimension");
/* 1122:1591 */       this.invulnerable = par1NBTTagCompound.getBoolean("Invulnerable");
/* 1123:1592 */       this.timeUntilPortal = par1NBTTagCompound.getInteger("PortalCooldown");
/* 1124:1594 */       if ((par1NBTTagCompound.func_150297_b("UUIDMost", 4)) && (par1NBTTagCompound.func_150297_b("UUIDLeast", 4))) {
/* 1125:1596 */         this.entityUniqueID = new UUID(par1NBTTagCompound.getLong("UUIDMost"), par1NBTTagCompound.getLong("UUIDLeast"));
/* 1126:     */       }
/* 1127:1599 */       setPosition(this.posX, this.posY, this.posZ);
/* 1128:1600 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 1129:1601 */       readEntityFromNBT(par1NBTTagCompound);
/* 1130:1603 */       if (shouldSetPosAfterLoading()) {
/* 1131:1605 */         setPosition(this.posX, this.posY, this.posZ);
/* 1132:     */       }
/* 1133:     */     }
/* 1134:     */     catch (Throwable var5)
/* 1135:     */     {
/* 1136:1610 */       CrashReport var3 = CrashReport.makeCrashReport(var5, "Loading entity NBT");
/* 1137:1611 */       CrashReportCategory var4 = var3.makeCategory("Entity being loaded");
/* 1138:1612 */       addEntityCrashInfo(var4);
/* 1139:1613 */       throw new ReportedException(var3);
/* 1140:     */     }
/* 1141:     */   }
/* 1142:     */   
/* 1143:     */   protected boolean shouldSetPosAfterLoading()
/* 1144:     */   {
/* 1145:1619 */     return true;
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   protected final String getEntityString()
/* 1149:     */   {
/* 1150:1627 */     return EntityList.getEntityString(this);
/* 1151:     */   }
/* 1152:     */   
/* 1153:     */   protected abstract void readEntityFromNBT(NBTTagCompound paramNBTTagCompound);
/* 1154:     */   
/* 1155:     */   protected abstract void writeEntityToNBT(NBTTagCompound paramNBTTagCompound);
/* 1156:     */   
/* 1157:     */   public void onChunkLoad() {}
/* 1158:     */   
/* 1159:     */   protected NBTTagList newDoubleNBTList(double... par1ArrayOfDouble)
/* 1160:     */   {
/* 1161:1647 */     NBTTagList var2 = new NBTTagList();
/* 1162:1648 */     double[] var3 = par1ArrayOfDouble;
/* 1163:1649 */     int var4 = par1ArrayOfDouble.length;
/* 1164:1651 */     for (int var5 = 0; var5 < var4; var5++)
/* 1165:     */     {
/* 1166:1653 */       double var6 = var3[var5];
/* 1167:1654 */       var2.appendTag(new NBTTagDouble(var6));
/* 1168:     */     }
/* 1169:1657 */     return var2;
/* 1170:     */   }
/* 1171:     */   
/* 1172:     */   protected NBTTagList newFloatNBTList(float... par1ArrayOfFloat)
/* 1173:     */   {
/* 1174:1665 */     NBTTagList var2 = new NBTTagList();
/* 1175:1666 */     float[] var3 = par1ArrayOfFloat;
/* 1176:1667 */     int var4 = par1ArrayOfFloat.length;
/* 1177:1669 */     for (int var5 = 0; var5 < var4; var5++)
/* 1178:     */     {
/* 1179:1671 */       float var6 = var3[var5];
/* 1180:1672 */       var2.appendTag(new NBTTagFloat(var6));
/* 1181:     */     }
/* 1182:1675 */     return var2;
/* 1183:     */   }
/* 1184:     */   
/* 1185:     */   public float getShadowSize()
/* 1186:     */   {
/* 1187:1680 */     return this.height / 2.0F;
/* 1188:     */   }
/* 1189:     */   
/* 1190:     */   public EntityItem func_145779_a(Item p_145779_1_, int p_145779_2_)
/* 1191:     */   {
/* 1192:1685 */     return func_145778_a(p_145779_1_, p_145779_2_, 0.0F);
/* 1193:     */   }
/* 1194:     */   
/* 1195:     */   public EntityItem func_145778_a(Item p_145778_1_, int p_145778_2_, float p_145778_3_)
/* 1196:     */   {
/* 1197:1690 */     return entityDropItem(new ItemStack(p_145778_1_, p_145778_2_, 0), p_145778_3_);
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   public EntityItem entityDropItem(ItemStack par1ItemStack, float par2)
/* 1201:     */   {
/* 1202:1698 */     if ((par1ItemStack.stackSize != 0) && (par1ItemStack.getItem() != null))
/* 1203:     */     {
/* 1204:1700 */       EntityItem var3 = new EntityItem(this.worldObj, this.posX, this.posY + par2, this.posZ, par1ItemStack);
/* 1205:1701 */       var3.delayBeforeCanPickup = 10;
/* 1206:1702 */       this.worldObj.spawnEntityInWorld(var3);
/* 1207:1703 */       return var3;
/* 1208:     */     }
/* 1209:1707 */     return null;
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   public boolean isEntityAlive()
/* 1213:     */   {
/* 1214:1716 */     return !this.isDead;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public boolean isEntityInsideOpaqueBlock()
/* 1218:     */   {
/* 1219:1724 */     for (int var1 = 0; var1 < 8; var1++)
/* 1220:     */     {
/* 1221:1726 */       float var2 = ((var1 >> 0) % 2 - 0.5F) * this.width * 0.8F;
/* 1222:1727 */       float var3 = ((var1 >> 1) % 2 - 0.5F) * 0.1F;
/* 1223:1728 */       float var4 = ((var1 >> 2) % 2 - 0.5F) * this.width * 0.8F;
/* 1224:1729 */       int var5 = MathHelper.floor_double(this.posX + var2);
/* 1225:1730 */       int var6 = MathHelper.floor_double(this.posY + getEyeHeight() + var3);
/* 1226:1731 */       int var7 = MathHelper.floor_double(this.posZ + var4);
/* 1227:1733 */       if (this.worldObj.getBlock(var5, var6, var7).isNormalCube()) {
/* 1228:1735 */         return true;
/* 1229:     */       }
/* 1230:     */     }
/* 1231:1739 */     return false;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public boolean interactFirst(EntityPlayer par1EntityPlayer)
/* 1235:     */   {
/* 1236:1747 */     return false;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public AxisAlignedBB getCollisionBox(Entity par1Entity)
/* 1240:     */   {
/* 1241:1756 */     return null;
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public void updateRidden()
/* 1245:     */   {
/* 1246:1764 */     if (this.ridingEntity.isDead)
/* 1247:     */     {
/* 1248:1766 */       this.ridingEntity = null;
/* 1249:     */     }
/* 1250:     */     else
/* 1251:     */     {
/* 1252:1770 */       this.motionX = 0.0D;
/* 1253:1771 */       this.motionY = 0.0D;
/* 1254:1772 */       this.motionZ = 0.0D;
/* 1255:1773 */       onUpdate();
/* 1256:1775 */       if (this.ridingEntity != null)
/* 1257:     */       {
/* 1258:1777 */         this.ridingEntity.updateRiderPosition();
/* 1259:1778 */         this.entityRiderYawDelta += this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw;
/* 1260:1780 */         for (this.entityRiderPitchDelta += this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch; this.entityRiderYawDelta >= 180.0D; this.entityRiderYawDelta -= 360.0D) {}
/* 1261:1785 */         while (this.entityRiderYawDelta < -180.0D) {
/* 1262:1787 */           this.entityRiderYawDelta += 360.0D;
/* 1263:     */         }
/* 1264:1790 */         while (this.entityRiderPitchDelta >= 180.0D) {
/* 1265:1792 */           this.entityRiderPitchDelta -= 360.0D;
/* 1266:     */         }
/* 1267:1795 */         while (this.entityRiderPitchDelta < -180.0D) {
/* 1268:1797 */           this.entityRiderPitchDelta += 360.0D;
/* 1269:     */         }
/* 1270:1800 */         double var1 = this.entityRiderYawDelta * 0.5D;
/* 1271:1801 */         double var3 = this.entityRiderPitchDelta * 0.5D;
/* 1272:1802 */         float var5 = 10.0F;
/* 1273:1804 */         if (var1 > var5) {
/* 1274:1806 */           var1 = var5;
/* 1275:     */         }
/* 1276:1809 */         if (var1 < -var5) {
/* 1277:1811 */           var1 = -var5;
/* 1278:     */         }
/* 1279:1814 */         if (var3 > var5) {
/* 1280:1816 */           var3 = var5;
/* 1281:     */         }
/* 1282:1819 */         if (var3 < -var5) {
/* 1283:1821 */           var3 = -var5;
/* 1284:     */         }
/* 1285:1824 */         this.entityRiderYawDelta -= var1;
/* 1286:1825 */         this.entityRiderPitchDelta -= var3;
/* 1287:     */       }
/* 1288:     */     }
/* 1289:     */   }
/* 1290:     */   
/* 1291:     */   public void updateRiderPosition()
/* 1292:     */   {
/* 1293:1832 */     if (this.riddenByEntity != null) {
/* 1294:1834 */       this.riddenByEntity.setPosition(this.posX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
/* 1295:     */     }
/* 1296:     */   }
/* 1297:     */   
/* 1298:     */   public double getYOffset()
/* 1299:     */   {
/* 1300:1843 */     return this.yOffset;
/* 1301:     */   }
/* 1302:     */   
/* 1303:     */   public double getMountedYOffset()
/* 1304:     */   {
/* 1305:1851 */     return this.height * 0.75D;
/* 1306:     */   }
/* 1307:     */   
/* 1308:     */   public void mountEntity(Entity par1Entity)
/* 1309:     */   {
/* 1310:1859 */     this.entityRiderPitchDelta = 0.0D;
/* 1311:1860 */     this.entityRiderYawDelta = 0.0D;
/* 1312:1862 */     if (par1Entity == null)
/* 1313:     */     {
/* 1314:1864 */       if (this.ridingEntity != null)
/* 1315:     */       {
/* 1316:1866 */         setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.boundingBox.minY + this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
/* 1317:1867 */         this.ridingEntity.riddenByEntity = null;
/* 1318:     */       }
/* 1319:1870 */       this.ridingEntity = null;
/* 1320:     */     }
/* 1321:     */     else
/* 1322:     */     {
/* 1323:1874 */       if (this.ridingEntity != null) {
/* 1324:1876 */         this.ridingEntity.riddenByEntity = null;
/* 1325:     */       }
/* 1326:1879 */       this.ridingEntity = par1Entity;
/* 1327:1880 */       par1Entity.riddenByEntity = this;
/* 1328:     */     }
/* 1329:     */   }
/* 1330:     */   
/* 1331:     */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
/* 1332:     */   {
/* 1333:1890 */     setPosition(par1, par3, par5);
/* 1334:1891 */     setRotation(par7, par8);
/* 1335:1892 */     List var10 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.contract(0.03125D, 0.0D, 0.03125D));
/* 1336:1894 */     if (!var10.isEmpty())
/* 1337:     */     {
/* 1338:1896 */       double var11 = 0.0D;
/* 1339:1898 */       for (int var13 = 0; var13 < var10.size(); var13++)
/* 1340:     */       {
/* 1341:1900 */         AxisAlignedBB var14 = (AxisAlignedBB)var10.get(var13);
/* 1342:1902 */         if (var14.maxY > var11) {
/* 1343:1904 */           var11 = var14.maxY;
/* 1344:     */         }
/* 1345:     */       }
/* 1346:1908 */       par3 += var11 - this.boundingBox.minY;
/* 1347:1909 */       setPosition(par1, par3, par5);
/* 1348:     */     }
/* 1349:     */   }
/* 1350:     */   
/* 1351:     */   public float getCollisionBorderSize()
/* 1352:     */   {
/* 1353:1915 */     return 0.1F;
/* 1354:     */   }
/* 1355:     */   
/* 1356:     */   public Vec3 getLookVec()
/* 1357:     */   {
/* 1358:1923 */     return null;
/* 1359:     */   }
/* 1360:     */   
/* 1361:     */   public void setInPortal()
/* 1362:     */   {
/* 1363:1931 */     if (this.timeUntilPortal > 0)
/* 1364:     */     {
/* 1365:1933 */       this.timeUntilPortal = getPortalCooldown();
/* 1366:     */     }
/* 1367:     */     else
/* 1368:     */     {
/* 1369:1937 */       double var1 = this.prevPosX - this.posX;
/* 1370:1938 */       double var3 = this.prevPosZ - this.posZ;
/* 1371:1940 */       if ((!this.worldObj.isClient) && (!this.inPortal)) {
/* 1372:1942 */         this.teleportDirection = Direction.getMovementDirection(var1, var3);
/* 1373:     */       }
/* 1374:1945 */       this.inPortal = true;
/* 1375:     */     }
/* 1376:     */   }
/* 1377:     */   
/* 1378:     */   public int getPortalCooldown()
/* 1379:     */   {
/* 1380:1954 */     return 300;
/* 1381:     */   }
/* 1382:     */   
/* 1383:     */   public void setVelocity(double par1, double par3, double par5)
/* 1384:     */   {
/* 1385:1962 */     this.motionX = par1;
/* 1386:1963 */     this.motionY = par3;
/* 1387:1964 */     this.motionZ = par5;
/* 1388:     */   }
/* 1389:     */   
/* 1390:     */   public void handleHealthUpdate(byte par1) {}
/* 1391:     */   
/* 1392:     */   public void performHurtAnimation() {}
/* 1393:     */   
/* 1394:     */   public ItemStack[] getLastActiveItems()
/* 1395:     */   {
/* 1396:1976 */     return null;
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {}
/* 1400:     */   
/* 1401:     */   public boolean isBurning()
/* 1402:     */   {
/* 1403:1989 */     boolean var1 = (this.worldObj != null) && (this.worldObj.isClient);
/* 1404:1990 */     return (!this.isImmuneToFire) && ((this.fire > 0) || ((var1) && (getFlag(0))));
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   public boolean isRiding()
/* 1408:     */   {
/* 1409:1999 */     return this.ridingEntity != null;
/* 1410:     */   }
/* 1411:     */   
/* 1412:     */   public boolean isSneaking()
/* 1413:     */   {
/* 1414:2007 */     return getFlag(1);
/* 1415:     */   }
/* 1416:     */   
/* 1417:     */   public void setSneaking(boolean par1)
/* 1418:     */   {
/* 1419:2015 */     setFlag(1, par1);
/* 1420:     */   }
/* 1421:     */   
/* 1422:     */   public boolean isSprinting()
/* 1423:     */   {
/* 1424:2023 */     return getFlag(3);
/* 1425:     */   }
/* 1426:     */   
/* 1427:     */   public void setSprinting(boolean par1)
/* 1428:     */   {
/* 1429:2031 */     setFlag(3, par1);
/* 1430:     */   }
/* 1431:     */   
/* 1432:     */   public boolean isInvisible()
/* 1433:     */   {
/* 1434:2036 */     return getFlag(5);
/* 1435:     */   }
/* 1436:     */   
/* 1437:     */   public boolean isInvisibleToPlayer(EntityPlayer par1EntityPlayer)
/* 1438:     */   {
/* 1439:2046 */     return isInvisible();
/* 1440:     */   }
/* 1441:     */   
/* 1442:     */   public void setInvisible(boolean par1)
/* 1443:     */   {
/* 1444:2051 */     setFlag(5, par1);
/* 1445:     */   }
/* 1446:     */   
/* 1447:     */   public boolean isEating()
/* 1448:     */   {
/* 1449:2056 */     return getFlag(4);
/* 1450:     */   }
/* 1451:     */   
/* 1452:     */   public void setEating(boolean par1)
/* 1453:     */   {
/* 1454:2061 */     setFlag(4, par1);
/* 1455:     */   }
/* 1456:     */   
/* 1457:     */   protected boolean getFlag(int par1)
/* 1458:     */   {
/* 1459:2070 */     return (this.dataWatcher.getWatchableObjectByte(0) & 1 << par1) != 0;
/* 1460:     */   }
/* 1461:     */   
/* 1462:     */   protected void setFlag(int par1, boolean par2)
/* 1463:     */   {
/* 1464:2078 */     byte var3 = this.dataWatcher.getWatchableObjectByte(0);
/* 1465:2080 */     if (par2) {
/* 1466:2082 */       this.dataWatcher.updateObject(0, Byte.valueOf((byte)(var3 | 1 << par1)));
/* 1467:     */     } else {
/* 1468:2086 */       this.dataWatcher.updateObject(0, Byte.valueOf((byte)(var3 & (1 << par1 ^ 0xFFFFFFFF))));
/* 1469:     */     }
/* 1470:     */   }
/* 1471:     */   
/* 1472:     */   public int getAir()
/* 1473:     */   {
/* 1474:2092 */     return this.dataWatcher.getWatchableObjectShort(1);
/* 1475:     */   }
/* 1476:     */   
/* 1477:     */   public void setAir(int par1)
/* 1478:     */   {
/* 1479:2097 */     this.dataWatcher.updateObject(1, Short.valueOf((short)par1));
/* 1480:     */   }
/* 1481:     */   
/* 1482:     */   public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
/* 1483:     */   {
/* 1484:2105 */     dealFireDamage(5);
/* 1485:2106 */     this.fire += 1;
/* 1486:2108 */     if (this.fire == 0) {
/* 1487:2110 */       setFire(8);
/* 1488:     */     }
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   public void onKillEntity(EntityLivingBase par1EntityLivingBase) {}
/* 1492:     */   
/* 1493:     */   protected boolean func_145771_j(double p_145771_1_, double p_145771_3_, double p_145771_5_)
/* 1494:     */   {
/* 1495:2121 */     int var7 = MathHelper.floor_double(p_145771_1_);
/* 1496:2122 */     int var8 = MathHelper.floor_double(p_145771_3_);
/* 1497:2123 */     int var9 = MathHelper.floor_double(p_145771_5_);
/* 1498:2124 */     double var10 = p_145771_1_ - var7;
/* 1499:2125 */     double var12 = p_145771_3_ - var8;
/* 1500:2126 */     double var14 = p_145771_5_ - var9;
/* 1501:2127 */     List var16 = this.worldObj.func_147461_a(this.boundingBox);
/* 1502:2129 */     if ((var16.isEmpty()) && (!this.worldObj.func_147469_q(var7, var8, var9))) {
/* 1503:2131 */       return false;
/* 1504:     */     }
/* 1505:2135 */     boolean var17 = !this.worldObj.func_147469_q(var7 - 1, var8, var9);
/* 1506:2136 */     boolean var18 = !this.worldObj.func_147469_q(var7 + 1, var8, var9);
/* 1507:2137 */     boolean var19 = !this.worldObj.func_147469_q(var7, var8 - 1, var9);
/* 1508:2138 */     boolean var20 = !this.worldObj.func_147469_q(var7, var8 + 1, var9);
/* 1509:2139 */     boolean var21 = !this.worldObj.func_147469_q(var7, var8, var9 - 1);
/* 1510:2140 */     boolean var22 = !this.worldObj.func_147469_q(var7, var8, var9 + 1);
/* 1511:2141 */     byte var23 = 3;
/* 1512:2142 */     double var24 = 9999.0D;
/* 1513:2144 */     if ((var17) && (var10 < var24))
/* 1514:     */     {
/* 1515:2146 */       var24 = var10;
/* 1516:2147 */       var23 = 0;
/* 1517:     */     }
/* 1518:2150 */     if ((var18) && (1.0D - var10 < var24))
/* 1519:     */     {
/* 1520:2152 */       var24 = 1.0D - var10;
/* 1521:2153 */       var23 = 1;
/* 1522:     */     }
/* 1523:2156 */     if ((var20) && (1.0D - var12 < var24))
/* 1524:     */     {
/* 1525:2158 */       var24 = 1.0D - var12;
/* 1526:2159 */       var23 = 3;
/* 1527:     */     }
/* 1528:2162 */     if ((var21) && (var14 < var24))
/* 1529:     */     {
/* 1530:2164 */       var24 = var14;
/* 1531:2165 */       var23 = 4;
/* 1532:     */     }
/* 1533:2168 */     if ((var22) && (1.0D - var14 < var24))
/* 1534:     */     {
/* 1535:2170 */       var24 = 1.0D - var14;
/* 1536:2171 */       var23 = 5;
/* 1537:     */     }
/* 1538:2174 */     float var26 = this.rand.nextFloat() * 0.2F + 0.1F;
/* 1539:2176 */     if (var23 == 0) {
/* 1540:2178 */       this.motionX = (-var26);
/* 1541:     */     }
/* 1542:2181 */     if (var23 == 1) {
/* 1543:2183 */       this.motionX = var26;
/* 1544:     */     }
/* 1545:2186 */     if (var23 == 2) {
/* 1546:2188 */       this.motionY = (-var26);
/* 1547:     */     }
/* 1548:2191 */     if (var23 == 3) {
/* 1549:2193 */       this.motionY = var26;
/* 1550:     */     }
/* 1551:2196 */     if (var23 == 4) {
/* 1552:2198 */       this.motionZ = (-var26);
/* 1553:     */     }
/* 1554:2201 */     if (var23 == 5) {
/* 1555:2203 */       this.motionZ = var26;
/* 1556:     */     }
/* 1557:2206 */     return true;
/* 1558:     */   }
/* 1559:     */   
/* 1560:     */   public void setInWeb()
/* 1561:     */   {
/* 1562:2215 */     this.isInWeb = true;
/* 1563:2216 */     this.fallDistance = 0.0F;
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   public String getCommandSenderName()
/* 1567:     */   {
/* 1568:2224 */     String var1 = EntityList.getEntityString(this);
/* 1569:2226 */     if (var1 == null) {
/* 1570:2228 */       var1 = "generic";
/* 1571:     */     }
/* 1572:2231 */     return StatCollector.translateToLocal("entity." + var1 + ".name");
/* 1573:     */   }
/* 1574:     */   
/* 1575:     */   public Entity[] getParts()
/* 1576:     */   {
/* 1577:2239 */     return null;
/* 1578:     */   }
/* 1579:     */   
/* 1580:     */   public boolean isEntityEqual(Entity par1Entity)
/* 1581:     */   {
/* 1582:2247 */     return this == par1Entity;
/* 1583:     */   }
/* 1584:     */   
/* 1585:     */   public float getRotationYawHead()
/* 1586:     */   {
/* 1587:2252 */     return 0.0F;
/* 1588:     */   }
/* 1589:     */   
/* 1590:     */   public void setRotationYawHead(float par1) {}
/* 1591:     */   
/* 1592:     */   public boolean canAttackWithItem()
/* 1593:     */   {
/* 1594:2265 */     return true;
/* 1595:     */   }
/* 1596:     */   
/* 1597:     */   public boolean hitByEntity(Entity par1Entity)
/* 1598:     */   {
/* 1599:2273 */     return false;
/* 1600:     */   }
/* 1601:     */   
/* 1602:     */   public String toString()
/* 1603:     */   {
/* 1604:2278 */     return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getCommandSenderName(), Integer.valueOf(this.field_145783_c), this.worldObj == null ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName(), Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) });
/* 1605:     */   }
/* 1606:     */   
/* 1607:     */   public boolean isEntityInvulnerable()
/* 1608:     */   {
/* 1609:2286 */     return this.invulnerable;
/* 1610:     */   }
/* 1611:     */   
/* 1612:     */   public void copyLocationAndAnglesFrom(Entity par1Entity)
/* 1613:     */   {
/* 1614:2294 */     setLocationAndAngles(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par1Entity.rotationYaw, par1Entity.rotationPitch);
/* 1615:     */   }
/* 1616:     */   
/* 1617:     */   public void copyDataFrom(Entity par1Entity, boolean par2)
/* 1618:     */   {
/* 1619:2304 */     NBTTagCompound var3 = new NBTTagCompound();
/* 1620:2305 */     par1Entity.writeToNBT(var3);
/* 1621:2306 */     readFromNBT(var3);
/* 1622:2307 */     this.timeUntilPortal = par1Entity.timeUntilPortal;
/* 1623:2308 */     this.teleportDirection = par1Entity.teleportDirection;
/* 1624:     */   }
/* 1625:     */   
/* 1626:     */   public void travelToDimension(int par1)
/* 1627:     */   {
/* 1628:2316 */     if ((!this.worldObj.isClient) && (!this.isDead))
/* 1629:     */     {
/* 1630:2318 */       this.worldObj.theProfiler.startSection("changeDimension");
/* 1631:2319 */       MinecraftServer var2 = MinecraftServer.getServer();
/* 1632:2320 */       int var3 = this.dimension;
/* 1633:2321 */       WorldServer var4 = var2.worldServerForDimension(var3);
/* 1634:2322 */       WorldServer var5 = var2.worldServerForDimension(par1);
/* 1635:2323 */       this.dimension = par1;
/* 1636:2325 */       if ((var3 == 1) && (par1 == 1))
/* 1637:     */       {
/* 1638:2327 */         var5 = var2.worldServerForDimension(0);
/* 1639:2328 */         this.dimension = 0;
/* 1640:     */       }
/* 1641:2331 */       this.worldObj.removeEntity(this);
/* 1642:2332 */       this.isDead = false;
/* 1643:2333 */       this.worldObj.theProfiler.startSection("reposition");
/* 1644:2334 */       var2.getConfigurationManager().transferEntityToWorld(this, var3, var4, var5);
/* 1645:2335 */       this.worldObj.theProfiler.endStartSection("reloading");
/* 1646:2336 */       Entity var6 = EntityList.createEntityByName(EntityList.getEntityString(this), var5);
/* 1647:2338 */       if (var6 != null)
/* 1648:     */       {
/* 1649:2340 */         var6.copyDataFrom(this, true);
/* 1650:2342 */         if ((var3 == 1) && (par1 == 1))
/* 1651:     */         {
/* 1652:2344 */           ChunkCoordinates var7 = var5.getSpawnPoint();
/* 1653:2345 */           var7.posY = this.worldObj.getTopSolidOrLiquidBlock(var7.posX, var7.posZ);
/* 1654:2346 */           var6.setLocationAndAngles(var7.posX, var7.posY, var7.posZ, var6.rotationYaw, var6.rotationPitch);
/* 1655:     */         }
/* 1656:2349 */         var5.spawnEntityInWorld(var6);
/* 1657:     */       }
/* 1658:2352 */       this.isDead = true;
/* 1659:2353 */       this.worldObj.theProfiler.endSection();
/* 1660:2354 */       var4.resetUpdateEntityTick();
/* 1661:2355 */       var5.resetUpdateEntityTick();
/* 1662:2356 */       this.worldObj.theProfiler.endSection();
/* 1663:     */     }
/* 1664:     */   }
/* 1665:     */   
/* 1666:     */   public float func_145772_a(Explosion p_145772_1_, World p_145772_2_, int p_145772_3_, int p_145772_4_, int p_145772_5_, Block p_145772_6_)
/* 1667:     */   {
/* 1668:2362 */     return p_145772_6_.getExplosionResistance(this);
/* 1669:     */   }
/* 1670:     */   
/* 1671:     */   public boolean func_145774_a(Explosion p_145774_1_, World p_145774_2_, int p_145774_3_, int p_145774_4_, int p_145774_5_, Block p_145774_6_, float p_145774_7_)
/* 1672:     */   {
/* 1673:2367 */     return true;
/* 1674:     */   }
/* 1675:     */   
/* 1676:     */   public int getMaxSafePointTries()
/* 1677:     */   {
/* 1678:2375 */     return 3;
/* 1679:     */   }
/* 1680:     */   
/* 1681:     */   public int getTeleportDirection()
/* 1682:     */   {
/* 1683:2380 */     return this.teleportDirection;
/* 1684:     */   }
/* 1685:     */   
/* 1686:     */   public boolean doesEntityNotTriggerPressurePlate()
/* 1687:     */   {
/* 1688:2385 */     return false;
/* 1689:     */   }
/* 1690:     */   
/* 1691:     */   public void addEntityCrashInfo(CrashReportCategory par1CrashReportCategory)
/* 1692:     */   {
/* 1693:2390 */     par1CrashReportCategory.addCrashSectionCallable("Entity Type", new Callable()
/* 1694:     */     {
/* 1695:     */       private static final String __OBFID = "CL_00001534";
/* 1696:     */       
/* 1697:     */       public String call()
/* 1698:     */       {
/* 1699:2395 */         return EntityList.getEntityString(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
/* 1700:     */       }
/* 1701:2397 */     });
/* 1702:2398 */     par1CrashReportCategory.addCrashSection("Entity ID", Integer.valueOf(this.field_145783_c));
/* 1703:2399 */     par1CrashReportCategory.addCrashSectionCallable("Entity Name", new Callable()
/* 1704:     */     {
/* 1705:     */       private static final String __OBFID = "CL_00001535";
/* 1706:     */       
/* 1707:     */       public String call()
/* 1708:     */       {
/* 1709:2404 */         return Entity.this.getCommandSenderName();
/* 1710:     */       }
/* 1711:2406 */     });
/* 1712:2407 */     par1CrashReportCategory.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) }));
/* 1713:2408 */     par1CrashReportCategory.addCrashSection("Entity's Block location", CrashReportCategory.getLocationInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
/* 1714:2409 */     par1CrashReportCategory.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.motionX), Double.valueOf(this.motionY), Double.valueOf(this.motionZ) }));
/* 1715:     */   }
/* 1716:     */   
/* 1717:     */   public boolean canRenderOnFire()
/* 1718:     */   {
/* 1719:2417 */     return isBurning();
/* 1720:     */   }
/* 1721:     */   
/* 1722:     */   public UUID getUniqueID()
/* 1723:     */   {
/* 1724:2422 */     return this.entityUniqueID;
/* 1725:     */   }
/* 1726:     */   
/* 1727:     */   public boolean isPushedByWater()
/* 1728:     */   {
/* 1729:2427 */     return true;
/* 1730:     */   }
/* 1731:     */   
/* 1732:     */   public IChatComponent func_145748_c_()
/* 1733:     */   {
/* 1734:2432 */     return new ChatComponentText(getCommandSenderName());
/* 1735:     */   }
/* 1736:     */   
/* 1737:     */   public void func_145781_i(int p_145781_1_) {}
/* 1738:     */   
/* 1739:     */   public static enum EnumEntitySize
/* 1740:     */   {
/* 1741:2439 */     SIZE_1("SIZE_1", 0),  SIZE_2("SIZE_2", 1),  SIZE_3("SIZE_3", 2),  SIZE_4("SIZE_4", 3),  SIZE_5("SIZE_5", 4),  SIZE_6("SIZE_6", 5);
/* 1742:     */     
/* 1743:2446 */     private static final EnumEntitySize[] $VALUES = { SIZE_1, SIZE_2, SIZE_3, SIZE_4, SIZE_5, SIZE_6 };
/* 1744:     */     private static final String __OBFID = "CL_00001537";
/* 1745:     */     
/* 1746:     */     private EnumEntitySize(String par1Str, int par2) {}
/* 1747:     */     
/* 1748:     */     public int multiplyBy32AndRound(double par1)
/* 1749:     */     {
/* 1750:2453 */       double var3 = par1 - (MathHelper.floor_double(par1) + 0.5D);
/* 1751:2455 */       switch (Entity.SwitchEnumEntitySize.field_96565_a[ordinal()])
/* 1752:     */       {
/* 1753:     */       case 1: 
/* 1754:2458 */         if (var3 < 0.0D)
/* 1755:     */         {
/* 1756:2460 */           if (var3 < -0.3125D) {
/* 1757:2462 */             return MathHelper.ceiling_double_int(par1 * 32.0D);
/* 1758:     */           }
/* 1759:     */         }
/* 1760:2465 */         else if (var3 < 0.3125D) {
/* 1761:2467 */           return MathHelper.ceiling_double_int(par1 * 32.0D);
/* 1762:     */         }
/* 1763:2470 */         return MathHelper.floor_double(par1 * 32.0D);
/* 1764:     */       case 2: 
/* 1765:2473 */         if (var3 < 0.0D)
/* 1766:     */         {
/* 1767:2475 */           if (var3 < -0.3125D) {
/* 1768:2477 */             return MathHelper.floor_double(par1 * 32.0D);
/* 1769:     */           }
/* 1770:     */         }
/* 1771:2480 */         else if (var3 < 0.3125D) {
/* 1772:2482 */           return MathHelper.floor_double(par1 * 32.0D);
/* 1773:     */         }
/* 1774:2485 */         return MathHelper.ceiling_double_int(par1 * 32.0D);
/* 1775:     */       case 3: 
/* 1776:2488 */         if (var3 > 0.0D) {
/* 1777:2490 */           return MathHelper.floor_double(par1 * 32.0D);
/* 1778:     */         }
/* 1779:2493 */         return MathHelper.ceiling_double_int(par1 * 32.0D);
/* 1780:     */       case 4: 
/* 1781:2496 */         if (var3 < 0.0D)
/* 1782:     */         {
/* 1783:2498 */           if (var3 < -0.1875D) {
/* 1784:2500 */             return MathHelper.ceiling_double_int(par1 * 32.0D);
/* 1785:     */           }
/* 1786:     */         }
/* 1787:2503 */         else if (var3 < 0.1875D) {
/* 1788:2505 */           return MathHelper.ceiling_double_int(par1 * 32.0D);
/* 1789:     */         }
/* 1790:2508 */         return MathHelper.floor_double(par1 * 32.0D);
/* 1791:     */       case 5: 
/* 1792:2511 */         if (var3 < 0.0D)
/* 1793:     */         {
/* 1794:2513 */           if (var3 < -0.1875D) {
/* 1795:2515 */             return MathHelper.floor_double(par1 * 32.0D);
/* 1796:     */           }
/* 1797:     */         }
/* 1798:2518 */         else if (var3 < 0.1875D) {
/* 1799:2520 */           return MathHelper.floor_double(par1 * 32.0D);
/* 1800:     */         }
/* 1801:2523 */         return MathHelper.ceiling_double_int(par1 * 32.0D);
/* 1802:     */       }
/* 1803:2527 */       if (var3 > 0.0D) {
/* 1804:2529 */         return MathHelper.ceiling_double_int(par1 * 32.0D);
/* 1805:     */       }
/* 1806:2533 */       return MathHelper.floor_double(par1 * 32.0D);
/* 1807:     */     }
/* 1808:     */   }
/* 1809:     */   
/* 1810:     */   static final class SwitchEnumEntitySize
/* 1811:     */   {
/* 1812:2541 */     static final int[] field_96565_a = new int[Entity.EnumEntitySize.values().length];
/* 1813:     */     private static final String __OBFID = "CL_00001536";
/* 1814:     */     
/* 1815:     */     static
/* 1816:     */     {
/* 1817:     */       try
/* 1818:     */       {
/* 1819:2548 */         field_96565_a[Entity.EnumEntitySize.SIZE_1.ordinal()] = 1;
/* 1820:     */       }
/* 1821:     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/* 1822:     */       try
/* 1823:     */       {
/* 1824:2557 */         field_96565_a[Entity.EnumEntitySize.SIZE_2.ordinal()] = 2;
/* 1825:     */       }
/* 1826:     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/* 1827:     */       try
/* 1828:     */       {
/* 1829:2566 */         field_96565_a[Entity.EnumEntitySize.SIZE_3.ordinal()] = 3;
/* 1830:     */       }
/* 1831:     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/* 1832:     */       try
/* 1833:     */       {
/* 1834:2575 */         field_96565_a[Entity.EnumEntitySize.SIZE_4.ordinal()] = 4;
/* 1835:     */       }
/* 1836:     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/* 1837:     */       try
/* 1838:     */       {
/* 1839:2584 */         field_96565_a[Entity.EnumEntitySize.SIZE_5.ordinal()] = 5;
/* 1840:     */       }
/* 1841:     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/* 1842:     */       try
/* 1843:     */       {
/* 1844:2593 */         field_96565_a[Entity.EnumEntitySize.SIZE_6.ordinal()] = 6;
/* 1845:     */       }
/* 1846:     */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/* 1847:     */     }
/* 1848:     */   }
/* 1849:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.Entity
 * JD-Core Version:    0.7.0.1
 */