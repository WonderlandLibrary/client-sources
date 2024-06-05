package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public abstract class EntityHanging extends Entity {
   private int tickCounter1;
   protected BlockPos hangingPosition;
   public EnumFacing facingDirection;

   public EntityHanging(World var1) {
      super(var1);
      this.setSize(0.5F, 0.5F);
   }

   public EntityHanging(World var1, BlockPos var2) {
      this(var1);
      this.hangingPosition = var2;
   }

   protected void entityInit() {
   }

   protected void updateFacingWithBoundingBox(EnumFacing var1) {
      Validate.notNull(var1);
      Validate.isTrue(var1.getAxis().isHorizontal());
      this.facingDirection = var1;
      this.prevRotationYaw = this.rotationYaw = (float)(this.facingDirection.getHorizontalIndex() * 90);
      this.updateBoundingBox();
   }

   private void updateBoundingBox() {
      if(this.facingDirection != null) {
         double var1 = (double)this.hangingPosition.getX() + 0.5D;
         double var3 = (double)this.hangingPosition.getY() + 0.5D;
         double var5 = (double)this.hangingPosition.getZ() + 0.5D;
         double var7 = 0.46875D;
         double var9 = this.func_174858_a(this.getWidthPixels());
         double var11 = this.func_174858_a(this.getHeightPixels());
         var1 = var1 - (double)this.facingDirection.getFrontOffsetX() * 0.46875D;
         var5 = var5 - (double)this.facingDirection.getFrontOffsetZ() * 0.46875D;
         var3 = var3 + var11;
         EnumFacing var13 = this.facingDirection.rotateYCCW();
         var1 = var1 + var9 * (double)var13.getFrontOffsetX();
         var5 = var5 + var9 * (double)var13.getFrontOffsetZ();
         this.posX = var1;
         this.posY = var3;
         this.posZ = var5;
         double var14 = (double)this.getWidthPixels();
         double var16 = (double)this.getHeightPixels();
         double var18 = (double)this.getWidthPixels();
         if(this.facingDirection.getAxis() == EnumFacing$Axis.Z) {
            var18 = 1.0D;
         } else {
            var14 = 1.0D;
         }

         var14 = var14 / 32.0D;
         var16 = var16 / 32.0D;
         var18 = var18 / 32.0D;
         this.setEntityBoundingBox(new AxisAlignedBB(var1 - var14, var3 - var16, var5 - var18, var1 + var14, var3 + var16, var5 + var18));
      }

   }

   private double func_174858_a(int var1) {
      return var1 % 32 == 0?0.5D:0.0D;
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if(this.tickCounter1++ == 100 && !this.worldObj.isRemote) {
         this.tickCounter1 = 0;
         if(!this.isDead && !this.onValidSurface()) {
            this.setDead();
            this.onBroken((Entity)null);
         }
      }

   }

   public boolean onValidSurface() {
      if(!this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
         return false;
      } else {
         int var1 = Math.max(1, this.getWidthPixels() / 16);
         int var2 = Math.max(1, this.getHeightPixels() / 16);
         BlockPos var3 = this.hangingPosition.offset(this.facingDirection.getOpposite());
         EnumFacing var4 = this.facingDirection.rotateYCCW();

         for(int var5 = 0; var5 < var1; ++var5) {
            for(int var6 = 0; var6 < var2; ++var6) {
               BlockPos var7 = var3.offset(var4, var5).up(var6);
               Block var8 = this.worldObj.getBlockState(var7).getBlock();
               if(!var8.getMaterial().isSolid() && !BlockRedstoneDiode.isRedstoneRepeaterBlockID(var8)) {
                  return false;
               }
            }
         }

         for(Entity var10 : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox())) {
            if(var10 instanceof EntityHanging) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean hitByEntity(Entity var1) {
      return var1 instanceof EntityPlayer && this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)var1), 0.0F);
   }

   public EnumFacing getHorizontalFacing() {
      return this.facingDirection;
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(this.isEntityInvulnerable(var1)) {
         return false;
      } else {
         if(!this.isDead && !this.worldObj.isRemote) {
            this.setDead();
            this.setBeenAttacked();
            this.onBroken(var1.getEntity());
         }

         return true;
      }
   }

   public void moveEntity(double var1, double var3, double var5) {
      if(!this.worldObj.isRemote && !this.isDead && var1 * var1 + var3 * var3 + var5 * var5 > 0.0D) {
         this.setDead();
         this.onBroken((Entity)null);
      }

   }

   public void addVelocity(double var1, double var3, double var5) {
      if(!this.worldObj.isRemote && !this.isDead && var1 * var1 + var3 * var3 + var5 * var5 > 0.0D) {
         this.setDead();
         this.onBroken((Entity)null);
      }

   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      var1.setByte("Facing", (byte)this.facingDirection.getHorizontalIndex());
      var1.setInteger("TileX", this.getHangingPosition().getX());
      var1.setInteger("TileY", this.getHangingPosition().getY());
      var1.setInteger("TileZ", this.getHangingPosition().getZ());
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      this.hangingPosition = new BlockPos(var1.getInteger("TileX"), var1.getInteger("TileY"), var1.getInteger("TileZ"));
      EnumFacing var2;
      if(var1.hasKey("Direction", 99)) {
         var2 = EnumFacing.getHorizontal(var1.getByte("Direction"));
         this.hangingPosition = this.hangingPosition.offset(var2);
      } else if(var1.hasKey("Facing", 99)) {
         var2 = EnumFacing.getHorizontal(var1.getByte("Facing"));
      } else {
         var2 = EnumFacing.getHorizontal(var1.getByte("Dir"));
      }

      this.updateFacingWithBoundingBox(var2);
   }

   public abstract int getWidthPixels();

   public abstract int getHeightPixels();

   public abstract void onBroken(Entity var1);

   protected boolean shouldSetPosAfterLoading() {
      return false;
   }

   public void setPosition(double var1, double var3, double var5) {
      this.posX = var1;
      this.posY = var3;
      this.posZ = var5;
      BlockPos var7 = this.hangingPosition;
      this.hangingPosition = new BlockPos(var1, var3, var5);
      if(!this.hangingPosition.equals(var7)) {
         this.updateBoundingBox();
         this.isAirBorne = true;
      }

   }

   public BlockPos getHangingPosition() {
      return this.hangingPosition;
   }
}
