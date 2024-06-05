package net.minecraft.entity.monster;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie$GroupData;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityZombie extends EntityMob {
   protected static final IAttribute reinforcementChance = (new RangedAttribute((IAttribute)null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");
   private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
   private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
   private final EntityAIBreakDoor breakDoor = new EntityAIBreakDoor(this);
   private int conversionTime;
   private boolean isBreakDoorsTaskSet = false;
   private float zombieWidth = -1.0F;
   private float zombieHeight;

   public EntityZombie(World var1) {
      super(var1);
      ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
      this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
      this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
      this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(8, new EntityAILookIdle(this));
      this.applyEntityAI();
      this.setSize(0.6F, 1.95F);
   }

   protected void applyEntityAI() {
      this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
      this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0D, true));
      this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityPigZombie.class}));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
      this.getAttributeMap().registerAttribute(reinforcementChance).setBaseValue(this.rand.nextDouble() * 0.10000000149011612D);
   }

   protected void entityInit() {
      super.entityInit();
      this.k().b(12, Byte.valueOf((byte)0));
      this.k().b(13, Byte.valueOf((byte)0));
      this.k().b(14, Byte.valueOf((byte)0));
   }

   public int getTotalArmorValue() {
      int var1 = super.getTotalArmorValue() + 2;
      if(var1 > 20) {
         var1 = 20;
      }

      return var1;
   }

   public boolean isBreakDoorsTaskSet() {
      return this.isBreakDoorsTaskSet;
   }

   public void setBreakDoorsAItask(boolean var1) {
      if(this.isBreakDoorsTaskSet != var1) {
         this.isBreakDoorsTaskSet = var1;
         this.tasks.addTask(1, this.breakDoor);
      }

   }

   public boolean isChild() {
      return this.k().g(12) == 1;
   }

   protected int getExperiencePoints(EntityPlayer var1) {
      if(this.isChild()) {
         this.experienceValue = (int)((float)this.experienceValue * 2.5F);
      }

      return super.getExperiencePoints(var1);
   }

   public void setChild(boolean var1) {
      this.k().a(12, Byte.valueOf((byte)1));
      if(this.worldObj != null && !this.worldObj.isRemote) {
         IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
         var2.removeModifier(babySpeedBoostModifier);
         var2.applyModifier(babySpeedBoostModifier);
      }

      this.setChildSize(var1);
   }

   public boolean isVillager() {
      return this.k().g(13) == 1;
   }

   public void setVillager(boolean var1) {
      this.k().a(13, Byte.valueOf((byte)1));
   }

   public void onLivingUpdate() {
      if(this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild()) {
         float var1 = this.getBrightness(1.0F);
         BlockPos var2 = new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ);
         if(var1 > 0.5F && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && this.worldObj.canSeeSky(var2)) {
            boolean var3 = true;
            ItemStack var4 = this.getEquipmentInSlot(4);
            if(var4.isItemStackDamageable()) {
               var4.setItemDamage(var4.getItemDamage() + this.rand.nextInt(2));
               if(var4.getItemDamage() >= var4.getMaxDamage()) {
                  this.renderBrokenItemStack(var4);
                  this.setCurrentItemOrArmor(4, (ItemStack)null);
               }
            }

            var3 = false;
            this.setFire(8);
         }
      }

      if(this.isRiding() && this.getAttackTarget() != null && this.ridingEntity instanceof EntityChicken) {
         ((EntityLiving)this.ridingEntity).getNavigator().a(this.getNavigator().getPath(), 1.5D);
      }

      super.onLivingUpdate();
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(super.attackEntityFrom(var1, var2)) {
         EntityLivingBase var3 = this.getAttackTarget();
         if(var1.getEntity() instanceof EntityLivingBase) {
            var3 = (EntityLivingBase)var1.getEntity();
         }

         if(this.worldObj.getDifficulty() == EnumDifficulty.HARD && (double)this.rand.nextFloat() < this.getEntityAttribute(reinforcementChance).getAttributeValue()) {
            int var4 = MathHelper.floor_double(this.posX);
            int var5 = MathHelper.floor_double(this.posY);
            int var6 = MathHelper.floor_double(this.posZ);
            EntityZombie var7 = new EntityZombie(this.worldObj);

            for(int var8 = 0; var8 < 50; ++var8) {
               int var9 = var4 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
               int var10 = var5 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
               int var11 = var6 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
               if(World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(var9, var10 - 1, var11)) && this.worldObj.getLightFromNeighbors(new BlockPos(var9, var10, var11)) < 10) {
                  var7.setPosition((double)var9, (double)var10, (double)var11);
                  if(!this.worldObj.isAnyPlayerWithinRangeAt((double)var9, (double)var10, (double)var11, 7.0D) && this.worldObj.checkNoEntityCollision(var7.getEntityBoundingBox(), var7) && this.worldObj.getCollidingBoundingBoxes(var7, var7.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(var7.getEntityBoundingBox())) {
                     this.worldObj.spawnEntityInWorld(var7);
                     var7.setAttackTarget(var3);
                     var7.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(var7)), (IEntityLivingData)null);
                     this.getEntityAttribute(reinforcementChance).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
                     var7.getEntityAttribute(reinforcementChance).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
                     break;
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void onUpdate() {
      if(!this.worldObj.isRemote && this.isConverting()) {
         int var1 = this.getConversionTimeBoost();
         this.conversionTime -= var1;
         if(this.conversionTime <= 0) {
            this.convertToVillager();
         }
      }

      super.onUpdate();
   }

   public boolean attackEntityAsMob(Entity var1) {
      boolean var2 = super.attackEntityAsMob(var1);
      int var3 = this.worldObj.getDifficulty().getDifficultyId();
      if(this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < (float)var3 * 0.3F) {
         var1.setFire(2 * var3);
      }

      return var2;
   }

   protected String getLivingSound() {
      return "mob.zombie.say";
   }

   protected String getHurtSound() {
      return "mob.zombie.hurt";
   }

   protected String getDeathSound() {
      return "mob.zombie.death";
   }

   protected void playStepSound(BlockPos var1, Block var2) {
      this.playSound("mob.zombie.step", 0.15F, 1.0F);
   }

   protected Item getDropItem() {
      return Items.rotten_flesh;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   protected void addRandomDrop() {
      switch(this.rand.nextInt(3)) {
      case 0:
         this.dropItem(Items.iron_ingot, 1);
         break;
      case 1:
         this.dropItem(Items.carrot, 1);
         break;
      case 2:
         this.dropItem(Items.potato, 1);
      }

   }

   protected void setEquipmentBasedOnDifficulty(DifficultyInstance var1) {
      super.setEquipmentBasedOnDifficulty(var1);
      if(this.rand.nextFloat() < (this.worldObj.getDifficulty() == EnumDifficulty.HARD?0.05F:0.01F)) {
         int var2 = this.rand.nextInt(3);
         this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
      }

   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      if(this.isChild()) {
         var1.setBoolean("IsBaby", true);
      }

      if(this.isVillager()) {
         var1.setBoolean("IsVillager", true);
      }

      var1.setInteger("ConversionTime", this.isConverting()?this.conversionTime:-1);
      var1.setBoolean("CanBreakDoors", this.isBreakDoorsTaskSet());
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      if(var1.getBoolean("IsBaby")) {
         this.setChild(true);
      }

      if(var1.getBoolean("IsVillager")) {
         this.setVillager(true);
      }

      if(var1.hasKey("ConversionTime", 99) && var1.getInteger("ConversionTime") > -1) {
         this.startConversion(var1.getInteger("ConversionTime"));
      }

      this.setBreakDoorsAItask(var1.getBoolean("CanBreakDoors"));
   }

   public void onKillEntity(EntityLivingBase var1) {
      super.onKillEntity(var1);
      if((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) && var1 instanceof EntityVillager) {
         if(this.worldObj.getDifficulty() != EnumDifficulty.HARD && this.rand.nextBoolean()) {
            return;
         }

         EntityLiving var2 = (EntityLiving)var1;
         EntityZombie var3 = new EntityZombie(this.worldObj);
         var3.copyLocationAndAnglesFrom(var1);
         this.worldObj.removeEntity(var1);
         var3.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(var3)), (IEntityLivingData)null);
         var3.setVillager(true);
         if(var1.isChild()) {
            var3.setChild(true);
         }

         var3.setNoAI(var2.isAIDisabled());
         if(var2.hasCustomName()) {
            var3.setCustomNameTag(var2.getCustomNameTag());
            var3.setAlwaysRenderNameTag(var2.getAlwaysRenderNameTag());
         }

         this.worldObj.spawnEntityInWorld(var3);
         this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
      }

   }

   public float getEyeHeight() {
      float var1 = 1.74F;
      if(this.isChild()) {
         var1 = (float)((double)var1 - 0.81D);
      }

      return var1;
   }

   protected boolean func_175448_a(ItemStack var1) {
      return (var1.getItem() != Items.egg || !this.isChild() || !this.isRiding()) && super.func_175448_a(var1);
   }

   public IEntityLivingData onInitialSpawn(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.onInitialSpawn(var1, var2);
      float var3 = var1.getClampedAdditionalDifficulty();
      this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * var3);
      EntityZombie$GroupData var8 = new EntityZombie$GroupData(this.worldObj.rand.nextFloat() < 0.05F, this.worldObj.rand.nextFloat() < 0.05F);
      if(var8 instanceof EntityZombie$GroupData) {
         EntityZombie$GroupData var4 = (EntityZombie$GroupData)var8;
         if(var4.isVillager) {
            this.setVillager(true);
         }

         if(var4.isChild) {
            this.setChild(true);
            if((double)this.worldObj.rand.nextFloat() < 0.05D) {
               List var5 = this.worldObj.getEntitiesWithinAABB(EntityChicken.class, this.getEntityBoundingBox().expand(5.0D, 3.0D, 5.0D), EntitySelectors.IS_STANDALONE);
               if(!var5.isEmpty()) {
                  EntityChicken var6 = (EntityChicken)var5.get(0);
                  var6.setChickenJockey(true);
                  this.mountEntity(var6);
               }
            } else if((double)this.worldObj.rand.nextFloat() < 0.05D) {
               EntityChicken var11 = new EntityChicken(this.worldObj);
               var11.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
               var11.onInitialSpawn(var1, (IEntityLivingData)null);
               var11.setChickenJockey(true);
               this.worldObj.spawnEntityInWorld(var11);
               this.mountEntity(var11);
            }
         }
      }

      this.setBreakDoorsAItask(this.rand.nextFloat() < var3 * 0.1F);
      this.setEquipmentBasedOnDifficulty(var1);
      this.setEnchantmentBasedOnDifficulty(var1);
      if(this.getEquipmentInSlot(4) == null) {
         Calendar var9 = this.worldObj.getCurrentDate();
         if(var9.get(2) + 1 == 10 && var9.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
            this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F?Blocks.lit_pumpkin:Blocks.pumpkin));
            this.equipmentDropChances[4] = 0.0F;
         }
      }

      this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
      double var10 = this.rand.nextDouble() * 1.5D * (double)var3;
      if(var10 > 1.0D) {
         this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", var10, 2));
      }

      if(this.rand.nextFloat() < var3 * 0.05F) {
         this.getEntityAttribute(reinforcementChance).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
         this.setBreakDoorsAItask(true);
      }

      return var8;
   }

   public boolean interact(EntityPlayer var1) {
      ItemStack var2 = var1.getCurrentEquippedItem();
      if(var2.getItem() == Items.golden_apple && var2.getMetadata() == 0 && this.isVillager() && this.isPotionActive(Potion.weakness)) {
         if(!var1.abilities.isCreative()) {
            --var2.stackSize;
         }

         if(var2.stackSize <= 0) {
            var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
         }

         if(!this.worldObj.isRemote) {
            this.startConversion(this.rand.nextInt(2401) + 3600);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void startConversion(int var1) {
      this.conversionTime = var1;
      this.k().a(14, Byte.valueOf((byte)1));
      this.removePotionEffect(Potion.weakness.getId());
      this.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), var1, Math.min(this.worldObj.getDifficulty().getDifficultyId() - 1, 0)));
      this.worldObj.setEntityState(this, (byte)16);
   }

   public void handleStatusUpdate(byte var1) {
      if(var1 == 16) {
         if(!this.isSilent()) {
            this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
         }
      } else {
         super.handleStatusUpdate(var1);
      }

   }

   protected boolean canDespawn() {
      return !this.isConverting();
   }

   public boolean isConverting() {
      return this.k().g(14) == 1;
   }

   protected void convertToVillager() {
      EntityVillager var1 = new EntityVillager(this.worldObj);
      var1.copyLocationAndAnglesFrom(this);
      var1.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(var1)), (IEntityLivingData)null);
      var1.setLookingForHome();
      if(this.isChild()) {
         var1.setGrowingAge(-24000);
      }

      this.worldObj.removeEntity(this);
      var1.setNoAI(this.isAIDisabled());
      if(this.hasCustomName()) {
         var1.setCustomNameTag(this.getCustomNameTag());
         var1.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
      }

      this.worldObj.spawnEntityInWorld(var1);
      var1.addPotionEffect(new PotionEffect(Potion.confusion.getId(), 200, 0));
      this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1017, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
   }

   protected int getConversionTimeBoost() {
      int var1 = 1;
      if(this.rand.nextFloat() < 0.01F) {
         int var2 = 0;
         BlockPos$MutableBlockPos var3 = new BlockPos$MutableBlockPos();

         for(int var4 = (int)this.posX - 4; var4 < (int)this.posX + 4 && var2 < 14; ++var4) {
            for(int var5 = (int)this.posY - 4; var5 < (int)this.posY + 4 && var2 < 14; ++var5) {
               for(int var6 = (int)this.posZ - 4; var6 < (int)this.posZ + 4 && var2 < 14; ++var6) {
                  Block var7 = this.worldObj.getBlockState(var3.func_181079_c(var4, var5, var6)).getBlock();
                  if(var7 == Blocks.iron_bars || var7 == Blocks.bed) {
                     if(this.rand.nextFloat() < 0.3F) {
                        ++var1;
                     }

                     ++var2;
                  }
               }
            }
         }
      }

      return var1;
   }

   public void setChildSize(boolean var1) {
      this.multiplySize(0.5F);
   }

   protected final void setSize(float var1, float var2) {
      boolean var3 = this.zombieWidth > 0.0F && this.zombieHeight > 0.0F;
      this.zombieWidth = var1;
      this.zombieHeight = var2;
      this.multiplySize(1.0F);
   }

   protected final void multiplySize(float var1) {
      super.setSize(this.zombieWidth * var1, this.zombieHeight * var1);
   }

   public double getYOffset() {
      return this.isChild()?0.0D:-0.35D;
   }

   public void onDeath(DamageSource var1) {
      super.onDeath(var1);
      if(var1.getEntity() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper)var1.getEntity()).getPowered() && ((EntityCreeper)var1.getEntity()).isAIEnabled()) {
         ((EntityCreeper)var1.getEntity()).func_175493_co();
         this.entityDropItem(new ItemStack(Items.skull, 1, 2), 0.0F);
      }

   }
}
