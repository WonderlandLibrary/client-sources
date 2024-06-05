package net.minecraft.entity.player;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList$EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer$1;
import net.minecraft.entity.player.EntityPlayer$EnumStatus;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent$Action;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings$GameType;

public abstract class EntityPlayer extends EntityLivingBase {
   public InventoryPlayer inventory = new InventoryPlayer(this);
   private InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();
   public Container inventoryContainer;
   public Container openContainer;
   protected FoodStats foodStats = new FoodStats();
   protected int flyToggleTimer;
   public float prevCameraYaw;
   public float cameraYaw;
   public int xpCooldown;
   public double prevChasingPosX;
   public double prevChasingPosY;
   public double prevChasingPosZ;
   public double chasingPosX;
   public double chasingPosY;
   public double chasingPosZ;
   protected boolean sleeping;
   public BlockPos playerLocation;
   private int sleepTimer;
   public float renderOffsetX;
   public float renderOffsetY;
   public float renderOffsetZ;
   private BlockPos spawnChunk;
   private boolean spawnForced;
   private BlockPos startMinecartRidingCoordinate;
   public PlayerAbilities abilities = new PlayerAbilities();
   public int experienceLevel;
   public int experienceTotal;
   public float experience;
   private int xpSeed;
   private ItemStack itemInUse;
   private int itemInUseCount;
   protected float speedOnGround = 0.1F;
   protected float speedInAir = 0.02F;
   private int lastXPSound;
   protected GameProfile gameProfile;
   private boolean hasReducedDebug = false;
   public EntityFishHook fishEntity;

   public EntityPlayer(World var1, GameProfile var2) {
      super(var1);
      this.setEntityUniqueID(getUUID(var2));
      this.gameProfile = var2;
      this.inventoryContainer = new ContainerPlayer(this.inventory, !var1.isRemote, this);
      this.openContainer = this.inventoryContainer;
      BlockPos var3 = var1.getSpawnPoint();
      this.setLocationAndAngles((double)var3.getX() + 0.5D, (double)(var3.getY() + 1), (double)var3.getZ() + 0.5D, 0.0F, 0.0F);
      this.fireResistance = 20;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(16, Byte.valueOf((byte)0));
      this.I.b(17, Float.valueOf(0.0F));
      this.I.b(18, Integer.valueOf(0));
      this.I.b(10, Byte.valueOf((byte)0));
   }

   public ItemStack getItemInUse() {
      return this.itemInUse;
   }

   public int getItemInUseCount() {
      return this.itemInUseCount;
   }

   public boolean isUsingItem() {
      return this.itemInUse != null;
   }

   public int getItemInUseDuration() {
      return this.isUsingItem()?this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount:0;
   }

   public void stopUsingItem() {
      if(this.itemInUse != null) {
         this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
      }

      this.clearItemInUse();
   }

   public void clearItemInUse() {
      this.itemInUse = null;
      this.itemInUseCount = 0;
      if(!this.worldObj.isRemote) {
         this.setEating(false);
      }

   }

   public boolean isBlocking() {
      return this.isUsingItem() && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.BLOCK;
   }

   public void onUpdate() {
      this.noClip = this.isSpectator();
      if(this.isSpectator()) {
         this.onGround = false;
      }

      if(this.itemInUse != null) {
         ItemStack var1 = this.inventory.getCurrentItem();
         if(var1 == this.itemInUse) {
            if(this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0) {
               this.updateItemUse(var1, 5);
            }

            if(--this.itemInUseCount == 0 && !this.worldObj.isRemote) {
               this.onItemUseFinish();
            }
         } else {
            this.clearItemInUse();
         }
      }

      if(this.xpCooldown > 0) {
         --this.xpCooldown;
      }

      if(this.isPlayerSleeping()) {
         ++this.sleepTimer;
         if(this.sleepTimer > 100) {
            this.sleepTimer = 100;
         }

         if(!this.worldObj.isRemote) {
            if(!this.isInBed()) {
               this.wakeUpPlayer(true, true, false);
            } else if(this.worldObj.isDaytime()) {
               this.wakeUpPlayer(false, true, true);
            }
         }
      } else if(this.sleepTimer > 0) {
         ++this.sleepTimer;
         if(this.sleepTimer >= 110) {
            this.sleepTimer = 0;
         }
      }

      super.onUpdate();
      if(!this.worldObj.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
         this.closeScreen();
         this.openContainer = this.inventoryContainer;
      }

      if(this.isBurning() && this.abilities.isDisabledDamage()) {
         this.extinguish();
      }

      this.prevChasingPosX = this.chasingPosX;
      this.prevChasingPosY = this.chasingPosY;
      this.prevChasingPosZ = this.chasingPosZ;
      double var14 = this.posX - this.chasingPosX;
      double var3 = this.posY - this.chasingPosY;
      double var5 = this.posZ - this.chasingPosZ;
      double var7 = 10.0D;
      if(var14 > var7) {
         this.prevChasingPosX = this.chasingPosX = this.posX;
      }

      if(var5 > var7) {
         this.prevChasingPosZ = this.chasingPosZ = this.posZ;
      }

      if(var3 > var7) {
         this.prevChasingPosY = this.chasingPosY = this.posY;
      }

      if(var14 < -var7) {
         this.prevChasingPosX = this.chasingPosX = this.posX;
      }

      if(var5 < -var7) {
         this.prevChasingPosZ = this.chasingPosZ = this.posZ;
      }

      if(var3 < -var7) {
         this.prevChasingPosY = this.chasingPosY = this.posY;
      }

      this.chasingPosX += var14 * 0.25D;
      this.chasingPosZ += var5 * 0.25D;
      this.chasingPosY += var3 * 0.25D;
      if(this.ridingEntity == null) {
         this.startMinecartRidingCoordinate = null;
      }

      if(!this.worldObj.isRemote) {
         this.foodStats.onUpdate(this);
         this.triggerAchievement(StatList.minutesPlayedStat);
         if(this.isEntityAlive()) {
            this.triggerAchievement(StatList.timeSinceDeathStat);
         }
      }

      int var9 = 29999999;
      double var10 = MathHelper.clamp_double(this.posX, -2.9999999E7D, 2.9999999E7D);
      double var12 = MathHelper.clamp_double(this.posZ, -2.9999999E7D, 2.9999999E7D);
      if(var10 != this.posX || var12 != this.posZ) {
         this.setPosition(var10, this.posY, var12);
      }

   }

   public int getMaxInPortalTime() {
      return this.abilities.isDisabledDamage()?0:80;
   }

   protected String getSwimSound() {
      return "game.player.swim";
   }

   protected String getSplashSound() {
      return "game.player.swim.splash";
   }

   public int getPortalCoolDown() {
      return 10;
   }

   public void playSound(String var1, float var2, float var3) {
      this.worldObj.playSoundToNearExcept(this, var1, var2, var3);
   }

   protected void updateItemUse(ItemStack var1, int var2) {
      if(var1.getItemUseAction() == EnumAction.DRINK) {
         this.playSound("random.drink", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      if(var1.getItemUseAction() == EnumAction.EAT) {
         for(int var3 = 0; var3 < var2; ++var3) {
            Vec3 var4 = new Vec3(((double)this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            var4 = var4.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
            var4 = var4.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
            double var5 = (double)(-this.rand.nextFloat()) * 0.6D - 0.3D;
            Vec3 var7 = new Vec3(((double)this.rand.nextFloat() - 0.5D) * 0.3D, var5, 0.6D);
            var7 = var7.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
            var7 = var7.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
            var7 = var7.addVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
            if(var1.getHasSubtypes()) {
               this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, var7.xCoord, var7.yCoord, var7.zCoord, var4.xCoord, var4.yCoord + 0.05D, var4.zCoord, new int[]{Item.b(var1.getItem()), var1.getMetadata()});
            } else {
               this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, var7.xCoord, var7.yCoord, var7.zCoord, var4.xCoord, var4.yCoord + 0.05D, var4.zCoord, new int[]{Item.b(var1.getItem())});
            }
         }

         this.playSound("random.eat", 0.5F + 0.5F * (float)this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      }

   }

   protected void onItemUseFinish() {
      if(this.itemInUse != null) {
         this.updateItemUse(this.itemInUse, 16);
         int var1 = this.itemInUse.stackSize;
         ItemStack var2 = this.itemInUse.onItemUseFinish(this.worldObj, this);
         if(var2 != this.itemInUse || var2.stackSize != var1) {
            this.inventory.mainInventory[this.inventory.currentItem] = var2;
            if(var2.stackSize == 0) {
               this.inventory.mainInventory[this.inventory.currentItem] = null;
            }
         }

         this.clearItemInUse();
      }

   }

   public void handleStatusUpdate(byte var1) {
      if(var1 == 9) {
         this.onItemUseFinish();
      } else if(var1 == 23) {
         this.hasReducedDebug = false;
      } else if(var1 == 22) {
         this.hasReducedDebug = true;
      } else {
         super.handleStatusUpdate(var1);
      }

   }

   protected boolean isMovementBlocked() {
      return this.getHealth() <= 0.0F || this.isPlayerSleeping();
   }

   protected void closeScreen() {
      this.openContainer = this.inventoryContainer;
   }

   public void updateRidden() {
      if(!this.worldObj.isRemote && this.isSneaking()) {
         this.mountEntity((Entity)null);
         this.setSneaking(false);
      } else {
         double var1 = this.posX;
         double var3 = this.posY;
         double var5 = this.posZ;
         float var7 = this.rotationYaw;
         float var8 = this.rotationPitch;
         super.updateRidden();
         this.prevCameraYaw = this.cameraYaw;
         this.cameraYaw = 0.0F;
         this.addMountedMovementStat(this.posX - var1, this.posY - var3, this.posZ - var5);
         if(this.ridingEntity instanceof EntityPig) {
            this.rotationPitch = var8;
            this.rotationYaw = var7;
            this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
         }
      }

   }

   public void preparePlayerToSpawn() {
      this.setSize(0.6F, 1.8F);
      super.preparePlayerToSpawn();
      this.setHealth(this.getMaxHealth());
      this.deathTime = 0;
   }

   protected void updateEntityActionState() {
      super.updateEntityActionState();
      this.updateArmSwingProgress();
      this.rotationYawHead = this.rotationYaw;
   }

   public void onLivingUpdate() {
      if(this.flyToggleTimer > 0) {
         --this.flyToggleTimer;
      }

      if(this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.worldObj.getGameRules().getBoolean("naturalRegeneration")) {
         if(this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 == 0) {
            this.heal(1.0F);
         }

         if(this.foodStats.needFood() && this.ticksExisted % 10 == 0) {
            this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
         }
      }

      this.inventory.decrementAnimations();
      this.prevCameraYaw = this.cameraYaw;
      super.onLivingUpdate();
      IAttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
      if(!this.worldObj.isRemote) {
         var1.setBaseValue((double)this.abilities.getWalkSpeed());
      }

      this.jumpMovementFactor = this.speedInAir;
      if(this.isSprinting()) {
         this.jumpMovementFactor = (float)((double)this.jumpMovementFactor + (double)this.speedInAir * 0.3D);
      }

      this.setAIMoveSpeed((float)var1.getAttributeValue());
      float var2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
      float var3 = (float)(Math.atan(-this.motionY * 0.20000000298023224D) * 15.0D);
      if(var2 > 0.1F) {
         var2 = 0.1F;
      }

      if(!this.onGround || this.getHealth() <= 0.0F) {
         var2 = 0.0F;
      }

      if(this.onGround || this.getHealth() <= 0.0F) {
         var3 = 0.0F;
      }

      this.cameraYaw += (var2 - this.cameraYaw) * 0.4F;
      this.cameraPitch += (var3 - this.cameraPitch) * 0.8F;
      if(this.getHealth() > 0.0F && !this.isSpectator()) {
         AxisAlignedBB var4 = null;
         if(this.ridingEntity != null && !this.ridingEntity.isDead) {
            var4 = this.getEntityBoundingBox().union(this.ridingEntity.getEntityBoundingBox()).expand(1.0D, 0.0D, 1.0D);
         } else {
            var4 = this.getEntityBoundingBox().expand(1.0D, 0.5D, 1.0D);
         }

         for(Entity var7 : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, var4)) {
            if(!var7.isDead) {
               this.collideWithPlayer(var7);
            }
         }
      }

   }

   private void collideWithPlayer(Entity var1) {
      var1.onCollideWithPlayer(this);
   }

   public int getScore() {
      return this.I.c(18);
   }

   public void setScore(int var1) {
      this.I.a(18, Integer.valueOf(var1));
   }

   public void addScore(int var1) {
      int var2 = this.getScore();
      this.I.a(18, Integer.valueOf(var2 + var1));
   }

   public void onDeath(DamageSource var1) {
      super.onDeath(var1);
      this.setSize(0.2F, 0.2F);
      this.setPosition(this.posX, this.posY, this.posZ);
      this.motionY = 0.10000000149011612D;
      if(this.getName().equals("Notch")) {
         this.a(new ItemStack(Items.apple, 1), true, false);
      }

      if(!this.worldObj.getGameRules().getBoolean("keepInventory")) {
         this.inventory.dropAllItems();
      }

      this.motionX = (double)(-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
      this.motionZ = (double)(-MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
      this.triggerAchievement(StatList.deathsStat);
      this.func_175145_a(StatList.timeSinceDeathStat);
   }

   protected String getHurtSound() {
      return "game.player.hurt";
   }

   protected String getDeathSound() {
      return "game.player.die";
   }

   public void addToPlayerScore(Entity var1, int var2) {
      this.addScore(var2);
      Collection var3 = this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.totalKillCount);
      if(var1 instanceof EntityPlayer) {
         this.triggerAchievement(StatList.playerKillsStat);
         var3.addAll(this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.playerKillCount));
         var3.addAll(this.func_175137_e(var1));
      } else {
         this.triggerAchievement(StatList.mobKillsStat);
      }

      for(ScoreObjective var5 : var3) {
         Score var6 = this.getWorldScoreboard().getValueFromObjective(this.getName(), var5);
         var6.func_96648_a();
      }

   }

   private Collection func_175137_e(Entity var1) {
      ScorePlayerTeam var2 = this.getWorldScoreboard().getPlayersTeam(this.getName());
      int var3 = var2.getChatFormat().getColorIndex();
      if(var3 < IScoreObjectiveCriteria.field_178793_i.length) {
         for(ScoreObjective var5 : this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178793_i[var3])) {
            Score var6 = this.getWorldScoreboard().getValueFromObjective(var1.getName(), var5);
            var6.func_96648_a();
         }
      }

      ScorePlayerTeam var7 = this.getWorldScoreboard().getPlayersTeam(var1.getName());
      int var8 = var7.getChatFormat().getColorIndex();
      return (Collection)(var8 < IScoreObjectiveCriteria.field_178792_h.length?this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178792_h[var8]):Lists.newArrayList());
   }

   public void dropOneItem(boolean var1) {
      this.a(this.inventory.decrStackSize(this.inventory.currentItem, this.inventory.getCurrentItem() != null?this.inventory.getCurrentItem().stackSize:1), false, true);
   }

   public EntityItem dropPlayerItemWithRandomChoice(ItemStack var1, boolean var2) {
      return this.a(var1, false, false);
   }

   public EntityItem a(ItemStack var1, boolean var2, boolean var3) {
      return null;
   }

   protected void joinEntityItemWithWorld(EntityItem var1) {
      this.worldObj.spawnEntityInWorld(var1);
   }

   public float getToolDigEfficiency(Block var1) {
      float var2 = this.inventory.getStrVsBlock(var1);
      if(var2 > 1.0F) {
         int var3 = EnchantmentHelper.getEfficiencyModifier(this);
         ItemStack var4 = this.inventory.getCurrentItem();
         var2 += (float)(var3 * var3 + 1);
      }

      if(this.isPotionActive(Potion.digSpeed)) {
         var2 *= 1.0F + (float)(this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
      }

      if(this.isPotionActive(Potion.digSlowdown)) {
         float var5 = 1.0F;
         switch(this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
         case 0:
            var5 = 0.3F;
            break;
         case 1:
            var5 = 0.09F;
            break;
         case 2:
            var5 = 0.0027F;
            break;
         case 3:
         default:
            var5 = 8.1E-4F;
         }

         var2 *= var5;
      }

      if(this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
         var2 /= 5.0F;
      }

      if(!this.onGround) {
         var2 /= 5.0F;
      }

      return var2;
   }

   public boolean canHarvestBlock(Block var1) {
      return this.inventory.canHeldItemHarvest(var1);
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.setEntityUniqueID(getUUID(this.gameProfile));
      NBTTagList var2 = var1.getTagList("Inventory", 10);
      this.inventory.readFromNBT(var2);
      this.inventory.currentItem = var1.getInteger("SelectedItemSlot");
      this.sleeping = var1.getBoolean("Sleeping");
      this.sleepTimer = var1.getShort("SleepTimer");
      this.experience = var1.getFloat("XpP");
      this.experienceLevel = var1.getInteger("XpLevel");
      this.experienceTotal = var1.getInteger("XpTotal");
      this.xpSeed = var1.getInteger("XpSeed");
      if(this.xpSeed == 0) {
         this.xpSeed = this.rand.nextInt();
      }

      this.setScore(var1.getInteger("Score"));
      if(this.sleeping) {
         this.playerLocation = new BlockPos(this);
         this.wakeUpPlayer(true, true, false);
      }

      if(var1.hasKey("SpawnX", 99) && var1.hasKey("SpawnY", 99) && var1.hasKey("SpawnZ", 99)) {
         this.spawnChunk = new BlockPos(var1.getInteger("SpawnX"), var1.getInteger("SpawnY"), var1.getInteger("SpawnZ"));
         this.spawnForced = var1.getBoolean("SpawnForced");
      }

      this.foodStats.readNBT(var1);
      this.abilities.readCapabilitiesFromNBT(var1);
      if(var1.hasKey("EnderItems", 9)) {
         NBTTagList var3 = var1.getTagList("EnderItems", 10);
         this.theInventoryEnderChest.loadInventoryFromNBT(var3);
      }

   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
      var1.setInteger("SelectedItemSlot", this.inventory.currentItem);
      var1.setBoolean("Sleeping", this.sleeping);
      var1.setShort("SleepTimer", (short)this.sleepTimer);
      var1.setFloat("XpP", this.experience);
      var1.setInteger("XpLevel", this.experienceLevel);
      var1.setInteger("XpTotal", this.experienceTotal);
      var1.setInteger("XpSeed", this.xpSeed);
      var1.setInteger("Score", this.getScore());
      if(this.spawnChunk != null) {
         var1.setInteger("SpawnX", this.spawnChunk.getX());
         var1.setInteger("SpawnY", this.spawnChunk.getY());
         var1.setInteger("SpawnZ", this.spawnChunk.getZ());
         var1.setBoolean("SpawnForced", this.spawnForced);
      }

      this.foodStats.writeNBT(var1);
      this.abilities.writeCapabilitiesToNBT(var1);
      var1.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
      ItemStack var2 = this.inventory.getCurrentItem();
      if(var2.getItem() != null) {
         var1.setTag("SelectedItem", var2.writeToNBT(new NBTTagCompound()));
      }

   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(this.isEntityInvulnerable(var1)) {
         return false;
      } else if(this.abilities.isDisabledDamage() && !var1.canHarmInCreative()) {
         return false;
      } else {
         this.entityAge = 0;
         if(this.getHealth() <= 0.0F) {
            return false;
         } else {
            if(this.isPlayerSleeping() && !this.worldObj.isRemote) {
               this.wakeUpPlayer(true, true, false);
            }

            if(var1.isDifficultyScaled()) {
               if(this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
                  var2 = 0.0F;
               }

               if(this.worldObj.getDifficulty() == EnumDifficulty.EASY) {
                  var2 = var2 / 2.0F + 1.0F;
               }

               if(this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                  var2 = var2 * 3.0F / 2.0F;
               }
            }

            if(var2 == 0.0F) {
               return false;
            } else {
               Entity var3 = var1.getEntity();
               if(var3 instanceof EntityArrow && ((EntityArrow)var3).shootingEntity != null) {
                  var3 = ((EntityArrow)var3).shootingEntity;
               }

               return super.attackEntityFrom(var1, var2);
            }
         }
      }
   }

   public boolean canAttackPlayer(EntityPlayer var1) {
      Team var2 = this.getTeam();
      Team var3 = var1.getTeam();
      return !var2.isSameTeam(var3) || var2.getAllowFriendlyFire();
   }

   protected void damageArmor(float var1) {
      this.inventory.damageArmor(var1);
   }

   public int getTotalArmorValue() {
      return this.inventory.getTotalArmorValue();
   }

   public float getArmorVisibility() {
      int var1 = 0;

      for(ItemStack var5 : this.inventory.armorInventory) {
         ++var1;
      }

      return (float)var1 / (float)this.inventory.armorInventory.length;
   }

   protected void damageEntity(DamageSource var1, float var2) {
      if(!this.isEntityInvulnerable(var1)) {
         if(!var1.isUnblockable() && this.isBlocking() && var2 > 0.0F) {
            var2 = (1.0F + var2) * 0.5F;
         }

         var2 = this.applyArmorCalculations(var1, var2);
         var2 = this.applyPotionDamageCalculations(var1, var2);
         float var3 = var2;
         var2 = Math.max(var2 - this.getAbsorptionAmount(), 0.0F);
         this.setAbsorptionAmount(this.getAbsorptionAmount() - (var3 - var2));
         if(var2 != 0.0F) {
            this.addExhaustion(var1.getHungerDamage());
            float var4 = this.getHealth();
            this.setHealth(this.getHealth() - var2);
            this.getCombatTracker().trackDamage(var1, var4, var2);
            if(var2 < 3.4028235E37F) {
               this.addStat(StatList.damageTakenStat, Math.round(var2 * 10.0F));
            }
         }
      }

   }

   public void openEditSign(TileEntitySign var1) {
   }

   public void openEditCommandBlock(CommandBlockLogic var1) {
   }

   public void displayVillagerTradeGui(IMerchant var1) {
   }

   public void displayGUIChest(IInventory var1) {
   }

   public void displayGUIHorse(EntityHorse var1, IInventory var2) {
   }

   public void displayGui(IInteractionObject var1) {
   }

   public void displayGUIBook(ItemStack var1) {
   }

   public boolean interactWith(Entity var1) {
      if(this.isSpectator()) {
         if(var1 instanceof IInventory) {
            this.displayGUIChest((IInventory)var1);
         }

         return false;
      } else {
         ItemStack var2 = this.getCurrentEquippedItem();
         ItemStack var3 = var2.copy();
         if(!var1.interactFirst(this)) {
            if(var1 instanceof EntityLivingBase) {
               if(this.abilities.isCreative()) {
                  var2 = var3;
               }

               if(var2.interactWithEntity(this, (EntityLivingBase)var1)) {
                  if(var2.stackSize <= 0 && !this.abilities.isCreative()) {
                     this.destroyCurrentEquippedItem();
                  }

                  return true;
               }
            }

            return false;
         } else {
            if(var2 == this.getCurrentEquippedItem()) {
               if(var2.stackSize <= 0 && !this.abilities.isCreative()) {
                  this.destroyCurrentEquippedItem();
               } else if(var2.stackSize < var3.stackSize && this.abilities.isCreative()) {
                  var2.stackSize = var3.stackSize;
               }
            }

            return true;
         }
      }
   }

   public ItemStack getCurrentEquippedItem() {
      return this.inventory.getCurrentItem();
   }

   public void destroyCurrentEquippedItem() {
      this.inventory.setInventorySlotContents(this.inventory.currentItem, (ItemStack)null);
   }

   public double getYOffset() {
      return -0.35D;
   }

   public void attackTargetEntityWithCurrentItem(Entity var1) {
      if(var1.canAttackWithItem() && !var1.hitByEntity(this)) {
         float var2 = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
         int var3 = 0;
         float var4 = 0.0F;
         if(var1 instanceof EntityLivingBase) {
            var4 = EnchantmentHelper.func_152377_a(this.getHeldItem(), ((EntityLivingBase)var1).getCreatureAttribute());
         } else {
            var4 = EnchantmentHelper.func_152377_a(this.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
         }

         var3 = var3 + EnchantmentHelper.getKnockbackModifier(this);
         if(this.isSprinting()) {
            ++var3;
         }

         if(var2 > 0.0F || var4 > 0.0F) {
            boolean var5 = this.fallDistance > 0.0F && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Potion.blindness) && this.ridingEntity == null && var1 instanceof EntityLivingBase;
            if(var2 > 0.0F) {
               var2 *= 1.5F;
            }

            var2 = var2 + var4;
            boolean var6 = false;
            int var7 = EnchantmentHelper.getFireAspectModifier(this);
            if(var1 instanceof EntityLivingBase && !var1.isBurning()) {
               var6 = true;
               var1.setFire(1);
            }

            double var8 = var1.motionX;
            double var10 = var1.motionY;
            double var12 = var1.motionZ;
            boolean var14 = var1.attackEntityFrom(DamageSource.causePlayerDamage(this), var2);
            var1.addVelocity((double)(-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * (float)var3 * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * (float)var3 * 0.5F));
            this.motionX *= 0.6D;
            this.motionZ *= 0.6D;
            this.setSprinting(false);
            if(var1 instanceof EntityPlayerMP && var1.velocityChanged) {
               ((EntityPlayerMP)var1).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(var1));
               var1.velocityChanged = false;
               var1.motionX = var8;
               var1.motionY = var10;
               var1.motionZ = var12;
            }

            this.onCriticalHit(var1);
            if(var4 > 0.0F) {
               this.onEnchantmentCritical(var1);
            }

            if(var2 >= 18.0F) {
               this.triggerAchievement(AchievementList.overkill);
            }

            this.setLastAttacker(var1);
            if(var1 instanceof EntityLivingBase) {
               EnchantmentHelper.applyThornEnchantments((EntityLivingBase)var1, this);
            }

            EnchantmentHelper.applyArthropodEnchantments(this, var1);
            ItemStack var15 = this.getCurrentEquippedItem();
            Object var16 = var1;
            if(var1 instanceof EntityDragonPart) {
               IEntityMultiPart var17 = ((EntityDragonPart)var1).entityDragonObj;
               if(var17 instanceof EntityLivingBase) {
                  var16 = (EntityLivingBase)var17;
               }
            }

            if(var16 instanceof EntityLivingBase) {
               var15.hitEntity((EntityLivingBase)var16, this);
               if(var15.stackSize <= 0) {
                  this.destroyCurrentEquippedItem();
               }
            }

            if(var1 instanceof EntityLivingBase) {
               this.addStat(StatList.damageDealtStat, Math.round(var2 * 10.0F));
               var1.setFire(var7 * 4);
            }

            this.addExhaustion(0.3F);
         }
      }

   }

   public void onCriticalHit(Entity var1) {
   }

   public void onEnchantmentCritical(Entity var1) {
   }

   public void respawnPlayer() {
   }

   public void setDead() {
      super.setDead();
      this.inventoryContainer.onContainerClosed(this);
      if(this.openContainer != null) {
         this.openContainer.onContainerClosed(this);
      }

   }

   public boolean isEntityInsideOpaqueBlock() {
      return !this.sleeping && super.isEntityInsideOpaqueBlock();
   }

   public boolean isUser() {
      return false;
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }

   public EntityPlayer$EnumStatus trySleep(BlockPos var1) {
      if(!this.worldObj.isRemote) {
         if(this.isPlayerSleeping() || !this.isEntityAlive()) {
            return EntityPlayer$EnumStatus.OTHER_PROBLEM;
         }

         if(!this.worldObj.provider.isSurfaceWorld()) {
            return EntityPlayer$EnumStatus.NOT_POSSIBLE_HERE;
         }

         if(this.worldObj.isDaytime()) {
            return EntityPlayer$EnumStatus.NOT_POSSIBLE_NOW;
         }

         if(Math.abs(this.posX - (double)var1.getX()) > 3.0D || Math.abs(this.posY - (double)var1.getY()) > 2.0D || Math.abs(this.posZ - (double)var1.getZ()) > 3.0D) {
            return EntityPlayer$EnumStatus.TOO_FAR_AWAY;
         }

         double var2 = 8.0D;
         double var4 = 5.0D;
         List var6 = this.worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB((double)var1.getX() - var2, (double)var1.getY() - var4, (double)var1.getZ() - var2, (double)var1.getX() + var2, (double)var1.getY() + var4, (double)var1.getZ() + var2));
         if(!var6.isEmpty()) {
            return EntityPlayer$EnumStatus.NOT_SAFE;
         }
      }

      if(this.isRiding()) {
         this.mountEntity((Entity)null);
      }

      this.setSize(0.2F, 0.2F);
      if(this.worldObj.isBlockLoaded(var1)) {
         EnumFacing var7 = (EnumFacing)this.worldObj.getBlockState(var1).getValue(BlockDirectional.FACING);
         float var3 = 0.5F;
         float var8 = 0.5F;
         switch(EntityPlayer$1.$SwitchMap$net$minecraft$util$EnumFacing[var7.ordinal()]) {
         case 1:
            var8 = 0.9F;
            break;
         case 2:
            var8 = 0.1F;
            break;
         case 3:
            var3 = 0.1F;
            break;
         case 4:
            var3 = 0.9F;
         }

         this.func_175139_a(var7);
         this.setPosition((double)((float)var1.getX() + var3), (double)((float)var1.getY() + 0.6875F), (double)((float)var1.getZ() + var8));
      } else {
         this.setPosition((double)((float)var1.getX() + 0.5F), (double)((float)var1.getY() + 0.6875F), (double)((float)var1.getZ() + 0.5F));
      }

      this.sleeping = true;
      this.sleepTimer = 0;
      this.playerLocation = var1;
      this.motionX = this.motionZ = this.motionY = 0.0D;
      if(!this.worldObj.isRemote) {
         this.worldObj.updateAllPlayersSleepingFlag();
      }

      return EntityPlayer$EnumStatus.OK;
   }

   private void func_175139_a(EnumFacing var1) {
      this.renderOffsetX = 0.0F;
      this.renderOffsetZ = 0.0F;
      switch(EntityPlayer$1.$SwitchMap$net$minecraft$util$EnumFacing[var1.ordinal()]) {
      case 1:
         this.renderOffsetZ = -1.8F;
         break;
      case 2:
         this.renderOffsetZ = 1.8F;
         break;
      case 3:
         this.renderOffsetX = 1.8F;
         break;
      case 4:
         this.renderOffsetX = -1.8F;
      }

   }

   public void wakeUpPlayer(boolean var1, boolean var2, boolean var3) {
      this.setSize(0.6F, 1.8F);
      IBlockState var4 = this.worldObj.getBlockState(this.playerLocation);
      if(this.playerLocation != null && var4.getBlock() == Blocks.bed) {
         this.worldObj.setBlockState(this.playerLocation, var4.withProperty(BlockBed.OCCUPIED, Boolean.FALSE), 4);
         BlockPos var5 = BlockBed.getSafeExitLocation(this.worldObj, this.playerLocation, 0);
         var5 = this.playerLocation.up();
         this.setPosition((double)((float)var5.getX() + 0.5F), (double)((float)var5.getY() + 0.1F), (double)((float)var5.getZ() + 0.5F));
      }

      this.sleeping = false;
      if(!this.worldObj.isRemote) {
         this.worldObj.updateAllPlayersSleepingFlag();
      }

      this.sleepTimer = 0;
      this.setSpawnPoint(this.playerLocation, false);
   }

   private boolean isInBed() {
      return this.worldObj.getBlockState(this.playerLocation).getBlock() == Blocks.bed;
   }

   public static BlockPos getBedSpawnLocation(World var0, BlockPos var1, boolean var2) {
      Block var3 = var0.getBlockState(var1).getBlock();
      return var3 != Blocks.bed?null:BlockBed.getSafeExitLocation(var0, var1, 0);
   }

   public float getBedOrientationInDegrees() {
      if(this.playerLocation != null) {
         EnumFacing var1 = (EnumFacing)this.worldObj.getBlockState(this.playerLocation).getValue(BlockDirectional.FACING);
         switch(EntityPlayer$1.$SwitchMap$net$minecraft$util$EnumFacing[var1.ordinal()]) {
         case 1:
            return 90.0F;
         case 2:
            return 270.0F;
         case 3:
            return 0.0F;
         case 4:
            return 180.0F;
         }
      }

      return 0.0F;
   }

   public boolean isPlayerSleeping() {
      return this.sleeping;
   }

   public boolean isPlayerFullyAsleep() {
      return this.sleeping && this.sleepTimer >= 100;
   }

   public int getSleepTimer() {
      return this.sleepTimer;
   }

   public void addComponentMessage(IChatComponent var1) {
   }

   public BlockPos getBedLocation() {
      return this.spawnChunk;
   }

   public boolean isSpawnForced() {
      return this.spawnForced;
   }

   public void setSpawnPoint(BlockPos var1, boolean var2) {
      this.spawnChunk = var1;
      this.spawnForced = var2;
   }

   public void triggerAchievement(StatBase var1) {
      this.addStat(var1, 1);
   }

   public void addStat(StatBase var1, int var2) {
   }

   public void func_175145_a(StatBase var1) {
   }

   public void jump() {
      super.jump();
      this.triggerAchievement(StatList.jumpStat);
      if(this.isSprinting()) {
         this.addExhaustion(0.8F);
      } else {
         this.addExhaustion(0.2F);
      }

   }

   public void moveEntityWithHeading(float var1, float var2) {
      double var3 = this.posX;
      double var5 = this.posY;
      double var7 = this.posZ;
      if(this.abilities.isFlying() && this.ridingEntity == null) {
         double var9 = this.motionY;
         float var11 = this.jumpMovementFactor;
         this.jumpMovementFactor = this.abilities.getFlySpeed() * (float)(this.isSprinting()?2:1);
         super.moveEntityWithHeading(var1, var2);
         this.motionY = var9 * 0.6D;
         this.jumpMovementFactor = var11;
      } else {
         super.moveEntityWithHeading(var1, var2);
      }

      this.addMovementStat(this.posX - var3, this.posY - var5, this.posZ - var7);
   }

   public float getAIMoveSpeed() {
      return (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
   }

   public void addMovementStat(double var1, double var3, double var5) {
      if(this.ridingEntity == null) {
         if(this.isInsideOfMaterial(Material.water)) {
            int var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5) * 100.0F);
            this.addStat(StatList.distanceDoveStat, var7);
            this.addExhaustion(0.015F * (float)var7 * 0.01F);
         } else if(this.isInWater()) {
            int var8 = Math.round(MathHelper.sqrt_double(var1 * var1 + var5 * var5) * 100.0F);
            this.addStat(StatList.distanceSwumStat, var8);
            this.addExhaustion(0.015F * (float)var8 * 0.01F);
         } else if(this.isOnLadder()) {
            if(var3 > 0.0D) {
               this.addStat(StatList.distanceClimbedStat, (int)Math.round(var3 * 100.0D));
            }
         } else if(this.onGround) {
            int var9 = Math.round(MathHelper.sqrt_double(var1 * var1 + var5 * var5) * 100.0F);
            this.addStat(StatList.distanceWalkedStat, var9);
            if(this.isSprinting()) {
               this.addStat(StatList.distanceSprintedStat, var9);
               this.addExhaustion(0.099999994F * (float)var9 * 0.01F);
            } else {
               if(this.isSneaking()) {
                  this.addStat(StatList.distanceCrouchedStat, var9);
               }

               this.addExhaustion(0.01F * (float)var9 * 0.01F);
            }
         } else {
            int var10 = Math.round(MathHelper.sqrt_double(var1 * var1 + var5 * var5) * 100.0F);
            if(var10 > 25) {
               this.addStat(StatList.distanceFlownStat, var10);
            }
         }
      }

   }

   private void addMountedMovementStat(double var1, double var3, double var5) {
      if(this.ridingEntity != null) {
         int var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5) * 100.0F);
         if(this.ridingEntity instanceof EntityMinecart) {
            this.addStat(StatList.distanceByMinecartStat, var7);
            if(this.startMinecartRidingCoordinate == null) {
               this.startMinecartRidingCoordinate = new BlockPos(this);
            } else if(this.startMinecartRidingCoordinate.distanceSq((double)MathHelper.floor_double(this.posX), (double)MathHelper.floor_double(this.posY), (double)MathHelper.floor_double(this.posZ)) >= 1000000.0D) {
               this.triggerAchievement(AchievementList.onARail);
            }
         } else if(this.ridingEntity instanceof EntityBoat) {
            this.addStat(StatList.distanceByBoatStat, var7);
         } else if(this.ridingEntity instanceof EntityPig) {
            this.addStat(StatList.distanceByPigStat, var7);
         } else if(this.ridingEntity instanceof EntityHorse) {
            this.addStat(StatList.distanceByHorseStat, var7);
         }
      }

   }

   public void fall(float var1, float var2) {
      if(!this.abilities.isAllowFlying()) {
         if(var1 >= 2.0F) {
            this.addStat(StatList.distanceFallenStat, (int)Math.round((double)var1 * 100.0D));
         }

         super.fall(var1, var2);
      }

   }

   protected void resetHeight() {
      if(!this.isSpectator()) {
         super.resetHeight();
      }

   }

   protected String getFallSoundString(int var1) {
      return var1 > 4?"game.player.hurt.fall.big":"game.player.hurt.fall.small";
   }

   public void onKillEntity(EntityLivingBase var1) {
      if(var1 instanceof IMob) {
         this.triggerAchievement(AchievementList.killEnemy);
      }

      EntityList$EntityEggInfo var2 = (EntityList$EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(EntityList.getEntityID(var1)));
      this.triggerAchievement(var2.field_151512_d);
   }

   public void setInWeb() {
      if(!this.abilities.isFlying()) {
         super.setInWeb();
      }

   }

   public ItemStack getCurrentArmor(int var1) {
      return this.inventory.armorItemInSlot(var1);
   }

   public void addExperience(int var1) {
      this.addScore(var1);
      int var2 = Integer.MAX_VALUE - this.experienceTotal;
      if(var1 > var2) {
         var1 = var2;
      }

      this.experience += (float)var1 / (float)this.xpBarCap();

      for(this.experienceTotal += var1; this.experience >= 1.0F; this.experience /= (float)this.xpBarCap()) {
         this.experience = (this.experience - 1.0F) * (float)this.xpBarCap();
         this.addExperienceLevel(1);
      }

   }

   public int getXPSeed() {
      return this.xpSeed;
   }

   public void removeExperienceLevel(int var1) {
      this.experienceLevel -= var1;
      if(this.experienceLevel < 0) {
         this.experienceLevel = 0;
         this.experience = 0.0F;
         this.experienceTotal = 0;
      }

      this.xpSeed = this.rand.nextInt();
   }

   public void addExperienceLevel(int var1) {
      this.experienceLevel += var1;
      if(this.experienceLevel < 0) {
         this.experienceLevel = 0;
         this.experience = 0.0F;
         this.experienceTotal = 0;
      }

      if(this.experienceLevel % 5 == 0 && (float)this.lastXPSound < (float)this.ticksExisted - 100.0F) {
         float var2 = this.experienceLevel > 30?1.0F:(float)this.experienceLevel / 30.0F;
         this.worldObj.playSoundAtEntity(this, "random.levelup", var2 * 0.75F, 1.0F);
         this.lastXPSound = this.ticksExisted;
      }

   }

   public int xpBarCap() {
      return this.experienceLevel >= 30?112 + (this.experienceLevel - 30) * 9:(this.experienceLevel >= 15?37 + (this.experienceLevel - 15) * 5:7 + this.experienceLevel * 2);
   }

   public void addExhaustion(float var1) {
      if(!this.abilities.isDisabledDamage() && !this.worldObj.isRemote) {
         this.foodStats.addExhaustion(var1);
      }

   }

   public FoodStats getFoodStats() {
      return this.foodStats;
   }

   public boolean canEat(boolean var1) {
      return this.foodStats.needFood() && !this.abilities.isDisabledDamage();
   }

   public boolean shouldHeal() {
      return this.getHealth() > 0.0F && this.getHealth() < this.getMaxHealth();
   }

   public void setItemInUse(ItemStack var1, int var2) {
      if(var1 != this.itemInUse) {
         this.itemInUse = var1;
         this.itemInUseCount = var2;
         if(!this.worldObj.isRemote) {
            this.setEating(true);
         }
      }

   }

   public boolean isAllowEdit() {
      return this.abilities.isAllowEdit();
   }

   public boolean a(BlockPos var1, EnumFacing var2, ItemStack var3) {
      return this.abilities.isAllowEdit();
   }

   protected int getExperiencePoints(EntityPlayer var1) {
      if(this.worldObj.getGameRules().getBoolean("keepInventory")) {
         return 0;
      } else {
         int var2 = this.experienceLevel * 7;
         return Math.min(var2, 100);
      }
   }

   protected boolean isPlayer() {
      return true;
   }

   public boolean getAlwaysRenderNameTagForRender() {
      return true;
   }

   public void clonePlayer(EntityPlayer var1, boolean var2) {
      this.inventory.copyInventory(var1.inventory);
      this.setHealth(var1.getHealth());
      this.foodStats = var1.foodStats;
      this.experienceLevel = var1.experienceLevel;
      this.experienceTotal = var1.experienceTotal;
      this.experience = var1.experience;
      this.setScore(var1.getScore());
      this.field_181016_an = var1.field_181016_an;
      this.field_181017_ao = var1.field_181017_ao;
      this.field_181018_ap = var1.field_181018_ap;
      this.xpSeed = var1.xpSeed;
      this.theInventoryEnderChest = var1.theInventoryEnderChest;
      this.k().a(10, Byte.valueOf(var1.k().g(10)));
   }

   protected boolean canTriggerWalking() {
      return !this.abilities.isFlying();
   }

   public void sendPlayerAbilities() {
   }

   public void setGameType(WorldSettings$GameType var1) {
   }

   public String getName() {
      return this.gameProfile.getName();
   }

   public InventoryEnderChest getInventoryEnderChest() {
      return this.theInventoryEnderChest;
   }

   public ItemStack getEquipmentInSlot(int var1) {
      return this.inventory.getCurrentItem();
   }

   public ItemStack getHeldItem() {
      return this.inventory.getCurrentItem();
   }

   public void setCurrentItemOrArmor(int var1, ItemStack var2) {
      this.inventory.armorInventory[var1] = var2;
   }

   public boolean isInvisibleToPlayer(EntityPlayer var1) {
      if(!this.isInvisible()) {
         return false;
      } else if(var1.isSpectator()) {
         return false;
      } else {
         Team var2 = this.getTeam();
         return var1.getTeam() != var2 || !var2.getSeeFriendlyInvisiblesEnabled();
      }
   }

   public abstract boolean isSpectator();

   public ItemStack[] getInventory() {
      return this.inventory.armorInventory;
   }

   public boolean isPushedByWater() {
      return !this.abilities.isFlying();
   }

   public Scoreboard getWorldScoreboard() {
      return this.worldObj.getScoreboard();
   }

   public Team getTeam() {
      return this.getWorldScoreboard().getPlayersTeam(this.getName());
   }

   public IChatComponent getDisplayName() {
      ChatComponentText var1 = new ChatComponentText(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()));
      var1.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent$Action.SUGGEST_COMMAND, "/msg " + this.getName() + " "));
      var1.getChatStyle().setChatHoverEvent(this.getHoverEvent());
      var1.getChatStyle().setInsertion(this.getName());
      return var1;
   }

   public float getEyeHeight() {
      float var1 = 1.62F;
      if(this.isPlayerSleeping()) {
         var1 = 0.2F;
      }

      if(this.isSneaking()) {
         var1 -= 0.08F;
      }

      return var1;
   }

   public float getAbsorptionAmount() {
      return this.k().b(17);
   }

   public void setAbsorptionAmount(float var1) {
      if(var1 < 0.0F) {
         var1 = 0.0F;
      }

      this.k().a(17, Float.valueOf(var1));
   }

   public static UUID getUUID(GameProfile var0) {
      UUID var1 = var0.getId();
      var1 = getOfflineUUID(var0.getName());
      return var1;
   }

   public static UUID getOfflineUUID(String var0) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + var0).getBytes(Charsets.UTF_8));
   }

   public void setGameProfile(GameProfile var1) {
      this.gameProfile = var1;
   }

   public boolean canOpen(LockCode var1) {
      if(var1.isEmpty()) {
         return true;
      } else {
         ItemStack var2 = this.getCurrentEquippedItem();
         return var2.hasDisplayName() && var2.getDisplayName().equals(var1.getLock());
      }
   }

   public boolean isWearing(EnumPlayerModelParts var1) {
      return (this.k().g(10) & var1.getPartMask()) == var1.getPartMask();
   }

   public boolean sendCommandFeedback() {
      return MinecraftServer.getServer().worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
   }

   public boolean replaceItemInInventory(int var1, ItemStack var2) {
      if(var1 < this.inventory.mainInventory.length) {
         this.inventory.setInventorySlotContents(var1, var2);
         return true;
      } else {
         int var3 = var1 - 100;
         if(var3 >= this.inventory.armorInventory.length) {
            int var5 = var1 - 200;
            if(var5 < this.theInventoryEnderChest.getSizeInventory()) {
               this.theInventoryEnderChest.setInventorySlotContents(var5, var2);
               return true;
            } else {
               return false;
            }
         } else {
            int var4 = var3 + 1;
            if(var2.getItem() != null) {
               if(var2.getItem() instanceof ItemArmor) {
                  if(EntityLiving.getArmorPosition(var2) != var4) {
                     return false;
                  }
               } else if(var4 != 4 || var2.getItem() != Items.skull && !(var2.getItem() instanceof ItemBlock)) {
                  return false;
               }
            }

            this.inventory.setInventorySlotContents(var3 + this.inventory.mainInventory.length, var2);
            return true;
         }
      }
   }

   public boolean hasReducedDebug() {
      return this.hasReducedDebug;
   }

   public void setReducedDebug(boolean var1) {
      this.hasReducedDebug = var1;
   }
}
