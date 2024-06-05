package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityRabbit$AIAvoidEntity;
import net.minecraft.entity.passive.EntityRabbit$AIEvilAttack;
import net.minecraft.entity.passive.EntityRabbit$AIPanic;
import net.minecraft.entity.passive.EntityRabbit$AIRaidFarm;
import net.minecraft.entity.passive.EntityRabbit$EnumMoveType;
import net.minecraft.entity.passive.EntityRabbit$RabbitJumpHelper;
import net.minecraft.entity.passive.EntityRabbit$RabbitMoveHelper;
import net.minecraft.entity.passive.EntityRabbit$RabbitTypeData;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityRabbit extends EntityAnimal {
   private EntityRabbit$AIAvoidEntity aiAvoidWolves;
   private int field_175540_bm = 0;
   private int field_175535_bn = 0;
   private boolean field_175536_bo = false;
   private boolean field_175537_bp = false;
   private int currentMoveTypeDuration = 0;
   private EntityRabbit$EnumMoveType moveType = EntityRabbit$EnumMoveType.HOP;
   private int carrotTicks = 0;
   private EntityPlayer field_175543_bt = null;

   public EntityRabbit(World var1) {
      super(var1);
      this.setSize(0.6F, 0.7F);
      this.jumpHelper = new EntityRabbit$RabbitJumpHelper(this, this);
      this.moveHelper = new EntityRabbit$RabbitMoveHelper(this);
      ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
      this.navigator.setHeightRequirement(2.5F);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityRabbit$AIPanic(this, 1.33D));
      this.tasks.addTask(2, new EntityAITempt(this, 1.0D, Items.carrot, false));
      this.tasks.addTask(2, new EntityAITempt(this, 1.0D, Items.golden_carrot, false));
      this.tasks.addTask(2, new EntityAITempt(this, 1.0D, Item.getItemFromBlock(Blocks.yellow_flower), false));
      this.tasks.addTask(3, new EntityAIMate(this, 0.8D));
      this.tasks.addTask(5, new EntityRabbit$AIRaidFarm(this));
      this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
      this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      this.aiAvoidWolves = new EntityRabbit$AIAvoidEntity(this, EntityWolf.class, 16.0F, 1.33D, 1.33D);
      this.tasks.addTask(4, this.aiAvoidWolves);
      this.setMovementSpeed(0.0D);
   }

   protected float i() {
      return this.moveHelper.isUpdating() && this.moveHelper.getY() > this.posY + 0.5D?0.5F:this.moveType.func_180074_b();
   }

   public void setMoveType(EntityRabbit$EnumMoveType var1) {
      this.moveType = var1;
   }

   public float func_175521_o(float var1) {
      return this.field_175535_bn == 0?0.0F:((float)this.field_175540_bm + var1) / (float)this.field_175535_bn;
   }

   public void setMovementSpeed(double var1) {
      this.getNavigator().setSpeed(var1);
      this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), var1);
   }

   public void a(boolean var1, EntityRabbit$EnumMoveType var2) {
      super.setJumping(var1);
      if(this.moveType == EntityRabbit$EnumMoveType.ATTACK) {
         this.moveType = EntityRabbit$EnumMoveType.HOP;
      }

      this.field_175536_bo = var1;
   }

   public void doMovementAction(EntityRabbit$EnumMoveType var1) {
      this.a(true, var1);
      this.field_175535_bn = var1.func_180073_d();
      this.field_175540_bm = 0;
   }

   public boolean func_175523_cj() {
      return this.field_175536_bo;
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(18, Byte.valueOf((byte)0));
   }

   public void updateAITasks() {
      if(this.moveHelper.getSpeed() > 0.8D) {
         this.setMoveType(EntityRabbit$EnumMoveType.SPRINT);
      } else if(this.moveType != EntityRabbit$EnumMoveType.ATTACK) {
         this.setMoveType(EntityRabbit$EnumMoveType.HOP);
      }

      if(this.currentMoveTypeDuration > 0) {
         --this.currentMoveTypeDuration;
      }

      if(this.carrotTicks > 0) {
         this.carrotTicks -= this.rand.nextInt(3);
         if(this.carrotTicks < 0) {
            this.carrotTicks = 0;
         }
      }

      if(this.onGround) {
         if(!this.field_175537_bp) {
            this.a(false, EntityRabbit$EnumMoveType.NONE);
            this.func_175517_cu();
         }

         if(this.getRabbitType() == 99 && this.currentMoveTypeDuration == 0) {
            EntityLivingBase var1 = this.getAttackTarget();
            if(this.getDistanceSqToEntity(var1) < 16.0D) {
               this.calculateRotationYaw(var1.posX, var1.posZ);
               this.moveHelper.setMoveTo(var1.posX, var1.posY, var1.posZ, this.moveHelper.getSpeed());
               this.doMovementAction(EntityRabbit$EnumMoveType.ATTACK);
               this.field_175537_bp = true;
            }
         }

         EntityRabbit$RabbitJumpHelper var4 = (EntityRabbit$RabbitJumpHelper)this.jumpHelper;
         if(!var4.getIsJumping()) {
            if(this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0) {
               PathEntity var2 = this.navigator.getPath();
               Vec3 var3 = new Vec3(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());
               if(var2.getCurrentPathIndex() < var2.getCurrentPathLength()) {
                  var3 = var2.getPosition(this);
               }

               this.calculateRotationYaw(var3.xCoord, var3.zCoord);
               this.doMovementAction(this.moveType);
            }
         } else if(!var4.func_180065_d()) {
            this.func_175518_cr();
         }
      }

      this.field_175537_bp = this.onGround;
   }

   public void spawnRunningParticles() {
   }

   private void calculateRotationYaw(double var1, double var3) {
      this.rotationYaw = (float)(MathHelper.func_181159_b(var3 - this.posZ, var1 - this.posX) * 180.0D / 3.141592653589793D) - 90.0F;
   }

   private void func_175518_cr() {
      ((EntityRabbit$RabbitJumpHelper)this.jumpHelper).func_180066_a(true);
   }

   private void func_175520_cs() {
      ((EntityRabbit$RabbitJumpHelper)this.jumpHelper).func_180066_a(false);
   }

   private void updateMoveTypeDuration() {
      this.currentMoveTypeDuration = this.getMoveTypeDuration();
   }

   private void func_175517_cu() {
      this.updateMoveTypeDuration();
      this.func_175520_cs();
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if(this.field_175540_bm != this.field_175535_bn) {
         if(this.field_175540_bm == 0 && !this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte)1);
         }

         ++this.field_175540_bm;
      } else if(this.field_175535_bn != 0) {
         this.field_175540_bm = 0;
         this.field_175535_bn = 0;
      }

   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setInteger("RabbitType", this.getRabbitType());
      var1.setInteger("MoreCarrotTicks", this.carrotTicks);
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.setRabbitType(var1.getInteger("RabbitType"));
      this.carrotTicks = var1.getInteger("MoreCarrotTicks");
   }

   protected String getJumpingSound() {
      return "mob.rabbit.hop";
   }

   protected String getLivingSound() {
      return "mob.rabbit.idle";
   }

   protected String getHurtSound() {
      return "mob.rabbit.hurt";
   }

   protected String getDeathSound() {
      return "mob.rabbit.death";
   }

   public boolean attackEntityAsMob(Entity var1) {
      if(this.getRabbitType() == 99) {
         this.playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         return var1.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
      } else {
         return var1.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
      }
   }

   public int getTotalArmorValue() {
      return this.getRabbitType() == 99?8:super.getTotalArmorValue();
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      return !this.isEntityInvulnerable(var1) && super.attackEntityFrom(var1, var2);
   }

   protected void addRandomDrop() {
      this.entityDropItem(new ItemStack(Items.rabbit_foot, 1), 0.0F);
   }

   protected void dropFewItems(boolean var1, int var2) {
      int var3 = this.rand.nextInt(2) + this.rand.nextInt(1 + var2);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.dropItem(Items.rabbit_hide, 1);
      }

      var3 = this.rand.nextInt(2);

      for(int var6 = 0; var6 < var3; ++var6) {
         if(this.isBurning()) {
            this.dropItem(Items.cooked_rabbit, 1);
         } else {
            this.dropItem(Items.rabbit, 1);
         }
      }

   }

   private boolean isRabbitBreedingItem(Item var1) {
      return var1 == Items.carrot || var1 == Items.golden_carrot || var1 == Item.getItemFromBlock(Blocks.yellow_flower);
   }

   public EntityRabbit createChild(EntityAgeable var1) {
      EntityRabbit var2 = new EntityRabbit(this.worldObj);
      if(var1 instanceof EntityRabbit) {
         var2.setRabbitType(this.rand.nextBoolean()?this.getRabbitType():((EntityRabbit)var1).getRabbitType());
      }

      return var2;
   }

   public boolean isBreedingItem(ItemStack var1) {
      return this.isRabbitBreedingItem(var1.getItem());
   }

   public int getRabbitType() {
      return this.I.g(18);
   }

   public void setRabbitType(int var1) {
      if(var1 == 99) {
         this.tasks.removeTask(this.aiAvoidWolves);
         this.tasks.addTask(4, new EntityRabbit$AIEvilAttack(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityWolf.class, true));
         if(!this.hasCustomName()) {
            this.setCustomNameTag(StatCollector.translateToLocal("entity.KillerBunny.name"));
         }
      }

      this.I.a(18, Byte.valueOf((byte)var1));
   }

   public IEntityLivingData onInitialSpawn(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.onInitialSpawn(var1, var2);
      int var3 = this.rand.nextInt(6);
      boolean var4 = false;
      if(var2 instanceof EntityRabbit$RabbitTypeData) {
         var3 = ((EntityRabbit$RabbitTypeData)var2).typeData;
         var4 = true;
      } else {
         var2 = new EntityRabbit$RabbitTypeData(var3);
      }

      this.setRabbitType(var3);
      this.setGrowingAge(-24000);
      return var2;
   }

   private boolean isCarrotEaten() {
      return this.carrotTicks == 0;
   }

   protected int getMoveTypeDuration() {
      return this.moveType.getDuration();
   }

   protected void createEatingParticles() {
      this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D, new int[]{Block.getStateId(Blocks.carrots.getStateFromMeta(7))});
      this.carrotTicks = 100;
   }

   public void handleStatusUpdate(byte var1) {
      if(var1 == 1) {
         this.createRunningParticles();
         this.field_175535_bn = 10;
         this.field_175540_bm = 0;
      } else {
         super.handleStatusUpdate(var1);
      }

   }

   static boolean access$000(EntityRabbit var0) {
      return var0.isCarrotEaten();
   }
}
