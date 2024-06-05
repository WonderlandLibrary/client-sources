package net.minecraft.entity.boss;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityWither extends EntityMob implements IBossDisplayData, IRangedAttackMob {
   private int[] field_82223_h = new int[2];
   private int[] field_82224_i = new int[2];
   private float[] field_82221_e = new float[2];
   private int field_82222_j;
   private float[] field_82217_f = new float[2];
   private static final Predicate attackEntitySelector = new Predicate() {
      private static final String __OBFID = "CL_00001662";

      public boolean func_180027_a(Entity var1) {
         return var1 instanceof EntityLivingBase && ((EntityLivingBase)var1).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
      }

      public boolean apply(Object var1) {
         return this.func_180027_a((Entity)var1);
      }
   };
   private static final String __OBFID = "CL_00001661";
   private float[] field_82218_g = new float[2];
   private float[] field_82220_d = new float[2];

   protected String getLivingSound() {
      return "mob.wither.idle";
   }

   public float func_82210_r(int var1) {
      return this.field_82220_d[var1];
   }

   protected String getDeathSound() {
      return "mob.wither.death";
   }

   public EntityWither(World var1) {
      super(var1);
      this.setHealth(this.getMaxHealth());
      this.setSize(0.9F, 3.5F);
      this.isImmuneToFire = true;
      ((PathNavigateGround)this.getNavigator()).func_179693_d(true);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 40, 20.0F));
      this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(7, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, attackEntitySelector));
      this.experienceValue = 50;
   }

   public float func_82207_a(int var1) {
      return this.field_82221_e[var1];
   }

   protected void updateAITasks() {
      int var1;
      if (this.getInvulTime() > 0) {
         var1 = this.getInvulTime() - 1;
         if (var1 <= 0) {
            this.worldObj.newExplosion(this, this.posX, this.posY + (double)this.getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            this.worldObj.func_175669_a(1013, new BlockPos(this), 0);
         }

         this.setInvulTime(var1);
         if (this.ticksExisted % 10 == 0) {
            this.heal(10.0F);
         }
      } else {
         super.updateAITasks();

         int var2;
         int var3;
         for(var1 = 1; var1 < 3; ++var1) {
            if (this.ticksExisted >= this.field_82223_h[var1 - 1]) {
               this.field_82223_h[var1 - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
               int var4;
               if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                  var3 = var1 - 1;
                  var4 = this.field_82224_i[var1 - 1];
                  this.field_82224_i[var3] = this.field_82224_i[var1 - 1] + 1;
                  if (var4 > 15) {
                     float var5 = 10.0F;
                     float var6 = 5.0F;
                     double var7 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - (double)var5, this.posX + (double)var5);
                     double var9 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - (double)var6, this.posY + (double)var6);
                     double var11 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - (double)var5, this.posZ + (double)var5);
                     this.launchWitherSkullToCoords(var1 + 1, var7, var9, var11, true);
                     this.field_82224_i[var1 - 1] = 0;
                  }
               }

               var2 = this.getWatchedTargetId(var1);
               if (var2 > 0) {
                  Entity var14 = this.worldObj.getEntityByID(var2);
                  if (var14 != null && var14.isEntityAlive() && this.getDistanceSqToEntity(var14) <= 900.0D && this.canEntityBeSeen(var14)) {
                     this.launchWitherSkullToEntity(var1 + 1, (EntityLivingBase)var14);
                     this.field_82223_h[var1 - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
                     this.field_82224_i[var1 - 1] = 0;
                  } else {
                     this.func_82211_c(var1, 0);
                  }
               } else {
                  List var13 = this.worldObj.func_175647_a(EntityLivingBase.class, this.getEntityBoundingBox().expand(20.0D, 8.0D, 20.0D), Predicates.and(attackEntitySelector, IEntitySelector.field_180132_d));

                  for(var4 = 0; var4 < 10 && !var13.isEmpty(); ++var4) {
                     EntityLivingBase var16 = (EntityLivingBase)var13.get(this.rand.nextInt(var13.size()));
                     if (var16 != this && var16.isEntityAlive() && this.canEntityBeSeen(var16)) {
                        if (var16 instanceof EntityPlayer) {
                           if (!((EntityPlayer)var16).capabilities.disableDamage) {
                              this.func_82211_c(var1, var16.getEntityId());
                           }
                        } else {
                           this.func_82211_c(var1, var16.getEntityId());
                        }
                        break;
                     }

                     var13.remove(var16);
                  }
               }
            }
         }

         if (this.getAttackTarget() != null) {
            this.func_82211_c(0, this.getAttackTarget().getEntityId());
         } else {
            this.func_82211_c(0, 0);
         }

         if (this.field_82222_j > 0) {
            --this.field_82222_j;
            if (this.field_82222_j == 0 && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
               var1 = MathHelper.floor_double(this.posY);
               var2 = MathHelper.floor_double(this.posX);
               var3 = MathHelper.floor_double(this.posZ);
               boolean var15 = false;
               int var17 = -1;

               while(true) {
                  if (var17 > 1) {
                     if (var15) {
                        this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1012, new BlockPos(this), 0);
                     }
                     break;
                  }

                  for(int var18 = -1; var18 <= 1; ++var18) {
                     for(int var19 = 0; var19 <= 3; ++var19) {
                        int var8 = var2 + var17;
                        int var20 = var1 + var19;
                        int var10 = var3 + var18;
                        Block var21 = this.worldObj.getBlockState(new BlockPos(var8, var20, var10)).getBlock();
                        if (var21.getMaterial() != Material.air && var21 != Blocks.bedrock && var21 != Blocks.end_portal && var21 != Blocks.end_portal_frame && var21 != Blocks.command_block && var21 != Blocks.barrier) {
                           var15 = this.worldObj.destroyBlock(new BlockPos(var8, var20, var10), true) || var15;
                        }
                     }
                  }

                  ++var17;
               }
            }
         }

         if (this.ticksExisted % 20 == 0) {
            this.heal(1.0F);
         }
      }

   }

   public void setInWeb() {
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.setInvulTime(var1.getInteger("Invul"));
   }

   public int getBrightnessForRender(float var1) {
      return 15728880;
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if (this.func_180431_b(var1)) {
         return false;
      } else if (var1 != DamageSource.drown && !(var1.getEntity() instanceof EntityWither)) {
         if (this.getInvulTime() > 0 && var1 != DamageSource.outOfWorld) {
            return false;
         } else {
            Entity var3;
            if (this.isArmored()) {
               var3 = var1.getSourceOfDamage();
               if (var3 instanceof EntityArrow) {
                  return false;
               }
            }

            var3 = var1.getEntity();
            if (var3 != null && !(var3 instanceof EntityPlayer) && var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).getCreatureAttribute() == this.getCreatureAttribute()) {
               return false;
            } else {
               if (this.field_82222_j <= 0) {
                  this.field_82222_j = 20;
               }

               for(int var4 = 0; var4 < this.field_82224_i.length; ++var4) {
                  int[] var10000 = this.field_82224_i;
                  var10000[var4] += 3;
               }

               return super.attackEntityFrom(var1, var2);
            }
         }
      } else {
         return false;
      }
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(17, new Integer(0));
      this.dataWatcher.addObject(18, new Integer(0));
      this.dataWatcher.addObject(19, new Integer(0));
      this.dataWatcher.addObject(20, new Integer(0));
   }

   public void onLivingUpdate() {
      this.motionY *= 0.6000000238418579D;
      double var1;
      double var3;
      double var5;
      if (!this.worldObj.isRemote && this.getWatchedTargetId(0) > 0) {
         Entity var7 = this.worldObj.getEntityByID(this.getWatchedTargetId(0));
         if (var7 != null) {
            if (this.posY < var7.posY || !this.isArmored() && this.posY < var7.posY + 5.0D) {
               if (this.motionY < 0.0D) {
                  this.motionY = 0.0D;
               }

               this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
            }

            double var8 = var7.posX - this.posX;
            var1 = var7.posZ - this.posZ;
            var3 = var8 * var8 + var1 * var1;
            if (var3 > 9.0D) {
               var5 = (double)MathHelper.sqrt_double(var3);
               this.motionX += (var8 / var5 * 0.5D - this.motionX) * 0.6000000238418579D;
               this.motionZ += (var1 / var5 * 0.5D - this.motionZ) * 0.6000000238418579D;
            }
         }
      }

      if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806D) {
         this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F;
      }

      super.onLivingUpdate();

      int var20;
      for(var20 = 0; var20 < 2; ++var20) {
         this.field_82218_g[var20] = this.field_82221_e[var20];
         this.field_82217_f[var20] = this.field_82220_d[var20];
      }

      double var10;
      double var12;
      double var14;
      int var21;
      for(var20 = 0; var20 < 2; ++var20) {
         var21 = this.getWatchedTargetId(var20 + 1);
         Entity var9 = null;
         if (var21 > 0) {
            var9 = this.worldObj.getEntityByID(var21);
         }

         if (var9 != null) {
            var1 = this.func_82214_u(var20 + 1);
            var3 = this.func_82208_v(var20 + 1);
            var5 = this.func_82213_w(var20 + 1);
            var10 = var9.posX - var1;
            var12 = var9.posY + (double)var9.getEyeHeight() - var3;
            var14 = var9.posZ - var5;
            double var16 = (double)MathHelper.sqrt_double(var10 * var10 + var14 * var14);
            float var18 = (float)(Math.atan2(var14, var10) * 180.0D / 3.141592653589793D) - 90.0F;
            float var19 = (float)(-(Math.atan2(var12, var16) * 180.0D / 3.141592653589793D));
            this.field_82220_d[var20] = this.func_82204_b(this.field_82220_d[var20], var19, 40.0F);
            this.field_82221_e[var20] = this.func_82204_b(this.field_82221_e[var20], var18, 10.0F);
         } else {
            this.field_82221_e[var20] = this.func_82204_b(this.field_82221_e[var20], this.renderYawOffset, 10.0F);
         }
      }

      boolean var22 = this.isArmored();

      for(var21 = 0; var21 < 3; ++var21) {
         var10 = this.func_82214_u(var21);
         var12 = this.func_82208_v(var21);
         var14 = this.func_82213_w(var21);
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var10 + this.rand.nextGaussian() * 0.30000001192092896D, var12 + this.rand.nextGaussian() * 0.30000001192092896D, var14 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
         if (var22 && this.worldObj.rand.nextInt(4) == 0) {
            this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, var10 + this.rand.nextGaussian() * 0.30000001192092896D, var12 + this.rand.nextGaussian() * 0.30000001192092896D, var14 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
         }
      }

      if (this.getInvulTime() > 0) {
         for(var21 = 0; var21 < 3; ++var21) {
            this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + (double)(this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
         }
      }

   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
   }

   private double func_82213_w(int var1) {
      if (var1 <= 0) {
         return this.posZ;
      } else {
         float var2 = (this.renderYawOffset + (float)(180 * (var1 - 1))) / 180.0F * 3.1415927F;
         float var3 = MathHelper.sin(var2);
         return this.posZ + (double)var3 * 1.3D;
      }
   }

   public void setInvulTime(int var1) {
      this.dataWatcher.updateObject(20, var1);
   }

   public void func_82211_c(int var1, int var2) {
      this.dataWatcher.updateObject(17 + var1, var2);
   }

   private float func_82204_b(float var1, float var2, float var3) {
      float var4 = MathHelper.wrapAngleTo180_float(var2 - var1);
      if (var4 > var3) {
         var4 = var3;
      }

      if (var4 < -var3) {
         var4 = -var3;
      }

      return var1 + var4;
   }

   public boolean isArmored() {
      return this.getHealth() <= this.getMaxHealth() / 2.0F;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setInteger("Invul", this.getInvulTime());
   }

   private void launchWitherSkullToCoords(int var1, double var2, double var4, double var6, boolean var8) {
      this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, new BlockPos(this), 0);
      double var9 = this.func_82214_u(var1);
      double var11 = this.func_82208_v(var1);
      double var13 = this.func_82213_w(var1);
      double var15 = var2 - var9;
      double var17 = var4 - var11;
      double var19 = var6 - var13;
      EntityWitherSkull var21 = new EntityWitherSkull(this.worldObj, this, var15, var17, var19);
      if (var8) {
         var21.setInvulnerable(true);
      }

      var21.posY = var11;
      var21.posX = var9;
      var21.posZ = var13;
      this.worldObj.spawnEntityInWorld(var21);
   }

   public void attackEntityWithRangedAttack(EntityLivingBase var1, float var2) {
      this.launchWitherSkullToEntity(0, var1);
   }

   private double func_82208_v(int var1) {
      return var1 <= 0 ? this.posY + 3.0D : this.posY + 2.2D;
   }

   protected void despawnEntity() {
      this.entityAge = 0;
   }

   public int getTotalArmorValue() {
      return 4;
   }

   protected void dropFewItems(boolean var1, int var2) {
      EntityItem var3 = this.dropItem(Items.nether_star, 1);
      if (var3 != null) {
         var3.func_174873_u();
      }

      if (!this.worldObj.isRemote) {
         Iterator var4 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)).iterator();

         while(var4.hasNext()) {
            EntityPlayer var5 = (EntityPlayer)var4.next();
            var5.triggerAchievement(AchievementList.killWither);
         }
      }

   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   public int getInvulTime() {
      return this.dataWatcher.getWatchableObjectInt(20);
   }

   public void func_82206_m() {
      this.setInvulTime(220);
      this.setHealth(this.getMaxHealth() / 3.0F);
   }

   private void launchWitherSkullToEntity(int var1, EntityLivingBase var2) {
      this.launchWitherSkullToCoords(var1, var2.posX, var2.posY + (double)var2.getEyeHeight() * 0.5D, var2.posZ, var1 == 0 && this.rand.nextFloat() < 0.001F);
   }

   public int getWatchedTargetId(int var1) {
      return this.dataWatcher.getWatchableObjectInt(17 + var1);
   }

   private double func_82214_u(int var1) {
      if (var1 <= 0) {
         return this.posX;
      } else {
         float var2 = (this.renderYawOffset + (float)(180 * (var1 - 1))) / 180.0F * 3.1415927F;
         float var3 = MathHelper.cos(var2);
         return this.posX + (double)var3 * 1.3D;
      }
   }

   public void fall(float var1, float var2) {
   }

   public void addPotionEffect(PotionEffect var1) {
   }

   protected String getHurtSound() {
      return "mob.wither.hurt";
   }

   public void mountEntity(Entity var1) {
      this.ridingEntity = null;
   }
}
