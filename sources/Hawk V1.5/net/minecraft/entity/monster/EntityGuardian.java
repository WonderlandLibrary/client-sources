package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityGuardian extends EntityMob {
   private int field_175479_bo;
   private EntityLivingBase field_175478_bn;
   private float field_175485_bl;
   private float field_175486_bm;
   private boolean field_175480_bp;
   private static final String __OBFID = "CL_00002213";
   private float field_175483_bk;
   private EntityAIWander field_175481_bq;
   private float field_175484_c;
   private float field_175482_b;

   public float func_175471_a(float var1) {
      return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * var1;
   }

   public int getTalkInterval() {
      return 160;
   }

   public boolean func_175474_cn() {
      return this.dataWatcher.getWatchableObjectInt(17) != 0;
   }

   public float func_180484_a(BlockPos var1) {
      return this.worldObj.getBlockState(var1).getBlock().getMaterial() == Material.water ? 10.0F + this.worldObj.getLightBrightness(var1) - 0.5F : super.func_180484_a(var1);
   }

   public boolean func_175472_n() {
      return this.func_175468_a(2);
   }

   public void func_175467_a(boolean var1) {
      this.func_175473_a(4, var1);
      if (var1) {
         this.setSize(1.9975F, 1.9975F);
         this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
         this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
         this.enablePersistence();
         this.field_175481_bq.func_179479_b(400);
      }

   }

   public EntityGuardian(World var1) {
      super(var1);
      this.experienceValue = 10;
      this.setSize(0.85F, 0.85F);
      this.tasks.addTask(4, new EntityGuardian.AIGuardianAttack(this));
      EntityAIMoveTowardsRestriction var2;
      this.tasks.addTask(5, var2 = new EntityAIMoveTowardsRestriction(this, 1.0D));
      this.tasks.addTask(7, this.field_175481_bq = new EntityAIWander(this, 1.0D, 80));
      this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
      this.tasks.addTask(9, new EntityAILookIdle(this));
      this.field_175481_bq.setMutexBits(3);
      var2.setMutexBits(3);
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new EntityGuardian.GuardianTargetSelector(this)));
      this.moveHelper = new EntityGuardian.GuardianMoveHelper(this);
      this.field_175484_c = this.field_175482_b = this.rand.nextFloat();
   }

   public boolean handleLavaMovement() {
      return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty();
   }

   protected void updateAITasks() {
      super.updateAITasks();
      if (this.func_175461_cl()) {
         boolean var1 = true;
         boolean var2 = true;
         boolean var3 = true;
         boolean var4 = true;
         if ((this.ticksExisted + this.getEntityId()) % 1200 == 0) {
            Potion var5 = Potion.digSlowdown;
            List var6 = this.worldObj.func_175661_b(EntityPlayerMP.class, new Predicate(this) {
               final EntityGuardian this$0;
               private static final String __OBFID = "CL_00002212";

               public boolean apply(Object var1) {
                  return this.func_179913_a((EntityPlayerMP)var1);
               }

               public boolean func_179913_a(EntityPlayerMP var1) {
                  return this.this$0.getDistanceSqToEntity(var1) < 2500.0D && var1.theItemInWorldManager.func_180239_c();
               }

               {
                  this.this$0 = var1;
               }
            });
            Iterator var7 = var6.iterator();

            label40:
            while(true) {
               EntityPlayerMP var8;
               do {
                  if (!var7.hasNext()) {
                     break label40;
                  }

                  var8 = (EntityPlayerMP)var7.next();
               } while(var8.isPotionActive(var5) && var8.getActivePotionEffect(var5).getAmplifier() >= 2 && var8.getActivePotionEffect(var5).getDuration() >= 1200);

               var8.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(10, 0.0F));
               var8.addPotionEffect(new PotionEffect(var5.id, 6000, 2));
            }
         }

         if (!this.hasHome()) {
            this.func_175449_a(new BlockPos(this), 16);
         }
      }

   }

   public float func_175469_o(float var1) {
      return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * var1;
   }

   public boolean getCanSpawnHere() {
      return (this.rand.nextInt(20) == 0 || !this.worldObj.canBlockSeeSky(new BlockPos(this))) && super.getCanSpawnHere();
   }

   private void func_175463_b(int var1) {
      this.dataWatcher.updateObject(17, var1);
   }

   private void func_175473_a(int var1, boolean var2) {
      int var3 = this.dataWatcher.getWatchableObjectInt(16);
      if (var2) {
         this.dataWatcher.updateObject(16, var3 | var1);
      } else {
         this.dataWatcher.updateObject(16, var3 & ~var1);
      }

   }

   public float func_175477_p(float var1) {
      return ((float)this.field_175479_bo + var1) / (float)this.func_175464_ck();
   }

   public boolean func_175461_cl() {
      return this.func_175468_a(4);
   }

   public void func_145781_i(int var1) {
      super.func_145781_i(var1);
      if (var1 == 16) {
         if (this.func_175461_cl() && this.width < 1.0F) {
            this.setSize(1.9975F, 1.9975F);
         }
      } else if (var1 == 17) {
         this.field_175479_bo = 0;
         this.field_175478_bn = null;
      }

   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
   }

   public void func_175465_cm() {
      this.func_175467_a(true);
      this.field_175486_bm = this.field_175485_bl = 1.0F;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setBoolean("Elder", this.func_175461_cl());
   }

   static EntityAIWander access$1(EntityGuardian var0) {
      return var0.field_175481_bq;
   }

   public EntityLivingBase func_175466_co() {
      if (!this.func_175474_cn()) {
         return null;
      } else if (this.worldObj.isRemote) {
         if (this.field_175478_bn != null) {
            return this.field_175478_bn;
         } else {
            Entity var1 = this.worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(17));
            if (var1 instanceof EntityLivingBase) {
               this.field_175478_bn = (EntityLivingBase)var1;
               return this.field_175478_bn;
            } else {
               return null;
            }
         }
      } else {
         return this.getAttackTarget();
      }
   }

   private boolean func_175468_a(int var1) {
      return (this.dataWatcher.getWatchableObjectInt(16) & var1) != 0;
   }

   protected String getHurtSound() {
      return !this.isInWater() ? "mob.guardian.land.hit" : (this.func_175461_cl() ? "mob.guardian.elder.hit" : "mob.guardian.hit");
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.func_175467_a(var1.getBoolean("Elder"));
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, 0);
      this.dataWatcher.addObject(17, 0);
   }

   protected PathNavigate func_175447_b(World var1) {
      return new PathNavigateSwimmer(this, var1);
   }

   public void moveEntityWithHeading(float var1, float var2) {
      if (this.isServerWorld()) {
         if (this.isInWater()) {
            this.moveFlying(var1, var2, 0.1F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8999999761581421D;
            this.motionY *= 0.8999999761581421D;
            this.motionZ *= 0.8999999761581421D;
            if (!this.func_175472_n() && this.getAttackTarget() == null) {
               this.motionY -= 0.005D;
            }
         } else {
            super.moveEntityWithHeading(var1, var2);
         }
      } else {
         super.moveEntityWithHeading(var1, var2);
      }

   }

   static void access$0(EntityGuardian var0, int var1) {
      var0.func_175463_b(var1);
   }

   public float getEyeHeight() {
      return this.height * 0.5F;
   }

   public void onLivingUpdate() {
      if (this.worldObj.isRemote) {
         this.field_175484_c = this.field_175482_b;
         if (!this.isInWater()) {
            this.field_175483_bk = 2.0F;
            if (this.motionY > 0.0D && this.field_175480_bp && !this.isSlient()) {
               this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.guardian.flop", 1.0F, 1.0F, false);
            }

            this.field_175480_bp = this.motionY < 0.0D && this.worldObj.func_175677_d((new BlockPos(this)).offsetDown(), false);
         } else if (this.func_175472_n()) {
            if (this.field_175483_bk < 0.5F) {
               this.field_175483_bk = 4.0F;
            } else {
               this.field_175483_bk += (0.5F - this.field_175483_bk) * 0.1F;
            }
         } else {
            this.field_175483_bk += (0.125F - this.field_175483_bk) * 0.2F;
         }

         this.field_175482_b += this.field_175483_bk;
         this.field_175486_bm = this.field_175485_bl;
         if (!this.isInWater()) {
            this.field_175485_bl = this.rand.nextFloat();
         } else if (this.func_175472_n()) {
            this.field_175485_bl += (0.0F - this.field_175485_bl) * 0.25F;
         } else {
            this.field_175485_bl += (1.0F - this.field_175485_bl) * 0.06F;
         }

         if (this.func_175472_n() && this.isInWater()) {
            Vec3 var1 = this.getLook(0.0F);

            for(int var2 = 0; var2 < 2; ++var2) {
               this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width - var1.xCoord * 1.5D, this.posY + this.rand.nextDouble() * (double)this.height - var1.yCoord * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width - var1.zCoord * 1.5D, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.func_175474_cn()) {
            if (this.field_175479_bo < this.func_175464_ck()) {
               ++this.field_175479_bo;
            }

            EntityLivingBase var14 = this.func_175466_co();
            if (var14 != null) {
               this.getLookHelper().setLookPositionWithEntity(var14, 90.0F, 90.0F);
               this.getLookHelper().onUpdateLook();
               double var15 = (double)this.func_175477_p(0.0F);
               double var4 = var14.posX - this.posX;
               double var6 = var14.posY + (double)(var14.height * 0.5F) - (this.posY + (double)this.getEyeHeight());
               double var8 = var14.posZ - this.posZ;
               double var10 = Math.sqrt(var4 * var4 + var6 * var6 + var8 * var8);
               var4 /= var10;
               var6 /= var10;
               var8 /= var10;
               double var12 = this.rand.nextDouble();

               while(var12 < var10) {
                  var12 += 1.8D - var15 + this.rand.nextDouble() * (1.7D - var15);
                  this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + var4 * var12, this.posY + var6 * var12 + (double)this.getEyeHeight(), this.posZ + var8 * var12, 0.0D, 0.0D, 0.0D);
               }
            }
         }
      }

      if (this.inWater) {
         this.setAir(300);
      } else if (this.onGround) {
         this.motionY += 0.5D;
         this.motionX += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
         this.motionZ += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
         this.rotationYaw = this.rand.nextFloat() * 360.0F;
         this.onGround = false;
         this.isAirBorne = true;
      }

      if (this.func_175474_cn()) {
         this.rotationYaw = this.rotationYawHead;
      }

      super.onLivingUpdate();
   }

   protected String getLivingSound() {
      return !this.isInWater() ? "mob.guardian.land.idle" : (this.func_175461_cl() ? "mob.guardian.elder.idle" : "mob.guardian.idle");
   }

   static void access$2(EntityGuardian var0, boolean var1) {
      var0.func_175476_l(var1);
   }

   protected void dropFewItems(boolean var1, int var2) {
      int var3 = this.rand.nextInt(3) + this.rand.nextInt(var2 + 1);
      if (var3 > 0) {
         this.entityDropItem(new ItemStack(Items.prismarine_shard, var3, 0), 1.0F);
      }

      if (this.rand.nextInt(3 + var2) > 1) {
         this.entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getItemDamage()), 1.0F);
      } else if (this.rand.nextInt(3 + var2) > 1) {
         this.entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0F);
      }

      if (var1 && this.func_175461_cl()) {
         this.entityDropItem(new ItemStack(Blocks.sponge, 1, 1), 1.0F);
      }

   }

   public int getVerticalFaceSpeed() {
      return 180;
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public int func_175464_ck() {
      return this.func_175461_cl() ? 60 : 80;
   }

   protected boolean isValidLightLevel() {
      return true;
   }

   protected String getDeathSound() {
      return !this.isInWater() ? "mob.guardian.land.death" : (this.func_175461_cl() ? "mob.guardian.elder.death" : "mob.guardian.death");
   }

   protected void addRandomArmor() {
      ItemStack var1 = ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.func_174855_j())).getItemStack(this.rand);
      this.entityDropItem(var1, 1.0F);
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if (!this.func_175472_n() && !var1.isMagicDamage() && var1.getSourceOfDamage() instanceof EntityLivingBase) {
         EntityLivingBase var3 = (EntityLivingBase)var1.getSourceOfDamage();
         if (!var1.isExplosion()) {
            var3.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
            var3.playSound("damage.thorns", 0.5F, 1.0F);
         }
      }

      this.field_175481_bq.func_179480_f();
      return super.attackEntityFrom(var1, var2);
   }

   private void func_175476_l(boolean var1) {
      this.func_175473_a(2, var1);
   }

   class GuardianMoveHelper extends EntityMoveHelper {
      private static final String __OBFID = "CL_00002209";
      final EntityGuardian this$0;
      private EntityGuardian field_179930_g;

      public void onUpdateMoveHelper() {
         if (this.update && !this.field_179930_g.getNavigator().noPath()) {
            double var1 = this.posX - this.field_179930_g.posX;
            double var3 = this.posY - this.field_179930_g.posY;
            double var5 = this.posZ - this.field_179930_g.posZ;
            double var7 = var1 * var1 + var3 * var3 + var5 * var5;
            var7 = (double)MathHelper.sqrt_double(var7);
            var3 /= var7;
            float var9 = (float)(Math.atan2(var5, var1) * 180.0D / 3.141592653589793D) - 90.0F;
            this.field_179930_g.rotationYaw = this.limitAngle(this.field_179930_g.rotationYaw, var9, 30.0F);
            this.field_179930_g.renderYawOffset = this.field_179930_g.rotationYaw;
            float var10 = (float)(this.speed * this.field_179930_g.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
            this.field_179930_g.setAIMoveSpeed(this.field_179930_g.getAIMoveSpeed() + (var10 - this.field_179930_g.getAIMoveSpeed()) * 0.125F);
            double var11 = Math.sin((double)(this.field_179930_g.ticksExisted + this.field_179930_g.getEntityId()) * 0.5D) * 0.05D;
            double var13 = Math.cos((double)(this.field_179930_g.rotationYaw * 3.1415927F / 180.0F));
            double var15 = Math.sin((double)(this.field_179930_g.rotationYaw * 3.1415927F / 180.0F));
            EntityGuardian var10000 = this.field_179930_g;
            var10000.motionX += var11 * var13;
            var10000 = this.field_179930_g;
            var10000.motionZ += var11 * var15;
            var11 = Math.sin((double)(this.field_179930_g.ticksExisted + this.field_179930_g.getEntityId()) * 0.75D) * 0.05D;
            var10000 = this.field_179930_g;
            var10000.motionY += var11 * (var15 + var13) * 0.25D;
            var10000 = this.field_179930_g;
            var10000.motionY += (double)this.field_179930_g.getAIMoveSpeed() * var3 * 0.1D;
            EntityLookHelper var17 = this.field_179930_g.getLookHelper();
            double var18 = this.field_179930_g.posX + var1 / var7 * 2.0D;
            double var20 = (double)this.field_179930_g.getEyeHeight() + this.field_179930_g.posY + var3 / var7 * 1.0D;
            double var22 = this.field_179930_g.posZ + var5 / var7 * 2.0D;
            double var24 = var17.func_180423_e();
            double var26 = var17.func_180422_f();
            double var28 = var17.func_180421_g();
            if (!var17.func_180424_b()) {
               var24 = var18;
               var26 = var20;
               var28 = var22;
            }

            this.field_179930_g.getLookHelper().setLookPosition(var24 + (var18 - var24) * 0.125D, var26 + (var20 - var26) * 0.125D, var28 + (var22 - var28) * 0.125D, 10.0F, 40.0F);
            EntityGuardian.access$2(this.field_179930_g, true);
         } else {
            this.field_179930_g.setAIMoveSpeed(0.0F);
            EntityGuardian.access$2(this.field_179930_g, false);
         }

      }

      public GuardianMoveHelper(EntityGuardian var1) {
         super(var1);
         this.this$0 = var1;
         this.field_179930_g = var1;
      }
   }

   class GuardianTargetSelector implements Predicate {
      private static final String __OBFID = "CL_00002210";
      private EntityGuardian field_179916_a;
      final EntityGuardian this$0;

      public boolean apply(Object var1) {
         return this.func_179915_a((EntityLivingBase)var1);
      }

      GuardianTargetSelector(EntityGuardian var1) {
         this.this$0 = var1;
         this.field_179916_a = var1;
      }

      public boolean func_179915_a(EntityLivingBase var1) {
         return (var1 instanceof EntityPlayer || var1 instanceof EntitySquid) && var1.getDistanceSqToEntity(this.field_179916_a) > 9.0D;
      }
   }

   class AIGuardianAttack extends EntityAIBase {
      private int field_179455_b;
      final EntityGuardian this$0;
      private static final String __OBFID = "CL_00002211";
      private EntityGuardian field_179456_a;

      public boolean continueExecuting() {
         return super.continueExecuting() && (this.field_179456_a.func_175461_cl() || this.field_179456_a.getDistanceSqToEntity(this.field_179456_a.getAttackTarget()) > 9.0D);
      }

      public void startExecuting() {
         this.field_179455_b = -10;
         this.field_179456_a.getNavigator().clearPathEntity();
         this.field_179456_a.getLookHelper().setLookPositionWithEntity(this.field_179456_a.getAttackTarget(), 90.0F, 90.0F);
         this.field_179456_a.isAirBorne = true;
      }

      public AIGuardianAttack(EntityGuardian var1) {
         this.this$0 = var1;
         this.field_179456_a = var1;
         this.setMutexBits(3);
      }

      public void resetTask() {
         EntityGuardian.access$0(this.field_179456_a, 0);
         this.field_179456_a.setAttackTarget((EntityLivingBase)null);
         EntityGuardian.access$1(this.field_179456_a).func_179480_f();
      }

      public boolean shouldExecute() {
         EntityLivingBase var1 = this.field_179456_a.getAttackTarget();
         return var1 != null && var1.isEntityAlive();
      }

      public void updateTask() {
         EntityLivingBase var1 = this.field_179456_a.getAttackTarget();
         this.field_179456_a.getNavigator().clearPathEntity();
         this.field_179456_a.getLookHelper().setLookPositionWithEntity(var1, 90.0F, 90.0F);
         if (!this.field_179456_a.canEntityBeSeen(var1)) {
            this.field_179456_a.setAttackTarget((EntityLivingBase)null);
         } else {
            ++this.field_179455_b;
            if (this.field_179455_b == 0) {
               EntityGuardian.access$0(this.field_179456_a, this.field_179456_a.getAttackTarget().getEntityId());
               this.field_179456_a.worldObj.setEntityState(this.field_179456_a, (byte)21);
            } else if (this.field_179455_b >= this.field_179456_a.func_175464_ck()) {
               float var2 = 1.0F;
               if (this.field_179456_a.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                  var2 += 2.0F;
               }

               if (this.field_179456_a.func_175461_cl()) {
                  var2 += 2.0F;
               }

               var1.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.field_179456_a, this.field_179456_a), var2);
               var1.attackEntityFrom(DamageSource.causeMobDamage(this.field_179456_a), (float)this.field_179456_a.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
               this.field_179456_a.setAttackTarget((EntityLivingBase)null);
            } else if (this.field_179455_b >= 60 && this.field_179455_b % 20 == 0) {
            }

            super.updateTask();
         }

      }
   }
}
