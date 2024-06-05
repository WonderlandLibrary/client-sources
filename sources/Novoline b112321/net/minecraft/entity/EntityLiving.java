package net.minecraft.entity;

import java.util.UUID;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.Reflector;

public abstract class EntityLiving extends EntityLivingBase {
   public int livingSoundTime;
   protected int experienceValue;
   private EntityLookHelper lookHelper;
   protected EntityMoveHelper moveHelper;
   protected EntityJumpHelper jumpHelper;
   private EntityBodyHelper bodyHelper;
   protected PathNavigate navigator;
   protected final EntityAITasks tasks;
   protected final EntityAITasks targetTasks;
   private EntityLivingBase attackTarget;
   private EntitySenses senses;
   private ItemStack[] equipment = new ItemStack[5];
   protected float[] equipmentDropChances = new float[5];
   private boolean canPickUpLoot;
   private boolean persistenceRequired;
   private boolean isLeashed;
   private Entity leashedToEntity;
   private NBTTagCompound leashNBTTag;
   private static final String be = "CL_00001550";
   public int randomMobsId = 0;
   public BiomeGenBase spawnBiome = null;
   public BlockPos spawnPosition = null;

   public EntityLiving(World var1) {
      super(var1);
      this.tasks = new EntityAITasks(var1.theProfiler != null?var1.theProfiler:null);
      this.targetTasks = new EntityAITasks(var1.theProfiler != null?var1.theProfiler:null);
      this.lookHelper = new EntityLookHelper(this);
      this.moveHelper = new EntityMoveHelper(this);
      this.jumpHelper = new EntityJumpHelper(this);
      this.bodyHelper = new EntityBodyHelper(this);
      this.navigator = this.getNewNavigator(var1);
      this.senses = new EntitySenses(this);

      for(int var2 = 0; var2 < this.equipmentDropChances.length; ++var2) {
         this.equipmentDropChances[var2] = 0.085F;
      }

      UUID var5 = this.getUniqueID();
      long var3 = var5.getLeastSignificantBits();
      this.randomMobsId = (int)(var3 & 2147483647L);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
   }

   protected PathNavigate getNewNavigator(World var1) {
      return new PathNavigateGround(this, var1);
   }

   public EntityLookHelper getLookHelper() {
      return this.lookHelper;
   }

   public EntityMoveHelper getMoveHelper() {
      return this.moveHelper;
   }

   public EntityJumpHelper getJumpHelper() {
      return this.jumpHelper;
   }

   public PathNavigate getNavigator() {
      return this.navigator;
   }

   public EntitySenses getEntitySenses() {
      return this.senses;
   }

   public EntityLivingBase getAttackTarget() {
      return this.attackTarget;
   }

   public void setAttackTarget(EntityLivingBase var1) {
      this.attackTarget = var1;
      Reflector.a(Reflector.M, new Object[]{this, var1});
   }

   public boolean canAttackClass(Class var1) {
      return var1 != EntityGhast.class;
   }

   public void eatGrassBonus() {
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(15, Byte.valueOf((byte)0));
   }

   public int getTalkInterval() {
      return 80;
   }

   public void playLivingSound() {
      String var1 = this.getLivingSound();
      this.playSound(var1, this.getSoundVolume(), this.getSoundPitch());
   }

   public void onEntityUpdate() {
      super.onEntityUpdate();
      this.worldObj.theProfiler.startSection("mobBaseTick");
      if(this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
         this.livingSoundTime = -this.getTalkInterval();
         this.playLivingSound();
      }

      this.worldObj.theProfiler.endSection();
   }

   protected int getExperiencePoints(EntityPlayer var1) {
      if(this.experienceValue > 0) {
         int var2 = this.experienceValue;
         ItemStack[] var3 = this.getInventory();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            if(var3[var4] != null && this.equipmentDropChances[var4] <= 1.0F) {
               var2 += 1 + this.rand.nextInt(3);
            }
         }

         return var2;
      } else {
         return this.experienceValue;
      }
   }

   public void spawnExplosionParticle() {
      if(this.worldObj.isRemote) {
         for(int var1 = 0; var1 < 20; ++var1) {
            double var2 = this.rand.nextGaussian() * 0.02D;
            double var4 = this.rand.nextGaussian() * 0.02D;
            double var6 = this.rand.nextGaussian() * 0.02D;
            double var8 = 10.0D;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - var2 * var8, this.posY + (double)(this.rand.nextFloat() * this.height) - var4 * var8, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - var6 * var8, var2, var4, var6, new int[0]);
         }
      } else {
         this.worldObj.setEntityState(this, (byte)20);
      }

   }

   public void handleStatusUpdate(byte var1) {
      if(var1 == 20) {
         this.spawnExplosionParticle();
      } else {
         super.handleStatusUpdate(var1);
      }

   }

   public void onUpdate() {
      if(Config.isSmoothWorld() && this.canSkipUpdate()) {
         this.onUpdateMinimal();
      } else {
         super.onUpdate();
         if(!this.worldObj.isRemote) {
            this.updateLeashedState();
         }
      }

   }

   protected float func_110146_f(float var1, float var2) {
      this.bodyHelper.updateRenderAngles();
      return var2;
   }

   protected String getLivingSound() {
      return null;
   }

   protected Item getDropItem() {
      return null;
   }

   protected void dropFewItems(boolean var1, int var2) {
      Item var3 = this.getDropItem();
      int var4 = this.rand.nextInt(3);
      var4 = var4 + this.rand.nextInt(var2 + 1);

      for(int var5 = 0; var5 < var4; ++var5) {
         this.dropItem(var3, 1);
      }

   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setBoolean("CanPickUpLoot", this.canPickUpLoot());
      var1.setBoolean("PersistenceRequired", this.persistenceRequired);
      NBTTagList var2 = new NBTTagList();

      for(ItemStack var6 : this.equipment) {
         NBTTagCompound var7 = new NBTTagCompound();
         var6.writeToNBT(var7);
         var2.appendTag(var7);
      }

      var1.setTag("Equipment", var2);
      NBTTagList var8 = new NBTTagList();

      for(float var14 : this.equipmentDropChances) {
         var8.appendTag(new NBTTagFloat(var14));
      }

      var1.setTag("DropChances", var8);
      var1.setBoolean("Leashed", this.isLeashed);
      if(this.leashedToEntity != null) {
         NBTTagCompound var10 = new NBTTagCompound();
         if(this.leashedToEntity instanceof EntityLivingBase) {
            var10.setLong("UUIDMost", this.leashedToEntity.getUniqueID().getMostSignificantBits());
            var10.setLong("UUIDLeast", this.leashedToEntity.getUniqueID().getLeastSignificantBits());
         } else if(this.leashedToEntity instanceof EntityHanging) {
            BlockPos var12 = ((EntityHanging)this.leashedToEntity).getHangingPosition();
            var10.setInteger("X", var12.getX());
            var10.setInteger("Y", var12.getY());
            var10.setInteger("Z", var12.getZ());
         }

         var1.setTag("Leash", var10);
      }

      if(this.isAIDisabled()) {
         var1.setBoolean("NoAI", this.isAIDisabled());
      }

   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      if(var1.hasKey("CanPickUpLoot", 1)) {
         this.setCanPickUpLoot(var1.getBoolean("CanPickUpLoot"));
      }

      this.persistenceRequired = var1.getBoolean("PersistenceRequired");
      if(var1.hasKey("Equipment", 9)) {
         NBTTagList var2 = var1.getTagList("Equipment", 10);

         for(int var3 = 0; var3 < this.equipment.length; ++var3) {
            this.equipment[var3] = ItemStack.loadItemStackFromNBT(var2.getCompoundTagAt(var3));
         }
      }

      if(var1.hasKey("DropChances", 9)) {
         NBTTagList var4 = var1.getTagList("DropChances", 5);

         for(int var5 = 0; var5 < var4.tagCount(); ++var5) {
            this.equipmentDropChances[var5] = var4.getFloatAt(var5);
         }
      }

      this.isLeashed = var1.getBoolean("Leashed");
      if(this.isLeashed && var1.hasKey("Leash", 10)) {
         this.leashNBTTag = var1.getCompoundTag("Leash");
      }

      this.setNoAI(var1.getBoolean("NoAI"));
   }

   public void setMoveForward(float var1) {
      this.moveForward = var1;
   }

   public void setAIMoveSpeed(float var1) {
      super.setAIMoveSpeed(var1);
      this.setMoveForward(var1);
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      this.worldObj.theProfiler.startSection("looting");
      if(!this.worldObj.isRemote && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getBoolean("mobGriefing")) {
         for(EntityItem var2 : this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D))) {
            if(!var2.isDead && var2.getEntityItem() != null && !var2.cannotPickup()) {
               this.updateEquipmentIfNeeded(var2);
            }
         }
      }

      this.worldObj.theProfiler.endSection();
   }

   protected void updateEquipmentIfNeeded(EntityItem var1) {
      ItemStack var2 = var1.getEntityItem();
      int var3 = getArmorPosition(var2);
      if(var3 > -1) {
         boolean var4 = true;
         ItemStack var5 = this.getEquipmentInSlot(var3);
         if(var2.getItem() instanceof ItemSword && !(var5.getItem() instanceof ItemSword)) {
            var4 = true;
         } else if(var2.getItem() instanceof ItemSword && var5.getItem() instanceof ItemSword) {
            ItemSword var6 = (ItemSword)var2.getItem();
            ItemSword var7 = (ItemSword)var5.getItem();
            if(var6.getDamageVsEntity() != var7.getDamageVsEntity()) {
               var4 = var6.getDamageVsEntity() > var7.getDamageVsEntity();
            } else if(var2.getMetadata() <= var5.getMetadata() && (!var2.hasTagCompound() || var5.hasTagCompound())) {
               boolean var13 = false;
            } else {
               boolean var10000 = true;
            }
         } else if(var2.getItem() instanceof ItemBow && var5.getItem() instanceof ItemBow) {
            var4 = var2.hasTagCompound() && !var5.hasTagCompound();
         } else {
            var4 = false;
         }

         if(this.func_175448_a(var2)) {
            if(this.rand.nextFloat() - 0.1F < this.equipmentDropChances[var3]) {
               this.entityDropItem(var5, 0.0F);
            }

            if(var2.getItem() == Items.diamond && var1.getThrower() != null) {
               EntityPlayer var12 = this.worldObj.getPlayerEntityByName(var1.getThrower());
               var12.triggerAchievement(AchievementList.diamondsToYou);
            }

            this.setCurrentItemOrArmor(var3, var2);
            this.equipmentDropChances[var3] = 2.0F;
            this.persistenceRequired = true;
            this.onItemPickup(var1, 1);
            var1.setDead();
         }
      }

   }

   protected boolean func_175448_a(ItemStack var1) {
      return true;
   }

   protected boolean canDespawn() {
      return true;
   }

   protected void despawnEntity() {
      Object var1 = null;
      Object var2 = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
      Object var3 = Reflector.getFieldValue(Reflector.Event_Result_DENY);
      if(this.persistenceRequired) {
         this.entityAge = 0;
      } else if((this.entityAge & 31) == 31 && (var1 = Reflector.f(Reflector.n, new Object[]{this})) != var2) {
         if(var1 == var3) {
            this.entityAge = 0;
         } else {
            this.setDead();
         }
      } else {
         EntityPlayer var4 = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
         double var5 = var4.posX - this.posX;
         double var7 = var4.posY - this.posY;
         double var9 = var4.posZ - this.posZ;
         double var11 = var5 * var5 + var7 * var7 + var9 * var9;
         if(this.canDespawn() && var11 > 16384.0D) {
            this.setDead();
         }

         if(this.entityAge > 600 && this.rand.nextInt(800) == 0 && var11 > 1024.0D && this.canDespawn()) {
            this.setDead();
         } else if(var11 < 1024.0D) {
            this.entityAge = 0;
         }
      }

   }

   protected final void updateEntityActionState() {
      ++this.entityAge;
      this.worldObj.theProfiler.startSection("checkDespawn");
      this.despawnEntity();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("sensing");
      this.senses.clearSensingCache();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("targetSelector");
      this.targetTasks.onUpdateTasks();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("goalSelector");
      this.tasks.onUpdateTasks();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("navigation");
      this.navigator.onUpdateNavigation();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("mob tick");
      this.updateAITasks();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("controls");
      this.worldObj.theProfiler.startSection("move");
      this.moveHelper.onUpdateMoveHelper();
      this.worldObj.theProfiler.endStartSection("look");
      this.lookHelper.onUpdateLook();
      this.worldObj.theProfiler.endStartSection("jump");
      this.jumpHelper.doJump();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.endSection();
   }

   protected void updateAITasks() {
   }

   public int getVerticalFaceSpeed() {
      return 40;
   }

   public void faceEntity(Entity var1, float var2, float var3) {
      double var4 = var1.posX - this.posX;
      double var6 = var1.posZ - this.posZ;
      double var8;
      if(var1 instanceof EntityLivingBase) {
         EntityLivingBase var10 = (EntityLivingBase)var1;
         var8 = var10.posY + (double)var10.getEyeHeight() - (this.posY + (double)this.getEyeHeight());
      } else {
         var8 = (var1.getEntityBoundingBox().minY + var1.getEntityBoundingBox().maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
      }

      double var14 = (double)MathHelper.sqrt_double(var4 * var4 + var6 * var6);
      float var12 = (float)(MathHelper.func_181159_b(var6, var4) * 180.0D / 3.141592653589793D) - 90.0F;
      float var13 = (float)(-(MathHelper.func_181159_b(var8, var14) * 180.0D / 3.141592653589793D));
      this.rotationPitch = this.updateRotation(this.rotationPitch, var13, var3);
      this.rotationYaw = this.updateRotation(this.rotationYaw, var12, var2);
   }

   private float updateRotation(float var1, float var2, float var3) {
      float var4 = MathHelper.wrapAngleTo180_float(var2 - var1);
      if(var4 > var3) {
         var4 = var3;
      }

      if(var4 < -var3) {
         var4 = -var3;
      }

      return var1 + var4;
   }

   public boolean getCanSpawnHere() {
      return true;
   }

   public boolean isNotColliding() {
      return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
   }

   public float getRenderSizeModifier() {
      return 1.0F;
   }

   public int getMaxSpawnedInChunk() {
      return 4;
   }

   public int getMaxFallHeight() {
      if(this.getAttackTarget() == null) {
         return 3;
      } else {
         int var1 = (int)(this.getHealth() - this.getMaxHealth() * 0.33F);
         var1 = var1 - (3 - this.worldObj.getDifficulty().getDifficultyId()) * 4;
         var1 = 0;
         return var1 + 3;
      }
   }

   public ItemStack getHeldItem() {
      return this.equipment[0];
   }

   public ItemStack getEquipmentInSlot(int var1) {
      return this.equipment[var1];
   }

   public ItemStack getCurrentArmor(int var1) {
      return this.equipment[var1 + 1];
   }

   public void setCurrentItemOrArmor(int var1, ItemStack var2) {
      this.equipment[var1] = var2;
   }

   public ItemStack[] getInventory() {
      return this.equipment;
   }

   protected void dropEquipment(boolean var1, int var2) {
      for(int var3 = 0; var3 < this.getInventory().length; ++var3) {
         ItemStack var4 = this.getEquipmentInSlot(var3);
         boolean var5 = this.equipmentDropChances[var3] > 1.0F;
         if(this.rand.nextFloat() - (float)var2 * 0.01F < this.equipmentDropChances[var3]) {
            if(var4.isItemStackDamageable()) {
               int var6 = Math.max(var4.getMaxDamage() - 25, 1);
               int var7 = var4.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(var6) + 1);
               if(var7 > var6) {
                  var7 = var6;
               }

               if(var7 < 1) {
                  var7 = 1;
               }

               var4.setItemDamage(var7);
            }

            this.entityDropItem(var4, 0.0F);
         }
      }

   }

   protected void setEquipmentBasedOnDifficulty(DifficultyInstance var1) {
      if(this.rand.nextFloat() < 0.15F * var1.getClampedAdditionalDifficulty()) {
         int var2 = this.rand.nextInt(2);
         float var3 = this.worldObj.getDifficulty() == EnumDifficulty.HARD?0.1F:0.25F;
         if(this.rand.nextFloat() < 0.095F) {
            ++var2;
         }

         if(this.rand.nextFloat() < 0.095F) {
            ++var2;
         }

         if(this.rand.nextFloat() < 0.095F) {
            ++var2;
         }

         int var4 = 3;

         while(true) {
            ItemStack var5 = this.getCurrentArmor(var4);
            if(var4 < 3 && this.rand.nextFloat() < var3) {
               break;
            }

            Item var6 = getArmorItemForSlot(var4 + 1, var2);
            this.setCurrentItemOrArmor(var4 + 1, new ItemStack(var6));
            --var4;
         }
      }

   }

   public static int getArmorPosition(ItemStack var0) {
      if(var0.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && var0.getItem() != Items.skull) {
         if(var0.getItem() instanceof ItemArmor) {
            switch(((ItemArmor)var0.getItem()).armorType) {
            case 0:
               return 4;
            case 1:
               return 3;
            case 2:
               return 2;
            case 3:
               return 1;
            }
         }

         return 0;
      } else {
         return 4;
      }
   }

   public static Item getArmorItemForSlot(int var0, int var1) {
      switch(var0) {
      case 1:
         return Items.leather_boots;
      case 2:
         return Items.leather_leggings;
      case 3:
         return Items.leather_chestplate;
      case 4:
         return Items.leather_helmet;
      default:
         return null;
      }
   }

   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance var1) {
      float var2 = var1.getClampedAdditionalDifficulty();
      if(this.getHeldItem() != null && this.rand.nextFloat() < 0.25F * var2) {
         EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(5.0F + var2 * (float)this.rand.nextInt(18)));
      }

      for(int var3 = 0; var3 < 4; ++var3) {
         ItemStack var4 = this.getCurrentArmor(var3);
         if(this.rand.nextFloat() < 0.5F * var2) {
            EnchantmentHelper.addRandomEnchantment(this.rand, var4, (int)(5.0F + var2 * (float)this.rand.nextInt(18)));
         }
      }

   }

   public IEntityLivingData onInitialSpawn(DifficultyInstance var1, IEntityLivingData var2) {
      this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
      return var2;
   }

   public boolean canBeSteered() {
      return false;
   }

   public void enablePersistence() {
      this.persistenceRequired = true;
   }

   public void setEquipmentDropChance(int var1, float var2) {
      this.equipmentDropChances[var1] = var2;
   }

   public boolean canPickUpLoot() {
      return this.canPickUpLoot;
   }

   public void setCanPickUpLoot(boolean var1) {
      this.canPickUpLoot = var1;
   }

   public boolean isNoDespawnRequired() {
      return this.persistenceRequired;
   }

   public final boolean interactFirst(EntityPlayer var1) {
      if(this.getLeashed() && this.getLeashedToEntity() == var1) {
         this.clearLeashed(true, !var1.abilities.isCreative());
         return true;
      } else {
         ItemStack var2 = var1.inventory.getCurrentItem();
         if(var2.getItem() == Items.lead && this.allowLeashing()) {
            if(!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
               this.setLeashedToEntity(var1, true);
               --var2.stackSize;
               return true;
            }

            if(((EntityTameable)this).isOwner(var1)) {
               this.setLeashedToEntity(var1, true);
               --var2.stackSize;
               return true;
            }
         }

         return this.interact(var1) || super.interactFirst(var1);
      }
   }

   protected boolean interact(EntityPlayer var1) {
      return false;
   }

   protected void updateLeashedState() {
      if(this.leashNBTTag != null) {
         this.recreateLeash();
      }

      if(this.isLeashed) {
         if(!this.isEntityAlive()) {
            this.clearLeashed(true, true);
         }

         if(this.leashedToEntity == null || this.leashedToEntity.isDead) {
            this.clearLeashed(true, true);
         }
      }

   }

   public void clearLeashed(boolean var1, boolean var2) {
      if(this.isLeashed) {
         this.isLeashed = false;
         this.leashedToEntity = null;
         if(!this.worldObj.isRemote) {
            this.dropItem(Items.lead, 1);
         }

         if(!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(1, this, (Entity)null));
         }
      }

   }

   public boolean allowLeashing() {
      return !this.getLeashed() && !(this instanceof IMob);
   }

   public boolean getLeashed() {
      return this.isLeashed;
   }

   public Entity getLeashedToEntity() {
      return this.leashedToEntity;
   }

   public void setLeashedToEntity(Entity var1, boolean var2) {
      this.isLeashed = true;
      this.leashedToEntity = var1;
      if(!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
         ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(1, this, this.leashedToEntity));
      }

   }

   private void recreateLeash() {
      if(this.isLeashed && this.leashNBTTag != null) {
         if(this.leashNBTTag.hasKey("UUIDMost", 4) && this.leashNBTTag.hasKey("UUIDLeast", 4)) {
            UUID var4 = new UUID(this.leashNBTTag.getLong("UUIDMost"), this.leashNBTTag.getLong("UUIDLeast"));

            for(EntityLivingBase var3 : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(10.0D, 10.0D, 10.0D))) {
               if(var3.getUniqueID().equals(var4)) {
                  this.leashedToEntity = var3;
                  break;
               }
            }
         } else if(this.leashNBTTag.hasKey("X", 99) && this.leashNBTTag.hasKey("Y", 99) && this.leashNBTTag.hasKey("Z", 99)) {
            BlockPos var1 = new BlockPos(this.leashNBTTag.getInteger("X"), this.leashNBTTag.getInteger("Y"), this.leashNBTTag.getInteger("Z"));
            EntityLeashKnot var2 = EntityLeashKnot.getKnotForPosition(this.worldObj, var1);
            var2 = EntityLeashKnot.createKnot(this.worldObj, var1);
            this.leashedToEntity = var2;
         } else {
            this.clearLeashed(false, true);
         }
      }

      this.leashNBTTag = null;
   }

   public boolean replaceItemInInventory(int var1, ItemStack var2) {
      int var3;
      if(var1 == 99) {
         var3 = 0;
      } else {
         var3 = var1 - 100 + 1;
         if(var3 >= this.equipment.length) {
            return false;
         }
      }

      if(getArmorPosition(var2) != var3 && (var3 != 4 || !(var2.getItem() instanceof ItemBlock))) {
         return false;
      } else {
         this.setCurrentItemOrArmor(var3, var2);
         return true;
      }
   }

   public boolean isServerWorld() {
      return super.isServerWorld() && !this.isAIDisabled();
   }

   public void setNoAI(boolean var1) {
      this.I.a(15, Byte.valueOf((byte)1));
   }

   public boolean isAIDisabled() {
      return this.I.g(15) != 0;
   }

   public boolean isEntityInsideOpaqueBlock() {
      if(!this.noClip) {
         BlockPosM var1 = new BlockPosM(0, 0, 0);

         for(int var2 = 0; var2 < 8; ++var2) {
            double var3 = this.posX + (double)(((float)((var2 >> 0) % 2) - 0.5F) * this.width * 0.8F);
            double var5 = this.posY + (double)(((float)((var2 >> 1) % 2) - 0.5F) * 0.1F);
            double var7 = this.posZ + (double)(((float)((var2 >> 2) % 2) - 0.5F) * this.width * 0.8F);
            var1.setXyz(var3, var5 + (double)this.getEyeHeight(), var7);
            if(this.worldObj.getBlockState(var1).getBlock().isVisuallyOpaque()) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean canSkipUpdate() {
      if(this.isChild()) {
         return false;
      } else if(this.hurtTime > 0) {
         return false;
      } else if(this.ticksExisted < 20) {
         return false;
      } else {
         World var1 = this.getEntityWorld();
         return false;
      }
   }

   private void onUpdateMinimal() {
      ++this.entityAge;
      if(this instanceof EntityMob) {
         float var1 = this.getBrightness(1.0F);
         if(var1 > 0.5F) {
            this.entityAge += 2;
         }
      }

      this.despawnEntity();
   }
}
