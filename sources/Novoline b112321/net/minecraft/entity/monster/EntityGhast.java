package net.minecraft.entity.monster;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.monster.EntityGhast$AIFireballAttack;
import net.minecraft.entity.monster.EntityGhast$AILookAround;
import net.minecraft.entity.monster.EntityGhast$AIRandomFly;
import net.minecraft.entity.monster.EntityGhast$GhastMoveHelper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityGhast extends EntityFlying implements IMob {
   private int explosionStrength = 1;

   public EntityGhast(World var1) {
      super(var1);
      this.setSize(4.0F, 4.0F);
      this.isImmuneToFire = true;
      this.experienceValue = 5;
      this.moveHelper = new EntityGhast$GhastMoveHelper(this);
      this.tasks.addTask(5, new EntityGhast$AIRandomFly(this));
      this.tasks.addTask(7, new EntityGhast$AILookAround(this));
      this.tasks.addTask(7, new EntityGhast$AIFireballAttack(this));
      this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
   }

   public boolean isAttacking() {
      return this.I.g(16) != 0;
   }

   public void setAttacking(boolean var1) {
      this.I.a(16, Byte.valueOf((byte)1));
   }

   public int getFireballStrength() {
      return this.explosionStrength;
   }

   public void onUpdate() {
      super.onUpdate();
      if(!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
         this.setDead();
      }

   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(this.isEntityInvulnerable(var1)) {
         return false;
      } else if("fireball".equals(var1.getDamageType()) && var1.getEntity() instanceof EntityPlayer) {
         super.attackEntityFrom(var1, 1000.0F);
         ((EntityPlayer)var1.getEntity()).triggerAchievement(AchievementList.ghast);
         return true;
      } else {
         return super.attackEntityFrom(var1, var2);
      }
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(16, Byte.valueOf((byte)0));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
   }

   protected String getLivingSound() {
      return "mob.ghast.moan";
   }

   protected String getHurtSound() {
      return "mob.ghast.scream";
   }

   protected String getDeathSound() {
      return "mob.ghast.death";
   }

   protected Item getDropItem() {
      return Items.gunpowder;
   }

   protected void dropFewItems(boolean var1, int var2) {
      int var3 = this.rand.nextInt(2) + this.rand.nextInt(1 + var2);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.dropItem(Items.ghast_tear, 1);
      }

      var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + var2);

      for(int var6 = 0; var6 < var3; ++var6) {
         this.dropItem(Items.gunpowder, 1);
      }

   }

   protected float getSoundVolume() {
      return 10.0F;
   }

   public boolean getCanSpawnHere() {
      return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
   }

   public int getMaxSpawnedInChunk() {
      return 1;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setInteger("ExplosionPower", this.explosionStrength);
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      if(var1.hasKey("ExplosionPower", 99)) {
         this.explosionStrength = var1.getInteger("ExplosionPower");
      }

   }

   public float getEyeHeight() {
      return 2.6F;
   }
}
