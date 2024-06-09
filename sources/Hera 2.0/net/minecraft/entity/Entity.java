/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import me.eagler.Client;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockLiquid;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.block.state.pattern.BlockPattern;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.enchantment.EnchantmentProtection;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.event.HoverEvent;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagDouble;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Entity
/*      */   implements ICommandSender
/*      */ {
/*   54 */   private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*      */ 
/*      */   
/*      */   private static int nextEntityID;
/*      */ 
/*      */   
/*      */   private int entityId;
/*      */ 
/*      */   
/*      */   public double renderDistanceWeight;
/*      */ 
/*      */   
/*      */   public boolean preventEntitySpawning;
/*      */ 
/*      */   
/*      */   public Entity riddenByEntity;
/*      */ 
/*      */   
/*      */   public Entity ridingEntity;
/*      */ 
/*      */   
/*      */   public boolean forceSpawn;
/*      */ 
/*      */   
/*      */   public World worldObj;
/*      */ 
/*      */   
/*      */   public double prevPosX;
/*      */ 
/*      */   
/*      */   public double prevPosY;
/*      */ 
/*      */   
/*      */   public double prevPosZ;
/*      */ 
/*      */   
/*      */   public double posX;
/*      */ 
/*      */   
/*      */   public double posY;
/*      */ 
/*      */   
/*      */   public double posZ;
/*      */ 
/*      */   
/*      */   public double motionX;
/*      */ 
/*      */   
/*      */   public double motionY;
/*      */ 
/*      */   
/*      */   public double motionZ;
/*      */ 
/*      */   
/*      */   public float rotationYaw;
/*      */ 
/*      */   
/*      */   public float rotationPitch;
/*      */ 
/*      */   
/*      */   public float prevRotationYaw;
/*      */ 
/*      */   
/*      */   public float prevRotationPitch;
/*      */ 
/*      */   
/*      */   private AxisAlignedBB boundingBox;
/*      */ 
/*      */   
/*      */   public boolean onGround;
/*      */ 
/*      */   
/*      */   public boolean isCollidedHorizontally;
/*      */ 
/*      */   
/*      */   public boolean isCollidedVertically;
/*      */ 
/*      */   
/*      */   public boolean isCollided;
/*      */ 
/*      */   
/*      */   public boolean velocityChanged;
/*      */ 
/*      */   
/*      */   protected boolean isInWeb;
/*      */ 
/*      */   
/*      */   private boolean isOutsideBorder;
/*      */ 
/*      */   
/*      */   public boolean isDead;
/*      */ 
/*      */   
/*      */   public float width;
/*      */ 
/*      */   
/*      */   public float height;
/*      */ 
/*      */   
/*      */   public float prevDistanceWalkedModified;
/*      */ 
/*      */   
/*      */   public float distanceWalkedModified;
/*      */ 
/*      */   
/*      */   public float distanceWalkedOnStepModified;
/*      */ 
/*      */   
/*      */   public float fallDistance;
/*      */   
/*      */   private int nextStepDistance;
/*      */   
/*      */   public double lastTickPosX;
/*      */   
/*      */   public double lastTickPosY;
/*      */   
/*      */   public double lastTickPosZ;
/*      */   
/*      */   public float stepHeight;
/*      */   
/*      */   public boolean noClip;
/*      */   
/*      */   public float entityCollisionReduction;
/*      */   
/*      */   protected Random rand;
/*      */   
/*      */   public int ticksExisted;
/*      */   
/*      */   public int fireResistance;
/*      */   
/*      */   private int fire;
/*      */   
/*      */   protected boolean inWater;
/*      */   
/*      */   public int hurtResistantTime;
/*      */   
/*      */   protected boolean firstUpdate;
/*      */   
/*      */   protected boolean isImmuneToFire;
/*      */   
/*      */   protected DataWatcher dataWatcher;
/*      */   
/*      */   private double entityRiderPitchDelta;
/*      */   
/*      */   private double entityRiderYawDelta;
/*      */   
/*      */   public boolean addedToChunk;
/*      */   
/*      */   public int chunkCoordX;
/*      */   
/*      */   public int chunkCoordY;
/*      */   
/*      */   public int chunkCoordZ;
/*      */   
/*      */   public int serverPosX;
/*      */   
/*      */   public int serverPosY;
/*      */   
/*      */   public int serverPosZ;
/*      */   
/*      */   public boolean ignoreFrustumCheck;
/*      */   
/*      */   public boolean isAirBorne;
/*      */   
/*      */   public int timeUntilPortal;
/*      */   
/*      */   protected boolean inPortal;
/*      */   
/*      */   protected int portalCounter;
/*      */   
/*      */   public int dimension;
/*      */   
/*      */   protected BlockPos field_181016_an;
/*      */   
/*      */   protected Vec3 field_181017_ao;
/*      */   
/*      */   protected EnumFacing field_181018_ap;
/*      */   
/*      */   private boolean invulnerable;
/*      */   
/*      */   protected UUID entityUniqueID;
/*      */   
/*      */   private final CommandResultStats cmdResultStats;
/*      */ 
/*      */   
/*      */   public int getEntityId() {
/*  240 */     return this.entityId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityId(int id) {
/*  245 */     this.entityId = id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  253 */     setDead();
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity(World worldIn) {
/*  258 */     this.entityId = nextEntityID++;
/*  259 */     this.renderDistanceWeight = 1.0D;
/*  260 */     this.boundingBox = ZERO_AABB;
/*  261 */     this.width = 0.6F;
/*  262 */     this.height = 1.8F;
/*  263 */     this.nextStepDistance = 1;
/*  264 */     this.rand = new Random();
/*  265 */     this.fireResistance = 1;
/*  266 */     this.firstUpdate = true;
/*  267 */     this.entityUniqueID = MathHelper.getRandomUuid(this.rand);
/*  268 */     this.cmdResultStats = new CommandResultStats();
/*  269 */     this.worldObj = worldIn;
/*  270 */     setPosition(0.0D, 0.0D, 0.0D);
/*      */     
/*  272 */     if (worldIn != null)
/*      */     {
/*  274 */       this.dimension = worldIn.provider.getDimensionId();
/*      */     }
/*      */     
/*  277 */     this.dataWatcher = new DataWatcher(this);
/*  278 */     this.dataWatcher.addObject(0, Byte.valueOf((byte)0));
/*  279 */     this.dataWatcher.addObject(1, Short.valueOf((short)300));
/*  280 */     this.dataWatcher.addObject(3, Byte.valueOf((byte)0));
/*  281 */     this.dataWatcher.addObject(2, "");
/*  282 */     this.dataWatcher.addObject(4, Byte.valueOf((byte)0));
/*  283 */     entityInit();
/*      */   }
/*      */ 
/*      */   
/*      */   protected abstract void entityInit();
/*      */   
/*      */   public DataWatcher getDataWatcher() {
/*  290 */     return this.dataWatcher;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object p_equals_1_) {
/*  295 */     return (p_equals_1_ instanceof Entity) ? ((((Entity)p_equals_1_).entityId == this.entityId)) : false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  300 */     return this.entityId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preparePlayerToSpawn() {
/*  309 */     if (this.worldObj != null) {
/*      */       
/*  311 */       while (this.posY > 0.0D && this.posY < 256.0D) {
/*      */         
/*  313 */         setPosition(this.posX, this.posY, this.posZ);
/*      */         
/*  315 */         if (this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  320 */         this.posY++;
/*      */       } 
/*      */       
/*  323 */       this.motionX = this.motionY = this.motionZ = 0.0D;
/*  324 */       this.rotationPitch = 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDead() {
/*  333 */     this.isDead = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSize(float width, float height) {
/*  341 */     if (width != this.width || height != this.height) {
/*      */       
/*  343 */       float f = this.width;
/*  344 */       this.width = width;
/*  345 */       this.height = height;
/*  346 */       setEntityBoundingBox(new AxisAlignedBB((getEntityBoundingBox()).minX, (getEntityBoundingBox()).minY, (getEntityBoundingBox()).minZ, (getEntityBoundingBox()).minX + this.width, (getEntityBoundingBox()).minY + this.height, (getEntityBoundingBox()).minZ + this.width));
/*      */       
/*  348 */       if (this.width > f && !this.firstUpdate && !this.worldObj.isRemote)
/*      */       {
/*  350 */         moveEntity((f - this.width), 0.0D, (f - this.width));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setRotation(float yaw, float pitch) {
/*  360 */     this.rotationYaw = yaw % 360.0F;
/*  361 */     this.rotationPitch = pitch % 360.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPosition(double x, double y, double z) {
/*  369 */     this.posX = x;
/*  370 */     this.posY = y;
/*  371 */     this.posZ = z;
/*  372 */     float f = this.width / 2.0F;
/*  373 */     float f1 = this.height;
/*  374 */     setEntityBoundingBox(new AxisAlignedBB(x - f, y, z - f, x + f, y + f1, z + f));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngles(float yaw, float pitch) {
/*  383 */     float f = this.rotationPitch;
/*  384 */     float f1 = this.rotationYaw;
/*  385 */     this.rotationYaw = (float)(this.rotationYaw + yaw * 0.15D);
/*  386 */     this.rotationPitch = (float)(this.rotationPitch - pitch * 0.15D);
/*  387 */     this.rotationPitch = MathHelper.clamp_float(this.rotationPitch, -90.0F, 90.0F);
/*  388 */     this.prevRotationPitch += this.rotationPitch - f;
/*  389 */     this.prevRotationYaw += this.rotationYaw - f1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  397 */     onEntityUpdate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  405 */     this.worldObj.theProfiler.startSection("entityBaseTick");
/*      */     
/*  407 */     if (this.ridingEntity != null && this.ridingEntity.isDead)
/*      */     {
/*  409 */       this.ridingEntity = null;
/*      */     }
/*      */     
/*  412 */     this.prevDistanceWalkedModified = this.distanceWalkedModified;
/*  413 */     this.prevPosX = this.posX;
/*  414 */     this.prevPosY = this.posY;
/*  415 */     this.prevPosZ = this.posZ;
/*  416 */     this.prevRotationPitch = this.rotationPitch;
/*  417 */     this.prevRotationYaw = this.rotationYaw;
/*      */     
/*  419 */     if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
/*      */       
/*  421 */       this.worldObj.theProfiler.startSection("portal");
/*  422 */       MinecraftServer minecraftserver = ((WorldServer)this.worldObj).getMinecraftServer();
/*  423 */       int i = getMaxInPortalTime();
/*      */       
/*  425 */       if (this.inPortal) {
/*      */         
/*  427 */         if (minecraftserver.getAllowNether())
/*      */         {
/*  429 */           if (this.ridingEntity == null && this.portalCounter++ >= i) {
/*      */             int j;
/*  431 */             this.portalCounter = i;
/*  432 */             this.timeUntilPortal = getPortalCooldown();
/*      */ 
/*      */             
/*  435 */             if (this.worldObj.provider.getDimensionId() == -1) {
/*      */               
/*  437 */               j = 0;
/*      */             }
/*      */             else {
/*      */               
/*  441 */               j = -1;
/*      */             } 
/*      */             
/*  444 */             travelToDimension(j);
/*      */           } 
/*      */           
/*  447 */           this.inPortal = false;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  452 */         if (this.portalCounter > 0)
/*      */         {
/*  454 */           this.portalCounter -= 4;
/*      */         }
/*      */         
/*  457 */         if (this.portalCounter < 0)
/*      */         {
/*  459 */           this.portalCounter = 0;
/*      */         }
/*      */       } 
/*      */       
/*  463 */       if (this.timeUntilPortal > 0)
/*      */       {
/*  465 */         this.timeUntilPortal--;
/*      */       }
/*      */       
/*  468 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */     
/*  471 */     spawnRunningParticles();
/*  472 */     handleWaterMovement();
/*      */     
/*  474 */     if (this.worldObj.isRemote) {
/*      */       
/*  476 */       this.fire = 0;
/*      */     }
/*  478 */     else if (this.fire > 0) {
/*      */       
/*  480 */       if (this.isImmuneToFire) {
/*      */         
/*  482 */         this.fire -= 4;
/*      */         
/*  484 */         if (this.fire < 0)
/*      */         {
/*  486 */           this.fire = 0;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  491 */         if (this.fire % 20 == 0)
/*      */         {
/*  493 */           attackEntityFrom(DamageSource.onFire, 1.0F);
/*      */         }
/*      */         
/*  496 */         this.fire--;
/*      */       } 
/*      */     } 
/*      */     
/*  500 */     if (isInLava()) {
/*      */       
/*  502 */       setOnFireFromLava();
/*  503 */       this.fallDistance *= 0.5F;
/*      */     } 
/*      */     
/*  506 */     if (this.posY < -64.0D)
/*      */     {
/*  508 */       kill();
/*      */     }
/*      */     
/*  511 */     if (!this.worldObj.isRemote)
/*      */     {
/*  513 */       setFlag(0, (this.fire > 0));
/*      */     }
/*      */     
/*  516 */     this.firstUpdate = false;
/*  517 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxInPortalTime() {
/*  525 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setOnFireFromLava() {
/*  533 */     if (!this.isImmuneToFire) {
/*      */       
/*  535 */       attackEntityFrom(DamageSource.lava, 4.0F);
/*  536 */       setFire(15);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFire(int seconds) {
/*  545 */     int i = seconds * 20;
/*  546 */     i = EnchantmentProtection.getFireTimeForEntity(this, i);
/*      */     
/*  548 */     if (this.fire < i)
/*      */     {
/*  550 */       this.fire = i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void extinguish() {
/*  559 */     this.fire = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void kill() {
/*  567 */     setDead();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOffsetPositionInLiquid(double x, double y, double z) {
/*  575 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox().offset(x, y, z);
/*  576 */     return isLiquidPresentInAABB(axisalignedbb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLiquidPresentInAABB(AxisAlignedBB bb) {
/*  584 */     return (this.worldObj.getCollidingBoundingBoxes(this, bb).isEmpty() && !this.worldObj.isAnyLiquid(bb));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntity(double x, double y, double z) {
/*  592 */     if (this.noClip) {
/*      */       
/*  594 */       setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
/*  595 */       resetPositionToBB();
/*      */     }
/*      */     else {
/*      */       
/*  599 */       this.worldObj.theProfiler.startSection("move");
/*  600 */       double d0 = this.posX;
/*  601 */       double d1 = this.posY;
/*  602 */       double d2 = this.posZ;
/*      */       
/*  604 */       if (this.isInWeb) {
/*      */         
/*  606 */         this.isInWeb = false;
/*  607 */         x *= 0.25D;
/*  608 */         y *= 0.05000000074505806D;
/*  609 */         z *= 0.25D;
/*  610 */         this.motionX = 0.0D;
/*  611 */         this.motionY = 0.0D;
/*  612 */         this.motionZ = 0.0D;
/*      */       } 
/*      */       
/*  615 */       double d3 = x;
/*  616 */       double d4 = y;
/*  617 */       double d5 = z;
/*  618 */       boolean flag = !((!this.onGround || !isSneaking() || !(this instanceof EntityPlayer)) && !Client.instance.getModuleManager().getModuleByName("ScaffoldWalk").isEnabled());
/*      */       
/*  620 */       if (flag) {
/*      */         double d6;
/*      */ 
/*      */         
/*  624 */         for (d6 = 0.05D; x != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(x, -1.0D, 0.0D)).isEmpty(); d3 = x) {
/*      */           
/*  626 */           if (x < d6 && x >= -d6) {
/*      */             
/*  628 */             x = 0.0D;
/*      */           }
/*  630 */           else if (x > 0.0D) {
/*      */             
/*  632 */             x -= d6;
/*      */           }
/*      */           else {
/*      */             
/*  636 */             x += d6;
/*      */           } 
/*      */         } 
/*      */         
/*  640 */         for (; z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(0.0D, -1.0D, z)).isEmpty(); d5 = z) {
/*      */           
/*  642 */           if (z < d6 && z >= -d6) {
/*      */             
/*  644 */             z = 0.0D;
/*      */           }
/*  646 */           else if (z > 0.0D) {
/*      */             
/*  648 */             z -= d6;
/*      */           }
/*      */           else {
/*      */             
/*  652 */             z += d6;
/*      */           } 
/*      */         } 
/*      */         
/*  656 */         for (; x != 0.0D && z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(x, -1.0D, z)).isEmpty(); d5 = z) {
/*      */           
/*  658 */           if (x < d6 && x >= -d6) {
/*      */             
/*  660 */             x = 0.0D;
/*      */           }
/*  662 */           else if (x > 0.0D) {
/*      */             
/*  664 */             x -= d6;
/*      */           }
/*      */           else {
/*      */             
/*  668 */             x += d6;
/*      */           } 
/*      */           
/*  671 */           d3 = x;
/*      */           
/*  673 */           if (z < d6 && z >= -d6) {
/*      */             
/*  675 */             z = 0.0D;
/*      */           }
/*  677 */           else if (z > 0.0D) {
/*      */             
/*  679 */             z -= d6;
/*      */           }
/*      */           else {
/*      */             
/*  683 */             z += d6;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  688 */       List<AxisAlignedBB> list1 = this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().addCoord(x, y, z));
/*  689 */       AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*      */       
/*  691 */       for (AxisAlignedBB axisalignedbb1 : list1)
/*      */       {
/*  693 */         y = axisalignedbb1.calculateYOffset(getEntityBoundingBox(), y);
/*      */       }
/*      */       
/*  696 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*  697 */       boolean flag1 = !(!this.onGround && (d4 == y || d4 >= 0.0D));
/*      */       
/*  699 */       for (AxisAlignedBB axisalignedbb2 : list1)
/*      */       {
/*  701 */         x = axisalignedbb2.calculateXOffset(getEntityBoundingBox(), x);
/*      */       }
/*      */       
/*  704 */       setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*      */       
/*  706 */       for (AxisAlignedBB axisalignedbb13 : list1)
/*      */       {
/*  708 */         z = axisalignedbb13.calculateZOffset(getEntityBoundingBox(), z);
/*      */       }
/*      */       
/*  711 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*      */       
/*  713 */       if (this.stepHeight > 0.0F && flag1 && (d3 != x || d5 != z)) {
/*      */         
/*  715 */         double d11 = x;
/*  716 */         double d7 = y;
/*  717 */         double d8 = z;
/*  718 */         AxisAlignedBB axisalignedbb3 = getEntityBoundingBox();
/*  719 */         setEntityBoundingBox(axisalignedbb);
/*  720 */         y = this.stepHeight;
/*  721 */         List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().addCoord(d3, y, d5));
/*  722 */         AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
/*  723 */         AxisAlignedBB axisalignedbb5 = axisalignedbb4.addCoord(d3, 0.0D, d5);
/*  724 */         double d9 = y;
/*      */         
/*  726 */         for (AxisAlignedBB axisalignedbb6 : list)
/*      */         {
/*  728 */           d9 = axisalignedbb6.calculateYOffset(axisalignedbb5, d9);
/*      */         }
/*      */         
/*  731 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, d9, 0.0D);
/*  732 */         double d15 = d3;
/*      */         
/*  734 */         for (AxisAlignedBB axisalignedbb7 : list)
/*      */         {
/*  736 */           d15 = axisalignedbb7.calculateXOffset(axisalignedbb4, d15);
/*      */         }
/*      */         
/*  739 */         axisalignedbb4 = axisalignedbb4.offset(d15, 0.0D, 0.0D);
/*  740 */         double d16 = d5;
/*      */         
/*  742 */         for (AxisAlignedBB axisalignedbb8 : list)
/*      */         {
/*  744 */           d16 = axisalignedbb8.calculateZOffset(axisalignedbb4, d16);
/*      */         }
/*      */         
/*  747 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d16);
/*  748 */         AxisAlignedBB axisalignedbb14 = getEntityBoundingBox();
/*  749 */         double d17 = y;
/*      */         
/*  751 */         for (AxisAlignedBB axisalignedbb9 : list)
/*      */         {
/*  753 */           d17 = axisalignedbb9.calculateYOffset(axisalignedbb14, d17);
/*      */         }
/*      */         
/*  756 */         axisalignedbb14 = axisalignedbb14.offset(0.0D, d17, 0.0D);
/*  757 */         double d18 = d3;
/*      */         
/*  759 */         for (AxisAlignedBB axisalignedbb10 : list)
/*      */         {
/*  761 */           d18 = axisalignedbb10.calculateXOffset(axisalignedbb14, d18);
/*      */         }
/*      */         
/*  764 */         axisalignedbb14 = axisalignedbb14.offset(d18, 0.0D, 0.0D);
/*  765 */         double d19 = d5;
/*      */         
/*  767 */         for (AxisAlignedBB axisalignedbb11 : list)
/*      */         {
/*  769 */           d19 = axisalignedbb11.calculateZOffset(axisalignedbb14, d19);
/*      */         }
/*      */         
/*  772 */         axisalignedbb14 = axisalignedbb14.offset(0.0D, 0.0D, d19);
/*  773 */         double d20 = d15 * d15 + d16 * d16;
/*  774 */         double d10 = d18 * d18 + d19 * d19;
/*      */         
/*  776 */         if (d20 > d10) {
/*      */           
/*  778 */           x = d15;
/*  779 */           z = d16;
/*  780 */           y = -d9;
/*  781 */           setEntityBoundingBox(axisalignedbb4);
/*      */         }
/*      */         else {
/*      */           
/*  785 */           x = d18;
/*  786 */           z = d19;
/*  787 */           y = -d17;
/*  788 */           setEntityBoundingBox(axisalignedbb14);
/*      */         } 
/*      */         
/*  791 */         for (AxisAlignedBB axisalignedbb12 : list)
/*      */         {
/*  793 */           y = axisalignedbb12.calculateYOffset(getEntityBoundingBox(), y);
/*      */         }
/*      */         
/*  796 */         setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*      */         
/*  798 */         if (d11 * d11 + d8 * d8 >= x * x + z * z) {
/*      */           
/*  800 */           x = d11;
/*  801 */           y = d7;
/*  802 */           z = d8;
/*  803 */           setEntityBoundingBox(axisalignedbb3);
/*      */         } 
/*      */       } 
/*      */       
/*  807 */       this.worldObj.theProfiler.endSection();
/*  808 */       this.worldObj.theProfiler.startSection("rest");
/*  809 */       resetPositionToBB();
/*  810 */       this.isCollidedHorizontally = !(d3 == x && d5 == z);
/*  811 */       this.isCollidedVertically = (d4 != y);
/*  812 */       this.onGround = (this.isCollidedVertically && d4 < 0.0D);
/*  813 */       this.isCollided = !(!this.isCollidedHorizontally && !this.isCollidedVertically);
/*  814 */       int i = MathHelper.floor_double(this.posX);
/*  815 */       int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/*  816 */       int k = MathHelper.floor_double(this.posZ);
/*  817 */       BlockPos blockpos = new BlockPos(i, j, k);
/*  818 */       Block block1 = this.worldObj.getBlockState(blockpos).getBlock();
/*      */       
/*  820 */       if (block1.getMaterial() == Material.air) {
/*      */         
/*  822 */         Block block = this.worldObj.getBlockState(blockpos.down()).getBlock();
/*      */         
/*  824 */         if (block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockWall || block instanceof net.minecraft.block.BlockFenceGate) {
/*      */           
/*  826 */           block1 = block;
/*  827 */           blockpos = blockpos.down();
/*      */         } 
/*      */       } 
/*      */       
/*  831 */       updateFallState(y, this.onGround, block1, blockpos);
/*      */       
/*  833 */       if (d3 != x)
/*      */       {
/*  835 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/*  838 */       if (d5 != z)
/*      */       {
/*  840 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/*  843 */       if (d4 != y)
/*      */       {
/*  845 */         block1.onLanded(this.worldObj, this);
/*      */       }
/*      */       
/*  848 */       if (canTriggerWalking() && !flag && this.ridingEntity == null) {
/*      */         
/*  850 */         double d12 = this.posX - d0;
/*  851 */         double d13 = this.posY - d1;
/*  852 */         double d14 = this.posZ - d2;
/*      */         
/*  854 */         if (block1 != Blocks.ladder)
/*      */         {
/*  856 */           d13 = 0.0D;
/*      */         }
/*      */         
/*  859 */         if (block1 != null && this.onGround)
/*      */         {
/*  861 */           block1.onEntityCollidedWithBlock(this.worldObj, blockpos, this);
/*      */         }
/*      */         
/*  864 */         this.distanceWalkedModified = (float)(this.distanceWalkedModified + MathHelper.sqrt_double(d12 * d12 + d14 * d14) * 0.6D);
/*  865 */         this.distanceWalkedOnStepModified = (float)(this.distanceWalkedOnStepModified + MathHelper.sqrt_double(d12 * d12 + d13 * d13 + d14 * d14) * 0.6D);
/*      */         
/*  867 */         if (this.distanceWalkedOnStepModified > this.nextStepDistance && block1.getMaterial() != Material.air) {
/*      */           
/*  869 */           this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
/*      */           
/*  871 */           if (isInWater()) {
/*      */             
/*  873 */             float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.35F;
/*      */             
/*  875 */             if (f > 1.0F)
/*      */             {
/*  877 */               f = 1.0F;
/*      */             }
/*      */             
/*  880 */             playSound(getSwimSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*      */           } 
/*      */           
/*  883 */           playStepSound(blockpos, block1);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  889 */         doBlockCollisions();
/*      */       }
/*  891 */       catch (Throwable throwable) {
/*      */         
/*  893 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
/*  894 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
/*  895 */         addEntityCrashInfo(crashreportcategory);
/*  896 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  899 */       boolean flag2 = isWet();
/*      */       
/*  901 */       if (this.worldObj.isFlammableWithin(getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D))) {
/*      */         
/*  903 */         dealFireDamage(1);
/*      */         
/*  905 */         if (!flag2)
/*      */         {
/*  907 */           this.fire++;
/*      */           
/*  909 */           if (this.fire == 0)
/*      */           {
/*  911 */             setFire(8);
/*      */           }
/*      */         }
/*      */       
/*  915 */       } else if (this.fire <= 0) {
/*      */         
/*  917 */         this.fire = -this.fireResistance;
/*      */       } 
/*      */       
/*  920 */       if (flag2 && this.fire > 0) {
/*      */         
/*  922 */         playSound("random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*  923 */         this.fire = -this.fireResistance;
/*      */       } 
/*      */       
/*  926 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetPositionToBB() {
/*  935 */     this.posX = ((getEntityBoundingBox()).minX + (getEntityBoundingBox()).maxX) / 2.0D;
/*  936 */     this.posY = (getEntityBoundingBox()).minY;
/*  937 */     this.posZ = ((getEntityBoundingBox()).minZ + (getEntityBoundingBox()).maxZ) / 2.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSwimSound() {
/*  942 */     return "game.neutral.swim";
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doBlockCollisions() {
/*  947 */     BlockPos blockpos = new BlockPos((getEntityBoundingBox()).minX + 0.001D, (getEntityBoundingBox()).minY + 0.001D, (getEntityBoundingBox()).minZ + 0.001D);
/*  948 */     BlockPos blockpos1 = new BlockPos((getEntityBoundingBox()).maxX - 0.001D, (getEntityBoundingBox()).maxY - 0.001D, (getEntityBoundingBox()).maxZ - 0.001D);
/*      */     
/*  950 */     if (this.worldObj.isAreaLoaded(blockpos, blockpos1))
/*      */     {
/*  952 */       for (int i = blockpos.getX(); i <= blockpos1.getX(); i++) {
/*      */         
/*  954 */         for (int j = blockpos.getY(); j <= blockpos1.getY(); j++) {
/*      */           
/*  956 */           for (int k = blockpos.getZ(); k <= blockpos1.getZ(); k++) {
/*      */             
/*  958 */             BlockPos blockpos2 = new BlockPos(i, j, k);
/*  959 */             IBlockState iblockstate = this.worldObj.getBlockState(blockpos2);
/*      */ 
/*      */             
/*      */             try {
/*  963 */               iblockstate.getBlock().onEntityCollidedWithBlock(this.worldObj, blockpos2, iblockstate, this);
/*      */             }
/*  965 */             catch (Throwable throwable) {
/*      */               
/*  967 */               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
/*  968 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
/*  969 */               CrashReportCategory.addBlockInfo(crashreportcategory, blockpos2, iblockstate);
/*  970 */               throw new ReportedException(crashreport);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  980 */     Block.SoundType block$soundtype = blockIn.stepSound;
/*      */     
/*  982 */     if (this.worldObj.getBlockState(pos.up()).getBlock() == Blocks.snow_layer) {
/*      */       
/*  984 */       block$soundtype = Blocks.snow_layer.stepSound;
/*  985 */       playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */     }
/*  987 */     else if (!blockIn.getMaterial().isLiquid()) {
/*      */       
/*  989 */       playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(String name, float volume, float pitch) {
/*  995 */     if (!isSilent())
/*      */     {
/*  997 */       this.worldObj.playSoundAtEntity(this, name, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSilent() {
/* 1006 */     return (this.dataWatcher.getWatchableObjectByte(4) == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSilent(boolean isSilent) {
/* 1014 */     this.dataWatcher.updateObject(4, Byte.valueOf((byte)(isSilent ? 1 : 0)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/* 1023 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
/* 1028 */     if (onGroundIn) {
/*      */       
/* 1030 */       if (this.fallDistance > 0.0F)
/*      */       {
/* 1032 */         if (blockIn != null) {
/*      */           
/* 1034 */           blockIn.onFallenUpon(this.worldObj, pos, this, this.fallDistance);
/*      */         }
/*      */         else {
/*      */           
/* 1038 */           fall(this.fallDistance, 1.0F);
/*      */         } 
/*      */         
/* 1041 */         this.fallDistance = 0.0F;
/*      */       }
/*      */     
/* 1044 */     } else if (y < 0.0D) {
/*      */       
/* 1046 */       this.fallDistance = (float)(this.fallDistance - y);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBoundingBox() {
/* 1055 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dealFireDamage(int amount) {
/* 1064 */     if (!this.isImmuneToFire)
/*      */     {
/* 1066 */       attackEntityFrom(DamageSource.inFire, amount);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isImmuneToFire() {
/* 1072 */     return this.isImmuneToFire;
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/* 1077 */     if (this.riddenByEntity != null)
/*      */     {
/* 1079 */       this.riddenByEntity.fall(distance, damageMultiplier);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWet() {
/* 1088 */     return !(!this.inWater && !this.worldObj.canLightningStrike(new BlockPos(this.posX, this.posY, this.posZ)) && !this.worldObj.canLightningStrike(new BlockPos(this.posX, this.posY + this.height, this.posZ)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInWater() {
/* 1097 */     return this.inWater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleWaterMovement() {
/* 1105 */     if (this.worldObj.handleMaterialAcceleration(getEntityBoundingBox().expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, this)) {
/*      */       
/* 1107 */       if (!this.inWater && !this.firstUpdate)
/*      */       {
/* 1109 */         resetHeight();
/*      */       }
/*      */       
/* 1112 */       this.fallDistance = 0.0F;
/* 1113 */       this.inWater = true;
/* 1114 */       this.fire = 0;
/*      */     }
/*      */     else {
/*      */       
/* 1118 */       this.inWater = false;
/*      */     } 
/*      */     
/* 1121 */     return this.inWater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHeight() {
/* 1129 */     float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.2F;
/*      */     
/* 1131 */     if (f > 1.0F)
/*      */     {
/* 1133 */       f = 1.0F;
/*      */     }
/*      */     
/* 1136 */     playSound(getSplashSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/* 1137 */     float f1 = MathHelper.floor_double((getEntityBoundingBox()).minY);
/*      */     
/* 1139 */     for (int i = 0; i < 1.0F + this.width * 20.0F; i++) {
/*      */       
/* 1141 */       float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1142 */       float f3 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1143 */       this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + f2, (f1 + 1.0F), this.posZ + f3, this.motionX, this.motionY - (this.rand.nextFloat() * 0.2F), this.motionZ, new int[0]);
/*      */     } 
/*      */     
/* 1146 */     for (int j = 0; j < 1.0F + this.width * 20.0F; j++) {
/*      */       
/* 1148 */       float f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1149 */       float f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1150 */       this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + f4, (f1 + 1.0F), this.posZ + f5, this.motionX, this.motionY, this.motionZ, new int[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnRunningParticles() {
/* 1159 */     if (isSprinting() && !isInWater())
/*      */     {
/* 1161 */       createRunningParticles();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void createRunningParticles() {
/* 1167 */     int i = MathHelper.floor_double(this.posX);
/* 1168 */     int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/* 1169 */     int k = MathHelper.floor_double(this.posZ);
/* 1170 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 1171 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 1172 */     Block block = iblockstate.getBlock();
/*      */     
/* 1174 */     if (block.getRenderType() != -1)
/*      */     {
/* 1176 */       this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, (getEntityBoundingBox()).minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, -this.motionX * 4.0D, 1.5D, -this.motionZ * 4.0D, new int[] { Block.getStateId(iblockstate) });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSplashSound() {
/* 1182 */     return "game.neutral.swim.splash";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInsideOfMaterial(Material materialIn) {
/* 1190 */     double d0 = this.posY + getEyeHeight();
/* 1191 */     BlockPos blockpos = new BlockPos(this.posX, d0, this.posZ);
/* 1192 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 1193 */     Block block = iblockstate.getBlock();
/*      */     
/* 1195 */     if (block.getMaterial() == materialIn) {
/*      */       
/* 1197 */       float f = BlockLiquid.getLiquidHeightPercent(iblockstate.getBlock().getMetaFromState(iblockstate)) - 0.11111111F;
/* 1198 */       float f1 = (blockpos.getY() + 1) - f;
/* 1199 */       boolean flag = (d0 < f1);
/* 1200 */       return (!flag && this instanceof EntityPlayer) ? false : flag;
/*      */     } 
/*      */ 
/*      */     
/* 1204 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInLava() {
/* 1210 */     return this.worldObj.isMaterialInBB(getEntityBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.lava);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveFlying(float strafe, float forward, float friction) {
/* 1218 */     float f = strafe * strafe + forward * forward;
/*      */     
/* 1220 */     if (f >= 1.0E-4F) {
/*      */       
/* 1222 */       f = MathHelper.sqrt_float(f);
/*      */       
/* 1224 */       if (f < 1.0F)
/*      */       {
/* 1226 */         f = 1.0F;
/*      */       }
/*      */       
/* 1229 */       f = friction / f;
/* 1230 */       strafe *= f;
/* 1231 */       forward *= f;
/* 1232 */       float f1 = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F);
/* 1233 */       float f2 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F);
/* 1234 */       this.motionX += (strafe * f2 - forward * f1);
/* 1235 */       this.motionZ += (forward * f2 + strafe * f1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBrightnessForRender(float partialTicks) {
/* 1241 */     BlockPos blockpos = new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ);
/* 1242 */     return this.worldObj.isBlockLoaded(blockpos) ? this.worldObj.getCombinedLight(blockpos, 0) : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBrightness(float partialTicks) {
/* 1250 */     BlockPos blockpos = new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ);
/* 1251 */     return this.worldObj.isBlockLoaded(blockpos) ? this.worldObj.getLightBrightness(blockpos) : 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorld(World worldIn) {
/* 1259 */     this.worldObj = worldIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
/* 1267 */     this.prevPosX = this.posX = x;
/* 1268 */     this.prevPosY = this.posY = y;
/* 1269 */     this.prevPosZ = this.posZ = z;
/* 1270 */     this.prevRotationYaw = this.rotationYaw = yaw;
/* 1271 */     this.prevRotationPitch = this.rotationPitch = pitch;
/* 1272 */     double d0 = (this.prevRotationYaw - yaw);
/*      */     
/* 1274 */     if (d0 < -180.0D)
/*      */     {
/* 1276 */       this.prevRotationYaw += 360.0F;
/*      */     }
/*      */     
/* 1279 */     if (d0 >= 180.0D)
/*      */     {
/* 1281 */       this.prevRotationYaw -= 360.0F;
/*      */     }
/*      */     
/* 1284 */     setPosition(this.posX, this.posY, this.posZ);
/* 1285 */     setRotation(yaw, pitch);
/*      */   }
/*      */ 
/*      */   
/*      */   public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn) {
/* 1290 */     setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, rotationYawIn, rotationPitchIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
/* 1298 */     this.lastTickPosX = this.prevPosX = this.posX = x;
/* 1299 */     this.lastTickPosY = this.prevPosY = this.posY = y;
/* 1300 */     this.lastTickPosZ = this.prevPosZ = this.posZ = z;
/* 1301 */     this.rotationYaw = yaw;
/* 1302 */     this.rotationPitch = pitch;
/* 1303 */     setPosition(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDistanceToEntity(Entity entityIn) {
/* 1311 */     float f = (float)(this.posX - entityIn.posX);
/* 1312 */     float f1 = (float)(this.posY - entityIn.posY);
/* 1313 */     float f2 = (float)(this.posZ - entityIn.posZ);
/* 1314 */     return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistanceSq(double x, double y, double z) {
/* 1322 */     double d0 = this.posX - x;
/* 1323 */     double d1 = this.posY - y;
/* 1324 */     double d2 = this.posZ - z;
/* 1325 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDistanceSq(BlockPos pos) {
/* 1330 */     return pos.distanceSq(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDistanceSqToCenter(BlockPos pos) {
/* 1335 */     return pos.distanceSqToCenter(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistance(double x, double y, double z) {
/* 1343 */     double d0 = this.posX - x;
/* 1344 */     double d1 = this.posY - y;
/* 1345 */     double d2 = this.posZ - z;
/* 1346 */     return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistanceSqToEntity(Entity entityIn) {
/* 1354 */     double d0 = this.posX - entityIn.posX;
/* 1355 */     double d1 = this.posY - entityIn.posY;
/* 1356 */     double d2 = this.posZ - entityIn.posZ;
/* 1357 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCollideWithPlayer(EntityPlayer entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyEntityCollision(Entity entityIn) {
/* 1372 */     if (entityIn.riddenByEntity != this && entityIn.ridingEntity != this)
/*      */     {
/* 1374 */       if (!entityIn.noClip && !this.noClip) {
/*      */         
/* 1376 */         double d0 = entityIn.posX - this.posX;
/* 1377 */         double d1 = entityIn.posZ - this.posZ;
/* 1378 */         double d2 = MathHelper.abs_max(d0, d1);
/*      */         
/* 1380 */         if (d2 >= 0.009999999776482582D) {
/*      */           
/* 1382 */           d2 = MathHelper.sqrt_double(d2);
/* 1383 */           d0 /= d2;
/* 1384 */           d1 /= d2;
/* 1385 */           double d3 = 1.0D / d2;
/*      */           
/* 1387 */           if (d3 > 1.0D)
/*      */           {
/* 1389 */             d3 = 1.0D;
/*      */           }
/*      */           
/* 1392 */           d0 *= d3;
/* 1393 */           d1 *= d3;
/* 1394 */           d0 *= 0.05000000074505806D;
/* 1395 */           d1 *= 0.05000000074505806D;
/* 1396 */           d0 *= (1.0F - this.entityCollisionReduction);
/* 1397 */           d1 *= (1.0F - this.entityCollisionReduction);
/*      */           
/* 1399 */           if (this.riddenByEntity == null)
/*      */           {
/* 1401 */             addVelocity(-d0, 0.0D, -d1);
/*      */           }
/*      */           
/* 1404 */           if (entityIn.riddenByEntity == null)
/*      */           {
/* 1406 */             entityIn.addVelocity(d0, 0.0D, d1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addVelocity(double x, double y, double z) {
/* 1418 */     this.motionX += x;
/* 1419 */     this.motionY += y;
/* 1420 */     this.motionZ += z;
/* 1421 */     this.isAirBorne = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setBeenAttacked() {
/* 1429 */     this.velocityChanged = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 1437 */     if (isEntityInvulnerable(source))
/*      */     {
/* 1439 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1443 */     setBeenAttacked();
/* 1444 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLook(float partialTicks) {
/* 1453 */     if (partialTicks == 1.0F)
/*      */     {
/* 1455 */       return getVectorForRotation(this.rotationPitch, this.rotationYaw);
/*      */     }
/*      */ 
/*      */     
/* 1459 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
/* 1460 */     float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * partialTicks;
/* 1461 */     return getVectorForRotation(f, f1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Vec3 getVectorForRotation(float pitch, float yaw) {
/* 1470 */     float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
/* 1471 */     float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
/* 1472 */     float f2 = -MathHelper.cos(-pitch * 0.017453292F);
/* 1473 */     float f3 = MathHelper.sin(-pitch * 0.017453292F);
/* 1474 */     return new Vec3((f1 * f2), f3, (f * f2));
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 getPositionEyes(float partialTicks) {
/* 1479 */     if (partialTicks == 1.0F)
/*      */     {
/* 1481 */       return new Vec3(this.posX, this.posY + getEyeHeight(), this.posZ);
/*      */     }
/*      */ 
/*      */     
/* 1485 */     double d0 = this.prevPosX + (this.posX - this.prevPosX) * partialTicks;
/* 1486 */     double d1 = this.prevPosY + (this.posY - this.prevPosY) * partialTicks + getEyeHeight();
/* 1487 */     double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks;
/* 1488 */     return new Vec3(d0, d1, d2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTrace(double blockReachDistance, float partialTicks) {
/* 1494 */     Vec3 vec3 = getPositionEyes(partialTicks);
/* 1495 */     Vec3 vec31 = getLook(partialTicks);
/* 1496 */     Vec3 vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
/* 1497 */     return this.worldObj.rayTraceBlocks(vec3, vec32, false, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 1505 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/* 1513 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToPlayerScore(Entity entityIn, int amount) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRender3d(double x, double y, double z) {
/* 1526 */     double d0 = this.posX - x;
/* 1527 */     double d1 = this.posY - y;
/* 1528 */     double d2 = this.posZ - z;
/* 1529 */     double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 1530 */     return isInRangeToRenderDist(d3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRenderDist(double distance) {
/* 1539 */     double d0 = getEntityBoundingBox().getAverageEdgeLength();
/*      */     
/* 1541 */     if (Double.isNaN(d0))
/*      */     {
/* 1543 */       d0 = 1.0D;
/*      */     }
/*      */     
/* 1546 */     d0 = d0 * 64.0D * this.renderDistanceWeight;
/* 1547 */     return (distance < d0 * d0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeMountToNBT(NBTTagCompound tagCompund) {
/* 1556 */     String s = getEntityString();
/*      */     
/* 1558 */     if (!this.isDead && s != null) {
/*      */       
/* 1560 */       tagCompund.setString("id", s);
/* 1561 */       writeToNBT(tagCompund);
/* 1562 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1566 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeToNBTOptional(NBTTagCompound tagCompund) {
/* 1577 */     String s = getEntityString();
/*      */     
/* 1579 */     if (!this.isDead && s != null && this.riddenByEntity == null) {
/*      */       
/* 1581 */       tagCompund.setString("id", s);
/* 1582 */       writeToNBT(tagCompund);
/* 1583 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1587 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeToNBT(NBTTagCompound tagCompund) {
/*      */     try {
/* 1598 */       tagCompund.setTag("Pos", (NBTBase)newDoubleNBTList(new double[] { this.posX, this.posY, this.posZ }));
/* 1599 */       tagCompund.setTag("Motion", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/* 1600 */       tagCompund.setTag("Rotation", (NBTBase)newFloatNBTList(new float[] { this.rotationYaw, this.rotationPitch }));
/* 1601 */       tagCompund.setFloat("FallDistance", this.fallDistance);
/* 1602 */       tagCompund.setShort("Fire", (short)this.fire);
/* 1603 */       tagCompund.setShort("Air", (short)getAir());
/* 1604 */       tagCompund.setBoolean("OnGround", this.onGround);
/* 1605 */       tagCompund.setInteger("Dimension", this.dimension);
/* 1606 */       tagCompund.setBoolean("Invulnerable", this.invulnerable);
/* 1607 */       tagCompund.setInteger("PortalCooldown", this.timeUntilPortal);
/* 1608 */       tagCompund.setLong("UUIDMost", getUniqueID().getMostSignificantBits());
/* 1609 */       tagCompund.setLong("UUIDLeast", getUniqueID().getLeastSignificantBits());
/*      */       
/* 1611 */       if (getCustomNameTag() != null && getCustomNameTag().length() > 0) {
/*      */         
/* 1613 */         tagCompund.setString("CustomName", getCustomNameTag());
/* 1614 */         tagCompund.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
/*      */       } 
/*      */       
/* 1617 */       this.cmdResultStats.writeStatsToNBT(tagCompund);
/*      */       
/* 1619 */       if (isSilent())
/*      */       {
/* 1621 */         tagCompund.setBoolean("Silent", isSilent());
/*      */       }
/*      */       
/* 1624 */       writeEntityToNBT(tagCompund);
/*      */       
/* 1626 */       if (this.ridingEntity != null)
/*      */       {
/* 1628 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */         
/* 1630 */         if (this.ridingEntity.writeMountToNBT(nbttagcompound))
/*      */         {
/* 1632 */           tagCompund.setTag("Riding", (NBTBase)nbttagcompound);
/*      */         }
/*      */       }
/*      */     
/* 1636 */     } catch (Throwable throwable) {
/*      */       
/* 1638 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Saving entity NBT");
/* 1639 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being saved");
/* 1640 */       addEntityCrashInfo(crashreportcategory);
/* 1641 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readFromNBT(NBTTagCompound tagCompund) {
/*      */     try {
/* 1652 */       NBTTagList nbttaglist = tagCompund.getTagList("Pos", 6);
/* 1653 */       NBTTagList nbttaglist1 = tagCompund.getTagList("Motion", 6);
/* 1654 */       NBTTagList nbttaglist2 = tagCompund.getTagList("Rotation", 5);
/* 1655 */       this.motionX = nbttaglist1.getDoubleAt(0);
/* 1656 */       this.motionY = nbttaglist1.getDoubleAt(1);
/* 1657 */       this.motionZ = nbttaglist1.getDoubleAt(2);
/*      */       
/* 1659 */       if (Math.abs(this.motionX) > 10.0D)
/*      */       {
/* 1661 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/* 1664 */       if (Math.abs(this.motionY) > 10.0D)
/*      */       {
/* 1666 */         this.motionY = 0.0D;
/*      */       }
/*      */       
/* 1669 */       if (Math.abs(this.motionZ) > 10.0D)
/*      */       {
/* 1671 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/* 1674 */       this.prevPosX = this.lastTickPosX = this.posX = nbttaglist.getDoubleAt(0);
/* 1675 */       this.prevPosY = this.lastTickPosY = this.posY = nbttaglist.getDoubleAt(1);
/* 1676 */       this.prevPosZ = this.lastTickPosZ = this.posZ = nbttaglist.getDoubleAt(2);
/* 1677 */       this.prevRotationYaw = this.rotationYaw = nbttaglist2.getFloatAt(0);
/* 1678 */       this.prevRotationPitch = this.rotationPitch = nbttaglist2.getFloatAt(1);
/* 1679 */       setRotationYawHead(this.rotationYaw);
/* 1680 */       func_181013_g(this.rotationYaw);
/* 1681 */       this.fallDistance = tagCompund.getFloat("FallDistance");
/* 1682 */       this.fire = tagCompund.getShort("Fire");
/* 1683 */       setAir(tagCompund.getShort("Air"));
/* 1684 */       this.onGround = tagCompund.getBoolean("OnGround");
/* 1685 */       this.dimension = tagCompund.getInteger("Dimension");
/* 1686 */       this.invulnerable = tagCompund.getBoolean("Invulnerable");
/* 1687 */       this.timeUntilPortal = tagCompund.getInteger("PortalCooldown");
/*      */       
/* 1689 */       if (tagCompund.hasKey("UUIDMost", 4) && tagCompund.hasKey("UUIDLeast", 4)) {
/*      */         
/* 1691 */         this.entityUniqueID = new UUID(tagCompund.getLong("UUIDMost"), tagCompund.getLong("UUIDLeast"));
/*      */       }
/* 1693 */       else if (tagCompund.hasKey("UUID", 8)) {
/*      */         
/* 1695 */         this.entityUniqueID = UUID.fromString(tagCompund.getString("UUID"));
/*      */       } 
/*      */       
/* 1698 */       setPosition(this.posX, this.posY, this.posZ);
/* 1699 */       setRotation(this.rotationYaw, this.rotationPitch);
/*      */       
/* 1701 */       if (tagCompund.hasKey("CustomName", 8) && tagCompund.getString("CustomName").length() > 0)
/*      */       {
/* 1703 */         setCustomNameTag(tagCompund.getString("CustomName"));
/*      */       }
/*      */       
/* 1706 */       setAlwaysRenderNameTag(tagCompund.getBoolean("CustomNameVisible"));
/* 1707 */       this.cmdResultStats.readStatsFromNBT(tagCompund);
/* 1708 */       setSilent(tagCompund.getBoolean("Silent"));
/* 1709 */       readEntityFromNBT(tagCompund);
/*      */       
/* 1711 */       if (shouldSetPosAfterLoading())
/*      */       {
/* 1713 */         setPosition(this.posX, this.posY, this.posZ);
/*      */       }
/*      */     }
/* 1716 */     catch (Throwable throwable) {
/*      */       
/* 1718 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
/* 1719 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being loaded");
/* 1720 */       addEntityCrashInfo(crashreportcategory);
/* 1721 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean shouldSetPosAfterLoading() {
/* 1727 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getEntityString() {
/* 1735 */     return EntityList.getEntityString(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void readEntityFromNBT(NBTTagCompound paramNBTTagCompound);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void writeEntityToNBT(NBTTagCompound paramNBTTagCompound);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkLoad() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList newDoubleNBTList(double... numbers) {
/* 1757 */     NBTTagList nbttaglist = new NBTTagList(); byte b; int i;
/*      */     double[] arrayOfDouble;
/* 1759 */     for (i = (arrayOfDouble = numbers).length, b = 0; b < i; ) { double d0 = arrayOfDouble[b];
/*      */       
/* 1761 */       nbttaglist.appendTag((NBTBase)new NBTTagDouble(d0));
/*      */       b++; }
/*      */     
/* 1764 */     return nbttaglist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList newFloatNBTList(float... numbers) {
/* 1772 */     NBTTagList nbttaglist = new NBTTagList(); byte b; int i;
/*      */     float[] arrayOfFloat;
/* 1774 */     for (i = (arrayOfFloat = numbers).length, b = 0; b < i; ) { float f = arrayOfFloat[b];
/*      */       
/* 1776 */       nbttaglist.appendTag((NBTBase)new NBTTagFloat(f));
/*      */       b++; }
/*      */     
/* 1779 */     return nbttaglist;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem dropItem(Item itemIn, int size) {
/* 1784 */     return dropItemWithOffset(itemIn, size, 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem dropItemWithOffset(Item itemIn, int size, float offsetY) {
/* 1789 */     return entityDropItem(new ItemStack(itemIn, size, 0), offsetY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem entityDropItem(ItemStack itemStackIn, float offsetY) {
/* 1797 */     if (itemStackIn.stackSize != 0 && itemStackIn.getItem() != null) {
/*      */       
/* 1799 */       EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY + offsetY, this.posZ, itemStackIn);
/* 1800 */       entityitem.setDefaultPickupDelay();
/* 1801 */       this.worldObj.spawnEntityInWorld((Entity)entityitem);
/* 1802 */       return entityitem;
/*      */     } 
/*      */ 
/*      */     
/* 1806 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityAlive() {
/* 1815 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInsideOpaqueBlock() {
/* 1823 */     if (this.noClip)
/*      */     {
/* 1825 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1829 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(-2147483648, -2147483648, -2147483648);
/*      */     
/* 1831 */     for (int i = 0; i < 8; i++) {
/*      */       
/* 1833 */       int j = MathHelper.floor_double(this.posY + ((((i >> 0) % 2) - 0.5F) * 0.1F) + getEyeHeight());
/* 1834 */       int k = MathHelper.floor_double(this.posX + ((((i >> 1) % 2) - 0.5F) * this.width * 0.8F));
/* 1835 */       int l = MathHelper.floor_double(this.posZ + ((((i >> 2) % 2) - 0.5F) * this.width * 0.8F));
/*      */       
/* 1837 */       if (blockpos$mutableblockpos.getX() != k || blockpos$mutableblockpos.getY() != j || blockpos$mutableblockpos.getZ() != l) {
/*      */         
/* 1839 */         blockpos$mutableblockpos.func_181079_c(k, j, l);
/*      */         
/* 1841 */         if (this.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().isVisuallyOpaque())
/*      */         {
/* 1843 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1848 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactFirst(EntityPlayer playerIn) {
/* 1857 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBox(Entity entityIn) {
/* 1866 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/* 1874 */     if (this.ridingEntity.isDead) {
/*      */       
/* 1876 */       this.ridingEntity = null;
/*      */     }
/*      */     else {
/*      */       
/* 1880 */       this.motionX = 0.0D;
/* 1881 */       this.motionY = 0.0D;
/* 1882 */       this.motionZ = 0.0D;
/* 1883 */       onUpdate();
/*      */       
/* 1885 */       if (this.ridingEntity != null) {
/*      */         
/* 1887 */         this.ridingEntity.updateRiderPosition();
/* 1888 */         this.entityRiderYawDelta += (this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw);
/*      */         
/* 1890 */         for (this.entityRiderPitchDelta += (this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch); this.entityRiderYawDelta >= 180.0D; this.entityRiderYawDelta -= 360.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1895 */         while (this.entityRiderYawDelta < -180.0D)
/*      */         {
/* 1897 */           this.entityRiderYawDelta += 360.0D;
/*      */         }
/*      */         
/* 1900 */         while (this.entityRiderPitchDelta >= 180.0D)
/*      */         {
/* 1902 */           this.entityRiderPitchDelta -= 360.0D;
/*      */         }
/*      */         
/* 1905 */         while (this.entityRiderPitchDelta < -180.0D)
/*      */         {
/* 1907 */           this.entityRiderPitchDelta += 360.0D;
/*      */         }
/*      */         
/* 1910 */         double d0 = this.entityRiderYawDelta * 0.5D;
/* 1911 */         double d1 = this.entityRiderPitchDelta * 0.5D;
/* 1912 */         float f = 10.0F;
/*      */         
/* 1914 */         if (d0 > f)
/*      */         {
/* 1916 */           d0 = f;
/*      */         }
/*      */         
/* 1919 */         if (d0 < -f)
/*      */         {
/* 1921 */           d0 = -f;
/*      */         }
/*      */         
/* 1924 */         if (d1 > f)
/*      */         {
/* 1926 */           d1 = f;
/*      */         }
/*      */         
/* 1929 */         if (d1 < -f)
/*      */         {
/* 1931 */           d1 = -f;
/*      */         }
/*      */         
/* 1934 */         this.entityRiderYawDelta -= d0;
/* 1935 */         this.entityRiderPitchDelta -= d1;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateRiderPosition() {
/* 1942 */     if (this.riddenByEntity != null)
/*      */     {
/* 1944 */       this.riddenByEntity.setPosition(this.posX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/* 1953 */     return 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMountedYOffset() {
/* 1961 */     return this.height * 0.75D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/* 1969 */     this.entityRiderPitchDelta = 0.0D;
/* 1970 */     this.entityRiderYawDelta = 0.0D;
/*      */     
/* 1972 */     if (entityIn == null) {
/*      */       
/* 1974 */       if (this.ridingEntity != null) {
/*      */         
/* 1976 */         setLocationAndAngles(this.ridingEntity.posX, (this.ridingEntity.getEntityBoundingBox()).minY + this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
/* 1977 */         this.ridingEntity.riddenByEntity = null;
/*      */       } 
/*      */       
/* 1980 */       this.ridingEntity = null;
/*      */     }
/*      */     else {
/*      */       
/* 1984 */       if (this.ridingEntity != null)
/*      */       {
/* 1986 */         this.ridingEntity.riddenByEntity = null;
/*      */       }
/*      */       
/* 1989 */       if (entityIn != null)
/*      */       {
/* 1991 */         for (Entity entity = entityIn.ridingEntity; entity != null; entity = entity.ridingEntity) {
/*      */           
/* 1993 */           if (entity == this) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 2000 */       this.ridingEntity = entityIn;
/* 2001 */       entityIn.riddenByEntity = this;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 2007 */     setPosition(x, y, z);
/* 2008 */     setRotation(yaw, pitch);
/* 2009 */     List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().contract(0.03125D, 0.0D, 0.03125D));
/*      */     
/* 2011 */     if (!list.isEmpty()) {
/*      */       
/* 2013 */       double d0 = 0.0D;
/*      */       
/* 2015 */       for (AxisAlignedBB axisalignedbb : list) {
/*      */         
/* 2017 */         if (axisalignedbb.maxY > d0)
/*      */         {
/* 2019 */           d0 = axisalignedbb.maxY;
/*      */         }
/*      */       } 
/*      */       
/* 2023 */       y += d0 - (getEntityBoundingBox()).minY;
/* 2024 */       setPosition(x, y, z);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCollisionBorderSize() {
/* 2030 */     return 0.1F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLookVec() {
/* 2038 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181015_d(BlockPos p_181015_1_) {
/* 2043 */     if (this.timeUntilPortal > 0) {
/*      */       
/* 2045 */       this.timeUntilPortal = getPortalCooldown();
/*      */     }
/*      */     else {
/*      */       
/* 2049 */       if (!this.worldObj.isRemote && !p_181015_1_.equals(this.field_181016_an)) {
/*      */         
/* 2051 */         this.field_181016_an = p_181015_1_;
/* 2052 */         BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.portal.func_181089_f(this.worldObj, p_181015_1_);
/* 2053 */         double d0 = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? blockpattern$patternhelper.func_181117_a().getZ() : blockpattern$patternhelper.func_181117_a().getX();
/* 2054 */         double d1 = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? this.posZ : this.posX;
/* 2055 */         d1 = Math.abs(MathHelper.func_181160_c(d1 - ((blockpattern$patternhelper.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) ? true : false), d0, d0 - blockpattern$patternhelper.func_181118_d()));
/* 2056 */         double d2 = MathHelper.func_181160_c(this.posY - 1.0D, blockpattern$patternhelper.func_181117_a().getY(), (blockpattern$patternhelper.func_181117_a().getY() - blockpattern$patternhelper.func_181119_e()));
/* 2057 */         this.field_181017_ao = new Vec3(d1, d2, 0.0D);
/* 2058 */         this.field_181018_ap = blockpattern$patternhelper.getFinger();
/*      */       } 
/*      */       
/* 2061 */       this.inPortal = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPortalCooldown() {
/* 2070 */     return 300;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVelocity(double x, double y, double z) {
/* 2078 */     this.motionX = x;
/* 2079 */     this.motionY = y;
/* 2080 */     this.motionZ = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] getInventory() {
/* 2099 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBurning() {
/* 2114 */     boolean flag = (this.worldObj != null && this.worldObj.isRemote);
/* 2115 */     return (!this.isImmuneToFire && (this.fire > 0 || (flag && getFlag(0))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRiding() {
/* 2124 */     return (this.ridingEntity != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSneaking() {
/* 2132 */     return getFlag(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSneaking(boolean sneaking) {
/* 2140 */     setFlag(1, sneaking);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSprinting() {
/* 2148 */     return getFlag(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/* 2156 */     setFlag(3, sprinting);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInvisible() {
/* 2161 */     return getFlag(5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvisibleToPlayer(EntityPlayer player) {
/* 2171 */     return player.isSpectator() ? false : isInvisible();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInvisible(boolean invisible) {
/* 2176 */     setFlag(5, invisible);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEating() {
/* 2181 */     return getFlag(4);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEating(boolean eating) {
/* 2186 */     setFlag(4, eating);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean getFlag(int flag) {
/* 2195 */     return ((this.dataWatcher.getWatchableObjectByte(0) & 1 << flag) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFlag(int flag, boolean set) {
/* 2203 */     byte b0 = this.dataWatcher.getWatchableObjectByte(0);
/*      */     
/* 2205 */     if (set) {
/*      */       
/* 2207 */       this.dataWatcher.updateObject(0, Byte.valueOf((byte)(b0 | 1 << flag)));
/*      */     }
/*      */     else {
/*      */       
/* 2211 */       this.dataWatcher.updateObject(0, Byte.valueOf((byte)(b0 & (1 << flag ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAir() {
/* 2217 */     return this.dataWatcher.getWatchableObjectShort(1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAir(int air) {
/* 2222 */     this.dataWatcher.updateObject(1, Short.valueOf((short)air));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 2230 */     attackEntityFrom(DamageSource.lightningBolt, 5.0F);
/* 2231 */     this.fire++;
/*      */     
/* 2233 */     if (this.fire == 0)
/*      */     {
/* 2235 */       setFire(8);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillEntity(EntityLivingBase entityLivingIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean pushOutOfBlocks(double x, double y, double z) {
/* 2248 */     BlockPos blockpos = new BlockPos(x, y, z);
/* 2249 */     double d0 = x - blockpos.getX();
/* 2250 */     double d1 = y - blockpos.getY();
/* 2251 */     double d2 = z - blockpos.getZ();
/* 2252 */     List<AxisAlignedBB> list = this.worldObj.func_147461_a(getEntityBoundingBox());
/*      */     
/* 2254 */     if (list.isEmpty() && !this.worldObj.isBlockFullCube(blockpos))
/*      */     {
/* 2256 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2260 */     int i = 3;
/* 2261 */     double d3 = 9999.0D;
/*      */     
/* 2263 */     if (!this.worldObj.isBlockFullCube(blockpos.west()) && d0 < d3) {
/*      */       
/* 2265 */       d3 = d0;
/* 2266 */       i = 0;
/*      */     } 
/*      */     
/* 2269 */     if (!this.worldObj.isBlockFullCube(blockpos.east()) && 1.0D - d0 < d3) {
/*      */       
/* 2271 */       d3 = 1.0D - d0;
/* 2272 */       i = 1;
/*      */     } 
/*      */     
/* 2275 */     if (!this.worldObj.isBlockFullCube(blockpos.up()) && 1.0D - d1 < d3) {
/*      */       
/* 2277 */       d3 = 1.0D - d1;
/* 2278 */       i = 3;
/*      */     } 
/*      */     
/* 2281 */     if (!this.worldObj.isBlockFullCube(blockpos.north()) && d2 < d3) {
/*      */       
/* 2283 */       d3 = d2;
/* 2284 */       i = 4;
/*      */     } 
/*      */     
/* 2287 */     if (!this.worldObj.isBlockFullCube(blockpos.south()) && 1.0D - d2 < d3) {
/*      */       
/* 2289 */       d3 = 1.0D - d2;
/* 2290 */       i = 5;
/*      */     } 
/*      */     
/* 2293 */     float f = this.rand.nextFloat() * 0.2F + 0.1F;
/*      */     
/* 2295 */     if (i == 0)
/*      */     {
/* 2297 */       this.motionX = -f;
/*      */     }
/*      */     
/* 2300 */     if (i == 1)
/*      */     {
/* 2302 */       this.motionX = f;
/*      */     }
/*      */     
/* 2305 */     if (i == 3)
/*      */     {
/* 2307 */       this.motionY = f;
/*      */     }
/*      */     
/* 2310 */     if (i == 4)
/*      */     {
/* 2312 */       this.motionZ = -f;
/*      */     }
/*      */     
/* 2315 */     if (i == 5)
/*      */     {
/* 2317 */       this.motionZ = f;
/*      */     }
/*      */     
/* 2320 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInWeb() {
/* 2329 */     this.isInWeb = true;
/* 2330 */     this.fallDistance = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 2338 */     if (hasCustomName())
/*      */     {
/* 2340 */       return getCustomNameTag();
/*      */     }
/*      */ 
/*      */     
/* 2344 */     String s = EntityList.getEntityString(this);
/*      */     
/* 2346 */     if (s == null)
/*      */     {
/* 2348 */       s = "generic";
/*      */     }
/*      */     
/* 2351 */     return StatCollector.translateToLocal("entity." + s + ".name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity[] getParts() {
/* 2360 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityEqual(Entity entityIn) {
/* 2368 */     return (this == entityIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getRotationYawHead() {
/* 2373 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotationYawHead(float rotation) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_181013_g(float p_181013_1_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackWithItem() {
/* 2392 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hitByEntity(Entity entityIn) {
/* 2400 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2405 */     return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getName(), Integer.valueOf(this.entityId), (this.worldObj == null) ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName(), Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) });
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEntityInvulnerable(DamageSource source) {
/* 2410 */     return (this.invulnerable && source != DamageSource.outOfWorld && !source.isCreativePlayer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyLocationAndAnglesFrom(Entity entityIn) {
/* 2418 */     setLocationAndAngles(entityIn.posX, entityIn.posY, entityIn.posZ, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyDataFromOld(Entity entityIn) {
/* 2426 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2427 */     entityIn.writeToNBT(nbttagcompound);
/* 2428 */     readFromNBT(nbttagcompound);
/* 2429 */     this.timeUntilPortal = entityIn.timeUntilPortal;
/* 2430 */     this.field_181016_an = entityIn.field_181016_an;
/* 2431 */     this.field_181017_ao = entityIn.field_181017_ao;
/* 2432 */     this.field_181018_ap = entityIn.field_181018_ap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void travelToDimension(int dimensionId) {
/* 2440 */     if (!this.worldObj.isRemote && !this.isDead) {
/*      */       
/* 2442 */       this.worldObj.theProfiler.startSection("changeDimension");
/* 2443 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 2444 */       int i = this.dimension;
/* 2445 */       WorldServer worldserver = minecraftserver.worldServerForDimension(i);
/* 2446 */       WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
/* 2447 */       this.dimension = dimensionId;
/*      */       
/* 2449 */       if (i == 1 && dimensionId == 1) {
/*      */         
/* 2451 */         worldserver1 = minecraftserver.worldServerForDimension(0);
/* 2452 */         this.dimension = 0;
/*      */       } 
/*      */       
/* 2455 */       this.worldObj.removeEntity(this);
/* 2456 */       this.isDead = false;
/* 2457 */       this.worldObj.theProfiler.startSection("reposition");
/* 2458 */       minecraftserver.getConfigurationManager().transferEntityToWorld(this, i, worldserver, worldserver1);
/* 2459 */       this.worldObj.theProfiler.endStartSection("reloading");
/* 2460 */       Entity entity = EntityList.createEntityByName(EntityList.getEntityString(this), (World)worldserver1);
/*      */       
/* 2462 */       if (entity != null) {
/*      */         
/* 2464 */         entity.copyDataFromOld(this);
/*      */         
/* 2466 */         if (i == 1 && dimensionId == 1) {
/*      */           
/* 2468 */           BlockPos blockpos = this.worldObj.getTopSolidOrLiquidBlock(worldserver1.getSpawnPoint());
/* 2469 */           entity.moveToBlockPosAndAngles(blockpos, entity.rotationYaw, entity.rotationPitch);
/*      */         } 
/*      */         
/* 2472 */         worldserver1.spawnEntityInWorld(entity);
/*      */       } 
/*      */       
/* 2475 */       this.isDead = true;
/* 2476 */       this.worldObj.theProfiler.endSection();
/* 2477 */       worldserver.resetUpdateEntityTick();
/* 2478 */       worldserver1.resetUpdateEntityTick();
/* 2479 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
/* 2488 */     return blockStateIn.getBlock().getExplosionResistance(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean verifyExplosion(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_) {
/* 2493 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxFallHeight() {
/* 2501 */     return 3;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 func_181014_aG() {
/* 2506 */     return this.field_181017_ao;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumFacing func_181012_aH() {
/* 2511 */     return this.field_181018_ap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doesEntityNotTriggerPressurePlate() {
/* 2519 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addEntityCrashInfo(CrashReportCategory category) {
/* 2524 */     category.addCrashSectionCallable("Entity Type", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2528 */             return String.valueOf(EntityList.getEntityString(Entity.this)) + " (" + Entity.this.getClass().getCanonicalName() + ")";
/*      */           }
/*      */         });
/* 2531 */     category.addCrashSection("Entity ID", Integer.valueOf(this.entityId));
/* 2532 */     category.addCrashSectionCallable("Entity Name", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2536 */             return Entity.this.getName();
/*      */           }
/*      */         });
/* 2539 */     category.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) }));
/* 2540 */     category.addCrashSection("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
/* 2541 */     category.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.motionX), Double.valueOf(this.motionY), Double.valueOf(this.motionZ) }));
/* 2542 */     category.addCrashSectionCallable("Entity's Rider", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2546 */             return Entity.this.riddenByEntity.toString();
/*      */           }
/*      */         });
/* 2549 */     category.addCrashSectionCallable("Entity's Vehicle", new Callable<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2553 */             return Entity.this.ridingEntity.toString();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRenderOnFire() {
/* 2563 */     return isBurning();
/*      */   }
/*      */ 
/*      */   
/*      */   public UUID getUniqueID() {
/* 2568 */     return this.entityUniqueID;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPushedByWater() {
/* 2573 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 2581 */     ChatComponentText chatcomponenttext = new ChatComponentText(getName());
/* 2582 */     chatcomponenttext.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 2583 */     chatcomponenttext.getChatStyle().setInsertion(getUniqueID().toString());
/* 2584 */     return (IChatComponent)chatcomponenttext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCustomNameTag(String name) {
/* 2592 */     this.dataWatcher.updateObject(2, name);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCustomNameTag() {
/* 2597 */     return this.dataWatcher.getWatchableObjectString(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCustomName() {
/* 2605 */     return (this.dataWatcher.getWatchableObjectString(2).length() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAlwaysRenderNameTag(boolean alwaysRenderNameTag) {
/* 2610 */     this.dataWatcher.updateObject(3, Byte.valueOf((byte)(alwaysRenderNameTag ? 1 : 0)));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTag() {
/* 2615 */     return (this.dataWatcher.getWatchableObjectByte(3) == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndUpdate(double x, double y, double z) {
/* 2623 */     setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 2628 */     return getAlwaysRenderNameTag();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDataWatcherUpdate(int dataID) {}
/*      */ 
/*      */   
/*      */   public EnumFacing getHorizontalFacing() {
/* 2637 */     return EnumFacing.getHorizontal(MathHelper.floor_double((this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3);
/*      */   }
/*      */ 
/*      */   
/*      */   protected HoverEvent getHoverEvent() {
/* 2642 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2643 */     String s = EntityList.getEntityString(this);
/* 2644 */     nbttagcompound.setString("id", getUniqueID().toString());
/*      */     
/* 2646 */     if (s != null)
/*      */     {
/* 2648 */       nbttagcompound.setString("type", s);
/*      */     }
/*      */     
/* 2651 */     nbttagcompound.setString("name", getName());
/* 2652 */     return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, (IChatComponent)new ChatComponentText(nbttagcompound.toString()));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpectatedByPlayer(EntityPlayerMP player) {
/* 2657 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getEntityBoundingBox() {
/* 2662 */     return this.boundingBox;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityBoundingBox(AxisAlignedBB bb) {
/* 2667 */     this.boundingBox = bb;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/* 2672 */     return this.height * 0.85F;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOutsideBorder() {
/* 2677 */     return this.isOutsideBorder;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOutsideBorder(boolean outsideBorder) {
/* 2682 */     this.isOutsideBorder = outsideBorder;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 2687 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 2702 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 2711 */     return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getPositionVector() {
/* 2720 */     return new Vec3(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public World getEntityWorld() {
/* 2729 */     return this.worldObj;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getCommandSenderEntity() {
/* 2737 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 2745 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 2750 */     this.cmdResultStats.func_179672_a(this, type, amount);
/*      */   }
/*      */ 
/*      */   
/*      */   public CommandResultStats getCommandStats() {
/* 2755 */     return this.cmdResultStats;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_174817_o(Entity entityIn) {
/* 2760 */     this.cmdResultStats.func_179671_a(entityIn.getCommandStats());
/*      */   }
/*      */ 
/*      */   
/*      */   public NBTTagCompound getNBTTagCompound() {
/* 2765 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clientUpdateEntityNBT(NBTTagCompound compound) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactAt(EntityPlayer player, Vec3 targetVec3) {
/* 2780 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isImmuneToExplosions() {
/* 2785 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
/* 2790 */     if (entityIn instanceof EntityLivingBase)
/*      */     {
/* 2792 */       EnchantmentHelper.applyThornEnchantments((EntityLivingBase)entityIn, entityLivingBaseIn);
/*      */     }
/*      */     
/* 2795 */     EnchantmentHelper.applyArthropodEnchantments(entityLivingBaseIn, entityIn);
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\Entity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */