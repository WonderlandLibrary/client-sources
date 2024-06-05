package net.minecraft.entity.boss;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.entity.boss.IBossDisplayData;
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
   private float[] field_82220_d = new float[2];
   private float[] field_82221_e = new float[2];
   private float[] field_82217_f = new float[2];
   private float[] field_82218_g = new float[2];
   private int[] field_82223_h = new int[2];
   private int[] field_82224_i = new int[2];
   private int blockBreakCounter;
   private static final Predicate attackEntitySelector = EntityWither::lambda$static$0;

   public EntityWither(World var1) {
      super(var1);
      this.setHealth(this.getMaxHealth());
      this.setSize(0.9F, 3.5F);
      this.isImmuneToFire = true;
      ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 40, 20.0F));
      this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(7, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, attackEntitySelector));
      this.experienceValue = 50;
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(17, Integer.valueOf(0));
      this.I.b(18, Integer.valueOf(0));
      this.I.b(19, Integer.valueOf(0));
      this.I.b(20, Integer.valueOf(0));
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setInteger("Invul", this.getInvulTime());
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.setInvulTime(var1.getInteger("Invul"));
   }

   protected String getLivingSound() {
      return "mob.wither.idle";
   }

   protected String getHurtSound() {
      return "mob.wither.hurt";
   }

   protected String getDeathSound() {
      return "mob.wither.death";
   }

   public void onLivingUpdate() {
      this.motionY *= 0.6000000238418579D;
      if(!this.worldObj.isRemote && this.getWatchedTargetId(0) > 0) {
         Entity var1 = this.worldObj.getEntityByID(this.getWatchedTargetId(0));
         if(this.posY < var1.posY || !this.isArmored() && this.posY < var1.posY + 5.0D) {
            if(this.motionY < 0.0D) {
               this.motionY = 0.0D;
            }

            this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
         }

         double var2 = var1.posX - this.posX;
         double var4 = var1.posZ - this.posZ;
         double var6 = var2 * var2 + var4 * var4;
         if(var6 > 9.0D) {
            double var8 = (double)MathHelper.sqrt_double(var6);
            this.motionX += (var2 / var8 * 0.5D - this.motionX) * 0.6000000238418579D;
            this.motionZ += (var4 / var8 * 0.5D - this.motionZ) * 0.6000000238418579D;
         }
      }

      if(this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806D) {
         this.rotationYaw = (float)MathHelper.func_181159_b(this.motionZ, this.motionX) * 57.295776F - 90.0F;
      }

      super.onLivingUpdate();

      for(int var20 = 0; var20 < 2; ++var20) {
         this.field_82218_g[var20] = this.field_82221_e[var20];
         this.field_82217_f[var20] = this.field_82220_d[var20];
      }

      for(int var21 = 0; var21 < 2; ++var21) {
         int var23 = this.getWatchedTargetId(var21 + 1);
         Entity var3 = null;
         var3 = this.worldObj.getEntityByID(var23);
         double var28 = this.b(var21 + 1);
         double var29 = this.func_82208_v(var21 + 1);
         double var30 = this.j(var21 + 1);
         double var10 = var3.posX - var28;
         double var12 = var3.posY + (double)var3.getEyeHeight() - var29;
         double var14 = var3.posZ - var30;
         double var16 = (double)MathHelper.sqrt_double(var10 * var10 + var14 * var14);
         float var18 = (float)(MathHelper.func_181159_b(var14, var10) * 180.0D / 3.141592653589793D) - 90.0F;
         float var19 = (float)(-(MathHelper.func_181159_b(var12, var16) * 180.0D / 3.141592653589793D));
         this.field_82220_d[var21] = this.func_82204_b(this.field_82220_d[var21], var19, 40.0F);
         this.field_82221_e[var21] = this.func_82204_b(this.field_82221_e[var21], var18, 10.0F);
      }

      boolean var22 = this.isArmored();

      for(int var24 = 0; var24 < 3; ++var24) {
         double var27 = this.b(var24);
         double var5 = this.func_82208_v(var24);
         double var7 = this.j(var24);
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var27 + this.rand.nextGaussian() * 0.30000001192092896D, var5 + this.rand.nextGaussian() * 0.30000001192092896D, var7 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
         if(this.worldObj.rand.nextInt(4) == 0) {
            this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, var27 + this.rand.nextGaussian() * 0.30000001192092896D, var5 + this.rand.nextGaussian() * 0.30000001192092896D, var7 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
         }
      }

      if(this.getInvulTime() > 0) {
         for(int var25 = 0; var25 < 3; ++var25) {
            this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + (double)(this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
         }
      }

   }

   protected void updateAITasks() {
      if(this.getInvulTime() > 0) {
         int var1 = this.getInvulTime() - 1;
         this.worldObj.newExplosion(this, this.posX, this.posY + (double)this.getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getBoolean("mobGriefing"));
         this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
         this.setInvulTime(var1);
         if(this.ticksExisted % 10 == 0) {
            this.heal(10.0F);
         }
      } else {
         super.updateAITasks();

         for(int var13 = 1; var13 < 3; ++var13) {
            if(this.ticksExisted >= this.field_82223_h[var13 - 1]) {
               this.field_82223_h[var13 - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
               if(this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                  int var2 = var13 - 1;
                  int var3 = this.field_82224_i[var13 - 1];
                  this.field_82224_i[var2] = this.field_82224_i[var13 - 1] + 1;
                  if(var3 > 15) {
                     float var4 = 10.0F;
                     float var5 = 5.0F;
                     double var6 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - (double)var4, this.posX + (double)var4);
                     double var8 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - (double)var5, this.posY + (double)var5);
                     double var10 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - (double)var4, this.posZ + (double)var4);
                     this.launchWitherSkullToCoords(var13 + 1, var6, var8, var10, true);
                     this.field_82224_i[var13 - 1] = 0;
                  }
               }

               int var15 = this.getWatchedTargetId(var13);
               Entity var17 = this.worldObj.getEntityByID(var15);
               if(var17.isEntityAlive() && this.getDistanceSqToEntity(var17) <= 900.0D && this.canEntityBeSeen(var17)) {
                  if(var17 instanceof EntityPlayer && ((EntityPlayer)var17).abilities.isDisabledDamage()) {
                     this.updateWatchedTargetId(var13, 0);
                  } else {
                     this.launchWitherSkullToEntity(var13 + 1, (EntityLivingBase)var17);
                     this.field_82223_h[var13 - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
                     this.field_82224_i[var13 - 1] = 0;
                  }
               } else {
                  this.updateWatchedTargetId(var13, 0);
               }
            }
         }

         if(this.getAttackTarget() != null) {
            this.updateWatchedTargetId(0, this.getAttackTarget().getEntityID());
         } else {
            this.updateWatchedTargetId(0, 0);
         }

         if(this.blockBreakCounter > 0) {
            --this.blockBreakCounter;
            if(this.blockBreakCounter == 0 && this.worldObj.getGameRules().getBoolean("mobGriefing")) {
               int var14 = MathHelper.floor_double(this.posY);
               int var16 = MathHelper.floor_double(this.posX);
               int var18 = MathHelper.floor_double(this.posZ);
               boolean var19 = false;

               for(int var21 = -1; var21 <= 1; ++var21) {
                  for(int var22 = -1; var22 <= 1; ++var22) {
                     for(int var7 = 0; var7 <= 3; ++var7) {
                        int var23 = var16 + var21;
                        int var9 = var14 + var7;
                        int var24 = var18 + var22;
                        BlockPos var11 = new BlockPos(var23, var9, var24);
                        Block var12 = this.worldObj.getBlockState(var11).getBlock();
                        if(var12.getMaterial() != Material.air && func_181033_a(var12)) {
                           if(!this.worldObj.destroyBlock(var11, true)) {
                              ;
                           }

                           var19 = true;
                        }
                     }
                  }
               }

               this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1012, new BlockPos(this), 0);
            }
         }

         if(this.ticksExisted % 20 == 0) {
            this.heal(1.0F);
         }
      }

   }

   public static boolean func_181033_a(Block var0) {
      return var0 != Blocks.bedrock && var0 != Blocks.end_portal && var0 != Blocks.end_portal_frame && var0 != Blocks.command_block && var0 != Blocks.barrier;
   }

   public void func_82206_m() {
      this.setInvulTime(220);
      this.setHealth(this.getMaxHealth() / 3.0F);
   }

   public void setInWeb() {
   }

   public int getTotalArmorValue() {
      return 4;
   }

   private double b(int var1) {
      return this.posX;
   }

   private double func_82208_v(int var1) {
      return this.posY + 3.0D;
   }

   private double j(int var1) {
      return this.posZ;
   }

   private float func_82204_b(float var1, float var2, float var3) {
      float var4 = MathHelper.wrapAngleTo180_float(var2 - var1);
      if(var4 > var3) {
         var4 = var3;
      }

      if(var4 < -var3) {
         var4 = -var3;
      }

      return var1 + var4;
   }

   private void launchWitherSkullToEntity(int var1, EntityLivingBase var2) {
      this.launchWitherSkullToCoords(var1, var2.posX, var2.posY + (double)var2.getEyeHeight() * 0.5D, var2.posZ, this.rand.nextFloat() < 0.001F);
   }

   private void launchWitherSkullToCoords(int var1, double var2, double var4, double var6, boolean var8) {
      this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, new BlockPos(this), 0);
      double var9 = this.b(var1);
      double var11 = this.func_82208_v(var1);
      double var13 = this.j(var1);
      double var15 = var2 - var9;
      double var17 = var4 - var11;
      double var19 = var6 - var13;
      EntityWitherSkull var21 = new EntityWitherSkull(this.worldObj, this, var15, var17, var19);
      var21.setInvulnerable(true);
      var21.posY = var11;
      var21.posX = var9;
      var21.posZ = var13;
      this.worldObj.spawnEntityInWorld(var21);
   }

   public void attackEntityWithRangedAttack(EntityLivingBase var1, float var2) {
      this.launchWitherSkullToEntity(0, var1);
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(this.isEntityInvulnerable(var1)) {
         return false;
      } else if(var1 != DamageSource.drown && !(var1.getEntity() instanceof EntityWither)) {
         if(this.getInvulTime() > 0 && var1 != DamageSource.outOfWorld) {
            return false;
         } else {
            if(this.isArmored()) {
               Entity var3 = var1.getSourceOfDamage();
               if(var3 instanceof EntityArrow) {
                  return false;
               }
            }

            Entity var5 = var1.getEntity();
            if(!(var5 instanceof EntityPlayer) && var5 instanceof EntityLivingBase && ((EntityLivingBase)var5).getCreatureAttribute() == this.getCreatureAttribute()) {
               return false;
            } else {
               if(this.blockBreakCounter <= 0) {
                  this.blockBreakCounter = 20;
               }

               for(int var4 = 0; var4 < this.field_82224_i.length; ++var4) {
                  this.field_82224_i[var4] += 3;
               }

               return super.attackEntityFrom(var1, var2);
            }
         }
      } else {
         return false;
      }
   }

   protected void dropFewItems(boolean var1, int var2) {
      EntityItem var3 = this.dropItem(Items.nether_star, 1);
      var3.setNoDespawn();
      if(!this.worldObj.isRemote) {
         for(EntityPlayer var5 : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D))) {
            var5.triggerAchievement(AchievementList.killWither);
         }
      }

   }

   protected void despawnEntity() {
      this.entityAge = 0;
   }

   public int getBrightnessForRender(float var1) {
      return 15728880;
   }

   public void fall(float var1, float var2) {
   }

   public void addPotionEffect(PotionEffect var1) {
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
   }

   public float func_82207_a(int var1) {
      return this.field_82221_e[var1];
   }

   public float func_82210_r(int var1) {
      return this.field_82220_d[var1];
   }

   public int getInvulTime() {
      return this.I.c(20);
   }

   public void setInvulTime(int var1) {
      this.I.a(20, Integer.valueOf(var1));
   }

   public int getWatchedTargetId(int var1) {
      return this.I.c(17 + var1);
   }

   public void updateWatchedTargetId(int var1, int var2) {
      this.I.a(17 + var1, Integer.valueOf(var2));
   }

   public boolean isArmored() {
      return this.getHealth() <= this.getMaxHealth() / 2.0F;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   public void mountEntity(Entity var1) {
      this.ridingEntity = null;
   }

   private static boolean lambda$static$0(Entity var0) {
      return var0 instanceof EntityLivingBase && ((EntityLivingBase)var0).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
   }
}
