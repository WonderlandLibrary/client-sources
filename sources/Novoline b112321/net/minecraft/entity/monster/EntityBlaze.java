package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityBlaze$AIFireballAttack;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityBlaze extends EntityMob {
   private float heightOffset = 0.5F;
   private int heightOffsetUpdateTime;

   public EntityBlaze(World var1) {
      super(var1);
      this.isImmuneToFire = true;
      this.experienceValue = 10;
      this.tasks.addTask(4, new EntityBlaze$AIFireballAttack(this));
      this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
      this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
      this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(8, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0D);
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(16, Byte.valueOf((byte)0));
   }

   protected String getLivingSound() {
      return "mob.blaze.breathe";
   }

   protected String getHurtSound() {
      return "mob.blaze.hit";
   }

   protected String getDeathSound() {
      return "mob.blaze.death";
   }

   public int getBrightnessForRender(float var1) {
      return 15728880;
   }

   public float getBrightness(float var1) {
      return 1.0F;
   }

   public void onLivingUpdate() {
      if(!this.onGround && this.motionY < 0.0D) {
         this.motionY *= 0.6D;
      }

      if(this.worldObj.isRemote) {
         if(this.rand.nextInt(24) == 0 && !this.isSilent()) {
            this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.fire", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
         }

         for(int var1 = 0; var1 < 2; ++var1) {
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, new int[0]);
         }
      }

      super.onLivingUpdate();
   }

   protected void updateAITasks() {
      if(this.isWet()) {
         this.attackEntityFrom(DamageSource.drown, 1.0F);
      }

      --this.heightOffsetUpdateTime;
      if(this.heightOffsetUpdateTime <= 0) {
         this.heightOffsetUpdateTime = 100;
         this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
      }

      EntityLivingBase var1 = this.getAttackTarget();
      if(var1.posY + (double)var1.getEyeHeight() > this.posY + (double)this.getEyeHeight() + (double)this.heightOffset) {
         this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
         this.isAirBorne = true;
      }

      super.updateAITasks();
   }

   public void fall(float var1, float var2) {
   }

   protected Item getDropItem() {
      return Items.blaze_rod;
   }

   public boolean isBurning() {
      return this.func_70845_n();
   }

   protected void dropFewItems(boolean var1, int var2) {
      int var3 = this.rand.nextInt(2 + var2);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.dropItem(Items.blaze_rod, 1);
      }

   }

   public boolean func_70845_n() {
      return (this.I.g(16) & 1) != 0;
   }

   public void setOnFire(boolean var1) {
      byte var2 = this.I.g(16);
      var2 = (byte)(var2 | 1);
      this.I.a(16, Byte.valueOf(var2));
   }

   protected boolean isValidLightLevel() {
      return true;
   }
}
