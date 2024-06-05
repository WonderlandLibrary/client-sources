package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBoat extends Entity {
   private double boatYaw;
   private static final String __OBFID = "CL_00001667";
   private boolean isBoatEmpty;
   private double velocityX;
   private double boatX;
   private double boatY;
   private double velocityY;
   private double boatZ;
   private double boatPitch;
   private double velocityZ;
   private double speedMultiplier;
   private int boatPosRotationIncrements;

   public AxisAlignedBB getCollisionBox(Entity var1) {
      return var1.getEntityBoundingBox();
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   public void setIsBoatEmpty(boolean var1) {
      this.isBoatEmpty = var1;
   }

   public int getTimeSinceHit() {
      return this.dataWatcher.getWatchableObjectInt(17);
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
   }

   public void performHurtAnimation() {
      this.setForwardDirection(-this.getForwardDirection());
      this.setTimeSinceHit(10);
      this.setDamageTaken(this.getDamageTaken() * 11.0F);
   }

   public AxisAlignedBB getBoundingBox() {
      return this.getEntityBoundingBox();
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.getTimeSinceHit() > 0) {
         this.setTimeSinceHit(this.getTimeSinceHit() - 1);
      }

      if (this.getDamageTaken() > 0.0F) {
         this.setDamageTaken(this.getDamageTaken() - 1.0F);
      }

      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      byte var1 = 5;
      double var2 = 0.0D;

      for(int var4 = 0; var4 < var1; ++var4) {
         double var5 = this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (double)var4 / (double)var1 - 0.125D;
         double var7 = this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (double)(var4 + 1) / (double)var1 - 0.125D;
         AxisAlignedBB var9 = new AxisAlignedBB(this.getEntityBoundingBox().minX, var5, this.getEntityBoundingBox().minZ, this.getEntityBoundingBox().maxX, var7, this.getEntityBoundingBox().maxZ);
         if (this.worldObj.isAABBInMaterial(var9, Material.water)) {
            var2 += 1.0D / (double)var1;
         }
      }

      double var21 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      double var6;
      double var8;
      int var10;
      double var11;
      double var13;
      if (var21 > 0.2975D) {
         var6 = Math.cos((double)this.rotationYaw * 3.141592653589793D / 180.0D);
         var8 = Math.sin((double)this.rotationYaw * 3.141592653589793D / 180.0D);

         for(var10 = 0; (double)var10 < 1.0D + var21 * 60.0D; ++var10) {
            var11 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
            var13 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
            double var15;
            double var17;
            if (this.rand.nextBoolean()) {
               var15 = this.posX - var6 * var11 * 0.8D + var8 * var13;
               var17 = this.posZ - var8 * var11 * 0.8D - var6 * var13;
               this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, var15, this.posY - 0.125D, var17, this.motionX, this.motionY, this.motionZ);
            } else {
               var15 = this.posX + var6 + var8 * var11 * 0.7D;
               var17 = this.posZ + var8 - var6 * var11 * 0.7D;
               this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, var15, this.posY - 0.125D, var17, this.motionX, this.motionY, this.motionZ);
            }
         }
      }

      if (this.worldObj.isRemote && this.isBoatEmpty) {
         if (this.boatPosRotationIncrements > 0) {
            var6 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
            var8 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
            var11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
            var13 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + var13 / (double)this.boatPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
            --this.boatPosRotationIncrements;
            this.setPosition(var6, var8, var11);
            this.setRotation(this.rotationYaw, this.rotationPitch);
         } else {
            var6 = this.posX + this.motionX;
            var8 = this.posY + this.motionY;
            var11 = this.posZ + this.motionZ;
            this.setPosition(var6, var8, var11);
            if (this.onGround) {
               this.motionX *= 0.5D;
               this.motionY *= 0.5D;
               this.motionZ *= 0.5D;
            }

            this.motionX *= 0.9900000095367432D;
            this.motionY *= 0.949999988079071D;
            this.motionZ *= 0.9900000095367432D;
         }
      } else {
         if (var2 < 1.0D) {
            var6 = var2 * 2.0D - 1.0D;
            this.motionY += 0.03999999910593033D * var6;
         } else {
            if (this.motionY < 0.0D) {
               this.motionY /= 2.0D;
            }

            this.motionY += 0.007000000216066837D;
         }

         if (this.riddenByEntity instanceof EntityLivingBase) {
            EntityLivingBase var22 = (EntityLivingBase)this.riddenByEntity;
            float var16 = this.riddenByEntity.rotationYaw + -var22.moveStrafing * 90.0F;
            this.motionX += -Math.sin((double)(var16 * 3.1415927F / 180.0F)) * this.speedMultiplier * (double)var22.moveForward * 0.05000000074505806D;
            this.motionZ += Math.cos((double)(var16 * 3.1415927F / 180.0F)) * this.speedMultiplier * (double)var22.moveForward * 0.05000000074505806D;
         }

         var6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         if (var6 > 0.35D) {
            var8 = 0.35D / var6;
            this.motionX *= var8;
            this.motionZ *= var8;
            var6 = 0.35D;
         }

         if (var6 > var21 && this.speedMultiplier < 0.35D) {
            this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
            if (this.speedMultiplier > 0.35D) {
               this.speedMultiplier = 0.35D;
            }
         } else {
            this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
            if (this.speedMultiplier < 0.07D) {
               this.speedMultiplier = 0.07D;
            }
         }

         int var23;
         for(var23 = 0; var23 < 4; ++var23) {
            int var24 = MathHelper.floor_double(this.posX + ((double)(var23 % 2) - 0.5D) * 0.8D);
            var10 = MathHelper.floor_double(this.posZ + ((double)(var23 / 2) - 0.5D) * 0.8D);

            for(int var26 = 0; var26 < 2; ++var26) {
               int var18 = MathHelper.floor_double(this.posY) + var26;
               BlockPos var19 = new BlockPos(var24, var18, var10);
               Block var20 = this.worldObj.getBlockState(var19).getBlock();
               if (var20 == Blocks.snow_layer) {
                  this.worldObj.setBlockToAir(var19);
                  this.isCollidedHorizontally = false;
               } else if (var20 == Blocks.waterlily) {
                  this.worldObj.destroyBlock(var19, true);
                  this.isCollidedHorizontally = false;
               }
            }
         }

         if (this.onGround) {
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
         }

         this.moveEntity(this.motionX, this.motionY, this.motionZ);
         if (this.isCollidedHorizontally && var21 > 0.2D) {
            if (!this.worldObj.isRemote && !this.isDead) {
               this.setDead();

               for(var23 = 0; var23 < 3; ++var23) {
                  this.dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
               }

               for(var23 = 0; var23 < 2; ++var23) {
                  this.dropItemWithOffset(Items.stick, 1, 0.0F);
               }
            }
         } else {
            this.motionX *= 0.9900000095367432D;
            this.motionY *= 0.949999988079071D;
            this.motionZ *= 0.9900000095367432D;
         }

         this.rotationPitch = 0.0F;
         var8 = (double)this.rotationYaw;
         var11 = this.prevPosX - this.posX;
         var13 = this.prevPosZ - this.posZ;
         if (var11 * var11 + var13 * var13 > 0.001D) {
            var8 = (double)((float)(Math.atan2(var13, var11) * 180.0D / 3.141592653589793D));
         }

         double var25 = MathHelper.wrapAngleTo180_double(var8 - (double)this.rotationYaw);
         if (var25 > 20.0D) {
            var25 = 20.0D;
         }

         if (var25 < -20.0D) {
            var25 = -20.0D;
         }

         this.rotationYaw = (float)((double)this.rotationYaw + var25);
         this.setRotation(this.rotationYaw, this.rotationPitch);
         if (!this.worldObj.isRemote) {
            List var27 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
            if (var27 != null && !var27.isEmpty()) {
               for(int var28 = 0; var28 < var27.size(); ++var28) {
                  Entity var29 = (Entity)var27.get(var28);
                  if (var29 != this.riddenByEntity && var29.canBePushed() && var29 instanceof EntityBoat) {
                     var29.applyEntityCollision(this);
                  }
               }
            }

            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
               this.riddenByEntity = null;
            }
         }
      }

   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public double getMountedYOffset() {
      return (double)this.height * 0.0D - 0.30000001192092896D;
   }

   public int getForwardDirection() {
      return this.dataWatcher.getWatchableObjectInt(18);
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if (this.func_180431_b(var1)) {
         return false;
      } else if (!this.worldObj.isRemote && !this.isDead) {
         if (this.riddenByEntity != null && this.riddenByEntity == var1.getEntity() && var1 instanceof EntityDamageSourceIndirect) {
            return false;
         } else {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + var2 * 10.0F);
            this.setBeenAttacked();
            boolean var3 = var1.getEntity() instanceof EntityPlayer && ((EntityPlayer)var1.getEntity()).capabilities.isCreativeMode;
            if (var3 || this.getDamageTaken() > 40.0F) {
               if (this.riddenByEntity != null) {
                  this.riddenByEntity.mountEntity(this);
               }

               if (!var3) {
                  this.dropItemWithOffset(Items.boat, 1, 0.0F);
               }

               this.setDead();
            }

            return true;
         }
      } else {
         return true;
      }
   }

   public EntityBoat(World var1) {
      super(var1);
      this.isBoatEmpty = true;
      this.speedMultiplier = 0.07D;
      this.preventEntitySpawning = true;
      this.setSize(1.5F, 0.6F);
   }

   public void setTimeSinceHit(int var1) {
      this.dataWatcher.updateObject(17, var1);
   }

   protected void func_180433_a(double var1, boolean var3, Block var4, BlockPos var5) {
      if (var3) {
         if (this.fallDistance > 3.0F) {
            this.fall(this.fallDistance, 1.0F);
            if (!this.worldObj.isRemote && !this.isDead) {
               this.setDead();

               int var6;
               for(var6 = 0; var6 < 3; ++var6) {
                  this.dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
               }

               for(var6 = 0; var6 < 2; ++var6) {
                  this.dropItemWithOffset(Items.stick, 1, 0.0F);
               }
            }

            this.fallDistance = 0.0F;
         }
      } else if (this.worldObj.getBlockState((new BlockPos(this)).offsetDown()).getBlock().getMaterial() != Material.water && var1 < 0.0D) {
         this.fallDistance = (float)((double)this.fallDistance - var1);
      }

   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   public boolean canBePushed() {
      return true;
   }

   public void func_180426_a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      if (var10 && this.riddenByEntity != null) {
         this.prevPosX = this.posX = var1;
         this.prevPosY = this.posY = var3;
         this.prevPosZ = this.posZ = var5;
         this.rotationYaw = var7;
         this.rotationPitch = var8;
         this.boatPosRotationIncrements = 0;
         this.setPosition(var1, var3, var5);
         this.motionX = this.velocityX = 0.0D;
         this.motionY = this.velocityY = 0.0D;
         this.motionZ = this.velocityZ = 0.0D;
      } else {
         if (this.isBoatEmpty) {
            this.boatPosRotationIncrements = var9 + 5;
         } else {
            double var11 = var1 - this.posX;
            double var13 = var3 - this.posY;
            double var15 = var5 - this.posZ;
            double var17 = var11 * var11 + var13 * var13 + var15 * var15;
            if (var17 <= 1.0D) {
               return;
            }

            this.boatPosRotationIncrements = 3;
         }

         this.boatX = var1;
         this.boatY = var3;
         this.boatZ = var5;
         this.boatYaw = (double)var7;
         this.boatPitch = (double)var8;
         this.motionX = this.velocityX;
         this.motionY = this.velocityY;
         this.motionZ = this.velocityZ;
      }

   }

   public void setForwardDirection(int var1) {
      this.dataWatcher.updateObject(18, var1);
   }

   public void setDamageTaken(float var1) {
      this.dataWatcher.updateObject(19, var1);
   }

   protected void entityInit() {
      this.dataWatcher.addObject(17, new Integer(0));
      this.dataWatcher.addObject(18, new Integer(1));
      this.dataWatcher.addObject(19, new Float(0.0F));
   }

   public void updateRiderPosition() {
      if (this.riddenByEntity != null) {
         double var1 = Math.cos((double)this.rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
         double var3 = Math.sin((double)this.rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
         this.riddenByEntity.setPosition(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
      }

   }

   public EntityBoat(World var1, double var2, double var4, double var6) {
      this(var1);
      this.setPosition(var2, var4, var6);
      this.motionX = 0.0D;
      this.motionY = 0.0D;
      this.motionZ = 0.0D;
      this.prevPosX = var2;
      this.prevPosY = var4;
      this.prevPosZ = var6;
   }

   public boolean interactFirst(EntityPlayer var1) {
      if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != var1) {
         return true;
      } else {
         if (!this.worldObj.isRemote) {
            var1.mountEntity(this);
         }

         return true;
      }
   }

   public float getDamageTaken() {
      return this.dataWatcher.getWatchableObjectFloat(19);
   }

   public void setVelocity(double var1, double var3, double var5) {
      this.velocityX = this.motionX = var1;
      this.velocityY = this.motionY = var3;
      this.velocityZ = this.motionZ = var5;
   }
}
