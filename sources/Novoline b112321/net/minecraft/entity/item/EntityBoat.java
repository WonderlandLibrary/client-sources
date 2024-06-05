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
   private boolean isBoatEmpty;
   private double speedMultiplier;
   private int boatPosRotationIncrements;
   private double boatX;
   private double boatY;
   private double boatZ;
   private double boatYaw;
   private double boatPitch;
   private double velocityX;
   private double velocityY;
   private double velocityZ;

   public EntityBoat(World var1) {
      super(var1);
      this.isBoatEmpty = true;
      this.speedMultiplier = 0.07D;
      this.preventEntitySpawning = true;
      this.setSize(1.5F, 0.6F);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void entityInit() {
      this.I.b(17, Integer.valueOf(0));
      this.I.b(18, Integer.valueOf(1));
      this.I.b(19, Float.valueOf(0.0F));
   }

   public AxisAlignedBB getCollisionBox(Entity var1) {
      return var1.getEntityBoundingBox();
   }

   public AxisAlignedBB getCollisionBoundingBox() {
      return this.getEntityBoundingBox();
   }

   public boolean canBePushed() {
      return true;
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

   public double getMountedYOffset() {
      return -0.3D;
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(this.isEntityInvulnerable(var1)) {
         return false;
      } else if(!this.worldObj.isRemote && !this.isDead) {
         if(this.riddenByEntity != null && this.riddenByEntity == var1.getEntity() && var1 instanceof EntityDamageSourceIndirect) {
            return false;
         } else {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + var2 * 10.0F);
            this.setBeenAttacked();
            boolean var3 = var1.getEntity() instanceof EntityPlayer && ((EntityPlayer)var1.getEntity()).abilities.isCreative();
            if(this.getDamageTaken() > 40.0F) {
               if(this.riddenByEntity != null) {
                  this.riddenByEntity.mountEntity(this);
               }

               if(this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
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

   public void performHurtAnimation() {
      this.setForwardDirection(-this.getForwardDirection());
      this.setTimeSinceHit(10);
      this.setDamageTaken(this.getDamageTaken() * 11.0F);
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      if(this.riddenByEntity != null) {
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
         if(this.isBoatEmpty) {
            this.boatPosRotationIncrements = var9 + 5;
         } else {
            double var11 = var1 - this.posX;
            double var13 = var3 - this.posY;
            double var15 = var5 - this.posZ;
            double var17 = var11 * var11 + var13 * var13 + var15 * var15;
            if(var17 <= 1.0D) {
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

   public void setVelocity(double var1, double var3, double var5) {
      this.velocityX = this.motionX = var1;
      this.velocityY = this.motionY = var3;
      this.velocityZ = this.motionZ = var5;
   }

   public void onUpdate() {
      super.onUpdate();
      if(this.getTimeSinceHit() > 0) {
         this.setTimeSinceHit(this.getTimeSinceHit() - 1);
      }

      if(this.getDamageTaken() > 0.0F) {
         this.setDamageTaken(this.getDamageTaken() - 1.0F);
      }

      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      byte var1 = 5;
      double var2 = 0.0D;

      for(int var4 = 0; var4 < var1; ++var4) {
         double var5 = this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (double)(var4 + 0) / (double)var1 - 0.125D;
         double var7 = this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (double)(var4 + 1) / (double)var1 - 0.125D;
         AxisAlignedBB var9 = new AxisAlignedBB(this.getEntityBoundingBox().minX, var5, this.getEntityBoundingBox().minZ, this.getEntityBoundingBox().maxX, var7, this.getEntityBoundingBox().maxZ);
         if(this.worldObj.isAABBInMaterial(var9, Material.water)) {
            var2 += 1.0D / (double)var1;
         }
      }

      double var19 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      if(var19 > 0.2975D) {
         double var6 = (double)MathHelper.cos((double)this.rotationYaw * 3.141592653589793D / 180.0D);
         double var8 = (double)MathHelper.sin((double)this.rotationYaw * 3.141592653589793D / 180.0D);

         for(int var10 = 0; (double)var10 < 1.0D + var19 * 60.0D; ++var10) {
            double var11 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
            double var13 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
            if(this.rand.nextBoolean()) {
               double var15 = this.posX - var6 * var11 * 0.8D + var8 * var13;
               double var17 = this.posZ - var8 * var11 * 0.8D - var6 * var13;
               this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, var15, this.posY - 0.125D, var17, this.motionX, this.motionY, this.motionZ, new int[0]);
            } else {
               double var43 = this.posX + var6 + var8 * var11 * 0.7D;
               double var44 = this.posZ + var8 - var6 * var11 * 0.7D;
               this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, var43, this.posY - 0.125D, var44, this.motionX, this.motionY, this.motionZ, new int[0]);
            }
         }
      }

      if(this.worldObj.isRemote && this.isBoatEmpty) {
         if(this.boatPosRotationIncrements > 0) {
            double var23 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
            double var31 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
            double var36 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
            double var40 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + var40 / (double)this.boatPosRotationIncrements);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
            --this.boatPosRotationIncrements;
            this.setPosition(var23, var31, var36);
            this.setRotation(this.rotationYaw, this.rotationPitch);
         } else {
            double var24 = this.posX + this.motionX;
            double var32 = this.posY + this.motionY;
            double var37 = this.posZ + this.motionZ;
            this.setPosition(var24, var32, var37);
            if(this.onGround) {
               this.motionX *= 0.5D;
               this.motionY *= 0.5D;
               this.motionZ *= 0.5D;
            }

            this.motionX *= 0.9900000095367432D;
            this.motionY *= 0.949999988079071D;
            this.motionZ *= 0.9900000095367432D;
         }
      } else {
         if(var2 < 1.0D) {
            double var20 = var2 * 2.0D - 1.0D;
            this.motionY += 0.03999999910593033D * var20;
         } else {
            if(this.motionY < 0.0D) {
               this.motionY /= 2.0D;
            }

            this.motionY += 0.007000000216066837D;
         }

         if(this.riddenByEntity instanceof EntityLivingBase) {
            EntityLivingBase var21 = (EntityLivingBase)this.riddenByEntity;
            float var25 = this.riddenByEntity.rotationYaw + -var21.moveStrafing * 90.0F;
            this.motionX += (double)(-MathHelper.sin(var25 * 3.1415927F / 180.0F)) * this.speedMultiplier * (double)var21.moveForward * 0.05000000074505806D;
            this.motionZ += (double)MathHelper.cos(var25 * 3.1415927F / 180.0F) * this.speedMultiplier * (double)var21.moveForward * 0.05000000074505806D;
         }

         double var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         if(var22 > 0.35D) {
            double var26 = 0.35D / var22;
            this.motionX *= var26;
            this.motionZ *= var26;
            var22 = 0.35D;
         }

         if(var22 > var19 && this.speedMultiplier < 0.35D) {
            this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
            if(this.speedMultiplier > 0.35D) {
               this.speedMultiplier = 0.35D;
            }
         } else {
            this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
            if(this.speedMultiplier < 0.07D) {
               this.speedMultiplier = 0.07D;
            }
         }

         for(int var27 = 0; var27 < 4; ++var27) {
            int var33 = MathHelper.floor_double(this.posX + ((double)(var27 % 2) - 0.5D) * 0.8D);
            int var34 = MathHelper.floor_double(this.posZ + ((double)(var27 / 2) - 0.5D) * 0.8D);

            for(int var38 = 0; var38 < 2; ++var38) {
               int var12 = MathHelper.floor_double(this.posY) + var38;
               BlockPos var41 = new BlockPos(var33, var12, var34);
               Block var14 = this.worldObj.getBlockState(var41).getBlock();
               if(var14 == Blocks.snow_layer) {
                  this.worldObj.setBlockToAir(var41);
                  this.isCollidedHorizontally = false;
               } else if(var14 == Blocks.waterlily) {
                  this.worldObj.destroyBlock(var41, true);
                  this.isCollidedHorizontally = false;
               }
            }
         }

         if(this.onGround) {
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
         }

         this.moveEntity(this.motionX, this.motionY, this.motionZ);
         if(this.isCollidedHorizontally && var19 > 0.2975D) {
            if(!this.worldObj.isRemote && !this.isDead) {
               this.setDead();
               if(this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                  for(int var28 = 0; var28 < 3; ++var28) {
                     this.dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
                  }

                  for(int var29 = 0; var29 < 2; ++var29) {
                     this.dropItemWithOffset(Items.stick, 1, 0.0F);
                  }
               }
            }
         } else {
            this.motionX *= 0.9900000095367432D;
            this.motionY *= 0.949999988079071D;
            this.motionZ *= 0.9900000095367432D;
         }

         this.rotationPitch = 0.0F;
         double var30 = (double)this.rotationYaw;
         double var35 = this.prevPosX - this.posX;
         double var39 = this.prevPosZ - this.posZ;
         if(var35 * var35 + var39 * var39 > 0.001D) {
            var30 = (double)((float)(MathHelper.func_181159_b(var39, var35) * 180.0D / 3.141592653589793D));
         }

         double var42 = MathHelper.wrapAngleTo180_double(var30 - (double)this.rotationYaw);
         if(var42 > 20.0D) {
            var42 = 20.0D;
         }

         if(var42 < -20.0D) {
            var42 = -20.0D;
         }

         this.rotationYaw = (float)((double)this.rotationYaw + var42);
         this.setRotation(this.rotationYaw, this.rotationPitch);
         if(!this.worldObj.isRemote) {
            List var16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
            if(!var16.isEmpty()) {
               for(Entity var18 : var16) {
                  if(var18 != this.riddenByEntity && var18.canBePushed() && var18 instanceof EntityBoat) {
                     var18.applyEntityCollision(this);
                  }
               }
            }

            if(this.riddenByEntity != null && this.riddenByEntity.isDead) {
               this.riddenByEntity = null;
            }
         }
      }

   }

   public void updateRiderPosition() {
      if(this.riddenByEntity != null) {
         double var1 = (double)MathHelper.cos((double)this.rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
         double var3 = (double)MathHelper.sin((double)this.rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
         this.riddenByEntity.setPosition(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
      }

   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   public boolean interactFirst(EntityPlayer var1) {
      if((this.riddenByEntity == null || !(this.riddenByEntity instanceof EntityPlayer) || this.riddenByEntity == var1) && !this.worldObj.isRemote) {
         var1.mountEntity(this);
      }

      return true;
   }

   protected void updateFallState(double var1, boolean var3, Block var4, BlockPos var5) {
      if(this.fallDistance > 3.0F) {
         this.fall(this.fallDistance, 1.0F);
         if(!this.worldObj.isRemote && !this.isDead) {
            this.setDead();
            if(this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
               for(int var6 = 0; var6 < 3; ++var6) {
                  this.dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
               }

               for(int var7 = 0; var7 < 2; ++var7) {
                  this.dropItemWithOffset(Items.stick, 1, 0.0F);
               }
            }
         }

         this.fallDistance = 0.0F;
      }

   }

   public float getDamageTaken() {
      return this.I.b(19);
   }

   public void setDamageTaken(float var1) {
      this.I.a(19, Float.valueOf(var1));
   }

   public int getTimeSinceHit() {
      return this.I.c(17);
   }

   public void setTimeSinceHit(int var1) {
      this.I.a(17, Integer.valueOf(var1));
   }

   public int getForwardDirection() {
      return this.I.c(18);
   }

   public void setForwardDirection(int var1) {
      this.I.a(18, Integer.valueOf(var1));
   }

   public void setIsBoatEmpty(boolean var1) {
      this.isBoatEmpty = var1;
   }
}
