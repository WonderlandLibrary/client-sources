package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGuardian$AIGuardianAttack;
import net.minecraft.entity.monster.EntityGuardian$GuardianMoveHelper;
import net.minecraft.entity.monster.EntityGuardian$GuardianTargetSelector;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood$FishType;
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
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;

public class EntityGuardian extends EntityMob {
   private float field_175482_b;
   private float field_175484_c;
   private float field_175483_bk;
   private float field_175485_bl;
   private float field_175486_bm;
   private EntityLivingBase targetedEntity;
   private int field_175479_bo;
   private boolean field_175480_bp;
   private EntityAIWander wander;

   public EntityGuardian(World var1) {
      super(var1);
      this.experienceValue = 10;
      this.setSize(0.85F, 0.85F);
      this.tasks.addTask(4, new EntityGuardian$AIGuardianAttack(this));
      EntityAIMoveTowardsRestriction var2;
      this.tasks.addTask(5, var2 = new EntityAIMoveTowardsRestriction(this, 1.0D));
      this.tasks.addTask(7, this.wander = new EntityAIWander(this, 1.0D, 80));
      this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
      this.tasks.addTask(9, new EntityAILookIdle(this));
      this.wander.setMutexBits(3);
      var2.setMutexBits(3);
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new EntityGuardian$GuardianTargetSelector(this)));
      this.moveHelper = new EntityGuardian$GuardianMoveHelper(this);
      this.field_175484_c = this.field_175482_b = this.rand.nextFloat();
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.setElder(var1.getBoolean("Elder"));
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setBoolean("Elder", this.isElder());
   }

   protected PathNavigate getNewNavigator(World var1) {
      return new PathNavigateSwimmer(this, var1);
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(16, Integer.valueOf(0));
      this.I.b(17, Integer.valueOf(0));
   }

   private boolean isSyncedFlagSet(int var1) {
      return (this.I.c(16) & var1) != 0;
   }

   private void setSyncedFlag(int var1, boolean var2) {
      int var3 = this.I.c(16);
      this.I.a(16, Integer.valueOf(var3 | var1));
   }

   public boolean func_175472_n() {
      return this.isSyncedFlagSet(2);
   }

   private void func_175476_l(boolean var1) {
      this.setSyncedFlag(2, var1);
   }

   public int func_175464_ck() {
      return this.isElder()?60:80;
   }

   public boolean isElder() {
      return this.isSyncedFlagSet(4);
   }

   public void setElder(boolean var1) {
      this.setSyncedFlag(4, var1);
      this.setSize(1.9975F, 1.9975F);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
      this.enablePersistence();
      this.wander.setExecutionChance(400);
   }

   public void setElder() {
      this.setElder(true);
      this.field_175486_bm = this.field_175485_bl = 1.0F;
   }

   public boolean hasTargetedEntity() {
      return this.I.c(17) != 0;
   }

   public EntityLivingBase getTargetedEntity() {
      if(!this.hasTargetedEntity()) {
         return null;
      } else if(this.worldObj.isRemote) {
         if(this.targetedEntity != null) {
            return this.targetedEntity;
         } else {
            Entity var1 = this.worldObj.getEntityByID(this.I.c(17));
            if(var1 instanceof EntityLivingBase) {
               this.targetedEntity = (EntityLivingBase)var1;
               return this.targetedEntity;
            } else {
               return null;
            }
         }
      } else {
         return this.getAttackTarget();
      }
   }

   private void setTargetedEntity(int var1) {
      this.I.a(17, Integer.valueOf(var1));
   }

   public void onDataWatcherUpdate(int var1) {
      super.onDataWatcherUpdate(var1);
      if(var1 == 16) {
         if(this.isElder() && this.width < 1.0F) {
            this.setSize(1.9975F, 1.9975F);
         }
      } else if(var1 == 17) {
         this.field_175479_bo = 0;
         this.targetedEntity = null;
      }

   }

   public int getTalkInterval() {
      return 160;
   }

   protected String getLivingSound() {
      return !this.isInWater()?"mob.guardian.land.getId()le":(this.isElder()?"mob.guardian.elder.getId()le":"mob.guardian.getId()le");
   }

   protected String getHurtSound() {
      return !this.isInWater()?"mob.guardian.land.hit":(this.isElder()?"mob.guardian.elder.hit":"mob.guardian.hit");
   }

   protected String getDeathSound() {
      return !this.isInWater()?"mob.guardian.land.death":(this.isElder()?"mob.guardian.elder.death":"mob.guardian.death");
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public float getEyeHeight() {
      return this.height * 0.5F;
   }

   public float getBlockPathWeight(BlockPos var1) {
      return this.worldObj.getBlockState(var1).getBlock().getMaterial() == Material.water?10.0F + this.worldObj.getLightBrightness(var1) - 0.5F:super.getBlockPathWeight(var1);
   }

   public void onLivingUpdate() {
      if(this.worldObj.isRemote) {
         this.field_175484_c = this.field_175482_b;
         if(!this.isInWater()) {
            this.field_175483_bk = 2.0F;
            if(this.motionY > 0.0D && this.field_175480_bp && !this.isSilent()) {
               this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.guardian.flop", 1.0F, 1.0F, false);
            }

            this.field_175480_bp = this.motionY < 0.0D && this.worldObj.isBlockNormalCube((new BlockPos(this)).down(), false);
         } else if(this.func_175472_n()) {
            if(this.field_175483_bk < 0.5F) {
               this.field_175483_bk = 4.0F;
            } else {
               this.field_175483_bk += (0.5F - this.field_175483_bk) * 0.1F;
            }
         } else {
            this.field_175483_bk += (0.125F - this.field_175483_bk) * 0.2F;
         }

         this.field_175482_b += this.field_175483_bk;
         this.field_175486_bm = this.field_175485_bl;
         if(!this.isInWater()) {
            this.field_175485_bl = this.rand.nextFloat();
         } else if(this.func_175472_n()) {
            this.field_175485_bl += (0.0F - this.field_175485_bl) * 0.25F;
         } else {
            this.field_175485_bl += (1.0F - this.field_175485_bl) * 0.06F;
         }

         if(this.func_175472_n() && this.isInWater()) {
            Vec3 var1 = this.getLook(0.0F);

            for(int var2 = 0; var2 < 2; ++var2) {
               this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width - var1.xCoord * 1.5D, this.posY + this.rand.nextDouble() * (double)this.height - var1.yCoord * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width - var1.zCoord * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
            }
         }

         if(this.hasTargetedEntity()) {
            if(this.field_175479_bo < this.func_175464_ck()) {
               ++this.field_175479_bo;
            }

            EntityLivingBase var14 = this.getTargetedEntity();
            this.getLookHelper().setLookPositionWithEntity(var14, 90.0F, 90.0F);
            this.getLookHelper().onUpdateLook();
            double var15 = (double)this.func_175477_p(0.0F);
            double var4 = var14.posX - this.posX;
            double var6 = var14.posY + (double)(var14.height * 0.5F) - (this.posY + (double)this.getEyeHeight());
            double var8 = var14.posZ - this.posZ;
            double var10 = Math.sqrt(var4 * var4 + var6 * var6 + var8 * var8);
            var4 = var4 / var10;
            var6 = var6 / var10;
            var8 = var8 / var10;
            double var12 = this.rand.nextDouble();

            while(var12 < var10) {
               var12 += 1.8D - var15 + this.rand.nextDouble() * (1.7D - var15);
               this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + var4 * var12, this.posY + var6 * var12 + (double)this.getEyeHeight(), this.posZ + var8 * var12, 0.0D, 0.0D, 0.0D, new int[0]);
            }
         }
      }

      if(this.inWater) {
         this.setAir(300);
      } else if(this.onGround) {
         this.motionY += 0.5D;
         this.motionX += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
         this.motionZ += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
         this.rotationYaw = this.rand.nextFloat() * 360.0F;
         this.onGround = false;
         this.isAirBorne = true;
      }

      if(this.hasTargetedEntity()) {
         this.rotationYaw = this.rotationYawHead;
      }

      super.onLivingUpdate();
   }

   public float func_175471_a(float var1) {
      return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * var1;
   }

   public float func_175469_o(float var1) {
      return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * var1;
   }

   public float func_175477_p(float var1) {
      return ((float)this.field_175479_bo + var1) / (float)this.func_175464_ck();
   }

   protected void updateAITasks() {
      super.updateAITasks();
      if(this.isElder()) {
         if((this.ticksExisted + this.getEntityID()) % 1200 == 0) {
            Potion var1 = Potion.digSlowdown;

            for(EntityPlayerMP var3 : this.worldObj.getPlayers(EntityPlayerMP.class, this::lambda$updateAITasks$0)) {
               if(!var3.isPotionActive(var1) || var3.getActivePotionEffect(var1).getAmplifier() < 2 || var3.getActivePotionEffect(var1).getDuration() < 1200) {
                  var3.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(10, 0.0F));
                  var3.addPotionEffect(new PotionEffect(var1.getId(), 6000, 2));
               }
            }
         }

         if(!this.hasHome()) {
            this.setHomePosAndDistance(new BlockPos(this), 16);
         }
      }

   }

   protected void dropFewItems(boolean var1, int var2) {
      int var3 = this.rand.nextInt(3) + this.rand.nextInt(var2 + 1);
      this.entityDropItem(new ItemStack(Items.prismarine_shard, var3, 0), 1.0F);
      if(this.rand.nextInt(3 + var2) > 1) {
         this.entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood$FishType.COD.getMetadata()), 1.0F);
      } else if(this.rand.nextInt(3 + var2) > 1) {
         this.entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0F);
      }

      if(this.isElder()) {
         this.entityDropItem(new ItemStack(Blocks.sponge, 1, 1), 1.0F);
      }

   }

   protected void addRandomDrop() {
      ItemStack var1 = ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.func_174855_j())).getItemStack(this.rand);
      this.entityDropItem(var1, 1.0F);
   }

   protected boolean isValidLightLevel() {
      return true;
   }

   public boolean isNotColliding() {
      return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty();
   }

   public boolean getCanSpawnHere() {
      return (this.rand.nextInt(20) == 0 || !this.worldObj.canBlockSeeSky(new BlockPos(this))) && super.getCanSpawnHere();
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(!this.func_175472_n() && !var1.isMagicDamage() && var1.getSourceOfDamage() instanceof EntityLivingBase) {
         EntityLivingBase var3 = (EntityLivingBase)var1.getSourceOfDamage();
         if(!var1.isExplosion()) {
            var3.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
            var3.playSound("damage.thorns", 0.5F, 1.0F);
         }
      }

      this.wander.makeUpdate();
      return super.attackEntityFrom(var1, var2);
   }

   public int getVerticalFaceSpeed() {
      return 180;
   }

   public void moveEntityWithHeading(float var1, float var2) {
      if(this.isServerWorld()) {
         if(this.isInWater()) {
            this.moveFlying(var1, var2, 0.1F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8999999761581421D;
            this.motionY *= 0.8999999761581421D;
            this.motionZ *= 0.8999999761581421D;
            if(!this.func_175472_n() && this.getAttackTarget() == null) {
               this.motionY -= 0.005D;
            }
         } else {
            super.moveEntityWithHeading(var1, var2);
         }
      } else {
         super.moveEntityWithHeading(var1, var2);
      }

   }

   private boolean lambda$updateAITasks$0(EntityPlayerMP var1) {
      return this.getDistanceSqToEntity(var1) < 2500.0D && var1.theItemInWorldManager.survivalOrAdventure();
   }

   static void access$000(EntityGuardian var0, int var1) {
      var0.setTargetedEntity(var1);
   }

   static EntityAIWander access$100(EntityGuardian var0) {
      return var0.wander;
   }

   static void access$200(EntityGuardian var0, boolean var1) {
      var0.func_175476_l(var1);
   }
}
