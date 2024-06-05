package net.minecraft.entity.player;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.Collection;
import java.util.Iterator;
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
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.ClickEvent;
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
import net.minecraft.world.WorldSettings;

public abstract class EntityPlayer extends EntityLivingBase {
   public float field_71089_bV;
   public BlockPos playerLocation;
   protected boolean sleeping;
   private BlockPos spawnChunk;
   private int itemInUseCount;
   private int field_82249_h;
   public float cameraYaw;
   private ItemStack itemInUse;
   protected FoodStats foodStats = new FoodStats();
   public double field_71085_bR;
   protected int flyToggleTimer;
   private final GameProfile gameProfile;
   private boolean spawnForced;
   public Container inventoryContainer;
   private int sleepTimer;
   private BlockPos startMinecartRidingCoordinate;
   public Container openContainer;
   public float experience;
   public EntityFishHook fishEntity;
   public int experienceLevel;
   public double field_71091_bM;
   public InventoryPlayer inventory = new InventoryPlayer(this);
   private int field_175152_f;
   public float field_71079_bU;
   private boolean field_175153_bG = false;
   public int xpCooldown;
   public float speedInAir = 0.02F;
   public double field_71095_bQ;
   public double field_71094_bP;
   public float prevCameraYaw;
   public double field_71097_bO;
   public float field_71082_cx;
   private InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();
   public int experienceTotal;
   public double field_71096_bN;
   public PlayerCapabilities capabilities = new PlayerCapabilities();
   protected float speedOnGround = 0.1F;
   private static final String __OBFID = "CL_00001711";

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.entityUniqueID = getUUID(this.gameProfile);
      NBTTagList var2 = var1.getTagList("Inventory", 10);
      this.inventory.readFromNBT(var2);
      this.inventory.currentItem = var1.getInteger("SelectedItemSlot");
      this.sleeping = var1.getBoolean("Sleeping");
      this.sleepTimer = var1.getShort("SleepTimer");
      this.experience = var1.getFloat("XpP");
      this.experienceLevel = var1.getInteger("XpLevel");
      this.experienceTotal = var1.getInteger("XpTotal");
      this.field_175152_f = var1.getInteger("XpSeed");
      if (this.field_175152_f == 0) {
         this.field_175152_f = this.rand.nextInt();
      }

      this.setScore(var1.getInteger("Score"));
      if (this.sleeping) {
         this.playerLocation = new BlockPos(this);
         this.wakeUpPlayer(true, true, false);
      }

      if (var1.hasKey("SpawnX", 99) && var1.hasKey("SpawnY", 99) && var1.hasKey("SpawnZ", 99)) {
         this.spawnChunk = new BlockPos(var1.getInteger("SpawnX"), var1.getInteger("SpawnY"), var1.getInteger("SpawnZ"));
         this.spawnForced = var1.getBoolean("SpawnForced");
      }

      this.foodStats.readNBT(var1);
      this.capabilities.readCapabilitiesFromNBT(var1);
      if (var1.hasKey("EnderItems", 9)) {
         NBTTagList var3 = var1.getTagList("EnderItems", 10);
         this.theInventoryEnderChest.loadInventoryFromNBT(var3);
      }

   }

   protected void joinEntityItemWithWorld(EntityItem var1) {
      this.worldObj.spawnEntityInWorld(var1);
   }

   public boolean isPushedByWater() {
      return !this.capabilities.isFlying;
   }

   public int getSleepTimer() {
      return this.sleepTimer;
   }

   protected void onItemUseFinish() {
      if (this.itemInUse != null) {
         this.updateItemUse(this.itemInUse, 16);
         int var1 = this.itemInUse.stackSize;
         ItemStack var2 = this.itemInUse.onItemUseFinish(this.worldObj, this);
         if (var2 != this.itemInUse || var2 != null && var2.stackSize != var1) {
            this.inventory.mainInventory[this.inventory.currentItem] = var2;
            if (var2.stackSize == 0) {
               this.inventory.mainInventory[this.inventory.currentItem] = null;
            }
         }

         this.clearItemInUse();
      }

   }

   public void setCurrentItemOrArmor(int var1, ItemStack var2) {
      this.inventory.armorInventory[var1] = var2;
   }

   public int xpBarCap() {
      return this.experienceLevel >= 30 ? 112 + (this.experienceLevel - 30) * 9 : (this.experienceLevel >= 15 ? 37 + (this.experienceLevel - 15) * 5 : 7 + this.experienceLevel * 2);
   }

   public void addScore(int var1) {
      int var2 = this.getScore();
      this.dataWatcher.updateObject(18, var2 + var1);
   }

   public boolean isPlayerSleeping() {
      return this.sleeping;
   }

   public int getTotalArmorValue() {
      return this.inventory.getTotalArmorValue();
   }

   public static BlockPos func_180467_a(World var0, BlockPos var1, boolean var2) {
      if (var0.getBlockState(var1).getBlock() != Blocks.bed) {
         if (!var2) {
            return null;
         } else {
            Material var3 = var0.getBlockState(var1).getBlock().getMaterial();
            Material var4 = var0.getBlockState(var1.offsetUp()).getBlock().getMaterial();
            boolean var5 = !var3.isSolid() && !var3.isLiquid();
            boolean var6 = !var4.isSolid() && !var4.isLiquid();
            return var5 && var6 ? var1 : null;
         }
      } else {
         return BlockBed.getSafeExitLocation(var0, var1, 0);
      }
   }

   public void setScore(int var1) {
      this.dataWatcher.updateObject(18, var1);
   }

   public float getEyeHeight() {
      float var1 = 1.62F;
      if (this.isPlayerSleeping()) {
         var1 = 0.2F;
      }

      if (this.isSneaking()) {
         var1 -= 0.08F;
      }

      return var1;
   }

   public boolean isBlocking() {
      return this.isUsingItem() && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.BLOCK;
   }

   public boolean getAlwaysRenderNameTagForRender() {
      return true;
   }

   public void destroyCurrentEquippedItem() {
      this.inventory.setInventorySlotContents(this.inventory.currentItem, (ItemStack)null);
   }

   public int getItemInUseDuration() {
      return this.isUsingItem() ? this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount : 0;
   }

   public int func_175138_ci() {
      return this.field_175152_f;
   }

   public ItemStack getHeldItem() {
      return this.inventory.getCurrentItem();
   }

   public EntityPlayer.EnumStatus func_180469_a(BlockPos var1) {
      if (!this.worldObj.isRemote) {
         label70: {
            if (!this.isPlayerSleeping() && this.isEntityAlive()) {
               if (!this.worldObj.provider.isSurfaceWorld()) {
                  return EntityPlayer.EnumStatus.NOT_POSSIBLE_HERE;
               }

               if (this.worldObj.isDaytime()) {
                  return EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW;
               }

               if (!(Math.abs(this.posX - (double)var1.getX()) > 3.0D) && !(Math.abs(this.posY - (double)var1.getY()) > 2.0D) && !(Math.abs(this.posZ - (double)var1.getZ()) > 3.0D)) {
                  double var2 = 8.0D;
                  double var4 = 5.0D;
                  List var6 = this.worldObj.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB((double)var1.getX() - var2, (double)var1.getY() - var4, (double)var1.getZ() - var2, (double)var1.getX() + var2, (double)var1.getY() + var4, (double)var1.getZ() + var2));
                  if (!var6.isEmpty()) {
                     return EntityPlayer.EnumStatus.NOT_SAFE;
                  }
                  break label70;
               }

               return EntityPlayer.EnumStatus.TOO_FAR_AWAY;
            }

            return EntityPlayer.EnumStatus.OTHER_PROBLEM;
         }
      }

      if (this.isRiding()) {
         this.mountEntity((Entity)null);
      }

      this.setSize(0.2F, 0.2F);
      if (this.worldObj.isBlockLoaded(var1)) {
         EnumFacing var7 = (EnumFacing)this.worldObj.getBlockState(var1).getValue(BlockDirectional.AGE);
         float var3 = 0.5F;
         float var8 = 0.5F;
         switch(var7) {
         case SOUTH:
            var8 = 0.9F;
            break;
         case NORTH:
            var8 = 0.1F;
            break;
         case WEST:
            var3 = 0.1F;
            break;
         case EAST:
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
      if (!this.worldObj.isRemote) {
         this.worldObj.updateAllPlayersSleepingFlag();
      }

      return EntityPlayer.EnumStatus.OK;
   }

   public void onCriticalHit(Entity var1) {
   }

   public Team getTeam() {
      return this.getWorldScoreboard().getPlayersTeam(this.getName());
   }

   public void displayGUIChest(IInventory var1) {
   }

   public IChatComponent getDisplayName() {
      ChatComponentText var1 = new ChatComponentText(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()));
      var1.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.valueOf((new StringBuilder("/msg ")).append(this.getName()).append(" "))));
      var1.getChatStyle().setChatHoverEvent(this.func_174823_aP());
      var1.getChatStyle().setInsertion(this.getName());
      return var1;
   }

   public float getArmorVisibility() {
      int var1 = 0;
      ItemStack[] var2 = this.inventory.armorInventory;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ItemStack var5 = var2[var4];
         if (var5 != null) {
            ++var1;
         }
      }

      return (float)var1 / (float)this.inventory.armorInventory.length;
   }

   public void jump() {
      super.jump();
      this.triggerAchievement(StatList.jumpStat);
      if (this.isSprinting()) {
         this.addExhaustion(0.8F);
      } else {
         this.addExhaustion(0.2F);
      }

   }

   public boolean func_175140_cp() {
      return this.field_175153_bG;
   }

   public int getScore() {
      return this.dataWatcher.getWatchableObjectInt(18);
   }

   private void collideWithPlayer(Entity var1) {
      var1.onCollideWithPlayer(this);
   }

   public abstract boolean func_175149_v();

   public void clonePlayer(EntityPlayer var1, boolean var2) {
      if (var2) {
         this.inventory.copyInventory(var1.inventory);
         this.setHealth(var1.getHealth());
         this.foodStats = var1.foodStats;
         this.experienceLevel = var1.experienceLevel;
         this.experienceTotal = var1.experienceTotal;
         this.experience = var1.experience;
         this.setScore(var1.getScore());
         this.teleportDirection = var1.teleportDirection;
      } else if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
         this.inventory.copyInventory(var1.inventory);
         this.experienceLevel = var1.experienceLevel;
         this.experienceTotal = var1.experienceTotal;
         this.experience = var1.experience;
         this.setScore(var1.getScore());
      }

      this.theInventoryEnderChest = var1.theInventoryEnderChest;
      this.getDataWatcher().updateObject(10, var1.getDataWatcher().getWatchableObjectByte(10));
   }

   public void setGameType(WorldSettings.GameType var1) {
   }

   public void func_175145_a(StatBase var1) {
   }

   public boolean shouldHeal() {
      return this.getHealth() > 0.0F && this.getHealth() < this.getMaxHealth();
   }

   public boolean isUsingItem() {
      return this.itemInUse != null;
   }

   public void addChatComponentMessage(IChatComponent var1) {
   }

   public float getAIMoveSpeed() {
      return (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
   }

   public boolean canHarvestBlock(Block var1) {
      return this.inventory.func_146025_b(var1);
   }

   protected String func_146067_o(int var1) {
      return var1 > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
   }

   public void moveEntityWithHeading(float var1, float var2) {
      double var3 = this.posX;
      double var5 = this.posY;
      double var7 = this.posZ;
      if (this.capabilities.isFlying && this.ridingEntity == null) {
         double var9 = this.motionY;
         float var11 = this.jumpMovementFactor;
         this.jumpMovementFactor = this.capabilities.getFlySpeed() * (float)(this.isSprinting() ? 2 : 1);
         super.moveEntityWithHeading(var1, var2);
         this.motionY = var9 * 0.6D;
         this.jumpMovementFactor = var11;
      } else {
         super.moveEntityWithHeading(var1, var2);
      }

      this.addMovementStat(this.posX - var3, this.posY - var5, this.posZ - var7);
   }

   public static UUID getUUID(GameProfile var0) {
      UUID var1 = var0.getId();
      if (var1 == null) {
         var1 = func_175147_b(var0.getName());
      }

      return var1;
   }

   protected String getDeathSound() {
      return "game.player.die";
   }

   public void displayGui(IInteractionObject var1) {
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
   }

   public void setItemInUse(ItemStack var1, int var2) {
      if (var1 != this.itemInUse) {
         this.itemInUse = var1;
         this.itemInUseCount = var2;
         if (!this.worldObj.isRemote) {
            this.setEating(true);
         }
      }

   }

   public ItemStack getCurrentArmor(int var1) {
      return this.inventory.armorItemInSlot(var1);
   }

   public static UUID func_175147_b(String var0) {
      return UUID.nameUUIDFromBytes(String.valueOf((new StringBuilder("OfflinePlayer:")).append(var0)).getBytes(Charsets.UTF_8));
   }

   public boolean isPlayerFullyAsleep() {
      return this.sleeping && this.sleepTimer >= 100;
   }

   protected void closeScreen() {
      this.openContainer = this.inventoryContainer;
   }

   public void clearItemInUse() {
      this.itemInUse = null;
      this.itemInUseCount = 0;
      if (!this.worldObj.isRemote) {
         this.setEating(false);
      }

   }

   public boolean func_175146_a(LockCode var1) {
      if (var1.isEmpty()) {
         return true;
      } else {
         ItemStack var2 = this.getCurrentEquippedItem();
         return var2 != null && var2.hasDisplayName() ? var2.getDisplayName().equals(var1.getLock()) : false;
      }
   }

   public void func_71013_b(int var1) {
      this.experienceLevel -= var1;
      if (this.experienceLevel < 0) {
         this.experienceLevel = 0;
         this.experience = 0.0F;
         this.experienceTotal = 0;
      }

      this.field_175152_f = this.rand.nextInt();
   }

   public ItemStack getItemInUse() {
      return this.itemInUse;
   }

   public boolean canEat(boolean var1) {
      return (var1 || this.foodStats.needFood()) && !this.capabilities.disableDamage;
   }

   protected void damageEntity(DamageSource var1, float var2) {
      if (!this.func_180431_b(var1)) {
         if (!var1.isUnblockable() && this.isBlocking() && var2 > 0.0F) {
            var2 = (1.0F + var2) * 0.5F;
         }

         var2 = this.applyArmorCalculations(var1, var2);
         var2 = this.applyPotionDamageCalculations(var1, var2);
         float var3 = var2;
         var2 = Math.max(var2 - this.getAbsorptionAmount(), 0.0F);
         this.setAbsorptionAmount(this.getAbsorptionAmount() - (var3 - var2));
         if (var2 != 0.0F) {
            this.addExhaustion(var1.getHungerDamage());
            float var4 = this.getHealth();
            this.setHealth(this.getHealth() - var2);
            this.getCombatTracker().func_94547_a(var1, var4, var2);
            if (var2 < 3.4028235E37F) {
               this.addStat(StatList.damageTakenStat, Math.round(var2 * 10.0F));
            }
         }
      }

   }

   protected void updateItemUse(ItemStack var1, int var2) {
      if (var1.getItemUseAction() == EnumAction.DRINK) {
         this.playSound("random.drink", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      if (var1.getItemUseAction() == EnumAction.EAT) {
         for(int var3 = 0; var3 < var2; ++var3) {
            Vec3 var4 = new Vec3(((double)this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            var4 = var4.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
            var4 = var4.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
            double var5 = (double)(-this.rand.nextFloat()) * 0.6D - 0.3D;
            Vec3 var7 = new Vec3(((double)this.rand.nextFloat() - 0.5D) * 0.3D, var5, 0.6D);
            var7 = var7.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
            var7 = var7.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
            var7 = var7.addVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
            if (var1.getHasSubtypes()) {
               this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, var7.xCoord, var7.yCoord, var7.zCoord, var4.xCoord, var4.yCoord + 0.05D, var4.zCoord, Item.getIdFromItem(var1.getItem()), var1.getMetadata());
            } else {
               this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, var7.xCoord, var7.yCoord, var7.zCoord, var4.xCoord, var4.yCoord + 0.05D, var4.zCoord, Item.getIdFromItem(var1.getItem()));
            }
         }

         this.playSound("random.eat", 0.5F + 0.5F * (float)this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      }

   }

   public void addToPlayerScore(Entity var1, int var2) {
      this.addScore(var2);
      Collection var3 = this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.totalKillCount);
      if (var1 instanceof EntityPlayer) {
         this.triggerAchievement(StatList.playerKillsStat);
         var3.addAll(this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.playerKillCount));
         var3.addAll(this.func_175137_e(var1));
      } else {
         this.triggerAchievement(StatList.mobKillsStat);
      }

      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         ScoreObjective var5 = (ScoreObjective)var4.next();
         Score var6 = this.getWorldScoreboard().getValueFromObjective(this.getName(), var5);
         var6.func_96648_a();
      }

   }

   public ItemStack[] getInventory() {
      return this.inventory.armorInventory;
   }

   public void onLivingUpdate() {
      if (this.flyToggleTimer > 0) {
         --this.flyToggleTimer;
      }

      if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration")) {
         if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 == 0) {
            this.heal(1.0F);
         }

         if (this.foodStats.needFood() && this.ticksExisted % 10 == 0) {
            this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
         }
      }

      this.inventory.decrementAnimations();
      this.prevCameraYaw = this.cameraYaw;
      super.onLivingUpdate();
      IAttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
      if (!this.worldObj.isRemote) {
         var1.setBaseValue((double)this.capabilities.getWalkSpeed());
      }

      this.jumpMovementFactor = this.speedInAir;
      if (this.isSprinting()) {
         this.jumpMovementFactor = (float)((double)this.jumpMovementFactor + (double)this.speedInAir * 0.3D);
      }

      this.setAIMoveSpeed((float)var1.getAttributeValue());
      float var2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
      float var3 = (float)(Math.atan(-this.motionY * 0.20000000298023224D) * 15.0D);
      if (var2 > 0.1F) {
         var2 = 0.1F;
      }

      if (!this.onGround || this.getHealth() <= 0.0F) {
         var2 = 0.0F;
      }

      if (this.onGround || this.getHealth() <= 0.0F) {
         var3 = 0.0F;
      }

      this.cameraYaw += (var2 - this.cameraYaw) * 0.4F;
      this.cameraPitch += (var3 - this.cameraPitch) * 0.8F;
      if (this.getHealth() > 0.0F && !this.func_175149_v()) {
         AxisAlignedBB var4 = null;
         if (this.ridingEntity != null && !this.ridingEntity.isDead) {
            var4 = this.getEntityBoundingBox().union(this.ridingEntity.getEntityBoundingBox()).expand(1.0D, 0.0D, 1.0D);
         } else {
            var4 = this.getEntityBoundingBox().expand(1.0D, 0.5D, 1.0D);
         }

         List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, var4);

         for(int var6 = 0; var6 < var5.size(); ++var6) {
            Entity var7 = (Entity)var5.get(var6);
            if (!var7.isDead) {
               this.collideWithPlayer(var7);
            }
         }
      }

   }

   public void setAbsorptionAmount(float var1) {
      if (var1 < 0.0F) {
         var1 = 0.0F;
      }

      this.getDataWatcher().updateObject(17, var1);
   }

   public boolean func_174820_d(int var1, ItemStack var2) {
      if (var1 >= 0 && var1 < this.inventory.mainInventory.length) {
         this.inventory.setInventorySlotContents(var1, var2);
         return true;
      } else {
         int var3 = var1 - 100;
         int var4;
         if (var3 >= 0 && var3 < this.inventory.armorInventory.length) {
            var4 = var3 + 1;
            if (var2 != null && var2.getItem() != null) {
               if (var2.getItem() instanceof ItemArmor) {
                  if (EntityLiving.getArmorPosition(var2) != var4) {
                     return false;
                  }
               } else if (var4 != 4 || var2.getItem() != Items.skull && !(var2.getItem() instanceof ItemBlock)) {
                  return false;
               }
            }

            this.inventory.setInventorySlotContents(var3 + this.inventory.mainInventory.length, var2);
            return true;
         } else {
            var4 = var1 - 200;
            if (var4 >= 0 && var4 < this.theInventoryEnderChest.getSizeInventory()) {
               this.theInventoryEnderChest.setInventorySlotContents(var4, var2);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   public void onKillEntity(EntityLivingBase var1) {
      if (var1 instanceof IMob) {
         this.triggerAchievement(AchievementList.killEnemy);
      }

      EntityList.EntityEggInfo var2 = (EntityList.EntityEggInfo)EntityList.entityEggs.get(EntityList.getEntityID(var1));
      if (var2 != null) {
         this.triggerAchievement(var2.field_151512_d);
      }

   }

   public void displayGUIHorse(EntityHorse var1, IInventory var2) {
   }

   private void func_175139_a(EnumFacing var1) {
      this.field_71079_bU = 0.0F;
      this.field_71089_bV = 0.0F;
      switch(var1) {
      case SOUTH:
         this.field_71089_bV = -1.8F;
         break;
      case NORTH:
         this.field_71089_bV = 1.8F;
         break;
      case WEST:
         this.field_71079_bU = 1.8F;
         break;
      case EAST:
         this.field_71079_bU = -1.8F;
      }

   }

   public void triggerAchievement(StatBase var1) {
      this.addStat(var1, 1);
   }

   public double getYOffset() {
      return -0.35D;
   }

   protected void updateEntityActionState() {
      super.updateEntityActionState();
      this.updateArmSwingProgress();
      this.rotationYawHead = this.rotationYaw;
   }

   public ItemStack getEquipmentInSlot(int var1) {
      return var1 == 0 ? this.inventory.getCurrentItem() : this.inventory.armorInventory[var1 - 1];
   }

   public boolean func_175151_a(BlockPos var1, EnumFacing var2, ItemStack var3) {
      if (this.capabilities.allowEdit) {
         return true;
      } else if (var3 == null) {
         return false;
      } else {
         BlockPos var4 = var1.offset(var2.getOpposite());
         Block var5 = this.worldObj.getBlockState(var4).getBlock();
         return var3.canPlaceOn(var5) || var3.canEditBlocks();
      }
   }

   public EntityItem dropOneItem(boolean var1) {
      return this.func_146097_a(this.inventory.decrStackSize(this.inventory.currentItem, var1 && this.inventory.getCurrentItem() != null ? this.inventory.getCurrentItem().stackSize : 1), false, true);
   }

   public void wakeUpPlayer(boolean var1, boolean var2, boolean var3) {
      this.setSize(0.6F, 1.8F);
      IBlockState var4 = this.worldObj.getBlockState(this.playerLocation);
      if (this.playerLocation != null && var4.getBlock() == Blocks.bed) {
         this.worldObj.setBlockState(this.playerLocation, var4.withProperty(BlockBed.OCCUPIED_PROP, false), 4);
         BlockPos var5 = BlockBed.getSafeExitLocation(this.worldObj, this.playerLocation, 0);
         if (var5 == null) {
            var5 = this.playerLocation.offsetUp();
         }

         this.setPosition((double)((float)var5.getX() + 0.5F), (double)((float)var5.getY() + 0.1F), (double)((float)var5.getZ() + 0.5F));
      }

      this.sleeping = false;
      if (!this.worldObj.isRemote && var2) {
         this.worldObj.updateAllPlayersSleepingFlag();
      }

      this.sleepTimer = var1 ? 0 : 100;
      if (var3) {
         this.func_180473_a(this.playerLocation, false);
      }

   }

   public void func_175141_a(TileEntitySign var1) {
   }

   protected boolean isPlayer() {
      return true;
   }

   public void func_175150_k(boolean var1) {
      this.field_175153_bG = var1;
   }

   public EntityItem func_146097_a(ItemStack var1, boolean var2, boolean var3) {
      if (var1 == null) {
         return null;
      } else if (var1.stackSize == 0) {
         return null;
      } else {
         double var4 = this.posY - 0.30000001192092896D + (double)this.getEyeHeight();
         EntityItem var6 = new EntityItem(this.worldObj, this.posX, var4, this.posZ, var1);
         var6.setPickupDelay(40);
         if (var3) {
            var6.setThrower(this.getName());
         }

         float var7;
         float var8;
         if (var2) {
            var7 = this.rand.nextFloat() * 0.5F;
            var8 = this.rand.nextFloat() * 3.1415927F * 2.0F;
            var6.motionX = (double)(-MathHelper.sin(var8) * var7);
            var6.motionZ = (double)(MathHelper.cos(var8) * var7);
            var6.motionY = 0.20000000298023224D;
         } else {
            var7 = 0.3F;
            var6.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * var7);
            var6.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * var7);
            var6.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F) * var7 + 0.1F);
            var8 = this.rand.nextFloat() * 3.1415927F * 2.0F;
            var7 = 0.02F * this.rand.nextFloat();
            var6.motionX += Math.cos((double)var8) * (double)var7;
            var6.motionY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
            var6.motionZ += Math.sin((double)var8) * (double)var7;
         }

         this.joinEntityItemWithWorld(var6);
         if (var3) {
            this.triggerAchievement(StatList.dropStat);
         }

         return var6;
      }
   }

   public void sendPlayerAbilities() {
   }

   public void addStat(StatBase var1, int var2) {
   }

   public void addExperienceLevel(int var1) {
      this.experienceLevel += var1;
      if (this.experienceLevel < 0) {
         this.experienceLevel = 0;
         this.experience = 0.0F;
         this.experienceTotal = 0;
      }

      if (var1 > 0 && this.experienceLevel % 5 == 0 && (float)this.field_82249_h < (float)this.ticksExisted - 100.0F) {
         float var2 = this.experienceLevel > 30 ? 1.0F : (float)this.experienceLevel / 30.0F;
         this.worldObj.playSoundAtEntity(this, "random.levelup", var2 * 0.75F, 1.0F);
         this.field_82249_h = this.ticksExisted;
      }

   }

   public void addExhaustion(float var1) {
      if (!this.capabilities.disableDamage && !this.worldObj.isRemote) {
         this.foodStats.addExhaustion(var1);
      }

   }

   public float getAbsorptionAmount() {
      return this.getDataWatcher().getWatchableObjectFloat(17);
   }

   private boolean func_175143_p() {
      return this.worldObj.getBlockState(this.playerLocation).getBlock() == Blocks.bed;
   }

   public EntityPlayer(World var1, GameProfile var2) {
      super(var1);
      this.entityUniqueID = getUUID(var2);
      this.gameProfile = var2;
      this.inventoryContainer = new ContainerPlayer(this.inventory, !var1.isRemote, this);
      this.openContainer = this.inventoryContainer;
      BlockPos var3 = var1.getSpawnPoint();
      this.setLocationAndAngles((double)var3.getX() + 0.5D, (double)(var3.getY() + 1), (double)var3.getZ() + 0.5D, 0.0F, 0.0F);
      this.field_70741_aB = 180.0F;
      this.fireResistance = 20;
   }

   public BlockPos func_180470_cg() {
      return this.spawnChunk;
   }

   public void onDeath(DamageSource var1) {
      super.onDeath(var1);
      this.setSize(0.2F, 0.2F);
      this.setPosition(this.posX, this.posY, this.posZ);
      this.motionY = 0.10000000149011612D;
      if (this.getName().equals("Notch")) {
         this.func_146097_a(new ItemStack(Items.apple, 1), true, false);
      }

      if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
         this.inventory.dropAllItems();
      }

      if (var1 != null) {
         this.motionX = (double)(-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
         this.motionZ = (double)(-MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
      } else {
         this.motionX = this.motionZ = 0.0D;
      }

      this.triggerAchievement(StatList.deathsStat);
      this.func_175145_a(StatList.timeSinceDeathStat);
   }

   private void addMountedMovementStat(double var1, double var3, double var5) {
      if (this.ridingEntity != null) {
         int var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5) * 100.0F);
         if (var7 > 0) {
            if (this.ridingEntity instanceof EntityMinecart) {
               this.addStat(StatList.distanceByMinecartStat, var7);
               if (this.startMinecartRidingCoordinate == null) {
                  this.startMinecartRidingCoordinate = new BlockPos(this);
               } else if (this.startMinecartRidingCoordinate.distanceSq((double)MathHelper.floor_double(this.posX), (double)MathHelper.floor_double(this.posY), (double)MathHelper.floor_double(this.posZ)) >= 1000000.0D) {
                  this.triggerAchievement(AchievementList.onARail);
               }
            } else if (this.ridingEntity instanceof EntityBoat) {
               this.addStat(StatList.distanceByBoatStat, var7);
            } else if (this.ridingEntity instanceof EntityPig) {
               this.addStat(StatList.distanceByPigStat, var7);
            } else if (this.ridingEntity instanceof EntityHorse) {
               this.addStat(StatList.distanceByHorseStat, var7);
            }
         }
      }

   }

   public String getName() {
      return this.gameProfile.getName();
   }

   protected String getHurtSound() {
      return "game.player.hurt";
   }

   private Collection func_175137_e(Entity var1) {
      ScorePlayerTeam var2 = this.getWorldScoreboard().getPlayersTeam(this.getName());
      if (var2 != null) {
         int var3 = var2.func_178775_l().func_175746_b();
         if (var3 >= 0 && var3 < IScoreObjectiveCriteria.field_178793_i.length) {
            Iterator var4 = this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.field_178793_i[var3]).iterator();

            while(var4.hasNext()) {
               ScoreObjective var5 = (ScoreObjective)var4.next();
               Score var6 = this.getWorldScoreboard().getValueFromObjective(var1.getName(), var5);
               var6.func_96648_a();
            }
         }
      }

      ScorePlayerTeam var7 = this.getWorldScoreboard().getPlayersTeam(var1.getName());
      if (var7 != null) {
         int var8 = var7.func_178775_l().func_175746_b();
         if (var8 >= 0 && var8 < IScoreObjectiveCriteria.field_178792_h.length) {
            return this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.field_178792_h[var8]);
         }
      }

      return Lists.newArrayList();
   }

   public boolean interactWith(Entity var1) {
      if (this.func_175149_v()) {
         if (var1 instanceof IInventory) {
            this.displayGUIChest((IInventory)var1);
         }

         return false;
      } else {
         ItemStack var2 = this.getCurrentEquippedItem();
         ItemStack var3 = var2 != null ? var2.copy() : null;
         if (!var1.interactFirst(this)) {
            if (var2 != null && var1 instanceof EntityLivingBase) {
               if (this.capabilities.isCreativeMode) {
                  var2 = var3;
               }

               if (var2.interactWithEntity(this, (EntityLivingBase)var1)) {
                  if (var2.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                     this.destroyCurrentEquippedItem();
                  }

                  return true;
               }
            }

            return false;
         } else {
            if (var2 != null && var2 == this.getCurrentEquippedItem()) {
               if (var2.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                  this.destroyCurrentEquippedItem();
               } else if (var2.stackSize < var3.stackSize && this.capabilities.isCreativeMode) {
                  var2.stackSize = var3.stackSize;
               }
            }

            return true;
         }
      }
   }

   public boolean func_175148_a(EnumPlayerModelParts var1) {
      return (this.getDataWatcher().getWatchableObjectByte(10) & var1.func_179327_a()) == var1.func_179327_a();
   }

   public void displayGUIBook(ItemStack var1) {
   }

   public float func_180471_a(Block var1) {
      float var2 = this.inventory.getStrVsBlock(var1);
      if (var2 > 1.0F) {
         int var3 = EnchantmentHelper.getEfficiencyModifier(this);
         ItemStack var4 = this.inventory.getCurrentItem();
         if (var3 > 0 && var4 != null) {
            var2 += (float)(var3 * var3 + 1);
         }
      }

      if (this.isPotionActive(Potion.digSpeed)) {
         var2 *= 1.0F + (float)(this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
      }

      if (this.isPotionActive(Potion.digSlowdown)) {
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

      if (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
         var2 /= 5.0F;
      }

      if (!this.onGround) {
         var2 /= 5.0F;
      }

      return var2;
   }

   public int getPortalCooldown() {
      return 10;
   }

   public void func_180473_a(BlockPos var1, boolean var2) {
      if (var1 != null) {
         this.spawnChunk = var1;
         this.spawnForced = var2;
      } else {
         this.spawnChunk = null;
         this.spawnForced = false;
      }

   }

   public Scoreboard getWorldScoreboard() {
      return this.worldObj.getScoreboard();
   }

   public boolean func_175144_cb() {
      return false;
   }

   public int getMaxInPortalTime() {
      return this.capabilities.disableDamage ? 0 : 80;
   }

   public void handleHealthUpdate(byte var1) {
      if (var1 == 9) {
         this.onItemUseFinish();
      } else if (var1 == 23) {
         this.field_175153_bG = false;
      } else if (var1 == 22) {
         this.field_175153_bG = true;
      } else {
         super.handleHealthUpdate(var1);
      }

   }

   protected void damageArmor(float var1) {
      this.inventory.damageArmor(var1);
   }

   public void setDead() {
      super.setDead();
      this.inventoryContainer.onContainerClosed(this);
      if (this.openContainer != null) {
         this.openContainer.onContainerClosed(this);
      }

   }

   protected String getSplashSound() {
      return "game.player.swim.splash";
   }

   public FoodStats getFoodStats() {
      return this.foodStats;
   }

   protected int getExperiencePoints(EntityPlayer var1) {
      if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
         return 0;
      } else {
         int var2 = this.experienceLevel * 7;
         return var2 > 100 ? 100 : var2;
      }
   }

   public void setInWeb() {
      if (!this.capabilities.isFlying) {
         super.setInWeb();
      }

   }

   public void attackTargetEntityWithCurrentItem(Entity var1) {
      if (var1.canAttackWithItem() && !var1.hitByEntity(this)) {
         float var2 = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
         byte var3 = 0;
         float var4 = 0.0F;
         if (var1 instanceof EntityLivingBase) {
            var4 = EnchantmentHelper.func_152377_a(this.getHeldItem(), ((EntityLivingBase)var1).getCreatureAttribute());
         } else {
            var4 = EnchantmentHelper.func_152377_a(this.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
         }

         int var5 = var3 + EnchantmentHelper.getRespiration(this);
         if (this.isSprinting()) {
            ++var5;
         }

         if (var2 > 0.0F || var4 > 0.0F) {
            boolean var6 = this.fallDistance > 0.0F && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Potion.blindness) && this.ridingEntity == null && var1 instanceof EntityLivingBase;
            if (var6 && var2 > 0.0F) {
               var2 *= 1.5F;
            }

            var2 += var4;
            boolean var7 = false;
            int var8 = EnchantmentHelper.getFireAspectModifier(this);
            if (var1 instanceof EntityLivingBase && var8 > 0 && !var1.isBurning()) {
               var7 = true;
               var1.setFire(1);
            }

            double var9 = var1.motionX;
            double var11 = var1.motionY;
            double var13 = var1.motionZ;
            boolean var15 = var1.attackEntityFrom(DamageSource.causePlayerDamage(this), var2);
            if (var15) {
               if (var5 > 0) {
                  var1.addVelocity((double)(-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * (float)var5 * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * (float)var5 * 0.5F));
                  this.motionX *= 0.6D;
                  this.motionZ *= 0.6D;
                  this.setSprinting(false);
               }

               if (var1 instanceof EntityPlayerMP && var1.velocityChanged) {
                  ((EntityPlayerMP)var1).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(var1));
                  var1.velocityChanged = false;
                  var1.motionX = var9;
                  var1.motionY = var11;
                  var1.motionZ = var13;
               }

               if (var6) {
                  this.onCriticalHit(var1);
               }

               if (var4 > 0.0F) {
                  this.onEnchantmentCritical(var1);
               }

               if (var2 >= 18.0F) {
                  this.triggerAchievement(AchievementList.overkill);
               }

               this.setLastAttacker(var1);
               if (var1 instanceof EntityLivingBase) {
                  EnchantmentHelper.func_151384_a((EntityLivingBase)var1, this);
               }

               EnchantmentHelper.func_151385_b(this, var1);
               ItemStack var16 = this.getCurrentEquippedItem();
               Object var17 = var1;
               if (var1 instanceof EntityDragonPart) {
                  IEntityMultiPart var18 = ((EntityDragonPart)var1).entityDragonObj;
                  if (var18 instanceof EntityLivingBase) {
                     var17 = (EntityLivingBase)var18;
                  }
               }

               if (var16 != null && var17 instanceof EntityLivingBase) {
                  var16.hitEntity((EntityLivingBase)var17, this);
                  if (var16.stackSize <= 0) {
                     this.destroyCurrentEquippedItem();
                  }
               }

               if (var1 instanceof EntityLivingBase) {
                  this.addStat(StatList.damageDealtStat, Math.round(var2 * 10.0F));
                  if (var8 > 0) {
                     var1.setFire(var8 * 4);
                  }
               }

               this.addExhaustion(0.3F);
            } else if (var7) {
               var1.extinguish();
            }
         }
      }

   }

   public boolean sendCommandFeedback() {
      return MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("sendCommandFeedback");
   }

   public ItemStack getCurrentEquippedItem() {
      return this.inventory.getCurrentItem();
   }

   public boolean func_175142_cm() {
      return this.capabilities.allowEdit;
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }

   public boolean isSpawnForced() {
      return this.spawnForced;
   }

   public void playSound(String var1, float var2, float var3) {
      this.worldObj.playSoundToNearExcept(this, var1, var2, var3);
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if (this.func_180431_b(var1)) {
         return false;
      } else if (this.capabilities.disableDamage && !var1.canHarmInCreative()) {
         return false;
      } else {
         this.entityAge = 0;
         if (this.getHealth() <= 0.0F) {
            return false;
         } else {
            if (this.isPlayerSleeping() && !this.worldObj.isRemote) {
               this.wakeUpPlayer(true, true, false);
            }

            if (var1.isDifficultyScaled()) {
               if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
                  var2 = 0.0F;
               }

               if (this.worldObj.getDifficulty() == EnumDifficulty.EASY) {
                  var2 = var2 / 2.0F + 1.0F;
               }

               if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                  var2 = var2 * 3.0F / 2.0F;
               }
            }

            if (var2 == 0.0F) {
               return false;
            } else {
               Entity var3 = var1.getEntity();
               if (var3 instanceof EntityArrow && ((EntityArrow)var3).shootingEntity != null) {
                  var3 = ((EntityArrow)var3).shootingEntity;
               }

               return super.attackEntityFrom(var1, var2);
            }
         }
      }
   }

   public float getBedOrientationInDegrees() {
      if (this.playerLocation != null) {
         EnumFacing var1 = (EnumFacing)this.worldObj.getBlockState(this.playerLocation).getValue(BlockDirectional.AGE);
         switch(var1) {
         case SOUTH:
            return 90.0F;
         case NORTH:
            return 270.0F;
         case WEST:
            return 0.0F;
         case EAST:
            return 180.0F;
         }
      }

      return 0.0F;
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)0);
      this.dataWatcher.addObject(17, 0.0F);
      this.dataWatcher.addObject(18, 0);
      this.dataWatcher.addObject(10, (byte)0);
   }

   public boolean canAttackPlayer(EntityPlayer var1) {
      Team var2 = this.getTeam();
      Team var3 = var1.getTeam();
      return var2 == null ? true : (!var2.isSameTeam(var3) ? true : var2.getAllowFriendlyFire());
   }

   public void onEnchantmentCritical(Entity var1) {
   }

   public boolean isEntityInsideOpaqueBlock() {
      return !this.sleeping && super.isEntityInsideOpaqueBlock();
   }

   protected void resetHeight() {
      if (!this.func_175149_v()) {
         super.resetHeight();
      }

   }

   public void onUpdate() {
      this.noClip = this.func_175149_v();
      if (this.func_175149_v()) {
         this.onGround = false;
      }

      if (this.itemInUse != null) {
         ItemStack var1 = this.inventory.getCurrentItem();
         if (var1 == this.itemInUse) {
            if (this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0) {
               this.updateItemUse(var1, 5);
            }

            if (--this.itemInUseCount == 0 && !this.worldObj.isRemote) {
               this.onItemUseFinish();
            }
         } else {
            this.clearItemInUse();
         }
      }

      if (this.xpCooldown > 0) {
         --this.xpCooldown;
      }

      if (this.isPlayerSleeping()) {
         ++this.sleepTimer;
         if (this.sleepTimer > 100) {
            this.sleepTimer = 100;
         }

         if (!this.worldObj.isRemote) {
            if (!this.func_175143_p()) {
               this.wakeUpPlayer(true, true, false);
            } else if (this.worldObj.isDaytime()) {
               this.wakeUpPlayer(false, true, true);
            }
         }
      } else if (this.sleepTimer > 0) {
         ++this.sleepTimer;
         if (this.sleepTimer >= 110) {
            this.sleepTimer = 0;
         }
      }

      super.onUpdate();
      if (!this.worldObj.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
         this.closeScreen();
         this.openContainer = this.inventoryContainer;
      }

      if (this.isBurning() && this.capabilities.disableDamage) {
         this.extinguish();
      }

      this.field_71091_bM = this.field_71094_bP;
      this.field_71096_bN = this.field_71095_bQ;
      this.field_71097_bO = this.field_71085_bR;
      double var14 = this.posX - this.field_71094_bP;
      double var3 = this.posY - this.field_71095_bQ;
      double var5 = this.posZ - this.field_71085_bR;
      double var7 = 10.0D;
      if (var14 > var7) {
         this.field_71091_bM = this.field_71094_bP = this.posX;
      }

      if (var5 > var7) {
         this.field_71097_bO = this.field_71085_bR = this.posZ;
      }

      if (var3 > var7) {
         this.field_71096_bN = this.field_71095_bQ = this.posY;
      }

      if (var14 < -var7) {
         this.field_71091_bM = this.field_71094_bP = this.posX;
      }

      if (var5 < -var7) {
         this.field_71097_bO = this.field_71085_bR = this.posZ;
      }

      if (var3 < -var7) {
         this.field_71096_bN = this.field_71095_bQ = this.posY;
      }

      this.field_71094_bP += var14 * 0.25D;
      this.field_71085_bR += var5 * 0.25D;
      this.field_71095_bQ += var3 * 0.25D;
      if (this.ridingEntity == null) {
         this.startMinecartRidingCoordinate = null;
      }

      if (!this.worldObj.isRemote) {
         this.foodStats.onUpdate(this);
         this.triggerAchievement(StatList.minutesPlayedStat);
         if (this.isEntityAlive()) {
            this.triggerAchievement(StatList.timeSinceDeathStat);
         }
      }

      int var9 = 29999999;
      double var10 = MathHelper.clamp_double(this.posX, -2.9999999E7D, 2.9999999E7D);
      double var12 = MathHelper.clamp_double(this.posZ, -2.9999999E7D, 2.9999999E7D);
      if (var10 != this.posX || var12 != this.posZ) {
         this.setPosition(var10, this.posY, var12);
      }

   }

   protected boolean canTriggerWalking() {
      return !this.capabilities.isFlying;
   }

   public InventoryEnderChest getInventoryEnderChest() {
      return this.theInventoryEnderChest;
   }

   public int getItemInUseCount() {
      return this.itemInUseCount;
   }

   public void addExperience(int var1) {
      this.addScore(var1);
      int var2 = Integer.MAX_VALUE - this.experienceTotal;
      if (var1 > var2) {
         var1 = var2;
      }

      this.experience += (float)var1 / (float)this.xpBarCap();

      for(this.experienceTotal += var1; this.experience >= 1.0F; this.experience /= (float)this.xpBarCap()) {
         this.experience = (this.experience - 1.0F) * (float)this.xpBarCap();
         this.addExperienceLevel(1);
      }

   }

   public void updateRidden() {
      if (!this.worldObj.isRemote && this.isSneaking()) {
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
         if (this.ridingEntity instanceof EntityPig) {
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

   public void stopUsingItem() {
      if (this.itemInUse != null) {
         this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
      }

      this.clearItemInUse();
   }

   public void func_146095_a(CommandBlockLogic var1) {
   }

   public void fall(float var1, float var2) {
      if (!this.capabilities.allowFlying) {
         if (var1 >= 2.0F) {
            this.addStat(StatList.distanceFallenStat, (int)Math.round((double)var1 * 100.0D));
         }

         super.fall(var1, var2);
      }

   }

   public void addMovementStat(double var1, double var3, double var5) {
      if (this.ridingEntity == null) {
         int var7;
         if (this.isInsideOfMaterial(Material.water)) {
            var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5) * 100.0F);
            if (var7 > 0) {
               this.addStat(StatList.distanceDoveStat, var7);
               this.addExhaustion(0.015F * (float)var7 * 0.01F);
            }
         } else if (this.isInWater()) {
            var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var5 * var5) * 100.0F);
            if (var7 > 0) {
               this.addStat(StatList.distanceSwumStat, var7);
               this.addExhaustion(0.015F * (float)var7 * 0.01F);
            }
         } else if (this.isOnLadder()) {
            if (var3 > 0.0D) {
               this.addStat(StatList.distanceClimbedStat, (int)Math.round(var3 * 100.0D));
            }
         } else if (this.onGround) {
            var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var5 * var5) * 100.0F);
            if (var7 > 0) {
               this.addStat(StatList.distanceWalkedStat, var7);
               if (this.isSprinting()) {
                  this.addStat(StatList.distanceSprintedStat, var7);
                  this.addExhaustion(0.099999994F * (float)var7 * 0.01F);
               } else {
                  if (this.isSneaking()) {
                     this.addStat(StatList.distanceCrouchedStat, var7);
                  }

                  this.addExhaustion(0.01F * (float)var7 * 0.01F);
               }
            }
         } else {
            var7 = Math.round(MathHelper.sqrt_double(var1 * var1 + var5 * var5) * 100.0F);
            if (var7 > 25) {
               this.addStat(StatList.distanceFlownStat, var7);
            }
         }
      }

   }

   public void displayVillagerTradeGui(IMerchant var1) {
   }

   public EntityItem dropPlayerItemWithRandomChoice(ItemStack var1, boolean var2) {
      return this.func_146097_a(var1, false, false);
   }

   public void respawnPlayer() {
   }

   protected String getSwimSound() {
      return "game.player.swim";
   }

   protected boolean isMovementBlocked() {
      return this.getHealth() <= 0.0F || this.isPlayerSleeping();
   }

   public boolean isInvisibleToPlayer(EntityPlayer var1) {
      if (!this.isInvisible()) {
         return false;
      } else if (var1.func_175149_v()) {
         return false;
      } else {
         Team var2 = this.getTeam();
         return var2 == null || var1 == null || var1.getTeam() != var2 || !var2.func_98297_h();
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
      var1.setInteger("XpSeed", this.field_175152_f);
      var1.setInteger("Score", this.getScore());
      if (this.spawnChunk != null) {
         var1.setInteger("SpawnX", this.spawnChunk.getX());
         var1.setInteger("SpawnY", this.spawnChunk.getY());
         var1.setInteger("SpawnZ", this.spawnChunk.getZ());
         var1.setBoolean("SpawnForced", this.spawnForced);
      }

      this.foodStats.writeNBT(var1);
      this.capabilities.writeCapabilitiesToNBT(var1);
      var1.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
      ItemStack var2 = this.inventory.getCurrentItem();
      if (var2 != null && var2.getItem() != null) {
         var1.setTag("SelectedItem", var2.writeToNBT(new NBTTagCompound()));
      }

   }

   public static enum EnumStatus {
      OK("OK", 0),
      NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1),
      OTHER_PROBLEM("OTHER_PROBLEM", 4),
      NOT_SAFE("NOT_SAFE", 5),
      TOO_FAR_AWAY("TOO_FAR_AWAY", 3);

      private static final EntityPlayer.EnumStatus[] ENUM$VALUES = new EntityPlayer.EnumStatus[]{OK, NOT_POSSIBLE_HERE, NOT_POSSIBLE_NOW, TOO_FAR_AWAY, OTHER_PROBLEM, NOT_SAFE};
      NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW", 2);

      private static final EntityPlayer.EnumStatus[] $VALUES = new EntityPlayer.EnumStatus[]{OK, NOT_POSSIBLE_HERE, NOT_POSSIBLE_NOW, TOO_FAR_AWAY, OTHER_PROBLEM, NOT_SAFE};
      private static final String __OBFID = "CL_00001712";

      private EnumStatus(String var3, int var4) {
      }
   }

   public static enum EnumChatVisibility {
      SYSTEM("SYSTEM", 1, 1, "options.chat.visibility.system");

      private static final EntityPlayer.EnumChatVisibility[] field_151432_d = new EntityPlayer.EnumChatVisibility[values().length];
      private static final EntityPlayer.EnumChatVisibility[] ENUM$VALUES = new EntityPlayer.EnumChatVisibility[]{FULL, SYSTEM, HIDDEN};
      FULL("FULL", 0, 0, "options.chat.visibility.full");

      private final int chatVisibility;
      HIDDEN("HIDDEN", 2, 2, "options.chat.visibility.hidden");

      private final String resourceKey;
      private static final EntityPlayer.EnumChatVisibility[] $VALUES = new EntityPlayer.EnumChatVisibility[]{FULL, SYSTEM, HIDDEN};
      private static final String __OBFID = "CL_00001714";

      public static EntityPlayer.EnumChatVisibility getEnumChatVisibility(int var0) {
         return field_151432_d[var0 % field_151432_d.length];
      }

      public int getChatVisibility() {
         return this.chatVisibility;
      }

      static {
         EntityPlayer.EnumChatVisibility[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            EntityPlayer.EnumChatVisibility var3 = var0[var2];
            field_151432_d[var3.chatVisibility] = var3;
         }

      }

      private EnumChatVisibility(String var3, int var4, int var5, String var6) {
         this.chatVisibility = var5;
         this.resourceKey = var6;
      }

      public String getResourceKey() {
         return this.resourceKey;
      }
   }

   static final class SwitchEnumFacing {
      static final int[] field_179420_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002188";

      static {
         try {
            field_179420_a[EnumFacing.SOUTH.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_179420_a[EnumFacing.NORTH.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_179420_a[EnumFacing.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_179420_a[EnumFacing.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
