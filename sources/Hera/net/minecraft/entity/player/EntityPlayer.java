/*      */ package net.minecraft.entity.player;
/*      */ 
/*      */ import com.google.common.base.Charsets;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockBed;
/*      */ import net.minecraft.block.BlockDirectional;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.command.server.CommandBlockLogic;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EnumCreatureAttribute;
/*      */ import net.minecraft.entity.IEntityMultiPart;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.boss.EntityDragonPart;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.monster.EntityMob;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.passive.EntityPig;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.entity.projectile.EntityFishHook;
/*      */ import net.minecraft.event.ClickEvent;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerPlayer;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryEnderChest;
/*      */ import net.minecraft.item.EnumAction;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.FoodStats;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.LockCode;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class EntityPlayer
/*      */   extends EntityLivingBase
/*      */ {
/*   86 */   public InventoryPlayer inventory = new InventoryPlayer(this);
/*   87 */   private InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTraitor = false;
/*      */ 
/*      */   
/*      */   public Container inventoryContainer;
/*      */ 
/*      */   
/*      */   public Container openContainer;
/*      */ 
/*      */   
/*  100 */   protected FoodStats foodStats = new FoodStats();
/*      */ 
/*      */   
/*      */   protected int flyToggleTimer;
/*      */ 
/*      */   
/*      */   public float prevCameraYaw;
/*      */ 
/*      */   
/*      */   public float cameraYaw;
/*      */   
/*      */   public int xpCooldown;
/*      */   
/*      */   public double prevChasingPosX;
/*      */   
/*      */   public double prevChasingPosY;
/*      */   
/*      */   public double prevChasingPosZ;
/*      */   
/*      */   public double chasingPosX;
/*      */   
/*      */   public double chasingPosY;
/*      */   
/*      */   public double chasingPosZ;
/*      */   
/*      */   protected boolean sleeping;
/*      */   
/*      */   public BlockPos playerLocation;
/*      */   
/*      */   private int sleepTimer;
/*      */   
/*      */   public float renderOffsetX;
/*      */   
/*      */   public float renderOffsetY;
/*      */   
/*      */   public float renderOffsetZ;
/*      */   
/*      */   private BlockPos spawnChunk;
/*      */   
/*      */   private boolean spawnForced;
/*      */   
/*      */   private BlockPos startMinecartRidingCoordinate;
/*      */   
/*  143 */   public PlayerCapabilities capabilities = new PlayerCapabilities();
/*      */ 
/*      */ 
/*      */   
/*      */   public int experienceLevel;
/*      */ 
/*      */ 
/*      */   
/*      */   public int experienceTotal;
/*      */ 
/*      */ 
/*      */   
/*      */   public float experience;
/*      */ 
/*      */ 
/*      */   
/*      */   private int xpSeed;
/*      */ 
/*      */ 
/*      */   
/*      */   private ItemStack itemInUse;
/*      */ 
/*      */   
/*      */   private int itemInUseCount;
/*      */ 
/*      */   
/*  169 */   protected float speedOnGround = 0.1F;
/*  170 */   protected float speedInAir = 0.02F;
/*      */ 
/*      */   
/*      */   private int lastXPSound;
/*      */ 
/*      */   
/*      */   private final GameProfile gameProfile;
/*      */   
/*      */   private boolean hasReducedDebug = false;
/*      */   
/*      */   public EntityFishHook fishEntity;
/*      */ 
/*      */   
/*      */   public EntityPlayer(World worldIn, GameProfile gameProfileIn) {
/*  184 */     super(worldIn);
/*  185 */     this.entityUniqueID = getUUID(gameProfileIn);
/*  186 */     this.gameProfile = gameProfileIn;
/*  187 */     this.inventoryContainer = (Container)new ContainerPlayer(this.inventory, !worldIn.isRemote, this);
/*  188 */     this.openContainer = this.inventoryContainer;
/*  189 */     BlockPos blockpos = worldIn.getSpawnPoint();
/*  190 */     setLocationAndAngles(blockpos.getX() + 0.5D, (blockpos.getY() + 1), blockpos.getZ() + 0.5D, 0.0F, 0.0F);
/*  191 */     this.field_70741_aB = 180.0F;
/*  192 */     this.fireResistance = 20;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  197 */     super.applyEntityAttributes();
/*  198 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
/*  199 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  204 */     super.entityInit();
/*  205 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  206 */     this.dataWatcher.addObject(17, Float.valueOf(0.0F));
/*  207 */     this.dataWatcher.addObject(18, Integer.valueOf(0));
/*  208 */     this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getItemInUse() {
/*  216 */     return this.itemInUse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemInUseCount() {
/*  224 */     return this.itemInUseCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsingItem() {
/*  232 */     return (this.itemInUse != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemInUseDuration() {
/*  240 */     return isUsingItem() ? (this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount) : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopUsingItem() {
/*  245 */     if (this.itemInUse != null)
/*      */     {
/*  247 */       this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
/*      */     }
/*      */     
/*  250 */     clearItemInUse();
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearItemInUse() {
/*  255 */     this.itemInUse = null;
/*  256 */     this.itemInUseCount = 0;
/*      */     
/*  258 */     if (!this.worldObj.isRemote)
/*      */     {
/*  260 */       setEating(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlocking() {
/*  266 */     return (isUsingItem() && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.BLOCK);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  274 */     this.noClip = isSpectator();
/*      */     
/*  276 */     if (isSpectator())
/*      */     {
/*  278 */       this.onGround = false;
/*      */     }
/*      */     
/*  281 */     if (this.itemInUse != null) {
/*      */       
/*  283 */       ItemStack itemstack = this.inventory.getCurrentItem();
/*      */       
/*  285 */       if (itemstack == this.itemInUse) {
/*      */         
/*  287 */         if (this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0)
/*      */         {
/*  289 */           updateItemUse(itemstack, 5);
/*      */         }
/*      */         
/*  292 */         if (--this.itemInUseCount == 0 && !this.worldObj.isRemote)
/*      */         {
/*  294 */           onItemUseFinish();
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  299 */         clearItemInUse();
/*      */       } 
/*      */     } 
/*      */     
/*  303 */     if (this.xpCooldown > 0)
/*      */     {
/*  305 */       this.xpCooldown--;
/*      */     }
/*      */     
/*  308 */     if (isPlayerSleeping()) {
/*      */       
/*  310 */       this.sleepTimer++;
/*      */       
/*  312 */       if (this.sleepTimer > 100)
/*      */       {
/*  314 */         this.sleepTimer = 100;
/*      */       }
/*      */       
/*  317 */       if (!this.worldObj.isRemote)
/*      */       {
/*  319 */         if (!isInBed())
/*      */         {
/*  321 */           wakeUpPlayer(true, true, false);
/*      */         }
/*  323 */         else if (this.worldObj.isDaytime())
/*      */         {
/*  325 */           wakeUpPlayer(false, true, true);
/*      */         }
/*      */       
/*      */       }
/*  329 */     } else if (this.sleepTimer > 0) {
/*      */       
/*  331 */       this.sleepTimer++;
/*      */       
/*  333 */       if (this.sleepTimer >= 110)
/*      */       {
/*  335 */         this.sleepTimer = 0;
/*      */       }
/*      */     } 
/*      */     
/*  339 */     super.onUpdate();
/*      */     
/*  341 */     if (!this.worldObj.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
/*      */       
/*  343 */       closeScreen();
/*  344 */       this.openContainer = this.inventoryContainer;
/*      */     } 
/*      */     
/*  347 */     if (isBurning() && this.capabilities.disableDamage)
/*      */     {
/*  349 */       extinguish();
/*      */     }
/*      */     
/*  352 */     this.prevChasingPosX = this.chasingPosX;
/*  353 */     this.prevChasingPosY = this.chasingPosY;
/*  354 */     this.prevChasingPosZ = this.chasingPosZ;
/*  355 */     double d5 = this.posX - this.chasingPosX;
/*  356 */     double d0 = this.posY - this.chasingPosY;
/*  357 */     double d1 = this.posZ - this.chasingPosZ;
/*  358 */     double d2 = 10.0D;
/*      */     
/*  360 */     if (d5 > d2)
/*      */     {
/*  362 */       this.prevChasingPosX = this.chasingPosX = this.posX;
/*      */     }
/*      */     
/*  365 */     if (d1 > d2)
/*      */     {
/*  367 */       this.prevChasingPosZ = this.chasingPosZ = this.posZ;
/*      */     }
/*      */     
/*  370 */     if (d0 > d2)
/*      */     {
/*  372 */       this.prevChasingPosY = this.chasingPosY = this.posY;
/*      */     }
/*      */     
/*  375 */     if (d5 < -d2)
/*      */     {
/*  377 */       this.prevChasingPosX = this.chasingPosX = this.posX;
/*      */     }
/*      */     
/*  380 */     if (d1 < -d2)
/*      */     {
/*  382 */       this.prevChasingPosZ = this.chasingPosZ = this.posZ;
/*      */     }
/*      */     
/*  385 */     if (d0 < -d2)
/*      */     {
/*  387 */       this.prevChasingPosY = this.chasingPosY = this.posY;
/*      */     }
/*      */     
/*  390 */     this.chasingPosX += d5 * 0.25D;
/*  391 */     this.chasingPosZ += d1 * 0.25D;
/*  392 */     this.chasingPosY += d0 * 0.25D;
/*      */     
/*  394 */     if (this.ridingEntity == null)
/*      */     {
/*  396 */       this.startMinecartRidingCoordinate = null;
/*      */     }
/*      */     
/*  399 */     if (!this.worldObj.isRemote) {
/*      */       
/*  401 */       this.foodStats.onUpdate(this);
/*  402 */       triggerAchievement(StatList.minutesPlayedStat);
/*      */       
/*  404 */       if (isEntityAlive())
/*      */       {
/*  406 */         triggerAchievement(StatList.timeSinceDeathStat);
/*      */       }
/*      */     } 
/*      */     
/*  410 */     int i = 29999999;
/*  411 */     double d3 = MathHelper.clamp_double(this.posX, -2.9999999E7D, 2.9999999E7D);
/*  412 */     double d4 = MathHelper.clamp_double(this.posZ, -2.9999999E7D, 2.9999999E7D);
/*      */     
/*  414 */     if (d3 != this.posX || d4 != this.posZ)
/*      */     {
/*  416 */       setPosition(d3, this.posY, d4);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxInPortalTime() {
/*  425 */     return this.capabilities.disableDamage ? 0 : 80;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSwimSound() {
/*  430 */     return "game.player.swim";
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getSplashSound() {
/*  435 */     return "game.player.swim.splash";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPortalCooldown() {
/*  443 */     return 10;
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(String name, float volume, float pitch) {
/*  448 */     this.worldObj.playSoundToNearExcept(this, name, volume, pitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateItemUse(ItemStack itemStackIn, int p_71010_2_) {
/*  456 */     if (itemStackIn.getItemUseAction() == EnumAction.DRINK)
/*      */     {
/*  458 */       playSound("random.drink", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*      */     }
/*      */     
/*  461 */     if (itemStackIn.getItemUseAction() == EnumAction.EAT) {
/*      */       
/*  463 */       for (int i = 0; i < p_71010_2_; i++) {
/*      */         
/*  465 */         Vec3 vec3 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*  466 */         vec3 = vec3.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/*  467 */         vec3 = vec3.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/*  468 */         double d0 = -this.rand.nextFloat() * 0.6D - 0.3D;
/*  469 */         Vec3 vec31 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
/*  470 */         vec31 = vec31.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/*  471 */         vec31 = vec31.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/*  472 */         vec31 = vec31.addVector(this.posX, this.posY + getEyeHeight(), this.posZ);
/*      */         
/*  474 */         if (itemStackIn.getHasSubtypes()) {
/*      */           
/*  476 */           this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, new int[] { Item.getIdFromItem(itemStackIn.getItem()), itemStackIn.getMetadata() });
/*      */         }
/*      */         else {
/*      */           
/*  480 */           this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, new int[] { Item.getIdFromItem(itemStackIn.getItem()) });
/*      */         } 
/*      */       } 
/*      */       
/*  484 */       playSound("random.eat", 0.5F + 0.5F * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onItemUseFinish() {
/*  493 */     if (this.itemInUse != null) {
/*      */       
/*  495 */       updateItemUse(this.itemInUse, 16);
/*  496 */       int i = this.itemInUse.stackSize;
/*  497 */       ItemStack itemstack = this.itemInUse.onItemUseFinish(this.worldObj, this);
/*      */       
/*  499 */       if (itemstack != this.itemInUse || (itemstack != null && itemstack.stackSize != i)) {
/*      */         
/*  501 */         this.inventory.mainInventory[this.inventory.currentItem] = itemstack;
/*      */         
/*  503 */         if (itemstack.stackSize == 0)
/*      */         {
/*  505 */           this.inventory.mainInventory[this.inventory.currentItem] = null;
/*      */         }
/*      */       } 
/*      */       
/*  509 */       clearItemInUse();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/*  515 */     if (id == 9) {
/*      */       
/*  517 */       onItemUseFinish();
/*      */     }
/*  519 */     else if (id == 23) {
/*      */       
/*  521 */       this.hasReducedDebug = false;
/*      */     }
/*  523 */     else if (id == 22) {
/*      */       
/*  525 */       this.hasReducedDebug = true;
/*      */     }
/*      */     else {
/*      */       
/*  529 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/*  538 */     return !(getHealth() > 0.0F && !isPlayerSleeping());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeScreen() {
/*  546 */     this.openContainer = this.inventoryContainer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/*  554 */     if (!this.worldObj.isRemote && isSneaking()) {
/*      */       
/*  556 */       mountEntity(null);
/*  557 */       setSneaking(false);
/*      */     }
/*      */     else {
/*      */       
/*  561 */       double d0 = this.posX;
/*  562 */       double d1 = this.posY;
/*  563 */       double d2 = this.posZ;
/*  564 */       float f = this.rotationYaw;
/*  565 */       float f1 = this.rotationPitch;
/*  566 */       super.updateRidden();
/*  567 */       this.prevCameraYaw = this.cameraYaw;
/*  568 */       this.cameraYaw = 0.0F;
/*  569 */       addMountedMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);
/*      */       
/*  571 */       if (this.ridingEntity instanceof EntityPig) {
/*      */         
/*  573 */         this.rotationPitch = f1;
/*  574 */         this.rotationYaw = f;
/*  575 */         this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preparePlayerToSpawn() {
/*  586 */     setSize(0.6F, 1.8F);
/*  587 */     super.preparePlayerToSpawn();
/*  588 */     setHealth(getMaxHealth());
/*  589 */     this.deathTime = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateEntityActionState() {
/*  594 */     super.updateEntityActionState();
/*  595 */     updateArmSwingProgress();
/*  596 */     this.rotationYawHead = this.rotationYaw;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  605 */     if (this.flyToggleTimer > 0)
/*      */     {
/*  607 */       this.flyToggleTimer--;
/*      */     }
/*      */     
/*  610 */     if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.worldObj.getGameRules().getBoolean("naturalRegeneration")) {
/*      */       
/*  612 */       if (getHealth() < getMaxHealth() && this.ticksExisted % 20 == 0)
/*      */       {
/*  614 */         heal(1.0F);
/*      */       }
/*      */       
/*  617 */       if (this.foodStats.needFood() && this.ticksExisted % 10 == 0)
/*      */       {
/*  619 */         this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
/*      */       }
/*      */     } 
/*      */     
/*  623 */     this.inventory.decrementAnimations();
/*  624 */     this.prevCameraYaw = this.cameraYaw;
/*  625 */     super.onLivingUpdate();
/*  626 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*      */     
/*  628 */     if (!this.worldObj.isRemote)
/*      */     {
/*  630 */       iattributeinstance.setBaseValue(this.capabilities.getWalkSpeed());
/*      */     }
/*      */     
/*  633 */     this.jumpMovementFactor = this.speedInAir;
/*      */     
/*  635 */     if (isSprinting())
/*      */     {
/*  637 */       this.jumpMovementFactor = (float)(this.jumpMovementFactor + this.speedInAir * 0.3D);
/*      */     }
/*      */     
/*  640 */     setAIMoveSpeed((float)iattributeinstance.getAttributeValue());
/*  641 */     float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  642 */     float f1 = (float)(Math.atan(-this.motionY * 0.20000000298023224D) * 15.0D);
/*      */     
/*  644 */     if (f > 0.1F)
/*      */     {
/*  646 */       f = 0.1F;
/*      */     }
/*      */     
/*  649 */     if (!this.onGround || getHealth() <= 0.0F)
/*      */     {
/*  651 */       f = 0.0F;
/*      */     }
/*      */     
/*  654 */     if (this.onGround || getHealth() <= 0.0F)
/*      */     {
/*  656 */       f1 = 0.0F;
/*      */     }
/*      */     
/*  659 */     this.cameraYaw += (f - this.cameraYaw) * 0.4F;
/*  660 */     this.cameraPitch += (f1 - this.cameraPitch) * 0.8F;
/*      */     
/*  662 */     if (getHealth() > 0.0F && !isSpectator()) {
/*      */       
/*  664 */       AxisAlignedBB axisalignedbb = null;
/*      */       
/*  666 */       if (this.ridingEntity != null && !this.ridingEntity.isDead) {
/*      */         
/*  668 */         axisalignedbb = getEntityBoundingBox().union(this.ridingEntity.getEntityBoundingBox()).expand(1.0D, 0.0D, 1.0D);
/*      */       }
/*      */       else {
/*      */         
/*  672 */         axisalignedbb = getEntityBoundingBox().expand(1.0D, 0.5D, 1.0D);
/*      */       } 
/*      */       
/*  675 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, axisalignedbb);
/*      */       
/*  677 */       for (int i = 0; i < list.size(); i++) {
/*      */         
/*  679 */         Entity entity = list.get(i);
/*      */         
/*  681 */         if (!entity.isDead)
/*      */         {
/*  683 */           collideWithPlayer(entity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void collideWithPlayer(Entity p_71044_1_) {
/*  691 */     p_71044_1_.onCollideWithPlayer(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getScore() {
/*  696 */     return this.dataWatcher.getWatchableObjectInt(18);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScore(int p_85040_1_) {
/*  704 */     this.dataWatcher.updateObject(18, Integer.valueOf(p_85040_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addScore(int p_85039_1_) {
/*  712 */     int i = getScore();
/*  713 */     this.dataWatcher.updateObject(18, Integer.valueOf(i + p_85039_1_));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  721 */     super.onDeath(cause);
/*  722 */     setSize(0.2F, 0.2F);
/*  723 */     setPosition(this.posX, this.posY, this.posZ);
/*  724 */     this.motionY = 0.10000000149011612D;
/*      */     
/*  726 */     if (getName().equals("Notch"))
/*      */     {
/*  728 */       dropItem(new ItemStack(Items.apple, 1), true, false);
/*      */     }
/*      */     
/*  731 */     if (!this.worldObj.getGameRules().getBoolean("keepInventory"))
/*      */     {
/*  733 */       this.inventory.dropAllItems();
/*      */     }
/*      */     
/*  736 */     if (cause != null) {
/*      */       
/*  738 */       this.motionX = (-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
/*  739 */       this.motionZ = (-MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
/*      */     }
/*      */     else {
/*      */       
/*  743 */       this.motionX = this.motionZ = 0.0D;
/*      */     } 
/*      */     
/*  746 */     triggerAchievement(StatList.deathsStat);
/*  747 */     func_175145_a(StatList.timeSinceDeathStat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getHurtSound() {
/*  755 */     return "game.player.hurt";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDeathSound() {
/*  763 */     return "game.player.die";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToPlayerScore(Entity entityIn, int amount) {
/*  772 */     addScore(amount);
/*  773 */     Collection<ScoreObjective> collection = getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.totalKillCount);
/*      */     
/*  775 */     if (entityIn instanceof EntityPlayer) {
/*      */       
/*  777 */       triggerAchievement(StatList.playerKillsStat);
/*  778 */       collection.addAll(getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.playerKillCount));
/*  779 */       collection.addAll(func_175137_e(entityIn));
/*      */     }
/*      */     else {
/*      */       
/*  783 */       triggerAchievement(StatList.mobKillsStat);
/*      */     } 
/*      */     
/*  786 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/*  788 */       Score score = getWorldScoreboard().getValueFromObjective(getName(), scoreobjective);
/*  789 */       score.func_96648_a();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Collection<ScoreObjective> func_175137_e(Entity p_175137_1_) {
/*  795 */     ScorePlayerTeam scoreplayerteam = getWorldScoreboard().getPlayersTeam(getName());
/*      */     
/*  797 */     if (scoreplayerteam != null) {
/*      */       
/*  799 */       int i = scoreplayerteam.getChatFormat().getColorIndex();
/*      */       
/*  801 */       if (i >= 0 && i < IScoreObjectiveCriteria.field_178793_i.length)
/*      */       {
/*  803 */         for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178793_i[i])) {
/*      */           
/*  805 */           Score score = getWorldScoreboard().getValueFromObjective(p_175137_1_.getName(), scoreobjective);
/*  806 */           score.func_96648_a();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  811 */     ScorePlayerTeam scoreplayerteam1 = getWorldScoreboard().getPlayersTeam(p_175137_1_.getName());
/*      */     
/*  813 */     if (scoreplayerteam1 != null) {
/*      */       
/*  815 */       int j = scoreplayerteam1.getChatFormat().getColorIndex();
/*      */       
/*  817 */       if (j >= 0 && j < IScoreObjectiveCriteria.field_178792_h.length)
/*      */       {
/*  819 */         return getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178792_h[j]);
/*      */       }
/*      */     } 
/*      */     
/*  823 */     return Lists.newArrayList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem dropOneItem(boolean dropAll) {
/*  831 */     return dropItem(this.inventory.decrStackSize(this.inventory.currentItem, (dropAll && this.inventory.getCurrentItem() != null) ? (this.inventory.getCurrentItem()).stackSize : 1), false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem dropPlayerItemWithRandomChoice(ItemStack itemStackIn, boolean unused) {
/*  839 */     return dropItem(itemStackIn, false, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem dropItem(ItemStack droppedItem, boolean dropAround, boolean traceItem) {
/*  844 */     if (droppedItem == null)
/*      */     {
/*  846 */       return null;
/*      */     }
/*  848 */     if (droppedItem.stackSize == 0)
/*      */     {
/*  850 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  854 */     double d0 = this.posY - 0.30000001192092896D + getEyeHeight();
/*  855 */     EntityItem entityitem = new EntityItem(this.worldObj, this.posX, d0, this.posZ, droppedItem);
/*  856 */     entityitem.setPickupDelay(40);
/*      */     
/*  858 */     if (traceItem)
/*      */     {
/*  860 */       entityitem.setThrower(getName());
/*      */     }
/*      */     
/*  863 */     if (dropAround) {
/*      */       
/*  865 */       float f = this.rand.nextFloat() * 0.5F;
/*  866 */       float f1 = this.rand.nextFloat() * 3.1415927F * 2.0F;
/*  867 */       entityitem.motionX = (-MathHelper.sin(f1) * f);
/*  868 */       entityitem.motionZ = (MathHelper.cos(f1) * f);
/*  869 */       entityitem.motionY = 0.20000000298023224D;
/*      */     }
/*      */     else {
/*      */       
/*  873 */       float f2 = 0.3F;
/*  874 */       entityitem.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f2);
/*  875 */       entityitem.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f2);
/*  876 */       entityitem.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F) * f2 + 0.1F);
/*  877 */       float f3 = this.rand.nextFloat() * 3.1415927F * 2.0F;
/*  878 */       f2 = 0.02F * this.rand.nextFloat();
/*  879 */       entityitem.motionX += Math.cos(f3) * f2;
/*  880 */       entityitem.motionY += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
/*  881 */       entityitem.motionZ += Math.sin(f3) * f2;
/*      */     } 
/*      */     
/*  884 */     joinEntityItemWithWorld(entityitem);
/*      */     
/*  886 */     if (traceItem)
/*      */     {
/*  888 */       triggerAchievement(StatList.dropStat);
/*      */     }
/*      */     
/*  891 */     return entityitem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void joinEntityItemWithWorld(EntityItem itemIn) {
/*  900 */     this.worldObj.spawnEntityInWorld((Entity)itemIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getToolDigEfficiency(Block p_180471_1_) {
/*  908 */     float f = this.inventory.getStrVsBlock(p_180471_1_);
/*      */     
/*  910 */     if (f > 1.0F) {
/*      */       
/*  912 */       int i = EnchantmentHelper.getEfficiencyModifier(this);
/*  913 */       ItemStack itemstack = this.inventory.getCurrentItem();
/*      */       
/*  915 */       if (i > 0 && itemstack != null)
/*      */       {
/*  917 */         f += (i * i + 1);
/*      */       }
/*      */     } 
/*      */     
/*  921 */     if (isPotionActive(Potion.digSpeed))
/*      */     {
/*  923 */       f *= 1.0F + (getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
/*      */     }
/*      */     
/*  926 */     if (isPotionActive(Potion.digSlowdown)) {
/*      */       
/*  928 */       float f1 = 1.0F;
/*      */       
/*  930 */       switch (getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
/*      */         
/*      */         case 0:
/*  933 */           f1 = 0.3F;
/*      */           break;
/*      */         
/*      */         case 1:
/*  937 */           f1 = 0.09F;
/*      */           break;
/*      */         
/*      */         case 2:
/*  941 */           f1 = 0.0027F;
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  946 */           f1 = 8.1E-4F;
/*      */           break;
/*      */       } 
/*  949 */       f *= f1;
/*      */     } 
/*      */     
/*  952 */     if (isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this))
/*      */     {
/*  954 */       f /= 5.0F;
/*      */     }
/*      */     
/*  957 */     if (!this.onGround)
/*      */     {
/*  959 */       f /= 5.0F;
/*      */     }
/*      */     
/*  962 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(Block blockToHarvest) {
/*  970 */     return this.inventory.canHeldItemHarvest(blockToHarvest);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  978 */     super.readEntityFromNBT(tagCompund);
/*  979 */     this.entityUniqueID = getUUID(this.gameProfile);
/*  980 */     NBTTagList nbttaglist = tagCompund.getTagList("Inventory", 10);
/*  981 */     this.inventory.readFromNBT(nbttaglist);
/*  982 */     this.inventory.currentItem = tagCompund.getInteger("SelectedItemSlot");
/*  983 */     this.sleeping = tagCompund.getBoolean("Sleeping");
/*  984 */     this.sleepTimer = tagCompund.getShort("SleepTimer");
/*  985 */     this.experience = tagCompund.getFloat("XpP");
/*  986 */     this.experienceLevel = tagCompund.getInteger("XpLevel");
/*  987 */     this.experienceTotal = tagCompund.getInteger("XpTotal");
/*  988 */     this.xpSeed = tagCompund.getInteger("XpSeed");
/*      */     
/*  990 */     if (this.xpSeed == 0)
/*      */     {
/*  992 */       this.xpSeed = this.rand.nextInt();
/*      */     }
/*      */     
/*  995 */     setScore(tagCompund.getInteger("Score"));
/*      */     
/*  997 */     if (this.sleeping) {
/*      */       
/*  999 */       this.playerLocation = new BlockPos((Entity)this);
/* 1000 */       wakeUpPlayer(true, true, false);
/*      */     } 
/*      */     
/* 1003 */     if (tagCompund.hasKey("SpawnX", 99) && tagCompund.hasKey("SpawnY", 99) && tagCompund.hasKey("SpawnZ", 99)) {
/*      */       
/* 1005 */       this.spawnChunk = new BlockPos(tagCompund.getInteger("SpawnX"), tagCompund.getInteger("SpawnY"), tagCompund.getInteger("SpawnZ"));
/* 1006 */       this.spawnForced = tagCompund.getBoolean("SpawnForced");
/*      */     } 
/*      */     
/* 1009 */     this.foodStats.readNBT(tagCompund);
/* 1010 */     this.capabilities.readCapabilitiesFromNBT(tagCompund);
/*      */     
/* 1012 */     if (tagCompund.hasKey("EnderItems", 9)) {
/*      */       
/* 1014 */       NBTTagList nbttaglist1 = tagCompund.getTagList("EnderItems", 10);
/* 1015 */       this.theInventoryEnderChest.loadInventoryFromNBT(nbttaglist1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 1024 */     super.writeEntityToNBT(tagCompound);
/* 1025 */     tagCompound.setTag("Inventory", (NBTBase)this.inventory.writeToNBT(new NBTTagList()));
/* 1026 */     tagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
/* 1027 */     tagCompound.setBoolean("Sleeping", this.sleeping);
/* 1028 */     tagCompound.setShort("SleepTimer", (short)this.sleepTimer);
/* 1029 */     tagCompound.setFloat("XpP", this.experience);
/* 1030 */     tagCompound.setInteger("XpLevel", this.experienceLevel);
/* 1031 */     tagCompound.setInteger("XpTotal", this.experienceTotal);
/* 1032 */     tagCompound.setInteger("XpSeed", this.xpSeed);
/* 1033 */     tagCompound.setInteger("Score", getScore());
/*      */     
/* 1035 */     if (this.spawnChunk != null) {
/*      */       
/* 1037 */       tagCompound.setInteger("SpawnX", this.spawnChunk.getX());
/* 1038 */       tagCompound.setInteger("SpawnY", this.spawnChunk.getY());
/* 1039 */       tagCompound.setInteger("SpawnZ", this.spawnChunk.getZ());
/* 1040 */       tagCompound.setBoolean("SpawnForced", this.spawnForced);
/*      */     } 
/*      */     
/* 1043 */     this.foodStats.writeNBT(tagCompound);
/* 1044 */     this.capabilities.writeCapabilitiesToNBT(tagCompound);
/* 1045 */     tagCompound.setTag("EnderItems", (NBTBase)this.theInventoryEnderChest.saveInventoryToNBT());
/* 1046 */     ItemStack itemstack = this.inventory.getCurrentItem();
/*      */     
/* 1048 */     if (itemstack != null && itemstack.getItem() != null)
/*      */     {
/* 1050 */       tagCompound.setTag("SelectedItem", (NBTBase)itemstack.writeToNBT(new NBTTagCompound()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 1059 */     if (isEntityInvulnerable(source))
/*      */     {
/* 1061 */       return false;
/*      */     }
/* 1063 */     if (this.capabilities.disableDamage && !source.canHarmInCreative())
/*      */     {
/* 1065 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1069 */     this.entityAge = 0;
/*      */     
/* 1071 */     if (getHealth() <= 0.0F)
/*      */     {
/* 1073 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1077 */     if (isPlayerSleeping() && !this.worldObj.isRemote)
/*      */     {
/* 1079 */       wakeUpPlayer(true, true, false);
/*      */     }
/*      */     
/* 1082 */     if (source.isDifficultyScaled()) {
/*      */       
/* 1084 */       if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
/*      */       {
/* 1086 */         amount = 0.0F;
/*      */       }
/*      */       
/* 1089 */       if (this.worldObj.getDifficulty() == EnumDifficulty.EASY)
/*      */       {
/* 1091 */         amount = amount / 2.0F + 1.0F;
/*      */       }
/*      */       
/* 1094 */       if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*      */       {
/* 1096 */         amount = amount * 3.0F / 2.0F;
/*      */       }
/*      */     } 
/*      */     
/* 1100 */     if (amount == 0.0F)
/*      */     {
/* 1102 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1106 */     Entity entity = source.getEntity();
/*      */     
/* 1108 */     if (entity instanceof EntityArrow && ((EntityArrow)entity).shootingEntity != null)
/*      */     {
/* 1110 */       entity = ((EntityArrow)entity).shootingEntity;
/*      */     }
/*      */     
/* 1113 */     return super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackPlayer(EntityPlayer other) {
/* 1121 */     Team team = getTeam();
/* 1122 */     Team team1 = other.getTeam();
/* 1123 */     return (team == null) ? true : (!team.isSameTeam(team1) ? true : team.getAllowFriendlyFire());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void damageArmor(float p_70675_1_) {
/* 1128 */     this.inventory.damageArmor(p_70675_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalArmorValue() {
/* 1136 */     return this.inventory.getTotalArmorValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getArmorVisibility() {
/* 1145 */     int i = 0; byte b; int j;
/*      */     ItemStack[] arrayOfItemStack;
/* 1147 */     for (j = (arrayOfItemStack = this.inventory.armorInventory).length, b = 0; b < j; ) { ItemStack itemstack = arrayOfItemStack[b];
/*      */       
/* 1149 */       if (itemstack != null)
/*      */       {
/* 1151 */         i++;
/*      */       }
/*      */       b++; }
/*      */     
/* 1155 */     return i / this.inventory.armorInventory.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 1164 */     if (!isEntityInvulnerable(damageSrc)) {
/*      */       
/* 1166 */       if (!damageSrc.isUnblockable() && isBlocking() && damageAmount > 0.0F)
/*      */       {
/* 1168 */         damageAmount = (1.0F + damageAmount) * 0.5F;
/*      */       }
/*      */       
/* 1171 */       damageAmount = applyArmorCalculations(damageSrc, damageAmount);
/* 1172 */       damageAmount = applyPotionDamageCalculations(damageSrc, damageAmount);
/* 1173 */       float f = damageAmount;
/* 1174 */       damageAmount = Math.max(damageAmount - getAbsorptionAmount(), 0.0F);
/* 1175 */       setAbsorptionAmount(getAbsorptionAmount() - f - damageAmount);
/*      */       
/* 1177 */       if (damageAmount != 0.0F) {
/*      */         
/* 1179 */         addExhaustion(damageSrc.getHungerDamage());
/* 1180 */         float f1 = getHealth();
/* 1181 */         setHealth(getHealth() - damageAmount);
/* 1182 */         getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
/*      */         
/* 1184 */         if (damageAmount < 3.4028235E37F)
/*      */         {
/* 1186 */           addStat(StatList.damageTakenStat, Math.round(damageAmount * 10.0F));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openEditSign(TileEntitySign signTile) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayVillagerTradeGui(IMerchant villager) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIChest(IInventory chestInventory) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGui(IInteractionObject guiOwner) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIBook(ItemStack bookStack) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactWith(Entity p_70998_1_) {
/* 1228 */     if (isSpectator()) {
/*      */       
/* 1230 */       if (p_70998_1_ instanceof IInventory)
/*      */       {
/* 1232 */         displayGUIChest((IInventory)p_70998_1_);
/*      */       }
/*      */       
/* 1235 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1239 */     ItemStack itemstack = getCurrentEquippedItem();
/* 1240 */     ItemStack itemstack1 = (itemstack != null) ? itemstack.copy() : null;
/*      */     
/* 1242 */     if (!p_70998_1_.interactFirst(this)) {
/*      */       
/* 1244 */       if (itemstack != null && p_70998_1_ instanceof EntityLivingBase) {
/*      */         
/* 1246 */         if (this.capabilities.isCreativeMode)
/*      */         {
/* 1248 */           itemstack = itemstack1;
/*      */         }
/*      */         
/* 1251 */         if (itemstack.interactWithEntity(this, (EntityLivingBase)p_70998_1_)) {
/*      */           
/* 1253 */           if (itemstack.stackSize <= 0 && !this.capabilities.isCreativeMode)
/*      */           {
/* 1255 */             destroyCurrentEquippedItem();
/*      */           }
/*      */           
/* 1258 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/* 1262 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1266 */     if (itemstack != null && itemstack == getCurrentEquippedItem())
/*      */     {
/* 1268 */       if (itemstack.stackSize <= 0 && !this.capabilities.isCreativeMode) {
/*      */         
/* 1270 */         destroyCurrentEquippedItem();
/*      */       }
/* 1272 */       else if (itemstack.stackSize < itemstack1.stackSize && this.capabilities.isCreativeMode) {
/*      */         
/* 1274 */         itemstack.stackSize = itemstack1.stackSize;
/*      */       } 
/*      */     }
/*      */     
/* 1278 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getCurrentEquippedItem() {
/* 1288 */     return this.inventory.getCurrentItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroyCurrentEquippedItem() {
/* 1296 */     this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/* 1304 */     return -0.35D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
/* 1313 */     if (targetEntity.canAttackWithItem())
/*      */     {
/* 1315 */       if (!targetEntity.hitByEntity((Entity)this)) {
/*      */         
/* 1317 */         float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
/* 1318 */         int i = 0;
/* 1319 */         float f1 = 0.0F;
/*      */         
/* 1321 */         if (targetEntity instanceof EntityLivingBase) {
/*      */           
/* 1323 */           f1 = EnchantmentHelper.func_152377_a(getHeldItem(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
/*      */         }
/*      */         else {
/*      */           
/* 1327 */           f1 = EnchantmentHelper.func_152377_a(getHeldItem(), EnumCreatureAttribute.UNDEFINED);
/*      */         } 
/*      */         
/* 1330 */         i += EnchantmentHelper.getKnockbackModifier(this);
/*      */         
/* 1332 */         if (isSprinting())
/*      */         {
/* 1334 */           i++;
/*      */         }
/*      */         
/* 1337 */         if (f > 0.0F || f1 > 0.0F) {
/*      */           
/* 1339 */           boolean flag = (this.fallDistance > 0.0F && !this.onGround && !isOnLadder() && !isInWater() && !isPotionActive(Potion.blindness) && this.ridingEntity == null && targetEntity instanceof EntityLivingBase);
/*      */           
/* 1341 */           if (flag && f > 0.0F)
/*      */           {
/* 1343 */             f *= 1.5F;
/*      */           }
/*      */           
/* 1346 */           f += f1;
/* 1347 */           boolean flag1 = false;
/* 1348 */           int j = EnchantmentHelper.getFireAspectModifier(this);
/*      */           
/* 1350 */           if (targetEntity instanceof EntityLivingBase && j > 0 && !targetEntity.isBurning()) {
/*      */             
/* 1352 */             flag1 = true;
/* 1353 */             targetEntity.setFire(1);
/*      */           } 
/*      */           
/* 1356 */           double d0 = targetEntity.motionX;
/* 1357 */           double d1 = targetEntity.motionY;
/* 1358 */           double d2 = targetEntity.motionZ;
/* 1359 */           boolean flag2 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(this), f);
/*      */           
/* 1361 */           if (flag2) {
/*      */             EntityLivingBase entityLivingBase;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1371 */             if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
/*      */               
/* 1373 */               ((EntityPlayerMP)targetEntity).playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity(targetEntity));
/* 1374 */               targetEntity.velocityChanged = false;
/* 1375 */               targetEntity.motionX = d0;
/* 1376 */               targetEntity.motionY = d1;
/* 1377 */               targetEntity.motionZ = d2;
/*      */             } 
/*      */             
/* 1380 */             if (flag)
/*      */             {
/* 1382 */               onCriticalHit(targetEntity);
/*      */             }
/*      */             
/* 1385 */             if (f1 > 0.0F)
/*      */             {
/* 1387 */               onEnchantmentCritical(targetEntity);
/*      */             }
/*      */             
/* 1390 */             if (f >= 18.0F)
/*      */             {
/* 1392 */               triggerAchievement((StatBase)AchievementList.overkill);
/*      */             }
/*      */             
/* 1395 */             setLastAttacker(targetEntity);
/*      */             
/* 1397 */             if (targetEntity instanceof EntityLivingBase)
/*      */             {
/* 1399 */               EnchantmentHelper.applyThornEnchantments((EntityLivingBase)targetEntity, (Entity)this);
/*      */             }
/*      */             
/* 1402 */             EnchantmentHelper.applyArthropodEnchantments(this, targetEntity);
/* 1403 */             ItemStack itemstack = getCurrentEquippedItem();
/* 1404 */             Entity entity = targetEntity;
/*      */             
/* 1406 */             if (targetEntity instanceof EntityDragonPart) {
/*      */               
/* 1408 */               IEntityMultiPart ientitymultipart = ((EntityDragonPart)targetEntity).entityDragonObj;
/*      */               
/* 1410 */               if (ientitymultipart instanceof EntityLivingBase)
/*      */               {
/* 1412 */                 entityLivingBase = (EntityLivingBase)ientitymultipart;
/*      */               }
/*      */             } 
/*      */             
/* 1416 */             if (itemstack != null && entityLivingBase instanceof EntityLivingBase) {
/*      */               
/* 1418 */               itemstack.hitEntity(entityLivingBase, this);
/*      */               
/* 1420 */               if (itemstack.stackSize <= 0)
/*      */               {
/* 1422 */                 destroyCurrentEquippedItem();
/*      */               }
/*      */             } 
/*      */             
/* 1426 */             if (targetEntity instanceof EntityLivingBase) {
/*      */               
/* 1428 */               addStat(StatList.damageDealtStat, Math.round(f * 10.0F));
/*      */               
/* 1430 */               if (j > 0)
/*      */               {
/* 1432 */                 targetEntity.setFire(j * 4);
/*      */               }
/*      */             } 
/*      */             
/* 1436 */             addExhaustion(0.3F);
/*      */           }
/* 1438 */           else if (flag1) {
/*      */             
/* 1440 */             targetEntity.extinguish();
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
/*      */   public void onCriticalHit(Entity entityHit) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEnchantmentCritical(Entity entityHit) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void respawnPlayer() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDead() {
/* 1467 */     super.setDead();
/* 1468 */     this.inventoryContainer.onContainerClosed(this);
/*      */     
/* 1470 */     if (this.openContainer != null)
/*      */     {
/* 1472 */       this.openContainer.onContainerClosed(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInsideOpaqueBlock() {
/* 1481 */     return (!this.sleeping && super.isEntityInsideOpaqueBlock());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUser() {
/* 1489 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 1497 */     return this.gameProfile;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumStatus trySleep(BlockPos bedLocation) {
/* 1502 */     if (!this.worldObj.isRemote) {
/*      */       
/* 1504 */       if (isPlayerSleeping() || !isEntityAlive())
/*      */       {
/* 1506 */         return EnumStatus.OTHER_PROBLEM;
/*      */       }
/*      */       
/* 1509 */       if (!this.worldObj.provider.isSurfaceWorld())
/*      */       {
/* 1511 */         return EnumStatus.NOT_POSSIBLE_HERE;
/*      */       }
/*      */       
/* 1514 */       if (this.worldObj.isDaytime())
/*      */       {
/* 1516 */         return EnumStatus.NOT_POSSIBLE_NOW;
/*      */       }
/*      */       
/* 1519 */       if (Math.abs(this.posX - bedLocation.getX()) > 3.0D || Math.abs(this.posY - bedLocation.getY()) > 2.0D || Math.abs(this.posZ - bedLocation.getZ()) > 3.0D)
/*      */       {
/* 1521 */         return EnumStatus.TOO_FAR_AWAY;
/*      */       }
/*      */       
/* 1524 */       double d0 = 8.0D;
/* 1525 */       double d1 = 5.0D;
/* 1526 */       List<EntityMob> list = this.worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(bedLocation.getX() - d0, bedLocation.getY() - d1, bedLocation.getZ() - d0, bedLocation.getX() + d0, bedLocation.getY() + d1, bedLocation.getZ() + d0));
/*      */       
/* 1528 */       if (!list.isEmpty())
/*      */       {
/* 1530 */         return EnumStatus.NOT_SAFE;
/*      */       }
/*      */     } 
/*      */     
/* 1534 */     if (isRiding())
/*      */     {
/* 1536 */       mountEntity(null);
/*      */     }
/*      */     
/* 1539 */     setSize(0.2F, 0.2F);
/*      */     
/* 1541 */     if (this.worldObj.isBlockLoaded(bedLocation)) {
/*      */       
/* 1543 */       EnumFacing enumfacing = (EnumFacing)this.worldObj.getBlockState(bedLocation).getValue((IProperty)BlockDirectional.FACING);
/* 1544 */       float f = 0.5F;
/* 1545 */       float f1 = 0.5F;
/*      */       
/* 1547 */       switch (enumfacing) {
/*      */         
/*      */         case SOUTH:
/* 1550 */           f1 = 0.9F;
/*      */           break;
/*      */         
/*      */         case NORTH:
/* 1554 */           f1 = 0.1F;
/*      */           break;
/*      */         
/*      */         case WEST:
/* 1558 */           f = 0.1F;
/*      */           break;
/*      */         
/*      */         case EAST:
/* 1562 */           f = 0.9F;
/*      */           break;
/*      */       } 
/* 1565 */       func_175139_a(enumfacing);
/* 1566 */       setPosition((bedLocation.getX() + f), (bedLocation.getY() + 0.6875F), (bedLocation.getZ() + f1));
/*      */     }
/*      */     else {
/*      */       
/* 1570 */       setPosition((bedLocation.getX() + 0.5F), (bedLocation.getY() + 0.6875F), (bedLocation.getZ() + 0.5F));
/*      */     } 
/*      */     
/* 1573 */     this.sleeping = true;
/* 1574 */     this.sleepTimer = 0;
/* 1575 */     this.playerLocation = bedLocation;
/* 1576 */     this.motionX = this.motionZ = this.motionY = 0.0D;
/*      */     
/* 1578 */     if (!this.worldObj.isRemote)
/*      */     {
/* 1580 */       this.worldObj.updateAllPlayersSleepingFlag();
/*      */     }
/*      */     
/* 1583 */     return EnumStatus.OK;
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_175139_a(EnumFacing p_175139_1_) {
/* 1588 */     this.renderOffsetX = 0.0F;
/* 1589 */     this.renderOffsetZ = 0.0F;
/*      */     
/* 1591 */     switch (p_175139_1_) {
/*      */       
/*      */       case SOUTH:
/* 1594 */         this.renderOffsetZ = -1.8F;
/*      */         break;
/*      */       
/*      */       case NORTH:
/* 1598 */         this.renderOffsetZ = 1.8F;
/*      */         break;
/*      */       
/*      */       case WEST:
/* 1602 */         this.renderOffsetX = 1.8F;
/*      */         break;
/*      */       
/*      */       case EAST:
/* 1606 */         this.renderOffsetX = -1.8F;
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void wakeUpPlayer(boolean p_70999_1_, boolean updateWorldFlag, boolean setSpawn) {
/* 1615 */     setSize(0.6F, 1.8F);
/* 1616 */     IBlockState iblockstate = this.worldObj.getBlockState(this.playerLocation);
/*      */     
/* 1618 */     if (this.playerLocation != null && iblockstate.getBlock() == Blocks.bed) {
/*      */       
/* 1620 */       this.worldObj.setBlockState(this.playerLocation, iblockstate.withProperty((IProperty)BlockBed.OCCUPIED, Boolean.valueOf(false)), 4);
/* 1621 */       BlockPos blockpos = BlockBed.getSafeExitLocation(this.worldObj, this.playerLocation, 0);
/*      */       
/* 1623 */       if (blockpos == null)
/*      */       {
/* 1625 */         blockpos = this.playerLocation.up();
/*      */       }
/*      */       
/* 1628 */       setPosition((blockpos.getX() + 0.5F), (blockpos.getY() + 0.1F), (blockpos.getZ() + 0.5F));
/*      */     } 
/*      */     
/* 1631 */     this.sleeping = false;
/*      */     
/* 1633 */     if (!this.worldObj.isRemote && updateWorldFlag)
/*      */     {
/* 1635 */       this.worldObj.updateAllPlayersSleepingFlag();
/*      */     }
/*      */     
/* 1638 */     this.sleepTimer = p_70999_1_ ? 0 : 100;
/*      */     
/* 1640 */     if (setSpawn)
/*      */     {
/* 1642 */       setSpawnPoint(this.playerLocation, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isInBed() {
/* 1648 */     return (this.worldObj.getBlockState(this.playerLocation).getBlock() == Blocks.bed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BlockPos getBedSpawnLocation(World worldIn, BlockPos bedLocation, boolean forceSpawn) {
/* 1656 */     Block block = worldIn.getBlockState(bedLocation).getBlock();
/*      */     
/* 1658 */     if (block != Blocks.bed) {
/*      */       
/* 1660 */       if (!forceSpawn)
/*      */       {
/* 1662 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1666 */       boolean flag = block.func_181623_g();
/* 1667 */       boolean flag1 = worldIn.getBlockState(bedLocation.up()).getBlock().func_181623_g();
/* 1668 */       return (flag && flag1) ? bedLocation : null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1673 */     return BlockBed.getSafeExitLocation(worldIn, bedLocation, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBedOrientationInDegrees() {
/* 1682 */     if (this.playerLocation != null) {
/*      */       
/* 1684 */       EnumFacing enumfacing = (EnumFacing)this.worldObj.getBlockState(this.playerLocation).getValue((IProperty)BlockDirectional.FACING);
/*      */       
/* 1686 */       switch (enumfacing) {
/*      */         
/*      */         case SOUTH:
/* 1689 */           return 90.0F;
/*      */         
/*      */         case NORTH:
/* 1692 */           return 270.0F;
/*      */         
/*      */         case WEST:
/* 1695 */           return 0.0F;
/*      */         
/*      */         case EAST:
/* 1698 */           return 180.0F;
/*      */       } 
/*      */     
/*      */     } 
/* 1702 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerSleeping() {
/* 1710 */     return this.sleeping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerFullyAsleep() {
/* 1718 */     return (this.sleeping && this.sleepTimer >= 100);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSleepTimer() {
/* 1723 */     return this.sleepTimer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatComponentMessage(IChatComponent chatComponent) {}
/*      */ 
/*      */   
/*      */   public BlockPos getBedLocation() {
/* 1732 */     return this.spawnChunk;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpawnForced() {
/* 1737 */     return this.spawnForced;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpawnPoint(BlockPos pos, boolean forced) {
/* 1742 */     if (pos != null) {
/*      */       
/* 1744 */       this.spawnChunk = pos;
/* 1745 */       this.spawnForced = forced;
/*      */     }
/*      */     else {
/*      */       
/* 1749 */       this.spawnChunk = null;
/* 1750 */       this.spawnForced = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void triggerAchievement(StatBase achievementIn) {
/* 1759 */     addStat(achievementIn, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat, int amount) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_175145_a(StatBase p_175145_1_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void jump() {
/* 1778 */     super.jump();
/* 1779 */     triggerAchievement(StatList.jumpStat);
/*      */     
/* 1781 */     if (isSprinting()) {
/*      */       
/* 1783 */       addExhaustion(0.8F);
/*      */     }
/*      */     else {
/*      */       
/* 1787 */       addExhaustion(0.2F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntityWithHeading(float strafe, float forward) {
/* 1796 */     double d0 = this.posX;
/* 1797 */     double d1 = this.posY;
/* 1798 */     double d2 = this.posZ;
/*      */     
/* 1800 */     if (this.capabilities.isFlying && this.ridingEntity == null) {
/*      */       
/* 1802 */       double d3 = this.motionY;
/* 1803 */       float f = this.jumpMovementFactor;
/* 1804 */       this.jumpMovementFactor = this.capabilities.getFlySpeed() * (isSprinting() ? 2 : true);
/* 1805 */       super.moveEntityWithHeading(strafe, forward);
/* 1806 */       this.motionY = d3 * 0.6D;
/* 1807 */       this.jumpMovementFactor = f;
/*      */     }
/*      */     else {
/*      */       
/* 1811 */       super.moveEntityWithHeading(strafe, forward);
/*      */     } 
/*      */     
/* 1814 */     addMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAIMoveSpeed() {
/* 1822 */     return (float)getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMovementStat(double p_71000_1_, double p_71000_3_, double p_71000_5_) {
/* 1830 */     if (this.ridingEntity == null)
/*      */     {
/* 1832 */       if (isInsideOfMaterial(Material.water)) {
/*      */         
/* 1834 */         int i = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1836 */         if (i > 0)
/*      */         {
/* 1838 */           addStat(StatList.distanceDoveStat, i);
/* 1839 */           addExhaustion(0.015F * i * 0.01F);
/*      */         }
/*      */       
/* 1842 */       } else if (isInWater()) {
/*      */         
/* 1844 */         int j = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1846 */         if (j > 0)
/*      */         {
/* 1848 */           addStat(StatList.distanceSwumStat, j);
/* 1849 */           addExhaustion(0.015F * j * 0.01F);
/*      */         }
/*      */       
/* 1852 */       } else if (isOnLadder()) {
/*      */         
/* 1854 */         if (p_71000_3_ > 0.0D)
/*      */         {
/* 1856 */           addStat(StatList.distanceClimbedStat, (int)Math.round(p_71000_3_ * 100.0D));
/*      */         }
/*      */       }
/* 1859 */       else if (this.onGround) {
/*      */         
/* 1861 */         int k = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1863 */         if (k > 0) {
/*      */           
/* 1865 */           addStat(StatList.distanceWalkedStat, k);
/*      */           
/* 1867 */           if (isSprinting())
/*      */           {
/* 1869 */             addStat(StatList.distanceSprintedStat, k);
/* 1870 */             addExhaustion(0.099999994F * k * 0.01F);
/*      */           }
/*      */           else
/*      */           {
/* 1874 */             if (isSneaking())
/*      */             {
/* 1876 */               addStat(StatList.distanceCrouchedStat, k);
/*      */             }
/*      */             
/* 1879 */             addExhaustion(0.01F * k * 0.01F);
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/* 1885 */         int l = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1887 */         if (l > 25)
/*      */         {
/* 1889 */           addStat(StatList.distanceFlownStat, l);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMountedMovementStat(double p_71015_1_, double p_71015_3_, double p_71015_5_) {
/* 1900 */     if (this.ridingEntity != null) {
/*      */       
/* 1902 */       int i = Math.round(MathHelper.sqrt_double(p_71015_1_ * p_71015_1_ + p_71015_3_ * p_71015_3_ + p_71015_5_ * p_71015_5_) * 100.0F);
/*      */       
/* 1904 */       if (i > 0)
/*      */       {
/* 1906 */         if (this.ridingEntity instanceof net.minecraft.entity.item.EntityMinecart) {
/*      */           
/* 1908 */           addStat(StatList.distanceByMinecartStat, i);
/*      */           
/* 1910 */           if (this.startMinecartRidingCoordinate == null)
/*      */           {
/* 1912 */             this.startMinecartRidingCoordinate = new BlockPos((Entity)this);
/*      */           }
/* 1914 */           else if (this.startMinecartRidingCoordinate.distanceSq(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) >= 1000000.0D)
/*      */           {
/* 1916 */             triggerAchievement((StatBase)AchievementList.onARail);
/*      */           }
/*      */         
/* 1919 */         } else if (this.ridingEntity instanceof net.minecraft.entity.item.EntityBoat) {
/*      */           
/* 1921 */           addStat(StatList.distanceByBoatStat, i);
/*      */         }
/* 1923 */         else if (this.ridingEntity instanceof EntityPig) {
/*      */           
/* 1925 */           addStat(StatList.distanceByPigStat, i);
/*      */         }
/* 1927 */         else if (this.ridingEntity instanceof EntityHorse) {
/*      */           
/* 1929 */           addStat(StatList.distanceByHorseStat, i);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/* 1937 */     if (!this.capabilities.allowFlying) {
/*      */       
/* 1939 */       if (distance >= 2.0F)
/*      */       {
/* 1941 */         addStat(StatList.distanceFallenStat, (int)Math.round(distance * 100.0D));
/*      */       }
/*      */       
/* 1944 */       super.fall(distance, damageMultiplier);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHeight() {
/* 1953 */     if (!isSpectator())
/*      */     {
/* 1955 */       super.resetHeight();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getFallSoundString(int damageValue) {
/* 1961 */     return (damageValue > 4) ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillEntity(EntityLivingBase entityLivingIn) {
/* 1969 */     if (entityLivingIn instanceof net.minecraft.entity.monster.IMob)
/*      */     {
/* 1971 */       triggerAchievement((StatBase)AchievementList.killEnemy);
/*      */     }
/*      */     
/* 1974 */     EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(EntityList.getEntityID((Entity)entityLivingIn)));
/*      */     
/* 1976 */     if (entitylist$entityegginfo != null)
/*      */     {
/* 1978 */       triggerAchievement(entitylist$entityegginfo.field_151512_d);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInWeb() {
/* 1987 */     if (!this.capabilities.isFlying)
/*      */     {
/* 1989 */       super.setInWeb();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getCurrentArmor(int slotIn) {
/* 1995 */     return this.inventory.armorItemInSlot(slotIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperience(int amount) {
/* 2003 */     addScore(amount);
/* 2004 */     int i = Integer.MAX_VALUE - this.experienceTotal;
/*      */     
/* 2006 */     if (amount > i)
/*      */     {
/* 2008 */       amount = i;
/*      */     }
/*      */     
/* 2011 */     this.experience += amount / xpBarCap();
/*      */     
/* 2013 */     for (this.experienceTotal += amount; this.experience >= 1.0F; this.experience /= xpBarCap()) {
/*      */       
/* 2015 */       this.experience = (this.experience - 1.0F) * xpBarCap();
/* 2016 */       addExperienceLevel(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getXPSeed() {
/* 2022 */     return this.xpSeed;
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeExperienceLevel(int levels) {
/* 2027 */     this.experienceLevel -= levels;
/*      */     
/* 2029 */     if (this.experienceLevel < 0) {
/*      */       
/* 2031 */       this.experienceLevel = 0;
/* 2032 */       this.experience = 0.0F;
/* 2033 */       this.experienceTotal = 0;
/*      */     } 
/*      */     
/* 2036 */     this.xpSeed = this.rand.nextInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperienceLevel(int levels) {
/* 2044 */     this.experienceLevel += levels;
/*      */     
/* 2046 */     if (this.experienceLevel < 0) {
/*      */       
/* 2048 */       this.experienceLevel = 0;
/* 2049 */       this.experience = 0.0F;
/* 2050 */       this.experienceTotal = 0;
/*      */     } 
/*      */     
/* 2053 */     if (levels > 0 && this.experienceLevel % 5 == 0 && this.lastXPSound < this.ticksExisted - 100.0F) {
/*      */       
/* 2055 */       float f = (this.experienceLevel > 30) ? 1.0F : (this.experienceLevel / 30.0F);
/* 2056 */       this.worldObj.playSoundAtEntity((Entity)this, "random.levelup", f * 0.75F, 1.0F);
/* 2057 */       this.lastXPSound = this.ticksExisted;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int xpBarCap() {
/* 2067 */     return (this.experienceLevel >= 30) ? (112 + (this.experienceLevel - 30) * 9) : ((this.experienceLevel >= 15) ? (37 + (this.experienceLevel - 15) * 5) : (7 + this.experienceLevel * 2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExhaustion(float p_71020_1_) {
/* 2075 */     if (!this.capabilities.disableDamage)
/*      */     {
/* 2077 */       if (!this.worldObj.isRemote)
/*      */       {
/* 2079 */         this.foodStats.addExhaustion(p_71020_1_);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FoodStats getFoodStats() {
/* 2089 */     return this.foodStats;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canEat(boolean ignoreHunger) {
/* 2094 */     return ((ignoreHunger || this.foodStats.needFood()) && !this.capabilities.disableDamage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldHeal() {
/* 2102 */     return (getHealth() > 0.0F && getHealth() < getMaxHealth());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemInUse(ItemStack stack, int duration) {
/* 2110 */     if (stack != this.itemInUse) {
/*      */       
/* 2112 */       this.itemInUse = stack;
/* 2113 */       this.itemInUseCount = duration;
/*      */       
/* 2115 */       if (!this.worldObj.isRemote)
/*      */       {
/* 2117 */         setEating(true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAllowEdit() {
/* 2124 */     return this.capabilities.allowEdit;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPlayerEdit(BlockPos p_175151_1_, EnumFacing p_175151_2_, ItemStack p_175151_3_) {
/* 2129 */     if (this.capabilities.allowEdit)
/*      */     {
/* 2131 */       return true;
/*      */     }
/* 2133 */     if (p_175151_3_ == null)
/*      */     {
/* 2135 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2139 */     BlockPos blockpos = p_175151_1_.offset(p_175151_2_.getOpposite());
/* 2140 */     Block block = this.worldObj.getBlockState(blockpos).getBlock();
/* 2141 */     return !(!p_175151_3_.canPlaceOn(block) && !p_175151_3_.canEditBlocks());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/* 2150 */     if (this.worldObj.getGameRules().getBoolean("keepInventory"))
/*      */     {
/* 2152 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2156 */     int i = this.experienceLevel * 7;
/* 2157 */     return (i > 100) ? 100 : i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPlayer() {
/* 2166 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 2171 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clonePlayer(EntityPlayer oldPlayer, boolean respawnFromEnd) {
/* 2180 */     if (respawnFromEnd) {
/*      */       
/* 2182 */       this.inventory.copyInventory(oldPlayer.inventory);
/* 2183 */       setHealth(oldPlayer.getHealth());
/* 2184 */       this.foodStats = oldPlayer.foodStats;
/* 2185 */       this.experienceLevel = oldPlayer.experienceLevel;
/* 2186 */       this.experienceTotal = oldPlayer.experienceTotal;
/* 2187 */       this.experience = oldPlayer.experience;
/* 2188 */       setScore(oldPlayer.getScore());
/* 2189 */       this.field_181016_an = oldPlayer.field_181016_an;
/* 2190 */       this.field_181017_ao = oldPlayer.field_181017_ao;
/* 2191 */       this.field_181018_ap = oldPlayer.field_181018_ap;
/*      */     }
/* 2193 */     else if (this.worldObj.getGameRules().getBoolean("keepInventory")) {
/*      */       
/* 2195 */       this.inventory.copyInventory(oldPlayer.inventory);
/* 2196 */       this.experienceLevel = oldPlayer.experienceLevel;
/* 2197 */       this.experienceTotal = oldPlayer.experienceTotal;
/* 2198 */       this.experience = oldPlayer.experience;
/* 2199 */       setScore(oldPlayer.getScore());
/*      */     } 
/*      */     
/* 2202 */     this.xpSeed = oldPlayer.xpSeed;
/* 2203 */     this.theInventoryEnderChest = oldPlayer.theInventoryEnderChest;
/* 2204 */     getDataWatcher().updateObject(10, Byte.valueOf(oldPlayer.getDataWatcher().getWatchableObjectByte(10)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/* 2213 */     return !this.capabilities.isFlying;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendPlayerAbilities() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType gameType) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 2246 */     return this.gameProfile.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InventoryEnderChest getInventoryEnderChest() {
/* 2254 */     return this.theInventoryEnderChest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getEquipmentInSlot(int slotIn) {
/* 2262 */     return (slotIn == 0) ? this.inventory.getCurrentItem() : this.inventory.armorInventory[slotIn - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getHeldItem() {
/* 2270 */     return this.inventory.getCurrentItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/* 2278 */     this.inventory.armorInventory[slotIn] = stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvisibleToPlayer(EntityPlayer player) {
/* 2288 */     if (!isInvisible())
/*      */     {
/* 2290 */       return false;
/*      */     }
/* 2292 */     if (player.isSpectator())
/*      */     {
/* 2294 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2298 */     Team team = getTeam();
/* 2299 */     return !(team != null && player != null && player.getTeam() == team && team.getSeeFriendlyInvisiblesEnabled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] getInventory() {
/* 2313 */     return this.inventory.armorInventory;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPushedByWater() {
/* 2318 */     return !this.capabilities.isFlying;
/*      */   }
/*      */ 
/*      */   
/*      */   public Scoreboard getWorldScoreboard() {
/* 2323 */     return this.worldObj.getScoreboard();
/*      */   }
/*      */ 
/*      */   
/*      */   public Team getTeam() {
/* 2328 */     return (Team)getWorldScoreboard().getPlayersTeam(getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 2336 */     ChatComponentText chatComponentText = new ChatComponentText(ScorePlayerTeam.formatPlayerName(getTeam(), getName()));
/* 2337 */     chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + getName() + " "));
/* 2338 */     chatComponentText.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 2339 */     chatComponentText.getChatStyle().setInsertion(getName());
/*      */     
/* 2341 */     return (IChatComponent)chatComponentText;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/* 2346 */     float f = 1.62F;
/*      */     
/* 2348 */     if (isPlayerSleeping())
/*      */     {
/* 2350 */       f = 0.2F;
/*      */     }
/*      */     
/* 2353 */     if (isSneaking())
/*      */     {
/* 2355 */       f -= 0.08F;
/*      */     }
/*      */     
/* 2358 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAbsorptionAmount(float amount) {
/* 2363 */     if (amount < 0.0F)
/*      */     {
/* 2365 */       amount = 0.0F;
/*      */     }
/*      */     
/* 2368 */     getDataWatcher().updateObject(17, Float.valueOf(amount));
/*      */   }
/*      */ 
/*      */   
/*      */   public float getAbsorptionAmount() {
/* 2373 */     return getDataWatcher().getWatchableObjectFloat(17);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UUID getUUID(GameProfile profile) {
/* 2381 */     UUID uuid = profile.getId();
/*      */     
/* 2383 */     if (uuid == null)
/*      */     {
/* 2385 */       uuid = getOfflineUUID(profile.getName());
/*      */     }
/*      */     
/* 2388 */     return uuid;
/*      */   }
/*      */ 
/*      */   
/*      */   public static UUID getOfflineUUID(String username) {
/* 2393 */     return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canOpen(LockCode code) {
/* 2401 */     if (code.isEmpty())
/*      */     {
/* 2403 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2407 */     ItemStack itemstack = getCurrentEquippedItem();
/* 2408 */     return (itemstack != null && itemstack.hasDisplayName()) ? itemstack.getDisplayName().equals(code.getLock()) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWearing(EnumPlayerModelParts p_175148_1_) {
/* 2414 */     return ((getDataWatcher().getWatchableObjectByte(10) & p_175148_1_.getPartMask()) == p_175148_1_.getPartMask());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 2422 */     return (MinecraftServer.getServer()).worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 2427 */     if (inventorySlot >= 0 && inventorySlot < this.inventory.mainInventory.length) {
/*      */       
/* 2429 */       this.inventory.setInventorySlotContents(inventorySlot, itemStackIn);
/* 2430 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2434 */     int i = inventorySlot - 100;
/*      */     
/* 2436 */     if (i >= 0 && i < this.inventory.armorInventory.length) {
/*      */       
/* 2438 */       int k = i + 1;
/*      */       
/* 2440 */       if (itemStackIn != null && itemStackIn.getItem() != null)
/*      */       {
/* 2442 */         if (itemStackIn.getItem() instanceof net.minecraft.item.ItemArmor) {
/*      */           
/* 2444 */           if (EntityLiving.getArmorPosition(itemStackIn) != k)
/*      */           {
/* 2446 */             return false;
/*      */           }
/*      */         }
/* 2449 */         else if (k != 4 || (itemStackIn.getItem() != Items.skull && !(itemStackIn.getItem() instanceof net.minecraft.item.ItemBlock))) {
/*      */           
/* 2451 */           return false;
/*      */         } 
/*      */       }
/*      */       
/* 2455 */       this.inventory.setInventorySlotContents(i + this.inventory.mainInventory.length, itemStackIn);
/* 2456 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2460 */     int j = inventorySlot - 200;
/*      */     
/* 2462 */     if (j >= 0 && j < this.theInventoryEnderChest.getSizeInventory()) {
/*      */       
/* 2464 */       this.theInventoryEnderChest.setInventorySlotContents(j, itemStackIn);
/* 2465 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2469 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasReducedDebug() {
/* 2480 */     return this.hasReducedDebug;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setReducedDebug(boolean reducedDebug) {
/* 2485 */     this.hasReducedDebug = reducedDebug;
/*      */   }
/*      */   
/*      */   public enum EnumChatVisibility
/*      */   {
/* 2490 */     FULL(0, "options.chat.visibility.full"),
/* 2491 */     SYSTEM(1, "options.chat.visibility.system"),
/* 2492 */     HIDDEN(2, "options.chat.visibility.hidden");
/*      */     
/* 2494 */     private static final EnumChatVisibility[] ID_LOOKUP = new EnumChatVisibility[(values()).length];
/*      */     
/*      */     private final int chatVisibility;
/*      */     private final String resourceKey;
/*      */     
/*      */     EnumChatVisibility(int id, String resourceKey) {
/*      */       this.chatVisibility = id;
/*      */       this.resourceKey = resourceKey;
/*      */     }
/*      */     
/*      */     public int getChatVisibility() {
/*      */       return this.chatVisibility;
/*      */     }
/*      */     
/*      */     public static EnumChatVisibility getEnumChatVisibility(int id) {
/*      */       return ID_LOOKUP[id % ID_LOOKUP.length];
/*      */     }
/*      */     
/*      */     public String getResourceKey() {
/*      */       return this.resourceKey;
/*      */     }
/*      */     
/*      */     static {
/*      */       byte b;
/*      */       int i;
/*      */       EnumChatVisibility[] arrayOfEnumChatVisibility;
/* 2520 */       for (i = (arrayOfEnumChatVisibility = values()).length, b = 0; b < i; ) { EnumChatVisibility entityplayer$enumchatvisibility = arrayOfEnumChatVisibility[b];
/*      */         
/* 2522 */         ID_LOOKUP[entityplayer$enumchatvisibility.chatVisibility] = entityplayer$enumchatvisibility;
/*      */         b++; }
/*      */     
/*      */     } }
/*      */   
/*      */   public void setTraitor(boolean isTraitor) {
/* 2528 */     this.isTraitor = isTraitor;
/*      */   }
/*      */   
/*      */   public boolean isTraitor() {
/* 2532 */     return this.isTraitor;
/*      */   }
/*      */   
/*      */   public abstract boolean isSpectator();
/*      */   
/* 2537 */   public enum EnumStatus { OK,
/* 2538 */     NOT_POSSIBLE_HERE,
/* 2539 */     NOT_POSSIBLE_NOW,
/* 2540 */     TOO_FAR_AWAY,
/* 2541 */     OTHER_PROBLEM,
/* 2542 */     NOT_SAFE; }
/*      */ 
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\player\EntityPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */