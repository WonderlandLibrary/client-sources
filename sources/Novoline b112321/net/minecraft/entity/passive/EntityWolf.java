package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf$1;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWolf extends EntityTameable {
   private float headRotationCourse;
   private float headRotationCourseOld;
   private boolean isWet;
   private boolean isShaking;
   private float timeWolfIsShaking;
   private float prevTimeWolfIsShaking;

   public EntityWolf(World var1) {
      super(var1);
      this.setSize(0.6F, 0.8F);
      ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, this.aiSit);
      this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
      this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
      this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
      this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
      this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
      this.tasks.addTask(8, new EntityAIBeg(this, 8.0F));
      this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(9, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
      this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
      this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
      this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new EntityWolf$1(this)));
      this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
      this.setTamed(false);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
      if(this.isTamed()) {
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
      } else {
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
      }

      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
   }

   public void setAttackTarget(EntityLivingBase var1) {
      super.setAttackTarget(var1);
      this.setAngry(false);
   }

   protected void updateAITasks() {
      this.I.a(18, Float.valueOf(this.getHealth()));
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(18, Float.valueOf(this.getHealth()));
      this.I.b(19, Byte.valueOf((byte)0));
      this.I.b(20, Byte.valueOf((byte)EnumDyeColor.RED.getMetadata()));
   }

   protected void playStepSound(BlockPos var1, Block var2) {
      this.playSound("mob.wolf.step", 0.15F, 1.0F);
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setBoolean("Angry", this.isAngry());
      var1.setByte("CollarColor", (byte)this.getCollarColor().getDyeDamage());
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.setAngry(var1.getBoolean("Angry"));
      if(var1.hasKey("CollarColor", 99)) {
         this.setCollarColor(EnumDyeColor.byDyeDamage(var1.getByte("CollarColor")));
      }

   }

   protected String getLivingSound() {
      return this.isAngry()?"mob.wolf.growl":(this.rand.nextInt(3) == 0?(this.isTamed() && this.I.b(18) < 10.0F?"mob.wolf.whine":"mob.wolf.panting"):"mob.wolf.bark");
   }

   protected String getHurtSound() {
      return "mob.wolf.hurt";
   }

   protected String getDeathSound() {
      return "mob.wolf.death";
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected Item getDropItem() {
      return Item.getItemById(-1);
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if(!this.worldObj.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround) {
         this.isShaking = true;
         this.timeWolfIsShaking = 0.0F;
         this.prevTimeWolfIsShaking = 0.0F;
         this.worldObj.setEntityState(this, (byte)8);
      }

      if(!this.worldObj.isRemote && this.getAttackTarget() == null && this.isAngry()) {
         this.setAngry(false);
      }

   }

   public void onUpdate() {
      super.onUpdate();
      this.headRotationCourseOld = this.headRotationCourse;
      if(this.isBegging()) {
         this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
      } else {
         this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
      }

      if(this.isWet()) {
         this.isWet = true;
         this.isShaking = false;
         this.timeWolfIsShaking = 0.0F;
         this.prevTimeWolfIsShaking = 0.0F;
      } else if((this.isWet || this.isShaking) && this.isShaking) {
         if(this.timeWolfIsShaking == 0.0F) {
            this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         }

         this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
         this.timeWolfIsShaking += 0.05F;
         if(this.prevTimeWolfIsShaking >= 2.0F) {
            this.isWet = false;
            this.isShaking = false;
            this.prevTimeWolfIsShaking = 0.0F;
            this.timeWolfIsShaking = 0.0F;
         }

         if(this.timeWolfIsShaking > 0.4F) {
            float var1 = (float)this.getEntityBoundingBox().minY;
            int var2 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * 3.1415927F) * 7.0F);

            for(int var3 = 0; var3 < var2; ++var3) {
               float var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
               float var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
               this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (double)var4, (double)(var1 + 0.8F), this.posZ + (double)var5, this.motionX, this.motionY, this.motionZ, new int[0]);
            }
         }
      }

   }

   public boolean isWolfWet() {
      return this.isWet;
   }

   public float getShadingWhileWet(float var1) {
      return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * var1) / 2.0F * 0.25F;
   }

   public float getShakeAngle(float var1, float var2) {
      float var3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * var1 + var2) / 1.8F;
      if(var3 < 0.0F) {
         var3 = 0.0F;
      } else if(var3 > 1.0F) {
         var3 = 1.0F;
      }

      return MathHelper.sin(var3 * 3.1415927F) * MathHelper.sin(var3 * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
   }

   public float getInterestedAngle(float var1) {
      return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * var1) * 0.15F * 3.1415927F;
   }

   public float getEyeHeight() {
      return this.height * 0.8F;
   }

   public int getVerticalFaceSpeed() {
      return this.isSitting()?20:super.getVerticalFaceSpeed();
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(this.isEntityInvulnerable(var1)) {
         return false;
      } else {
         Entity var3 = var1.getEntity();
         this.aiSit.setSitting(false);
         if(!(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow)) {
            var2 = (var2 + 1.0F) / 2.0F;
         }

         return super.attackEntityFrom(var1, var2);
      }
   }

   public boolean attackEntityAsMob(Entity var1) {
      boolean var2 = var1.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue()));
      this.applyEnchantments(this, var1);
      return var2;
   }

   public void setTamed(boolean var1) {
      super.setTamed(var1);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
   }

   public boolean interact(EntityPlayer var1) {
      ItemStack var2 = var1.inventory.getCurrentItem();
      if(this.isTamed()) {
         if(var2.getItem() instanceof ItemFood) {
            ItemFood var3 = (ItemFood)var2.getItem();
            if(var3.isWolfsFavoriteMeat() && this.I.b(18) < 20.0F) {
               if(!var1.abilities.isCreative()) {
                  --var2.stackSize;
               }

               this.heal((float)var3.getHealAmount(var2));
               if(var2.stackSize <= 0) {
                  var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
               }

               return true;
            }
         } else if(var2.getItem() == Items.dye) {
            EnumDyeColor var4 = EnumDyeColor.byDyeDamage(var2.getMetadata());
            if(var4 != this.getCollarColor()) {
               this.setCollarColor(var4);
               if(!var1.abilities.isCreative() && --var2.stackSize <= 0) {
                  var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
               }

               return true;
            }
         }

         if(this.isOwner(var1) && !this.worldObj.isRemote && !this.isBreedingItem(var2)) {
            this.aiSit.setSitting(!this.isSitting());
            this.isJumping = false;
            this.navigator.clearPathEntity();
            this.setAttackTarget((EntityLivingBase)null);
         }
      } else if(var2.getItem() == Items.bone && !this.isAngry()) {
         if(!var1.abilities.isCreative()) {
            --var2.stackSize;
         }

         if(var2.stackSize <= 0) {
            var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
         }

         if(!this.worldObj.isRemote) {
            if(this.rand.nextInt(3) == 0) {
               this.setTamed(true);
               this.navigator.clearPathEntity();
               this.setAttackTarget((EntityLivingBase)null);
               this.aiSit.setSitting(true);
               this.setHealth(20.0F);
               this.setOwnerId(var1.getUniqueID().toString());
               this.playTameEffect(true);
               this.worldObj.setEntityState(this, (byte)7);
            } else {
               this.playTameEffect(false);
               this.worldObj.setEntityState(this, (byte)6);
            }
         }

         return true;
      }

      return super.interact(var1);
   }

   public void handleStatusUpdate(byte var1) {
      if(var1 == 8) {
         this.isShaking = true;
         this.timeWolfIsShaking = 0.0F;
         this.prevTimeWolfIsShaking = 0.0F;
      } else {
         super.handleStatusUpdate(var1);
      }

   }

   public float getTailRotation() {
      return this.isAngry()?1.5393804F:(this.isTamed()?(0.55F - (20.0F - this.I.b(18)) * 0.02F) * 3.1415927F:0.62831855F);
   }

   public boolean isBreedingItem(ItemStack var1) {
      return var1.getItem() instanceof ItemFood && ((ItemFood)var1.getItem()).isWolfsFavoriteMeat();
   }

   public int getMaxSpawnedInChunk() {
      return 8;
   }

   public boolean isAngry() {
      return (this.I.g(16) & 2) != 0;
   }

   public void setAngry(boolean var1) {
      byte var2 = this.I.g(16);
      this.I.a(16, Byte.valueOf((byte)(var2 | 2)));
   }

   public EnumDyeColor getCollarColor() {
      return EnumDyeColor.byDyeDamage(this.I.g(20) & 15);
   }

   public void setCollarColor(EnumDyeColor var1) {
      this.I.a(20, Byte.valueOf((byte)(var1.getDyeDamage() & 15)));
   }

   public EntityWolf createChild(EntityAgeable var1) {
      EntityWolf var2 = new EntityWolf(this.worldObj);
      String var3 = this.getOwnerId();
      if(!var3.trim().isEmpty()) {
         var2.setOwnerId(var3);
         var2.setTamed(true);
      }

      return var2;
   }

   public void setBegging(boolean var1) {
      this.I.a(19, Byte.valueOf((byte)1));
   }

   public boolean canMateWith(EntityAnimal var1) {
      if(var1 == this) {
         return false;
      } else if(!this.isTamed()) {
         return false;
      } else if(!(var1 instanceof EntityWolf)) {
         return false;
      } else {
         EntityWolf var2 = (EntityWolf)var1;
         return var2.isTamed() && !var2.isSitting() && this.isInLove() && var2.isInLove();
      }
   }

   public boolean isBegging() {
      return this.I.g(19) == 1;
   }

   protected boolean canDespawn() {
      return !this.isTamed() && this.ticksExisted > 2400;
   }

   public boolean shouldAttackEntity(EntityLivingBase var1, EntityLivingBase var2) {
      if(!(var1 instanceof EntityCreeper) && !(var1 instanceof EntityGhast)) {
         if(var1 instanceof EntityWolf) {
            EntityWolf var3 = (EntityWolf)var1;
            if(var3.isTamed() && var3.a() == var2) {
               return false;
            }
         }

         return (!(var1 instanceof EntityPlayer) || !(var2 instanceof EntityPlayer) || ((EntityPlayer)var2).canAttackPlayer((EntityPlayer)var1)) && (!(var1 instanceof EntityHorse) || !((EntityHorse)var1).isTame());
      } else {
         return false;
      }
   }

   public boolean allowLeashing() {
      return !this.isAngry() && super.allowLeashing();
   }
}
